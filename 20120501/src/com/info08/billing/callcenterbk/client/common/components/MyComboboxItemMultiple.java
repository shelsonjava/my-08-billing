package com.info08.billing.callcenterbk.client.common.components;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

public class MyComboboxItemMultiple extends TextItem {

	private MyComboboxItemMultClass paramClass;
	private MyComboboxItemMultDlg comboboxItemMultDlg;

	public void setParams(MyComboboxItemMultClass paramClass) {
		this.paramClass = paramClass;
	}

	public MyComboboxItemMultiple() {
		super();
		init();
	}

	public MyComboboxItemMultiple(String name) {
		super(name);
		init();
	}

	public MyComboboxItemMultiple(String name, String title) {
		super(name);
		setTitle(title);
		init();
	}

	public MyComboboxItemMultiple(String name, String title, int width) {
		super(name);
		setTitle(title);
		setWidth(width);
		init();
	}

	private void init() {
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				event.cancel();
			}
		});
		PickerIcon addOrgActivity = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						if (paramClass == null) {
							return;
						}
						comboboxItemMultDlg = new MyComboboxItemMultDlg(
								MyComboboxItemMultiple.this);
						comboboxItemMultDlg.show();
					}
				});
		this.setIcons(addOrgActivity);
	}

	public MyComboboxItemMultClass getParamClass() {
		return paramClass;
	}

	public void setDisplayValue() {
		Record records[] = paramClass.getValueRecords();
		setValue("");
		if (records == null) {
			return;
		}
		String displayFields[] = paramClass.getDisplayFields();
		String value = "";
		String valuePrompt = "<table style=\"width: 300px;background-color:yellow;\"><tr>";
		for (String displayField : paramClass.getDisplayFieldTitles()) {
			valuePrompt += "<th style=\"border: solid 1px;\">" + displayField
					+ "</th>";
		}
		valuePrompt += "</tr>";
		int i = 0;
		for (Record record : records) {

			valuePrompt += "<tr>";
			for (String displayField : displayFields) {
				String itemValue = record.getAttributeAsString(displayField);
				if (i > 0) {
					value += ",";
				}
				value += itemValue;
				valuePrompt += "<td style=\"border: solid 1px\">" + itemValue
						+ "</td>";
			}
			valuePrompt += "</tr>";
			i++;

		}

		valuePrompt += "</table>";
		System.out.println("valuePrompt = " + valuePrompt);
		setPrompt(valuePrompt);
		setValue(value);
	}
}
