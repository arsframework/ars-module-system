package ars.module.system.service;

import ars.invoke.local.Api;
import ars.invoke.request.Requester;
import ars.module.system.model.Recycle;
import ars.database.service.SearchService;
import ars.database.service.DeleteService;

/**
 * 回收站业务操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
@Api("system/recycle")
public interface RecycleService<T extends Recycle> extends SearchService<T>, DeleteService<T> {
    /**
     * 清空回收站
     *
     * @param requester 请求对象
     */
    @Api("clear")
    public void clear(Requester requester);

    /**
     * 数据恢复
     *
     * @param requester 请求对象
     * @throws Exception 操作异常
     */
    @Api("restore")
    public void restore(Requester requester) throws Exception;

}
