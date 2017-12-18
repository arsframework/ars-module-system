package ars.module.system.service;

import ars.module.system.model.Message;
import ars.module.system.service.MessageService;
import ars.database.service.StandardGeneralService;

/**
 * 订阅消息业务操作抽象实现
 * 
 * @author yongqiangwu
 *
 * @param <T>
 *            数据模型
 */
public abstract class AbstractMessageService<T extends Message> extends StandardGeneralService<T>
		implements MessageService<T> {

}
