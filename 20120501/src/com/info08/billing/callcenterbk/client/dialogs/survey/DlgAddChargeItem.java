package com.info08.billing.callcenterbk.client.dialogs.survey;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgAddChargeItem extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem serviceItem;
	private TextItem chargeCountItem;

	private IButton chargeButton;
	private ListGridRecord listGridRecord;
	private ListGrid listGrid;
	private DlgAddCharge dlgAddCharge;

	public DlgAddChargeItem(DlgAddCharge dlgAddCharge, ListGrid listGrid,
			DataSource dataSource, ListGridRecord listGridRecord) {
		try {
			this.listGridRecord = listGridRecord;
			this.listGrid = listGrid;
			this.dlgAddCharge = dlgAddCharge;
			setTitle(CallCenterBK.constants.addCharge());

			setHeight(215);
			setWidth(620);
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

			DetailViewer detailViewer = new DetailViewer();
			detailViewer.setCanSelectText(true);
			detailViewer.setDataSource(dataSource);
			detailViewer.setWidth100();
			detailViewer.selectRecord(listGridRecord);

			DetailViewerField phone = new DetailViewerField("call_phone",
					CallCenterBK.constants.phone());

			DetailViewerField start_date = new DetailViewerField(
					"call_start_date", CallCenterBK.constants.time());
			start_date.setDateFormatter(DateDisplayFormat.TOSERIALIZEABLEDATE);

			DetailViewerField operator = new DetailViewerField("uname",
					CallCenterBK.constants.operator());

			DetailViewerField duration = new DetailViewerField("call_duration",
					CallCenterBK.constants.durationShort());

			detailViewer.setFields(phone, start_date, operator, duration);

			hLayout.addMember(detailViewer);

			ListGridRecord arr[] = new ListGridRecord[1];
			arr[0] = listGridRecord;
			detailViewer.setData(arr);

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setNumCols(2);
			dynamicForm.setTitleOrientation(TitleOrientation.TOP);
			hLayout.addMember(dynamicForm);

			serviceItem = new ComboBoxItem();
			serviceItem.setTitle(CallCenterBK.constants.service());
			serviceItem.setType("comboBox");
			serviceItem.setName("serviceItem");
			serviceItem.setWidth(300);
			DataSource services = CommonSingleton.getInstance().getServicesDS();
			serviceItem.setOptionDataSource(services);
			serviceItem.setDisplayField("service_description");
			serviceItem.setValueField("service_price_id");

			chargeCountItem = new TextItem();
			chargeCountItem.setTitle(CallCenterBK.constants.chargeCount());
			chargeCountItem.setName("chargeCountItem");
			chargeCountItem.setWidth(288);
			chargeCountItem.setValue(1);

			dynamicForm.setFields(serviceItem, chargeCountItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			chargeButton = new IButton();
			chargeButton.setTitle(CallCenterBK.constants.charge());
			chargeButton.setIcon("moneySmall.png");
			chargeButton.setWidth(150);

			buttonLayout.addMember(chargeButton);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			buttonLayout.addMember(cancItem);

			hLayout.addMember(buttonLayout);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});

			chargeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					addChargeToCall();
				}
			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void addChargeToCall() {
		try {
			String serviceId_str = serviceItem.getValueAsString();
			if (serviceId_str == null || serviceId_str.trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.chooseService());
				return;
			}
			Integer serviceId = null;
			try {
				serviceId = Integer.parseInt(serviceId_str);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.invalidService());
				return;
			}
			String chargeCount_str = chargeCountItem.getValueAsString();
			if (chargeCount_str == null || chargeCount_str.trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.enterChargeCount());
				return;
			}
			Integer chargeCount = null;
			try {
				chargeCount = Integer.parseInt(chargeCount_str);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.invalidChargeCount());
				return;
			}

			String session_id = listGridRecord
					.getAttributeAsString("session_id");
			Integer year_month = listGridRecord.getAttributeAsInt("year_month");
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("upd_user", loggedUser);
			record.setAttribute("service_price_id", serviceId);
			record.setAttribute("chargeCount", chargeCount);
			record.setAttribute("session_id", session_id);
			record.setAttribute("year_month", year_month);
			record.setAttribute("call_phone",
					listGridRecord.getAttributeAsString("call_phone"));
			record.setAttribute("uname",
					listGridRecord.getAttributeAsString("uname"));
			record.setAttribute("call_duration",
					listGridRecord.getAttributeAsInt("call_duration"));
			record.setAttribute("call_start_date",
					listGridRecord.getAttributeAsDate("call_start_date"));
			record.setAttribute("call_session_id",
					listGridRecord.getAttributeAsInt("call_session_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "addChargesBySurvey");
			listGrid.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					dlgAddCharge.search();
					destroy();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
