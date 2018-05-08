package ars.module.system.repository;

import ars.module.system.model.Recyclable;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 可回收配置数据持久抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractRecyclableRepository<T extends Recyclable> extends HibernateSimpleRepository<T>
    implements RecyclableRepository<T> {

}
