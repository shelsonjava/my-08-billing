package com.info08.billing.callcenterbk.server.impl.dmi;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.contractors.Contract;
import com.info08.billing.callcenterbk.shared.entity.contractors.ContractPriceItem;
import com.info08.billing.callcenterbk.shared.entity.contractors.ContractorPhone;
import com.info08.billing.callcenterbk.shared.entity.org.Organization;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;
import com.isomorphic.util.DataTools;

/**
 * ეს კლასი გამოიყენება კონტრაქტორების მონაცემების დასამუშავებლად და შესანახად
 * მონაცემთა ბაზაში.
 * 
 * @author პაატა ლომინაძე
 * @version 1.0.0.1
 * @since 1.0.0.1
 * 
 */
public class ContractorsDMI implements QueryConstants {

	/**
	 * კლასი რომლის მეშვეობითაც ხდება სისტემური ინფორმაციის ლოგირება სერვერზე
	 * ფაილში.
	 */
	protected Logger logger = Logger.getLogger(ContractorsDMI.class.getName());

	/**
	 * ახალი კონტრაქტორის დამატების ფუნქციონალი.
	 * 
	 * @param dsRequest
	 *            ამ პარამეტრში მოდის თუ რა ინფორმაცია უნდა შეინახოს კონტრატორის
	 *            შესახებ
	 * @return Contract ფუნქცია აბრუნებს ჰიბერნეიტის კლასს რომელიც უკვე
	 *         შენახულია მონაცმთა ბაზაში. ეს კლასი ბრუნდება კლიენტის მხარეს რათა
	 *         კლიენტმა დაინახოს მის მიერ ახალ დამატებული კონტრაქტორი
	 * @throws Exception
	 *             შეცდომის დამუშავება.
	 */
	@SuppressWarnings("rawtypes")
	public Map<?, ?> addEditContractor(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addContractor.";

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
							
							item.setCall_count_start(new Long(key1));
							item.setCall_count_end(new Long(key2));
							item.setPrice(new BigDecimal(value2));
							contractAdvPrices.add(item);
							break;
						}
					}
				}
			}			
			
			
			Contract contract = new Contract();

			Map<?, ?> map = dsRequest.getValues();
			map.remove("contractorAdvPrices");
			DataTools.setProperties(map, contract);

			String loggedUserName = map.get("loggedUserName").toString();
			contract.setLoggedUserName(loggedUserName);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			Object oSmsWarning = dsRequest.getFieldValue("sms_warning");
			Long sms_warning = (oSmsWarning == null) ? null : Long
					.parseLong(oSmsWarning.toString());
			contract.setSms_warning(sms_warning);

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "Adding Contract.");

			Long checkContractor = new Long(dsRequest.getFieldValue(
					"checkContractor").toString());
			BigDecimal range_curr_price = new BigDecimal(dsRequest
					.getFieldValue("range_curr_price").toString());

			Long price_type = contract.getPrice_type();

//			boolean needCalc = (price_type != null && price_type.equals(1L))
//					&& (range_curr_price.doubleValue() <= 0 || checkContractor
//							.intValue() == 1);

			

