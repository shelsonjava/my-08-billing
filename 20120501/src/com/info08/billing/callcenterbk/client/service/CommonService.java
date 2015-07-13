package com.info08.billing.callcenterbk.client.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.shared.common.ServerSession;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("commonService")
public interface CommonService extends RemoteService {

	@SuppressWarnings("rawtypes")
	ServerSession login(String userName, String user_password, String sessionId, boolean isOperUser) throws CallCenterException;

	String findSessionMp3ById(String sessionId, Date sessionDate) throws CallCenterException;

	void getBillingCompBillByMonth(Integer billing_company_id, Integer ym) throws CallCenterException;

	void getBillingCompBillByDay(Integer billing_company_id, Date date_param) throws CallCenterException;

	@SuppressWarnings("rawtypes")
	public ServerSession getInfoByPhone(String phone, ServerSession serverSession) throws CallCenterException;
	
	@SuppressWarnings("rawtypes")
	public ServerSession endCallSession(String phone, ServerSession serverSession) throws CallCenterException;
	
	
}
