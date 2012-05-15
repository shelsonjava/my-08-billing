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

public class DlgViewEvent extends Window {

	private VLayout mainLayout;

	private ListGridRecord listGridRecord;
	private ChargePanel chargePanel;
	private boolean smsSend = false;
	private ToolStripButton sendSMS;
	private ToolStripButton sendSMS1;

	public DlgViewEvent(final DataSource dataSource,
			ListGridRecord listGridRecord) {
		this.listGridRecord = listGridRecord;

		setTitle(CallCenterBK.constants.poster());

		setHeight(400);
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

		StringBuilder disc_txt = new StringBuilder();
		String event_owner_name_txt = listGridRecord
				.getAttributeAsString("event_owner_name");
		if (event_owner_name_txt != null) {
			disc_txt.append(event_owner_name_txt).append(" ");
		}

		String itemname_txt = listGridRecord.getAttributeAsString("event_list_name");
		if (itemname_txt != null && !itemname_txt.trim().equals("")) {
			disc_txt.append(itemname_txt).append(" ");
		}
		String itemdate_txt = listGridRecord.getAttributeAsString("event_list_date");
		if (itemdate_txt != null && !itemdate_txt.trim().equals("")) {
			disc_txt.append(itemdate_txt).append(" ");
		}
		String price_txt = listGridRecord.getAttributeAsString("event_list_price");
		if (price_txt != null && !price_txt.trim().equals("")) {
			disc_txt.append(price_txt).append(" ");
		}

		chargePanel = new ChargePanel(950, true, true,
				Constants.servicePosterInfo, null, 3, disc_txt.toString());
		mainLayout.addMember(chargePanel);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		sendSMS = new ToolStripButton(
				CallCenterBK.constants.smsPosterCurrentRow(), "sms.png");
		sendSMS.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS);

		toolStrip.addFill();

		sendSMS1 = new ToolStripButton(
				CallCenterBK.constants.smsPosterCurrentDay(), "sms.png");
		sendSMS1.setLayoutAlign(Alignment.LEFT);
		sendSMS1.setTitleStyle("fontRed");
		toolStrip.addButton(sendSMS1);

		final DetailViewer detailViewer = new DetailViewer();
		detailViewer.setCanSelectText(true);
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setHeight100();

		DetailViewerField event_owner_name = new DetailViewerField(
				"event_owner_name", CallCenterBK.constants.entPosterCategory());

		DetailViewerField itemdate = new DetailViewerField("event_list_date",
				CallCenterBK.constants.date());

		DetailViewerField itemname = new DetailViewerField("event_list_name",
				CallCenterBK.constants.poster());

		DetailViewerField price = new DetailViewerField("event_list_price",
				CallCenterBK.constants.price());

		DetailViewerField fullinfo = new DetailViewerField("event_list_info",
				CallCenterBK.constants.comment());

		detailViewer.selectRecord(listGridRecord);
		ListGridRecord arr[] = new ListGridRecord[1];
		arr[0] = listGridRecord;
		detailViewer.setData(arr);

		detailViewer.setFields(event_owner_name, itemdate, itemname, price,
				fullinfo);

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
				sendCurrRowSMS();
			}
		});
		sendSMS1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendCurrDaySMS(dataSource);
			}
		});

		addItem(mainLayout);
	}

	private void destroyDlg() {
		try {
			final DlgViewEvent dlgViewIndex = this;
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

	private void sendCurrDaySMS(DataSource dataSource) {
		try {
			final ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			final String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.phoneIsNotMobile());
				return;
			}
			CanvasDisableTimer.addCanvasClickTimer(sendSMS1);

			Criteria criteria = new Criteria();
			criteria.setAttribute("event_list_id",
					listGridRecord.getAttributeAsInt("event_list_id"));
			criteria.setAttribute("event_list_time",
					listGridRecord.getAttributeAsDate("event_list_time"));

			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchAllEventForCallCenter");
			dataSource.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					if (records != null && records.length > 0) {
						sendBatchPosterSMS(records, serverSession, phone);
					}
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sendBatchPosterSMS(Record[] records,
			ServerSession serverSession, String phone) {
		try {
			StringBuilder sms_text = new StringBuilder();

			int i = 0;

			for (Record record : records) {
				if (i == 0) {
					String event_owner_name = record
							.getAttributeAsString("event_owner_name");
					if (event_owner_name != null
							&& !event_owner_name.trim().equals("")) {
						sms_text.append(event_owner_name).append(";");
					}
					String new_itm_date = record
							.getAttributeAsString("event_list_n_date");
					if (new_itm_date != null && !new_itm_date.trim().equals("")) {
						sms_text.append(new_itm_date).append(";");
					}
				}

				String new_itm_date_time = record.getAttributeAsString("event_list_n_time");
				if (new_itm_date_time != null && !new_itm_date_time.trim().equals("")) {
					sms_text.append(new_itm_date_time).append("-");
				}
				
				String itemname = record.getAttributeAsString("event_list_name");
				if (itemname != null && !itemname.trim().equals("")) {
					sms_text.append(itemname).append(" ");
				}
				
				String price = record.getAttributeAsString("event_list_price");
				if (price != null && !price.trim().equals("")) {
					sms_text.append(price).append(";");
				}
				i++;
			}
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id", Constants.servicePosterInfo);
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

	private void sendCurrRowSMS() {
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

			String event_owner_name = listGridRecord
					.getAttributeAsString("event_owner_name");
			if (event_owner_name != null && !event_owner_name.trim().equals("")) {
				sms_text.append(event_owner_name).append(";");
			}

			String itemname = listGridRecord.getAttributeAsString("event_list_name");
			if (itemname != null && !itemname.trim().equals("")) {
				sms_text.append(itemname).append(";");
			}
			String itemdate = listGridRecord.getAttributeAsString("event_list_date");
			if (itemdate != null && !itemdate.trim().equals("")) {
				sms_text.append(itemdate).append(";");
			}
			String price = listGridRecord.getAttributeAsString("event_list_price");
			if (price != null && !price.trim().equals("")) {
				sms_text.append(price).append(";");
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id", Constants.servicePosterInfo);
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
