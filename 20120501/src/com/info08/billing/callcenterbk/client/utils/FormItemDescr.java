package com.info08.billing.callcenterbk.client.utils;

import java.util.Map;

import com.smartgwt.client.widgets.form.fields.FormItem;

public class FormItemDescr {
	public FormItem formItem;
	public String parentName;
	public Map<?, ?> aditionalCriteria;

	public FormItemDescr(FormItem formItem) {
		this(formItem, null, null);
	}

	public FormItemDescr(FormItem formItem, String parentName) {
		this(formItem, parentName, null);
	}

	public FormItemDescr(FormItem formItem, String parentName,
			Map<?, ?> aditionalCriteria) {
		this.formItem = formItem;
		this.parentName = parentName;
		this.aditionalCriteria = aditionalCriteria;
	}
}
