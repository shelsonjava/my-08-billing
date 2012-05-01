package com.info08.billing.callcenterbk.server.impl.dmi;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.entity.Country;
import com.info08.billing.callcenterbk.shared.entity.currency.Rate;
import com.info08.billing.callcenterbk.shared.entity.currency.RateCurr;
import com.info08.billing.callcenterbk.shared.entity.currency.RateLog;
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
	public RateCurr addRateCurr(RateCurr rateCurr) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addRateCurr.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = rateCurr.getLoggedUserName();
			rateCurr.setRec_date(recDate);
			rateCurr.setRec_user(loggedUserName);
			rateCurr.setCustom_order(1000L);

			oracleManager.persist(rateCurr);
			oracleManager.flush();

			rateCurr = oracleManager
					.find(RateCurr.class, rateCurr.getCurr_id());

			rateCurr.setLoggedUserName(loggedUserName);
			Long country_id = rateCurr.getCountry_id();
			if (country_id != null) {
				Country country = oracleManager.find(Country.class, country_id);
				if (country != null) {
					rateCurr.setCountry_name_geo(country.getCountry_name_geo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return rateCurr;
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
	public RateCurr updateRateCurr(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateRateCurr.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long curr_id = new Long(record.get("curr_id").toString());
			Long country_id = record.get("country_id") == null ? null
					: new Long(record.get("country_id").toString());
			Long curr_order = record.get("curr_order") == null ? null
					: new Long(record.get("curr_order").toString());
			String curr_abbrev = record.get("curr_abbrev") == null ? null
					: record.get("curr_abbrev").toString();
			String curr_name_geo = record.get("curr_name_geo") == null ? null
					: record.get("curr_name_geo").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			RateCurr rateCurr = oracleManager.find(RateCurr.class, curr_id);

			rateCurr.setCountry_id(country_id);
			rateCurr.setUpd_user(loggedUserName);
			rateCurr.setCurr_abbrev(curr_abbrev);
			rateCurr.setUpd_date(currDate);
			rateCurr.setCurr_name_geo(curr_name_geo);
			rateCurr.setCurr_order(curr_order);

			oracleManager.merge(rateCurr);
			oracleManager.flush();

			rateCurr = oracleManager.find(RateCurr.class, curr_id);

			rateCurr.setUpd_user(loggedUserName);
			if (country_id != null) {
				Country country = oracleManager.find(Country.class, country_id);
				if (country != null) {
					rateCurr.setCountry_name_geo(country.getCountry_name_geo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return rateCurr;
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
	public RateCurr updateRateCurrStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateRateCurrStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp curr_date = new Timestamp(System.currentTimeMillis());
			Long curr_id = new Long(record.get("curr_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			RateCurr rateCurr = oracleManager.find(RateCurr.class, curr_id);

			rateCurr.setDeleted(deleted);
			rateCurr.setUpd_user(loggedUserName);
			rateCurr.setUpd_date(curr_date);

			oracleManager.merge(rateCurr);
			oracleManager.flush();

			rateCurr = oracleManager.find(RateCurr.class, curr_id);

			rateCurr.setLoggedUserName(loggedUserName);
			Long country_id = rateCurr.getCountry_id();
			if (country_id != null) {
				Country country = oracleManager.find(Country.class, country_id);
				if (country != null) {
					rateCurr.setCountry_name_geo(country.getCountry_name_geo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return rateCurr;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Rate updateRate(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateRate.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp currDate = new Timestamp(System.currentTimeMillis());
			Long curr_id = record.get("curr_id") == null ? null : new Long(
					record.get("curr_id").toString());
			Long deleted = record.get("deleted") == null ? null : new Long(
					record.get("deleted").toString());
			BigDecimal market_rate = record.get("market_rate") == null ? null
					: new BigDecimal(record.get("market_rate").toString());
			BigDecimal rate = record.get("rate") == null ? null
					: new BigDecimal(record.get("rate").toString());
			Long rate_coeff = record.get("rate_coeff") == null ? null
					: new Long(record.get("rate_coeff").toString());
			BigDecimal sale_market_rate = record.get("sale_market_rate") == null ? null
					: new BigDecimal(record.get("sale_market_rate").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			ArrayList<Rate> ratesOld = (ArrayList<Rate>) oracleManager
					.createNamedQuery("Rate.getRateByCurr")
					.setParameter("curr_id", curr_id).getResultList();
			if (ratesOld != null && !ratesOld.isEmpty()) {
				for (Rate rateOld : ratesOld) {
					oracleManager.remove(rateOld);
					RateLog rateLog = new RateLog();
					rateLog.setCurr_id(rateOld.getCurr_id());
					rateLog.setDeleted(rateOld.getDeleted());
					rateLog.setMarket_rate(rateOld.getMarket_rate());
					rateLog.setRate(rateOld.getRate());
					rateLog.setRate_coeff(rateOld.getRate_coeff());
					rateLog.setRec_date(currDate);
					rateLog.setRec_user(loggedUserName);
					rateLog.setSale_market_rate(rateOld.getSale_market_rate());
					rateLog.setUpd_date(currDate);
					rateLog.setUpd_user(loggedUserName);

					Calendar calendar = new GregorianCalendar();
					calendar.setTimeInMillis(currDate.getTime());
					calendar.add(Calendar.DAY_OF_YEAR, -1);
					rateLog.setDt(new Timestamp(calendar.getTimeInMillis()));

					oracleManager.persist(rateLog);
				}
			}

			oracleManager.flush();

			Rate rateObject = new Rate();
			rateObject.setCurr_id(curr_id);
			rateObject.setMarket_rate(market_rate);
			rateObject.setRate(rate);
			rateObject.setRate_coeff(rate_coeff);
			rateObject.setSale_market_rate(sale_market_rate);
			rateObject.setUpd_date(currDate);
			rateObject.setUpd_user(loggedUserName);
			rateObject.setRec_date(currDate);
			rateObject.setRec_user(loggedUserName);
			rateObject.setDeleted(deleted);

			oracleManager.persist(rateObject);
			oracleManager.flush();

			rateObject = oracleManager
					.find(Rate.class, rateObject.getRate_id());

			rateObject.setUpd_user(loggedUserName);
			if (curr_id != null) {
				RateCurr rateCurr = oracleManager.find(RateCurr.class, curr_id);
				if (rateCurr != null) {
					rateObject.setCurr_name_geo(rateCurr.getCountry_name_geo());
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return rateObject;
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
