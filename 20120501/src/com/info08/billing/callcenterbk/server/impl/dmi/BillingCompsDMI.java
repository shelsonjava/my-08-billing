package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.billingcomps.BillingCompany;
import com.info08.billing.callcenterbk.shared.entity.billingcomps.BillingCompanyInd;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;

/**
 * ეს კლასი გამოიყენება ქალაქის სატელეფონო კომპანიების და მათი ინდექსების
 * საზღვრების დასამუშავებლად და შესანახად მონაცემთა ბაზაში.
 * 
 * @author პაატა ლომინაძე
 * @version 1.0.0.1
 * @since 1.0.0.1
 * 
 */
public class BillingCompsDMI implements QueryConstants {

	/**
	 * კლასი რომლის მეშვეობითაც ხდება სისტემური ინფორმაციის ლოგირება სერვერზე -
	 * ფაილში.
	 */
	private Logger logger = Logger.getLogger(BillingCompsDMI.class.getName());

	/**
	 * ახალი სატელეფონო კომპანიის და მისი ინდექსების დამატების ფუნქციონალი.
	 * 
	 * @param dsRequest
	 *            ამ პარამეტრში მოდის თუ რა ინფორმაცია უნდა შეინახოს სატელეფონო
	 *            კომპანიის შესახებ
	 * @return BillingComp ფუნქცია აბრუნებს ჰიბერნეიტის კლასს რომელიც უკვე
	 *         შენახულია მონაცმთა ბაზაში. ეს კლასი ბრუნდება კლიენტის მხარეს რათა
	 *         კლიენტმა დაინახოს მის მიერ ახალ დამატებული სატელეფონო კომპანია
	 * @throws Exception
	 *             შეცდომის დამუშავება.
	 */
	@SuppressWarnings("rawtypes")
	public BillingCompany addBillingComp(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:BillingCompsDMI.addBillingComp.";

			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();

			// sysdate
			Timestamp rec_date = new Timestamp(System.currentTimeMillis());

			BillingCompany billingComp = new BillingCompany();
			billingComp.setLoggedUserName(loggedUserName);

			Object obilling_company_name = dsRequest
					.getFieldValue("billing_company_name");
			String billing_company_name = (obilling_company_name == null ? null
					: obilling_company_name.toString());
			billingComp.setBilling_company_name(billing_company_name);

			Object oour_percent = dsRequest.getFieldValue("our_percent");
			Double our_percent = (oour_percent == null ? 1L : new Double(
					oour_percent.toString()));
			billingComp.setOur_percent(our_percent);

			Object ocall_price = dsRequest.getFieldValue("call_price");
			Double call_price = (ocall_price == null ? 1L : new Double(
					ocall_price.toString()));
			billingComp.setCall_price(call_price);

			Object ohas_calculation = dsRequest
					.getFieldValue("has_calculation");
			Long has_calculation = (ohas_calculation == null ? -1L : new Long(
					ohas_calculation.toString()));
			billingComp.setHas_calculation(has_calculation);

			Long operator_src = Long.parseLong(dsRequest.getFieldValue(
					"operator_src").toString());
			Long mobile_company = Long.parseLong(dsRequest.getFieldValue(
					"mobile_company").toString());

			Object mobile_company_name_o = dsRequest
					.getFieldValue("mobile_company_name");
			String mobile_company_name = (mobile_company_name_o == null ? null
					: mobile_company_name_o.toString());

			billingComp.setOperator_src(operator_src);
			billingComp.setMobile_company(mobile_company);
			billingComp.setMobile_company_name(mobile_company_name);

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			RCNGenerator.getInstance().initRcn(oracleManager, rec_date,
					loggedUserName, "Adding BillingComp.");

			oracleManager.persist(billingComp);
			oracleManager.flush();

			Object oMap = dsRequest.getFieldValue("billingCompIdexes");
			if (oMap != null) {
				LinkedMap indexed = (LinkedMap) oMap;
				if (!indexed.isEmpty()) {
					Set keys1 = indexed.keySet();
					checkBillingCompIndexes(null, operator_src, indexed);
					for (Object okey1 : keys1) {
						String str_bill_index_start = okey1.toString();
						LinkedMap value1 = (LinkedMap) indexed
								.get(str_bill_index_start);
						String str_bill_index_end = value1.get(
								"str_bill_index_end").toString();
						String str_applied_wholly = value1.get(
								"str_applied_wholly").toString();
						String str_calcul_type = value1.get("str_calcul_type")
								.toString();

						BillingCompanyInd item = new BillingCompanyInd();
						item.setApplied_wholly(new Long(str_applied_wholly));
						item.setBill_index_end(new Long(str_bill_index_end));
						item.setBill_index_start(new Long(str_bill_index_start));
						item.setCalcul_type(new Long(str_calcul_type));
						item.setBilling_company_id(billingComp
								.getbilling_company_id());
						oracleManager.persist(item);
					}
				}
			}

			billingComp = oracleManager.find(BillingCompany.class,
					billingComp.getbilling_company_id());
			billingComp.setLoggedUserName(loggedUserName);
			if (has_calculation.equals(1L)) {
				billingComp.setHas_calculation_descr("დიახ");
			} else if (has_calculation.equals(0L)) {
				billingComp.setHas_calculation_descr("არა");
			} else {
				billingComp.setHas_calculation_descr("ყველა");
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return billingComp;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert BillingComp Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * სატელეფონო კომპანიის და მისი ინდექსების ინფორმაციის განახლება.
	 * 
	 * @param record
	 *            ეს პარამეტრი მოიცავს ყველა ინფორმაცის ცვლილების შესახებ.
	 * @return BillingComp ფუნქცია აბრუნებს კონტრაქტორის კლასს რომელშიც უკვე
	 *         შესულია ცვლილება და ასახულია ბაზაში.
	 * @throws Exception
	 *             შეცდომის დამუშავება
	 */
	@SuppressWarnings("rawtypes")
	public BillingCompany updateBillingComp(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:BillingCompsDMI.updateBillingComp.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long billing_company_id = new Long(record.get("billing_company_id")
					.toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp upd_date = new Timestamp(System.currentTimeMillis());

			String billing_company_name = record.get("billing_company_name") == null ? null
					: record.get("billing_company_name").toString();
			Object oour_percent = record.get("our_percent");
			Double our_percent = (oour_percent == null ? 1L : new Double(
					oour_percent.toString()));

			Object ocall_price = record.get("call_price");
			Double call_price = (ocall_price == null ? 1L : new Double(
					ocall_price.toString()));

			Object ohas_calculation = record.get("has_calculation");
			Long has_calculation = (ohas_calculation == null ? -1L : new Long(
					ohas_calculation.toString()));

			Long operator_src = Long.parseLong(record.get("operator_src")
					.toString());
			Long mobile_company = Long.parseLong(record.get("mobile_company")
					.toString());

			Object mobile_company_name_o = record.get("mobile_company_name");
			String mobile_company_name = (mobile_company_name_o == null ? null
					: mobile_company_name_o.toString());

			BillingCompany billingComp = oracleManager.find(
					BillingCompany.class, billing_company_id);
			billingComp.setLoggedUserName(loggedUserName);
			billingComp.setBilling_company_name(billing_company_name);
			billingComp.setOur_percent(our_percent);
			billingComp.setCall_price(call_price);
			billingComp.setHas_calculation(has_calculation);
			billingComp.setOperator_src(operator_src);
			billingComp.setMobile_company(mobile_company);
			billingComp.setMobile_company_name(mobile_company_name);

			RCNGenerator.getInstance().initRcn(oracleManager, upd_date,
					loggedUserName, "Updating Contract.");

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_BILLINGCOMP_IND)
					.setParameter(1, billingComp.getbilling_company_id())
					.executeUpdate();

			oracleManager.merge(billingComp);
			oracleManager.flush();

			Object oMap = record.get("billingCompIdexes");
			if (oMap != null) {
				LinkedMap indexed = (LinkedMap) oMap;
				if (!indexed.isEmpty()) {
					checkBillingCompIndexes(billing_company_id, operator_src,
							indexed);
					Set keys1 = indexed.keySet();
					for (Object okey1 : keys1) {
						String str_st_ind = okey1.toString();
						LinkedMap value1 = (LinkedMap) indexed.get(str_st_ind);
						String str_bill_index_end = value1.get(
								"str_bill_index_end").toString();
						String str_applied_wholly = value1.get(
								"str_applied_wholly").toString();
						String str_calcul_type = value1.get("str_calcul_type")
								.toString();

						BillingCompanyInd item = new BillingCompanyInd();
						item.setApplied_wholly(new Long(str_applied_wholly));
						item.setBill_index_end(new Long(str_bill_index_end));
						item.setBill_index_start(new Long(str_st_ind));
						item.setCalcul_type(new Long(str_calcul_type));
						item.setBilling_company_id(billingComp
								.getbilling_company_id());
						oracleManager.persist(item);
					}
				}
			}

			billingComp = oracleManager.find(BillingCompany.class,
					billing_company_id);
			billingComp.setLoggedUserName(loggedUserName);
			if (has_calculation.equals(1L)) {
				billingComp.setHas_calculation_descr("დიახ");
			} else if (has_calculation.equals(0L)) {
				billingComp.setHas_calculation_descr("არა");
			} else {
				billingComp.setHas_calculation_descr("ყველა");
			}
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return billingComp;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update BillingComp Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void checkBillingCompIndexes(Long billing_company_id,
			Long operator_src, LinkedMap indexes) throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:BillingCompsDMI.checkBillingCompIndexes.";
			oracleManager = EMF.getEntityManager();
			if (!indexes.isEmpty()) {
				Set keys1 = indexes.keySet();

				if (billing_company_id == null) {
					billing_company_id = -111111111L;
				}

				for (Object okey1 : keys1) {
					String str_st_ind = okey1.toString();
					LinkedMap value1 = (LinkedMap) indexes.get(str_st_ind);
					String str_bill_index_end = value1
							.get("str_bill_index_end").toString();
					Long st_ind = new Long(str_st_ind);
					Long end_ind = new Long(str_bill_index_end);

					Long count = 0L;
					if (billing_company_id.equals(-111111111L)) {
						count = new Long(oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_BILLING_COMP_IND)
								.setParameter(1, st_ind)
								.setParameter(2, operator_src)
								.getSingleResult().toString());
					} else {
						count = new Long(
								oracleManager
										.createNativeQuery(
												QueryConstants.Q_GET_BILLING_COMP_IND_BY_ID)
										.setParameter(1, st_ind)
										.setParameter(2, billing_company_id)
										.setParameter(3, operator_src)
										.getSingleResult().toString());
					}
					if (count.longValue() > 0) {
						throw new CallCenterException(
								"მსგავსი ინდექსი უკვე რეგისტრირებულია : "
										+ st_ind);
					}
					if (billing_company_id.equals(-111111111L)) {
						count = new Long(oracleManager
								.createNativeQuery(
										QueryConstants.Q_GET_BILLING_COMP_IND)
								.setParameter(1, end_ind)
								.setParameter(2, operator_src)
								.getSingleResult().toString());
					} else {
						count = new Long(
								oracleManager
										.createNativeQuery(
												QueryConstants.Q_GET_BILLING_COMP_IND_BY_ID)
										.setParameter(1, end_ind)
										.setParameter(2, billing_company_id)
										.setParameter(3, operator_src)
										.getSingleResult().toString());
					}
					if (count.longValue() > 0) {
						throw new CallCenterException(
								"მსგავსი ინდექსი უკვე რეგისტრირებულია : "
										+ end_ind);
					}
				}
			}
			log += ". Check Finished SuccessFully. ";
			logger.info(log);
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Checking BillingComp Indexes Into Database : ",
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
	 * სატელეფონო კომპანიის და მისი ინდექსების ინფორმაციის გაუქმების ფუნქცია.
	 * 
	 * @param record
	 *            პარამეტრი რომელშიც მოდის თუ რომელი სატელეფონო კომპანა და მისი
	 *            ინდექსების ინფორმაცა უნდა წაიშალოს მონაცემთა ბაზიდან
	 * @return Contract აბრუნებს ცარიელი კონტრაქტის ობიექს რადგან ინფორმაცია
	 *         უკვე წაშლილია
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public BillingCompany removeBillingComp(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			Map oldValue = dsRequest.getOldValues();
			Long billing_company_id = new Long(dsRequest.getFieldValue(
					"billing_company_id").toString());
			String loggedUserName = oldValue.get("loggedUserName").toString();

			String log = "Method:BillingCompsDMI.removeBillingComp.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp updDate = new Timestamp(System.currentTimeMillis());

			BillingCompany billingComp = oracleManager.find(
					BillingCompany.class, billing_company_id);

			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Remove BillingComp.");

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_BILLINGCOMP_IND)
					.setParameter(1, billingComp.getbilling_company_id())
					.executeUpdate();
			oracleManager.remove(billingComp);

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
			logger.error("Error While Remove BillingComps From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წაშლისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}