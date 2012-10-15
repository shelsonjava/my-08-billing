package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.CanvasDisableTimer;
import com.info08.billing.callcenterbk.client.common.components.ChargePanel;
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
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgViewStreet extends Window {

	private VLayout mainLayout;

	private ListGridRecord listGridRecord;
	private ChargePanel chargePanel;
	private boolean smsSend = false;
	private ToolStripButton sendSMS;

	public DlgViewStreet(DataSource dataSource, ListGridRecord listGridRecord) {
		this.listGridRecord = listGridRecord;

		setTitle(CallCenterBK.constants.streetInfo());

		setHeight(430);
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

		String logText = listGridRecord.getAttribute("town_name") + " / "
				+ listGridRecord.getAttribute("street_name") + " / "
				+ listGridRecord.getAttribute("street_index");

		chargePanel = new ChargePanel(950, true, true,
				Constants.serviceAddressInfo, null, logText);
		mainLayout.addMember(chargePanel);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		sendSMS = new ToolStripButton(CallCenterBK.constants.smsStreetInfo(),
				"sms.png");
		sendSMS.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS);

		final DetailViewer detailViewer = new DetailViewer();
		detailViewer.setCanSelectText(true);
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setHeight100();

		DetailViewerField town_name = new DetailViewerField("town_name",
				CallCenterBK.constants.town());

		DetailViewerField street_name = new DetailViewerField("street_name",
				CallCenterBK.constants.street());

		DetailViewerField oldName = new DetailViewerField(
				"street_old_name_descr", CallCenterBK.constants.oldStreetName());

		DetailViewerField streetIndex = new DetailViewerField("street_index",
				CallCenterBK.constants.indexes());
		streetIndex.setCellStyle("fontRedWithBorder");

		DetailViewerField streetDistrict = new DetailViewerField(
				"street_to_town_district", CallCenterBK.constants.district());

		DetailViewerField street_location = new DetailViewerField(
				"street_location", CallCenterBK.constants.streetDescr());

		detailViewer.selectRecord(listGridRecord);
		ListGridRecord arr[] = new ListGridRecord[1];
		arr[0] = listGridRecord;
		detailViewer.setData(arr);

		detailViewer.setFields(town_name, street_name, oldName, streetIndex,
				streetDistrict, street_location);

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
			final DlgViewStreet dlgViewIndex = this;
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
					SC.ask(CallCenterBK.constants.warning(),
							CallCenterBK.constants.chargeDidNotMade(),
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
			CanvasDisableTimer.addCanvasClickTimer(sendSMS);
			StringBuilder sms_text = new StringBuilder();

			String street_name = listGridRecord
					.getAttributeAsString("street_name");
			if (street_name != null && !street_name.trim().equals("")) {
				sms_text.append(street_name).append(" ");
			}
			String street_index = listGridRecord
					.getAttributeAsString("street_index");
			if (street_index != null && !street_index.trim().equals("")) {
				sms_text.append(street_index).append(" ");
			}
			String streetDistrict = listGridRecord
					.getAttributeAsString("streetDistrict");
			if (streetDistrict != null && !streetDistrict.trim().equals("")) {
				sms_text.append(streetDistrict).append(" ");
			}
			String street_location = listGridRecord
					.getAttributeAsString("street_location");
			if (street_location != null && !street_location.trim().equals("")) {
				sms_text.append(street_location).append(" ");
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam
					.setAttribute("service_id", Constants.serviceAddressInfo);
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
