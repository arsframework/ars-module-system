package ars.module.system.service;

import ars.invoke.local.Api;
import ars.module.system.model.Subscribe;
import ars.database.service.BasicService;

/**
 * 请求订阅业务操作接口
 * 
 * @author yongqiangwu
 *
 * @param <T>
 *            数据模型
 */
@Api("system/subscribe")
public interface SubscribeService<T extends Subscribe> extends BasicService<T> {

}
