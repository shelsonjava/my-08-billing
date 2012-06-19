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

	public void clearValues() {
		clearValue();
		paramClass.setValueRecords(null);
		setPrompt(null);
	}

	public void setDisplayValue() {
		if (paramClass == null) {
			setPrompt(null);
			return;
		}
		Record records[] = paramClass.getValueRecords();
		setValue("");
		if (records == null) {
			setPrompt(null);
			return;
		}
		String displayFields[] = paramClass.getDisplayFields();
		String value = "";

		String valuePrompt = "<table cellpadding=\"0\" cellspacing=\"0\" style=\"width: 350px; border: 1px solid #ABABAB;\"><tr>";
		for (String displayField : paramClass.getDisplayFieldTitles()) {
			valuePrompt += "<td style=\"border-top: 1px solid #F0F0F0; font-weight: bold; height: 22px; padding-left: 4px; padding-right: 4px; background-image: url('images/hdr-bg-image.png');\">"
					+ displayField + "</td>";
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
				String borderColor = "#ABABAB";
				String bgColor = "";
				if (i != 0) {
					borderColor = "#F0F0F0";
				}
				if (i % 2 != 0) {
					bgColor = "background-color: #FAFAFA;";
				}

				valuePrompt += "<td style=\"border-top: 1px solid "
						+ borderColor
						+ "; height: 22px; padding-left: 4px; padding-right: 4px; "
						+ bgColor + "\">" + itemValue + "</td>";
			}

			valuePrompt += "</tr>";
			i++;

		}

		valuePrompt += "</table>";
		setPrompt(valuePrompt);
		setValue(value);
	}
}
