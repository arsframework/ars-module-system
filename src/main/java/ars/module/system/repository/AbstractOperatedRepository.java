package ars.module.system.repository;

import ars.module.system.model.Operated;
import ars.module.system.repository.OperatedRepository;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 操作日志数据持久抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractOperatedRepository<T extends Operated> extends HibernateSimpleRepository<T>
		implements OperatedRepository<T> {

}
