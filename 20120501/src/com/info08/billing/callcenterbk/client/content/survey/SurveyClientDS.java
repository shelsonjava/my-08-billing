package com.info08.billing.callcenterbk.client.content.survey;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class SurveyClientDS extends DataSource {

	private static SurveyClientDS instance = null;

	public static SurveyClientDS getInstance() {
		if (instance == null) {
			instance = new SurveyClientDS("SurveyClientDS_DS");
		}
		return instance;
	}
	public SurveyClientDS(String id) {
		setID(id);		
		DataSourceIntegerField survey_id = new DataSourceIntegerField("survey_id");
		DataSourceIntegerField session_call_id = new DataSourceIntegerField("session_call_id");
		DataSourceIntegerField survey_kind_id = new DataSourceIntegerField("survey_kind_id");
		DataSourceIntegerField survery_responce_status = new DataSourceIntegerField("survery_responce_status");
		DataSourceIntegerField survey_done = new DataSourceIntegerField("survey_done");
		DataSourceIntegerField survey_reply_type_id = new DataSourceIntegerField("survey_reply_type_id");
		DataSourceTextField survey_kind_name = new DataSourceTextField("survey_kind_name",CallCenterBK.constants.type());
		DataSourceTextField p_numb = new DataSourceTextField("p_numb",CallCenterBK.constants.phone());		
		DataSourceTextField survey_phone = new DataSourceTextField("survey_phone",CallCenterBK.constants.contactPhone());
		DataSourceTextField survey_person = new DataSourceTextField("survey_person",CallCenterBK.constants.contactPerson());
		DataSourceTextField survey_descript = new DataSourceTextField("survey_descript",CallCenterBK.constants.message());
		DataSourceTextField survey_creator = new DataSourceTextField("survey_creator",CallCenterBK.constants.shortOp());
		DataSourceTextField status_descr = new DataSourceTextField("status_descr",CallCenterBK.constants.status());
		
		survey_id.setHidden(true);
		survey_id.setPrimaryKey(true);
		survey_id.setRequired(true);
		session_call_id.setHidden(true);
		survey_kind_id.setHidden(true);
		survery_responce_status.setHidden(true);
		survey_done.setHidden(true);
		survey_reply_type_id.setHidden(true);

		setFields(survey_kind_name, survey_id, session_call_id, survey_kind_id,
				survery_responce_status, survey_done, survey_reply_type_id,
				p_numb, survey_phone, survey_person, survey_descript,
				survey_creator, status_descr);
		setClientOnly(true);
	}
}