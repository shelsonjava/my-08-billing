package com.info08.billing.callcenterbk.server.smpp;

/*
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.MessageType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;

public class SMSCConnection implements MessageReceiverListener, Runnable {

	public static final int CONN_STATUS_CONNECTED = 1;
	public static final int CONN_STATUS_NOT_CONNECTED = 2;

	public static final int CONN_DEF_RECV_TIMEOUT = 20 * 1000;

	private int status = CONN_STATUS_NOT_CONNECTED;

	private String serverIPAddress;
	private int serverPort;
	private String systemId;
	private String password;
	private int interfaceVersion;
	private int enqLinkFreq;
	private int reconnectInterval;
	private String sourceAddr;

	private SMPPSession session;

	private Logger logger;
	private Logger PDULogger;

	private boolean waitingForEnqLinkResp;

	private Thread thread;
	private boolean timeToStop;

	private int sequenceNumber = 0;

	public SMSCConnection(String serverIPAddress, int serverPort,
			String systemId, String password, int interfaceVersion,
			int enqLinkFreq, int reconnectInterval, String sourceAddr,
			Logger logger, Logger PDULogger) {

		this.serverIPAddress = serverIPAddress;
		this.serverPort = serverPort;
		this.systemId = systemId;
		this.password = password;
		this.interfaceVersion = interfaceVersion;
		this.enqLinkFreq = enqLinkFreq;
		this.reconnectInterval = reconnectInterval;
		this.sourceAddr = sourceAddr;
		this.logger = logger;
		this.PDULogger = PDULogger;
	}

	public synchronized int getNextSequence() {
		if (sequenceNumber == 65535)
			sequenceNumber = 1;
		else
			sequenceNumber++;
		return sequenceNumber;
	}

	public String getSourceAddr() {
		return this.sourceAddr;
	}

	private SMPPSession bind() throws Exception {
		try {

			session.connectAndBind(serverIPAddress, serverPort, new BindParameter(
					BindType.BIND_TRX, this.systemId, this.password, "cp",
					TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));

			BindRequest req = null;
			BindResponse resp = null;
			Session newSession = null;

			req = new BindTransciever(); // Application will be a transceiver
			TCPIPConnection conn = // Trying to connect to the SMSC
			new TCPIPConnection(serverIPAddress, serverPort);

			conn.setReceiveTimeout(CONN_DEF_RECV_TIMEOUT);
			newSession = new Session(conn);

			// Prepare a bind request
			req.setSystemId(this.systemId);
			req.setPassword(this.password);
			req.setSystemType(null); // System type is empty
			req.setInterfaceVersion((byte) this.interfaceVersion);
			req.setAddressRange((String) null); // Address range is also set to
												// empty

			this.logger.info("Connecting and binding to the SMSC "
					+ this.serverIPAddress + ":" + this.serverPort + " as "
					+ this.systemId + "...");
			resp = newSession.bind(req, this); // Trying to bind as an SMPP app
			this.logger.info("Bind response received from SMSC "
					+ this.serverIPAddress + ":" + this.serverPort);

			int commandStatus = resp.getCommandStatus();

			if (commandStatus == Data.ESME_ROK) {
				this.logger.info("Connected to SMSC " + this.serverIPAddress
						+ ":" + this.serverPort + " successfully and bound as "
						+ this.systemId);
				setStatus(CONN_STATUS_CONNECTED);
				session = newSession;
			} else {
				if (newSession != null)
					newSession.close();
				this.logger.warn("Unable to connect and/or bind to SMSC "
						+ this.serverIPAddress + ":" + this.serverPort
						+ ". Error code=" + commandStatus);
				setStatus(CONN_STATUS_NOT_CONNECTED);
				session = null;
			}

			return session;
		} catch (Exception e) {
			this.logger.error("Error while bind to SMSC : ", e);
			throw e;
		}
	}

	public int getStatus() {
		return this.status;
	}

	private void setStatus(int status) {
		this.status = status;
	}

	public Logger getLogger() {
		return this.logger;
	}

	public Logger getPDULogger() {
		return this.PDULogger;
	}

	public void start() {

		this.logger.info("Starting SMPP connection...");

		thread = new Thread(this);
		timeToStop = false;
		thread.start();
	}

	public void stop() {
		this.logger.info("Stopping SMPP connection...");
		timeToStop = true;
		try {
			thread.join();
			this.logger.info("SMPP connection stopped");
		} catch (Exception ex) {

		}
	}

	public void send(PDU pdu) throws ValueNotSetException, TimeoutException,
			PDUException, IOException, WrongSessionStateException, Exception {
		try {
			pdu.assignSequenceNumber();

			this.PDULogger.info(pdu);
			if (pdu instanceof SubmitSM) {
				SubmitSMResp response11 = session.submit((SubmitSM) pdu);
				if (response11 != null) {
					String messageId = response11.getMessageId();
					this.PDULogger.info("+++++++++++++++++++++++ messageId = "
							+ messageId);
				}
			} else if (pdu instanceof Response)
				session.respond((Response) pdu);
		} catch (ValueNotSetException e) {
			this.logger.error("ValueNotSetException while sending sms : ", e);
			throw e;
		} catch (TimeoutException e) {
			this.logger.error("TimeoutException while sending sms : ", e);
			throw e;
		} catch (PDUException e) {
			this.logger.error("PDUException while sending sms : ", e);
			throw e;
		} catch (IOException e) {
			this.logger.error("IOException while sending sms : ", e);
			throw e;
		} catch (WrongSessionStateException e) {
			this.logger.error(
					"WrongSessionStateException while sending sms : ", e);
			throw e;
		} catch (Exception e) {
			this.logger.error("UnknownException while sending sms : ", e);
			throw e;
		}
	}

	
	public void onAcceptDeliverSm(DeliverSm deliverSm)
            throws ProcessRequestException {
        if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) {
            // counter.incrementAndGet();
            // delivery receipt
            try {
                DeliveryReceipt delReceipt = deliverSm.getShortMessageAsDeliveryReceipt();
                long id = Long.parseLong(delReceipt.getId()) & 0xffffffff;
                String messageId = Long.toString(id, 16).toUpperCase();
                System.out.println("Receiving delivery receipt for message '" + messageId + "' : " + delReceipt);
            } catch (InvalidDeliveryReceiptException e) {
                System.err.println("Failed getting delivery receipt");
                e.printStackTrace();
            }
        } else {
            // regular short message
            System.out.println("Receiving message : " + new String(deliverSm.getShortMessage()));
        }
    }
    
    public DataSmResult onAcceptDataSm(DataSm dataSm, Session source)
            throws ProcessRequestException {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void onAcceptAlertNotification(
            AlertNotification alertNotification) {
    }
	
	
	public void handleEvent(ServerPDUEvent event) {
		PDU pdu = event.getPDU();
		this.PDULogger.info(pdu);

		if (pdu instanceof SubmitSMResp)
			handleSubmitSMResp((SubmitSMResp) pdu);
		else if (pdu instanceof DeliverSM)
			handleDeliverSM((DeliverSM) pdu);
		else if (pdu instanceof EnquireLinkResp) {
			// This means that enq_link_resp was received
			this.waitingForEnqLinkResp = false;
			// Notify all threads that are waiting that ENQ_LINK_RESP received
			synchronized (this) {
				notify();
			}
		}
	}

	public void run() {
		while (true) {
			if (timeToStop)
				break;
			logger.info("Attempting to start SMPP connection to SMSC...");
			try {
				if (bind() == null) {
					this.logger
							.warn("Unable to connect/bind to SMSC. Will retry in "
									+ this.reconnectInterval
									/ 1000
									+ " seconds...");
					try {
						Thread.sleep(this.reconnectInterval);
					} catch (InterruptedException inex) {
					}
					continue;
				}

			} catch (Exception ex) {
				this.logger
						.error("Unable to connect/bind to SMSC. Will retry in "
								+ this.reconnectInterval / 1000 + " seconds...",
								ex);
				try {
					Thread.sleep(this.reconnectInterval);
				} catch (InterruptedException inex) {
				}
				continue;
			}

			while (true) { // Send an EnqLink with enqLinkFreq frequency
				try {
					Thread.sleep(this.enqLinkFreq);
					if (timeToStop)
						break;
					this.logger.info("Sending ENQ_LINK to SMSC...");

					// EnquireLink enqLink = new EnquireLink();

					session.enquireLink();
					waitingForEnqLinkResp = true;
					synchronized (this) {
						// Wait for 10 seconds
						wait(10000);
					}
					if (waitingForEnqLinkResp) {
						// This means that there is no response
						this.logger
								.warn("No response from SMSC to the latest ENQ_LINK");
						setStatus(CONN_STATUS_NOT_CONNECTED);
					}
				} catch (InterruptedException inex) {
					this.logger.error("InterruptedException : ", inex);
				} catch (ValueNotSetException vnse) {
					this.logger.error("ValueNotSetException : ", vnse);
				} catch (WrongSessionStateException wsse) {
					this.logger.error("WrongSessionStateException : ", wsse);
				} catch (IOException ioex) {
					// This is what we have to deal with: Exception while
					// sending
					this.logger.warn("Got exception while sending ENQ_LINK: "
							+ ioex.toString());
					setStatus(CONN_STATUS_NOT_CONNECTED);
				} catch (TimeoutException toex) {
					this.logger.error("TimeoutException : ", toex);
				} catch (PDUException pduex) {
					this.logger.error("PDUException : ", pduex);
				}

				// If connection break is suspected, try to unbind
				if (getStatus() == CONN_STATUS_NOT_CONNECTED) {
					try {
						this.logger.warn("Trying to unbind....");
						this.session.unbind();
					} catch (Exception ex) {
						// An exception while unbinding: not a big deal
						this.logger.warn("Got exception while unbinding "
								+ ex.toString());
					} finally {
						this.logger
								.warn("Will try to reconnect in "
										+ this.reconnectInterval / 1000
										+ " seconds...");
						try {
							Thread.sleep(this.reconnectInterval);
						} catch (InterruptedException inex) {
						}
						break;
					}
				}
			}
		}
	}

	private void handleSubmitSMResp(SubmitSMResp resp) {
		try {
			this.logger
					.info(" ================ handleSubmitSMResp(SubmitSMResp resp) ... "
							+ resp.getMessageId());
		} catch (Exception e) {
			this.PDULogger.error("Error on handleSubmitSMResp : ", e);
		}
	}

	private void handleDeliverSM(DeliverSM deliverSM) {

		int messageSeq = 0;
		try {

			this.logger
					.info(" ++++++++++++= handleDeliverSM(DeliverSM deliverSM) ... deliverSM = "
							+ deliverSM.getMessageState());

			messageSeq = deliverSM.getSequenceNumber();

			// TibrvMsg m = null;

			if (!deliverSM.hasReceiptedMessageId()) {
				// Prepare and send a TibRV message

				// m = new TibrvMsg();
				//
				// m.add("MESSAGE_TYPE", TibrvMsgHandler.MT_MO);
				// m.add("GW_CONN_HANDLE", this.systemId);
				// m.add("GW_MSG_REF", messageSeq);
				// m.add("ORIGINATING_MSISDN", deliverSM.getSourceAddr()
				// .getAddress());
				// // m.add( "MSG_CONTENT", deliverSM.getBody().getBuffer(),
				// // TibrvMsg.U8ARRAY );
				// m.add("MSG_CONTENT", deliverSM.getShortMessageObj().getData()
				// .getBuffer(), TibrvMsg.U8ARRAY);
				//
				// if (deliverSM.hasUserMessageReference())
				// m.add("MSG_REF", deliverSM.getUserMessageReference());
			} else {
				// TODO: Handle the delivery report message case
				// For now just send the response

				Response resp = deliverSM.getResponse();
				resp.setCommandId(Data.ESME_ROK);

			}
			// if (m != null) {
			// Environment.getInstance().getTibrv().send(m);
			// }
		} catch (Exception ex) {
			this.logger.warn("Error encountered while handling message "
					+ messageSeq + ": " + ex.toString());
			Response resp = deliverSM.getResponse();
			resp.setCommandStatus(Data.ESME_RUNKNOWNERR);

			try {
				this.send(resp);
			} catch (Exception ex2) {
				this.logger.warn("Unable send a NAK to msg " + messageSeq
						+ ": " + ex2.toString());
			}
		}

	}
}
*/