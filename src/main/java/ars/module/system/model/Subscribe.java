package ars.module.system.model;

import ars.invoke.remote.Protocol;
import ars.database.model.AbstractModel;

/**
 * 请求订阅数据模型
 * 
 * @author yongqiangwu
 *
 */
public class Subscribe extends AbstractModel {
	private static final long serialVersionUID = 1L;

	private String target; // 目标资源地址
	private Event event; // 目标资源事件
	private Protocol protocol; // 客户端协议
	private String host; // 客户端地址
	private Integer port; // 客户端端口
	private String resource; // 客户端资源地址

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		if (this.target == null || this.event == null || this.protocol == null || this.host == null) {
			return super.toString();
		}
		StringBuilder buffer = new StringBuilder(this.target).append(":").append(this.event).append("->")
				.append(this.protocol).append(":").append(this.host).append(":").append(this.port);
		return this.resource == null ? buffer.toString() : buffer.append(":").append(this.resource).toString();
	}

	/**
	 * 请求订阅事件
	 * 
	 * @author yongqiangwu
	 *
	 */
	public enum Event {
		/**
		 * 请求之前
		 */
		BEFORE,

		/**
		 * 请求成功
		 */
		AFTER,

		/**
		 * 请求失败
		 */
		ERROR,

		/**
		 * 请求完成（成功/失败）
		 */
		COMPLETE;

	}

}
