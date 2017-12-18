package ars.module.system.service;

import ars.invoke.local.Api;
import ars.module.system.model.Modified;
import ars.database.service.SearchService;

/**
 * 数据更新记录业务操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
@Api("system/modified")
public interface ModifiedService<T extends Modified> extends SearchService<T> {

}
