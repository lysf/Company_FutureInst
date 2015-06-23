package com.futureinst.model.usermodel;

import java.util.List;

import com.futureinst.model.basemodel.BaseModel;

@SuppressWarnings("serial")
public class FaqInfoDAO extends BaseModel{
	private int size;
	private List<FaqDAO> faqs;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<FaqDAO> getFaqs() {
		return faqs;
	}
	public void setFaqs(List<FaqDAO> faqs) {
		this.faqs = faqs;
	}
	@Override
	public String toString() {
		return "FaqInfoDAO [size=" + size + ", faqs=" + faqs + "]";
	}
}
