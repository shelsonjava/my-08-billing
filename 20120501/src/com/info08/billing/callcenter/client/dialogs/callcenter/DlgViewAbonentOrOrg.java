package com.info08.billing.callcenter.client.dialogs.callcenter;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.common.components.CanvasDisableTimer;
import com.info08.billing.callcenter.client.common.components.ChargePanel;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.shared.common.Constants;
import com.info08.billing.callcenter.shared.common.ServerSession;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
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

public class DlgViewAbonentOrOrg extends Window {

	private VLayout mainLayout;

	private ListGridRecord listGridRecord;
	private ChargePanel chargePanel;
	private boolean smsSend = false;
	private ToolStripButton sendSMS;

	public DlgViewAbonentOrOrg(ListGrid listGrid, DataSource dataSource,
			ListGridRecord listGridRecord) {
		this.listGridRecord = listGridRecord;

		setTitle(CallCenter.constants.findByNumber());

		setHeight(390);
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
				Constants.serviceFindByNumberInfo, null);
		mainLayout.addMember(chargePanel);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		sendSMS = new ToolStripButton(CallCenter.constants.smsFindByNumber(),
				"sms.png");
		sendSMS.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS);

		final DetailViewer detailViewer = new DetailViewer();
		detailViewer.setCanSelectText(true);
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setHeight100();

		DetailViewerField fullName = new DetailViewerField("fullName",
				CallCenter.constants.dasaxeleba());

		DetailViewerField city_name_geo = new DetailViewerField(
				"city_name_geo", CallCenter.constants.city());

		DetailViewerField streetName = new DetailViewerField("streetName",
				CallCenter.constants.street());

		DetailViewerField phone = new DetailViewerField("phone",
				CallCenter.constants.phone());

		detailViewer.viewSelectedData(listGrid);

		detailViewer.setFields(fullName, city_name_geo, streetName, phone);

		mainLayout.addMember(detailViewer);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton cancItem = new IButton();
		cancItem.setTitle(CallCenter.constants.close());
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

		sendSMS.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendSMS();
			}
		});
		addItem(mainLayout);
	}

	private void destroyDlg() {
		try {
			final DlgViewAbonentOrOrg dlgViewIndex = this;
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
					SC.ask(CallCenter.constants.warning(),
							CallCenter.constants.smsDidNotSent(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										dlgViewIndex.destroy();
									}
								}
							});
				} else {
					destroy();
				}
			} else {
				int chCount = chargePanel.getChrgCounter();
				if (chCount <= 0) {
					SC.ask(CallCenter.constants.warning(),
							CallCenter.constants.chargeDidNotMade(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										dlgViewIndex.destroy();
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

	private void sendSMS() {
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
			CanvasDisableTimer.addCanvasClickTimer(sendSMS);
			StringBuilder sms_text = new StringBuilder();

			String fullName = listGridRecord.getAttributeAsString("fullName");
			if (fullName != null && !fullName.trim().equals("")) {
				sms_text.append(fullName).append(" ");
			}
			String streetName = listGridRecord
					.getAttributeAsString("streetName");
			if (streetName != null && !streetName.trim().equals("")) {
				sms_text.append(streetName).append(" ");
			}

			String pPhone = listGridRecord.getAttributeAsString("phone");
			Integer hide = listGridRecord.getAttributeAsInt("hide");
			if (pPhone != null && !pPhone.trim().equals("")
					&& (hide == null || !hide.equals(1))) {
				sms_text.append("032");
				sms_text.append(pPhone).append(" ");
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id",
					Constants.serviceFindByNumberInfo);
			recordParam
					.setAttribute("session_id", serverSession.getSessionId());
			recordParam.setAttribute("sms_text", sms_text.toString());
			recordParam.setAttribute("phone", phone);
			recordParam.setAttribute("rec_user", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			
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
