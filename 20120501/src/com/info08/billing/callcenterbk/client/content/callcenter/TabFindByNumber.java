package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewOrg;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewSubscriberOrOrg;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
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

public class TabFindByNumber extends Tab {

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
	private DataSource phoneViewDS;

	public TabFindByNumber() {

		setTitle(CallCenterBK.constants.findByNumber());
		setCanClose(true);

		phoneViewDS = DataSource.get("PhoneViewDS");

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

		listGrid = new ListGrid() {

			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord gridRecord = (ListGridRecord) record;
				if (gridRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				Integer owner_type = gridRecord.getAttributeAsInt("owner_type");
				if (owner_type == null || owner_type.equals(0)) {
					return super.getCellCSSText(record, rowNum, colNum);
				}

				Integer contact_phones = gridRecord
						.getAttributeAsInt("for_contact");

				Integer status = gridRecord.getAttributeAsInt("status");
				Integer super_priority = gridRecord
						.getAttributeAsInt("super_priority");
				Integer important_remark = gridRecord
						.getAttributeAsInt("important_remark");
				String columnName = listGrid.getFieldName(colNum);

				boolean isphoneColumns = ClientUtils.containsOneOfString(
						columnName, "phone_shown", "phone_contract_type_desc");
				if (super_priority != null && super_priority < 0
						&& (!isphoneColumns)) {
					return "color:red;";
				} else if (status != null && status.equals(2)
						&& (!isphoneColumns)) {
					if (important_remark != null
							&& important_remark.intValue() == -1
							&& (!isphoneColumns)) {
						return "color:red;";
					} else {
						return "color:gray;";
					}
				} else if (status != null && status.equals(1)
						&& (!isphoneColumns)) {
					if (important_remark != null
							&& important_remark.intValue() == -1
							&& (!isphoneColumns)) {
						return "color:red;";
					} else {
						return "color:blue;";
					}

				} else if (status != null && status.equals(3)
						&& (!isphoneColumns)) {
					if (important_remark != null
							&& important_remark.intValue() == -1
							&& (!isphoneColumns)) {
						return "color:red;";
					} else {
						return "color:green;";
					}
				} else if (contact_phones == null || contact_phones.equals(1)
						&& (isphoneColumns)) {
					return "color: red;";
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};

		};
		listGrid.setWidth(1100);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(phoneViewDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("customSearch");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField full_name = new ListGridField("full_name",
				CallCenterBK.constants.dasaxeleba());
		full_name.setAlign(Alignment.LEFT);

		ListGridField owner_type = new ListGridField("owner_type_descr",
				CallCenterBK.constants.type(), 50);
		owner_type.setAlign(Alignment.CENTER);

		ListGridField town_name = new ListGridField("town_name",
				CallCenterBK.constants.town(), 100);
		town_name.setAlign(Alignment.LEFT);

		ListGridField address = new ListGridField("concat_address",
				CallCenterBK.constants.street(), 350);
		address.setAlign(Alignment.LEFT);

		ListGridField phone = new ListGridField("phone_shown",
				CallCenterBK.constants.phone(), 80);

		ListGridField phone_contract_type_desc = new ListGridField(
				"phone_contract_type_desc",
				CallCenterBK.constants.phoneStatus(), 100);

		listGrid.setFields(full_name, owner_type, town_name, address, phone,
				phone_contract_type_desc);

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
				DlgViewSubscriberOrOrg dlgViewAbonentOrOrg = new DlgViewSubscriberOrOrg(
						listGrid, phoneViewDS, listGrid.getSelectedRecord());
				dlgViewAbonentOrOrg.show();
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
			ListGridRecord record = listGrid.getSelectedRecord();
			if (record == null) {
				SC.say(CallCenterBK.constants.pleaseSelrecord());
				return;
			}
			Integer owner_type = record.getAttributeAsInt("owner_type");
			if (owner_type == null || !owner_type.equals(1)) {
				SC.say(CallCenterBK.constants.selRecordIsNotOrg());
				return;
			}

			Integer mainID = record.getAttributeAsInt("mainID");
			if (mainID == null) {
				SC.say(CallCenterBK.constants.invalidOrganization());
				return;
			}
			Criteria criteria = new Criteria();
			criteria.setAttribute("organization_id", mainID);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("customOrgSearchForCallCenterByMainId");
			phoneViewDS.fetchData(criteria, new DSCallback() {
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
						pRecord.setAttribute("town_name",
								record.getAttributeAsString("town_name"));
						pRecord.setAttribute("town_district_name", record
								.getAttributeAsString("town_district_name"));
						pRecord.setAttribute("street_location",
								record.getAttributeAsString("street_location"));
						pRecord.setAttribute("index_text",
								record.getAttributeAsString("index_text"));
						pRecord.setAttribute("legal_statuse",
								record.getAttributeAsString("legal_statuse"));
						pRecord.setAttribute("town_id",
								record.getAttributeAsInt("town_id"));
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

			String phone = phoneItem.getValueAsString();
			if (phone == null || phone.trim().equals("")) {
				SC.say(CallCenterBK.constants.pleaseEnterPhone());
				return;
			}
			criteria.setAttribute("phone", phone);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "customSearch");
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
			DlgViewOrg dlgViewOrg = new DlgViewOrg(phoneViewDS, pRecord);
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
