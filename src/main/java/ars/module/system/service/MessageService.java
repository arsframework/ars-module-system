package ars.module.system.service;

import ars.invoke.local.Api;
import ars.module.system.model.Message;
import ars.database.service.SearchService;
import ars.database.service.DeleteService;

/**
 * 订阅消息业务操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
@Api("system/message")
public interface MessageService<T extends Message> extends SearchService<T>, DeleteService<T> {

}
