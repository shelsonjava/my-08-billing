package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.org.Organization;
import com.info08.billing.callcenterbk.shared.entity.org.OrganizationActivity;
import com.info08.billing.callcenterbk.shared.entity.org.OrganizationDepartMent;
import com.info08.billing.callcenterbk.shared.entity.org.OrganizationDepartToPhone;
import com.info08.billing.callcenterbk.shared.entity.org.OrganizationPartnerBank;
import com.info08.billing.callcenterbk.shared.entity.org.OrganizationToActivity;
import com.info08.billing.callcenterbk.shared.items.Address;
import com.info08.billing.callcenterbk.shared.items.PhoneNumber;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.jpa.EMF;
import com.isomorphic.util.DataTools;

public class OrganizationDMI {

	private static final String Q_CHECK_ORG_ACTIVITIES = "select count(1) from organization_to_activities t where t.org_activity_id = ? ";
	private static final String Q_DELETE_ORG_ACTIVITIES = "delete from organization_to_activities t where t.organization_id = ? ";
	private static final String Q_DELETE_ORG_PART_BANKS = "delete from organization_partner_banks t where t.organization_id = ? ";
	private static final String Q_DELETE_ORG_DEP_PHONES = "delete from organization_depart_to_phones t where t.org_department_id = ? ";
	private static final String Q_DELETE_ORG_DEPARTMENT = "delete from organization_department t where t.org_department_id = ? ";
	private static final String Q_DELETE_ADDRESS = "delete from addresses a where a.addr_id = ? ";
	private static final String Q_DELETE_ORGANIZATION = "delete from organizations t where t.organization_id = ? ";
	private static final String Q_UPDATE_ORG_DEP_ORDER = " update organization_department t set t.inner_order = ? where t.org_department_id = ? and t.inner_order <> ? ";
	private static final String Q_UPDATE_ORG_ORDER = " update organizations t set t.priority = ? where t.organization_id = ? and t.priority <> ? ";
	private static final String Q_UPDATE_ORG_DEP_PHONE_ORDER = " update organization_depart_to_phones t set t.phone_order = ? where t.org_dep_to_ph_id = ? and t.phone_order <> ? ";

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
			String log = "Method:OrganizationDMI.addOrgActivity.";
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
			String log = "Method:OrganizationDMI.updateOrgActivity.";
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

			String log = "Method:OrganizationDMI.removeOrgActivity.";
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
			String log = "Method:OrganizationDMI.updateMainServiceOrg.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long parrent_organization_id = record
					.get("parrent_organization_id") == null ? null : new Long(
					record.get("parrent_organization_id").toString());
			Long organization_id = new Long(record.get("organization_id")
					.toString());
			String loggedUserName = record.get("loggedUserName").toString();

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
	public DSResponse addOrUpdateOrganization(DSRequest dsRequest)
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

			Long legal_address_id = values.get("legal_address_id") != null ? new Long(
					values.get("legal_address_id").toString()) : null;
			Long physical_address_id = values.get("physical_address_id") != null ? new Long(
					values.get("physical_address_id").toString()) : null;

			Map<?, ?> legalAddrValues = (Map<?, ?>) values
					.get("legalAddrValues");
			boolean deleteLegalAddress = legal_address_id != null
					&& legalAddrValues == null;

			if (legalAddrValues != null) {
				legal_address_id = persistAddress(oracleManager, values,
						legal_address_id, "legalAddrValues").getAddr_id();
			}
			Address physical_address = persistAddress(oracleManager, values,
					physical_address_id, "physicalAddrValues");
			physical_address_id = physical_address.getAddr_id();

			organization.setLegal_address_id(deleteLegalAddress ? null
					: legal_address_id);
			organization.setPhysical_address_id(physical_address_id);

			organization.setPh_town_id(physical_address.getTown_id());

			boolean isPersist = (organization_id == null);
			if (organization_id == null) {
				oracleManager.persist(organization);
			} else {
				oracleManager.merge(organization);
			}
			if (deleteLegalAddress) {
				oracleManager.remove(oracleManager.find(Address.class,
						legal_address_id));
			}
			organization_id = organization.getOrganization_id();

