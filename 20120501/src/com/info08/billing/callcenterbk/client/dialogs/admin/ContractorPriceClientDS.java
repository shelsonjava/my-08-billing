package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class ContractorPriceClientDS extends DataSource {

	public static ContractorPriceClientDS getInstance() {
		return new ContractorPriceClientDS("ContractorPriceClientDS");
	}

	public ContractorPriceClientDS(String id) {
		//setID(id);
		DataSourceIntegerField prcId = new DataSourceIntegerField("id");
		prcId.setHidden(true);
		prcId.setPrimaryKey(true);
		prcId.setRequired(true);
		
		DataSourceTextField call_count_start = new DataSourceTextField("call_count_start", CallCenter.constants.startCount());
		DataSourceTextField call_count_end = new DataSourceTextField("call_count_end", CallCenter.constants.endCount());
		DataSourceTextField price = new DataSourceTextField("price", CallCenter.constants.price());
		

		setFields(prcId, call_count_start, call_count_end, price);
		setClientOnly(true);
	}
}