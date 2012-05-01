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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgViewIndex extends Window {

	private VLayout mainLayout;

	private ListGridRecord listGridRecord;
	private ChargePanel chargePanel;
	private boolean smsSend = false;
	private ToolStripButton sendCountryInfoSMS;
	private ToolStripButton sendCountryInfoSMS1;

	public DlgViewIndex(ListGrid listGrid, DataSource dataSource,
			ListGridRecord listGridRecord) {
		this.listGridRecord = listGridRecord;

		setTitle(CallCenterBK.constants.codes());

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

		chargePanel = new ChargePanel(950, true, true,
				Constants.serviceCodesInfo,
				listGridRecord.getAttributeAsInt("main_id"));
		mainLayout.addMember(chargePanel);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		sendCountryInfoSMS = new ToolStripButton(
				CallCenterBK.constants.smsIndexInfo(), "sms.png");
		sendCountryInfoSMS.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendCountryInfoSMS);

		sendCountryInfoSMS1 = new ToolStripButton(
				CallCenterBK.constants.smsIndexInfo1(), "sms.png");
		sendCountryInfoSMS1.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendCountryInfoSMS1);

		final DetailViewer detailViewer = new DetailViewer();
		detailViewer.setCanSelectText(true);
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();
		detailViewer.setHeight100();

		dataSource.getField("cityName").setHidden(false);
		dataSource.getField("countrycode").setHidden(false);
		dataSource.getField("countryregion").setHidden(false);
		dataSource.getField("code").setHidden(false);
		dataSource.getField("gmtoff").setHidden(false);
		dataSource.getField("gmtoffwinter").setHidden(false);
		dataSource.getField("ctm").setHidden(false);
		dataSource.getField("country_name_geo").setHidden(false);

		DetailViewerField cityName = new DetailViewerField("cityName",
				CallCenterBK.constants.city());
		DetailViewerField countrycode = new DetailViewerField("countrycode",
				CallCenterBK.constants.countryCode());
		DetailViewerField countryregion = new DetailViewerField(
				"countryregion", CallCenterBK.constants.region());
		DetailViewerField code = new DetailViewerField("code",
				CallCenterBK.constants.oldCode());
		DetailViewerField city_new_code = new DetailViewerField(
				"city_new_code", CallCenterBK.constants.newCode());

		DetailViewerField gmtoff = new DetailViewerField("gmtoff",
				CallCenterBK.constants.gmtoff());
		DetailViewerField gmtoffwinter = new DetailViewerField("gmtoffwinter",
				CallCenterBK.constants.gmtoffwinter());
		DetailViewerField ctm = new DetailViewerField("ctm",
				CallCenterBK.constants.ctm());

		detailViewer.viewSelectedData(listGrid);

		detailViewer.setFields(cityName, countrycode, countryregion,
				city_new_code, code, gmtoff, gmtoffwinter, ctm);
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

		sendCountryInfoSMS.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendCountryInfoSMS();
			}
		});

		sendCountryInfoSMS1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendCountryInfoSMS1();
			}
		});

		addItem(mainLayout);
	}

	private void destroyDlg() {
		try {
			final DlgViewIndex dlgViewIndex = this;
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

	private void sendCountryInfoSMS() {
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
			CanvasDisableTimer.addCanvasClickTimer(sendCountryInfoSMS);
			StringBuilder sms_text = new StringBuilder();

			String country_name_geo = listGridRecord
					.getAttributeAsString("country_name_geo");
			if (country_name_geo != null && !country_name_geo.trim().equals("")) {
				sms_text.append(country_name_geo).append(" ");
			}

			String countrycode = listGridRecord
					.getAttributeAsString("countrycode");
			if (countrycode != null && !countrycode.trim().equals("")) {
				sms_text.append(countrycode).append(";");
			}

			String cityName = listGridRecord
					.getAttributeAsString("city_name_geo");
			if (cityName != null && !cityName.trim().equals("")) {
				sms_text.append(cityName).append(" ");
			}
			String city_new_code = listGridRecord
					.getAttributeAsString("city_new_code");
			if (city_new_code != null && !city_new_code.trim().equals("")) {
				sms_text.append(city_new_code);
			}
			Integer seasonid = listGridRecord.getAttributeAsInt("seasonid");

			if (seasonid != null) {
				if (seasonid.equals(0)) {
					Integer gmtoffwinter = listGridRecord
							.getAttributeAsInt("gmtoffwinter");
					if (gmtoffwinter != null) {
						sms_text.append("zafxulis dro:").append(gmtoffwinter)
								.append(";");
					}
					String ctm = listGridRecord.getAttributeAsString("ctm");
					if (ctm != null && !ctm.trim().equals("")) {
						sms_text.append("am droit:").append(ctm).append(";");
					}
				} else if (seasonid.equals(1)) {
					Integer gmtoffwinter = listGridRecord
							.getAttributeAsInt("gmtoffwinter");
					if (gmtoffwinter != null) {
						sms_text.append("zamtris dro:").append(gmtoffwinter)
								.append(";");
					}
					String ctm = listGridRecord.getAttributeAsString("ctm");
					if (ctm != null && !ctm.trim().equals("")) {
						sms_text.append("am droit:").append(ctm).append(";");
					}
				}
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id", Constants.serviceCodesInfo);
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

	private void sendCountryInfoSMS1() {
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
			CanvasDisableTimer.addCanvasClickTimer(sendCountryInfoSMS1);
			StringBuilder sms_text = new StringBuilder();

			String country_name_geo = listGridRecord.getAttributeAsString("country_name_geo");
			if (country_name_geo != null && !country_name_geo.trim().equals("")) {
				sms_text.append(country_name_geo).append("-");
			}
			
			String cityName = listGridRecord.getAttributeAsString("city_name_geo");
			if (cityName != null && !cityName.trim().equals("")) {
				sms_text.append(cityName).append(";");
			}
			
			Integer seasonid = listGridRecord.getAttributeAsInt("seasonid");

			if (seasonid != null) {
				if (seasonid.equals(0)) {
					Integer gmtoffwinter = listGridRecord
							.getAttributeAsInt("gmtoffwinter");
					if (gmtoffwinter != null) {
						sms_text.append("zafxulis dro:").append(gmtoffwinter)
								.append(";");
					}
					String ctm = listGridRecord.getAttributeAsString("ctm");
					if (ctm != null && !ctm.trim().equals("")) {
						sms_text.append("am droit:").append(ctm).append(";");
					}
				} else if (seasonid.equals(1)) {
					Integer gmtoffwinter = listGridRecord
							.getAttributeAsInt("gmtoffwinter");
					if (gmtoffwinter != null) {
						sms_text.append("zamtris dro:").append(gmtoffwinter)
								.append(";");
					}
					String ctm = listGridRecord.getAttributeAsString("ctm");
					if (ctm != null && !ctm.trim().equals("")) {
						sms_text.append("am droit:").append(ctm).append(";");
					}
				}
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id", Constants.serviceCodesInfo);
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
