package ars.module.system.service;

import java.util.Map;
import java.util.List;

import ars.invoke.request.Requester;
import ars.invoke.request.RequestHandleException;
import ars.module.system.model.Recycle;
import ars.module.system.service.RecycleService;
import ars.database.repository.Query;
import ars.database.repository.Repository;
import ars.database.repository.Repositories;
import ars.database.service.StandardGeneralService;

/**
 * 数据回收业务操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractRecycleService<T extends Recycle> extends StandardGeneralService<T>
		implements RecycleService<T> {

	@Override
	public Query<T> getQuery(Requester requester) {
		return super.getQuery(requester).eq("creator", requester.getUser());
	}

	@Override
	public void deleteObject(Requester requester, T object) {
		if (!requester.getUser().equals(object.getCreator())) {
			throw new RequestHandleException("Unauthorized operation");
		}
		super.deleteObject(requester, object);
	}

	@Override
	public void clear(Requester requester, Map<String, Object> parameters) {
		List<T> histories = this.getQuery(requester).custom(parameters).list();
		for (int i = 0; i < histories.size(); i++) {
			this.deleteObject(requester, histories.get(i));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void restore(Requester requester, Map<String, Object> parameters) throws Exception {
		List<T> recycles = this.getQuery(requester).custom(parameters).list();
		for (int i = 0; i < recycles.size(); i++) {
			T recycle = recycles.get(i);
			Object entity = recycle.getEntity();
			Class<?> model = Class.forName(recycle.getModel());
			Repository repository = Repositories.getRepository(model);
			repository.save(entity);
			this.deleteObject(requester, recycle);
		}
	}

}
