package ars.module.system.service;

import ars.invoke.local.Api;
import ars.module.system.model.Menu;
import ars.database.service.TreeService;
import ars.database.service.BasicService;

/**
 * 菜单业务操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
@Api("system/menu")
public interface MenuService<T extends Menu> extends BasicService<T>, TreeService<T> {

}
