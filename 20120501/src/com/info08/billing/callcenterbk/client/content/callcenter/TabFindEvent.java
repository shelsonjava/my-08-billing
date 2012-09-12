package com.info08.billing.callcenterbk.client.content.callcenter;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewOrg;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewEvent;
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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabFindEvent extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private TextItem eventAndComItem;
	private DateItem eventDateStartItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;
	private ListGrid listGridEntPlace;
	private ListGrid listGridEntType;
	// DataSource
	private DataSource EventDS;

	public TabFindEvent() {

		setTitle(CallCenterBK.constants.findPoster());
		setCanClose(true);

		EventDS = DataSource.get("EventDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		HLayout gridsLayOut = new HLayout(10);
		gridsLayOut.setWidth(800);
		gridsLayOut.setHeight(250);
		mainLayout.addMember(gridsLayOut);

		DataSource EventCategoryDS = DataSource.get("EventCategoryDS");

		listGridEntType = new ListGrid();
		listGridEntType.setWidth(280);
		listGridEntType.setHeight100();
		listGridEntType.setDataSource(EventCategoryDS);
		listGridEntType.setAutoFetchData(true);
		listGridEntType.setFetchOperation("searchAllEventCategoryForCC");

		ListGridField event_category_name = new ListGridField(
				"event_category_name", CallCenterBK.constants.entType());
		listGridEntType.setFields(event_category_name);

		DataSource EventOwnerDS = DataSource.get("EventOwnerDS");

		listGridEntPlace = new ListGrid();
		listGridEntPlace.setWidth100();
		listGridEntPlace.setHeight100();
		listGridEntPlace.setDataSource(EventOwnerDS);
		listGridEntPlace.setAutoFetchData(false);
		listGridEntPlace.setCanRemoveRecords(false);
		listGridEntPlace.setFetchOperation("searchAllEventOwnerForCallCenter2");
		listGridEntPlace.setShowFilterEditor(true);
		listGridEntPlace.setFilterOnKeypress(true);

		ListGridField event_owner_name1 = new ListGridField("event_owner_name",
				CallCenterBK.constants.entPosterCategory());
		event_owner_name1.setCanFilter(true);

		listGridEntPlace.setFields(event_owner_name1);

		gridsLayOut.setMembers(listGridEntType, listGridEntPlace);

		listGridEntType.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				ListGridRecord listGridRecord = listGridEntType
						.getSelectedRecord();
				if (listGridRecord == null) {
					return;
				}
				Integer event_category_id = listGridRecord
						.getAttributeAsInt("event_category_id");
				if (event_category_id == null) {
					return;
				}
				Criteria criteria = new Criteria();
				criteria.setAttribute("event_category_id", event_category_id);
				DSRequest dsRequest = new DSRequest();
				dsRequest.setOperationId("searchAllEventOwnerForCallCenter2");
				listGridEntPlace.invalidateCache();
				listGridEntPlace.fetchData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {

					}
				}, dsRequest);
			}
		});

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(true);
		searchForm.setWidth(800);
		searchForm.setNumCols(2);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(searchForm);

		eventAndComItem = new TextItem();
		eventAndComItem.setTitle(CallCenterBK.constants
				.entPosterNameAndComm());
		eventAndComItem.setName("entPostGeoAndComItem");
		eventAndComItem.setWidth(280);

		eventDateStartItem = new DateItem();
		eventDateStartItem.setTitle(CallCenterBK.constants.date());
		eventDateStartItem.setWidth(280);
		eventDateStartItem.setName("posterDateStartItem");
		eventDateStartItem.setUseTextField(true);

		searchForm.setFields(eventAndComItem, eventDateStartItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(560);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		findButton = new IButton();
		findButton.setTitle(CallCenterBK.constants.find());

		clearButton = new IButton();
		clearButton.setTitle(CallCenterBK.constants.clear());

		buttonLayout.setMembers(findButton, clearButton);
		mainLayout.addMember(buttonLayout);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth(1040);
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		ToolStripButton viewOrg = new ToolStripButton(
				CallCenterBK.constants.organization(), "organization.gif");
		viewOrg.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(viewOrg);

		listGrid = new ListGrid() {
			@Override
			protected String getCellStyle(ListGridRecord record, int rowNum,
					int colNum) {
				return super.getCellStyle(record, rowNum, colNum);
			}
		};
		listGrid.setWidth(1040);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(EventDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchAllEventForCallCenter1");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField itemdate = new ListGridField("event_list_date",
				CallCenterBK.constants.date(), 90);
		itemdate.setAlign(Alignment.LEFT);

		ListGridField itemname = new ListGridField("event_list_name",
				CallCenterBK.constants.poster(), 180);
		itemname.setAlign(Alignment.LEFT);

		ListGridField price = new ListGridField("event_list_price",
				CallCenterBK.constants.price(), 70);
		price.setAlign(Alignment.CENTER);

		ListGridField fullinfo = new ListGridField("event_list_remark",
				CallCenterBK.constants.comment());

		ListGridField event_owner_name = new ListGridField("event_owner_name",
				CallCenterBK.constants.entPosterCategory(), 170);
		price.setAlign(Alignment.CENTER);

		listGrid.setFields(itemdate, event_owner_name, itemname, price, fullinfo);

		mainLayout.addMember(listGrid);

		findButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search(false);
			}
		});

		listGridEntPlace
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						search(true);
					}
				});

		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				listGridEntType.deselectAllRecords();
				listGridEntPlace.deselectAllRecords();
				eventAndComItem.clearValue();
				eventDateStartItem.clearValue();
			}
		});
		eventAndComItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search(false);
				}
			}
		});

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				DlgViewEvent dlgViewEvent = new DlgViewEvent(EventDS,
						listGrid.getSelectedRecord());
				dlgViewEvent.show();
			}
		});

		viewOrg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				openOrganization();
			}
		});

		setPane(mainLayout);
	}

	private void openOrganization() {
		try {
			ListGridRecord record = listGridEntPlace.getSelectedRecord();
			if (record == null) {
				SC.say(CallCenterBK.constants.pleaseSelrecord());
				return;
			}

			Integer organization_id = record.getAttributeAsInt("organization_id");
			if (organization_id == null) {
				SC.say(CallCenterBK.constants.invalidOrganization());
				return;
			}
			Criteria criteria = new Criteria();
			criteria.setAttribute("organization_id", organization_id);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("customOrgSearchForCallCenterNew");
			final DataSource orgDS = DataSource.get("OrgDS");
			orgDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					try {
						Record[] records = response.getData();
						if (records == null || records.length <= 0) {
							SC.say(CallCenterBK.constants.orgNotFound());
							return;
						}
						Record record = records[0];
						ListGridRecord pRecord = new ListGridRecord();
						pRecord.setAttribute("organization_id",
								record.getAttributeAsInt("organization_id"));
						pRecord.setAttribute("priority",
								record.getAttributeAsInt("priority"));
						pRecord.setAttribute("organization_name", record
								.getAttributeAsString("organization_name"));
						pRecord.setAttribute("remark",
								record.getAttributeAsString("remark"));
						pRecord.setAttribute("chief",
								record.getAttributeAsString("chief"));
						pRecord.setAttribute("found_date",
								record.getAttributeAsString("found_date"));
						pRecord.setAttribute("ident_code",
								record.getAttributeAsString("ident_code"));
						pRecord.setAttribute("ident_code_new",
								record.getAttributeAsString("ident_code_new"));
						pRecord.setAttribute("call_center_address", record
								.getAttributeAsString("call_center_address"));
						pRecord.setAttribute("real_address_descr", record
								.getAttributeAsString("real_address_descr"));
						pRecord.setAttribute("work_hours",
								record.getAttributeAsString("work_hours"));
						pRecord.setAttribute("day_offs_descr",
								record.getAttributeAsString("day_offs_descr"));
						pRecord.setAttribute("additional_info",
								record.getAttributeAsString("additional_info"));
						pRecord.setAttribute("contact_person",
								record.getAttributeAsString("contact_person"));
						pRecord.setAttribute("staff_count",
								record.getAttributeAsString("staff_count"));
						pRecord.setAttribute("organization_index", record
								.getAttributeAsString("organization_index"));
						pRecord.setAttribute("web_address",
								record.getAttributeAsString("web_address"));
						pRecord.setAttribute("email_address",
								record.getAttributeAsString("email_address"));
						pRecord.setAttribute("town_name",
								record.getAttributeAsString("town_name"));
						pRecord.setAttribute("town_district_name", record
								.getAttributeAsString("town_district_name"));
						pRecord.setAttribute("street_location",
								record.getAttributeAsString("street_location"));
						pRecord.setAttribute("index_text",
								record.getAttributeAsString("index_text"));
						pRecord.setAttribute("status",
								record.getAttributeAsString("status"));
						pRecord.setAttribute("town_id",
								record.getAttributeAsInt("town_id"));
						pRecord.setAttribute("org_allert_by_buss_det", record
								.getAttributeAsString("org_allert_by_buss_det"));
						pRecord.setAttribute("full_address_not_hidden", record
								.getAttributeAsString("full_address_not_hidden"));
						pRecord.setAttribute("hidden_by_request",
								record.getAttributeAsInt("hidden_by_request"));

						showOrgDialog(orgDS, pRecord);
					} catch (Exception e) {
						e.printStackTrace();
						SC.say(e.toString());
					}
				}
			}, dsRequest);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search(boolean isCatDoubleClick) {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);

			ListGridRecord listGridRecord = listGridEntType.getSelectedRecord();
			if (listGridRecord != null) {
				Integer event_category_id = listGridRecord
						.getAttributeAsInt("event_category_id");
				if (event_category_id != null) {
					criteria.setAttribute("event_category_id",
							event_category_id);
				}
			}

			ListGridRecord listGridRecordPl = listGridEntPlace
					.getSelectedRecord();
			if (listGridRecordPl != null) {
				Integer event_owner_id = listGridRecordPl.getAttributeAsInt("event_owner_id");
				if (event_owner_id != null) {
					criteria.setAttribute("event_owner_id", new Integer(event_owner_id));
				}
			}

			String entPostGeoAndCom = eventAndComItem.getValueAsString();
			if (entPostGeoAndCom != null && !entPostGeoAndCom.trim().equals("")) {
				String tmp = entPostGeoAndCom.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("entPostGeoAndCom" + i, item);
					i++;
				}
			}
			Date poster_date_p = null;
			try {
				poster_date_p = eventDateStartItem.getValueAsDate();
				if (poster_date_p != null) {
					criteria.setAttribute("event_date_p", poster_date_p);
				}
			} catch (Exception e) {
				e.printStackTrace();
				SC.say(CallCenterBK.constants.invalidDate());
				return;
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"searchAllEventForCallCenter1");
			listGrid.invalidateCache();
			listGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);

			if (!isCatDoubleClick
					&& ((entPostGeoAndCom != null && !entPostGeoAndCom.trim()
							.equals("")) || poster_date_p != null)) {
				DSRequest dsRequest1 = new DSRequest();
				dsRequest1.setAttribute("operationId",
						"searchAllEventOwnerForCallCenter2");
				listGridEntPlace.invalidateCache();
				listGridEntPlace.filterData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
					}
				}, dsRequest1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void showOrgDialog(DataSource orgDS, ListGridRecord pRecord) {
		try {
			DlgViewOrg dlgViewOrg = new DlgViewOrg(orgDS, pRecord);
			dlgViewOrg.show();

			String org_allert_by_buss_det = pRecord
					.getAttributeAsString("org_allert_by_buss_det");
			if (org_allert_by_buss_det != null
					&& !org_allert_by_buss_det.trim().equals("")) {
				SC.warn(org_allert_by_buss_det);
			}

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
