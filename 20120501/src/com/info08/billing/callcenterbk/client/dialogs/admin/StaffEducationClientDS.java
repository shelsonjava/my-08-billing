package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class StaffEducationClientDS extends DataSource {

	public static StaffEducationClientDS getInstance() {
		return new StaffEducationClientDS("StaffEducationClientDS");
	}

	public StaffEducationClientDS(String id) {
		DataSourceIntegerField staff_education_id = new DataSourceIntegerField(
				"staff_education_id");
		staff_education_id.setHidden(true);
		staff_education_id.setPrimaryKey(true);
		staff_education_id.setRequired(true);

		DataSourceIntegerField staff_id = new DataSourceIntegerField("staff_id");
		staff_id.setHidden(true);

		DataSourceTextField college_name = new DataSourceTextField(
				"college_name", "დაწესებულების დასახელება");

		DataSourceTextField faculty_name = new DataSourceTextField(
				"faculty_name", "ფაკულტეტი");

		DataSourceIntegerField degree_descr_id = new DataSourceIntegerField(
				"degree_descr_id");
		degree_descr_id.setHidden(true);

		DataSourceTextField degree_descr = new DataSourceTextField(
				"degree_descr", "წოდება");

		DataSourceTextField se_years = new DataSourceTextField("se_years",
				"წლები");

		setFields(staff_education_id, staff_id, college_name, faculty_name,
				degree_descr_id, degree_descr, se_years);
		setClientOnly(true);
	}
}