package com.futureinst.model.basemodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RegistsKeywordDAO implements Serializable{
	private String keyword;
	private int backgroundColor;
	private int textColor;
	private boolean isCheck;
	public RegistsKeywordDAO() {
		super();
	}
	public RegistsKeywordDAO(String keyword, int backgroundColor,
			int textColor, boolean isCheck) {
		super();
		this.keyword = keyword;
		this.backgroundColor = backgroundColor;
		this.textColor = textColor;
		this.isCheck = isCheck;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public int getTextColor() {
		return textColor;
	}
	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	@Override
	public String toString() {
		return "RegistsKeywordDAO [keyword=" + keyword + ", backgroundColor="
				+ backgroundColor + ", textColor=" + textColor + ", isCheck="
				+ isCheck + "]";
	}
}
