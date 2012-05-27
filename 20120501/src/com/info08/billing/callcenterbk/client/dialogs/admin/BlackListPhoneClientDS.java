package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class BlackListPhoneClientDS extends DataSource {

	public static BlackListPhoneClientDS getInstance() {
		return new BlackListPhoneClientDS("BlackListPhoneClientDS");
	}

	public BlackListPhoneClientDS(String id) {
		DataSourceTextField phone = new DataSourceTextField("phone",
				CallCenterBK.constants.phone());
		phone.setPrimaryKey(true);
		phone.setRequired(true);
		setFields(phone);
		setClientOnly(true);
	}
	
}