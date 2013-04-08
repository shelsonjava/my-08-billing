package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.correction.CorrUsrStat;
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
	private static final String Q_SELECT_ORG_ACTIVITIES = "select t.org_activity_id from organization_to_activities t where t.organization_id = ?  order by t.org_activity_id";
	private static final String Q_DELETE_ORG_PART_BANKS = "delete from organization_partner_banks t where t.organization_id = ? ";
	private static final String Q_SELECT_ORG_PART_BANKS = "select t.part_bank_org_id from organization_partner_banks t where t.organization_id = ? order by t.part_bank_org_id";
	private static final String Q_DELETE_ORG_DEP_PHONES = "delete from organization_depart_to_phones t where t.org_department_id = ? ";
	private static final String Q_DELETE_ORG_DEPARTMENT = "delete from organization_department t where t.org_department_id = ? ";
	private static final String Q_DELETE_ADDRESS = "delete from addresses a where a.addr_id = ? ";
	private static final String Q_DELETE_ORGANIZATION = "delete from organizations t where t.organization_id = ? ";
	private static final String Q_UPDATE_ORG_DEP_ORDER = " update organization_department t set t.inner_order = ? where t.org_department_id = ? and t.inner_order <> ? ";
	private static final String Q_UPDATE_ORG_ORDER = " update organizations t set t.priority = ? where t.organization_id = ? and t.priority <> ? ";
	private static final String Q_UPDATE_ORG_DEP_PHONE_ORDER = " update organization_depart_to_phones t set t.phone_order = ? where t.org_dep_to_ph_id = ? and t.phone_order <> ? ";
	private static final String Q_SELECT_ORG_ADDRESS = "select nvl(t.concat_address_with_town,'') from addresses t where t.addr_id = ? ";

	private Logger logger = Logger.getLogger(OrganizationDMI.class.getName());
	private static final SimpleDateFormat dateFormatMMYYY = new SimpleDateFormat("MMyy");

	/**
	 * Adding New OrganizationActivity
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public OrganizationActivity addOrgActivity(OrganizationActivity organizationActivity) throws Exception {
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
	 * 
	 * @param dsRequest
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
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
			Map oldValues = null;
			boolean isNewOrg = false;
			if (organization_id != null) {
				organization = oracleManager.find(Organization.class, organization_id);
				oldValues = DataTools.getProperties(organization);				
			}
			if (organization == null && organization_id != null) {
				throw new Exception("ვერ ვიპოვე ორგანიზაცია შესაცვლელად(ID="+ organization_id + ")");
			}
			Long super_priority = null;
			if (organization == null) {
				organization = new Organization();
				isNewOrg = true;
			} else {
				super_priority = organization.getSuper_priority();
			}

			DataTools.setProperties(values, organization);
			if (super_priority != null && Math.abs(super_priority.longValue()) > 1) {
				organization.setSuper_priority(super_priority);
			}

			RCNGenerator.getInstance().initRcn(oracleManager, recDate, loggedUserName, log);

			Long legal_address_id = values.get("legal_address_id") != null ? new Long(values.get("legal_address_id").toString()) : null;
			Long physical_address_id = values.get("physical_address_id") != null ? new Long(values.get("physical_address_id").toString()) : null;
			
			Map<?, ?> legalAddrValues = (Map<?, ?>) values.get("legalAddrValues");			
			
			boolean deleteLegalAddress = legal_address_id != null && legalAddrValues == null;
			boolean isAddressChanged = false;
			if (legalAddrValues != null) {
				Address legAddress = persistAddress(oracleManager, values, legal_address_id, "legalAddrValues");
				legal_address_id = legAddress.getAddr_id();
				if(legAddress.isAddrChanched()){
					isAddressChanged = true;
				}
			}
			
			Address physical_address = persistAddress(oracleManager, values, physical_address_id, "physicalAddrValues");
			if(physical_address.isAddrChanched() && !isAddressChanged){
				isAddressChanged = true;
			}
			physical_address_id = physical_address.getAddr_id();

			organization.setLegal_address_id(deleteLegalAddress ? null : legal_address_id);
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

			Integer old_activities[] = new Integer[0];
			if (!isPersist) {

				List resultlist = oracleManager
						.createNativeQuery(Q_SELECT_ORG_ACTIVITIES)
						.setParameter(1, organization_id).getResultList();
				if (resultlist != null && !resultlist.isEmpty()) {
					old_activities = new Integer[resultlist.size()];
					for (int i = 0; i < resultlist.size(); i++) {
						Object object = resultlist.get(i);
						old_activities[i] = new Integer(object.toString());
					}
				}
				oracleManager.createNativeQuery(Q_DELETE_ORG_ACTIVITIES)
						.setParameter(1, organization_id).executeUpdate();
				oracleManager.flush();
			}

			Map<?, ?> orgActivities = (Map<?, ?>) values.get("orgActivities");
			Set<?> orgActKeys = orgActivities.keySet();
			SortedSet<Integer> sortedSet = new TreeSet<Integer>();
			for (Object orgActKey : orgActKeys) {
				Object value = orgActivities.get(orgActKey);
				OrganizationToActivity organizationToActivity = new OrganizationToActivity();
				organizationToActivity.setOrganization_id(organization_id);
				Long actId = new Long(value.toString());
				organizationToActivity.setOrg_activity_id(actId);
				sortedSet.add(new Integer(actId.intValue()));
				oracleManager.persist(organizationToActivity);
			}
			Integer new_activities[] = sortedSet.toArray(new Integer[]{});			
			
			Integer old_part_banks[] = new Integer[0];
			
			if (!isPersist) {
				
				List resultlist = oracleManager
						.createNativeQuery(Q_SELECT_ORG_PART_BANKS)
						.setParameter(1, organization_id).getResultList();
				if (resultlist != null && !resultlist.isEmpty()) {
					old_part_banks = new Integer[resultlist.size()];
					for (int i = 0; i < resultlist.size(); i++) {
						Object object = resultlist.get(i);
						old_part_banks[i] = new Integer(object.toString());
					}
				}
				oracleManager.createNativeQuery(Q_DELETE_ORG_PART_BANKS)
						.setParameter(1, organization_id).executeUpdate();
				oracleManager.flush();
			}

			Map<?, ?> orgPartnerBanks = (Map<?, ?>) values.get("orgPartnerBanks");
			SortedSet<Integer> sortedSetPB = new TreeSet<Integer>();
			if (orgPartnerBanks != null) {
				Set<?> orgPartnerBankKeys = orgPartnerBanks.keySet();
				for (Object partBankKey : orgPartnerBankKeys) {
					Object value = orgPartnerBanks.get(partBankKey);
					OrganizationPartnerBank organizationPartnerBank = new OrganizationPartnerBank();
					organizationPartnerBank.setOrganization_id(organization_id);
					organizationPartnerBank.setPart_bank_org_id(new Long(value.toString()));
					sortedSetPB.add(new Integer(value.toString()));
					oracleManager.persist(organizationPartnerBank);
				}
			}
			Integer new_part_banks[] = sortedSetPB.toArray(new Integer[]{});
			
			
			if (isNewOrg) {
				saveOrgActionHistNewOrDelOrg(true, loggedUserName,
						oracleManager);
			} else {
				saveOrgActionHistUpdateOrg(loggedUserName, oracleManager,
						dsRequest, oldValues, old_activities, new_activities, old_part_banks, new_part_banks, isAddressChanged);
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
			// dsResponse.setInvalidateCache(true);
			return dsResponse;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
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
		Object ostreets_id = addrValues.get("streets_id");
		if (ostreets_id != null) {
			address.setStreet_id(new Long(ostreets_id.toString()));
		}
		address.setAddr_id(address_id);
		boolean changed = false;
		String oldFullAdd = null;
		String newFullAdd = null;
		if (address.getAddr_id() != null) {
			Object oldObject = oracleManager.createNativeQuery(Q_SELECT_ORG_ADDRESS).setParameter(1, address.getAddr_id()).getSingleResult();
			oldFullAdd = oldObject == null ? "" : oldObject.toString();			
			oracleManager.merge(address);			
		} else {
			changed = true;
			oracleManager.persist(address);
		}
		if(!changed){
			oracleManager.flush();
			Object newObject = oracleManager.createNativeQuery(Q_SELECT_ORG_ADDRESS).setParameter(1, address.getAddr_id()).getSingleResult();
			newFullAdd = newObject == null ? "" : newObject.toString();
			changed = !oldFullAdd.equals(newFullAdd);
		}
		address.setAddrChanched(changed);
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
			if (organizationDepartMent.getInner_order() == null) {
				organizationDepartMent.setInner_order(0L);
			}

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);

			Map<?, ?> physicalAddrValues = (Map<?, ?>) values
					.get("physicalAddrValues");
			Long physical_address_id = values.get("physical_address_id") != null ? new Long(
					values.get("physical_address_id").toString()) : null;
			boolean deletePhysicalAddress = physical_address_id != null && physicalAddrValues == null;

			if (physicalAddrValues != null) {
				physical_address_id = persistAddress(oracleManager, values, physical_address_id, "physicalAddrValues").getAddr_id();
			}

			organizationDepartMent.setPhysical_address_id(deletePhysicalAddress ? null : physical_address_id);

			if (org_department_id == null) {
				oracleManager.persist(organizationDepartMent);
			} else {
				oracleManager.merge(organizationDepartMent);
			}
			org_department_id = organizationDepartMent.getOrg_department_id();

			if (deletePhysicalAddress) {
				oracleManager.remove(oracleManager.find(Address.class, physical_address_id));
			}

			oracleManager.flush();

			oracleManager.createNativeQuery("{call createOrgDepartmentHist(?)}").setParameter(1, org_department_id).executeUpdate();
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

			saveOrgActionHistNewOrDelOrg(false, loggedUserName, oracleManager);

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

	private void saveOrgActionHistNewOrDelOrg(boolean isNewOrg, String user,
			EntityManager oracleManager) throws CallCenterException {
		try {
			CorrUsrStat corrUsrStat = new CorrUsrStat();
			corrUsrStat.setAct_date(new Timestamp(System.currentTimeMillis()));
			corrUsrStat.setUser_name(user);
			corrUsrStat.setMmyy(Long.parseLong(dateFormatMMYYY.format(new Date(
					System.currentTimeMillis()))));
			if (isNewOrg) {
				corrUsrStat.setNew_org(1L);
				corrUsrStat.setDel_org(0L);
			} else {
				corrUsrStat.setNew_org(0L);
				corrUsrStat.setDel_org(1L);
			}
			corrUsrStat.setAddress(0L);
			corrUsrStat.setDel_phone(0L);
			corrUsrStat.setDel_subs(0L);
			corrUsrStat.setDirector(0L);
			corrUsrStat.setEmail(0L);
			corrUsrStat.setFounded_date(0L);
			corrUsrStat.setIdent_code(0L);
			corrUsrStat.setNew_phone(0L);
			corrUsrStat.setNew_subs(0L);
			corrUsrStat.setOrg_comment(0L);
			corrUsrStat.setOther(0L);
			corrUsrStat.setPart_bank(0L);
			corrUsrStat.setPhone_upd(0L);
			corrUsrStat.setSoc_network(0L);
			corrUsrStat.setUpdate_subs(0L);
			corrUsrStat.setWeb_site(0L);
			corrUsrStat.setWork_hour_dayy_off(0L);

			oracleManager.persist(corrUsrStat);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error saveOrgActionHistNewOrg : ", e);
			throw new CallCenterException("saveOrgActionHistNewOrg : "
					+ e.toString());
		}
	}

	@SuppressWarnings("rawtypes")
	private void saveOrgActionHistUpdateOrg(String user,
			EntityManager oracleManager, DSRequest dsRequest, Map oldValues, 
			Integer [] old_activities, Integer [] new_activities,
			Integer old_part_banks[],Integer new_part_banks[], boolean isAddressChanged)
			throws CallCenterException {
		try {
			Map<?, ?> values = dsRequest.getValues();

			CorrUsrStat corrUsrStat = new CorrUsrStat();
			corrUsrStat.setAct_date(new Timestamp(System.currentTimeMillis()));
			corrUsrStat.setUser_name(user);
			corrUsrStat.setMmyy(Long.parseLong(dateFormatMMYYY.format(new Date(System.currentTimeMillis()))));
			corrUsrStat.setNew_org(0L);
			corrUsrStat.setDel_org(0L);
			corrUsrStat.setDel_phone(0L);
			corrUsrStat.setDel_subs(0L);
			corrUsrStat.setNew_phone(0L);
			corrUsrStat.setNew_subs(0L);
			corrUsrStat.setPhone_upd(0L);
			corrUsrStat.setUpdate_subs(0L);
			corrUsrStat.setDirector( (!(values.get("chief")==null?"":values.get("chief").toString()).equals(oldValues.get("chief")==null?"":oldValues.get("chief").toString())) ? 1L : 0L);			 
			corrUsrStat.setEmail( (!(values.get("email_address")==null?"":values.get("email_address").toString()).equals(oldValues.get("email_address")==null?"":oldValues.get("email_address").toString())) ? 1L : 0L);
			corrUsrStat.setOrg_comment( (!(values.get("remark")==null?"":values.get("remark").toString()).equals(oldValues.get("remark")==null?"":oldValues.get("remark").toString())) ? 1L : 0L);			
			corrUsrStat.setSoc_network( (!(values.get("social_address")==null?"":values.get("social_address").toString()).equals(oldValues.get("social_address")==null?"":oldValues.get("social_address").toString())) ? 1L : 0L);
			corrUsrStat.setWeb_site( (!(values.get("web_address")==null?"":values.get("web_address").toString()).equals(oldValues.get("web_address")==null?"":oldValues.get("web_address").toString())) ? 1L : 0L);			
			corrUsrStat.setIdent_code((
										(!(values.get("ident_code")==null?"":values.get("ident_code").toString()).equals(oldValues.get("ident_code")==null?"":oldValues.get("ident_code").toString())) ||
										(!(values.get("ident_code_new")==null?"":values.get("ident_code_new").toString()).equals(oldValues.get("ident_code_new")==null?"":oldValues.get("ident_code_new").toString()))
									  ) ? 1L : 0L);
			corrUsrStat.setWork_hour_dayy_off((
										(!(values.get("work_hours")==null?"":values.get("work_hours").toString()).equals(oldValues.get("work_hours")==null?"":oldValues.get("work_hours").toString())) ||
										(!(values.get("day_offs")==null?"":values.get("day_offs").toString()).equals(oldValues.get("day_offs")==null?"":oldValues.get("day_offs").toString()))
									  ) ? 1L : 0L);

			Long fd = values.get("found_date")==null?0L:Long.valueOf(((Date)values.get("found_date")).getTime());
			Long fd1 =oldValues.get("found_date")==null?0L:Long.valueOf(((Date)oldValues.get("found_date")).getTime());			
			corrUsrStat.setFounded_date( !fd.equals(fd1) ? 1L : 0L);
			
			Long cnt = 0L;
			String sc = values.get("staff_count")==null?"":values.get("staff_count").toString();
			String sc1 = oldValues.get("staff_count")==null?"":oldValues.get("staff_count").toString();
			cnt = cnt.longValue()+ ( !sc.equals(sc1) ? 1 : 0);
			
			
			String oi = values.get("organization_index")==null?"":values.get("organization_index").toString();
			String oi1 = oldValues.get("organization_index")==null?"":oldValues.get("organization_index").toString();
			cnt = cnt.longValue()+ ( !oi.equals(oi1) ? 1 : 0);
			
			String ai = values.get("additional_info")==null?"":values.get("additional_info").toString();
			String ai1 = oldValues.get("additional_info")==null?"":oldValues.get("additional_info").toString();
			cnt = cnt.longValue()+ ( !ai.equals(ai1) ? 1 : 0);
			
			cnt = cnt.longValue()+ ( !Arrays.equals(old_activities, new_activities) ? 1 : 0);
			corrUsrStat.setOther(cnt);			
			corrUsrStat.setPart_bank( !Arrays.equals(old_part_banks, new_part_banks) ? 1L : 0L);
			
			
			corrUsrStat.setAddress( !isAddressChanged ? 0L : 1L);
			
			if(corrUsrStat.getDirector().equals(0L) &&
			   corrUsrStat.getEmail().equals(0L) &&
			   corrUsrStat.getOrg_comment().equals(0L) &&
			   corrUsrStat.getSoc_network().equals(0L) &&
			   corrUsrStat.getWeb_site().equals(0L) &&
			   corrUsrStat.getIdent_code().equals(0L) &&
			   corrUsrStat.getWork_hour_dayy_off().equals(0L) &&
			   corrUsrStat.getFounded_date().equals(0L) &&
			   corrUsrStat.getOther().equals(0L) &&
			   corrUsrStat.getAddress().equals(0L) &&
			   corrUsrStat.getPart_bank().equals(0L)){
				return;
			}
			
			oracleManager.persist(corrUsrStat);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error saveOrgActionHistNewOrg : ", e);
			throw new CallCenterException("saveOrgActionHistNewOrg : "
					+ e.toString());
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

			if (phoneNumberByNum != null
					&& !phoneNumberByNum.getPhone_number_id().equals(
							phone_number_id)) {
				Long id_tmp = phoneNumberByNum.getPhone_number_id();
				phoneNumber = phoneNumberByNum;
				DataTools.setProperties(values, phoneNumber);
				phoneNumber.setPhone_number_id(id_tmp);
			}

			RCNGenerator.getInstance().initRcn(oracleManager, recDate, loggedUserName, "OrgDepPhone Changes.");
			
			boolean isNewPhone = false;
			
			if (phoneNumber.getPhone_number_id() == null) {
				oracleManager.persist(phoneNumber);
				isNewPhone = true;
			} else {
				oracleManager.createNativeQuery(QueryConstants.Q_UPDATE_PHONE)
						.setParameter(1, phoneNumber.getPhone())
						.setParameter(2, phoneNumber.getPhone_state_id())
						.setParameter(3, phoneNumber.getPhone_type_id())
						.setParameter(4, phoneNumber.getIs_parallel())
						.setParameter(5, phoneNumber.getPhone_number_id())
						.executeUpdate();
			}
			DataTools.setProperties(values, orgDepartToPhone);
			orgDepartToPhone.setPhone_number_id(phoneNumber.getPhone_number_id());
			orgDepartToPhone.setRec_upd_date(recDate);
			if (orgDepartToPhone.getOrg_dep_to_ph_id() == null) {
				oracleManager.persist(orgDepartToPhone);
				isNewPhone = true;
			} else {
				oracleManager
						.createNativeQuery(QueryConstants.Q_UPDATEORG_DEP_PHONE)
						.setParameter(1,
								orgDepartToPhone.getOrg_department_id())
						.setParameter(2, orgDepartToPhone.getPhone_number_id())
						.setParameter(3,
								orgDepartToPhone.getHidden_by_request())
						.setParameter(4,
								orgDepartToPhone.getPhone_contract_type())
						.setParameter(5, orgDepartToPhone.getFor_contact())
						.setParameter(6, orgDepartToPhone.getPhone_order())
						.setParameter(7, orgDepartToPhone.getRec_upd_date())
						.setParameter(8, orgDepartToPhone.getOrg_dep_to_ph_id())
						.executeUpdate();
			}
			org_dep_to_ph_id = orgDepartToPhone.getOrg_dep_to_ph_id();
			EMF.commitTransaction(transaction);
			
			saveOrgActionHistDelorAddOrUpdatePhone(isNewPhone, false, loggedUserName, oracleManager);
			
			log += ". Updating Finished SuccessFully. ";
			logger.info(log);

			values = DMIUtils.findRecordById("OrgDepPhoneDS",
					"searchOrgDepPhones", org_dep_to_ph_id, "org_dep_to_ph_id");
			DSResponse dsResponse = new DSResponse();
			dsResponse.setData(values);
			// dsResponse.setInvalidateCache(true);
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
			saveOrgActionHistDelorAddOrUpdatePhone(false, true, loggedUserName, oracleManager);

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
	
	
	private void saveOrgActionHistDelorAddOrUpdatePhone(boolean isNewPhone,boolean isDelete, String user,EntityManager oracleManager) throws CallCenterException {
		try {
			CorrUsrStat corrUsrStat = new CorrUsrStat();
			corrUsrStat.setAct_date(new Timestamp(System.currentTimeMillis()));
			corrUsrStat.setUser_name(user);
			corrUsrStat.setMmyy(Long.parseLong(dateFormatMMYYY.format(new Date(
					System.currentTimeMillis()))));
			corrUsrStat.setNew_org(0L);
			corrUsrStat.setDel_org(0L);			
			corrUsrStat.setAddress(0L);
			
			if(isDelete){
				corrUsrStat.setNew_phone(0L);
				corrUsrStat.setPhone_upd(0L);
				corrUsrStat.setDel_phone(1L);
			}else {
				if (isNewPhone) {
					corrUsrStat.setNew_phone(1L);
					corrUsrStat.setPhone_upd(0L);
					corrUsrStat.setDel_phone(0L);
				}else{
					corrUsrStat.setNew_phone(0L);
					corrUsrStat.setPhone_upd(1L);
					corrUsrStat.setDel_phone(0L);
				}
			}
						
			corrUsrStat.setDel_subs(0L);
			corrUsrStat.setDirector(0L);
			corrUsrStat.setEmail(0L);
			corrUsrStat.setFounded_date(0L);
			corrUsrStat.setIdent_code(0L);
			
			corrUsrStat.setNew_subs(0L);
			corrUsrStat.setOrg_comment(0L);
			corrUsrStat.setOther(0L);
			corrUsrStat.setPart_bank(0L);
			
			corrUsrStat.setSoc_network(0L);
			corrUsrStat.setUpdate_subs(0L);
			corrUsrStat.setWeb_site(0L);
			corrUsrStat.setWork_hour_dayy_off(0L);
			oracleManager.persist(corrUsrStat);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error saveOrgActionHistNewOrg : ", e);
			throw new CallCenterException("saveOrgActionHistNewOrg : "
					+ e.toString());
		}
	}
	
}
