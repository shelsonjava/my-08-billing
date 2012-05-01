package com.info08.billing.callcenterbk.server.spring.jobs;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.List;
import java.util.TimerTask;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.entity.discovery.DiscoverySmsHist;
import com.isomorphic.jpa.EMF;

public class DiscoverySMSSenderJob extends TimerTask {

	Logger logger = Logger.getLogger(DiscoverySMSSenderJob.class.getName());

	public void run() {
		sendDiscoverySMS();
	}

	@SuppressWarnings("rawtypes")
	public void sendDiscoverySMS() {

		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			if (!InetAddress.getLocalHost().getHostAddress()
					.contains("127.0.0")) {
				String log = "Job. Sending Auto Discovery SMS.\n";
				oracleManager = EMF.getEntityManager();
				transaction = EMF.getTransaction(oracleManager);

				List resultList = oracleManager.createNativeQuery(
						QueryConstants.Q_GET_DISC_SMS_FOR_SEND).getResultList();
				if (resultList != null && !resultList.isEmpty()) {
					log += "=========== SMS List ===========\n";
					for (Object object : resultList) {
						Object data[] = (Object[]) object;
						Long discovery_id = (data[0] == null) ? null
								: new Long(data[0].toString());
						String sessionId = (data[1] == null) ? null : data[1]
								.toString();
						String phone = (data[2] == null) ? null : data[2]
								.toString();
						if (discovery_id == null || phone == null) {
							continue;
						}
						Timestamp rec_date = new Timestamp(
								System.currentTimeMillis());
						DiscoverySmsHist discoverySmsHist = new DiscoverySmsHist();
						discoverySmsHist.setDiscovery_id(discovery_id);
						discoverySmsHist.setRec_date(rec_date);
						oracleManager
								.createNativeQuery(
										"{call insert_send_sms(?,?,?,?)}")
								.setParameter(1, Constants.discSMSDefText)
								.setParameter(2, phone)
								.setParameter(3, sessionId).setParameter(4, 0)
								.executeUpdate();
						log += "Phone = " + phone + ", SessionId = "
								+ sessionId + ", Rec. Date = "
								+ rec_date.toString() + "\n";
						oracleManager.persist(discoverySmsHist);
					}
					log += "=========== SMS List ===========";
				} else {
					log += "SMS List Is Empty.";
				}
				// sysdate
				EMF.commitTransaction(transaction);
				logger.info(log);
			}
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			logger.error(e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}