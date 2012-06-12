package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class OrgDepPhoneClientDS extends DataSource {

	public static OrgDepPhoneClientDS getInstance() {
		return new OrgDepPhoneClientDS("OrgDepPhoneClientDS");
	}

	public OrgDepPhoneClientDS(String id) {

		DataSourceTextField org_dep_to_ph_id = new DataSourceTextField(
				"org_dep_to_ph_id");
		org_dep_to_ph_id.setHidden(true);
		org_dep_to_ph_id.setPrimaryKey(true);

		DataSourceIntegerField hidden_by_request = new DataSourceIntegerField(
				"hidden_by_request");
		hidden_by_request.setHidden(true);

		DataSourceIntegerField phone_contract_type = new DataSourceIntegerField(
				"phone_contract_type");
		phone_contract_type.setHidden(true);

		DataSourceIntegerField for_contact = new DataSourceIntegerField(
				"for_contact");
		for_contact.setHidden(true);

		DataSourceIntegerField phone_state_id = new DataSourceIntegerField(
				"phone_state_id");
		phone_state_id.setHidden(true);

		DataSourceIntegerField phone_type_id = new DataSourceIntegerField(
				"phone_type_id");
		phone_type_id.setHidden(true);

		DataSourceIntegerField is_parallel = new DataSourceIntegerField(
				"is_parallel");
		is_parallel.setHidden(true);

		DataSourceIntegerField phone_order = new DataSourceIntegerField(
				"phone_order");
		phone_order.setHidden(true);

		DataSourceTextField phone = new DataSourceTextField("phone",
				CallCenterBK.constants.phone());
		phone.setRequired(true);

		DataSourceTextField hidden_by_request_descr = new DataSourceTextField(
				"hidden_by_request_descr", CallCenterBK.constants.openClose());
		DataSourceTextField phone_contract_type_descr = new DataSourceTextField(
				"phone_contract_type_descr",
				CallCenterBK.constants.phoneStatus());
		DataSourceTextField for_contact_descr = new DataSourceTextField(
				"for_contact_descr", CallCenterBK.constants.contactPhone());
		DataSourceTextField phone_state_descr = new DataSourceTextField(
				"phone_state_descr", CallCenterBK.constants.phoneState());
		DataSourceTextField phone_type_descr = new DataSourceTextField(
				"phone_type_descr", CallCenterBK.constants.type());
		DataSourceTextField is_parallel_descr = new DataSourceTextField(
				"is_parallel_descr", CallCenterBK.constants.paraller());

		setFields(org_dep_to_ph_id, hidden_by_request, phone_contract_type,
				for_contact, phone_state_id, phone_type_id, is_parallel,
				phone_order, phone, hidden_by_request_descr,
				phone_contract_type_descr, for_contact_descr,
				phone_state_descr, phone_type_descr, is_parallel_descr);
		setClientOnly(true);
	}
}