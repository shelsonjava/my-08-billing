package com.info08.billing.callcenterbk.server.impl.dmi;

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

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.UserPermission;
import com.info08.billing.callcenterbk.shared.entity.Users;
import com.info08.billing.callcenterbk.shared.items.Departments;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.jpa.EMF;

public class UsersDMI implements QueryConstants {

	Logger logger = Logger.getLogger(UsersDMI.class.getName());

	private static TreeMap<Integer, Users> usersMap = new TreeMap<Integer, Users>();

	public DSResponse login(DSRequest dsRequest) throws CallCenterException {
		try {
			String user_name = dsRequest.getFieldValue("user_name").toString();
			String user_password = dsRequest.getFieldValue("user_password")
					.toString();

			String message = "User Identification. user_name = " + user_name
					+ ", user_password = " + user_password;
			if (user_name == null || user_name.trim().equals("")) {
				message += ". Result = Invalid user_name. ";
				logger.info(message);
				throw new CallCenterException("არასწორი მომხმარებელი");
			}
			if (user_password == null || user_password.trim().equals("")) {
				message += ". Result = Invalid Password. ";
				logger.info(message);
				throw new CallCenterException("არასწორი პაროლი");
			}
			Collection<Users> users = usersMap.values();

			if (users == null || users.isEmpty()) {
				throw new CallCenterException("მომხმარებელი: " + user_name
						+ " ვერ მოიძებნა 1.");
			}
			Users user = null;
			for (Users userItem : users) {
				if (userItem.getUser_name() != null
						&& userItem.getUser_name().equalsIgnoreCase(user_name)) {
					user = userItem;
					break;
				}
			}
			if (user == null) {
				throw new CallCenterException("მომხმარებელი: " + user_name
						+ " ვერ მოიძებნა.");
			}
			String databasePassword = user.getUser_password();
			if (!user_password.equals(databasePassword)) {
				throw new CallCenterException(
						"მომხმარებელის პაროლი არასწორია !");
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
	public ArrayList<Users> fetchAllUsersFromDB(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {

			logger.info("getting Users From Database ... ");

			Object user_firstname = dsRequest.getFieldValue("user_firstname");
			Object user_lastname = dsRequest.getFieldValue("user_lastname");
			Object user_name = dsRequest.getFieldValue("userName");
			String query = "select e from Users e where 1 = 1 ";
			if (user_firstname != null
					&& !user_firstname.toString().trim().equalsIgnoreCase("")) {
				query += " and e.user_firstname like '"
						+ user_firstname.toString() + "%'";
			}
			if (user_lastname != null
					&& !user_lastname.toString().trim().equalsIgnoreCase("")) {
				query += " and e.user_lastname like '"
						+ user_lastname.toString() + "%'";
			}
			if (user_name != null
					&& !user_name.toString().trim().equalsIgnoreCase("")) {
				query += " and e.user_name like '" + user_name.toString()
						+ "%'";
			}

			query += " order by e.user_id ";

			oracleManager = EMF.getEntityManager();
			ArrayList<Users> result = (ArrayList<Users>) oracleManager
					.createQuery(query).getResultList();
			if (result != null && !result.isEmpty()) {
				for (Users user : result) {
					// Long user_id = user.getUser_id();
					Long department_id = user.getDepartment_id();
					if (department_id == null) {
						continue;
					}
					Departments department = CommonDMI
							.getDepartments(department_id.intValue());
					if (department == null) {
						continue;
					}
					user.setDepartment_name(department.getDepartment_name());

					List userPermissions = oracleManager
							.createNativeQuery(Q_GET_USER_PERMISSIONS)
							.setParameter(1, department_id).getResultList();
					if (userPermissions != null && !userPermissions.isEmpty()) {
						Map<String, String> mapPermissions = new LinkedHashMap<String, String>();
						for (Object object : userPermissions) {
							mapPermissions.put(object.toString(), "1");
						}
						user.setUserPerms(mapPermissions);
					}
				}
			}
			return result;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Users From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Users> fetchAllUsers(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		try {

			logger.info("getting Persons ...");

			if (usersMap == null || usersMap.isEmpty()) {
				logger.info("getting users from DB. ");
				usersMap = new TreeMap<Integer, Users>();
				oracleManager = EMF.getEntityManager();
				ArrayList<Users> result = (ArrayList<Users>) oracleManager
						.createNamedQuery("Users.getAllUsers").getResultList();
				if (result != null && !result.isEmpty()) {
					for (Users user : result) {
						Long user_id = user.getUser_id();

						Long departmentId = user.getDepartment_id();
						if (departmentId == null) {
							continue;
						}
						Departments department = CommonDMI
								.getDepartments(departmentId.intValue());
						if (department == null) {
							continue;
						}
						user.setDepartment_name(department.getDepartment_name());

						List userPermissions = oracleManager
								.createNativeQuery(Q_GET_USER_PERMISSIONS)
								.setParameter(1, user_id).getResultList();
						if (userPermissions != null
								&& !userPermissions.isEmpty()) {
							Map<String, String> mapPermissions = new LinkedHashMap<String, String>();
							for (Object object : userPermissions) {
								mapPermissions.put(object.toString(), "1");
							}
							user.setUserPerms(mapPermissions);
						}
						usersMap.put(user_id.intValue(), user);
					}
				}
			}

			ArrayList<Users> ret = new ArrayList<Users>();
			ret.addAll(usersMap.values());
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Users From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Users addUser(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long department_id = new Long(dsRequest.getFieldValue(
					"department_id").toString());

			Users user = new Users();
			user.setUser_firstname(dsRequest.getFieldValue("user_firstname")
					.toString());
			user.setUser_lastname(dsRequest.getFieldValue("user_lastname")
					.toString());
			user.setUser_name(dsRequest.getFieldValue("user_name").toString());
			user.setUser_password(dsRequest.getFieldValue("user_password")
					.toString());
			user.setDepartment_id(department_id);
			Departments departments = CommonDMI.getDepartments(department_id
					.intValue());
			user.setDepartment_name(departments == null ? null : departments
					.getDepartment_name());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add User.");

			oracleManager.persist(user);

			Long user_id = user.getUser_id();
			Map userPerms = null;
			Object oUserPerms = dsRequest.getFieldValue("userPerms");
			if (oUserPerms != null) {
				userPerms = (Map) oUserPerms;
			}

			if (userPerms != null && !userPerms.isEmpty()) {
				if (userPerms != null && !userPerms.isEmpty()) {
					Set<String> keySet = userPerms.keySet();
					for (String permissionItem : keySet) {
						UserPermission userPermission = new UserPermission();
						userPermission
								.setPermission_id(new Long(permissionItem));
						userPermission.setUser_id(user_id);
						oracleManager.persist(userPermission);
					}
				}
			}
			user.setUserPerms(userPerms);
			EMF.commitTransaction(transaction);
			usersMap.put(user_id.intValue(), user);
			return user;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While adding Users into Database : ", e);
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
	public Users updateUser(Map fieldValues) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = fieldValues.get("loggedUserName")
					.toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Edit User.");
			Long user_id = -1L;
			Object operatorId = fieldValues.get("user_id");
			if (operatorId != null) {
				user_id = new Long(operatorId.toString());
			}
			oracleManager
					.createNativeQuery(Q_UPDATE_USER)
					.setParameter(1,
							fieldValues.get("user_firstname").toString())
					.setParameter(2,
							fieldValues.get("user_lastname").toString())
					.setParameter(3, fieldValues.get("user_name").toString())
					.setParameter(4,
							fieldValues.get("user_password").toString())
					.setParameter(
							5,
							new Long(fieldValues.get("department_id")
									.toString())).setParameter(6, user_id)
					.executeUpdate();

			Map userPermissions = null;
			Object oUserPerms = fieldValues.get("userPerms");
			if (oUserPerms != null) {
				userPermissions = (Map) oUserPerms;
			}

			oracleManager.createNativeQuery(Q_DELETE_USER_PERMISSION)
					.setParameter(1, user_id).executeUpdate();

			oracleManager.flush();

			if (userPermissions != null && !userPermissions.isEmpty()) {

				Set<String> keySet = userPermissions.keySet();
				for (String permissionId : keySet) {
					UserPermission userPermission = new UserPermission();
					userPermission.setPermission_id(new Long(permissionId));
					userPermission.setUser_id(user_id);
					oracleManager.persist(userPermission);

				}
			}
			EMF.commitTransaction(transaction);
			Users user = oracleManager.find(Users.class, user_id);
			Departments departments = CommonDMI.getDepartments(user
					.getDepartment_id().intValue());
			user.setDepartment_name((departments == null ? null : departments
					.getDepartment_name()));
			user.setUserPerms(userPermissions);
			usersMap.remove((int) user.getUser_id());
			usersMap.put((int) user.getUser_id(), user);
			return user;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While updating User into Database : ", e);
			throw new CallCenterException(
					"შეცდომა მომხმარებლის მონაცემების განახლებისას : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public Users deleteUser(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Long user_id = new Long(dsRequest.getOldValues().get("user_id")
					.toString());

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Delete User.");
			oracleManager.createNativeQuery(Q_DELETE_USER)
					.setParameter(1, user_id).executeUpdate();

			oracleManager.createNativeQuery(Q_DELETE_USER_PERMISSIONS)
					.setParameter(1, user_id).executeUpdate();

			usersMap.remove(user_id.intValue());

			EMF.commitTransaction(transaction);

			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While updating Users into Database : ", e);
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
	public DSResponse fetchOrderedUsers(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		try {
			oracleManager = EMF.getEntityManager();
			long startRow = dsRequest.getStartRow();
			long endRow = dsRequest.getEndRow();

			List result = oracleManager.createNativeQuery(
					QueryConstants.Q_GET_ALL_USERS).getResultList();

			ArrayList<Users> matchingItems = new ArrayList<Users>();

			if (result != null && !result.isEmpty()) {
				for (Object row : result) {
					Object cols[] = (Object[]) row;
					Long user_id = new Long(cols[0] == null ? "-1"
							: cols[0].toString());
					String firstname = cols[1] == null ? "-1" : cols[1]
							.toString();
					String surName = cols[2] == null ? "-1" : cols[2]
							.toString();
					String user_name = cols[3] == null ? "-1" : cols[3]
							.toString();
					Users user = new Users();
					user.setUser_id(user_id);
					user.setUser_firstname(firstname);
					user.setUser_lastname(surName);
					user.setUser_name(user_name);
					matchingItems.add(user);
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
			logger.error("Error While Fetching Users From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

}
