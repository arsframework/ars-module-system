package ars.module.system.service;

import ars.invoke.local.Api;
import ars.database.service.BasicService;
import ars.module.system.model.Recyclable;

/**
 * 可回收配置业务操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
@Api("system/recyclable")
public interface RecyclableService<T extends Recyclable> extends BasicService<T> {

}
