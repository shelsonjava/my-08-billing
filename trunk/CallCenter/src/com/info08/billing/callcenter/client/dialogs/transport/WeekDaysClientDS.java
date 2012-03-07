package com.info08.billing.callcenter.client.dialogs.transport;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class WeekDaysClientDS extends DataSource {

	public static WeekDaysClientDS getInstance() {
		return new WeekDaysClientDS("WeekDaysClientDS");
	}

	public WeekDaysClientDS(String id) {
		//setID(id);
		setRecordXPath("/List/weekday"); 
		DataSourceIntegerField access_id = new DataSourceIntegerField("day_id");
		access_id.setHidden(true);
		access_id.setPrimaryKey(true);
		access_id.setRequired(true);

		DataSourceTextField access_name = new DataSourceTextField("day_name", "კვირის დღეები");

		setFields(access_id, access_name);
		setClientOnly(true);
		setDataURL("ds/data/weekdays.data.xml");  
	}
}