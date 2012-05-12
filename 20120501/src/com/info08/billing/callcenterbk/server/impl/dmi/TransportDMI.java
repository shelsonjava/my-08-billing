package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.entity.City;
import com.info08.billing.callcenterbk.shared.entity.StreetEnt;
import com.info08.billing.callcenterbk.shared.entity.transport.BusRoute;
import com.info08.billing.callcenterbk.shared.entity.transport.BusRouteStreet;
import com.info08.billing.callcenterbk.shared.entity.transport.Transport;
import com.info08.billing.callcenterbk.shared.entity.transport.TransportCompany;
import com.info08.billing.callcenterbk.shared.entity.transport.TransportDetail;
import com.info08.billing.callcenterbk.shared.entity.transport.TransportPlace;
import com.info08.billing.callcenterbk.shared.entity.transport.TransportType;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;

public class TransportDMI implements QueryConstants {

	Logger logger = Logger.getLogger(TransportDMI.class.getName());

	/**
	 * Adding New TransportType
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public TransportType addTransportType(TransportType transportType)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addTransportType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = transportType.getLoggedUserName();
			transportType.setRec_date(recDate);
			transportType.setRec_user(loggedUserName);

			oracleManager.persist(transportType);
			oracleManager.flush();

			transportType = oracleManager.find(TransportType.class,
					transportType.getTransport_type_id());

			transportType.setLoggedUserName(loggedUserName);
			Long interCity = transportType.getIntercity();
			switch (interCity.intValue()) {
			case 0:
				transportType.setIntercityDescr("საქალაქო");
				break;
			case 1:
				transportType.setIntercityDescr("საქალაქთაშორისო");
				break;
			default:
				transportType.setIntercityDescr("უცნობი");
				break;
			}
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return transportType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert TransportType Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating TransportType
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public TransportType updateTransportType(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransportType.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long transport_type_id = new Long(record.get("transport_type_id")
					.toString());
			Long intercity = new Long(record.get("intercity").toString());
			String transport_type_name_geo = record
					.get("transport_type_name_geo") == null ? null : record
					.get("transport_type_name_geo").toString();
			String transport_type_name_eng = record
					.get("transport_type_name_eng") == null ? null : record
					.get("transport_type_name_eng").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			TransportType transportType = oracleManager.find(
					TransportType.class, transport_type_id);

			transportType.setIntercity(intercity);
			transportType.setTransport_type_name_geo(transport_type_name_geo);
			transportType.setTransport_type_name_eng(transport_type_name_eng);
			transportType.setUpd_user(loggedUserName);

			oracleManager.merge(transportType);
			oracleManager.flush();

			transportType = oracleManager.find(TransportType.class,
					transport_type_id);

			transportType.setLoggedUserName(loggedUserName);
			Long interCity = transportType.getIntercity();
			switch (interCity.intValue()) {
			case 0:
				transportType.setIntercityDescr("საქალაქო");
				break;
			case 1:
				transportType.setIntercityDescr("საქალაქთაშორისო");
				break;
			default:
				transportType.setIntercityDescr("უცნობი");
				break;
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return transportType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update TransportType Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating TransportType Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public TransportType updateTransportTypeStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransportTypeStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long transport_type_id = new Long(record.get("transport_type_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			TransportType transportType = oracleManager.find(
					TransportType.class, transport_type_id);

			transportType.setDeleted(deleted);
			transportType.setUpd_user(loggedUserName);

			oracleManager.merge(transportType);
			oracleManager.flush();

			transportType = oracleManager.find(TransportType.class,
					transport_type_id);

			transportType.setLoggedUserName(loggedUserName);
			Long interCity = transportType.getIntercity();
			switch (interCity.intValue()) {
			case 0:
				transportType.setIntercityDescr("საქალაქო");
				break;
			case 1:
				transportType.setIntercityDescr("საქალაქთაშორისო");
				break;
			default:
				transportType.setIntercityDescr("უცნობი");
				break;
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return transportType;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for TransportType Into Database : ",
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
	 * Adding New TransportCompany
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public TransportCompany addTransportCompany(
			TransportCompany transportCompany) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addTransportCompany.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = transportCompany.getLoggedUserName();
			transportCompany.setRec_date(recDate);
			transportCompany.setRec_user(loggedUserName);

			oracleManager.persist(transportCompany);
			oracleManager.flush();

			transportCompany = oracleManager.find(TransportCompany.class,
					transportCompany.getTransport_company_id());

			transportCompany.setLoggedUserName(loggedUserName);
			Long transpTypeId = transportCompany.getTransport_type_id();
			TransportType transportType = oracleManager.find(
					TransportType.class, transpTypeId);
			if (transportType != null) {
				transportCompany.setTransport_type_name_geo(transportType
						.getTransport_type_name_geo());
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return transportCompany;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Insert TransportCompany Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating TransportCompany
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public TransportCompany updateTransportCompany(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransportCompany.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long transport_company_id = new Long(record.get(
					"transport_company_id").toString());
			Long transport_type_id = new Long(record.get("transport_type_id")
					.toString());
			String transport_company_geo = record.get("transport_company_geo") == null ? null
					: record.get("transport_company_geo").toString();
			String transport_company_eng = record.get("transport_company_eng") == null ? null
					: record.get("transport_company_eng").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			TransportCompany transportCompany = oracleManager.find(
					TransportCompany.class, transport_company_id);

			transportCompany.setTransport_type_id(transport_type_id);
			transportCompany.setTransport_company_geo(transport_company_geo);
			transportCompany.setTransport_company_eng(transport_company_eng);
			transportCompany.setUpd_user(loggedUserName);

			oracleManager.merge(transportCompany);
			oracleManager.flush();

			transportCompany = oracleManager.find(TransportCompany.class,
					transport_company_id);

			transportCompany.setLoggedUserName(loggedUserName);
			Long transpTypeId = transportCompany.getTransport_type_id();
			TransportType transportType = oracleManager.find(
					TransportType.class, transpTypeId);
			if (transportType != null) {
				transportCompany.setTransport_type_name_geo(transportType
						.getTransport_type_name_geo());
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return transportCompany;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update TransportCompany Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating TransportCompany Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public TransportCompany updateTransportCompanyStatus(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransportCompanyStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long transport_company_id = new Long(record.get(
					"transport_company_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			TransportCompany transportCompany = oracleManager.find(
					TransportCompany.class, transport_company_id);

			transportCompany.setDeleted(deleted);
			transportCompany.setUpd_user(loggedUserName);

			oracleManager.merge(transportCompany);
			oracleManager.flush();

			transportCompany = oracleManager.find(TransportCompany.class,
					transport_company_id);

			transportCompany.setLoggedUserName(loggedUserName);
			Long transpTypeId = transportCompany.getTransport_type_id();
			TransportType transportType = oracleManager.find(
					TransportType.class, transpTypeId);
			if (transportType != null) {
				transportCompany.setTransport_type_name_geo(transportType
						.getTransport_type_name_geo());
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return transportCompany;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for TransportCompany Into Database : ",
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
	 * Adding New TransportPlace
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public TransportPlace addTransportPlace(TransportPlace transportPlace)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addTransportPlace.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = transportPlace.getLoggedUserName();
			transportPlace.setRec_date(recDate);
			transportPlace.setRec_user(loggedUserName);

			Long city_id = transportPlace.getCity_id();
			City city = oracleManager.find(City.class, city_id);
			String descr = "";
			if (city != null && city.getCity_name_geo() != null) {
				descr += city.getCity_name_geo() + " ";
			}
			descr += transportPlace.getTransport_place_geo();

			Long transpTypeId = transportPlace.getTransport_type_id();
			TransportType transportType = oracleManager.find(
					TransportType.class, transpTypeId);
			if (transportType != null
					&& transportType.getTransport_type_name_geo() != null) {
				descr += " ( " + transportType.getTransport_type_name_geo()
						+ " ) ";
			}

			transportPlace.setTransport_place_geo_descr(descr);

			oracleManager.persist(transportPlace);
			oracleManager.flush();

			transportPlace = oracleManager.find(TransportPlace.class,
					transportPlace.getTransport_place_id());

			transportPlace.setLoggedUserName(loggedUserName);

			if (transportType != null) {
				transportPlace.setTransport_type_name_geo(transportType
						.getTransport_type_name_geo());
			}

			if (city != null) {
				transportPlace.setCity_name_geo(city.getCity_name_geo());
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return transportPlace;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert TransportPlace Into Database : ",
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
	 * Updating TransportPlace
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public TransportPlace updateTransportPlace(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransportPlace.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long transport_place_id = new Long(record.get("transport_place_id")
					.toString());
			Long transport_type_id = new Long(record.get("transport_type_id")
					.toString());
			Long city_id = new Long(record.get("city_id").toString());

			String transport_place_geo = record.get("transport_place_geo") == null ? null
					: record.get("transport_place_geo").toString();
			String transport_place_eng = record.get("transport_place_eng") == null ? null
					: record.get("transport_place_eng").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			TransportPlace transportPlace = oracleManager.find(
					TransportPlace.class, transport_place_id);

			transportPlace.setTransport_type_id(transport_type_id);
			transportPlace.setCity_id(city_id);
			transportPlace.setTransport_place_eng(transport_place_eng);
			transportPlace.setTransport_place_geo(transport_place_geo);
			transportPlace.setUpd_user(loggedUserName);

			City city = oracleManager.find(City.class, city_id);
			String descr = "";
			if (city != null && city.getCity_name_geo() != null) {
				descr += city.getCity_name_geo() + " ";
			}
			descr += transportPlace.getTransport_place_geo();

			TransportType transportType = oracleManager.find(
					TransportType.class, transport_type_id);
			if (transportType != null
					&& transportType.getTransport_type_name_geo() != null) {
				descr += " ( " + transportType.getTransport_type_name_geo()
						+ " ) ";
			}

			transportPlace.setTransport_place_geo_descr(descr);

			oracleManager.merge(transportPlace);
			oracleManager.flush();

			transportPlace = oracleManager.find(TransportPlace.class,
					transportPlace.getTransport_place_id());

			transportPlace.setLoggedUserName(loggedUserName);

			if (transportType != null) {
				transportPlace.setTransport_type_name_geo(transportType
						.getTransport_type_name_geo());
			}

			if (city != null) {
				transportPlace.setCity_name_geo(city.getCity_name_geo());
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return transportPlace;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update TransportPlace Into Database : ",
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
	 * Updating TransportPlace Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public TransportPlace updateTransportPlaceStatus(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransportPlaceStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long transport_place_id = new Long(record.get("transport_place_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			TransportPlace transportPlace = oracleManager.find(
					TransportPlace.class, transport_place_id);

			transportPlace.setDeleted(deleted);
			transportPlace.setUpd_user(loggedUserName);

			oracleManager.merge(transportPlace);
			oracleManager.flush();

			transportPlace = oracleManager.find(TransportPlace.class,
					transport_place_id);

			transportPlace.setLoggedUserName(loggedUserName);
			TransportType transportType = oracleManager.find(
					TransportType.class, transportPlace.getTransport_type_id());
			if (transportType != null) {
				transportPlace.setTransport_type_name_geo(transportType
						.getTransport_type_name_geo());
			}
			City city = oracleManager.find(City.class,
					transportPlace.getCity_id());
			if (city != null) {
				transportPlace.setCity_name_geo(city.getCity_name_geo());
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return transportPlace;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for TransportPlace Into Database : ",
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
	 * Adding New BusRoute
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public BusRoute addBusRoute(BusRoute busRoute) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addBusRoute.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = busRoute.getLoggedUserName();

			oracleManager.persist(busRoute);
			oracleManager.flush();

			busRoute = oracleManager.find(BusRoute.class,
					busRoute.getId());

			busRoute.setLoggedUserName(loggedUserName);
			Long transpTypeId = busRoute.getService_id();
			TransportType transportType = oracleManager.find(
					TransportType.class, transpTypeId);
			if (transportType != null) {
				busRoute.setService_descr(transportType
						.getTransport_type_name_geo());
			}
			Long round_id = busRoute.getCycle_id();
			switch (round_id.intValue()) {
			case 1:
				busRoute.setСycle_descr("ჩვეულებრივი");
				break;
			case 2:
				busRoute.setСycle_descr("წრიული");
				break;
			default:
				break;
			}
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return busRoute;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert BusRoute Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating BusRoute
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public BusRoute updateBusRoute(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateBusRoute.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long route_id = new Long(record.get("route_id").toString());
			Long round_id = new Long(record.get("round_id").toString());
			Long service_id = new Long(record.get("service_id").toString());

			String route_nm = record.get("route_nm") == null ? null : record
					.get("route_nm").toString();
			String route_old_nm = record.get("route_old_nm") == null ? null
					: record.get("route_old_nm").toString();
			String loggedUserName = record.get("loggedUserName").toString();

			BusRoute busRoute = oracleManager.find(BusRoute.class, route_id);

			busRoute.setCycle_id(round_id);
			busRoute.setDir_num(route_nm);
			busRoute.setDir_old_num(route_old_nm);
			busRoute.setService_id(service_id);

			oracleManager.merge(busRoute);
			oracleManager.flush();

			busRoute = oracleManager.find(BusRoute.class, route_id);

			busRoute.setLoggedUserName(loggedUserName);
			Long transpTypeId = busRoute.getService_id();
			TransportType transportType = oracleManager.find(
					TransportType.class, transpTypeId);
			if (transportType != null) {
				busRoute.setService_descr(transportType
						.getTransport_type_name_geo());
			}
			switch (round_id.intValue()) {
			case 1:
				busRoute.setСycle_descr("ჩვეულებრივი");
				break;
			case 2:
				busRoute.setСycle_descr("წრიული");
				break;
			default:
				break;
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return busRoute;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update BusRoute Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating BusRoute Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public BusRoute updateBusRouteStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateBusRouteStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long route_id = new Long(record.get("route_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			BusRoute busRoute = oracleManager.find(BusRoute.class, route_id);


			oracleManager.merge(busRoute);
			oracleManager.flush();

			busRoute = oracleManager.find(BusRoute.class, route_id);

			busRoute.setLoggedUserName(loggedUserName);
			Long transpTypeId = busRoute.getService_id();
			TransportType transportType = oracleManager.find(
					TransportType.class, transpTypeId);
			if (transportType != null) {
				busRoute.setService_descr(transportType
						.getTransport_type_name_geo());
			}
			Long round_id = busRoute.getCycle_id();
			switch (round_id.intValue()) {
			case 1:
				busRoute.setСycle_descr("ჩვეულებრივი");
				break;
			case 2:
				busRoute.setСycle_descr("წრიული");
				break;
			default:
				break;
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return busRoute;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for BusRoute Into Database : ",
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
	 * Adding New BusRouteStreet
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public BusRouteStreet addBusRouteStreet(BusRouteStreet busRouteStreet)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addBusRouteStreet.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = busRouteStreet.getLoggedUserName();
			busRouteStreet.setRec_date(recDate);
			busRouteStreet.setRec_user(loggedUserName);

			oracleManager.persist(busRouteStreet);
			oracleManager.flush();

			busRouteStreet = oracleManager.find(BusRouteStreet.class,
					busRouteStreet.getRoute_street_id());

			busRouteStreet.setLoggedUserName(loggedUserName);
			Long streetId = busRouteStreet.getStreet_id();
			if (streetId != null) {
				StreetEnt streetEnt = oracleManager.find(StreetEnt.class,
						streetId);
				if (streetEnt != null) {
					busRouteStreet.setStreet_name(streetEnt
							.getStreet_name_geo());
				}
			}
			Long route_id = busRouteStreet.getRoute_id();
			if (route_id != null) {
				BusRoute busRoute = oracleManager
						.find(BusRoute.class, route_id);
				if (busRoute != null) {
					busRouteStreet.setRoute_descr(busRoute.getDir_num());
				}
			}
			Long route_dir = busRouteStreet.getRoute_dir();
			if (route_dir != null) {
				switch (route_dir.intValue()) {
				case 1:
					busRouteStreet.setRoute_dir_descr("წინ");
					break;
				case 2:
					busRouteStreet.setRoute_dir_descr("უკან");
					break;
				default:
					busRouteStreet.setRoute_dir_descr("უცნობი");
					break;
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return busRouteStreet;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert BusRouteStreet Into Database : ",
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
	 * Updating BusRouteStreet
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public BusRouteStreet updateBusRouteStreet(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateBusRouteStreet.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long route_street_id = new Long(record.get("route_street_id")
					.toString());
			String notes = record.get("notes") == null ? null : record.get(
					"notes").toString();
			Long route_dir = new Long(record.get("route_dir").toString());
			Long route_id = new Long(record.get("route_id").toString());
			Long route_order = new Long(record.get("route_order").toString());
			Long street_id = new Long(record.get("street_id").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			BusRouteStreet busRouteStreet = oracleManager.find(
					BusRouteStreet.class, route_street_id);

			busRouteStreet.setDeleted(0L);
			busRouteStreet.setUpd_user(loggedUserName);
			busRouteStreet.setNotes(notes);
			busRouteStreet.setRoute_dir(route_dir);
			busRouteStreet.setRoute_order(route_order);
			busRouteStreet.setStreet_id(street_id);
			busRouteStreet.setRoute_id(route_id);

			oracleManager.merge(busRouteStreet);
			oracleManager.flush();

			busRouteStreet = oracleManager.find(BusRouteStreet.class,
					route_street_id);

			busRouteStreet.setLoggedUserName(loggedUserName);
			if (street_id != null) {
				StreetEnt streetEnt = oracleManager.find(StreetEnt.class,
						street_id);
				if (streetEnt != null) {
					busRouteStreet.setStreet_name(streetEnt
							.getStreet_name_geo());
				}
			}
			if (route_id != null) {
				BusRoute busRoute = oracleManager
						.find(BusRoute.class, route_id);
				if (busRoute != null) {
					busRouteStreet.setRoute_descr(busRoute.getDir_num());
				}
			}
			if (route_dir != null) {
				switch (route_dir.intValue()) {
				case 1:
					busRouteStreet.setRoute_dir_descr("წინ");
					break;
				case 2:
					busRouteStreet.setRoute_dir_descr("უკან");
					break;
				default:
					busRouteStreet.setRoute_dir_descr("უცნობი");
					break;
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return busRouteStreet;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update BusRouteStreet Into Database : ",
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
	 * Updating BusRouteStreet Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public BusRouteStreet updateBusRouteStreetStatus(Map record)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateBusRouteStreetStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long route_street_id = new Long(record.get("route_street_id")
					.toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();

			BusRouteStreet busRouteStreet = oracleManager.find(
					BusRouteStreet.class, route_street_id);

			busRouteStreet.setDeleted(deleted);
			busRouteStreet.setUpd_user(loggedUserName);

			oracleManager.merge(busRouteStreet);
			oracleManager.flush();

			busRouteStreet = oracleManager.find(BusRouteStreet.class,
					route_street_id);

			busRouteStreet.setLoggedUserName(loggedUserName);
			Long streetId = busRouteStreet.getStreet_id();
			if (streetId != null) {
				StreetEnt streetEnt = oracleManager.find(StreetEnt.class,
						streetId);
				if (streetEnt != null) {
					busRouteStreet.setStreet_name(streetEnt
							.getStreet_name_geo());
				}
			}
			Long route_id = busRouteStreet.getRoute_id();
			if (route_id != null) {
				BusRoute busRoute = oracleManager
						.find(BusRoute.class, route_id);
				if (busRoute != null) {
					busRouteStreet.setRoute_descr(busRoute.getDir_num());
				}
			}
			Long route_dir = busRouteStreet.getRoute_dir();
			if (route_dir != null) {
				switch (route_dir.intValue()) {
				case 1:
					busRouteStreet.setRoute_dir_descr("წინ");
					break;
				case 2:
					busRouteStreet.setRoute_dir_descr("უკან");
					break;
				default:
					busRouteStreet.setRoute_dir_descr("უცნობი");
					break;
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return busRouteStreet;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for BusRouteStreet Into Database : ",
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
	 * Adding New Transport
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Transport addTransport(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addTransport.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = dsRequest.getFieldValue("loggedUserName").toString();
			Long transport_type_id = dsRequest.getFieldValue("transport_type_id")==null?null: new Long(dsRequest.getFieldValue("transport_type_id").toString()); 
			Long out_transport_place_id = dsRequest.getFieldValue("out_transport_place_id")==null?null: new Long(dsRequest.getFieldValue("out_transport_place_id").toString());
			Long in_transport_place_id = dsRequest.getFieldValue("in_transport_place_id")==null?null: new Long(dsRequest.getFieldValue("in_transport_place_id").toString());
			Date out_time = dsRequest.getFieldValue("out_time")==null?null: (Date)dsRequest.getFieldValue("out_time");
			Date in_time = dsRequest.getFieldValue("in_time")==null?null: (Date)dsRequest.getFieldValue("in_time");
			String note_geo = dsRequest.getFieldValue("note_geo")==null?null: dsRequest.getFieldValue("note_geo").toString();
			Long deleted = dsRequest.getFieldValue("deleted")==null?null: new Long(dsRequest.getFieldValue("deleted").toString());
			String transport_price_geo = dsRequest.getFieldValue("transport_price_geo")==null?null: dsRequest.getFieldValue("transport_price_geo").toString();
			Long transport_company_id = dsRequest.getFieldValue("transport_company_id")==null?null: new Long(dsRequest.getFieldValue("transport_company_id").toString());
			Long transport_plane_id = dsRequest.getFieldValue("transport_plane_id")==null?null: new Long(dsRequest.getFieldValue("transport_plane_id").toString());
			String trip_criteria = dsRequest.getFieldValue("trip_criteria")==null?null: dsRequest.getFieldValue("trip_criteria").toString();
			Long note_crit = dsRequest.getFieldValue("note_crit")==null?null: new Long(dsRequest.getFieldValue("note_crit").toString());
			Long days = dsRequest.getFieldValue("days")==null?null: new Long(dsRequest.getFieldValue("days").toString());

			Transport transport = new Transport();
			transport.setDays(new Long(days));			
			transport.setDeleted(deleted);
			if (in_time != null) {
				transport.setIn_time(new Timestamp(in_time.getTime()));				
			}
			if (out_time != null) {
				transport.setOut_time(new Timestamp(out_time.getTime()));				
			}
			transport.setIn_transport_place_id(in_transport_place_id);
			transport.setNote_crit(note_crit);
			transport.setNote_geo(note_geo);
			transport.setOut_transport_place_id(out_transport_place_id);
			transport.setRec_date(recDate);
			transport.setRec_user(loggedUserName);
			transport.setTransport_company_id(transport_company_id);
			transport.setTransport_plane_id(transport_plane_id);
			transport.setTransport_price_geo(transport_price_geo);
			transport.setTransport_type_id(transport_type_id);
			transport.setTrip_criteria(trip_criteria);			
			
			oracleManager.persist(transport);
			
			Object oListTranspDetails = dsRequest.getFieldValue("listTranspDetails");
			if (oListTranspDetails != null) {
				Map listTranspDetails = (Map) oListTranspDetails;
				if(listTranspDetails!=null && !listTranspDetails.isEmpty()){
					Set keys = listTranspDetails.keySet();
					for (Object object : keys) {
						String transport_place_id = object.toString(); 
						Map transpDetailsMap = (Map) listTranspDetails.get(transport_place_id);
						if (transpDetailsMap == null || transpDetailsMap.isEmpty()) {
							continue;
						}
						Long deleted_det = transpDetailsMap.get("deleted")==null?null:new Long(transpDetailsMap.get("deleted").toString());
						Long transport_detail_order = transpDetailsMap.get("transport_detail_order")==null?null:new Long(transpDetailsMap.get("transport_detail_order").toString());
						Date out_time_det = transpDetailsMap.get("out_time")==null?null: (Date)transpDetailsMap.get("out_time");
						Date in_time_det = transpDetailsMap.get("in_time")==null?null: (Date)transpDetailsMap.get("in_time");
						
						TransportDetail transportDetail = new TransportDetail();
						transportDetail.setDeleted(deleted_det);
						if (in_time_det != null) {
							transportDetail.setIn_time(new Timestamp(in_time_det.getTime()));				
						}
						if (out_time_det != null) {
							transportDetail.setOut_time(new Timestamp(out_time_det.getTime()));				
						}
						transportDetail.setRec_date(recDate);
						transportDetail.setRec_user(loggedUserName);
						transportDetail.setOut_transport_place_id(out_transport_place_id);
						transportDetail.setIn_transport_place_id(in_transport_place_id);
						
						transportDetail.setTransport_detail_order(transport_detail_order);
						transportDetail.setTransport_id(transport.getTransport_id());
						transportDetail.setTransport_place_id(new Long(transport_place_id));
						
						oracleManager.persist(transportDetail);
					}
				}
			}
			oracleManager.flush();
			
			transport.setLoggedUserName(loggedUserName);
			
			List resultList = oracleManager.createNativeQuery(Q_GET_TRANSPORT_BY_ID).setParameter(1, transport.getTransport_id()).getResultList();
			if(resultList!=null && !resultList.isEmpty()){
				Object array[] = (Object[])resultList.get(0);
				String days_descr = array[0]==null?null:array[0].toString();  
				String transport_type_name_geo = array[1]==null?null:array[1].toString();
				String transport_place_geo_out = array[2]==null?null:array[2].toString();
				String transport_place_geo_in = array[3]==null?null:array[3].toString();
				String transport_company_geo = array[4]==null?null:array[4].toString();				
				String transport_plane_geo = array[5]==null?null:array[5].toString();
				transport.setDays_descr(days_descr);
				transport.setTransport_type_name_geo(transport_type_name_geo);
				transport.setTransport_place_geo_out(transport_place_geo_out);
				transport.setTransport_place_geo_in(transport_place_geo_in);
				transport.setTransport_company_geo(transport_company_geo);
				transport.setTransport_plane_geo(transport_plane_geo);
			}
			
			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return transport;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert Transport Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating Transport
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Transport updateTransport(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransport.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Timestamp recDate = new Timestamp(System.currentTimeMillis());
			String loggedUserName = record.get("loggedUserName").toString();
			Long transport_id = record.get("transport_id")==null?null: new Long(record.get("transport_id").toString());
			Long transport_type_id = record.get("transport_type_id")==null?null: new Long(record.get("transport_type_id").toString()); 
			Long out_transport_place_id = record.get("out_transport_place_id")==null?null: new Long(record.get("out_transport_place_id").toString());
			Long in_transport_place_id = record.get("in_transport_place_id")==null?null: new Long(record.get("in_transport_place_id").toString());
			Date out_time = record.get("out_time")==null?null: (Date)record.get("out_time");
			Date in_time = record.get("in_time")==null?null: (Date)record.get("in_time");
			String note_geo = record.get("note_geo")==null?null: record.get("note_geo").toString();
			Long deleted = record.get("deleted")==null?null: new Long(record.get("deleted").toString());
			String transport_price_geo = record.get("transport_price_geo")==null?null: record.get("transport_price_geo").toString();
			Long transport_company_id = record.get("transport_company_id")==null?null: new Long(record.get("transport_company_id").toString());
			Long transport_plane_id = record.get("transport_plane_id")==null?null: new Long(record.get("transport_plane_id").toString());
			String trip_criteria = record.get("trip_criteria")==null?null: record.get("trip_criteria").toString();
			Long note_crit = record.get("note_crit")==null?null: new Long(record.get("note_crit").toString());
			Long days = record.get("days")==null?null: new Long(record.get("days").toString());

			Transport transport = oracleManager.find(Transport.class, transport_id);

			transport.setDays(new Long(days));			
			transport.setDeleted(deleted);
			if (in_time != null) {
				transport.setIn_time(new Timestamp(in_time.getTime()));				
			}
			if (out_time != null) {
				transport.setOut_time(new Timestamp(out_time.getTime()));				
			}
			transport.setIn_transport_place_id(in_transport_place_id);
			transport.setNote_crit(note_crit);
			transport.setNote_geo(note_geo);
			transport.setOut_transport_place_id(out_transport_place_id);
			transport.setUpd_date(recDate);
			transport.setUpd_user(loggedUserName);
			transport.setTransport_company_id(transport_company_id);
			transport.setTransport_plane_id(transport_plane_id);
			transport.setTransport_price_geo(transport_price_geo);
			transport.setTransport_type_id(transport_type_id);
			transport.setTrip_criteria(trip_criteria);	

			oracleManager.merge(transport);
			
			Object oListTranspDetails = record.get("listTranspDetails");
			System.out.println("oListTranspDetails = "+oListTranspDetails);
			if (oListTranspDetails != null) {
				Map listTranspDetails = (Map) oListTranspDetails;
				if(listTranspDetails!=null && !listTranspDetails.isEmpty()){
					Set keys = listTranspDetails.keySet();
					for (Object object : keys) {
						String transport_detail_id = object.toString();
						 
						Map transpDetailsMap = (Map) listTranspDetails.get(transport_detail_id);
						if (transpDetailsMap == null || transpDetailsMap.isEmpty()) {
							continue;
						}
						Long transport_place_id = transpDetailsMap.get("transport_place_id")==null?null:new Long(transpDetailsMap.get("transport_place_id").toString());
						Long deleted_det = transpDetailsMap.get("deleted")==null?null:new Long(transpDetailsMap.get("deleted").toString());
						Long transport_detail_order = transpDetailsMap.get("transport_detail_order")==null?null:new Long(transpDetailsMap.get("transport_detail_order").toString());
						Date out_time_det = (transpDetailsMap.get("c_out_time")==null || transpDetailsMap.get("c_out_time").toString().trim().equals(""))?null: new Date(new Long(transpDetailsMap.get("c_out_time").toString()));
						Date in_time_det = (transpDetailsMap.get("c_in_time")==null || transpDetailsMap.get("c_in_time").toString().trim().equals(""))?null: new Date(new Long(transpDetailsMap.get("c_in_time").toString()));
						
						TransportDetail transportDetail = null;
						boolean insert = false;
						if (transport_detail_id != null) {
							transportDetail = oracleManager.find(TransportDetail.class,new Long(transport_detail_id));
							if(transportDetail==null){
								transportDetail = new TransportDetail();
							}else{
								transportDetail.setUpd_user(loggedUserName);	
							}
						} else {
							transportDetail = new TransportDetail();
							transportDetail.setRec_date(recDate);
							transportDetail.setRec_user(loggedUserName);
							insert = true;
						}
						
						transportDetail.setDeleted(deleted_det);
						if (in_time_det != null) {
							transportDetail.setIn_time(new Timestamp(in_time_det.getTime()));				
						}
						if (out_time_det != null) {
							transportDetail.setOut_time(new Timestamp(out_time_det.getTime()));				
						}
						transportDetail.setTransport_detail_order(transport_detail_order);
						transportDetail.setTransport_id(transport.getTransport_id());
						transportDetail.setTransport_place_id(new Long(transport_place_id));
						transportDetail.setOut_transport_place_id(out_transport_place_id);
						transportDetail.setIn_transport_place_id(in_transport_place_id);
						
						if (insert) {
							oracleManager.persist(transportDetail);	
						} else {
							oracleManager.merge(transportDetail);
						}
					}
				}
			}
			oracleManager.flush();
			transport = oracleManager.find(Transport.class, transport_id);

			transport.setLoggedUserName(loggedUserName);
			
			List resultList = oracleManager.createNativeQuery(Q_GET_TRANSPORT_BY_ID).setParameter(1, transport.getTransport_id()).getResultList();
			if(resultList!=null && !resultList.isEmpty()){
				Object array[] = (Object[])resultList.get(0);
				String days_descr = array[0]==null?null:array[0].toString();  
				String transport_type_name_geo = array[1]==null?null:array[1].toString();
				String transport_place_geo_out = array[2]==null?null:array[2].toString();
				String transport_place_geo_in = array[3]==null?null:array[3].toString();
				String transport_company_geo = array[4]==null?null:array[4].toString();				
				String transport_plane_geo = array[5]==null?null:array[5].toString();
				transport.setDays_descr(days_descr);
				transport.setTransport_type_name_geo(transport_type_name_geo);
				transport.setTransport_place_geo_out(transport_place_geo_out);
				transport.setTransport_place_geo_in(transport_place_geo_in);
				transport.setTransport_company_geo(transport_company_geo);
				transport.setTransport_plane_geo(transport_plane_geo);
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return transport;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Transport Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Updating Transport Status
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Transport updateTransportStatus(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateTransportStatus.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long transport_id = new Long(record.get("transport_id").toString());
			Long deleted = new Long(record.get("deleted").toString());
			String loggedUserName = record.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			Transport transport = oracleManager.find(Transport.class, transport_id);

			transport.setDeleted(deleted);
			transport.setUpd_user(loggedUserName);
			transport.setUpd_date(recDate);

			oracleManager.merge(transport);
			
			oracleManager.flush();

			transport = oracleManager.find(Transport.class, transport_id);
			transport.setLoggedUserName(loggedUserName);
			List resultList = oracleManager.createNativeQuery(Q_GET_TRANSPORT_BY_ID).setParameter(1, transport.getTransport_id()).getResultList();
			if(resultList!=null && !resultList.isEmpty()){
				Object array[] = (Object[])resultList.get(0);
				String days_descr = array[0]==null?null:array[0].toString();  
				String transport_type_name_geo = array[1]==null?null:array[1].toString();
				String transport_place_geo_out = array[2]==null?null:array[2].toString();
				String transport_place_geo_in = array[3]==null?null:array[3].toString();
				String transport_company_geo = array[4]==null?null:array[4].toString();				
				String transport_plane_geo = array[5]==null?null:array[5].toString();
				transport.setDays_descr(days_descr);
				transport.setTransport_type_name_geo(transport_type_name_geo);
				transport.setTransport_place_geo_out(transport_place_geo_out);
				transport.setTransport_place_geo_in(transport_place_geo_in);
				transport.setTransport_company_geo(transport_company_geo);
				transport.setTransport_plane_geo(transport_plane_geo);
			}

			EMF.commitTransaction(transaction);
			log += ". Status Updating Finished SuccessFully. ";
			logger.info(log);
			return transport;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update Status for Transport Into Database : ",
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
