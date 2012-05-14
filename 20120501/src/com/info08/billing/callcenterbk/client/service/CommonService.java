package com.info08.billing.callcenterbk.client.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.Person;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("commonService")
public interface CommonService extends RemoteService {

	ServerSession login(String userName, String password, String sessionId)
			throws CallCenterException;

	String findSessionMp3ById(String sessionId, Date sessionDate)
			throws CallCenterException;

	// Log Personal Notes
	void saveOrUpdateLogPersNote(Integer noteId, String sessionId,
			Integer visOpt, String note, Integer particular, Person person)
			throws CallCenterException;

	void deleteLogPersNote(Integer noteId) throws CallCenterException;

	void getBillingCompBillByMonth(Integer billing_company_id, Integer ym)
			throws CallCenterException;

	void getBillingCompBillByDay(Integer billing_company_id, Date date_param)
			throws CallCenterException;
}
