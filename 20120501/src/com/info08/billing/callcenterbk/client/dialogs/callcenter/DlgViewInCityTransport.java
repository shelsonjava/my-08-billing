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
import com.smartgwt.client.data.RecordList;
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
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgViewInCityTransport extends Window {

	private VLayout mainLayout;

	private ListGridRecord listGridRecord;
	private ChargePanel chargePanel;
	private boolean smsSend = false;
	private ToolStripButton sendSMS1;
	private ToolStripButton sendSMS2;
	private ToolStripButton sendSMS3;
	private ListGrid listGridInner;
	private DataSource dataSourceInner;

	public DlgViewInCityTransport(DataSource dataSource,
			ListGridRecord listGridRecord, final String fromStreetId,
			final String toStreetId) {
		this.listGridRecord = listGridRecord;

		setTitle(CallCenterBK.constants.tbilisiTransport());

		setHeight(800);
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
				Constants.serviceInCityTransportInfo,
				listGridRecord.getAttributeAsInt("main_id"));
		mainLayout.addMember(chargePanel);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		sendSMS3 = new ToolStripButton(
				CallCenterBK.constants.smsInCityTranspInfo(), "sms.png");
		sendSMS3.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS3);

		sendSMS1 = new ToolStripButton(
				CallCenterBK.constants.smsRouteDirForward(), "sms.png");
		sendSMS1.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS1);

		sendSMS2 = new ToolStripButton(
				CallCenterBK.constants.smsRouteDirBackward(), "sms.png");
		sendSMS2.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS2);

		final DetailViewer detailViewer = new DetailViewer();
		detailViewer.setCanSelectText(true);
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setHeight(120);

		DetailViewerField dir_num = new DetailViewerField("dir_num",
				CallCenterBK.constants.routeNumber());

		DetailViewerField service_descr = new DetailViewerField(
				"service_descr", CallCenterBK.constants.transportType());

		DetailViewerField icity_name_geo = new DetailViewerField(
				"icity_name_geo", CallCenterBK.constants.stationTo());
		icity_name_geo.setCellStyle("fontGreenWithBorder");

		DetailViewerField start_place = new DetailViewerField("start_place",
				CallCenterBK.constants.stationFrom());

		DetailViewerField end_place = new DetailViewerField("end_place",
				CallCenterBK.constants.stationTo());

		DetailViewerField cycle_descr = new DetailViewerField("cycle_descr",
				CallCenterBK.constants.type());

		detailViewer.selectRecord(listGridRecord);
		ListGridRecord arr[] = new ListGridRecord[1];
		arr[0] = listGridRecord;
		detailViewer.setData(arr);

		detailViewer.setFields(dir_num, service_descr, start_place, end_place,
				cycle_descr);

		mainLayout.addMember(detailViewer);

		dataSourceInner = DataSource.get("PubTranspDirStreetDS");

		Criteria criteria = new Criteria();
		criteria.setAttribute("dir_id",
				listGridRecord.getAttributeAsInt("pt_id"));

		listGridInner = new ListGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord countryRecord = (ListGridRecord) record;
				if (countryRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				String street_name = countryRecord
						.getAttributeAsString("street_name");

				if (street_name == null || street_name.trim().equals("")) {
					return super.getCellCSSText(record, rowNum, colNum);
				}

				if (fromStreetId != null && !fromStreetId.trim().equals("")
						&& street_name.contains(fromStreetId)) {
					return "color:red;";
				} else if (toStreetId != null && !toStreetId.trim().equals("")
						&& street_name.contains(toStreetId)) {
					return "color:red;";
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};
		};
		listGridInner.setWidth100();
		listGridInner.setHeight100();
		listGridInner.setAlternateRecordStyles(true);
		listGridInner.setCriteria(criteria);
		listGridInner.setDataSource(dataSourceInner);
		listGridInner.setAutoFetchData(true);
		listGridInner.setShowFilterEditor(false);
		listGridInner.setCanEdit(false);
		listGridInner.setCanRemoveRecords(false);
		listGridInner
				.setFetchOperation("searchAllPublicTransportDirectionstreets");
		listGridInner.setCanSort(false);
		listGridInner.setCanResizeFields(false);

		ListGridField dir_order = new ListGridField("dir_order",
				CallCenterBK.constants.no(), 50);
		dir_order.setAlign(Alignment.LEFT);

		ListGridField street_name = new ListGridField("street_name",
				CallCenterBK.constants.street(), 300);
		street_name.setAlign(Alignment.LEFT);

		ListGridField remarks = new ListGridField("remarks",
				CallCenterBK.constants.description());
		remarks.setAlign(Alignment.LEFT);

		ListGridField dir_descr = new ListGridField("dir_descr",
				CallCenterBK.constants.routeDirDescr(), 150);
		dir_descr.setAlign(Alignment.CENTER);

		listGridInner.setFields(dir_order, dir_descr, street_name, remarks);

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

		sendSMS1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendSMS(1);
			}
		});
		sendSMS2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendSMS(2);
			}
		});
		sendSMS3.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendSMS(3);
			}
		});
		addItem(mainLayout);
	}

	private void destroyDlg() {
		try {
			final DlgViewInCityTransport dlgViewIndex = this;
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

	private void sendSMS(int routeDir) {
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

			if (routeDir == 1) {
				CanvasDisableTimer.addCanvasClickTimer(sendSMS1);
			} else if (routeDir == 2) {
				CanvasDisableTimer.addCanvasClickTimer(sendSMS2);
			} else if (routeDir == 3) {
				CanvasDisableTimer.addCanvasClickTimer(sendSMS3);
			}

			StringBuilder sms_text = new StringBuilder();

			String service_descr = listGridRecord
					.getAttributeAsString("service_descr");
			if (service_descr != null && !service_descr.trim().equals("")) {
				sms_text.append(service_descr).append(" : ");
			}

			String dir_num = listGridRecord.getAttributeAsString("dir_num");
			if (dir_num != null && !dir_num.trim().equals("")) {
				sms_text.append("N").append(dir_num).append(";");
			}

			if (routeDir == 3) {
				String start_place = listGridRecord
						.getAttributeAsString("start_place");
				if (start_place != null && !start_place.trim().equals("")) {
					sms_text.append(start_place).append("-");
				}

				String end_place = listGridRecord
						.getAttributeAsString("end_place");
				if (end_place != null && !end_place.trim().equals("")) {
					sms_text.append(end_place).append(";");
				}
				Integer direction = listGridRecord
						.getAttributeAsInt("direction");
				if (direction != null && direction.intValue() != 1) {
					sms_text.append("tsriuli").append(";");
				}
			} else if (routeDir == 1 || routeDir == 2) {

				RecordList recordList = listGridInner.getDataAsRecordList();
				int length = recordList.getLength();
				if (length <= 0) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.errorRoutePartsNotFound());
					return;
				}
				StringBuilder cust_sms_text = new StringBuilder();
				for (int i = 0; i < length; i++) {
					Record record = recordList.get(i);
					Integer route_dir = record.getAttributeAsInt("dir");
					if (route_dir == null) {
						continue;
					}
					if (route_dir.intValue() != routeDir) {
						continue;
					}
					String street_name = record
							.getAttributeAsString("street_name");
					if (street_name != null && !street_name.trim().equals("")) {
						cust_sms_text.append(street_name.trim()).append(";");
					}
				}
				if (cust_sms_text == null
						|| cust_sms_text.toString().trim().equals("")) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.errorRoutePartsNotFound1());
				}
				String cust_sms = cust_sms_text.toString().trim();
				sms_text.append(cust_sms);
			}
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id",
					Constants.serviceInCityTransportInfo);
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
