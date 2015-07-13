package com.info08.billing.callcenterbk.client.dialogs.history;

import java.util.Date;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.GroupNode;
import com.smartgwt.client.widgets.grid.GroupTitleRenderer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgHistSubscriber extends MyWindow {

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
		super();
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
			phoneItem.setTitle("ტელ.ნომერი");
			phoneItem.setName("phoneItem");
			phoneItem.setWidth(300);

			districtItem = new TextItem();
			districtItem.setTitle(CallCenterBK.constants.district());
			districtItem.setName("districtItem");
			districtItem.setWidth(300);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth100();
			searchForm.setNumCols(3);
			searchForm.setColWidths("33%", "33%", "33%");
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
			histSubscriberListGrid.setHeight(320);
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

			histSubscriberListGrid.setGroupStartOpen(GroupStartOpen.ALL);
			histSubscriberListGrid.setGroupByField("subscriber_id");

			ListGridField subscriber_id = new ListGridField("subscriber_id",
					"სახელი", 100);

			subscriber_id.setGroupTitleRenderer(new GroupTitleRenderer() {

				@Override
				public String getGroupTitle(Object groupValue,
						GroupNode groupNode, ListGridField field,
						String fieldName, ListGrid grid) {
					return "აბონენტი";
				}
			});
			subscriber_id.setHidden(true);
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

			boolean hasPermission = CommonSingleton.getInstance()
					.hasPermission("106500");

			ListGridField hist_user_on = new ListGridField("on_user", "დაამატა");
			hist_user_on.setWidth(50);

			ListGridField hist_user_off = new ListGridField("off_user",
					"შეცვალა");
			hist_user_off.setWidth(50);
			ListGridField hist_start = new ListGridField("hist_start_date",
					"-დან");
			hist_start.setWidth(120);

			ListGridField hist_end = new ListGridField("hist_end_date", "-მდე");
			hist_end.setWidth(120);

			if (hasPermission) {
				histSubscriberListGrid.setFields(subscriber_id, first_name,
						last_name, town, street, hist_user_on, hist_start,
						hist_user_off, hist_end);
			} else {
				histSubscriberListGrid.setFields(subscriber_id, first_name,
						last_name, town, street, hist_start, hist_end);
			}

			histSubscriberPhoneListGrid = new ListGrid() {
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					String rdeleted = record.getAttribute("rdeleted");
					if (rdeleted != null)
						if (rdeleted.trim().equals("true"))
							return "color:red;";
						else
							return super.getCellCSSText(record, rowNum, colNum);
					else
						return super.getCellCSSText(record, rowNum, colNum);
				}
			};
			histSubscriberPhoneListGrid.setWidth100();
			histSubscriberPhoneListGrid.setHeight(180);
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
			histSubscriberPhoneListGrid.setGroupStartOpen(GroupStartOpen.ALL);
			histSubscriberPhoneListGrid.setGroupByField("phone");

			ListGridField phones = new ListGridField("phone",
					CallCenterBK.constants.phone(), 100);
			phones.setHidden(true);
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
			ListGridField hidden_by_request_descr = new ListGridField(
					"hidden_by_request_descr", "ღია/დაფარული");

			ListGridField ph_hist_user_on = new ListGridField("on_user",
					"დაამატა");
			ph_hist_user_on.setWidth(80);
			ListGridField ph_hist_user_off = new ListGridField("off_user",
					"შეცვალა");
			ph_hist_user_off.setWidth(80);
			ListGridField ph_hist_start = new ListGridField("hist_start_date",
					"-დან");
			ph_hist_start.setWidth(100);

			ListGridField ph_hist_end = new ListGridField("hist_end_date",
					"-მდე");
			ph_hist_end.setWidth(100);
			ListGridField ph_deleted = new ListGridField("rdeleted", "წაშლილი");
			ph_deleted.setWidth(70);
			ph_deleted.setType(ListGridFieldType.BOOLEAN);

			if (hasPermission) {
				histSubscriberPhoneListGrid.setFields(phones,
						hidden_by_request_descr, phone_state,
						phone_contract_type_desr, phone_type,
						is_parallel_descr, ph_hist_user_on, ph_hist_start,
						ph_hist_user_off, ph_hist_end, ph_deleted);
			} else {
				histSubscriberPhoneListGrid.setFields(phones,
						hidden_by_request_descr, phone_state,
						phone_contract_type_desr, phone_type,
						is_parallel_descr, ph_hist_start, ph_hist_end,
						ph_deleted);
			}

			hLayout.addMember(searchForm);
			hLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);

			ToolStripButton historyButton = new ToolStripButton(
					"კონკრეტული აბონენტის ისტორია", "date.png");
			historyButton.setLayoutAlign(Alignment.LEFT);
			historyButton.setWidth(50);
			toolStrip.addButton(historyButton);
			historyButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = null;
					listGridRecord = histSubscriberListGrid.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("მონიშნეთ ჩანაწერი ცხრილში");
						return;
					}
					searchBySubscriber(listGridRecord);
				}
			});
			if (abonentRecord != null) {
				searchBySubscriber(abonentRecord);
			}
			hLayout.addMember(toolStrip);
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

			searchByCriteria(criteria);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	protected void searchByCriteria(Criteria criteria) {
		DSRequest dsRequest = new DSRequest();
		dsRequest.setAttribute("operationId", "histSearch");
		// histSubscriberListGrid.invalidateCache();
		histSubscriberListGrid.filterData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				histSubscriberListGrid.getGroupTree().openAll();
				if (response.getData() != null && response.getData().length > 0) {
					histSubscriberListGrid.selectRecord(1);
					searchPhones();
				}
			}
		}, dsRequest);
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
				criteria.setAttribute("subscriber_id", new Long(subscriber_id));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"getPhoneHistoryForSubscriber");
			// histSubscriberPhoneListGrid.invalidateCache();
			histSubscriberPhoneListGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					histSubscriberPhoneListGrid.getGroupTree().openAll();
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	protected void searchBySubscriber(Record listGridRecord) {
		Integer subscriber_id = listGridRecord
				.getAttributeAsInt("subscriber_id");
		Criteria criteria = new Criteria();
		criteria.setAttribute("subscriber_id", subscriber_id);
		searchByCriteria(criteria);

	}
}
