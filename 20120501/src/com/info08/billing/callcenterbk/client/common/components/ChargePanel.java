package com.info08.billing.callcenterbk.client.common.components;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgAddEditTreatments;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgSendSurvey;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewChargesByPhone;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.callcenter.Treatments;
import com.info08.billing.callcenterbk.shared.entity.session.CallSession;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ChargePanel extends HLayout {

	private DynamicForm dynamicForm;
	private DynamicForm dynamicForm1;
	private DynamicForm dynamicForm2;
	private DynamicForm dynamicForm3;

	private TextItem userNameItem;
	private TextItem operatorItem;
	private TextItem currentNumberItem;
	private TextItem currentNameItem;
	private TextItem prevNumberItem;
	private TextItem prevNameItem;
	private Label chargeCounter;
	private boolean enableChargeButton;
	private boolean enableSurveyButton;

	private ToolStripButton addMyMobileInfo;
	private ToolStripButton viewChargeInfo;
	private ToolStripButton surveyBtn;
	private ToolStripButton makeImportantCall;
	
	
	private ToolStripButton makeCall;

	private ToolStripButton chargeBtn;
	private Integer service_id;
	private Integer organization_id;
	private int chrgCounter = 0;

	private String event_describtion;

	public ChargePanel(int width, boolean enableChargeButton,
			boolean enableSurveyButton, Integer service_id,
			Integer organization_id, final Integer surveyKindId,
			final String survey_descript) {

		try {
			this.service_id = service_id;
			this.organization_id = organization_id;
			this.enableChargeButton = enableChargeButton;
			this.enableSurveyButton = enableSurveyButton;

			Long persTypeId = CommonSingleton.getInstance().getSessionPerson()
					.getDepartment_id();
			boolean isOperator = (persTypeId != null && persTypeId
					.equals(Constants.OperatorDepartmentID));

			int aWidth = (width - 33);
			int cWidth = (aWidth - 314);

			setBorder("2px solid #92abcd");
			setWidth(aWidth);
			setHeight(90);
			setMembersMargin(5);

			VLayout vLayout = new VLayout(5);
			vLayout.setPadding(5);
			vLayout.setWidth100();

			HLayout hLayout = new HLayout();
			hLayout.setWidth100();
			hLayout.setPadding(0);
			hLayout.setMargin(0);

			HLayout hLayout2 = new HLayout();
			hLayout2.setWidth100();

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(false);
			dynamicForm.setWidth(300);
			dynamicForm.setNumCols(2);
			dynamicForm.setTitleWidth(120);
			hLayout2.addMember(dynamicForm);

			dynamicForm3 = new DynamicForm();
			dynamicForm3.setAutoFocus(false);
			dynamicForm3.setWidth(cWidth);
			dynamicForm3.setNumCols(2);
			dynamicForm3.setTitleWidth(100);
			hLayout2.addMember(dynamicForm3);

			vLayout.addMember(hLayout2);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setPadding(4);
			toolStrip.setWidth((aWidth - 16));
			vLayout.addMember(toolStrip);

			vLayout.addMember(hLayout);

			addMyMobileInfo = new ToolStripButton(
					CallCenterBK.constants.addMobSubsInfo(), "addIcon.png");
			addMyMobileInfo.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(addMyMobileInfo);

			toolStrip.addSeparator();

			viewChargeInfo = new ToolStripButton(
					CallCenterBK.constants.viewChargeInfo(), "moneySmall.png");
			viewChargeInfo.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(viewChargeInfo);

			toolStrip.addSeparator();

			surveyBtn = new ToolStripButton(CallCenterBK.constants.survey(),
					"survey.png");
			surveyBtn.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(surveyBtn);

			makeImportantCall = new ToolStripButton(
					CallCenterBK.constants.important(), "important.png");
			makeImportantCall.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(makeImportantCall);

			toolStrip.addSeparator();
			
			makeCall = new ToolStripButton("CALL 01");
			//toolStrip.addButton(makeCall);
			
			makeCall.addClickHandler(new ClickHandler() {				
				@Override
				public void onClick(ClickEvent event) {
					sipCall();
				}
			});
			
			toolStrip.addFill();

			chargeBtn = new ToolStripButton(CallCenterBK.constants.charge(),
					"moneySmall.png");
			chargeBtn.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(chargeBtn);
			toolStrip.addSeparator();

			chargeCounter = new Label(CallCenterBK.constants.charges() + " : 0");
			chargeCounter.setWidth(120);
			chargeCounter.setStyleName("fontRed");
			chargeCounter.setHeight(20);
			chargeCounter.setVisible(isOperator);
			toolStrip.addMember(chargeCounter);

			dynamicForm1 = new DynamicForm();
			dynamicForm1.setAutoFocus(false);
			dynamicForm1.setWidth(300);
			dynamicForm1.setNumCols(2);
			dynamicForm1.setTitleWidth(120);

			dynamicForm2 = new DynamicForm();
			dynamicForm2.setAutoFocus(false);
			dynamicForm2.setWidth(cWidth);
			dynamicForm2.setNumCols(2);
			dynamicForm2.setTitleWidth(100);

			userNameItem = new TextItem();
			userNameItem.setWidth("100%");
			userNameItem.setName("user_name");
			userNameItem.setTitle(CallCenterBK.constants.username());
			userNameItem.setCanEdit(false);
			userNameItem.setValue(CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			userNameItem.setCanFocus(false);

			operatorItem = new TextItem();
			operatorItem.setWidth("100%");
			operatorItem.setName("operatorItem");
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setCanEdit(false);
			String operator = CommonSingleton.getInstance().getServerSession()
					.getOperatorSrc();
			if (operator != null && !operator.trim().equals("")) {
				operatorItem.setValue(operator);
				if (operator.equals(Constants.OPERATOR_800800909)) {
					operatorItem.setTextBoxStyle("textItemRed");
				}
			}
			operatorItem.setCanFocus(false);

			currentNumberItem = new TextItem();
			currentNumberItem.setWidth(178);
			currentNumberItem.setName("current_number");
			currentNumberItem.setTitle(CallCenterBK.constants.currentphone());
			currentNumberItem.setCanEdit(false);
			currentNumberItem.setValue("");
			currentNumberItem.setCanFocus(false);

			currentNameItem = new TextItem();
			currentNameItem.setWidth("100%");
			currentNameItem.setName("current_name");
			currentNameItem.setTitle(CallCenterBK.constants.currentname());
			currentNameItem.setCanEdit(false);
			currentNameItem.setValue("");
			currentNameItem.setCanFocus(false);

			prevNumberItem = new TextItem();
			prevNumberItem.setWidth(178);
			prevNumberItem.setName("prev_number");
			prevNumberItem.setTitle(CallCenterBK.constants.prevphone());
			prevNumberItem.setCanEdit(false);
			prevNumberItem.setValue("");
			prevNumberItem.setCanFocus(false);

			prevNameItem = new TextItem();
			prevNameItem.setWidth("100%");
			prevNameItem.setName("prev_name");
			prevNameItem.setTitle(CallCenterBK.constants.prevname());
			prevNameItem.setCanEdit(false);
			prevNameItem.setValue("");
			prevNameItem.setCanFocus(false);

			drawDynamicPanel(isOperator);

			hLayout.addMember(dynamicForm1);
			hLayout.addMember(dynamicForm2);

			dynamicForm.setFields(userNameItem);
			dynamicForm3.setFields(operatorItem);

			dynamicForm1.setFields(currentNumberItem, prevNumberItem);
			dynamicForm2.setFields(currentNameItem, prevNameItem);

			chargeBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					makeCharge();
				}
			});

			surveyBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					sendSurvey(surveyKindId, survey_descript);
				}
			});

			addMyMobileInfo.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					addMyMobile();
				}
			});

			makeImportantCall.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					makeCallImportant();
				}
			});

			viewChargeInfo.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					viewChargesInfo();
				}
			});
			addMember(vLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	
	public static native void sipCall() /*-{
		$wnd.sipCall("call-audio");		
	}-*/;
	
	public ChargePanel(int width, boolean enableChargeButton,
			boolean enableSurveyButton, Integer service_id,
			Integer organization_id) {
		this(width, enableChargeButton, enableSurveyButton, service_id,
				organization_id, null, null);

	}

	public ChargePanel(int width, boolean enableChargeButton,
			boolean enableSurveyButton, Integer service_id,
			Integer organization_id, String event_describtion) {
		this(width, enableChargeButton, enableSurveyButton, service_id,
				organization_id, null, null);
		this.event_describtion = event_describtion;
		logSessionEvent(event_describtion);

	}

	@SuppressWarnings("rawtypes")
	private void logSessionEvent(String event_describtion) {
		try {
			final ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				return;
			}

			if (service_id == null || service_id.intValue() < 0) {
				SC.say(CallCenterBK.constants.invalidService());
				return;
			}
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("service_id", service_id);
			record.setAttribute("session_id", serverSession.getSessionId());
			record.setAttribute("loggedUserName", serverSession.getUser_name());
			record.setAttribute("event_describtion", event_describtion);

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "LogSessionEvent");
			logSessChDS.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					// int chCount = serverSession.getChcount();
					// chCount++;
					// serverSession.setChcount(chCount);
					// chargeCounter.setContents(CallCenterBK.constants.charges()
					// + " : " + chCount);
					//
					// int lChargeCounter = getChrgCounter();
					// lChargeCounter++;
					// setChrgCounter(lChargeCounter);
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			SC.say(e.toString());
		} finally {
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		}
	}

	@SuppressWarnings("rawtypes")
	private void drawDynamicPanel(boolean isOperator) {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession != null && !serverSession.isWebSession()) {

				chargeCounter.setContents(CallCenterBK.constants.charges()
						+ " : " + serverSession.getChcount());

				String phone = serverSession.getPhone();
				String phoneDescription = serverSession.getPhoneDescription();
				String abonentName = serverSession.getAbonentName();
				Long gender = serverSession.getGender();
				// currentNameItem.setTextBoxStyle("textBoxStyleWoman");

				if (abonentName != null
						&& !abonentName.trim().equalsIgnoreCase("")) {
					addMyMobileInfo.setDisabled(!serverSession
							.isAbonentVisible());
				}

				if (!serverSession.isAbonentVisible()) {
					currentNameItem.setTextBoxStyle("textBoxStyle");
				} else {
					if (gender.equals(74101L)
							|| (phoneDescription != null && !phoneDescription
									.trim().equalsIgnoreCase(""))) {
						currentNameItem.setTextBoxStyle("textBoxStyleWoman");
					} else if (gender.equals(74100L)) {
						currentNameItem.setTextBoxStyle("textBoxStyleMan");
					}
				}
				// if (phoneDescription != null
				// && !phoneDescription.trim().equalsIgnoreCase("")
				// && abonentName != null
				// && !abonentName.trim().equalsIgnoreCase("")) {
				// currentNameItem.setValue(phoneDescription + " - "
				// + abonentName);
				// }
				if (phoneDescription != null
						&& !phoneDescription.trim().equalsIgnoreCase("")
						&& serverSession.isAbonentVisible()) {
					currentNameItem.setValue(abonentName + ", "
							+ phoneDescription);
				} else if (abonentName != null
						&& !abonentName.trim().equalsIgnoreCase("")
						&& serverSession.isAbonentVisible()) {
					currentNameItem.setValue(abonentName);
				} else if (!serverSession.isAbonentVisible()
						&& abonentName != null
						&& !abonentName.trim().equalsIgnoreCase("")) {
					currentNameItem.setValue("დაფარულია");
				}
				currentNumberItem.setValue(phone);

				int callType = serverSession.getCallType();
				if (callType == Constants.callTypeNoncharge) {
					chargeBtn.setDisabled(true);
					chargeCounter.setVisible(false);
				}

				// prev
				ServerSession prevServerSession = serverSession
						.getPrevSession();
				if (prevServerSession != null) {
					String prevPhone = prevServerSession.getPhone();
					String prevAbonentName = prevServerSession.getAbonentName();
					prevNumberItem.setValue(prevPhone);

					if (prevAbonentName != null
							&& !prevAbonentName.trim().equalsIgnoreCase("")
							&& prevServerSession.isAbonentVisible()) {
						prevNameItem.setValue(prevAbonentName);
					}
				}
			}
			chargeBtn.setVisible(enableChargeButton);
			chargeCounter.setVisible(enableChargeButton);
			surveyBtn.setVisible(enableSurveyButton);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	@SuppressWarnings("rawtypes")
	private void makeCallImportant() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("session_id", serverSession.getSessionId());
			record.setAttribute("cse_id", serverSession.getCallSession()
					.getCall_session_id());

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "makeCallImportant");
			DataSource logSessChDS = DataSource.get("LogSessChDS");

			logSessChDS.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					SC.say(CallCenterBK.constants.importantCallSaved());
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private void viewChargesInfo() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.phoneIsMobile());
				return;
			}
			DlgViewChargesByPhone dlgViewChargesByPhone = new DlgViewChargesByPhone();
			dlgViewChargesByPhone.show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	@SuppressWarnings("rawtypes")
	private void addMyMobile() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.phoneIsNotMobile());
				return;
			}
			// TODO
			// DlgAddTreatments dlgAddTreatment = new DlgAddTreatments();
			// dlgAddTreatment.show();

			if (serverSession.getTreatment() == null) {
				String serverSession_phone = serverSession.getPhone();
				Treatments treatments = new Treatments();
				treatments.setPhone_number(serverSession_phone);
				DlgAddEditTreatments dlgAddEditTreatments = new DlgAddEditTreatments(
						new ListGrid(), null, treatments);
				dlgAddEditTreatments.show();
			} else {

				DlgAddEditTreatments dlgAddEditTreatments = new DlgAddEditTreatments(
						new ListGrid(), null, serverSession.getTreatment());
				dlgAddEditTreatments.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DlgSendSurvey dlgSendSurvey = null;

	public void setDlgSendSurvey(DlgSendSurvey dlgSendSurvey) {
		this.dlgSendSurvey = dlgSendSurvey;
	}

	private void sendSurvey(Integer survey_kind_Id, String survey_descript) {
		try {
			if (dlgSendSurvey == null) {
				dlgSendSurvey = new DlgSendSurvey(this, survey_kind_Id,
						survey_descript);
				dlgSendSurvey.show();
			} else {
				dlgSendSurvey.show();
			}

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private int getUnixTimeStamp() {
		Date date = new Date();
		int iTimeStamp = (int) (date.getTime() * .001);
		return iTimeStamp;
	}

	@SuppressWarnings("rawtypes")
	private void makeCharge() {
		try {

			final ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}

			if (!phone.startsWith("570") && serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.chargeImpPhoneIsMobile());
				return;
			}

			if (service_id == null || service_id.intValue() < 0) {
				SC.say(CallCenterBK.constants.invalidService());
				return;
			}
			CallSession callSession = serverSession.getCallSession();
			Long call_session_id = callSession.getCall_session_id();
			Long call_kind = callSession.getCall_kind();
			boolean isFreeOfCharge = serverSession.isFreeOfCharge();
			if (isFreeOfCharge) {
				SC.say(CallCenterBK.constants.freeOfChargeMessage());
				return;
			}

			CanvasDisableTimer.addCanvasClickTimer(chargeBtn);

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			int time = getUnixTimeStamp();
			record.setAttribute("id", new Integer(time));
			record.setAttribute("service_id", service_id);
			record.setAttribute("session_id", serverSession.getSessionId());
			record.setAttribute("ym", serverSession.getYearMonth());
			record.setAttribute("organization_id", organization_id);
			record.setAttribute("loggedUserName", serverSession.getUser_name());
			record.setAttribute("call_session_id", call_session_id);
			record.setAttribute("call_kind", call_kind);
			record.setAttribute("call_phone", phone);
			record.setAttribute("event_describtion", event_describtion);
			record.setAttribute("operator_src",
					Integer.parseInt(serverSession.getOperatorSrc()));

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "addLogSessionCharge");
			logSessChDS.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					int chCount = serverSession.getChcount();
					chCount++;
					serverSession.setChcount(chCount);
					chargeCounter.setContents(CallCenterBK.constants.charges()
							+ " : " + chCount);

					int lChargeCounter = getChrgCounter();
					lChargeCounter++;
					setChrgCounter(lChargeCounter);
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	@SuppressWarnings("rawtypes")
	public void refreshChargeCounterContent() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				return;
			}
			int chCount = serverSession.getChcount();
			chargeCounter.setContents(CallCenterBK.constants.charges() + " : "
					+ chCount);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public int getChrgCounter() {
		return chrgCounter;
	}

	public void setChrgCounter(int chrgCounter) {
		this.chrgCounter = chrgCounter;
	}
}
