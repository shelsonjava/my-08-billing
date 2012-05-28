package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.org.Organization;
import com.info08.billing.callcenterbk.shared.entity.org.OrganizationActivity;
import com.info08.billing.callcenterbk.shared.entity.org.OrganizationPartnerBank;
import com.info08.billing.callcenterbk.shared.entity.org.OrganizationToActivity;
import com.info08.billing.callcenterbk.shared.items.Address;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;
import com.isomorphic.util.DataTools;

public class OrganizationDMI {

	private static final String Q_CHECK_ORG_ACTIVITIES = "select count(1) from organization_to_activities t where t.org_activity_id = ? ";
	private static final String Q_DELETE_ORG_ACTIVITIES = "delete from organization_to_activities t where t.organization_id = ? ";
	private static final String Q_DELETE_ORG_PART_BANKS = "delete from organization_partner_banks t where t.organization_id = ? ";

	private Logger logger = Logger.getLogger(OrganizationDMI.class.getName());

	/**
	 * Adding New OrganizationActivity
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public OrganizationActivity addOrgActivity(
			OrganizationActivity organizationActivity) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.addOrgActivity.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			String loggedUserName = organizationActivity.getLoggedUserName();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Add OrganizationActivity.");

			oracleManager.persist(organizationActivity);
			oracleManager.flush();

			organizationActivity = oracleManager.find(
					OrganizationActivity.class,
					organizationActivity.getOrg_activity_id());

			organizationActivity.setLoggedUserName(loggedUserName);

			Long is_bank_activity = organizationActivity.getIs_bank_activity();
			if (is_bank_activity != null) {
				switch (is_bank_activity.intValue()) {
				case 0:
					organizationActivity.setIs_bank_activity_descr("არა");
					break;
				case 1:
					organizationActivity.setIs_bank_activity_descr("დიახ");
					break;
				default:
					organizationActivity.setIs_bank_activity_descr("უცნობი");
					break;
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Inserting Finished SuccessFully. ";
			logger.info(log);
			return organizationActivity;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Insert organizationActivity Into Database : ",
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
	 * Updating OrganizationActivity
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public OrganizationActivity updateOrgActivity(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:CommonDMI.updateOrgActivity.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long org_activity_id = new Long(record.get("org_activity_id")
					.toString());
			String activity_description = record.get("activity_description") == null ? null
					: record.get("activity_description").toString();
			String remark = record.get("remark") == null ? null : record.get(
					"remark").toString();
			Long is_bank_activity = new Long(record.get("is_bank_activity")
					.toString());

			String loggedUserName = record.get("loggedUserName").toString();

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update OrganizationActivity.");

			OrganizationActivity organizationActivity = oracleManager.find(
					OrganizationActivity.class, org_activity_id);

			organizationActivity.setActivity_description(activity_description);
			organizationActivity.setIs_bank_activity(is_bank_activity);
			organizationActivity.setRemark(remark);

			oracleManager.merge(organizationActivity);
			oracleManager.flush();

			organizationActivity = oracleManager.find(
					OrganizationActivity.class, org_activity_id);

			if (is_bank_activity != null) {
				switch (is_bank_activity.intValue()) {
				case 0:
					organizationActivity.setIs_bank_activity_descr("არა");
					break;
				case 1:
					organizationActivity.setIs_bank_activity_descr("დიახ");
					break;
				default:
					organizationActivity.setIs_bank_activity_descr("უცნობი");
					break;
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return organizationActivity;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While Update organizationActivity Into Database : ",
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
	 * Remove OrganizationActivity
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public OrganizationActivity removeOrgActivity(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {

			String log = "Method:CommonDMI.removeOrgActivity.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Long org_activity_id = new Long(dsRequest.getOldValues()
					.get("org_activity_id").toString());

			Long count = new Long(oracleManager
					.createNativeQuery(Q_CHECK_ORG_ACTIVITIES)
					.setParameter(1, org_activity_id).getSingleResult()
					.toString());

			if (count.intValue() > 0) {
				throw new CallCenterException("ეს საქმიანობა უკვე მიბმულია "
						+ count + " ორგანიზაციაზე !!! ");
			}

			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp updDate = new Timestamp(System.currentTimeMillis());

			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Removing EventCategory.");

			OrganizationActivity organizationActivity = oracleManager.find(
					OrganizationActivity.class, org_activity_id);

			oracleManager.remove(organizationActivity);
			oracleManager.flush();

			EMF.commitTransaction(transaction);
			log += ". Status Removing Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While remove organizationActivity from Database : ",
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
	 * Updating Organizations
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Organization updateMainServiceOrg(Map record) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			System.out
					.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			String log = "Method:CommonDMI.updateMainServiceOrg.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long parrent_organization_id = record
					.get("parrent_organization_id") == null ? null : new Long(
					record.get("parrent_organization_id").toString());
			Long organization_id = new Long(record.get("organization_id")
					.toString());
			String loggedUserName = record.get("loggedUserName").toString();

			System.out.println("parrent_organization_id = "
					+ parrent_organization_id);
			System.out.println("organization_id = " + organization_id);
			System.out.println("loggedUserName = " + loggedUserName);

			Timestamp updDate = new Timestamp(System.currentTimeMillis());
			RCNGenerator.getInstance().initRcn(oracleManager, updDate,
					loggedUserName, "Update Organization.");

			Organization organization = oracleManager.find(Organization.class,
					organization_id);

			organization.setParrent_organization_id(parrent_organization_id);
			oracleManager.merge(organization);
			EMF.commitTransaction(transaction);
			DMIUtils.findRecordById("OrgDS", "customOrgSearchForCallCenterNew",
					organization_id, "organization_id", organization);
			organization.setLoggedUserName(loggedUserName);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update organization Into Database : ", e);
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
	 * @param dsRequest
	 * @return
	 * @throws Exception
	 */
	public Map<?, ?> addOrUpdateOrganization(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrganizationDMI.addOrUpdateOrganization.";
			Map<?, ?> values = dsRequest.getValues();
			Long organization_id = values.containsKey("organization_id") ? Long
					.parseLong(values.get("organization_id").toString()) : null;
			String loggedUserName = values.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Organization organization = null;
			if (organization_id != null) {
				organization = oracleManager.find(Organization.class,
						organization_id);
			}
			if (organization == null && organization_id != null)
				throw new Exception("ვერ ვიპოვე ორგანიზაცია შესაცვლელად(ID="
						+ organization_id + ")");
			if (organization == null) {
				organization = new Organization();
			}

			DataTools.setProperties(values, organization);

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);

