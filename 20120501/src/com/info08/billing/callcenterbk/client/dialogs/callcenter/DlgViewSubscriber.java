package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.CanvasDisableTimer;
import com.info08.billing.callcenterbk.client.common.components.ChargePanel;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgViewSubscriber extends MyWindow {

	private VLayout mainLayout;

	private ListGridRecord listGridRecord;
	private ChargePanel chargePanel;
	private boolean smsSend = false;
	private ToolStripButton sendAbonentInfoSMS;

	public DlgViewSubscriber(ListGrid listGrid, DataSource dataSource,
			ListGridRecord listGridRecord) {
		super();
		this.listGridRecord = listGridRecord;

		setTitle(CallCenterBK.constants.findAbonent());

		setHeight(370);
		setWidth(950);
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setPadding(10);

		chargePanel = new ChargePanel(950, true, true,
				Constants.serviceAbonentInfo,
				listGridRecord.getAttributeAsInt("organization_id"),
				listGridRecord.getAttributeAsString("family_name") + " "
						+ listGridRecord.getAttributeAsString("name"));
		mainLayout.addMember(chargePanel);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		sendAbonentInfoSMS = new ToolStripButton(
				CallCenterBK.constants.abonentInfoSMS(), "sms.png");
		sendAbonentInfoSMS.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendAbonentInfoSMS);

		final DetailViewer detailViewer = new DetailViewer();
		detailViewer.setCanSelectText(true);
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setHeight100();

		dataSource.getField("address").setHidden(false);

		DetailViewerField firstname = new DetailViewerField("name",
				CallCenterBK.constants.name());
		DetailViewerField lastname = new DetailViewerField("family_name",
				CallCenterBK.constants.lastName());
		DetailViewerField city = new DetailViewerField("town_name",
				CallCenterBK.constants.town());
		DetailViewerField address = new DetailViewerField("concat_address",
				CallCenterBK.constants.address());
		DetailViewerField phone = new DetailViewerField("shown_phones",
				CallCenterBK.constants.phone());
		DetailViewerField is_parallel_descr = new DetailViewerField(
				"phone_contract_type_desc", CallCenterBK.constants.status());

		detailViewer.viewSelectedData(listGrid);

		detailViewer.setFields(firstname, lastname, city, address, phone,
				is_parallel_descr);
		mainLayout.addMember(detailViewer);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton cancItem = new IButton();
		cancItem.setTitle(CallCenterBK.constants.close());
		cancItem.setWidth(100);

		hLayoutItem.setMembers(cancItem);

		mainLayout.addMember(hLayoutItem);

		cancItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroyDlg();
			}
		});
		addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				destroyDlg();
			}
		});

		sendAbonentInfoSMS.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendAbonentInfoSMS();
			}
		});

		addItem(mainLayout);
	}

	private void destroyDlg() {
		try {
			final DlgViewSubscriber dlgViewAbonent = this;
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				destroy();
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				destroy();
				return;
			}
			boolean isMobile = serverSession.isPhoneIsMobile();
			if (isMobile) {
				if (!smsSend) {
					SC.ask(CallCenterBK.constants.warning(),
							CallCenterBK.constants.smsDidNotSent(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										dlgViewAbonent.destroy();
									}
								}
							});
				} else {
					destroy();
				}
			} else {
				int chCount = chargePanel.getChrgCounter();
				if (chCount <= 0) {
					SC.ask(CallCenterBK.constants.warning(),
							CallCenterBK.constants.chargeDidNotMade(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										dlgViewAbonent.destroy();
									}
								}
							});
				} else {
					destroy();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sendAbonentInfoSMS() {
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
			CanvasDisableTimer.addCanvasClickTimer(sendAbonentInfoSMS);
			StringBuilder sms_text = new StringBuilder();

			sms_text.append(listGridRecord.getAttributeAsString("name"))
					.append(" ");
			sms_text.append(listGridRecord.getAttributeAsString("family_name"))
					.append(";");
			String address = listGridRecord
					.getAttributeAsString("concat_address");

			if (address != null && !address.trim().equals("")) {
				sms_text.append(address).append("; ");
			}

			String pPhone = listGridRecord.getAttributeAsString("shown_phones");
			if (pPhone != null && !pPhone.trim().equals("")) {
				int phLength = pPhone.length();
				if (pPhone.startsWith("2") && phLength == 7) {
					pPhone = "032" + pPhone;
				} else if (pPhone.startsWith("79")) {
					pPhone = "0" + pPhone;
				} else if (phLength == 9
						&& (pPhone.startsWith("3") || pPhone.startsWith("4"))) {
					pPhone = "0" + pPhone;
				}

				sms_text.append(pPhone).append(" ; ");
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam
					.setAttribute("service_id", Constants.serviceAbonentInfo);
			recordParam
					.setAttribute("session_id", serverSession.getSessionId());
			recordParam.setAttribute("sms_text", sms_text.toString());
			recordParam.setAttribute("phone", phone);
			recordParam.setAttribute("rec_user", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "LogSMS");
			logSessChDS.addData(recordParam, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					smsSend = true;
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
