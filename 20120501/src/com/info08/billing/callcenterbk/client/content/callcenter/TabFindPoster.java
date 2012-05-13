package com.info08.billing.callcenterbk.client.content.callcenter;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewOrg;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewPoster;
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

public class TabFindPoster extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private TextItem entPostGeoAndComItem;
	private DateItem posterDateStartItem;

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
	private DataSource entPosterDS;

	public TabFindPoster() {

		setTitle(CallCenterBK.constants.findPoster());
		setCanClose(true);

		entPosterDS = DataSource.get("EntPosterDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		HLayout gridsLayOut = new HLayout(10);
		gridsLayOut.setWidth(800);
		gridsLayOut.setHeight(250);
		mainLayout.addMember(gridsLayOut);

		DataSource entTypeDS = DataSource.get("EntTypeDS");

		listGridEntType = new ListGrid();
		listGridEntType.setWidth(280);
		listGridEntType.setHeight100();
		listGridEntType.setDataSource(entTypeDS);
		listGridEntType.setAutoFetchData(true);
		listGridEntType.setFetchOperation("searchAllEventCategoryForCC");

		ListGridField event_category_name = new ListGridField("event_category_name",
				CallCenterBK.constants.entType());
		listGridEntType.setFields(event_category_name);

		DataSource entPlaceDS = DataSource.get("EntPlaceDS");

		listGridEntPlace = new ListGrid();
		listGridEntPlace.setWidth100();
		listGridEntPlace.setHeight100();
		listGridEntPlace.setDataSource(entPlaceDS);
		listGridEntPlace.setAutoFetchData(false);
		listGridEntPlace.setCanRemoveRecords(false);
		listGridEntPlace.setFetchOperation("searchAllEntPostersForCallCenter2");
		listGridEntPlace.setShowFilterEditor(true);
		listGridEntPlace.setFilterOnKeypress(true);

		ListGridField ent_place_geo1 = new ListGridField("ent_place_geo",
				CallCenterBK.constants.entPosterCategory());
		ent_place_geo1.setCanFilter(true);

		listGridEntPlace.setFields(ent_place_geo1);

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
				dsRequest.setOperationId("searchAllEntPostersForCallCenter2");
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

		entPostGeoAndComItem = new TextItem();
		entPostGeoAndComItem.setTitle(CallCenterBK.constants
				.entPosterNameAndComm());
		entPostGeoAndComItem.setName("entPostGeoAndComItem");
		entPostGeoAndComItem.setWidth(280);

		posterDateStartItem = new DateItem();
		posterDateStartItem.setTitle(CallCenterBK.constants.date());
		posterDateStartItem.setWidth(280);
		posterDateStartItem.setName("posterDateStartItem");
		posterDateStartItem.setUseTextField(true);

		searchForm.setFields(entPostGeoAndComItem, posterDateStartItem);

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
		listGrid.setDataSource(entPosterDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchAllEntPostersForCallCenter1");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField itemdate = new ListGridField("itemdate",
				CallCenterBK.constants.date(), 90);
		itemdate.setAlign(Alignment.LEFT);

		ListGridField itemname = new ListGridField("itemname",
				CallCenterBK.constants.poster(), 180);
		itemname.setAlign(Alignment.LEFT);

		ListGridField price = new ListGridField("price",
				CallCenterBK.constants.price(), 70);
		price.setAlign(Alignment.CENTER);

		ListGridField fullinfo = new ListGridField("info",
				CallCenterBK.constants.comment());

		ListGridField ent_place_geo = new ListGridField("ent_place_geo",
				CallCenterBK.constants.entPosterCategory(), 170);
		price.setAlign(Alignment.CENTER);

		listGrid.setFields(itemdate, ent_place_geo, itemname, price, fullinfo);

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
				entPostGeoAndComItem.clearValue();
				posterDateStartItem.clearValue();
			}
		});
		entPostGeoAndComItem.addKeyPressHandler(new KeyPressHandler() {
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
				DlgViewPoster dlgViewPoster = new DlgViewPoster(entPosterDS,
						listGrid.getSelectedRecord());
				dlgViewPoster.show();
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

			Integer main_id = record.getAttributeAsInt("main_id");
			if (main_id == null) {
				SC.say(CallCenterBK.constants.invalidOrganization());
				return;
			}
			Criteria criteria = new Criteria();
			criteria.setAttribute("main_id", main_id);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("customOrgSearchForCallCenterByMainId");
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
						pRecord.setAttribute("main_id",
								record.getAttributeAsInt("main_id"));
						pRecord.setAttribute("note_crit",
								record.getAttributeAsInt("note_crit"));
						pRecord.setAttribute("org_name",
								record.getAttributeAsString("org_name"));
						pRecord.setAttribute("org_name_eng",
								record.getAttributeAsString("org_name_eng"));
						pRecord.setAttribute("note",
								record.getAttributeAsString("note"));
						pRecord.setAttribute("director",
								record.getAttributeAsString("director"));
						pRecord.setAttribute("founded",
								record.getAttributeAsString("founded"));
						pRecord.setAttribute("identcode",
								record.getAttributeAsString("identcode"));
						pRecord.setAttribute("new_identcode",
								record.getAttributeAsString("new_identcode"));
						pRecord.setAttribute("legaladdress",
								record.getAttributeAsString("legaladdress"));
						pRecord.setAttribute("real_address",
								record.getAttributeAsString("real_address"));
						pRecord.setAttribute("workinghours",
								record.getAttributeAsString("workinghours"));
						pRecord.setAttribute("dayoffs",
								record.getAttributeAsString("dayoffs"));
						pRecord.setAttribute("org_info",
								record.getAttributeAsString("org_info"));
						pRecord.setAttribute("contactperson",
								record.getAttributeAsString("contactperson"));
						pRecord.setAttribute("workpersoncountity", record
								.getAttributeAsString("workpersoncountity"));
						pRecord.setAttribute("ind",
								record.getAttributeAsString("ind"));
						pRecord.setAttribute("webaddress",
								record.getAttributeAsString("webaddress"));
						pRecord.setAttribute("mail",
								record.getAttributeAsString("mail"));
						pRecord.setAttribute("city_name_geo",
								record.getAttributeAsString("city_name_geo"));
						pRecord.setAttribute("city_region_name_geo", record
								.getAttributeAsString("city_region_name_geo"));
						pRecord.setAttribute("street_location_geo", record
								.getAttributeAsString("street_location_geo"));
						pRecord.setAttribute("index_text",
								record.getAttributeAsString("index_text"));
						pRecord.setAttribute("legal_statuse",
								record.getAttributeAsString("legal_statuse"));
						pRecord.setAttribute("city_id",
								record.getAttributeAsInt("city_id"));
						pRecord.setAttribute("org_allert_by_buss_det", record
								.getAttributeAsString("org_allert_by_buss_det"));

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
					criteria.setAttribute("event_category_id", event_category_id);
				}
			}

			ListGridRecord listGridRecordPl = listGridEntPlace
					.getSelectedRecord();
			if (listGridRecordPl != null) {
				Integer event_owner_id = listGridRecordPl
						.getAttributeAsInt("event_owner_id");
				if (event_owner_id != null) {
					criteria.setAttribute("scheduleplaceid", new Integer(
							event_owner_id));
				}
			}

			String entPostGeoAndCom = entPostGeoAndComItem.getValueAsString();
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
				poster_date_p = posterDateStartItem.getValueAsDate();
				if (poster_date_p != null) {
					criteria.setAttribute("poster_date_p", poster_date_p);
				}
			} catch (Exception e) {
				e.printStackTrace();
				SC.say(CallCenterBK.constants.invalidDate());
				return;
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"searchAllEntPostersForCallCenter1");
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
						"searchAllEntPostersForCallCenter2");
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
