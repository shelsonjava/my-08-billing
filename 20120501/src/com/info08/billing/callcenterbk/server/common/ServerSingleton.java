package com.info08.billing.callcenterbk.server.common;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.shared.entity.admin.LandlineIndexes;
import com.info08.billing.callcenterbk.shared.entity.admin.GSMIndexes;
import com.isomorphic.jpa.EMF;

public class ServerSingleton {

	Logger logger = Logger.getLogger(ServerSingleton.class.getName());

	private static ServerSingleton instance;
	private static TreeMap<String, GSMIndexes> gsmIndexes;
	private static TreeMap<String, LandlineIndexes> landlineIndex;

	public static ServerSingleton getInstance() {
		if (instance == null) {
			instance = new ServerSingleton();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public TreeMap<String, LandlineIndexes> getLandlineIndexes(
			String landlineIndexParam) throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (landlineIndex == null) {
				landlineIndex = new TreeMap<String, LandlineIndexes>();
			}
			if (landlineIndex.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<LandlineIndexes> list = (ArrayList<LandlineIndexes>) oracleManager
						.createNamedQuery("LandlineIndexes.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (LandlineIndexes item : list) {
						landlineIndex.put(item.getLandline_index(), item);
					}
				}
			}
			return landlineIndex;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error(
					"Error While getting all LandlineIndexes from Database : ",
					e);
			throw new CallCenterException(
					"Error While getting all LandlineIndexes from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public TreeMap<String, GSMIndexes> getGSMIndexes()
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (gsmIndexes == null) {
				gsmIndexes = new TreeMap<String, GSMIndexes>();
			}
			if (gsmIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<GSMIndexes> list = (ArrayList<GSMIndexes>) oracleManager
						.createNamedQuery("GSMIndexes.getAllGSMIndexes")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (GSMIndexes item : list) {
						gsmIndexes.put(item.getGsm_index(), item);
					}
				}
			}
			return gsmIndexes;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error("Error While getting all GSMIndexes from Database : ",
					e);
			throw new CallCenterException(
					"Error While getting all GSMIndexes from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public GSMIndexes getGSMIndexes(String gsmIndex) throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (gsmIndexes == null) {
				gsmIndexes = new TreeMap<String, GSMIndexes>();
			}
			if (gsmIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<GSMIndexes> list = (ArrayList<GSMIndexes>) oracleManager
						.createNamedQuery("GSMIndexes.getAllGSMIndexes")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (GSMIndexes item : list) {
						gsmIndexes.put(item.getGsm_index(), item);
					}
				}
			}
			return gsmIndexes.get(gsmIndex);
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error("Error While getting GSMIndexes from Database : ", e);
			throw new CallCenterException(
					"Error While getting GSMIndexes from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void addGSMIndexes(GSMIndexes gsmIndexeParam)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (gsmIndexes == null) {
				gsmIndexes = new TreeMap<String, GSMIndexes>();
			}
			if (gsmIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<GSMIndexes> list = (ArrayList<GSMIndexes>) oracleManager
						.createNamedQuery("GSMIndexes.getAllGSMIndexes")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (GSMIndexes item : list) {
						gsmIndexes.put(item.getGsm_index(), item);
					}
				}
			}
			gsmIndexes.put(gsmIndexeParam.getGsm_index(), gsmIndexeParam);
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error("Error While adding GSMIndexes to Database : ", e);
			throw new CallCenterException(
					"Error While adding GSMIndexes to Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void updateGSMIndexes(GSMIndexes gsmIndexParam)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (gsmIndexes == null) {
				gsmIndexes = new TreeMap<String, GSMIndexes>();
			}
			if (gsmIndexes.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<GSMIndexes> list = (ArrayList<GSMIndexes>) oracleManager
						.createNamedQuery("GSMIndexes.getAllGSMIndexes")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (GSMIndexes item : list) {
						gsmIndexes.put(item.getGsm_index(), item);
					}
				}
			}
			gsmIndexes.remove(gsmIndexParam.getGsm_index());

		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error("Error While update GSMIndexes from Database : ", e);
			throw new CallCenterException(
					"Error While update GSMIndexes from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	// @SuppressWarnings("unchecked")
	// public LandlineIndexes getFixedOperatorPrefix(String fixedIndex)
	// throws CallCenterException {
	// EntityManager oracleManager = null;
	// try {
	// if (landlineIndex == null) {
	// landlineIndex = new TreeMap<String, LandlineIndexes>();
	// }
	// if (landlineIndex.isEmpty()) {
	// oracleManager = EMF.getEntityManager();
	// ArrayList<LandlineIndexes> list = (ArrayList<LandlineIndexes>)
	// oracleManager
	// .createNamedQuery("FixedOperatorPrefixe.getAll")
	// .getResultList();
	// if (list != null && !list.isEmpty()) {
	// for (LandlineIndexes item : list) {
	// landlineIndex.put(item.getPrefix(), item);
	// }
	// }
	// }
	// return landlineIndex.get(fixedIndex);
	// } catch (Exception e) {
	// if (e instanceof CallCenterException) {
	// throw (CallCenterException) e;
	// }
	// e.printStackTrace();
	// logger.error(
	// "Error While getting FixedOperatorPrefixe from Database : ",
	// e);
	// throw new CallCenterException(
	// "Error While getting FixedOperatorPrefixe from Database : "
	// + e.toString());
	// } finally {
	// if (oracleManager != null) {
	// EMF.returnEntityManager(oracleManager);
	// }
	// }
	// }

	@SuppressWarnings("unchecked")
	public void addLandlineIndexes(LandlineIndexes landlineIndexesParam)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (landlineIndex == null) {
				landlineIndex = new TreeMap<String, LandlineIndexes>();
			}
			if (landlineIndex.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<LandlineIndexes> list = (ArrayList<LandlineIndexes>) oracleManager
						.createNamedQuery("LandlineIndexes.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (LandlineIndexes item : list) {
						landlineIndex.put(item.getLandline_index(), item);
					}
				}
			}
			landlineIndex.put(landlineIndexesParam.getLandline_index(),
					landlineIndexesParam);
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error("Error While adding LandlineIndexes to Database : ", e);
			throw new CallCenterException(
					"Error While adding LandlineIndexes to Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void updateLandlineIndexes(LandlineIndexes landlineIndexesParam)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			if (landlineIndex == null) {
				landlineIndex = new TreeMap<String, LandlineIndexes>();
			}
			if (landlineIndex.isEmpty()) {
				oracleManager = EMF.getEntityManager();
				ArrayList<LandlineIndexes> list = (ArrayList<LandlineIndexes>) oracleManager
						.createNamedQuery("LandlineIndexes.getAll")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (LandlineIndexes item : list) {
						landlineIndex.put(item.getLandline_index(), item);
					}
				}
			}
			landlineIndex.remove(landlineIndexesParam.getLandline_index());

		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			logger.error(
					"Error While update LandlineIndexes from Database : ",
					e);
			throw new CallCenterException(
					"Error While update LandlineIndexes from Database : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
