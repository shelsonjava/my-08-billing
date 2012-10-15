package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.Staff;
import com.info08.billing.callcenterbk.shared.entity.StaffComputerSkill;
import com.info08.billing.callcenterbk.shared.entity.StaffEducation;
import com.info08.billing.callcenterbk.shared.entity.StaffFamousPeople;
import com.info08.billing.callcenterbk.shared.entity.StaffLanguages;
import com.info08.billing.callcenterbk.shared.entity.StaffPhones;
import com.info08.billing.callcenterbk.shared.entity.StaffRelative;
import com.info08.billing.callcenterbk.shared.entity.StaffWorks;
import com.info08.billing.callcenterbk.shared.items.Address;
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
	@SuppressWarnings("unchecked")
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

			/************************************************************************************/
			// Address physicalAddress
			Long address_id = values.get("address_id") != null ? new Long(
					values.get("address_id").toString()) : null;
			Map<?, ?> physicalAddressMap = (Map<?, ?>) values
					.get("physicalAddress");

			Address physicalAddress = null;
			if (address_id != null) {
				physicalAddress = oracleManager.find(Address.class, address_id);
				if (physicalAddress == null) {
					physicalAddress = new Address();
				}
			} else {
				physicalAddress = new Address();
			}

			// {PhysicalAddress_town_id=51063,
			// PhysicalAddress_legalAdressOpCloseItem=0,
			// PhysicalAddress_street_id=53046,
			// PhysicalAddress_town_district_id=50005,
			// PhysicalAddress_streetDescrItem=მდებარეობს მოსკოვის გამზირიდან,
			// (ყოფილ უნივერმაღ "მოსკოვის" უკან). მეტრო "სამგორთან".,
			// PhysicalAddress_street_index=0137 : ყველა; ,
			// PhysicalAddress_legalAdressItem=12,
			// PhysicalAddress_appartItem=12,
			// PhysicalAddress_legalBlockItem=12}

			// physicalAddress.set

			// physicalAddress.set

			DataTools.setProperties(physicalAddressMap, physicalAddress);
			Object strId = physicalAddressMap.get("streets_id");
			if (strId != null) {
				physicalAddress.setStreet_id(new Long(strId.toString()));
			}

			if (physicalAddress.getStreet_id() != null) {

				if (physicalAddress.getAddr_id() != null) {
					oracleManager.merge(physicalAddress);
				} else {
					oracleManager.persist(physicalAddress);
				}
			}

			/************************************************************************************/

			/************************************************************************************/
			// Address physicalAddress
			Long legalAddress_id = values.get("document_address_id") != null ? new Long(
					values.get("document_address_id").toString()) : null;
			Map<?, ?> legalAddressMap = (Map<?, ?>) values.get("legalAddress");

			Address legalAddress = null;
			if (legalAddress_id != null) {
				legalAddress = oracleManager.find(Address.class,
						legalAddress_id);
				if (legalAddress == null) {
					legalAddress = new Address();
				}
			} else {
				legalAddress = new Address();
			}

			// {PhysicalAddress_town_id=51063,
			// PhysicalAddress_legalAdressOpCloseItem=0,
			// PhysicalAddress_street_id=53046,
			// PhysicalAddress_town_district_id=50005,
			// PhysicalAddress_streetDescrItem=მდებარეობს მოსკოვის გამზირიდან,
			// (ყოფილ უნივერმაღ "მოსკოვის" უკან). მეტრო "სამგორთან".,
			// PhysicalAddress_street_index=0137 : ყველა; ,
			// PhysicalAddress_legalAdressItem=12,
			// PhysicalAddress_appartItem=12,
			// PhysicalAddress_legalBlockItem=12}

			// physicalAddress.set

			// physicalAddress.set

			DataTools.setProperties(legalAddressMap, legalAddress);

			Object legstrId = legalAddressMap.get("streets_id");
			if (legstrId != null) {
				legalAddress.setStreet_id(new Long(legstrId.toString()));
			}

			if (legalAddress.getStreet_id() != null) {
				if (legalAddress.getAddr_id() != null) {
					oracleManager.merge(legalAddress);
				} else {
					oracleManager.persist(legalAddress);
				}
			}

			/************************************************************************************/
			if (physicalAddress.getAddr_id() != null) {
				staff.setAddress_id(physicalAddress.getAddr_id());
			}

			if (legalAddress.getAddr_id() != null) {
				staff.setDocument_address_id(legalAddress.getAddr_id());
			}

			if (staff_id != null) {
				oracleManager.merge(staff);
			} else {
				oracleManager.persist(staff);
			}

			staff_id = staff.getStaff_id();
			Map<String, Map<String, String>> staffEducation = new TreeMap<String, Map<String, String>>();
			staffEducation = (Map<String, Map<String, String>>) values
					.get("preStaffEducation");

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_STAFF_EDUCATION)
					.setParameter(1, staff_id).executeUpdate();

			if (staffEducation != null) {
				Set<String> keys = staffEducation.keySet();
				if (keys != null) {
					for (String key : keys) {
						Map<String, String> item = staffEducation.get(key);
						if (item != null) {
							StaffEducation education = new StaffEducation();
							education.setStaff_id(staff_id);
							education.setLoggedUserName(loggedUserName);
							education.setCollege_name(item.get("college_name"));
							education.setFaculty_name(item.get("faculty_name"));
							education.setDegree_descr_id(new Long(item
									.get("degree_descr_id")));
							education
									.setStart_year(item.get("start_year") == null ? null
											: new Long(item.get("start_year")));
							education
									.setEnd_year(item.get("end_year") == null ? null
											: new Long(item.get("end_year")));

							oracleManager.persist(education);

						}
					}
				}
			}

			/********************************************************************************/

			Map<String, Map<String, String>> staffComputerSkills = new TreeMap<String, Map<String, String>>();
			staffComputerSkills = (Map<String, Map<String, String>>) values
					.get("preStaffCompSkills");

			oracleManager
					.createNativeQuery(
							QueryConstants.Q_DELETE_STAFF_COMPUTER_SKILLS)
					.setParameter(1, staff_id).executeUpdate();

			if (staffComputerSkills != null) {
				Set<String> keys = staffComputerSkills.keySet();
				if (keys != null) {
					for (String key : keys) {
						Map<String, String> item = staffComputerSkills.get(key);
						if (item != null) {
							StaffComputerSkill computerSkill = new StaffComputerSkill();
							computerSkill.setStaff_id(staff_id);
							computerSkill.setLoggedUserName(loggedUserName);
							computerSkill.setSoftware(item.get("software"));
							computerSkill.setTraining_course(item
									.get("training_course"));
							computerSkill.setRemark(item.get("remark"));

							oracleManager.persist(computerSkill);

						}
					}
				}
			}

			/************************************************************************************/

			/************************************************************************************/

			Map<String, Map<String, String>> staffLanguages = new TreeMap<String, Map<String, String>>();
			staffLanguages = (Map<String, Map<String, String>>) values
					.get("preStaffLanguages");

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_STAFF_LANGUAGES)
					.setParameter(1, staff_id).executeUpdate();

			if (staffLanguages != null) {
				Set<String> keys = staffLanguages.keySet();
				if (keys != null) {
					for (String key : keys) {
						Map<String, String> item = staffLanguages.get(key);
						if (item != null) {
							StaffLanguages staffLanguage = new StaffLanguages();
							staffLanguage.setStaff_id(staff_id);
							staffLanguage.setLoggedUserName(loggedUserName);
							staffLanguage.setLanguage_descr_id(new Long(item
									.get("language_descr_id")));
							staffLanguage.setLanguage_level_descr_id(new Long(
									item.get("language_level_descr_id")));
							staffLanguage.setCertificate_descr(item
									.get("certificate_descr"));

							oracleManager.persist(staffLanguage);

						}
					}
				}
			}

			/************************************************************************************/

			/************************************************************************************/

			Map<String, Map<String, String>> staffPhones = new TreeMap<String, Map<String, String>>();
			staffPhones = (Map<String, Map<String, String>>) values
					.get("preStaffPhones");

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_STAFF_PHONES)
					.setParameter(1, staff_id).executeUpdate();

			if (staffPhones != null) {
				Set<String> keys = staffPhones.keySet();
				if (keys != null) {
					for (String key : keys) {
						Map<String, String> item = staffPhones.get(key);
						if (item != null) {
							StaffPhones staffPhone = new StaffPhones();
							staffPhone.setStaff_id(staff_id);
							staffPhone.setLoggedUserName(loggedUserName);
							staffPhone.setStaff_phone_type_id(new Long(item
									.get("staff_phone_type_id")));
							staffPhone.setStaff_phone(item.get("staff_phone"));

							oracleManager.persist(staffPhone);

						}
					}
				}
			}

			/************************************************************************************/

			/************************************************************************************/

			Map<String, Map<String, String>> staffWorks = new TreeMap<String, Map<String, String>>();
			staffWorks = (Map<String, Map<String, String>>) values
					.get("preStaffWorks");

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_STAFF_WORKS)
					.setParameter(1, staff_id).executeUpdate();

			if (staffWorks != null) {
				Set<String> keys = staffWorks.keySet();
				if (keys != null) {
					for (String key : keys) {
						Map<?, ?> item = staffWorks.get(key);
						if (item != null) {
							StaffWorks staffWork = new StaffWorks();
							staffWork.setStaff_id(staff_id);
							staffWork.setLoggedUserName(loggedUserName);
							staffWork.setWork_place(item.get("work_place")
									.toString());
							staffWork.setWork_position(item
									.get("work_position").toString());

							Date a_start = (Date) item.get("work_start_date");
							Date a_end = (Date) item.get("work_end_date");

							if (a_start != null) {
								staffWork.setWork_start_date(new Timestamp(
										a_start.getTime()));
							}
							if (a_end != null) {
								staffWork.setWork_end_date(new Timestamp(a_end
										.getTime()));
							}

							oracleManager.persist(staffWork);

						}
					}
				}
			}

			/************************************************************************************/

			Map<String, Map<String, String>> staffRelative = new TreeMap<String, Map<String, String>>();
			staffRelative = (Map<String, Map<String, String>>) values
					.get("preStaffRaltive");

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_STAFF_RELATIVE)
					.setParameter(1, staff_id).executeUpdate();

			if (staffRelative != null) {
				Set<String> keys = staffRelative.keySet();
				if (keys != null) {
					for (String key : keys) {
						Map<String, String> item = staffRelative.get(key);
						if (item != null) {
							StaffRelative staffRelativeItem = new StaffRelative();
							staffRelativeItem.setStaff_id(staff_id);
							staffRelativeItem.setLoggedUserName(loggedUserName);
							staffRelativeItem.setFirst_name(item
									.get("first_name"));
							staffRelativeItem.setLast_name(item
									.get("last_name"));
							staffRelativeItem.setPosition(item.get("position"));

							oracleManager.persist(staffRelativeItem);

						}
					}
				}
			}

			/************************************************************************************/

			/************************************************************************************/

			Map<String, Map<String, String>> staffFamousPeople = new TreeMap<String, Map<String, String>>();
			staffFamousPeople = (Map<String, Map<String, String>>) values
					.get("preStaffFamousPeople");

			oracleManager
					.createNativeQuery(
							QueryConstants.Q_DELETE_STAFF_FAMOUS_PEOPLE)
					.setParameter(1, staff_id).executeUpdate();

			if (staffFamousPeople != null) {
				Set<String> keys = staffFamousPeople.keySet();
				if (keys != null) {
					for (String key : keys) {
						Map<String, String> item = staffFamousPeople.get(key);
						if (item != null) {
							StaffFamousPeople staffFamousPeopleItem = new StaffFamousPeople();
							staffFamousPeopleItem.setStaff_id(staff_id);
							staffFamousPeopleItem
									.setLoggedUserName(loggedUserName);
							staffFamousPeopleItem.setFirst_name(item
									.get("first_name"));
							staffFamousPeopleItem.setLast_name(item
									.get("last_name"));
							staffFamousPeopleItem.setRelation_type_id(new Long(
									item.get("relation_type_id")));
							staffFamousPeopleItem
									.setSphere_of_activity_id(new Long(item
											.get("sphere_of_activity_id")));

							oracleManager.persist(staffFamousPeopleItem);

						}
					}
				}
			}

			/************************************************************************************/

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

			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_STAFF_EDUCATION)
					.setParameter(1, staff_id).executeUpdate();
			oracleManager
					.createNativeQuery(
							QueryConstants.Q_DELETE_STAFF_COMPUTER_SKILLS)
					.setParameter(1, staff_id).executeUpdate();
			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_STAFF_LANGUAGES)
					.setParameter(1, staff_id).executeUpdate();
			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_STAFF_PHONES)
					.setParameter(1, staff_id).executeUpdate();
			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_STAFF_WORKS)
					.setParameter(1, staff_id).executeUpdate();
			oracleManager
					.createNativeQuery(QueryConstants.Q_DELETE_STAFF_RELATIVE)
					.setParameter(1, staff_id).executeUpdate();
			oracleManager
					.createNativeQuery(
							QueryConstants.Q_DELETE_STAFF_FAMOUS_PEOPLE)
					.setParameter(1, staff_id).executeUpdate();

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
