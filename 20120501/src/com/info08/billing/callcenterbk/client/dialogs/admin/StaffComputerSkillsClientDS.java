package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class StaffComputerSkillsClientDS extends DataSource {

	public static StaffComputerSkillsClientDS getInstance() {
		return new StaffComputerSkillsClientDS("StaffComputerSkillsClientDS");
	}

	public StaffComputerSkillsClientDS(String id) {
		DataSourceIntegerField staff_comp_skill_id = new DataSourceIntegerField(
				"staff_comp_skill_id");
		staff_comp_skill_id.setHidden(true);
		staff_comp_skill_id.setPrimaryKey(true);
		staff_comp_skill_id.setRequired(true);

		DataSourceIntegerField staff_id = new DataSourceIntegerField("staff_id");
		staff_id.setHidden(true);

		DataSourceTextField software = new DataSourceTextField("software",
				"პროგრამა");

		DataSourceTextField training_course = new DataSourceTextField(
				"training_course", "კურსის დასახელება");

		DataSourceTextField remark = new DataSourceTextField("remark",
				"კომენტარი");

		setFields(staff_comp_skill_id, staff_id, software, training_course,
				remark);
		setClientOnly(true);
	}
}