package ars.module.system.service;

import ars.invoke.local.Api;
import ars.module.system.model.Operated;
import ars.database.service.SearchService;

/**
 * 操作日志业务操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
@Api("system/operated")
public interface OperatedService<T extends Operated> extends SearchService<T> {

}
