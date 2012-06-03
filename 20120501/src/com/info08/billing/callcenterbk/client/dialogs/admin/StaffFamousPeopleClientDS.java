package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class StaffFamousPeopleClientDS extends DataSource {

	public static StaffFamousPeopleClientDS getInstance() {
		return new StaffFamousPeopleClientDS("StaffFamousPeopleClientDS");
	}

	public StaffFamousPeopleClientDS(String id) {
		DataSourceIntegerField staff_famous_people_id = new DataSourceIntegerField(
				"staff_famous_people_id");
		staff_famous_people_id.setHidden(true);
		staff_famous_people_id.setPrimaryKey(true);
		staff_famous_people_id.setRequired(true);

		DataSourceIntegerField staff_id = new DataSourceIntegerField("staff_id");
		staff_id.setHidden(true);

		DataSourceTextField first_name = new DataSourceTextField("first_name",
				"სახელი");

		DataSourceTextField last_name = new DataSourceTextField("last_name",
				"გვარი");

		DataSourceIntegerField relation_type_id = new DataSourceIntegerField(
				"relation_type_id");
		relation_type_id.setHidden(true);

		DataSourceTextField relation_type_id_descr = new DataSourceTextField(
				"relation_type_id_descr", "კავშირი");
		
		DataSourceIntegerField sphere_of_activity_id = new DataSourceIntegerField(
				"sphere_of_activity_id");
		sphere_of_activity_id.setHidden(true);

		DataSourceTextField sphere_of_activity_id_descr = new DataSourceTextField(
				"sphere_of_activity_id_descr", "მოღვაწეობის სფერო");

		
		setFields(staff_famous_people_id, staff_id, first_name, last_name,
				relation_type_id, relation_type_id_descr, sphere_of_activity_id, sphere_of_activity_id_descr);
		setClientOnly(true);
	}
}