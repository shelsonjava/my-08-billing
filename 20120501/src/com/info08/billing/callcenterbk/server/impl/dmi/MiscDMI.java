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
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.server.common.ServerSingleton;
import com.info08.billing.callcenterbk.shared.entity.Service;
import com.info08.billing.callcenterbk.shared.entity.admin.FixedOperatorPrefixe;
import com.info08.billing.callcenterbk.shared.entity.admin.MobileOperatorPrefixe;
import com.info08.billing.callcenterbk.shared.entity.facts.FactStatus;
import com.info08.billing.callcenterbk.shared.entity.facts.FactType;
import com.info08.billing.callcenterbk.shared.entity.facts.Facts;
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
	public Facts addFact(Facts fact) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:MiscDMI.addFact.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = fact.getLoggedUserName();
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);

			Timestamp calendar_day = fact.getFact_date();
			if (calendar_day != null) {
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(calendar_day.getTime());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				fact.setFact_date(new Timestamp(calendar.getTimeInMillis()));
			}

			oracleManager.persist(fact);
			oracleManager.flush();

			fact.setLoggedUserName(loggedUserName);
			fact = refetchFact(fact, oracleManager);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return fact;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert Facts Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	private Facts refetchFact(Facts fact, EntityManager oracleManager) {
		fact = oracleManager.find(Facts.class, fact.getFact_id());

		Long event_id = fact.getFact_type_id();
		if (event_id != null) {
			FactType calendarEvent = oracleManager.find(FactType.class,
					event_id);
			if (calendarEvent != null) {
				fact.setFact_type_name(calendarEvent.getFact_type_name());
				fact.setFacts_descriptor_id(calendarEvent
						.getFacts_descriptor_id());
			}
		}
		Long state_id = fact.getFact_status_id();
		if (state_id != null) {
			FactStatus calendarState = oracleManager.find(FactStatus.class,
					state_id);
			if (calendarState != null) {
				fact.setFact_status_name(calendarState.getFact_status_name());
			}
		}
		return fact;
	}

	/**
	 * Updating SecularCalendar
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Facts updateFact(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateFact.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);

			Long calendar_id = new Long(record.get("fact_id").toString());
			Long calendar_state_id = record.get("fact_status_id") == null ? null
					: new Long(record.get("fact_status_id").toString());
			Long calendar_event_id = record.get("fact_type_id") == null ? null
					: new Long(record.get("fact_type_id").toString());
			String calendar_comment = record.get("additional_comment") == null ? null
					: record.get("additional_comment").toString();
			Timestamp calendar_day = record.get("fact_date") == null ? null
					: new Timestamp(((Date) record.get("fact_date")).getTime());
			String calendar_description = record.get("remark") == null ? null
					: record.get("remark").toString();
			String sun_rise = record.get("sunup") == null ? null : record.get(
					"sunup").toString();

			Facts fact = oracleManager.find(Facts.class, calendar_id);
			fact.setAdditional_comment(calendar_comment);
			fact.setFact_date(calendar_day);
			fact.setRemark(calendar_description);
			fact.setFact_type_id(calendar_event_id);
			fact.setFact_status_id(calendar_state_id);
			fact.setSunup(sun_rise);
			fact.setLoggedUserName(loggedUserName);

			if (calendar_day != null) {
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(calendar_day.getTime());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				fact.setFact_date(new Timestamp(calendar.getTimeInMillis()));
			}

			oracleManager.merge(fact);
			oracleManager.flush();
			fact = refetchFact(fact, oracleManager);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return fact;
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
public static void main(String[] args) {
	String s="setСycle_descr";
	for (char c : s.toCharArray()) {
		System.out.println((int)c+" "+c);
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
	public Facts removeFacts(DSRequest req) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateSecularCalendarStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Map record = req.getOldValues();
			Long fact_id = new Long(record.get("fact_id").toString());

			String loggedUserName = record.get("loggedUserName").toString();
			RCNGenerator.getInstance().initRcn(oracleManager, currDate,
					loggedUserName, log);
			Facts fact = oracleManager.find(Facts.class, fact_id);
			oracleManager.remove(fact);
			oracleManager.flush();

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return null;
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
					.prepareCall("{ call ccare.newBillSupport2.saveOrUpdateMainDetail( ?,?,?,?,?,?,?,?,?,?,?,?,?,? ) }");

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
						.prepareCall("{ call ccare.insert_orgsession_charge( ?,?,?,? ) }");
				insertStatement.setInt(1, service_id);
				insertStatement.setString(2, session_id);
				insertStatement.setInt(3, ym);
				insertStatement.setInt(4, main_id);
			} else {
				insertStatement = connection
						.prepareCall("{ call ccare.insert_session_charge( ?,?,? ) }");
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
