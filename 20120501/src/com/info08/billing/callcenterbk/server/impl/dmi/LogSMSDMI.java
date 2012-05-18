package com.info08.billing.callcenterbk.server.impl.dmi;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.shared.entity.SentSMSHist;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.jpa.EMF;

public class LogSMSDMI {

	Logger logger = Logger.getLogger(LogSMSDMI.class.getName());

	@SuppressWarnings("unchecked")
	public DSResponse fetch(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		try {
			String session_call_id = dsRequest.getFieldValue("session_call_id").toString();
			oracleManager = EMF.getEntityManager();

			long startRow = dsRequest.getStartRow();
			long endRow = dsRequest.getEndRow();

			Long totalRows = new Long(oracleManager
					.createNamedQuery("SentSMSHist.getBySessionIdCount")
					.setParameter("sessId", session_call_id).getSingleResult()
					.toString());

			ArrayList<SentSMSHist> matchingItems = (ArrayList<SentSMSHist>) oracleManager
					.createNamedQuery("SentSMSHist.getBySessionId")
					.setParameter("sessId", session_call_id).getResultList();

			DSResponse dsResponse = new DSResponse();
			dsResponse.setTotalRows(totalRows);
			dsResponse.setTotalRows(0);
			dsResponse.setStartRow(startRow);

			endRow = Math.min(endRow, totalRows);
			endRow = Math.min(endRow, 0);
			dsResponse.setEndRow(endRow);

			// just return the List of matching beans
			dsResponse.setData(matchingItems);

			return dsResponse;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching SMS From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
