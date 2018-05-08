package ars.module.system.service;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.Collections;

import ars.util.Nfile;
import ars.util.Beans;
import ars.util.Strings;
import ars.util.Servers;
import ars.util.Streams;
import ars.file.Operator;
import ars.file.NameGenerator;
import ars.file.DirectoryGenerator;
import ars.file.RandomNameGenerator;
import ars.file.DateDirectoryGenerator;
import ars.file.office.Converts;
import ars.file.disk.DiskOperator;
import ars.invoke.request.Requester;
import ars.module.system.model.Attachment;
import ars.database.service.StandardGeneralService;

/**
 * 附件业务操作抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractAttachmentService<T extends Attachment> extends StandardGeneralService<T>
    implements AttachmentService<T> {
    public static final Operator DEFAULT_OPERATOR = new DiskOperator(); // 默认文件处理器

    private Map<String, Operator> operators = Collections.emptyMap(); // 文件操作接口对象映射
    private NameGenerator nameGenerator = new RandomNameGenerator(); // 文件名称生成器
    private DirectoryGenerator directoryGenerator = new DateDirectoryGenerator(); // 文件目录生成器

    public Map<String, Operator> getOperators() {
        return operators;
    }

    public void setOperators(Map<String, Operator> operators) {
        this.operators = operators;
    }

    /**
     * 查找文件操作接口对象
     *
     * @param path 文件名称或路径
     * @return 文件操作接口对象
     */
    protected Operator lookupOperator(String path) {
        if (this.operators != null && !this.operators.isEmpty()) {
            path = path.toLowerCase();
            for (Entry<String, Operator> entry : this.operators.entrySet()) {
                if (Strings.matches(path, entry.getKey().toLowerCase())) {
                    return entry.getValue();
                }
            }
        }
        return DEFAULT_OPERATOR;
    }

    @Override
    public void deleteObject(Requester requester, T object) {
        super.deleteObject(requester, object);
        String path = object.getPath();
        try {
            this.lookupOperator(path).delete(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Attachment upload(Requester requester, Nfile file, Boolean previewable, Boolean downloadable)
        throws Exception {
        String name = file.getName();
        StringBuilder buffer = new StringBuilder();
        if (this.directoryGenerator != null) {
            buffer.append(this.directoryGenerator.generate(name));
        }
        if (this.nameGenerator != null) {
            buffer.append('/').append(this.nameGenerator.generate(name));
        }
        String path = buffer.toString();
        this.lookupOperator(file.getName()).write(file, path);
        T attachment = Beans.getInstance(this.getModel());
        attachment.setName(file.getName());
        attachment.setPath(path);
        attachment.setSize(file.getSize());
        attachment.setCreator(requester.getUser());
        attachment.setPreviewable(previewable == null ? true : previewable);
        attachment.setDownloadable(downloadable == null ? true : downloadable);
        Integer id = (Integer) this.saveObject(requester, attachment);
        attachment.setId(id);
        return attachment;
    }

    @Override
    public Nfile download(final Requester requester) throws Exception {
        final T attachment = this.object(requester);
        if (attachment == null) {
            return null;
        } else if (attachment.getDownloadable() != Boolean.TRUE) {
            throw new IllegalStateException("Attachment is not downloadable:" + attachment);
        }
        final String path = attachment.getPath();
        Nfile file = this.lookupOperator(path).read(path);
        if (file != null) {
            Servers.execute(new Runnable() {

                @Override
                public void run() {
                    synchronized (path.intern()) {
                        T entity = getRepository().get(attachment.getId());
                        if (entity != null) {
                            entity.setDownloads(entity.getDownloads() + 1);
                            updateObject(requester, entity);
                        }
                    }
                }

            });
        }
        return file;
    }

    @Override
    public Nfile preview(final Requester requester) throws Exception {
        final T attachment = this.object(requester);
        if (attachment == null) {
            return null;
        } else if (attachment.getPreviewable() != Boolean.TRUE) {
            throw new IllegalStateException("Attachment is not previewable:" + attachment);
        }
        String path = attachment.getPath();
        Operator operator = this.lookupOperator(path);
        if (!operator.exists(path)) {
            return null;
        }
        if (path.toLowerCase().endsWith(".swf")) {
            return operator.read(path);
        }
        String swf = path + ".temp.swf";
        if (!operator.exists(swf)) {
            synchronized (swf.intern()) {
                if (!operator.exists(swf)) {
                    Nfile input = operator.read(path);
                    File source = new File(Strings.TEMP_PATH, UUID.randomUUID().toString() + input.getName());
                    File target = new File(Strings.TEMP_PATH, UUID.randomUUID().toString() + input.getName());
                    try {
                        Streams.write(input, source);
                        Converts.file2swf(source, target);
                        operator.write(target, swf);
                    } finally {
                        source.delete();
                        target.delete();
                    }
                }
            }
        }
        return operator.read(swf);
    }

}