			if (!isPersist) {
				oracleManager.createNativeQuery(Q_DELETE_ORG_ACTIVITIES)
						.setParameter(1, organization_id).executeUpdate();
				oracleManager.flush();
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
				oracleManager.flush();
			}

			Map<?, ?> orgPartnerBanks = (Map<?, ?>) values
					.get("orgPartnerBanks");
			if (orgPartnerBanks != null) {
				Set<?> orgPartnerBankKeys = orgPartnerBanks.keySet();
				for (Object partBankKey : orgPartnerBankKeys) {
					Object value = orgPartnerBanks.get(partBankKey);
					OrganizationPartnerBank organizationPartnerBank = new OrganizationPartnerBank();
					organizationPartnerBank.setOrganization_id(organization_id);
					organizationPartnerBank.setPart_bank_org_id(new Long(value
							.toString()));
					oracleManager.persist(organizationPartnerBank);
				}
			}

			oracleManager.flush();
			oracleManager.createNativeQuery("{call createOrganizationHist(?)}")
					.setParameter(1, organization_id).executeUpdate();

			EMF.commitTransaction(transaction);
			log += ". Save Or Update Finished SuccessFully. ";
			logger.info(log);
			values = DMIUtils.findRecordById("OrgDS",
					"customOrgSearchForCallCenterNew", organization_id,
					"organization_id");
			DSResponse dsResponse = new DSResponse();
			dsResponse.setData(values);
			//dsResponse.setInvalidateCache(true);
			return dsResponse;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While adding New organization Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	private Address persistAddress(EntityManager oracleManager,
			Map<?, ?> values, Long address_id, String subMapValueNames)
			throws Exception {
		Address address = null;
		if (address_id != null) {
			address = oracleManager.find(Address.class, address_id);
			if (address == null) {
				address = new Address();
			}
		} else {
			address = new Address();
		}

		Map<?, ?> addrValues = (Map<?, ?>) values.get(subMapValueNames);
		DataTools.setProperties(addrValues, address);
		address.setAddr_id(address_id);

		if (address.getAddr_id() != null) {
			oracleManager.merge(address);
		} else {
			oracleManager.persist(address);
		}
		return address;
	}

	/**
	 * 
	 * @param dsRequest
	 * @return
	 * @throws Exception
	 */
	public Map<?, ?> addOrUpdateOrganizationDepartment(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrganizationDMI.addOrUpdateOrganizationDepartment.";
			Map<?, ?> values = dsRequest.getValues();
			Long org_department_id = values.containsKey("org_department_id") ? Long
					.parseLong(values.get("org_department_id").toString())
					: null;
			String loggedUserName = values.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			OrganizationDepartMent organizationDepartMent = null;
			if (org_department_id != null) {
				organizationDepartMent = oracleManager.find(
						OrganizationDepartMent.class, org_department_id);
			}
			if (organizationDepartMent == null && org_department_id != null)
				throw new Exception("ვერ ვიპოვე დეპარტამენტი შესაცვლელად(ID="
						+ org_department_id + ")");
			if (organizationDepartMent == null) {
				organizationDepartMent = new OrganizationDepartMent();
			}
			DataTools.setProperties(values, organizationDepartMent);
			organizationDepartMent.setDepartment(values.get(
					"department_original").toString());
			organizationDepartMent.setInner_order(0L);

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);

			Map<?, ?> physicalAddrValues = (Map<?, ?>) values
					.get("physicalAddrValues");
			Long physical_address_id = values.get("physical_address_id") != null ? new Long(
					values.get("physical_address_id").toString()) : null;
			boolean deletePhysicalAddress = physical_address_id != null
					&& physicalAddrValues == null;

			if (physicalAddrValues != null) {
				physical_address_id = persistAddress(oracleManager, values,
						physical_address_id, "physicalAddrValues").getAddr_id();
			}

			organizationDepartMent
					.setPhysical_address_id(deletePhysicalAddress ? null
							: physical_address_id);

