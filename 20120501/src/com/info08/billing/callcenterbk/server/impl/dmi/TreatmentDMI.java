package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.callcenter.Treatments;
import com.info08.billing.callcenterbk.shared.entity.descriptions.Description;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;

public class TreatmentDMI implements QueryConstants {

	Logger logger = Logger.getLogger(TreatmentDMI.class.getName());

	public Treatments addTreatment(Treatments treatments) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:TreatmentDMI.addTreatment.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = treatments.getLoggedUserName();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add Treatments.");

			oracleManager.persist(treatments);
			oracleManager.flush();

			treatments = oracleManager.find(Treatments.class,
					treatments.getTreatment_id());

			treatments.setLoggedUserName(loggedUserName);

			Description gender_descr = oracleManager.find(Description.class,
					treatments.getGender());
			treatments.setGender_descr(gender_descr.getDescription());

			Description visible_descr = oracleManager.find(Description.class,
					treatments.getVisible());
			treatments.setVisible_descr(visible_descr.getDescription());

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return treatments;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert treatments Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Treatments updateTreatments(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTreatments.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long treatment_id = new Long(record.get("treatment_id").toString());
			String phone_number = record.get("phone_number") == null ? null
					: record.get("phone_number").toString();
			String treatment = record.get("treatment") == null ? null : record
					.get("treatment").toString();
			Long visible = record.get("visible") == null ? null : new Long(
					record.get("visible").toString());
			Long gender = record.get("gender") == null ? null : new Long(record
					.get("gender").toString());

			String loggedUserName = record.get("loggedUserName").toString();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update Treatments.");

			Treatments treatments = oracleManager.find(Treatments.class,
					treatment_id);

			treatments.setGender(gender);
			treatments.setVisible(visible);
			treatments.setPhone_number(phone_number);
			treatments.setTreatment(treatment);

			treatments.setLoggedUserName(loggedUserName);

			oracleManager.merge(treatments);
			oracleManager.flush();

			treatments = oracleManager.find(Treatments.class, treatment_id);

			Description gender_descr = oracleManager.find(Description.class,
					gender);
			treatments.setGender_descr(gender_descr.getDescription());

			Description visible_descr = oracleManager.find(Description.class,
					visible);
			treatments.setVisible_descr(visible_descr.getDescription());

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return treatments;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update treatments Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public Treatments removeTreatments(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.removeTreatments.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long treatment_id = new Long(dsRequest.getOldValues()
					.get("treatment_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing Treatments.");

			Treatments treatments = oracleManager.find(Treatments.class,
					treatment_id);
			treatments.setLoggedUserName(loggedUserName);

			oracleManager.remove(treatments);
			oracleManager.flush();

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return treatments;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for treatments Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	// ----------------------------------------------------------------------------------------------

	public Long removeUnknownNumber(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:TreatmentDMI.removeUnknownNumber.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long phone_number_id = new Long(dsRequest.getOldValues()
					.get("phone_number_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing UnknownNumber.");

			oracleManager.createNativeQuery(QueryConstants.DEL_UNKNOWN_NUMBER)
					.setParameter(1, phone_number_id).executeUpdate();

			EMF.commitTransaction(transaction);
			log += ". Status Removing Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove UnknownNumber From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
