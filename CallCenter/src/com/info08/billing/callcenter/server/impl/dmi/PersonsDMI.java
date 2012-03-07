package com.info08.billing.callcenter.server.impl.dmi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenter.client.exception.CallCenterException;
import com.info08.billing.callcenter.server.common.QueryConstants;
import com.info08.billing.callcenter.shared.entity.Person;
import com.info08.billing.callcenter.shared.entity.PersonToAccess;
import com.info08.billing.callcenter.shared.items.PersonnelTypes;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.jpa.EMF;

public class PersonsDMI implements QueryConstants {

	Logger logger = Logger.getLogger(PersonsDMI.class.getName());

	private static TreeMap<Integer, Person> persons = new TreeMap<Integer, Person>();

	public DSResponse login(DSRequest dsRequest) throws CallCenterException {
		try {
			String userName = dsRequest.getFieldValue("userName").toString();
			String password = dsRequest.getFieldValue("password").toString();

			String message = "User Identification. Username = " + userName
					+ ", Password = " + password;
			if (userName == null || userName.trim().equals("")) {
				message += ". Result = Invalid UserName. ";
				logger.info(message);
				throw new CallCenterException("არასწორი მომხმარებელი");
			}
			if (password == null || password.trim().equals("")) {
				message += ". Result = Invalid Password. ";
				logger.info(message);
				throw new CallCenterException("არასწორი პაროლი");
			}
			Collection<Person> users = persons.values();

			if (users == null || users.isEmpty()) {
				throw new CallCenterException("მომხმარებელი: " + userName
						+ " ვერ მოიძებნა 1.");
			}
			Person user = null;
			for (Person person : users) {
				if (person.getUserName() != null
						&& person.getUserName().equalsIgnoreCase(userName)) {
					user = person;
					break;
				}
			}
			if (user == null) {
				throw new CallCenterException("მომხმარებელი: " + userName
						+ " ვერ მოიძებნა.");
			}
			String dbPassword = user.getPassword();
			if (!password.equals(dbPassword)) {
				throw new CallCenterException(
						"მომხმარებელის პაროლი არასწორია !");
			}
			if (user.getDeleted() != null && !user.getDeleted().equals(0L)) {
				throw new CallCenterException(
						"თქვენი მომხმარებელი მლოკირებულია !");
			}

			DSResponse dsResponse = new DSResponse();
			dsResponse.setTotalRows(1);
			dsResponse.setStartRow(1);
			dsResponse.setEndRow(1);
			dsResponse.setData(user);
			return dsResponse;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			throw new CallCenterException("შეცდომა სისტემაში შესვლისას : "
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Person> fetchAllDataFromDB(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {

			logger.info("getting Persons From Database ... ");

			Object personelName = dsRequest.getFieldValue("personelName");
			Object personelSurName = dsRequest.getFieldValue("personelSurName");
			Object userName = dsRequest.getFieldValue("userName");
			Object recUser = dsRequest.getFieldValue("recUser");
			// String query = "select e from Person e order by e.personelId";
			String query = "select e from Person e where 1 = 1 ";
			if (personelName != null
					&& !personelName.toString().trim().equalsIgnoreCase("")) {
				query += " and e.personelName like '" + personelName.toString()
						+ "%'";
			}
			if (personelSurName != null
					&& !personelSurName.toString().trim().equalsIgnoreCase("")) {
				query += " and e.personelSurName like '"
						+ personelSurName.toString() + "%'";
			}
			if (userName != null
					&& !userName.toString().trim().equalsIgnoreCase("")) {
				query += " and e.userName like '" + userName.toString() + "%'";
			}
			if (recUser != null
					&& !recUser.toString().trim().equalsIgnoreCase("")) {
				query += " and e.recUser like '" + recUser.toString() + "%'";
			}
			query += " order by e.personelId ";

			oracleManager = EMF.getEntityManager();
			ArrayList<Person> result = (ArrayList<Person>) oracleManager
					.createQuery(query).getResultList();
			if (result != null && !result.isEmpty()) {
				for (Person person : result) {
					Long personnelId = person.getPersonelId();
					Long persTypeId = person.getPersonelTypeId();
					if (persTypeId == null) {
						continue;
					}
					PersonnelTypes personnelType = CommonDMI
							.getPersonnelTypes(persTypeId.intValue());
					if (personnelType == null) {
						continue;
					}
					person.setPersonelType(personnelType
							.getPersonnel_type_name_geo());

					List persToAccList = oracleManager
							.createNativeQuery(Q_GET_PERS_TO_ACC)
							.setParameter(1, personnelId).getResultList();
					if (persToAccList != null && !persToAccList.isEmpty()) {
						Map<String, String> mapPerms = new LinkedHashMap<String, String>();
						for (Object object : persToAccList) {
							mapPerms.put(object.toString(), "1");
						}
						person.setUserPerms(mapPerms);
					}
				}
			}
			return result;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Persons From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Person> fetchAllData(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		try {

			logger.info("getting Persons ...");

			if (persons == null || persons.isEmpty()) {
				logger.info("getting persons from DB. ");
				persons = new TreeMap<Integer, Person>();
				oracleManager = EMF.getEntityManager();
				ArrayList<Person> result = (ArrayList<Person>) oracleManager
						.createNamedQuery("Person.getAllPersonel")
						.getResultList();
				if (result != null && !result.isEmpty()) {
					for (Person person : result) {
						Long personnelId = person.getPersonelId();
						Long persTypeId = person.getPersonelTypeId();
						if (persTypeId == null) {
							continue;
						}
						PersonnelTypes personnelType = CommonDMI
								.getPersonnelTypes(persTypeId.intValue());
						if (personnelType == null) {
							continue;
						}
						person.setPersonelType(personnelType
								.getPersonnel_type_name_geo());

						List persToAccList = oracleManager
								.createNativeQuery(Q_GET_PERS_TO_ACC)
								.setParameter(1, personnelId).getResultList();
						if (persToAccList != null && !persToAccList.isEmpty()) {
							Map<String, String> mapPerms = new LinkedHashMap<String, String>();
							for (Object object : persToAccList) {
								mapPerms.put(object.toString(), "1");
							}
							person.setUserPerms(mapPerms);
						}
						persons.put(personnelId.intValue(), person);
					}
				}
			}

			ArrayList<Person> ret = new ArrayList<Person>();
			ret.addAll(persons.values());
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Persons From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Person customAdd(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			Long persTypeId = new Long(dsRequest.getFieldValue(
					"personnel_type_id").toString());

			Person person = new Person();
			person.setPersonelName(dsRequest.getFieldValue("personelName")
					.toString());
			person.setPersonelSurName(dsRequest
					.getFieldValue("personelSurName").toString());
			person.setUserName(dsRequest.getFieldValue("userName").toString());
			person.setPassword(dsRequest.getFieldValue("password").toString());
			person.setAccessId(-1L);
			person.setDeleted(0L);
			person.setPersonelTypeId(persTypeId);
			person.setRecDate(currDate);
			person.setRecUser(loggedUserName);
			PersonnelTypes personnelTypes = CommonDMI
					.getPersonnelTypes(persTypeId.intValue());
			person.setPersonelType(personnelTypes == null ? null
					: personnelTypes.getPersonnel_type_name_geo());

			oracleManager.persist(person);

			Long personnelId = person.getPersonelId();
			Map userPerms = null;
			Object oUserPerms = dsRequest.getFieldValue("userPerms");
			if (oUserPerms != null) {
				userPerms = (Map) oUserPerms;
			}

			if (userPerms != null && !userPerms.isEmpty()) {
				if (userPerms != null && !userPerms.isEmpty()) {
					Set<String> keySet = userPerms.keySet();
					for (String accessId : keySet) {
						PersonToAccess personToAccess = new PersonToAccess();
						personToAccess.setAccessId(new Long(accessId));
						personToAccess.setPersonelId(personnelId);
						oracleManager.persist(personToAccess);
					}
				}
			}
			person.setUserPerms(userPerms);
			EMF.commitTransaction(transaction);
			persons.put(personnelId.intValue(), person);
			return person;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While adding Personnel into Database : ", e);
			throw new CallCenterException(
					"შეცდომა მომხმარებლის მონაცემების შენახვისას : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Person customUpdate(Map fieldValues) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Long personelId = -1L;
			Object opersId = fieldValues.get("personelId");
			if (opersId != null) {
				personelId = new Long(opersId.toString());
			}
			oracleManager
					.createNativeQuery(Q_UPDATE_PERSONNEL)
					.setParameter(1, fieldValues.get("personelName").toString())
					.setParameter(2,
							fieldValues.get("personelSurName").toString())
					.setParameter(3, fieldValues.get("userName").toString())
					.setParameter(4, fieldValues.get("password").toString())
					.setParameter(5,
							fieldValues.get("loggedUserName").toString())
					.setParameter(
							6,
							new Long(fieldValues.get("personnel_type_id")
									.toString())).setParameter(7, personelId)
					.executeUpdate();

			Map userPerms = null;
			Object oUserPerms = fieldValues.get("userPerms");
			if (oUserPerms != null) {
				userPerms = (Map) oUserPerms;
			}

			oracleManager.createNativeQuery(Q_DELETE_PERSONNEL)
					.setParameter(1, personelId).executeUpdate();

			oracleManager.flush();

			if (userPerms != null && !userPerms.isEmpty()) {
				if (userPerms != null && !userPerms.isEmpty()) {
					Set<String> keySet = userPerms.keySet();
					for (String accessId : keySet) {
						PersonToAccess personToAccess = new PersonToAccess();
						personToAccess.setAccessId(new Long(accessId));
						personToAccess.setPersonelId(personelId);
						oracleManager.persist(personToAccess);
					}
				}
			}
			EMF.commitTransaction(transaction);
			Person person = oracleManager.find(Person.class, personelId);
			PersonnelTypes personnelTypes = CommonDMI.getPersonnelTypes(person
					.getPersonelTypeId().intValue());
			person.setPersonelType(personnelTypes == null ? null
					: personnelTypes.getPersonnel_type_name_geo());
			person.setUserPerms(userPerms);
			persons.remove((int) person.getPersonelId());
			persons.put((int) person.getPersonelId(), person);
			return person;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While updating Personnel into Database : ", e);
			throw new CallCenterException(
					"შეცდომა მომხმარებლის მონაცემების განახლებისას : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Person customUpdateStatus(Map fieldValues) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Long personelId = -1L;
			Object opersId = fieldValues.get("personelId");
			if (opersId != null) {
				personelId = new Long(opersId.toString());
			}
			oracleManager
					.createNativeQuery(Q_UPDATE_PERSONNEL_STATUS)
					.setParameter(1,
							new Integer(fieldValues.get("deleted").toString()))
					.setParameter(2,
							fieldValues.get("loggedUserName").toString())
					.setParameter(3, personelId).executeUpdate();

			EMF.commitTransaction(transaction);
			Person person = oracleManager.find(Person.class, personelId);
			PersonnelTypes personnelTypes = CommonDMI.getPersonnelTypes(person
					.getPersonelTypeId().intValue());
			person.setPersonelType(personnelTypes == null ? null
					: personnelTypes.getPersonnel_type_name_geo());
			List persToAccList = oracleManager
					.createNativeQuery(Q_GET_PERS_TO_ACC)
					.setParameter(1, person.getPersonelId()).getResultList();
			if (persToAccList != null && !persToAccList.isEmpty()) {
				Map<String, String> mapPerms = new LinkedHashMap<String, String>();
				for (Object object : persToAccList) {
					mapPerms.put(object.toString(), "1");
				}
				person.setUserPerms(mapPerms);
			}
			persons.remove((int) person.getPersonelId());
			persons.put((int) person.getPersonelId(), person);
			return person;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While updating Personnel into Database : ", e);
			throw new CallCenterException(
					"შეცდომა მომხმარებლის მონაცემების განახლებისას : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public DSResponse fetchAdvOrdered(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		try {
			oracleManager = EMF.getEntityManager();
			long startRow = dsRequest.getStartRow();
			long endRow = dsRequest.getEndRow();

			List result = oracleManager.createNativeQuery(
					QueryConstants.Q_GET_ALL_PERSONS).getResultList();

			ArrayList<Person> matchingItems = new ArrayList<Person>();

			if (result != null && !result.isEmpty()) {
				for (Object row : result) {
					Object cols[] = (Object[]) row;
					Long personId = new Long(cols[0] == null ? "-1"
							: cols[0].toString());
					String name = cols[1] == null ? "-1" : cols[1].toString();
					String surName = cols[2] == null ? "-1" : cols[2]
							.toString();
					String userName = cols[3] == null ? "-1" : cols[3]
							.toString();
					Person person = new Person();
					person.setPersonelId(personId);
					person.setPersonelName(name);
					person.setPersonelSurName(surName);
					person.setUserName(userName);
					matchingItems.add(person);
				}
			}

			Long totalRows = new Long(matchingItems.size());
			DSResponse dsResponse = new DSResponse();
			dsResponse.setTotalRows(totalRows);
			// dsResponse.setTotalRows(0);
			dsResponse.setStartRow(startRow);

			endRow = Math.min(endRow, totalRows);
			endRow = Math.min(endRow, 0);
			dsResponse.setEndRow(endRow);
			// just return the List of matching beans
			dsResponse.setData(matchingItems);
			return dsResponse;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Persons From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

}
