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
	 * Adding New eventCategory
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public EventCategory addEventCategory(EventCategory eventCategory)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEventCategory.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = eventCategory.getLoggedUserName();
			eventCategory.setLoggedUserName(loggedUserName);

			oracleManager.persist(eventCategory);
			oracleManager.flush();

			eventCategory = oracleManager.find(EventCategory.class,
					eventCategory.getEvent_category_id());

			eventCategory.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return eventCategory;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert eventCategory Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating EventCategory
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

			Long event_category_id = new Long(record.get("event_category_id")
					.toString());
			String event_category_name = record.get("event_category_name") == null ? null
					: record.get("event_category_name").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			EventCategory eventCategory = oracleManager.find(
					EventCategory.class, event_category_id);

			eventCategory.setEvent_category_name(event_category_name);
			eventCategory.setLoggedUserName(loggedUserName);

			oracleManager.merge(eventCategory);
			oracleManager.flush();

			eventCategory = oracleManager.find(EventCategory.class,
					event_category_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return eventCategory;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update eventCategory Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating eventCategory Status
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
			String log = "Method:CommonDMI.removeEventCategory.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long event_category_id = new Long(record.get("event_category_id")
					.toString());
			String loggedUserName = record.get("loggedUserName").toString();

			EventCategory eventCategory = oracleManager.find(
					EventCategory.class, event_category_id);
			eventCategory.setLoggedUserName(loggedUserName);

			oracleManager.merge(eventCategory);
			oracleManager.flush();

			eventCategory = oracleManager.find(EventCategory.class,
					event_category_id);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return eventCategory;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for eventCategory Into Database : ",
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
	 * Adding New eventOwner
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public EventOwner addEntPlace(EventOwner eventOwner) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEntPlace.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = eventOwner.getLoggedUserName();
			eventOwner.setLoggedUserName(loggedUserName);

			oracleManager.persist(eventOwner);
			oracleManager.flush();

			eventOwner = oracleManager.find(EventOwner.class,
					eventOwner.getEvent_owner_id());
			eventOwner.setLoggedUserName(loggedUserName);
			Long event_category_id = eventOwner.getEvent_category_id();
			if (event_category_id != null) {
				EventCategory eventCategory = oracleManager.find(
						EventCategory.class, event_category_id);
				if (eventCategory != null) {
					eventOwner.setEvent_category_name(eventCategory
							.getEvent_category_name());
				}
			}

			Long main_id = eventOwner.getMain_id();
			if (main_id != null && main_id.longValue() > 0) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class, main_id);
				if (mainOrg != null) {
					eventOwner.setOrg_name(mainOrg.getOrg_name());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return eventOwner;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert eventOwner Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating eventOwner
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public EventOwner updateEventOwner(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEventOwner.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long event_owner_id = new Long(record.get("event_owner_id")
					.toString());
			Long event_category_id = new Long(record.get("event_category_id")
					.toString());
			Long main_id = record.get("main_id") == null ? null : new Long(
					record.get("main_id").toString());
			Long reservation = record.get("reservation") == null ? null
					: new Long(record.get("reservation").toString());
			String ent_place_geo = record.get("ent_place_geo") == null ? null
					: record.get("ent_place_geo").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			EventOwner entPlace = oracleManager.find(EventOwner.class,
					event_owner_id);

			entPlace.setEvent_category_id(event_category_id);
			entPlace.setLoggedUserName(loggedUserName);
			entPlace.setMain_id(main_id);
			entPlace.setEvent_owner_name(ent_place_geo);
			entPlace.setReservable(reservation);

			oracleManager.merge(entPlace);
			oracleManager.flush();

			entPlace = oracleManager.find(EventOwner.class, event_owner_id);
			entPlace.setLoggedUserName(loggedUserName);
			if (event_category_id != null) {
				EventCategory eventCategory = oracleManager.find(
						EventCategory.class, event_category_id);
				if (eventCategory != null) {
					entPlace.setEvent_category_name(eventCategory
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

			Long event_owner_id = new Long(record.get("event_owner_id")
					.toString());
			String loggedUserName = record.get("loggedUserName").toString();

			EventOwner entPlace = oracleManager.find(EventOwner.class,
					event_owner_id);

			entPlace.setLoggedUserName(loggedUserName);

			oracleManager.merge(entPlace);
			oracleManager.flush();

			entPlace = oracleManager.find(EventOwner.class, event_owner_id);
			entPlace.setLoggedUserName(loggedUserName);
			Long event_category_id = entPlace.getEvent_category_id();
			if (event_category_id != null) {
				EventCategory eventCategory = oracleManager.find(
						EventCategory.class, event_category_id);
				if (eventCategory != null) {
					entPlace.setEvent_category_id(eventCategory
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

			Long event_owner_id = entPoster.getEvent_owner_id();
			if (event_owner_id != null) {
				EventOwner entPlace = oracleManager.find(EventOwner.class,
						event_owner_id);
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
			Long event_owner_id = new Long(record.get("event_owner_id")
					.toString());
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
			entPoster.setEvent_owner_id(event_owner_id);
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

			if (event_owner_id != null) {
				EventOwner entPlace = oracleManager.find(EventOwner.class,
						event_owner_id);
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

			Long event_owner_id = entPoster.getEvent_owner_id();
			if (event_owner_id != null) {
				EventOwner entPlace = oracleManager.find(EventOwner.class,
						event_owner_id);
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
