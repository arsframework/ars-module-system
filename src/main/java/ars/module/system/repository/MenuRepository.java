package ars.module.system.repository;

import ars.module.system.model.Menu;
import ars.database.repository.Repository;

/**
 * 菜单数据操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public interface MenuRepository<T extends Menu> extends Repository<T> {

}