//			if (needCalc) {
//				range_curr_price = getRangeCurrPrice(
//						contract.getOrganization_id(), oracleManager,
//						contractAdvPrices);
//			}
//			contract.setRange_curr_price(range_curr_price);
			if (contract.getContract_id() == null)
				oracleManager.persist(contract);
			else
				oracleManager.merge(contract);
			oracleManager.flush();

			if (contractAdvPrices != null && !contractAdvPrices.isEmpty()) {
				for (ContractPriceItem priceItem : contractAdvPrices) {
					priceItem.setContract_id(contract.getContract_id());
					oracleManager.persist(priceItem);
				}
			}
			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PRICES)
					.setParameter(1, contract.getContract_id()).executeUpdate();
			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PHONES)
					.setParameter(1, contract.getContract_id()).executeUpdate();
			oracleManager.flush();
			Object oMap1 = dsRequest.getFieldValue("contractorAdvPhones");
			if (oMap1 != null) {
				LinkedMap contractorAdvPhones = (LinkedMap) oMap1;
				if (!contractorAdvPhones.isEmpty()) {
					Set keys1 = contractorAdvPhones.keySet();

					for (Object okey1 : keys1) {
						String phone = okey1.toString();
						ContractorPhone item = new ContractorPhone();
						item.setContract_id(contract.getContract_id());
						item.setPhone(phone);
						oracleManager.persist(item);
					}
				}
			}

			contract = oracleManager.find(Contract.class,
					contract.getContract_id());
			contract.setLoggedUserName(loggedUserName);
			oracleManager.flush();
			blockUnblockContractorPhones(contract, oracleManager);
			EMF.commitTransaction(transaction);
			map = DMIUtils.findRecordById("ContractorsDS",
					"searchAllContractors", contract.getContract_id(),
					"contract_id");
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return map;
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

	/**
	 * მეთოდი რომელიც გამოიყენება ლოკალურად მხოლოდ მიმდინარე კლასში და
	 * განკუთვნილია კონტრაქტორის ფასის გამოსათვლელად რომელსაც აქვს ზღვრული ფასი.
	 * ანუ მაგალითად (1 დან 1000 ზარამდე - ზარის ფასია 0.77, 1001 დან 5000 მდე
	 * 0.70, 5001 დან 999999999 მდე არის 0.65, ფუნქცია ანგარიშობს მიმდინარე
	 * თვეში რა ფასი ეკუთვნის კონტრაქტორს განხორციელებული ზარებიდან გამომდინარე)
	 * 
	 * @param organization_id
	 *            კონტრაქტორი ორგანიზაციის იდენტიფიკატორი
	 * @param main_detail_id
	 *            კონტრაქტორი ორგანიზაციის განყოფილების იდენტიფიკატორი
	 * @param oracleManager
	 *            ბაზის მენეჯერი რომლის საშუალებითახ ხდება ნებისმიერო ოპერაცია
	 *            მონაცემთა ბაზაში
	 * @param contractAdvPrices
	 *            კონტრაქტორის ზღვრული ფასები
	 * @return BigDecimal აბრუნებს ფასს
	 * @throws CallCenterException
	 *             შეცდომის დამუშავება
	 */
	private BigDecimal getRangeCurrPrice(Long organization_id,
			EntityManager oracleManager,
			ArrayList<ContractPriceItem> contractAdvPrices)
			throws CallCenterException {
		try {
			BigDecimal result = new BigDecimal("0");
			if (contractAdvPrices == null || contractAdvPrices.isEmpty()) {
				return result;
			}
			Long callCnt = 0L;

			callCnt = new Long(oracleManager
					.createNativeQuery(QueryConstants.Q_GET_ORG_CALL_CNT_BY_YM)
					.setParameter(1, organization_id).getSingleResult()
					.toString());
			for (ContractPriceItem priceItem : contractAdvPrices) {
				Long start = priceItem.getCall_count_start();
				Long end = priceItem.getCall_count_end();
				if (start != null && end != null && callCnt >= start
						&& callCnt < end) {
					result = priceItem.getPrice();
					break;
				}
			}
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
	 * კონტრაქტორის ინფორმაციის განახლება.
	 * 
	 * @param record
	 *            ეს პარამეტრი მოიცავს ყველა ინფორმაცის ცვლილების შესახებ.
	 * @return Contract ფუნქცია აბრუნებს კონტრაქტორის კლასს რომელშიც უკვე
	 *         შესულია ცვლილება და ასახულია ბაზაში.
	 * @throws Exception
	 *             შეცდომის დამუშავება
	 */
	
	/**
	 * კონტრაქტორის გაუმქნების ფუნქცია.
	 * 
	 * @param record
	 *            პარამეტრი რომელშიც მოდის თუ რომელი კონტრაქტორი უნდა წაიშალოს
	 *            მონაცემთა ბაზიდან
	 * @return
	 * @throws Exception
	 */
	/*
	 * @SuppressWarnings("rawtypes") public Contract removeContractor(Map
	 * record) throws Exception { EntityManager oracleManager = null; Object
	 * transaction = null; try { String log =
	 * "Method:CommonDMI.removeContractor."; oracleManager =
	 * EMF.getEntityManager(); transaction = EMF.getTransaction(oracleManager);
	 * 
	 * Long contract_id = new Long(record.get("contract_id").toString()); String
	 * loggedUserName = record.get("loggedUserName").toString(); Timestamp
	 * updDate = new Timestamp(System.currentTimeMillis());
	 * 
	 * Contract contract = oracleManager.find(Contract.class, contract_id);
	 * 
	 * RCNGenerator.getInstance().initRcn(oracleManager, updDate,
	 * loggedUserName, "Updating Contract Status.");
	 * 
	 * oracleManager .createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PRICES)
	 * .setParameter(1, contract.getContract_id()).executeUpdate();
	 * 
	 * oracleManager .createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PHONES)
	 * .setParameter(1, contract.getContract_id()).executeUpdate();
	 * 
	 * oracleManager.remove(contract); oracleManager.flush();
	 * 
	 * blockUnblockContractorPhones(contract, oracleManager);
	 * EMF.commitTransaction(transaction); log +=
	 * ". Status Updating Finished SuccessFully. "; logger.info(log); return
	 * null; } catch (Exception e) { EMF.rollbackTransaction(transaction); if (e
	 * instanceof CallCenterException) { throw (CallCenterException) e; }
	 * logger.error( "Error While Update Status for Contract Into Database : ",
	 * e); throw new CallCenterException("შეცდომა მონაცემების შენახვისას : " +
	 * e.toString()); } finally { if (oracleManager != null) {
	 * EMF.returnEntityManager(oracleManager); } } }
	 */
	/**
	 * მიმდინარე ფუნქცია გამოიყენება იმისათვის რომ მოხდეს ზღვრული ფასების მქონე
	 * კონტრაქტორის მიმდინარე ფასის ცვლილება.
	 * 
	 * @param record
	 *            პარამეტრად მოდის თუ რომელ კონტრაქტორს უნდა შეეცვალოს ფასი და
	 *            რა მნიშვნელობა უნდა მიენიჭოს.
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
			if (contract.getOrganization_id() != null) {
				Organization mainOrg = oracleManager.find(Organization.class,
						contract.getOrganization_id());
				if (mainOrg != null) {
					contract.setOrgName(mainOrg.getOrganization_name());
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
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 *//*
	@SuppressWarnings("rawtypes")
	public Contract blockUnBlockContractor(Map record) throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.blockUnBlockContractor.";
			oracleManager = EMF.getEntityManager();
			Long contract_id = new Long(record.get("contract_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Long organization_id = new Long(record.get("organization_id")
					.toString());
			Contract contract = oracleManager.find(Contract.class, contract_id);
			contract.setLoggedUserName(loggedUserName);
			if (contract.getOrganization_id() != null) {
				Organization mainOrg = oracleManager.find(Organization.class,
						contract.getOrganization_id());
				if (mainOrg != null) {
					contract.setOrgName(mainOrg.getOrganization_name());
				}
			}
			List resultList = oracleManager
					.createNativeQuery(
							QueryConstants.Q_GET_PHONE_LIST_BY_ORGANIZATION_ID)
					.setParameter(1, organization_id).getResultList();
			if (resultList != null && !resultList.isEmpty()) {

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
*/
	/**
	 * ხდება შემოწმება თუ რამდენად სწორედ შეიყვანა ოპერატორმა კონტრაქტორის
	 * ნომრები - ეკუთვნის თუ არა ეს ნომრების არჩეულ ორგანიზაციას(დეპარტამენტს).
	 * 
	 * @param record
	 *            ნომრები რომლებიც უნდა შემოწმდეს
	 * @return აბრუნებს კონტრაქტორის ჩანაწერს
	 * @throws Exception
	 *             გამოისვის შეცდომას თუ რომელიმე ნომერი არ ეკუთვბნის ამ
	 *             ორგანიზაციას(დეპარტამენტს)
	 */
	@SuppressWarnings("rawtypes")
	public Contract checkContractorNumbers(Map record) throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.checkContractorNumbers.";
			oracleManager = EMF.getEntityManager();
			Long organization_id = new Long(record.get("organization_id")
					.toString());
			Long main_detail_id = new Long(
					record.get("main_detail_id") == null ? "0" : record.get(
							"main_detail_id").toString());

			Object oMap1 = record.get("contractorAdvPhones");
			if (oMap1 != null && organization_id != null
					&& organization_id.longValue() > 0) {
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
											.setParameter(1, organization_id)
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
	 * ფუნქცია აბრუნებს თუ რამდენი ზარი აქვს განხორციელებული კონტრაქტორს
	 * 
	 * @param record
	 *            რომელი კონტრაქტორისათვის უნდა მოხდეს დათვლა
	 * @return აბრუნებს ზარების რაოდენობას
	 * @throws Exception
	 *             შეცდომის დამუშავება
	 *//*

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
			if (contract.getOrganization_id() != null
					&& contract.getOrganization_id().longValue() > 0) {
				Organization mainOrg = oracleManager.find(Organization.class,
						contract.getOrganization_id());
				if (mainOrg != null) {
					contract.setOrgName(mainOrg.getOrganization_name());
				}
			}
			Long organization_id = contract.getOrganization_id();
			Long contrCallCnt = -1L;

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
										QueryConstants.Q_GET_CALL_CNT_BY_CONT_AND_ORGANIZATION_ID)
								.setParameter(1, contract.getContract_id())
								.setParameter(2, organization_id)
								.setParameter(3, organization_id)
								.setParameter(4, organization_id)
								.getSingleResult().toString());
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
	}*/

	/**
	 * ფუნქციის საშუალებით ხდება კონტრაქტორის მიმდინარე დავალიანების გამოთვლა.
	 * 
	 * @param record
	 *            რომელი კონტრაქტორის დავალიანების გამოთვლა უნდა მოხდეს
	 * @return აბრუნებს დავალიანებას ლარებში.
	 * @throws Exception
	 *//*
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
			if (contract.getOrganization_id() != null
					&& contract.getOrganization_id().longValue() > 0) {
				Organization mainOrg = oracleManager.find(Organization.class,
						contract.getOrganization_id());
				if (mainOrg != null) {
					contract.setOrgName(mainOrg.getOrganization_name());
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
			Long organization_id = contract.getOrganization_id();
			Double contrCharges = -1D;

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
										QueryConstants.Q_GET_CHARGES_BY_CONT_AND_ORGANIZATION_ID)
								.setParameter(1, contract.getContract_id())
								.setParameter(2, organization_id)
								.setParameter(3, organization_id)
								.setParameter(4, organization_id)
								.getSingleResult().toString());
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
	}*/

	/**
	 * ფუნქცია გამოიყენება ლოკალურად მხოლოდ ამ კლასში და უზრუნველყოფს
	 * კონტრაქტორის ნომრების მართვას ე.წ. SIP ის ბაზაში. ამ ფუნქციის საშუალებით
	 * ხდება ნომრების დაბლოკვა რომ ვერ დარეკოს 11808 ზე გადაუხდელობის ან კიდევ
	 * კონტრაქტის ვადის გასვლის შემთხვევაში. ასევე განბლოკვა თანხის გადახდის ან
	 * კონტრაქტის ვადის გაგრძელების შემთხვევაში.
	 * 
	 * @param contract
	 *            კონტრატორის მონაცემები.
	 * @param oracleManager
	 *            მონაცემთა ბაზის მენეჯერი.
	 * @throws CallCenterException
	 *             შეცდომის დამუშავება თუ რაიმე პარამეტრი არასწორია
	 */
	public void blockUnblockContractorPhones(Contract contract,
			EntityManager oracleManager) throws CallCenterException {
		/*try {
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
			Long organization_id = contract.getOrganization_id();
			if (organization_id == null) {
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
						.setParameter(1, organization_id)
						.setParameter(2, organization_id)
						.setParameter(3, organization_id).getResultList();
			}

			log.append("Params : 1. Contract Id : ").append(
					contract.getContract_id());
			log.append(", 2. Main Id : ").append(organization_id);
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
					if (critical_numer != null
							&& critical_numer.intValue() != Constants.criticalNumberIgnore) {
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
													QueryConstants.Q_GET_CALL_CNT_BY_CONT_AND_ORGANIZATION_ID)
											.setParameter(1,
													contract.getContract_id())
											.setParameter(2, organization_id)
											.setParameter(3, organization_id)
											.setParameter(4, organization_id)
											.getSingleResult().toString());
						}
						if (contrCallCnt <= 0
								|| (contrCallCnt + 10) >= critical_numer) {
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
								.setParameter(1, organization_id)
								.setParameter(2, organization_id)
								.setParameter(3, organization_id)
								.setParameter(4, contract.getContract_id())
								.getResultList();
						break;
					case 3: // 3 - All Phones From Organization Can Call Except
							// List, Block This List
						oracleManager.flush();
						list = oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST1)
								.setParameter(1, organization_id)
								.setParameter(2, organization_id)
								.setParameter(3, organization_id)
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
		}*/
	}
}