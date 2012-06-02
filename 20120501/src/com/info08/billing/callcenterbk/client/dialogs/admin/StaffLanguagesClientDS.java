package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class StaffLanguagesClientDS extends DataSource {

	public static StaffLanguagesClientDS getInstance() {
		return new StaffLanguagesClientDS("StaffLanguagesClientDS");
	}

	public StaffLanguagesClientDS(String id) {
		DataSourceIntegerField staff_language_id = new DataSourceIntegerField(
				"staff_language_id");
		staff_language_id.setHidden(true);
		staff_language_id.setPrimaryKey(true);
		staff_language_id.setRequired(true);

		DataSourceIntegerField staff_id = new DataSourceIntegerField("staff_id");
		staff_id.setHidden(true);

		DataSourceIntegerField language_descr_id = new DataSourceIntegerField(
				"language_descr_id");
		language_descr_id.setHidden(true);

		DataSourceIntegerField language_level_descr_id = new DataSourceIntegerField(
				"language_level_descr_id");
		language_level_descr_id.setHidden(true);

		DataSourceTextField language_descr = new DataSourceTextField(
				"language_descr", "ენა");

		DataSourceTextField language_level_descr = new DataSourceTextField(
				"language_level_descr", "ცოდნის დონე");

		DataSourceTextField certificate_descr = new DataSourceTextField(
				"certificate_descr", "სერტიფიკატი");

		setFields(staff_language_id, staff_id, language_descr_id,
				language_level_descr_id, language_descr, language_level_descr,
				certificate_descr);
		setClientOnly(true);
	}
}