			if (org_department_id == null) {
				oracleManager.persist(organizationDepartMent);
			} else {
				oracleManager.merge(organizationDepartMent);
			}
			org_department_id = organizationDepartMent.getOrg_department_id();

			if (deletePhysicalAddress) {
				oracleManager.remove(oracleManager.find(Address.class,
						physical_address_id));
			}

			oracleManager.flush();

			oracleManager
					.createNativeQuery("{call createOrgDepartmentHist(?)}")
					.setParameter(1, org_department_id).executeUpdate();
			EMF.commitTransaction(transaction);
			log += ". Save Or Update Finished SuccessFully. ";
			logger.info(log);
			values = DMIUtils.findRecordById("OrgDepartmentDS",
					"orgDepartSearch", org_department_id, "org_department_id");
			return values;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error(
					"Error While adding New organization Into Database : ", e);
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
	public OrganizationDepartMent removeOrganizationDepartment(
			DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrganizationDMI.removeOrganizationDepartment.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long org_department_id = new Long(dsRequest.getOldValues()
					.get("org_department_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			Map<String, Integer> criteria = new LinkedHashMap<String, Integer>();
			criteria.put("org_department_id", org_department_id.intValue());
			List<OrganizationDepartMent> dataForDelete = DMIUtils
					.findObjectsdByCriteria("OrgDepartmentDS",
							"orgDepartSearchCustom1", criteria,
							OrganizationDepartMent.class);

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "Remove Department.");

			if (dataForDelete != null && !dataForDelete.isEmpty()) {
				for (OrganizationDepartMent item : dataForDelete) {
					Long orgDepartId = item.getOrg_department_id();
					oracleManager.createNativeQuery(Q_DELETE_ORG_DEP_PHONES)
							.setParameter(1, orgDepartId).executeUpdate();
					oracleManager.createNativeQuery(Q_DELETE_ORG_DEPARTMENT)
							.setParameter(1, orgDepartId).executeUpdate();
					Long physical_address_id = item.getPhysical_address_id();
					if (physical_address_id != null) {
						oracleManager.createNativeQuery(Q_DELETE_ADDRESS)
								.setParameter(1, physical_address_id)
								.executeUpdate();
					}
					Long legal_address_id = item.getLegal_address_id();
					if (legal_address_id != null) {
						oracleManager.createNativeQuery(Q_DELETE_ADDRESS)
								.setParameter(1, legal_address_id)
								.executeUpdate();
					}
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Removing Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove Data From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წაშლისას : "
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
	public Organization removeOrganization(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrganizationDMI.removeOrganization.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Long organization_id = new Long(dsRequest.getOldValues()
					.get("organization_id").toString());
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			Map<String, Integer> criteria = new LinkedHashMap<String, Integer>();
			criteria.put("organization_id", organization_id.intValue());
			List<Organization> dataForDelete = DMIUtils.findObjectsdByCriteria(
					"OrgDS", "searchOrgReversed", criteria, Organization.class);

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "Remove Organization.");

			if (dataForDelete != null && !dataForDelete.isEmpty()) {
				for (Organization item : dataForDelete) {
					Long child_organization_id = item.getOrganization_id();

					oracleManager.createNativeQuery(Q_DELETE_ORG_ACTIVITIES)
							.setParameter(1, child_organization_id)
							.executeUpdate();
					oracleManager.createNativeQuery(Q_DELETE_ORG_PART_BANKS)
							.setParameter(1, child_organization_id)
							.executeUpdate();

					List<OrganizationDepartMent> dataForDeleteDeps = DMIUtils
							.findObjectsdByCriteria("OrgDepartmentDS",
									"orgDepartSearchCustom2", criteria,
									OrganizationDepartMent.class);

					if (dataForDeleteDeps != null
							&& !dataForDeleteDeps.isEmpty()) {
						for (OrganizationDepartMent itemDeps : dataForDeleteDeps) {
							Long orgDepartId = itemDeps.getOrg_department_id();
							oracleManager
									.createNativeQuery(Q_DELETE_ORG_DEP_PHONES)
									.setParameter(1, orgDepartId)
									.executeUpdate();
							oracleManager
									.createNativeQuery(Q_DELETE_ORG_DEPARTMENT)
									.setParameter(1, orgDepartId)
									.executeUpdate();
							Long physical_address_id = itemDeps
									.getPhysical_address_id();
							if (physical_address_id != null) {
								oracleManager
										.createNativeQuery(Q_DELETE_ADDRESS)
										.setParameter(1, physical_address_id)
										.executeUpdate();
							}
							Long legal_address_id = itemDeps
									.getLegal_address_id();
							if (legal_address_id != null) {
								oracleManager
										.createNativeQuery(Q_DELETE_ADDRESS)
										.setParameter(1, legal_address_id)
										.executeUpdate();
							}
						}
					}

					oracleManager.createNativeQuery(Q_DELETE_ORGANIZATION)
							.setParameter(1, child_organization_id)
							.executeUpdate();

					Long physical_address_id = item.getPhysical_address_id();
					if (physical_address_id != null) {
						oracleManager.createNativeQuery(Q_DELETE_ADDRESS)
								.setParameter(1, physical_address_id)
								.executeUpdate();
					}
					Long legal_address_id = item.getLegal_address_id();
					if (legal_address_id != null) {
						oracleManager.createNativeQuery(Q_DELETE_ADDRESS)
								.setParameter(1, legal_address_id)
								.executeUpdate();
					}
				}
			}

			EMF.commitTransaction(transaction);
			log += ". Removing Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove Data From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წაშლისას : "
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
	public OrganizationDepartMent updateOrgDepSortOrder(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrganizationDMI.updateOrgDepSortOrder.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			ArrayList orgDepIdList = (ArrayList) dsRequest.getOldValues().get(
					"orgDepIdList");
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "Remove Organization.");
			if (orgDepIdList != null && !orgDepIdList.isEmpty()) {
				int order = 1;
				for (Object org_department_id_o : orgDepIdList) {
					Long org_department_id = new Long(
							org_department_id_o.toString());
					oracleManager.createNativeQuery(Q_UPDATE_ORG_DEP_ORDER)
							.setParameter(1, order)
							.setParameter(2, org_department_id)
							.setParameter(3, order).executeUpdate();
					order++;
				}
			}
			EMF.commitTransaction(transaction);
			log += ". Removing Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove Data From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წაშლისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Organization updateOrgSortOrder(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrganizationDMI.updateOrgSortOrder.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			ArrayList mainIdList = (ArrayList) dsRequest.getOldValues().get(
					"mainIdList");
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "Update Organization Order.");
			if (mainIdList != null && !mainIdList.isEmpty()) {
				int order = 1;
				for (Object org_id_o : mainIdList) {
					Long organization_id = new Long(org_id_o.toString());
					oracleManager.createNativeQuery(Q_UPDATE_ORG_ORDER)
							.setParameter(1, order)
							.setParameter(2, organization_id)
							.setParameter(3, order).executeUpdate();
					order++;
				}
			}
			EMF.commitTransaction(transaction);
			log += ". Removing Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove Data From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წაშლისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Organization updateOrgDepPhoneSortOrder(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrganizationDMI.updateOrgDepPhoneSortOrder.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			ArrayList orgDepPhoneIdList = (ArrayList) dsRequest.getOldValues()
					.get("orgDepPhoneIdList");
			String loggedUserName = dsRequest.getOldValues()
					.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "Update Organization Order.");
			if (orgDepPhoneIdList != null && !orgDepPhoneIdList.isEmpty()) {
				int order = 1;
				for (Object org_dep_phone_id_o : orgDepPhoneIdList) {
					Long org_dep_phone_id = new Long(
							org_dep_phone_id_o.toString());
					oracleManager
							.createNativeQuery(Q_UPDATE_ORG_DEP_PHONE_ORDER)
							.setParameter(1, order)
							.setParameter(2, org_dep_phone_id)
							.setParameter(3, order).executeUpdate();
					order++;
				}
			}
			EMF.commitTransaction(transaction);
			log += ". Removing Finished SuccessFully. ";
			logger.info(log);
			return null;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove Data From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წაშლისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public DSResponse addOrUpdateOrgDepPhone(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrganizationDMI.addOrUpdateOrgDepPhone.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Map<?, ?> values = dsRequest.getValues();
			Long org_dep_to_ph_id = values.containsKey("org_dep_to_ph_id") ? Long
					.parseLong(values.get("org_dep_to_ph_id").toString())
					: null;
			Long phone_number_id = values.containsKey("phone_number_id") ? Long
					.parseLong(values.get("phone_number_id").toString()) : null;
			String loggedUserName = values.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			OrganizationDepartToPhone orgDepartToPhone = null;
			if (org_dep_to_ph_id != null) {
				orgDepartToPhone = oracleManager.find(
						OrganizationDepartToPhone.class, org_dep_to_ph_id);
			} else {
				orgDepartToPhone = new OrganizationDepartToPhone();
			}

			PhoneNumber phoneNumber = null;
			if (phone_number_id != null) {
				phoneNumber = oracleManager.find(PhoneNumber.class,
						phone_number_id);
			} else {
				phoneNumber = new PhoneNumber();
			}

			DataTools.setProperties(values, phoneNumber);
			String phone = phoneNumber.getPhone();

			ArrayList<PhoneNumber> listPhones = (ArrayList<PhoneNumber>) oracleManager
					.createNamedQuery("PhoneNumber.getByPhoneNumber")
					.setParameter("phone", phone).getResultList();

			PhoneNumber phoneNumberByNum = (listPhones != null && !listPhones
					.isEmpty()) ? listPhones.get(0) : null;

			if (phoneNumberByNum != null && !phoneNumberByNum.getPhone_number_id().equals(phone_number_id)) {
				Long id_tmp = phoneNumberByNum.getPhone_number_id();
				phoneNumber = phoneNumberByNum;
				DataTools.setProperties(values, phoneNumber);
				phoneNumber.setPhone_number_id(id_tmp);
			}

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "OrgDepPhone Changes.");
			if (phoneNumber.getPhone_number_id() == null) {
				oracleManager.persist(phoneNumber);
			} else {
				oracleManager.merge(phoneNumber);
			}
			DataTools.setProperties(values, orgDepartToPhone);
			orgDepartToPhone.setPhone_number_id(phoneNumber
					.getPhone_number_id());
			orgDepartToPhone.setRec_upd_date(recDate);
			if (orgDepartToPhone.getOrg_dep_to_ph_id() == null) {
				oracleManager.persist(orgDepartToPhone);
			} else {
				oracleManager.merge(orgDepartToPhone);
			}

			org_dep_to_ph_id = orgDepartToPhone.getOrg_dep_to_ph_id();
			EMF.commitTransaction(transaction);
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);

