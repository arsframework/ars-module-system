package ars.module.system.model;

import java.util.Date;
import java.io.Serializable;

import ars.invoke.request.Requester;
import ars.module.system.model.Subscribe;

/**
 * 请求订阅消息数据模型
 * 
 * @author yongqiangwu
 *
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 主键
	private Subscribe subscribe; // 所属订阅
	private Requester requester; // 订阅对象
	private Integer resend = 0; // 重发次数
	private Date dateJoined = new Date(); // 创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Subscribe getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Subscribe subscribe) {
		this.subscribe = subscribe;
	}

	public Requester getRequester() {
		return requester;
	}

	public void setRequester(Requester requester) {
		this.requester = requester;
	}

	public Integer getResend() {
		return resend;
	}

	public void setResend(Integer resend) {
		this.resend = resend;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	@Override
	public String toString() {
		return this.subscribe == null || this.requester == null ? super.toString()
				: this.subscribe.toString() + this.requester.getParameters().toString();
	}

}
