package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class DlgManageOrgDepartments extends Window {

	// main layout
	private VLayout hLayout;

	private DynamicForm searchForm;
	private TextItem orgDepNameItem;
	private TextItem orgDepAddrItem;
	private TextItem orgDepPhoneItem;
	private CheckboxItem orgDepPhoneContItem;

	private ListGrid orgDepListGrid;
	private ListGrid orgDepPhonesListGrid;
	private DataSource orgDepDataSource;

	protected ListGridRecord listGridRecord;
	protected TreeGrid orgTreeGrid;

	private ToolStripButton sortBtn;
	private ToolStripButton addNewOrgBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	public DlgManageOrgDepartments(ListGridRecord listGridRecord,
			TreeGrid orgTreeGrid) {
		try {
			this.orgTreeGrid = orgTreeGrid;
			this.listGridRecord = listGridRecord;
			setTitle(CallCenterBK.constants.manageOrgDepartments());

			setHeight(760);
			setWidth(1272);
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

			VLayout content = new VLayout();
			content.setHeight100();
			content.setWidth100();
			content.setMembersMargin(10);
			hLayout.addMember(content);

			HLayout searchLayout = new HLayout();
			searchLayout.setWidth100();
			content.addMember(searchLayout);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth100();
			searchForm.setNumCols(4);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			searchLayout.addMember(searchForm);

			orgDepNameItem = new TextItem();
			orgDepNameItem.setTitle(CallCenterBK.constants.department());
			orgDepNameItem.setWidth(350);
			orgDepNameItem.setName("department");

			orgDepAddrItem = new TextItem();
			orgDepAddrItem.setTitle(CallCenterBK.constants.address());
			orgDepAddrItem.setWidth(350);
			orgDepAddrItem.setName("real_address_descr");

			orgDepPhoneItem = new TextItem();
			orgDepPhoneItem.setTitle(CallCenterBK.constants.phone());
			orgDepPhoneItem.setWidth(150);
			orgDepPhoneItem.setName("phone");

			orgDepPhoneContItem = new CheckboxItem();
			orgDepPhoneContItem.setTitle(CallCenterBK.constants
					.contPhoneShort());
			orgDepPhoneContItem.setWidth(138);
			orgDepPhoneContItem.setName("phone_contact");

			searchForm.setFields(orgDepNameItem, orgDepAddrItem,
					orgDepPhoneItem, orgDepPhoneContItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setHeight(38);
			buttonLayout.setAlign(Alignment.RIGHT);
			buttonLayout.setDefaultLayoutAlign(VerticalAlignment.BOTTOM);

			IButton clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			IButton findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			searchLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			content.addMember(toolStrip);

			addNewOrgBtn = new ToolStripButton(
					CallCenterBK.constants.addNewOrgDepartment(), "addIcon.png");
			addNewOrgBtn.setLayoutAlign(Alignment.LEFT);
			addNewOrgBtn.setWidth(50);
			toolStrip.addButton(addNewOrgBtn);

			editBtn = new ToolStripButton(CallCenterBK.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			toolStrip.addSeparator();

			sortBtn = new ToolStripButton(CallCenterBK.constants.sort(),
					"sort.png");
			sortBtn.setLayoutAlign(Alignment.LEFT);
			sortBtn.setWidth(50);
			toolStrip.addButton(sortBtn);

			orgDepDataSource = DataSource.get("OrgDepartmentDS");
			Criteria criteria = new Criteria();
			criteria.setAttribute("p_organization_id",
					listGridRecord.getAttributeAsInt("organization_id"));

			orgDepListGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer isBold = countryRecord.getAttributeAsInt("isBold");
					if (isBold != null && isBold.equals(1)) {
						return "font-weight: bold;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};
			orgDepListGrid.setLeft(50);
			orgDepListGrid.setTop(50);
			orgDepListGrid.setWidth100();
			orgDepListGrid.setHeight100();
			orgDepListGrid.setFetchOperation("orgDepartSearch");
			orgDepListGrid.setCriteria(criteria);
			orgDepListGrid.setDataSource(orgDepDataSource);
			orgDepListGrid.setAutoFetchData(true);
			orgDepListGrid.setAutoSaveEdits(false);
			orgDepListGrid.setCanSelectAll(false);
			orgDepListGrid.setWrapCells(true);
			orgDepListGrid.setFixedRecordHeights(false);
			orgDepListGrid.setCanSort(false);

			content.addMember(orgDepListGrid);
			ListGridField tree_org_parrent = new ListGridField("tree_parrent",
					" ", 30);
			tree_org_parrent.setAlign(Alignment.CENTER);
			tree_org_parrent.setType(ListGridFieldType.IMAGE);
			tree_org_parrent.setImageURLPrefix("orgtypes/");
			tree_org_parrent.setImageURLSuffix(".png");
			tree_org_parrent.setCanSort(false);
			tree_org_parrent.setCanFilter(false);

			ListGridField tree_org_child = new ListGridField("tree_child", " ",
					30);
			tree_org_child.setAlign(Alignment.CENTER);
			tree_org_child.setType(ListGridFieldType.IMAGE);
			tree_org_child.setImageURLPrefix("orgtypes/");
			tree_org_child.setImageURLSuffix(".png");
			tree_org_child.setCanSort(false);
			tree_org_child.setCanFilter(false);

			TreeGridField organization_name = new TreeGridField("department",
					CallCenterBK.constants.department());
			organization_name.setTreeField(true);

			ListGridField real_address = new ListGridField(
					"full_address_not_hidden",
					CallCenterBK.constants.realAddress(), 400);
			orgDepListGrid.setFields(organization_name, real_address,
					tree_org_parrent, tree_org_child);

			orgDepPhonesListGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer contact_phones = countryRecord
							.getAttributeAsInt("contact_phones");
					if (contact_phones != null && contact_phones.equals(1)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			DataSource phoneDS = DataSource.get("OrgDepPhoneDS");

			orgDepPhonesListGrid.setAlternateRecordStyles(true);
			orgDepPhonesListGrid.setWidth100();
			orgDepPhonesListGrid.setHeight(200);
			orgDepPhonesListGrid.setDataSource(phoneDS);
			orgDepPhonesListGrid.setAutoFetchData(false);
			orgDepPhonesListGrid.setCanEdit(false);
			orgDepPhonesListGrid.setCanRemoveRecords(false);
			orgDepPhonesListGrid.setFetchOperation("searchOrgDepPhones");
			orgDepPhonesListGrid.setCriteria(new Criteria());
			orgDepPhonesListGrid.setCanSort(false);
			orgDepPhonesListGrid.setCanResizeFields(false);
			orgDepPhonesListGrid.setShowFilterEditor(true);
			orgDepPhonesListGrid.setFilterOnKeypress(true);
			orgDepPhonesListGrid.setWrapCells(true);
			orgDepPhonesListGrid.setFixedRecordHeights(false);
			orgDepPhonesListGrid.setCanSelectText(true);
			orgDepPhonesListGrid.setCanDragSelectText(true);

			ListGridField phone = new ListGridField("phone",
					CallCenterBK.constants.phone());
			phone.setCanFilter(true);

			ListGridField phone_state_descr = ClientUtils
					.createDescrFilterField("phone_state_id",
							CallCenterBK.constants.phoneState(), 150, 52000,
							false);
			phone_state_descr.setAlign(Alignment.CENTER);

			ListGridField hidden_by_request_descr = ClientUtils
					.createCommonCloseFilterField("hidden_by_request",
							CallCenterBK.constants.openClose(), 150, false);
			hidden_by_request_descr.setAlign(Alignment.CENTER);

			ListGridField phone_contract_type_descr = ClientUtils
					.createDescrFilterField("phone_contract_type",
							CallCenterBK.constants.phoneStatus(), 150, 53000,
							false);
			phone_contract_type_descr.setAlign(Alignment.CENTER);

			ListGridField for_contact_descr = ClientUtils
					.createCommonCloseFilterField("for_contact",
							CallCenterBK.constants.contactPhone(), 150, 1,
							false);
			for_contact_descr.setAlign(Alignment.CENTER);

			ListGridField phone_type_descr = ClientUtils
					.createDescrFilterField("phone_type_id",
							CallCenterBK.constants.type(), 150, 54000, false);
			phone_type_descr.setAlign(Alignment.CENTER);

			ListGridField is_parallel_descr = ClientUtils
					.createCommonCloseFilterField("is_parallel",
							CallCenterBK.constants.paraller(), 150, 1, false);
			is_parallel_descr.setAlign(Alignment.CENTER);

			orgDepPhonesListGrid.setFields(phone, phone_state_descr,
					hidden_by_request_descr, phone_contract_type_descr,
					for_contact_descr, phone_type_descr, is_parallel_descr);

			content.addMember(orgDepPhonesListGrid);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.addMember(cancItem);

			hLayout.addMember(hLayoutItem);

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					orgDepNameItem.clearValue();
					orgDepAddrItem.clearValue();
				}
			});
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchDepartment();
				}
			});

			orgDepNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						searchDepartment();
					}
				}
			});
			orgDepAddrItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						searchDepartment();
					}
				}
			});

			orgDepPhoneItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						searchDepartment();
					}
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					orgDepNameItem.clearValue();
					orgDepAddrItem.clearValue();
					orgDepPhoneItem.clearValue();
					orgDepPhoneContItem.setValue(false);
				}
			});

			orgDepListGrid
					.addSelectionChangedHandler(new SelectionChangedHandler() {
						public void onSelectionChanged(SelectionEvent event) {
							Record record = event.getRecord();
							Integer org_department_id = record
									.getAttributeAsInt("org_department_id");
							Criteria criteria = new Criteria();
							criteria.setAttribute("org_department_id",
									org_department_id);
							DSRequest dsRequest = new DSRequest();
							dsRequest.setAttribute("operationId",
									"searchOrgDepPhones");
							orgDepPhonesListGrid.invalidateCache();
							orgDepPhonesListGrid.filterData(criteria,
									new DSCallback() {
										@Override
										public void execute(
												DSResponse response,
												Object rawData,
												DSRequest request) {
										}
									}, dsRequest);
						}
					});
			fillFields();
			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void searchDepartment() {
		try {
			Integer p_organization_id = listGridRecord
					.getAttributeAsInt("organization_id");
			String departament = orgDepNameItem.getValueAsString();
			String real_address_descr = orgDepAddrItem.getValueAsString();

			Criteria criteria = new Criteria();
			criteria.setAttribute("p_organization_id", p_organization_id);

			if (departament != null && !departament.trim().equals("")) {
				String tmp = departament.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("department" + i, item);
					i++;
				}
			}
			criteria.setAttribute("real_address_descr", real_address_descr);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "orgDepartSearch");
			orgDepListGrid.invalidateCache();
			orgDepListGrid.fetchData(criteria, new DSCallback() {
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

	private void fillFields() {
		try {
			if (listGridRecord != null) {
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	protected void save() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
