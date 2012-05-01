package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.entity.block.BlockList;
import com.info08.billing.callcenterbk.shared.entity.block.BlockListPhone;
import com.info08.billing.callcenterbk.shared.entity.main.MainDetail;
import com.info08.billing.callcenterbk.shared.entity.org.MainOrg;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DataSourceManager;
import com.isomorphic.jpa.EMF;
import com.isomorphic.sql.SQLDataSource;

public class BlockListManagerDMI implements QueryConstants {

	private Logger logger = Logger.getLogger(BlockListManagerDMI.class
			.getName());

	/**
	 * Adding New BlockList
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public BlockList addBlockList(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addBlockList.";
			BlockList blockList = new BlockList();
			Object oMainId = dsRequest.getFieldValue("main_id");
			Long main_id = (oMainId == null) ? null : Long.parseLong(oMainId
					.toString());
			blockList.setMain_id(main_id);

			Object oBlockType = dsRequest.getFieldValue("block_type");
			Long block_type = (oBlockType == null) ? null : Long
					.parseLong(oBlockType.toString());
			blockList.setBlock_type(block_type);

			Object oStatus = dsRequest.getFieldValue("status");
			Long status = (oStatus == null) ? null : Long.parseLong(oStatus
					.toString());
			blockList.setStatus(status);

			blockList.setDeleted(0L);

			Object oNote = dsRequest.getFieldValue("note");
			String note = (oNote == null) ? null : oNote.toString();
			blockList.setNote(note);

			Object oMainDetailId = dsRequest.getFieldValue("main_detail_id");
			Long main_detail_id = (oMainDetailId == null) ? null : Long
					.parseLong(oMainDetailId.toString());
			blockList.setMain_detail_id(main_detail_id);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			blockList.setRec_date(recDate);
			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			blockList.setRec_user(loggedUserName);

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			oracleManager.persist(blockList);
			oracleManager.flush();

			Object oMap1 = dsRequest.getFieldValue("blockListPhones");
			if (oMap1 != null) {
				LinkedMap contractorAdvPhones = (LinkedMap) oMap1;
				if (!contractorAdvPhones.isEmpty()) {
					Set keys1 = contractorAdvPhones.keySet();
					oracleManager
							.createNativeQuery(
									QueryConstants.Q_DELETE_BLOCKLIST_PHONES)
							.setParameter(1, blockList.getId()).executeUpdate();

					oracleManager.flush();
					for (Object okey1 : keys1) {
						String phone = okey1.toString();

						BlockListPhone item = new BlockListPhone();
						item.setBlock_list_id(blockList.getId());
						item.setPhone(phone);
						oracleManager.persist(item);
					}
				}
			}

			blockList = oracleManager.find(BlockList.class, blockList.getId());
			blockList.setLoggedUserName(loggedUserName);
			if (main_id != null) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class, main_id);
				if (mainOrg != null) {
					blockList.setOrgName(mainOrg.getOrg_name());
				}
			}
			if (main_detail_id != null) {
				MainDetail mainDetail = oracleManager.find(MainDetail.class,
						main_detail_id);
				if (mainDetail != null) {
					blockList.setOrgDepName(mainDetail.getMain_detail_geo());
				}
			}

			makeBlockList(blockList, oracleManager);
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return blockList;
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

	/**
	 * Updating BlockList
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public BlockList updateBlockList(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateBlockList.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long id = new Long(record.get("id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			Long main_id = new Long(record.get("main_id").toString());
			Long main_detail_id = new Long(
					record.get("main_detail_id") == null ? "0" : record.get(
							"main_detail_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			Long block_type = new Long(record.get("block_type").toString());
			String note = record.get("note") == null ? null : record
					.get("note").toString();
			Long status = new Long(record.get("status").toString());

			BlockList blockList = oracleManager.find(BlockList.class, id);
			blockList.setMain_detail_id(main_detail_id);
			blockList.setMain_id(main_id);
			blockList.setDeleted(deleted);
			blockList.setBlock_type(block_type);
			blockList.setNote(note);
			blockList.setStatus(status);
			blockList.setUpd_user(loggedUserName);
			blockList.setUpd_date(updDate);

			oracleManager.merge(blockList);
			oracleManager.flush();

			Object oMap1 = record.get("blockListPhones");
			if (oMap1 != null) {
				LinkedMap contractorAdvPhones = (LinkedMap) oMap1;
				if (!contractorAdvPhones.isEmpty()) {
					Set keys1 = contractorAdvPhones.keySet();
					oracleManager
							.createNativeQuery(
									QueryConstants.Q_DELETE_BLOCKLIST_PHONES)
							.setParameter(1, blockList.getId()).executeUpdate();

					oracleManager.flush();
					for (Object okey1 : keys1) {
						String phone = okey1.toString();

						BlockListPhone item = new BlockListPhone();
						item.setBlock_list_id(blockList.getId());
						item.setPhone(phone);
						oracleManager.persist(item);
					}
				}
			}

			blockList = oracleManager.find(BlockList.class, blockList.getId());
			blockList.setLoggedUserName(loggedUserName);
			if (main_id != null) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class, main_id);
				if (mainOrg != null) {
					blockList.setOrgName(mainOrg.getOrg_name());
				}
			}
			if (main_detail_id != null) {
				MainDetail mainDetail = oracleManager.find(MainDetail.class,
						main_detail_id);
				if (mainDetail != null) {
					blockList.setOrgDepName(mainDetail.getMain_detail_geo());
				}
			}
			makeBlockList(blockList, oracleManager);
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return blockList;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update BlockList Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating BlockList Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public BlockList updateBlockListStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateBlockListStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long id = new Long(record.get("id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());

			BlockList blockList = oracleManager.find(BlockList.class, id);

			blockList.setDeleted(deleted);
			blockList.setUpd_user(loggedUserName);
			blockList.setUpd_date(updDate);

			oracleManager.merge(blockList);
			oracleManager.flush();

			blockList = oracleManager.find(BlockList.class, id);
			blockList.setLoggedUserName(loggedUserName);
			Long main_id = blockList.getMain_id();
			if (main_id != null) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class, main_id);
				if (mainOrg != null) {
					blockList.setOrgName(mainOrg.getOrg_name());
				}
			}
			Long main_detail_id = blockList.getMain_detail_id();
			if (main_detail_id != null) {
				MainDetail mainDetail = oracleManager.find(MainDetail.class,
						main_detail_id);
				if (mainDetail != null) {
					blockList.setOrgDepName(mainDetail.getMain_detail_geo());
				}
			}
			makeBlockList(blockList, oracleManager);
			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return blockList;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for BlockList Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void makeBlockList(BlockList blockList, EntityManager oracleManager)
			throws CallCenterException {
		try {
			logger.info("++++++++++++++++++++++++++++++++++++");
			if (blockList == null) {
				return;
			}
			logger.info("111111111111111111111111111111111111");
			Long blockType = blockList.getBlock_type();
			if (blockType == null) {
				return;
			}
			logger.info("blockType = " + blockType);
			Long main_id = blockList.getMain_id();
			if (main_id == null) {
				return;
			}
			logger.info("main_id = " + main_id);
			Long main_detail_id = blockList.getMain_detail_id();
			logger.info("main_detail_id = " + main_detail_id);

			List resultList = null;
			if (blockType.equals(4L)) {
				oracleManager.flush();
				resultList = oracleManager
						.createNativeQuery(
								QueryConstants.Q_GET_BLOCK_LIST_PHONES)
						.setParameter(1, blockList.getId()).getResultList();
			} else {
				if (main_detail_id != null && main_detail_id.longValue() > 0) {
					resultList = oracleManager
							.createNativeQuery(
									QueryConstants.Q_GET_MAIN_DET_PHONES_HIERARCHY)
							.setParameter(1, main_detail_id).getResultList();
				} else {
					resultList = oracleManager
							.createNativeQuery(
									QueryConstants.Q_GET_PHONE_LIST_BY_MAIN_ID)
							.setParameter(1, main_id).getResultList();
				}
			}

			if (resultList == null || resultList.isEmpty()) {
				return;
			}

			logger.info("resultList.size() = " + resultList.size());

			SQLDataSource mySqlDS = (SQLDataSource) DataSourceManager
					.get("MySQLSubsDS");
			Connection mySQLConnection = mySqlDS.getConnection();
			PreparedStatement statement = mySQLConnection
					.prepareStatement(QueryConstants.Q_MYSQL_DELETE_BLOCK_PHONE);

			TreeSet<String> contractPhons = new TreeSet<String>();
			for (Object object : resultList) {
				String phone = object.toString();
				contractPhons.add(phone);
				statement.setString(1, phone);
				statement.executeUpdate();
			}

			Long deleted = blockList.getDeleted();
			logger.info("blockList.getDeleted() = " + deleted);
			if (deleted != null && deleted.equals(1L)) {
				return;
			}

			Long status = blockList.getStatus();
			logger.info("blockList.getStatus() = " + status);
			if (status == null || status.equals(0L)) {
				return;
			}

			List list = null;

			if (blockType.equals(4L)) {
				oracleManager.flush();
				list = oracleManager
						.createNativeQuery(
								QueryConstants.Q_GET_BLOCK_LIST_PHONES)
						.setParameter(1, blockList.getId()).getResultList();
			} else {
				if (main_detail_id != null && main_detail_id.longValue() > 0) {
					switch (blockType.intValue()) {
					case 1: // 1 - All Phones From Organization's Department
							// will
							// block
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_MAIN_DET_PHONES_HIERARCHY)
								.setParameter(1, main_detail_id)
								.getResultList();

						break;
					case 2: // 2 - Block Only Listed Phones From This Department
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_MAIN_DET_PHONES_HIERARCHY_BLOCK_LIST)
								.setParameter(1, main_detail_id)
								.setParameter(2, blockList.getId())
								.getResultList();
					case 3: // 3 - All Phones From Organization's Department
							// will be
							// blocked except this list
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_MAIN_DET_PHONES_HIERARCHY_EXCEPT_BLOCK_LIST)
								.setParameter(1, main_detail_id)
								.setParameter(2, blockList.getId())
								.getResultList();
					default:
						break;
					}
				} else {
					switch (blockType.intValue()) {
					case 1: // Block Whole Organization
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_MAIN_ORGS_PHONES_HIERARCHY)
								.setParameter(1, main_id).getResultList();
						break;
					case 2: // 2 - Block Only Listed Phones From This
							// Organization
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_MAIN_ORGS_PHONES_HIERARCHY_BLOCK_LIST)
								.setParameter(1, main_id)
								.setParameter(2, blockList.getId())
								.getResultList();
					case 3: // 3 - Block Whole Organizations Phones Except This
							// List
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_MAIN_ORGS_PHONES_HIERARCHY_EXCEPT_BLOCK_LIST)
								.setParameter(1, main_id)
								.setParameter(2, blockList.getId())
								.getResultList();
					default:
						break;
					}
				}
			}
			if (list == null || list.isEmpty()) {
				return;
			}
			PreparedStatement statement1 = mySQLConnection
					.prepareStatement(QueryConstants.Q_MYSQL_INSERT_BLOCK_PHONE);

			for (Object oPhone : resultList) {
				String phone = oPhone.toString();
				statement1.setString(1, phone);
				statement1.setInt(2, phone.length());
				statement1.setInt(3, phone.length());
				statement1.executeUpdate();
			}
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Add Or Remove Contractor Phones Into MySQL Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების ცვლილებისას : "
					+ e.toString());
		}
	}
}
