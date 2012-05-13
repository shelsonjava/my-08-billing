package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.entity.ent.Event;
import com.info08.billing.callcenterbk.shared.entity.ent.EventCategory;
import com.info08.billing.callcenterbk.shared.entity.ent.EventOwner;
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
	public EventCategory addEventCategory(EventCategory entType)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEventCategory.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = entType.getLoggedUserName();
			entType.setLoggedUserName(loggedUserName);

			oracleManager.persist(entType);
			oracleManager.flush();

			entType = oracleManager.find(EventCategory.class,
					entType.getEvent_category_id());

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
	public EventCategory updateEventCategory(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEventCategory.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long ent_type_id = new Long(record.get("ent_type_id").toString());
			String ent_type_geo = record.get("ent_type_geo") == null ? null
					: record.get("ent_type_geo").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			EventCategory entType = oracleManager.find(EventCategory.class,
					ent_type_id);

			entType.setEvent_category_name(ent_type_geo);
			entType.setLoggedUserName(loggedUserName);

			oracleManager.merge(entType);
			oracleManager.flush();

			entType = oracleManager.find(EventCategory.class, ent_type_id);

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
	public EventCategory removeEventCategory(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransportTypeStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long ent_type_id = new Long(record.get("ent_type_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			EventCategory entType = oracleManager.find(EventCategory.class,
					ent_type_id);
			entType.setLoggedUserName(loggedUserName);

			oracleManager.merge(entType);
			oracleManager.flush();

			entType = oracleManager.find(EventCategory.class, ent_type_id);

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
	public EventOwner addEntPlace(EventOwner entPlace) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEntPlace.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = entPlace.getLoggedUserName();
			entPlace.setLoggedUserName(loggedUserName);

			oracleManager.persist(entPlace);
			oracleManager.flush();

			entPlace = oracleManager.find(EventOwner.class,
					entPlace.getEvent_owner_id());
			entPlace.setLoggedUserName(loggedUserName);
			Long ent_type_id = entPlace.getEvent_category_id();
			if (ent_type_id != null) {
				EventCategory entType = oracleManager.find(EventCategory.class,
						ent_type_id);
				if (entType != null) {
					entPlace.setEvent_category_name(entType
							.getEvent_category_name());
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
	public EventOwner updateEntPlace(Map record) throws Exception {
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

			EventOwner entPlace = oracleManager.find(EventOwner.class,
					ent_place_id);

			entPlace.setEvent_category_id(ent_type_id);
			entPlace.setLoggedUserName(loggedUserName);
			entPlace.setMain_id(main_id);
			entPlace.setEvent_owner_name(ent_place_geo);
			entPlace.setReservable(reservation);

			oracleManager.merge(entPlace);
			oracleManager.flush();

			entPlace = oracleManager.find(EventOwner.class, ent_place_id);
			entPlace.setLoggedUserName(loggedUserName);
			if (ent_type_id != null) {
				EventCategory entType = oracleManager.find(EventCategory.class,
						ent_type_id);
				if (entType != null) {
					entPlace.setEvent_category_name(entType
							.getEvent_category_name());
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
	public EventOwner updateEntPlaceStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEntPlaceStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long ent_place_id = new Long(record.get("ent_place_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			EventOwner entPlace = oracleManager.find(EventOwner.class,
					ent_place_id);

			entPlace.setLoggedUserName(loggedUserName);

			oracleManager.merge(entPlace);
			oracleManager.flush();

			entPlace = oracleManager.find(EventOwner.class, ent_place_id);
			entPlace.setLoggedUserName(loggedUserName);
			Long ent_type_id = entPlace.getEvent_category_id();
			if (ent_type_id != null) {
				EventCategory entType = oracleManager.find(EventCategory.class,
						ent_type_id);
				if (entType != null) {
					entPlace.setEvent_category_id(entType
							.getEvent_category_id());
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
	public Event addEntPoster(Event entPoster) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEntPoster.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = entPoster.getLoggedUserName();
			entPoster.setLoggedUserName(loggedUserName);

			oracleManager.persist(entPoster);
			oracleManager.flush();

			entPoster = oracleManager
					.find(Event.class, entPoster.getEvent_id());
			entPoster.setLoggedUserName(loggedUserName);

			Long ent_place_id = entPoster.getEvent_owner_id();
			if (ent_place_id != null) {
				EventOwner entPlace = oracleManager.find(EventOwner.class,
						ent_place_id);
				if (entPlace != null) {
					entPoster.setEvent_owner_name(entPlace
							.getEvent_owner_name());
					entPoster.setEvent_category_id(entPlace
							.getEvent_category_id());
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
	public Event updateEntPoster(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEntPoster.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

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

			Event entPoster = oracleManager.find(Event.class, ent_poster_id);

			entPoster.setLoggedUserName(loggedUserName);

			entPoster.setRemark(comment_geo);
			entPoster.setEvent_owner_id(ent_place_id);
			entPoster.setEvent_name(ent_poster_geo);
			entPoster.setSms_remark(sms_comment);
			if (poster_date != null) {
				entPoster.setEvent_date(new Timestamp(poster_date.getTime()));
			}
			entPoster.setEvent_time(poster_time);
			entPoster.setEvent_owner_name(poster_price_geo);
			entPoster.setDate_criteria(dt_crit);
			entPoster.setDate_visibility(dt_view_crit);

			oracleManager.merge(entPoster);
			oracleManager.flush();

			entPoster = oracleManager.find(Event.class, ent_poster_id);
			entPoster.setLoggedUserName(loggedUserName);

			if (ent_place_id != null) {
				EventOwner entPlace = oracleManager.find(EventOwner.class,
						ent_place_id);
				if (entPlace != null) {
					entPoster.setEvent_owner_name(entPlace
							.getEvent_owner_name());
					entPoster.setEvent_category_id(entPlace
							.getEvent_category_id());
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
	public Event updateEntPosterStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEntPosterStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			
			Long ent_poster_id = new Long(record.get("ent_poster_id")
					.toString());
			
			String loggedUserName = record.get("loggedUserName").toString();

			Event entPoster = oracleManager.find(Event.class, ent_poster_id);

			entPoster.setLoggedUserName(loggedUserName);

			oracleManager.merge(entPoster);
			oracleManager.flush();

			entPoster = oracleManager.find(Event.class, ent_poster_id);
			entPoster.setLoggedUserName(loggedUserName);

			Long ent_place_id = entPoster.getEvent_owner_id();
			if (ent_place_id != null) {
				EventOwner entPlace = oracleManager.find(EventOwner.class,
						ent_place_id);
				if (entPlace != null) {
					entPoster.setEvent_owner_name(entPlace
							.getEvent_owner_name());
					entPoster.setEvent_category_id(entPlace
							.getEvent_category_id());
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
	public Event deleteEntPoster(Event entPoster) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Long ent_poster_id = entPoster.getEvent_id();
			entPoster = oracleManager.find(Event.class, ent_poster_id);
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
