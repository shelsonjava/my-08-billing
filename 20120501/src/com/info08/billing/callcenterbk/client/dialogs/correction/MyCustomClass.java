package com.info08.billing.callcenterbk.client.dialogs.correction;

import com.smartgwt.client.data.Record;

public class MyCustomClass {

	private Record listGridRecord;
	private Integer phone;
	private Integer iOpClose;
	private Integer iParalelUsual;
	private Integer iPhoneStatus;
	private Integer iPhoneState;
	private Integer iPhoneType;
	private boolean isAdd1;

	public MyCustomClass(Record listGridRecord, Integer phone,
			Integer iOpClose, Integer iParalelUsual, Integer iPhoneStatus,
			Integer iPhoneState, Integer iPhoneType, boolean isAdd1) {
		this.listGridRecord = listGridRecord;
		this.phone = phone;
		this.iOpClose = iOpClose;
		this.iParalelUsual = iParalelUsual;
		this.iPhoneStatus = iPhoneStatus;
		this.iPhoneState = iPhoneState;
		this.iPhoneType = iPhoneType;
		this.isAdd1 = isAdd1;
	}

	public Record getListGridRecord() {
		return listGridRecord;
	}

	public void setListGridRecord(Record listGridRecord) {
		this.listGridRecord = listGridRecord;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public Integer getiOpClose() {
		return iOpClose;
	}

	public void setiOpClose(Integer iOpClose) {
		this.iOpClose = iOpClose;
	}

	public Integer getiParalelUsual() {
		return iParalelUsual;
	}

	public void setiParalelUsual(Integer iParalelUsual) {
		this.iParalelUsual = iParalelUsual;
	}

	public Integer getiPhoneStatus() {
		return iPhoneStatus;
	}

	public void setiPhoneStatus(Integer iPhoneStatus) {
		this.iPhoneStatus = iPhoneStatus;
	}

	public Integer getiPhoneState() {
		return iPhoneState;
	}

	public void setiPhoneState(Integer iPhoneState) {
		this.iPhoneState = iPhoneState;
	}

	public Integer getiPhoneType() {
		return iPhoneType;
	}

	public void setiPhoneType(Integer iPhoneType) {
		this.iPhoneType = iPhoneType;
	}

	public boolean isAdd1() {
		return isAdd1;
	}

	public void setAdd1(boolean isAdd1) {
		this.isAdd1 = isAdd1;
	}

}
