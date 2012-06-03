package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class StaffWorksClientDS extends DataSource {

	public static StaffWorksClientDS getInstance() {
		return new StaffWorksClientDS("StaffWorksClientDS");
	}

	public StaffWorksClientDS(String id) {
		DataSourceIntegerField staff_work_id = new DataSourceIntegerField(
				"staff_work_id");
		staff_work_id.setHidden(true);
		staff_work_id.setPrimaryKey(true);
		staff_work_id.setRequired(true);

		DataSourceIntegerField staff_id = new DataSourceIntegerField("staff_id");
		staff_id.setHidden(true);

		DataSourceTextField work_place = new DataSourceTextField("work_place",
				"კომპანია");

		DataSourceTextField work_position = new DataSourceTextField(
				"work_position", "პოზიცია");

		DataSourceDateField work_start_date = new DataSourceDateField(
				"work_start_date");
		work_start_date.setHidden(true);

		DataSourceDateField work_end_date = new DataSourceDateField(
				"work_end_date");
		work_end_date.setHidden(true);

		DataSourceTextField work_start_date_descr = new DataSourceTextField(
				"work_start_date_descr", "დაწყება");

		DataSourceTextField work_end_date_descr = new DataSourceTextField(
				"work_end_date_descr", "დამთავრება");

		setFields(staff_work_id, staff_id, work_place, work_position,
				work_start_date, work_end_date, work_start_date_descr,
				work_end_date_descr);
		setClientOnly(true);
	}
}