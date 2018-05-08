package ars.module.system.repository;

import ars.module.system.model.Message;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 请求订阅消息数据持久抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractMessageRepository<T extends Message> extends HibernateSimpleRepository<T>
    implements MessageRepository<T> {

}
