package com.info08.billing.callcenterbk.server.impl.dmi;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.Country;
import com.info08.billing.callcenterbk.shared.entity.currency.Currency;
import com.info08.billing.callcenterbk.shared.entity.currency.CurrencyCourse;
import com.isomorphic.jpa.EMF;

public class CurrencyDMI implements QueryConstants {

	Logger logger = Logger.getLogger(CurrencyDMI.class.getName());

	/**
	 * Adding New RateCurr
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public Currency addCurrency(Currency currency) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addCurrency.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = currency.getLoggedUserName();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Adding Currency.");
			oracleManager.persist(currency);
			oracleManager.flush();

			currency = oracleManager.find(Currency.class,
					currency.getCurrency_id());

			Long country_id = currency.getCountry_id();
			if (country_id != null) {
				Country country = oracleManager.find(Country.class, country_id);
				if (country != null) {
					currency.setCountry_name_geo(country.getCountry_name_geo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return currency;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert RateCurr Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating RateCurr
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Currency updateCurrency(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateCurrency.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long id = new Long(record.get("currency_id").toString());
			Long country_id = record.get("country_id") == null ? null
					: new Long(record.get("country_id").toString());
			Long sort_order = record.get("sort_order") == null ? null
					: new Long(record.get("sort_order").toString());
			String code = record.get("code") == null ? null : record
					.get("code").toString();
			String name_descr = record.get("name_descr") == null ? null
					: record.get("name_descr").toString();

			Currency currency = oracleManager.find(Currency.class, id);

			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Updating Currency.");

			currency.setCountry_id(country_id);
			currency.setCode(code);
			currency.setName_descr(name_descr);
			currency.setSort_order(sort_order);

			oracleManager.merge(currency);
			oracleManager.flush();

			currency = oracleManager.find(Currency.class, id);

			if (country_id != null) {
				Country country = oracleManager.find(Country.class, country_id);
				if (country != null) {
					currency.setCountry_name_geo(country.getCountry_name_geo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return currency;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update RateCurr Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating RateCurr Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Currency removeCurrency(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateCurrencyStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing Currency.");

			Long id = new Long(record.get("currency_id").toString());

			Currency currency = oracleManager.find(Currency.class, id);

			oracleManager.remove(currency);
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
					"Error While Update Status for RateCurr Into Database : ",
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
	 * Updating Rate
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public CurrencyCourse updateCurrencyCourse(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateCurrencyCourse.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long currency_id = record.get("currency_id") == null ? null
					: new Long(record.get("currency_id").toString());
			BigDecimal bank_buy_course = record.get("bank_buy_course") == null ? null
					: new BigDecimal(record.get("bank_buy_course").toString());
			BigDecimal national_course = record.get("national_course") == null ? null
					: new BigDecimal(record.get("national_course").toString());
			Long coefficient = record.get("coefficient") == null ? null
					: new Long(record.get("coefficient").toString());
			BigDecimal bank_sell_course = record.get("bank_sell_course") == null ? null
					: new BigDecimal(record.get("bank_sell_course").toString());

			CurrencyCourse currencyCourseObject = new CurrencyCourse();
			currencyCourseObject.setCurrency_id(currency_id);
			currencyCourseObject.setBank_buy_course(bank_buy_course);
			currencyCourseObject.setNational_course(national_course);
			currencyCourseObject.setCoefficient(coefficient);
			currencyCourseObject.setBank_sell_course(bank_sell_course);

			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Updating Currency Course.");

			oracleManager.persist(currencyCourseObject);
			oracleManager.flush();

			currencyCourseObject = oracleManager.find(CurrencyCourse.class,
					currencyCourseObject.getCurrency_course_id());

			if (currency_id != null) {
				Currency currency = oracleManager.find(Currency.class,
						currency_id);
				if (currency != null) {
					currencyCourseObject.setName_descr(currency
							.getCountry_name_geo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return currencyCourseObject;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Rate Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
