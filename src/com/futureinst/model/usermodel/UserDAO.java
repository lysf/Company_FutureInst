package com.futureinst.model.usermodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDAO implements Serializable{
	private Long id;
	private String uuid;
	private String name;
	private int gender;//0表示未知、1表示男、2表示女
	private String headImage;
	private String description;
	private String cid;
	private String ctype;
	private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "UserDAO [id=" + id + ", uuid=" + uuid + ", name=" + name
				+ ", gender=" + gender + ", headImage=" + headImage
				+ ", description=" + description + ", cid=" + cid + ", ctype="
				+ ctype + ", status=" + status + "]";
	}
}
