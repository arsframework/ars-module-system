package ars.module.system.repository;

import ars.module.system.model.Message;
import ars.database.repository.Repository;

/**
 * 请求订阅消息数据操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public interface MessageRepository<T extends Message> extends Repository<T> {

}
