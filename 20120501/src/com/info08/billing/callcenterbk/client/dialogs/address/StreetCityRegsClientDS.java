package com.info08.billing.callcenterbk.client.dialogs.address;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class StreetCityRegsClientDS extends DataSource {

	public static StreetCityRegsClientDS getInstance() {
		return new StreetCityRegsClientDS("StreetCityRegsClientDS");
	}

	public StreetCityRegsClientDS(String id) {
		//setID(id);
		DataSourceIntegerField city_region_id = new DataSourceIntegerField(
				"city_region_id");
		city_region_id.setHidden(true);
		city_region_id.setPrimaryKey(true);
		city_region_id.setRequired(true);

		DataSourceTextField city_region_name_geo = new DataSourceTextField(
				"city_region_name_geo", "არჩეული რეგიონი");

		setFields(city_region_id, city_region_name_geo);
		setClientOnly(true);
	}
}