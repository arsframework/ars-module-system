package ars.module.system.repository;

import ars.module.system.model.Operated;
import ars.database.repository.Repository;

/**
 * 操作日志数据操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public interface OperatedRepository<T extends Operated> extends Repository<T> {

}
