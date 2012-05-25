package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class OrgPartnetBanksClientDS extends DataSource {

	public static OrgPartnetBanksClientDS getInstance() {
		return new OrgPartnetBanksClientDS("OrgPartnetBanksClientDS");
	}

	public OrgPartnetBanksClientDS(String id) {
		// setID(id);
		DataSourceIntegerField organization_id = new DataSourceIntegerField("organization_id");
		organization_id.setHidden(true);
		organization_id.setPrimaryKey(true);
		organization_id.setRequired(true);

		DataSourceTextField organization_name = new DataSourceTextField("organization_name", CallCenterBK.constants.orgName());
		DataSourceTextField full_address_not_hidden = new DataSourceTextField("full_address_not_hidden", CallCenterBK.constants.orgName());

		setFields(organization_id, organization_name, full_address_not_hidden);
		setClientOnly(true);		
	}
}