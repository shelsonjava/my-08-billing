package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.common.SharedUtils;
import com.info08.billing.callcenterbk.shared.entity.City;
import com.info08.billing.callcenterbk.shared.entity.CityDistance;
import com.info08.billing.callcenterbk.shared.entity.CityRegion;
//import com.info08.billing.callcenterbk.shared.entity.CityType;
import com.info08.billing.callcenterbk.shared.entity.Continents;
import com.info08.billing.callcenterbk.shared.entity.Country;
import com.info08.billing.callcenterbk.shared.entity.StreetDescr;
import com.info08.billing.callcenterbk.shared.entity.StreetDistrict;
import com.info08.billing.callcenterbk.shared.entity.StreetEnt;
import com.info08.billing.callcenterbk.shared.entity.StreetType;
import com.info08.billing.callcenterbk.shared.entity.StreetsOldEnt;
import com.info08.billing.callcenterbk.shared.items.FirstName;
import com.info08.billing.callcenterbk.shared.items.LastName;
import com.info08.billing.callcenterbk.shared.items.Departments;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DataSource;
import com.isomorphic.datasource.DataSourceManager;
import com.isomorphic.jpa.EMF;
import com.isomorphic.sql.SQLDataSource;

public class CommonDMI implements QueryConstants {

	Logger logger = Logger.getLogger(CommonDMI.class.getName());

	private static TreeMap<Integer, FirstName> firstNames = new TreeMap<Integer, FirstName>();
	private static TreeMap<Integer, LastName> lastNames = new TreeMap<Integer, LastName>();
	private static TreeMap<Integer, Departments> departments = new TreeMap<Integer, Departments>();
	private static TreeMap<Long, Continents> continents = new TreeMap<Long, Continents>();
	private static TreeMap<Long, Country> countries = new TreeMap<Long, Country>();
	//private static TreeMap<Long, CityType> cityTypes = new TreeMap<Long, CityType>();
	private static TreeMap<Long, City> cities = new TreeMap<Long, City>();
	private static TreeMap<Long, ArrayList<StreetEnt>> streetsByCityId = new TreeMap<Long, ArrayList<StreetEnt>>();
	private static TreeMap<Long, StreetType> streetTypes = new TreeMap<Long, StreetType>();
	private static TreeMap<Long, StreetDescr> streetDescrs = new TreeMap<Long, StreetDescr>();
	private static TreeMap<Long, StreetEnt> streetEnts = new TreeMap<Long, StreetEnt>();
	private static TreeMap<Long, CityRegion> cityRegions = new TreeMap<Long, CityRegion>();
	private static TreeMap<Long, TreeMap<Long, CityRegion>> cityRegionsByCityId = new TreeMap<Long, TreeMap<Long, CityRegion>>();

	public static StreetEnt getStreetEnt(Long steetEntId) {
		return streetEnts.get(steetEntId);
	}

	public static StreetDescr getStreetDescr(Long steetDescrId) {
		return streetDescrs.get(steetDescrId);
	}

	public static CityRegion getCityRegion(Integer cityRegionId) {
		return cityRegions.get(cityRegionId);
	}

	public static FirstName getFirstName(Integer firstNameId) {
		return firstNames.get(firstNameId);
	}

	public static LastName getLastName(Integer lastNameId) {
		return lastNames.get(lastNameId);
	}

	public static Departments getDepartments(Integer departmentId) {
		return departments.get(departmentId);
	}

//	public static CityType getCityType(Long cityTypeId) {
//		return cityTypes.get(cityTypeId);
//		
//		
//	}

	public static City getCity(Long cityId) {
		return cities.get(cityId);
	}

	public static Country getCountry(Long countryId) {
		return countries.get(countryId);
	}

	public static StreetType getStreetType(Long streetTypeId) {
		return streetTypes.get(streetTypeId);
	}

