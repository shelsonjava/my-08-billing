package com.info08.billing.callcenterbk.server.servlets.sip;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.ServletTimer;
import javax.servlet.sip.SipApplicationSession;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipSession;
import javax.servlet.sip.SipSession.State;
import javax.servlet.sip.SipURI;
import javax.servlet.sip.TimerListener;

import org.apache.log4j.Logger;

public class CallCenterServlet extends SipServlet implements TimerListener {
	private static Logger logger = Logger.getLogger(CallCenterServlet.class);
	private static final long serialVersionUID = 1L;

	@Resource
	SipFactory sipFactory;

	HashMap<SipSession, SipSession> sessions = new HashMap<SipSession, SipSession>();

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		logger.info("the simple sip servlet has been started");
		super.init(servletConfig);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doInvite(SipServletRequest request) throws ServletException,
			IOException {
		try {
			logger.info("doInvite Called. SipServletRequest [ "
					+ request.toString() + " ] ");
			request.getSession().setAttribute("lastRequest", request);
			if (logger.isInfoEnabled()) {
				logger.info("Simple Servlet: Got request:\n"
						+ request.getMethod());
			}
			SipServletRequest outRequest = sipFactory.createRequest(request
					.getApplicationSession(), "INVITE", request.getFrom()
					.getURI(), request.getTo().getURI());
			String user = ((SipURI) request.getTo().getURI()).getUser();
			logger.info("Invite User = " + user);
			//Address calleeAddress = registeredUsersToIp.get(user);
			Address calleeAddress = registeredUsersToIp.values().iterator().next();
			logger.info("calleeAddress = " + calleeAddress);
			if (calleeAddress == null) {
				request.createResponse(SipServletResponse.SC_NOT_FOUND).send();
				return;
			}
			logger.info("111111111111111111111111111");
			outRequest.setRequestURI(calleeAddress.getURI());
			if (request.getContent() != null) {
				outRequest.setContent(request.getContent(),
						request.getContentType());
			}
			logger.info("22222222222222222222");
			outRequest.send();
			sessions.put(request.getSession(), outRequest.getSession());
			sessions.put(outRequest.getSession(), request.getSession());
			logger.info("333333333333333333333");

		} catch (Exception e) {
			logger.error("Error On doInvite: ", e);
			if (e instanceof ServletException) {
				throw (ServletException) e;
			} else if (e instanceof IOException) {
				throw (IOException) e;
			}
		}
	}

	@Override
	protected void doAck(SipServletRequest request) throws ServletException,
			IOException {
		logger.info("doAck Called. SipServletRequest [ " + request.toString()
				+ " ] ");

		SipServletResponse response = (SipServletResponse) sessions.get(
				request.getSession()).getAttribute("lastResponse");
		response.createAck().send();
		SipApplicationSession sipApplicationSession = request
				.getApplicationSession();
		// Defaulting the sip application session to 1h
		sipApplicationSession.setExpires(60);
	}

	HashMap<String, Address> registeredUsersToIp = new HashMap<String, Address>();

	@Override
	protected void doRegister(SipServletRequest request)
			throws ServletException, IOException {
		logger.info("doRegister Called. SipServletRequest [ "
				+ request.toString() + " ] ");
		Address addr = request.getAddressHeader("Contact");
		SipURI sipUri = (SipURI) addr.getURI();
		registeredUsersToIp.put(sipUri.getUser(), addr);
		if (logger.isInfoEnabled()) {
			logger.info("Address registered " + addr);
		}
		SipServletResponse sipServletResponse = request
				.createResponse(SipServletResponse.SC_OK);
		sipServletResponse.send();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doBye(SipServletRequest request) throws ServletException,
			IOException {
		logger.info("doBye Called. SipServletRequest [ " + request.toString()
				+ " ] ");
		request.getSession().setAttribute("lastRequest", request);
		if (logger.isInfoEnabled()) {
			logger.info("SimpleProxyServlet: Got BYE request:\n" + request);
		}
		sessions.get(request.getSession()).createRequest("BYE").send();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doResponse(SipServletResponse response)
			throws ServletException, IOException {
		logger.info("doResponse Called. SipServletResponse [ "
				+ response.toString() + " ] ");

		if (logger.isInfoEnabled()) {
			logger.info("SimpleProxyServlet: Got response:\n" + response);
		}
		response.getSession().setAttribute("lastResponse", response);
		SipServletRequest request = (SipServletRequest) sessions.get(
				response.getSession()).getAttribute("lastRequest");
		SipServletResponse resp = request.createResponse(response.getStatus());
		if (response.getContent() != null) {
			resp.setContent(response.getContent(), response.getContentType());
		}
		resp.send();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.sip.TimerListener#timeout(javax.servlet.sip.ServletTimer)
	 */
	@Override
	public void timeout(ServletTimer servletTimer) {
		logger.info("timeout Called. servletTimer [ " + servletTimer.toString()
				+ " ] ");
		SipSession sipSession = servletTimer.getApplicationSession()
				.getSipSession((String) servletTimer.getInfo());
		if (!State.TERMINATED.equals(sipSession.getState())) {
			try {
				sipSession.createRequest("BYE").send();
			} catch (IOException e) {
				logger.error(
						"An unexpected exception occured while sending the BYE",
						e);
			}
		}
	}

	@Override
	protected void doOptions(SipServletRequest req) throws ServletException,
			IOException {
		try {
			logger.info("doOptions Called. Resuest [ " + req.toString() + " ] ");			
			req.createResponse(SipServletResponse.SC_OK).send();
		} catch (Exception e) {
			logger.error("Error On doOptions: ", e);
			if (e instanceof ServletException) {
				throw (ServletException) e;
			} else if (e instanceof IOException) {
				throw (IOException) e;
			}
		}
	}

}