package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.DistrictIndexes;
import com.info08.billing.callcenterbk.shared.entity.StreetEnt;
import com.info08.billing.callcenterbk.shared.entity.StreetIndex;
import com.info08.billing.callcenterbk.shared.entity.VillageIndexes;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;

public class DistrictVillageIndexesDMI implements QueryConstants {

	Logger logger = Logger.getLogger(DistrictVillageIndexesDMI.class.getName());

	/**
	 * Adding New DistrictIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	// public DistrictIndexes addDistrictIndexes(DistrictIndexes
	// districtIndexes)
	// throws Exception {
	// EntityManager oracleManager = null;
	// Object transaction = null;
	// try {
	// String log = "Method:CommonDMI.addDistrictIndexes.";
	// oracleManager = EMF.getEntityManager();
	// transaction = EMF.getTransaction(oracleManager);
	//
	// String loggedUserName = districtIndexes.getLoggedUserName();
	//
	// Timestamp updDate = new Timestamp(System.currentTimeMillis());
	// RCNGenerator.getInstance().initRcn(oracleManager, updDate,
	// loggedUserName, "Add DistrictIndexes.");
	//
	// oracleManager.persist(districtIndexes);
	// oracleManager.flush();
	//
	// districtIndexes = oracleManager.find(DistrictIndexes.class,
	// districtIndexes.getDistrict_index_id());
	//
	// EMF.commitTransaction(transaction);
	// log += ". Inserting Finished SuccessFully. ";
	// logger.info(log);
	// return districtIndexes;
	// } catch (Exception e) {
	// EMF.rollbackTransaction(transaction);
	// if (e instanceof CallCenterException) {
	// throw (CallCenterException) e;
	// }
	// logger.error("Error While Insert DistrictIndexes Into Database : ",
	// e);
	// throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
	// + e.toString());
	// } finally {
	// if (oracleManager != null) {
	// EMF.returnEntityManager(oracleManager);
	// }
	// }
	// }

	@SuppressWarnings("rawtypes")
	public DistrictIndexes addDistrictIndexes(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addDistrictIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			DistrictIndexes districtIndexes = new DistrictIndexes();
			districtIndexes.setDistrict_index_name(record
					.get("district_index_name") == null ? null : record.get(
					"district_index_name").toString());

			String loggedUserName = record.get("loggedUserName").toString();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add DistrictIndexes.");

			oracleManager.persist(districtIndexes);
			oracleManager.flush();

			districtIndexes = oracleManager.find(DistrictIndexes.class,
					districtIndexes.getDistrict_index_id());

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return districtIndexes;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert DistrictIndexes Into Database : ",
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
	 * Updating DistrictIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public DistrictIndexes updateDistrictIndexes(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateDistrictIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long district_index_id = new Long(record.get("district_index_id")
					.toString());
			String district_index_name = record.get("district_index_name") == null ? null
					: record.get("district_index_name").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			DistrictIndexes districtIndex = oracleManager.find(
					DistrictIndexes.class, district_index_id);

			districtIndex.setDistrict_index_name(district_index_name);

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Detele DistrictIndexes.");

			oracleManager.merge(districtIndex);
			oracleManager.flush();

			districtIndex = oracleManager.find(DistrictIndexes.class,
					district_index_id);

			districtIndex.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return districtIndex;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update DistrictIndexes Into Database : ",
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
	 * Delete DistrictIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */

	public DistrictIndexes deleteDistrictIndexes(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.deleteDistrictIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long district_index_id = new Long(dsRequest.getOldValues()
					.get("district_index_id").toString());

			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Detele DistrictIndexes.");

			DistrictIndexes districtIndexes = oracleManager.find(
					DistrictIndexes.class, district_index_id);

			oracleManager.remove(districtIndexes);
			oracleManager.flush();

			EMF.commitTransaction(transaction);
			log += ". Status Delete Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for DistrictIndexes Into Database : ",
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
	 * Adding New VillageIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public VillageIndexes addVillageIndexes(VillageIndexes villageIndexesParam)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addVillageIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = villageIndexesParam.getLoggedUserName();

			oracleManager.persist(villageIndexesParam);
			oracleManager.flush();

			villageIndexesParam = oracleManager.find(VillageIndexes.class,
					villageIndexesParam.getVillage_index_id());
			villageIndexesParam.setLoggedUserName(loggedUserName);

			Long district_index_id = villageIndexesParam.getDistrict_index_id();
			if (district_index_id != null) {
				DistrictIndexes districtIndex = oracleManager.find(
						DistrictIndexes.class, district_index_id);
				if (districtIndex != null) {
					villageIndexesParam.setDistrict_index_name(districtIndex
							.getDistrict_index_name());
				}
			}
			Long district_center = villageIndexesParam.getDistrict_center();
			if (district_center != null) {
				switch (district_center.intValue()) {
				case -1:
					villageIndexesParam
							.setDistrict_center_descr("რაიონული ცენტრი");
					break;
				case 0:
					villageIndexesParam.setDistrict_center_descr("სოფელი");
					break;
				default:
					villageIndexesParam.setDistrict_center_descr("უცნობია");
					break;
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return villageIndexesParam;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert VillageIndexes Into Database : ",
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
	 * Updating VillageIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public VillageIndexes updateVillageIndexes(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateVillageIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long village_index_id = new Long(record.get("village_index_id")
					.toString());
			String village_index_name = record.get("village_index_name") == null ? null
					: record.get("village_index_name").toString();
			String village_index = record.get("village_index") == null ? null
					: record.get("village_index").toString();
			Long district_center = new Long(record.get("district_center")
					.toString());
			Long district_index_id = new Long(record.get("district_index_id")
					.toString());

			String loggedUserName = record.get("loggedUserName").toString();

			VillageIndexes villageIndex = oracleManager.find(
					VillageIndexes.class, village_index_id);

			villageIndex.setVillage_index_name(village_index_name);
			villageIndex.setVillage_index(village_index);
			villageIndex.setDistrict_center(district_center);
			villageIndex.setDistrict_index_id(district_index_id);

			oracleManager.merge(villageIndex);
			oracleManager.flush();

			villageIndex = oracleManager.find(VillageIndexes.class,
					village_index_id);

			villageIndex.setLoggedUserName(loggedUserName);

			if (district_index_id != null) {
				DistrictIndexes districtIndex = oracleManager.find(
						DistrictIndexes.class, district_index_id);
				if (districtIndex != null) {
					villageIndex.setDistrict_index_name(districtIndex
							.getDistrict_index_name());
				}
			}
			if (district_center != null) {
				switch (district_center.intValue()) {
				case -1:
					villageIndex.setDistrict_center_descr("რაიონული ცენტრი");
					break;
				case 0:
					villageIndex.setDistrict_center_descr("სოფელი");
					break;
				default:
					villageIndex.setDistrict_center_descr("უცნობია");
					break;
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return villageIndex;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update VillageIndexes Into Database : ",
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
	 * Delete VillageIndexes
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public VillageIndexes deleteVillageIndexes(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.deleteVillageIndexes.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long village_index_id = new Long(dsRequest.getOldValues()
					.get("village_index_id").toString());

			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Detele Event.");

			VillageIndexes villageIndexes = oracleManager.find(
					VillageIndexes.class, village_index_id);

			oracleManager.remove(villageIndexes);
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
			logger.error("Error While Delete VillageIndexes From Database : ",
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
	 * Adding New StreetIndex
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public StreetIndex addStreetIndex(StreetIndex streetIndex) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addStreetIndex.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = streetIndex.getLoggedUserName();
			streetIndex.setRec_date(recDate);

			oracleManager.persist(streetIndex);
			oracleManager.flush();

			streetIndex = oracleManager.find(StreetIndex.class,
					streetIndex.getStreet_index_id());
			streetIndex.setLoggedUserName(loggedUserName);

			Long street_id = streetIndex.getStreet_id();
			if (street_id != null) {
				StreetEnt streetEnt = oracleManager.find(StreetEnt.class,
						street_id);
				if (streetEnt != null) {
					streetIndex.setStreetName(streetEnt.getStreet_name_geo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return streetIndex;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert StreetIndex Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating StreetIndex
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetIndex updateStreetIndex(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetIndex.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_index_id = new Long(record.get("street_index_id")
					.toString());
			Long street_id = record.get("street_id") == null ? null : new Long(
					record.get("street_id").toString());
			String street_comment = record.get("street_comment") == null ? null
					: record.get("street_comment").toString();
			String street_index = record.get("street_index") == null ? null
					: record.get("street_index").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			StreetIndex streetIndex = oracleManager.find(StreetIndex.class,
					street_index_id);

			streetIndex.setStreet_id(street_id);
			streetIndex.setStreet_comment(street_comment);
			streetIndex.setStreet_index(street_index);
			streetIndex.setUpd_user(loggedUserName);

			oracleManager.merge(streetIndex);
			oracleManager.flush();

			streetIndex = oracleManager
					.find(StreetIndex.class, street_index_id);
			streetIndex.setLoggedUserName(loggedUserName);

			if (street_id != null) {
				StreetEnt streetEnt = oracleManager.find(StreetEnt.class,
						street_id);
				if (streetEnt != null) {
					streetIndex.setStreetName(streetEnt.getStreet_name_geo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return streetIndex;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update StreetIndex Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების განახლებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating StreetIndex Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetIndex updateStreetIndexStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetIndexStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_index_id = new Long(record.get("street_index_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			StreetIndex streetIndex = oracleManager.find(StreetIndex.class,
					street_index_id);

			streetIndex.setDeleted(deleted);
			streetIndex.setUpd_user(loggedUserName);

			oracleManager.merge(streetIndex);
			oracleManager.flush();
			streetIndex = oracleManager
					.find(StreetIndex.class, street_index_id);

			streetIndex.setLoggedUserName(loggedUserName);

			Long street_id = streetIndex.getStreet_id();
			if (street_id != null) {
				StreetEnt streetEnt = oracleManager.find(StreetEnt.class,
						street_id);
				if (streetEnt != null) {
					streetIndex.setStreetName(streetEnt.getStreet_name_geo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return streetIndex;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for StreetIndex Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
