package com.info08.billing.callcenterbk.client.content;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyComboboxItemMultClass;
import com.info08.billing.callcenterbk.client.common.components.MyComboboxItemMultiple;
import com.info08.billing.callcenterbk.client.dialogs.history.DlgHistOrganization;
import com.info08.billing.callcenterbk.client.dialogs.org.DlgAddEditOrganization;
import com.info08.billing.callcenterbk.client.dialogs.org.DlgManageOrgDepartments;
import com.info08.billing.callcenterbk.client.dialogs.org.DlgSortOrderOrgs;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class TabOrganization extends Tab {

	private DynamicForm searchForm;

	// form fields
	private TextItem orgNameGeoItem;
	private TextItem orgCommentItem;
	private TextItem orgDirectorItem;
	private TextItem orgDepartmentItem;
	private TextItem orgIdentCodeItem;
	private TextItem orgWebAddressItem;
	private TextItem orgEmailItem;
	private TextItem orgWorkingHoursItem;
	private TextItem phoneItem;
	private DateItem orgFoundedStartItem;
	private DateItem orgFoundedEndItem;
	private TextItem phoneUpdDateItem;
	private TextItem orgSocialAddressItem;
	private ComboBoxItem townItem;
	private TextItem streetItem;
	private SelectItem regionItem;
	private ComboBoxItem legalTownItem;
	private TextItem legalStreetItem;
	private SelectItem legalRegionItem;
	private SelectItem partnerBankItem;
	private MyComboboxItemMultiple orgActivity;
	private SelectItem weekDaysItem;
	private TextItem orgContactPersonItem;
	private SelectItem orgStatusItem;
	private CheckboxItem mainOrganizationItem;

	// actions
	private IButton findButton;
	private IButton clearButton;

	private ToolStripButton sortBtn;
	private ToolStripButton addNewOrgBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ToolStripButton supperOrgBtn;
	private ToolStripButton parrentOrgBtn;
	private ToolStripButton currentOrgBtn;
	private ToolStripButton orgDepartmentsBtn;
	private ToolStripButton historyBtn;
	private ToolStripButton exportBtn;
	private ToolStripButton exportCountBtn;

	private ListGrid organizationsGrid;
	private DetailViewer detailViewer;
	private VLayout mainLayout;

	private DataSource orgDS;
	private TabOrganization instance;
	private Criteria advCriteria;

	public TabOrganization() {
		try {

			setTitle(CallCenterBK.constants.manageOrgs());
			setCanClose(true);

			orgDS = DataSource.get("OrgDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(980);
			searchForm.setNumCols(5);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			mainLayout.addMember(searchForm);

			orgNameGeoItem = new TextItem();
			orgNameGeoItem.setTitle(CallCenterBK.constants.orgName());
			orgNameGeoItem.setWidth(245);
			orgNameGeoItem.setName("orgNameGeoItem");

			orgCommentItem = new TextItem();
			orgCommentItem.setTitle(CallCenterBK.constants.comment());
			orgCommentItem.setWidth(245);
			orgCommentItem.setName("orgCommentItem");

			orgDirectorItem = new TextItem();
			orgDirectorItem.setTitle(CallCenterBK.constants.director());
			orgDirectorItem.setWidth(245);
			orgDirectorItem.setName("orgDirectorItem");

			orgDepartmentItem = new TextItem();
			orgDepartmentItem.setTitle(CallCenterBK.constants.department());
			orgDepartmentItem.setWidth(245);
			orgDepartmentItem.setName("department");

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

			mainOrganizationItem = new CheckboxItem();
			mainOrganizationItem.setTitle(CallCenterBK.constants
					.mainOrganization());
			mainOrganizationItem.setWidth(245);
			mainOrganizationItem.setName("mainOrganizationItem");
			mainOrganizationItem.setValue(false);

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phoneNumber());
			phoneItem.setWidth(245);
			phoneItem.setName("phone");

			orgFoundedStartItem = new DateItem();
			orgFoundedStartItem.setTitle(CallCenterBK.constants
					.orgFoundDateStart());
			orgFoundedStartItem.setWidth(245);
			orgFoundedStartItem.setName("orgFoundedStartItem");
			orgFoundedStartItem.setUseTextField(true);
			orgFoundedStartItem
					.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
			orgFoundedStartItem.setUseMask(true);

			orgFoundedEndItem = new DateItem();
			orgFoundedEndItem
					.setTitle(CallCenterBK.constants.orgFoundDateEnd());
			orgFoundedEndItem.setWidth(245);
			orgFoundedEndItem.setName("orgFoundedEndItem");
			orgFoundedEndItem.setUseTextField(true);
			orgFoundedEndItem
					.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
			orgFoundedEndItem.setUseMask(true);

			streetItem = new TextItem();
			streetItem.setTitle(CallCenterBK.constants.street());
			streetItem.setName("streetItem");
			streetItem.setWidth(245);

			townItem = new ComboBoxItem();
			townItem.setTitle(CallCenterBK.constants.town());
			townItem.setName("town_id");
			townItem.setWidth(245);
			ClientUtils.fillCombo(townItem, "TownsDS",
					"searchCitiesFromDBForCombos", "town_id", "town_name");
			townItem.setValue(Constants.defCityTbilisiId);

			regionItem = new SelectItem();
			regionItem.setMultiple(true);
			regionItem.setTitle(CallCenterBK.constants.cityRegion());
			regionItem.setName("town_district_id");
			regionItem.setWidth(245);

			townItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					String value = townItem.getValueAsString();
					if (value == null) {
						return;
					}
					regionItem.clearValue();
					Map<String, Integer> aditionalCriteria = new TreeMap<String, Integer>();
					aditionalCriteria.put("town_id", Integer.parseInt(value));
					aditionalCriteria.put("need_indexes", 1);

					ClientUtils.fillCombo(regionItem, "TownDistrictDS",
							"searchCityRegsFromDBForCombos",
							"town_district_id", "town_district_name",
							aditionalCriteria);
				}
			});

			Map<String, Integer> aditionalCriteria = new TreeMap<String, Integer>();
			aditionalCriteria.put("town_id", Constants.defCityTbilisiId);
			aditionalCriteria.put("need_indexes", 1);

			ClientUtils.fillCombo(regionItem, "TownDistrictDS",
					"searchCityRegsFromDBForCombos", "town_district_id",
					"town_district_name", aditionalCriteria);

			legalStreetItem = new TextItem();
			legalStreetItem.setTitle(CallCenterBK.constants.streetLegal());
			legalStreetItem.setName("legalStreetItem");
			legalStreetItem.setWidth(245);

			legalTownItem = new ComboBoxItem();
			legalTownItem.setTitle(CallCenterBK.constants.townLegal());
			legalTownItem.setName("town_id_1");
			legalTownItem.setWidth(245);
			ClientUtils.fillCombo(legalTownItem, "TownsDS",
					"searchCitiesFromDBForCombos", "town_id", "town_name");

			legalRegionItem = new SelectItem();
			legalRegionItem.setMultiple(true);
			legalRegionItem.setTitle(CallCenterBK.constants.cityRegionLegal());
			legalRegionItem.setName("town_district_id_1");
			legalRegionItem.setWidth(245);

			ClientUtils.fillCombo(legalRegionItem, "TownDistrictDS",
					"searchCityRegsFromDBForCombos", "town_district_id",
					"town_district_name", aditionalCriteria);

			legalTownItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					String value = legalTownItem.getValueAsString();
					if (value == null) {
						return;
					}
					legalRegionItem.clearValue();
					Map<String, Integer> aditionalCriteria = new TreeMap<String, Integer>();
					aditionalCriteria.put("town_id", Integer.parseInt(value));
					aditionalCriteria.put("need_indexes", 1);
					ClientUtils.fillCombo(legalRegionItem, "TownDistrictDS",
							"searchCityRegsFromDBForCombos",
							"town_district_id", "town_district_name",
							aditionalCriteria);
				}
			});

			phoneUpdDateItem = new TextItem();
			phoneUpdDateItem.setName("organization_index");
			phoneUpdDateItem.setWidth(245);
			phoneUpdDateItem.setTitle(CallCenterBK.constants.yearMonth());
			phoneUpdDateItem.setMask("####");

			orgSocialAddressItem = new TextItem();
			orgSocialAddressItem.setName("social_address");
			orgSocialAddressItem.setWidth(245);
			orgSocialAddressItem.setTitle(CallCenterBK.constants
					.socialAddress());

			partnerBankItem = new SelectItem();
			partnerBankItem.setMultiple(true);
			partnerBankItem.setTitle(CallCenterBK.constants.partnerBank());
			partnerBankItem.setName("organization_id");
			partnerBankItem.setWidth(245);
			ClientUtils.fillCombo(partnerBankItem, "OrgDS",
					"searchPartnerBanks", "organization_id",
					"organization_name");

			orgActivity = new MyComboboxItemMultiple();
			orgActivity.setTitle(CallCenterBK.constants.activity());
			orgActivity.setWidth(245);
			MyComboboxItemMultClass params = new MyComboboxItemMultClass(
					"OrgActDS", "searchAllBusinesActivitiesForCB",
					"org_activity_id", new String[] { "activity_description" },
					new String[] { CallCenterBK.constants.activity() }, null,
					CallCenterBK.constants.chooseActivity(), 700, 400,
					CallCenterBK.constants.thisRecordAlreadyChoosen());
			orgActivity.setParams(params);

			weekDaysItem = new SelectItem();
			weekDaysItem.setMultiple(true);
			weekDaysItem.setTitle(CallCenterBK.constants.weekDay());
			weekDaysItem.setName("day_id");
			weekDaysItem.setWidth(245);
			weekDaysItem.setValueMap(ClientMapUtil.getInstance().getWeekDays());

			orgContactPersonItem = new TextItem();
			orgContactPersonItem.setName("contact_person");
			orgContactPersonItem.setWidth(245);
			orgContactPersonItem.setTitle(CallCenterBK.constants
					.contactPerson());

			orgStatusItem = new SelectItem();
			orgStatusItem.setMultiple(true);
			orgStatusItem.setTitle(CallCenterBK.constants.status());
			orgStatusItem.setName("status");
			orgStatusItem.setWidth(245);
			orgStatusItem.setValueMap(ClientMapUtil.getInstance()
					.getOrgStatuses());

			searchForm.setFields(orgNameGeoItem, orgCommentItem,
					orgDepartmentItem, phoneItem, orgDirectorItem, streetItem,
					regionItem, townItem, orgIdentCodeItem,
					orgContactPersonItem, legalStreetItem, legalRegionItem,
					legalTownItem, orgWorkingHoursItem, orgSocialAddressItem,
					orgWebAddressItem, orgEmailItem, orgFoundedStartItem,
					orgFoundedEndItem, phoneUpdDateItem, partnerBankItem,
					orgActivity, weekDaysItem, orgStatusItem,
					mainOrganizationItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(1223);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.LEFT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.addMember(findButton);
			buttonLayout.addMember(clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addNewOrgBtn = new ToolStripButton(
					CallCenterBK.constants.addNewOrg(), "addIcon.png");
			addNewOrgBtn.setLayoutAlign(Alignment.LEFT);
			addNewOrgBtn.setWidth(50);
			toolStrip.addButton(addNewOrgBtn);

			editBtn = new ToolStripButton(CallCenterBK.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			sortBtn = new ToolStripButton(CallCenterBK.constants.sort(),
					"sort.png");
			sortBtn.setLayoutAlign(Alignment.LEFT);
			sortBtn.setWidth(50);
			toolStrip.addButton(sortBtn);

			supperOrgBtn = new ToolStripButton(
					CallCenterBK.constants.supperOrg(), "folder_classic_up.png");
			supperOrgBtn.setLayoutAlign(Alignment.LEFT);
			supperOrgBtn.setWidth(50);
			toolStrip.addButton(supperOrgBtn);

			parrentOrgBtn = new ToolStripButton(
					CallCenterBK.constants.parrentOrganization(),
					"folder_classic_up.png");
			parrentOrgBtn.setLayoutAlign(Alignment.LEFT);
			parrentOrgBtn.setWidth(50);
			toolStrip.addButton(parrentOrgBtn);

			currentOrgBtn = new ToolStripButton(
					CallCenterBK.constants.currentOrganization(),
					"folder_classic_up.png");
			currentOrgBtn.setLayoutAlign(Alignment.LEFT);
			currentOrgBtn.setWidth(50);
			toolStrip.addButton(currentOrgBtn);

			toolStrip.addSeparator();

			orgDepartmentsBtn = new ToolStripButton(
					CallCenterBK.constants.departments(),
					"orgtypes/associations.png");
			orgDepartmentsBtn.setLayoutAlign(Alignment.LEFT);
			orgDepartmentsBtn.setWidth(50);
			toolStrip.addButton(orgDepartmentsBtn);

			toolStrip.addSeparator();

			historyBtn = new ToolStripButton(CallCenterBK.constants.history(),
					"date.png");
			historyBtn.setLayoutAlign(Alignment.LEFT);
			historyBtn.setWidth(50);
			toolStrip.addButton(historyBtn);
			boolean hasHistPerm = CommonSingleton.getInstance().hasPermission(
					"106500");
			historyBtn.setDisabled(!hasHistPerm);

			toolStrip.addSeparator();

			exportBtn = new ToolStripButton(CallCenterBK.constants.save(),
					"excel.gif");
			exportBtn.setLayoutAlign(Alignment.LEFT);
			exportBtn.setWidth(50);
			toolStrip.addButton(exportBtn);
			boolean hasExcelExpPerm = CommonSingleton.getInstance()
					.hasPermission("106000");
			exportBtn.setDisabled(!hasExcelExpPerm);

			exportCountBtn = new ToolStripButton(
					CallCenterBK.constants.count(), "excel.gif");
			exportCountBtn.setLayoutAlign(Alignment.LEFT);
			exportCountBtn.setWidth(50);
			toolStrip.addButton(exportCountBtn);
			exportCountBtn.setDisabled(!hasExcelExpPerm);

			toolStrip.addFill();

			deleteBtn = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			organizationsGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer flow_priority = countryRecord
							.getAttributeAsInt("flow_priority");
					Integer flow_priority_09 = countryRecord
							.getAttributeAsInt("flow_priority_09");
					Integer status = countryRecord.getAttributeAsInt("status");
					Integer is_contractor = countryRecord
							.getAttributeAsInt("super_priority");
					Integer tree_org_child = (countryRecord
							.getAttributeAsString("tree_org_child") != null && countryRecord
							.getAttributeAsString("tree_org_child").equals(
									"associations")) ? 1 : -1000;

					if ((flow_priority != null && flow_priority.intValue() < 0)
							|| (flow_priority_09 != null && flow_priority_09
									.intValue() < 0)) {
						return "color:red;";
					} else if (is_contractor != null && is_contractor == 100) {
						if (tree_org_child != -1000) {
							return "font-weight:bold;color:violet;";
						} else {
							return "color:violet;";
						}
					} else if (status != null && status.equals(2)) {
						if (tree_org_child != -1000) {
							return "font-weight:bold;color:gray;";
						} else {
							return "color:gray;";
						}
					} else if (status != null && status.equals(1)) {
						if (tree_org_child != -1000) {
							return "font-weight:bold;color:blue;";
						} else {
							return "color:blue;";
						}
					} else if (status != null && status.equals(3)) {
						if (tree_org_child != -1000) {
							return "font-weight:bold;color:green;";
						} else {
							return "color:green;";
						}
					} else {
						if (tree_org_child != -1000) {
							return "font-weight:bold;";
						} else {
							return super.getCellCSSText(record, rowNum, colNum);
						}
					}
				};
			};
			organizationsGrid.setLeft(50);
			organizationsGrid.setTop(50);
			organizationsGrid.setWidth100();
			organizationsGrid.setHeight100();
			organizationsGrid
					.setFetchOperation("customOrgSearchForCallCenterNew");
			organizationsGrid.setDataSource(orgDS);
			organizationsGrid.setAutoFetchData(false);
			organizationsGrid.setAutoSaveEdits(false);
			organizationsGrid.setCanSelectAll(false);
			organizationsGrid.setWrapCells(true);
			organizationsGrid.setFixedRecordHeights(false);
			organizationsGrid.setCanSort(false);
			organizationsGrid.setCanSelectText(true);
			organizationsGrid.setWrapCells(true);
			organizationsGrid.setCanDragSelectText(true);
			organizationsGrid.setShowFilterEditor(true);
			organizationsGrid.setFilterOnKeypress(true);

			mainLayout.addMember(organizationsGrid);
			ListGridField tree_org_parrent = new ListGridField(
					"tree_org_parrent", " ", 30);
			tree_org_parrent.setAlign(Alignment.CENTER);
			tree_org_parrent.setType(ListGridFieldType.IMAGE);
			tree_org_parrent.setImageURLPrefix("orgtypes/");
			tree_org_parrent.setImageURLSuffix(".png");
			tree_org_parrent.setCanSort(false);
			tree_org_parrent.setCanFilter(false);

			ListGridField tree_org_child = new ListGridField("tree_org_child",
					" ", 30);
			tree_org_child.setAlign(Alignment.CENTER);
			tree_org_child.setType(ListGridFieldType.IMAGE);
			tree_org_child.setImageURLPrefix("orgtypes/");
			tree_org_child.setImageURLSuffix(".png");
			tree_org_child.setCanSort(false);
			tree_org_child.setCanFilter(false);

			ListGridField organization_name = new ListGridField(
					"organization_name", CallCenterBK.constants.orgName());
			ListGridField real_address = new ListGridField(
					"full_address_not_hidden",
					CallCenterBK.constants.realAddress(), 400);
			real_address.setCanFilter(false);

			organizationsGrid.setFields(tree_org_parrent, tree_org_child,
					organization_name, real_address);

			detailViewer = new DetailViewer();
			detailViewer.setCanSelectText(true);
			detailViewer.setDataSource(orgDS);
			DetailViewerField original_org_name = new DetailViewerField(
					"original_org_name", CallCenterBK.constants.organization());
			DetailViewerField remarkDF = new DetailViewerField("remark",
					CallCenterBK.constants.remark());

			DetailViewerField full_address_not_hiddenDF = new DetailViewerField(
					"full_address_not_hidden",
					CallCenterBK.constants.realAddress());

			DetailViewerField chiefDF = new DetailViewerField("chief",
					CallCenterBK.constants.director());

			DetailViewerField work_hoursDF = new DetailViewerField(
					"work_hours", CallCenterBK.constants.workinghours());

			detailViewer.setFields(original_org_name, remarkDF,
					full_address_not_hiddenDF, chiefDF, work_hoursDF);

			detailViewer.setWidth100();
			detailViewer.setHeight(150);

			mainLayout.addMember(detailViewer);

			organizationsGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(organizationsGrid);
				}
			});

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search(false, false);
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					clearFilter();
				}
			});

			sortBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord records[] = organizationsGrid
							.getSelectedRecords();
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
						criteria.setAttribute("parrent_organization_id",
								organization_id);
						criteria.setAttribute("operator_src",
								Constants.OPERATOR_11808);
						DSRequest requestProperties = new DSRequest();
						requestProperties
								.setOperationId("customOrgSearchForCallCenterNew");
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

			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = organizationsGrid
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
										deleteOrganization(listGridRecord);
									}
								}
							});
				}
			});
			addNewOrgBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = organizationsGrid
							.getSelectedRecord();
					DlgAddEditOrganization dlgAddEditMainOrg = new DlgAddEditOrganization(
							listGridRecord, null, organizationsGrid);
					dlgAddEditMainOrg.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = organizationsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					// Integer is_org_contractor =
					// listGridRecord.getAttributeAsInt("is_org_contractor");
					// if(is_org_contractor!=null &&
					// is_org_contractor.intValue()>0){
					//
					// SC.warn("AAAAAAAAAAAAAAAAAAAAAAA", new BooleanCallback()
					// {
					// @Override
					// public void execute(Boolean value) {
					// DlgAddEditOrganization dlgAddEditMainOrg = new
					// DlgAddEditOrganization(
					// null, listGridRecord, organizationsGrid);
					// dlgAddEditMainOrg.show();
					// }
					// });
					//
					// }else{
					DlgAddEditOrganization dlgAddEditMainOrg = new DlgAddEditOrganization(
							null, listGridRecord, organizationsGrid);
					dlgAddEditMainOrg.show();
					// }
				}
			});

			orgDepartmentsBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = organizationsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					showOrgDepManagementDlg(listGridRecord, null);
				}
			});

			historyBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					ListGridRecord listGridRecord = organizationsGrid
							.getSelectedRecord();
					DlgHistOrganization dlgHistOrganization = new DlgHistOrganization(
							listGridRecord);
					dlgHistOrganization.show();
				}
			});

			KeyPressHandler searchHendler = new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search(false, false);
					}
				}
			};

			orgNameGeoItem.addKeyPressHandler(searchHendler);
			orgCommentItem.addKeyPressHandler(searchHendler);
			orgDepartmentItem.addKeyPressHandler(searchHendler);
			phoneItem.addKeyPressHandler(searchHendler);
			orgDirectorItem.addKeyPressHandler(searchHendler);
			streetItem.addKeyPressHandler(searchHendler);
			orgIdentCodeItem.addKeyPressHandler(searchHendler);
			phoneUpdDateItem.addKeyPressHandler(searchHendler);
			legalStreetItem.addKeyPressHandler(searchHendler);
			orgWorkingHoursItem.addKeyPressHandler(searchHendler);
			orgWebAddressItem.addKeyPressHandler(searchHendler);
			orgEmailItem.addKeyPressHandler(searchHendler);
			orgSocialAddressItem.addKeyPressHandler(searchHendler);
			orgContactPersonItem.addKeyPressHandler(searchHendler);

			supperOrgBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = organizationsGrid
							.getSelectedRecord();
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

			parrentOrgBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = organizationsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer parrent_organization_id = listGridRecord
							.getAttributeAsInt("parrent_organization_id");
					if (parrent_organization_id == null
							|| parrent_organization_id.intValue() <= 0) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.orgDoesNotHaveParrent());
						return;
					}
					fingOrgById(parrent_organization_id, true);
				}
			});

			currentOrgBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = organizationsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer parrent_organization_id = listGridRecord
							.getAttributeAsInt("organization_id");
					if (parrent_organization_id == null
							|| parrent_organization_id.intValue() <= 0) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.orgDoesNotHaveParrent());
						return;
					}
					fingOrgById(parrent_organization_id, true);
				}
			});

			organizationsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = organizationsGrid
									.getSelectedRecord();
							if (listGridRecord == null) {
								SC.say(CallCenterBK.constants.warning(),
										CallCenterBK.constants
												.pleaseSelrecord());
								return;
							}
							DlgAddEditOrganization dlgAddEditMainOrg = new DlgAddEditOrganization(
									null, listGridRecord, organizationsGrid);
							dlgAddEditMainOrg.show();
						}
					});

			exportBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search(true, false);
				}
			});

			exportCountBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search(true, true);
				}
			});

			setPane(mainLayout);
			instance = this;

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
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
					fingOrgById(ret_organization_id, false);
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fingOrgById(Integer organization_id, boolean onlyParrent) {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("pp_organization_id", organization_id);
			if (onlyParrent) {
				criteria.setAttribute("only_parrent", "only_parrent");
			}
			organizationsGrid.setCriteria(criteria);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void deleteOrganization(ListGridRecord listGridRecord) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("organization_id",
					listGridRecord.getAttributeAsInt("organization_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeOrganization");
			organizationsGrid.removeData(record, new DSCallback() {
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

	public void search(boolean isExport, boolean count) {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("operator_src", Constants.OPERATOR_11808);
			String org_name = orgNameGeoItem.getValueAsString();
			if (org_name != null && !org_name.trim().equals("")) {
				criteria.setAttribute("organization_name_param", org_name);
			}

			String remark = orgCommentItem.getValueAsString();
			if (remark != null && !remark.trim().equals("")) {
				criteria.setAttribute("remark_param", remark);
			}
			String phone = phoneItem.getValueAsString();
			if (phone != null && !phone.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("phone", phone);
			}
			String chief = orgDirectorItem.getValueAsString();
			if (chief != null && !chief.trim().equals("")) {
				criteria.setAttribute("chief_param", chief);
			}
			String street = streetItem.getValueAsString();
			if (street != null && !street.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("real_address_descr", street);
			}
			String town_district_id = regionItem.getValueAsString();
			if (town_district_id != null && !town_district_id.trim().equals("")) {
				criteria.setAttribute("town_district_id", town_district_id);
			}
			String town_id = townItem.getValueAsString();
			if (town_id != null && !town_id.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("town_id", new Integer(town_id));
			}
			String ident_code = orgIdentCodeItem.getValueAsString();
			if (ident_code != null && !ident_code.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("ident_code", ident_code);
			}
			String phone_upd_date = phoneUpdDateItem.getValueAsString();
			if (phone_upd_date != null
					&& !phone_upd_date.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("phone_upd_date", new Integer(
						phone_upd_date));
			}
			String legal_street = legalStreetItem.getValueAsString();
			if (legal_street != null
					&& !legal_street.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("legal_real_address_descr", legal_street);
			}
			String legal_town_district_id = legalRegionItem.getValueAsString();
			if (legal_town_district_id != null
					&& !legal_town_district_id.trim().equals("")) {
				criteria.setAttribute("legal_town_district_id", new Integer(
						legal_town_district_id));
			}
			String legal_town_id = legalTownItem.getValueAsString();
			if (legal_town_id != null
					&& !legal_town_id.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("legal_town_id", new Integer(
						legal_town_id));
			}
			String work_hours = orgWorkingHoursItem.getValueAsString();
			if (work_hours != null && !work_hours.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("work_hours", work_hours);
			}
			Boolean main_organization = mainOrganizationItem
					.getValueAsBoolean();
			if (main_organization != null && main_organization.booleanValue()) {
				criteria.setAttribute("main_organization", "YES");
			}
			String department = orgDepartmentItem.getValueAsString();
			if (department != null && !department.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("department_param", department);
			}
			String web_address = orgWebAddressItem.getValueAsString();
			if (web_address != null && !web_address.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("web_address", web_address);
			}
			String email_address = orgEmailItem.getValueAsString();
			if (email_address != null
					&& !email_address.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("email_address", email_address);
			}
			String social_address = orgSocialAddressItem.getValueAsString();
			if (social_address != null
					&& !social_address.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("social_address", social_address);
			}
			Date org_found_date_start = orgFoundedStartItem.getValueAsDate();
			Date org_found_date_end = orgFoundedEndItem.getValueAsDate();
			if (org_found_date_start != null && org_found_date_end != null) {
				criteria.setAttribute("org_found_date_start",
						org_found_date_start);
				criteria.setAttribute("org_found_date_end", org_found_date_end);
			}
			String org_partner_banks = partnerBankItem.getValueAsString();
			if (org_partner_banks != null
					&& !org_partner_banks.trim().equals("")) {
				criteria.setAttribute("org_partner_banks", org_partner_banks);
			}
			MyComboboxItemMultClass orgActivities = orgActivity.getParamClass();
			boolean isByOrgActivity = false;
			if (orgActivities != null
					&& orgActivities.getValueRecords() != null
					&& orgActivities.getValueRecords().length > 0) {
				Record records[] = orgActivities.getValueRecords();
				String orgActivitiesCrit = "";
				int i = 0;
				for (Record record : records) {
					Integer org_activity_id = record
							.getAttributeAsInt("org_activity_id");
					if (i > 0) {
						orgActivitiesCrit += ",";
					}
					orgActivitiesCrit += org_activity_id;
					i++;
				}
				criteria.setAttribute("orgActivitiesCrit", orgActivitiesCrit);
				isByOrgActivity = true;
			}

			String week_daysItem = weekDaysItem.getValueAsString();
			if (week_daysItem != null && !week_daysItem.trim().equals("")) {
				criteria.setAttribute("week_daysItemCrit", week_daysItem);
			}

			String org_statuses = orgStatusItem.getValueAsString();
			if (org_statuses != null && !org_statuses.trim().equals("")) {
				criteria.setAttribute("org_statuses", org_statuses);
			}

			String contact_person = orgContactPersonItem.getValueAsString();
			if (contact_person != null && !contact_person.trim().equals("")) {
				criteria.setAttribute("contact_person", contact_person);
			}

			if ((org_name == null || org_name.trim().equals(""))
					&& (remark == null || remark.trim().equals(""))
					&& (chief == null || chief.trim().equals(""))
					&& (work_hours == null || work_hours.trim().equals(""))
					&& (ident_code == null || ident_code.trim().equals(""))
					&& (department == null || department.trim().equals(""))
					&& (web_address == null || web_address.trim().equals(""))
					&& (email_address == null || email_address.trim()
							.equals(""))
					&& (street == null || street.trim().equals(""))
					&& (legal_street == null || legal_street.trim().equals(""))
					&& (phone == null || phone.trim().equals(""))
					&& (org_found_date_start == null && org_found_date_end == null)
					&& (phone_upd_date == null || phone_upd_date.trim().equals(
							"")) && !isByOrgActivity
					&& (org_statuses == null || org_statuses.trim().equals(""))) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.findOrgEnterAnyParam());
				return;
			}

			DSRequest dsRequest = new DSRequest();

			dsRequest.setAttribute("operationId",
					"customOrgSearchForCallCenterNew");
			if (isExport) {
				criteria.setAttribute("need_phones", "YES");
				criteria.setAttribute("isExport", 1L);
				criteria.setAttribute("operator_src", Constants.OPERATOR_11808);
				dsRequest.setExportAs((ExportFormat) EnumUtil.getEnum(
						ExportFormat.values(), "xls"));
				dsRequest.setExportDisplay(ExportDisplay.DOWNLOAD);
				dsRequest.setExportFields(new String[] { "original_org_name",
						"full_address_not_hidden", "chief",
						"legal_full_address_not_hidden", "unique_ident_code",
						"email_address", "web_address", "concat_phones",
						"concat_activity_descrs" });
			}

			detailViewer.setData(new Record[0]);
			if (isExport && !count) {
				organizationsGrid.getDataSource().exportData(criteria,
						dsRequest, new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
							}
						});
			} else {
				if (count) {
					criteria.setAttribute("only_count", "YES");
					organizationsGrid.getDataSource().fetchData(criteria,
							new DSCallback() {
								@Override
								public void execute(DSResponse response,
										Object rawData, DSRequest request) {
									int count = 0;
									Record records[] = response.getData();
									if (records != null && records.length > 0) {
										count = records[0]
												.getAttributeAsInt("recCount");
									}
									SC.say(CallCenterBK.constants.information(),
											CallCenterBK.constants
													.recordsCount()
													+ " : "
													+ count);
								}
							}, dsRequest);
				} else {
					organizationsGrid.invalidateCache();
					organizationsGrid.fetchData(criteria, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
						}
					}, dsRequest);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public Criteria getAdvCriteria() {
		return advCriteria;
	}

	public void setAdvCriteria(Criteria advCriteria) {
		this.advCriteria = advCriteria;
	}

	public void showOrgDepManagementDlg(ListGridRecord listGridRecord,
			Long department_id) {
		DlgManageOrgDepartments addEditOrgDepartments = new DlgManageOrgDepartments(
				listGridRecord, organizationsGrid, department_id);
		addEditOrgDepartments.show();
	}

	public void filterByDepartmentIdAndPhone(final Long department_id,
			String phone) {
		clearFilter();
		phoneItem.setValue(phone);
		DSRequest dsRequest = new DSRequest();
		dsRequest
				.setAttribute("operationId", "customOrgSearchForCallCenterNew");
		Criteria criteria = new Criteria();
		criteria.setAttribute("org_department_id", department_id);
		criteria.setAttribute("operator_src", Constants.OPERATOR_11808);
		organizationsGrid.invalidateCache();
		organizationsGrid.fetchData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				organizationsGrid.selectRecord(0);
				ListGridRecord listGridRecord = organizationsGrid
						.getSelectedRecord();
				if (listGridRecord != null)
					showOrgDepManagementDlg(listGridRecord, department_id);
			}
		}, dsRequest);
	}

	protected void clearFilter() {
		orgNameGeoItem.clearValue();
		orgCommentItem.clearValue();
		orgDepartmentItem.clearValue();
		phoneItem.clearValue();
		orgDirectorItem.clearValue();
		streetItem.clearValue();
		regionItem.clearValue();
		townItem.clearValue();
		orgIdentCodeItem.clearValue();
		phoneUpdDateItem.clearValue();
		legalStreetItem.clearValue();
		legalRegionItem.clearValue();
		legalTownItem.clearValue();
		orgWorkingHoursItem.clearValue();
		mainOrganizationItem.setValue(false);
		orgWebAddressItem.clearValue();
		orgEmailItem.clearValue();
		orgSocialAddressItem.clearValue();
		orgFoundedEndItem.clearValue();
		orgFoundedStartItem.clearValue();
		partnerBankItem.clearValue();
		orgActivity.clearValues();
		weekDaysItem.clearValue();
		orgStatusItem.clearValue();
		orgContactPersonItem.clearValue();
		try {
			searchForm.focusInItem(orgNameGeoItem);
		} catch (Exception e) {
			// TODO: handle exception
		}
		organizationsGrid.deselectAllRecords();
	}
}
