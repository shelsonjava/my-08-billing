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
import com.info08.billing.callcenterbk.shared.entity.CityDistance;
import com.info08.billing.callcenterbk.shared.entity.Continents;
import com.info08.billing.callcenterbk.shared.entity.Country;
import com.info08.billing.callcenterbk.shared.entity.Street;
import com.info08.billing.callcenterbk.shared.entity.StreetKind;
import com.info08.billing.callcenterbk.shared.entity.StreetNames;
import com.info08.billing.callcenterbk.shared.entity.StreetToTownDistricts;
import com.info08.billing.callcenterbk.shared.entity.StreetsOldNames;
import com.info08.billing.callcenterbk.shared.entity.TownDistrict;
import com.info08.billing.callcenterbk.shared.entity.Towns;
import com.info08.billing.callcenterbk.shared.entity.descriptions.Description;
import com.info08.billing.callcenterbk.shared.items.Departments;
import com.info08.billing.callcenterbk.shared.items.FirstName;
import com.info08.billing.callcenterbk.shared.items.LastName;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DataSource;
import com.isomorphic.datasource.DataSourceManager;
import com.isomorphic.jpa.EMF;
import com.isomorphic.sql.SQLDataSource;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSResponse;

public class CommonDMI implements QueryConstants {

	Logger logger = Logger.getLogger(CommonDMI.class.getName());

	private static TreeMap<Integer, FirstName> firstNames = new TreeMap<Integer, FirstName>();
	private static TreeMap<Integer, LastName> lastNames = new TreeMap<Integer, LastName>();
	private static TreeMap<Integer, Departments> departments = new TreeMap<Integer, Departments>();
	private static TreeMap<Long, Continents> continents = new TreeMap<Long, Continents>();
	private static TreeMap<Long, Country> countries = new TreeMap<Long, Country>();
	private static TreeMap<Long, Towns> towns = new TreeMap<Long, Towns>();
	private static TreeMap<Long, ArrayList<Street>> streetsByCityId = new TreeMap<Long, ArrayList<Street>>();
	private static TreeMap<Long, StreetKind> streetKinds = new TreeMap<Long, StreetKind>();
	private static TreeMap<Long, StreetNames> streetDescrs = new TreeMap<Long, StreetNames>();
	private static TreeMap<Long, Street> streetEnts = new TreeMap<Long, Street>();
	private static TreeMap<Long, TownDistrict> townDistricts = new TreeMap<Long, TownDistrict>();
	private static TreeMap<Long, TreeMap<Long, TownDistrict>> townDistrictByTownId = new TreeMap<Long, TreeMap<Long, TownDistrict>>();

	public static Street getStreetEnt(Long steetEntId) {
		return streetEnts.get(steetEntId);
	}

	public static StreetNames getStreetDescr(Long steetDescrId) {
		return streetDescrs.get(steetDescrId);
	}

