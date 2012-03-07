package com.info08.billing.callcenter.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenter.client.exception.CallCenterException;
import com.info08.billing.callcenter.server.common.QueryConstants;
import com.info08.billing.callcenter.shared.entity.GeoIndCountry;
import com.info08.billing.callcenter.shared.entity.GeoIndRegion;
import com.info08.billing.callcenter.shared.entity.Service;
import com.info08.billing.callcenter.shared.entity.StreetEnt;
import com.info08.billing.callcenter.shared.entity.StreetIndex;
import com.isomorphic.jpa.EMF;

public class GeoIndDMI implements QueryConstants {

	Logger logger = Logger.getLogger(GeoIndDMI.class.getName());

	/**
	 * Adding New GeoIndRegion
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public GeoIndRegion addGeoIndRegion(GeoIndRegion geoIndRegion)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addGeoIndRegion.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = geoIndRegion.getLoggedUserName();
			geoIndRegion.setRec_date(recDate);
			geoIndRegion.setService_id(48L);

			oracleManager.persist(geoIndRegion);
			oracleManager.flush();

			geoIndRegion = oracleManager.find(GeoIndRegion.class,
					geoIndRegion.getRegion_id());
			geoIndRegion.setLoggedUserName(loggedUserName);
			Service service = oracleManager.find(Service.class, 48L);
			if (service != null) {
				geoIndRegion.setServiceName(service.getServiceNameGeo());
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return geoIndRegion;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert GeoIndRegion Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating GeoIndRegion
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public GeoIndRegion updateGeoIndRegion(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateGeoIndRegion.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long region_id = new Long(record.get("region_id").toString());
			String region_name_geo = record.get("region_name_geo") == null ? null
					: record.get("region_name_geo").toString();
			String region_name_eng = record.get("region_name_eng") == null ? null
					: record.get("region_name_eng").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			GeoIndRegion geoIndRegion = oracleManager.find(GeoIndRegion.class,
					region_id);

			geoIndRegion.setRegion_name_geo(region_name_geo);
			geoIndRegion.setRegion_name_eng(region_name_eng);
			geoIndRegion.setUpd_user(loggedUserName);

			oracleManager.merge(geoIndRegion);
			oracleManager.flush();

			geoIndRegion = oracleManager.find(GeoIndRegion.class, region_id);

			geoIndRegion.setLoggedUserName(loggedUserName);
			Service service = oracleManager.find(Service.class, 48L);
			if (service != null) {
				geoIndRegion.setServiceName(service.getServiceNameGeo());
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return geoIndRegion;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update GeoIndRegion Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating GeoIndRegion Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public GeoIndRegion updateGeoIndRegionStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateGeoIndRegionStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long region_id = new Long(record.get("region_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			GeoIndRegion geoIndRegion = oracleManager.find(GeoIndRegion.class,
					region_id);
			geoIndRegion.setDeleted(deleted);
			geoIndRegion.setUpd_user(loggedUserName);

			oracleManager.merge(geoIndRegion);
			oracleManager.flush();
			geoIndRegion = oracleManager.find(GeoIndRegion.class, region_id);

			geoIndRegion.setLoggedUserName(loggedUserName);
			Service service = oracleManager.find(Service.class, 48L);
			if (service != null) {
				geoIndRegion.setServiceName(service.getServiceNameGeo());
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return geoIndRegion;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for GeoIndRegion Into Database : ",
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
	 * Adding New GeoIndCountry
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public GeoIndCountry addGeoIndCountry(GeoIndCountry geoIndCountry)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addGeoIndCountry.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = geoIndCountry.getLoggedUserName();
			geoIndCountry.setRec_date(recDate);

			oracleManager.persist(geoIndCountry);
			oracleManager.flush();

			geoIndCountry = oracleManager.find(GeoIndCountry.class,
					geoIndCountry.getGeo_country_id());
			geoIndCountry.setLoggedUserName(loggedUserName);

			Long region_id = geoIndCountry.getRegion_id();
			if (region_id != null) {
				GeoIndRegion geoIndRegion = oracleManager.find(
						GeoIndRegion.class, region_id);
				if (geoIndRegion != null) {
					geoIndCountry.setRegionName(geoIndRegion
							.getRegion_name_geo());
				}
			}
			Long is_center = geoIndCountry.getIs_center();
			if (is_center != null) {
				switch (is_center.intValue()) {
				case -1:
					geoIndCountry.setIsCenterDescr("რაიონული ცენტრი");
					break;
				case 0:
					geoIndCountry.setIsCenterDescr("სოფელი");
					break;
				default:
					geoIndCountry.setIsCenterDescr("უცნობია");
					break;
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return geoIndCountry;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert GeoIndCountry Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating GeoIndCountry
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public GeoIndCountry updateGeoIndCountry(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateGeoIndCountry.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long geo_country_id = new Long(record.get("geo_country_id")
					.toString());
			String geo_country_geo = record.get("geo_country_geo") == null ? null
					: record.get("geo_country_geo").toString();
			String geo_country_eng = record.get("geo_country_eng") == null ? null
					: record.get("geo_country_eng").toString();
			String geo_index = record.get("geo_index") == null ? null : record
					.get("geo_index").toString();
			Long is_center = new Long(record.get("is_center").toString());
			Long region_id = new Long(record.get("region_id").toString());

			String loggedUserName = record.get("loggedUserName").toString();

			GeoIndCountry geoIndCountry = oracleManager.find(
					GeoIndCountry.class, geo_country_id);

			geoIndCountry.setGeo_country_eng(geo_country_eng);
			geoIndCountry.setGeo_country_geo(geo_country_geo);
			geoIndCountry.setGeo_index(geo_index);
			geoIndCountry.setIs_center(is_center);
			geoIndCountry.setRegion_id(region_id);
			geoIndCountry.setUpd_user(loggedUserName);

			oracleManager.merge(geoIndCountry);
			oracleManager.flush();

			geoIndCountry = oracleManager.find(GeoIndCountry.class,
					geo_country_id);
			geoIndCountry.setLoggedUserName(loggedUserName);

			if (region_id != null) {
				GeoIndRegion geoIndRegion = oracleManager.find(
						GeoIndRegion.class, region_id);
				if (geoIndRegion != null) {
					geoIndCountry.setRegionName(geoIndRegion
							.getRegion_name_geo());
				}
			}
			if (is_center != null) {
				switch (is_center.intValue()) {
				case -1:
					geoIndCountry.setIsCenterDescr("რაიონული ცენტრი");
					break;
				case 0:
					geoIndCountry.setIsCenterDescr("სოფელი");
					break;
				default:
					geoIndCountry.setIsCenterDescr("უცნობია");
					break;
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return geoIndCountry;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update GeoIndCountry Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating GeoIndCountry Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public GeoIndCountry updateGeoIndCountryStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateGeoIndCountryStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long geo_country_id = new Long(record.get("geo_country_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			GeoIndCountry geoIndCountry = oracleManager.find(
					GeoIndCountry.class, geo_country_id);
			geoIndCountry.setDeleted(deleted);
			geoIndCountry.setUpd_user(loggedUserName);

			oracleManager.merge(geoIndCountry);
			oracleManager.flush();
			geoIndCountry = oracleManager.find(GeoIndCountry.class,
					geo_country_id);

			geoIndCountry.setLoggedUserName(loggedUserName);

			Long region_id = geoIndCountry.getRegion_id();
			if (region_id != null) {
				GeoIndRegion geoIndRegion = oracleManager.find(
						GeoIndRegion.class, region_id);
				if (geoIndRegion != null) {
					geoIndCountry.setRegionName(geoIndRegion
							.getRegion_name_geo());
				}
			}
			Long is_center = geoIndCountry.getIs_center();
			if (is_center != null) {
				switch (is_center.intValue()) {
				case -1:
					geoIndCountry.setIsCenterDescr("რაიონული ცენტრი");
					break;
				case 0:
					geoIndCountry.setIsCenterDescr("სოფელი");
					break;
				default:
					geoIndCountry.setIsCenterDescr("უცნობია");
					break;
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return geoIndCountry;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for GeoIndCountry Into Database : ",
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
	public StreetIndex addStreetIndex(StreetIndex streetIndex)
			throws Exception {
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
				StreetEnt streetEnt = oracleManager.find(
						StreetEnt.class, street_id);
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

			Long street_index_id = new Long(record.get("street_index_id").toString());
			Long street_id = record.get("street_id") == null ? null : new Long(record.get("street_id").toString());
			String street_comment = record.get("street_comment") == null ? null : record.get("street_comment").toString();
			String street_index = record.get("street_index") == null ? null : record.get("street_index").toString();			
			String loggedUserName = record.get("loggedUserName").toString();

			StreetIndex streetIndex = oracleManager.find(StreetIndex.class, street_index_id);

			streetIndex.setStreet_id(street_id);
			streetIndex.setStreet_comment(street_comment);
			streetIndex.setStreet_index(street_index);
			streetIndex.setUpd_user(loggedUserName);

			oracleManager.merge(streetIndex);
			oracleManager.flush();

			streetIndex = oracleManager.find(StreetIndex.class, street_index_id);
			streetIndex.setLoggedUserName(loggedUserName);

			if (street_id != null) {
				StreetEnt streetEnt = oracleManager.find(
						StreetEnt.class, street_id);
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

			Long street_index_id = new Long(record.get("street_index_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			StreetIndex streetIndex = oracleManager.find(StreetIndex.class, street_index_id);
			
			streetIndex.setDeleted(deleted);
			streetIndex.setUpd_user(loggedUserName);

			oracleManager.merge(streetIndex);
			oracleManager.flush();
			streetIndex = oracleManager.find(StreetIndex.class, street_index_id);

			streetIndex.setLoggedUserName(loggedUserName);
			
			Long street_id = streetIndex.getStreet_id(); 
			if (street_id != null) {
				StreetEnt streetEnt = oracleManager.find(
						StreetEnt.class, street_id);
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
