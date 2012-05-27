package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.black.BlackList;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DataSourceManager;
import com.isomorphic.jpa.EMF;
import com.isomorphic.sql.SQLDataSource;
import com.isomorphic.util.DataTools;

public class BlackListManagerDMI implements QueryConstants {

	private Logger logger = Logger.getLogger(BlackListManagerDMI.class
			.getName());

	@SuppressWarnings("deprecation")
	public BlackList addEditBlackList(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:BlackListManagerDMI.addEditBlackList.";
			Map<?, ?> values = dsRequest.getValues();

			Object oPhones = values.get("blackListPhones");
			String phones = oPhones.toString();
			Long black_list_id = values.containsKey("black_list_id") ? Long
					.parseLong(values.get("black_list_id").toString()) : null;

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);

			BlackList blackList = null;

			if (black_list_id != null)
				blackList = oracleManager.find(BlackList.class, black_list_id);
			if (blackList == null && black_list_id != null)
				throw new Exception("ვერ ვიპოვე შავი სია შესაცვლელად(ID="
						+ black_list_id + ")");
			if (blackList == null)
				blackList = new BlackList();

			DataTools.setProperties(values, blackList);
			if (black_list_id == null) {
				oracleManager.persist(blackList);
				black_list_id = blackList.getBlack_list_id();
			} else
				oracleManager.persist(blackList);
			oracleManager.flush();
			Object obj1 = oracleManager.getDelegate();
			Connection con = (Connection) ((Session) obj1).connection();

			CallableStatement cs = con
					.prepareCall("{? = call persist_black_list_phones(?, ?)}");
			cs.registerOutParameter(1, java.sql.Types.CHAR);
			cs.setString(2, phones);
			cs.setLong(3, black_list_id);

			cs.executeUpdate();
			String delim_phones = cs.getString(1);
			System.out.println(delim_phones);

			String[] oper_numb = delim_phones.split("&");
			String deleteStr = null;
			String insertStr = null;
			if (oper_numb.length > 0) {

				oper_numb[0] = oper_numb[0].trim();

				if (oper_numb[0].length() > 0)
					deleteStr = oper_numb[0];

				if (oper_numb.length > 1) {
					oper_numb[1] = oper_numb[1].trim();
					if (oper_numb[1].length() > 0)
						deleteStr = oper_numb[1];

				}

			}

			operateWithMysql(deleteStr == null ? null : deleteStr.split(","),
					insertStr == null ? null : insertStr.split(","));
			// makeBlockList(blockList, oracleManager);
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";

			return blackList;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert BlockList Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public BlackList deleteBlackList(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:BlackListManagerDMI.deleteBlackList.";
			Map<?, ?> values = dsRequest.getOldValues();

			Long black_list_id = Long.parseLong(values.get("black_list_id")
					.toString());

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = values.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);

			String old_phones = oracleManager
					.createNativeQuery(Q_SELECT_BLACK_LIST_PHONES)
					.setParameter(1, black_list_id).getSingleResult()
					.toString();

			oracleManager.createNativeQuery(Q_DELETE_BLACK_LIST_PHONES)
					.setParameter(1, black_list_id).executeUpdate();

			oracleManager.createNativeQuery(Q_DELETE_BLACK_LIST)
					.setParameter(1, black_list_id).executeUpdate();

			operateWithMysql(old_phones == null ? null : old_phones.split(","),
					null);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";

			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert BlockList Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	private void operateWithMysql(String[] deletedPhones,
			String[] insertedPhones) throws Exception {
		SQLDataSource mySqlDS = (SQLDataSource) DataSourceManager
				.get("MySQLSubsDS");
		Connection mySQLConnection = null;
		try {
			mySQLConnection = mySqlDS.getConnection();
			mySQLConnection.setAutoCommit(false);
			executeStetment(deletedPhones, mySQLConnection,
					Q_MYSQL_DELETE_BLOCK_PHONE, false);
			if (insertedPhones != null && insertedPhones.length > 0) {

				ArrayList<String> list = new ArrayList<String>();
				for (String str : insertedPhones) {
					list.add(str);
					list.add("32" + str);
				}
				executeStetment(list.toArray(new String[] {}), mySQLConnection,
						Q_MYSQL_INSERT_BLOCK_PHONE, true);
			}
			mySQLConnection.commit();
		} catch (Exception e) {
			try {
				mySQLConnection.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			throw e;
		} finally {
			try {
				mySQLConnection.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	protected void executeStetment(String[] phones, Connection mySQLConnection,
			String param, boolean insert) throws Exception {
		PreparedStatement stmt = null;
		if (phones != null && phones.length > 0) {
			try {
				stmt = mySQLConnection.prepareStatement(param);

				for (String str : phones) {
					if (str.length() < 7)
						continue;
					stmt.setString(1, str);
					if (insert) {
						stmt.setInt(2, str.length());
						stmt.setInt(3, str.length());
					}
					stmt.addBatch();
				}
				stmt.executeBatch();

			} catch (Exception e) {
				throw e;
			} finally {
				try {
					stmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

}
