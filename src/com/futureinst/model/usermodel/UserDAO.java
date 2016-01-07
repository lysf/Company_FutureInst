package com.futureinst.model.usermodel;

import java.io.Serializable;
import java.util.List;

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
	private String interest;
	private String birthday;
	private int peertoNum;
	private int topeerNum;
	private int commentNum;
	private PermitDAO permitMap;//权限
    private List<String> onOffSet;
	private String status;
	private String lastOrderTime;
	public String getLastOrderTime() {
		return lastOrderTime;
	}
	public void setLastOrderTime(String lastOrderTime) {
		this.lastOrderTime = lastOrderTime;
	}
	public PermitDAO getPermitMap() {
		return permitMap;
	}
	public void setPermitMap(PermitDAO permitMap) {
		this.permitMap = permitMap;
	}
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
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public int getPeertoNum() {
		return peertoNum;
	}
	public void setPeertoNum(int peertoNum) {
		this.peertoNum = peertoNum;
	}
	public int getTopeerNum() {
		return topeerNum;
	}
	public void setTopeerNum(int topeerNum) {
		this.topeerNum = topeerNum;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

    public List<String> getOnOffSet() {
        return onOffSet;
    }

    public void setOnOffSet(List<String> onOffSet) {
        this.onOffSet = onOffSet;
    }

    @Override
    public String toString() {
        return "UserDAO{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", headImage='" + headImage + '\'' +
                ", description='" + description + '\'' +
                ", cid='" + cid + '\'' +
                ", ctype='" + ctype + '\'' +
                ", interest='" + interest + '\'' +
                ", birthday='" + birthday + '\'' +
                ", peertoNum=" + peertoNum +
                ", topeerNum=" + topeerNum +
                ", commentNum=" + commentNum +
                ", permitMap=" + permitMap +
                ", onOffSet=" + onOffSet +
                ", status='" + status + '\'' +
                ", lastOrderTime='" + lastOrderTime + '\'' +
                '}';
    }
}
