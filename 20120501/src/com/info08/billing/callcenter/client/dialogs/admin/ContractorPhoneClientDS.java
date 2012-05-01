package com.info08.billing.callcenter.client.dialogs.admin;

import com.info08.billing.callcenter.client.CallCenter;
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
				CallCenter.constants.phone());
		DataSourceTextField deleted = new DataSourceTextField("deleted",
				CallCenter.constants.status());
		deleted.setHidden(true);

		setFields(prcId, phone, deleted);
		setClientOnly(true);
	}
}