package com.info08.billing.callcenterbk.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.shared.common.CommonFunctions;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.Users;
import com.info08.billing.callcenterbk.shared.entity.callcenter.Treatments;
import com.info08.billing.callcenterbk.shared.entity.contractors.CorpClientBlockChecker;
import com.info08.billing.callcenterbk.shared.entity.session.CallSession;
import com.isomorphic.jpa.EMF;

public class InitAppServlet extends HttpServlet {

	private static final long serialVersionUID = -60980466875400896L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			long time = System.currentTimeMillis();
			String userName = request.getParameter("userName");
			String phone = request.getParameter("phone");
			int ampIndex = phone.indexOf("A");
			String type = request.getParameter("type");
			String who = request.getParameter("who");
			if (userName == null || userName.trim().equalsIgnoreCase("")) {
				out.println("Invalid UserName From SIP Client : " + userName);
				return;
			}
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				out.println("Invalid Phone From SIP Client : " + phone);
				return;
			}
			if (ampIndex < 0) {
				out.println("Invalid Session From SIP Client : " + phone);
				return;
			}
			if (type == null || !type.equalsIgnoreCase(Constants.callStart)) {
				out.println("Invalid Type Parameter From SIP Client : " + type);
				return;
			}
			if (who == null || who.trim().equalsIgnoreCase("")) {
				out.println("Invalid Who Parameter From SIP Client : " + who);
				return;
			}
			userName = userName.trim();
			String sessionId = phone.substring(ampIndex + 1, phone.length());
			if (sessionId == null || sessionId.trim().equalsIgnoreCase("")) {
				out.println("Invalid Session Parameter From SIP Client : "
						+ sessionId);
				return;
			}
			String operatorSrc = null;
			if (sessionId.endsWith("B")) {
				operatorSrc = "11808";
			} else if (sessionId.endsWith("D")) {
				operatorSrc = "11809";
			} else if (sessionId.endsWith("C")) {
				operatorSrc = "16007";
			} else {
				out.println("Invalid Call Number (f.e 11808,11809) ");
				return;
			}

			String ipAddress = request.getRemoteAddr();

			oracleManager = EMF.getEntityManager();
			transaction = EMF.getTransaction(oracleManager);

			Query q = oracleManager.createNamedQuery("Users.selectByUserName")
					.setParameter("usrName", userName);
			ArrayList<Users> users = (ArrayList<Users>) q.getResultList();
			if (users == null || users.isEmpty()) {
				out.println("UserName Not Found : " + userName);
				return;
			}
			if (users.size() > 1) {
				out.println("Multiple UserName Found : " + userName);
				return;
			}
			Users user = users.get(0);
			Long department_id = user.getDepartment_id();
			if (department_id == null
					|| !department_id.equals(Constants.OperatorDepartmentID)) {
				out.println("This UserName Is Not Operator : " + userName);
				return;
			}
			List userPermissions = oracleManager
					.createNativeQuery(QueryConstants.Q_GET_USER_PERMISSIONS)
					.setParameter(1, user.getUser_id()).getResultList();
			if (userPermissions != null && !userPermissions.isEmpty()) {
				Map<String, String> mapUserPermissions = new LinkedHashMap<String, String>();
				for (Object object : userPermissions) {
					mapUserPermissions.put(object.toString(), "1");
				}
				user.setUserPerms(mapUserPermissions);
			}
			String realPhone = phone.substring(0, ampIndex);

			int length = realPhone.length();
			if (!realPhone.equals("322112")) {
				switch (length) {
				case 9:
					break;
				case 8:
					realPhone = "2" + realPhone;
					break;
				case 7:
					realPhone = "22" + realPhone;
					break;
				case 6:
					realPhone = "322" + realPhone;
					break;
				default:
					out.println("Invalid Phone Length : " + realPhone);
					return;
				}
			}

			boolean phoneIsMobile = CommonFunctions.isPhoneMobile(realPhone);
			int callKind = 0;

			String non_charge_remark = "";
			String phoneDescription = "";
			String abonentName = "";
			Long gender = -1L;
			Long abonentVisible = 75100L;
			Double bid = new Double(-1);
			Treatments treatment = null;
			Long organization_id = -1L;
			// boolean checkContractor = false;

			boolean isUnknownPhoneNumber = false;

			String findPhone = "";
			if (realPhone.startsWith("22") || realPhone.startsWith("32")) {
				findPhone = realPhone.substring(2);
			} else {
				findPhone = realPhone;
			}

			// Free Of Charge
			List freeOfChargeList = oracleManager
					.createNativeQuery(
							QueryConstants.Q_GET_PHONE_FREE_OF_CHARGE)
					.setParameter(1, findPhone)
					.setParameter(2, Integer.parseInt(operatorSrc))
					.getResultList();
			boolean isBirthdayOrg = false;
			boolean isFreeOfCharge = false;
			String freeOfChargeText = "";
			if (freeOfChargeList != null && !freeOfChargeList.isEmpty()) {
				freeOfChargeText = freeOfChargeList.get(0).toString();
				isFreeOfCharge = true;
			}

			// Check for Unknown Phone Number
			// TODO
			List qResisUnknownPhoneNumber = oracleManager
					.createNativeQuery(
							QueryConstants.Q_GET_IS_UNKNOWN_PHONE_NUMBER)
					.setParameter(1, findPhone).setParameter(2, findPhone)
					.getResultList();

			if (qResisUnknownPhoneNumber != null
					&& qResisUnknownPhoneNumber.size() > 0) {
				int res = new Long(qResisUnknownPhoneNumber.get(0).toString())
						.intValue();
				if (res == 0) {
					isUnknownPhoneNumber = true;
				}
			}

			if (!phoneIsMobile && isUnknownPhoneNumber) {
				oracleManager
						.createNativeQuery(QueryConstants.INS_UNKNOWN_NUMBER)
						.setParameter(1, findPhone).executeUpdate();
			}

			// mobile
			if (phoneIsMobile) {
				if (who.equals("M")) {
					callKind = Constants.callTypeMobitel;
					List list = oracleManager.createNativeQuery(
							QueryConstants.Q_GET_MOBITEL_NOTE).getResultList();
					if (list != null && list.size() > 0) {
						phoneDescription = list.get(0).toString();
					}
				} else {
					callKind = Constants.callTypeMobile;
				}

				List<Treatments> list = (List<Treatments>) oracleManager
						.createNamedQuery(
								"Treatments.getTreatmentByPhoneNumber")
						.setParameter("phone_number", realPhone)
						.getResultList();

				if (list != null && !list.isEmpty()) {
					treatment = list.get(0);
					abonentName = treatment.getTreatment();
					gender = treatment.getGender();
					abonentVisible = treatment.getVisible();
					treatment.setLoggedUserName(userName);
				}
			} else {
				// fixed telephony
				Long nonChargeAbonentCount = new Long(oracleManager
						.createNativeQuery(
								QueryConstants.Q_GET_NON_CHARGE_ABONENT)
						.setParameter(1, realPhone)
						.setParameter(2, Integer.parseInt(operatorSrc))
						.getSingleResult().toString());
				if (nonChargeAbonentCount.longValue() > 0) {
					String remark = oracleManager
							.createNativeQuery(
									QueryConstants.Q_GET_NON_CHARGE_ABONENT_REMARK)
							.setParameter(1, realPhone).getSingleResult()
							.toString();
					callKind = Constants.callTypeNoncharge;
					non_charge_remark = remark;
				} else {

					List result = oracleManager
							.createNativeQuery(
									QueryConstants.Q_GET_ORG_ABONENT_NEW)
							.setParameter(1, findPhone)
							.setParameter(2, findPhone).getResultList();
					if (result != null && !result.isEmpty()) {
						Object array[] = (Object[]) result.get(0);
						organization_id = new Long(array[0] == null ? "-1"
								: array[0].toString());
						abonentName = array[1] == null ? "" : array[1]
								.toString();
						bid = new Double(array[2] == null ? "-1"
								: array[2].toString());
						callKind = Constants.callTypeOrganization;
						// Organization Birthday
						Long orgBirthdayList = new Long(
								oracleManager
										.createNativeQuery(
												QueryConstants.Q_GET_PHONE_ORG_BIRTHDAY)
										.setParameter(1, findPhone)
										.getSingleResult().toString());
						if (orgBirthdayList != null
								&& orgBirthdayList.longValue() > 0) {
							isBirthdayOrg = true;
						}
					} else {
						callKind = Constants.callTypeAbonent;
					}
				}
			}

			ServerSession serverSession = new ServerSession();

			List specNoteList = oracleManager
					.createNativeQuery(
							QueryConstants.Q_GET_SPECIAL_TEXT_BY_NUMBER)
					.setParameter(1, realPhone).getResultList();
			if (specNoteList != null && !specNoteList.isEmpty()) {
				Object object = specNoteList.get(0);
				if (object != null && !object.toString().trim().equals("")) {
					serverSession.setSpecialAlertMessage(object.toString()
							.trim());
				}
			}

			List reqMsg = oracleManager.createNativeQuery(
					QueryConstants.Q_GET_CALL_CENTER_REQ_MSG).getResultList();
			if (reqMsg != null && !reqMsg.isEmpty()) {
				String reqMsgText = reqMsg.get(0).toString();
				if (reqMsgText != null && !reqMsgText.trim().equals("")) {
					serverSession.setCallCenterReqMsg(reqMsgText);
				}
			}

			Long persNotesCount = new Long(oracleManager
					.createNativeQuery(QueryConstants.Q_GET_OPERATOR_REMARKS)
					.setParameter(1, userName).getSingleResult().toString());

			Long unreadNewsCnt = new Long(oracleManager
					.createNativeQuery(QueryConstants.Q_GET_CC_USER_NEWS_CNT)
					.setParameter(1, user.getUser_id()).getSingleResult()
					.toString());

			boolean isContractor = false;

			// if (checkContractor) {
			// List resultList = oracleManager
			// .createNativeQuery(QueryConstants.Q_GET_CONTRACTOR_INFO)
			// .setParameter(1, findPhone).getResultList();
			// if (resultList != null && !resultList.isEmpty()) {
			// serverSession.setContractorPhone(true);
			// Object dateRow[] = (Object[]) resultList.get(0);
			// Long contractorId = new Long(dateRow[0] == null ? "-1"
			// : dateRow[0].toString());
			// serverSession.setContractorId(contractorId);
			// if (dateRow[1] != null) {
			// serverSession.setContractorStartDate(new Timestamp(
			// new Long(dateRow[1].toString())));
			// }
			// if (dateRow[2] != null) {
			// serverSession.setContractorEndDate(new Timestamp(
			// new Long(dateRow[2].toString())));
			// }
			// Long contractorCirtNumber = new Long(
			// dateRow[3] == null ? "-1" : dateRow[3].toString());
			// serverSession
			// .setContractorCriticalNumber(contractorCirtNumber);
			// Long contractorIsBudget = new Long(
			// dateRow[4] == null ? "-1" : dateRow[4].toString());
			// serverSession.setContractorIsBudget(contractorIsBudget);
			// Long contractorPriceType = new Long(
			// dateRow[5] == null ? "-1" : dateRow[5].toString());
			// serverSession.setContractorPriceType(contractorPriceType);
			// Double contractorPrice = new Double(
			// dateRow[6] == null ? "0" : dateRow[6].toString());
			// Long contractorBlock = new Long(dateRow[7] == null ? "-1"
			// : dateRow[7].toString());
			// serverSession.setContractorBlock(contractorBlock);
			//
			// Long contractorMainId = new Long(dateRow[8] == null ? "-1"
			// : dateRow[8].toString());
			// serverSession.setContractorMainId(contractorMainId);
			//
			// Long contractorMainDetailId = new Long(
			// dateRow[9] == null ? "-1" : dateRow[9].toString());
			// serverSession
			// .setContractorMainDetailId(contractorMainDetailId);
			// serverSession.setContractorCallPrice(contractorPrice);
			// serverSession.setContractorCallCnt(0L);
			//
			// isContractor = true;
			// }
			// }

			serverSession.setUnreadPersNotesCount(persNotesCount);
			serverSession.setAbonentName(abonentName);
			serverSession.setMachineIP(ipAddress);
			serverSession.setPhone(realPhone);
			serverSession.setPhoneDescription(phoneDescription);
			serverSession.setSessionId(sessionId);
			serverSession.setGender(gender);
			serverSession.setNon_charge_remark(non_charge_remark);
			serverSession
					.setAbonentVisible(abonentVisible.equals(75100L) ? true
							: false);
			serverSession.setTreatment(treatment);
			serverSession.setUser(user);
			serverSession.setUserName(userName);
			serverSession.setWebSession(false);
			serverSession.setCallType(callKind);
			serverSession.setCbd(bid);
			serverSession.setTreatment(treatment);
			serverSession.setPhoneIsMobile(phoneIsMobile);
			serverSession.setOrganization_id(organization_id);
			serverSession.setOperatorSrc(operatorSrc);
			serverSession.setFreeOfCharge(isFreeOfCharge);
			serverSession.setFreeOfChargeText(freeOfChargeText);
			serverSession.setBirthdayOrg(isBirthdayOrg);
			serverSession.setUnreadNewsCnt(unreadNewsCnt);

			if (isContractor) {
				blockContractor(serverSession, oracleManager);
			}

			System.out.println("InitAppServlet. Incomming Session ID : "
					+ sessionId + ", userName = " + userName + ", phone = "
					+ phone + ", type = " + type + ", who = " + who);

			CallSession callSession = new CallSession();
			callSession.setCall_kind(new Long(callKind));
			callSession.setCall_phone(realPhone);
			callSession.setCall_start_date(new Timestamp(time));
			callSession.setSession_id(sessionId);
			callSession.setUname(userName);
			callSession.setYear_month(new Long(serverSession.getYearMonth()));
			callSession.setCall_quality(0L);
			callSession.setSwitch_ower_type(0L);
			callSession.setReject_type(0L);
			callSession.setCall_duration(0L);
			callSession.setOperator_src(operatorSrc);
			callSession.setImportant(0L);

			oracleManager.persist(callSession);
			serverSession.setCallSession(callSession);

			HttpSession session = request.getSession(true);
			ServerSession prevSession = (ServerSession) session
					.getAttribute("prevSession");
			if (prevSession != null) {
				serverSession.setPrevSession(prevSession);
			}
			session.setAttribute(sessionId, serverSession);
			session.setAttribute("prevSession", serverSession);

			EMF.commitTransaction(transaction);
			System.out
					.println("================================== Session Initialized ==================================");

			// // My Host - Test
