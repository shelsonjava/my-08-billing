package com.info08.billing.callcenterbk.client.dialogs.correction;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class AbPhonesClientDS extends DataSource {

	public static AbPhonesClientDS getInstance() {
		return new AbPhonesClientDS("AbPhonesClientDS");
	}

	public AbPhonesClientDS(String id) {
		//setID(id);

		DataSourceIntegerField pk = new DataSourceIntegerField("pk");
		pk.setPrimaryKey(true);
		pk.setRequired(true);
		pk.setHidden(true);

		// phone number descr
		DataSourceTextField phone = new DataSourceTextField("phone",
				"ტელეფონის ნომერი", 140);

		DataSourceTextField opCloseField = new DataSourceTextField(
				"is_hide_descr", "ღია/დაფარული", 100);
		// is parallel or not field
		DataSourceTextField parallelUsualField = new DataSourceTextField(
				"is_parallel_descr", "პარალელური", 120);
		// phone status field
		DataSourceTextField phoneStatusField = new DataSourceTextField(
				"phone_status", "სტატუსი", 100);
		// phone state field
		DataSourceTextField phoneStateField = new DataSourceTextField(
				"phone_state", "მდგომარეობა", 100);
		// phone type field
		DataSourceTextField phoneTypeField = new DataSourceTextField(
				"phone_type", "ტიპი", 119);

		DataSourceIntegerField is_hide = new DataSourceIntegerField("is_hide");
		is_hide.setHidden(true);

		DataSourceIntegerField is_parallel = new DataSourceIntegerField(
				"is_parallel");
		is_parallel.setHidden(true);

		DataSourceIntegerField phone_status_id = new DataSourceIntegerField(
				"phone_status_id");
		phone_status_id.setHidden(true);

		DataSourceIntegerField phone_state_id = new DataSourceIntegerField(
				"phone_state_id");
		phone_state_id.setHidden(true);

		DataSourceIntegerField phone_type_id = new DataSourceIntegerField(
				"phone_type_id");
		phone_type_id.setHidden(true);

		setFields(pk, phone, opCloseField, parallelUsualField,
				phoneStatusField, phoneStateField, phoneTypeField, is_hide,
				is_parallel, phone_status_id, phone_state_id, phone_type_id);
		setClientOnly(true);
	}
}