package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class StaffRelativeClientDS extends DataSource {

	public static StaffRelativeClientDS getInstance() {
		return new StaffRelativeClientDS("StaffRelativeClientDS");
	}

	public StaffRelativeClientDS(String id) {
		DataSourceIntegerField staff_relative_id = new DataSourceIntegerField(
				"staff_relative_id");
		staff_relative_id.setHidden(true);
		staff_relative_id.setPrimaryKey(true);
		staff_relative_id.setRequired(true);

		DataSourceIntegerField staff_id = new DataSourceIntegerField("staff_id");
		staff_id.setHidden(true);

		DataSourceTextField first_name = new DataSourceTextField("first_name",
				"სახელი");

		DataSourceTextField last_name = new DataSourceTextField("last_name",
				"გვარი");

		DataSourceTextField position = new DataSourceTextField("position",
				"პოზიცია");
		setFields(staff_relative_id, staff_id, first_name, last_name,
				position);
		setClientOnly(true);
	}
}