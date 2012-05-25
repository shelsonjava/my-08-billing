package com.info08.billing.callcenterbk.client.dialogs.org;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class DayOffsClientDS extends DataSource {

	public static DayOffsClientDS getInstance() {
		return new DayOffsClientDS("DayOffsClientDS");
	}

	public DayOffsClientDS(String id) {
		// setID(id);
		setRecordXPath("/List/weekday");
		DataSourceIntegerField permission_id = new DataSourceIntegerField("day_id");
		permission_id.setHidden(true);
		permission_id.setPrimaryKey(true);
		permission_id.setRequired(true);

		DataSourceTextField permission_name = new DataSourceTextField("day_name", "დასვენების დღეები");

		setFields(permission_id, permission_name);
		setClientOnly(true);
		setDataURL("ds/data/daysoffs.data.xml");
	}
}