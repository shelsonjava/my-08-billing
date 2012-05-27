package com.info08.billing.callcenterbk.server.impl.dmi;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.shared.items.Address;
import com.info08.billing.callcenterbk.shared.items.PhoneNumber;
import com.info08.billing.callcenterbk.shared.items.Subscribers;
import com.info08.billing.callcenterbk.shared.items.SubscribersToPhones;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.jpa.EMF;
import com.isomorphic.util.DataTools;

public class SubscriberDMI implements QueryConstants {

	Logger logger = Logger.getLogger(SubscriberDMI.class.getName());

	/**
	 * Save Or Update Subscriber
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	public Map<?, ?> saveOrUpdateSubscriber(DSRequest dsRequest) throws Exception {
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
			if (subscriber_id != null)
				subscr = oracleManager.find(Subscribers.class, subscriber_id);
			if (subscr == null && subscriber_id != null)
				throw new Exception("ვერ ვიპოვე აბონენტი შესაცვლელად(ID="
						+ subscriber_id + ")");
			if (subscr == null)
				subscr = new Subscribers();

			DataTools.setProperties(values, subscr);
			Address addr = null;
			Long address_id = subscr.getAddr_id();

			if (address_id != null)
				addr = oracleManager.find(Address.class, address_id);

			if (addr == null && address_id != null)
				throw new Exception("ვერ ვიპოვე აბონენტი შესაცვლელად(ID="
						+ subscriber_id + ")");
			if (addr == null)
				addr = new Address();
			DataTools.setProperties(values, addr);

			if (address_id == null)
				oracleManager.persist(addr);
			else
				oracleManager.merge(addr);
			address_id = addr.getAddr_id();

			subscr.setAddr_id(address_id);

			if (subscriber_id == null)
				oracleManager.persist(subscr);
			else
				oracleManager.merge(subscr);
			subscriber_id = subscr.getSubscriber_id();

			oracleManager.createNativeQuery(Q_DELETE_PHONES_BY_SUBSCRIBER)
					.setParameter(1, subscriber_id).executeUpdate();
			oracleManager.flush();

			Object objPhoneList = values.get("listPhones");
			if (objPhoneList == null)
				throw new Exception("ტელეფონების სია არ არის გადმოცემული !!!");

			if (!(objPhoneList instanceof Map))
				throw new Exception(
						"ტელეფონების სია არასწორედაა გადმოცემული !!!");
			Map<?, ?> mpPhoneList = (Map<?, ?>) objPhoneList;
			if (mpPhoneList.isEmpty())
				throw new Exception("ტელეფონების სია ცარიელია !!!");
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
				if (objPhone == null || !(objPhone instanceof Map))
					throw new Exception("პარამეტრები ნომერზე - " + key
							+ " არ არის გადმოცემული !!!");

				Map<?, ?> mpPhone = (Map<?, ?>) objPhone;
				if (mpPhone.isEmpty())
					throw new Exception("პარამეტრები ნომერზე - " + key
							+ " ცარიელია !!!");

				List<PhoneNumber> phoneNumbers = (List<PhoneNumber>) oracleManager
						.createNamedQuery("PhoneNumber.getByPhoneNumber")
						.setParameter("phone", key).getResultList();
				PhoneNumber number = null;
				if (phoneNumbers == null || phoneNumbers.isEmpty())
					number = new PhoneNumber();
				else
					number = phoneNumbers.get(0);
				Long phone_id = number.getPhone_number_id();
				DataTools.setProperties(mpPhone, number);
				number.setPhone_number_id(phone_id);
				if (phone_id == null)
					oracleManager.persist(number);
				else
					oracleManager.merge(number);
				phone_id = number.getPhone_number_id();
				SubscribersToPhones sp = new SubscribersToPhones();
				DataTools.setProperties(mpPhone, sp);
				sp.setSubscriber_id(subscriber_id);
				sp.setPhone_number_id(phone_id);
				oracleManager.persist(sp);

			}
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
}
