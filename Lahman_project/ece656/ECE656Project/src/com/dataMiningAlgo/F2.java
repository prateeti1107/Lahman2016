package com.dataMiningAlgo;

public class F2 {

	String ITEM_1;
	String ITEM_2;
	float support;
	float confidende;
	float cnt;
	
	public F2(String ITEM_1, String ITEM_2, float support, float confidence, float cnt) {
		// TODO Auto-generated constructor stub
		this.ITEM_1 = ITEM_1;
		this.ITEM_2 = ITEM_2;
		this.support = support;
		this.confidende = confidence;
		this.cnt = cnt;
	}
	public String getITEM_1() {
		return ITEM_1;
	}
	public void setITEM_1(String iTEM_1) {
		ITEM_1 = iTEM_1;
	}
	public String getITEM_2() {
		return ITEM_2;
	}
	public void setITEM_2(String iTEM_2) {
		ITEM_2 = iTEM_2;
	}
	public float getSupport() {
		return support;
	}
	public void setSupport(float support) {
		this.support = support;
	}
	public float getConfidende() {
		return confidende;
	}
	public void setConfidende(float confidende) {
		this.confidende = confidende;
	}
	public float getCnt() {
		return cnt;
	}
	public void setCnt(float cnt) {
		this.cnt = cnt;
	}
	
}