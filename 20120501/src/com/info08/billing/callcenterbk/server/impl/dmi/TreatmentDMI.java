package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.callcenter.FreeOfChargePhone;
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

	// ----------------------------------------------------------------------------------------------

	public FreeOfChargePhone addFreeOfChargePhone(
			FreeOfChargePhone freeOfChargePhone) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:TreatmentDMI.addFreeOfChargePhone.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = freeOfChargePhone.getLoggedUserName();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add FreeOfChargePhone.");

			oracleManager.persist(freeOfChargePhone);
			oracleManager.flush();

			freeOfChargePhone = oracleManager.find(FreeOfChargePhone.class,
					freeOfChargePhone.getPhone_number_id());

			freeOfChargePhone.setLoggedUserName(loggedUserName);
			Date start = new Date(freeOfChargePhone.getStart_date().getTime());
			final SimpleDateFormat dateFormatter = new SimpleDateFormat(
					"dd-MM-yyyy");
			final String start_descr = dateFormatter.format(start);

			Date end = new Date(freeOfChargePhone.getEnd_date().getTime());
			final String end_descr = dateFormatter.format(end);

			if (freeOfChargePhone != null) {
				freeOfChargePhone.setStart_date_descr(start_descr);
				freeOfChargePhone.setEnd_date_descr(end_descr);
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return freeOfChargePhone;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public FreeOfChargePhone updateFreeOfChargePhone(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateFreeOfChargePhone.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long phone_number_id = new Long(record.get("phone_number_id")
					.toString());
			String phone_number = record.get("phone_number") == null ? null
					: record.get("phone_number").toString();
			String remark = record.get("remark") == null ? null : record.get(
					"remark").toString();

			Date start_date = record.get("start_date") == null ? null
					: (Date) record.get("start_date");

			Date end_date = record.get("end_date") == null ? null
					: (Date) record.get("end_date");

			String loggedUserName = record.get("loggedUserName").toString();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update FreeOfChargePhone.");

			FreeOfChargePhone freeOfChargePhone = oracleManager.find(
					FreeOfChargePhone.class, phone_number_id);

			freeOfChargePhone.setPhone_number(phone_number);
			freeOfChargePhone.setRemark(remark);
			if (start_date != null) {
				freeOfChargePhone.setStart_date(new Timestamp(start_date
						.getTime()));
			}

			if (end_date != null) {
				freeOfChargePhone
						.setEnd_date(new Timestamp(end_date.getTime()));
			}
			freeOfChargePhone.setLoggedUserName(loggedUserName);

			oracleManager.merge(freeOfChargePhone);
			oracleManager.flush();

			freeOfChargePhone = oracleManager.find(FreeOfChargePhone.class,
					phone_number_id);

			Date start = new Date(freeOfChargePhone.getStart_date().getTime());
			final SimpleDateFormat dateFormatter = new SimpleDateFormat(
					"dd-MM-yyyy");

			final String start_descr = dateFormatter.format(start);

			Date end = new Date(freeOfChargePhone.getEnd_date().getTime());
			final String end_descr = dateFormatter.format(end);

			if (freeOfChargePhone != null) {
				freeOfChargePhone.setStart_date_descr(start_descr);
				freeOfChargePhone.setEnd_date_descr(end_descr);
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return freeOfChargePhone;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public FreeOfChargePhone removeFreeOfChargePhone(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.removeTreatments.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long phone_number_id = new Long(dsRequest.getOldValues()
					.get("phone_number_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing Treatments.");

			FreeOfChargePhone freeOfChargePhone = oracleManager.find(
					FreeOfChargePhone.class, phone_number_id);
			freeOfChargePhone.setLoggedUserName(loggedUserName);

			oracleManager.remove(freeOfChargePhone);
			oracleManager.flush();

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return freeOfChargePhone;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Delete From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

}
