package ars.module.system.service;

import java.util.Map;

import ars.util.Beans;
import ars.util.Strings;
import ars.server.Servers;
import ars.invoke.request.Requester;
import ars.invoke.event.InvokeListener;
import ars.invoke.event.InvokeCompleteEvent;
import ars.module.system.model.Operated;
import ars.module.system.service.OperatedService;
import ars.database.service.StandardGeneralService;

/**
 * 系统日志业务操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractOperatedService<T extends Operated> extends StandardGeneralService<T>
		implements OperatedService<T>, InvokeListener<InvokeCompleteEvent> {
	private String pattern; // 资源地址匹配模式

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * 记录操作日志
	 * 
	 * @param requester
	 *            请求对象
	 * @param value
	 *            请求结果
	 */
	protected void record(Requester requester, Object value) {
		boolean failed = value instanceof Throwable;
		Map<String, Object> parameters = requester.getParameters();
		String parameter = parameters.isEmpty() ? null : Strings.format(parameters);
		String message = failed ? Beans.getThrowableCause(((Throwable) value)).getMessage() : Strings.toString(value);

		T operated = Beans.getInstance(this.getModel());
		operated.setUser(requester.getUser());
		operated.setParameter(parameter);
		operated.setMessage(message);
		operated.setHost(requester.getHost());
		operated.setUri(requester.getUri());
		operated.setSuccess(!failed);
		operated.setSpend(System.currentTimeMillis() - requester.getCreated().getTime());
		this.saveObject(requester, operated);
	}

	@Override
	public void onInvokeEvent(final InvokeCompleteEvent event) {
		if (this.pattern == null || Strings.matches(event.getSource().getUri(), this.pattern)) {
			Servers.execute(new Runnable() {

				@Override
				public void run() {
					record(event.getSource(), event.getValue());
				}

			});
		}
	}

}
