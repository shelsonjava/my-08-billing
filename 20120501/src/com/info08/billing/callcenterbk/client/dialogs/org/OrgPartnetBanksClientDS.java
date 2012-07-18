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
		DataSourceIntegerField org_part_bank_id = new DataSourceIntegerField("org_part_bank_id");
		org_part_bank_id.setHidden(true);
		org_part_bank_id.setPrimaryKey(true);
		org_part_bank_id.setRequired(true);

		DataSourceTextField organization_name = new DataSourceTextField("organization_name", CallCenterBK.constants.orgName());

		setFields(org_part_bank_id, organization_name);
		setClientOnly(true);		
	}	
}