package com.info08.billing.callcenterbk.client.singletons;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.Users;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;

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

	@SuppressWarnings("rawtypes")
	private ServerSession serverSession;
	private boolean callCenterOperator;

	private TreeMap<String, Window> dialogInstances;

	public CommonSingleton() {
		if (dialogInstances == null) {
			dialogInstances = new TreeMap<String, Window>();
		}
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
				public void execute(DSResponse response, Object rawData, DSRequest request) {

				}
			}, dsRequest);

			servicesDS = DataSource.get("ServiceDS");
			departmentDS = DataSource.get("DepartmentDS");

			dsRequest.setAttribute("operationId", "searchDepartments");
			departmentDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

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

	@SuppressWarnings("rawtypes")
	public ServerSession getServerSession() {
		return serverSession;
	}

	@SuppressWarnings("rawtypes")
	public void setServerSession(ServerSession serverSession) {
		this.serverSession = serverSession;
	}

	public void setDepartmentDS(DataSource departmentDS) {
		this.departmentDS = departmentDS;
	}

	public boolean isCallCenterOperator() {
		return callCenterOperator;
	}

	public void setCallCenterOperator(boolean callCenterOperator) {
		this.callCenterOperator = callCenterOperator;
	}

	public boolean hasPermission(String accessKey) {
		try {
			Long persTypeId = sessionPerson.getDepartment_id();
			if (persTypeId != null && (persTypeId.equals(2) || persTypeId.equals(7))) {
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

	public void addDialogInstance(Window window) {
		if (dialogInstances == null) {
			dialogInstances = new TreeMap<String, Window>();
		}
		dialogInstances.put(window.getClass().getName(), window);
	}

	public void removeDialogInstance(Window window) {
		if (dialogInstances == null) {
			return;
		}
		dialogInstances.remove(window.getClass().getName());
	}

	public TreeMap<String, Window> getDialogInstances() {
		return dialogInstances;
	}

	public void closeAllOpenDialogs() {
		if (dialogInstances == null || dialogInstances.isEmpty()) {
			return;
		}
		Set<String> keys = dialogInstances.keySet();
		for (String key : keys) {
			Window window = dialogInstances.get(key);
			if (window == null) {
				continue;
			}
			window.destroy();
		}
	}
}