	/**
	 * Adding New CityDistance
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public CityDistance addCityDistance(CityDistance cityDistance)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addCityDistance.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			cityDistance.setRec_date(recDate);

			oracleManager.persist(cityDistance);
			oracleManager.flush();

			cityDistance = oracleManager.find(CityDistance.class,
					cityDistance.getCity_distance_id());

			City cityStart = getCity(cityDistance.getCity_id_start());
			if (cityStart != null) {
				cityDistance.setCityStart(cityStart.getCity_name_geo());
			}
			City cityEnd = getCity(cityDistance.getCity_id_end());
			if (cityEnd != null) {
				cityDistance.setCityEnd(cityEnd.getCity_name_geo());
			}
			cityDistance.setLoggedUserName(cityDistance.getRec_user());
			cityDistance.setCityDistTypeDesc(SharedUtils.getInstance()
					.getDistanceTypeDescr(
							cityDistance.getCity_distance_type().toString()));

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return cityDistance;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert City Distances Into Database : ",
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
	 * Updating CityDistance
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public CityDistance updateCityDistance(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateCityDistance.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			Long city_distance_id = new Long(record.get("city_distance_id")
					.toString());
			String city_distance_geo = record.get("city_distance_geo")
					.toString();
			Long city_id_start = new Long(record.get("city_id_start")
					.toString());
			Long city_id_end = new Long(record.get("city_id_end").toString());
			Long city_distance_type = new Long(record.get("city_distance_type")
					.toString());
			String note_geo = record.get("note_geo").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			CityDistance cityDistance = oracleManager.find(CityDistance.class,
					city_distance_id);
			cityDistance.setCity_distance_geo(city_distance_geo);
			cityDistance.setCity_id_start(city_id_start);
			cityDistance.setCity_id_end(city_id_end);
			cityDistance.setCity_distance_type(city_distance_type);
			cityDistance.setNote_geo(note_geo);
			cityDistance.setUpd_date(timestamp);
			cityDistance.setUpd_user(loggedUserName);

			oracleManager.merge(cityDistance);
			oracleManager.flush();

			cityDistance = oracleManager.find(CityDistance.class,
					city_distance_id);

			City cityStart = getCity(city_id_start);
			if (cityStart != null) {
				cityDistance.setCityStart(cityStart.getCity_name_geo());
			}
			City cityEnd = getCity(city_id_end);
			if (cityEnd != null) {
				cityDistance.setCityEnd(cityEnd.getCity_name_geo());
			}
			cityDistance.setLoggedUserName(loggedUserName);
			cityDistance.setCityDistTypeDesc(SharedUtils.getInstance()
					.getDistanceTypeDescr(city_distance_type.toString()));

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return cityDistance;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update City Distance Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating CityDistance Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public CityDistance updateCityDistanceStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateCityDistanceStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			Long city_distance_id = new Long(record.get("city_distance_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			CityDistance cityDistance = oracleManager.find(CityDistance.class,
					city_distance_id);
			cityDistance.setUpd_date(timestamp);
			cityDistance.setDeleted(deleted);
			cityDistance.setUpd_user(loggedUserName);

			oracleManager.merge(cityDistance);
			oracleManager.flush();
			cityDistance = oracleManager.find(CityDistance.class,
					city_distance_id);

			City cityStart = getCity(cityDistance.getCity_id_start());
			if (cityStart != null) {
				cityDistance.setCityStart(cityStart.getCity_name_geo());
			}
			City cityEnd = getCity(cityDistance.getCity_id_end());
			if (cityEnd != null) {
				cityDistance.setCityEnd(cityEnd.getCity_name_geo());
			}
			cityDistance.setLoggedUserName(loggedUserName);
			cityDistance.setCityDistTypeDesc(SharedUtils.getInstance()
					.getDistanceTypeDescr(
							cityDistance.getCity_distance_type().toString()));

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return cityDistance;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status CityDistance Into Database : ",
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
	 * Adding New Street
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public StreetEnt addStreetEnt(StreetEnt streetEnt, DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addStreetEnt.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			streetEnt.setRec_date(recDate);
			streetEnt.setRecord_type(1L);

			String streetName = buildStreetName(streetEnt, oracleManager);
			streetEnt.setStreet_name_geo(streetName);
			oracleManager.persist(streetEnt);

			// StreetsOldEnt streetsOldEnt = new StreetsOldEnt();
			// streetsOldEnt.setCity_id(streetEnt.getCity_id());
			// streetsOldEnt.setDeleted(streetEnt.getDeleted());
			// streetsOldEnt.setRec_date(recDate);
			// streetsOldEnt.setRec_user(streetEnt.getRec_user());
			// streetsOldEnt.setStreet_id(streetEnt.getStreet_id());
			// streetsOldEnt.setStreet_old_name_eng(streetEnt.getStreet_location_eng());
			// streetsOldEnt.setStreet_old_name_geo(streetEnt.getStreet_name_geo());
			//
			// oracleManager.persist(streetsOldEnt);

			Object oStreet_Districts = dsRequest
					.getFieldValue("mapStreDistricts");
			if (oStreet_Districts != null) {
				Map<String, String> street_Districts = (Map<String, String>) oStreet_Districts;
				Set<String> keySet = street_Districts.keySet();
				for (String city_region_id : keySet) {
					StreetDistrict streetDistrict = new StreetDistrict();
					streetDistrict.setCity_id(streetEnt.getCity_id());
					streetDistrict.setCity_region_id(Long
							.parseLong(city_region_id));
					streetDistrict.setDeleted(0L);
					streetDistrict.setRec_date(recDate);
					streetDistrict.setRec_user(streetEnt.getRec_user());
					streetDistrict.setStreet_id(streetEnt.getStreet_id());
					streetDistrict.setUpd_user(streetEnt.getRec_user());
					oracleManager.persist(streetDistrict);
				}
				streetEnt.setMapStreDistricts(street_Districts);
			}

			oracleManager.flush();
			streetEnt = getStreetEntById(streetEnt.getStreet_id(),
					oracleManager);

			if (streetEnt != null) {
				streetEnts.put(streetEnt.getStreet_id(), streetEnt);
			}
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return streetEnt;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert Street Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	private String buildStreetName(StreetEnt streetEnt,
			EntityManager oracleManager) throws Exception {
		try {
			if (streetEnt == null) {
				return null;
			}
			StringBuilder streetName = new StringBuilder("");
			Long str_descr_level_1 = streetEnt.getDescr_id_level_1();
			if (str_descr_level_1 != null) {
				StreetDescr streetDescr = oracleManager.find(StreetDescr.class,
						str_descr_level_1);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_descr_name_geo())
							.append(" ");
				}
			}
			Long str_descr_type_level_1 = streetEnt.getDescr_type_id_level_1();
			if (str_descr_type_level_1 != null) {
				StreetType streetType = oracleManager.find(StreetType.class,
						str_descr_type_level_1);
				if (streetType != null) {
					streetName.append(streetType.getStreet_type_name_geo())
							.append(" ");
				}
			}

			Long str_descr_level_2 = streetEnt.getDescr_id_level_2();
			if (str_descr_level_2 != null) {
				StreetDescr streetDescr = oracleManager.find(StreetDescr.class,
						str_descr_level_2);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_descr_name_geo())
							.append(" ");
				}
			}
			Long str_descr_type_level_2 = streetEnt.getDescr_type_id_level_2();
			if (str_descr_type_level_2 != null) {
				StreetType streetType = oracleManager.find(StreetType.class,
						str_descr_type_level_2);
				if (streetType != null) {
					streetName.append(streetType.getStreet_type_name_geo())
							.append(" ");
				}
			}

			Long str_descr_level_3 = streetEnt.getDescr_id_level_3();
			if (str_descr_level_3 != null) {
				StreetDescr streetDescr = oracleManager.find(StreetDescr.class,
						str_descr_level_3);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_descr_name_geo())
							.append(" ");
				}
			}
			Long str_descr_type_level_3 = streetEnt.getDescr_type_id_level_3();
			if (str_descr_type_level_3 != null) {
				StreetType streetType = oracleManager.find(StreetType.class,
						str_descr_type_level_3);
				if (streetType != null) {
					streetName.append(streetType.getStreet_type_name_geo())
							.append(" ");
				}
			}

			Long str_descr_level_4 = streetEnt.getDescr_id_level_4();
			if (str_descr_level_4 != null) {
				StreetDescr streetDescr = oracleManager.find(StreetDescr.class,
						str_descr_level_4);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_descr_name_geo())
							.append(" ");
				}
			}
			Long str_descr_type_level_4 = streetEnt.getDescr_type_id_level_4();
			if (str_descr_type_level_4 != null) {
				StreetType streetType = oracleManager.find(StreetType.class,
						str_descr_type_level_4);
				if (streetType != null) {
					streetName.append(streetType.getStreet_type_name_geo())
							.append(" ");
				}
			}

			Long str_descr_level_5 = streetEnt.getDescr_id_level_5();
			if (str_descr_level_5 != null) {
				StreetDescr streetDescr = oracleManager.find(StreetDescr.class,
						str_descr_level_5);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_descr_name_geo())
							.append(" ");
				}
			}
			Long str_descr_type_level_5 = streetEnt.getDescr_type_id_level_5();
			if (str_descr_type_level_5 != null) {
				StreetType streetType = oracleManager.find(StreetType.class,
						str_descr_type_level_5);
				if (streetType != null) {
					streetName.append(streetType.getStreet_type_name_geo())
							.append(" ");
				}
			}

			Long str_descr_level_6 = streetEnt.getDescr_id_level_6();
			if (str_descr_level_6 != null) {
				StreetDescr streetDescr = oracleManager.find(StreetDescr.class,
						str_descr_level_6);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_descr_name_geo())
							.append(" ");
				}
			}
			Long str_descr_type_level_6 = streetEnt.getDescr_type_id_level_6();
			if (str_descr_type_level_6 != null) {
				StreetType streetType = oracleManager.find(StreetType.class,
						str_descr_type_level_6);
				if (streetType != null) {
					streetName.append(streetType.getStreet_type_name_geo())
							.append(" ");
				}
			}

			Long str_descr_level_7 = streetEnt.getDescr_id_level_7();
			if (str_descr_level_7 != null) {
				StreetDescr streetDescr = oracleManager.find(StreetDescr.class,
						str_descr_level_7);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_descr_name_geo())
							.append(" ");
				}
			}
			Long str_descr_type_level_7 = streetEnt.getDescr_type_id_level_7();
			if (str_descr_type_level_7 != null) {
				StreetType streetType = oracleManager.find(StreetType.class,
						str_descr_type_level_7);
				if (streetType != null) {
					streetName.append(streetType.getStreet_type_name_geo())
							.append(" ");
				}
			}

			Long str_descr_level_8 = streetEnt.getDescr_id_level_8();
			if (str_descr_level_8 != null) {
				StreetDescr streetDescr = oracleManager.find(StreetDescr.class,
						str_descr_level_8);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_descr_name_geo())
							.append(" ");
				}
			}
			Long str_descr_type_level_8 = streetEnt.getDescr_type_id_level_8();
			if (str_descr_type_level_8 != null) {
				StreetType streetType = oracleManager.find(StreetType.class,
						str_descr_type_level_8);
				if (streetType != null) {
					streetName.append(streetType.getStreet_type_name_geo())
							.append(" ");
				}
			}

			Long str_descr_level_9 = streetEnt.getDescr_id_level_9();
			if (str_descr_level_9 != null) {
				StreetDescr streetDescr = oracleManager.find(StreetDescr.class,
						str_descr_level_9);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_descr_name_geo())
							.append(" ");
				}
			}
			Long str_descr_type_level_9 = streetEnt.getDescr_type_id_level_9();
			if (str_descr_type_level_9 != null) {
				StreetType streetType = oracleManager.find(StreetType.class,
						str_descr_type_level_9);
				if (streetType != null) {
					streetName.append(streetType.getStreet_type_name_geo())
							.append(" ");
				}
			}

			Long str_descr_level_10 = streetEnt.getDescr_id_level_10();
			if (str_descr_level_10 != null) {
				StreetDescr streetDescr = oracleManager.find(StreetDescr.class,
						str_descr_level_10);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_descr_name_geo())
							.append(" ");
				}
			}
			Long str_descr_type_level_10 = streetEnt
					.getDescr_type_id_level_10();
			if (str_descr_type_level_10 != null) {
				StreetType streetType = oracleManager.find(StreetType.class,
						str_descr_type_level_10);
				if (streetType != null) {
					streetName.append(streetType.getStreet_type_name_geo())
							.append(" ");
				}
			}
			return streetName.toString().trim();
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Building Street Name : ", e);
			throw new CallCenterException(
					"შეცდომა ქუჩის სახელის გენერაციისას : " + e.toString());
		}
	}

	private StreetEnt getStreetEntById(Long streetId,
			EntityManager oracleManager) throws Exception {
		try {
			StreetEnt ret = oracleManager.find(StreetEnt.class, streetId);
			if (ret != null) {
				Long cityId = ret.getCity_id();
				if (cityId != null) {
					City city = oracleManager.find(City.class, cityId);
					if (city != null) {
						ret.setCity_name_geo(city.getCity_name_geo());
					}
				}
			}
			return ret;
		} catch (Exception e) {
			logger.error("Error While getting street by id : ", e);
			throw new CallCenterException(
					"შეცდომა ქუჩის მონაცემის ამოღებისას მონაცემთა ბაზიდან Ex: "
							+ e.toString());
		}
	}

	/**
	 * Updating Street
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StreetEnt updateStreetEnt(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetEnt.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_id = new Long(record.get("street_id").toString());
			Long city_id = new Long(record.get("city_id").toString());
			String street_location_geo = record.get("street_location_geo") == null ? null
					: record.get("street_location_geo").toString();
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			Object odescr_id_level_1 = record.get("descr_id_level_1");
			Long descr_id_level_1 = (odescr_id_level_1 == null) ? null
					: new Long(odescr_id_level_1.toString());

			Object odescr_id_level_2 = record.get("descr_id_level_2");
			Long descr_id_level_2 = (odescr_id_level_2 == null) ? null
					: new Long(odescr_id_level_2.toString());

			Object odescr_id_level_3 = record.get("descr_id_level_3");
			Long descr_id_level_3 = (odescr_id_level_3 == null) ? null
					: new Long(odescr_id_level_3.toString());

			Object odescr_id_level_4 = record.get("descr_id_level_4");
			Long descr_id_level_4 = (odescr_id_level_4 == null) ? null
					: new Long(odescr_id_level_4.toString());

			Object odescr_id_level_5 = record.get("descr_id_level_5");
			Long descr_id_level_5 = (odescr_id_level_5 == null) ? null
					: new Long(odescr_id_level_5.toString());

			Object odescr_id_level_6 = record.get("descr_id_level_6");
			Long descr_id_level_6 = (odescr_id_level_6 == null) ? null
					: new Long(odescr_id_level_6.toString());

			Object odescr_id_level_7 = record.get("descr_id_level_7");
			Long descr_id_level_7 = (odescr_id_level_7 == null) ? null
					: new Long(odescr_id_level_7.toString());

			Object odescr_id_level_8 = record.get("descr_id_level_8");
			Long descr_id_level_8 = (odescr_id_level_8 == null) ? null
					: new Long(odescr_id_level_8.toString());

			Object odescr_id_level_9 = record.get("descr_id_level_9");
			Long descr_id_level_9 = (odescr_id_level_9 == null) ? null
					: new Long(odescr_id_level_9.toString());

			Object odescr_id_level_10 = record.get("descr_id_level_10");
			Long descr_id_level_10 = (odescr_id_level_10 == null) ? null
					: new Long(odescr_id_level_10.toString());

			Object odescr_type_id_level_1 = record.get("descr_type_id_level_1");
			Long descr_type_id_level_1 = (odescr_type_id_level_1 == null) ? null
					: new Long(odescr_type_id_level_1.toString());

			Object odescr_type_id_level_2 = record.get("descr_type_id_level_2");
			Long descr_type_id_level_2 = (odescr_type_id_level_2 == null) ? null
					: new Long(odescr_type_id_level_2.toString());

			Object odescr_type_id_level_3 = record.get("descr_type_id_level_3");
			Long descr_type_id_level_3 = (odescr_type_id_level_3 == null) ? null
					: new Long(odescr_type_id_level_3.toString());

			Object odescr_type_id_level_4 = record.get("descr_type_id_level_4");
			Long descr_type_id_level_4 = (odescr_type_id_level_4 == null) ? null
					: new Long(odescr_type_id_level_4.toString());

			Object odescr_type_id_level_5 = record.get("descr_type_id_level_5");
			Long descr_type_id_level_5 = (odescr_type_id_level_5 == null) ? null
					: new Long(odescr_type_id_level_5.toString());

			Object odescr_type_id_level_6 = record.get("descr_type_id_level_6");
			Long descr_type_id_level_6 = (odescr_type_id_level_6 == null) ? null
					: new Long(odescr_type_id_level_6.toString());

			Object odescr_type_id_level_7 = record.get("descr_type_id_level_7");
			Long descr_type_id_level_7 = (odescr_type_id_level_7 == null) ? null
					: new Long(odescr_type_id_level_7.toString());

			Object odescr_type_id_level_8 = record.get("descr_type_id_level_8");
			Long descr_type_id_level_8 = (odescr_type_id_level_8 == null) ? null
					: new Long(odescr_type_id_level_8.toString());

			Object odescr_type_id_level_9 = record.get("descr_type_id_level_9");
			Long descr_type_id_level_9 = (odescr_type_id_level_9 == null) ? null
					: new Long(odescr_type_id_level_9.toString());

			Object odescr_type_id_level_10 = record
					.get("descr_type_id_level_10");
			Long descr_type_id_level_10 = (odescr_type_id_level_10 == null) ? null
					: new Long(odescr_type_id_level_10.toString());

			Object saveStreetHistOrNotItem = record
					.get("saveStreetHistOrNotItem");
			Boolean bSaveStreetHistOrNotItem = (saveStreetHistOrNotItem == null) ? null
					: (Boolean) saveStreetHistOrNotItem;

			StreetEnt streetEntForGen = oracleManager.find(StreetEnt.class,
					street_id);

			if (bSaveStreetHistOrNotItem != null
					&& bSaveStreetHistOrNotItem.booleanValue()) {
				StreetsOldEnt streetsOldEnt = new StreetsOldEnt();
				streetsOldEnt.setCity_id(streetEntForGen.getCity_id());
				streetsOldEnt.setDeleted(streetEntForGen.getDeleted());
				streetsOldEnt.setRec_date(recDate);
				streetsOldEnt.setRec_user(streetEntForGen.getRec_user());
				streetsOldEnt.setStreet_id(streetEntForGen.getStreet_id());
				streetsOldEnt.setStreet_old_name_eng(streetEntForGen
						.getStreet_location_eng());
				streetsOldEnt.setStreet_old_name_geo(streetEntForGen
						.getStreet_name_geo());
				oracleManager.persist(streetsOldEnt);
			}

			streetEntForGen.setDescr_id_level_1(descr_id_level_1);
			streetEntForGen.setDescr_id_level_2(descr_id_level_2);
			streetEntForGen.setDescr_id_level_3(descr_id_level_3);
			streetEntForGen.setDescr_id_level_4(descr_id_level_4);
			streetEntForGen.setDescr_id_level_5(descr_id_level_5);
			streetEntForGen.setDescr_id_level_6(descr_id_level_6);
			streetEntForGen.setDescr_id_level_7(descr_id_level_7);
			streetEntForGen.setDescr_id_level_8(descr_id_level_8);
			streetEntForGen.setDescr_id_level_9(descr_id_level_9);
			streetEntForGen.setDescr_id_level_10(descr_id_level_10);
			streetEntForGen.setDescr_type_id_level_1(descr_type_id_level_1);
			streetEntForGen.setDescr_type_id_level_2(descr_type_id_level_2);
			streetEntForGen.setDescr_type_id_level_3(descr_type_id_level_3);
			streetEntForGen.setDescr_type_id_level_4(descr_type_id_level_4);
			streetEntForGen.setDescr_type_id_level_5(descr_type_id_level_5);
			streetEntForGen.setDescr_type_id_level_6(descr_type_id_level_6);
			streetEntForGen.setDescr_type_id_level_7(descr_type_id_level_7);
			streetEntForGen.setDescr_type_id_level_8(descr_type_id_level_8);
			streetEntForGen.setDescr_type_id_level_9(descr_type_id_level_9);
			streetEntForGen.setDescr_type_id_level_10(descr_type_id_level_10);
			String streetName = buildStreetName(streetEntForGen, oracleManager);
			streetEntForGen.setStreet_name_geo(streetName);
			streetEntForGen.setCity_id(city_id);
			streetEntForGen.setStreet_location_geo(street_location_geo);
			streetEntForGen.setUpd_user(loggedUserName);
			streetEntForGen.setRecord_type(1L);
			oracleManager.merge(streetEntForGen);

			oracleManager
					.createNativeQuery(Q_DELETE_STREET_DISCTRICTS_BY_STREET_ID)
					.setParameter(1, streetEntForGen.getStreet_id())
					.executeUpdate();

			Map<String, String> street_Districts = null;
			Object oStreet_Districts = record.get("mapStreDistricts");
			if (oStreet_Districts != null && (oStreet_Districts instanceof Map)) {
				street_Districts = (Map<String, String>) oStreet_Districts;
				if (street_Districts != null && !street_Districts.isEmpty()) {
					Set<String> keySet = street_Districts.keySet();
					for (String city_region_id : keySet) {
						StreetDistrict streetDistrict = new StreetDistrict();
						streetDistrict.setCity_id(city_id);
						streetDistrict.setCity_region_id(Long
								.parseLong(city_region_id));
						streetDistrict.setDeleted(0L);
						streetDistrict.setRec_date(recDate);
						streetDistrict.setRec_user(loggedUserName);
						streetDistrict.setStreet_id(street_id);
						streetDistrict.setUpd_user(loggedUserName);
						oracleManager.persist(streetDistrict);
					}
				}
			}

			oracleManager.flush();

			StreetEnt streetEnt = getStreetEntById(street_id, oracleManager);
			if (street_Districts != null && !street_Districts.isEmpty()) {
				streetEnt.setMapStreDistricts(street_Districts);
			}
			streetEnts.remove(street_id);
			streetEnts.put(street_id, streetEnt);
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return streetEnt;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Street Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Updating Street Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetEnt updateStreetEntStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetEntStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_id = new Long(record.get("street_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			oracleManager.createNativeQuery(Q_UPDATE_STREET_STATUS)
					.setParameter(1, deleted).setParameter(2, loggedUserName)
					.setParameter(3, street_id).executeUpdate();
			oracleManager.flush();
			StreetEnt retItem = getStreetEntById(street_id, oracleManager);
			if (retItem != null) {
				streetEnts.remove(retItem.getStreet_id());
				if (deleted.equals(0L)) {
					streetEnts.put(retItem.getStreet_id(), retItem);
				}
			}
			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Status Street Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Fetching All Active Street
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<StreetEnt> fetchStreetEnts(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.fetchStreetEnts.";
			oracleManager = EMF.getEntityManager();
			if (streetEnts == null || streetEnts.isEmpty()) {
				log += " fetching Streets From DB ...";
				streetEnts = new TreeMap<Long, StreetEnt>();
				ArrayList<StreetEnt> list = (ArrayList<StreetEnt>) oracleManager
						.createNamedQuery("StreetEnt.getAllActive")
						.getResultList();
				if (list != null && !list.isEmpty()) {

					List strDistrList = oracleManager.createNativeQuery(
							Q_GET_STREET_DISTRICTS_ALL).getResultList();
					TreeMap<String, TreeMap<String, String>> mapStrDistricts = new TreeMap<String, TreeMap<String, String>>();
					if (strDistrList != null && !strDistrList.isEmpty()) {
						for (Object object : strDistrList) {
							Object row[] = (Object[]) object;
							Long street_id = new Long(row[1].toString());
							Long city_region_id = new Long(row[2].toString());
							String city_region_name_geo = row[3].toString();
							TreeMap<String, String> mapItem = mapStrDistricts
									.get(street_id.toString());
							if (mapItem == null) {
								mapItem = new TreeMap<String, String>();
							}
							mapItem.put(city_region_id.toString(),
									city_region_name_geo);
							mapStrDistricts.put(street_id.toString(), mapItem);
						}
					}

					streetsByCityId = new TreeMap<Long, ArrayList<StreetEnt>>();

					for (StreetEnt streetEnt : list) {
						TreeMap<String, String> mapItem = mapStrDistricts
								.get(streetEnt.getStreet_id().toString());
						if (mapItem != null) {
							streetEnt.setMapStreDistricts(mapItem);
						}
						Long city_id = streetEnt.getCity_id();
						if (city_id != null) {
							ArrayList<StreetEnt> listByCity = streetsByCityId
									.get(city_id);
							if (listByCity == null) {
								listByCity = new ArrayList<StreetEnt>();
							}
							listByCity.add(streetEnt);
							streetsByCityId.put(city_id, listByCity);
						}
						streetEnts.put(streetEnt.getStreet_id(), streetEnt);
					}
				}
			}
			log += ". Fetching Streets Finished SuccessFully. ";
			logger.info(log);
			ArrayList<StreetEnt> ret = new ArrayList<StreetEnt>();
			if (streetEnts != null && !streetEnts.isEmpty()) {

				Object oStreet_id = dsRequest.getFieldValue("street_id");
				Long street_id = -100L;
				if (oStreet_id != null) {
					street_id = new Long(oStreet_id.toString());
				}
				if (!street_id.equals(-100L)) {
					ret.add(streetEnts.get(street_id));
				} else {
					Object oCity_id = dsRequest.getFieldValue("city_id");
					if (oCity_id != null) {
						Long city_id = new Long(oCity_id.toString());
						ret.addAll(streetsByCityId.get(city_id));
					} else {
						ret.addAll(streetEnts.values());
					}
				}
			}
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Streets From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების წამოღებისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Adding New Street Description
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public StreetDescr addStreetDescr(StreetDescr streetDescr) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addStreetDescr.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			streetDescr.setRec_date(recDate);

			oracleManager.persist(streetDescr);

			StreetDescr retItem = oracleManager.find(StreetDescr.class,
					streetDescr.getStreet_descr_id());

			streetDescrs.put(retItem.getStreet_descr_id(), retItem);
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Insert Street Description Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Updating Street Descr
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetDescr updateStreetDescr(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetDescr.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_descr_id = new Long(record.get("street_descr_id")
					.toString());
			String street_descr_name_geo = record.get("street_descr_name_geo")
					.toString();
			String street_descr_name_eng = record.get("street_descr_name_eng") != null ? record
					.get("street_descr_name_eng").toString() : null;
			String loggedUserName = record.get("loggedUserName").toString();

			oracleManager.createNativeQuery(Q_UPDATE_STREET_DESCR)
					.setParameter(1, street_descr_name_geo)
					.setParameter(2, street_descr_name_eng)
					.setParameter(3, loggedUserName)
					.setParameter(4, street_descr_id).executeUpdate();

			StreetDescr retItem = oracleManager.find(StreetDescr.class,
					street_descr_id);
			streetDescrs.remove(street_descr_id);
			streetDescrs.put(street_descr_id, retItem);
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Street Descr Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Updating Street Descr Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetDescr updateStreetDescrStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetDescrStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_descr_id = new Long(record.get("street_descr_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			oracleManager.createNativeQuery(Q_UPDATE_STREET_DESCR_STATUS)
					.setParameter(1, deleted).setParameter(2, loggedUserName)
					.setParameter(3, street_descr_id).executeUpdate();

			StreetDescr retItem = oracleManager.find(StreetDescr.class,
					street_descr_id);
			streetDescrs.remove(street_descr_id);
			if (deleted.equals(0L)) {
				streetDescrs.put(street_descr_id, retItem);
			}
			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status Street Descr Into Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Updating Street Descr Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetsOldEnt updateStreetsOldEntStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetsOldEnt.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long old_id = new Long(record.get("old_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			StreetsOldEnt find = oracleManager
					.find(StreetsOldEnt.class, old_id);
			find.setDeleted(deleted);
			find.setUpd_user(loggedUserName);

			oracleManager.merge(find);
			oracleManager.flush();

			find = oracleManager.find(StreetsOldEnt.class, old_id);
			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return find;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status Old Street Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Fetching All Active Street Descrs.
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<StreetDescr> fetchStreetDescrs(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.fetchStreetDescrs.";
			oracleManager = EMF.getEntityManager();
			if (streetDescrs == null || streetDescrs.isEmpty()) {
				log += " fetching Street Descriptions From DB ...";
				streetDescrs = new TreeMap<Long, StreetDescr>();
				ArrayList<StreetDescr> list = (ArrayList<StreetDescr>) oracleManager
						.createNamedQuery("StreetDescr.getAllActive")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (StreetDescr streetDescr : list) {
						streetDescrs.put(streetDescr.getStreet_descr_id(),
								streetDescr);
					}
				}
			}
			log += ". Fetching StreetDescrs Finished SuccessFully. ";
			logger.info(log);
			ArrayList<StreetDescr> ret = new ArrayList<StreetDescr>();
			if (streetDescrs != null && !streetDescrs.isEmpty()) {
				ret.addAll(streetDescrs.values());
			}
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching StreetDescrs From Database : ",
					e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Adding New Street Type
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public StreetType addStreetType(StreetType streetType) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addStreetType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			streetType.setRec_date(recDate);

			oracleManager.persist(streetType);

			StreetType retItem = oracleManager.find(StreetType.class,
					streetType.getStreet_type_Id());
			streetTypes.put(retItem.getStreet_type_Id(), retItem);
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert StreetTypes Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Updating Street Type
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetType updateStreetType(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_type_Id = new Long(record.get("street_type_Id")
					.toString());
			String street_type_name_geo = record.get("street_type_name_geo")
					.toString();
			String street_type_name_eng = record.get("street_type_name_eng")
					.toString();
			String loggedUserName = record.get("loggedUserName").toString();

			oracleManager.createNativeQuery(Q_UPDATE_STREET_TYPE)
					.setParameter(1, street_type_name_geo)
					.setParameter(2, street_type_name_eng)
					.setParameter(3, loggedUserName)
					.setParameter(4, street_type_Id).executeUpdate();

			StreetType retItem = oracleManager.find(StreetType.class,
					street_type_Id);
			streetTypes.remove(street_type_Id);
			streetTypes.put(street_type_Id, retItem);
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update StreetTypes Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Updating Street Type Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetType updateStreetTypeStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetTypeStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_type_Id = new Long(record.get("street_type_Id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			oracleManager.createNativeQuery(Q_UPDATE_STREET_TYPE_STATUS)
					.setParameter(1, deleted).setParameter(2, loggedUserName)
					.setParameter(3, street_type_Id).executeUpdate();

			StreetType retItem = oracleManager.find(StreetType.class,
					street_type_Id);
			streetTypes.remove(street_type_Id);
			if (deleted.equals(0L)) {
				streetTypes.put(street_type_Id, retItem);
			}
			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status StreetTypes Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Fetching All Active Street Type
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<StreetType> fetchStreetTypes(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.fetchStreetTypeStatus.";
			oracleManager = EMF.getEntityManager();
			if (streetTypes == null || streetTypes.isEmpty()) {
				log += " fetching StreetStatuses From DB ...";
				streetTypes = new TreeMap<Long, StreetType>();
				ArrayList<StreetType> list = (ArrayList<StreetType>) oracleManager
						.createNamedQuery("StreetType.getAllActive")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (StreetType streetType : list) {
						streetTypes.put(streetType.getStreet_type_Id(),
								streetType);
					}
				}
			}
			log += ". Fetching StreetTypes Finished SuccessFully. ";
			logger.info(log);
			ArrayList<StreetType> ret = new ArrayList<StreetType>();
			if (streetTypes != null && !streetTypes.isEmpty()) {
				ret.addAll(streetTypes.values());
			}
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching StreetTypes From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (oracleManager != null) {
					EMF.returnEntityManager(oracleManager);
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Adding New City Region
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public CityRegion addCityRegion(CityRegion cityRegion) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addCityRegion.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			cityRegion.setRec_date(recDate);
			cityRegion.setCity_region_type_id(1L);
			cityRegion.setDeleted(0L);
			cityRegion.setMap_id(0L);
			oracleManager.persist(cityRegion);

			oracleManager.flush();

			cityRegion = oracleManager.find(CityRegion.class,
					cityRegion.getCity_region_id());

			cityRegions.put(cityRegion.getCity_region_id(), cityRegion);
			TreeMap<Long, CityRegion> byCityId = cityRegionsByCityId
					.get(cityRegion.getCity_id());
			if (byCityId == null) {
				byCityId = new TreeMap<Long, CityRegion>();
			}
			byCityId.put(cityRegion.getCity_region_id(), cityRegion);
			cityRegionsByCityId.put(cityRegion.getCity_id(), byCityId);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return cityRegion;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert City Region Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating City Region
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public CityRegion updateCityRegion(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateCityRegion.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp upd_date = new Timestamp(System.currentTimeMillis());
			Long city_region_id = new Long(record.get("city_region_id")
					.toString());
			Long city_id = new Long(record.get("city_id").toString());
			String city_region_name_eng = record.get("city_region_name_eng")
					.toString();
			String city_region_name_geo = record.get("city_region_name_geo")
					.toString();
			String loggedUserName = record.get("loggedUserName").toString();

			CityRegion cityRegion = oracleManager.find(CityRegion.class,
					city_region_id);
			cityRegion.setCity_id(city_id);
			cityRegion.setCity_region_name_eng(city_region_name_eng);
			cityRegion.setCity_region_name_geo(city_region_name_geo);
			cityRegion.setUpd_date(upd_date);
			cityRegion.setUpd_user(loggedUserName);

			oracleManager.merge(cityRegion);
			oracleManager.flush();

			cityRegion = oracleManager.find(CityRegion.class, city_region_id);

			cityRegions.remove(cityRegion.getCity_region_id());
			cityRegions.put(cityRegion.getCity_region_id(), cityRegion);
			TreeMap<Long, CityRegion> byCityId = cityRegionsByCityId
					.get(cityRegion.getCity_id());
			if (byCityId == null) {
				byCityId = new TreeMap<Long, CityRegion>();
			}
			byCityId.remove(cityRegion.getCity_region_id());
			byCityId.put(cityRegion.getCity_region_id(), cityRegion);
			cityRegionsByCityId.remove(cityRegion.getCity_id());
			cityRegionsByCityId.put(cityRegion.getCity_id(), byCityId);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return cityRegion;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update City Regions Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating CityRegion Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public CityRegion updateCityRegionStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateCityRegionStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long city_region_id = new Long(record.get("city_region_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			CityRegion cityRegion = oracleManager.find(CityRegion.class,
					city_region_id);
			cityRegion.setDeleted(deleted);
			cityRegion.setUpd_user(loggedUserName);

			oracleManager.merge(cityRegion);
			oracleManager.flush();

			cityRegion = oracleManager.find(CityRegion.class, city_region_id);

			cityRegions.remove(cityRegion.getCity_region_id());
			cityRegions.put(cityRegion.getCity_region_id(), cityRegion);
			TreeMap<Long, CityRegion> byCityId = cityRegionsByCityId
					.get(cityRegion.getCity_id());
			if (byCityId == null) {
				byCityId = new TreeMap<Long, CityRegion>();
			}
			byCityId.remove(cityRegion.getCity_region_id());
			byCityId.put(cityRegion.getCity_region_id(), cityRegion);
			cityRegionsByCityId.remove(cityRegion.getCity_id());
			cityRegionsByCityId.put(cityRegion.getCity_id(), byCityId);

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return cityRegion;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update City Regions Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<CityRegion> fetchCityRegions(DSRequest dsRequest)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			Object oCityId = dsRequest.getFieldValue("city_id");
			Long pCity_id = -100L;
			if (oCityId != null) {
				pCity_id = new Long(oCityId.toString());
			}

			logger.info("getting city regions names ...");
			if (cityRegions == null || cityRegions.isEmpty()) {

				logger.info("getting city regions from DB. ");
				oracleManager = EMF.getEntityManager();

				cityRegions = new TreeMap<Long, CityRegion>();
				cityRegionsByCityId = new TreeMap<Long, TreeMap<Long, CityRegion>>();

				ArrayList<CityRegion> cityRegList = (ArrayList<CityRegion>) oracleManager
						.createNamedQuery("CityRegion.getAllActive")
						.getResultList();

				if (cityRegList != null && !cityRegList.isEmpty()) {
					for (CityRegion cityRegion : cityRegList) {
						Long city_id = cityRegion.getCity_id();
						if (city_id != null) {
							TreeMap<Long, CityRegion> listByCity = cityRegionsByCityId
									.get(city_id);
							if (listByCity == null) {
								listByCity = new TreeMap<Long, CityRegion>();
							}
							listByCity.put(cityRegion.getCity_region_id(),
									cityRegion);
							cityRegionsByCityId.put(city_id, listByCity);
						}
						cityRegions.put(cityRegion.getCity_region_id(),
								cityRegion);
					}
				}
			}
			if (pCity_id.equals(-100L)) {
				ArrayList<CityRegion> ret = new ArrayList<CityRegion>();
				ret.addAll(cityRegions.values());
				return ret;
			} else {
				TreeMap<Long, CityRegion> map = cityRegionsByCityId
						.get(pCity_id);
				ArrayList<CityRegion> ret = new ArrayList<CityRegion>();
				if (map != null) {
					ret.addAll(map.values());
				}
				return ret;
			}
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Select City Regions From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public City cityAdd(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			Timestamp rec_date = new Timestamp(System.currentTimeMillis());
			Long country_id = new Long(dsRequest.getFieldValue("country_id")
					.toString());
			Country country = null;
			if (country_id != null) {
				if (countries.isEmpty()) {
					fetchAllCountries(dsRequest);
				}
				country = getCountry(country_id);
			}
			Long cityTypeId = new Long(dsRequest.getFieldValue("city_type_id")
					.toString());
//			CityType cityType = null;
//			if (cityTypeId != null) {
//				if (cityTypes.isEmpty()) {
//					fetchCityTypes(dsRequest);
//				}
//				cityType = getCityType(cityTypeId);
//			}

			City city = new City();
			city.setCity_code(dsRequest.getFieldValue("city_code") == null ? null
					: dsRequest.getFieldValue("city_code").toString());
			city.setCity_name_eng(dsRequest.getFieldValue("city_name_eng") == null ? null
					: dsRequest.getFieldValue("city_name_eng").toString());
			city.setCity_name_geo(dsRequest.getFieldValue("city_name_geo") == null ? null
					: dsRequest.getFieldValue("city_name_geo").toString());
			city.setCity_new_code(dsRequest.getFieldValue("city_new_code") == null ? null
					: dsRequest.getFieldValue("city_new_code").toString());
			city.setIs_capital(dsRequest.getFieldValue("is_capital") == null ? null
					: new Long(dsRequest.getFieldValue("is_capital").toString()));
			city.setOf_gmt(dsRequest.getFieldValue("of_gmt") == null ? null
					: new Long(dsRequest.getFieldValue("of_gmt").toString()));
			city.setOf_gmt_winter(dsRequest.getFieldValue("of_gmt_winter") == null ? null
					: new Long(dsRequest.getFieldValue("of_gmt_winter")
							.toString()));
			city.setCity_type_id(cityTypeId);
//			if (cityType != null) {
//				city.setCityType(cityType.getCity_type_geo());
//			}
			city.setCountry_id(country_id);
			if (country != null) {
				city.setCountryName(country.getCountry_name());
			}
			city.setCountry_region_id(-1L);
			city.setDeleted(0L);
			city.setMap_id(-1L);
			city.setRec_date(rec_date);
			city.setRec_user(loggedUserName);
			city.setIsCapitalText(city.getIs_capital() != null
					&& city.getIs_capital().equals(1L) ? "დედაქალაქი"
					: "ჩვეულებრივი");

			oracleManager.persist(city);

			EMF.commitTransaction(transaction);
			cities.put(city.getCity_id(), city);
			return city;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While adding City into Database : ", e);
			throw new CallCenterException(
					"შეცდომა ქალაქის მონაცემის შენახვისას : " + e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public City cityUpdate(Map fieldValues) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Long city_id = new Long(fieldValues.get("city_id").toString());
			Long country_id = new Long(fieldValues.get("country_id").toString());
			String loggedUserName = fieldValues.get("loggedUserName")
					.toString();

			City old_city = oracleManager.find(City.class, city_id);

			Country country = null;
			if (country_id != null) {
				if (countries.isEmpty()) {
					fetchAllCountries(null);
				}
				country = getCountry(country_id);
			}
			Long cityTypeId = new Long(fieldValues.get("city_type_id")
					.toString());
//			CityType cityType = null;
//			if (cityTypeId != null) {
//				if (cityTypes.isEmpty()) {
//					fetchCityTypes(null);
//				}
//				cityType = getCityType(cityTypeId);
//			}
			Timestamp curr_date = new Timestamp(System.currentTimeMillis());

			oracleManager
					.createNativeQuery(Q_UPDATE_CITY)
					.setParameter(
							1,
							fieldValues.get("city_name_geo") == null ? null
									: fieldValues.get("city_name_geo")
											.toString())
					.setParameter(
							2,
							fieldValues.get("city_name_eng") == null ? null
									: fieldValues.get("city_name_eng")
											.toString())
					.setParameter(3, country_id)
					.setParameter(4, cityTypeId)
					.setParameter(
							5,
							fieldValues.get("of_gmt") == null ? null
									: new Long(fieldValues.get("of_gmt")
											.toString()))
					.setParameter(
							6,
							fieldValues.get("of_gmt_winter") == null ? null
									: new Long(fieldValues.get("of_gmt_winter")
											.toString()))
					.setParameter(
							7,
							fieldValues.get("is_capital") == null ? null
									: new Long(fieldValues.get("is_capital")
											.toString()))
					.setParameter(8, loggedUserName)
					.setParameter(
							9,
							fieldValues.get("city_code") == null ? null
									: fieldValues.get("city_code").toString())
					.setParameter(10, curr_date)
					.setParameter(
							11,
							fieldValues.get("city_new_code") == null ? null
									: fieldValues.get("city_new_code")
											.toString())
					.setParameter(12, city_id).executeUpdate();

			if (old_city != null) {
				boolean changed = false;
				if (old_city.getCity_name_geo() != null) {
					if (old_city.getCity_name_geo().compareTo(
							fieldValues.get("city_name_geo").toString()) > 0) {
						changed = true;
					}
				} else if (fieldValues.get("city_name_geo").toString()
						.equals(old_city.getCity_name_geo())) {
					changed = true;
				}
				if (changed) {
					// ArrayList<TranspStation> trPlaces =
					// (ArrayList<TranspStation>) oracleManager
					// .createNamedQuery("TranspStation.getByCityId")
					// .setParameter("city_id", city_id).getResultList();
					// if (trPlaces != null && !trPlaces.isEmpty()) {
					// String newCityName = fieldValues.get("city_name_geo")
					// .toString();
					// for (TranspStation transportPlace : trPlaces) {
					// Long transpTypeId = transportPlace
					// .getTransport_type_id();
					// TranspType transportType = oracleManager.find(
					// TranspType.class, transpTypeId);
					// String newDescr = newCityName
					// + " "
					// + transportPlace.getTransport_place_geo()
					// + " ( "
					// + (transportType == null ? "NULL"
					// : transportType.getName_descr())
					// + " ) ";
					// transportPlace
					// .setTransport_place_geo_descr(newDescr);
					// oracleManager.merge(transportPlace);
					// }
					// }
				}
			}

			oracleManager.flush();
			City city = oracleManager.find(City.class, city_id);
			if (country != null) {
				city.setCountryName(country.getCountry_name());
			}
//			if (cityType != null) {
//				city.setCityType(cityType.getCity_type_geo());
//			}
			city.setIsCapitalText(city.getIs_capital() != null
					&& city.getIs_capital().equals(1L) ? "დედაქალაქი"
					: "ჩვეულებრივი");
			EMF.commitTransaction(transaction);
			cities.remove(city_id);
			cities.put(city_id, city);
			return city;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While updating City into Database : ", e);
			throw new CallCenterException(
					"შეცდომა ქალაქის მონაცემების განახლებისას : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public City cityStatusUpdate(Map fieldValues) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Long city_id = -1L;
			Object oCity_id = fieldValues.get("city_id");
			if (oCity_id != null) {
				city_id = new Long(oCity_id.toString());
			}
			Timestamp curr_date = new Timestamp(System.currentTimeMillis());
			String loggedUserName = fieldValues.get("loggedUserName")
					.toString();

			oracleManager
					.createNativeQuery(Q_UPDATE_CITY_STATUS)
					.setParameter(1, loggedUserName)
					.setParameter(2, curr_date)
					.setParameter(3,
							new Integer(fieldValues.get("deleted").toString()))
					.setParameter(4, city_id).executeUpdate();

			oracleManager.flush();
			City city = oracleManager.find(City.class, city_id);
			Long country_id = city.getCountry_id();
			if (country_id != null) {
				if (countries.isEmpty()) {
					fetchAllCountries(null);
				}
				Country country = getCountry(country_id);
				if (country != null) {
					city.setCountryName(country.getCountry_name());
				}
			}
			Long cityTypeId = city.getCity_type_id();
//			if (cityTypeId != null) {
//				if (cityTypes.isEmpty()) {
//					fetchCityTypes(null);
//				}
//				CityType cityType = getCityType(cityTypeId);
//				if (cityType != null) {
//					city.setCityType(cityType.getCity_type_geo());
//				}
//			}
			city.setIsCapitalText(city.getIs_capital() != null
					&& city.getIs_capital().equals(1L) ? "დედაქალაქი"
					: "ჩვეულებრივი");
			EMF.commitTransaction(transaction);
			cities.remove(city_id);
			cities.put(city_id, city);
			return city;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While updating City Status into Database : ", e);
			throw new CallCenterException(
					"შეცდომა ქალაქის სტატუსის მონაცემების განახლებისას : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public Country countryAdd(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long continent_id = new Long(dsRequest
					.getFieldValue("continent_id").toString());
			if (continents.isEmpty()) {
				fetchContinents(dsRequest);
			}
			Continents continent = continents.get(continent_id);

			Country country = new Country();
			country.setCountry_name(dsRequest.getFieldValue("country_name")
					.toString());
			country.setPhone_code(dsRequest.getFieldValue("phone_code")
					.toString());
			country.setContinent_id(continent_id);
			country.setSeason(0L);
			country.setContinent(continent != null ? continent.getName_descr()
					: null);

			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add Country.");

			oracleManager.persist(country);

			EMF.commitTransaction(transaction);
			countries.put(country.getCountry_id(), country);
			return country;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While adding Country into Database : ", e);
			throw new CallCenterException(
					"შეცდომა მომხმარებლის მონაცემების შენახვისას : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Country countryUpdate(Map fieldValues) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Long country_id = -1L;
			Object oCountry_id = fieldValues.get("country_id");

			if (oCountry_id != null) {
				country_id = new Long(oCountry_id.toString());
			}
			String loggedUserName = fieldValues.get("loggedUserName")
					.toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update Country.");

			oracleManager
					.createNativeQuery(Q_UPDATE_COUNTRY)
					.setParameter(1, fieldValues.get("country_name").toString())
					.setParameter(2, fieldValues.get("phone_code").toString())
					.setParameter(
							3,
							new Long(fieldValues.get("continent_id").toString()))
					.setParameter(4, country_id).executeUpdate();
			oracleManager.flush();
			Country country = oracleManager.find(Country.class, country_id);
			Long continent_id = country.getContinent_id();
			if (continent_id != null) {
				if (continents.isEmpty()) {
					fetchContinents(null);
				}
				Continents continent = continents.get(continent_id);
				if (continent != null) {
					country.setContinent(continent.getName_descr());
				}
			}
			countries.remove(country_id);
			countries.put(country_id, country);
			EMF.commitTransaction(transaction);
			return country;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While updating Country into Database : ", e);
			throw new CallCenterException(
					"შეცდომა მომხმარებლის მონაცემების განახლებისას : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public Country deleteCountry(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Long country_id = -1L;
			Object oCountry_id = dsRequest.getOldValues().get("country_id");
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			if (oCountry_id != null) {
				country_id = new Long(oCountry_id.toString());
			}
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Delete Country.");

			oracleManager.createNativeQuery(Q_DELETE_COUNTRY)
					.setParameter(1, country_id).executeUpdate();

			oracleManager.flush();

			countries.remove(country_id);
			EMF.commitTransaction(transaction);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While updating Country into Database : ", e);
			throw new CallCenterException(
					"შეცდომა მომხმარებლის მონაცემების განახლებისას : "
							+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Country> fetchAllCountriesFromDB(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {

			logger.info("getting Countries From Database ... ");

			Object country_name = dsRequest.getFieldValue("country_name");
			Object phone_code = dsRequest.getFieldValue("phone_code");
			Object continent_id = dsRequest.getFieldValue("continent_id");

			String query = "select e from Country e where 1 = 1 ";
			if (country_name != null
					&& !country_name.toString().trim().equalsIgnoreCase("")) {
				query += " and e.country_name like '" + country_name.toString()
						+ "%'";
			}
	
			if (phone_code != null
					&& !phone_code.toString().trim().equalsIgnoreCase("")) {
				query += " and e.phone_code like '" + phone_code.toString()
						+ "%'";
			}
			if (continent_id != null) {
				query += " and e.continent_id = "
						+ new Long(continent_id.toString());
			}
			query += " order by e.country_id ";
			
			System.out.println(query);

			oracleManager = EMF.getEntityManager();
			ArrayList<Country> result = (ArrayList<Country>) oracleManager
					.createQuery(query).getResultList();
			if (result != null && !result.isEmpty()) {
				for (Country country : result) {
					Long continentId = country.getContinent_id();
					if (continentId != null) {
						if (continents.isEmpty()) {
							fetchContinents(dsRequest);
						}
						Continents continent = continents.get(continentId);
						if (continent != null) {
							country.setContinent(continent.getName_descr());
						}

					}
				}
			}
			return result;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Countries From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public ArrayList<Country> fetchAllCountries(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {
			logger.info("getting Countries ... ");
			if (countries == null || countries.isEmpty()) {
				countries = new TreeMap<Long, Country>();
				ArrayList<Country> tmpCountries = fetchAllCountriesFromDB(dsRequest);
				if (tmpCountries != null && !tmpCountries.isEmpty()) {
					for (Country country : tmpCountries) {
						countries.put(country.getCountry_id(), country);
					}
				}
			}
			ArrayList<Country> ret = new ArrayList<Country>();
			ret.addAll(countries.values());
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Countries From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<City> fetchAllCitiesFromDB(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {
			logger.info("getting Cities From Database ... ");

			Object city_name_geo = dsRequest.getFieldValue("city_name_geo");
			Object city_name_eng = dsRequest.getFieldValue("city_name_eng");
			Object country_id = dsRequest.getFieldValue("country_id");
			Object deleted = dsRequest.getFieldValue("deleted");
			Object city_type_id = dsRequest.getFieldValue("city_type_id");
			Object is_capital = dsRequest.getFieldValue("is_capital");
			Object city_code = dsRequest.getFieldValue("city_code");
			Object city_new_code = dsRequest.getFieldValue("city_new_code");
			Object of_gmt = dsRequest.getFieldValue("of_gmt");
			Object of_gmt_winter = dsRequest.getFieldValue("of_gmt_winter");

			String query = "select e from City e where e.deleted = 0 ";
			if (city_name_geo != null
					&& !city_name_geo.toString().trim().equalsIgnoreCase("")) {
				query += " and e.city_name_geo like '"
						+ city_name_geo.toString() + "%'";
			}
			if (city_name_eng != null
					&& !city_name_eng.toString().trim().equalsIgnoreCase("")) {
				query += " and e.city_name_eng like '"
						+ city_name_eng.toString() + "%'";
			}
			if (city_code != null
					&& !city_code.toString().trim().equalsIgnoreCase("")) {
				query += " and e.city_code like '" + city_code.toString()
						+ "%'";
			}
			if (city_new_code != null
					&& !city_new_code.toString().trim().equalsIgnoreCase("")) {
				query += " and e.city_new_code like '"
						+ city_new_code.toString() + "%'";
			}
			if (country_id != null) {
				query += " and e.country_id = "
						+ new Long(country_id.toString());
			}
			if (deleted != null) {
				query += " and e.deleted = " + new Long(deleted.toString());
			}
			if (city_type_id != null) {
				query += " and e.city_type_id = "
						+ new Long(city_type_id.toString());
			}
			if (is_capital != null) {
				query += " and e.is_capital = "
						+ new Long(is_capital.toString());
			}
			if (of_gmt != null) {
				query += " and e.of_gmt = " + of_gmt.toString();
			}
			if (of_gmt_winter != null) {
				query += " and e.of_gmt_winter = " + of_gmt_winter.toString();
			}
			query += " order by e.city_id ";

			oracleManager = EMF.getEntityManager();
			ArrayList<City> result = (ArrayList<City>) oracleManager
					.createQuery(query).getResultList();
			if (result != null && !result.isEmpty()) {
				for (City city : result) {
					Long cTypeId = city.getCity_type_id();
					if (cTypeId != null) {
//						if (cityTypes.isEmpty()) {
//							fetchCityTypes(dsRequest);
//						}
//						CityType cityType = getCityType(cTypeId);
//						if (cityType != null) {
//							city.setCityType(cityType.getCity_type_geo());
//						}
					}
					Long countryId = city.getCountry_id();
					if (countryId != null) {
						if (countries.isEmpty()) {
							fetchAllCountries(dsRequest);
						}
						Country country = getCountry(countryId);
						if (country != null) {
							city.setCountryName(country.getCountry_name());
						}
					}
					city.setIsCapitalText(city.getIs_capital() != null
							&& city.getIs_capital().equals(1L) ? "დედაქალაქი"
							: "ჩვეულებრივი");
				}
			}
			return result;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Cities From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public ArrayList<City> fetchAllCities(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		try {
			logger.info("getting Cities ... ");
			if (cities == null || cities.isEmpty()) {
				cities = new TreeMap<Long, City>();
				ArrayList<City> tmpCities = fetchAllCitiesFromDB(dsRequest);
				if (tmpCities != null && !tmpCities.isEmpty()) {
					for (City city : tmpCities) {
						cities.put(city.getCity_id(), city);
					}
				}
			}
			ArrayList<City> ret = new ArrayList<City>();
			ret.addAll(cities.values());
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Cities From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Continents> fetchContinents(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {

			logger.info("getting Continents ...");

			if (continents == null || continents.isEmpty()) {
				logger.info("getting continents from DB. ");
				continents = new TreeMap<Long, Continents>();
				oracleManager = EMF.getEntityManager();
				ArrayList<Continents> result = (ArrayList<Continents>) oracleManager
						.createNamedQuery("Continents.getAllContinents")
						.getResultList();
				if (result != null && !result.isEmpty()) {
					for (Continents continent : result) {
						Long id = continent.getId();
						continents.put(id, continent);
					}
				}
			}
			ArrayList<Continents> ret = new ArrayList<Continents>();
			ret.addAll(continents.values());
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Fetching Continents From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	//@SuppressWarnings("unchecked")
//	public ArrayList<CityType> fetchCityTypes(DSRequest dsRequest)
//			throws Exception {
//		EntityManager oracleManager = null;
//		try {
//			logger.info("getting CityTypes ...");
//			if (cityTypes == null || cityTypes.isEmpty()) {
//				logger.info("getting CityTypes from DB. ");
//				cityTypes = new TreeMap<Long, CityType>();
//				oracleManager = EMF.getEntityManager();
//				ArrayList<CityType> result = (ArrayList<CityType>) oracleManager
//						.createNamedQuery("CityType.getAllCityTypes")
//						.getResultList();
//				if (result != null && !result.isEmpty()) {
//					for (CityType cityType : result) {
//						Long id = cityType.getCity_type_id();
//						cityTypes.put(id, cityType);
//					}
//				}
//			}
//			ArrayList<CityType> ret = new ArrayList<CityType>();
//			ret.addAll(cityTypes.values());
//			return ret;
//		} catch (Exception e) {
//			if (e instanceof CallCenterException) {
//				throw (CallCenterException) e;
//			}
//			logger.error("Error While Fetching CityTypes From Database : ", e);
//			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
//					+ e.toString());
//		} finally {
//			if (oracleManager != null) {
//				EMF.returnEntityManager(oracleManager);
//			}
//		}
//	}

	public ArrayList<Departments> fetchDepartments(DSRequest dsRequest)
			throws CallCenterException {
		PreparedStatement selectStmt = null;
		Connection connection = null;
		try {
			logger.info("getting PersonnelTypes ...");

			if (departments == null || departments.isEmpty()) {
				logger.info("getting departments from DB. ");
				departments = new TreeMap<Integer, Departments>();
				DataSource ds = DataSourceManager.get("CallSessDS");
				SQLDataSource sqlDS = (SQLDataSource) ds;
				connection = sqlDS.getConnection();

				selectStmt = connection.prepareStatement(Q_GET_DEPARTMENTS);
				ResultSet rDepartments = selectStmt.executeQuery();
				while (rDepartments.next()) {
					Departments department = new Departments();
					Integer department_id = rDepartments.getInt(1);
					department.setDepartment_id(department_id);
					department.setDepartment_name(rDepartments.getString(2));
					departments.put(department_id, department);
				}
			}
			ArrayList<Departments> ret = new ArrayList<Departments>();
			ret.addAll(departments.values());
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Select Departments From Database : ", e);
			throw new CallCenterException(
					"შეცდომა მონაცემების წამოღებისას მონაცემთა ბაზიდან : "
							+ e.toString());
		} finally {
			try {
				if (selectStmt != null) {
					selectStmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების წამოღებისას მონაცემთა ბაზიდან Ex: "
								+ e2.toString());
			}
		}
	}

	public ArrayList<LastName> fetchLastNames(DSRequest dsRequest)
			throws CallCenterException {
		PreparedStatement selectStmt = null;
		Connection connection = null;
		try {
			logger.info("getting lastnames ...");

			if (lastNames == null || lastNames.isEmpty()) {
				logger.info("getting lastnames from DB. ");
				lastNames = new TreeMap<Integer, LastName>();
				DataSource ds = DataSourceManager.get("CallSessDS");
				SQLDataSource sqlDS = (SQLDataSource) ds;
				connection = sqlDS.getConnection();

				selectStmt = connection.prepareStatement(Q_GET_LAST_NAMES_ALL);
				ResultSet rLastNames = selectStmt.executeQuery();
				while (rLastNames.next()) {
					LastName lastName = new LastName();
					Integer lastname_id = rLastNames.getInt(4);
					lastName.setDeleted(rLastNames.getInt(1));
					lastName.setDeletedText(rLastNames.getString(2));
					lastName.setLastname(rLastNames.getString(3));
					lastName.setLastname_Id(lastname_id);
					lastName.setRec_date(rLastNames.getTimestamp(5));
					lastNames.put(lastname_id, lastName);
				}
			}
			ArrayList<LastName> ret = new ArrayList<LastName>();
			ret.addAll(lastNames.values());
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Select LastNames From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (selectStmt != null) {
					selectStmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	public ArrayList<FirstName> fetchFirstNames(DSRequest dsRequest)
			throws CallCenterException {
		PreparedStatement selectStmt = null;
		Connection connection = null;
		try {
			logger.info("getting first names ...");
			if (firstNames == null || firstNames.isEmpty()) {

				logger.info("getting first names from DB. ");
				firstNames = new TreeMap<Integer, FirstName>();
				DataSource ds = DataSourceManager.get("CallSessDS");
				SQLDataSource sqlDS = (SQLDataSource) ds;
				connection = sqlDS.getConnection();

				selectStmt = connection.prepareStatement(Q_GET_FIRST_NAMES_ALL);
				ResultSet rFirstNames = selectStmt.executeQuery();
				while (rFirstNames.next()) {
					FirstName firstName = new FirstName();
					Integer firstname_id = rFirstNames.getInt(4);
					firstName.setDeleted(rFirstNames.getInt(1));
					firstName.setDeletedText(rFirstNames.getString(2));
					firstName.setFirstname(rFirstNames.getString(3));
					firstName.setFirstname_Id(firstname_id);
					firstName.setRec_date(rFirstNames.getTimestamp(5));
					firstNames.put(firstname_id, firstName);
				}
			}
			ArrayList<FirstName> ret = new ArrayList<FirstName>();
			ret.addAll(firstNames.values());
			return ret;
		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Select FirstNames From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (selectStmt != null) {
					selectStmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Adding New First Name
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public FirstName addFirstName(FirstName record) throws Exception {
		PreparedStatement countStatement = null;
		CallableStatement insertStatement = null;
		Connection connection = null;
		try {
			String firstName = record.getFirstname();
			String loggedUserName = record.getLoggedUserName();

			String log = "Method:CommonDMI.addFirstName. Params : 1. firstName = "
					+ firstName;
			if (firstName == null || firstName.trim().equals("")) {
				log += ". Result : Invalid SessionId";
				logger.info(log);
				throw new CallCenterException("არასწორი სახელი : " + firstName);
			}
			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();
			countStatement = connection
					.prepareStatement(Q_GET_FIRST_NAME_COUNT);
			countStatement.setString(1, firstName);
			ResultSet names = countStatement.executeQuery();
			if (names.next()) {
				int count = names.getInt(1);
				if (count > 0) {
					log += ". Result : Duplicate First Name ";
					logger.info(log);
					throw new CallCenterException(
							"ასეთი სახელი უკვე არსებობს: " + firstName);
				}
			}
			connection.setAutoCommit(false);

			insertStatement = connection
					.prepareCall("{ call ccare.newBillSupport2.saveOrUpdateFirstName( ?,?,?,?,?,? ) }");
			insertStatement.setInt(1, -100);
			insertStatement.setString(2, firstName);
			insertStatement.setTimestamp(3, recDate);
			insertStatement.setString(4, loggedUserName);
			insertStatement.setString(5, "");
			insertStatement.setInt(6, 0);
			insertStatement.registerOutParameter(1, Types.INTEGER);
			insertStatement.executeUpdate();

			Integer firstNameId = insertStatement.getInt(1);

			FirstName retItem = getFirstName(firstNameId, loggedUserName,
					connection);
			firstNames.put(retItem.getFirstname_Id(), retItem);
			connection.commit();
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex1: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert FirstName Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (countStatement != null) {
					countStatement.close();
				}
				if (insertStatement != null) {
					insertStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Update FirstName Table
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public FirstName updateFirstName(Map record) throws Exception {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		try {
			Integer firstname_Id = new Integer(record.get("firstname_Id")
					.toString());
			String firstName = record.get("firstname").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			String log = "Method:CommonDMI.updateFirstName. Params : 1. firstname_Id = "
					+ firstname_Id
					+ ", firstName = "
					+ firstName
					+ ", loggedUserName = " + loggedUserName;

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();

			connection.setAutoCommit(false);

			updateStatement = connection.prepareStatement(Q_UPDATE_FIRST_NAME);
			updateStatement.setString(1, firstName);
			updateStatement.setString(2, loggedUserName);
			updateStatement.setInt(3, firstname_Id);
			updateStatement.executeUpdate();

			log += ". Update Finished Successfully";
			FirstName updatedRecord = getFirstName(firstname_Id,
					loggedUserName, connection);
			connection.commit();
			firstNames.remove(updatedRecord.getFirstname());
			firstNames.put(updatedRecord.getFirstname_Id(), updatedRecord);
			logger.info(log);
			return updatedRecord;
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex2: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update FirstName Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების განახლებისას : "
					+ e.toString());
		} finally {
			try {
				if (updateStatement != null) {
					updateStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Update FirstName Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public FirstName updateFirstNameStatus(Map record) throws Exception {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		try {
			Integer firstname_Id = new Integer(record.get("firstname_Id")
					.toString());
			Integer deleted = new Integer(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			String log = "Method:CommonDMI.updateFirstNameStatus. Params : 1. firstname_Id = "
					+ firstname_Id
					+ ", deleted = "
					+ deleted
					+ ", loggedUserName = " + loggedUserName;

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();

			connection.setAutoCommit(false);

			updateStatement = connection
					.prepareStatement(Q_UPDATE_FIRST_NAME_STATUS);
			updateStatement.setInt(1, deleted);
			updateStatement.setString(2, loggedUserName);
			updateStatement.setInt(3, firstname_Id);
			updateStatement.executeUpdate();

			log += ". Update Finished Successfully";
			FirstName updatedRecord = getFirstName(firstname_Id,
					loggedUserName, connection);
			firstNames.remove(updatedRecord.getFirstname_Id());
			firstNames.put(updatedRecord.getFirstname_Id(), updatedRecord);
			connection.commit();
			logger.info(log);
			return updatedRecord;
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex2: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update FirstiName Status Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების განახლებისას : "
					+ e.toString());
		} finally {
			try {
				if (updateStatement != null) {
					updateStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	private FirstName getFirstName(Integer firstNameId, String loggedUserName,
			Connection connection) throws CallCenterException {
		PreparedStatement selectNote = null;
		try {
			String log = "Method:CommonDMI.getFirstName. Params : 1. firstNameId = "
					+ firstNameId + ", 2. loggedUserName = " + loggedUserName;
			selectNote = connection.prepareStatement(Q_GET_FIRST_NAME_BY_ID);
			selectNote.setInt(1, firstNameId);
			ResultSet resultSetNote = selectNote.executeQuery();
			if (!resultSetNote.next()) {
				log += ". Result : Invalid FirstName From Database: "
						+ firstNameId;
				logger.info(log);
				throw new CallCenterException(
						"შეცდომა შენიშვნის ჩანაწერის წამოღებისას");
			}
			FirstName existingRecord = new FirstName();
			existingRecord.setLoggedUserName(loggedUserName);
			existingRecord.setFirstname_Id(resultSetNote.getInt(1));
			existingRecord.setFirstname(resultSetNote.getString(2));
			existingRecord.setRec_date(resultSetNote.getTimestamp(3));
			existingRecord.setDeleted(resultSetNote.getInt(4));
			existingRecord.setDeletedText(resultSetNote.getString(5));
			log += ". Result : getFirstName Finished Successfully.";
			logger.info(log);
			return existingRecord;
		} catch (Exception e) {
			logger.error("Error While Retrieving FirstNames : ", e);
			throw new CallCenterException(
					"შეცდომა მონაცემების წამოღებისას მონაცემთა ბაზიდან: "
							+ e.toString());
		} finally {
			try {
				if (selectNote != null) {
					selectNote.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Adding New LastName
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public LastName addLastName(LastName record) throws Exception {
		PreparedStatement countStatement = null;
		CallableStatement insertStatement = null;
		Connection connection = null;
		try {
			String lastName = record.getLastname();
			String loggedUserName = record.getLoggedUserName();

			String log = "Method:CommonDMI.addLastName. Params : 1. lastName = "
					+ lastName;
			if (lastName == null || lastName.trim().equals("")) {
				log += ". Result : Invalid lastName";
				logger.info(log);
				throw new CallCenterException("არასწორი გვარი : " + lastName);
			}
			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();
			countStatement = connection.prepareStatement(Q_GET_LAST_NAME_COUNT);
			countStatement.setString(1, lastName);
			ResultSet names = countStatement.executeQuery();
			if (names.next()) {
				int count = names.getInt(1);
				if (count > 0) {
					log += ". Result : Duplicate LastName ";
					logger.info(log);
					throw new CallCenterException("ასეთი გვარი უკვე არსებობს: "
							+ lastName);
				}
			}
			connection.setAutoCommit(false);

			insertStatement = connection
					.prepareCall("{ call ccare.newBillSupport2.saveOrUpdateLastName( ?,?,?,?,?,? ) }");
			insertStatement.setInt(1, -100);
			insertStatement.setString(2, lastName);
			insertStatement.setTimestamp(3, recDate);
			insertStatement.setString(4, loggedUserName);
			insertStatement.setString(5, "");
			insertStatement.setInt(6, 0);
			insertStatement.registerOutParameter(1, Types.INTEGER);
			insertStatement.executeUpdate();

			Integer lastNameId = insertStatement.getInt(1);

			LastName retItem = getLastName(lastNameId, loggedUserName,
					connection);
			lastNames.put(retItem.getLastname_Id(), retItem);
			connection.commit();
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex1: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert FirstName Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			try {
				if (countStatement != null) {
					countStatement.close();
				}
				if (insertStatement != null) {
					insertStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Update LastName Table
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public LastName updateLastName(Map record) throws Exception {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		try {
			Integer lastname_Id = new Integer(record.get("lastname_Id")
					.toString());
			String lastName = record.get("lastname").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			String log = "Method:CommonDMI.updateLastName. Params : 1. lastname_Id = "
					+ lastname_Id
					+ ", lastName = "
					+ lastName
					+ ", loggedUserName = " + loggedUserName;

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();

			connection.setAutoCommit(false);

			updateStatement = connection.prepareStatement(Q_UPDATE_LAST_NAME);
			updateStatement.setString(1, lastName);
			updateStatement.setString(2, loggedUserName);
			updateStatement.setInt(3, lastname_Id);
			updateStatement.executeUpdate();

			log += ". Update Finished Successfully";
			LastName updatedRecord = getLastName(lastname_Id, loggedUserName,
					connection);
			lastNames.remove(updatedRecord.getLastname_Id());
			lastNames.put(updatedRecord.getLastname_Id(), updatedRecord);
			connection.commit();
			logger.info(log);
			return updatedRecord;
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex2: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update FirstName Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების განახლებისას : "
					+ e.toString());
		} finally {
			try {
				if (updateStatement != null) {
					updateStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	/**
	 * Update LastName Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public LastName updateLastNameStatus(Map record) throws Exception {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		try {
			Integer lastname_Id = new Integer(record.get("lastname_Id")
					.toString());
			Integer deleted = new Integer(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			String log = "Method:CommonDMI.updateLastNameStatus. Params : 1. lastname_Id = "
					+ lastname_Id
					+ ", deleted = "
					+ deleted
					+ ", loggedUserName = " + loggedUserName;

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();

			connection.setAutoCommit(false);

			updateStatement = connection
					.prepareStatement(Q_UPDATE_LAST_NAME_STATUS);
			updateStatement.setInt(1, deleted);
			updateStatement.setString(2, loggedUserName);
			updateStatement.setInt(3, lastname_Id);
			updateStatement.executeUpdate();

			log += ". Update Finished Successfully";
			LastName updatedRecord = getLastName(lastname_Id, loggedUserName,
					connection);
			lastNames.remove(updatedRecord.getLastname_Id());
			lastNames.put(updatedRecord.getLastname_Id(), updatedRecord);
			connection.commit();
			logger.info(log);
			return updatedRecord;
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex2: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update FirstiName Status Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების განახლებისას : "
					+ e.toString());
		} finally {
			try {
				if (updateStatement != null) {
					updateStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

	private LastName getLastName(Integer lastNameId, String loggedUserName,
			Connection connection) throws CallCenterException {
		PreparedStatement selectNote = null;
		try {
			String log = "Method:CommonDMI.getLastName. Params : 1. lastNameId = "
					+ lastNameId + ", 2. loggedUserName = " + loggedUserName;
			selectNote = connection.prepareStatement(Q_GET_LAST_NAME_BY_ID);
			selectNote.setInt(1, lastNameId);
			ResultSet resultSetNote = selectNote.executeQuery();
			if (!resultSetNote.next()) {
				log += ". Result : Invalid LastName From Database: "
						+ lastNameId;
				logger.info(log);
				throw new CallCenterException(
						"შეცდომა შენიშვნის ჩანაწერის წამოღებისას");
			}
			LastName existingRecord = new LastName();
			existingRecord.setLoggedUserName(loggedUserName);
			existingRecord.setLastname_Id(resultSetNote.getInt(1));
			existingRecord.setLastname(resultSetNote.getString(2));
			existingRecord.setRec_date(resultSetNote.getTimestamp(3));
			existingRecord.setDeleted(resultSetNote.getInt(4));
			existingRecord.setDeletedText(resultSetNote.getString(5));
			log += ". Result : getLastName Finished Successfully.";
			logger.info(log);
			return existingRecord;
		} catch (Exception e) {
			logger.error("Error While Retrieving LastNames : ", e);
			throw new CallCenterException(
					"შეცდომა მონაცემების წამოღებისას მონაცემთა ბაზიდან: "
							+ e.toString());
		} finally {
			try {
				if (selectNote != null) {
					selectNote.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Update LastName Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String updateLockStatus(Map record) throws Exception {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		try {

			Object session_my_id = record.get("session_my_id");
			System.out.println("session_my_id = " + session_my_id);

			String session_my_idI = session_my_id.toString();

			DataSource ds = DataSourceManager.get("CallSessDS");
			SQLDataSource sqlDS = (SQLDataSource) ds;
			connection = sqlDS.getConnection();

			connection.setAutoCommit(false);

			updateStatement = connection.prepareStatement(Q_UPDATE_LOCK_STATUS);
			updateStatement.setString(1, session_my_idI);
			updateStatement.executeUpdate();
			connection.commit();

			return "OK";
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception e2) {
				logger.error("Error While Rollback Database : ", e);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex2: " + e.toString());
			}

			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update FirstiName Status Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების განახლებისას : "
					+ e.toString());
		} finally {
			try {
				if (updateStatement != null) {
					updateStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				logger.error("Error While Closing Connection : ", e2);
				throw new CallCenterException(
						"შეცდომა მონაცემების შენახვისას Ex: " + e2.toString());
			}
		}
	}

}
