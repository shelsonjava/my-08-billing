package com.info08.billing.callcenterbk.client.dialogs.correction;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgAddVirtualCharge extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem serviceItem;
	private SelectItem typeItem;
	private TextItem chargeCountItem;

	private IButton chargeButton;
	private ListGridRecord listGridRecord;

	public DlgAddVirtualCharge(DataSource dataSource,
			ListGridRecord listGridRecord) {
		try {
			this.listGridRecord = listGridRecord;
			setTitle(CallCenterBK.constants.addCharge());

			setHeight(260);
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

			DetailViewerField phone = new DetailViewerField("phone",
					CallCenterBK.constants.phone());

			DetailViewerField fullName = new DetailViewerField("fullName",
					CallCenterBK.constants.dasaxeleba());

			DetailViewerField orgOrAbonent = new DetailViewerField(
					"orgOrAbonent", CallCenterBK.constants.type());

			DetailViewerField city_name_geo = new DetailViewerField(
					"city_name_geo", CallCenterBK.constants.city());

			DetailViewerField streetName = new DetailViewerField("streetName",
					CallCenterBK.constants.street());

			DetailViewerField phone_status = new DetailViewerField(
					"phone_status", CallCenterBK.constants.phoneStatus());

			detailViewer.setFields(phone, fullName, orgOrAbonent,
					city_name_geo, streetName, phone_status);

			hLayout.addMember(detailViewer);

			ListGridRecord arr[] = new ListGridRecord[1];
			arr[0] = listGridRecord;
			detailViewer.setData(arr);

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setNumCols(3);
			dynamicForm.setTitleOrientation(TitleOrientation.TOP);
			hLayout.addMember(dynamicForm);

			serviceItem = new ComboBoxItem();
			serviceItem.setTitle(CallCenterBK.constants.service());
			serviceItem.setType("comboBox");
			serviceItem.setName("serviceItem");
			serviceItem.setWidth(230);
			DataSource services = CommonSingleton.getInstance().getServicesDS();
			serviceItem.setOptionDataSource(services);
			serviceItem.setDisplayField("serviceNameGeo");
			serviceItem.setValueField("serviceId");

			typeItem = new SelectItem();
			typeItem.setTitle(CallCenterBK.constants.type());
			typeItem.setName("typeItem");
			typeItem.setWidth(150);
			typeItem.setDefaultToFirstOption(true);
			typeItem.setValueMap(ClientMapUtil.getInstance().getCallTypes());

			chargeCountItem = new TextItem();
			chargeCountItem.setTitle(CallCenterBK.constants.chargeCount());
			chargeCountItem.setName("chargeCountItem");
			chargeCountItem.setWidth(208);
			chargeCountItem.setValue(1);

			dynamicForm.setFields(serviceItem, typeItem, chargeCountItem);

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

			String phone = listGridRecord.getAttributeAsString("phone");
			if (phone == null || phone.trim().equals("")) {
				SC.say(CallCenterBK.constants.invalidPhone());
				return;
			}
			phone = phone.trim();
			try {
				Long.parseLong(phone);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.invalidPhone());
				return;
			}
			if (phone.length() == 7) {
				phone = "32" + phone;
			}
			if (phone.length() != 9) {
				SC.say(CallCenterBK.constants.phoneLengthMustBe9());
				return;
			}

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

			Integer virt_call_type = new Integer(typeItem.getValueAsString());

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("upd_user", loggedUser);
			record.setAttribute("service_id", serviceId);
			record.setAttribute("chargeCount", chargeCount);
			record.setAttribute("phone", phone);
			record.setAttribute("virt_call_type", virt_call_type);

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "addChargesWithoutCall");

			DataSource dataSource = DataSource.get("LogSessDS");

			dataSource.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
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
