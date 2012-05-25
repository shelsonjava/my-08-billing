package com.info08.billing.callcenterbk.client.dialogs.org;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class OrgDayOffsClientDS extends DataSource {

	public static OrgDayOffsClientDS getInstance() {
		return new OrgDayOffsClientDS("OrgDayOffsClientDS");
	}

	public OrgDayOffsClientDS(String id) {
		// setID(id);
		DataSourceIntegerField permission_id = new DataSourceIntegerField("day_id");
		permission_id.setHidden(true);
		permission_id.setPrimaryKey(true);
		permission_id.setRequired(true);

		DataSourceTextField permission_name = new DataSourceTextField("day_name", "ორგ. დასვენების დღეები");

		setFields(permission_id, permission_name);
		setClientOnly(true);
	}
}