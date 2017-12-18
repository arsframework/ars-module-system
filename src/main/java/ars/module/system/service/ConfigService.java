package ars.module.system.service;

import java.util.Map;

import ars.invoke.local.Api;
import ars.invoke.local.Param;
import ars.invoke.request.Requester;
import ars.database.service.Service;
import ars.module.system.model.Config;

/**
 * 系统配置业务操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
@Api("system/config")
public interface ConfigService<T extends Config> extends Service<T> {
	/**
	 * 获取系统配置值
	 * 
	 * @param requester
	 *            请求对象
	 * @param key
	 *            键
	 * @param parameters
	 *            请求参数
	 * @return 值
	 */
	@Api("get")
	public String get(Requester requester, @Param(name = "key", required = true) String key,
			Map<String, Object> parameters);

	/**
	 * 设置系统配置
	 * 
	 * @param requester
	 *            请求对象
	 * @param parameters
	 *            键/值映射表
	 */
	@Api("set")
	public void set(Requester requester, Map<String, Object> parameters);

	/**
	 * 删除系统配置
	 * 
	 * @param requester
	 *            请求对象
	 * @param keys
	 *            键
	 * @param parameters
	 *            请求参数
	 */
	@Api("remove")
	public void remove(Requester requester, @Param(name = "key", required = true) String[] keys,
			Map<String, Object> parameters);

}
