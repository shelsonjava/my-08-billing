package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.ArrayList;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgContractorPhones extends Window {

	private IButton okButton;
	private IButton closeButton;

	private DynamicForm searchForm;
	private TextItem organizationNameItem;
	private TextItem departmentNameItem;
	private TextItem phoneItem;
	private ButtonItem findItem;
	private ButtonItem clearItem;

	private ListGrid organizationGrid;
	private ListGrid departmentGrid;
	private ListGrid phoneGrid;

	private ListGrid orgPhonesGrid;

	private ListGrid contractPhonesGrid;

	private ListGrid listGridPhones;

	private DataSource OrgDS;
	private DataSource OrgDepartmentDS;
	private DataSource ContractorsPhonesDS;

	public DlgContractorPhones(Integer organization_id, ListGrid listGridPhones) {
		try {
			this.listGridPhones = listGridPhones;
			OrgDS = DataSource.get("OrgDS");
			OrgDepartmentDS = DataSource.get("OrgDepartmentDS");
			ContractorsPhonesDS = DataSource.get("ContractorsPhonesDS");

			setTitle(CallCenterBK.constants.advParameters());
			setHeight(680);
			setWidth(1000);
			setShowMinimizeButton(false);
			setIsModal(true);
			setShowModalMask(true);
			setCanDrag(false);
			setCanDragReposition(false);
			setCanDragResize(false);
			setCanDragScroll(false);
			centerInPage();

			VLayout mainLayout = new VLayout(5);
			mainLayout.setMargin(2);
			mainLayout.setWidth100();
			mainLayout.setHeight100();

			HLayout hLayout = new HLayout();
			hLayout.setMembersMargin(10);
			hLayout.setWidth100();
			hLayout.setHeight100();
			mainLayout.addMember(hLayout);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth100();
			searchForm.setNumCols(8);
			searchForm.setTitleOrientation(TitleOrientation.LEFT);

			organizationNameItem = new TextItem();
			organizationNameItem
					.setTitle(CallCenterBK.constants.organization());
			organizationNameItem.setWidth(200);
			organizationNameItem.setName("organizationNameItem");

			departmentNameItem = new TextItem();
			departmentNameItem.setTitle(CallCenterBK.constants.department());
			departmentNameItem.setWidth(200);
			departmentNameItem.setName("departmentNameItem");

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setWidth(100);
			phoneItem.setName("phoneItem");

			findItem = new ButtonItem();
			findItem.setTitle(CallCenterBK.constants.find());
			findItem.setStartRow(false);
			findItem.setEndRow(false);

			clearItem = new ButtonItem();
			clearItem.setTitle(CallCenterBK.constants.clear());
			clearItem.setStartRow(false);
			clearItem.setEndRow(true);

			KeyPressHandler enterKey = new KeyPressHandler() {

				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			};

			organizationNameItem.addKeyPressHandler(enterKey);
			departmentNameItem.addKeyPressHandler(enterKey);
			phoneItem.addKeyPressHandler(enterKey);

			findItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

				@Override
				public void onClick(
						com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
					search();
				}
			});

			clearItem
					.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

						@Override
						public void onClick(
								com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
							organizationNameItem.setValue("");
							departmentNameItem.setValue("");
							phoneItem.setValue("");

							Criteria orgGridCriteria = new Criteria();
							orgGridCriteria.setAttribute("pp_organization_id",
									80353);
							organizationGrid.fetchData(orgGridCriteria);
						}
					});

			searchForm.setFields(organizationNameItem, departmentNameItem,
					phoneItem, findItem, clearItem);

			/*-------------------------------------*/

			organizationGrid = new ListGrid();
			organizationGrid.setDataSource(OrgDS);

			organizationGrid
					.setFetchOperation("customOrgSearchForCallCenterNew");

			organizationGrid.setWidth100();
			organizationGrid.setHeight(200);
			organizationGrid.setAlternateRecordStyles(true);
			organizationGrid.setAutoFetchData(false);
			// organizationGrid.setShowFilterEditor(true);
			// organizationGrid.setFilterOnKeypress(true);

			ListGridField organizationName = new ListGridField(
					"organization_name", CallCenterBK.constants.organization());

			organizationGrid.setFields(organizationName);

			Criteria orgGridCriteria = new Criteria();
			orgGridCriteria.setAttribute("pp_organization_id", organization_id);
			organizationGrid.fetchData(orgGridCriteria);

			organizationGrid.addRecordClickHandler(new RecordClickHandler() {

				@Override
				public void onRecordClick(RecordClickEvent event) {
					Record record = organizationGrid.getSelectedRecord();
					fetchDepartmentAndPhones(record);
				}
			});

			/*--------------------------------------------*/

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);

			ToolStripButton addPhones = new ToolStripButton();
			addPhones.setIcon("restore.png");
			addPhones.setTitle("გადატანა");

			addPhones.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord gridRecord[] = phoneGrid.getRecords();
					for (ListGridRecord listGridRecord : gridRecord) {
						if (listGridRecord != null
								&& listGridRecord.getAttribute("phone") != null) {
							addPhone(listGridRecord.getAttribute("phone"),
									false);
						}
					}

				}
			});

			toolStrip.addButton(addPhones);

			/*--------------------------------------------*/

			departmentGrid = new ListGrid();
			departmentGrid.setDataSource(OrgDepartmentDS);

			departmentGrid.setFetchOperation("customOrgDepSearchByOrgId");

			departmentGrid.setWidth100();
			departmentGrid.setHeight100();
			departmentGrid.setAlternateRecordStyles(true);
			departmentGrid.setAutoFetchData(false);
			// departmentGrid.setShowFilterEditor(true);
			// departmentGrid.setFilterOnKeypress(true);

			ListGridField departmentName = new ListGridField("department",
					CallCenterBK.constants.department());

			departmentGrid.setFields(departmentName);

			departmentGrid.addRecordClickHandler(new RecordClickHandler() {

				@Override
				public void onRecordClick(RecordClickEvent event) {

					Record record = departmentGrid.getSelectedRecord();

					fetchPhones(record);

				}
			});

			/*--------------------------------------------*/

			phoneGrid = new ListGrid();
			phoneGrid.setDataSource(ContractorsPhonesDS);

			phoneGrid.setFetchOperation("searchPhones");

			phoneGrid.setWidth100();
			phoneGrid.setHeight100();
			phoneGrid.setAlternateRecordStyles(true);
			phoneGrid.setAutoFetchData(false);
			// phoneGrid.setShowFilterEditor(true);
			// phoneGrid.setFilterOnKeypress(true);

			ListGridField phoneCol = new ListGridField("phone",
					CallCenterBK.constants.phone());

			phoneGrid.setFields(phoneCol);

			/*******************/

			orgPhonesGrid = new ListGrid() {
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					if (record == null)
						return super.getCellCSSText(record, rowNum, colNum);
					String field = orgPhonesGrid.getField(colNum).getName();
					if ("name".equals(field)) {
						String tp = record.getAttribute("pr");
						if ("1".equals(tp)) {
							return "font-weight:bold;";
						} else if ("2".equals(tp)) {
							return "color:blue;";
						} else if ("3".equals(tp)) {
							String cp_id = record.getAttribute("cp_id");
							if (cp_id != null)
								return "color:red;";
						}
					}
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};

			DataSource dsSource = DataSource.get("ContractorsPhonesDS");
			orgPhonesGrid.setDataSource(dsSource);

			Criteria cr = new Criteria();
			cr.setAttribute("organization_id", 80353);
			cr.setAttribute("contract_id", 5);

			orgPhonesGrid.setFetchOperation("organisationPhones");

			orgPhonesGrid.setWidth("83%");
			orgPhonesGrid.setHeight100();
			orgPhonesGrid.setAlternateRecordStyles(true);
			orgPhonesGrid.setAutoFetchData(false);
			orgPhonesGrid.setShowFilterEditor(true);
			orgPhonesGrid.setFilterOnKeypress(true);

			orgPhonesGrid.fetchData(cr);

			ListGridField name = new ListGridField("name",
					CallCenterBK.constants.name());
			name.setAlign(Alignment.LEFT);

			ListGridField add = new ListGridField("add", "", 20);
			add.setAlign(Alignment.LEFT);
			add.setType(ListGridFieldType.ICON);
			add.setCellIcon("restore.png");
			add.setCanEdit(false);
			add.setCanFilter(true);
			add.setFilterEditorType(new SpacerItem());
			add.setCanGroupBy(false);
			add.setCanSort(false);

			orgPhonesGrid.addCellClickHandler(new CellClickHandler() {

				@Override
				public void onCellClick(CellClickEvent event) {
					String field_name = orgPhonesGrid.getField(
							event.getColNum()).getName();
					if (field_name.equals("add"))
						addPhones(event.getRecord());
				}
			});

			orgPhonesGrid.setFields(name, add);

			contractPhonesGrid = new ListGrid();
			contractPhonesGrid.setWidth("17%");
			contractPhonesGrid.setHeight100();
			contractPhonesGrid.setCanRemoveRecords(true);
			contractPhonesGrid.setPreventDuplicates(true);
			contractPhonesGrid.setDuplicateDragMessage(CallCenterBK.constants
					.thisOrgActAlreadyChoosen());

			ListGridField phone = new ListGridField("phone",
					CallCenterBK.constants.phone());
			phone.setAlign(Alignment.LEFT);

			ListGridField remove = new ListGridField("remove", "", 20);
			remove.setAlign(Alignment.LEFT);
			remove.setType(ListGridFieldType.ICON);
			remove.setCellIcon("[SKINIMG]/actions/remove.png");
			remove.setCanEdit(false);
			remove.setCanFilter(true);
			remove.setFilterEditorType(new SpacerItem());
			remove.setCanGroupBy(false);
			remove.setCanSort(false);

			DataSource dsDest = new DataSource();
			DataSourceField dsPhone = new DataSourceField("phone",
					FieldType.TEXT);
			dsPhone.setPrimaryKey(true);
			dsDest.setFields(dsPhone);
			dsDest.setClientOnly(true);

			contractPhonesGrid.setDataSource(dsDest);
			contractPhonesGrid.setFields(phone, remove);
			contractPhonesGrid.setShowFilterEditor(true);
			contractPhonesGrid.setFilterOnKeypress(true);

			RecordList phoneList = listGridPhones.getRecordList();

			if (phoneList != null && !phoneList.isEmpty()) {
				for (int i = 0; i < phoneList.getLength(); i++) {
					contractPhonesGrid.addData(phoneList.get(i));
				}
			}

			/****************************************************/

			HLayout gridsLayout = new HLayout();
			gridsLayout.setMargin(2);
			gridsLayout.setHeight100();
			gridsLayout.setWidth100();

			// gridsLayout.setMembers(orgPhonesGrid, contractPhonesGrid);

			HLayout hLayout2 = new HLayout();
			hLayout2.setMembers(departmentGrid, phoneGrid);

			VLayout vLayout = new VLayout();
			vLayout.setMembers(searchForm, organizationGrid, toolStrip,
					hLayout2);

			gridsLayout.setMembers(vLayout, contractPhonesGrid);
			hLayout.addMember(gridsLayout);

			/********************************************************/

			HLayout buttonsLayout = new HLayout();
			buttonsLayout.setMargin(4);
			buttonsLayout.setWidth100();
			buttonsLayout.setAlign(Alignment.RIGHT);
			buttonsLayout.setMembersMargin(5);
			buttonsLayout.setHeight(50);
			mainLayout.addMember(buttonsLayout);

			okButton = new IButton();
			okButton.setTitle(CallCenterBK.constants.save());

			closeButton = new IButton();
			closeButton.setTitle(CallCenterBK.constants.close());

			buttonsLayout.setMembers(okButton, closeButton);

			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});
			addCloseClickHandler(new CloseClickHandler() {
				@Override
				public void onCloseClick(CloseClickEvent event) {
				}
			});
			addItem(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		Criteria orgGridCriteria = new Criteria();
		orgGridCriteria.setAttribute("parrent_organization_id", 80353);
		if (organizationNameItem.getValueAsString() != null
				&& !organizationNameItem.getValueAsString().equals("")) {
			orgGridCriteria.setAttribute("organization_name",
					organizationNameItem.getValueAsString());
		}

		if (departmentNameItem.getValueAsString() != null
				&& !departmentNameItem.getValueAsString().equals("")) {
			orgGridCriteria.setAttribute("department",
					departmentNameItem.getValueAsString());
		}

		if (phoneItem.getValueAsString() != null
				&& !phoneItem.getValueAsString().equals("")) {
			orgGridCriteria.setAttribute("phone", phoneItem.getValueAsString());
		}
		organizationGrid.fetchData(orgGridCriteria, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				Record r[] = response.getData();
				if (r != null && r.length > 0)
					organizationGrid.selectRecord(r[0]);
				Record record = organizationGrid.getSelectedRecord();
				record = record == null ? new Record() : record;
				fetchDepartmentAndPhones(record);

				// Record r[]= response.getData();
				// if(r==null||r.length==0)
				// fetchDepartmentAndPhones(new Record());
				// else
				// {
				//
				//
				// }

			}
		});

	}

	protected void addPhones(ListGridRecord record) {
		if (record == null)
			return;

		String tp = record.getAttribute("pr");
		if (tp == null)
			return;
		if ("3".equals(tp)) {
			String phone = record.getAttribute("phone");
			if (phone != null)
				addPhone(phone, true);
			orgPhonesGrid.redraw();
			return;
		}
		String id = record.getAttribute("rid");
		if (id == null)
			return;
		DataSource ds = DataSource.get("ContractorsPhonesDS");
		Criteria cr = new Criteria();
		DSRequest req = new DSRequest();
		req.setOperationId("searchPhones");
		if ("1".equals(tp)) {
			cr.setAttribute("organization_id", id);
		}
		if ("2".equals(tp)) {
			cr.setAttribute("org_department_id", id);
		}
		ds.fetchData(cr, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				if (response.getData() != null) {
					Record records[] = response.getData();
					for (Record record2 : records) {
						String phone = record2.getAttribute("phone");
						if (phone != null)
							addPhone(phone, true);
					}
					orgPhonesGrid.redraw();

				}

			}
		}, req);

	}

	private void addPhone(String phone, boolean setSource) {
		Record record = contractPhonesGrid.getRecordList().find("phone", phone);
		if (record == null) {
			record = new Record();
			record.setAttribute("phone", phone);
			contractPhonesGrid.getDataSource().addData(record);
			contractPhonesGrid.fetchData();
		}

	}

	private void fetchPhones(Record record) {
		String org_department_id = record.getAttribute("org_department_id");
		String organization_id = record.getAttribute("organization_id");
		Criteria phoneCriteria = new Criteria();
		if (org_department_id != null)
			phoneCriteria.setAttribute("org_department_id", org_department_id);
		else if (organization_id != null)
			phoneCriteria.setAttribute("organization_id", organization_id);
		else
			phoneCriteria.setAttribute("organization_id", -1);

		phoneGrid.fetchData(phoneCriteria);
	}

	private void fetchDepartmentAndPhones(Record record) {
		String organization_id = record.getAttribute("organization_id");
		organization_id = organization_id == null ? "-1" : organization_id;
		Criteria depGridCriteria = new Criteria();
		depGridCriteria.setAttribute("organization_id", organization_id);
		departmentGrid.fetchData(depGridCriteria);
		fetchPhones(record);
	}
}
