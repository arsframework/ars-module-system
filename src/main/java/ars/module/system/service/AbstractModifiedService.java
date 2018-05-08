package ars.module.system.service;

import java.util.Map;

import ars.util.Beans;
import ars.util.Strings;
import ars.invoke.request.Requester;
import ars.module.system.model.Modified;
import ars.database.repository.Repositories;
import ars.database.service.event.UpdateEvent;
import ars.database.service.event.ServiceListener;
import ars.database.service.StandardGeneralService;

/**
 * 数据更新记录业务操作抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractModifiedService<T extends Modified> extends StandardGeneralService<T>
    implements ModifiedService<T>, ServiceListener<UpdateEvent> {
    private String pattern; // 需要更新记录的数据模型路径匹配模式

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * 记录更新数据
     *
     * @param requester 请求对象
     * @param model     数据模型
     * @param before    更新前实体
     * @param after     更新后实体
     * @param different 更新数据项
     */
    protected void record(Requester requester, Class<?> model, Object before, Object after,
                          Map<String, Object[]> different) {
        T modified = Beans.getInstance(this.getModel());
        modified.setKey(Repositories.getIdentifier(after).toString());
        modified.setModel(model.getName());
        modified.setDifferent(different);
        this.saveObject(requester, modified);
    }

    @Override
    public void onServiceEvent(UpdateEvent event) {
        Class<?> model = event.getService().getModel();
        if (!Modified.class.isAssignableFrom(model)
            && (this.pattern == null || Strings.matches(model.getName(), this.pattern))) {
            Object entity = event.getEntity();
            Object original = event.getOriginal();
            Map<String, Object[]> different = Beans.getDifferent(entity, original, Beans.getFields(model));
            if (!different.isEmpty()) {
                record(event.getSource(), model, original, entity, different);
            }
        }
    }

}
