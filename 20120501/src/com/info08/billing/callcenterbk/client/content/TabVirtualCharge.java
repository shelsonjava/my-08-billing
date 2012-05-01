package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewOrg;
import com.info08.billing.callcenterbk.client.dialogs.correction.DlgAddVirtualCharge;
import com.info08.billing.callcenterbk.shared.common.Constants;
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
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabVirtualCharge extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private TextItem phoneItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource orgDS;

	public TabVirtualCharge() {

		setTitle(CallCenterBK.constants.virtualCharge());
		setCanClose(true);

		orgDS = DataSource.get("OrgDS");

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
		phoneItem.setWidth(500);

		searchForm.setFields(phoneItem);

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

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth(1100);
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		ToolStripButton viewOrg = new ToolStripButton(
				CallCenterBK.constants.organization(), "organization.gif");
		viewOrg.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(viewOrg);

		ToolStripButton addCharge = new ToolStripButton(
				CallCenterBK.constants.charge(), "moneySmall.png");
		addCharge.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(addCharge);

		listGrid = new ListGrid() {

			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord countryRecord = (ListGridRecord) record;
				if (countryRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				Integer is_abonent = countryRecord
						.getAttributeAsInt("is_abonent");
				if (is_abonent == null || is_abonent.equals(1)) {
					return super.getCellCSSText(record, rowNum, colNum);
				}

				Integer contact_phones = countryRecord
						.getAttributeAsInt("contact_phones");
				Integer statuse = countryRecord.getAttributeAsInt("statuse");
				Integer extra_priority = countryRecord
						.getAttributeAsInt("extra_priority");
				Integer note_crit = countryRecord
						.getAttributeAsInt("note_crit");

				if (extra_priority != null && extra_priority < 0
						&& (colNum != 4 && colNum != 5)) {
					return "color:red;";
				} else if (statuse != null && statuse.equals(2)
						&& (colNum != 4 && colNum != 5)) {
					if (note_crit != null && note_crit.intValue() == -1
							&& (colNum != 4 && colNum != 5)) {
						return "color:red;";
					} else {
						return "color:gray;";
					}
				} else if (statuse != null && statuse.equals(1)
						&& (colNum != 4 && colNum != 5)) {
					if (note_crit != null && note_crit.intValue() == -1
							&& (colNum != 4 && colNum != 5)) {
						return "color:red;";
					} else {
						return "color:blue;";
					}

				} else if (statuse != null && statuse.equals(3)
						&& (colNum != 4 && colNum != 5)) {
					if (note_crit != null && note_crit.intValue() == -1
							&& (colNum != 4 && colNum != 5)) {
						return "color:red;";
					} else {
						return "color:green;";
					}
				} else if (contact_phones == null || contact_phones.equals(1)
						&& (colNum == 4 || colNum == 5)) {
					return "color: red;";
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
		listGrid.setFetchOperation("customOrgAndAbonentSearchForCallCenter");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField fullName = new ListGridField("fullName",
				CallCenterBK.constants.dasaxeleba());
		fullName.setAlign(Alignment.LEFT);

		ListGridField orgOrAbonent = new ListGridField("orgOrAbonent",
				CallCenterBK.constants.type(), 50);
		orgOrAbonent.setAlign(Alignment.CENTER);

		ListGridField city_name_geo = new ListGridField("city_name_geo",
				CallCenterBK.constants.city(), 100);
		city_name_geo.setAlign(Alignment.LEFT);

		ListGridField streetName = new ListGridField("streetName",
				CallCenterBK.constants.street(), 350);
		streetName.setAlign(Alignment.LEFT);

		ListGridField phone = new ListGridField("phone",
				CallCenterBK.constants.phone(), 80);

		ListGridField phone_status = new ListGridField("phone_status",
				CallCenterBK.constants.phoneStatus(), 100);

		listGrid.setFields(fullName, orgOrAbonent, city_name_geo, streetName,
				phone, phone_status);

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

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				makeAdvancedCharge();
			}
		});

		viewOrg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				openOrganization();
			}
		});

		addCharge.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				makeAdvancedCharge();
			}
		});

		setPane(mainLayout);
	}

	private void makeAdvancedCharge() {
		try {
			ListGridRecord record = listGrid.getSelectedRecord();
			if (record == null) {
				SC.say(CallCenterBK.constants.pleaseSelrecord());
				return;
			}
			DlgAddVirtualCharge addVirtualCharge = new DlgAddVirtualCharge(
					orgDS, record);
			addVirtualCharge.show();

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void openOrganization() {
		try {
			ListGridRecord record = listGrid.getSelectedRecord();
			if (record == null) {
				SC.say(CallCenterBK.constants.pleaseSelrecord());
				return;
			}
			Integer service_id = record.getAttributeAsInt("service_id");
			if (service_id == null
					|| !service_id.equals(Constants.serviceOrganization)) {
				SC.say(CallCenterBK.constants.selRecordIsNotOrg());
				return;
			}

			Integer mainID = record.getAttributeAsInt("mainID");
			if (mainID == null) {
				SC.say(CallCenterBK.constants.invalidOrganization());
				return;
			}
			Criteria criteria = new Criteria();
			criteria.setAttribute("main_id", mainID);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("customOrgSearchForCallCenterByMainId");
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

						showOrgDialog(pRecord);
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

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);

			String phone = phoneItem.getValueAsString();
			if (phone == null || phone.trim().equals("")) {
				SC.say(CallCenterBK.constants.pleaseEnterPhone());
				return;
			}
			criteria.setAttribute("phone", phone);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"customOrgAndAbonentSearchForCallCenter");
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

	private void showOrgDialog(ListGridRecord pRecord) {
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
