package ars.module.system.repository;

import ars.module.system.model.Attachment;
import ars.module.system.repository.AttachmentRepository;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 附件数据持久抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractAttachmentRepository<T extends Attachment> extends HibernateSimpleRepository<T>
		implements AttachmentRepository<T> {

}
