package ars.module.system.service;

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
	 * @return 值
	 */
	@Api("get")
	public String get(Requester requester, @Param(name = "key", required = true) String key);

	/**
	 * 设置系统配置
	 * 
	 * @param requester
	 *            请求对象
	 */
	@Api("set")
	public void set(Requester requester);

	/**
	 * 删除系统配置
	 * 
	 * @param requester
	 *            请求对象
	 * @param keys
	 *            键
	 */
	@Api("remove")
	public void remove(Requester requester, @Param(name = "key", required = true) String[] keys);

}
