package com.dataMiningAlgo;

public class Info {

	String col;
	String entropy;
	String info_gain;
	public Info(String col, String entropy, String info_gain) {
		super();
		this.col = col;
		this.entropy = entropy;
		this.info_gain = info_gain;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getEntropy() {
		return entropy;
	}
	public void setEntropy(String entropy) {
		this.entropy = entropy;
	}
	public String getInfo_gain() {
		return info_gain;
	}
	public void setInfo_gain(String info_gain) {
		this.info_gain = info_gain;
	}
	
	
}