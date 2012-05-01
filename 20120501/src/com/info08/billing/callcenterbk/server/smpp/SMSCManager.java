/*
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.info08.billing.callcenterbk.server.smpp;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.MessageType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.Session;
import org.jsmpp.session.SessionStateListener;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.InvalidDeliveryReceiptException;
import org.jsmpp.util.TimeFormatter;

/**
 * @author paata
 * 
 */
public class SMSCManager implements MessageReceiverListener {

	private Logger logger = Logger.getLogger(SMSCManager.class.getName());

	private TimeFormatter timeFormatter = new AbsoluteTimeFormatter();
	private String serverIPAddress;
	private int serverPort;
	private String systemId;
	private String password;
	private int reconnectInterval;
	private String sourceAddr;
	private TypeOfNumber typeOfNumber;
	private NumberingPlanIndicator numberingPlanIndicator;

	private SMPPSession smppSession;
	private RegisteredDelivery registeredDelivery;

	public SMSCManager(String serverIPAddress, int serverPort, String systemId,
			String password, int reconnectInterval, String sourceAddr,
			TypeOfNumber typeOfNumber,
			NumberingPlanIndicator numberingPlanIndicator) {
		this.serverIPAddress = serverIPAddress;
		this.serverPort = serverPort;
		this.systemId = systemId;
		this.password = password;
		this.reconnectInterval = reconnectInterval;
		this.sourceAddr = sourceAddr;
		this.typeOfNumber = typeOfNumber;
		this.numberingPlanIndicator = numberingPlanIndicator;
	}

	public void connectAndBind() throws Exception {
		BasicConfigurator.configure();
		smppSession = new SMPPSession();
		try {
			smppSession.connectAndBind(serverIPAddress, serverPort,
					new BindParameter(BindType.BIND_TX, this.systemId,
							this.password, sourceAddr, typeOfNumber,
							numberingPlanIndicator, null));
			smppSession.setTransactionTimer(10000);
			registeredDelivery = new RegisteredDelivery();
			registeredDelivery
					.setSMSCDeliveryReceipt(SMSCDeliveryReceipt.SUCCESS_FAILURE);

			smppSession.addSessionStateListener(new SessionStateListenerImpl());

		} catch (IOException e) {
			logger.error("Failed connect and bind to host : ", e);
			throw e;
		} catch (Exception e) {
			logger.error("Failed connect and bind to host (Unknown Error) : ",
					e);
			throw e;
		}
	}

	public void onAcceptDeliverSm(DeliverSm deliverSm)
			throws ProcessRequestException {
		if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) {
			try {
				DeliveryReceipt delReceipt = deliverSm
						.getShortMessageAsDeliveryReceipt();
				long id = Long.parseLong(delReceipt.getId()) & 0xffffffff;
				String messageId = Long.toString(id, 16).toUpperCase();
				logger.info("Receiving delivery receipt for message '"
						+ messageId + "' : " + delReceipt);
			} catch (InvalidDeliveryReceiptException e) {
				logger.error("Failed getting delivery receipt : ", e);
			}
		} else {
			logger.error("Receiving message : "
					+ new String(deliverSm.getShortMessage()));
		}
	}

	public DataSmResult onAcceptDataSm(DataSm dataSm, Session source)
			throws ProcessRequestException {
		logger.info("onAcceptDataSm ... ");
		return null;
	}

	public void onAcceptAlertNotification(AlertNotification alertNotification) {
		logger.info("onAcceptAlertNotification ... ");
	}

	public String sendMessage(String destNumber, String messageText)
			throws PDUException, ResponseTimeoutException,
			InvalidResponseException, NegativeResponseException, IOException,
			Exception {
		try {
			System.out.println("++++++++++++++++++++++++++++++++++++");
			System.out.println("sourceAddr = "+sourceAddr+", destNumber = "+destNumber);
			String messageId = smppSession.submitShortMessage("CMT",
					TypeOfNumber.ALPHANUMERIC,
                    NumberingPlanIndicator.UNKNOWN,
					sourceAddr, 
					TypeOfNumber.INTERNATIONAL,
					NumberingPlanIndicator.UNKNOWN, 
					destNumber, 
					new ESMClass(),
					(byte) 0, (byte) 1, 
					timeFormatter.format(new Date()), 
					null,
					registeredDelivery,
					(byte) 0, 
					new GeneralDataCoding(false,true, MessageClass.CLASS1, Alphabet.ALPHA_DEFAULT),
					(byte) 0, 
					messageText.getBytes());

			logger.info("Sms Message Sent. MessageId = " + messageId);
			return messageId;
		} catch (PDUException e) {
			logger.error("Invalid PDU parameter : ", e);
			throw e;
		} catch (ResponseTimeoutException e) {
			logger.error("Response timeout : ", e);
			throw e;
		} catch (InvalidResponseException e) {
			logger.error("Receive invalid respose : ", e);
			throw e;
		} catch (NegativeResponseException e) {
			logger.error("Receive negative response : ", e);
			throw e;
		} catch (IOException e) {
			logger.error("IO error occur : ", e);
			throw e;
		} catch (Exception e) {
			logger.error("Unkwnown error occur : ", e);
			throw e;
		}
	}

	/**
	 * Reconnect session after specified interval.
	 * 
	 * @param timeInMillis
	 *            is the interval.
	 */
	private void reconnectAfter(final long timeInMillis) {
		new Thread() {
			@Override
			public void run() {
				logger.info("Schedule reconnect after " + timeInMillis
						+ " millis");
				try {
					Thread.sleep(timeInMillis);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int attempt = 0;
				while (smppSession == null
						|| smppSession.getSessionState().equals(
								SessionState.CLOSED)) {
					try {
						logger.info("Reconnecting attempt #" + (++attempt)
								+ "...");
						connectAndBind();
					} catch (Exception e) {
						logger.error("Failed opening connection and bind to "
								+ serverIPAddress + ":" + serverPort, e);
						// wait for a second
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ee) {
							ee.printStackTrace();
						}
					}
				}
			}
		}.start();
	}

	private class SessionStateListenerImpl implements SessionStateListener {
		public void onStateChange(SessionState newState, SessionState oldState,
				Object source) {
			if (newState.equals(SessionState.CLOSED)) {
				logger.info("Session closed");
				reconnectAfter(reconnectInterval);
			}
		}
	}
}
