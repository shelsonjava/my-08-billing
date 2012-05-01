package com.info08.billing.callcenterbk.client.content.discovery;

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
		DataSourceIntegerField discover_id = new DataSourceIntegerField("discover_id");
		DataSourceIntegerField call_id = new DataSourceIntegerField("call_id");
		DataSourceIntegerField ccr = new DataSourceIntegerField("ccr");
		DataSourceIntegerField deleted = new DataSourceIntegerField("deleted");
		DataSourceIntegerField discover_type_id = new DataSourceIntegerField("discover_type_id");
		DataSourceIntegerField execution_status = new DataSourceIntegerField("execution_status");
		DataSourceIntegerField iscorrect = new DataSourceIntegerField("iscorrect");
		DataSourceIntegerField response_type_id = new DataSourceIntegerField("response_type_id");
		DataSourceDateTimeField upd_date = new DataSourceDateTimeField("upd_date");
		DataSourceTextField upd_user = new DataSourceTextField("upd_user");
		DataSourceTextField discover_type = new DataSourceTextField("discover_type",CallCenterBK.constants.type());
		DataSourceTextField phone = new DataSourceTextField("phone",CallCenterBK.constants.phone());		
		DataSourceTextField contact_phone = new DataSourceTextField("contact_phone",CallCenterBK.constants.contactPhone());
		DataSourceTextField contact_person = new DataSourceTextField("contact_person",CallCenterBK.constants.contactPerson());
		DataSourceTextField discover_txt = new DataSourceTextField("discover_txt",CallCenterBK.constants.message());
		DataSourceTextField rec_user = new DataSourceTextField("rec_user",CallCenterBK.constants.shortOp());
		DataSourceDateTimeField rec_date = new DataSourceDateTimeField("rec_date",CallCenterBK.constants.time());
		DataSourceTextField status_descr = new DataSourceTextField("status_descr",CallCenterBK.constants.status());
		
		discover_id.setHidden(true);
		discover_id.setPrimaryKey(true);
		discover_id.setRequired(true);
		call_id.setHidden(true);
		ccr.setHidden(true);
		deleted.setHidden(true);
		discover_type_id.setHidden(true);
		execution_status.setHidden(true);
		iscorrect.setHidden(true);
		response_type_id.setHidden(true);
		upd_date.setHidden(true);
		upd_user.setHidden(true);
		
		setFields(discover_type, discover_id, call_id, deleted, discover_type_id,
				execution_status, iscorrect, response_type_id, phone,
				contact_phone, contact_person, discover_txt, rec_user,
				rec_date, upd_date, upd_user, status_descr,ccr);
		setClientOnly(true);
	}
}