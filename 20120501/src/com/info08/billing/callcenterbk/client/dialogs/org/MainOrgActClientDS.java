package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class MainOrgActClientDS extends DataSource {

	public static MainOrgActClientDS getInstance() {
		return new MainOrgActClientDS("MainOrgActClientDS");
	}

	public MainOrgActClientDS(String id) {
		//setID(id);
		DataSourceIntegerField business_detail_id = new DataSourceIntegerField(
				"business_detail_id");
		business_detail_id.setHidden(true);
		business_detail_id.setPrimaryKey(true);
		business_detail_id.setRequired(true);

		DataSourceTextField business_detail_name_geo = new DataSourceTextField(
				"business_detail_name_geo", CallCenter.constants.activity());

		setFields(business_detail_id, business_detail_name_geo);
		setClientOnly(true);
	}
}