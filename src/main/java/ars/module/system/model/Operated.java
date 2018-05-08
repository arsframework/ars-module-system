package ars.module.system.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户操作日志数据模型
 *
 * @author wuyongqiang
 */
public class Operated implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id; // 主键
    private String user; // 用户标识
    private String parameter; // 请求参数
    private String message; // 结果信息
    private String host; // 客户端主机地址
    private String uri; // 请求资源地址
    private Long spend; // 请求耗时
    private Boolean success; // 请求是否成功
    private Date dateJoined = new Date(); // 请求时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Long getSpend() {
        return spend;
    }

    public void setSpend(Long spend) {
        this.spend = spend;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    @Override
    public String toString() {
        return this.user == null || this.uri == null ? super.toString()
            : new StringBuilder(this.user).append(':').append(this.uri).toString();
    }

}
