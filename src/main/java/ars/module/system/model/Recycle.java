package ars.module.system.model;

import java.io.Serializable;

import ars.database.model.AbstractModel;
import ars.module.system.model.Recyclable;

/**
 * 数据回收站数据模型
 * 
 * @author yongqiangwu
 * 
 */
public class Recycle extends AbstractModel {
	private static final long serialVersionUID = 1L;

	private String name; // 数据名称
	private String model; // 数据模型
	private Serializable entity; // 数据对象实例
	private Recyclable recyclable; // 可回收配置

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Serializable getEntity() {
		return entity;
	}

	public void setEntity(Serializable entity) {
		this.entity = entity;
	}

	public Recyclable getRecyclable() {
		return recyclable;
	}

	public void setRecyclable(Recyclable recyclable) {
		this.recyclable = recyclable;
	}

	@Override
	public String toString() {
		return this.entity == null ? super.toString() : this.entity.toString();
	}

}
