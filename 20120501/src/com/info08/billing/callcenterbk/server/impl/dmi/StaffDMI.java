package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.Staff;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;
import com.isomorphic.util.DataTools;

public class StaffDMI implements QueryConstants {

	Logger logger = Logger.getLogger(StaffDMI.class.getName());

	/**
	 * Adding New Staff
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public Map<?, ?> addOrUpdate(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:StaffDMI.addOrUpdate.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Map<?, ?> values = dsRequest.getValues();
			Long staff_id = values.containsKey("staff_id") ? Long
					.parseLong(values.get("staff_id").toString()) : null;
			String loggedUserName = values.get("loggedUserName").toString();

			Staff staff = null;
			if (staff_id != null) {
				staff = oracleManager.find(Staff.class, staff_id);
			}
			if (staff == null && staff_id != null)
				throw new Exception("ვერ ვიპოვე თანამშრომელი შესაცვლელად(ID="
						+ staff_id + ")");
			if (staff == null) {
				staff = new Staff();
			}

			DataTools.setProperties(values, staff);

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add or Update Staff.");

			if (staff_id != null) {
				oracleManager.merge(staff);
			} else {
				oracleManager.persist(staff);
			}
			oracleManager.flush();

			staff = oracleManager.find(Staff.class, staff.getStaff_id());

			EMF.commitTransaction(transaction);

			values = DMIUtils.findRecordById("StaffDS", "getAllStaff",
					staff.getStaff_id(), "staff_id");

			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return values;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert staff Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	/**
	 * Delete Staff
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public Staff removeStaff(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:StaffDMI.removeStaff.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long staff_id = new Long(dsRequest.getOldValues().get("staff_id")
					.toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing Staff.");

			Staff staff = oracleManager.find(Staff.class, staff_id);
			staff.setLoggedUserName(loggedUserName);

			oracleManager.remove(staff);
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
			logger.error("Error While Remove Staff From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
