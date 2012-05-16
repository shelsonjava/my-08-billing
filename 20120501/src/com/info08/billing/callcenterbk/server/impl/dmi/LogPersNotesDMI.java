package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.entity.control.LogPersonellNote;
import com.info08.billing.callcenterbk.shared.items.LogPersNoteItem;
import com.isomorphic.datasource.DataSource;
import com.isomorphic.datasource.DataSourceManager;
import com.isomorphic.jpa.EMF;
import com.isomorphic.sql.SQLDataSource;

public class LogPersNotesDMI {

	Logger logger = Logger.getLogger(LogPersNotesDMI.class.getName());

	@SuppressWarnings("rawtypes")
	public LogPersonellNote updatePersNote(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:LogPersNotesDMI.updatePersNote.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long note_id = new Long(record.get("note_id").toString());
			String upd_user = record.get("upd_user").toString();
			LogPersonellNote logPersonellNote = oracleManager.find(
					LogPersonellNote.class, note_id);
			logPersonellNote.setReceived(1L);
			logPersonellNote.setUpd_user(upd_user);
			logPersonellNote.setUpd_date(new Timestamp(System
					.currentTimeMillis()));

			oracleManager.merge(logPersonellNote);
			logPersonellNote = oracleManager.find(LogPersonellNote.class,
					note_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Personel Notes SuccessFully. ";
			logger.info(log);
			return logPersonellNote;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Updating Personel Notes Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public LogPersNoteItem add(LogPersNoteItem record) throws Exception {
		PreparedStatement selectNote = null;
		CallableStatement insertStatement = null;
		PreparedStatement selectStatement = null;
		Connection connection = null;
		try {
			String sessionId = record.getSessionId();
			Integer noteId = record.getNote_id();
			Integer visOpt = record.getVisibilityInt();
			String note = record.getNote();
			Integer particular = record.getParticularInt();
			String loggedUserName = record.getLoggedUserName();

			String log = "Method:LogPersNotesDMI.add. Params : 1. SessionId = "
					+ sessionId + " 2. noteId = " + noteId + ", visOpt = "
					+ visOpt + ", note = " + note + ", loggedUserName = "
					+ loggedUserName;
			if (sessionId == null || sessionId.trim().equals("")) {
				log += ". Result : Invalid SessionId";
				logger.info(log);
				throw new CallCenterException(
						"არასწორი სესიის იდენტიფიკატორი : " + sessionId);
			}
			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();

			connection.setAutoCommit(false);

			selectStatement = connection
					.prepareStatement(QueryConstants.Q_GET_SESSION_BY_ID);
			selectStatement.setString(1, sessionId);
			ResultSet resultSet = selectStatement.executeQuery();
			if (!resultSet.next()) {
				log += ". Result : Invalid Session Id From Database: "
						+ sessionId;
				logger.info(log);
				throw new CallCenterException("არასწორი სესიის იდენტიფიკატორი");
			}
			Integer ym = resultSet.getInt(1);
			String userName = resultSet.getString(2);
			String phone = resultSet.getString(4);
			Timestamp callDate = resultSet.getTimestamp(3);

			insertStatement = connection
					.prepareCall("{ call ccare.newBillSupport2.psaveOrUpdateLogPersNote( ?,?,?,?,?,?,?,?,?,?,? ) }");

			insertStatement.setInt(1, ym);
			insertStatement.setString(2, sessionId);
			insertStatement.setString(3, userName);
			insertStatement.setInt(4, -100);
			insertStatement.setString(5, note);
			insertStatement.setString(6, loggedUserName);
			insertStatement.setTimestamp(7, recDate);
			insertStatement.setInt(8, visOpt);
			insertStatement.setString(9, phone);
			insertStatement.setTimestamp(10, callDate);
			insertStatement.setInt(11, particular);
			insertStatement.registerOutParameter(4, Types.INTEGER);
			insertStatement.executeUpdate();

			Integer retNoteId = insertStatement.getInt(4);

			LogPersNoteItem retItem = getLogPersNoteItem(retNoteId,
					loggedUserName, connection);
			connection.commit();
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return retItem;
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
			logger.error("Error While Insert Personal Note Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (selectNote != null) {
					selectNote.close();
				}
				if (insertStatement != null) {
					insertStatement.close();
				}
				if (selectStatement != null) {
					selectStatement.close();
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

	@SuppressWarnings("rawtypes")
	public LogPersNoteItem update(Map record) throws Exception {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		PreparedStatement selectNote = null;
		try {
			Integer noteId = new Integer(record.get("note_id").toString());
			Integer visibOpt = new Integer(record.get("visibilityInt")
					.toString());
			Integer partic = new Integer(record.get("particularInt").toString());
			String note = record.get("note").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			String log = "Method:LogPersNotesDMI.update. Params : 2. noteId = "
					+ noteId + ", visibOpt = " + visibOpt + ", note = " + note
					+ ", partic = " + partic;

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();

			connection.setAutoCommit(false);

			updateStatement = connection
					.prepareStatement(QueryConstants.Q_UPDATE_PERS_NOTES);
			updateStatement.setString(1, note);
			updateStatement.setInt(2, visibOpt);
			updateStatement.setInt(3, partic);
			updateStatement.setInt(4, noteId);
			updateStatement.executeUpdate();

			log += ". Update Finished Successfully";
			LogPersNoteItem updatedRecord = getLogPersNoteItem(noteId,
					loggedUserName, connection);
			connection.commit();
			logger.info(log);
			return updatedRecord;
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex2: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Personal Note Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების განახლებისას : "
					+ e.toString());
		} finally {
			try {
				if (selectNote != null) {
					selectNote.close();
				}
				if (updateStatement != null) {
					updateStatement.close();
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

	public LogPersNoteItem deleteLogPersNoteItem(LogPersNoteItem record) throws Exception {
		Connection connection = null;
		PreparedStatement deleteNote = null;
		try {
			Integer noteId = record.getNote_id();
			String log = "Method:LogPersNotesDMI.remove. Params : 1. noteId = "
					+ noteId;

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();
			connection.setAutoCommit(false);

			deleteNote = connection
					.prepareStatement(QueryConstants.Q_DELETE_PERS_NOTES);
			deleteNote.setInt(1, noteId);
			deleteNote.executeUpdate();

			connection.commit();

			log += ". Result : Deleting note finished successfully.";
			logger.info(log);
			return null;
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex2: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Delete Personal Note Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემის წაშლისას : "
					+ e.toString());
		} finally {
			try {
				if (deleteNote != null) {
					deleteNote.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(e2.toString());
			}
		}
	}

	private LogPersNoteItem getLogPersNoteItem(Integer noteId,
			String loggedUserName, Connection connection)
			throws CallCenterException {
		PreparedStatement selectNote = null;
		try {
			String log = "Method:LogPersNotesDMI.getLogPersNoteItem. Params : 2. noteId = "
					+ noteId + ", loggedUserName = " + loggedUserName;
			selectNote = connection
					.prepareStatement(QueryConstants.Q_GET_PERS_NOTE_BY_ID_ADV);
			selectNote.setInt(1, noteId);
			ResultSet resultSetNote = selectNote.executeQuery();
			if (!resultSetNote.next()) {
				log += ". Result : Invalid Note From Database: " + noteId;
				logger.info(log);
				throw new CallCenterException(
						"შეცდომა შენიშვნის ჩანაწერის წამოღებისას");
			}
			LogPersNoteItem existingRecord = new LogPersNoteItem();
			existingRecord.setLoggedUserName(loggedUserName);
			existingRecord.setNote(resultSetNote.getString(6));
			existingRecord.setNote_id(noteId);
			existingRecord.setParticularInt(resultSetNote.getInt(11));
			existingRecord.setParticular(resultSetNote.getString(10));
			existingRecord.setPhone(resultSetNote.getString(5));
			existingRecord.setRec_date(resultSetNote.getTimestamp(7));
			existingRecord.setReceiver(resultSetNote.getString(3));
			existingRecord.setSender(resultSetNote.getString(4));
			existingRecord.setSessionId(resultSetNote.getString(2));
			existingRecord.setVisibility(resultSetNote.getString(8));
			existingRecord.setVisibilityInt(resultSetNote.getInt(9));
			log += ". Result : getLogPersNoteItem Finished Successfully.";
			logger.info(log);
			return existingRecord;
		} catch (Exception e) {
			logger.error("Error While Retrieving LogPersNoteItem : ", e);
			throw new CallCenterException(
					"შეცდომა მონაცემების წამოღებისას მონაცემთა ბაზიდან: "
							+ e.toString());
		} finally {
			try {
				if (selectNote != null) {
					selectNote.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
