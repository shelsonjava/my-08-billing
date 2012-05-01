package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class ContractorPhoneClientDS extends DataSource {

	public static ContractorPhoneClientDS getInstance() {
		return new ContractorPhoneClientDS("ContractorPhoneClientDS");
	}

	public ContractorPhoneClientDS(String id) {
		DataSourceIntegerField prcId = new DataSourceIntegerField("id");
		prcId.setHidden(true);
		prcId.setPrimaryKey(true);
		prcId.setRequired(true);

		DataSourceTextField phone = new DataSourceTextField("phone",
				CallCenterBK.constants.phone());
		DataSourceTextField deleted = new DataSourceTextField("deleted",
				CallCenterBK.constants.status());
		deleted.setHidden(true);

		setFields(prcId, phone, deleted);
		setClientOnly(true);
	}
}