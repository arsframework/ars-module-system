package ars.module.system.service;

import java.util.Map;
import java.io.Serializable;

import ars.util.Beans;
import ars.util.Strings;
import ars.invoke.request.Requester;
import ars.module.system.model.Modified;
import ars.module.system.service.ModifiedService;
import ars.database.service.event.UpdateEvent;
import ars.database.service.event.ServiceListener;
import ars.database.service.StandardGeneralService;

/**
 * 数据更新记录业务操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractModifiedService<T extends Modified> extends StandardGeneralService<T>
		implements ModifiedService<T>, ServiceListener<UpdateEvent> {
	private String path; // 需要更新记录的数据模型路径

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public T record(Requester requester, Object entity, Map<String, Object[]> different) {
		if (!different.isEmpty()) {
			String primary = this.getRepository().getPrimary();
			T modified = Beans.getInstance(this.getModel());
			modified.setKey(Beans.getValue(entity, primary).toString());
			modified.setModel(entity.getClass().getName());
			modified.setDifferent(different);
			modified.setId((Integer) this.saveObject(requester, modified));
			return modified;
		}
		return null;
	}

	@Override
	public void onServiceEvent(UpdateEvent event) {
		Serializable entity = (Serializable) event.getEntity();
		Class<?> model = event.getService().getModel();
		if (!Modified.class.isAssignableFrom(model) && this.path != null
				&& Strings.matches(model.getName(), this.path)) {
			String primary = this.getRepository().getPrimary();
			T before = this.getRepository().get(Beans.getValue(entity, primary));
			Map<String, Object[]> different = Beans.getDifferent(before, entity, Beans.getFields(this.getModel()));
			if (!different.isEmpty()) {
				record(event.getSource(), before, different);
			}
		}
	}

}
