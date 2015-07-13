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

public class DlgViewDistBetweenTowns extends MyWindow {

	private VLayout mainLayout;

	private ListGridRecord listGridRecord;
	private ChargePanel chargePanel;
	private boolean smsSend = false;
	private ToolStripButton sendSMS;

	public DlgViewDistBetweenTowns(ListGrid listGrid, DataSource dataSource,
			ListGridRecord listGridRecord) {
		super();
		this.listGridRecord = listGridRecord;

		setTitle(CallCenterBK.constants.distBetweenCities());

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

		String logText = listGridRecord
				.getAttribute("town_distance_type_descr")
				+ " / "
				+ listGridRecord.getAttribute("town_start")
				+ " / "
				+ listGridRecord.getAttribute("town_end")
				+ " / "
				+ listGridRecord.getAttribute("dist_between_towns_value");

		chargePanel = new ChargePanel(950, true, true,
				Constants.serviceCityDistInfo,
				listGridRecord.getAttributeAsInt("organization_id"), logText);
		mainLayout.addMember(chargePanel);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		sendSMS = new ToolStripButton(CallCenterBK.constants.smsCityDistance(),
				"sms.png");
		sendSMS.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS);

		final DetailViewer detailViewer = new DetailViewer();
		detailViewer.setCanSelectText(true);
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setHeight100();

		DetailViewerField town_start = new DetailViewerField("town_start",
				CallCenterBK.constants.townFrom());

		DetailViewerField town_end = new DetailViewerField("town_end",
				CallCenterBK.constants.townTo());

		DetailViewerField town_distance_type_descr = new DetailViewerField(
				"town_distance_type_descr", CallCenterBK.constants.type());

		DetailViewerField dist_between_towns_value = new DetailViewerField(
				"dist_between_towns_value", CallCenterBK.constants.distance());

		DetailViewerField dist_between_towns_remark = new DetailViewerField(
				"dist_between_towns_remark", CallCenterBK.constants.comment());

		detailViewer.viewSelectedData(listGrid);

		detailViewer.setFields(town_start, town_end, town_distance_type_descr,
				dist_between_towns_value, dist_between_towns_remark);

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
			final DlgViewDistBetweenTowns dlgViewIndex = this;
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

			String town_start = listGridRecord
					.getAttributeAsString("town_start");
			if (town_start != null && !town_start.trim().equals("")) {
				sms_text.append(town_start).append("-");
			}
			String town_end = listGridRecord.getAttributeAsString("town_end");
			if (town_end != null && !town_end.trim().equals("")) {
				sms_text.append(town_end).append(" ");
			}
			String dist_between_towns_value = listGridRecord
					.getAttributeAsString("dist_between_towns_value");
			if (dist_between_towns_value != null
					&& !dist_between_towns_value.trim().equals("")) {
				sms_text.append(dist_between_towns_value).append("km.; ");
			}

			String dist_between_towns_remark = listGridRecord
					.getAttributeAsString("dist_between_towns_remark");
			if (dist_between_towns_remark != null
					&& !dist_between_towns_remark.trim().equals("")) {
				sms_text.append(dist_between_towns_remark).append(";");
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id",
					Constants.serviceCityDistInfo);
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
