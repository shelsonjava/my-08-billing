package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class MainOrgActClientDS extends DataSource {

	public static MainOrgActClientDS getInstance() {
		return new MainOrgActClientDS("MainOrgActClientDS");
	}

	public MainOrgActClientDS(String id) {
		//setID(id);
		DataSourceIntegerField org_activity_id = new DataSourceIntegerField("org_activity_id");
		org_activity_id.setHidden(true);
		org_activity_id.setPrimaryKey(true);
		org_activity_id.setRequired(true);

		DataSourceTextField activity_description = new DataSourceTextField("activity_description", CallCenterBK.constants.activity());

		setFields(org_activity_id, activity_description);
		setClientOnly(true);
	}
}