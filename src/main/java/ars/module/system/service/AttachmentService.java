package ars.module.system.service;

import ars.util.Nfile;
import ars.invoke.local.Api;
import ars.invoke.local.Param;
import ars.invoke.request.Requester;
import ars.database.service.Service;
import ars.database.service.DeleteService;
import ars.database.service.SearchService;
import ars.module.system.model.Attachment;

/**
 * 附件业务操作接口
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
@Api("system/attachment")
public interface AttachmentService<T extends Attachment> extends Service<T>, SearchService<T>, DeleteService<T> {
    /**
     * 附件上传
     *
     * @param requester    请求对象
     * @param file         文件对象
     * @param previewable  是否可预览
     * @param downloadable 是否可下载
     * @return 附件对象实体
     * @throws Exception 操作异常
     */
    @Api("upload")
    public Attachment upload(Requester requester, @Param(name = "file", required = true) Nfile file,
                             @Param(name = "previewable") Boolean previewable, @Param(name = "downloadable") Boolean downloadable)
        throws Exception;

    /**
     * 附件下载
     *
     * @param requester 请求对象
     * @return 文件对象
     * @throws Exception 操作异常
     */
    @Api("download")
    public Nfile download(Requester requester) throws Exception;

    /**
     * 附件预览
     *
     * @param requester 请求对象
     * @return 文件对象
     * @throws Exception 操作异常
     */
    @Api("preview")
    public Nfile preview(Requester requester) throws Exception;

}
