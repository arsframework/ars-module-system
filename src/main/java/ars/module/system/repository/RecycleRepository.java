package ars.module.system.repository;

import ars.module.system.model.Recycle;
import ars.database.repository.Repository;

/**
 * 回收站数据操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public interface RecycleRepository<T extends Recycle> extends Repository<T> {

}
