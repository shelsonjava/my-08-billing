package com.info08.billing.callcenterbk.client.dialogs.transport;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class WeekDaysClientDS extends DataSource {

	public static WeekDaysClientDS getInstance() {
		return new WeekDaysClientDS("WeekDaysClientDS");
	}

	public WeekDaysClientDS(String id) {
		// setID(id);
		setRecordXPath("/List/weekday");
		DataSourceIntegerField permission_id = new DataSourceIntegerField(
				"day_id");
		permission_id.setHidden(true);
		permission_id.setPrimaryKey(true);
		permission_id.setRequired(true);

		DataSourceTextField permission_name = new DataSourceTextField("day_name",
				"კვირის დღეები");

		setFields(permission_id, permission_name);
		setClientOnly(true);
		setDataURL("ds/data/weekdays.data.xml");
	}
}