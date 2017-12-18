package ars.module.system.service;

import java.util.Map;
import java.util.List;

import ars.invoke.request.Requester;
import ars.invoke.request.ParameterInvalidException;
import ars.module.system.model.Menu;
import ars.module.system.service.MenuService;
import ars.database.repository.Query;
import ars.database.repository.Repository;
import ars.database.service.StandardGeneralService;

/**
 * 菜单业务操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractMenuService<T extends Menu> extends StandardGeneralService<T> implements MenuService<T> {

	@Override
	public void initObject(Requester requester, T entity, Map<String, Object> parameters) {
		super.initObject(requester, entity, parameters);
		Menu parent = entity.getParent();
		Query<T> query = this.getRepository().query().ne("id", entity.getId()).eq("name", entity.getName());
		if (parent == null) {
			query.empty("parent");
		} else {
			query.eq("parent", parent);
		}
		if (query.count() > 0) {
			throw new ParameterInvalidException("name", "exist");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateObject(Requester requester, T object) {
		Repository<T> repository = this.getRepository();
		T old = repository.get(object.getId());
		super.updateObject(requester, object);
		if (old.getActive() != object.getActive()) {
			if (object.getActive() == Boolean.TRUE) {
				T parent = (T) object.getParent();
				while (parent != null) {
					if (parent.getActive() == Boolean.FALSE) {
						parent.setActive(true);
						repository.update(parent);
					}
					parent = (T) parent.getParent();
				}
			} else if (object.getActive() == Boolean.FALSE) {
				List<T> menus = repository.query().ne("id", object.getId()).eq("active", true)
						.start("key", object.getKey()).list();
				for (int i = 0; i < menus.size(); i++) {
					T menu = menus.get(i);
					menu.setActive(false);
					repository.update(menu);
				}
			}
		}
	}

}
