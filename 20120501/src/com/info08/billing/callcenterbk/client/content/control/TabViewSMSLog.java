package com.info08.billing.callcenterbk.client.content.control;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabViewSMSLog extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private TextItem phoneItem;
	private DateItem dateItem;
	private SelectItem statusItem;
	private ComboBoxItem operatorItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource orgDS;

	public TabViewSMSLog() {
		try {

			setTitle(CallCenterBK.constants.findByNumber());
			setCanClose(true);

			orgDS = DataSource.get("LogSMSDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setNumCols(2);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			mainLayout.addMember(searchForm);

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setName("phoneItem");
			phoneItem.setWidth(250);

			dateItem = new DateItem();
			dateItem.setTitle(CallCenterBK.constants.date());
			dateItem.setName("dateItem");
			dateItem.setWidth(250);
			dateItem.setUseTextField(true);
			dateItem.setValue(new Date());

			statusItem = new SelectItem();
			statusItem.setName("statusItem");
			statusItem.setWidth(250);
			statusItem.setTitle(CallCenterBK.constants.status());
			statusItem
					.setValueMap(ClientMapUtil.getInstance().getSmsStatuses());
			statusItem.setDefaultToFirstOption(true);

			DataSource persons = CommonSingleton.getInstance().getUsersDS();
			operatorItem = new ComboBoxItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setType("comboBox");
			operatorItem.setWidth(250);
			operatorItem.setName("user_id");
			operatorItem.setOptionOperationId("searchUser");
			operatorItem.setOptionDataSource(persons);
			operatorItem.setValueField("user_id");
			operatorItem.setDisplayField("fullPersonName");

			searchForm.setFields(phoneItem, dateItem, statusItem, operatorItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			listGrid = new ListGrid() {

				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer status = countryRecord.getAttributeAsInt("status");
					if (status == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					if (!status.equals(1)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};

			};
			listGrid.setWidth(1100);
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(orgDS);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("findSMSLog");
			listGrid.setCanSort(false);
			listGrid.setCanResizeFields(false);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);

			ListGridField sms_type_descr = new ListGridField("sms_type_descr",
					CallCenterBK.constants.type(), 100);
			sms_type_descr.setAlign(Alignment.CENTER);

			ListGridField status_descr = new ListGridField("status_descr",
					CallCenterBK.constants.status(), 100);
			status_descr.setAlign(Alignment.CENTER);

			ListGridField phone = new ListGridField("phone",
					CallCenterBK.constants.phone(), 80);
			phone.setAlign(Alignment.CENTER);

			ListGridField sms_date = new ListGridField("sms_date",
					CallCenterBK.constants.date(), 120);
			sms_date.setAlign(Alignment.CENTER);

			ListGridField rec_user = new ListGridField("rec_user",
					CallCenterBK.constants.recUser(), 120);
			rec_user.setAlign(Alignment.CENTER);

			ListGridField sms_text = new ListGridField("sms_text",
					CallCenterBK.constants.sms());
			sms_text.setAlign(Alignment.LEFT);

			listGrid.setFields(sms_type_descr, status_descr, phone, sms_date,
					rec_user, sms_text);

			mainLayout.addMember(listGrid);

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					phoneItem.clearValue();
					dateItem.setValue(new Date());
					statusItem.clearValue();
					operatorItem.clearValue();
				}
			});
			phoneItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			setPane(mainLayout);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);

			String phone = phoneItem.getValueAsString();
			if (phone != null && !phone.trim().equals("")) {
				criteria.setAttribute("phone", phone);
			}
			try {
				Date sms_date = dateItem.getValueAsDate();
				if (sms_date != null) {
					criteria.setAttribute("sms_date", sms_date);
				}
			} catch (Exception e) {
			}

			String str_status = statusItem.getValueAsString();
			if (str_status != null && !str_status.trim().equals("")) {
				Integer status = Integer.parseInt(str_status);
				if (status.equals(1)) {
					criteria.setAttribute("pStatDelivered", 1);
				} else if (status.equals(-1)) {
					criteria.setAttribute("pStatNotDelivered", 1);
				}
			}

			String operator = operatorItem.getValueAsString();
			if (operator != null && !operator.trim().equals("")) {
				criteria.setAttribute("user_id", Integer.parseInt(operator));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "findSMSLog");
			listGrid.invalidateCache();
			listGrid.filterData(criteria, new DSCallback() {
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
