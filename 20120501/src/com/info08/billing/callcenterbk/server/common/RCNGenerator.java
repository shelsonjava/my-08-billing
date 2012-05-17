package com.info08.billing.callcenterbk.server.common;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;

public class RCNGenerator {
	private Logger logger = Logger.getLogger(RCNGenerator.class.getName());

	private static RCNGenerator instance;

	public static synchronized RCNGenerator getInstance() {
		if (instance == null) {
			instance = new RCNGenerator();
		}
		return instance;
	}

	public Long initRcn(EntityManager oracleManager, Timestamp date,
			String userName, String operation) throws CallCenterException {
		StringBuilder log = new StringBuilder("\nGenerating RCN.\n");
		try {
			long time = System.currentTimeMillis();
			Query query = oracleManager
					.createNativeQuery("select Rcnpackage.CREATE_RCN(?,?,?) from dual");
			query.setParameter(1, userName);
			query.setParameter(2, date);
			query.setParameter(3, operation);
			Long rcn = new Long(query.getSingleResult().toString());
			time = System.currentTimeMillis() - time;

			log.append("Data = ").append(date).append("\n");
			log.append("User = ").append(userName).append("\n");
			log.append("Operation = ").append(operation).append("\n");
			log.append("RCN Value = ").append(rcn).append("\n");
			log.append("RCN Generation Time = ").append(time).append("\n");
			logger.info(log.toString());
			return rcn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CallCenterException("შეცდომა ისტორიის შექმნისას : "
					+ e.toString());

		}
	}
}
