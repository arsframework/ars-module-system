package ars.module.system.repository;

import ars.module.system.model.Subscribe;
import ars.database.repository.Repository;

/**
 * 请求订阅数据操作接口
 * 
 * @author yongqiangwu
 *
 * @param <T>
 *            数据模型
 */
public interface SubscribeRepository<T extends Subscribe> extends Repository<T> {

}
