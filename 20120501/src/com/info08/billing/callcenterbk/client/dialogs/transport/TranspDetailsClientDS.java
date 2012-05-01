package com.info08.billing.callcenterbk.client.dialogs.transport;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class TranspDetailsClientDS extends DataSource {

	public static TranspDetailsClientDS getInstance() {
		return new TranspDetailsClientDS("TranspDetailsClientDS");
	}

	public TranspDetailsClientDS(String id) {
		//setID(id);
		DataSourceIntegerField transport_detail_id = new DataSourceIntegerField("transport_detail_id");
		transport_detail_id.setHidden(true);
		transport_detail_id.setPrimaryKey(true);
		transport_detail_id.setRequired(true);

		DataSourceTextField transport_place_geo_out = new DataSourceTextField("transport_place_geo_out", "გავლის პუნქტი");
		DataSourceDateTimeField in_time = new DataSourceDateTimeField("in_time", "ჩასვლის დრო");
		DataSourceDateTimeField out_time = new DataSourceDateTimeField("out_time", "გასვლის დრო");
		DataSourceDateTimeField transport_detail_order = new DataSourceDateTimeField("transport_detail_order", "თანმიმდევრობა");

		setFields(transport_detail_id, transport_place_geo_out, in_time,
				out_time, transport_detail_order);
		setClientOnly(true);
	}
}