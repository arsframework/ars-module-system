package ars.module.system.repository;

import ars.module.system.model.Subscribe;
import ars.module.system.repository.SubscribeRepository;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 请求订阅数据持久抽象实现
 * 
 * @author yongqiangwu
 *
 * @param <T>
 *            数据模型
 */
public abstract class AbstractSubscribeRepository<T extends Subscribe> extends HibernateSimpleRepository<T>
		implements SubscribeRepository<T> {

}
