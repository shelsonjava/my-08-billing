package com.info08.billing.callcenterbk.client.common.components;

import java.util.Map;

import com.smartgwt.client.data.Record;

public class MyComboboxItemMultClass {
	private String dsName;
	private String dsOperationId;
	private String idField;
	private String displayFields[];
	private String displayFieldTitles[];
	private Map<?, ?> criteria;

	private String windowTitle;
	private int windowWidth;
	private int windowHeight;
	private String dublicateMessage;

	private Record valueRecords[];

	public MyComboboxItemMultClass(String dsName, String dsOperationId,
			String idField, String displayFields[],
			String displayFieldTitles[], Map<?, ?> criteria,
			String windowTitle, int windowWidth, int windowHeight,
			String dublicateMessage) {
		this.dsName = dsName;
		this.dsOperationId = dsOperationId;
		this.idField = idField;
		this.displayFields = displayFields;
		this.displayFieldTitles = displayFieldTitles;
		this.criteria = criteria;
		this.windowTitle = windowTitle;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.setDublicateMessage(dublicateMessage);
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getDsOperationId() {
		return dsOperationId;
	}

	public void setDsOperationId(String dsOperationId) {
		this.dsOperationId = dsOperationId;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String[] getDisplayFields() {
		return displayFields;
	}

	public void setDisplayFields(String[] displayFields) {
		this.displayFields = displayFields;
	}

	public Map<?, ?> getCriteria() {
		return criteria;
	}

	public void setCriteria(Map<?, ?> criteria) {
		this.criteria = criteria;
	}

	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public String[] getDisplayFieldTitles() {
		return displayFieldTitles;
	}

	public void setDisplayFieldTitles(String[] displayFieldTitles) {
		this.displayFieldTitles = displayFieldTitles;
	}

	public Record[] getValueRecords() {
		return valueRecords;
	}

	public void setValueRecords(Record[] valueRecords) {
		this.valueRecords = valueRecords;
	}

	public String getDublicateMessage() {
		return dublicateMessage;
	}

	public void setDublicateMessage(String dublicateMessage) {
		this.dublicateMessage = dublicateMessage;
	}
}
