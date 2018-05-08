package ars.module.system.repository;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;

import ars.util.Beans;
import ars.module.system.model.Config;
import ars.database.hibernate.HibernateSimpleRepository;

/**
 * 系统配置数据持久抽象实现
 *
 * @param <T> 数据模型
 * @author wuyongqiang
 */
public abstract class AbstractConfigRepository<T extends Config> extends HibernateSimpleRepository<T>
    implements ConfigRepository<T> {
    private Map<String, String> configure; // 系统配置缓存

    /**
     * 初始化系统配置缓存
     */
    protected void initConfigureCache() {
        if (this.configure == null) {
            List<T> configs = this.query().list();
            this.configure = new HashMap<String, String>(configs.size());
            for (int i = 0; i < configs.size(); i++) {
                Config config = configs.get(i);
                this.configure.put(config.getKey(), config.getValue());
            }
        }
    }

    @Override
    public Map<String, String> getConfigure() {
        if (this.configure == null) {
            synchronized (this) {
                this.initConfigureCache();
            }
        }
        return Collections.unmodifiableMap(this.configure);
    }

    @Override
    public synchronized String get(String key) {
        this.initConfigureCache();
        return this.configure.get(key);
    }

    @Override
    public synchronized void set(String key, String value) {
        this.initConfigureCache();
        if (!Beans.isEqual(this.configure.get(key), value)) {
            T config = this.query().eq("key", key).single();
            if (config == null) {
                config = Beans.getInstance(this.getModel());
                config.setKey(key);
                config.setValue(value);
                this.save(config);
            } else {
                config.setValue(value);
                this.update(config);
            }
            this.configure.put(key, value);
        }
    }

    @Override
    public synchronized void remove(String key) {
        this.initConfigureCache();
        if (this.configure.containsKey(key)) {
            T config = this.query().eq("key", key).single();
            if (config != null) {
                this.delete(config);
            }
            this.configure.remove(key);
        }
    }

}
