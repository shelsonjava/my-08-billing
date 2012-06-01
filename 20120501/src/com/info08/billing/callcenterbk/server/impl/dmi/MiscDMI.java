package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
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
import com.info08.billing.callcenterbk.shared.entity.Country;
import com.info08.billing.callcenterbk.shared.entity.OperatorBreaks;
import com.info08.billing.callcenterbk.shared.entity.Service;
import com.info08.billing.callcenterbk.shared.entity.Users;
import com.info08.billing.callcenterbk.shared.entity.admin.CountryIndexes;
import com.info08.billing.callcenterbk.shared.entity.admin.GSMIndexes;
import com.info08.billing.callcenterbk.shared.entity.admin.LandlineIndexes;
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
import com.isomorphic.util.DataTools;

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
		String s = "setСycle_descr";
		for (char c : s.toCharArray()) {
			System.out.println((int) c + " " + c);
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
			Integer pOrganization_id = new Integer(
					dsRequest.getFieldValue("organization_id") == null ? "-100"
							: dsRequest.getFieldValue("organization_id")
									.toString());
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

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();
			connection.setAutoCommit(false);

			// save or update main detail
			insertStatement = connection
					.prepareCall("{ call ccare.newBillSupport2.saveOrUpdateMainDetail( ?,?,?,?,?,?,?,?,?,?,?,?,?,? ) }");

			insertStatement.setInt(1, pOrganization_id);
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
	public GSMIndexes addGSMIndexes(GSMIndexes gsmIndex) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addGSMIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = gsmIndex.getLoggedUserName();

			oracleManager.persist(gsmIndex);
			oracleManager.flush();

			gsmIndex = oracleManager.find(GSMIndexes.class,
					gsmIndex.getGsm_index_id());
			gsmIndex.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance().addGSMIndexes(gsmIndex);

			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return gsmIndex;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert GSMIndexes Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating GSMIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public GSMIndexes updateGSMIndexes(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateGSMIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long gsm_index_id = new Long(record.get("gsm_index_id").toString());
			String gsm_company = record.get("gsm_company") == null ? null
					: record.get("gsm_company").toString();
			String gsm_index = record.get("gsm_index") == null ? null : record
					.get("gsm_index").toString();

			GSMIndexes gsmIndex = oracleManager.find(GSMIndexes.class,
					gsm_index_id);
			gsmIndex.setGsm_company(gsm_company);
			gsmIndex.setGsm_index(gsm_index);

			oracleManager.merge(gsmIndex);
			oracleManager.flush();

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance().updateGSMIndexes(gsmIndex);

			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return gsmIndex;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update GSMIndexes Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Deleting GSMIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public GSMIndexes deleteGSMIndexes(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.deleteGSMIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long gsm_index_id = new Long(record.get("gsm_index_id").toString());

			GSMIndexes gsmIndex = oracleManager.find(GSMIndexes.class,
					gsm_index_id);

			oracleManager.remove(gsmIndex);
			oracleManager.flush();

			EMF.commitTransaction(transaction);

			log += ". Deleting Finished SuccessFully. ";
			logger.info(log);
			return gsmIndex;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for GSMIndexes Into Database : ",
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
	 * Adding New CoutryIndex
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public CountryIndexes addCountryIndexes(CountryIndexes countryIndexes)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addCountryIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = countryIndexes.getLoggedUserName();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add CountryIndexes.");

			oracleManager.persist(countryIndexes);

			CountryIndexes retItem = oracleManager.find(CountryIndexes.class,
					countryIndexes.getCountry_index_id());
			Country country = oracleManager.find(Country.class,
					retItem.getCountry_id());
			retItem.setCountry_name(country.getCountry_name());
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert CountryIndexes Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Updating CountryIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public CountryIndexes updateCountryIndexes(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateCountryIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long country_index_id = new Long(record.get("country_index_id")
					.toString());
			Long country_id = new Long(record.get("country_id").toString());

			String country_index_value = record.get("country_index_value") == null ? null
					: record.get("country_index_value").toString();
			String country_index_remark_geo = record
					.get("country_index_remark_geo") == null ? null : record
					.get("country_index_remark_geo").toString();
			String country_index_remark_eng = record
					.get("country_index_remark_eng") == null ? null : record
					.get("country_index_remark_eng").toString();

			String loggedUserName = record.get("loggedUserName").toString();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update StreetKind.");

			CountryIndexes countryIndexes = oracleManager.find(
					CountryIndexes.class, country_index_id);

			countryIndexes.setCountry_index_id(country_index_id);
			countryIndexes.setCountry_id(country_id);
			countryIndexes.setCountry_index_value(country_index_value);
			countryIndexes
					.setCountry_index_remark_geo(country_index_remark_geo);
			countryIndexes
					.setCountry_index_remark_eng(country_index_remark_eng);
			countryIndexes.setLoggedUserName(loggedUserName);

			oracleManager.merge(countryIndexes);
			oracleManager.flush();

			countryIndexes = oracleManager.find(CountryIndexes.class,
					country_index_id);

			Country country = oracleManager.find(Country.class,
					countryIndexes.getCountry_id());
			countryIndexes.setCountry_name(country.getCountry_name());

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return countryIndexes;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update CountryIndexes Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Delete CountryIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public CountryIndexes deleteCountryIndexes(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.deleteCountryIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long country_index_id = new Long(dsRequest.getOldValues()
					.get("country_index_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing CountryIndexes.");

			CountryIndexes countryIndexes = oracleManager.find(
					CountryIndexes.class, country_index_id);
			countryIndexes.setLoggedUserName(loggedUserName);

			oracleManager.remove(countryIndexes);
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
			logger.error("Error While Delete CountryIndexes From Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Adding New LandlineIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public LandlineIndexes addLandlineIndexes(
			LandlineIndexes landlineIndexesParam) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addLandlineIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			oracleManager.persist(landlineIndexesParam);
			oracleManager.flush();

			landlineIndexesParam = oracleManager.find(LandlineIndexes.class,
					landlineIndexesParam.getLandline_id());

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance().addLandlineIndexes(
					landlineIndexesParam);

			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return landlineIndexesParam;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert LandlineIndexes Into Database : ",
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
	 * Updating LandlineIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public LandlineIndexes updateLandlineIndexes(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateLandlineIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long landline_id = new Long(record.get("landline_id").toString());
			String landline_index = record.get("landline_index") == null ? null
					: record.get("landline_index").toString();

			LandlineIndexes landlineIndex = oracleManager.find(
					LandlineIndexes.class, landline_id);
			landlineIndex.setLandline_index(landline_index);

			oracleManager.merge(landlineIndex);
			oracleManager.flush();

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance().updateLandlineIndexes(landlineIndex);

			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return landlineIndex;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update LandlineIndexes Into Database : ",
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
	 * Delete LandlineIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public LandlineIndexes deleteLandlineIndexes(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.deleteLandlineIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long landline_id = new Long(record.get("landline_id").toString());

			LandlineIndexes landlineIndexes = oracleManager.find(
					LandlineIndexes.class, landline_id);

			oracleManager.remove(landlineIndexes);
			oracleManager.flush();

			EMF.commitTransaction(transaction);

			ServerSingleton.getInstance()
					.updateLandlineIndexes(landlineIndexes);

			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Delete LandlineIndexes From Database : ",
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
			Integer organization_id = Integer.parseInt(dsRequest
					.getFieldValue("organization_id") == null ? "-1"
					: dsRequest.getFieldValue("organization_id").toString());
			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();

			String log = "Method:MiscDMI.addLogSessionCharge. service_id = "
					+ service_id + ", session_id = " + session_id + ", ym = "
					+ ym + ", organization_id = " + organization_id;

			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();
			connection.setAutoCommit(false);

			if (service_id.equals(-3) || service_id.equals(3)) {
				insertStatement = connection
						.prepareCall("{ call ccare.insert_orgsession_charge( ?,?,?,? ) }");
				insertStatement.setInt(1, service_id);
				insertStatement.setString(2, session_id);
				insertStatement.setInt(3, ym);
				insertStatement.setInt(4, organization_id);
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

	/**
	 * Adding OperatorBreak
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public OperatorBreaks addOperatorBreak(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addEventCategory.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Map<?, ?> map = dsRequest.getValues();
			OperatorBreaks operatorBreaks = new OperatorBreaks();
			DataTools.setProperties(map, operatorBreaks);

			String loggedUserName = operatorBreaks.getLoggedUserName();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add OperatorBreak.");

			oracleManager.persist(operatorBreaks);
			oracleManager.flush();

			operatorBreaks = oracleManager.find(OperatorBreaks.class,
					operatorBreaks.getOperator_break_id());

			operatorBreaks.setLoggedUserName(loggedUserName);
			Users users = oracleManager.find(Users.class,
					operatorBreaks.getUser_id());

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

			operatorBreaks.setUser_firstname(users.getUser_firstname());
			operatorBreaks.setUser_lastname(users.getUser_lastname());
			operatorBreaks.setUser_name(users.getUser_name());
			operatorBreaks.setBreak_date_text(dateFormat.format(operatorBreaks
					.getBreak_date()));

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return operatorBreaks;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert operatorBreaks Into Database : ",
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
	 * Updating OperatorBreaks
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public OperatorBreaks updateOperatorBreak(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateOperatorBreak.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Map<?, ?> map = dsRequest.getValues();
			Long operator_break_id = map.containsKey("operator_break_id") ? new Long(
					map.get("operator_break_id").toString()) : null;

			if (operator_break_id == null) {
				throw new CallCenterException("მომხმარებელი ვერ მოიებნა!");
			}

			String loggedUserName = map.containsKey("loggedUserName") ? map
					.get("loggedUserName").toString() : null;

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update EventCategory.");

			OperatorBreaks operatorBreak = oracleManager.find(
					OperatorBreaks.class, operator_break_id);

			DataTools.setProperties(map, operatorBreak);

			oracleManager.merge(operatorBreak);
			oracleManager.flush();

			operatorBreak = oracleManager.find(OperatorBreaks.class,
					operator_break_id);

			Users users = oracleManager.find(Users.class,
					operatorBreak.getUser_id());

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

			operatorBreak.setUser_firstname(users.getUser_firstname());
			operatorBreak.setUser_lastname(users.getUser_lastname());
			operatorBreak.setUser_name(users.getUser_name());
			operatorBreak.setBreak_date_text(dateFormat.format(operatorBreak
					.getBreak_date()));

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return operatorBreak;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update operatorBreak Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Delete operatorBreaks
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public OperatorBreaks deleteOperatorBreak(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.OperatorBreaks.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long operator_break_id = new Long(dsRequest.getOldValues()
					.get("operator_break_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing operatorBreaks.");

			OperatorBreaks operatorBreaks = oracleManager.find(
					OperatorBreaks.class, operator_break_id);
			operatorBreaks.setLoggedUserName(loggedUserName);

			oracleManager.remove(operatorBreaks);
			oracleManager.flush();

			EMF.commitTransaction(transaction);
			log += ". Delete Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Delete OperatorBreaks : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public Map<?, ?> updatePartniorNumbers(DSRequest req) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:MiscDMI.updatePartniorNumbers.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Map<?, ?> map = req.getValues();
			Long phone_number = new Long(map.get("phone_number").toString());
			Object sSubscriber_proceeded = map.get("subscriber_proceeded");
			// Object organization_proceeded =
			// map.get("organization_proceeded");

			String loggedUserName = map.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, log);

			if (sSubscriber_proceeded != null) {
				Long subscriber_proceeded = new Long(
						sSubscriber_proceeded.toString());
				oracleManager
						.createNativeQuery(
								"update PARTNIOR_TABLE set subscriber_proceeded=? where phone_number=?")
						.setParameter(1, subscriber_proceeded)
						.setParameter(2, phone_number).executeUpdate();
			}

			// if (organization_proceeded != null) {
			// oracleManager
			// .createNativeQuery(
			// "update PARTNIOR_TABLE set subscriber_proceeded=? where phone_number=?")
			// .setParameter(1, subscriber_proceeded)
			// .setParameter(2, phone_number).executeUpdate();
			// }

			EMF.commitTransaction(transaction);
			log += ". updatePartniorNumbers Finished SuccessFully. ";
			logger.info(log);
			map = DMIUtils.findRecordById("PartniorNumbersDS", "customSearch",
					phone_number, "phone_number");
			return map;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Delete OperatorBreaks : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}

	}

	public Map<?, ?> deletePartniorNumbers(DSRequest req) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:MiscDMI.deletePartniorNumbers.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Map<?, ?> map = req.getOldValues();
			Long phone_number = new Long(map.get("phone_number").toString());

			String loggedUserName = map.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, log);

			oracleManager
					.createNativeQuery(
							"delete from PARTNIOR_TABLE where phone_number=?")
					.setParameter(1, phone_number).executeUpdate();

			// if (organization_proceeded != null) {
			// oracleManager
			// .createNativeQuery(
			// "update PARTNIOR_TABLE set subscriber_proceeded=? where phone_number=?")
			// .setParameter(1, subscriber_proceeded)
			// .setParameter(2, phone_number).executeUpdate();
			// }

			EMF.commitTransaction(transaction);
			log += ". updatePartniorNumbers Finished SuccessFully. ";
			logger.info(log);

			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Delete OperatorBreaks : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}

	}

}
