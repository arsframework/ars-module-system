package ars.module.system.repository;

import ars.module.system.model.Menu;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 菜单数据持久抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractMenuRepository<T extends Menu> extends HibernateSimpleRepository<T>
    implements MenuRepository<T> {

}
