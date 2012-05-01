package com.info08.billing.callcenterbk.client.common.components;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgAddMyMobBase;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgSendDiscovery;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewChargesByPhone;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
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
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ChargePanel extends HLayout {

	private DynamicForm dynamicForm;
	private DynamicForm dynamicForm1;
	private DynamicForm dynamicForm2;

	private TextItem userNameItem;
	private TextItem currentNumberItem;
	private TextItem currentNameItem;
	private TextItem prevNumberItem;
	private TextItem prevNameItem;
	private Label chargeCounter;
	private boolean enableChargeButton;
	private boolean enableDiscoveryButton;

	private ToolStripButton addMyMobileInfo;
	private ToolStripButton viewChargeInfo;
	private ToolStripButton discoveryBtn;
	private ToolStripButton chargeBtn;
	private Integer service_id;
	private Integer main_id;
	private int chrgCounter = 0;

	public ChargePanel(int width, boolean enableChargeButton,
			boolean enableDiscoveryButton, Integer service_id, Integer main_id,
			final Integer discoveryTypeId, final String discovery_txt) {

		try {
			this.service_id = service_id;
			this.main_id = main_id;
			this.enableChargeButton = enableChargeButton;
			this.enableDiscoveryButton = enableDiscoveryButton;

			Long persTypeId = CommonSingleton.getInstance().getSessionPerson()
					.getPersonelTypeId();
			boolean isOperator = (persTypeId != null && persTypeId.equals(9L));

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

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(false);
			dynamicForm.setWidth100();
			dynamicForm.setNumCols(2);
			dynamicForm.setTitleWidth(120);
			vLayout.addMember(dynamicForm);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setPadding(4);
			toolStrip.setWidth((aWidth - 16));
			vLayout.addMember(toolStrip);

			vLayout.addMember(hLayout);

			addMyMobileInfo = new ToolStripButton(
					CallCenter.constants.addMobSubsInfo(), "addIcon.png");
			addMyMobileInfo.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(addMyMobileInfo);

			toolStrip.addSeparator();

			viewChargeInfo = new ToolStripButton(
					CallCenter.constants.viewChargeInfo(), "moneySmall.png");
			viewChargeInfo.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(viewChargeInfo);

			toolStrip.addSeparator();

			discoveryBtn = new ToolStripButton(
					CallCenter.constants.discovery(), "discovery.png");
			discoveryBtn.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(discoveryBtn);

			toolStrip.addSeparator();
			toolStrip.addFill();

			chargeBtn = new ToolStripButton(CallCenter.constants.charge(),
					"moneySmall.png");
			chargeBtn.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(chargeBtn);
			toolStrip.addSeparator();

			chargeCounter = new Label(CallCenter.constants.charges() + " : 0");
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
			userNameItem.setTitle(CallCenter.constants.username());
			userNameItem.setCanEdit(false);
			userNameItem.setValue(CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			userNameItem.setCanFocus(false);

			currentNumberItem = new TextItem();
			currentNumberItem.setWidth(175);
			currentNumberItem.setName("current_number");
			currentNumberItem.setTitle(CallCenter.constants.currentphone());
			currentNumberItem.setCanEdit(false);
			currentNumberItem.setValue("");
			currentNumberItem.setCanFocus(false);

			currentNameItem = new TextItem();
			currentNameItem.setWidth("100%");
			currentNameItem.setName("current_name");
			currentNameItem.setTitle(CallCenter.constants.currentname());
			currentNameItem.setCanEdit(false);
			currentNameItem.setValue("");
			currentNameItem.setCanFocus(false);

			prevNumberItem = new TextItem();
			prevNumberItem.setWidth(175);
			prevNumberItem.setName("prev_number");
			prevNumberItem.setTitle(CallCenter.constants.prevphone());
			prevNumberItem.setCanEdit(false);
			prevNumberItem.setValue("");
			prevNumberItem.setCanFocus(false);

			prevNameItem = new TextItem();
			prevNameItem.setWidth("100%");
			prevNameItem.setName("prev_name");
			prevNameItem.setTitle(CallCenter.constants.prevname());
			prevNameItem.setCanEdit(false);
			prevNameItem.setValue("");
			prevNameItem.setCanFocus(false);

			drawDynamicPanel(isOperator);

			hLayout.addMember(dynamicForm1);
			hLayout.addMember(dynamicForm2);

			dynamicForm.setFields(userNameItem);

			dynamicForm1.setFields(currentNumberItem, prevNumberItem);
			dynamicForm2.setFields(currentNameItem, prevNameItem);

			chargeBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					makeCharge();
				}
			});

			discoveryBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					sendDiscovery(discoveryTypeId, discovery_txt);
				}
			});

			addMyMobileInfo.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					addMyMobile();
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

	public ChargePanel(int width, boolean enableChargeButton,
			boolean enableDiscoveryButton, Integer service_id, Integer main_id) {
		this(width, enableChargeButton, enableDiscoveryButton, service_id,
				main_id, null, null);
	}

	private void drawDynamicPanel(boolean isOperator) {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession != null && !serverSession.isWebSession()) {

				chargeCounter.setContents(CallCenter.constants.charges()
						+ " : " + serverSession.getChcount());

				String phone = serverSession.getPhone();
				String phoneDescription = serverSession.getPhoneDescription();
				String abonentName = serverSession.getAbonentName();
				Long sex = serverSession.getSex();
				// currentNameItem.setTextBoxStyle("textBoxStyleWoman");
				if (sex.equals(0L)
						|| (phoneDescription != null && !phoneDescription
								.trim().equalsIgnoreCase(""))) {
					currentNameItem.setTextBoxStyle("textBoxStyleWoman");
				} else if (sex.equals(1L)) {
					currentNameItem.setTextBoxStyle("textBoxStyleMan");
				}
				// if (phoneDescription != null
				// && !phoneDescription.trim().equalsIgnoreCase("")
				// && abonentName != null
				// && !abonentName.trim().equalsIgnoreCase("")) {
				// currentNameItem.setValue(phoneDescription + " - "
				// + abonentName);
				// }
				if (phoneDescription != null
						&& !phoneDescription.trim().equalsIgnoreCase("")) {
					currentNameItem.setValue(abonentName + ", "
							+ phoneDescription);
				} else if (abonentName != null
						&& !abonentName.trim().equalsIgnoreCase("")) {
					currentNameItem.setValue(abonentName);
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
							&& !prevAbonentName.trim().equalsIgnoreCase("")) {
						prevNameItem.setValue(prevAbonentName);
					}
				}
			}
			chargeBtn.setVisible(enableChargeButton);
			chargeCounter.setVisible(enableChargeButton);
			discoveryBtn.setVisible(enableDiscoveryButton);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void viewChargesInfo() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			if (serverSession.isPhoneIsMobile()) {
				SC.say(CallCenter.constants.phoneIsMobile());
				return;
			}
			DlgViewChargesByPhone dlgViewChargesByPhone = new DlgViewChargesByPhone();
			dlgViewChargesByPhone.show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void addMyMobile() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenter.constants.phoneIsNotMobile());
				return;
			}
			DlgAddMyMobBase addMyMobBase = new DlgAddMyMobBase();
			addMyMobBase.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DlgSendDiscovery dlgSendDiscovery = null;

	public void setDlgSendDiscovery(DlgSendDiscovery dlgSendDiscovery) {
		this.dlgSendDiscovery = dlgSendDiscovery;
	}

	private void sendDiscovery(Integer discoveryTypeId, String discovery_txt) {
		try {
			if (dlgSendDiscovery == null) {
				dlgSendDiscovery = new DlgSendDiscovery(this, discoveryTypeId,
						discovery_txt);
				dlgSendDiscovery.show();
			} else {
				dlgSendDiscovery.show();
			}

			// ServerSession serverSession = CommonSingleton.getInstance()
			// .getServerSession();
			// if (serverSession == null || serverSession.isWebSession()) {
			// SC.say(CallCenter.constants.notCallCenterUser());
			// return;
			// }
			// String isTest = com.google.gwt.user.client.Window.Location
			// .getParameter("gwt.codesvr");
			// String url = "";
			// if (isTest != null && !isTest.trim().equals("")) {
			// url =
			// "http://192.168.1.3:8888/CallCenter.html?gwt.codesvr=192.168.1.3:9997&isDisc=yes&sessionId="
			// + serverSession.getSessionId()
			// + "&discTypeId="
			// + discoveryTypeId + "&discoveryTxt=" + discovery_txt;
			// } else {
			// url =
			// "http://192.168.1.5:18080/CallCenter/CallCenter.html?sessionId="
			// + serverSession.getSessionId()
			// + "&isDisc=yes&discTypeId="
			// + discoveryTypeId
			// + "&discoveryTxt=" + discovery_txt;
			// }
			//
			// Window.open(url, "DiscoverySentWindow", null);
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

	private void makeCharge() {
		try {

			final ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}

			if (!phone.startsWith("570") && serverSession.isPhoneIsMobile()) {
				SC.say(CallCenter.constants.chargeImpPhoneIsMobile());
				return;
			}

			if (service_id == null || service_id.intValue() < 0) {
				SC.say(CallCenter.constants.invalidService());
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
			record.setAttribute("main_id", main_id);
			record.setAttribute("loggedUserName", serverSession.getUserName());

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
					chargeCounter.setContents(CallCenter.constants.charges()
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

	public void refreshChargeCounterContent() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				return;
			}
			int chCount = serverSession.getChcount();
			chargeCounter.setContents(CallCenter.constants.charges() + " : "
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
