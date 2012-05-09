package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.ServerSingleton;
import com.info08.billing.callcenterbk.shared.entity.Service;
import com.info08.billing.callcenterbk.shared.entity.admin.FixedOperatorPrefixe;
import com.info08.billing.callcenterbk.shared.entity.admin.MobileOperatorPrefixe;
import com.info08.billing.callcenterbk.shared.entity.calendar.CalendarState;
import com.info08.billing.callcenterbk.shared.entity.calendar.ChurchCalendar;
import com.info08.billing.callcenterbk.shared.entity.calendar.ChurchCalendarEvent;
import com.info08.billing.callcenterbk.shared.entity.calendar.SecularCalendar;
import com.info08.billing.callcenterbk.shared.entity.calendar.SecularCalendarEvent;
import com.info08.billing.callcenterbk.shared.entity.main.MainDetail;
import com.info08.billing.callcenterbk.shared.entity.main.MainDetailType;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DataSource;
import com.isomorphic.datasource.DataSourceManager;
import com.isomorphic.jpa.EMF;
import com.isomorphic.sql.SQLDataSource;

public class MiscDMI implements QueryConstants {

	Logger logger = Logger.getLogger(MiscDMI.class.getName());

	/**
	 * Adding New SecularCalendar
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public SecularCalendar addSecularCalendar(SecularCalendar secularCalendar)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addSecularCalendar.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = secularCalendar.getLoggedUserName();
			secularCalendar.setRec_date(recDate);
			secularCalendar.setRec_user(loggedUserName);

			Timestamp calendar_day = secularCalendar.getCalendar_day();
			if (calendar_day != null) {
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(calendar_day.getTime());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				secularCalendar.setCalendar_day(new Timestamp(calendar
						.getTimeInMillis()));
			}

			oracleManager.persist(secularCalendar);
			oracleManager.flush();

			secularCalendar = oracleManager.find(SecularCalendar.class,
					secularCalendar.getCalendar_id());
			secularCalendar.setLoggedUserName(loggedUserName);
			Long event_id = secularCalendar.getCalendar_event_id();
			if (event_id != null) {
				SecularCalendarEvent calendarEvent = oracleManager.find(
						SecularCalendarEvent.class, event_id);
				if (calendarEvent != null) {
					secularCalendar.setEvent(calendarEvent.getEvent());
				}
			}
			Long state_id = secularCalendar.getCalendar_state_id();
			if (state_id != null) {
				CalendarState calendarState = oracleManager.find(
						CalendarState.class, state_id);
				if (calendarState != null) {
					secularCalendar.setState(calendarState.getState());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return secularCalendar;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert SecularCalendar Into Database : ",
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
	 * Updating SecularCalendar
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public SecularCalendar updateSecularCalendar(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateSecularCalendar.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long calendar_id = new Long(record.get("calendar_id").toString());
			Long calendar_state_id = record.get("calendar_state_id") == null ? null
					: new Long(record.get("calendar_state_id").toString());
			Long calendar_event_id = record.get("calendar_event_id") == null ? null
					: new Long(record.get("calendar_event_id").toString());
			String calendar_comment = record.get("calendar_comment") == null ? null
					: record.get("calendar_comment").toString();
			Timestamp calendar_day = record.get("calendar_day") == null ? null
					: new Timestamp(
							((Date) record.get("calendar_day")).getTime());
			String calendar_description = record.get("calendar_description") == null ? null
					: record.get("calendar_description").toString();
			String sun_rise = record.get("sun_rise") == null ? null : record
					.get("sun_rise").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			SecularCalendar secularCalendar = oracleManager.find(
					SecularCalendar.class, calendar_id);
			secularCalendar.setCalendar_comment(calendar_comment);
			secularCalendar.setCalendar_day(calendar_day);
			secularCalendar.setCalendar_description(calendar_description);
			secularCalendar.setCalendar_event_id(calendar_event_id);
			secularCalendar.setCalendar_state_id(calendar_state_id);
			secularCalendar.setSun_rise(sun_rise);
			secularCalendar.setUpd_date(currDate);
			secularCalendar.setUpd_user(loggedUserName);

			if (calendar_day != null) {
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(calendar_day.getTime());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				secularCalendar.setCalendar_day(new Timestamp(calendar
						.getTimeInMillis()));
			}

			oracleManager.merge(secularCalendar);
			oracleManager.flush();

			secularCalendar = oracleManager.find(SecularCalendar.class,
					calendar_id);
			secularCalendar.setLoggedUserName(loggedUserName);
			Long event_id = secularCalendar.getCalendar_event_id();
			if (event_id != null) {
				SecularCalendarEvent calendarEvent = oracleManager.find(
						SecularCalendarEvent.class, event_id);
				if (calendarEvent != null) {
					secularCalendar.setEvent(calendarEvent.getEvent());
				}
			}
			Long state_id = secularCalendar.getCalendar_state_id();
			if (state_id != null) {
				CalendarState calendarState = oracleManager.find(
						CalendarState.class, state_id);
				if (calendarState != null) {
					secularCalendar.setState(calendarState.getState());
				}
			}
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return secularCalendar;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update SecularCalendar Into Database : ",
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
	 * Updating SecularCalendar Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public SecularCalendar updateSecularCalendarStatus(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateSecularCalendarStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long calendar_id = new Long(record.get("calendar_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			SecularCalendar secularCalendar = oracleManager.find(
					SecularCalendar.class, calendar_id);

			secularCalendar.setDeleted(deleted);
			secularCalendar.setUpd_user(loggedUserName);
			secularCalendar.setUpd_date(currDate);

			oracleManager.merge(secularCalendar);
			oracleManager.flush();

			secularCalendar = oracleManager.find(SecularCalendar.class,
					calendar_id);
			secularCalendar.setLoggedUserName(loggedUserName);
			Long event_id = secularCalendar.getCalendar_event_id();
			if (event_id != null) {
				SecularCalendarEvent calendarEvent = oracleManager.find(
						SecularCalendarEvent.class, event_id);
				if (calendarEvent != null) {
					secularCalendar.setEvent(calendarEvent.getEvent());
				}
			}
			Long state_id = secularCalendar.getCalendar_state_id();
			if (state_id != null) {
				CalendarState calendarState = oracleManager.find(
						CalendarState.class, state_id);
				if (calendarState != null) {
					secularCalendar.setState(calendarState.getState());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return secularCalendar;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for SecularCalendar Into Database : ",
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
	 * Adding New ChurchCalendar
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public ChurchCalendar addChurchCalendar(ChurchCalendar churchCalendar)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addSecularCalendar.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = churchCalendar.getLoggedUserName();
			churchCalendar.setRec_date(recDate);
			churchCalendar.setRec_user(loggedUserName);

			Timestamp calendar_day = churchCalendar.getCalendar_day();
			if (calendar_day != null) {
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(calendar_day.getTime());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				churchCalendar.setCalendar_day(new Timestamp(calendar
						.getTimeInMillis()));
			}

			oracleManager.persist(churchCalendar);
			oracleManager.flush();

			churchCalendar = oracleManager.find(ChurchCalendar.class,
					churchCalendar.getCalendar_id());
			churchCalendar.setLoggedUserName(loggedUserName);
			Long event_id = churchCalendar.getCalendar_event_id();
			if (event_id != null) {
				ChurchCalendarEvent churchCalendarEvent = oracleManager.find(
						ChurchCalendarEvent.class, event_id);
				if (churchCalendarEvent != null) {
					churchCalendar.setEvent(churchCalendarEvent.getEvent());
				}
			}
			Long state_id = churchCalendar.getCalendar_state_id();
			if (state_id != null) {
				CalendarState calendarState = oracleManager.find(
						CalendarState.class, state_id);
				if (calendarState != null) {
					churchCalendar.setState(calendarState.getState());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return churchCalendar;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert ChurchCalendar Into Database : ",
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
	 * Updating ChurchCalendar
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ChurchCalendar updateChurchCalendar(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateSecularCalendar.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long calendar_id = new Long(record.get("calendar_id").toString());
			Long calendar_state_id = record.get("calendar_state_id") == null ? null
					: new Long(record.get("calendar_state_id").toString());
			Long calendar_event_id = record.get("calendar_event_id") == null ? null
					: new Long(record.get("calendar_event_id").toString());
			Timestamp calendar_day = record.get("calendar_day") == null ? null
					: new Timestamp(
							((Date) record.get("calendar_day")).getTime());
			String calendar_description = record.get("calendar_description") == null ? null
					: record.get("calendar_description").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			ChurchCalendar churchCalendar = oracleManager.find(
					ChurchCalendar.class, calendar_id);
			churchCalendar.setCalendar_day(calendar_day);
			churchCalendar.setCalendar_description(calendar_description);
			churchCalendar.setCalendar_event_id(calendar_event_id);
			churchCalendar.setCalendar_state_id(calendar_state_id);
			churchCalendar.setUpd_date(currDate);
			churchCalendar.setUpd_user(loggedUserName);

			if (calendar_day != null) {
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(calendar_day.getTime());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				churchCalendar.setCalendar_day(new Timestamp(calendar
						.getTimeInMillis()));
			}

			oracleManager.merge(churchCalendar);
			oracleManager.flush();

			churchCalendar = oracleManager.find(ChurchCalendar.class,
					calendar_id);
			churchCalendar.setLoggedUserName(loggedUserName);
			Long event_id = churchCalendar.getCalendar_event_id();
			if (event_id != null) {
				ChurchCalendarEvent churchCalendarEvent = oracleManager.find(
						ChurchCalendarEvent.class, event_id);
				if (churchCalendarEvent != null) {
					churchCalendar.setEvent(churchCalendarEvent.getEvent());
				}
			}
			Long state_id = churchCalendar.getCalendar_state_id();
			if (state_id != null) {
				CalendarState calendarState = oracleManager.find(
						CalendarState.class, state_id);
				if (calendarState != null) {
					churchCalendar.setState(calendarState.getState());
				}
			}
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return churchCalendar;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update ChurchCalendar Into Database : ",
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
	 * Updating ChurchCalendar Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ChurchCalendar updateChurchCalendarStatus(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateSecularCalendarStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long calendar_id = new Long(record.get("calendar_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			ChurchCalendar churchCalendar = oracleManager.find(
					ChurchCalendar.class, calendar_id);

			churchCalendar.setDeleted(deleted);
			churchCalendar.setUpd_user(loggedUserName);
			churchCalendar.setUpd_date(currDate);

			oracleManager.merge(churchCalendar);
			oracleManager.flush();

			churchCalendar = oracleManager.find(ChurchCalendar.class,
					calendar_id);
			churchCalendar.setLoggedUserName(loggedUserName);
			Long event_id = churchCalendar.getCalendar_event_id();
			if (event_id != null) {
				ChurchCalendarEvent churchCalendarEvent = oracleManager.find(
						ChurchCalendarEvent.class, event_id);
				if (churchCalendarEvent != null) {
					churchCalendar.setEvent(churchCalendarEvent.getEvent());
				}
			}
			Long state_id = churchCalendar.getCalendar_state_id();
			if (state_id != null) {
				CalendarState calendarState = oracleManager.find(
						CalendarState.class, state_id);
				if (calendarState != null) {
					churchCalendar.setState(calendarState.getState());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return churchCalendar;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for ChurchCalendar Into Database : ",
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
	 * Adding New MainDetailType
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public MainDetailType addMainDetailType(MainDetailType mainDetailType)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addMainDetailType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = mainDetailType.getLoggedUserName();
			mainDetailType.setRec_date(recDate);
			mainDetailType.setRec_user(loggedUserName);

			oracleManager.persist(mainDetailType);
			oracleManager.flush();

			mainDetailType = oracleManager.find(MainDetailType.class,
					mainDetailType.getMain_detail_type_id());
			mainDetailType.setLoggedUserName(loggedUserName);

			Long service_id = mainDetailType.getService_id();
			if (service_id != null) {
				Service service = oracleManager.find(Service.class, service_id);
				if (service != null) {
					mainDetailType.setService_name_geo(service
							.getServiceNameGeo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return mainDetailType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert MainDetailType Into Database : ",
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
	 * Updating MainDetailType
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public MainDetailType updateMainDetailType(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateMainDetailType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long main_detail_type_id = new Long(record.get(
					"main_detail_type_id").toString());
			Long criteria_type = record.get("criteria_type") == null ? null
					: new Long(record.get("criteria_type").toString());
			String main_detail_type_name_geo = record
					.get("main_detail_type_name_geo") == null ? null : record
					.get("main_detail_type_name_geo").toString();
			Long service_id = record.get("service_id") == null ? null
					: new Long(record.get("service_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			MainDetailType mainDetailType = oracleManager.find(
					MainDetailType.class, main_detail_type_id);

			mainDetailType.setCriteria_type(criteria_type);
			mainDetailType.setDeleted(0L);
			mainDetailType
					.setMain_detail_type_name_geo(main_detail_type_name_geo);
			mainDetailType.setUpd_date(currDate);
			mainDetailType.setUpd_user(loggedUserName);
			mainDetailType.setService_id(service_id);
			mainDetailType.setVisible_option(0L);
			mainDetailType.setSearcher_zone(0L);
			mainDetailType.setMain_detail_type_name_eng(null);

			oracleManager.merge(mainDetailType);
			oracleManager.flush();

			mainDetailType = oracleManager.find(MainDetailType.class,
					main_detail_type_id);
			mainDetailType.setLoggedUserName(loggedUserName);
			if (service_id != null) {
				Service service = oracleManager.find(Service.class, service_id);
				if (service != null) {
					mainDetailType.setService_name_geo(service
							.getServiceNameGeo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return mainDetailType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update MainDetailType Into Database : ",
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
	 * Updating MainDetailType Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public MainDetailType updateMainDetailTypeStatus(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateMainDetailTypeStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long main_detail_type_id = new Long(record.get(
					"main_detail_type_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			MainDetailType mainDetailType = oracleManager.find(
					MainDetailType.class, main_detail_type_id);

			mainDetailType.setDeleted(deleted);
			mainDetailType.setUpd_user(loggedUserName);
			mainDetailType.setUpd_date(currDate);

			oracleManager.merge(mainDetailType);
			oracleManager.flush();

			mainDetailType = oracleManager.find(MainDetailType.class,
					main_detail_type_id);
			mainDetailType.setLoggedUserName(loggedUserName);

			Long service_id = mainDetailType.getService_id();
			if (service_id != null) {
				Service service = oracleManager.find(Service.class, service_id);
				if (service != null) {
					mainDetailType.setService_name_geo(service
							.getServiceNameGeo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return mainDetailType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for MainDetailType Into Database : ",
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
	 * Adding or Updating MainDetail
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public MainDetail addOrUpdateMainDetail(DSRequest dsRequest)
			throws Exception {
		CallableStatement insertStatement = null;
		PreparedStatement stmt = null;
		Connection connection = null;
		try {

			String log = "Method:CommonDMI.addOrUpdateMainDetail.";
			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			Integer pMain_id = new Integer(
					dsRequest.getFieldValue("main_id") == null ? "-100"
							: dsRequest.getFieldValue("main_id").toString());
			Integer pMainDetailId = new Integer(
					dsRequest.getFieldValue("main_detail_id") == null ? "-100"
							: dsRequest.getFieldValue("main_detail_id")
									.toString());
			Integer pMainDetailTypeId = new Integer(
					dsRequest.getFieldValue("main_detail_type_id") == null ? "-100"
							: dsRequest.getFieldValue("main_detail_type_id")
									.toString());
			String pMainDetailGeo = dsRequest.getFieldValue("main_detail_geo") == null ? null
					: dsRequest.getFieldValue("main_detail_geo").toString();
			String pMainDetailEng = dsRequest.getFieldValue("main_detail_eng") == null ? null
					: dsRequest.getFieldValue("main_detail_eng").toString();
			Integer pDeleted = new Integer(
					dsRequest.getFieldValue("deleted") == null ? "-100"
							: dsRequest.getFieldValue("deleted").toString());
			Integer pMainDetailMasterId = new Integer(
					dsRequest.getFieldValue("main_detail_master_id") == null ? "-100"
							: dsRequest.getFieldValue("main_detail_master_id")
									.toString());
			Integer pOldId = new Integer(
					dsRequest.getFieldValue("old_id") == null ? "-100"
							: dsRequest.getFieldValue("old_id").toString());
			Integer pFieldsOrder = new Integer(
					dsRequest.getFieldValue("fields_order") == null ? "-100"
							: dsRequest.getFieldValue("fields_order")
									.toString());
			String pMainDetailNoteGeo = dsRequest
					.getFieldValue("main_detail_note_geo") == null ? null
					: dsRequest.getFieldValue("main_detail_note_geo")
							.toString();
			String pMainDetailNoteEng = dsRequest
					.getFieldValue("main_detail_note_eng") == null ? null
					: dsRequest.getFieldValue("main_detail_note_eng")
							.toString();
			Integer pServiceId = new Integer(
					dsRequest.getFieldValue("service_id") == null ? "-100"
							: dsRequest.getFieldValue("service_id").toString());

			DataSource ds = DataSourceManager.get("LogSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();
			connection.setAutoCommit(false);

			// save or update main detail
			insertStatement = connection
					.prepareCall("{ call paata.newBillSupport2.saveOrUpdateMainDetail( ?,?,?,?,?,?,?,?,?,?,?,?,?,? ) }");

			insertStatement.setInt(1, pMain_id);
			insertStatement.setInt(2, pMainDetailId);
			insertStatement.setTimestamp(3, currDate);
			insertStatement.setString(4, loggedUserName);
			insertStatement.setInt(5, pMainDetailTypeId);
			insertStatement.setString(6, pMainDetailGeo);
			insertStatement.setString(7, pMainDetailEng);
			insertStatement.setInt(8, pDeleted);
			insertStatement.setInt(9, pMainDetailMasterId);
			insertStatement.setInt(10, pOldId);
			insertStatement.setInt(11, pFieldsOrder);
			insertStatement.setString(12, pMainDetailNoteGeo);
			insertStatement.setString(13, pMainDetailNoteEng);
			insertStatement.setInt(14, pServiceId);

			insertStatement.registerOutParameter(1, Types.INTEGER);
			insertStatement.registerOutParameter(2, Types.INTEGER);

			insertStatement.executeUpdate();
			Integer retMainId = insertStatement.getInt(1);
			Integer retMainDetailId = insertStatement.getInt(2);

			// Fuck This :D
			retMainId++;
			// End of Fuck This :D

			connection.commit();

			MainDetail mainDetail = getMainDetailById(new Long(retMainDetailId));

			log += ". Saving Finished SuccessFully. ";
			logger.info(log);

			return mainDetail;
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex1: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Saving Main Details Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Retrieving Main Detail From DB.
	 * 
	 * @param mainDetailId
	 * @return
	 * @throws Exception
	 */
	private MainDetail getMainDetailById(Long mainDetailId) throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.getMainDetailById.";
			oracleManager = EMF.getEntityManager();
			MainDetail mainDetail = oracleManager.find(MainDetail.class,
					mainDetailId);
			if (mainDetail != null) {
				Long main_det_type_id = mainDetail.getMain_detail_type_id();
				if (main_det_type_id != null) {
					MainDetailType mainDetailType = oracleManager.find(
							MainDetailType.class, main_det_type_id);
					if (mainDetailType != null) {
						mainDetail.setMain_detail_type_name_geo(mainDetailType
								.getMain_detail_type_name_geo());
					}
				}
			}
			log += ". Finding Finished SuccessFully. ";
			logger.info(log);
			return mainDetail;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert MainDetailType Into Database : ",
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
	 * Adding New MobileOperatorPrefix
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public MobileOperatorPrefixe addMobileOperatorPrefix(
			MobileOperatorPrefixe mobileOperatorPrefixe) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addMobileOperatorPrefix.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = mobileOperatorPrefixe.getLoggedUserName();
			mobileOperatorPrefixe.setRec_date(recDate);
			mobileOperatorPrefixe.setRec_user(loggedUserName);
			mobileOperatorPrefixe.setDeleted(0L);

