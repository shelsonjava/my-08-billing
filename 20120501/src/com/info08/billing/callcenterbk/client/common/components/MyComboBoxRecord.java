package com.info08.billing.callcenterbk.client.common.components;

public class MyComboBoxRecord {
	
	private String fieldName;
	private String fieldTitle;
	private boolean displayField;

	public MyComboBoxRecord(String fieldName, String fieldTitle,
			boolean displayField) {
		this.displayField = displayField;
		this.fieldName = fieldName;
		this.fieldTitle = fieldTitle;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTitle() {
		return fieldTitle;
	}

	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}

	public boolean isDisplayField() {
		return displayField;
	}

	public void setDisplayField(boolean displayField) {
		this.displayField = displayField;
	}
}
