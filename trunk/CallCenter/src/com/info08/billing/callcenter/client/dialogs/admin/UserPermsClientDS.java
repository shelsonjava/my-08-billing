package com.info08.billing.callcenter.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class UserPermsClientDS extends DataSource {

	public static UserPermsClientDS getInstance() {
		return new UserPermsClientDS("UserPermsClientDS");
	}

	public UserPermsClientDS(String id) {
		//setID(id);
		DataSourceIntegerField access_id = new DataSourceIntegerField(
				"access_id");
		access_id.setHidden(true);
		access_id.setPrimaryKey(true);
		access_id.setRequired(true);

		DataSourceIntegerField is_main_perm = new DataSourceIntegerField(
				"is_main_perm");
		is_main_perm.setHidden(true);

		DataSourceTextField access_name = new DataSourceTextField(
				"access_name", "მომხმარებლის უფლება");

		setFields(access_id, is_main_perm, access_name);
		setClientOnly(true);
	}
}