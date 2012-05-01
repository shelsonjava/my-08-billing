package com.info08.billing.callcenter.server.listener;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info08.billing.callcenter.server.common.QueryConstants;
import com.info08.billing.callcenter.shared.entity.discovery.Discover;
import com.isomorphic.jpa.EMF;
import com.isomorphic.messaging.ISCMessage;
import com.isomorphic.messaging.ISCMessageDispatcher;

public class DiscoveryServlet extends HttpServlet {

	private static final long serialVersionUID = 6200724895227971697L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	// RPC transactions may be encoded as a GET request, so handle those as well
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "no-cache");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		new Thread() {
			@SuppressWarnings("rawtypes")
			public void run() {

				EntityManager oracleManager = null;
				try {
					ISCMessageDispatcher dispatcher = ISCMessageDispatcher
							.instance();
					while (true) {
						try {
							oracleManager = EMF.getEntityManager();
							List list = oracleManager.createNativeQuery(
									QueryConstants.Q_GET_DISCOVERY_LIST)
									.getResultList();
							ArrayList<Discover> listData = new ArrayList<Discover>();
							if (list != null && !list.isEmpty()) {
								for (Object object : list) {
									Object columns[] = (Object[]) object;
									Discover discover = new Discover();
									String discover_type = columns[0] == null ? null
											: columns[0].toString();
									String response_type = columns[1] == null ? null
											: columns[1].toString();
									Long discover_id = new Long(
											columns[2] == null ? "-1000"
													: columns[2].toString());
									String call_id = columns[3] == null ? null
											: columns[3].toString();
									String phone = columns[4] == null ? null
											: columns[4].toString();
									String discover_txt = columns[5] == null ? null
											: columns[5].toString();
									String contact_phone = columns[6] == null ? null
											: columns[6].toString();
									Long discover_type_id = new Long(
											columns[7] == null ? "-1000"
													: columns[7].toString());
									Timestamp rec_date = columns[8] == null ? null
											: (Timestamp) columns[8];
									String rec_user = columns[9] == null ? null
											: columns[9].toString();
									Long deleted = new Long(
											columns[10] == null ? "-1000"
													: columns[10].toString());
									Timestamp upd_date = columns[11] == null ? null
											: (Timestamp) columns[11];
									String upd_user = columns[12] == null ? null
											: columns[12].toString();
									Long response_type_id = new Long(
											columns[13] == null ? "-1000"
													: columns[13].toString());
									String contact_person = columns[14] == null ? null
											: columns[14].toString();
									Long execution_status = new Long(
											columns[15] == null ? "-1000"
													: columns[15].toString());
									Long ccr = new Long(
											columns[16] == null ? "-1000"
													: columns[16].toString());
									Long iscorrect = new Long(
											columns[17] == null ? "-1000"
													: columns[17].toString());

									discover.setCall_id(call_id);
									discover.setCcr(ccr);
									discover.setContact_person(contact_person);
									discover.setContact_phone(contact_phone);
									discover.setDeleted(deleted);
									discover.setDiscover_id(discover_id);
									discover.setDiscover_txt(discover_txt);
									discover.setDiscover_type(discover_type);
									discover.setDiscover_type_id(discover_type_id);
									discover.setExecution_status(execution_status);
									discover.setIscorrect(iscorrect);
									discover.setPhone(phone);
									discover.setRec_date(rec_date);
									discover.setRec_user(rec_user);
									discover.setResponse_type_id(response_type_id);
									//discover.setStatus_descr("Pending");
									discover.setUpd_date(upd_date);
									discover.setUpd_user(upd_user);
									discover.setResponse_type(response_type);
									listData.add(discover);
								}
								System.out.println("Sending List ... Size = "
										+ listData.size());
								ISCMessage iscMessage = new ISCMessage(
										"discoveryServlet", listData);
								dispatcher.send(iscMessage);
								System.out.println("Sending List ... Size = "
										+ listData.size());
							}
							Thread.sleep(10000);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (oracleManager != null) {
								try {
									EMF.returnEntityManager(oracleManager);
								} catch (Exception e2) {
									e2.printStackTrace();
								}
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
		// ClientMessageSender.startListening();
	}
}
