package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class StaffRelative09ClientDS extends DataSource {

	public static StaffRelative09ClientDS getInstance() {
		return new StaffRelative09ClientDS("StaffRelative09ClientDS");
	}

	public StaffRelative09ClientDS(String id) {
		DataSourceIntegerField staff_relative_09_id = new DataSourceIntegerField(
				"staff_relative_09_id");
		staff_relative_09_id.setHidden(true);
		staff_relative_09_id.setPrimaryKey(true);
		staff_relative_09_id.setRequired(true);

		DataSourceIntegerField staff_id = new DataSourceIntegerField("staff_id");
		staff_id.setHidden(true);

		DataSourceTextField first_name = new DataSourceTextField("first_name",
				"სახელი");

		DataSourceTextField last_name = new DataSourceTextField("last_name",
				"გვარი");

		DataSourceTextField position = new DataSourceTextField("position",
				"პოზიცია");
		setFields(staff_relative_09_id, staff_id, first_name, last_name,
				position);
		setClientOnly(true);
	}
}