package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.entity.main.MainDetail;
import com.info08.billing.callcenterbk.shared.items.MainOrgItem;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;

public class MainOrgDMI implements QueryConstants {

	Logger logger = Logger.getLogger(MainOrgDMI.class.getName());

	/**
	 * Updating Orgs Sorting
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public Object updateMainServiceOrders(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateMainServiceOrders.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());
			ArrayList mainIdList = (ArrayList) record.get("mainIdList");
			String loggedUserName = record.get("loggedUserName").toString();

			int order = 1;
			for (Object organization_id : mainIdList) {
				oracleManager.createNativeQuery(Q_UPDATE_MAIN_SERVICE_SORT)
						.setParameter(1, order).setParameter(2, loggedUserName)
						.setParameter(3, currentDate)
						.setParameter(4, (Long) organization_id)
						.executeUpdate();
				order++;
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return "";
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Main Orgs Sorting Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Disable or Activate Main Org.
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public MainOrgItem updateMainOrgDeleteOrRestore(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateMainOrgDeleteOrRestore.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());
			Long organization_id = new Long(record.get("organization_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			oracleManager.createNativeQuery(Q_UPDATE_MAIN_SERVICE_DEL_STAT)
					.setParameter(1, deleted).setParameter(2, loggedUserName)
					.setParameter(3, currentDate)
					.setParameter(4, (Long) organization_id).executeUpdate();
			oracleManager.flush();
			MainOrgItem ret = getMainOrgItem(organization_id, oracleManager);
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return ret;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Main Orgs Del. Status Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Update Main Org. Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public MainOrgItem updateMainOrgStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateMainOrgStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());
			Long organization_id = new Long(record.get("organization_id")
					.toString());
			Long statuse = new Long(record.get("statuse").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			oracleManager.createNativeQuery(Q_UPDATE_MAIN_ORG_STATUS)
					.setParameter(1, statuse)
					.setParameter(2, (Long) organization_id).executeUpdate();

			oracleManager.createNativeQuery(Q_UPDATE_MAIN_SERVICE_HIST)
					.setParameter(1, loggedUserName)
					.setParameter(2, currentDate)
					.setParameter(3, (Long) organization_id).executeUpdate();
			oracleManager.flush();
			MainOrgItem ret = getMainOrgItem(organization_id, oracleManager);
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return ret;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Main Orgs Status Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public MainOrgItem getMainOrgItem(Long organization_id,
			EntityManager oracleManager) throws Exception {
		try {
			MainOrgItem mainOrgItem = new MainOrgItem();
			List result = oracleManager
					.createNativeQuery(QueryConstants.Q_GET_MAIN_ORG_BY_ID)
					.setParameter(1, organization_id).getResultList();
			if (result != null && result.size() > 0) {
				Object columns[] = (Object[]) result.get(0);
				mainOrgItem.setOrganization_id(new Long(
						columns[0] == null ? "-1" : columns[0].toString()));
				mainOrgItem.setMain_master_id(new Long(
						columns[1] == null ? "-1" : columns[1].toString()));
				mainOrgItem.setOrg_name(columns[2] == null ? null : columns[2]
						.toString());
				mainOrgItem.setIdentcode(columns[3] == null ? null : columns[3]
						.toString());
				mainOrgItem.setDirector(columns[4] == null ? null : columns[4]
						.toString());
				mainOrgItem.setLegaladdress(columns[5] == null ? null
						: columns[5].toString());
				mainOrgItem.setDeleted(new Long(columns[6] == null ? "-1"
						: columns[6].toString()));
				mainOrgItem.setNote(columns[7] == null ? null : columns[7]
						.toString());
				mainOrgItem.setWorkinghours(columns[8] == null ? null
						: columns[8].toString());
				mainOrgItem.setFounded(columns[9] == null ? null : columns[9]
						.toString());
				mainOrgItem.setMail(columns[10] == null ? null : columns[10]
						.toString());
				mainOrgItem.setOrg_info(columns[11] == null ? null
						: columns[11].toString());
				mainOrgItem.setWebaddress(columns[12] == null ? null
						: columns[12].toString());
				mainOrgItem.setContactperson(columns[13] == null ? null
						: columns[13].toString());
				mainOrgItem.setDayoffs(columns[14] == null ? null : columns[14]
						.toString());
				mainOrgItem.setWorkpersoncountity(columns[15] == null ? null
						: columns[15].toString());
				mainOrgItem.setInd(columns[16] == null ? null : columns[16]
						.toString());
				mainOrgItem.setOrg_name_eng(columns[17] == null ? null
						: columns[17].toString());
				mainOrgItem.setNew_identcode(columns[18] == null ? null
						: columns[18].toString());
				mainOrgItem.setLegal_statuse_id(new Long(
						columns[19] == null ? "-1" : columns[19].toString()));
				mainOrgItem.setStatuse(new Long(columns[20] == null ? "-1"
						: columns[20].toString()));
				mainOrgItem.setReal_address(columns[21] == null ? null
						: columns[21].toString());
			}
			return mainOrgItem;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Getting Main Orgs From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		}
	}

	@SuppressWarnings("unused")
	public ArrayList<MainDetail> mainDetailSearchDMI(DSRequest dsRequest)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			Long organization_id = new Long(dsRequest.getFieldValue("phone")
					.toString());
			String main_detail_geo = dsRequest.getFieldValue("main_detail_geo") == null ? null
					: dsRequest.getFieldValue("main_detail_geo").toString();
			return null;
		} catch (Exception e) {
			logger.error("Error While Retrieving OrgDeps : ", e);
			throw new CallCenterException(
					"შეცდომა მონაცემების წამოღებისას მონაცემთა ბაზიდან: "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}		
}
