package com.info08.billing.callcenterbk.server.spring.jobs;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.server.common.RCNGenerator;
import com.info08.billing.callcenterbk.server.impl.dmi.ContractorsDMI;
import com.info08.billing.callcenterbk.shared.entity.contractors.CorporateClient;
import com.info08.billing.callcenterbk.shared.entity.contractors.CorpClientBlockChecker;
import com.isomorphic.jpa.EMF;

public class ContractorCheckerJob extends TimerTask {

	Logger logger = Logger.getLogger(ContractorCheckerJob.class.getName());

	public void run() {
		checkContractor();
	}

	@SuppressWarnings("unchecked")
	public void checkContractor() {

		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			if (!InetAddress.getLocalHost().getHostAddress()
					.contains("127.0.0")) {
				String log = "Job. Checking Contractors.\n";
				oracleManager = EMF.getEntityManager();

				ArrayList<CorpClientBlockChecker> list = (ArrayList<CorpClientBlockChecker>) oracleManager
						.createNamedQuery(
								"CorpClientBlockChecker.getPendingContractors")
						.getResultList();
				if (list == null || list.isEmpty()) {
					log += "Contractors List Is Empty. ";
					logger.info(log);
					return;
				}
				transaction = EMF.getTransaction(oracleManager);
				Timestamp currDate = new Timestamp(System.currentTimeMillis());

				ContractorsDMI contractorsDMI = new ContractorsDMI();

				RCNGenerator.getInstance().initRcn(oracleManager,
						new Timestamp(System.currentTimeMillis()), "JOB",
						"Checking Contract.");

				for (CorpClientBlockChecker blockChecker : list) {
					Long contractId = blockChecker.getCorporate_client_id();
					if (contractId == null) {
						continue;
					}
					CorporateClient contract = oracleManager.find(
							CorporateClient.class, contractId);
					if (contract == null) {
						continue;
					}

					contractorsDMI.blockUnblockContractorPhones(contract,
							false, oracleManager);

					blockChecker.setUpdDate(currDate);
					blockChecker.setStatus(1L);
					oracleManager.merge(blockChecker);
				}
				log += "Contractors Checking Finished Successfully. ";
				logger.info(log);
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