	public static TownDistrict getTownDistricts(Integer town_district_id) {
		return townDistricts.get(town_district_id);
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

	public static Towns getTown(Long townId) {
		return towns.get(townId);
	}

	public static Country getCountry(Long countryId) {
		return countries.get(countryId);
	}

	public static StreetKind getStreetKind(Long streetKindId) {
		return streetKinds.get(streetKindId);
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

			Towns cityStart = getTown(cityDistance.getTown_id_start());
			if (cityStart != null) {
				cityDistance.setCityStart(cityStart.getCapital_town_name());
			}
			Towns cityEnd = getTown(cityDistance.getTown_id_end());
			if (cityEnd != null) {
				cityDistance.setCityEnd(cityEnd.getCapital_town_name());
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
			Long town_id_start = new Long(record.get("town_id_start")
					.toString());
			Long town_id_end = new Long(record.get("town_id_end").toString());
			Long city_distance_type = new Long(record.get("city_distance_type")
					.toString());
			String note_geo = record.get("note_geo").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			CityDistance cityDistance = oracleManager.find(CityDistance.class,
					city_distance_id);
			cityDistance.setCity_distance_geo(city_distance_geo);
			cityDistance.setTown_id_start(town_id_start);
			cityDistance.setTown_id_end(town_id_end);
			cityDistance.setCity_distance_type(city_distance_type);
			cityDistance.setNote_geo(note_geo);
			cityDistance.setUpd_date(timestamp);
			cityDistance.setUpd_user(loggedUserName);

			oracleManager.merge(cityDistance);
			oracleManager.flush();

			cityDistance = oracleManager.find(CityDistance.class,
					city_distance_id);

			Towns cityStart = getTown(town_id_start);
			if (cityStart != null) {
				cityDistance.setCityStart(cityStart.getCapital_town_name());
			}
			Towns cityEnd = getTown(town_id_end);
			if (cityEnd != null) {
				cityDistance.setCityEnd(cityEnd.getCapital_town_name());
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

			Towns cityStart = getTown(cityDistance.getTown_id_start());
			if (cityStart != null) {
				cityDistance.setCityStart(cityStart.getCapital_town_name());
			}
			Towns cityEnd = getTown(cityDistance.getTown_id_end());
			if (cityEnd != null) {
				cityDistance.setCityEnd(cityEnd.getCapital_town_name());
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
	public Street addStreet(Street street, DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addStreet.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			String loggedUserName = street.getLoggedUserName();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add StreetKind.");
			String streetName = buildStreetName(street, oracleManager);
			street.setStreet_name(streetName);
			street.setRecord_type(1L);

			oracleManager.persist(street);

			Object oStreet_Districts = dsRequest
					.getFieldValue("mapStreDistricts");
			if (oStreet_Districts != null) {
				Map<String, String> street_Districts = (Map<String, String>) oStreet_Districts;
				Set<String> keySet = street_Districts.keySet();
				for (String town_district_id : keySet) {
					StreetToTownDistricts streetDistrict = new StreetToTownDistricts();
					streetDistrict.setTown_id(street.getTown_id());
					streetDistrict.setTown_district_id(Long
							.parseLong(town_district_id));
					streetDistrict.setStreet_id(street.getStreet_id());
					oracleManager.persist(streetDistrict);
				}
				street.setMapStreDistricts(street_Districts);
			}

			oracleManager.flush();
			street = getStreetEntById(street.getStreet_id(), oracleManager);

			if (street != null) {
				streetEnts.put(street.getStreet_id(), street);
			}
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return street;
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

	private String buildStreetName(Street streetEnt, EntityManager oracleManager)
			throws Exception {
		try {
			if (streetEnt == null) {
				return null;
			}
			StringBuilder streetName = new StringBuilder("");
			Long str_descr_level_1 = streetEnt.getDescr_id_level_1();
			if (str_descr_level_1 != null) {
				StreetNames streetDescr = oracleManager.find(StreetNames.class,
						str_descr_level_1);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_name_descr())
							.append(" ");
				}
			}
			Long str_descr_type_level_1 = streetEnt.getDescr_type_id_level_1();
			if (str_descr_type_level_1 != null) {
				StreetKind streetType = oracleManager.find(StreetKind.class,
						str_descr_type_level_1);
				if (streetType != null) {
					streetName.append(streetType.getStreet_kind_name()).append(
							" ");
				}
			}

			Long str_descr_level_2 = streetEnt.getDescr_id_level_2();
			if (str_descr_level_2 != null) {
				StreetNames streetDescr = oracleManager.find(StreetNames.class,
						str_descr_level_2);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_name_descr())
							.append(" ");
				}
			}
			Long str_descr_type_level_2 = streetEnt.getDescr_type_id_level_2();
			if (str_descr_type_level_2 != null) {
				StreetKind streetType = oracleManager.find(StreetKind.class,
						str_descr_type_level_2);
				if (streetType != null) {
					streetName.append(streetType.getStreet_kind_name()).append(
							" ");
				}
			}

			Long str_descr_level_3 = streetEnt.getDescr_id_level_3();
			if (str_descr_level_3 != null) {
				StreetNames streetDescr = oracleManager.find(StreetNames.class,
						str_descr_level_3);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_name_descr())
							.append(" ");
				}
			}
			Long str_descr_type_level_3 = streetEnt.getDescr_type_id_level_3();
			if (str_descr_type_level_3 != null) {
				StreetKind streetType = oracleManager.find(StreetKind.class,
						str_descr_type_level_3);
				if (streetType != null) {
					streetName.append(streetType.getStreet_kind_name()).append(
							" ");
				}
			}

			Long str_descr_level_4 = streetEnt.getDescr_id_level_4();
			if (str_descr_level_4 != null) {
				StreetNames streetDescr = oracleManager.find(StreetNames.class,
						str_descr_level_4);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_name_descr())
							.append(" ");
				}
			}
			Long str_descr_type_level_4 = streetEnt.getDescr_type_id_level_4();
			if (str_descr_type_level_4 != null) {
				StreetKind streetType = oracleManager.find(StreetKind.class,
						str_descr_type_level_4);
				if (streetType != null) {
					streetName.append(streetType.getStreet_kind_name()).append(
							" ");
				}
			}

