package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.entity.discovery.Discover;
import com.info08.billing.callcenterbk.shared.entity.discovery.DiscoverRtype;
import com.info08.billing.callcenterbk.shared.entity.discovery.DiscoverType;
import com.info08.billing.callcenterbk.shared.entity.session.LogSession;
import com.info08.billing.callcenterbk.shared.entity.session.LogSessionCharge;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;

public class DiscoveryDMI implements QueryConstants {

	Logger logger = Logger.getLogger(DiscoveryDMI.class.getName());

	/**
	 * Adding New DiscoverRtype
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public DiscoverRtype addDiscoverRtypes(DiscoverRtype discoverRtype)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addDiscoverRtypes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = discoverRtype.getLoggedUserName();
			discoverRtype.setRec_date(recDate);
			discoverRtype.setRec_user(loggedUserName);

			oracleManager.persist(discoverRtype);
			oracleManager.flush();

			discoverRtype = oracleManager.find(DiscoverRtype.class,
					discoverRtype.getResponse_type_id());
			discoverRtype.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return discoverRtype;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert DiscoverRtype Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating DiscoverRtype
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public DiscoverRtype updateDiscoverRtypes(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateDiscoverRtypes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long response_type_id = new Long(record.get("response_type_id")
					.toString());
			String response_type = record.get("response_type") == null ? null
					: record.get("response_type").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			DiscoverRtype discoverRtype = oracleManager.find(
					DiscoverRtype.class, response_type_id);

			discoverRtype.setResponse_type(response_type);
			discoverRtype.setUpd_user(loggedUserName);
			discoverRtype.setUpd_date(recDate);

			oracleManager.merge(discoverRtype);
			oracleManager.flush();

			discoverRtype = oracleManager.find(DiscoverRtype.class,
					response_type_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return discoverRtype;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update DiscoverRtype Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating DiscoverRtype Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public DiscoverRtype updateDiscoverRtypesStatus(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransportTypeStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long response_type_id = new Long(record.get("response_type_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			DiscoverRtype discoverRtype = oracleManager.find(
					DiscoverRtype.class, response_type_id);

			discoverRtype.setDeleted(deleted);
			discoverRtype.setUpd_user(loggedUserName);
			discoverRtype.setUpd_date(recDate);

			oracleManager.merge(discoverRtype);
			oracleManager.flush();

			discoverRtype = oracleManager.find(DiscoverRtype.class,
					response_type_id);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return discoverRtype;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for DiscoverRtype Into Database : ",
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
	 * Adding New DiscoverType
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public DiscoverType addDiscoverType(DiscoverType discovertype)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addDiscoverType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = discovertype.getLoggedUserName();
			discovertype.setRec_date(recDate);
			discovertype.setRec_user(loggedUserName);

			oracleManager.persist(discovertype);
			oracleManager.flush();

			discovertype = oracleManager.find(DiscoverType.class,
					discovertype.getDiscover_type_id());
			discovertype.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return discovertype;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert DiscoverType Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating DiscoverType
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public DiscoverType updateDiscoverType(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateDiscoverType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long discover_type_id = new Long(record.get("discover_type_id")
					.toString());
			String discover_type = record.get("discover_type") == null ? null
					: record.get("discover_type").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			DiscoverType discoverType = oracleManager.find(DiscoverType.class,
					discover_type_id);

			discoverType.setDiscover_type(discover_type);
			discoverType.setUpd_user(loggedUserName);
			discoverType.setUpd_date(recDate);

			oracleManager.merge(discoverType);
			oracleManager.flush();

			discoverType = oracleManager.find(DiscoverType.class,
					discover_type_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return discoverType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update DiscoverType Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating DiscoverType Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public DiscoverType updateDiscoverTypeStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateDiscoverTypeStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long discover_type_id = new Long(record.get("discover_type_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			DiscoverType discoverType = oracleManager.find(DiscoverType.class,
					discover_type_id);

			discoverType.setDeleted(deleted);
			discoverType.setUpd_user(loggedUserName);
			discoverType.setUpd_date(recDate);

			oracleManager.merge(discoverType);
			oracleManager.flush();

			discoverType = oracleManager.find(DiscoverType.class,
					discover_type_id);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return discoverType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for DiscoverType Into Database : ",
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
	 * Taking Discover - Update Discovery Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Discover takeDiscover(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.takeDiscover.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			Long discover_id = new Long(record.get("discover_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			ArrayList<Discover> discoveryList = (ArrayList<Discover>) oracleManager
					.createNamedQuery("Discover.getLocked")
					.setParameter("discId", discover_id).getResultList();
			if (discoveryList == null || discoveryList.isEmpty()) {
				throw new CallCenterException(
						"გარკვევის ეს ჩანაწერი უკვე გადაწყვეტილია. გთხოვთ სცადოთ სხვა !");
			}
			Discover discover = discoveryList.get(0);
			oracleManager.lock(discover, LockModeType.OPTIMISTIC);
			if (discover.getIs_locked() != null
					&& discover.getIs_locked().equals(1L)
					&& !loggedUserName.equals(discover.getUpd_user())) {
				throw new CallCenterException(
						"გარკვევის ეს ჩანაწერი უკვე აღებულია სხვის მიერ. გთხოვთ სცადოთ სხვა !");
			}
			discover.setIs_locked(1L);
			discover.setUpd_user(loggedUserName);
			discover.setUpd_date(recDate);
			oracleManager.merge(discover);

			discover = oracleManager.find(Discover.class, discover_id);
			oracleManager.flush();
			Long discovery_type_id = discover.getDiscover_type_id();
			DiscoverType discoverType = oracleManager.find(DiscoverType.class,
					discovery_type_id);
			discover.setDiscover_type(discoverType.getDiscover_type());
			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return discover;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for Discover Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების განახლებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Adding charges From Discovery
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public LogSessionCharge addChargesByDiscovery(
			LogSessionCharge logSessionCharge) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addChargesByDiscovery.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			for (int i = 0; i < logSessionCharge.getChargeCount(); i++) {
				LogSessionCharge item = new LogSessionCharge();
				item.setId(new Long((System.currentTimeMillis() * (i + 1))));
				item.setService_id(logSessionCharge.getService_id());
				item.setSession_id(logSessionCharge.getSession_id());
				item.setUpd_user(logSessionCharge.getUpd_user());
				item.setYm(logSessionCharge.getYm());
				item.setDeleted(0L);
				item.setRec_date(new Timestamp(System.currentTimeMillis()));
				oracleManager.persist(item);
			}

			EMF.commitTransaction(transaction);
			log += ". Adding Charges Finished SuccessFully. ";
			logger.info(log);
			return logSessionCharge;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Adding Charges Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Adding Virtual charges (Making new virtual call)
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public LogSession addChargesWithoutCall(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addChargesWithoutCall.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = dsRequest.getAttribute("loggedUserName")
					.toString();
			String phone = dsRequest.getAttribute("phone").toString();
			Long service_id = new Long(dsRequest.getAttribute("service_id")
					.toString());
			Long chargeCount = new Long(dsRequest.getAttribute("chargeCount")
					.toString());

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
			Long ym = new Long(dateFormat.format(currDate));

			LogSession logSession = new LogSession();
			String session_id = oracleManager
					.createNativeQuery(QueryConstants.Q_GET_VIRTUAL_SESSION_ID)
					.getSingleResult().toString();
			logSession.setSession_id(session_id);
			logSession.setCall_type(new Long(Constants.callTypeVirtualDirect));			
			logSession.setDuration(0L);
			logSession.setEnd_date(currDate);
			logSession.setHungup(0L);
			logSession.setIs_new_bill(1L);
			logSession.setParent_id(0L);
			logSession.setPhone(phone);
			logSession.setSession_quality(0L);
			logSession.setStart_date(currDate);
			logSession.setUser_name(loggedUserName);
			logSession.setYm(ym);

			oracleManager.persist(logSession);

			for (int i = 0; i < chargeCount.intValue(); i++) {
				LogSessionCharge item = new LogSessionCharge();
				item.setId(new Long((System.currentTimeMillis() * (i + 1))));
				item.setService_id(service_id);
				item.setSession_id(session_id);
				item.setUpd_user(loggedUserName);
				item.setYm(ym);
				item.setDeleted(0L);
				item.setRec_date(currDate);
				oracleManager.persist(item);
			}

			EMF.commitTransaction(transaction);
			log += ". Adding Virtual Charges Finished SuccessFully. ";
			logger.info(log);
			return logSession;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Adding Virtual Charges Into Database : ",
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
	 * Updating Discover
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Discover updateDiscoverItem(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateDiscover.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Date recDate = new Date(System.currentTimeMillis());
			Long discover_id = new Long(record.get("discover_id").toString());
			Long response_type_id = new Long(record.get("response_type_id")
					.toString());
			String upd_user = record.get("upd_user").toString();

			Discover discover = oracleManager.find(Discover.class, discover_id);

			discover.setResponse_type_id(response_type_id);
			discover.setUpd_user(upd_user);
			discover.setUpd_date(recDate);
			discover.setIs_locked(0L);
			discover.setExecution_status(1L);

			oracleManager.merge(discover);
			oracleManager.flush();

			List resultList = oracleManager
					.createNativeQuery(QueryConstants.Q_GET_DISCOVERY_RECORD)
					.setParameter(1, discover_id).getResultList();
			if (resultList == null || resultList.isEmpty()) {
				return null;
			}

			Object columns[] = (Object[]) resultList.get(0);
			Discover discoverRet = new Discover();
			discoverRet.setDiscover_type(columns[0] == null ? null : columns[0]
					.toString());
			discoverRet.setResponse_type(columns[1] == null ? null : columns[1]
					.toString());
			discoverRet.setDiscover_id(new Long(columns[2] == null ? "-1"
					: columns[2].toString()));
			discoverRet.setCall_id(columns[3] == null ? null : columns[3]
					.toString());
			discoverRet.setPhone(columns[4] == null ? null : columns[4]
					.toString());
			discoverRet.setDiscover_txt(columns[5] == null ? null : columns[5]
					.toString());
			discoverRet.setContact_phone(columns[6] == null ? null : columns[6]
					.toString());
			discoverRet.setDiscover_type_id(new Long(columns[7] == null ? "-1"
					: columns[7].toString()));
			discoverRet.setRec_date(columns[8] == null ? null
					: (Date) columns[8]);
			// if(columns[8]!=null){
			// Timestamp rec_date = (Timestamp)columns[8];
			// SimpleDateFormat sDF = new SimpleDateFormat("dd/MM/yyyy HH MM");
			// Date dt = new Date(rec_date.getTime());
			// String fDate = sDF.format(dt);
			// dt = sDF.parse(fDate);
			// discoverRet.setRec_date(new Timestamp(dt.getTime()));
			// }
			discoverRet.setRec_user(columns[9] == null ? null : columns[9]
					.toString());
			discoverRet.setDeleted(new Long(columns[10] == null ? "-1"
					: columns[10].toString()));
			discoverRet.setUpd_date(columns[11] == null ? null
					: (Date) columns[11]);
			discoverRet.setUpd_user(columns[12] == null ? null : columns[12]
					.toString());
			discoverRet.setResponse_type_id(new Long(columns[13] == null ? "-1"
					: columns[13].toString()));
			discoverRet.setContact_person(columns[14] == null ? null
					: columns[14].toString());
			discoverRet.setExecution_status(new Long(columns[15] == null ? "-1"
					: columns[15].toString()));
			discoverRet.setCcr(new Long(columns[16] == null ? "-1"
					: columns[16].toString()));
			discoverRet.setIscorrect(new Long(columns[17] == null ? "-1"
					: columns[17].toString()));
			discoverRet.setIs_locked(new Long(columns[18] == null ? "-1"
					: columns[18].toString()));
			discoverRet.setStart_date(columns[19] == null ? null
					: (Date) columns[19]);
			discoverRet.setPersonnel_id(new Long(columns[20] == null ? "-1"
					: columns[20].toString()));
			discoverRet.setPersonnel_id1(new Long(columns[21] == null ? "-1"
					: columns[21].toString()));

			System.out.println("++++++++++++++++++++++++++++++");
			System.out.println("discover = " + discover);
			if (discoverRet != null) {
				System.out.println("getCall_id = " + discoverRet.getCall_id());
				System.out.println("getContact_person = "
						+ discoverRet.getContact_person());
				System.out.println("getContact_phone = "
						+ discoverRet.getContact_phone());
				System.out.println("getDiscover_txt = "
						+ discoverRet.getDiscover_txt());
				System.out.println("getDiscover_type = "
						+ discoverRet.getDiscover_type());
				System.out.println("getPhone = " + discoverRet.getPhone());
				System.out
						.println("getRec_user = " + discoverRet.getRec_user());
				System.out.println("getResponse_type = "
						+ discoverRet.getResponse_type());
				// System.out.println("getStatus_descr = "+discoverRet.getStatus_descr());
				System.out
						.println("getUpd_user = " + discoverRet.getUpd_user());
				System.out.println("getCcr = " + discoverRet.getCcr());
				System.out.println("getDeleted = " + discoverRet.getDeleted());
				System.out.println("getDiscover_id = "
						+ discoverRet.getDiscover_id());
				System.out.println("getDiscover_type_id = "
						+ discoverRet.getDiscover_type_id());
				System.out.println("getExecution_status = "
						+ discoverRet.getExecution_status());
				System.out.println("getIs_locked = "
						+ discoverRet.getIs_locked());
				System.out.println("getIscorrect = "
						+ discoverRet.getIscorrect());
				System.out.println("getIscorrect = "
						+ discoverRet.getIscorrect());
				System.out.println("getResponse_type_id = "
						+ discoverRet.getResponse_type_id());
				System.out
						.println("getRec_date = " + discoverRet.getRec_date());
				System.out
						.println("getUpd_date = " + discoverRet.getUpd_date());
				System.out.println("getPersonnel_id = "
						+ discoverRet.getPersonnel_id());
				System.out.println("getPersonnel_id1 = "
						+ discoverRet.getPersonnel_id1());
				System.out.println("getStart_date = "
						+ discoverRet.getStart_date());
			}
			System.out.println("++++++++++++++++++++++++++++++");

			EMF.commitTransaction(transaction);

			System.out
					.println("++++++++++++++++++++++++++++++1111111111111111");
			System.out.println("discover = " + discover);
			if (discoverRet != null) {
				System.out.println("getCall_id = " + discoverRet.getCall_id());
				System.out.println("getContact_person = "
						+ discoverRet.getContact_person());
				System.out.println("getContact_phone = "
						+ discoverRet.getContact_phone());
				System.out.println("getDiscover_txt = "
						+ discoverRet.getDiscover_txt());
				System.out.println("getDiscover_type = "
						+ discoverRet.getDiscover_type());
				System.out.println("getPhone = " + discoverRet.getPhone());
				System.out
						.println("getRec_user = " + discoverRet.getRec_user());
				System.out.println("getResponse_type = "
						+ discoverRet.getResponse_type());
				// System.out.println("getStatus_descr = "+discoverRet.getStatus_descr());
				System.out
						.println("getUpd_user = " + discoverRet.getUpd_user());
				System.out.println("getCcr = " + discoverRet.getCcr());
				System.out.println("getDeleted = " + discoverRet.getDeleted());
				System.out.println("getDiscover_id = "
						+ discoverRet.getDiscover_id());
				System.out.println("getDiscover_type_id = "
						+ discoverRet.getDiscover_type_id());
				System.out.println("getExecution_status = "
						+ discoverRet.getExecution_status());
				System.out.println("getIs_locked = "
						+ discoverRet.getIs_locked());
				System.out.println("getIscorrect = "
						+ discoverRet.getIscorrect());
				System.out.println("getIscorrect = "
						+ discoverRet.getIscorrect());
				System.out.println("getResponse_type_id = "
						+ discoverRet.getResponse_type_id());
				System.out
						.println("getRec_date = " + discoverRet.getRec_date());
				System.out
						.println("getUpd_date = " + discoverRet.getUpd_date());
				System.out.println("getPersonnel_id = "
						+ discoverRet.getPersonnel_id());
				System.out.println("getPersonnel_id1 = "
						+ discoverRet.getPersonnel_id1());
				System.out.println("getStart_date = "
						+ discoverRet.getStart_date());
			}
			System.out
					.println("++++++++++++++++++++++++++++++22222222222222222");

			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return discoverRet;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Discover Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

}
