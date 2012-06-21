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

public class DlgViewStreetInfo extends Window {

	private VLayout hLayout;
	private ToolStripButton sendAddressInfoSMS;
	private ChargePanel chargePanel;
	private boolean smsSend = false;
	private ListGridRecord pRecord;
	private ChargePanel paramChargePanel;

	public DlgViewStreetInfo(ListGridRecord pRecord, DataSource dataSource,
			ChargePanel paramChargePanel) {
		this.pRecord = pRecord;
		this.paramChargePanel = paramChargePanel;
		setTitle(CallCenterBK.constants.addressInfoTitle());

		setHeight(450);
		setWidth(950);
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		hLayout = new VLayout(5);
		hLayout.setWidth100();
		hLayout.setHeight100();
		hLayout.setPadding(10);

		chargePanel = new ChargePanel(950, true, true,
				Constants.serviceAddressInfo,
				pRecord.getAttributeAsInt("organization_id"));
		hLayout.addMember(chargePanel);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		hLayout.addMember(toolStrip);

		sendAddressInfoSMS = new ToolStripButton(
				CallCenterBK.constants.orgSMSStreetInfo(), "sms.png");
		sendAddressInfoSMS.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendAddressInfoSMS);

		final DetailViewer detailViewer = new DetailViewer();
		detailViewer.setCanSelectText(true);
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setHeight100();
		detailViewer.selectRecord(pRecord);
		ListGridRecord arr[] = new ListGridRecord[1];
		arr[0] = pRecord;
		detailViewer.setData(arr);

		hLayout.addMember(detailViewer);

		DetailViewerField real_address = new DetailViewerField("street_name",
				CallCenterBK.constants.address());
		DetailViewerField town_name = new DetailViewerField("town_name",
				CallCenterBK.constants.town());
		DetailViewerField town_district_name = new DetailViewerField(
				"town_district_name", CallCenterBK.constants.cityRegion());
		DetailViewerField street_location = new DetailViewerField(
				"street_location", CallCenterBK.constants.streetDescr());
		DetailViewerField index_text = new DetailViewerField("index_text",
				CallCenterBK.constants.streetIdx());
		DetailViewerField street_old_name = new DetailViewerField(
				"street_old_name", CallCenterBK.constants.streetOldName());

		detailViewer.setFields(town_name, town_district_name, real_address,
				street_old_name, street_location, index_text);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton cancItem = new IButton();
		cancItem.setTitle(CallCenterBK.constants.close());
		cancItem.setWidth(100);

		hLayoutItem.setMembers(cancItem);

		hLayout.addMember(hLayoutItem);

		addItem(hLayout);

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

		sendAddressInfoSMS.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendAddressSMS();
			}
		});
	}

	private void sendAddressSMS() {
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
			CanvasDisableTimer.addCanvasClickTimer(sendAddressInfoSMS);
			StringBuilder sms_text = new StringBuilder();

			String real_address = pRecord.getAttributeAsString("call_center_address");
			sms_text.append(real_address).append(" ");

			String index_text = pRecord.getAttributeAsString("index_text");
			if (index_text != null && !index_text.trim().equalsIgnoreCase("")) {
				sms_text.append(index_text).append(" ");
			}

			String town_district_name = pRecord
					.getAttributeAsString("town_district_name");

			if (town_district_name != null
					&& !town_district_name.trim().equals("")) {
				sms_text.append(town_district_name).append(" ");
			}

			String street_location = pRecord
					.getAttributeAsString("street_location");
			if (street_location != null
					&& !street_location.trim().equalsIgnoreCase("")) {
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

	private void destroyDlg() {
		try {
			final DlgViewStreetInfo dlgViewStreetInfo = this;
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
										dlgViewStreetInfo.destroy();
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
										dlgViewStreetInfo.destroy();
									}
								}
							});
				} else {
					if (paramChargePanel != null) {
						paramChargePanel.refreshChargeCounterContent();
					}
					destroy();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
