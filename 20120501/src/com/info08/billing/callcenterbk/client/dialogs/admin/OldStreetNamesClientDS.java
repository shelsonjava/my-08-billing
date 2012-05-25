package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class OldStreetNamesClientDS extends DataSource {

	public static OldStreetNamesClientDS getInstance() {
		return new OldStreetNamesClientDS("OldStreetNamesClientDS");
	}

	public OldStreetNamesClientDS(String id) {
		DataSourceIntegerField street_old_id = new DataSourceIntegerField(
				"street_old_id");
		street_old_id.setHidden(true);
		street_old_id.setPrimaryKey(true);
		street_old_id.setRequired(true);

		DataSourceIntegerField street_id = new DataSourceIntegerField(
				"street_id");
		street_id.setHidden(true);

		DataSourceTextField street_old_name_descr = new DataSourceTextField(
				"street_old_name_descr", "ქუჩის ძველი დასახელება");

		setFields(street_old_id, street_id, street_old_name_descr);
		setClientOnly(true);
	}
}