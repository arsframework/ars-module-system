package ars.module.system.repository;

import ars.module.system.model.Modified;
import ars.database.repository.Repository;

/**
 * 数据更新记录数据操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public interface ModifiedRepository<T extends Modified> extends Repository<T> {

}
