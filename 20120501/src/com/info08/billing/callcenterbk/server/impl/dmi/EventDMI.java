package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.entity.ent.EntPlace;
import com.info08.billing.callcenterbk.shared.entity.ent.EntPoster;
import com.info08.billing.callcenterbk.shared.entity.ent.EntType;
import com.info08.billing.callcenterbk.shared.entity.org.MainOrg;
import com.isomorphic.jpa.EMF;

public class EventDMI implements QueryConstants {

	Logger logger = Logger.getLogger(EventDMI.class.getName());

	/**
	 * Adding New EntType
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public EntType addEntType(EntType entType) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEntType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = entType.getLoggedUserName();
			entType.setRec_date(recDate);
			entType.setRec_user(loggedUserName);
			entType.setOld_id(-1000L);
			entType.setService_id(10L);

			oracleManager.persist(entType);
			oracleManager.flush();

			entType = oracleManager.find(EntType.class,
					entType.getEnt_type_id());

			entType.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return entType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert EntType Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating EntType
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public EntType updateEntType(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEntType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long ent_type_id = new Long(record.get("ent_type_id").toString());
			String ent_type_geo = record.get("ent_type_geo") == null ? null
					: record.get("ent_type_geo").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			EntType entType = oracleManager.find(EntType.class, ent_type_id);

			entType.setEnt_type_geo(ent_type_geo);
			entType.setUpd_user(loggedUserName);

			oracleManager.merge(entType);
			oracleManager.flush();

			entType = oracleManager.find(EntType.class, ent_type_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return entType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update EntType Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating EntType Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public EntType updateEntTypeStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransportTypeStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long ent_type_id = new Long(record.get("ent_type_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			EntType entType = oracleManager.find(EntType.class, ent_type_id);

			entType.setDeleted(deleted);
			entType.setUpd_user(loggedUserName);

			oracleManager.merge(entType);
			oracleManager.flush();

			entType = oracleManager.find(EntType.class, ent_type_id);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return entType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for EntType Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Adding New EntPlace
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public EntPlace addEntPlace(EntPlace entPlace) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEntPlace.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = entPlace.getLoggedUserName();
			entPlace.setRec_date(recDate);
			entPlace.setRec_user(loggedUserName);
			entPlace.setOld_id(-1000L);

			oracleManager.persist(entPlace);
			oracleManager.flush();

			entPlace = oracleManager.find(EntPlace.class,
					entPlace.getEnt_place_id());
			entPlace.setLoggedUserName(loggedUserName);
			Long ent_type_id = entPlace.getEnt_type_id();
			if (ent_type_id != null) {
				EntType entType = oracleManager
						.find(EntType.class, ent_type_id);
				if (entType != null) {
					entPlace.setEnt_type_geo(entType.getEnt_type_geo());
				}
			}

			Long main_id = entPlace.getMain_id();
			if (main_id != null && main_id.longValue() > 0) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class, main_id);
				if (mainOrg != null) {
					entPlace.setOrg_name(mainOrg.getOrg_name());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return entPlace;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert EntPlace Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating EntPlace
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public EntPlace updateEntPlace(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEntPlace.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long ent_place_id = new Long(record.get("ent_place_id").toString());
			Long ent_type_id = new Long(record.get("ent_type_id").toString());
			Long main_id = record.get("main_id") == null ? null : new Long(
					record.get("main_id").toString());
			Long reservation = record.get("reservation") == null ? null
					: new Long(record.get("reservation").toString());
			String ent_place_geo = record.get("ent_place_geo") == null ? null
					: record.get("ent_place_geo").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			EntPlace entPlace = oracleManager
					.find(EntPlace.class, ent_place_id);

			entPlace.setEnt_type_id(ent_type_id);
			entPlace.setUpd_user(loggedUserName);
			entPlace.setMain_id(main_id);
			entPlace.setEnt_place_geo(ent_place_geo);
			entPlace.setReservation(reservation);

			oracleManager.merge(entPlace);
			oracleManager.flush();

			entPlace = oracleManager.find(EntPlace.class, ent_place_id);
			entPlace.setLoggedUserName(loggedUserName);
			if (ent_type_id != null) {
				EntType entType = oracleManager
						.find(EntType.class, ent_type_id);
				if (entType != null) {
					entPlace.setEnt_type_geo(entType.getEnt_type_geo());
				}
			}

			if (main_id != null && main_id.longValue() > 0) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class, main_id);
				if (mainOrg != null) {
					entPlace.setOrg_name(mainOrg.getOrg_name());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return entPlace;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update EntPlace Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating EntPlace Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public EntPlace updateEntPlaceStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEntPlaceStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long ent_place_id = new Long(record.get("ent_place_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			EntPlace entPlace = oracleManager
					.find(EntPlace.class, ent_place_id);

			entPlace.setDeleted(deleted);
			entPlace.setUpd_user(loggedUserName);

			oracleManager.merge(entPlace);
			oracleManager.flush();

			entPlace = oracleManager.find(EntPlace.class, ent_place_id);
			entPlace.setLoggedUserName(loggedUserName);
			Long ent_type_id = entPlace.getEnt_type_id();
			if (ent_type_id != null) {
				EntType entType = oracleManager
						.find(EntType.class, ent_type_id);
				if (entType != null) {
					entPlace.setEnt_type_geo(entType.getEnt_type_geo());
				}
			}

			Long main_id = entPlace.getMain_id();
			if (main_id != null && main_id.longValue() > 0) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class, main_id);
				if (mainOrg != null) {
					entPlace.setOrg_name(mainOrg.getOrg_name());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return entPlace;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for EntPlace Into Database : ",
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
	 * Adding New EntPoster
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public EntPoster addEntPoster(EntPoster entPoster) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEntPoster.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = entPoster.getLoggedUserName();
			entPoster.setRec_date(recDate);
			entPoster.setRec_user(loggedUserName);

			oracleManager.persist(entPoster);
			oracleManager.flush();

			entPoster = oracleManager.find(EntPoster.class,
					entPoster.getEnt_poster_id());
			entPoster.setLoggedUserName(loggedUserName);

			Long ent_place_id = entPoster.getEnt_place_id();
			if (ent_place_id != null) {
				EntPlace entPlace = oracleManager.find(EntPlace.class,
						ent_place_id);
				if (entPlace != null) {
					entPoster.setEnt_place_geo(entPlace.getEnt_place_geo());
					entPoster.setEnt_type_id(entPlace.getEnt_type_id());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return entPoster;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert EntPoster Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating EntPoster
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public EntPoster updateEntPoster(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEntPoster.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp upd_date = new Timestamp(System.currentTimeMillis());

			Long ent_poster_id = new Long(record.get("ent_poster_id")
					.toString());
			Long ent_place_id = new Long(record.get("ent_place_id").toString());
			String ent_poster_geo = record.get("ent_poster_geo") == null ? null
					: record.get("ent_poster_geo").toString();
			String comment_geo = record.get("comment_geo") == null ? null
					: record.get("comment_geo").toString();
			Date poster_date = record.get("poster_date") == null ? null
					: (Date) record.get("poster_date");
			String poster_time = record.get("poster_time") == null ? null
					: record.get("poster_time").toString();
			String sms_comment = record.get("sms_comment") == null ? null
					: record.get("sms_comment").toString();
			String poster_price_geo = record.get("poster_price_geo") == null ? null
					: record.get("poster_price_geo").toString();
			Long dt_crit = record.get("dt_crit") == null ? null : new Long(
					record.get("dt_crit").toString());
			Long dt_view_crit = record.get("dt_view_crit") == null ? null
					: new Long(record.get("dt_view_crit").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			EntPoster entPoster = oracleManager.find(EntPoster.class,
					ent_poster_id);

			entPoster.setUpd_user(loggedUserName);
			entPoster.setUpd_date(upd_date);
			entPoster.setComment_geo(comment_geo);
			entPoster.setEnt_place_id(ent_place_id);
			entPoster.setEnt_poster_geo(ent_poster_geo);
			entPoster.setSms_comment(sms_comment);
			if (poster_date != null) {
				entPoster.setPoster_date(new Timestamp(poster_date.getTime()));
			}
			entPoster.setPoster_time(poster_time);
			entPoster.setPoster_price_geo(poster_price_geo);
			entPoster.setDt_crit(dt_crit);
			entPoster.setDt_view_crit(dt_view_crit);

			oracleManager.merge(entPoster);
			oracleManager.flush();

			entPoster = oracleManager.find(EntPoster.class, ent_poster_id);
			entPoster.setLoggedUserName(loggedUserName);

			if (ent_place_id != null) {
				EntPlace entPlace = oracleManager.find(EntPlace.class,
						ent_place_id);
				if (entPlace != null) {
					entPoster.setEnt_place_geo(entPlace.getEnt_place_geo());
					entPoster.setEnt_type_id(entPlace.getEnt_type_id());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return entPoster;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update EntPoster Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating EntPoster Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public EntPoster updateEntPosterStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEntPosterStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp upd_date = new Timestamp(System.currentTimeMillis());
			Long ent_poster_id = new Long(record.get("ent_poster_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			EntPoster entPoster = oracleManager.find(EntPoster.class,
					ent_poster_id);

			entPoster.setDeleted(deleted);
			entPoster.setUpd_user(loggedUserName);
			entPoster.setUpd_date(upd_date);

			oracleManager.merge(entPoster);
			oracleManager.flush();

			entPoster = oracleManager.find(EntPoster.class, ent_poster_id);
			entPoster.setLoggedUserName(loggedUserName);

			Long ent_place_id = entPoster.getEnt_place_id();
			if (ent_place_id != null) {
				EntPlace entPlace = oracleManager.find(EntPlace.class,
						ent_place_id);
				if (entPlace != null) {
					entPoster.setEnt_place_geo(entPlace.getEnt_place_geo());
					entPoster.setEnt_type_id(entPlace.getEnt_type_id());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return entPoster;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for EntPoster Into Database : ",
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
	 * Deleting EntPoster Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public EntPoster deleteEntPoster(EntPoster entPoster) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Long ent_poster_id = entPoster.getEnt_poster_id();
			entPoster = oracleManager.find(EntPoster.class, ent_poster_id);
			oracleManager.remove(entPoster);
			EMF.commitTransaction(transaction);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Delete Status for EntPoster Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების წაშლისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
