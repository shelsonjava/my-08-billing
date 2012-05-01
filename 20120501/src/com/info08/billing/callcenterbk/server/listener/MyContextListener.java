package com.info08.billing.callcenterbk.server.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.info08.billing.callcenterbk.server.common.RCNGenerator;

public class MyContextListener implements ServletContextListener {

	private Logger logger = Logger.getLogger(MyContextListener.class.getName());

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			logger.info("ServletContextListener destroyed ................ ");
		} catch (Exception e) {
			logger.error(
					"Error While Context Destroyed For SMSMessageListener : ",
					e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		StringBuilder log = new StringBuilder();
		try {
			logger.info("ServletContextListener started ................ ");
			RCNGenerator.getInstance();
			logger.info(log.toString());
		} catch (Exception e) {
			logger.error(
					"Error While Context Initialize For ContectListener : ", e);
		}
	}

}
