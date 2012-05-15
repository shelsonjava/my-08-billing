package com.info08.billing.callcenterbk.server.listener;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.server.impl.dmi.DMIUtils;
import com.info08.billing.callcenterbk.shared.entity.survey.Survey;
import com.isomorphic.messaging.ISCMessage;
import com.isomorphic.messaging.ISCMessageDispatcher;

public class ClientMessageSender implements Runnable {

	private static ClientMessageSender instance = null;

	@Override
	public void run() {

		try {
			ISCMessageDispatcher dispatcher = ISCMessageDispatcher.instance();
			while (true) {
				try {
					Map<?, ?> mp = new TreeMap<String, String>();
					List<Survey> listData = DMIUtils.findObjectsdByCriteria(
							"SurveyDS", "searchAllSurvey", mp, Survey.class);
					System.out.println("Sending List ... Size = "
							+ listData.size());
					ISCMessage iscMessage = new ISCMessage("surveyServlet",
							listData);
					dispatcher.send(iscMessage);
					System.out.println("Sending List ... Size = "
							+ listData.size());
					Thread.sleep(10000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// if (oracleManager != null) {
					// try {
					// EMF.returnEntityManager(oracleManager);
					// } catch (Exception e2) {
					// e2.printStackTrace();
					// }
					// }
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startListening() {
		if (instance == null) {
			instance = new ClientMessageSender();
			System.out.println("Listening ... ");
			new Thread(instance).start();
		}
	}
}
