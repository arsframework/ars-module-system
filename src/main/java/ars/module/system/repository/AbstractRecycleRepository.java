package ars.module.system.repository;

import ars.module.system.model.Recycle;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 回收站数据持久抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractRecycleRepository<T extends Recycle> extends HibernateSimpleRepository<T>
    implements RecycleRepository<T> {

}
