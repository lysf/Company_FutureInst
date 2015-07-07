package com.futureinst.model.homeeventmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QueryEventDAO implements Serializable{
	private Long id;
	private String title;
	private String description;
	private String rule;
	private String imgsrc;
	private String videosrc;
	private String tagstr;
	private Long tradeTime;
	private Long tradeNum;
	private Long clearTime;
	private float currPrice;
	private float priceChange;
	private int clearPrice;
	private Long involve;
	private int sug;
	private String lead;
	private Long ctime;
	private String statusStr;
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
	public Long getInvolve() {
		return involve;
	}
	public void setInvolve(Long involve) {
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
		return "QueryEventDAO [id=" + id + ", title=" + title
				+ ", description=" + description + ", imgsrc=" + imgsrc
				+ ", videosrc=" + videosrc + ", tagstr=" + tagstr
				+ ", tradeTime=" + tradeTime + ", tradeNum=" + tradeNum
				+ ", clearTime=" + clearTime + ", currPrice=" + currPrice
				+ ", priceChange=" + priceChange + ", clearPrice=" + clearPrice
				+ ", involve=" + involve + ", ctime=" + ctime + ", statusStr="
				+ statusStr + "]";
	}
}
