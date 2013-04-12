package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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
import com.smartgwt.client.util.BooleanCallback;
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
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class DlgManageOrgDepartments extends Window {

	// main layout
	private VLayout hLayout;

	private DynamicForm searchForm;
	private TextItem orgFullNameItem;
	private TextItem orgDepNameItem;
	private TextItem orgDepAddrItem;
	private TextItem orgDepPhoneItem;
	private CheckboxItem orgDepPhoneContItem;

	private ListGrid orgDepListGrid;
	private ListGrid orgDepPhonesListGrid;
	private DataSource orgDepDataSource;

	protected Record orgListGridRecord;
	protected ListGrid orgTreeGrid;

	private ToolStripButton sortBtn;
	private ToolStripButton addOrgDepBtn;
	private ToolStripButton editOrgDepBtn;
	private ToolStripButton deleteOrgDepBtn;
	private ToolStripButton sortPhonesBtn;
	private ToolStripButton openOrgBtn;

	public DlgManageOrgDepartments(final Record listGridRecord,
			ListGrid orgTreeGrid, final Long department_id) {
		try {
			this.orgTreeGrid = orgTreeGrid;
			this.orgListGridRecord = listGridRecord;
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

			String fullName = listGridRecord
					.getAttributeAsString("original_org_name")
					+ " --- "
					+ listGridRecord
							.getAttributeAsString("full_address_not_hidden");

			orgFullNameItem = new TextItem();
			orgFullNameItem.setTitle(CallCenterBK.constants.department());
			orgFullNameItem.setWidth("100%");
			orgFullNameItem.setName("orgFullNameItem");
			orgFullNameItem.setCanEdit(false);
			orgFullNameItem.setColSpan(4);
			orgFullNameItem.setValue(fullName);

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
			orgDepPhoneContItem.setWidth(180);
			orgDepPhoneContItem.setName("phone_contact");

			searchForm.setFields(orgFullNameItem, orgDepNameItem,
					orgDepAddrItem, orgDepPhoneItem, orgDepPhoneContItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setHeight(75);
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

			addOrgDepBtn = new ToolStripButton(
					CallCenterBK.constants.addNewOrgDepartment(), "addIcon.png");
			addOrgDepBtn.setLayoutAlign(Alignment.LEFT);
			addOrgDepBtn.setWidth(50);
			toolStrip.addButton(addOrgDepBtn);

			editOrgDepBtn = new ToolStripButton(
					CallCenterBK.constants.modify(), "editIcon.png");
			editOrgDepBtn.setLayoutAlign(Alignment.LEFT);
			editOrgDepBtn.setWidth(50);
			toolStrip.addButton(editOrgDepBtn);

			deleteOrgDepBtn = new ToolStripButton(
					CallCenterBK.constants.disable(), "deleteIcon.png");
			deleteOrgDepBtn.setLayoutAlign(Alignment.LEFT);
			deleteOrgDepBtn.setWidth(50);
			toolStrip.addButton(deleteOrgDepBtn);

			toolStrip.addSeparator();

			sortBtn = new ToolStripButton(CallCenterBK.constants.sort(),
					"sort.png");
			sortBtn.setLayoutAlign(Alignment.LEFT);
			sortBtn.setWidth(50);
			toolStrip.addButton(sortBtn);

			openOrgBtn = new ToolStripButton(
					CallCenterBK.constants.organization(), "organization.gif");
			openOrgBtn.setLayoutAlign(Alignment.LEFT);
			openOrgBtn.setWidth(50);
			toolStrip.addButton(openOrgBtn);

			toolStrip.addFill();

			sortPhonesBtn = new ToolStripButton(
					CallCenterBK.constants.sortPhones(), "sort.png");
			sortPhonesBtn.setLayoutAlign(Alignment.LEFT);
			sortPhonesBtn.setWidth(50);
			toolStrip.addButton(sortPhonesBtn);

			orgDepDataSource = DataSource.get("OrgDepartmentDS");
			Criteria criteria = new Criteria();
			criteria.setAttribute("organization_id",
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
			orgDepListGrid.setAutoFetchData(department_id == null);
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

			TreeGridField department = new TreeGridField("department",
					CallCenterBK.constants.department());
			department.setTreeField(true);

			ListGridField real_address = new ListGridField(
					"full_address_not_hidden",
					CallCenterBK.constants.realAddress(), 400);
			orgDepListGrid.setFields(tree_org_parrent, tree_org_child,
					department, real_address);

			if (department_id != null) {
				orgDepListGrid.fetchData(criteria, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record rec = orgDepListGrid.getRecordList().find(
								"org_department_id", department_id);
						if (rec != null)
							orgDepListGrid.selectRecord(rec);

					}
				});
			}

			ToolStrip toolStrip1 = new ToolStrip();
			toolStrip1.setWidth100();
			toolStrip1.setPadding(5);
			content.addMember(toolStrip1);

			ToolStripButton addNewPhone = new ToolStripButton(
					CallCenterBK.constants.add(), "addIcon.png");
			addNewPhone.setLayoutAlign(Alignment.LEFT);
			addNewPhone.setWidth(50);
			toolStrip1.addButton(addNewPhone);

			ToolStripButton editPhoneBtn = new ToolStripButton(
					CallCenterBK.constants.modify(), "editIcon.png");
			editPhoneBtn.setLayoutAlign(Alignment.LEFT);
			editPhoneBtn.setWidth(50);
			toolStrip1.addButton(editPhoneBtn);

			ToolStripButton deletePhoneBtn = new ToolStripButton(
					CallCenterBK.constants.disable(), "deleteIcon.png");
			deletePhoneBtn.setLayoutAlign(Alignment.LEFT);
			deletePhoneBtn.setWidth(50);
			toolStrip1.addButton(deletePhoneBtn);

			orgDepPhonesListGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer for_contact = countryRecord
							.getAttributeAsInt("for_contact");
					if (for_contact != null && for_contact.equals(1)) {
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
			orgDepPhonesListGrid.setShowRowNumbers(true);

			ListGridField phone = new ListGridField("phone",CallCenterBK.constants.phone());
			phone.setCanFilter(true);

			ListGridField rec_upd_date = new ListGridField("rec_upd_date",CallCenterBK.constants.updDate(), 130);
			rec_upd_date.setAlign(Alignment.CENTER);
			rec_upd_date.setCanFilter(false);

			ListGridField phone_state_descr = new ListGridField(
					"phone_state_descr", CallCenterBK.constants.phoneState(),
					150);
			phone_state_descr.setAlign(Alignment.CENTER);
			phone_state_descr.setCanFilter(false);

			ListGridField hidden_by_request_descr = new ListGridField(
					"hidden_by_request_descr",
					CallCenterBK.constants.openClose(), 150);
			hidden_by_request_descr.setAlign(Alignment.CENTER);
			hidden_by_request_descr.setCanFilter(false);

			ListGridField phone_contract_type_descr = new ListGridField(
					"phone_contract_type_descr",
					CallCenterBK.constants.phoneStatus(), 150);
			phone_contract_type_descr.setAlign(Alignment.CENTER);
			phone_contract_type_descr.setCanFilter(false);

			ListGridField for_contact_descr = new ListGridField(
					"for_contact_descr", CallCenterBK.constants.contactPhone(),
					150);
			for_contact_descr.setAlign(Alignment.CENTER);
			for_contact_descr.setCanFilter(false);

			ListGridField phone_type_descr = new ListGridField(
					"phone_type_descr", CallCenterBK.constants.type(), 150);
			phone_type_descr.setAlign(Alignment.CENTER);
			phone_type_descr.setCanFilter(false);

			ListGridField is_parallel_descr = new ListGridField(
					"is_parallel_descr", CallCenterBK.constants.paraller(), 150);
			is_parallel_descr.setAlign(Alignment.CENTER);
			is_parallel_descr.setCanFilter(false);

			orgDepPhonesListGrid.setFields(phone, rec_upd_date,
					phone_state_descr, hidden_by_request_descr,
					phone_contract_type_descr, for_contact_descr,
					phone_type_descr, is_parallel_descr);

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

			addOrgDepBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord depListGridRecord = orgDepListGrid
							.getSelectedRecord();
					Integer organization_id = orgListGridRecord
							.getAttributeAsInt("organization_id");
					DlgAddEditOrgDepartments dlgAddEditOrgDepartments = new DlgAddEditOrgDepartments(
							depListGridRecord, organization_id, null,
							orgDepListGrid, DlgManageOrgDepartments.this);
					dlgAddEditOrgDepartments.show();
				}
			});
			editOrgDepBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord depListGridRecord = orgDepListGrid
							.getSelectedRecord();
					if (depListGridRecord == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer organization_id = orgListGridRecord
							.getAttributeAsInt("organization_id");

					DlgAddEditOrgDepartments dlgAddEditOrgDepartments = new DlgAddEditOrgDepartments(
							null, organization_id, depListGridRecord,
							orgDepListGrid, DlgManageOrgDepartments.this);
					dlgAddEditOrgDepartments.show();
				}
			});
			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});

			deleteOrgDepBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = orgDepListGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					SC.ask(CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										removeDepartment(listGridRecord
												.getAttributeAsInt("org_department_id"));
									}
								}
							});
				}
			});

			sortBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					sort();
				}
			});

			sortPhonesBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					sort1();
				}
			});

			orgDepListGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord depListGridRecord = orgDepListGrid
									.getSelectedRecord();
							if (depListGridRecord == null) {
								SC.say(CallCenterBK.constants.warning(),
										CallCenterBK.constants
												.pleaseSelrecord());
								return;
							}
							Integer organization_id = orgListGridRecord
									.getAttributeAsInt("organization_id");

							DlgAddEditOrgDepartments dlgAddEditOrgDepartments = new DlgAddEditOrgDepartments(
									null, organization_id, depListGridRecord,
									orgDepListGrid,
									DlgManageOrgDepartments.this);
							dlgAddEditOrgDepartments.show();
						}
					});

			addNewPhone.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord depListGridRecord = orgDepListGrid
							.getSelectedRecord();
					if (depListGridRecord == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.pleaseSelrecord());
						return;
					}

					DlgAddEditOrgDepPhone addEditOrgDepPhone = new DlgAddEditOrgDepPhone(
							null, orgDepPhonesListGrid, depListGridRecord);
					addEditOrgDepPhone.show();
				}
			});
			editPhoneBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord orgDepPhoneListGridRecord = orgDepPhonesListGrid
							.getSelectedRecord();
					if (orgDepPhoneListGridRecord == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					ListGridRecord depListGridRecord = orgDepListGrid
							.getSelectedRecord();
					DlgAddEditOrgDepPhone addEditOrgDepPhone = new DlgAddEditOrgDepPhone(
							orgDepPhoneListGridRecord, orgDepPhonesListGrid,
							depListGridRecord);
					addEditOrgDepPhone.show();
				}
			});
			deletePhoneBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord orgDepPhoneListGridRecord = orgDepPhonesListGrid
							.getSelectedRecord();
					if (orgDepPhoneListGridRecord == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					SC.ask(CallCenterBK.constants.warning(),
							CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										deleteOrgDepPhone(orgDepPhoneListGridRecord);
									}
								}
							});
				}
			});

			orgDepPhonesListGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord orgDepPhoneListGridRecord = orgDepPhonesListGrid
									.getSelectedRecord();
							if (orgDepPhoneListGridRecord == null) {
								SC.say(CallCenterBK.constants.warning(),
										CallCenterBK.constants
												.pleaseSelrecord());
								return;
							}
							ListGridRecord depListGridRecord = orgDepListGrid
									.getSelectedRecord();
							DlgAddEditOrgDepPhone addEditOrgDepPhone = new DlgAddEditOrgDepPhone(
									orgDepPhoneListGridRecord,
									orgDepPhonesListGrid, depListGridRecord);
							addEditOrgDepPhone.show();
						}
					});

			openOrgBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Integer organization_id = listGridRecord
							.getAttributeAsInt("organization_id");
					openOrganization(organization_id);
				}
			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void openOrganization(Integer organization_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			DataSource dataSource = DataSource.get("OrgDS");

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "customOrgSearchForCallCenterNew");

			Criteria criteria = new Criteria();
			criteria.setAttribute("organization_id", organization_id);

			dataSource.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					if (records == null || records.length != 1) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.orgNotFound());
						return;
					} else {
						DlgAddEditOrganization dlgAddEditMainOrg = new DlgAddEditOrganization(
								null, records[0], orgTreeGrid);
						dlgAddEditMainOrg.show();
					}
				}
			}, req);

			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void deleteOrgDepPhone(ListGridRecord listGridRecord) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("org_dep_to_ph_id",
					listGridRecord.getAttributeAsInt("org_dep_to_ph_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeOrgDepPhone");
			orgDepPhonesListGrid.removeData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sort1() {
		try {
			ListGridRecord listGridRecord = orgDepListGrid.getSelectedRecord();
			if (listGridRecord == null) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.pleaseSelrecord());
				return;
			}

			ListGridRecord phoneData[] = orgDepPhonesListGrid.getRecords();
			if (phoneData == null || phoneData.length <= 0) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.phoneDataIsEmpty());
				return;
			}

			DlgSortOrderOrgDepPhones dlgSortOrderOrgDeps = new DlgSortOrderOrgDepPhones(
					orgDepPhonesListGrid, phoneData);
			dlgSortOrderOrgDeps.show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sort() {
		try {
			Integer organization_id = orgListGridRecord
					.getAttributeAsInt("organization_id");
			ListGridRecord listGridRecord = orgDepListGrid.getSelectedRecord();
			Integer org_department_id = null;
			if (listGridRecord != null) {
				org_department_id = listGridRecord
						.getAttributeAsInt("org_department_id");
			}
			DlgSortOrderOrgDeps dlgSortOrderOrgDeps = new DlgSortOrderOrgDeps(
					organization_id, org_department_id,
					DlgManageOrgDepartments.this);
			dlgSortOrderOrgDeps.show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public void searchDepartment() {
		try {
			Integer organization_id = orgListGridRecord
					.getAttributeAsInt("organization_id");
			String departament = orgDepNameItem.getValueAsString();
			String real_address_descr = orgDepAddrItem.getValueAsString();
			String phone = orgDepPhoneItem.getValueAsString();

			Criteria criteria = new Criteria();
			criteria.setAttribute("organization_id", organization_id);

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
			criteria.setAttribute("department_real_address", real_address_descr);

			if (phone != null && !phone.trim().equals("")) {
				criteria.setAttribute("phone", phone);
			}

			Boolean for_contact = orgDepPhoneContItem.getValueAsBoolean();
			if (for_contact != null && for_contact.booleanValue()) {
				criteria.setAttribute("for_contact", new Integer(1));
			}

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

	private void removeDepartment(Integer org_department_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("org_department_id", org_department_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeOrganizationDepartment");
			orgDepListGrid.removeData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					searchDepartment();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
