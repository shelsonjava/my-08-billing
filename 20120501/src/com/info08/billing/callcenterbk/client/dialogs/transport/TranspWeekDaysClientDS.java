package com.info08.billing.callcenterbk.client.dialogs.transport;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class TranspWeekDaysClientDS extends DataSource {

	public static TranspWeekDaysClientDS getInstance() {
		return new TranspWeekDaysClientDS("TranspWeekDaysClientDS");
	}

	public TranspWeekDaysClientDS(String id) {
		// setID(id);
		DataSourceIntegerField permission_id = new DataSourceIntegerField(
				"day_id");
		permission_id.setHidden(true);
		permission_id.setPrimaryKey(true);
		permission_id.setRequired(true);

		DataSourceTextField permission_name = new DataSourceTextField("day_name",
				"გრაფიკის დღეები");

		setFields(permission_id, permission_name);
		setClientOnly(true);
	}
}