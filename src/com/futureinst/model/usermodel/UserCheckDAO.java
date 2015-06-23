package com.futureinst.model.usermodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserCheckDAO implements Serializable{
	private Long id;
	private Long userId;
	private String title;
	private float balanceChange;
	private float balanceCurr;
	private Long ctime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getBalanceChange() {
		return balanceChange;
	}
	public void setBalanceChange(float balanceChange) {
		this.balanceChange = balanceChange;
	}
	public float getBalanceCurr() {
		return balanceCurr;
	}
	public void setBalanceCurr(float balanceCurr) {
		this.balanceCurr = balanceCurr;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "UserCheckDAO [id=" + id + ", userId=" + userId + ", title="
				+ title + ", balanceChange=" + balanceChange + ", balanceCurr="
				+ balanceCurr + ", ctime=" + ctime + "]";
	}
}
