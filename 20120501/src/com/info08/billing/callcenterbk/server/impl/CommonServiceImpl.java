package com.info08.billing.callcenterbk.server.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.client.service.CommonService;
import com.info08.billing.callcenterbk.server.common.QueryConstants;
import com.info08.billing.callcenterbk.server.opencsv.CSVWriter;
import com.info08.billing.callcenterbk.shared.common.CommonFunctions;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.common.ServerSessionSurvItem;
import com.info08.billing.callcenterbk.shared.entity.SipConfig;
import com.info08.billing.callcenterbk.shared.entity.Users;
import com.info08.billing.callcenterbk.shared.entity.callcenter.Treatments;
import com.info08.billing.callcenterbk.shared.entity.session.CallSession;
import com.isomorphic.jpa.EMF;

@SuppressWarnings("serial")
public class CommonServiceImpl extends RemoteServiceServlet implements CommonService {

	private Logger logger = Logger.getLogger(CommonServiceImpl.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ServerSession login(String userName, String user_password, String sessionId, boolean isOperUser) throws CallCenterException {
		EntityManager oracleManager = null;
		Object tx = null;
		try {
			HttpSession session = this.getThreadLocalRequest().getSession();
			if (session != null && sessionId != null && !sessionId.trim().equalsIgnoreCase("")) {
				ServerSession currSession = (ServerSession) session.getAttribute(sessionId);
				if (currSession != null) {
					return currSession;
				}
			}

			oracleManager = EMF.getEntityManager();
			tx = EMF.getTransaction(oracleManager);

			String message = "User Identification. Username = " + userName + ", Password = " + user_password;
			if (userName == null || userName.trim().equals("")) {
				message += ". Result = Invalid UserName. ";
				logger.info(message);
				throw new CallCenterException("არასწორი მომხმარებელი");
			}
			if (user_password == null || user_password.trim().equals("")) {
				message += ". Result = Invalid Password. ";
				logger.info(message);
				throw new CallCenterException("არასწორი პაროლი");
			}

			Query q = oracleManager.createNamedQuery("Users.selectByUserName").setParameter("usrName", userName);
			ArrayList<Users> users = (ArrayList<Users>) q.getResultList();

			if (users == null || users.isEmpty()) {
				throw new CallCenterException("მომხმარებელი: " + userName + " ვერ მოიძებნა.");
			}
			if (users.size() != 1) {
				throw new CallCenterException("ასეთი მომხმარებელი: " + userName + " მონაცემთა ბაზაში რამოდენიმეა.");
			}
			Users user = users.get(0);

			String dbPassword = user.getUser_password();
			if (!user_password.equals(dbPassword)) {
				throw new CallCenterException("მომხმარებელის პაროლი არასწორია !");
			}
			List userPermissions = oracleManager.createNativeQuery(QueryConstants.Q_GET_USER_PERMISSIONS).setParameter(1, user.getUser_id()).getResultList();
			if (userPermissions != null && !userPermissions.isEmpty()) {
				Map<String, String> mapUserPermissions = new LinkedHashMap<String, String>();
				for (Object object : userPermissions) {
					mapUserPermissions.put(object.toString(), "1");
				}
				user.setUserPerms(mapUserPermissions);
			}

			ServerSession serverSession = new ServerSession();

			SipConfig[] arrSipConfigs = null;
			if (isOperUser) {
				Long department_id = user.getDepartment_id();
				if (department_id == null || (!department_id.equals(Constants.OperatorDepartmentID) && !department_id.equals(Constants.ITDepartmentID))) {
					throw new CallCenterException("ეს მომხმარებელი: " + userName + " არ არის ოპერატორი.");
				}

				List result = oracleManager.createNativeQuery("select * from ccare.SIP_CONFIGS t order by t.order_id asc").getResultList();
				if (result != null && !result.isEmpty()) {
					arrSipConfigs = new SipConfig[result.size()];
					int i = 0;
					for (Object record : result) {
						Object row[] = (Object[]) record;
						SipConfig sipConfig = new SipConfig();
						sipConfig.setId(Long.parseLong(row[0] == null ? "-1" : row[0].toString()));
						sipConfig.setConfig_id(Long.parseLong(row[1] == null ? "-1" : row[1].toString()));
						sipConfig.setConfig_name(row[2] == null ? "-1" : row[2].toString());
						sipConfig.setConfig_value(row[3] == null ? "-1" : row[3].toString());
						sipConfig.setParam_1(row[4] == null ? "-1" : row[4].toString());
						sipConfig.setParam_2(row[5] == null ? "-1" : row[5].toString());
						sipConfig.setParam_3(Long.parseLong(row[6] == null ? "-1" : row[6].toString()));
						sipConfig.setParam_4(Long.parseLong(row[7] == null ? "-1" : row[7].toString()));
						sipConfig.setOrder_id(Long.parseLong(row[8] == null ? "-1" : row[8].toString()));
						arrSipConfigs[i] = sipConfig;
						i++;
					}
				}
			}

			EMF.commitTransaction(tx);

			serverSession.setMachineIP(this.getThreadLocalRequest().getRemoteAddr());
			serverSession.setWebSession(!isOperUser);
			String mySessionId = oracleManager.createNativeQuery(QueryConstants.Q_GET_WEB_SESSION_ID).getSingleResult().toString();
			serverSession.setSessionId(sessionId);
			serverSession.setUser(user);
			serverSession.setUserName(userName);
			serverSession.setSipConfs(arrSipConfigs);

			session.setAttribute(mySessionId, serverSession);

			return serverSession;
		} catch (Exception e) {
			EMF.rollbackTransaction(tx);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			throw new CallCenterException("შეცდომა სისტემაში შესვლისას : " + e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@Override
	public String findSessionMp3ById(String sessionId, Date sessionDate) throws CallCenterException {
		HttpClient httpClient = null;
		PostMethod method = null;
		BufferedReader br = null;
		try {
			String log = "Method:findSessionMp3ById. Params : 1. SessionId = " + sessionId + " 2. sessionDate = " + sessionDate;
			if (sessionId == null || sessionId.trim().equals("")) {
				log += ". Result : Invalid SessionId";
				logger.info(log);
				throw new CallCenterException("არასწორი სესიის იდენტიფიკატორი : " + sessionId);
			}
			if (sessionDate == null) {
				log += ". Result : Invalid StartDate";
				logger.info(log);
				throw new CallCenterException("არასწორი სესიის თარიღი : " + sessionDate);
			}
			if (sessionId.endsWith("B") || sessionId.endsWith("C")) {
				sessionId = sessionId.substring(0, sessionId.length() - 1);
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formatedDate = simpleDateFormat.format(sessionDate);
			httpClient = new HttpClient();
			String fullUrl = Constants.sessionFilesUrl;
			method = new PostMethod(fullUrl);
			method.addParameter("sid", "A" + sessionId);
			method.addParameter("date", formatedDate);

			int returnCode = httpClient.executeMethod(method);
			log += ". fullURL = " + fullUrl;

			br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
			String response = br.readLine();

			log += ". Response = " + response;

			if (returnCode != HttpStatus.SC_OK) {
				log += ". Result : Invalid Result From Url.";
				logger.info(log);
				throw new CallCenterException("არასწორი სესიის მისამართი, შეცდომის კოდი : " + returnCode);
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CallCenterException("შეცდომა ფაილის მოძებნისას : " + e.toString());
		} finally {
			if (method != null) {
				method.releaseConnection();
			}

			if (br != null) {
				try {
					br.close();
				} catch (Exception fe) {
					fe.printStackTrace();
				}
			}
		}
	}

	@Override
	public void getBillingCompBillByMonth(Integer billing_company_id, Integer ym) throws CallCenterException {
		EntityManager oracleManager = null;
		try {

		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While getting cvs from database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : " + e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getBillingCompBillByDay(Integer billing_company_id, Date date_param) throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			oracleManager = EMF.getEntityManager();

			List<String[]> retResult = new ArrayList<String[]>();

			List resultList = oracleManager.createNativeQuery(QueryConstants.Q_GET_BILLING_COMP_BILL_BY_DAY).setParameter(1, new Timestamp(date_param.getTime()))
					.setParameter(2, new Timestamp(date_param.getTime())).setParameter(3, billing_company_id).getResultList();
			if (resultList != null && !resultList.isEmpty()) {
				for (Object row : resultList) {
					Object record[] = (Object[]) row;
					String rowData[] = new String[6];
					String phoneA = (record[0] == null) ? "" : record[0].toString();
					String phoneB = (record[1] == null) ? "" : record[1].toString();
					String charge_date1 = (record[3] == null) ? "" : record[3].toString();
					Double duration = (record[4] == null) ? new Double(0) : new Double(record[4].toString());
					Double rate = (record[5] == null) ? new Double(0) : new Double(record[5].toString());
					Double price = (record[6] == null) ? new Double(0) : new Double(record[6].toString());
					rowData[0] = phoneA;
					rowData[1] = phoneB;
					rowData[2] = charge_date1;
					rowData[3] = duration.toString();
					rowData[4] = rate.toString();
					rowData[5] = price.toString();
					retResult.add(rowData);
				}
			}
			String filename = (File.separator + System.getProperty("user.home") + File.separator + System.currentTimeMillis() + ".csv");
			File file = new File(filename);
			FileWriter fileWriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fileWriter);
			writer.writeAll(retResult);
			writer.close();
			int length = 0;

			HttpServletResponse resp = getThreadLocalResponse();
			ServletOutputStream op = resp.getOutputStream();
			resp.setContentType("application/octet-stream");
			resp.setContentLength((int) file.length());
			resp.setHeader("Content-Disposition", "attachment; filename*=\"utf-8''" + filename + "");

			byte[] bbuf = new byte[1024];
			DataInputStream in = new DataInputStream(new FileInputStream(file));

			while ((in != null) && ((length = in.read(bbuf)) != -1)) {
				op.write(bbuf, 0, length);
			}

			in.close();
			op.flush();
			op.close();

		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While getting cvs from database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : " + e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ServerSession getInfoByPhone(String phone, ServerSession serverSession) throws CallCenterException {
		EntityManager oracleManager = null;
		Object tx = null;
		try {
			System.out.println("11111111111111111111");
			long time = System.currentTimeMillis();
			System.out.println("phone = " + phone);

			oracleManager = EMF.getEntityManager();
			tx = EMF.getTransaction(oracleManager);
			String userName = serverSession.getUser_name();
			int ampIndex = phone.indexOf("A");

			System.out.println("22222222222222");

			String sessionId = phone.substring(ampIndex + 1, phone.length());
			if (sessionId == null || sessionId.trim().equalsIgnoreCase("")) {
				throw new CallCenterException("არასწორი სესიის იდენტიფიკატორი");
			}

			System.out.println("33333333333333333333333");

			String operatorSrc = null;
			if (sessionId.endsWith("B")) {
				operatorSrc = "11808";
			} else if (sessionId.endsWith("D")) {
				operatorSrc = "11809";
			} else if (sessionId.endsWith("C")) {
				operatorSrc = "16007";
			} else if (sessionId.endsWith("G")) {
				operatorSrc = "800800909";
			} else {
				throw new CallCenterException("არასწორი შემომავალი ნომერი");
			}

			System.out.println("44444444444444444");

			String ipAddress = serverSession.getMachineIP();
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
				case 3:
					realPhone = "311000" + realPhone;
					break;
				default:
					throw new CallCenterException("არასწორი ნომერი : " + realPhone);
				}
			}

			System.out.println("5555555555555555555");
			System.out.println("realPhone = " + realPhone);

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

			System.out.println("6666666666666666666");

			// Free Of Charge
			List freeOfChargeList = oracleManager.createNativeQuery(QueryConstants.Q_GET_PHONE_FREE_OF_CHARGE).setParameter(1, findPhone).setParameter(2, Integer.parseInt(operatorSrc))
					.getResultList();
			boolean isBirthdayOrg = false;
			boolean isFreeOfCharge = false;
			String freeOfChargeText = "";
			if (freeOfChargeList != null && !freeOfChargeList.isEmpty()) {
				freeOfChargeText = freeOfChargeList.get(0).toString();
				isFreeOfCharge = true;
			}

			System.out.println("7777777777777777777");
			// Check for Unknown Phone Number
			// TODO
			List qResisUnknownPhoneNumber = oracleManager.createNativeQuery(QueryConstants.Q_GET_IS_UNKNOWN_PHONE_NUMBER).setParameter(1, findPhone).setParameter(2, findPhone).getResultList();

			if (qResisUnknownPhoneNumber != null && qResisUnknownPhoneNumber.size() > 0) {
				int res = new Long(qResisUnknownPhoneNumber.get(0).toString()).intValue();
				if (res == 0) {
					isUnknownPhoneNumber = true;
				}
			}

			System.out.println("888888888888888888888");

			if (!phoneIsMobile && isUnknownPhoneNumber) {
				oracleManager.createNativeQuery(QueryConstants.INS_UNKNOWN_NUMBER).setParameter(1, findPhone).executeUpdate();
			}

			System.out.println("99999999999999999999999");

			// mobile
			if (phoneIsMobile) {
				callKind = Constants.callTypeMobile;
				List<Treatments> list = (List<Treatments>) oracleManager.createNamedQuery("Treatments.getTreatmentByPhoneNumber").setParameter("phone_number", realPhone).getResultList();

				if (list != null && !list.isEmpty()) {
					treatment = list.get(0);
					abonentName = treatment.getTreatment();
					gender = treatment.getGender();
					abonentVisible = treatment.getVisible();
					treatment.setLoggedUserName(userName);
				}
			} else {
				// fixed telephony
				Long nonChargeAbonentCount = new Long(oracleManager.createNativeQuery(QueryConstants.Q_GET_NON_CHARGE_ABONENT).setParameter(1, realPhone)
						.setParameter(2, Integer.parseInt(operatorSrc)).getSingleResult().toString());
				if (nonChargeAbonentCount.longValue() > 0) {
					String remark = oracleManager.createNativeQuery(QueryConstants.Q_GET_NON_CHARGE_ABONENT_REMARK).setParameter(1, realPhone).getSingleResult().toString();
					callKind = Constants.callTypeNoncharge;
					non_charge_remark = remark;
				} else {

					List result = oracleManager.createNativeQuery(QueryConstants.Q_GET_ORG_ABONENT_NEW).setParameter(1, findPhone).setParameter(2, findPhone).getResultList();
					if (result != null && !result.isEmpty()) {
						Object array[] = (Object[]) result.get(0);
						organization_id = new Long(array[0] == null ? "-1" : array[0].toString());
						abonentName = array[1] == null ? "" : array[1].toString();
						bid = new Double(array[2] == null ? "-1" : array[2].toString());
						callKind = Constants.callTypeOrganization;
						// Organization Birthday
						Long orgBirthdayList = new Long(oracleManager.createNativeQuery(QueryConstants.Q_GET_PHONE_ORG_BIRTHDAY).setParameter(1, findPhone).getSingleResult().toString());
						if (orgBirthdayList != null && orgBirthdayList.longValue() > 0) {
							isBirthdayOrg = true;
						}
					} else {
						callKind = Constants.callTypeAbonent;
					}
				}
			}

			System.out.println("101010101010101010101010");

			List specNoteList = oracleManager.createNativeQuery(QueryConstants.Q_GET_SPECIAL_TEXT_BY_NUMBER).setParameter(1, realPhone).getResultList();
			if (specNoteList != null && !specNoteList.isEmpty()) {
				Object object = specNoteList.get(0);
				if (object != null && !object.toString().trim().equals("")) {
					serverSession.setSpecialAlertMessage(object.toString().trim());
				}
			}

			List reqMsg = oracleManager.createNativeQuery(QueryConstants.Q_GET_CALL_CENTER_REQ_MSG).getResultList();
			if (reqMsg != null && !reqMsg.isEmpty()) {
				String reqMsgText = reqMsg.get(0).toString();
				if (reqMsgText != null && !reqMsgText.trim().equals("")) {
					serverSession.setCallCenterReqMsg(reqMsgText);
				}
			}

			System.out.println("1111111111111111111111111111");

			Long orgSubsCall = new Long(oracleManager.createNativeQuery(QueryConstants.Q_GET_ORG_SUBS_CALL).setParameter(1, findPhone).getSingleResult().toString());

			List resultList = oracleManager.createNativeQuery(QueryConstants.Q_GET_SURVEYS_BY_PHONE).setParameter(1, realPhone).setParameter(2, realPhone).getResultList();
			ServerSessionSurvItem surveryList[] = null;
			if (resultList != null && !resultList.isEmpty()) {
				surveryList = new ServerSessionSurvItem[resultList.size()];
				int i = 0;
				for (Object object : resultList) {
					Object row[] = (Object[]) object;
					ServerSessionSurvItem record = new ServerSessionSurvItem();
					record.setSurvey_kind_name(row[0] == null ? null : row[0].toString());
					record.setSurvey_reply_type_name(row[1] == null ? null : row[1].toString());
					record.setSurvey_id(row[2] == null ? null : Long.parseLong(row[2].toString()));
					record.setSession_call_id(row[3] == null ? null : row[3].toString());
					record.setP_numb(row[4] == null ? null : row[4].toString());
					record.setSurvey_descript(row[5] == null ? null : row[5].toString());
					record.setSurvey_phone(row[6] == null ? null : row[6].toString());
					record.setSurvey_kind_id(row[7] == null ? null : Integer.parseInt(row[7].toString()));
					record.setSurvey_reply_type_id(row[8] == null ? null : Integer.parseInt(row[8].toString()));
					record.setSurvey_person(row[9] == null ? null : row[9].toString());
					record.setSurvery_responce_status(row[10] == null ? null : Integer.parseInt(row[10].toString()));
					record.setSurvey_done(row[11] == null ? null : Integer.parseInt(row[11].toString()));
					record.setBblocked(row[12] == null ? null : Integer.parseInt(row[12].toString()));
					record.setSurvey_creator(row[13] == null ? null : row[13].toString());
					record.setSurvey_created(row[14] == null ? null : (Timestamp) row[14]);
					record.setLoked_user(row[15] == null ? null : row[15].toString());
					record.setStart_date(row[16] == null ? null : (Timestamp) row[16]);
					record.setPersonnel_id(row[17] == null ? null : Integer.parseInt(row[17].toString()));
					record.setOperator_src(row[18] == null ? null : row[18].toString());
					record.setSurvey_stat_descr(row[19] == null ? null : row[19].toString());
					surveryList[i] = record;
					i++;
				}
			}

			System.out.println("12121212121212121212121212121212");

			serverSession.setSurveryList(surveryList);
			serverSession.setAbonentName(abonentName);
			serverSession.setMachineIP(ipAddress);
			serverSession.setPhone(realPhone);
			serverSession.setPhoneDescription(phoneDescription);
			serverSession.setSessionId(sessionId);
			serverSession.setGender(gender);
			serverSession.setNon_charge_remark(non_charge_remark);
			serverSession.setAbonentVisible(abonentVisible.equals(75100L) ? true : false);
			serverSession.setTreatment(treatment);
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
			serverSession.setOrgSubsCall(orgSubsCall);

			System.out.println("InitAppServlet. Incomming Session ID : " + sessionId + ", userName = " + userName + ", phone = " + phone);

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
			callSession.setOrgSubsCall(orgSubsCall);

			oracleManager.persist(callSession);
			serverSession.setCallSession(callSession);

			System.out.println("131313131313131313131313133131313131313131");

			HttpSession session = this.getThreadLocalRequest().getSession();
			ServerSession prevSession = (ServerSession) session.getAttribute("prevSession");
			if (prevSession != null) {
				serverSession.setPrevSession(prevSession);
			}
			session.setAttribute(sessionId, serverSession);
			session.setAttribute("prevSession", serverSession);

			System.out.println("================================== Session Initialized ==================================");

			time = System.currentTimeMillis() - time;
			System.out.println("Servlet Initialize Time Is : " + time + " MilliSecond.");

			EMF.commitTransaction(tx);
			return serverSession;
		} catch (Exception e) {
			EMF.rollbackTransaction(tx);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			throw new CallCenterException("შეცდომა ინფორმაციის წამოღებისას : " + e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public ServerSession endCallSession(String phone, ServerSession serverSession) throws CallCenterException {
		EntityManager oracleManager = null;
		Object tx = null;
		try {
			long time = System.currentTimeMillis();
			System.out.println("11111111111111111111");

			oracleManager = EMF.getEntityManager();
			tx = EMF.getTransaction(oracleManager);

			Long rejectType = serverSession.getRejectType();
			Long callDuration = serverSession.getSipCallDuration();
			Long switchOverType = serverSession.getSwitchOverType();
			if (rejectType == null) {
				rejectType = 0L;
			}
			if (callDuration == null) {
				callDuration = 0L;
			}
			if (switchOverType == null) {
				switchOverType = 0L;
			}
			String sessionId = "NO_SESSION";
			CallSession callSession = serverSession.getCallSession();
			if (callSession != null && callSession.getSession_id() != null) {
				sessionId = callSession.getSession_id();
			}
			oracleManager.createNativeQuery(QueryConstants.Q_UPDATE_CALL_SESSION).setParameter(1, rejectType).setParameter(2, callDuration).setParameter(3, switchOverType).setParameter(4, sessionId)
					.executeUpdate();

			time = System.currentTimeMillis() - time;
			System.out.println("Session Update Time Is : " + time + " MilliSecond.");

			EMF.commitTransaction(tx);
			return serverSession;
		} catch (Exception e) {
			EMF.rollbackTransaction(tx);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			throw new CallCenterException("შეცდომა ინფორმაციის წამოღებისას : " + e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

}
