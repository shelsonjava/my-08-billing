package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.redorg.OrgPriorityList;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.jpa.EMF;

public class OrgPriorityListDMI implements QueryConstants {

	Logger logger = Logger.getLogger(OrgPriorityListDMI.class.getName());

	public DSResponse addEditOrgPriorityList(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrgPriorityListDMI.addEditOrgPriorityList.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			String loggedUserName = dsRequest.getValues().get("loggedUserName")
					.toString();
			String contact_phones = dsRequest.getValues().get("contact_phones")
					.toString();
			String remark = dsRequest.getValues().get("remark").toString();
			Long debt = new Long(dsRequest.getValues().get("debt").toString());
			Double debt_amount = new Double(dsRequest.getValues()
					.get("debt_amount").toString());
			Double tariff = new Double(dsRequest.getValues().get("tariff")
					.toString());
			Long organization_id = new Long(dsRequest.getValues()
					.get("organization_id").toString());

			Date billing_date = (Date) dsRequest.getValues()
					.get("billing_date");

			Date start_date = (Date) dsRequest.getValues().get("start_date");

			Date end_date = (Date) dsRequest.getValues().get("end_date");

			Long operator_src = new Long(dsRequest.getValues()
					.get("operator_src").toString());

			Long sms_warning = new Long(dsRequest.getValues()
					.get("sms_warning").toString());

			Long id = dsRequest.getValues().get("id") == null ? null
					: new Long(dsRequest.getValues().get("id").toString());

			Double debet = new Double(
					dsRequest.getValues().get("debet") == null ? "0"
							: dsRequest.getValues().get("debet").toString());

			Timestamp currDateTime = new Timestamp(System.currentTimeMillis());

			OrgPriorityList orgPriorityList = new OrgPriorityList();
			if (id != null) {
				orgPriorityList.setId(id);
			}

			orgPriorityList.setBilling_date(new Timestamp(billing_date
					.getTime()));
			orgPriorityList.setContact_phones(contact_phones);
			orgPriorityList.setDebt(debt);
			orgPriorityList.setDebt_amount(debt_amount);
			orgPriorityList.setEnd_date(new Timestamp(end_date.getTime()));
			orgPriorityList.setOperator_src(operator_src);
			orgPriorityList.setOrganization_id(organization_id);
			orgPriorityList.setRemark(remark);
			orgPriorityList.setStart_date(new Timestamp(start_date.getTime()));
			orgPriorityList.setTariff(tariff);
			orgPriorityList.setUpdate_date(currDateTime);
			orgPriorityList.setUpdate_user(loggedUserName);
			orgPriorityList.setSms_warning(sms_warning);
			orgPriorityList.setDebet(debet);

			if (id != null) {
				oracleManager.merge(orgPriorityList);
			} else {
				oracleManager.persist(orgPriorityList);
			}

			EMF.commitTransaction(transaction);

			DSResponse resp = new DSResponse();
			resp.setData(orgPriorityList);
			resp.setStatus(DSResponse.STATUS_SUCCESS);

			log += ". Adding Virtual Charges Finished SuccessFully. ";
			logger.info(log);
			return resp;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public OrgPriorityList removeRecord(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrgPriorityListDMI.removeRecord.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long id = new Long(dsRequest.getOldValues().get("id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "Remove");

			oracleManager.createNativeQuery(QueryConstants.Q_RED_ORG)
					.setParameter(1, id).executeUpdate();

			EMF.commitTransaction(transaction);
			log += ". Removing Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
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
