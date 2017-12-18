package ars.module.system.repository;

import ars.module.system.model.Config;

import java.util.Map;

import ars.database.repository.Repository;

/**
 * 系统配置数据操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public interface ConfigRepository<T extends Config> extends Repository<T> {
	/**
	 * 获取系统配置
	 * 
	 * @return 系统配置键/值映射
	 */
	public Map<String, String> getConfigure();

	/**
	 * 获取系统配置
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public String get(String key);

	/**
	 * 设置系统配置
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void set(String key, String value);

	/**
	 * 删除系统配置
	 * 
	 * @param key
	 *            键
	 */
	public void remove(String key);

}
