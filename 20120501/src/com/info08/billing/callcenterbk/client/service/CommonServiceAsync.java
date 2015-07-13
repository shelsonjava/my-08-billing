package com.info08.billing.callcenterbk.client.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.shared.common.ServerSession;

/**
 * The async counterpart of <code>GreetingService</code>.
 */	
public interface CommonServiceAsync {

	@SuppressWarnings("rawtypes")
	void login(String userName, String user_password, String sessionId, boolean isOperUser, AsyncCallback<ServerSession> callback) throws CallCenterException;

	void findSessionMp3ById(String sessionId, Date sessionDate, AsyncCallback<String> callback) throws CallCenterException;

	void getBillingCompBillByMonth(Integer billing_company_id, Integer ym, AsyncCallback<Void> callback) throws CallCenterException;

	void getBillingCompBillByDay(Integer billing_company_id, Date date_param, AsyncCallback<Void> callback) throws CallCenterException;

	@SuppressWarnings("rawtypes")
	void getInfoByPhone(String phone, ServerSession serverSession, AsyncCallback<ServerSession> callback) throws CallCenterException;
	
	@SuppressWarnings("rawtypes")
	void endCallSession(String phone, ServerSession serverSession, AsyncCallback<ServerSession> callback) throws CallCenterException;
	
}
