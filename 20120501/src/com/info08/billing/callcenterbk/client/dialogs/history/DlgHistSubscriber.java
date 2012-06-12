package com.info08.billing.callcenterbk.client.dialogs.history;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.utils.HistoryResult;
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
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgHistSubscriber extends Window {

	private VLayout hLayout;
	private DynamicForm searchForm;

	// private DateItem histStartDateItem;
	// private DateItem histEndDateItem;

	private TextItem firstNameItem;
	private TextItem lastNameItem;

	private TextItem townItem;
	private TextItem streetItem;
	private TextItem phoneItem;
	private TextItem districtItem;

	private IButton findButton;
	private IButton clearButton;

	private ListGrid histSubscriberListGrid;
	private ListGrid histSubscriberPhoneListGrid;
	private DataSource SubscriberDS;
	private DataSource AbPhonesDS;

	public DlgHistSubscriber(final Record abonentRecord) {
		try {
			setWidth(1260);
			setHeight(730);
			setTitle("აბონენტის ინსტორია");
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

			Date startDate = new Date();
			CalendarUtil.addMonthsToDate(startDate, -1);

			SubscriberDS = DataSource.get("SubscriberDS");
			AbPhonesDS = DataSource.get("AbPhonesDS");

			/*
			 * histStartDateItem = new DateItem();
			 * histStartDateItem.setTitle(CallCenterBK.constants.dateFrom());
			 * histStartDateItem.setWidth(300);
			 * histStartDateItem.setValue(startDate);
			 * histStartDateItem.setName("histStartDateItem");
			 * histStartDateItem.setUseTextField(true);
			 * 
			 * histEndDateItem = new DateItem();
			 * histEndDateItem.setTitle(CallCenterBK.constants.dateTo());
			 * histEndDateItem.setWidth(300); histEndDateItem.setValue(new
			 * Date()); histEndDateItem.setName("histEndDateItem");
			 * histEndDateItem.setUseTextField(true);
			 */

			firstNameItem = new TextItem();
			firstNameItem.setTitle("სახელი");
			firstNameItem.setName("firstnameItem");
			firstNameItem.setWidth(300);

			lastNameItem = new TextItem();
			lastNameItem.setTitle("გვარი");
			lastNameItem.setName("lastNameItem");
			lastNameItem.setWidth(300);

			townItem = new TextItem();
			townItem.setTitle(CallCenterBK.constants.town());
			townItem.setName("townItem");
			townItem.setWidth(300);

			streetItem = new TextItem();
			streetItem.setTitle(CallCenterBK.constants.street());
			streetItem.setName("streetItem");
			streetItem.setWidth(300);

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setName("phoneItem");
			phoneItem.setWidth(300);

			districtItem = new TextItem();
			districtItem.setTitle(CallCenterBK.constants.district());
			districtItem.setName("districtItem");
			districtItem.setWidth(300);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth100();
			searchForm.setNumCols(2);
			searchForm.setColWidths("50%", "50%");
			searchForm.setPadding(5);
			searchForm.setTitleOrientation(TitleOrientation.TOP);

			searchForm.setFields(/* histStartDateItem, histEndDateItem, */
			firstNameItem, lastNameItem, townItem, streetItem, phoneItem,
					districtItem);

			searchForm.setBorder("1px solid #CCC");

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			buttonLayout.setMembers(findButton, clearButton);

			histSubscriberListGrid = new ListGrid();
			histSubscriberListGrid.setWidth100();
			histSubscriberListGrid.setHeight(250);
			histSubscriberListGrid.setAlternateRecordStyles(true);
			histSubscriberListGrid.setDataSource(SubscriberDS);
			histSubscriberListGrid.setAutoFetchData(false);
			histSubscriberListGrid.setShowFilterEditor(false);
			histSubscriberListGrid.setCanEdit(false);
			histSubscriberListGrid.setCanRemoveRecords(false);
			histSubscriberListGrid.setFetchOperation("histSearch");
			histSubscriberListGrid.setCanSort(false);
			histSubscriberListGrid.setCanResizeFields(false);
			histSubscriberListGrid.setWrapCells(true);
			histSubscriberListGrid.setFixedRecordHeights(false);
			histSubscriberListGrid.setCanDragSelectText(true);

			ListGridField first_name = new ListGridField("name", "სახელი", 100);
			first_name.setAlign(Alignment.LEFT);

			ListGridField last_name = new ListGridField("family_name", "გვარი",
					100);
			last_name.setAlign(Alignment.LEFT);

			ListGridField town = new ListGridField("town_name",
					CallCenterBK.constants.town(), 120);
			town.setAlign(Alignment.CENTER);

			ListGridField street = new ListGridField("concat_address",
					CallCenterBK.constants.street());

			ListGridField hist_user_on = new ListGridField("hist_user_on",
					"user_on");
			hist_user_on.setWidth(50);
			hist_user_on.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {

					return new HistoryResult(record).getOn_user();
				}
			});
			ListGridField hist_user_off = new ListGridField("hist_user_off",
					"user_off");
			hist_user_off.setWidth(50);
			hist_user_off.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {

					return new HistoryResult(record).getOff_user();
				}
			});

			final DateTimeFormat formatter = DateTimeFormat
					.getFormat("dd.MM.yyyy HH:mm:ss");

			ListGridField hist_start = new ListGridField("hist_start",
					"hist_start");
			hist_start.setWidth(120);

			hist_start.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					try {
						return formatter.format(new HistoryResult(record)
								.getStart());
					} catch (Exception e) {
						return null;
					}
				}
			});

			ListGridField hist_end = new ListGridField("hist_end", "hist_end");
			hist_end.setWidth(120);

			hist_end.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					try {
						return formatter.format(new HistoryResult(record)
								.getEnd());
					} catch (Exception e) {
						return null;
					}
				}
			});

			histSubscriberListGrid.setFields(first_name, last_name, town,
					street, hist_user_on, hist_start, hist_user_off, hist_end);

			histSubscriberPhoneListGrid = new ListGrid();
			histSubscriberPhoneListGrid.setWidth100();
			histSubscriberPhoneListGrid.setHeight(200);
			histSubscriberPhoneListGrid.setAlternateRecordStyles(true);
			histSubscriberPhoneListGrid.setDataSource(AbPhonesDS);
			histSubscriberPhoneListGrid.setAutoFetchData(false);
			histSubscriberPhoneListGrid.setShowFilterEditor(false);
			histSubscriberPhoneListGrid.setCanEdit(false);
			histSubscriberPhoneListGrid.setCanRemoveRecords(false);
			histSubscriberPhoneListGrid
					.setFetchOperation("getPhoneHistoryForSubscriber");
			histSubscriberPhoneListGrid.setCanSort(false);
			histSubscriberPhoneListGrid.setCanResizeFields(false);
			histSubscriberPhoneListGrid.setWrapCells(true);
			histSubscriberPhoneListGrid.setFixedRecordHeights(false);
			histSubscriberPhoneListGrid.setCanDragSelectText(true);

			ListGridField phones = new ListGridField("phone",
					CallCenterBK.constants.phone(), 100);
			first_name.setAlign(Alignment.LEFT);

			ListGridField phone_state = new ListGridField("phone_state",
					CallCenterBK.constants.phoneState(), 100);
			phone_state.setAlign(Alignment.LEFT);

			ListGridField phone_contract_type_desr = new ListGridField(
					"phone_contract_type_desr",
					CallCenterBK.constants.phoneStatus(), 100);
			phone_contract_type_desr.setAlign(Alignment.CENTER);

			ListGridField phone_type = new ListGridField("phone_type", "ტიპი",
					100);

			ListGridField is_parallel_descr = new ListGridField(
					"is_parallel_descr", "პარალელური");

			histSubscriberPhoneListGrid.setFields(phones, phone_state,
					phone_contract_type_desr, phone_type, is_parallel_descr);

			hLayout.addMember(searchForm);
			hLayout.addMember(buttonLayout);
			hLayout.addMember(histSubscriberListGrid);
			hLayout.addMember(histSubscriberPhoneListGrid);

			findButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					search();

				}
			});

			KeyPressHandler keyPressHandler = new KeyPressHandler() {

				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}

				}
			};

			firstNameItem.addKeyPressHandler(keyPressHandler);
			lastNameItem.addKeyPressHandler(keyPressHandler);
			townItem.addKeyPressHandler(keyPressHandler);
			streetItem.addKeyPressHandler(keyPressHandler);
			phoneItem.addKeyPressHandler(keyPressHandler);
			districtItem.addKeyPressHandler(keyPressHandler);
			// histStartDateItem.addKeyPressHandler(keyPressHandler);
			// histEndDateItem.addKeyPressHandler(keyPressHandler);

			histSubscriberListGrid.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					searchPhones();
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
			String firstName = firstNameItem.getValueAsString();
			String lastName = lastNameItem.getValueAsString();
			String phone = phoneItem.getValueAsString();
			// Date startDate = histStartDateItem.getValueAsDate();
			// Date endDate = histEndDateItem.getValueAsDate();

			Criteria criteria = new Criteria();

			if ((firstName == null || lastName == null) && phone == null) {
				SC.say("გთხოვთ შეიყვანოთ აუცილებელი კრიტერიები!");
				return;
			}

			if (firstName != null && !firstName.equals("")) {
				criteria.setAttribute("firstname", firstName);
			}

			if (lastName != null && !lastName.equals("")) {
				criteria.setAttribute("lastname", lastName);
			}

			if (phone != null && !phone.equals("")) {
				criteria.setAttribute("phone_number", phone);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "histSearch");
			histSubscriberListGrid.invalidateCache();
			histSubscriberListGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void searchPhones() {
		try {
			ListGridRecord gridRecord = histSubscriberListGrid
					.getSelectedRecord();
			String subscriber_id = "";
			if (gridRecord != null) {
				subscriber_id = gridRecord.getAttribute("subscriber_id");
			}
			Criteria criteria = new Criteria();
			if (!subscriber_id.equals("")) {
				criteria.setAttribute("subscriber_id", subscriber_id);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"getPhoneHistoryForSubscriber");
			histSubscriberPhoneListGrid.invalidateCache();
			histSubscriberPhoneListGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
