package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class UserPermissinsClientDS extends DataSource {

	public static UserPermissinsClientDS getInstance() {
		return new UserPermissinsClientDS("UserPermissinsClientDS");
	}

	public UserPermissinsClientDS(String id) {
		// setID(id);
		DataSourceIntegerField permission_id = new DataSourceIntegerField(
				"permission_id");
		permission_id.setHidden(true);
		permission_id.setPrimaryKey(true);
		permission_id.setRequired(true);

		DataSourceIntegerField is_permission_group = new DataSourceIntegerField(
				"is_permission_group");
		is_permission_group.setHidden(true);

		DataSourceTextField permission_name = new DataSourceTextField(
				"permission_name", "მომხმარებლის უფლება");

		setFields(permission_id, is_permission_group, permission_name);
		setClientOnly(true);
	}
}