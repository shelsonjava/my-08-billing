package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.entity.correction.CorrUsrStat;
import com.info08.billing.callcenterbk.shared.items.Address;
import com.info08.billing.callcenterbk.shared.items.PhoneNumber;
import com.info08.billing.callcenterbk.shared.items.Subscribers;
import com.info08.billing.callcenterbk.shared.items.SubscribersToPhones;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;
import com.isomorphic.util.DataTools;

public class SubscriberDMI implements QueryConstants {

	Logger logger = Logger.getLogger(SubscriberDMI.class.getName());
	private static final SimpleDateFormat dateFormatMMYYY = new SimpleDateFormat("MMyy");
	
	private static final String Q_SELECT_SUBS_ADDRESS = "select nvl(t.concat_address_with_town,'') from addresses t where t.addr_id = ? ";
	private static final String Q_SELECT_SUBS_PHONES = "select t.phone_number_id,t.hidden_by_request,t.phone_contract_type,pn.phone_state_id,pn.phone_type_id,pn.is_parallel from subscriber_to_phones t\n" +
														"       inner join phone_numbers pn on pn.phone_number_id = t.phone_number_id\n" + 
														"where t.subscriber_id = ? order by t.phone_number_id"; 
	
	/**
	 * Save Or Update Subscriber
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<?, ?> saveOrUpdateSubscriber(DSRequest dsRequest)
			throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:SubscriberDMI.saveOrUpdateSubscriber.";

			Map<?, ?> values = dsRequest.getValues();
			Long subscriber_id = values.containsKey("subscriber_id") ? Long
					.parseLong(values.get("subscriber_id").toString()) : null;

			String loggedUserName = dsRequest.getFieldValue("loggedUserName")
					.toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			Subscribers subscr = null;
			Map oldValues = null;
			if (subscriber_id != null) {
				subscr = oracleManager.find(Subscribers.class, subscriber_id);
				oldValues = DataTools.getProperties(subscr);	
			}
			if (subscr == null && subscriber_id != null) {
				throw new Exception("ვერ ვიპოვე აბონენტი შესაცვლელად(ID="
						+ subscriber_id + ")");
			}
			if (subscr == null) {
				subscr = new Subscribers();
			}

			DataTools.setProperties(values, subscr);
			Address addr = null;
			boolean isAddressChanged = false;
			Long address_id = subscr.getAddr_id();
			
			String oldFullAdd = null;
			if (address_id != null) {
				addr = oracleManager.find(Address.class, address_id);
				Object oldObject = oracleManager.createNativeQuery(Q_SELECT_SUBS_ADDRESS).setParameter(1, address_id).getSingleResult();
				oldFullAdd = oldObject == null ? "" : oldObject.toString();			
			}else {
				isAddressChanged = true;
			}

			if (addr == null && address_id != null) {
				throw new Exception("ვერ ვიპოვე აბონენტი შესაცვლელად(ID=" + subscriber_id + ")");
			}
			if (addr == null) {
				addr = new Address();
			}
			DataTools.setProperties(values, addr);
			Object streets_id = values.get("streets_id");
			if (streets_id != null) {
				addr.setStreet_id(Long.parseLong(streets_id.toString()));
			}
			if (address_id == null) {
				oracleManager.persist(addr);
			} else {
				oracleManager.merge(addr);
			}
			address_id = addr.getAddr_id();

			subscr.setAddr_id(address_id);
			subscr.setUptDate(new Timestamp(System.currentTimeMillis()));

			boolean isNewAbonent = false;
			
			if (subscriber_id == null) {
				oracleManager.persist(subscr);
				isNewAbonent = true;
			} else {
				oracleManager.merge(subscr);
			}
			subscriber_id = subscr.getSubscriber_id();

			Map<Long, Long[]> old_phones = getSubsPhones(oracleManager, subscriber_id);
			
			oracleManager.createNativeQuery(Q_DELETE_PHONES_BY_SUBSCRIBER)
					.setParameter(1, subscriber_id).executeUpdate();
			oracleManager.flush();
			
			if(!isAddressChanged){
				String newFullAdd = "";
				Object newObject = oracleManager.createNativeQuery(Q_SELECT_SUBS_ADDRESS).setParameter(1, address_id).getSingleResult();
				newFullAdd = newObject == null ? "" : newObject.toString();
				isAddressChanged = !oldFullAdd.equals(newFullAdd);	
			}			
			

			Object objPhoneList = values.get("listPhones");
			if (objPhoneList == null) {
				throw new Exception("ტელეფონების სია არ არის გადმოცემული !!!");
			}

			if (!(objPhoneList instanceof Map)) {
				throw new Exception(
						"ტელეფონების სია არასწორედაა გადმოცემული !!!");
			}
			Map<?, ?> mpPhoneList = (Map<?, ?>) objPhoneList;
			if (mpPhoneList.isEmpty()) {
				throw new Exception("ტელეფონების სია ცარიელია !!!");
			}
			String sql = "select decode(count(1), 0, 1, 0) allowed\n"
					+ "  from SUBSCRIBER_TO_PHONES t\n"
					+ " inner join phone_numbers pn\n"
					+ "    on pn.phone_number_id = t.phone_number_id\n"
					+ " where t.subscriber_id != ?\n" + "   and pn.phone = ?";

			Query qFindPhone = oracleManager.createNativeQuery(sql)
					.setParameter(1, subscriber_id);

			Set<?> pkeys = mpPhoneList.keySet();

			for (Object key : pkeys) {
				Object obj = qFindPhone.setParameter(2, key).getSingleResult()
						.toString();
				if (!("1".equals(obj)))
					throw new Exception("ნომერზე - " + key
							+ " უკვე რეგისტრირებულია სხვა აბონენტი !!!");
			}

			for (Object key : pkeys) {
				Object objPhone = mpPhoneList.get(key);
				if (objPhone == null || !(objPhone instanceof Map)) {
					throw new Exception("პარამეტრები ნომერზე - " + key
							+ " არ არის გადმოცემული !!!");
				}

				Map<?, ?> mpPhone = (Map<?, ?>) objPhone;
				if (mpPhone.isEmpty()) {
					throw new Exception("პარამეტრები ნომერზე - " + key
							+ " ცარიელია !!!");
				}

				List<PhoneNumber> phoneNumbers = (List<PhoneNumber>) oracleManager
						.createNamedQuery("PhoneNumber.getByPhoneNumber")
						.setParameter("phone", key).getResultList();
				PhoneNumber number = null;
				if (phoneNumbers == null || phoneNumbers.isEmpty()) {
					number = new PhoneNumber();
				} else {
					number = phoneNumbers.get(0);
				}
				Long phone_id = number.getPhone_number_id();
				DataTools.setProperties(mpPhone, number);
				number.setPhone_number_id(phone_id);
				if (phone_id == null) {
					oracleManager.persist(number);
				} else {
					oracleManager.merge(number);
				}
				phone_id = number.getPhone_number_id();
				SubscribersToPhones sp = new SubscribersToPhones();
				DataTools.setProperties(mpPhone, sp);
				sp.setSubscriber_id(subscriber_id);
				sp.setPhone_number_id(phone_id);
				oracleManager.persist(sp);
			}

			oracleManager.flush();			
			Map<Long, Long[]> new_phones = getSubsPhones(oracleManager, subscriber_id);
			
			if (isNewAbonent) {
				saveActionHistNewOrDelAb(true, loggedUserName, oracleManager);
			} else {
				saveOrgActionHistUpdateAbonent(loggedUserName, oracleManager, dsRequest, oldValues, isAddressChanged, old_phones, new_phones);
			}

			oracleManager.flush();
			oracleManager.createNativeQuery("{call createSubscriberHist(?)}")
					.setParameter(1, subscriber_id).executeUpdate();
			EMF.commitTransaction(transaction);
			values = DMIUtils.findRecordById("SubscriberDS", "customSearch",
					subscriber_id, "subscriber_id");
			return values;
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

	@SuppressWarnings("rawtypes")
	private Map<Long, Long[]> getSubsPhones(EntityManager oracleManager,
			Long subscriber_id) throws CallCenterException {
		try {
			List resultList = oracleManager.createNativeQuery(Q_SELECT_SUBS_PHONES).setParameter(1, subscriber_id).getResultList();			
			Map<Long, Long[]> old_phones = new TreeMap<Long, Long[]>();
			
			if(resultList != null && !resultList.isEmpty()){
				for (Object record : resultList) {
					Object data[] = (Object[]) record; 
					Long phone_number_id = new Long(data[0].toString());
					Long hidden_by_request = new Long(data[1].toString());
					Long phone_contract_type = new Long(data[2].toString());
					Long phone_state_id = new Long(data[3].toString());
					Long phone_type_id = new Long(data[4].toString());
					Long is_parallel = new Long(data[5].toString());
					Long phone_props[] = {hidden_by_request, phone_contract_type, phone_state_id, phone_type_id, is_parallel};
					old_phones.put(phone_number_id, phone_props);
				}
			}
			return old_phones;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		}
	}

	public Map<?, ?> removeSubscriber(DSRequest dsRequest) throws Exception {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			String log = "Method:SubscriberDMI.removeSubscriber.";
			Map<?, ?> mp = dsRequest.getOldValues();
			Long subscriber_id = Long.parseLong(dsRequest.getOldValues()
					.get("subscriber_id").toString());

			String loggedUserName = mp.get("loggedUserName").toString();
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			RCNGenerator.getInstance().initRcn(oracleManager, recDate,
					loggedUserName, log);
			oracleManager.createNativeQuery(Q_DELETE_PHONES_BY_SUBSCRIBER)
					.setParameter(1, subscriber_id).executeUpdate();
			oracleManager.flush();
			Object addressid = oracleManager
					.createNativeQuery(Q_SUBSCRIBER_ADDRESS_ID)
					.setParameter(1, subscriber_id).getSingleResult();

			oracleManager.createNativeQuery(Q_DELETE_SUBSCRIBER)
					.setParameter(1, subscriber_id).executeUpdate();
			oracleManager.flush();

			addressid = Long.parseLong(addressid.toString());
			oracleManager.createNativeQuery(Q_DELETE_SUBSCRIBER_ADDRESS)
					.setParameter(1, addressid).executeUpdate();
			oracleManager.flush();

			saveActionHistNewOrDelAb(false, loggedUserName, oracleManager);

			EMF.commitTransaction(transaction);
			return null;
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

	private void saveActionHistNewOrDelAb(boolean isNewAb, String user,
			EntityManager oracleManager) throws CallCenterException {
		try {
			CorrUsrStat corrUsrStat = new CorrUsrStat();
			corrUsrStat.setAct_date(new Timestamp(System.currentTimeMillis()));
			corrUsrStat.setUser_name(user);
			corrUsrStat.setMmyy(Long.parseLong(dateFormatMMYYY.format(new Date(
					System.currentTimeMillis()))));
			if (isNewAb) {
				corrUsrStat.setNew_subs(1L);
				corrUsrStat.setDel_subs(0L);
			} else {
				corrUsrStat.setNew_subs(0L);
				corrUsrStat.setDel_subs(1L);
			}
			corrUsrStat.setNew_org(0L);
			corrUsrStat.setDel_org(0L);
			corrUsrStat.setAddress(0L);
			corrUsrStat.setDel_phone(0L);
			corrUsrStat.setDirector(0L);
			corrUsrStat.setEmail(0L);
			corrUsrStat.setFounded_date(0L);
			corrUsrStat.setIdent_code(0L);
			corrUsrStat.setNew_phone(0L);

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
	private void saveOrgActionHistUpdateAbonent(String user,
			EntityManager oracleManager, DSRequest dsRequest, Map oldValues, boolean isAddressChanged,
			Map<Long, Long[]>  old_phones, Map<Long, Long[]>  new_phones)
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
			corrUsrStat.setFounded_date(0L);
			corrUsrStat.setOther(0L);			
			corrUsrStat.setPart_bank(0L);
			corrUsrStat.setAddress(0L);
			corrUsrStat.setDirector(0L);			 
			corrUsrStat.setEmail(0L);
			corrUsrStat.setOrg_comment(0L);			
			corrUsrStat.setSoc_network(0L);
			corrUsrStat.setWeb_site(0L);			
			corrUsrStat.setIdent_code(0L);
			corrUsrStat.setWork_hour_dayy_off(0L);
			
			boolean changed = isAddressChanged;
			if (!changed){
				changed = (!(values.get("name_id")==null?"":values.get("name_id").toString()).equals(oldValues.get("name_id")==null?"":oldValues.get("name_id").toString()));
			}
			if(!changed){
				changed = (!(values.get("family_name_id")==null?"":values.get("family_name_id").toString()).equals(oldValues.get("family_name_id")==null?"":oldValues.get("family_name_id").toString()));
			}
			if(!changed){
				changed = (!(values.get("full_hidden")==null?"":values.get("full_hidden").toString()).equals(oldValues.get("full_hidden")==null?"":oldValues.get("full_hidden").toString()));
			}
			if(!changed){
				changed = (!(values.get("full_hidden")==null?"":values.get("full_hidden").toString()).equals(oldValues.get("full_hidden")==null?"":oldValues.get("full_hidden").toString()));
			}			
			if(!changed){
				if(!old_phones.keySet().equals(new_phones.keySet())) {
					changed = true;
				}
				if (!changed) {
					Set<Long> oldPhoneKeys = old_phones.keySet();
					for (Long old_phone_key : oldPhoneKeys) {
						Long old_phone_values[] = old_phones.get(old_phone_key);
						Long new_phone_values[] = new_phones.get(old_phone_key);
						if(!Arrays.equals(old_phone_values, new_phone_values)){
							changed = true;
							break;
						}
					}
				}
			}
			if(!changed){
				return;
			}
			corrUsrStat.setUpdate_subs(changed ? 1L : 0L);
			oracleManager.persist(corrUsrStat);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error saveOrgActionHistNewOrg : ", e);
			throw new CallCenterException("saveOrgActionHistNewOrg : "
					+ e.toString());
		}
	}
	
}
