package ars.module.system.service;

import java.util.Map;

import ars.invoke.local.Api;
import ars.invoke.request.Requester;
import ars.module.system.model.Recycle;
import ars.database.service.SearchService;
import ars.database.service.DeleteService;

/**
 * 回收站业务操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
@Api("system/recycle")
public interface RecycleService<T extends Recycle> extends SearchService<T>, DeleteService<T> {
	/**
	 * 清空回收站
	 * 
	 * @param requester
	 *            请求对象
	 * @param parameters
	 *            请求参数
	 */
	@Api("clear")
	public void clear(Requester requester, Map<String, Object> parameters);

	/**
	 * 数据恢复
	 * 
	 * @param requester
	 *            请求对象
	 * @param parameters
	 *            请求参数
	 * @throws Exception
	 *             操作异常
	 */
	@Api("restore")
	public void restore(Requester requester, Map<String, Object> parameters) throws Exception;

}