			oracleManager.persist(mobileOperatorPrefixe);
			oracleManager.flush();

			mobileOperatorPrefixe = oracleManager.find(
					MobileOperatorPrefixe.class, mobileOperatorPrefixe.getId());
			mobileOperatorPrefixe.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance().addMobileOperatorPrefix(
					mobileOperatorPrefixe);

			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return mobileOperatorPrefixe;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Insert MobileOperatorPrefixe Into Database : ",
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
	 * Updating MobileOperatorPrefixe
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public MobileOperatorPrefixe updateMobileOperatorPrefix(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateSecularCalendar.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long id = new Long(record.get("id").toString());
			String oper = record.get("oper") == null ? null : record
					.get("oper").toString();
			String prefix = record.get("prefix") == null ? null : record.get(
					"prefix").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			MobileOperatorPrefixe mobileOperatorPrefixe = oracleManager.find(
					MobileOperatorPrefixe.class, id);
			mobileOperatorPrefixe.setOper(oper);
			mobileOperatorPrefixe.setPrefix(prefix);
			mobileOperatorPrefixe.setUpd_date(currDate);
			mobileOperatorPrefixe.setUpd_user(loggedUserName);

			oracleManager.merge(mobileOperatorPrefixe);
			oracleManager.flush();

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance().updateMobileOperatorPrefix(
					mobileOperatorPrefixe);

			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return mobileOperatorPrefixe;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update MobileOperatorPrefixe Into Database : ",
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
	 * Updating MobileOperatorPrefixe Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public MobileOperatorPrefixe updateMobileOperatorPrefixStatus(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateMobileOperatorPrefixStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long id = new Long(record.get("id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			MobileOperatorPrefixe mobileOperatorPrefixe = oracleManager.find(
					MobileOperatorPrefixe.class, id);

			mobileOperatorPrefixe.setDeleted(deleted);
			mobileOperatorPrefixe.setUpd_user(loggedUserName);
			mobileOperatorPrefixe.setUpd_date(currDate);

			oracleManager.merge(mobileOperatorPrefixe);
			oracleManager.flush();

			mobileOperatorPrefixe = oracleManager.find(
					MobileOperatorPrefixe.class, id);

			mobileOperatorPrefixe.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance().updateMobileOperatorPrefix(
					mobileOperatorPrefixe);

			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return mobileOperatorPrefixe;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for MobileOperatorPrefixe Into Database : ",
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
	 * Adding New FixedOperatorPrefixe
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public FixedOperatorPrefixe addFixedOperatorPrefix(
			FixedOperatorPrefixe fixedOperatorPrefixe) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addFixedOperatorPrefix.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = fixedOperatorPrefixe.getLoggedUserName();
			fixedOperatorPrefixe.setRec_date(recDate);
			fixedOperatorPrefixe.setRec_user(loggedUserName);
			fixedOperatorPrefixe.setDeleted(0L);

			oracleManager.persist(fixedOperatorPrefixe);
			oracleManager.flush();

			fixedOperatorPrefixe = oracleManager.find(
					FixedOperatorPrefixe.class, fixedOperatorPrefixe.getId());
			fixedOperatorPrefixe.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance().addFixedOperatorPrefix(
					fixedOperatorPrefixe);

			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return fixedOperatorPrefixe;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Insert FixedOperatorPrefixe Into Database : ",
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
	 * Updating FixedOperatorPrefixe
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public FixedOperatorPrefixe updateFixedOperatorPrefix(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateSecularCalendar.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long id = new Long(record.get("id").toString());
			String prefix = record.get("prefix") == null ? null : record.get(
					"prefix").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			FixedOperatorPrefixe fixedOperatorPrefixe = oracleManager.find(
					FixedOperatorPrefixe.class, id);
			fixedOperatorPrefixe.setPrefix(prefix);
			fixedOperatorPrefixe.setUpd_date(currDate);
			fixedOperatorPrefixe.setUpd_user(loggedUserName);

			oracleManager.merge(fixedOperatorPrefixe);
			oracleManager.flush();

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance().updateFixedOperatorPrefix(
					fixedOperatorPrefixe);

			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return fixedOperatorPrefixe;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update FixedOperatorPrefixe Into Database : ",
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
	 * Updating FixedOperatorPrefixe Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public FixedOperatorPrefixe updateFixedOperatorPrefixStatus(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateFixedOperatorPrefixStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long id = new Long(record.get("id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			FixedOperatorPrefixe fixedOperatorPrefixe = oracleManager.find(
					FixedOperatorPrefixe.class, id);

			fixedOperatorPrefixe.setDeleted(deleted);
			fixedOperatorPrefixe.setUpd_user(loggedUserName);
			fixedOperatorPrefixe.setUpd_date(currDate);

			oracleManager.merge(fixedOperatorPrefixe);
			oracleManager.flush();

			fixedOperatorPrefixe = oracleManager.find(
					FixedOperatorPrefixe.class, id);

			fixedOperatorPrefixe.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance().updateFixedOperatorPrefix(
					fixedOperatorPrefixe);

			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return fixedOperatorPrefixe;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for FixedOperatorPrefixe Into Database : ",
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
	 * Add Log Session Charge Record
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused" })
	public Object addLogSessionCharge(DSRequest dsRequest)
			throws CallCenterException {
		CallableStatement insertStatement = null;
		Connection connection = null;
		try {
			Integer service_id = Integer.parseInt(dsRequest
					.getFieldValue("service_id") == null ? "-1000" : dsRequest
					.getFieldValue("service_id").toString());
			if (service_id.equals(-1000)) {
				throw new CallCenterException(
						"არასწორი სერვისის იდენტიფიკატორი !");
			}
			String session_id = dsRequest.getFieldValue("session_id") == null ? "-1000"
					: dsRequest.getFieldValue("session_id").toString();
			if (session_id.equals("-1000")) {
				throw new CallCenterException(
						"არასწორი სესიის იდენტიფიკატორი !");
			}
			Integer ym = Integer
					.parseInt(dsRequest.getFieldValue("ym") == null ? "-1000"
							: dsRequest.getFieldValue("ym").toString());
			if (ym.equals(-1000)) {
				throw new CallCenterException("არასწორი თარიღი !");
			}
			Integer main_id = Integer.parseInt(dsRequest
					.getFieldValue("main_id") == null ? "-1" : dsRequest
					.getFieldValue("main_id").toString());
			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();

			String log = "Method:MiscDMI.addLogSessionCharge. service_id = "
					+ service_id + ", session_id = " + session_id + ", ym = "
					+ ym + ", main_id = " + main_id;

			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			DataSource ds = DataSourceManager.get("LogSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();
			connection.setAutoCommit(false);

			if (service_id.equals(-3) || service_id.equals(3)) {
				insertStatement = connection
						.prepareCall("{ call paata.insert_orgsession_charge( ?,?,?,? ) }");
				insertStatement.setInt(1, service_id);
				insertStatement.setString(2, session_id);
				insertStatement.setInt(3, ym);
				insertStatement.setInt(4, main_id);
			} else {
				insertStatement = connection
						.prepareCall("{ call paata.insert_session_charge( ?,?,? ) }");
				insertStatement.setInt(1, service_id);
				insertStatement.setString(2, session_id);
				insertStatement.setInt(3, ym);
			}

			insertStatement.executeUpdate();

			log += ". Inserting Log Session Charge Finished Successfully. ";
			logger.info(log);
			connection.commit();
			return "OK";
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex1: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Insert Log Session Charge Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (insertStatement != null) {
					insertStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}
}