//			// if (sessionId.startsWith("ts-")) {
			response.sendRedirect(response
					.encodeRedirectURL("http://127.0.0.1:8888/CallCenterBK.html?gwt.codesvr=127.0.0.1:9997&sessionId="
							+ sessionId));
			// } else {
			// // Live
//			 response.sendRedirect(response
//			 .encodeRedirectURL("http://192.168.1.5:19080/CallCenterBK/CallCenterBK.html?sessionId="
//			 + sessionId));
			// }
			time = System.currentTimeMillis() - time;
			System.out.println("Servlet Initialize Time Is : " + time
					+ " MilliSecond.");
		} catch (Exception e) {
			if (transaction != null) {
				EMF.rollbackTransaction(transaction);
			}
			e.printStackTrace();
			out.println(e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	private void blockContractor(ServerSession serverSession,
			EntityManager oracleManager) {
		try {
			// Long contractorId = serverSession.getContractorId();
			Long cBlock = serverSession.getContractorBlock();
			if (cBlock == null || !cBlock.equals(1L)) {
				return;
			}
			Timestamp contrStartDate = serverSession.getContractorStartDate();
			Timestamp contrEndDate = serverSession.getContractorEndDate();
			if (contrStartDate == null || contrEndDate == null) {
				return;
			}
			boolean needBlock = false;
			long contrStartDateTime = contrStartDate.getTime();
			long contrEndDateTime = contrEndDate.getTime();
			long currTime = System.currentTimeMillis();
			if (currTime < contrStartDateTime || currTime > contrEndDateTime) {
				needBlock = true;
			} else {
				Long contrCritNumb = serverSession
						.getContractorCriticalNumber();
				if (contrCritNumb != null && contrCritNumb.intValue() > 0) {
					// Long contrMainDetailId = serverSession
					// .getContractorMainDetailId();
					// Long contrMainId = serverSession.getContractorMainId();
					// Long contrCallCnt = -1L;
					//
					// if (contrMainDetailId != null
					// && contrMainDetailId.longValue() > 0) {
					// contrCallCnt = new Long(
					// oracleManager
					// .createNativeQuery(
					// QueryConstants.Q_GET_CALL_CNT_BY_CONT_AND_MAIN_DET_ID)
					// .setParameter(1, contractorId)
					// .setParameter(2, contrMainDetailId)
					// .setParameter(3, contrMainDetailId)
					// .getSingleResult().toString());
					// } else {
					// contrCallCnt = new Long(
					// oracleManager
					// .createNativeQuery(
					// QueryConstants.Q_GET_CALL_CNT_BY_CONT_AND_MAIN_ID)
					// .setParameter(1, contractorId)
					// .setParameter(2, contrMainId)
					// .setParameter(3, contrMainId)
					// .setParameter(4, contrMainId)
					// .getSingleResult().toString());
					// }
					// serverSession.setContractorCallCnt(contrCallCnt);
					//
					// if (contrCallCnt > 0
					// && (contrCallCnt + 10) >= contrCritNumb) {
					needBlock = true;
					// }
				}
			}
			serverSession.setContractorNeedBlock(needBlock);
			if (!needBlock) {
				return;
			}
			// Long contractorPriceType =
			// serverSession.getContractorPriceType();
			// Advanced Price Type
			// if (contractorPriceType.equals(1L)) {
			// Double contractorPrice = new Double(oracleManager
			// .createNativeQuery(
			// QueryConstants.Q_GET_CONTRACTOR_ADV_PRICE)
			// .setParameter(1, contractorId)
			// .setParameter(2, serverSession.getContractorCallCnt())
			// .setParameter(3, serverSession.getContractorCallCnt())
			// .getSingleResult().toString());
			// serverSession.setContractorCallPrice(contractorPrice);
			// }

			CorpClientBlockChecker blockChecker = new CorpClientBlockChecker();
			blockChecker
					.setCorporate_client_id(serverSession.getContractorId());
			blockChecker.setRec_date(new Timestamp(currTime));
			blockChecker.setUpdDate(new Timestamp(currTime));
			blockChecker.setStatus(0L);

			oracleManager.persist(blockChecker);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
