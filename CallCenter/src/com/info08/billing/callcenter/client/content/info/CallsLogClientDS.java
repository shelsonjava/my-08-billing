package com.info08.billing.callcenter.client.content.info;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class CallsLogClientDS extends DataSource {

	private static CallsLogClientDS instance = null;

	public static CallsLogClientDS getInstance() {
		if (instance == null) {
			instance = new CallsLogClientDS("CallsLogClientDS_DS");
		}
		return instance;
	}

	public CallsLogClientDS(String id) {
		setID(id);
		DataSourceIntegerField call_log_id = new DataSourceIntegerField("id");
		call_log_id.setHidden(true);
		call_log_id.setPrimaryKey(true);
		call_log_id.setRequired(true);
		
		DataSourceTextField op_user_name = new DataSourceTextField("op_user_name","ოპერატორი");
		DataSourceTextField phone = new DataSourceTextField("phone","ნომერი");
		DataSourceTextField abonent_name = new DataSourceTextField("abonent_name","აბონენტი");
		DataSourceIntegerField call_duration = new DataSourceIntegerField("call_duration","ხანგრძლივობა");
		DataSourceDateTimeField incomming_date = new DataSourceDateTimeField("incomming_date","შემოსვლის დრო");
		DataSourceDateTimeField end_date = new DataSourceDateTimeField("end_date","დასრულების დრო");
		DataSourceIntegerField status = new DataSourceIntegerField("status","სტატუსი");
		
		setFields(call_log_id,op_user_name,phone,abonent_name,call_duration,incomming_date,end_date,status);
		setClientOnly(true);
	}
}