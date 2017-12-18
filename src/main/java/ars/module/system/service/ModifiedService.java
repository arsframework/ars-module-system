package ars.module.system.service;

import java.util.Map;

import ars.invoke.local.Api;
import ars.invoke.request.Requester;
import ars.module.system.model.Modified;
import ars.database.service.SearchService;

/**
 * 数据更新记录业务操作接口
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
@Api("system/modified")
public interface ModifiedService<T extends Modified> extends SearchService<T> {
	/**
	 * 记录删除数据
	 * 
	 * @param requester
	 *            请求对象
	 * @param entity
	 *            对象实例
	 * @param different
	 *            差异属性值
	 * @return 数据更新记录实例
	 */
	public T record(Requester requester, Object entity, Map<String, Object[]> different);

}
