package ars.module.system.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 附件数据模型
 *
 * @author wuyongqiang
 */
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id; // 主键
    private String name; // 文件名称
    private String path; // 文件路径
    private Long size; // 文件大小
    private String creator; // 文件创建者
    private Integer downloads = 0; // 下载次数
    private Boolean previewable = true; // 是否可预览
    private Boolean downloadable = true; // 是否可下载
    private Date dateJoined = new Date(); // 创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Boolean getPreviewable() {
        return previewable;
    }

    public void setPreviewable(Boolean previewable) {
        this.previewable = previewable;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public void setDownloadable(Boolean downloadable) {
        this.downloadable = downloadable;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    @Override
    public String toString() {
        return this.name == null ? super.toString() : this.name;
    }

}
