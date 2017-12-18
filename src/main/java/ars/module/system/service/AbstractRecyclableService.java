package ars.module.system.service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.io.Serializable;

import ars.util.Beans;
import ars.invoke.request.Requester;
import ars.module.system.model.Recycle;
import ars.module.system.model.Recyclable;
import ars.module.system.service.RecyclableService;
import ars.database.repository.Repository;
import ars.database.repository.Repositories;
import ars.database.service.event.DeleteEvent;
import ars.database.service.event.ServiceListener;
import ars.database.service.StandardGeneralService;

/**
 * 可回收配置业务操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractRecyclableService<T extends Recyclable> extends StandardGeneralService<T>
		implements RecyclableService<T>, ServiceListener<DeleteEvent> {
	private Map<String, T> recyclables; // 可回收配置缓存

	/**
	 * 初始化可回收配置缓存
	 */
	protected void initRecyclableCache() {
		if (this.recyclables == null) {
			List<T> recyclables = this.getRepository().query().list();
			this.recyclables = new HashMap<String, T>(recyclables.size());
			for (int i = 0; i < recyclables.size(); i++) {
				T recyclable = recyclables.get(i);
				this.recyclables.put(recyclable.getModel(), recyclable);
			}
		}
	}

	/**
	 * 获取数据模型对应可回收配置
	 * 
	 * @param model
	 *            数据模型名称
	 * @return 可回收配置对象
	 */
	protected T getRecyclable(String model) {
		if (this.recyclables == null) {
			synchronized (this) {
				this.initRecyclableCache();
			}
		}
		return this.recyclables.get(model);
	}

	/**
	 * 记录删除数据
	 * 
	 * @param requester
	 *            请求对象
	 * @param recyclable
	 *            可回收配置对象实例
	 * @param entity
	 *            被删除对象实例
	 */
	protected void record(Requester requester, T recyclable, Serializable entity) {
		Repository<Recycle> repository = Repositories.getRepository(Recycle.class);
		Recycle recycle = Beans.getInstance(repository.getModel());
		recycle.setName(entity.toString());
		recycle.setModel(entity.getClass().getName());
		recycle.setEntity(entity);
		recycle.setRecyclable(recyclable);
		repository.save(recycle);
	}

	@Override
	public Serializable saveObject(Requester requester, T object) {
		Serializable id = super.saveObject(requester, object);
		synchronized (this) {
			this.initRecyclableCache();
			this.recyclables.put(object.getModel(), object);
		}
		return id;
	}

	@Override
	public void updateObject(Requester requester, T object) {
		super.updateObject(requester, object);
		synchronized (this) {
			this.initRecyclableCache();
			this.recyclables.put(object.getModel(), object);
		}
	}

	@Override
	public void deleteObject(Requester requester, T object) {
		super.deleteObject(requester, object);
		synchronized (this) {
			this.initRecyclableCache();
			this.recyclables.remove(object.getModel());
		}
	}

	@Override
	public void onServiceEvent(DeleteEvent event) {
		T recyclable = null;
		Serializable entity = (Serializable) event.getEntity();
		Class<?> model = event.getService().getModel();
		if (!Recycle.class.isAssignableFrom(model) && !Recyclable.class.isAssignableFrom(model)
				&& (recyclable = this.getRecyclable(model.getName())) != null) {
			this.record(event.getSource(), recyclable, entity);
		}
	}

}
