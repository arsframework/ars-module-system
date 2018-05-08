package ars.module.system.service;

import ars.module.system.model.Message;
import ars.database.service.StandardGeneralService;

/**
 * 订阅消息业务操作抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractMessageService<T extends Message> extends StandardGeneralService<T>
    implements MessageService<T> {

}
