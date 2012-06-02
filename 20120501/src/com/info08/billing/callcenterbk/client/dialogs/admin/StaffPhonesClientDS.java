package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class StaffPhonesClientDS extends DataSource {

	public static StaffPhonesClientDS getInstance() {
		return new StaffPhonesClientDS("StaffPhonesClientDS");
	}

	public StaffPhonesClientDS(String id) {
		DataSourceIntegerField staff_phone_id = new DataSourceIntegerField(
				"staff_phone_id");
		staff_phone_id.setHidden(true);
		staff_phone_id.setPrimaryKey(true);
		staff_phone_id.setRequired(true);

		DataSourceIntegerField staff_id = new DataSourceIntegerField("staff_id");
		staff_id.setHidden(true);

		DataSourceIntegerField staff_phone_type_id = new DataSourceIntegerField(
				"staff_phone_type_id");
		staff_phone_type_id.setHidden(true);

		DataSourceTextField staff_phone_type = new DataSourceTextField(
				"staff_phone_type", "ტელეფონის ტიპი");

		DataSourceTextField certificate_descr = new DataSourceTextField(
				"staff_phone", "ნომერი");

		setFields(staff_phone_id, staff_id, staff_phone_type_id,
				staff_phone_type, certificate_descr);
		setClientOnly(true);
	}
}