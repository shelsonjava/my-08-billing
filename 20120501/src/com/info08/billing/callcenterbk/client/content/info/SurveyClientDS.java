package com.info08.billing.callcenterbk.client.content.info;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class SurveyClientDS extends DataSource {

	private static SurveyClientDS instance = null;

	public static SurveyClientDS getInstance() {
		if (instance == null) {
			instance = new SurveyClientDS("SurveyClientDS");
		}
		return instance;
	}

	public SurveyClientDS(String id) {
		setID(id);
		DataSourceIntegerField survey_id = new DataSourceIntegerField("survey_id");
		survey_id.setHidden(true);
		survey_id.setPrimaryKey(true);
		survey_id.setRequired(true);
		
		DataSourceTextField survey_kind_name = new DataSourceTextField("survey_kind_name","");
		DataSourceTextField survey_reply_type_name = new DataSourceTextField("survey_reply_type_name","");
		DataSourceTextField session_call_id = new DataSourceTextField("session_call_id","");
		DataSourceTextField p_numb = new DataSourceTextField("p_numb","");
		DataSourceTextField survey_descript = new DataSourceTextField("survey_descript","");
		DataSourceTextField survey_phone = new DataSourceTextField("survey_phone","");
		DataSourceIntegerField survey_kind_id = new DataSourceIntegerField("survey_kind_id","");
		DataSourceIntegerField survey_reply_type_id = new DataSourceIntegerField("survey_reply_type_id","");
		DataSourceTextField survey_person = new DataSourceTextField("survey_person","");		
		DataSourceIntegerField survery_responce_status = new DataSourceIntegerField("survery_responce_status","");
		DataSourceIntegerField survey_done = new DataSourceIntegerField("survey_done","");
		DataSourceIntegerField bblocked = new DataSourceIntegerField("bblocked","");
		DataSourceTextField survey_creator = new DataSourceTextField("survey_creator","");
		DataSourceDateTimeField survey_created = new DataSourceDateTimeField("survey_created","");
		DataSourceTextField loked_user = new DataSourceTextField("loked_user","");		
		DataSourceDateTimeField start_date = new DataSourceDateTimeField("start_date","");
		DataSourceIntegerField personnel_id = new DataSourceIntegerField("personnel_id","");
		DataSourceTextField operator_src = new DataSourceTextField("operator_src","");
		
		setFields(survey_id, survey_kind_name, survey_reply_type_name,
				session_call_id, p_numb, survey_descript, survey_phone,
				survey_kind_id, survey_reply_type_id, survey_person,
				survery_responce_status, survey_done, bblocked, survey_creator,
				survey_created, loked_user, start_date, personnel_id,
				operator_src);
		setClientOnly(true);
	}
}