			Long legal_address_id = values.containsKey("legal_address_id") ? new Long(
					values.get("legal_address_id").toString()) : null;
			Long physical_address_id = values
					.containsKey("physical_address_id") ? new Long(values.get(
					"physical_address_id").toString()) : null;

			Address legalAddress = null;
			if (legal_address_id != null) {
				legalAddress = oracleManager.find(Address.class,
						legal_address_id);
				if (legalAddress == null) {
					legalAddress = new Address();
				}
			} else {
				legalAddress = new Address();
			}
			DataTools.setProperties(values, legalAddress);

			if (legalAddress.getAddr_id() != null) {
				oracleManager.merge(legalAddress);
			} else {
				oracleManager.persist(legalAddress);
			}

			Address physicalAddress = null;
			if (physical_address_id != null) {
				physicalAddress = oracleManager.find(Address.class,
						physical_address_id);
				if (physicalAddress == null) {
					physicalAddress = new Address();
				}
			} else {
				physicalAddress = new Address();
			}
			physicalAddress
					.setTown_id(values.containsKey("legal_addr_town_id") ? new Long(
							values.get("legal_addr_town_id").toString()) : null);
			physicalAddress.setStreet_id(values
					.containsKey("legal_addr_street_id") ? new Long(values.get(
					"legal_addr_street_id").toString()) : null);
			physicalAddress.setTown_district_id(values
					.containsKey("legal_addr_town_district_id") ? new Long(
					values.get("legal_addr_town_district_id").toString())
					: null);
			physicalAddress.setHidden_by_request(values
					.containsKey("legal_addr_hidden_by_request") ? new Long(
					values.get("legal_addr_hidden_by_request").toString())
					: null);
			physicalAddress
					.setAnumber(values.containsKey("legal_addr_anumber") ? values
							.get("legal_addr_anumber").toString() : null);
			physicalAddress.setFull_address(values
					.containsKey("legal_addr_full_address") ? values.get(
					"legal_addr_full_address").toString() : null);
			physicalAddress
					.setBlock(values.containsKey("legal_addr_block") ? values
							.get("legal_addr_block").toString() : null);
			physicalAddress
					.setAppt(values.containsKey("legal_addr_appt") ? values
							.get("legal_addr_appt").toString() : null);

			if (physicalAddress.getAddr_id() != null) {
				oracleManager.merge(physicalAddress);
			} else {
				oracleManager.persist(physicalAddress);
			}

			organization.setLegal_address_id(legalAddress.getAddr_id());
			organization.setPhysical_address_id(physicalAddress.getAddr_id());

			boolean isPersist = (organization_id == null);
			if (organization_id == null) {
				oracleManager.persist(organization);
			} else {
				oracleManager.merge(organization);
			}

			organization_id = organization.getOrganization_id();

			if (!isPersist) {
				oracleManager.createNativeQuery(Q_DELETE_ORG_ACTIVITIES)
						.setParameter(1, organization_id).executeUpdate();
			}

			Map<?, ?> orgActivities = (Map<?, ?>) values.get("orgActivities");
			Set<?> orgActKeys = orgActivities.keySet();
			for (Object orgActKey : orgActKeys) {
				Object value = orgActivities.get(orgActKey);
				OrganizationToActivity organizationToActivity = new OrganizationToActivity();
				organizationToActivity.setOrganization_id(organization_id);
				organizationToActivity.setOrg_activity_id(new Long(value
						.toString()));
				oracleManager.persist(organizationToActivity);
			}

			if (!isPersist) {
				oracleManager.createNativeQuery(Q_DELETE_ORG_PART_BANKS)
						.setParameter(1, organization_id).executeUpdate();
			}

			Map<?, ?> orgPartnerBanks = (Map<?, ?>) values
					.get("orgPartnerBanks");
			Set<?> orgPartnerBankKeys = orgPartnerBanks.keySet();
			for (Object partBankKey : orgPartnerBankKeys) {
				Object value = orgPartnerBanks.get(partBankKey);
				OrganizationPartnerBank organizationPartnerBank = new OrganizationPartnerBank();
				organizationPartnerBank.setOrganization_id(organization_id);
				organizationPartnerBank.setPart_bank_org_id(new Long(value
						.toString()));
				oracleManager.persist(organizationPartnerBank);
			}

			EMF.commitTransaction(transaction);
			log += ". Save Or Update Finished SuccessFully. ";
			logger.info(log);
			values = DMIUtils.findRecordById("OrgDS",
					"customOrgSearchForCallCenterNew", organization_id,
					"organization_id");
			return values;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While adding New organization Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : " + e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