			Long str_descr_level_5 = streetEnt.getDescr_id_level_5();
			if (str_descr_level_5 != null) {
				StreetNames streetDescr = oracleManager.find(StreetNames.class,
						str_descr_level_5);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_name_descr())
							.append(" ");
				}
			}
			Long str_descr_type_level_5 = streetEnt.getDescr_type_id_level_5();
			if (str_descr_type_level_5 != null) {
				StreetKind streetType = oracleManager.find(StreetKind.class,
						str_descr_type_level_5);
				if (streetType != null) {
					streetName.append(streetType.getStreet_kind_name()).append(
							" ");
				}
			}

			Long str_descr_level_6 = streetEnt.getDescr_id_level_6();
			if (str_descr_level_6 != null) {
				StreetNames streetDescr = oracleManager.find(StreetNames.class,
						str_descr_level_6);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_name_descr())
							.append(" ");
				}
			}
			Long str_descr_type_level_6 = streetEnt.getDescr_type_id_level_6();
			if (str_descr_type_level_6 != null) {
				StreetKind streetType = oracleManager.find(StreetKind.class,
						str_descr_type_level_6);
				if (streetType != null) {
					streetName.append(streetType.getStreet_kind_name()).append(
							" ");
				}
			}

			Long str_descr_level_7 = streetEnt.getDescr_id_level_7();
			if (str_descr_level_7 != null) {
				StreetNames streetDescr = oracleManager.find(StreetNames.class,
						str_descr_level_7);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_name_descr())
							.append(" ");
				}
			}
			Long str_descr_type_level_7 = streetEnt.getDescr_type_id_level_7();
			if (str_descr_type_level_7 != null) {
				StreetKind streetType = oracleManager.find(StreetKind.class,
						str_descr_type_level_7);
				if (streetType != null) {
					streetName.append(streetType.getStreet_kind_name()).append(
							" ");
				}
			}

			Long str_descr_level_8 = streetEnt.getDescr_id_level_8();
			if (str_descr_level_8 != null) {
				StreetNames streetDescr = oracleManager.find(StreetNames.class,
						str_descr_level_8);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_name_descr())
							.append(" ");
				}
			}
			Long str_descr_type_level_8 = streetEnt.getDescr_type_id_level_8();
			if (str_descr_type_level_8 != null) {
				StreetKind streetType = oracleManager.find(StreetKind.class,
						str_descr_type_level_8);
				if (streetType != null) {
					streetName.append(streetType.getStreet_kind_name()).append(
							" ");
				}
			}

			Long str_descr_level_9 = streetEnt.getDescr_id_level_9();
			if (str_descr_level_9 != null) {
				StreetNames streetDescr = oracleManager.find(StreetNames.class,
						str_descr_level_9);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_name_descr())
							.append(" ");
				}
			}
			Long str_descr_type_level_9 = streetEnt.getDescr_type_id_level_9();
			if (str_descr_type_level_9 != null) {
				StreetKind streetType = oracleManager.find(StreetKind.class,
						str_descr_type_level_9);
				if (streetType != null) {
					streetName.append(streetType.getStreet_kind_name()).append(
							" ");
				}
			}

			Long str_descr_level_10 = streetEnt.getDescr_id_level_10();
			if (str_descr_level_10 != null) {
				StreetNames streetDescr = oracleManager.find(StreetNames.class,
						str_descr_level_10);
				if (streetDescr != null) {
					streetName.append(streetDescr.getStreet_name_descr())
							.append(" ");
				}
			}
			Long str_descr_type_level_10 = streetEnt
					.getDescr_type_id_level_10();
			if (str_descr_type_level_10 != null) {
				StreetKind streetType = oracleManager.find(StreetKind.class,
						str_descr_type_level_10);
				if (streetType != null) {
					streetName.append(streetType.getStreet_kind_name()).append(
							" ");
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

	private Street getStreetEntById(Long streetId, EntityManager oracleManager)
			throws Exception {
		try {
			Street ret = oracleManager.find(Street.class, streetId);
			if (ret != null) {
				Long cityId = ret.getTown_id();
				if (cityId != null) {
					Towns city = oracleManager.find(Towns.class, cityId);
					if (city != null) {
						ret.setTown_name(city.getTown_name());
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
	public Street updateStreetEnt(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetEnt.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_id = new Long(record.get("street_id").toString());
			Long town_id = new Long(record.get("town_id").toString());
			String street_location_geo = record.get("street_location") == null ? null
					: record.get("street_location").toString();
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update Street.");

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

			Street streetEntForGen = oracleManager
					.find(Street.class, street_id);

			if (bSaveStreetHistOrNotItem != null
					&& bSaveStreetHistOrNotItem.booleanValue()) {
				StreetsOldNames streetsOldEnt = new StreetsOldNames();
				streetsOldEnt.setTown_id(streetEntForGen.getTown_id());
				streetsOldEnt.setStreet_id(streetEntForGen.getStreet_id());
				streetsOldEnt.setStreet_old_name_descr(streetEntForGen
						.getStreet_name());
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
			streetEntForGen.setStreet_name(streetName);
			streetEntForGen.setTown_id(town_id);
			streetEntForGen.setStreet_location(street_location_geo);
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
					for (String town_district_id : keySet) {
						StreetToTownDistricts streetDistrict = new StreetToTownDistricts();
						streetDistrict.setTown_id(town_id);
						streetDistrict.setTown_district_id(Long
								.parseLong(town_district_id));
						streetDistrict.setStreet_id(street_id);
						oracleManager.persist(streetDistrict);
					}
				}
			}

			oracleManager.flush();

			Street streetEnt = getStreetEntById(street_id, oracleManager);
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
	 * Delete Street
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public Street deleteStreet(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.deleteStreet.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_id = new Long(dsRequest.getOldValues().get("street_id")
					.toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing Street.");

			Street street = oracleManager.find(Street.class, street_id);
			street.setLoggedUserName(loggedUserName);

			oracleManager
					.createNativeQuery(Q_DELETE_STREET_DISCTRICTS_BY_STREET_ID)
					.setParameter(1, street.getStreet_id()).executeUpdate();

			oracleManager.remove(street);
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
	public ArrayList<Street> fetchStreetEnts(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.fetchStreetEnts.";
			oracleManager = EMF.getEntityManager();
			if (streetEnts == null || streetEnts.isEmpty()) {
				log += " fetching Streets From DB ...";
				streetEnts = new TreeMap<Long, Street>();
				ArrayList<Street> list = (ArrayList<Street>) oracleManager
						.createNamedQuery("Street.getAllActive")
						.getResultList();
				if (list != null && !list.isEmpty()) {

					List strDistrList = oracleManager.createNativeQuery(
							Q_GET_STREET_DISTRICTS_ALL).getResultList();
					TreeMap<String, TreeMap<String, String>> mapStrDistricts = new TreeMap<String, TreeMap<String, String>>();
					if (strDistrList != null && !strDistrList.isEmpty()) {
						for (Object object : strDistrList) {
							Object row[] = (Object[]) object;
							Long street_id = new Long(row[1].toString());
							Long town_district_id = new Long(row[2].toString());
							String town_district_name = row[3].toString();
							TreeMap<String, String> mapItem = mapStrDistricts
									.get(street_id.toString());
							if (mapItem == null) {
								mapItem = new TreeMap<String, String>();
							}
							mapItem.put(town_district_id.toString(),
									town_district_name);
							mapStrDistricts.put(street_id.toString(), mapItem);
						}
					}

					streetsByCityId = new TreeMap<Long, ArrayList<Street>>();

					for (Street streetEnt : list) {
						TreeMap<String, String> mapItem = mapStrDistricts
								.get(streetEnt.getStreet_id().toString());
						if (mapItem != null) {
							streetEnt.setMapStreDistricts(mapItem);
						}
						Long town_id = streetEnt.getTown_id();
						if (town_id != null) {
							ArrayList<Street> listByCity = streetsByCityId
									.get(town_id);
							if (listByCity == null) {
								listByCity = new ArrayList<Street>();
							}
							listByCity.add(streetEnt);
							streetsByCityId.put(town_id, listByCity);
						}
						streetEnts.put(streetEnt.getStreet_id(), streetEnt);
					}
				}
			}
			log += ". Fetching Streets Finished SuccessFully. ";
			logger.info(log);
			ArrayList<Street> ret = new ArrayList<Street>();
			if (streetEnts != null && !streetEnts.isEmpty()) {

				Object oStreet_id = dsRequest.getFieldValue("street_id");
				Long street_id = -100L;
				if (oStreet_id != null) {
					street_id = new Long(oStreet_id.toString());
				}
				if (!street_id.equals(-100L)) {
					ret.add(streetEnts.get(street_id));
				} else {
					Object oTown_id = dsRequest.getFieldValue("town_id");
					if (oTown_id != null) {
						Long town_id = new Long(oTown_id.toString());
						ret.addAll(streetsByCityId.get(town_id));
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
	 * Adding New StreetNames
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public StreetNames addStreetName(StreetNames streetName) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addStreetName.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = streetName.getLoggedUserName();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add streetName.");

			oracleManager.persist(streetName);

			StreetNames retItem = oracleManager.find(StreetNames.class,
					streetName.getStreet_name_id());

			streetDescrs.put(retItem.getStreet_name_id(), retItem);
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert streetName Into Database : ", e);
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
	 * updateStreetName
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetNames updateStreetName(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetName.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_name_id = new Long(record.get("street_name_id")
					.toString());
			String street_name_descr = record.get("street_name_descr") == null ? null
					: record.get("street_name_descr").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update StreetNames.");

			StreetNames streetNames = oracleManager.find(StreetNames.class,
					street_name_id);

			streetNames.setStreet_name_descr(street_name_descr);
			streetNames.setLoggedUserName(loggedUserName);

			oracleManager.merge(streetNames);
			oracleManager.flush();

			streetNames = oracleManager.find(StreetNames.class, street_name_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return streetNames;

		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Street Name Into Database : ", e);
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
	 * Delete Street Names
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public StreetNames deleteStreetName(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.deleteStreetName.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_name_id = new Long(dsRequest.getOldValues()
					.get("street_name_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing StreetNames.");

			StreetNames streetName = oracleManager.find(StreetNames.class,
					street_name_id);
			streetName.setLoggedUserName(loggedUserName);

			oracleManager.remove(streetName);
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
			logger.error("Error While Delete Street Names From Database : ", e);
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
	 * Delete Street Old Name
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetsOldNames deleteStreetsOldNames(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetsOldEnt.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long old_id = new Long(record.get("old_id").toString());
			// Long deleted = new Long(record.get("deleted").toString());
			// String loggedUserName = record.get("loggedUserName").toString();

			StreetsOldNames find = oracleManager.find(StreetsOldNames.class,
					old_id);

			oracleManager.remove(find);
			oracleManager.flush();

			find = oracleManager.find(StreetsOldNames.class, old_id);
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
	public ArrayList<StreetNames> fetchStreetDescrs(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.fetchStreetDescrs.";
			oracleManager = EMF.getEntityManager();
			if (streetDescrs == null || streetDescrs.isEmpty()) {
				log += " fetching Street Descriptions From DB ...";
				streetDescrs = new TreeMap<Long, StreetNames>();
				ArrayList<StreetNames> list = (ArrayList<StreetNames>) oracleManager
						.createNamedQuery("StreetDescr.getAllActive")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (StreetNames streetDescr : list) {
						streetDescrs.put(streetDescr.getStreet_name_id(),
								streetDescr);
					}
				}
			}
			log += ". Fetching StreetDescrs Finished SuccessFully. ";
			logger.info(log);
			ArrayList<StreetNames> ret = new ArrayList<StreetNames>();
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
	 * Adding New Street Kind
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public StreetKind addStreetKind(StreetKind streetKind) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addStreetKind.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = streetKind.getLoggedUserName();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add StreetKind.");

			oracleManager.persist(streetKind);

			StreetKind retItem = oracleManager.find(StreetKind.class,
					streetKind.getStreet_kind_id());
			streetKinds.put(retItem.getStreet_kind_id(), retItem);
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return retItem;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert StreetKind Into Database : ", e);
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
	 * Updating Street Kind
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public StreetKind updateStreetKind(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateStreetKind.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_kind_id = new Long(record.get("street_kind_id")
					.toString());
			String street_kind_name = record.get("street_kind_name") == null ? null
					: record.get("street_kind_name").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update StreetKind.");

			StreetKind streetKind = oracleManager.find(StreetKind.class,
					street_kind_id);

			streetKind.setStreet_kind_name(street_kind_name);
			streetKind.setLoggedUserName(loggedUserName);

			oracleManager.merge(streetKind);
			oracleManager.flush();

			streetKind = oracleManager.find(StreetKind.class, street_kind_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return streetKind;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update StreetKind Into Database : ", e);
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
	 * Delete Street Kind
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public StreetKind deleteStreetKind(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.deleteStreetKind.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long street_kind_id = new Long(dsRequest.getOldValues()
					.get("street_kind_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing StreetKind.");

			StreetKind streetKind = oracleManager.find(StreetKind.class,
					street_kind_id);
			streetKind.setLoggedUserName(loggedUserName);

			oracleManager.remove(streetKind);
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
			logger.error("Error While Delete StreetKind From Database : ", e);
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
	public ArrayList<StreetKind> fetchStreetTypes(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {
			String log = "Method:CommonDMI.fetchStreetTypeStatus.";
			oracleManager = EMF.getEntityManager();
			if (streetKinds == null || streetKinds.isEmpty()) {
				log += " fetching StreetStatuses From DB ...";
				streetKinds = new TreeMap<Long, StreetKind>();
				ArrayList<StreetKind> list = (ArrayList<StreetKind>) oracleManager
						.createNamedQuery("StreetType.getAllActive")
						.getResultList();
				if (list != null && !list.isEmpty()) {
					for (StreetKind streetType : list) {
						streetKinds.put(streetType.getStreet_kind_id(),
								streetType);
					}
				}
			}
			log += ". Fetching StreetTypes Finished SuccessFully. ";
			logger.info(log);
			ArrayList<StreetKind> ret = new ArrayList<StreetKind>();
			if (streetKinds != null && !streetKinds.isEmpty()) {
				ret.addAll(streetKinds.values());
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
	 * Adding New Town District
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public TownDistrict addTownDistrict(TownDistrict townDistrict)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addTownDistrict.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = townDistrict.getLoggedUserName();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Detele TownDistrict.");

			oracleManager.persist(townDistrict);

			oracleManager.flush();

			townDistrict = oracleManager.find(TownDistrict.class,
					townDistrict.getTown_district_id());

			townDistricts.put(townDistrict.getTown_district_id(), townDistrict);
			TreeMap<Long, TownDistrict> byTownId = townDistrictByTownId
					.get(townDistrict.getTown_id());
			if (byTownId == null) {
				byTownId = new TreeMap<Long, TownDistrict>();
			}
			byTownId.put(townDistrict.getTown_district_id(), townDistrict);
			townDistrictByTownId.put(townDistrict.getTown_id(), byTownId);

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return townDistrict;
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
	 * Updating Town District
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public TownDistrict updateTownDistrict(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.TownDistrict.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long town_district_id = new Long(record.get("town_district_id")
					.toString());
			Long town_id = new Long(record.get("town_id").toString());
			String town_district_name = record.get("town_district_name")
					.toString();

			String loggedUserName = record.get("loggedUserName").toString();

			TownDistrict townDistrict = oracleManager.find(TownDistrict.class,
					town_district_id);
			townDistrict.setTown_id(town_id);
			townDistrict.setTown_district_name(town_district_name);

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update TownDistrict.");

			oracleManager.merge(townDistrict);
			oracleManager.flush();

			townDistrict = oracleManager.find(TownDistrict.class,
					town_district_id);

			townDistricts.remove(townDistrict.getTown_district_id());
			townDistricts.put(townDistrict.getTown_district_id(), townDistrict);
			TreeMap<Long, TownDistrict> byTownId = townDistrictByTownId
					.get(townDistrict.getTown_id());
			if (byTownId == null) {
				byTownId = new TreeMap<Long, TownDistrict>();
			}
			byTownId.remove(townDistrict.getTown_district_id());
			byTownId.put(townDistrict.getTown_district_id(), townDistrict);
			townDistrictByTownId.remove(townDistrict.getTown_id());
			townDistrictByTownId.put(townDistrict.getTown_id(), byTownId);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return townDistrict;
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
	public TownDistrict deleteTownDistrict(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.deleteTownDistrict.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long town_district_id = new Long(dsRequest.getOldValues()
					.get("town_district_id").toString());

			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();

			TownDistrict townDistrict = oracleManager.find(TownDistrict.class,
					town_district_id);

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Detele TownDistrict.");

			oracleManager.remove(townDistrict);
			oracleManager.flush();

			EMF.commitTransaction(transaction);
			log += ". Status Delete Finished SuccessFully. ";
			logger.info(log);
			return null;
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
	public ArrayList<TownDistrict> fetchCityRegions(DSRequest dsRequest)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			Object oTown_id = dsRequest.getFieldValue("town_id");
			Long pTown_id = -100L;
			if (oTown_id != null) {
				pTown_id = new Long(oTown_id.toString());
			}

			logger.info("getting city regions names ...");
			if (townDistricts == null || townDistricts.isEmpty()) {

				logger.info("getting city regions from DB. ");
				oracleManager = EMF.getEntityManager();

				townDistricts = new TreeMap<Long, TownDistrict>();
				townDistrictByTownId = new TreeMap<Long, TreeMap<Long, TownDistrict>>();

				ArrayList<TownDistrict> cityRegList = (ArrayList<TownDistrict>) oracleManager
						.createNamedQuery("TownDistrict.getAllActive")
						.getResultList();

				if (cityRegList != null && !cityRegList.isEmpty()) {
					for (TownDistrict cityRegion : cityRegList) {
						Long town_id = cityRegion.getTown_id();
						if (town_id != null) {
							TreeMap<Long, TownDistrict> listByCity = townDistrictByTownId
									.get(town_id);
							if (listByCity == null) {
								listByCity = new TreeMap<Long, TownDistrict>();
							}
							listByCity.put(cityRegion.getTown_district_id(),
									cityRegion);
							townDistrictByTownId.put(town_id, listByCity);
						}
						townDistricts.put(cityRegion.getTown_district_id(),
								cityRegion);
					}
				}
			}
			if (pTown_id.equals(-100L)) {
				ArrayList<TownDistrict> ret = new ArrayList<TownDistrict>();
				ret.addAll(townDistricts.values());
				return ret;
			} else {
				TreeMap<Long, TownDistrict> map = townDistrictByTownId
						.get(pTown_id);
				ArrayList<TownDistrict> ret = new ArrayList<TownDistrict>();
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

	public Towns townAdd(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add Town.");
			Long country_id = new Long(dsRequest.getFieldValue("country_id")
					.toString());
			Country country = null;
			if (country_id != null) {
				if (countries.isEmpty()) {
					fetchAllCountries(dsRequest);
				}
				country = getCountry(country_id);
			}
			Long town_type_id = new Long(dsRequest
					.getFieldValue("town_type_id").toString());

			Towns town = new Towns();
			town.setTown_code(dsRequest.getFieldValue("town_code") == null ? null
					: dsRequest.getFieldValue("town_code").toString());
			town.setTown_name(dsRequest.getFieldValue("town_name") == null ? null
					: dsRequest.getFieldValue("town_name").toString());
			town.setTown_new_code(dsRequest.getFieldValue("town_new_code") == null ? null
					: dsRequest.getFieldValue("town_new_code").toString());
			town.setCapital_town(dsRequest.getFieldValue("capital_town") == null ? null
					: new Long(dsRequest.getFieldValue("capital_town")
							.toString()));
			town.setNormal_gmt(dsRequest.getFieldValue("normal_gmt") == null ? null
					: new Long(dsRequest.getFieldValue("normal_gmt").toString()));
			town.setWinter_gmt(dsRequest.getFieldValue("winter_gmt") == null ? null
					: new Long(dsRequest.getFieldValue("winter_gmt").toString()));

			Description description = oracleManager.find(Description.class,
					town_type_id);
			town.setTown_type_id(town_type_id);
			town.setTown_type_name(description.getDescription());

			town.setCountry_id(country_id);
			if (country != null) {
				town.setCountry_name(country.getCountry_name());
			}
			town.setCountry_district_id(-1L);

			town.setCapital_town_name(town.getCapital_town() != null
					&& town.getCapital_town().equals(1L) ? "დედაქალაქი"
					: "ჩვეულებრივი");

			oracleManager.persist(town);

			EMF.commitTransaction(transaction);
			towns.put(town.getTown_id(), town);
			return town;
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
	public Towns townUpdate(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTown.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long town_id = new Long(record.get("town_id").toString());
			String town_name = record.get("town_name") == null ? null : record
					.get("town_name").toString();
			String town_code = record.get("town_code") == null ? null : record
					.get("town_code").toString();
			String town_new_code = record.get("town_new_code") == null ? null
					: record.get("town_new_code").toString();
			Long capital_town = new Long(record.get("capital_town").toString()) == null ? null
					: new Long(record.get("capital_town").toString());
			Long normal_gmt = new Long(record.get("normal_gmt").toString()) == null ? null
					: new Long(record.get("normal_gmt").toString());
			Long winter_gmt = new Long(record.get("winter_gmt").toString()) == null ? null
					: new Long(record.get("winter_gmt").toString());
			Long town_type_id = new Long(record.get("town_type_id").toString()) == null ? null
					: new Long(record.get("town_type_id").toString());
			Long country_id = new Long(record.get("country_id").toString()) == null ? null
					: new Long(record.get("country_id").toString());

			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update Town.");

			Towns town = oracleManager.find(Towns.class, town_id);

			town.setTown_name(town_name);
			town.setTown_code(town_code);
			town.setTown_new_code(town_new_code);
			town.setCapital_town(capital_town);
			town.setNormal_gmt(normal_gmt);
			town.setWinter_gmt(winter_gmt);
			town.setTown_type_id(town_type_id);
			town.setCountry_id(country_id);

			town.setCapital_town_name(town.getCapital_town() != null
					&& town.getCapital_town().equals(1L) ? "დედაქალაქი"
					: "ჩვეულებრივი");

			Country country = oracleManager.find(Country.class, country_id);

			town.setCountry_name(country.getCountry_name());

			Description description = oracleManager.find(Description.class,
					town_type_id);

			town.setTown_type_name(description.getDescription());

			town.setLoggedUserName(loggedUserName);

			oracleManager.merge(town);
			oracleManager.flush();

			town = oracleManager.find(Towns.class, town_id);

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return town;
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

	public Towns townDelete(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.townDelete.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long town_id = new Long(dsRequest.getOldValues().get("town_id")
					.toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing Town.");

			Towns towns = oracleManager.find(Towns.class, town_id);
			towns.setLoggedUserName(loggedUserName);

			oracleManager.remove(towns);
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
	public ArrayList<Towns> fetchAllCitiesFromDB(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {
			logger.info("getting Cities From Database ... ");

			Object town_name = dsRequest.getFieldValue("town_name");
			Object country_id = dsRequest.getFieldValue("country_id");
			Object town_type_id = dsRequest.getFieldValue("town_type_id");
			Object capital_town = dsRequest.getFieldValue("capital_town");
			Object town_code = dsRequest.getFieldValue("town_code");
			Object town_new_code = dsRequest.getFieldValue("town_new_code");
			Object normal_gmt = dsRequest.getFieldValue("normal_gmt");
			Object winter_gmt = dsRequest.getFieldValue("winter_gmt");

			String query = "select e from Towns e where 1 = 1 ";
			if (town_name != null
					&& !town_name.toString().trim().equalsIgnoreCase("")) {
				query += " and e.town_name like '" + town_name.toString()
						+ "%'";
			}
			if (town_code != null
					&& !town_code.toString().trim().equalsIgnoreCase("")) {
				query += " and e.town_code like '" + town_code.toString()
						+ "%'";
			}
			if (town_new_code != null
					&& !town_new_code.toString().trim().equalsIgnoreCase("")) {
				query += " and e.town_new_code like '"
						+ town_new_code.toString() + "%'";
			}
			if (country_id != null) {
				query += " and e.country_id = "
						+ new Long(country_id.toString());
			}
			if (town_type_id != null) {
				query += " and e.town_type_id = "
						+ new Long(town_type_id.toString());
			}
			if (capital_town != null) {
				query += " and e.capital_town = "
						+ new Long(capital_town.toString());
			}
			if (normal_gmt != null) {
				query += " and e.normal_gmt = " + normal_gmt.toString();
			}
			if (winter_gmt != null) {
				query += " and e.winter_gmt = " + winter_gmt.toString();
			}
			query += " order by e.town_id ";

			oracleManager = EMF.getEntityManager();

			// String town_type_name = "";

			ArrayList<Towns> result = (ArrayList<Towns>) oracleManager
					.createQuery(query).getResultList();
			if (result != null && !result.isEmpty()) {
				for (Towns town : result) {
					Long cTypeId = town.getTown_type_id();
					if (cTypeId != null) {
						com.smartgwt.client.data.DataSource descriptionsDS = com.smartgwt.client.data.DataSource
								.get("DescriptionsDS");
						// DSRequest descriptionsDSRequest = new DSRequest();
						dsRequest.setOperationId("searchDescriptionsOrderById");

						// final RecordList list;
						descriptionsDS.fetchData(new Criteria(),
								new DSCallback() {
									@Override
									public void execute(
											DSResponse response,
											Object rawData,
											com.smartgwt.client.data.DSRequest request) {
										response.getData();

									}
								});

						// CityType cityType = getCityType(cTypeId);
						// if (cityType != null) {
						// city.setCityType(cityType.getCity_type_geo());
						// }
					}
					Long countryId = town.getCountry_id();
					if (countryId != null) {
						if (countries.isEmpty()) {
							fetchAllCountries(dsRequest);
						}
						Country country = getCountry(countryId);
						if (country != null) {
							town.setCountry_name(country.getCountry_name());
						}
					}
					town.setCapital_town_name(town.getCapital_town() != null
							&& town.getCapital_town().equals(1L) ? "დედაქალაქი"
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

	public ArrayList<Towns> fetchAllCities(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		try {
			logger.info("getting Cities ... ");
			if (towns == null || towns.isEmpty()) {
				towns = new TreeMap<Long, Towns>();
				ArrayList<Towns> tmpCities = fetchAllCitiesFromDB(dsRequest);
				if (tmpCities != null && !tmpCities.isEmpty()) {
					for (Towns town : tmpCities) {
						towns.put(town.getTown_id(), town);
					}
				}
			}
			ArrayList<Towns> ret = new ArrayList<Towns>();
			ret.addAll(towns.values());
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

	// @SuppressWarnings("unchecked")
	// public ArrayList<CityType> fetchCityTypes(DSRequest dsRequest)
	// throws Exception {
	// EntityManager oracleManager = null;
	// try {
	// logger.info("getting CityTypes ...");
	// if (cityTypes == null || cityTypes.isEmpty()) {
	// logger.info("getting CityTypes from DB. ");
	// cityTypes = new TreeMap<Long, CityType>();
	// oracleManager = EMF.getEntityManager();
	// ArrayList<CityType> result = (ArrayList<CityType>) oracleManager
	// .createNamedQuery("CityType.getAllCityTypes")
	// .getResultList();
	// if (result != null && !result.isEmpty()) {
	// for (CityType cityType : result) {
	// Long id = cityType.getCity_type_id();
	// cityTypes.put(id, cityType);
	// }
	// }
	// }
	// ArrayList<CityType> ret = new ArrayList<CityType>();
	// ret.addAll(cityTypes.values());
	// return ret;
	// } catch (Exception e) {
	// if (e instanceof CallCenterException) {
	// throw (CallCenterException) e;
	// }
	// logger.error("Error While Fetching CityTypes From Database : ", e);
	// throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
	// + e.toString());
	// } finally {
	// if (oracleManager != null) {
	// EMF.returnEntityManager(oracleManager);
	// }
	// }
	// }

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
