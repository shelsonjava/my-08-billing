package com.info08.billing.callcenterbk.server.listener;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info08.billing.callcenterbk.server.impl.dmi.DMIUtils;
import com.info08.billing.callcenterbk.shared.entity.survey.Survey;
import com.isomorphic.messaging.ISCMessage;
import com.isomorphic.messaging.ISCMessageDispatcher;

public class SurveyServlet extends HttpServlet {

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
			public void run() {

			
				try {
					ISCMessageDispatcher dispatcher = ISCMessageDispatcher
							.instance();
					while (true) {
						try {
							Map<String, String> criteria=new TreeMap<String,String>();
							criteria.put("current_date", "1");
							criteria.put("not_noll_status", "1");
							List<Survey> listData=DMIUtils.findObjectsdByCriteria("SurveyDS", "searchAllSurvey", criteria, Survey.class);
							if (listData != null && !listData.isEmpty()) {
//								for (Object object : list) {
//									Object columns[] = (Object[]) object;
//									Survey survey = new Survey();
//									String survey_kind_name = columns[0] == null ? null
//											: columns[0].toString();
//									String survey_reply_type_name = columns[1] == null ? null
//											: columns[1].toString();
//									Long survey_id = new Long(
//											columns[2] == null ? "-1000"
//													: columns[2].toString());
//									String session_call_id = columns[3] == null ? null
//											: columns[3].toString();
//									String phone = columns[4] == null ? null
//											: columns[4].toString();
//									String survey_descript = columns[5] == null ? null
//											: columns[5].toString();
//									String survey_phone = columns[6] == null ? null
//											: columns[6].toString();
//									Long survey_kind_id = new Long(
//											columns[7] == null ? "-1000"
//													: columns[7].toString());
//									Timestamp rec_date = columns[8] == null ? null
//											: (Timestamp) columns[8];
//									String rec_user = columns[9] == null ? null
//											: columns[9].toString();
//									Long deleted = new Long(
//											columns[10] == null ? "-1000"
//													: columns[10].toString());
//									Timestamp upd_date = columns[11] == null ? null
//											: (Timestamp) columns[11];
//									String upd_user = columns[12] == null ? null
//											: columns[12].toString();
//									Long survey_reply_type_id = new Long(
//											columns[13] == null ? "-1000"
//													: columns[13].toString());
//									String contact_person = columns[14] == null ? null
//											: columns[14].toString();
//									Long survery_responce_status = new Long(
//											columns[15] == null ? "-1000"
//													: columns[15].toString());
//									Long ccr = new Long(
//											columns[16] == null ? "-1000"
//													: columns[16].toString());
//									Long survey_done = new Long(
//											columns[17] == null ? "-1000"
//													: columns[17].toString());
//
//									survey.setSession_call_id(session_call_id);
//									survey.setSurvey_person(contact_person);
//									survey.setSurvey_phone(survey_phone);
//									survey.setSurvey_id(survey_id);
//									survey.setSurvey_descript(survey_descript);
//									survey.setSurvey_kind(survey_kind_name);
//									survey.setSurvey_kind_id(survey_kind_id);
//									survey.setSurvery_responce_status(survery_responce_status);
//									survey.setSurvey_done(survey_done);
//									survey.setP_numb(phone);
//									survey.setsurvey_reply_type_id(survey_reply_type_id);
//									// survey.setStatus_descr("Pending");
//									survey.setsurvey_reply_type_name(survey_reply_type_name);
//									listData.add(survey);
//								}
								System.out.println("Sending List ... Size = "
										+ listData.size());
								ISCMessage iscMessage = new ISCMessage(
										"surveyServlet", listData);
								dispatcher.send(iscMessage);
								System.out.println("Sending List ... Size = "
										+ listData.size());
							}
							Thread.sleep(10000);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							
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
