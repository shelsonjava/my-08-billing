package com.info08.billing.callcenterbk.server.impl.dmi;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.persistence.EntityManager;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.common.CommonFunctions;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.entity.contractors.CorpClientPriceItems;
import com.info08.billing.callcenterbk.shared.entity.contractors.CorpClientPhones;
import com.info08.billing.callcenterbk.shared.entity.contractors.CorporateClient;
import com.info08.billing.callcenterbk.shared.entity.org.Organization;
import com.info08.billing.callcenterbk.shared.items.PhoneNumber;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DataSourceManager;
import com.isomorphic.jpa.EMF;
import com.isomorphic.sql.SQLDataSource;
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

			ArrayList<CorpClientPriceItems> contractAdvPrices = new ArrayList<CorpClientPriceItems>();
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

							CorpClientPriceItems item = new CorpClientPriceItems();

							item.setCall_count_start(new Long(key1));
							item.setCall_count_end(new Long(key2));
							item.setPrice(new BigDecimal(value2));
							contractAdvPrices.add(item);
							break;
						}
					}
				}
			}

			CorporateClient corporateClient = new CorporateClient();

			Map<?, ?> map = dsRequest.getValues();
			map.remove("contractorAdvPrices");
			DataTools.setProperties(map, corporateClient);

			String loggedUserName = map.get("loggedUserName").toString();
			corporateClient.setLoggedUserName(loggedUserName);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			Object oSmsWarning = dsRequest.getFieldValue("sms_warning");
			Long sms_warning = (oSmsWarning == null) ? null : Long
					.parseLong(oSmsWarning.toString());
			corporateClient.setSms_warning(sms_warning);

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "Adding Contract.");

			BigDecimal range_curr_price = corporateClient.getRange_curr_price();
			Long price_type = corporateClient.getPrice_type();

			Long checkContractor = corporateClient.getCheckContractor();

			boolean needCalc = (price_type != null && price_type.equals(1L))
					&& (range_curr_price.doubleValue() <= 0 || checkContractor
							.intValue() == 1);

			if (needCalc) {
				range_curr_price = getRangeCurrPrice(
						corporateClient.getCorporate_client_id(),
						oracleManager, contractAdvPrices);
			}
			corporateClient.setRange_curr_price(range_curr_price);
			if (corporateClient.getCorporate_client_id() == null)
				oracleManager.persist(corporateClient);
			else
				oracleManager.merge(corporateClient);
			oracleManager.flush();

			if (contractAdvPrices != null && !contractAdvPrices.isEmpty()) {
				for (CorpClientPriceItems priceItem : contractAdvPrices) {
					priceItem.setCorporate_client_id(corporateClient
							.getCorporate_client_id());
					oracleManager.persist(priceItem);
				}
			}
			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PRICES)
					.setParameter(1, corporateClient.getCorporate_client_id())
					.executeUpdate();
			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PHONES)
					.setParameter(1, corporateClient.getCorporate_client_id())
					.executeUpdate();
			oracleManager.flush();
			Object oMap1 = dsRequest.getFieldValue("contractorAdvPhones");
			if (oMap1 != null) {
				LinkedMap contractorAdvPhones = (LinkedMap) oMap1;
				if (!contractorAdvPhones.isEmpty()) {
					Set keys1 = contractorAdvPhones.keySet();

					for (Object okey1 : keys1) {
						String phone = okey1.toString();
						CorpClientPhones item = new CorpClientPhones();
						item.setCorporate_client_id(corporateClient
								.getCorporate_client_id());
						item.setPhone_number(phone);
						oracleManager.persist(item);
					}
				}
			}

			corporateClient = oracleManager.find(CorporateClient.class,
					corporateClient.getCorporate_client_id());
			corporateClient.setLoggedUserName(loggedUserName);
			oracleManager.flush();

			EMF.commitTransaction(transaction);
			blockUnblockContractorPhones(corporateClient, false, oracleManager);
			map = DMIUtils.findRecordById("CorporateClientsDS",
					"searchAllContractors",
					corporateClient.getCorporate_client_id(),
					"corporate_client_id");
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
	private BigDecimal getRangeCurrPrice(Long corporate_client_id,
			EntityManager oracleManager,
			ArrayList<CorpClientPriceItems> contractAdvPrices)
			throws CallCenterException {
		try {
			BigDecimal result = new BigDecimal("0");
			if (contractAdvPrices == null || contractAdvPrices.isEmpty()) {
				return result;
			}
			Long callCnt = 0L;

			callCnt = new Long(oracleManager
					.createNativeQuery(QueryConstants.Q_GET_ORG_CALL_CNT_BY_YM)
					.setParameter(1, corporate_client_id).getSingleResult()
					.toString());
			for (CorpClientPriceItems priceItem : contractAdvPrices) {
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

	@SuppressWarnings("rawtypes")
	public CorporateClient removeContractor(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			Map record = dsRequest.getOldValues();
			String log = "Method:CommonDMI.removeContractor.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long corporate_client_id = new Long(record.get(
					"corporate_client_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());

			CorporateClient corporateClient = oracleManager.find(
					CorporateClient.class, corporate_client_id);
			blockUnblockContractorPhones(corporateClient, true, oracleManager);
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Updating Contract Status.");

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PRICES)
					.setParameter(1, corporateClient.getCorporate_client_id())
					.executeUpdate();

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_CONTRACT_PHONES)
					.setParameter(1, corporateClient.getCorporate_client_id())
					.executeUpdate();

			oracleManager.remove(corporateClient);
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
	public Map<?, ?> updateContractorRangePrice(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateContractorRangePrice.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long corporate_client_id = new Long(record.get(
					"corporate_client_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			BigDecimal range_curr_price = new BigDecimal(record.get(
					"range_curr_price").toString());
			// getRangeCurrPrice(organization_id, oracleManager,
			// contractAdvPrices)
			Timestamp updDate = new Timestamp(System.currentTimeMillis());

			CorporateClient corporateClient = oracleManager.find(
					CorporateClient.class, corporate_client_id);

			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Updating Contract Status.");

			corporateClient.setRange_curr_price(range_curr_price);
			oracleManager.merge(corporateClient);
			EMF.commitTransaction(transaction);

			corporateClient = oracleManager.find(CorporateClient.class,
					corporate_client_id);
			corporateClient.setLoggedUserName(loggedUserName);
			if (corporateClient.getOrganization_id() != null) {
				Organization mainOrg = oracleManager.find(Organization.class,
						corporateClient.getOrganization_id());
				if (mainOrg != null) {
					corporateClient.setOrgName(mainOrg.getOrganization_name());
				}
			}
			corporateClient.setContractor_call_cnt(0L);
			corporateClient.setPrice_type_descr((corporateClient
					.getPrice_type() != null
					&& corporateClient.getPrice_type().equals(0L) ? "მარტ."
					: "რთული"));
			Map<?, ?> map = DMIUtils.findRecordById("CorporateClientsDS",
					"searchAllContractors",
					corporateClient.getCorporate_client_id(),
					"corporate_client_id");
			log += ". Range Price Updating Finished SuccessFully. ";
			logger.info(log);
			return map;
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
	 */
	/*
	 * @SuppressWarnings("rawtypes") public Contract blockUnBlockContractor(Map
	 * record) throws Exception { EntityManager oracleManager = null; try {
	 * String log = "Method:CommonDMI.blockUnBlockContractor."; oracleManager =
	 * EMF.getEntityManager(); Long corporate_client_id = new
	 * Long(record.get("corporate_client_id").toString()); String loggedUserName
	 * = record.get("loggedUserName").toString(); Long organization_id = new
	 * Long(record.get("organization_id") .toString()); Contract contract =
	 * oracleManager.find(Contract.class, corporate_client_id);
	 * contract.setLoggedUserName(loggedUserName); if
	 * (contract.getOrganization_id() != null) { Organization mainOrg =
	 * oracleManager.find(Organization.class, contract.getOrganization_id()); if
	 * (mainOrg != null) { contract.setOrgName(mainOrg.getOrganization_name());
	 * } } List resultList = oracleManager .createNativeQuery(
	 * QueryConstants.Q_GET_PHONE_LIST_BY_ORGANIZATION_ID) .setParameter(1,
	 * organization_id).getResultList(); if (resultList != null &&
	 * !resultList.isEmpty()) {
	 * 
	 * } contract.setContractor_call_cnt(0L);
	 * contract.setPrice_type_descr((contract.getPrice_type() != null &&
	 * contract.getPrice_type().equals(0L) ? "მარტ." : "რთული")); log +=
	 * ". Updating Finished SuccessFully. "; logger.info(log); return contract;
	 * } catch (Exception e) { if (e instanceof CallCenterException) { throw
	 * (CallCenterException) e; }
	 * logger.error("Error While Update Contract Into Database : ", e); throw
	 * new CallCenterException("შეცდომა მონაცემების შენახვისას : " +
	 * e.toString()); } finally { if (oracleManager != null) {
	 * EMF.returnEntityManager(oracleManager); } } }
	 */

	/**
	 * ფუნქცია აბრუნებს თუ რამდენი ზარი აქვს განხორციელებული კონტრაქტორს
	 * 
	 * @param record
	 *            რომელი კონტრაქტორისათვის უნდა მოხდეს დათვლა
	 * @return აბრუნებს ზარების რაოდენობას
	 * @throws Exception
	 *             შეცდომის დამუშავება
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<?, ?> getContractorCallCnt(Map record) throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.getContractorCallCnt.";
			oracleManager = EMF.getEntityManager();
			Long corporate_client_id = new Long(record.get(
					"corporate_client_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			CorporateClient corporateClient = oracleManager.find(
					CorporateClient.class, corporate_client_id);
			corporateClient.setLoggedUserName(loggedUserName);
			if (corporateClient.getOrganization_id() != null
					&& corporateClient.getOrganization_id().longValue() > 0) {
				Organization mainOrg = oracleManager.find(Organization.class,
						corporateClient.getOrganization_id());
				if (mainOrg != null) {
					corporateClient.setOrgName(mainOrg.getOrganization_name());
				}
			}
			Long contrCallCnt = -1L;

			contrCallCnt = new Long(
					oracleManager
							.createNativeQuery(
									QueryConstants.Q_GET_ORG_CALL_CNT_BY_ALL)
							.setParameter(1,
									corporateClient.getCorporate_client_id())
							.getSingleResult().toString());
			corporateClient.setContractor_call_cnt(contrCallCnt);
			corporateClient.setPrice_type_descr((corporateClient
					.getPrice_type() != null
					&& corporateClient.getPrice_type().equals(0L) ? "მარტ."
					: "რთული"));
			log += ". Getting Contractor Count Finished SuccessFully. ";
			logger.info(log);
			Map map = DMIUtils.findRecordById("CorporateClientsDS",
					"searchAllContractors",
					corporateClient.getCorporate_client_id(),
					"corporate_client_id");

			map.put("contractor_call_cnt", contrCallCnt);

			return map;
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
	 * ფუნქციის საშუალებით ხდება კონტრაქტორის მიმდინარე დავალიანების გამოთვლა.
	 * 
	 * @param record
	 *            რომელი კონტრაქტორის დავალიანების გამოთვლა უნდა მოხდეს
	 * @return აბრუნებს დავალიანებას ლარებში.
	 * @throws Exception
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<?, ?> getContractorCharges(Map record) throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.getContractorCharges.";
			oracleManager = EMF.getEntityManager();
			Long corporate_client_id = new Long(record.get(
					"corporate_client_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			CorporateClient corporateClient = oracleManager.find(
					CorporateClient.class, corporate_client_id);
			corporateClient.setLoggedUserName(loggedUserName);
			if (corporateClient.getOrganization_id() != null
					&& corporateClient.getOrganization_id().longValue() > 0) {
				Organization mainOrg = oracleManager.find(Organization.class,
						corporateClient.getOrganization_id());
				if (mainOrg != null) {
					corporateClient.setOrgName(mainOrg.getOrganization_name());
				}
			}

			Double contrCharges = -1D;

			contrCharges = new Double(oracleManager
					.createNativeQuery(
							QueryConstants.Q_GET_ORG_CALL_CNT_BY_ALL_SUM)
					.setParameter(1, corporateClient.getCorporate_client_id())
					.getSingleResult().toString());

			corporateClient.setContractor_charges(contrCharges);
			corporateClient.setPrice_type_descr((corporateClient
					.getPrice_type() != null
					&& corporateClient.getPrice_type().equals(0L) ? "მარტ."
					: "რთული"));
			log += ". Getting Contractor Charges Finished SuccessFully. ";
			logger.info(log);
			Map map = DMIUtils.findRecordById("CorporateClientsDS",
					"searchAllContractors",
					corporateClient.getCorporate_client_id(),
					"corporate_client_id");
			map.put("contractor_charges", contrCharges);
			return map;
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
	public void blockUnblockContractorPhones(CorporateClient contract,
			boolean onlyRemove, EntityManager oracleManager)
			throws CallCenterException {
		Connection mySQLConnection = null;
		try {
			StringBuilder log = new StringBuilder(
					"Checking Contractor Blocking ... \n");
			if (contract == null || contract.getCorporate_client_id() == null) {
				log.append("Result : Contract Or ContractId Is Null. \n");
				logger.info(log.toString());
				return;
			}

			Long organization_id = contract.getOrganization_id();
			if (organization_id == null) {
				log.append("Result : organization_id Is Null. \n");
				logger.info(log.toString());
				return;
			}

			List<?> resultList = oracleManager
					.createNativeQuery(QueryConstants.Q_GET_CORP_CLIENT_PHONES)
					.setParameter(1, contract.getCorporate_client_id())
					.getResultList();

			log.append("Params : 1. Contract Id : ").append(
					contract.getCorporate_client_id());
			log.append(", 2. organization_id : ").append(organization_id);

			if (resultList == null || resultList.isEmpty()) {
				log.append("\nResult : Phone List For This Contract Is Empty. \n");
				logger.info(log.toString());
				return;
			}
			SQLDataSource mySqlDS = (SQLDataSource) DataSourceManager
					.get("MySQLSubsDS");
			mySQLConnection = mySqlDS.getConnection();
			PreparedStatement statement = mySQLConnection
					.prepareStatement(QueryConstants.Q_MYSQL_DELETE_BLOCK_PHONE);

			TreeSet<String> contractPhons = new TreeSet<String>();
			for (Object object : resultList) {
				String phone = object.toString().trim();
				if (phone.length() == 0)
					continue;
				contractPhons.add(phone);
				statement.setString(1, phone);
				statement.executeUpdate();

			}
			if (onlyRemove)
				return;

			boolean blockedByCondition = oracleManager
					.createNativeQuery(
							"select have_to_block_contractor(?,?,?) from dual")
					.setParameter(1, contract.getCorporate_client_id())
					.setParameter(2, 10)
					// add_calls 10
					.setParameter(3, Constants.criticalNumberIgnore)
					.getSingleResult().toString().equals("1");

			log.append(", 8. blockedByCondition : ").append(blockedByCondition);
			if (blockedByCondition) {
				List<?> list = resultList;

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
					String phone = oPhone.toString().trim();
					if (phone.length() == 0)
						continue;
					;
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
			}
			log.append("\nResult : Contractor Blocking Finished Successfully. \n");
		} catch (Exception e) {
			try {
				mySQLConnection.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Add Or Remove Contractor Phones Into MySQL Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების ცვლილებისას : "
					+ e.toString());
		} finally {
			try {
				mySQLConnection.commit();
			} catch (Exception e2) {
			}
			try {
				mySQLConnection.close();
			} catch (Exception e2) {
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PhoneNumber> checkPhones(Map record) throws Exception {

		try {
			String log = "Method:CommonDMI.checkPhones";

			Long organization_id = new Long(record.get("organization_id")
					.toString());
			Object o = record.get("contractorAdvPhones");
			LinkedMap phones = (LinkedMap) o;

			Map<String, Long> criteria = new TreeMap<String, Long>();
			criteria.put("organization_id", organization_id);
			List<PhoneNumber> list = DMIUtils.findObjectsdByCriteria(
					"CorpClientPhonesDS", "searchPhonesHirarchy", criteria,
					PhoneNumber.class);

			List<PhoneNumber> result = new ArrayList<PhoneNumber>();
			Set<String> keys = phones.keySet();
			for (String phone : keys) {
				PhoneNumber pn = new PhoneNumber();
				pn.setPhone_number_id(0L);
				pn.setPhone(phone);
				result.add(pn);
				for (PhoneNumber phoneNumber : list) {
					if (phoneNumber.getPhone().equals(phone)) {
						pn.setPhone_number_id(1L);
						break;
					}
				}

			}

			logger.info(log);
			return result;
		} catch (Exception e) {

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("checkPhones: ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		}
	}
}