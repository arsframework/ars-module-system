package ars.module.system.model;

import java.util.Map;
import java.util.HashMap;

import ars.database.model.AbstractModel;

/**
 * 数据更新记录数据模型
 *
 * @author wuyongqiang
 */
public class Modified extends AbstractModel {
    private static final long serialVersionUID = 1L;

    private String key; // 数据标识
    private String model; // 数据模型
    private Map<String, Object[]> different = new HashMap<String, Object[]>(0); // 更新内容

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Map<String, Object[]> getDifferent() {
        return different;
    }

    public void setDifferent(Map<String, Object[]> different) {
        this.different = different;
    }

    @Override
    public String toString() {
        if (this.key == null || this.model == null) {
            return super.toString();
        }
        return new StringBuilder(this.model).append('#').append(this.key).append(this.different).toString();
    }

}
