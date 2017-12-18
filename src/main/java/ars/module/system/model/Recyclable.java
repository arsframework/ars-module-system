package ars.module.system.model;

import ars.database.model.AbstractModel;

/**
 * 可回收数据模型
 * 
 * @author yongqiangwu
 * 
 */
public class Recyclable extends AbstractModel {
	private static final long serialVersionUID = 1L;

	private String model; // 数据模型
	private String icon; // 模型图标

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return this.model == null ? super.toString() : this.model;
	}

}
