package com.info08.billing.callcenterbk.client.dialogs.transport;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class TranspWeekDaysClientDS extends DataSource {

	public static TranspWeekDaysClientDS getInstance() {
		return new TranspWeekDaysClientDS("TranspWeekDaysClientDS");
	}

	public TranspWeekDaysClientDS(String id) {
		//setID(id);
		DataSourceIntegerField access_id = new DataSourceIntegerField("day_id");
		access_id.setHidden(true);
		access_id.setPrimaryKey(true);
		access_id.setRequired(true);

		DataSourceTextField access_name = new DataSourceTextField("day_name",
				"გრაფიკის დღეები");

		setFields(access_id, access_name);
		setClientOnly(true);
	}
}