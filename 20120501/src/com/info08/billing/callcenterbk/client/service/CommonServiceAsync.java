package com.info08.billing.callcenterbk.client.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.Person;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CommonServiceAsync {
	void login(String userName, String password, String sessionId,
			AsyncCallback<ServerSession> callback) throws CallCenterException;

	void findSessionMp3ById(String sessionId, Date sessionDate,
			AsyncCallback<String> callback) throws CallCenterException;

	// Log Personal Notes
	void saveOrUpdateLogPersNote(Integer noteId, String sessionId,
			Integer visOpt, String note, Integer particular, Person person,
			AsyncCallback<Void> callback) throws CallCenterException;

	void deleteLogPersNote(Integer noteId, AsyncCallback<Void> callback)
			throws CallCenterException;

	void getBillingCompBillByMonth(Integer billing_company_id, Integer ym,
			AsyncCallback<Void> callback) throws CallCenterException;

	void getBillingCompBillByDay(Integer billing_company_id, Date date_param,
			AsyncCallback<Void> callback) throws CallCenterException;
}
