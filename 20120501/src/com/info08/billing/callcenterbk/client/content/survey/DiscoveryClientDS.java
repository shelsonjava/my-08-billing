package com.info08.billing.callcenterbk.client.content.survey;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class DiscoveryClientDS extends DataSource {

	private static DiscoveryClientDS instance = null;

	public static DiscoveryClientDS getInstance() {
		if (instance == null) {
			instance = new DiscoveryClientDS("DiscoveryClientDS_DS");
		}
		return instance;
	}
	public DiscoveryClientDS(String id) {
		setID(id);		
		DataSourceIntegerField survey_id = new DataSourceIntegerField("survey_id");
		DataSourceIntegerField session_call_id = new DataSourceIntegerField("session_call_id");
		DataSourceIntegerField survey_kind_id = new DataSourceIntegerField("survey_kind_id");
		DataSourceIntegerField survery_responce_status = new DataSourceIntegerField("survery_responce_status");
		DataSourceIntegerField survey_done = new DataSourceIntegerField("survey_done");
		DataSourceIntegerField survey_reply_type_id = new DataSourceIntegerField("survey_reply_type_id");
		DataSourceDateTimeField upd_date = new DataSourceDateTimeField("upd_date");
		DataSourceTextField survey_kind_name = new DataSourceTextField("survey_kind_name",CallCenterBK.constants.type());
		DataSourceTextField p_numb = new DataSourceTextField("p_numb",CallCenterBK.constants.phone());		
		DataSourceTextField survey_phone = new DataSourceTextField("survey_phone",CallCenterBK.constants.contactPhone());
		DataSourceTextField contact_person = new DataSourceTextField("contact_person",CallCenterBK.constants.contactPerson());
		DataSourceTextField survey_descript = new DataSourceTextField("survey_descript",CallCenterBK.constants.message());
		DataSourceTextField rec_user = new DataSourceTextField("rec_user",CallCenterBK.constants.shortOp());
		DataSourceTextField status_descr = new DataSourceTextField("status_descr",CallCenterBK.constants.status());
		
		survey_id.setHidden(true);
		survey_id.setPrimaryKey(true);
		survey_id.setRequired(true);
		session_call_id.setHidden(true);
		survey_kind_id.setHidden(true);
		survery_responce_status.setHidden(true);
		survey_done.setHidden(true);
		survey_reply_type_id.setHidden(true);
		upd_date.setHidden(true);
		
		setFields(survey_kind_name, survey_id, session_call_id, survey_kind_id,
				survery_responce_status, survey_done, survey_reply_type_id, p_numb,
				survey_phone, contact_person, survey_descript, rec_user,
				upd_date, status_descr);
		setClientOnly(true);
	}
}