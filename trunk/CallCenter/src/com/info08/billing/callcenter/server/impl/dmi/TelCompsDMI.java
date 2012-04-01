package com.info08.billing.callcenter.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;

import com.info08.billing.callcenter.client.exception.CallCenterException;
import com.info08.billing.callcenter.server.common.QueryConstants;
import com.info08.billing.callcenter.server.common.RCNGenerator;
import com.info08.billing.callcenter.shared.entity.contractors.Contract;
import com.info08.billing.callcenter.shared.entity.telcomps.TelComp;
import com.info08.billing.callcenter.shared.entity.telcomps.TelCompsInd;
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
public class TelCompsDMI implements QueryConstants {

	/**
	 * კლასი რომლის მეშვეობითაც ხდება სისტემური ინფორმაციის ლოგირება სერვერზე -
	 * ფაილში.
	 */
	private Logger logger = Logger.getLogger(TelCompsDMI.class.getName());

	/**
	 * ახალი სატელეფონო კომპანიის და მისი ინდექსების დამატების ფუნქციონალი.
	 * 
	 * @param dsRequest
	 *            ამ პარამეტრში მოდის თუ რა ინფორმაცია უნდა შეინახოს სატელეფონო
	 *            კომპანიის შესახებ
	 * @return TelComp ფუნქცია აბრუნებს ჰიბერნეიტის კლასს რომელიც უკვე შენახულია
	 *         მონაცმთა ბაზაში. ეს კლასი ბრუნდება კლიენტის მხარეს რათა კლიენტმა
	 *         დაინახოს მის მიერ ახალ დამატებული სატელეფონო კომპანია
	 * @throws Exception
	 *             შეცდომის დამუშავება.
	 */
	@SuppressWarnings("rawtypes")
	public TelComp addTelComp(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:TelCompsDMI.addTelComp.";

			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			// sysdate
			Timestamp rec_date = new Timestamp(System.currentTimeMillis());
			TelComp telComp = new TelComp();
			telComp.setDeleted(0L);
			telComp.setRec_date(rec_date);
			telComp.setLoggedUserName(loggedUserName);
			telComp.setRec_user(loggedUserName);

			Object otel_comp_name_eng = dsRequest
					.getFieldValue("tel_comp_name_eng");
			String tel_comp_name_eng = (otel_comp_name_eng == null ? null
					: otel_comp_name_eng.toString());
			telComp.setTel_comp_name_eng(tel_comp_name_eng);

			Object otel_comp_name_geo = dsRequest
					.getFieldValue("tel_comp_name_geo");
			String tel_comp_name_geo = (otel_comp_name_geo == null ? null
					: otel_comp_name_geo.toString());
			telComp.setTel_comp_name_geo(tel_comp_name_geo);
			telComp.setUpd_user(loggedUserName);

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			RCNGenerator.getInstance().initRcn(oracleManager, rec_date,
					loggedUserName, "Adding TelComp.");

			oracleManager.persist(telComp);
			oracleManager.flush();

			Object oMap = dsRequest.getFieldValue("telCompIdexes");
			if (oMap != null) {
				LinkedMap contractorAdvPrices = (LinkedMap) oMap;
				if (!contractorAdvPrices.isEmpty()) {
					Set keys1 = contractorAdvPrices.keySet();

					for (Object okey1 : keys1) {
						String str_st_ind = okey1.toString();
						LinkedMap value1 = (LinkedMap) contractorAdvPrices
								.get(str_st_ind);
						String str_end_ind = value1.get("str_end_ind")
								.toString();
						String str_cr = value1.get("str_cr").toString();

						TelCompsInd item = new TelCompsInd();
						item.setCr(new Long(str_cr));
						item.setEnd_ind(new Long(str_end_ind));
						item.setSt_ind(new Long(str_st_ind));
						item.setTel_comp_id(telComp.getTel_comp_id());
						oracleManager.persist(item);
					}
				}
			}

			telComp = oracleManager.find(TelComp.class,
					telComp.getTel_comp_id());
			telComp.setLoggedUserName(loggedUserName);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return telComp;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert TelComp Into Database : ", e);
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
	 * @return TelComp ფუნქცია აბრუნებს კონტრაქტორის კლასს რომელშიც უკვე შესულია
	 *         ცვლილება და ასახულია ბაზაში.
	 * @throws Exception
	 *             შეცდომის დამუშავება
	 */
	@SuppressWarnings("rawtypes")
	public TelComp updateTelComp(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:TelCompsDMI.updateTelComp.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long tel_comp_id = new Long(record.get("tel_comp_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp upd_date = new Timestamp(System.currentTimeMillis());
			String tel_comp_name_eng = record.get("tel_comp_name_eng") == null ? null
					: record.get("tel_comp_name_eng").toString();
			String tel_comp_name_geo = record.get("tel_comp_name_geo") == null ? null
					: record.get("tel_comp_name_geo").toString();

			TelComp telComp = oracleManager.find(TelComp.class, tel_comp_id);
			telComp.setDeleted(0L);
			telComp.setLoggedUserName(loggedUserName);
			telComp.setTel_comp_name_eng(tel_comp_name_eng);
			telComp.setTel_comp_name_geo(tel_comp_name_geo);

			RCNGenerator.getInstance().initRcn(oracleManager, upd_date,
					loggedUserName, "Updating Contract.");

			oracleManager.createNativeQuery(QueryConstants.Q_DELETE_TELCOMP)
					.setParameter(1, telComp.getTel_comp_id()).executeUpdate();

			oracleManager.merge(telComp);
			oracleManager.flush();

			Object oMap = record.get("telCompIdexes");
			if (oMap != null) {
				LinkedMap contractorAdvPrices = (LinkedMap) oMap;
				if (!contractorAdvPrices.isEmpty()) {
					Set keys1 = contractorAdvPrices.keySet();

					for (Object okey1 : keys1) {
						String str_st_ind = okey1.toString();
						LinkedMap value1 = (LinkedMap) contractorAdvPrices
								.get(str_st_ind);
						String str_end_ind = value1.get("str_end_ind")
								.toString();
						String str_cr = value1.get("str_cr").toString();

						TelCompsInd item = new TelCompsInd();
						item.setCr(new Long(str_cr));
						item.setEnd_ind(new Long(str_end_ind));
						item.setSt_ind(new Long(str_st_ind));
						item.setTel_comp_id(telComp.getTel_comp_id());
						oracleManager.persist(item);
					}
				}
			}

			telComp = oracleManager.find(TelComp.class, tel_comp_id);
			telComp.setLoggedUserName(loggedUserName);
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update TelComp Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
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
	public Contract removeTelComp(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:TelCompsDMI.removeTelComp.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long tel_comp_id = new Long(record.get("tel_comp_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());

			TelComp telComp = oracleManager.find(TelComp.class, tel_comp_id);

			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Updating Contract Status.");

			oracleManager.createNativeQuery(QueryConstants.Q_DELETE_TELCOMP)
					.setParameter(1, telComp.getTel_comp_id()).executeUpdate();
			oracleManager.remove(telComp);
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
					"Error While Update Status for TelComp Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}