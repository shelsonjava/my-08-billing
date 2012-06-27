package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.control.OperatorWarn;
import com.info08.billing.callcenterbk.shared.entity.session.CallSession;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.jpa.EMF;
import com.isomorphic.util.DataTools;

public class OperatorWarnsDMI {

	Logger logger = Logger.getLogger(OperatorWarnsDMI.class.getName());

	@SuppressWarnings("unchecked")
	public DSResponse addOrUpdateOperatorWarn(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OperatorWarnsDMI.addOrUpdateOperatorWarns.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Map<?, ?> values = dsRequest.getValues();
			Long oper_warn_id = values.get("oper_warn_id") != null ? Long
					.parseLong(values.get("oper_warn_id").toString()) : null;
			String call_session_id = values.get("call_session_id").toString();
			ArrayList<CallSession> callSessions = (ArrayList<CallSession>) oracleManager
					.createNamedQuery("CallSession.getByCallSessionId")
					.setParameter("callSessId", call_session_id)
					.getResultList();
			CallSession callSession = callSessions.get(0);

			String loggedUserName = values.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			OperatorWarn operatorWarn = null;
			if (oper_warn_id != null) {
				operatorWarn = oracleManager.find(OperatorWarn.class,
						oper_warn_id);
			} else {
				operatorWarn = new OperatorWarn();
			}
			DataTools.setProperties(values, operatorWarn);
			operatorWarn.setOperator(callSession.getUname());
			operatorWarn.setPhone_number(callSession.getCall_phone());

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "");

			if (operatorWarn.getOper_warn_id() == null) {
				oracleManager.persist(operatorWarn);
			} else {
				oracleManager.merge(operatorWarn);
			}

			EMF.commitTransaction(transaction);
			oper_warn_id = operatorWarn.getOper_warn_id();
			log += ". Adding/Updating Operator Warnings SuccessFully. ";
			logger.info(log);

			values = DMIUtils.findRecordById("OperatorWarnsDS",
					"operatorWarnsSeach", oper_warn_id, "oper_warn_id");
			DSResponse dsResponse = new DSResponse();
			dsResponse.setData(values);
			dsResponse.setInvalidateCache(true);
			return dsResponse;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Add Or Update Operator Warns : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public void deleteOperatorWarn(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OperatorWarnsDMI.deleteOperatorWarn.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Map<?, ?> values = dsRequest.getOldValues();
			Long oper_warn_id = Long.parseLong(values.get("oper_warn_id")
					.toString());
			String loggedUserName = values.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			OperatorWarn operatorWarn = oracleManager.find(OperatorWarn.class,
					oper_warn_id);
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "");
			oracleManager.remove(operatorWarn);

			EMF.commitTransaction(transaction);
			log += ". Removing Finished SuccessFully. ";
			logger.info(log);
		} catch (Exception e) {
			if (transaction != null) {
				EMF.rollbackTransaction(transaction);
			}
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove Data From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წაშლისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
