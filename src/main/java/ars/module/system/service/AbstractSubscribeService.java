package ars.module.system.service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.client.methods.HttpUriRequest;

import ars.util.Beans;
import ars.util.Servers;
import ars.util.AbstractTimerServer;
import ars.invoke.remote.Remotes;
import ars.invoke.remote.Protocol;
import ars.invoke.request.Requester;
import ars.invoke.event.InvokeEvent;
import ars.invoke.event.InvokeListener;
import ars.invoke.channel.http.Https;
import ars.invoke.event.InvokeAfterEvent;
import ars.invoke.event.InvokeErrorEvent;
import ars.invoke.event.InvokeBeforeEvent;
import ars.invoke.event.InvokeCompleteEvent;
import ars.module.system.model.Message;
import ars.module.system.model.Subscribe;
import ars.database.repository.Repository;
import ars.database.repository.Repositories;
import ars.database.service.StandardGeneralService;

/**
 * 请求订阅业务操作抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractSubscribeService<T extends Subscribe> extends StandardGeneralService<T>
    implements SubscribeService<T>, InvokeListener<InvokeEvent> {
    private int batch = 1000; // 消息同步批次
    private Map<String, T> subscribes; // 请求订阅资源定制/订阅实体映射
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AbstractSubscribeService() {
        this.initRefreshServer();
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        if (batch < 1) {
            throw new IllegalArgumentException("Illegal batch:" + batch);
        }
        this.batch = batch;
    }

    /**
     * 初始化消息刷新服务
     */
    protected void initRefreshServer() {
        new AbstractTimerServer() {

            @Override
            protected void execute() throws Exception {
                refresh();
            }

        }.start();
    }

    /**
     * 初始化请求订阅缓存
     */
    protected void initSubscribeCache() {
        if (this.subscribes == null) {
            List<T> subscribes = this.getRepository().query().list();
            this.subscribes = new HashMap<String, T>(subscribes.size());
            for (T subscribe : subscribes) {
                this.subscribes.put(subscribe.getTarget(), subscribe);
            }
        }
    }

    /**
     * 获取当前请求对应对应的请求订阅对象
     *
     * @param requester 请求对象
     * @return 请求订阅对象
     */
    protected T getSubscribe(Requester requester) {
        if (this.subscribes == null) {
            synchronized (this) {
                this.initSubscribeCache();
            }
        }
        return this.subscribes.get(requester.getUri());
    }

    /**
     * 消息推送
     *
     * @param requester 请求对象
     * @param subscribe 请求订阅对象
     * @throws Exception 操作异常
     */
    protected void push(Requester requester, Subscribe subscribe) throws Exception {
        Protocol protocol = subscribe.getProtocol();
        String host = subscribe.getHost();
        Integer port = subscribe.getPort();
        String resource = subscribe.getResource();
        if (protocol == Protocol.http || protocol == Protocol.https) {
            String url = Https.getUrl(protocol, host, port, resource);
            HttpUriRequest httpUriRequest = Https.getHttpUriRequest(url.toString(), Https.Method.POST,
                requester.getParameters());
            httpUriRequest.addHeader(Https.CONTEXT_TOKEN, requester.getToken().getCode());
            httpUriRequest.addHeader(Https.CONTEXT_CLIENT, Remotes.getClient());
            Https.getBytes(httpUriRequest);
        } else if (protocol == Protocol.ssl || protocol == Protocol.tcp || protocol == Protocol.udp) {
            Remotes.invoke(Remotes.getProxy(protocol, host, port), requester.getToken(), resource,
                requester.getParameters());
        } else {
            throw new RuntimeException("Not support protocol:" + protocol);
        }
    }

    /**
     * 消息同步（如果消息推送失败则将消息保存到数据库）
     *
     * @param requester 请求对象
     * @param subscribe 订阅对象
     */
    protected void synchron(Requester requester, Subscribe subscribe) {
        try {
            this.push(requester, subscribe);
        } catch (Exception e) {
            this.logger.error("Message synchronization failed", e);
            Repository<Message> repository = Repositories.getRepository(Message.class);
            Message message = Beans.getInstance(repository.getModel());
            message.setSubscribe(subscribe);
            message.setRequester(requester);
            repository.save(message);
        }
    }

    /**
     * 刷新消息（从数据库获取消息并重新推送，推送成功后删除消息实体）
     */
    protected void refresh() {
        Repository<Message> repository = Repositories.getRepository(Message.class);
        int count = repository.query().count();
        for (int page = 1, total = (int) Math.ceil((double) count / (double) this.batch); page <= total; page++) {
            List<Message> messages = repository.query().paging(page, this.batch).asc("dateJoined").list();
            for (final Message message : messages) {
                try {
                    Servers.submit(new Callable<Object>() {

                        @Override
                        public Object call() throws Exception {
                            push(message.getRequester(), message.getSubscribe());
                            return null;
                        }

                    }).get();
                    repository.delete(message);
                } catch (Exception e) {
                    this.logger.error("Message synchronization failed", e);
                    message.setResend(message.getResend() + 1);
                    repository.update(message);
                }
            }
        }
    }

    @Override
    public Serializable saveObject(Requester requester, T object) {
        Serializable id = super.saveObject(requester, object);
        synchronized (this) {
            this.initSubscribeCache();
            this.subscribes.put(object.getTarget(), object);
        }
        return id;
    }

    @Override
    public void updateObject(Requester requester, T object) {
        super.updateObject(requester, object);
        synchronized (this) {
            this.initSubscribeCache();
            this.subscribes.put(object.getTarget(), object);
        }
    }

    @Override
    public void deleteObject(Requester requester, T object) {
        super.deleteObject(requester, object);
        synchronized (this) {
            this.initSubscribeCache();
            this.subscribes.remove(object.getTarget());
        }
    }

    @Override
    public void onInvokeEvent(InvokeEvent event) {
        final Requester requester = event.getSource();
        final T subscribe = this.getSubscribe(requester);
        if (subscribe != null && ((subscribe.getEvent() == Subscribe.Event.BEFORE && event instanceof InvokeBeforeEvent)
            || (subscribe.getEvent() == Subscribe.Event.AFTER && event instanceof InvokeAfterEvent)
            || (subscribe.getEvent() == Subscribe.Event.ERROR && event instanceof InvokeErrorEvent)
            || (subscribe.getEvent() == Subscribe.Event.COMPLETE && event instanceof InvokeCompleteEvent))) {
            Servers.execute(new Runnable() {

                @Override
                public void run() {
                    synchron(requester, subscribe);
                }

            });
        }
    }

}
