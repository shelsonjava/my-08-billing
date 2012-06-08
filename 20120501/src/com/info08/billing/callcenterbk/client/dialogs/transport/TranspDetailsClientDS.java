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
		DataSourceIntegerField transp_item_id = new DataSourceIntegerField("transp_item_id");
		transp_item_id.setHidden(true);
		transp_item_id.setPrimaryKey(true);
		transp_item_id.setRequired(true);

		DataSourceTextField depart_station = new DataSourceTextField("depart_station", "გავლის პუნქტი");
		DataSourceDateTimeField arrival_time = new DataSourceDateTimeField("arrival_time", "ჩასვლის დრო");
		DataSourceDateTimeField departure_time = new DataSourceDateTimeField("departure_time", "გასვლის დრო");
		DataSourceDateTimeField item_order = new DataSourceDateTimeField("item_order", "თანმიმდევრობა");

		setFields(transp_item_id, depart_station, arrival_time,
				departure_time, item_order);
		setClientOnly(true);
	}
}