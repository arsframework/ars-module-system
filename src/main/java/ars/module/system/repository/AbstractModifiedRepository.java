package ars.module.system.repository;

import ars.module.system.model.Modified;
import ars.module.system.repository.ModifiedRepository;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 数据更新记录数据持久抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractModifiedRepository<T extends Modified> extends HibernateSimpleRepository<T>
		implements ModifiedRepository<T> {

}
