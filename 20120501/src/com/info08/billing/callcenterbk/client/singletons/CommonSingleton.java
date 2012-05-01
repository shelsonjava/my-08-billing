package com.info08.billing.callcenterbk.client.singletons;

import java.util.Date;
import java.util.Map;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.Person;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.SC;

public class CommonSingleton {

	private static CommonSingleton instance;

	public static CommonSingleton getInstance() throws CallCenterException {
		if (instance == null) {
			instance = new CommonSingleton();
		}
		return instance;
	}

	private DataSource personsDS;
	private DataSource servicesDS;
	private DataSource persTypeDS;
	public Person sessionPerson;
	private ServerSession serverSession;

	public CommonSingleton() {
	}

	public void reInitDS() throws CallCenterException {
		clearDS();
		initDS();
	}

	public void initDS() throws CallCenterException {
		try {
			personsDS = DataSource.get("PersDS");
			Criteria criteria = new Criteria();
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "customPersSearchAll");
			personsDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {

				}
			}, dsRequest);

			servicesDS = DataSource.get("ServiceDS");
			persTypeDS = DataSource.get("PersTypeDS");

			dsRequest.setAttribute("operationId", "persTypesSearch");
			persTypeDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {

				}
			}, dsRequest);
		} catch (Exception e) {
			throw new CallCenterException(e.toString());
		}
	}

	public void clearDS() {
		sessionPerson = null;
		personsDS = null;
		servicesDS = null;
		persTypeDS = null;
	}

	public DataSource getPersonsDS() {
		return personsDS;
	}

	public void setPersonsDS(DataSource personsDS) {
		this.personsDS = personsDS;
	}

	public DataSource getServicesDS() {
		return servicesDS;
	}

	public void setServicesDS(DataSource servicesDS) {
		this.servicesDS = servicesDS;
	}

	public DataSource getPersTypeDS() {
		return persTypeDS;
	}

	public Person getSessionPerson() {
		return sessionPerson;
	}

	public void setSessionPerson(Person sessionPerson) {
		this.sessionPerson = sessionPerson;
	}

	public ServerSession getServerSession() {
		return serverSession;
	}

	public void setServerSession(ServerSession serverSession) {
		this.serverSession = serverSession;
	}

	public void setPersTypeDS(DataSource persTypeDS) {
		this.persTypeDS = persTypeDS;
	}

	public boolean hasPermission(String accessKey) {
		try {
			Long persTypeId = sessionPerson.getPersonelTypeId();
			if (persTypeId != null
					&& (persTypeId.equals(2) || persTypeId.equals(7))) {
				return true;
			}
			Map<String, String> mapPerms = sessionPerson.getUserPerms();
			if (mapPerms == null || mapPerms.isEmpty()) {
				return false;
			}
			return mapPerms.containsKey(accessKey);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
			return false;
		}
	}

	public String getUnixTimeStamp() {
		Date date = new Date();
		return date.getTime() + "";
	}
}
