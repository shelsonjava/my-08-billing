package com.info08.billing.callcenterbk.client.dialogs.address;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class StreetToTownDistrictsClientDS extends DataSource {

	public static StreetToTownDistrictsClientDS getInstance() {
		return new StreetToTownDistrictsClientDS("StreetToTownDistrictsClientDS");
	}

	public StreetToTownDistrictsClientDS(String id) {
		//setID(id);
		DataSourceIntegerField town_district_id = new DataSourceIntegerField("town_district_id");
		town_district_id.setHidden(true);
		town_district_id.setPrimaryKey(true);
		town_district_id.setRequired(true);

		DataSourceTextField town_district_name = new DataSourceTextField("town_district_name", "არჩეული რეგიონი");

		setFields(town_district_id, town_district_name);
		setClientOnly(true);
	}
}