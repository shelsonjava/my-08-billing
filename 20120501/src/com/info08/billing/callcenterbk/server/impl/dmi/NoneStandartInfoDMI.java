package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.nonstandartinfo.NonStandartInfo;
import com.info08.billing.callcenterbk.shared.entity.nonstandartinfo.NonStandartInfoGroups;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;

public class NoneStandartInfoDMI implements QueryConstants {

	Logger logger = Logger.getLogger(NoneStandartInfoDMI.class.getName());

	/**
	 * Adding New Record
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public NonStandartInfoGroups addNoneStandartInfoGroups(
			NonStandartInfoGroups nonStandartInfoGroups) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:NoneStandartInfoDMI.addNoneStandartInfoGroups.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = nonStandartInfoGroups.getLoggedUserName();
			nonStandartInfoGroups.setLoggedUserName(loggedUserName);
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add NonStandartInfoGroups.");

			oracleManager.persist(nonStandartInfoGroups);
			oracleManager.flush();

			nonStandartInfoGroups = oracleManager.find(
					NonStandartInfoGroups.class,
					nonStandartInfoGroups.getInfo_group_id());

			nonStandartInfoGroups.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return nonStandartInfoGroups;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert WebSiteGroups Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating Record
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public NonStandartInfoGroups updateNoneStandartInfoGroups(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:NoneStandartInfoDMI.updateWebSiteGroup.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long info_group_id = new Long(record.get("info_group_id")
					.toString());
			String info_group_name = record.get("info_group_name") == null ? null
					: record.get("info_group_name").toString();
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update NonStandartInfoGroups.");

			NonStandartInfoGroups nonStandartInfoGroups = oracleManager.find(
					NonStandartInfoGroups.class, info_group_id);

			nonStandartInfoGroups.setInfo_group_name(info_group_name);
			nonStandartInfoGroups.setLoggedUserName(loggedUserName);

			oracleManager.merge(nonStandartInfoGroups);
			oracleManager.flush();

			nonStandartInfoGroups = oracleManager.find(
					NonStandartInfoGroups.class, info_group_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return nonStandartInfoGroups;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update WebSiteGroups Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Deleting Record
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public NonStandartInfoGroups removeNoneStandartInfoGroups(
			DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:NoneStandartInfoDMI.removeWebSiteGroup.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long info_group_id = new Long(dsRequest.getOldValues()
					.get("info_group_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing NonStandartInfoGroups.");

			NonStandartInfoGroups nonStandartInfoGroups = oracleManager.find(
					NonStandartInfoGroups.class, info_group_id);
			nonStandartInfoGroups.setLoggedUserName(loggedUserName);

			oracleManager.remove(nonStandartInfoGroups);
			oracleManager.flush();

			nonStandartInfoGroups = oracleManager.find(
					NonStandartInfoGroups.class, info_group_id);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return nonStandartInfoGroups;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove WebSiteGroups From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Adding New Record
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public NonStandartInfo addNoneStandartInfo(NonStandartInfo nonStandartInfo)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:NoneStandartInfoDMI.addNoneStandartInfo.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = nonStandartInfo.getLoggedUserName();
			nonStandartInfo.setLoggedUserName(loggedUserName);
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add NonStandartInfo.");

			oracleManager.persist(nonStandartInfo);
			oracleManager.flush();

			nonStandartInfo = oracleManager.find(NonStandartInfo.class,
					nonStandartInfo.getInfo_id());

			NonStandartInfoGroups groups = oracleManager.find(
					NonStandartInfoGroups.class,
					nonStandartInfo.getInfo_group_id());
			nonStandartInfo.setInfo_group_name(groups.getInfo_group_name());

			nonStandartInfo.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return nonStandartInfo;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert NonStandartInfo Into Database : ",
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
	 * Updating Record
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public NonStandartInfo updateNoneStandartInfo(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:NoneStandartInfoDMI.updateNoneStandartInfo.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long info_id = new Long(record.get("info_id").toString());

			Long info_group_id = new Long(record.get("info_group_id")
					.toString());

			String info_name = record.get("info_name") == null ? null : record
					.get("info_name").toString();

			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update WebSiteGroups.");

			NonStandartInfo nonStandartInfo = oracleManager.find(
					NonStandartInfo.class, info_id);

			nonStandartInfo.setInfo_name(info_name);

			nonStandartInfo.setInfo_group_id(info_group_id);

			nonStandartInfo.setLoggedUserName(loggedUserName);

			oracleManager.merge(nonStandartInfo);
			oracleManager.flush();

			nonStandartInfo = oracleManager
					.find(NonStandartInfo.class, info_id);

			NonStandartInfoGroups groups = oracleManager.find(
					NonStandartInfoGroups.class, info_group_id);
			nonStandartInfo.setInfo_group_name(groups.getInfo_group_name());

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return nonStandartInfo;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update WebSites Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Deleting Record
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public NonStandartInfo removeNoneStandartInfo(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:NoneStandartInfoDMI.removeNoneStandartInfo.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long info_id = new Long(dsRequest.getOldValues().get("info_id")
					.toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing NonStandartInfo.");

			NonStandartInfo nonStandartInfo = oracleManager.find(
					NonStandartInfo.class, info_id);
			nonStandartInfo.setLoggedUserName(loggedUserName);

			oracleManager.remove(nonStandartInfo);
			oracleManager.flush();

			nonStandartInfo = oracleManager
					.find(NonStandartInfo.class, info_id);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return nonStandartInfo;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove NonStandartInfo From Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
