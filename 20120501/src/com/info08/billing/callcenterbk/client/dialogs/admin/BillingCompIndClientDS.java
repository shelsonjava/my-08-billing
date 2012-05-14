package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class BillingCompIndClientDS extends DataSource {

	public static BillingCompIndClientDS getInstance() {
		return new BillingCompIndClientDS("BillingCompIndClientDS");
	}

	public BillingCompIndClientDS(String id) {

		DataSourceIntegerField ind_id = new DataSourceIntegerField("bill_index_id");
		ind_id.setHidden(true);
		ind_id.setPrimaryKey(true);
		ind_id.setRequired(true);

		DataSourceTextField st_ind = new DataSourceTextField("bill_index_start",
				CallCenterBK.constants.startIndex());

		DataSourceTextField end_ind = new DataSourceTextField("bill_index_end",
				CallCenterBK.constants.endIndex());

		DataSourceTextField cr_descr = new DataSourceTextField("applied_wholly_descr",
				CallCenterBK.constants.type());

		DataSourceTextField count_type_descr = new DataSourceTextField(
				"calcul_type_descr", CallCenterBK.constants.type());

		setFields(ind_id, st_ind, end_ind, cr_descr, count_type_descr);
		setClientOnly(true);
	}
}