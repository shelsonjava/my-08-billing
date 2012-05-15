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
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.Users;
import com.isomorphic.jpa.EMF;

@SuppressWarnings("serial")
public class CommonServiceImpl extends RemoteServiceServlet implements
		CommonService {

	private Logger logger = Logger.getLogger(CommonServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Override
	public ServerSession login(String userName, String user_password,
			String sessionId) throws CallCenterException {
		EntityManager oracleManager = null;
		Object tx = null;
		try {
			HttpSession session = this.getThreadLocalRequest().getSession();
			if (session != null && sessionId != null
					&& !sessionId.trim().equalsIgnoreCase("")) {
				ServerSession currSession = (ServerSession) session
						.getAttribute(sessionId);
				if (currSession != null) {
					return currSession;
				}
			}

			oracleManager = EMF.getEntityManager();
			tx = EMF.getTransaction(oracleManager);

			String message = "User Identification. Username = " + userName
					+ ", Password = " + user_password;
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

			Query q = oracleManager.createNamedQuery("Users.selectByUserName")
					.setParameter("usrName", userName);
			@SuppressWarnings("unchecked")
			ArrayList<Users> users = (ArrayList<Users>) q.getResultList();

			if (users == null || users.isEmpty()) {
				throw new CallCenterException("მომხმარებელი: " + userName
						+ " ვერ მოიძებნა.");
			}
			if (users.size() != 1) {
				throw new CallCenterException("ასეთი მომხმარებელი: " + userName
						+ " მონაცემთა ბაზაში რამოდენიმეა.");
			}
			Users user = users.get(0);

			String dbPassword = user.getUser_password();
			if (!user_password.equals(dbPassword)) {
				throw new CallCenterException(
						"მომხმარებელის პაროლი არასწორია !");
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
			EMF.commitTransaction(tx);

			ServerSession serverSession = new ServerSession();
			serverSession.setMachineIP(this.getThreadLocalRequest()
					.getRemoteAddr());
			serverSession.setWebSession(true);
			String mySessionId = oracleManager
					.createNativeQuery(QueryConstants.Q_GET_WEB_SESSION_ID)
					.getSingleResult().toString();
			serverSession.setSessionId(sessionId);
			serverSession.setUser(user);
			serverSession.setUserName(userName);

			session.setAttribute(mySessionId, serverSession);

			return serverSession;
		} catch (Exception e) {
			EMF.rollbackTransaction(tx);
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			e.printStackTrace();
			throw new CallCenterException("შეცდომა სისტემაში შესვლისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@Override
	public String findSessionMp3ById(String sessionId, Date sessionDate)
			throws CallCenterException {
		HttpClient httpClient = null;
		PostMethod method = null;
		BufferedReader br = null;
		try {
			String log = "Method:findSessionMp3ById. Params : 1. SessionId = "
					+ sessionId + " 2. sessionDate = " + sessionDate;
			if (sessionId == null || sessionId.trim().equals("")) {
				log += ". Result : Invalid SessionId";
				logger.info(log);
				throw new CallCenterException(
						"არასწორი სესიის იდენტიფიკატორი : " + sessionId);
			}
			if (sessionDate == null) {
				log += ". Result : Invalid StartDate";
				logger.info(log);
				throw new CallCenterException("არასწორი სესიის თარიღი : "
						+ sessionDate);
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			String formatedDate = simpleDateFormat.format(sessionDate);
			httpClient = new HttpClient();
			String fullUrl = Constants.sessionFilesUrl;
			method = new PostMethod(fullUrl);
			method.addParameter("sid", "A" + sessionId);
			method.addParameter("date", formatedDate);

			int returnCode = httpClient.executeMethod(method);
			log += ". fullURL = " + fullUrl;

			br = new BufferedReader(new InputStreamReader(
					method.getResponseBodyAsStream()));
			String response = br.readLine();

			log += ". Response = " + response;

			if (returnCode != HttpStatus.SC_OK) {
				log += ". Result : Invalid Result From Url.";
				logger.info(log);
				throw new CallCenterException(
						"არასწორი სესიის მისამართი, შეცდომის კოდი : "
								+ returnCode);
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CallCenterException("შეცდომა ფაილის მოძებნისას : "
					+ e.toString());
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

	// Log Personal Notes
	@SuppressWarnings("rawtypes")
	@Override
	public void saveOrUpdateLogPersNote(Integer noteId, String sessionId,
			Integer visOpt, String note, Integer particular, Users person)
			throws CallCenterException {
		EntityManager oracleManager = null;
		Object tx = null;
		try {

			String log = "Method:saveOrUpdateLogPersNote. Params : 1. SessionId = "
					+ sessionId
					+ " 2. noteId = "
					+ noteId
					+ ", visOpt = "
					+ visOpt + ", note = " + note;
			if (sessionId == null || sessionId.trim().equals("")) {
				log += ". Result : Invalid SessionId";
				logger.info(log);
				throw new CallCenterException(
						"არასწორი სესიის იდენტიფიკატორი : " + sessionId);
			}
			oracleManager = EMF.getEntityManager();
			tx = EMF.getTransaction(oracleManager);

			if (oracleManager == null) {
				log += ". Result : Can't get oracle manager from pool";
				logger.info(log);
				throw new CallCenterException(
						"სისტემური შეცდომა მონაცემთა ბაზასთა კავშირის დროს");
			}

			if (tx == null) {
				log += ". Result : Can't get transaction from transaction manager";
				logger.info(log);
				throw new CallCenterException(
						"სისტემური შეცდომა მონაცემთა ბაზასთა კავშირის დროს 1.");
			}
			// sysdate
			Timestamp recDate = new Timestamp(System.currentTimeMillis());

			// new note.
			if (noteId == null) {
				List result = oracleManager
						.createNativeQuery(QueryConstants.Q_GET_SESSION_BY_ID)
						.setParameter(1, sessionId).getResultList();
				if (result == null || result.size() != 1) {
					log += ". Result : Invalid Session Id From Database: "
							+ sessionId;
					logger.info(log);
					throw new CallCenterException(
							"არასწორი სესიის იდენტიფიკატორი");
				}
				log += ". Inserting Into Database. ";

				Object cols[] = (Object[]) result.get(0);
				Integer ym = new Integer(cols[0] == null ? "-1"
						: cols[0].toString());
				String userName = cols[1] == null ? "" : cols[1].toString();
				String recUser = person.getUser_name();
				String phone = cols[3] == null ? "" : cols[3].toString();
				Timestamp callDate = (Timestamp) cols[2];
				Query query = oracleManager
						.createNativeQuery(QueryConstants.Q_INSERT_PERS_NOTES);
				query = query.setParameter(1, ym);
				query = query.setParameter(2, sessionId);
				query = query.setParameter(3, userName);
				query = query.setParameter(4, note);
				query = query.setParameter(5, recUser);
				query = query.setParameter(6, recDate);
				query = query.setParameter(7, visOpt);
				query = query.setParameter(8, phone);
				query = query.setParameter(9, callDate);
				query = query.setParameter(10, particular);
				query.executeUpdate();
				log += ". Inserting Finished SuccessFully. ";
				logger.info(log);
			} else {
				// edit note
				log += ". Updating Database. ";
				List result = oracleManager
						.createNativeQuery(QueryConstants.Q_GET_PERS_NOTE_BY_ID)
						.setParameter(1, noteId).getResultList();
				if (result == null || result.size() != 1) {
					log += ". Result : Invalid Note Id From Database: "
							+ noteId;
					logger.info(log);
					throw new CallCenterException(
							"არასწორი შენიშვნის ჩანაწერის იდენტიფიკატორი");
				}
				Integer noteIdDb = new Integer(result.get(0).toString());
				Query query = oracleManager
						.createNativeQuery(QueryConstants.Q_UPDATE_PERS_NOTES);
				query = query.setParameter(1, note);
				query = query.setParameter(2, visOpt);
				query = query.setParameter(3, particular);
				query = query.setParameter(4, noteIdDb);
				query.executeUpdate();
				log += ". Updating Finished Successfully. ";
				logger.info(log);
			}

			EMF.commitTransaction(tx);
		} catch (Exception e) {
			if (tx != null) {
				EMF.rollbackTransaction(tx);
			}
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert Personal Note Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@Override
	public void deleteLogPersNote(Integer noteId) throws CallCenterException {
		EntityManager oracleManager = null;
		Object tx = null;
		try {
			String log = "Method:deleteLogPersNote. Params : 2. noteId = "
					+ noteId;
			if (noteId == null) {
				log += ". Result : Invalid NoteId";
				logger.info(log);
				throw new CallCenterException(
						"არასწორი ჩანაწერის იდენტიფიკატორი : " + noteId);
			}
			oracleManager = EMF.getEntityManager();
			tx = EMF.getTransaction(oracleManager);

			if (oracleManager == null) {
				log += ". Result : Can't get oracle manager from pool";
				logger.info(log);
				throw new CallCenterException(
						"სისტემური შეცდომა მონაცემთა ბაზასთა კავშირის დროს");
			}

			if (tx == null) {
				log += ". Result : Can't get transaction from transaction manager";
				logger.info(log);
				throw new CallCenterException(
						"სისტემური შეცდომა მონაცემთა ბაზასთა კავშირის დროს 1.");
			}
			log += ". Deleting Record From Database. ";
			oracleManager.createNativeQuery(QueryConstants.Q_DELETE_PERS_NOTES)
					.setParameter(1, noteId).executeUpdate();
			log += ". Deleting Record From Database Finished Successfully. ";
			logger.info(log);
			EMF.commitTransaction(tx);
		} catch (Exception e) {
			if (tx != null) {
				EMF.rollbackTransaction(tx);
			}
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While Insert Personal Note Into Database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების შენახვისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@Override
	public void getBillingCompBillByMonth(Integer billing_company_id, Integer ym)
			throws CallCenterException {
		EntityManager oracleManager = null;
		try {

		} catch (Exception e) {
			if (e instanceof CallCenterException) {
				throw (CallCenterException) e;
			}
			logger.error("Error While getting cvs from database : ", e);
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getBillingCompBillByDay(Integer billing_company_id,
			Date date_param) throws CallCenterException {
		EntityManager oracleManager = null;
		try {
			oracleManager = EMF.getEntityManager();

			List<String[]> retResult = new ArrayList<String[]>();

			List resultList = oracleManager
					.createNativeQuery(
							QueryConstants.Q_GET_BILLING_COMP_BILL_BY_DAY)
					.setParameter(1, new Timestamp(date_param.getTime()))
					.setParameter(2, new Timestamp(date_param.getTime()))
					.setParameter(3, billing_company_id).getResultList();
			if (resultList != null && !resultList.isEmpty()) {
				for (Object row : resultList) {
					Object record[] = (Object[]) row;
					String rowData[] = new String[6];
					String phoneA = (record[0] == null) ? "" : record[0]
							.toString();
					String phoneB = (record[1] == null) ? "" : record[1]
							.toString();
					String charge_date1 = (record[3] == null) ? "" : record[3]
							.toString();
					Double duration = (record[4] == null) ? new Double(0)
							: new Double(record[4].toString());
					;
					Double rate = (record[5] == null) ? new Double(0)
							: new Double(record[5].toString());
					Double price = (record[6] == null) ? new Double(0)
							: new Double(record[6].toString());
					;
					rowData[0] = phoneA;
					rowData[1] = phoneB;
					rowData[2] = charge_date1;
					rowData[3] = duration.toString();
					rowData[4] = rate.toString();
					rowData[5] = price.toString();
					retResult.add(rowData);
				}
			}
			String filename = (File.separator + System.getProperty("user.home")
					+ File.separator + System.currentTimeMillis() + ".csv");
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
			resp.setHeader("Content-Disposition",
					"attachment; filename*=\"utf-8''" + filename + "");

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
			throw new CallCenterException("შეცდომა მონაცემების წამოღებისას : "
					+ e.toString());
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}
}