			values = DMIUtils.findRecordById("OrgDepPhoneDS",
					"searchOrgDepPhones", org_dep_to_ph_id, "org_dep_to_ph_id");
			DSResponse dsResponse = new DSResponse();
			dsResponse.setData(values);
			//dsResponse.setInvalidateCache(true);
			return dsResponse;
		} catch (Exception e) {
			if (transaction != null) {
				EMF.rollbackTransaction(transaction);
			}
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Update Data Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წაშლისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	public void removeOrgDepPhone(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:OrganizationDMI.removeOrgDepPhone.";
			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);
			Map<?, ?> values = dsRequest.getOldValues();
			Long org_dep_to_ph_id = Long.parseLong(values.get(
					"org_dep_to_ph_id").toString());
			String loggedUserName = values.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			OrganizationDepartToPhone orgDepartToPhone = oracleManager.find(
					OrganizationDepartToPhone.class, org_dep_to_ph_id);
			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, "OrgDepPhone Remove.");
			oracleManager.remove(orgDepartToPhone);

			EMF.commitTransaction(transaction);
			log += ". Removing Finished SuccessFully. ";
			logger.info(log);
		} catch (Exception e) {
			if (transaction != null) {
				EMF.rollbackTransaction(transaction);
			}
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Remove Data From Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წაშლისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
