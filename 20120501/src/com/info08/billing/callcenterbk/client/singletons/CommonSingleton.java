package com.info08.billing.callcenterbk.client.singletons;

import java.util.Date;
import java.util.Map;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.Users;
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

	private DataSource usersDS;
	private DataSource servicesDS;
	private DataSource departmentDS;
	public Users sessionPerson;
	private ServerSession serverSession;

	public CommonSingleton() {
	}

	public void reInitDS() throws CallCenterException {
		clearDS();
		initDS();
	}

	public void initDS() throws CallCenterException {
		try {
			usersDS = DataSource.get("UsersDS");
			Criteria criteria = new Criteria();
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllUser");
			usersDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {

				}
			}, dsRequest);

			servicesDS = DataSource.get("ServiceDS");
			departmentDS = DataSource.get("DepartmentDS");

			dsRequest.setAttribute("operationId", "searchDepartments");
			departmentDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {

				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CallCenterException(e.toString());
		}
	}

	public void clearDS() {
		sessionPerson = null;
		usersDS = null;
		servicesDS = null;
		departmentDS = null;
	}

	public DataSource getUsersDS() {
		return usersDS;
	}

	public void setUsersDS(DataSource usersDS) {
		this.usersDS = usersDS;
	}

	public DataSource getServicesDS() {
		return servicesDS;
	}

	public void setServicesDS(DataSource servicesDS) {
		this.servicesDS = servicesDS;
	}

	public DataSource getDepartmentDS() {
		return departmentDS;
	}

	public Users getSessionPerson() {
		return sessionPerson;
	}

	public void setSessionPerson(Users sessionPerson) {
		this.sessionPerson = sessionPerson;
	}

	public ServerSession getServerSession() {
		return serverSession;
	}

	public void setServerSession(ServerSession serverSession) {
		this.serverSession = serverSession;
	}

	public void setDepartmentDS(DataSource departmentDS) {
		this.departmentDS = departmentDS;
	}

	public boolean hasPermission(String accessKey) {
		try {
			Long persTypeId = sessionPerson.getDepartment_id();
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
