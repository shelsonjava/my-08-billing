package com.info08.billing.callcenterbk.client.dialogs.correction;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class AbPhonesClientDS extends DataSource {

	public static AbPhonesClientDS getInstance() {
		return new AbPhonesClientDS("AbPhonesClientDS");
	}

	public AbPhonesClientDS(String id) {
		// setID(id);

		DataSourceIntegerField pk = new DataSourceIntegerField("pk");
		pk.setPrimaryKey(true);
		pk.setRequired(true);
		pk.setHidden(true);

		// phone number descr
		DataSourceTextField phone = new DataSourceTextField("phone",
				"ტელეფონის ნომერი", 140);

		DataSourceTextField opCloseField = new DataSourceTextField(
				"hidden_by_request_descr", "ღია/დაფარული", 100);
		// is parallel or not field
		DataSourceTextField parallelUsualField = new DataSourceTextField(
				"is_parallel_descr", "პარალელური", 120);
		// phone status field
		DataSourceTextField phoneStatusField = new DataSourceTextField(
				"phone_contract_type_desr", "სტატუსი", 100);
		// phone state field
		DataSourceTextField phoneStateField = new DataSourceTextField(
				"phone_state", "მდგომარეობა", 100);
		// phone type field
		DataSourceTextField phoneTypeField = new DataSourceTextField(
				"phone_type", "ტიპი", 119);

		setFields(pk, phone, opCloseField, parallelUsualField,
				phoneStatusField, phoneStateField, phoneTypeField);
		setClientOnly(true);
	}
}