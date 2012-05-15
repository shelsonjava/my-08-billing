package com.info08.billing.callcenterbk.server.common;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.shared.entity.admin.FixedOperatorPrefixe;
import com.info08.billing.callcenterbk.shared.entity.admin.GSMIndexes;
import com.isomorphic.jpa.EMF;

public class ServerSingleton {

	Logger logger = Logger.getLogger(ServerSingleton.class.getName());

	private static ServerSingleton instance;
	private static TreeMap<String, GSMIndexes> mobileIndexes;
	private static TreeMap<String, FixedOperatorPrefixe> fixedIndexes;

	public static ServerSingleton getInstance() {
		if (instance == null) {
			instance = new ServerSingleton();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public TreeMap<String, FixedOperatorPrefixe> getAllFixedOperPrefixes(
			String fixedIndex) throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (fixedIndexes == null) {
				fixedIndexes = new TreeMap<String, FixedOperatorPrefixe>();
			}
			if (fixedIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<FixedOperatorPrefixe> list = (ArrayList<FixedOperatorPrefixe>) oracleManager
						.createNamedQuery("FixedOperatorPrefixe.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (FixedOperatorPrefixe item : list) {
						fixedIndexes.put(item.getPrefix(), item);
					}
				}
			}
			return fixedIndexes;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error(
					"Error While getting all FixedOperatorPrefixe from Database : ",
					e);
			throw new CallCenterException(
					"Error While getting all FixedOperatorPrefixe from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public TreeMap<String, GSMIndexes> getAllMobileOperPrefixes()
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (mobileIndexes == null) {
				mobileIndexes = new TreeMap<String, GSMIndexes>();
			}
			if (mobileIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<GSMIndexes> list = (ArrayList<GSMIndexes>) oracleManager
						.createNamedQuery("MobileOperatorPrefixe.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (GSMIndexes item : list) {
						mobileIndexes.put(item.getGsm_index(), item);
					}
				}
			}
			return mobileIndexes;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error(
					"Error While getting all MobileOperatorPrefixes from Database : ",
					e);
			throw new CallCenterException(
					"Error While getting all MobileOperatorPrefixes from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public GSMIndexes getMobileOperatorPrefix(String mobileIndex)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (mobileIndexes == null) {
				mobileIndexes = new TreeMap<String, GSMIndexes>();
			}
			if (mobileIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<GSMIndexes> list = (ArrayList<GSMIndexes>) oracleManager
						.createNamedQuery("MobileOperatorPrefixe.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (GSMIndexes item : list) {
						mobileIndexes.put(item.getGsm_index(), item);
					}
				}
			}
			return mobileIndexes.get(mobileIndex);
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error(
					"Error While getting MobileOperatorPrefixes from Database : ",
					e);
			throw new CallCenterException(
					"Error While getting MobileOperatorPrefixes from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void addMobileOperatorPrefix(GSMIndexes mobileOperatorPrefixe)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (mobileIndexes == null) {
				mobileIndexes = new TreeMap<String, GSMIndexes>();
			}
			if (mobileIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<GSMIndexes> list = (ArrayList<GSMIndexes>) oracleManager
						.createNamedQuery("MobileOperatorPrefixe.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (GSMIndexes item : list) {
						mobileIndexes.put(item.getGsm_index(), item);
					}
				}
			}
			mobileIndexes.put(mobileOperatorPrefixe.getGsm_index(),
					mobileOperatorPrefixe);
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error(
					"Error While adding MobileOperatorPrefixes to Database : ",
					e);
			throw new CallCenterException(
					"Error While adding MobileOperatorPrefixes to Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void updateMobileOperatorPrefix(GSMIndexes mobileOperatorPrefixe)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (mobileIndexes == null) {
				mobileIndexes = new TreeMap<String, GSMIndexes>();
			}
			if (mobileIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<GSMIndexes> list = (ArrayList<GSMIndexes>) oracleManager
						.createNamedQuery("MobileOperatorPrefixe.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (GSMIndexes item : list) {
						mobileIndexes.put(item.getGsm_index(), item);
					}
				}
			}
			mobileIndexes.remove(mobileOperatorPrefixe.getGsm_index());

		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error(
					"Error While update MobileOperatorPrefixes from Database : ",
					e);
			throw new CallCenterException(
					"Error While update MobileOperatorPrefixes from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public FixedOperatorPrefixe getFixedOperatorPrefix(String fixedIndex)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (fixedIndexes == null) {
				fixedIndexes = new TreeMap<String, FixedOperatorPrefixe>();
			}
			if (fixedIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<FixedOperatorPrefixe> list = (ArrayList<FixedOperatorPrefixe>) oracleManager
						.createNamedQuery("FixedOperatorPrefixe.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (FixedOperatorPrefixe item : list) {
						fixedIndexes.put(item.getPrefix(), item);
					}
				}
			}
			return fixedIndexes.get(fixedIndex);
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error(
					"Error While getting FixedOperatorPrefixe from Database : ",
					e);
			throw new CallCenterException(
					"Error While getting FixedOperatorPrefixe from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void addFixedOperatorPrefix(FixedOperatorPrefixe fixedOperatorPrefixe)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (fixedIndexes == null) {
				fixedIndexes = new TreeMap<String, FixedOperatorPrefixe>();
			}
			if (fixedIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<FixedOperatorPrefixe> list = (ArrayList<FixedOperatorPrefixe>) oracleManager
						.createNamedQuery("FixedOperatorPrefixe.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (FixedOperatorPrefixe item : list) {
						fixedIndexes.put(item.getPrefix(), item);
					}
				}
			}
			fixedIndexes.put(fixedOperatorPrefixe.getPrefix(),
					fixedOperatorPrefixe);
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error(
					"Error While adding FixedOperatorPrefixe to Database : ", e);
			throw new CallCenterException(
					"Error While adding FixedOperatorPrefixe to Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void updateFixedOperatorPrefix(
			FixedOperatorPrefixe fixedOperatorPrefixe)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (fixedIndexes == null) {
				fixedIndexes = new TreeMap<String, FixedOperatorPrefixe>();
			}
			if (fixedIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<FixedOperatorPrefixe> list = (ArrayList<FixedOperatorPrefixe>) oracleManager
						.createNamedQuery("FixedOperatorPrefixe.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (FixedOperatorPrefixe item : list) {
						fixedIndexes.put(item.getPrefix(), item);
					}
				}
			}
			fixedIndexes.remove(fixedOperatorPrefixe.getPrefix());
			if (fixedOperatorPrefixe.getDeleted().equals(0L)) {
				fixedIndexes.put(fixedOperatorPrefixe.getPrefix(),
						fixedOperatorPrefixe);
			}
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error(
					"Error While update FixedOperatorPrefixe from Database : ",
					e);
			throw new CallCenterException(
					"Error While update FixedOperatorPrefixe from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
