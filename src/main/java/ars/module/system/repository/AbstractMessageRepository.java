package ars.module.system.repository;

import ars.module.system.model.Message;
import ars.module.system.repository.MessageRepository;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 请求订阅消息数据持久抽象实现
 * 
 * @author yongqiangwu
 *
 * @param <T>
 *            数据模型
 */
public abstract class AbstractMessageRepository<T extends Message> extends HibernateSimpleRepository<T>
		implements MessageRepository<T> {

}
