package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.org.DlgAddEditMainOrg;
import com.info08.billing.callcenterbk.client.dialogs.org.DlgOrgStatusChange;
import com.info08.billing.callcenterbk.client.dialogs.org.DlgSortOrderOrgs;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class TabOrganization extends Tab {

	private DynamicForm searchForm;

	// form fields
	private TextItem orgNameGeoItem;
	// private TextItem orgNameEngItem;
	private TextItem orgCommentItem;
	private TextItem orgDirectorItem;
	private TextItem orgLegalAddressItem;
	private TextItem orgIdentCodeItem;
	// private TextItem orgIdentCodeNewItem;
	private TextItem orgWebAddressItem;
	private TextItem orgEmailItem;
	private TextItem orgWorkingHoursItem;
	private TextItem orgDayOffsItem;
	private TextItem orgDepartmentItem;
	private DateItem orgFoundedStartItem;
	private DateItem orgFoundedEndItem;

	// address fields.
	private ComboBoxItem citiesItem;
	private TextItem streetItem;
	private ComboBoxItem regionItem;
	private TextItem adressItem;
	// private TextItem blockItem;
	// private TextItem appartItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton sortBtn;
	private ToolStripButton addNewOrgBtn;
	private ToolStripButton editBtn;
	private ToolStripButton disableBtn;
	private ToolStripButton activateBtn;
	private ToolStripButton statusBtn;
	private ToolStripButton supperOrgBtn;

	private TreeGrid orgTreeGrid;
	private VLayout mainLayout;

	private DataSource orgDS;

	private TabOrganization instance;

	public TabOrganization() {
		setTitle(CallCenterBK.constants.manageOrgs());
		setCanClose(true);

		orgDS = DataSource.get("OrgDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(true);
		searchForm.setWidth(980);
		searchForm.setNumCols(4);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(searchForm);

		orgNameGeoItem = new TextItem();
		orgNameGeoItem.setTitle(CallCenterBK.constants.orgName());
		orgNameGeoItem.setWidth(245);
		orgNameGeoItem.setName("orgNameGeoItem");
		orgNameGeoItem.setValue("");

		orgCommentItem = new TextItem();
		orgCommentItem.setTitle(CallCenterBK.constants.comment());
		orgCommentItem.setWidth(245);
		orgCommentItem.setName("orgCommentItem");

		orgDirectorItem = new TextItem();
		orgDirectorItem.setTitle(CallCenterBK.constants.director());
		orgDirectorItem.setWidth(245);
		orgDirectorItem.setName("orgDirectorItem");

		orgLegalAddressItem = new TextItem();
		orgLegalAddressItem.setTitle(CallCenterBK.constants.legalAddress());
		orgLegalAddressItem.setWidth(245);
		orgLegalAddressItem.setName("orgLegalAddressItem");

		orgIdentCodeItem = new TextItem();
		orgIdentCodeItem.setTitle(CallCenterBK.constants.identCodeAndNew());
		orgIdentCodeItem.setWidth(245);
		orgIdentCodeItem.setName("orgIdentCodeItem");

		orgWebAddressItem = new TextItem();
		orgWebAddressItem.setTitle(CallCenterBK.constants.webaddress());
		orgWebAddressItem.setWidth(245);
		orgWebAddressItem.setName("orgWebAddressItem");

		orgEmailItem = new TextItem();
		orgEmailItem.setTitle(CallCenterBK.constants.eMail());
		orgEmailItem.setWidth(245);
		orgEmailItem.setName("orgEmailItem");

		orgWorkingHoursItem = new TextItem();
		orgWorkingHoursItem.setTitle(CallCenterBK.constants.workinghours());
		orgWorkingHoursItem.setWidth(245);
		orgWorkingHoursItem.setName("orgWorkingHoursItem");

		orgDayOffsItem = new TextItem();
		orgDayOffsItem.setTitle(CallCenterBK.constants.dayOffs());
		orgDayOffsItem.setWidth(245);
		orgDayOffsItem.setName("orgDayOffsItem");

		orgDepartmentItem = new TextItem();
		orgDepartmentItem.setTitle(CallCenterBK.constants.department());
		orgDepartmentItem.setWidth(245);
		orgDepartmentItem.setName("orgDepartmentItem");

		orgFoundedStartItem = new DateItem();
		orgFoundedStartItem
				.setTitle(CallCenterBK.constants.orgFoundDateStart());
		orgFoundedStartItem.setWidth(245);
		orgFoundedStartItem.setName("orgFoundedStartItem");
		orgFoundedStartItem.setUseTextField(true);

		orgFoundedEndItem = new DateItem();
		orgFoundedEndItem.setTitle(CallCenterBK.constants.orgFoundDateEnd());
		orgFoundedEndItem.setWidth(245);
		orgFoundedEndItem.setName("orgFoundedEndItem");
		orgFoundedEndItem.setUseTextField(true);

		streetItem = new TextItem();
		streetItem.setTitle(CallCenterBK.constants.street());
		streetItem.setName("streetItem");
		streetItem.setWidth(245);

		adressItem = new TextItem();
		adressItem.setTitle(CallCenterBK.constants.number());
		adressItem.setName("adressItem");
		adressItem.setWidth(245);

		citiesItem = new ComboBoxItem();
		citiesItem.setTitle(CallCenterBK.constants.city());
		citiesItem.setName("citiesItem");
		citiesItem.setWidth(245);
		citiesItem.setFetchMissingValues(true);
		citiesItem.setFilterLocally(false);
		citiesItem.setAddUnknownValues(false);

		DataSource cityDS = DataSource.get("CityDS");
		citiesItem.setOptionOperationId("searchCitiesFromDBForCombos");
		citiesItem.setOptionDataSource(cityDS);
		citiesItem.setValueField("city_id");
		citiesItem.setDisplayField("city_name_geo");

		citiesItem.setOptionCriteria(new Criteria());
		citiesItem.setAutoFetchData(false);

		citiesItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = citiesItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("city_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("city_id", nullO);
					}
				}
			}
		});

		regionItem = new ComboBoxItem();
		regionItem.setTitle(CallCenterBK.constants.cityRegion());
		regionItem.setName("city_region_name_geo");
		regionItem.setWidth(245);
		regionItem.setFetchMissingValues(true);
		regionItem.setFilterLocally(false);
		regionItem.setAddUnknownValues(false);

		DataSource streetsDS = DataSource.get("CityRegionDS");
		regionItem.setOptionOperationId("searchCityRegsFromDBForCombos");
		regionItem.setOptionDataSource(streetsDS);
		regionItem.setValueField("city_region_id");
		regionItem.setDisplayField("city_region_name_geo");

		Criteria criteria = new Criterion();
		regionItem.setOptionCriteria(criteria);
		regionItem.setAutoFetchData(false);

		regionItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = regionItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("city_region_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("city_region_id", nullO);
					}
				}
			}
		});

		searchForm.setFields(orgNameGeoItem, orgCommentItem, orgDepartmentItem,
				orgDirectorItem, streetItem, adressItem, regionItem,
				citiesItem, orgWorkingHoursItem, orgDayOffsItem,
				orgIdentCodeItem, orgLegalAddressItem, orgWebAddressItem,
				orgEmailItem, orgFoundedStartItem, orgFoundedEndItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(980);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		clearButton = new IButton();
		clearButton.setTitle(CallCenterBK.constants.clear());

		findButton = new IButton();
		findButton.setTitle(CallCenterBK.constants.find());

		buttonLayout.setMembers(findButton, clearButton);
		mainLayout.addMember(buttonLayout);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		addNewOrgBtn = new ToolStripButton(CallCenterBK.constants.addNewOrg(),
				"addIcon.png");
		addNewOrgBtn.setLayoutAlign(Alignment.LEFT);
		addNewOrgBtn.setWidth(50);
		toolStrip.addButton(addNewOrgBtn);

		editBtn = new ToolStripButton(CallCenterBK.constants.modify(),
				"editIcon.png");
		editBtn.setLayoutAlign(Alignment.LEFT);
		editBtn.setWidth(50);
		toolStrip.addButton(editBtn);

		disableBtn = new ToolStripButton(CallCenterBK.constants.disable(),
				"deleteIcon.png");
		disableBtn.setLayoutAlign(Alignment.LEFT);
		disableBtn.setWidth(50);
		toolStrip.addButton(disableBtn);

		activateBtn = new ToolStripButton(CallCenterBK.constants.enable(),
				"restoreIcon.gif");
		activateBtn.setLayoutAlign(Alignment.LEFT);
		activateBtn.setWidth(50);
		toolStrip.addButton(activateBtn);

		toolStrip.addSeparator();

		statusBtn = new ToolStripButton(CallCenterBK.constants.state(),
				"gnome_status.png");
		statusBtn.setLayoutAlign(Alignment.LEFT);
		statusBtn.setWidth(50);
		toolStrip.addButton(statusBtn);

		sortBtn = new ToolStripButton(CallCenterBK.constants.sort(), "sort.png");
		sortBtn.setLayoutAlign(Alignment.LEFT);
		sortBtn.setWidth(50);
		toolStrip.addButton(sortBtn);

		supperOrgBtn = new ToolStripButton(CallCenterBK.constants.supperOrg(),
				"folder_classic_up.png");
		supperOrgBtn.setLayoutAlign(Alignment.LEFT);
		supperOrgBtn.setWidth(50);
		toolStrip.addButton(supperOrgBtn);

		orgTreeGrid = new TreeGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord countryRecord = (ListGridRecord) record;
				if (countryRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				Integer status = countryRecord.getAttributeAsInt("status");
				Integer super_priority = countryRecord
						.getAttributeAsInt("super_priority");
				Integer important = countryRecord
						.getAttributeAsInt("important");

				if (super_priority != null && super_priority < 0) {
					return "color:red;";
				} else if (status != null && status.equals(2)) {
					if (important != null && important.intValue() == -1
							&& colNum == 2) {
						return "color:red;";
					} else {
						return "color:gray;";
					}
				} else if (status != null && status.equals(1)) {
					if (important != null && important.intValue() == -1
							&& colNum == 2) {
						return "color:red;";
					} else {
						return "color:blue;";
					}

				} else if (status != null && status.equals(3)) {
					if (important != null && important.intValue() == -1
							&& colNum == 2) {
						return "color:red;";
					} else {
						return "color:green;";
					}
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};
		};
		orgTreeGrid.setLeft(50);
		orgTreeGrid.setTop(50);
		orgTreeGrid.setWidth100();
		orgTreeGrid.setHeight100();
		orgTreeGrid.setShowConnectors(true);
		orgTreeGrid.setFetchOperation("customOrgSearchForCallCenterNew");
		orgTreeGrid.setDataSource(orgDS);
		orgTreeGrid.setAutoFetchData(false);
		orgTreeGrid.setAutoSaveEdits(false);
		orgTreeGrid.setCanSelectAll(false);
		orgTreeGrid.setWrapCells(true);
		orgTreeGrid.setFixedRecordHeights(false);
		orgTreeGrid.setCanSort(false);

		mainLayout.addMember(orgTreeGrid);
		ListGridField tree_org_parrent = new ListGridField("tree_org_parrent",
				" ", 30);
		tree_org_parrent.setAlign(Alignment.CENTER);
		tree_org_parrent.setType(ListGridFieldType.IMAGE);
		tree_org_parrent.setImageURLPrefix("orgtypes/");
		tree_org_parrent.setImageURLSuffix(".png");
		tree_org_parrent.setCanSort(false);
		tree_org_parrent.setCanFilter(false);

		ListGridField tree_org_child = new ListGridField("tree_org_child", " ",
				30);
		tree_org_child.setAlign(Alignment.CENTER);
		tree_org_child.setType(ListGridFieldType.IMAGE);
		tree_org_child.setImageURLPrefix("orgtypes/");
		tree_org_child.setImageURLSuffix(".png");
		tree_org_child.setCanSort(false);
		tree_org_child.setCanFilter(false);

		TreeGridField organization_name = new TreeGridField(
				"organization_name", CallCenterBK.constants.orgName());
		organization_name.setTreeField(true);

		ListGridField real_address = new ListGridField(
				"full_address_not_hidden",
				CallCenterBK.constants.realAddress(), 400);
		orgTreeGrid.setFields(organization_name, real_address,
				tree_org_parrent, tree_org_child);

		findButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search();
			}
		});

		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				orgNameGeoItem.clearValue();
				orgCommentItem.clearValue();
				orgDepartmentItem.clearValue();
				orgDirectorItem.clearValue();
				streetItem.clearValue();
				adressItem.clearValue();
				regionItem.clearValue();
				citiesItem.clearValue();
				orgWorkingHoursItem.clearValue();
				orgDayOffsItem.clearValue();
				orgIdentCodeItem.clearValue();
				orgLegalAddressItem.clearValue();
				orgWebAddressItem.clearValue();
				orgEmailItem.clearValue();
				orgFoundedEndItem.clearValue();
				orgFoundedStartItem.clearValue();

				searchForm.focusInItem(orgNameGeoItem);
			}
		});

		sortBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord records[] = orgTreeGrid.getSelectedRecords();
				if (records == null || records.length <= 0) {
					SC.say(CallCenterBK.constants.pleaseSelrecord());
					return;
				}
				if (records.length > 1) {
					SC.say(CallCenterBK.constants.pleaseSelOnerecord());
					return;
				}
				Integer organization_id = null;
				try {
					organization_id = records[0]
							.getAttributeAsInt("organization_id");
				} catch (Exception e) {
					SC.say(CallCenterBK.constants.invalidTreeRecord());
					return;
				}
				if (organization_id == null) {
					SC.say(CallCenterBK.constants.invalidTreeRecord());
					return;
				}
				try {
					Criteria criteria = new Criteria();
					criteria.setAttribute("organization_id", organization_id);
					DSRequest requestProperties = new DSRequest();
					requestProperties.setOperationId("customOrgSearchByMainId");
					orgDS.fetchData(criteria, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							Record records[] = response.getData();
							if (records == null || records.length <= 0) {
								SC.say(CallCenterBK.constants
										.childListIsEmpty());
								return;
							} else {
								DlgSortOrderOrgs dlgSortOrderOrgs = new DlgSortOrderOrgs(
										instance, records);
								dlgSortOrderOrgs.show();
							}
						}
					}, requestProperties);
				} catch (Exception e) {
					SC.say(e.toString());
				}
			}
		});

		disableBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final ListGridRecord listGridRecord = orgTreeGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say(CallCenterBK.constants.pleaseSelrecord());
					return;
				}
				Integer deleted = listGridRecord.getAttributeAsInt("deleted");
				if (!deleted.equals(0)) {
					SC.say(CallCenterBK.constants.recordAlrDisabled());
					return;
				}
				SC.ask(CallCenterBK.constants.askForDisable(),
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value) {
									changeDelStatus(listGridRecord, 1);
								}
							}
						});
			}
		});
		activateBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final ListGridRecord listGridRecord = orgTreeGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say(CallCenterBK.constants.pleaseSelrecord());
					return;
				}
				Integer deleted = listGridRecord.getAttributeAsInt("deleted");
				if (deleted.equals(0)) {
					SC.say(CallCenterBK.constants.recordAlrEnabled());
					return;
				}
				SC.ask(CallCenterBK.constants.askForEnable(),
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value) {
									changeDelStatus(listGridRecord, 0);
								}
							}
						});
			}
		});

		statusBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final ListGridRecord listGridRecord = orgTreeGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say(CallCenterBK.constants.pleaseSelrecord());
					return;
				}
				Integer deleted = listGridRecord.getAttributeAsInt("deleted");
				if (deleted.equals(1)) {
					SC.say(CallCenterBK.constants.plsRestRecBefStatChange());
					return;
				}
				DlgOrgStatusChange dlgOrgStatusChange = new DlgOrgStatusChange(
						listGridRecord, orgTreeGrid);
				dlgOrgStatusChange.show();
			}
		});

		addNewOrgBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DlgAddEditMainOrg dlgAddEditMainOrg = new DlgAddEditMainOrg(
						true, null, orgTreeGrid);
				dlgAddEditMainOrg.show();
			}
		});

		editBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord listGridRecord = orgTreeGrid.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.pleaseSelrecord());
					return;
				}
				DlgAddEditMainOrg dlgAddEditMainOrg = new DlgAddEditMainOrg(
						false, listGridRecord, orgTreeGrid);
				dlgAddEditMainOrg.show();
			}
		});

		orgNameGeoItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		orgCommentItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		orgDepartmentItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		orgDirectorItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		adressItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		orgIdentCodeItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		orgLegalAddressItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		orgWebAddressItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		orgEmailItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		orgDayOffsItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		orgWorkingHoursItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		streetItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});

		supperOrgBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord listGridRecord = orgTreeGrid.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.pleaseSelrecord());
					return;
				}
				Integer organization_id = listGridRecord
						.getAttributeAsInt("organization_id");

				if (organization_id == null) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.invalidRecord());
					return;
				}
				findBySupperOrg(organization_id);
			}
		});
		setPane(mainLayout);
		instance = this;
	}

	private void findBySupperOrg(Integer organization_id) {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("organization_id", organization_id);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("findSupperMainOrgId");
			orgDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					if (records == null || records.length <= 0) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.supperOrgNotFound());
					}
					Record record = records[0];
					Integer ret_organization_id = record
							.getAttributeAsInt("organization_id");
					fingOrgById(ret_organization_id);
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fingOrgById(Integer organization_id) {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", new Integer(0));
			criteria.setAttribute("pp_organization_id", organization_id);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"customOrgSearchForCorrectByMainId");
			orgTreeGrid.invalidateCache();
			orgTreeGrid.fetchData(criteria, new DSCallback() {
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

	private void changeDelStatus(ListGridRecord listGridRecord, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("deleted", deleted);
			record.setAttribute("organization_id",
					listGridRecord.getAttributeAsInt("organization_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateMainOrgDeleteOrRestore");
			orgTreeGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	public void search() {
		try {
			Criteria criteria = new Criteria();
			String org_name = orgNameGeoItem.getValueAsString();
			if (org_name != null && !org_name.trim().equals("")) {
				String tmp = org_name.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("organization_name" + i, item);
					i++;
				}
			}

			String note = orgCommentItem.getValueAsString();
			if (note != null && !note.trim().equals("")) {
				String tmp = note.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("remark" + i, item);
					i++;
				}
			}
			String director = orgDirectorItem.getValueAsString();
			if (director != null && !director.trim().equals("")) {
				String tmp = director.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("director" + i, item);
					i++;
				}
			}
			String street = streetItem.getValueAsString();
			if (street != null && !street.trim().equalsIgnoreCase("")) {
				String tmp = street.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("real_address" + i, item);
					i++;
				}
			}
			String address_suffix_geo = adressItem.getValueAsString();
			if (address_suffix_geo != null
					&& !address_suffix_geo.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("address_suffix_geo", address_suffix_geo);
			}
			String city = citiesItem.getValueAsString();
			if (city != null && !city.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("city_id", new Integer(city));
			}
			String workinghours = orgWorkingHoursItem.getValueAsString();
			if (workinghours != null
					&& !workinghours.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("workinghours", workinghours);
			}
			String dayoffs = orgDayOffsItem.getValueAsString();
			if (dayoffs != null && !dayoffs.trim().equals("")) {
				String tmp = dayoffs.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("dayoffs" + i, item);
					i++;
				}
			}
			String ident_code = orgIdentCodeItem.getValueAsString();
			if (ident_code != null && !ident_code.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("ident_code", ident_code);
			}

			String legaladdress = orgLegalAddressItem.getValueAsString();
			if (legaladdress != null
					&& !legaladdress.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("legaladdress", legaladdress);
			}

			String webaddress = orgWebAddressItem.getValueAsString();
			if (webaddress != null && !webaddress.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("webaddress", webaddress);
			}

			String mail = orgEmailItem.getValueAsString();
			if (mail != null && !mail.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("mail", mail);
			}

			String city_region_id = regionItem.getValueAsString();
			if (city_region_id != null && !city_region_id.trim().equals("")) {
				criteria.setAttribute("city_region_id", new Integer(
						city_region_id));
			}

			if ((org_name == null || org_name.trim().equals(""))
					&& (note == null || note.trim().equals(""))
					&& (director == null || director.trim().equals(""))
					&& (workinghours == null || workinghours.trim().equals(""))
					&& (dayoffs == null || dayoffs.trim().equals(""))
					&& (ident_code == null || ident_code.trim().equals(""))
					&& (legaladdress == null || legaladdress.trim().equals(""))
					&& (webaddress == null || webaddress.trim().equals(""))
					&& (mail == null || mail.trim().equals(""))
					&& (street == null || street.trim().equals(""))) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.findOrgEnterAnyParam());
				return;
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"customOrgSearchForCallCenterNew");
			orgTreeGrid.invalidateCache();
			orgTreeGrid.fetchData(criteria, new DSCallback() {
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
