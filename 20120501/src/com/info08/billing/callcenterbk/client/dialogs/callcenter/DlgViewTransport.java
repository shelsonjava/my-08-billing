package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.CanvasDisableTimer;
import com.info08.billing.callcenterbk.client.common.components.ChargePanel;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgViewTransport extends Window {

	private VLayout mainLayout;

	private ListGridRecord listGridRecord;
	private ChargePanel chargePanel;
	// private boolean smsSend = false;
	private ToolStripButton sendTransportInfoSMS;

	private ListGrid listGridInner;

	public DlgViewTransport(ListGrid listGrid, DataSource dataSource,
			ListGridRecord listGridRecord, Integer transportTypeId) {
		this.listGridRecord = listGridRecord;

		setTitle(CallCenterBK.constants.transport());

		setHeight(650);
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
				Constants.serviceTransportInfo,
				listGridRecord.getAttributeAsInt("organization_id"));
		mainLayout.addMember(chargePanel);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		sendTransportInfoSMS = new ToolStripButton(
				CallCenterBK.constants.smsCountryInfo(), "sms.png");
		sendTransportInfoSMS.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendTransportInfoSMS);

		final DetailViewer detailViewer = new DetailViewer();
		detailViewer.setCanSelectText(true);
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setHeight(300);

		dataSource.getField("trip_criteria").setHidden(false);
		dataSource.getField("ocity_name_geo").setHidden(false);
		dataSource.getField("icity_name_geo").setHidden(false);
		dataSource.getField("ostation").setHidden(false);
		dataSource.getField("istation").setHidden(false);
		dataSource.getField("out_time").setHidden(false);
		dataSource.getField("in_time").setHidden(false);
		dataSource.getField("cmt").setHidden(false);
		dataSource.getField("transport_company_geo").setHidden(false);
		dataSource.getField("transport_plane_geo").setHidden(false);
		dataSource.getField("note_geo").setHidden(false);
		dataSource.getField("days_descr").setHidden(false);
		dataSource.getField("transport_price_geo").setHidden(false);

		DetailViewerField trip_criteria = new DetailViewerField(
				"trip_criteria", CallCenterBK.constants.routeNumber());

		DetailViewerField ocity_name_geo = new DetailViewerField(
				"ocity_name_geo", CallCenterBK.constants.stationFrom());
		ocity_name_geo.setCellStyle("fontRedWithBorder");

		DetailViewerField icity_name_geo = new DetailViewerField(
				"icity_name_geo", CallCenterBK.constants.stationTo());
		icity_name_geo.setCellStyle("fontGreenWithBorder");

		DetailViewerField ostation = new DetailViewerField("ostation",
				CallCenterBK.constants.station());

		DetailViewerField istation = new DetailViewerField("istation",
				CallCenterBK.constants.station());

		DetailViewerField out_time = new DetailViewerField("c_out_time",
				CallCenterBK.constants.outTime());

		DetailViewerField in_time = new DetailViewerField("c_in_time",
				CallCenterBK.constants.inTime());

		DetailViewerField cmt = new DetailViewerField("cmt",
				CallCenterBK.constants.ctm());
		cmt.setCellStyle("fontGreenWithBorder");

		DetailViewerField transport_company_geo = new DetailViewerField(
				"transport_company_geo",
				CallCenterBK.constants.transportCompShort());

		DetailViewerField transport_plane_geo = new DetailViewerField(
				"transport_plane_geo",
				CallCenterBK.constants.transportTypeShort());

		DetailViewerField note_geo = new DetailViewerField("note_geo",
				CallCenterBK.constants.comment());
		Integer note_crit = listGridRecord.getAttributeAsInt("note_crit");
		if (note_crit != null && note_crit.equals(-1)) {
			note_geo.setCellStyle("fontRedWithBorder");
		}

		DetailViewerField days_descr = new DetailViewerField("days_descr",
				CallCenterBK.constants.days());

		DetailViewerField transport_price_geo = new DetailViewerField(
				"transport_price_geo", CallCenterBK.constants.price());

		detailViewer.viewSelectedData(listGrid);

		detailViewer.setFields(trip_criteria, ocity_name_geo, ostation,
				out_time, icity_name_geo, istation, in_time, cmt,
				transport_company_geo, transport_plane_geo, note_geo,
				days_descr, transport_price_geo);

		mainLayout.addMember(detailViewer);

		Criteria criteria = new Criteria();
		criteria.setAttribute("transport_id",
				listGridRecord.getAttributeAsInt("transport_id"));

		listGridInner = new ListGrid();
		listGridInner.setWidth(500);
		listGridInner.setHeight100();
		listGridInner.setAlternateRecordStyles(true);
		listGridInner.setCriteria(criteria);
		listGridInner.setDataSource(dataSource);
		listGridInner.setAutoFetchData(true);
		listGridInner.setShowFilterEditor(false);
		listGridInner.setCanEdit(false);
		listGridInner.setCanRemoveRecords(false);
		listGridInner.setFetchOperation("searchAllTransportsInner");
		listGridInner.setCanSort(false);
		listGridInner.setCanResizeFields(false);
		listGridInner.setShowFilterEditor(true);
		listGridInner.setFilterOnKeypress(true);

		ListGridField outCity = new ListGridField("outCity",
				CallCenterBK.constants.city());
		outCity.setAlign(Alignment.LEFT);
		outCity.setCanFilter(true);

		ListGridField outPlace = new ListGridField("outPlace",
				CallCenterBK.constants.outPlace());
		outPlace.setAlign(Alignment.LEFT);
		outPlace.setCanFilter(true);

		ListGridField in_timeF = new ListGridField("c_in_time",
				CallCenterBK.constants.inTime());
		in_timeF.setAlign(Alignment.LEFT);
		in_timeF.setCanFilter(false);

		ListGridField out_timeF = new ListGridField("c_out_time",
				CallCenterBK.constants.outTime());
		out_timeF.setAlign(Alignment.LEFT);
		out_timeF.setCanFilter(false);

		if (transportTypeId != null && transportTypeId.equals(2)) {
			listGridInner.setFields(outCity, outPlace, out_timeF);
		} else {
			listGridInner.setFields(outCity, outPlace, in_timeF, out_timeF);
		}
		mainLayout.addMember(listGridInner);

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

		sendTransportInfoSMS.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendTransportInfoSMS();
			}
		});
		addItem(mainLayout);
	}

	private void destroyDlg() {
		try {
			destroy();
			// final DlgViewTransport dlgViewIndex = this;
			// ServerSession serverSession = CommonSingleton.getInstance()
			// .getServerSession();
			// if (serverSession == null || serverSession.isWebSession()) {
			// destroy();
			// return;
			// }
			// String phone = serverSession.getPhone();
			// if (phone == null || phone.trim().equalsIgnoreCase("")) {
			// destroy();
			// return;
			// }
			// boolean isMobile = serverSession.isPhoneIsMobile();
			// if (isMobile) {
			// if (!smsSend) {
			// SC.ask(CallCenter.constants.warning(),
			// CallCenter.constants.smsDidNotSent(),
			// new BooleanCallback() {
			// @Override
			// public void execute(Boolean value) {
			// if (value) {
			// dlgViewIndex.destroy();
			// }
			// }
			// });
			// } else {
			// destroy();
			// }
			// } else {
			// int chCount = chargePanel.getChrgCounter();
			// if (chCount <= 0) {
			// SC.ask(CallCenter.constants.warning(),
			// CallCenter.constants.chargeDidNotMade(),
			// new BooleanCallback() {
			// @Override
			// public void execute(Boolean value) {
			// if (value) {
			// dlgViewIndex.destroy();
			// }
			// }
			// });
			// } else {
			// destroy();
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sendTransportInfoSMS() {
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
			CanvasDisableTimer.addCanvasClickTimer(sendTransportInfoSMS);
			StringBuilder sms_text = new StringBuilder();

			String ocity_name_geo = listGridRecord
					.getAttributeAsString("ocity_name_geo");
			if (ocity_name_geo != null && !ocity_name_geo.trim().equals("")) {
				sms_text.append(ocity_name_geo).append(" ");
			}
			String ostation = listGridRecord.getAttributeAsString("ostation");
			if (ostation != null && !ostation.trim().equals("")) {
				sms_text.append("(").append(ostation).append(")");
			}
			String icity_name_geo = listGridRecord
					.getAttributeAsString("icity_name_geo");
			if (icity_name_geo != null && !icity_name_geo.trim().equals("")) {
				sms_text.append("-").append(icity_name_geo).append(" ");
			}

			String istation = listGridRecord.getAttributeAsString("istation");
			if (istation != null && !istation.trim().equals("")) {
				sms_text.append("(").append(istation).append(");");
			}
			String days_descr = listGridRecord
					.getAttributeAsString("days_descr");
			if (days_descr != null && !days_descr.trim().equals("")) {
				sms_text.append(days_descr).append(";");
			}

			String c_out_time = listGridRecord
					.getAttributeAsString("c_out_time");
			if (c_out_time != null && !c_out_time.trim().equals("")) {
				sms_text.append("gasvla:").append(c_out_time).append(";");
			}
			String c_in_time = listGridRecord.getAttributeAsString("c_in_time");
			if (c_in_time != null && !c_in_time.trim().equals("")) {
				sms_text.append("chasvla:").append(c_in_time).append(";");
			}

			String note_geo = listGridRecord.getAttributeAsString("note_geo");
			if (note_geo != null && !note_geo.trim().equals("")) {
				sms_text.append("komentari:").append(note_geo).append(";");
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id",
					Constants.serviceTransportInfo);
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
					// smsSend = true;
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
