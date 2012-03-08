package com.info08.billing.callcenter.server.impl.dmi;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;

import com.info08.billing.callcenter.client.exception.CallCenterException;
import com.info08.billing.callcenter.server.common.QueryConstants;
import com.info08.billing.callcenter.server.common.RCNGenerator;
import com.info08.billing.callcenter.shared.common.CommonFunctions;
import com.info08.billing.callcenter.shared.entity.contractors.Contract;
import com.info08.billing.callcenter.shared.entity.contractors.ContractPriceItem;
import com.info08.billing.callcenter.shared.entity.contractors.ContractorPhone;
import com.info08.billing.callcenter.shared.entity.main.MainDetail;
import com.info08.billing.callcenter.shared.entity.org.MainOrg;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DataSourceManager;
import com.isomorphic.jpa.EMF;
import com.isomorphic.sql.SQLDataSource;

public class ContractorsDMI implements QueryConstants {

	private Logger logger = Logger.getLogger(ContractorsDMI.class.getName());

	/**
	 * Adding New Contract
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Contract addContractor(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addContractor.";

			Contract contract = new Contract();
			Object oMainId = dsRequest.getFieldValue("main_id");
			Long main_id = (oMainId == null) ? null : Long.parseLong(oMainId
					.toString());
			contract.setMain_id(main_id);

			Object oBlock = dsRequest.getFieldValue("block");
			Long block = (oBlock == null) ? null : Long.parseLong(oBlock
					.toString());
			contract.setBlock(block);

			Object oCriticalNumber = dsRequest.getFieldValue("critical_number");
			Long critical_number = (oCriticalNumber == null) ? null : Long
					.parseLong(oCriticalNumber.toString());
			contract.setCritical_number(critical_number);

			contract.setDeleted(0L);

			Object oEndDate = dsRequest.getFieldValue("end_date");
			Timestamp end_date = (oEndDate == null) ? null : new Timestamp(
					((Date) oEndDate).getTime());
			contract.setEnd_date(end_date);

			Object oStartDate = dsRequest.getFieldValue("start_date");
			Timestamp start_date = (oStartDate == null) ? null : new Timestamp(
					((Date) oStartDate).getTime());
			contract.setStart_date(start_date);

			Object oIsBudget = dsRequest.getFieldValue("is_budget");
			Long is_budget = (oIsBudget == null) ? null : Long
					.parseLong(oIsBudget.toString());
			contract.setIs_budget(is_budget);

			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			contract.setLoggedUserName(loggedUserName);

			Object oMainDetailId = dsRequest.getFieldValue("main_detail_id");
			Long main_detail_id = (oMainDetailId == null) ? null : Long
					.parseLong(oMainDetailId.toString());
			contract.setMain_detail_id(main_detail_id);

			Object oNote = dsRequest.getFieldValue("note");
			String note = (oNote == null) ? null : oNote.toString();
			contract.setNote(note);

			Object oPrice = dsRequest.getFieldValue("price");
			BigDecimal price = (oPrice == null) ? new BigDecimal("0")
					: new BigDecimal(oPrice.toString());
			contract.setPrice(price);

			Object oPriceType = dsRequest.getFieldValue("price_type");
			Long price_type = (oPriceType == null) ? null : Long
					.parseLong(oPriceType.toString());
			contract.setPrice_type(price_type);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			contract.setRec_date(recDate);

			contract.setRec_user(loggedUserName);

			Object oSmsWarning = dsRequest.getFieldValue("sms_warning");
			Long sms_warning = (oSmsWarning == null) ? null : Long
					.parseLong(oSmsWarning.toString());
			contract.setSms_warning(sms_warning);

			Object oPhoneListType = dsRequest.getFieldValue("phone_list_type");
			Long phone_list_type = (oPhoneListType == null) ? null : Long
					.parseLong(oPhoneListType.toString());
			contract.setPhone_list_type(phone_list_type);

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "Adding Contract.");

			Long checkContractor = new Long(dsRequest.getFieldValue(
					"checkContractor").toString());
			BigDecimal range_curr_price = new BigDecimal(dsRequest
					.getFieldValue("range_curr_price").toString());

			boolean needCalc = (price_type != null && price_type.equals(1L))
					&& (range_curr_price.doubleValue() <= 0 || checkContractor
							.intValue() == 1);

			ArrayList<ContractPriceItem> contractAdvPrices = new ArrayList<ContractPriceItem>();
			Object oMap = dsRequest.getFieldValue("contractorAdvPrices");
			if (oMap != null) {
				LinkedMap contractorAdvPrices = (LinkedMap) oMap;
				if (!contractorAdvPrices.isEmpty()) {
					Set keys1 = contractorAdvPrices.keySet();

					for (Object okey1 : keys1) {
						String key1 = okey1.toString();
						LinkedMap value1 = (LinkedMap) contractorAdvPrices
								.get(key1);
						Set keys2 = value1.keySet();
						for (Object okey2 : keys2) {
							String key2 = okey2.toString();
							Object oValue2 = value1.get(key2);
							String value2 = oValue2.toString();

							ContractPriceItem item = new ContractPriceItem();
							item.setContract_id(contract.getContract_id());
							item.setCall_count_start(new Long(key1));
							item.setCall_count_end(new Long(key2));
							item.setPrice(new BigDecimal(value2));
							contractAdvPrices.add(item);
							break;
						}
					}
				}
			}

			if (needCalc) {
				range_curr_price = getRangeCurrPrice(main_id, main_detail_id,
						oracleManager, contractAdvPrices);
			}
			contract.setRange_curr_price(range_curr_price);

			oracleManager.persist(contract);
			oracleManager.flush();

			if (contractAdvPrices != null && !contractAdvPrices.isEmpty()) {
				for (ContractPriceItem priceItem : contractAdvPrices) {
					priceItem.setContract_id(contract.getContract_id());
					oracleManager.persist(priceItem);
				}
			}

			Object oMap1 = dsRequest.getFieldValue("contractorAdvPhones");
			if (oMap1 != null) {
				LinkedMap contractorAdvPhones = (LinkedMap) oMap1;
				if (!contractorAdvPhones.isEmpty()) {
					Set keys1 = contractorAdvPhones.keySet();

					for (Object okey1 : keys1) {
						String phone = okey1.toString();
						Long deleted = new Long(contractorAdvPhones.get(okey1)
								.toString());

						ContractorPhone item = new ContractorPhone();
						item.setContract_id(contract.getContract_id());
						item.setDeleted(deleted);
						item.setLimited(0L);
						item.setMain_detail_id(0L);
						item.setMain_id(0L);
						item.setPhone(phone);
						item.setPhone_id(0L);
						item.setRec_date(recDate);
						item.setRec_user(loggedUserName);
						item.setUpd_date(recDate);
						item.setUpd_user(loggedUserName);
						oracleManager.persist(item);
					}
				}
			}

			contract = oracleManager.find(Contract.class,
					contract.getContract_id());
			contract.setLoggedUserName(loggedUserName);
			if (main_id != null) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class, main_id);
				if (mainOrg != null) {
					contract.setOrgName(mainOrg.getOrg_name());
				}
			}
			if (main_detail_id != null) {
				MainDetail mainDetail = oracleManager.find(MainDetail.class,
						main_detail_id);
				if (mainDetail != null) {
					contract.setOrgDepName(mainDetail.getMain_detail_geo());
				}
			}
			contract.setContractor_call_cnt(0L);
			contract.setPrice_type_descr((contract.getPrice_type() != null
					&& contract.getPrice_type().equals(0L) ? "მარტ." : "რთული"));
			blockUnblockContractorPhones(contract, oracleManager);
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return contract;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert Contract Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	private BigDecimal getRangeCurrPrice(Long main_id, Long main_detail_id,
			EntityManager oracleManager,
			ArrayList<ContractPriceItem> contractAdvPrices)
			throws CallCenterException {
		try {
			BigDecimal result = new BigDecimal("0");
			if (contractAdvPrices == null || contractAdvPrices.isEmpty()) {
				return result;
			}
			Long callCnt = 0L;
			System.out.println("main_detail_id = " + main_detail_id);
			System.out.println("main_id = " + main_id);
			if (main_detail_id != null && main_detail_id.longValue() > 0) {
				callCnt = new Long(oracleManager
						.createNativeQuery(
								QueryConstants.Q_GET_DEP_CALL_CNT_BY_YM)
						.setParameter(1, main_detail_id).getSingleResult()
						.toString());
			} else {
				callCnt = new Long(oracleManager
						.createNativeQuery(
								QueryConstants.Q_GET_ORG_CALL_CNT_BY_YM)
						.setParameter(1, main_id).getSingleResult().toString());
			}
			System.out.println("callCnt = " + callCnt);
			for (ContractPriceItem priceItem : contractAdvPrices) {
				Long start = priceItem.getCall_count_start();
				Long end = priceItem.getCall_count_end();
				System.out.println("start = " + start + ", end = " + end);
				if (start != null && end != null && callCnt >= start
						&& callCnt < end) {
					result = priceItem.getPrice();
					break;
				}
			}
			System.out.println("result = " + result);
			return result;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Calculate Contract Range Price : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		}
	}

	/**
	 * Updating Contract
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Contract updateContractor(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateContractor.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long contract_id = new Long(record.get("contract_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			Long main_id = new Long(record.get("main_id").toString());
			Long main_detail_id = new Long(
					record.get("main_detail_id") == null ? "0" : record.get(
							"main_detail_id").toString());
			Long block = new Long(record.get("block").toString());
			Long critical_number = new Long(record.get("critical_number")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			Timestamp start_date = new Timestamp(
					((Date) record.get("start_date")).getTime());
			Timestamp end_date = new Timestamp(
					((Date) record.get("end_date")).getTime());
			String note = record.get("note") == null ? null : record
					.get("note").toString();
			BigDecimal price = record.get("price") == null ? new BigDecimal("0")
					: new BigDecimal(record.get("price").toString());
			Long is_budget = new Long(record.get("is_budget").toString());
			Long price_type = new Long(record.get("price_type").toString());
			Long sms_warning = new Long(record.get("sms_warning").toString());
			Long phone_list_type = new Long(record.get("phone_list_type")
					.toString());

			Contract contract = oracleManager.find(Contract.class, contract_id);
			contract.setBlock(block);
			contract.setCritical_number(critical_number);
			contract.setDeleted(deleted);
			contract.setEnd_date(end_date);
			contract.setIs_budget(is_budget);
			contract.setLoggedUserName(loggedUserName);
			contract.setMain_detail_id(main_detail_id);
			contract.setMain_id(main_id);
			contract.setNote(note);
			contract.setPrice(price);
			contract.setPrice_type(price_type);
			contract.setSms_warning(sms_warning);
			contract.setStart_date(start_date);
			contract.setUpd_date(updDate);
			contract.setUpd_user(loggedUserName);
			contract.setPhone_list_type(phone_list_type);

			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Updating Contract.");

			Long checkContractor = new Long(record.get("checkContractor")
					.toString());
			BigDecimal range_curr_price = new BigDecimal(record.get(
					"range_curr_price").toString());

			boolean needCalc = (price_type != null && price_type.equals(1L))
					&& (range_curr_price.doubleValue() <= 0 || checkContractor
							.intValue() == 1);
			System.out.println("+++++++++++++++++++++++++++++++");
			System.out.println("price_type = " + price_type);
			System.out.println("range_curr_price = " + range_curr_price);
			System.out.println("checkContractor = " + checkContractor);
			System.out.println("needCalc = " + needCalc);

			ArrayList<ContractPriceItem> contractAdvPrices = new ArrayList<ContractPriceItem>();
			Object oMap = record.get("contractorAdvPrices");
			if (oMap != null) {
				LinkedMap contractorAdvPrices = (LinkedMap) oMap;
				if (!contractorAdvPrices.isEmpty()) {
					Set keys1 = contractorAdvPrices.keySet();

					for (Object okey1 : keys1) {
						String key1 = okey1.toString();
						LinkedMap value1 = (LinkedMap) contractorAdvPrices
								.get(key1);
						Set keys2 = value1.keySet();
						for (Object okey2 : keys2) {
							String key2 = okey2.toString();
							Object oValue2 = value1.get(key2);
							String value2 = oValue2.toString();

							ContractPriceItem item = new ContractPriceItem();
							item.setContract_id(contract.getContract_id());
							item.setCall_count_start(new Long(key1));
							item.setCall_count_end(new Long(key2));
							item.setPrice(new BigDecimal(value2));
							contractAdvPrices.add(item);
							break;
						}
					}
				}
			}

			if (needCalc) {
				range_curr_price = getRangeCurrPrice(main_id, main_detail_id,
						oracleManager, contractAdvPrices);
			}
			contract.setRange_curr_price(range_curr_price);

			oracleManager.merge(contract);

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PRICES)
					.setParameter(1, contract.getContract_id()).executeUpdate();
			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PHONES)
					.setParameter(1, contract.getContract_id()).executeUpdate();
			oracleManager.flush();

			if (contractAdvPrices != null && !contractAdvPrices.isEmpty()) {
				for (ContractPriceItem item : contractAdvPrices) {
					item.setContract_id(contract.getContract_id());
					oracleManager.persist(item);
				}
			}

			Object oMap1 = record.get("contractorAdvPhones");
			if (oMap1 != null) {
				LinkedMap contractorAdvPhones = (LinkedMap) oMap1;
				if (!contractorAdvPhones.isEmpty()) {
					Set keys1 = contractorAdvPhones.keySet();

					oracleManager.flush();
					for (Object okey1 : keys1) {
						String phone = okey1.toString();
						Long deleted1 = new Long(contractorAdvPhones.get(okey1)
								.toString());

						ContractorPhone item = new ContractorPhone();
						item.setContract_id(contract.getContract_id());
						item.setDeleted(deleted1);
						item.setLimited(0L);
						item.setMain_detail_id(0L);
						item.setMain_id(0L);
						item.setPhone(phone);
						item.setPhone_id(0L);
						item.setRec_date(updDate);
						item.setRec_user(loggedUserName);
						item.setUpd_date(updDate);
						item.setUpd_user(loggedUserName);
						oracleManager.persist(item);
					}
				}
			}

			contract = oracleManager.find(Contract.class, contract_id);
			contract.setLoggedUserName(loggedUserName);
			if (main_id != null) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class, main_id);
				if (mainOrg != null) {
					contract.setOrgName(mainOrg.getOrg_name());
				}
			}
			if (main_detail_id != null) {
				MainDetail mainDetail = oracleManager.find(MainDetail.class,
						main_detail_id);
				if (mainDetail != null) {
					contract.setOrgDepName(mainDetail.getMain_detail_geo());
				}
			}
			blockUnblockContractorPhones(contract, oracleManager);
			contract.setContractor_call_cnt(0L);
			contract.setPrice_type_descr((contract.getPrice_type() != null
					&& contract.getPrice_type().equals(0L) ? "მარტ." : "რთული"));
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return contract;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Contract Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating Contract Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Contract removeContractor(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.removeContractor.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long contract_id = new Long(record.get("contract_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());

			Contract contract = oracleManager.find(Contract.class, contract_id);

			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Updating Contract Status.");

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PRICES)
					.setParameter(1, contract.getContract_id()).executeUpdate();

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PHONES)
					.setParameter(1, contract.getContract_id()).executeUpdate();

			oracleManager.remove(contract);
			oracleManager.flush();

			blockUnblockContractorPhones(contract, oracleManager);
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
					"Error While Update Status for Contract Into Database : ",
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
	 * Updating Contract Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Contract updateContractorRangePrice(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateContractorRangePrice.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long contract_id = new Long(record.get("contract_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			BigDecimal range_curr_price = new BigDecimal(record.get(
					"range_curr_price").toString());
			Timestamp updDate = new Timestamp(System.currentTimeMillis());

			Contract contract = oracleManager.find(Contract.class, contract_id);

			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Updating Contract Status.");

			contract.setRange_curr_price(range_curr_price);
			oracleManager.merge(contract);
			EMF.commitTransaction(transaction);

			contract = oracleManager.find(Contract.class, contract_id);
			contract.setLoggedUserName(loggedUserName);
			if (contract.getMain_id() != null) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class,
						contract.getMain_id());
				if (mainOrg != null) {
					contract.setOrgName(mainOrg.getOrg_name());
				}
			}
			if (contract.getMain_detail_id() != null) {
				MainDetail mainDetail = oracleManager.find(MainDetail.class,
						contract.getMain_detail_id());
				if (mainDetail != null) {
					contract.setOrgDepName(mainDetail.getMain_detail_geo());
				}
			}
			contract.setContractor_call_cnt(0L);
			contract.setPrice_type_descr((contract.getPrice_type() != null
					&& contract.getPrice_type().equals(0L) ? "მარტ." : "რთული"));

			log += ". Range Price Updating Finished SuccessFully. ";
			logger.info(log);
			return contract;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Range Price for Contract Into Database : ",
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
	 * Updating Contract
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Contract blockUnBlockContractor(Map record) throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.blockUnBlockContractor.";
			oracleManager = EMF.getEntityManager();
			Long contract_id = new Long(record.get("contract_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Long main_id = new Long(record.get("main_id").toString());
			Contract contract = oracleManager.find(Contract.class, contract_id);
			contract.setLoggedUserName(loggedUserName);
			if (contract.getMain_id() != null) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class,
						contract.getMain_id());
				if (mainOrg != null) {
					contract.setOrgName(mainOrg.getOrg_name());
				}
			}
			if (contract.getMain_detail_id() != null) {
				MainDetail mainDetail = oracleManager.find(MainDetail.class,
						contract.getMain_detail_id());
				if (mainDetail != null) {
					contract.setOrgDepName(mainDetail.getMain_detail_geo());
				}
			}
			List resultList = oracleManager
					.createNativeQuery(
							QueryConstants.Q_GET_PHONE_LIST_BY_MAIN_ID)
					.setParameter(1, main_id).getResultList();
			if (resultList != null && !resultList.isEmpty()) {
				for (Object object : resultList) {
					String phone = object.toString();
					System.out.println("phone = " + phone);
				}
			}
			contract.setContractor_call_cnt(0L);
			contract.setPrice_type_descr((contract.getPrice_type() != null
					&& contract.getPrice_type().equals(0L) ? "მარტ." : "რთული"));
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return contract;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Contract Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Check Contractor Numbers
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Contract checkContractorNumbers(Map record) throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.checkContractorNumbers.";
			oracleManager = EMF.getEntityManager();
			// Object oContract_id = record.get("contract_id");
			// if (oContract_id == null) {
			// return null;
			// }
			// Long contract_id = new Long(oContract_id.toString());
			// String loggedUserName = record.get("loggedUserName").toString();
			Long main_id = new Long(record.get("main_id").toString());
			Long main_detail_id = new Long(
					record.get("main_detail_id") == null ? "0" : record.get(
							"main_detail_id").toString());
			// Contract contract = oracleManager.find(Contract.class,
			// contract_id);
			// contract.setLoggedUserName(loggedUserName);
			// if (contract.getMain_id() != null
			// && contract.getMain_id().longValue() > 0) {
			// MainOrg mainOrg = oracleManager.find(MainOrg.class,
			// contract.getMain_id());
			// if (mainOrg != null) {
			// contract.setOrgName(mainOrg.getOrg_name());
			// }
			// }
			// if (contract.getMain_detail_id() != null
			// && contract.getMain_detail_id().longValue() > 0) {
			// MainDetail mainDetail = oracleManager.find(MainDetail.class,
			// contract.getMain_detail_id());
			// if (mainDetail != null) {
			// contract.setOrgDepName(mainDetail.getMain_detail_geo());
			// }
			// }

			Object oMap1 = record.get("contractorAdvPhones");
			if (oMap1 != null && main_id != null && main_id.longValue() > 0) {
				LinkedMap contractorAdvPhones = (LinkedMap) oMap1;
				if (!contractorAdvPhones.isEmpty()) {
					Set keys1 = contractorAdvPhones.keySet();
					for (Object okey1 : keys1) {
						String phone = okey1.toString();
						if (main_detail_id != null
								&& main_detail_id.longValue() > 0) {
							Long cnt = new Long(
									oracleManager
											.createNativeQuery(
													QueryConstants.Q_CHECK_PHONE_MAIN_ORG_DET)
											.setParameter(1, main_detail_id)
											.setParameter(2, phone)
											.getSingleResult().toString());
							if (cnt.longValue() <= 0) {
								throw new CallCenterException(
										"ეს ნომერი არ ეკუთვნის ამ ორგანიზაციას (განყოფილებას) : "
												+ phone);
							}
						} else {
							Long cnt = new Long(
									oracleManager
											.createNativeQuery(
													QueryConstants.Q_CHECK_PHONE_MAIN_ORG)
											.setParameter(1, main_id)
											.setParameter(2, phone)
											.getSingleResult().toString());
							if (cnt.longValue() <= 0) {
								throw new CallCenterException(
										"ეს ნომერი არ ეკუთვნის ამ ორგანიზაციას : "
												+ phone);
							}
						}
					}
				}
			}
			// contract.setContractor_call_cnt(0L);
			log += ". Check Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Check Contract Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შემოწმებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Check Contractor Numbers
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Contract getContractorCallCnt(Map record) throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.getContractorCallCnt.";
			oracleManager = EMF.getEntityManager();
			Long contract_id = new Long(record.get("contract_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			Contract contract = oracleManager.find(Contract.class, contract_id);
			contract.setLoggedUserName(loggedUserName);
			if (contract.getMain_id() != null
					&& contract.getMain_id().longValue() > 0) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class,
						contract.getMain_id());
				if (mainOrg != null) {
					contract.setOrgName(mainOrg.getOrg_name());
				}
			}
			if (contract.getMain_detail_id() != null
					&& contract.getMain_detail_id().longValue() > 0) {
				MainDetail mainDetail = oracleManager.find(MainDetail.class,
						contract.getMain_detail_id());
				if (mainDetail != null) {
					contract.setOrgDepName(mainDetail.getMain_detail_geo());
				}
			}
			Long main_detail_id = contract.getMain_detail_id();
			Long main_id = contract.getMain_id();
			Long contrCallCnt = -1L;
			System.out.println("contrCallCnt = " + contrCallCnt);

			if (main_detail_id != null && main_detail_id.longValue() > 0) {
				contrCallCnt = new Long(
						oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_CALL_CNT_BY_CONT_AND_MAIN_DET_ID)
								.setParameter(1, contract.getContract_id())
								.setParameter(2, main_detail_id)
								.setParameter(3, main_detail_id)
								.getSingleResult().toString());
			} else {
				contrCallCnt = new Long(
						oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_CALL_CNT_BY_CONT_AND_MAIN_ID)
								.setParameter(1, contract.getContract_id())
								.setParameter(2, main_id)
								.setParameter(3, main_id)
								.setParameter(4, main_id).getSingleResult()
								.toString());
			}
			contract.setContractor_call_cnt(contrCallCnt);
			contract.setPrice_type_descr((contract.getPrice_type() != null
					&& contract.getPrice_type().equals(0L) ? "მარტ." : "რთული"));
			log += ". Getting Contractor Count Finished SuccessFully. ";
			logger.info(log);
			return contract;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Getting Contractor Count From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შემოწმებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Check Contractor Numbers
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Contract getContractorCharges(Map record) throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.getContractorCharges.";
			oracleManager = EMF.getEntityManager();
			Long contract_id = new Long(record.get("contract_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			Contract contract = oracleManager.find(Contract.class, contract_id);
			contract.setLoggedUserName(loggedUserName);
			if (contract.getMain_id() != null
					&& contract.getMain_id().longValue() > 0) {
				MainOrg mainOrg = oracleManager.find(MainOrg.class,
						contract.getMain_id());
				if (mainOrg != null) {
					contract.setOrgName(mainOrg.getOrg_name());
				}
			}
			if (contract.getMain_detail_id() != null
					&& contract.getMain_detail_id().longValue() > 0) {
				MainDetail mainDetail = oracleManager.find(MainDetail.class,
						contract.getMain_detail_id());
				if (mainDetail != null) {
					contract.setOrgDepName(mainDetail.getMain_detail_geo());
				}
			}
			Long main_detail_id = contract.getMain_detail_id();
			Long main_id = contract.getMain_id();
			Double contrCharges = -1D;
			System.out.println("contrCallCnt = " + contrCharges);

			if (main_detail_id != null && main_detail_id.longValue() > 0) {
				contrCharges = new Double(
						oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_CHARGES_BY_CONT_AND_MAIN_DET_ID)
								.setParameter(1, contract.getContract_id())
								.setParameter(2, main_detail_id)
								.setParameter(3, main_detail_id)
								.getSingleResult().toString());
			} else {
				contrCharges = new Double(
						oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_CHARGES_BY_CONT_AND_MAIN_ID)
								.setParameter(1, contract.getContract_id())
								.setParameter(2, main_id)
								.setParameter(3, main_id)
								.setParameter(4, main_id).getSingleResult()
								.toString());
			}
			contract.setContractor_charges(contrCharges);
			contract.setPrice_type_descr((contract.getPrice_type() != null
					&& contract.getPrice_type().equals(0L) ? "მარტ." : "რთული"));
			log += ". Getting Contractor Charges Finished SuccessFully. ";
			logger.info(log);
			return contract;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Getting Contractor Charges From Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შემოწმებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void blockUnblockContractorPhones(Contract contract,
			EntityManager oracleManager) throws CallCenterException {
		try {
			StringBuilder log = new StringBuilder(
					"Checking Contractor Blocking ... \n");
			if (contract == null || contract.getContract_id() == null) {
				log.append("Result : Contract Or ContractId Is Null. \n");
				logger.info(log.toString());
				return;
			}
			Long phoneListType = contract.getPhone_list_type();
			if (phoneListType == null) {
				log.append("Result : Phone List Type Is Null. \n");
				logger.info(log.toString());
				return;
			}
			Long main_id = contract.getMain_id();
			if (main_id == null) {
				log.append("Result : Main ID Is Null. \n");
				logger.info(log.toString());
				return;
			}
			Long main_detail_id = contract.getMain_detail_id();

			List resultList = null;
			if (main_detail_id != null && main_detail_id.longValue() > 0) {
				resultList = oracleManager
						.createNativeQuery(
								QueryConstants.Q_GET_MAIN_DET_PHONES_HIERARCHY_NEW)
						.setParameter(1, main_detail_id)
						.setParameter(2, main_detail_id).getResultList();
			} else {
				resultList = oracleManager
						.createNativeQuery(
								QueryConstants.Q_GET_MAIN_ORGS_PHONES_HIERARCHY_NEW)
						.setParameter(1, main_id).setParameter(2, main_id)
						.setParameter(3, main_id).getResultList();
			}

			log.append("Params : 1. Contract Id : ").append(
					contract.getContract_id());
			log.append(", 2. Main Id : ").append(main_id);
			log.append(", 3. Main Detail Id : ").append(main_detail_id);
			log.append(", 4. Phone List Type : ").append(phoneListType);

			if (resultList == null || resultList.isEmpty()) {
				log.append("\nResult : Phone List For This Contract Is Empty. \n");
				logger.info(log.toString());
				return;
			}
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
			Long deleted = contract.getDeleted();
			if (deleted != null && deleted.equals(1L)) {
				log.append("\nResult : This Contract Is Deleted. \n");
				logger.info(log.toString());
				return;
			}
			boolean blockedByCondition = false;
			Long block = contract.getBlock();
			log.append(", 5. Block Type : ").append(block);

			if (block != null && block.equals(1L)) {
				Long currDateTime = System.currentTimeMillis();
				Long startDateTime = contract.getStart_date().getTime();
				Long endDateTime = contract.getEnd_date().getTime();

				log.append(", 6. Block Date Condition : ")
						.append((currDateTime < startDateTime || currDateTime > endDateTime));

				if (currDateTime < startDateTime || currDateTime > endDateTime) {
					blockedByCondition = true;
				} else {
					Long critical_numer = contract.getCritical_number();
					if (critical_numer != null && critical_numer.intValue() > 0) {
						Long contrCallCnt = -1L;

						log.append(", 7. Contract Call's Lomit : ").append(
								contrCallCnt);

						if (main_detail_id != null
								&& main_detail_id.longValue() > 0) {
							contrCallCnt = new Long(
									oracleManager
											.createNativeQuery(
													QueryConstants.Q_GET_CALL_CNT_BY_CONT_AND_MAIN_DET_ID)
											.setParameter(1,
													contract.getContract_id())
											.setParameter(2, main_detail_id)
											.setParameter(3, main_detail_id)
											.getSingleResult().toString());
						} else {
							contrCallCnt = new Long(
									oracleManager
											.createNativeQuery(
													QueryConstants.Q_GET_CALL_CNT_BY_CONT_AND_MAIN_ID)
											.setParameter(1,
													contract.getContract_id())
											.setParameter(2, main_id)
											.setParameter(3, main_id)
											.setParameter(4, main_id)
											.getSingleResult().toString());
						}
						if ((contrCallCnt + 10) >= critical_numer) {
							blockedByCondition = true;
						}
					}
				}
			}
			log.append(", 8. blockedByCondition : ").append(blockedByCondition);

			List list = null;

			if (main_detail_id != null && main_detail_id.longValue() > 0) {
				// Block Entire Organization Department's Phones
				if (blockedByCondition) {
					list = resultList;
				} else {
					switch (phoneListType.intValue()) {
					case 1: // 1 - All Phones From Organization's Department Can
							// Call, Ignore Blocking
						break;
					case 2: // 2 - Only Listed Phones Can Call From
							// Organization's
							// Department, Block Other
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_PHONE_LIST_ONLY_CONTR_LIST)
								.setParameter(1, main_detail_id)
								.setParameter(2, main_detail_id)
								.setParameter(3, contract.getContract_id())
								.getResultList();
						break;
					case 3: // 3 - All Phones From Organization's Department Can
							// Call Except List, Block This List
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST)
								.setParameter(1, main_detail_id)
								.setParameter(2, main_detail_id)
								.setParameter(3, contract.getContract_id())
								.getResultList();
						break;
					default:
						break;
					}
				}
			} else {
				if (blockedByCondition) {
					list = resultList;
				} else {
					switch (phoneListType.intValue()) {
					case 1: // 1 - All Phones From Organization Can Call, Ignore
							// Blocking
						break;
					case 2: // 2 - Only Listed Phones Can Call From
							// Organization,
							// Block Other
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_PHONE_LIST_ONLY_CONTR_LIST1)
								.setParameter(1, main_id)
								.setParameter(2, main_id)
								.setParameter(3, main_id)
								.setParameter(4, contract.getContract_id())
								.getResultList();
						break;
					case 3: // 3 - All Phones From Organization Can Call Except
							// List, Block This List
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST1)
								.setParameter(1, main_id)
								.setParameter(2, main_id)
								.setParameter(3, main_id)
								.setParameter(4, contract.getContract_id())
								.getResultList();
						break;
					default:
						break;
					}
				}
			}

			if (list == null || list.isEmpty()) {
				log.append(", 8. blockedByCondition : ");
				log.append("\nResult : list is empty. \n");
				logger.info(log.toString());
				return;
			}

			log.append(", 9. List Size For Block : ").append(list.size());

			PreparedStatement statement1 = mySQLConnection
					.prepareStatement(QueryConstants.Q_MYSQL_INSERT_BLOCK_PHONE);

			for (Object oPhone : list) {
				String phone = oPhone.toString();
				if (!CommonFunctions.isPhoneChargeable(phone)) {
					continue;
				}
				int phoneLength = phone.length();
				statement1.setString(1, phone);
				statement1.setInt(2, phone.length());
				statement1.setInt(3, phone.length());
				statement1.executeUpdate();

				if (phoneLength == 7) {
					statement1.setString(1, ("32" + phone));
					statement1.setInt(2, (phone.length() + 2));
					statement1.setInt(3, (phone.length() + 2));
					statement1.executeUpdate();
				}
			}
			log.append("\nResult : Contractor Blocking Finished Successfully. \n");
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