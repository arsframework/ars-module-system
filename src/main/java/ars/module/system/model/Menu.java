package ars.module.system.model;

import ars.database.model.AbstractTreeModel;

/**
 * 菜单数据模型
 * 
 * @author yongqiangwu
 * 
 */
public class Menu extends AbstractTreeModel<Menu> {
	private static final long serialVersionUID = 1L;

	private String name; // 菜单名称
	private String uri; // 菜单地址
	private String icon; // 菜单图标

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return this.name == null ? super.toString() : this.name;
	}

}
