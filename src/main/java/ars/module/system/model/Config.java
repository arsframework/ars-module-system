package ars.module.system.model;

import ars.database.model.AbstractModel;

/**
 * 系统配置数据模型
 *
 * @author wuyongqiang
 */
public class Config extends AbstractModel {
    private static final long serialVersionUID = 1L;

    private String key;// 系统设置建
    private String value;// 系统设置值

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.key == null ? super.toString()
            : new StringBuilder(this.key).append('=').append(this.value).toString();
    }

}
