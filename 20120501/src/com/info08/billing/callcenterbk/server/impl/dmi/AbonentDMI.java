package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.items.Abonent;
import com.info08.billing.callcenterbk.shared.items.OrgInfoByPhone;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DataSource;
import com.isomorphic.datasource.DataSourceManager;
import com.isomorphic.jpa.EMF;
import com.isomorphic.sql.SQLDataSource;

public class AbonentDMI implements QueryConstants {

	Logger logger = Logger.getLogger(AbonentDMI.class.getName());

	/**
	 * Save Or Update Abonent
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public Abonent saveOrUpdateAbonent(DSRequest dsRequest) throws Exception {
		CallableStatement insertStatement = null;
		PreparedStatement stmt = null;
		Connection connection = null;
		try {
			Integer firstname_id = Integer.parseInt(dsRequest.getFieldValue(
					"firstname_id").toString());
			Integer lastname_id = Integer.parseInt(dsRequest.getFieldValue(
					"lastname_id").toString());
			Integer city_id = Integer.parseInt(dsRequest.getFieldValue(
					"city_id").toString());
			Integer street_id = Integer.parseInt(dsRequest.getFieldValue(
					"street_id").toString());
			Integer city_region_id = dsRequest.getFieldValue("city_region_id") == null ? null
					: Integer.parseInt(dsRequest
							.getFieldValue("city_region_id").toString());
			Integer address_hide = Integer.parseInt(dsRequest.getFieldValue(
					"address_hide").toString());
			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();

			Object oAddr_number = dsRequest.getFieldValue("addr_number");
			Object oAddr_block = dsRequest.getFieldValue("addr_block");
			Object oAddr_appt = dsRequest.getFieldValue("addr_appt");
			Object oAddr_descr = dsRequest.getFieldValue("addr_descr");

			String addr_number = oAddr_number == null ? null : oAddr_number
					.toString();
			String addr_block = oAddr_block == null ? null : oAddr_block
					.toString();
			String addr_appt = oAddr_appt == null ? null : oAddr_appt
					.toString();
			String addr_descr = oAddr_descr == null ? null : oAddr_descr
					.toString();

			Object oMain_Id = dsRequest.getFieldValue("main_id");
			Integer main_id = -100;
			if (oMain_Id != null) {
				main_id = Integer.parseInt(oMain_Id.toString());
			}
			Object oAddress_id = dsRequest.getFieldValue("address_id");
			Integer address_id = -100;
			if (oAddress_id != null) {
				address_id = Integer.parseInt(oAddress_id.toString());
			}
			Object oAbonent_id = dsRequest.getFieldValue("abonent_id");
			Integer abonent_id = -100;
			if (oAbonent_id != null) {
				abonent_id = Integer.parseInt(oAbonent_id.toString());
			}
			String log = "Method:AbonentDMI.saveOrUpdateAbonent. Abonent = ";

			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			DataSource ds = DataSourceManager.get("LogSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();
			connection.setAutoCommit(false);

			// insert abonent
			insertStatement = connection
					.prepareCall("{ call ccare.newBillSupport2.saveOrUpdateAbonent( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) }");
			insertStatement.setInt(1, main_id);
			insertStatement.setInt(2, address_id);
			insertStatement.setInt(3, abonent_id);
			insertStatement.setTimestamp(4, recDate);
			insertStatement.setString(5, loggedUserName);
			insertStatement.setInt(6, city_id);
			insertStatement.setInt(7, street_id);
			insertStatement.setInt(8, address_hide);
			if (city_region_id != null) {
				insertStatement.setInt(9, city_region_id);
			} else {
				insertStatement.setNull(9, Types.INTEGER);
			}
			insertStatement.setString(10, addr_block);
			insertStatement.setString(11, addr_appt);
			insertStatement.setString(12, addr_descr);
			insertStatement.setString(13, addr_number);
			insertStatement.setInt(14, firstname_id);
			insertStatement.setInt(15, lastname_id);

			insertStatement.registerOutParameter(1, Types.INTEGER);
			insertStatement.registerOutParameter(2, Types.INTEGER);
			insertStatement.registerOutParameter(3, Types.INTEGER);

			insertStatement.executeUpdate();
			Integer retMainId = insertStatement.getInt(1);
			Integer retAddressId = insertStatement.getInt(2);
			Integer retAbonentId = insertStatement.getInt(3);

			stmt = connection.prepareStatement(Q_DELETE_PHONES_BY_ABONENT);
			stmt.setInt(1, retAbonentId);
			stmt.executeUpdate();

			Object phonesObject = dsRequest.getFieldValue("listPhones");
			if (phonesObject != null) {

				Integer phone_id = -100;

				Map phones = (Map) phonesObject;
				if (phones != null && !phones.isEmpty()) {
					Set keys = phones.keySet();
					for (Object object : keys) {
						String phone = object.toString();
						Map phoneProps = (Map) phones.get(phone);
						if (phoneProps == null || phoneProps.isEmpty()) {
							continue;
						}
						Integer abonent_hide = Integer.parseInt(phoneProps.get(
								"is_hide").toString());
						Integer phone_parallel = Integer.parseInt(phoneProps
								.get("is_parallel").toString());
						Integer phone_status_id = Integer.parseInt(phoneProps
								.get("phone_status_id").toString());
						Integer phone_state_id = Integer.parseInt(phoneProps
								.get("phone_state_id").toString());
						Integer phone_type_id = Integer.parseInt(phoneProps
								.get("phone_type_id").toString());

						insertStatement = connection
								.prepareCall("{ call ccare.newBillSupport2.saveOrUpdateAbonentPhones( ?,?,?,?,?,?,?,?,?,? ) }");
						insertStatement.setInt(1, phone_id);
						insertStatement.setInt(2, retAbonentId);
						insertStatement.setString(3, phone);
						insertStatement.setInt(4, phone_state_id);
						insertStatement.setTimestamp(5, recDate);
						insertStatement.setString(6, loggedUserName);
						insertStatement.setInt(7, phone_type_id);
						insertStatement.setInt(8, phone_status_id);
						insertStatement.setInt(9, abonent_hide);
						insertStatement.setInt(10, phone_parallel);

						insertStatement.registerOutParameter(1, Types.INTEGER);

						insertStatement.executeUpdate();

						Integer retPhoneId = insertStatement.getInt(1);
					}
				}
			}
			log += ". Inserting Subscriber Finished Successfully. ";
			connection.commit();
			return getAbonent(retAbonentId, loggedUserName, connection);
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
			logger.error("Error While Insert Abonent Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (insertStatement != null) {
					insertStatement.close();
				}
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

	public Abonent setAbonentStatusToDelete(DSRequest dsRequest)
			throws CallCenterException {
		PreparedStatement stmt = null;
		Connection connection = null;
		try {
			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			Object oMain_Id = dsRequest.getFieldValue("main_id");
			Integer main_id = -100;
			if (oMain_Id != null) {
				main_id = Integer.parseInt(oMain_Id.toString());
			}
			Object oAddress_id = dsRequest.getFieldValue("address_id");
			Integer address_id = -100;
			if (oAddress_id != null) {
				address_id = Integer.parseInt(oAddress_id.toString());
			}
			Object oAbonent_id = dsRequest.getFieldValue("abonent_id");
			Integer abonent_id = -100;
			if (oAbonent_id != null) {
				abonent_id = Integer.parseInt(oAbonent_id.toString());
			}

			Integer deleted = new Integer(dsRequest.getFieldValue("deleted")
					.toString());

			DataSource ds = DataSourceManager.get("LogSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();
			connection.setAutoCommit(false);
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			stmt = connection.prepareStatement(Q_UPDATE_ABONENT);
			stmt.setInt(1, deleted);
			stmt.setString(2, loggedUserName);
			stmt.setTimestamp(3, recDate);
			stmt.setInt(4, abonent_id);
			stmt.executeUpdate();

			stmt = connection.prepareStatement(Q_UPDATE_MAIN_SERVICE);
			stmt.setInt(1, deleted);
			stmt.setString(2, loggedUserName);
			stmt.setTimestamp(3, recDate);
			stmt.setInt(4, main_id);
			stmt.executeUpdate();

			stmt = connection.prepareStatement(Q_UPDATE_MAIN_ADDRESS);
			stmt.setInt(1, deleted);
			stmt.setString(2, loggedUserName);
			stmt.setTimestamp(3, recDate);
			stmt.setInt(4, address_id);
			stmt.setInt(5, main_id);
			stmt.executeUpdate();

			// stmt = connection.prepareStatement(Q_UPDATE_PHONES);
			// stmt.setInt(1, deleted);
			// stmt.setString(2, loggedUserName);
			// stmt.setInt(3, abonent_id);
			// stmt.executeUpdate();
			connection.commit();

			return getAbonent(abonent_id, loggedUserName, connection);
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
			logger.error("Error While Update Abonent Into Database : ", e);
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

	@SuppressWarnings("rawtypes")
	private Abonent getAbonent(Integer abonentId, String loggedUserName,
			Connection connection) throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			String log = "Method:AbonentDMI.getAbonent. Params : 1. abonentId = "
					+ abonentId + ", 2. loggedUserName = " + loggedUserName;
			oracleManager = EMF.getEntityManager();
			List resultList = oracleManager
					.createNativeQuery(Q_GET_ABONENT_BY_ID)
					.setParameter(1, abonentId).getResultList();

			if (resultList.isEmpty()) {
				return null;
			}

			Object columns[] = (Object[]) resultList.get(0);

			Abonent existingRecord = new Abonent();
			existingRecord.setLoggedUserName(loggedUserName);
			existingRecord.setAbonent_id(columns[0] == null ? null
					: new Integer(columns[0].toString()));
			existingRecord.setMain_id(columns[1] == null ? null : new Integer(
					columns[1].toString()));
			existingRecord.setAddress_id(columns[2] == null ? null
					: new Integer(columns[2].toString()));
			existingRecord.setPhone_id(columns[3] == null ? null : new Integer(
					columns[3].toString()));
			existingRecord.setFirstname(columns[4] == null ? null : columns[4]
					.toString());
			existingRecord.setLastname(columns[5] == null ? null : columns[5]
					.toString());
			existingRecord.setPhone(columns[6] == null ? null : columns[6]
					.toString());
			existingRecord.setPhone_state(columns[7] == null ? null
					: columns[7].toString());
			existingRecord.setPhone_state_id(columns[8] == null ? null
					: new Integer(columns[8].toString()));
			existingRecord.setUpd_date(columns[9] == null ? null
					: ((Timestamp) columns[9]));
			existingRecord.setCity(columns[10] == null ? null : columns[10]
					.toString());
			existingRecord.setStreet(columns[11] == null ? null : columns[11]
					.toString());
			existingRecord.setUpd_user(columns[12] == null ? null : columns[12]
					.toString());
			existingRecord.setFirstname_id(columns[13] == null ? null
					: new Integer(columns[13].toString()));
			existingRecord.setLastname_id(columns[14] == null ? null
					: new Integer(columns[14].toString()));
			existingRecord.setAbonent_hide(columns[15] == null ? null
					: new Integer(columns[15].toString()));
			existingRecord.setPhone_parallel(columns[16] == null ? null
					: new Integer(columns[16].toString()));
			existingRecord.setPhone_status_id(columns[17] == null ? null
					: new Integer(columns[17].toString()));
			existingRecord.setPhone_type_id(columns[18] == null ? null
					: new Integer(columns[18].toString()));
			existingRecord.setCity_id(columns[19] == null ? null : new Integer(
					columns[19].toString()));
			existingRecord.setStreet_id(columns[20] == null ? null
					: new Integer(columns[20].toString()));
			existingRecord.setCity_region_id(columns[21] == null ? null
					: new Integer(columns[21].toString()));
			existingRecord.setAddress_hide(columns[22] == null ? null
					: new Integer(columns[22].toString()));
			existingRecord.setAddress_suffix_geo(columns[23] == null ? null
					: columns[23].toString());
			existingRecord.setAddr_number(columns[24] == null ? null
					: columns[24].toString());
			existingRecord.setAddr_block(columns[25] == null ? null
					: columns[25].toString());
			existingRecord.setAddr_appt(columns[26] == null ? null
					: columns[26].toString());
			existingRecord.setAddr_descr(columns[27] == null ? null
					: columns[27].toString());
			existingRecord.setStreet_district_id(columns[28] == null ? null
					: new Integer(columns[28].toString()));
			existingRecord.setStreet_location_geo(columns[29] == null ? null
					: columns[29].toString());
			existingRecord.setDeleted(columns[30] == null ? null : new Integer(
					columns[30].toString()));

			log += ". Result : getAbonent Finished Successfully.";
			logger.info(log);
			return existingRecord;
		} catch (Exception e) {
			logger.error("Error While Retrieving FirstNames : ", e);
			throw new CallCenterException(
					"შეცდომა მონაცემების წამოღებისას მონაცემთა ბაზიდან: "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<OrgInfoByPhone> getOrgInfosByPhone(DSRequest dsRequest)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			String phone = dsRequest.getFieldValue("phone").toString();
			oracleManager = EMF.getEntityManager();
			List resultList = oracleManager
					.createNativeQuery(Q_GET_ORGS_BY_PHONE)
					.setParameter(1, phone).getResultList();
			ArrayList<OrgInfoByPhone> result = new ArrayList<OrgInfoByPhone>();
			if (resultList != null && !resultList.isEmpty()) {
				for (Object row : resultList) {
					Object columns[] = (Object[]) row;
					OrgInfoByPhone item = new OrgInfoByPhone();
					item.setMain_id(new Long(columns[0] == null ? "-100"
							: columns[0].toString()));
					item.setOrg_name(columns[1] == null ? "" : columns[1]
							.toString());
					item.setNote(columns[2] == null ? "" : columns[2]
							.toString());
					item.setWorkinghours(columns[3] == null ? "" : columns[3]
							.toString());
					item.setDirector(columns[4] == null ? "" : columns[4]
							.toString());
					item.setIdentcode(columns[5] == null ? "" : columns[5]
							.toString());
					item.setFounded(columns[6] == null ? "" : columns[6]
							.toString());
					item.setLegaladdress(columns[7] == null ? "" : columns[7]
							.toString());
					item.setMail(columns[8] == null ? "" : columns[8]
							.toString());
					item.setWebaddress(columns[9] == null ? "" : columns[9]
							.toString());
					item.setOrg_info(columns[10] == null ? "" : columns[10]
							.toString());
					item.setContactperson(columns[11] == null ? ""
							: columns[11].toString());
					item.setDayoffs(columns[12] == null ? "" : columns[12]
							.toString());
					item.setStatuse(new Long(columns[13] == null ? "-100"
							: columns[13].toString()));
					item.setLegal_statuse_id(new Long(
							columns[14] == null ? "-100" : columns[14]
									.toString()));
					item.setLegal_statuse(columns[15] == null ? ""
							: columns[15].toString());
					item.setPartnerbank_id(new Long(
							columns[16] == null ? "-100" : columns[16]
									.toString()));
					item.setPartnerbank(columns[17] == null ? "" : columns[17]
							.toString());
					item.setWorkpersoncountity(columns[18] == null ? ""
							: columns[18].toString());
					item.setUpd_user(columns[19] == null ? "" : columns[19]
							.toString());
					item.setUpd_date(columns[20] == null ? null
							: (Timestamp) columns[20]);
					item.setInd(columns[21] == null ? "" : columns[21]
							.toString());
					item.setNote_crit(new Long(columns[22] == null ? "-100"
							: columns[22].toString()));
					item.setOrg_name_eng(columns[23] == null ? "" : columns[23]
							.toString());
					item.setExtra_priority(new Long(
							columns[24] == null ? "-100" : columns[24]
									.toString()));
					item.setNew_identcode(columns[25] == null ? ""
							: columns[25].toString());
					result.add(item);
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("Error While Retrieving getOrgInfosByPhone : ", e);
			throw new CallCenterException(
					"შეცდომა მონაცემების წამოღებისას მონაცემთა ბაზიდან: "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

}
