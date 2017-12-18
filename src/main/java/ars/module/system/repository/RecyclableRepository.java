package ars.module.system.repository;

import ars.module.system.model.Recyclable;
import ars.database.repository.Repository;

/**
 * 可回收配置数据操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public interface RecyclableRepository<T extends Recyclable> extends Repository<T> {

}
