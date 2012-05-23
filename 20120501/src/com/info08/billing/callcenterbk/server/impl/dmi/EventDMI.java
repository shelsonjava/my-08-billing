package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.event.Event;
import com.info08.billing.callcenterbk.shared.entity.event.EventCategory;
import com.info08.billing.callcenterbk.shared.entity.event.EventOwner;
import com.info08.billing.callcenterbk.shared.entity.org.Organizations;
import com.isomorphic.datasource.DSRequest;
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
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add EventCategory.");

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
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update EventCategory.");

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
	public EventCategory removeEventCategory(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.removeEventCategory.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long event_category_id = new Long(dsRequest.getOldValues()
					.get("event_category_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing EventCategory.");

			EventCategory eventCategory = oracleManager.find(
					EventCategory.class, event_category_id);
			eventCategory.setLoggedUserName(loggedUserName);

			oracleManager.remove(eventCategory);
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
	public EventOwner addEventOwner(EventOwner eventOwner) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEventOwner.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = eventOwner.getLoggedUserName();
			eventOwner.setLoggedUserName(loggedUserName);

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add EventOwner.");

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

			Long organization_id = eventOwner.getOrganization_id();
			if (organization_id != null && organization_id.longValue() > 0) {
				Organizations organizations = oracleManager.find(
						Organizations.class, organization_id);
				if (organizations != null) {
					eventOwner.setOrganization_name(organizations
							.getOrganization_name());
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
			Long organizations_id = record.get("organization_id") == null ? null
					: new Long(record.get("organization_id").toString());
			Long reservable = record.get("reservable") == null ? null
					: new Long(record.get("reservable").toString());
			String event_owner_name = record.get("event_owner_name") == null ? null
					: record.get("event_owner_name").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			EventOwner entPlace = oracleManager.find(EventOwner.class,
					event_owner_id);

			entPlace.setEvent_category_id(event_category_id);
			entPlace.setLoggedUserName(loggedUserName);
			entPlace.setOrganization_id(organizations_id);
			entPlace.setEvent_owner_name(event_owner_name);
			entPlace.setReservable(reservable);

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update EventOwner.");

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

			if (organizations_id != null && organizations_id.longValue() > 0) {
				Organizations organizations = oracleManager.find(
						Organizations.class, organizations_id);
				if (organizations != null) {
					entPlace.setOrganization_name(organizations
							.getOrganization_name());
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
	public EventOwner removeEventOwner(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.removeEventOwner.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long event_owner_id = new Long(dsRequest.getOldValues()
					.get("event_owner_id").toString());

			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();

			EventOwner eventOwner = oracleManager.find(EventOwner.class,
					event_owner_id);

			eventOwner.setLoggedUserName(loggedUserName);
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Remove EventOwner.");

			oracleManager.remove(eventOwner);
			oracleManager.flush();

			EMF.commitTransaction(transaction);
			log += ". Status Remove Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for eventOwner Into Database : ",
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
	public Event addEvent(Event event) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEvent.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = event.getLoggedUserName();
			event.setLoggedUserName(loggedUserName);

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add Event.");

			oracleManager.persist(event);
			oracleManager.flush();

			event = oracleManager.find(Event.class, event.getEvent_id());
			event.setLoggedUserName(loggedUserName);

			Long event_owner_id = event.getEvent_owner_id();
			if (event_owner_id != null) {
				EventOwner entPlace = oracleManager.find(EventOwner.class,
						event_owner_id);
				if (entPlace != null) {
					event.setEvent_owner_name(entPlace.getEvent_owner_name());
					event.setEvent_category_id(entPlace.getEvent_category_id());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return event;
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
	public Event updateEvent(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateEvent.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long event_id = new Long(record.get("event_id").toString());
			Long event_owner_id = new Long(record.get("event_owner_id")
					.toString());
			String event_name = record.get("event_name") == null ? null
					: record.get("event_name").toString();
			String remark = record.get("remark") == null ? null : record.get(
					"remark").toString();
			Date poster_date = record.get("event_date") == null ? null
					: (Date) record.get("event_date");
			String poster_time = record.get("event_time") == null ? null
					: record.get("event_time").toString();
			String sms_comment = record.get("sms_remark") == null ? null
					: record.get("sms_remark").toString();
			String event_price = record.get("event_price") == null ? null
					: record.get("event_price").toString();
			Long dt_crit = record.get("date_criteria") == null ? null
					: new Long(record.get("date_criteria").toString());
			Long date_visibility = record.get("date_visibility") == null ? null
					: new Long(record.get("date_visibility").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			Event entPoster = oracleManager.find(Event.class, event_id);

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Edit Event.");

			entPoster.setLoggedUserName(loggedUserName);

			entPoster.setRemark(remark);
			entPoster.setEvent_owner_id(event_owner_id);
			entPoster.setEvent_name(event_name);
			entPoster.setSms_remark(sms_comment);
			if (poster_date != null) {
				entPoster.setEvent_date(new Timestamp(poster_date.getTime()));
			}
			entPoster.setEvent_time(poster_time);
			entPoster.setEvent_price(event_price);
			entPoster.setDate_criteria(dt_crit);
			entPoster.setDate_visibility(date_visibility);

			oracleManager.merge(entPoster);
			oracleManager.flush();

			entPoster = oracleManager.find(Event.class, event_id);
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
	// @SuppressWarnings("rawtypes")
	// public Event updateEntPosterStatus(Map record) throws Exception {
	// EntityManager oracleManager = null;
	// Object transaction = null;
	// try {
	// String log = "Method:CommonDMI.updateEntPosterStatus.";
	// oracleManager = EMF.getEntityManager();
	// transaction = EMF.getTransaction(oracleManager);
	//
	// Long ent_poster_id = new Long(record.get("ent_poster_id")
	// .toString());
	//
	// String loggedUserName = record.get("loggedUserName").toString();
	//
	// Event entPoster = oracleManager.find(Event.class, ent_poster_id);
	//
	// entPoster.setLoggedUserName(loggedUserName);
	//
	// oracleManager.merge(entPoster);
	// oracleManager.flush();
	//
	// entPoster = oracleManager.find(Event.class, ent_poster_id);
	// entPoster.setLoggedUserName(loggedUserName);
	//
	// Long event_owner_id = entPoster.getEvent_owner_id();
	// if (event_owner_id != null) {
	// EventOwner entPlace = oracleManager.find(EventOwner.class,
	// event_owner_id);
	// if (entPlace != null) {
	// entPoster.setEvent_owner_name(entPlace
	// .getEvent_owner_name());
	// entPoster.setEvent_category_id(entPlace
	// .getEvent_category_id());
	// }
	// }
	//
	// EMF.commitTransaction(transaction);
	// log += ". Status Updating Finished SuccessFully. ";
	// logger.info(log);
	// return entPoster;
	// } catch (Exception e) {
	// EMF.rollbackTransaction(transaction);
	// if (e instanceof CallCenterException) {
	// throw (CallCenterException) e;
	// }
	// logger.error(
	// "Error While Update Status for EntPoster Into Database : ",
	// e);
	// throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
	// + e.toString());
	// } finally {
	// if (oracleManager != null) {
	// EMF.returnEntityManager(oracleManager);
	// }
	// }
	// }

	/**
	 * Deleting EntPoster Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public Event removeEvent(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long event_id = new Long(dsRequest.getOldValues().get("event_id")
					.toString());

			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Detele Event.");

			Event event = oracleManager.find(Event.class, event_id);

			oracleManager.remove(event);
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
