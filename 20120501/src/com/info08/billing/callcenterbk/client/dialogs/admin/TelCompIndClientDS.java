package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class TelCompIndClientDS extends DataSource {

	public static TelCompIndClientDS getInstance() {
		return new TelCompIndClientDS("TelCompIndClientDS");
	}

	public TelCompIndClientDS(String id) {

		DataSourceIntegerField ind_id = new DataSourceIntegerField("ind_id");
		ind_id.setHidden(true);
		ind_id.setPrimaryKey(true);
		ind_id.setRequired(true);

		DataSourceTextField st_ind = new DataSourceTextField("st_ind",
				CallCenter.constants.startIndex());

		DataSourceTextField end_ind = new DataSourceTextField("end_ind",
				CallCenter.constants.endIndex());

		DataSourceTextField cr_descr = new DataSourceTextField("cr_descr",
				CallCenter.constants.type());

		DataSourceTextField count_type_descr = new DataSourceTextField(
				"count_type_descr", CallCenter.constants.type());

		setFields(ind_id, st_ind, end_ind, cr_descr, count_type_descr);
		setClientOnly(true);
	}
}