package com.info08.billing.callcenter.client.content;

import java.util.TreeMap;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.dialogs.org.DlgAddEditMainOrg;
import com.info08.billing.callcenter.client.dialogs.org.DlgOrgStatusChange;
import com.info08.billing.callcenter.client.dialogs.org.DlgSortOrderOrgs;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.FolderDropEvent;
import com.smartgwt.client.widgets.tree.events.FolderDropHandler;

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
	private ToolStripButton addBtn;
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
		setTitle(CallCenter.constants.manageOrgs());
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
		orgNameGeoItem.setTitle(CallCenter.constants.orgName());
		orgNameGeoItem.setWidth(245);
		orgNameGeoItem.setName("org_name_geo");
		orgNameGeoItem.setValue("");

		orgCommentItem = new TextItem();
		orgCommentItem.setTitle(CallCenter.constants.comment());
		orgCommentItem.setWidth(245);
		orgCommentItem.setName("org_comment");

		orgDirectorItem = new TextItem();
		orgDirectorItem.setTitle(CallCenter.constants.director());
		orgDirectorItem.setWidth(245);
		orgDirectorItem.setName("org_director");

		orgLegalAddressItem = new TextItem();
		orgLegalAddressItem.setTitle(CallCenter.constants.legalAddress());
		orgLegalAddressItem.setWidth(245);
		orgLegalAddressItem.setName("org_legal_address");

		orgIdentCodeItem = new TextItem();
		orgIdentCodeItem.setTitle(CallCenter.constants.identCodeAndNew());
		orgIdentCodeItem.setWidth(245);
		orgIdentCodeItem.setName("org_ident_code");

		orgWebAddressItem = new TextItem();
		orgWebAddressItem.setTitle(CallCenter.constants.webaddress());
		orgWebAddressItem.setWidth(245);
		orgWebAddressItem.setName("org_web_address");

		orgEmailItem = new TextItem();
		orgEmailItem.setTitle(CallCenter.constants.eMail());
		orgEmailItem.setWidth(245);
		orgEmailItem.setName("org_email");

		orgWorkingHoursItem = new TextItem();
		orgWorkingHoursItem.setTitle(CallCenter.constants.workinghours());
		orgWorkingHoursItem.setWidth(245);
		orgWorkingHoursItem.setName("org_working_hours");

		orgDayOffsItem = new TextItem();
		orgDayOffsItem.setTitle(CallCenter.constants.dayOffs());
		orgDayOffsItem.setWidth(245);
		orgDayOffsItem.setName("org_day_offs");

		orgDepartmentItem = new TextItem();
		orgDepartmentItem.setTitle(CallCenter.constants.department());
		orgDepartmentItem.setWidth(245);
		orgDepartmentItem.setName("org_department");

		orgFoundedStartItem = new DateItem();
		orgFoundedStartItem.setTitle(CallCenter.constants.orgFoundDateStart());
		orgFoundedStartItem.setWidth(245);
		orgFoundedStartItem.setName("org_dound_date_start");
		// orgFoundedStartItem.setHint(CallCenter.constants.choose());
		orgFoundedStartItem.setUseTextField(true);

		orgFoundedEndItem = new DateItem();
		orgFoundedEndItem.setTitle(CallCenter.constants.orgFoundDateEnd());
		orgFoundedEndItem.setWidth(245);
		orgFoundedEndItem.setName("org_dound_date_end");
		// orgFoundedEndItem.setHint(CallCenter.constants.choose());
		orgFoundedEndItem.setUseTextField(true);

		streetItem = new TextItem();
		streetItem.setTitle(CallCenter.constants.street());
		streetItem.setName("street_name_geo");
		streetItem.setWidth(245);

		adressItem = new TextItem();
		adressItem.setTitle(CallCenter.constants.number());
		adressItem.setName("adressItem");
		adressItem.setWidth(245);

		citiesItem = new ComboBoxItem();
		citiesItem.setTitle(CallCenter.constants.city());
		citiesItem.setName("city_name_geo");
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
		regionItem.setTitle(CallCenter.constants.cityRegion());
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
		clearButton.setTitle(CallCenter.constants.clear());

		findButton = new IButton();
		findButton.setTitle(CallCenter.constants.find());

		buttonLayout.setMembers(findButton, clearButton);
		mainLayout.addMember(buttonLayout);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		addBtn = new ToolStripButton(CallCenter.constants.addChildOrg(),
				"addIcon.png");
		addBtn.setLayoutAlign(Alignment.LEFT);
		addBtn.setWidth(50);
		toolStrip.addButton(addBtn);

		addNewOrgBtn = new ToolStripButton(CallCenter.constants.addNewOrg(),
				"addIcon.png");
		addNewOrgBtn.setLayoutAlign(Alignment.LEFT);
		addNewOrgBtn.setWidth(50);
		toolStrip.addButton(addNewOrgBtn);

		editBtn = new ToolStripButton(CallCenter.constants.modify(),
				"editIcon.png");
		editBtn.setLayoutAlign(Alignment.LEFT);
		editBtn.setWidth(50);
		toolStrip.addButton(editBtn);

		disableBtn = new ToolStripButton(CallCenter.constants.disable(),
				"deleteIcon.png");
		disableBtn.setLayoutAlign(Alignment.LEFT);
		disableBtn.setWidth(50);
		toolStrip.addButton(disableBtn);

		activateBtn = new ToolStripButton(CallCenter.constants.enable(),
				"restoreIcon.gif");
		activateBtn.setLayoutAlign(Alignment.LEFT);
		activateBtn.setWidth(50);
		toolStrip.addButton(activateBtn);

		toolStrip.addSeparator();

		statusBtn = new ToolStripButton(CallCenter.constants.state(),
				"gnome_status.png");
		statusBtn.setLayoutAlign(Alignment.LEFT);
		statusBtn.setWidth(50);
		toolStrip.addButton(statusBtn);

		sortBtn = new ToolStripButton(CallCenter.constants.sort(), "sort.png");
		sortBtn.setLayoutAlign(Alignment.LEFT);
		sortBtn.setWidth(50);
		toolStrip.addButton(sortBtn);

		supperOrgBtn = new ToolStripButton(CallCenter.constants.supperOrg(),
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
				Integer statuse = countryRecord.getAttributeAsInt("statuse");
				Integer extra_priority = countryRecord
						.getAttributeAsInt("extra_priority");
				Integer note_crit = countryRecord
						.getAttributeAsInt("note_crit");

				if (extra_priority != null && extra_priority < 0) {
					return "color:red;";
				} else if (statuse != null && statuse.equals(2)) {
					if (note_crit != null && note_crit.intValue() == -1
							&& colNum == 2) {
						return "color:red;";
					} else {
						return "color:gray;";
					}
				} else if (statuse != null && statuse.equals(1)) {
					if (note_crit != null && note_crit.intValue() == -1
							&& colNum == 2) {
						return "color:red;";
					} else {
						return "color:blue;";
					}

				} else if (statuse != null && statuse.equals(3)) {
					if (note_crit != null && note_crit.intValue() == -1
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
		orgTreeGrid.setUpdateOperation("updateMainServiceOrg");
		orgTreeGrid.setDataSource(orgDS);
		orgTreeGrid.setAutoFetchData(false);
		orgTreeGrid.setCanReorderRecords(true);
		orgTreeGrid.setCanAcceptDroppedRecords(true);
		orgTreeGrid.setCantDragIntoChildMessage(CallCenter.constants
				.cantDropItself());
		orgTreeGrid.setAutoSaveEdits(false);
		orgTreeGrid.setParentAlreadyContainsChildMessage(CallCenter.constants
				.parrentAlrContChild());
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

		TreeGridField org_name = new TreeGridField("org_name",
				CallCenter.constants.orgName());
		org_name.setTreeField(true);

		ListGridField real_address = new ListGridField(
				"full_address_not_hidden", CallCenter.constants.realAddress(),
				400);
		orgTreeGrid.setFields(org_name, real_address, tree_org_parrent,
				tree_org_child);

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

		orgTreeGrid.addFolderDropHandler(new FolderDropHandler() {
			@Override
			public void onFolderDrop(final FolderDropEvent event) {
				try {
					final TreeNode draged[] = event.getNodes();
					final TreeNode destFolder = event.getFolder();
					event.cancel();
					SC.ask(CallCenter.constants.warning(),
							CallCenter.constants.askForNodeChange(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										dragEvent(draged, destFolder);
									}
								}
							});
				} catch (Exception e) {
					SC.say(e.toString());
				}
			}
		});

		sortBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord records[] = orgTreeGrid.getSelectedRecords();
				if (records == null || records.length <= 0) {
					SC.say(CallCenter.constants.pleaseSelrecord());
					return;
				}
				if (records.length > 1) {
					SC.say(CallCenter.constants.pleaseSelOnerecord());
					return;
				}
				Integer main_id = null;
				try {
					main_id = records[0].getAttributeAsInt("main_id");
				} catch (Exception e) {
					SC.say(CallCenter.constants.invalidTreeRecord());
					return;
				}
				if (main_id == null) {
					SC.say(CallCenter.constants.invalidTreeRecord());
					return;
				}
				try {
					Criteria criteria = new Criteria();
					criteria.setAttribute("main_id", main_id);
					DSRequest requestProperties = new DSRequest();
					requestProperties.setOperationId("customOrgSearchByMainId");
					orgDS.fetchData(criteria, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							Record records[] = response.getData();
							if (records == null || records.length <= 0) {
								SC.say(CallCenter.constants.childListIsEmpty());
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
					SC.say(CallCenter.constants.pleaseSelrecord());
					return;
				}
				Integer deleted = listGridRecord.getAttributeAsInt("deleted");
				if (!deleted.equals(0)) {
					SC.say(CallCenter.constants.recordAlrDisabled());
					return;
				}
				SC.ask(CallCenter.constants.askForDisable(),
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
					SC.say(CallCenter.constants.pleaseSelrecord());
					return;
				}
				Integer deleted = listGridRecord.getAttributeAsInt("deleted");
				if (deleted.equals(0)) {
					SC.say(CallCenter.constants.recordAlrEnabled());
					return;
				}
				SC.ask(CallCenter.constants.askForEnable(),
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
					SC.say(CallCenter.constants.pleaseSelrecord());
					return;
				}
				Integer deleted = listGridRecord.getAttributeAsInt("deleted");
				if (deleted.equals(1)) {
					SC.say(CallCenter.constants.plsRestRecBefStatChange());
					return;
				}
				DlgOrgStatusChange dlgOrgStatusChange = new DlgOrgStatusChange(
						listGridRecord, orgTreeGrid);
				dlgOrgStatusChange.show();
			}
		});

		addBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final ListGridRecord listGridRecord = orgTreeGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say(CallCenter.constants.warning(),
							CallCenter.constants.pleaseSelrecord());
					return;
				}

				DlgAddEditMainOrg dlgAddEditMainOrg = new DlgAddEditMainOrg(
						true, listGridRecord, orgTreeGrid);
				dlgAddEditMainOrg.show();
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
					SC.say(CallCenter.constants.warning(),
							CallCenter.constants.pleaseSelrecord());
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
					SC.say(CallCenter.constants.warning(),
							CallCenter.constants.pleaseSelrecord());
					return;
				}
				Integer main_id = listGridRecord.getAttributeAsInt("main_id");

				if (main_id == null) {
					SC.say(CallCenter.constants.warning(),
							CallCenter.constants.invalidRecord());
					return;
				}
				findBySupperOrg(main_id);
			}
		});
		setPane(mainLayout);
		instance = this;
	}

	private void findBySupperOrg(Integer main_id) {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("main_id", main_id);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("findSupperMainOrgId");
			orgDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					if (records == null || records.length <= 0) {
						SC.say(CallCenter.constants.warning(),
								CallCenter.constants.supperOrgNotFound());
					}
					Record record = records[0];
					Integer ret_main_id = record.getAttributeAsInt("main_id");
					fingOrgById(ret_main_id);
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fingOrgById(Integer main_id) {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", new Integer(0));
			criteria.setAttribute("pp_main_id", main_id);
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
					.getSessionPerson().getUserName());
			record.setAttribute("deleted", deleted);
			record.setAttribute("main_id",
					listGridRecord.getAttributeAsInt("main_id"));

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
			criteria.setAttribute("deleted", new Integer(0));
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
					criteria.setAttribute("org_name" + i, item);
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
					criteria.setAttribute("note" + i, item);
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
				SC.say(CallCenter.constants.warning(),
						CallCenter.constants.findOrgEnterAnyParam());
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

	protected void dragEvent(final TreeNode[] draged, final TreeNode destFolder) {
		TreeMap<Integer, TreeNode> treeMap = new TreeMap<Integer, TreeNode>();
		for (TreeNode treeNode : draged) {
			try {
				Integer oldParentId = null;
				try {
					oldParentId = treeNode.getAttributeAsInt("main_master_id");
				} catch (Exception e) {
					oldParentId = 0;
				}
				Integer newParentId = null;
				try {
					newParentId = destFolder.getAttributeAsInt("main_id");
				} catch (Exception e) {
					newParentId = 0;
				}
				if (oldParentId.equals(newParentId)) {
					continue;
				}
				if (treeMap.get(oldParentId) == null) {
					// TreeNode parentNode =
					// orgTreeGrid.getData().find("main_id",oldParentId);
					// if (parentNode != null){
					// / treeMap.put(oldParentId, parentNode);
					// }
				}

				treeNode.setAttribute("main_master_id", newParentId);
				orgTreeGrid.updateData(treeNode);
			} catch (Exception e) {
				SC.say(e.toString());
			}
		}
	}
}
