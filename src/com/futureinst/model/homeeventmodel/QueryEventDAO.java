package com.futureinst.model.homeeventmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QueryEventDAO implements Serializable{
	private Long id;
	private int groupId;
	private int type;
	private String title;
	private String description;
	private String rule;
	private String imgsrc;
	private String videosrc;
	private int tag;
	private String tagstr;
	private Long tradeTime;
	private Long tradeNum;
	private Long clearTime;
	private float currPrice;
	private float priceChange;
	private int clearPrice;
	private int involve;
	private int sug;
	private String accord;
	private String lead;
	private Long ctime;
	private String statusStr;
	private int allComNum;
	private int buyComNum;
	private int articleNum;

	private int pk0Involve; //pk模式看好人数
	private float pk0Volume; //pk模式看好总未币量
	private int pk1Involve; //pk模式不看好总人数
	private float pk1Volume; //pk模式不看好总未币量

	public int getPk0Involve() {
		return pk0Involve;
	}

	public void setPk0Involve(int pk0Involve) {
		this.pk0Involve = pk0Involve;
	}

	public float getPk0Volume() {
		return pk0Volume;
	}

	public void setPk0Volume(float pk0Volume) {
		this.pk0Volume = pk0Volume;
	}

	public int getPk1Involve() {
		return pk1Involve;
	}

	public void setPk1Involve(int pk1Involve) {
		this.pk1Involve = pk1Involve;
	}

	public float getPk1Volume() {
		return pk1Volume;
	}

	public void setPk1Volume(float pk1Volume) {
		this.pk1Volume = pk1Volume;
	}

	public int getAllComNum() {
		return allComNum;
	}

	public void setAllComNum(int allComNum) {
		this.allComNum = allComNum;
	}

	public int getBuyComNum() {
		return buyComNum;
	}

	public void setBuyComNum(int buyComNum) {
		this.buyComNum = buyComNum;
	}

	public int getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(int articleNum) {
		this.articleNum = articleNum;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public String getAccord() {
		return accord;
	}
	public void setAccord(String accord) {
		this.accord = accord;
	}
	public int getSug() {
		return sug;
	}
	public void setSug(int sug) {
		this.sug = sug;
	}
	public String getLead() {
		return lead;
	}
	public void setLead(String lead) {
		this.lead = lead;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Long tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getVideosrc() {
		return videosrc;
	}
	public void setVideosrc(String videosrc) {
		this.videosrc = videosrc;
	}
	public String getTagstr() {
		return tagstr;
	}
	public void setTagstr(String tagstr) {
		this.tagstr = tagstr;
	}
	
	public Long getTradeNum() {
		return tradeNum;
	}
	public void setTradeNum(Long tradeNum) {
		this.tradeNum = tradeNum;
	}
	
	public Long getClearTime() {
		return clearTime;
	}
	public void setClearTime(Long clearTime) {
		this.clearTime = clearTime;
	}
	public float getCurrPrice() {
		return currPrice;
	}
	public void setCurrPrice(float currPrice) {
		this.currPrice = currPrice;
	}
	public float getPriceChange() {
		return priceChange;
	}
	public void setPriceChange(float priceChange) {
		this.priceChange = priceChange;
	}
	public int getClearPrice() {
		return clearPrice;
	}
	public void setClearPrice(int clearPrice) {
		this.clearPrice = clearPrice;
	}
	public int getInvolve() {
		return involve;
	}
	public void setInvolve(int involve) {
		this.involve = involve;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	@Override
	public String toString() {
		return "QueryEventDAO [id=" + id + ", groupId=" + groupId + ", title=" + title + ", description=" + description
				+ ", rule=" + rule + ", imgsrc=" + imgsrc + ", videosrc=" + videosrc + ", tag=" + tag + ", tagstr="
				+ tagstr + ", tradeTime=" + tradeTime + ", tradeNum=" + tradeNum + ", clearTime=" + clearTime
				+ ", currPrice=" + currPrice + ", priceChange=" + priceChange + ", clearPrice=" + clearPrice
				+ ", involve=" + involve + ", sug=" + sug + ", accord=" + accord + ", lead=" + lead + ", ctime=" + ctime
				+ ", statusStr=" + statusStr + "]";
	}
	
}
