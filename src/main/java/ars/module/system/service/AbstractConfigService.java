package ars.module.system.service;

import java.util.Map.Entry;

import ars.util.Strings;
import ars.invoke.request.Requester;
import ars.module.system.model.Config;
import ars.module.system.service.ConfigService;
import ars.module.system.repository.ConfigRepository;
import ars.database.service.StandardGeneralService;

/**
 * 系统配置业务操作抽象实现
 * 
 * @author yongqiangwu
 * 
 * @param <T>
 *            数据模型
 */
public abstract class AbstractConfigService<T extends Config> extends StandardGeneralService<T>
		implements ConfigService<T> {

	@Override
	public String get(Requester requester, String key) {
		return ((ConfigRepository<T>) this.getRepository()).get(key);
	}

	@Override
	public void set(Requester requester) {
		ConfigRepository<T> repository = (ConfigRepository<T>) this.getRepository();
		for (Entry<String, Object> entry : requester.getParameters().entrySet()) {
			repository.set(entry.getKey(), Strings.toString(entry.getValue()));
		}
	}

	@Override
	public void remove(Requester requester, String[] keys) {
		ConfigRepository<T> repository = (ConfigRepository<T>) this.getRepository();
		for (String key : keys) {
			repository.remove(key);
		}
	}

}
