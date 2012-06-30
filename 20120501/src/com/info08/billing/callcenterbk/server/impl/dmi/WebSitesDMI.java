package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.websites.WebSiteGroups;
import com.info08.billing.callcenterbk.shared.entity.websites.WebSites;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;

public class WebSitesDMI implements QueryConstants {

	Logger logger = Logger.getLogger(WebSitesDMI.class.getName());

	/**
	 * Adding New Record
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public WebSiteGroups addWebSiteGroup(WebSiteGroups webSiteGroup)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:WebSitesDMI.addWebSiteGroup.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = webSiteGroup.getLoggedUserName();
			webSiteGroup.setLoggedUserName(loggedUserName);
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add WebSiteGroups.");

			oracleManager.persist(webSiteGroup);
			oracleManager.flush();

			webSiteGroup = oracleManager.find(WebSiteGroups.class,
					webSiteGroup.getWeb_site_group_id());

			webSiteGroup.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return webSiteGroup;
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
	public WebSiteGroups updateWebSiteGroup(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:WebSitesDMI.updateWebSiteGroup.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long web_site_group_id = new Long(record.get("web_site_group_id")
					.toString());
			String web_site_group_name = record.get("web_site_group_name") == null ? null
					: record.get("web_site_group_name").toString();
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update WebSiteGroups.");

			WebSiteGroups webSiteGroups = oracleManager.find(
					WebSiteGroups.class, web_site_group_id);

			webSiteGroups.setWeb_site_group_name(web_site_group_name);
			webSiteGroups.setLoggedUserName(loggedUserName);

			oracleManager.merge(webSiteGroups);
			oracleManager.flush();

			webSiteGroups = oracleManager.find(WebSiteGroups.class,
					web_site_group_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return webSiteGroups;
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
	public WebSiteGroups removeWebSiteGroup(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:WebSitesDMI.removeWebSiteGroup.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long web_site_group_id = new Long(dsRequest.getOldValues()
					.get("web_site_group_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing WebSiteGroups.");

			WebSiteGroups webSiteGroups = oracleManager.find(
					WebSiteGroups.class, web_site_group_id);
			webSiteGroups.setLoggedUserName(loggedUserName);

			oracleManager.remove(webSiteGroups);
			oracleManager.flush();

			webSiteGroups = oracleManager.find(WebSiteGroups.class,
					web_site_group_id);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return webSiteGroups;
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
	public WebSites addWebSites(WebSites webSites) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:WebSitesDMI.addWebSites.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = webSites.getLoggedUserName();
			webSites.setLoggedUserName(loggedUserName);
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add WebSites.");

			oracleManager.persist(webSites);
			oracleManager.flush();

			webSites = oracleManager.find(WebSites.class,
					webSites.getWeb_site_id());

			WebSiteGroups groups = oracleManager.find(WebSiteGroups.class,
					webSites.getWeb_site_group_id());
			webSites.setWeb_site_group_name(groups.getWeb_site_group_name());

			webSites.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return webSites;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert WebSites Into Database : ", e);
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
	public WebSites updateWebSites(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:WebSitesDMI.updateWebSites.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long web_site_id = new Long(record.get("web_site_id").toString());

			Long web_site_group_id = new Long(record.get("web_site_group_id")
					.toString());

			String address = record.get("address") == null ? null : record.get(
					"address").toString();

			String remark = record.get("remark") == null ? null : record.get(
					"remark").toString();

			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update WebSiteGroups.");

			WebSites webSites = oracleManager.find(WebSites.class, web_site_id);

			webSites.setAddress(address);
			webSites.setRemark(remark);
			webSites.setWeb_site_group_id(web_site_group_id);
			webSites.setLoggedUserName(loggedUserName);

			oracleManager.merge(webSites);
			oracleManager.flush();

			webSites = oracleManager.find(WebSites.class, web_site_id);

			WebSiteGroups groups = oracleManager.find(WebSiteGroups.class,
					web_site_group_id);
			webSites.setWeb_site_group_name(groups.getWeb_site_group_name());

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return webSites;
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
	public WebSites removeWebSites(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:WebSitesDMI.removeWebSites.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long web_site_id = new Long(dsRequest.getOldValues()
					.get("web_site_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing WebSites.");

			WebSites webSites = oracleManager.find(WebSites.class, web_site_id);
			webSites.setLoggedUserName(loggedUserName);

			oracleManager.remove(webSites);
			oracleManager.flush();

			webSites = oracleManager.find(WebSites.class, web_site_id);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return webSites;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove WebSites From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
