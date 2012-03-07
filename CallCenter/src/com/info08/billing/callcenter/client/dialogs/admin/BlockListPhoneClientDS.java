package com.info08.billing.callcenter.client.dialogs.admin;

import com.info08.billing.callcenter.client.CallCenter;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class BlockListPhoneClientDS extends DataSource {

	public static BlockListPhoneClientDS getInstance() {
		return new BlockListPhoneClientDS("BlockListPhoneClientDS");
	}

	public BlockListPhoneClientDS(String id) {
		DataSourceIntegerField prcId = new DataSourceIntegerField("id");
		prcId.setHidden(true);
		prcId.setPrimaryKey(true);
		prcId.setRequired(true);

		DataSourceTextField phone = new DataSourceTextField("phone",
				CallCenter.constants.phone());

		setFields(prcId, phone);
		setClientOnly(true);
	}
}