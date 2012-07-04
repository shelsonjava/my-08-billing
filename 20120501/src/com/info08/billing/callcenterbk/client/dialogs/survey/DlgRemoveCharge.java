package com.info08.billing.callcenterbk.client.dialogs.survey;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
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
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgRemoveCharge extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem nmItem;
	private DateItem dateItem;
	private ComboBoxItem serviceItem;
	private TextItem operatorItem;

	private ListGrid listGrid;

	private IButton findButton;
	private IButton clearButton;
	private IButton removeChargeButton;

	public DlgRemoveCharge(String phone, String sOperator, Date callDate) {
		try {
			setTitle(CallCenterBK.constants.manageCharges());

			setHeight(500);
			setWidth(700);
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

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(150);
			dynamicForm.setNumCols(2);
			dynamicForm.setTitleOrientation(TitleOrientation.TOP);
			hLayout.addMember(dynamicForm);

			nmItem = new TextItem();
			nmItem.setTitle(CallCenterBK.constants.phone());
			nmItem.setName("phoneItem");
			nmItem.setWidth(250);
			nmItem.setValue(phone);

			dateItem = new DateItem();
			dateItem.setUseTextField(true);
			dateItem.setTitle(CallCenterBK.constants.date());
			dateItem.setName("dateItem");
			dateItem.setWidth(250);
			dateItem.setValue(callDate);

			serviceItem = new ComboBoxItem();
			serviceItem.setTitle(CallCenterBK.constants.service());
			serviceItem.setType("comboBox");
			serviceItem.setName("serviceItem");
			serviceItem.setWidth(250);
			DataSource services = CommonSingleton.getInstance().getServicesDS();
			serviceItem.setOptionDataSource(services);
			serviceItem.setDisplayField("serviceNameGeo");
			serviceItem.setValueField("serviceId");

			operatorItem = new TextItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setName("operatorItem");
			operatorItem.setWidth(250);
			operatorItem.setValue(sOperator);

			dynamicForm.setFields(nmItem, serviceItem, operatorItem, dateItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.LEFT);

			removeChargeButton = new IButton();
			removeChargeButton.setTitle(CallCenterBK.constants.removeCharge());
			removeChargeButton.setIcon("removeCharge.png");
			removeChargeButton.setWidth(150);

			buttonLayout.addMember(removeChargeButton);
			buttonLayout.addMember(new LayoutSpacer());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			buttonLayout.addMember(findButton);
			buttonLayout.addMember(clearButton);
			hLayout.addMember(buttonLayout);

			final DataSource callSessExpDS1 = DataSource.get("CallSessExpDS1");

			listGrid = new ListGrid();
			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setDataSource(callSessExpDS1);
			listGrid.setFetchOperation("selectChargesByPhoneInCurrMonthNoGrouped");
			listGrid.setAutoFetchData(false);
			listGrid.setCanSort(false);
			listGrid.setCanResizeFields(false);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);

			hLayout.addMember(listGrid);

			ListGridField service_name_geo = new ListGridField(
					"service_description", CallCenterBK.constants.service());

			ListGridField rec_date = new ListGridField("rec_date_time",
					CallCenterBK.constants.recDate(), 150);
			rec_date.setAlign(Alignment.CENTER);

			ListGridField price = new ListGridField("charge",
					CallCenterBK.constants.price(), 70);
			price.setAlign(Alignment.CENTER);

			ListGridField operator = new ListGridField("uname",
					CallCenterBK.constants.operator(), 100);
			operator.setAlign(Alignment.CENTER);

			listGrid.setFields(service_name_geo, rec_date, price, operator);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});

			removeChargeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					try {
						ListGridRecord listGridRecord = listGrid
								.getSelectedRecord();
						if (listGridRecord == null) {
							SC.say(CallCenterBK.constants.warning(),
									CallCenterBK.constants.pleaseSelrecord());
							return;
						}
						Integer cse_id = listGridRecord
								.getAttributeAsInt("cse_id");
						if (cse_id == null) {
							SC.say(CallCenterBK.constants.warning(),
									CallCenterBK.constants.invalidRecord());
							return;
						}

						com.smartgwt.client.rpc.RPCManager.startQueue();
						Record record = new Record();
						record.setAttribute("cse_id", cse_id);
						record.setAttribute("upd_user", CommonSingleton
								.getInstance().getSessionPerson()
								.getUser_name());

						DSRequest req = new DSRequest();
						req.setAttribute("operationId", "deleteSessionCharge");
						callSessExpDS1.updateData(record, new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {

							}
						}, req);
						com.smartgwt.client.rpc.RPCManager.sendQueue();

					} catch (Exception e) {
						SC.say(e.toString());
					}
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					nmItem.clearValue();
					dateItem.clearValue();
				}
			});

			nmItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			operatorItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}

			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();

			String call_phone = nmItem.getValueAsString();
			if (call_phone == null || call_phone.trim().equals("")) {
				SC.say(CallCenterBK.constants.pleaseEnterPhone());
				return;
			}

			Date rec_date_time = null;
			try {
				rec_date_time = dateItem.getValueAsDate();
			} catch (Exception e) {

			}
			if (rec_date_time != null) {
				criteria.setAttribute("rec_date_time", rec_date_time);
			}

			String user_name = operatorItem.getValueAsString();
			if (user_name != null && !user_name.trim().equals("")) {
				criteria.setAttribute("uname", user_name);
			}

			String service_id_str = serviceItem.getValueAsString();
			if (service_id_str != null && !service_id_str.trim().equals("")) {
				criteria.setAttribute("service_id", new Integer(service_id_str));
			}

			criteria.setAttribute("call_phone", call_phone);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"selectChargesByPhoneInCurrMonthNoGrouped");
			listGrid.invalidateCache();
			listGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
