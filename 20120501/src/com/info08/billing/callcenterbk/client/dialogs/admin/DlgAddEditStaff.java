package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyAddressPanel;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgAddEditStaff extends Window {

	// main layout
	private VLayout hLayout;

	// forms
	// org. main info
	private DynamicForm dynamicFormStaffLeftMainInfo;

	// org. address
	private MyAddressPanel physicalAddress;
	private MyAddressPanel legalAddress;

	private TabSet topTabSet;

	// main info fields.
	private TextItem firstNameItem;
	private TextItem lastNameItem;
	private ComboBoxItem departmentItem;
	private TextItem docNumberItem;
	private TextAreaItem remarkItem;
	private DateItem dobItem;
	private DateItem startDateItem;
	private SelectItem genderItem;
	private SelectItem nationalityItem;
	private SelectItem familyStatusItem;

	private DataSource dataSource;

	private IButton saveItem;
	private int selectedTabIndex = 0;

	// private TreeGrid orgTreeGrid;
	private ListGridRecord listGridRecord;

	private ToolStripButton copyAddress;

	private StaffEducationClientDS staffEducationClientDS;
	private ListGrid staffEducationGrid;
	private RecordList presaveStaffEducationGrid = new RecordList();
	private ToolStripButton addEducationBtn;
	private ToolStripButton editEducationBtn;

	private StaffComputerSkillsClientDS staffComputerSkillsClientDS;
	private ListGrid staffComputerSkillsGrid;
	private ToolStripButton addStaffComputerSkillsBtn;
	private ToolStripButton editStaffComputerSkillsBtn;

	private StaffLanguagesClientDS staffLanguagesClientDS;
	private ListGrid staffLanguagesGrid;
	private ToolStripButton addStaffLanguagesBtn;
	private ToolStripButton editStaffLanguagesBtn;

	private StaffPhonesClientDS staffPhonesClientDS;
	private ListGrid staffPhonesGrid;
	private ToolStripButton addStaffPhonesBtn;
	private ToolStripButton editStaffPhonesBtn;

	private StaffWorksClientDS staffWorksClientDS;
	private ListGrid staffWorksGrid;
	private ToolStripButton addStaffWorksBtn;
	private ToolStripButton editStaffWorksBtn;

	private StaffRelative09ClientDS staffRelative09ClientDS;
	private ListGrid staffRelative09Grid;
	private ToolStripButton addStaffRelative09Btn;
	private ToolStripButton editStaffRelative09Btn;

	private StaffFamousPeopleClientDS staffFamousPeopleClientDS;
	private ListGrid staffFamousPeopleGrid;
	private ToolStripButton addStaffFamousPeopleBtn;
	private ToolStripButton editStaffFamousPeopleBtn;

	public DlgAddEditStaff(DataSource dataSource, ListGridRecord listGridRecord) {
		this.dataSource = dataSource;
		this.listGridRecord = listGridRecord;
		setTitle(CallCenterBK.constants.manageOrgs());

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

		topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		topTabSet.setHeight100();

		dynamicFormStaffLeftMainInfo = new DynamicForm();
		dynamicFormStaffLeftMainInfo.setAutoFocus(true);
		dynamicFormStaffLeftMainInfo.setWidth100();
		dynamicFormStaffLeftMainInfo.setNumCols(2);
		dynamicFormStaffLeftMainInfo.setTitleWidth(150);
		dynamicFormStaffLeftMainInfo.setTitleOrientation(TitleOrientation.TOP);
		dynamicFormStaffLeftMainInfo.setColWidths("50%", "50%");
		dynamicFormStaffLeftMainInfo.setLayoutAlign(Alignment.CENTER);
		dynamicFormStaffLeftMainInfo.setAlign(Alignment.CENTER);

		HLayout formsLayout = new HLayout();
		formsLayout.setWidth100();
		formsLayout.setHeight100();

		VLayout leftLayOut = new VLayout();
		leftLayOut.setWidth(650);
		leftLayOut.setHeight100();

		VLayout formsLayoutAddInfo = new VLayout();
		formsLayoutAddInfo.setWidth100();
		formsLayoutAddInfo.setHeight100();
		formsLayoutAddInfo.setPadding(0);
		formsLayoutAddInfo.setMargin(0);
		formsLayoutAddInfo.setMembersMargin(10);

		HLayout hLayoutForAddresses = new HLayout();
		hLayoutForAddresses.setWidth100();
		hLayoutForAddresses.setPadding(0);
		hLayoutForAddresses.setMargin(0);

		physicalAddress = new MyAddressPanel(null, "PhysicalAddress",
				"ფაქტიური მისამართი", 614, 0);

		legalAddress = new MyAddressPanel(null, "LegalAddress",
				"იურიდიული მისამართი", 614, 0);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth(1228);
		toolStrip.setPadding(5);

		copyAddress = new ToolStripButton(CallCenterBK.constants.sameAddress(),
				"copy.png");
		copyAddress.setLayoutAlign(Alignment.LEFT);
		copyAddress.setWidth(50);
		toolStrip.addButton(copyAddress);

		hLayoutForAddresses.setMembers(physicalAddress, legalAddress);

		HLayout hLayoutForAddresses1 = new HLayout();
		hLayoutForAddresses1.setWidth100();
		hLayoutForAddresses1.setPadding(0);
		hLayoutForAddresses1.setMargin(0);

		// TODO

		/***********************************************************************************/
		/******************************* Staff Relative 09 *********************************/
		/***********************************************************************************/

		staffRelative09ClientDS = StaffRelative09ClientDS.getInstance();

		staffRelative09Grid = new ListGrid();
		staffRelative09Grid.setWidth100();
		staffRelative09Grid.setHeight(250);
		staffRelative09Grid.setDataSource(staffRelative09ClientDS);
		staffRelative09Grid.setCanReorderRecords(true);
		staffRelative09Grid.setCanRemoveRecords(true);
		staffRelative09Grid.setAutoFetchData(true);
		staffRelative09Grid.setWrapCells(true);
		staffRelative09Grid.setFixedRecordHeights(false);

		ListGridField first_name = new ListGridField("first_name", "სახელი",
				150);
		ListGridField last_name = new ListGridField("last_name", "გვარი", 150);
		ListGridField position = new ListGridField("position", "პოზიცია");

		staffRelative09Grid.setFields(first_name, last_name, position);

		ToolStrip toolRelative09 = new ToolStrip();
		toolRelative09.setWidth100();
		toolRelative09.setPadding(5);

		Label toolRelative09Label = new Label("კავშირი 09-თან");
		toolRelative09Label.setWidth(150);
		toolRelative09Label.setStyleName("staffGridTitle");
		toolRelative09.addMember(toolRelative09Label);

		addStaffRelative09Btn = new ToolStripButton(
				CallCenterBK.constants.add(), "addIcon.png");
		addStaffRelative09Btn.setLayoutAlign(Alignment.LEFT);
		addStaffRelative09Btn.setWidth(50);
		toolRelative09.addButton(addStaffRelative09Btn);

		addStaffRelative09Btn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DlgAddEditStaffRelative09 dlgAddEditStaffRelative09 = new DlgAddEditStaffRelative09(
						staffRelative09Grid, null);
				dlgAddEditStaffRelative09.show();
			}
		});

		editStaffRelative09Btn = new ToolStripButton(
				CallCenterBK.constants.modify(), "editIcon.png");
		editStaffRelative09Btn.setLayoutAlign(Alignment.LEFT);
		editStaffRelative09Btn.setWidth(50);
		toolRelative09.addButton(editStaffRelative09Btn);

		editStaffRelative09Btn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord listGridRecord = staffRelative09Grid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffRelative09 dlgAddEditStaffRelative09 = new DlgAddEditStaffRelative09(
						staffRelative09Grid, listGridRecord);
				dlgAddEditStaffRelative09.show();

			}
		});

		staffRelative09Grid.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				ListGridRecord listGridRecord = staffRelative09Grid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffRelative09 dlgAddEditStaffRelative09 = new DlgAddEditStaffRelative09(
						staffRelative09Grid, listGridRecord);
				dlgAddEditStaffRelative09.show();

			}
		});

		/***********************************************************************************/
		/***********************************************************************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/******************************* Staff Famous People *******************************/
		/***********************************************************************************/

		staffFamousPeopleClientDS = StaffFamousPeopleClientDS.getInstance();

		staffFamousPeopleGrid = new ListGrid();
		staffFamousPeopleGrid.setWidth100();
		staffFamousPeopleGrid.setHeight(250);
		staffFamousPeopleGrid.setDataSource(staffFamousPeopleClientDS);
		staffFamousPeopleGrid.setCanReorderRecords(true);
		staffFamousPeopleGrid.setCanRemoveRecords(true);
		staffFamousPeopleGrid.setAutoFetchData(true);
		staffFamousPeopleGrid.setWrapCells(true);
		staffFamousPeopleGrid.setFixedRecordHeights(false);

		ListGridField famous_people_first_name = new ListGridField(
				"first_name", "სახელი", 150);
		ListGridField famous_people_last_name = new ListGridField("last_name",
				"გვარი", 150);
		ListGridField relation_type_id_descr = new ListGridField(
				"relation_type_id_descr", "კავშირი");
		ListGridField sphere_of_activity_id_descr = new ListGridField(
				"sphere_of_activity_id_descr", "მოღვაწეობის სფერო");

		staffFamousPeopleGrid.setFields(famous_people_first_name,
				famous_people_last_name, relation_type_id_descr,
				sphere_of_activity_id_descr);

		ToolStrip toolFamousPeople = new ToolStrip();
		toolFamousPeople.setWidth100();
		toolFamousPeople.setPadding(5);

		Label toolFamousPeopleLabel = new Label("ცნობილი პიროვნებები");
		toolFamousPeopleLabel.setWidth(200);
		toolFamousPeopleLabel.setStyleName("staffGridTitle");
		toolFamousPeople.addMember(toolFamousPeopleLabel);

		addStaffFamousPeopleBtn = new ToolStripButton(
				CallCenterBK.constants.add(), "addIcon.png");
		addStaffFamousPeopleBtn.setLayoutAlign(Alignment.LEFT);
		addStaffFamousPeopleBtn.setWidth(50);
		toolFamousPeople.addButton(addStaffFamousPeopleBtn);

		addStaffFamousPeopleBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DlgAddEditStaffFamousPeople dlgAddEditStaffFamousPeople = new DlgAddEditStaffFamousPeople(
						staffFamousPeopleGrid, null);
				dlgAddEditStaffFamousPeople.show();
			}
		});

		editStaffFamousPeopleBtn = new ToolStripButton(
				CallCenterBK.constants.modify(), "editIcon.png");
		editStaffFamousPeopleBtn.setLayoutAlign(Alignment.LEFT);
		editStaffFamousPeopleBtn.setWidth(50);
		toolFamousPeople.addButton(editStaffFamousPeopleBtn);

		editStaffFamousPeopleBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord listGridRecord = staffFamousPeopleGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffFamousPeople dlgAddEditStaffFamousPeople = new DlgAddEditStaffFamousPeople(
						staffFamousPeopleGrid, listGridRecord);
				dlgAddEditStaffFamousPeople.show();

			}
		});

		staffFamousPeopleGrid.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				ListGridRecord listGridRecord = staffFamousPeopleGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffFamousPeople dlgAddEditStaffFamousPeople = new DlgAddEditStaffFamousPeople(
						staffFamousPeopleGrid, listGridRecord);
				dlgAddEditStaffFamousPeople.show();

			}
		});

		/***********************************************************************************/
		/***********************************************************************************/
		/***********************************************************************************/

		VLayout layoutRelative09 = new VLayout();
		layoutRelative09.setWidth("50%");

		VLayout layoutFamousPeople = new VLayout();
		layoutFamousPeople.setWidth("50%");

		layoutRelative09.setMembers(toolRelative09, staffRelative09Grid);
		layoutFamousPeople.setMembers(toolFamousPeople, staffFamousPeopleGrid);
		hLayoutForAddresses1.setMembers(layoutRelative09, layoutFamousPeople);

		formsLayoutAddInfo.addMember(toolStrip);
		formsLayoutAddInfo
				.addMembers(hLayoutForAddresses, hLayoutForAddresses1);

		firstNameItem = new TextItem();
		firstNameItem.setName("first_name");
		firstNameItem.setWidth(300);
		firstNameItem.setTitle("სახელი");
		// 650
		lastNameItem = new TextItem();
		lastNameItem.setName("last_name");
		lastNameItem.setWidth(300);
		lastNameItem.setTitle("გვარი");

		departmentItem = new ComboBoxItem();
		departmentItem.setTitle("განყოფილება");
		departmentItem.setName("department_id");
		departmentItem.setWidth(300);
		departmentItem.setFetchMissingValues(false);

		ClientUtils.fillCombo(departmentItem, "StaffDS", "getAllDepartments",
				"department_id", "department_name");

		genderItem = new SelectItem();
		genderItem.setTitle("სქესი");
		genderItem.setName("genderItem");
		genderItem.setWidth(300);
		genderItem.setFetchMissingValues(false);

		ClientUtils.fillDescriptionCombo(genderItem, 64000);

		nationalityItem = new SelectItem();
		nationalityItem.setTitle("ეროვნება");
		nationalityItem.setName("nationalityItem");
		nationalityItem.setWidth(300);
		nationalityItem.setFetchMissingValues(false);

		ClientUtils.fillDescriptionCombo(nationalityItem, 65000);

		familyStatusItem = new SelectItem();
		familyStatusItem.setTitle("ოჯახური მდგომარეობა");
		familyStatusItem.setName("familyStatusItem");
		familyStatusItem.setWidth(300);
		familyStatusItem.setFetchMissingValues(false);

		ClientUtils.fillDescriptionCombo(familyStatusItem, 66000);

		docNumberItem = new TextItem();
		docNumberItem.setTitle("პირადობა");
		docNumberItem.setWidth(300);
		docNumberItem.setName("doc_num");

		remarkItem = new TextAreaItem();
		remarkItem.setTitle("კომენტარი");
		remarkItem.setWidth(650);
		remarkItem.setColSpan(2);
		remarkItem.setName("remark");
		remarkItem.setHeight(70);

		dobItem = new DateItem();
		dobItem.setTitle("დაბადების თარიღი");
		dobItem.setWidth(300);
		dobItem.setName("dobItem");
		dobItem.setUseTextField(true);

		startDateItem = new DateItem();
		startDateItem.setTitle("დაწყების თარიღი");
		startDateItem.setWidth(300);
		startDateItem.setName("startDateItem");
		startDateItem.setUseTextField(true);

		staffEducationClientDS = StaffEducationClientDS.getInstance();

		staffEducationGrid = new ListGrid();
		staffEducationGrid.setWidth100();
		staffEducationGrid.setHeight(130);
		staffEducationGrid.setDataSource(staffEducationClientDS);
		staffEducationGrid.setCanReorderRecords(true);
		staffEducationGrid.setCanRemoveRecords(true);
		staffEducationGrid.setAutoFetchData(true);
		staffEducationGrid.setWrapCells(true);
		staffEducationGrid.setFixedRecordHeights(false);

		ListGridField college_name = new ListGridField("college_name",
				"დაწესებულების დასახელება");
		ListGridField faculty_name = new ListGridField("faculty_name",
				"ფაკულტეტი", 190);
		ListGridField degree_descr = new ListGridField("degree_descr",
				"წოდება", 90);
		ListGridField se_years = new ListGridField("se_years", "წლები", 70);

		staffEducationGrid.setFields(college_name, faculty_name, degree_descr,
				se_years);

		ToolStrip toolStripEducation = new ToolStrip();
		toolStripEducation.setWidth100();
		toolStripEducation.setPadding(5);

		Label label = new Label("განათლება");
		label.setStyleName("staffGridTitle");
		toolStripEducation.addMember(label);

		addEducationBtn = new ToolStripButton(CallCenterBK.constants.add(),
				"addIcon.png");
		addEducationBtn.setLayoutAlign(Alignment.LEFT);
		addEducationBtn.setWidth(50);
		toolStripEducation.addButton(addEducationBtn);

		addEducationBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DlgAddEditStaffEducation dlgAddEditStaffEducation = new DlgAddEditStaffEducation(
						staffEducationGrid, null);
				dlgAddEditStaffEducation.show();
			}
		});

		editEducationBtn = new ToolStripButton(CallCenterBK.constants.modify(),
				"editIcon.png");
		editEducationBtn.setLayoutAlign(Alignment.LEFT);
		editEducationBtn.setWidth(50);
		toolStripEducation.addButton(editEducationBtn);

		editEducationBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord listGridRecord = staffEducationGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffEducation dlgAddEditStaffEducation = new DlgAddEditStaffEducation(
						staffEducationGrid, listGridRecord);
				dlgAddEditStaffEducation.show();

			}
		});

		staffEducationGrid.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				ListGridRecord listGridRecord = staffEducationGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffEducation dlgAddEditStaffEducation = new DlgAddEditStaffEducation(
						staffEducationGrid, listGridRecord);
				dlgAddEditStaffEducation.show();

			}
		});

		/***********************************************************************************************/
		/*************************************** Staff Computer Skills *********************************/
		/***********************************************************************************************/

		staffComputerSkillsClientDS = StaffComputerSkillsClientDS.getInstance();

		staffComputerSkillsGrid = new ListGrid();
		staffComputerSkillsGrid.setWidth100();
		staffComputerSkillsGrid.setHeight(125);
		staffComputerSkillsGrid.setDataSource(staffComputerSkillsClientDS);
		staffComputerSkillsGrid.setCanReorderRecords(true);
		staffComputerSkillsGrid.setCanRemoveRecords(true);
		staffComputerSkillsGrid.setAutoFetchData(true);
		staffComputerSkillsGrid.setWrapCells(true);
		staffComputerSkillsGrid.setFixedRecordHeights(false);

		ListGridField software = new ListGridField("software", "პროგრამა", 200);
		ListGridField training_course = new ListGridField("training_course",
				"კურსის დასახელება");
		ListGridField comp_skill_remark = new ListGridField("remark",
				"კომენტარი", 200);

		staffComputerSkillsGrid.setFields(software, training_course,
				comp_skill_remark);

		ToolStrip toolComputerSkills = new ToolStrip();
		toolComputerSkills.setWidth100();
		toolComputerSkills.setPadding(5);

		Label toolComputerSkillsLabel = new Label("კომპ. ცოდნა");
		toolComputerSkillsLabel.setStyleName("staffGridTitle");
		toolComputerSkills.addMember(toolComputerSkillsLabel);

		addStaffComputerSkillsBtn = new ToolStripButton(
				CallCenterBK.constants.add(), "addIcon.png");
		addStaffComputerSkillsBtn.setLayoutAlign(Alignment.LEFT);
		addStaffComputerSkillsBtn.setWidth(50);
		toolComputerSkills.addButton(addStaffComputerSkillsBtn);

		addStaffComputerSkillsBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DlgAddEditStaffComputerSkills addEditStaffComputerSkills = new DlgAddEditStaffComputerSkills(
						staffComputerSkillsGrid, null);
				addEditStaffComputerSkills.show();
			}
		});

		editStaffComputerSkillsBtn = new ToolStripButton(
				CallCenterBK.constants.modify(), "editIcon.png");
		editStaffComputerSkillsBtn.setLayoutAlign(Alignment.LEFT);
		editStaffComputerSkillsBtn.setWidth(50);
		toolComputerSkills.addButton(editStaffComputerSkillsBtn);

		editStaffComputerSkillsBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord listGridRecord = staffComputerSkillsGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffComputerSkills addEditStaffComputerSkills = new DlgAddEditStaffComputerSkills(
						staffComputerSkillsGrid, listGridRecord);
				addEditStaffComputerSkills.show();

			}
		});

		staffComputerSkillsGrid.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				ListGridRecord listGridRecord = staffComputerSkillsGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffComputerSkills addEditStaffComputerSkills = new DlgAddEditStaffComputerSkills(
						staffComputerSkillsGrid, listGridRecord);
				addEditStaffComputerSkills.show();

			}
		});

		/***********************************************************************************/
		/***********************************************************************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/******************************* Staff Languages ***********************************/
		/***********************************************************************************/

		staffLanguagesClientDS = StaffLanguagesClientDS.getInstance();

		staffLanguagesGrid = new ListGrid();
		staffLanguagesGrid.setWidth100();
		staffLanguagesGrid.setHeight(120);
		staffLanguagesGrid.setDataSource(staffLanguagesClientDS);
		staffLanguagesGrid.setCanReorderRecords(true);
		staffLanguagesGrid.setCanRemoveRecords(true);
		staffLanguagesGrid.setAutoFetchData(true);
		staffLanguagesGrid.setWrapCells(true);
		staffLanguagesGrid.setFixedRecordHeights(false);

		ListGridField language_descr = new ListGridField("language_descr",
				"ენა", 150);
		ListGridField language_level_descr = new ListGridField(
				"language_level_descr", "ცოდნის დონე", 150);
		ListGridField certificate_descr = new ListGridField(
				"certificate_descr", "სერტიფიკატი");

		staffLanguagesGrid.setFields(language_descr, language_level_descr,
				certificate_descr);

		ToolStrip toolLanguages = new ToolStrip();
		toolLanguages.setWidth100();
		toolLanguages.setPadding(5);

		Label toolLanguagesLabel = new Label("ენები");
		toolLanguagesLabel.setStyleName("staffGridTitle");
		toolLanguages.addMember(toolLanguagesLabel);

		addStaffLanguagesBtn = new ToolStripButton(
				CallCenterBK.constants.add(), "addIcon.png");
		addStaffLanguagesBtn.setLayoutAlign(Alignment.LEFT);
		addStaffLanguagesBtn.setWidth(50);
		toolLanguages.addButton(addStaffLanguagesBtn);

		addStaffLanguagesBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DlgAddEditStaffLanguages dlgAddEditStaffLanguages = new DlgAddEditStaffLanguages(
						staffLanguagesGrid, null);
				dlgAddEditStaffLanguages.show();
			}
		});

		editStaffLanguagesBtn = new ToolStripButton(
				CallCenterBK.constants.modify(), "editIcon.png");
		editStaffLanguagesBtn.setLayoutAlign(Alignment.LEFT);
		editStaffLanguagesBtn.setWidth(50);
		toolLanguages.addButton(editStaffLanguagesBtn);

		editStaffLanguagesBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord listGridRecord = staffLanguagesGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffLanguages dlgAddEditStaffLanguages = new DlgAddEditStaffLanguages(
						staffLanguagesGrid, listGridRecord);
				dlgAddEditStaffLanguages.show();

			}
		});

		staffLanguagesGrid.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				ListGridRecord listGridRecord = staffLanguagesGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffLanguages dlgAddEditStaffLanguages = new DlgAddEditStaffLanguages(
						staffLanguagesGrid, listGridRecord);
				dlgAddEditStaffLanguages.show();

			}
		});

		/***********************************************************************************/
		/***********************************************************************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/******************************* Staff Phones **************************************/
		/***********************************************************************************/

		staffPhonesClientDS = StaffPhonesClientDS.getInstance();

		staffPhonesGrid = new ListGrid();
		staffPhonesGrid.setWidth100();
		staffPhonesGrid.setHeight(100);
		staffPhonesGrid.setDataSource(staffPhonesClientDS);
		staffPhonesGrid.setCanReorderRecords(true);
		staffPhonesGrid.setCanRemoveRecords(true);
		staffPhonesGrid.setAutoFetchData(true);
		staffPhonesGrid.setWrapCells(true);
		staffPhonesGrid.setFixedRecordHeights(false);

		ListGridField staff_phone_type = new ListGridField("staff_phone_type",
				"ტელეფონის ტიპი", 200);
		ListGridField staff_phone = new ListGridField("staff_phone", "ნომერი");

		staffPhonesGrid.setFields(staff_phone_type, staff_phone);

		ToolStrip toolPhones = new ToolStrip();
		toolPhones.setWidth100();
		toolPhones.setPadding(5);

		Label toolPhonesLabel = new Label("ტელეფონი");
		toolPhonesLabel.setStyleName("staffGridTitle");
		toolPhones.addMember(toolPhonesLabel);

		addStaffPhonesBtn = new ToolStripButton(CallCenterBK.constants.add(),
				"addIcon.png");
		addStaffPhonesBtn.setLayoutAlign(Alignment.LEFT);
		addStaffPhonesBtn.setWidth(50);
		toolPhones.addButton(addStaffPhonesBtn);

		addStaffPhonesBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DlgAddEditStaffPhones dlgAddEditStaffPhones = new DlgAddEditStaffPhones(
						staffPhonesGrid, null);
				dlgAddEditStaffPhones.show();
			}
		});

		editStaffPhonesBtn = new ToolStripButton(
				CallCenterBK.constants.modify(), "editIcon.png");
		editStaffPhonesBtn.setLayoutAlign(Alignment.LEFT);
		editStaffPhonesBtn.setWidth(50);
		toolPhones.addButton(editStaffPhonesBtn);

		editStaffPhonesBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord listGridRecord = staffPhonesGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffPhones dlgAddEditStaffPhones = new DlgAddEditStaffPhones(
						staffLanguagesGrid, listGridRecord);
				dlgAddEditStaffPhones.show();

			}
		});

		staffPhonesGrid.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				ListGridRecord listGridRecord = staffPhonesGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffPhones dlgAddEditStaffPhones = new DlgAddEditStaffPhones(
						staffLanguagesGrid, listGridRecord);
				dlgAddEditStaffPhones.show();

			}
		});

		/***********************************************************************************/
		/***********************************************************************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/******************************* Staff Works ***************************************/
		/***********************************************************************************/

		staffWorksClientDS = StaffWorksClientDS.getInstance();

		staffWorksGrid = new ListGrid();
		staffWorksGrid.setWidth100();
		staffWorksGrid.setHeight(299);
		staffWorksGrid.setDataSource(staffWorksClientDS);
		staffWorksGrid.setCanReorderRecords(true);
		staffWorksGrid.setCanRemoveRecords(true);
		staffWorksGrid.setAutoFetchData(true);
		staffWorksGrid.setWrapCells(true);
		staffWorksGrid.setFixedRecordHeights(false);

		ListGridField work_place = new ListGridField("work_place", "კომპანია",
				200);
		ListGridField work_position = new ListGridField("work_position",
				"პოზიცია");

		ListGridField work_start_date_descr = new ListGridField(
				"work_start_date_descr", "დაწყება", 75);
		ListGridField work_end_date_descr = new ListGridField(
				"work_end_date_descr", "დამთავრება", 75);

		staffWorksGrid.setFields(work_place, work_position,
				work_start_date_descr, work_end_date_descr);

		ToolStrip toolWorks = new ToolStrip();
		toolWorks.setWidth100();
		toolWorks.setPadding(5);

		Label toolWorksLabel = new Label("სამსახურები");
		toolWorksLabel.setStyleName("staffGridTitle");
		toolWorks.addMember(toolWorksLabel);

		addStaffWorksBtn = new ToolStripButton(CallCenterBK.constants.add(),
				"addIcon.png");
		addStaffWorksBtn.setLayoutAlign(Alignment.LEFT);
		addStaffWorksBtn.setWidth(50);
		toolWorks.addButton(addStaffWorksBtn);

		addStaffWorksBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DlgAddEditStaffWorks dlgAddEditStaffWorks = new DlgAddEditStaffWorks(
						staffWorksGrid, null);
				dlgAddEditStaffWorks.show();
			}
		});

		editStaffWorksBtn = new ToolStripButton(
				CallCenterBK.constants.modify(), "editIcon.png");
		editStaffWorksBtn.setLayoutAlign(Alignment.LEFT);
		editStaffWorksBtn.setWidth(50);
		toolWorks.addButton(editStaffWorksBtn);

		editStaffWorksBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord listGridRecord = staffWorksGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffWorks dlgAddEditStaffWorks = new DlgAddEditStaffWorks(
						staffWorksGrid, listGridRecord);
				dlgAddEditStaffWorks.show();

			}
		});

		staffWorksGrid.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				ListGridRecord listGridRecord = staffWorksGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}

				DlgAddEditStaffWorks dlgAddEditStaffWorks = new DlgAddEditStaffWorks(
						staffWorksGrid, listGridRecord);
				dlgAddEditStaffWorks.show();

			}
		});

		/***********************************************************************************/
		/***********************************************************************************/
		/***********************************************************************************/

		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setHeight(10);
		spacerItem.setColSpan(2);

		// dynamicFormMainInfo.setGroupTitle("");
		// dynamicFormMainInfo.setIsGroup(true);

		VLayout vLayoutSpaceRight = new VLayout();
		vLayoutSpaceRight.setHeight(10);

		VLayout vLayoutSpaceRight2 = new VLayout();
		vLayoutSpaceRight2.setHeight(16);

		VLayout rigthLayOut = new VLayout();
		rigthLayOut.setHeight100();
		rigthLayOut.addMembers(toolLanguages, staffLanguagesGrid,
				vLayoutSpaceRight, toolPhones, staffPhonesGrid,
				vLayoutSpaceRight2, toolWorks, staffWorksGrid);

		formsLayout.setMembers(leftLayOut, rigthLayOut);
		Tab tabMainInfo = new Tab(CallCenterBK.constants.maininfo());
		tabMainInfo.setPane(formsLayout);
		topTabSet.addTab(tabMainInfo);

		Tab tabAddInfo = new Tab(CallCenterBK.constants.addInfo());
		tabAddInfo.setPane(formsLayoutAddInfo);
		topTabSet.addTab(tabAddInfo);

		hLayout.addMember(topTabSet);

		VLayout vLayoutSpace = new VLayout();
		vLayoutSpace.setHeight(10);
		leftLayOut.setMembers(dynamicFormStaffLeftMainInfo, toolStripEducation,
				staffEducationGrid, vLayoutSpace, toolComputerSkills,
				staffComputerSkillsGrid);

		dynamicFormStaffLeftMainInfo.setFields(firstNameItem, lastNameItem,
				departmentItem, docNumberItem, genderItem, nationalityItem,
				familyStatusItem, dobItem, startDateItem, remarkItem,
				spacerItem);

		// leftLayOut.addMember(gridsLayout);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		saveItem = new IButton();
		saveItem.setTitle(CallCenterBK.constants.save());
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle(CallCenterBK.constants.close());
		cancItem.setWidth(100);

		hLayoutItem.setMembers(saveItem, cancItem);

		hLayout.addMember(hLayoutItem);

		cancItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});
		saveItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (selectedTabIndex == 0) {
					topTabSet.selectTab(1);
				} else {
					save();
				}
			}
		});

		copyAddress.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				legalAddress.setValues(physicalAddress.getValues());
			}
		});

		topTabSet.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				selectedTabIndex = event.getTabNum();
				if (selectedTabIndex == 0) {
					saveItem.setTitle(CallCenterBK.constants.next());
				} else {
					saveItem.setTitle(CallCenterBK.constants.save());
				}
			}
		});

		fillFields();
		addItem(hLayout);
	}

	private void fillFields() {
		try {
			if (listGridRecord == null) {
				genderItem.setValue(64100);
				nationalityItem.setValue(65100);
				return;
			}

			Integer address_id = listGridRecord.getAttributeAsInt("address_id");
			Integer document_address_id = listGridRecord
					.getAttributeAsInt("document_address_id");

			physicalAddress.setValue(address_id);
			legalAddress.setValue(document_address_id);

			firstNameItem.setValue(listGridRecord.getAttribute("first_name"));
			lastNameItem.setValue(listGridRecord.getAttribute("last_name"));
			departmentItem.setValue(listGridRecord
					.getAttribute("department_id"));
			docNumberItem.setValue(listGridRecord.getAttribute("doc_num"));
			genderItem.setValue(listGridRecord.getAttribute("gender_id"));
			nationalityItem.setValue(listGridRecord
					.getAttribute("nationality_id"));
			familyStatusItem.setValue(listGridRecord
					.getAttribute("family_status_id"));
			startDateItem.setValue(listGridRecord
					.getAttribute("start_date_descr"));

			dobItem.setValue(listGridRecord.getAttribute("dob_descr"));
			remarkItem.setValue(listGridRecord.getAttribute("remark"));

			DataSource staffEducationDS = DataSource.get("StaffEducationDS");

			Criteria criteria = new Criteria();
			criteria.setAttribute("staff_id",
					listGridRecord.getAttributeAsInt("staff_id"));

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "getAllStaffEducations");

			staffEducationDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records == null || records.length <= 0) {
						return;
					}
					for (Record record : records) {
						staffEducationGrid.addData(record);
						presaveStaffEducationGrid.add(record);
					}
				}
			}, dsRequest);

			DataSource staffComputerSkillsDS = DataSource
					.get("StaffComputerSkillsDS");

			Criteria criteriaCompSkills = new Criteria();
			criteriaCompSkills.setAttribute("staff_id",
					listGridRecord.getAttributeAsInt("staff_id"));

			DSRequest dsRequestCompSkills = new DSRequest();
			dsRequestCompSkills.setAttribute("operationId",
					"getAllStaffComputerSkills");

			staffComputerSkillsDS.fetchData(criteriaCompSkills,
					new DSCallback() {

						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							Record records[] = response.getData();
							if (records == null || records.length <= 0) {
								return;
							}
							for (Record record : records) {
								staffComputerSkillsGrid.addData(record);
							}
						}
					}, dsRequestCompSkills);

			DataSource staffLanguagesDS = DataSource.get("StaffLanguagesDS");

			Criteria criteriaLanguages = new Criteria();
			criteriaLanguages.setAttribute("staff_id",
					listGridRecord.getAttributeAsInt("staff_id"));

			DSRequest dsRequestLanguages = new DSRequest();
			dsRequestLanguages.setAttribute("operationId",
					"getAllStaffLanguages");

			staffLanguagesDS.fetchData(criteriaLanguages, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records == null || records.length <= 0) {
						return;
					}
					for (Record record : records) {
						staffLanguagesGrid.addData(record);
					}
				}
			}, dsRequestLanguages);

			DataSource staffPhonesDS = DataSource.get("StaffPhonesDS");

			Criteria criteriaPhones = new Criteria();
			criteriaPhones.setAttribute("staff_id",
					listGridRecord.getAttributeAsInt("staff_id"));

			DSRequest dsRequestPhones = new DSRequest();
			dsRequestPhones.setAttribute("operationId", "getAllStaffPhones");

			staffPhonesDS.fetchData(criteriaPhones, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records == null || records.length <= 0) {
						return;
					}
					for (Record record : records) {
						staffPhonesGrid.addData(record);
					}
				}
			}, dsRequestPhones);

			DataSource staffWorksDS = DataSource.get("StaffWorksDS");

			Criteria criteriaWorks = new Criteria();
			criteriaWorks.setAttribute("staff_id",
					listGridRecord.getAttributeAsInt("staff_id"));

			DSRequest dsRequestWorks = new DSRequest();
			dsRequestWorks.setAttribute("operationId", "getAllStaffWorks");

			staffWorksDS.fetchData(criteriaWorks, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records == null || records.length <= 0) {
						return;
					}
					for (Record record : records) {
						staffWorksGrid.addData(record);
					}
				}
			}, dsRequestWorks);

			DataSource staffRelative09DS = DataSource.get("StaffRelative09DS");

			Criteria criteriaRelative09 = new Criteria();
			criteriaRelative09.setAttribute("staff_id",
					listGridRecord.getAttributeAsInt("staff_id"));

			DSRequest dsRequestRelative09 = new DSRequest();
			dsRequestRelative09.setAttribute("operationId",
					"getAllStaffRelative09");

			staffRelative09DS.fetchData(criteriaRelative09, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records == null || records.length <= 0) {
						return;
					}
					for (Record record : records) {
						staffRelative09Grid.addData(record);
					}
				}
			}, dsRequestRelative09);

			DataSource staffFamousPeopleDS = DataSource
					.get("StaffFamousPeopleDS");

			Criteria criteriaFamousPeople = new Criteria();
			criteriaFamousPeople.setAttribute("staff_id",
					listGridRecord.getAttributeAsInt("staff_id"));

			DSRequest dsRequestFamousPeople = new DSRequest();
			dsRequestFamousPeople.setAttribute("operationId",
					"getAllStaffFamousPeople");

			staffFamousPeopleDS.fetchData(criteriaFamousPeople,
					new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							Record records[] = response.getData();
							if (records == null || records.length <= 0) {
								return;
							}
							for (Record record : records) {
								staffFamousPeopleGrid.addData(record);
							}
						}
					}, dsRequestFamousPeople);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String firstName = firstNameItem.getValueAsString();
			if (firstName == null || firstName.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ სახელი !");
				return;
			}
			String lastName = lastNameItem.getValueAsString();
			if (lastName == null || lastName.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ გვარი !");
				return;
			}
			String docNumber = docNumberItem.getValueAsString();
			if (docNumber == null || docNumber.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ პირადი ნომერი !");
				return;
			}

			if (firstName.length() > 100) {
				SC.say("სახელი შედგება მაქსიმუმ 100 სიმბოლოსაგან !");
				return;
			}
			if (lastName.length() > 150) {
				SC.say("გვარი შედგება მაქსიმუმ 150 სიმბოლოსაგან !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			Map<String, Map<String, String>> preStaffEducation = new TreeMap<String, Map<String, String>>();

			if (staffEducationGrid.getDataAsRecordList() != null) {
				for (int i = 0; i < staffEducationGrid.getDataAsRecordList()
						.getLength(); i++) {
					Record element = staffEducationGrid.getDataAsRecordList()
							.get(i);
					if (element != null) {
						Map<String, String> item = new TreeMap<String, String>();
						item.put("college_name",
								element.getAttribute("college_name"));
						item.put("faculty_name",
								element.getAttribute("faculty_name"));
						item.put("degree_descr_id",
								element.getAttribute("degree_descr_id"));
						item.put("start_year",
								element.getAttribute("start_year"));
						item.put("end_year", element.getAttribute("end_year"));
						preStaffEducation.put(element
								.getAttributeAsString("staff_education_id"),
								item);
					}
				}
			}

			Map<String, Map<String, String>> preStaffCompSkills = new TreeMap<String, Map<String, String>>();

			if (staffComputerSkillsGrid.getDataAsRecordList() != null) {
				for (int i = 0; i < staffComputerSkillsGrid
						.getDataAsRecordList().getLength(); i++) {
					Record element = staffComputerSkillsGrid
							.getDataAsRecordList().get(i);
					if (element != null) {
						Map<String, String> item = new TreeMap<String, String>();
						item.put("software", element.getAttribute("software"));
						item.put("training_course",
								element.getAttribute("training_course"));
						item.put("remark", element.getAttribute("remark"));
						preStaffCompSkills.put(element
								.getAttributeAsString("staff_comp_skill_id"),
								item);
					}
				}
			}

			Map<String, Map<String, String>> preStaffLanguages = new TreeMap<String, Map<String, String>>();

			if (staffLanguagesGrid.getDataAsRecordList() != null) {
				for (int i = 0; i < staffLanguagesGrid.getDataAsRecordList()
						.getLength(); i++) {
					Record element = staffLanguagesGrid.getDataAsRecordList()
							.get(i);
					if (element != null) {
						Map<String, String> item = new TreeMap<String, String>();
						item.put("language_descr_id",
								element.getAttribute("language_descr_id"));
						item.put("language_level_descr_id",
								element.getAttribute("language_level_descr_id"));
						item.put("certificate_descr",
								element.getAttribute("certificate_descr"));
						preStaffLanguages.put(element
								.getAttributeAsString("staff_language_id"),
								item);
					}
				}
			}

			Map<String, Map<String, String>> preStaffPhones = new TreeMap<String, Map<String, String>>();

			if (staffPhonesGrid.getDataAsRecordList() != null) {
				for (int i = 0; i < staffPhonesGrid.getDataAsRecordList()
						.getLength(); i++) {
					Record element = staffPhonesGrid.getDataAsRecordList().get(
							i);
					if (element != null) {
						Map<String, String> item = new TreeMap<String, String>();
						item.put("staff_phone_type_id",
								element.getAttribute("staff_phone_type_id"));
						item.put("staff_phone",
								element.getAttribute("staff_phone"));
						preStaffPhones.put(
								element.getAttributeAsString("staff_phone_id"),
								item);
					}
				}
			}

			Map<String, Map<String, Object>> preStaffWorks = new TreeMap<String, Map<String, Object>>();

			if (staffWorksGrid.getDataAsRecordList() != null) {
				for (int i = 0; i < staffWorksGrid.getDataAsRecordList()
						.getLength(); i++) {
					Record element = staffWorksGrid.getDataAsRecordList()
							.get(i);
					if (element != null) {
						Map<String, Object> item = new LinkedHashMap<String, Object>();
						item.put("work_place",
								element.getAttributeAsString("work_place"));
						item.put("work_position",
								element.getAttributeAsString("work_position"));
						item.put("work_start_date",
								element.getAttributeAsDate("work_start_date"));
						item.put("work_end_date",
								element.getAttributeAsDate("work_end_date"));

						preStaffWorks.put(
								element.getAttributeAsString("staff_work_id"),
								item);
					}
				}
			}

			Map<String, Map<String, String>> preStaffRaltive09 = new TreeMap<String, Map<String, String>>();

			if (staffRelative09Grid.getDataAsRecordList() != null) {
				for (int i = 0; i < staffRelative09Grid.getDataAsRecordList()
						.getLength(); i++) {
					Record element = staffRelative09Grid.getDataAsRecordList()
							.get(i);
					if (element != null) {
						Map<String, String> item = new TreeMap<String, String>();
						item.put("first_name",
								element.getAttribute("first_name"));
						item.put("last_name", element.getAttribute("last_name"));
						item.put("position", element.getAttribute("position"));
						preStaffRaltive09.put(element
								.getAttributeAsString("staff_relative_09_id"),
								item);
					}
				}
			}

			Map<String, Map<String, String>> preStaffFamousPeople = new TreeMap<String, Map<String, String>>();

			if (staffFamousPeopleGrid.getDataAsRecordList() != null) {
				for (int i = 0; i < staffFamousPeopleGrid.getDataAsRecordList()
						.getLength(); i++) {
					Record element = staffFamousPeopleGrid
							.getDataAsRecordList().get(i);
					if (element != null) {
						Map<String, String> item = new TreeMap<String, String>();
						item.put("first_name",
								element.getAttribute("first_name"));
						item.put("last_name", element.getAttribute("last_name"));
						item.put("relation_type_id",
								element.getAttribute("relation_type_id"));
						item.put("sphere_of_activity_id",
								element.getAttribute("sphere_of_activity_id"));
						preStaffFamousPeople
								.put(element
										.getAttributeAsString("staff_famous_people_id"),
										item);
					}
				}
			}

			// Map<?, ?> asd = physicalAddress.getValues();

			if (listGridRecord != null) {
				record.setAttribute("staff_id",
						listGridRecord.getAttributeAsInt("staff_id"));
				if (listGridRecord.getAttributeAsInt("address_id") != null) {
					record.setAttribute("address_id",
							listGridRecord.getAttributeAsInt("address_id"));
				}
				if (listGridRecord.getAttributeAsInt("document_address_id") != null) {
					record.setAttribute("document_address_id", listGridRecord
							.getAttributeAsInt("document_address_id"));
				}

			}

			record.setAttribute("physicalAddress", physicalAddress.getValues());
			record.setAttribute("legalAddress", legalAddress.getValues());

			record.setAttribute("first_name", firstName);
			record.setAttribute("last_name", lastName);
			record.setAttribute("department_id",
					departmentItem.getValueAsString());
			record.setAttribute("doc_num", docNumberItem.getValueAsString());
			record.setAttribute("gender_id", genderItem.getValueAsString());
			record.setAttribute("nationality_id",
					nationalityItem.getValueAsString());
			record.setAttribute("family_status_id",
					familyStatusItem.getValueAsString());
			record.setAttribute("start_date", startDateItem.getValueAsDate());
			record.setAttribute("dob", dobItem.getValueAsDate());
			record.setAttribute("remark", remarkItem.getValueAsString());
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("preStaffEducation", preStaffEducation);
			record.setAttribute("preStaffCompSkills", preStaffCompSkills);
			record.setAttribute("preStaffLanguages", preStaffLanguages);
			record.setAttribute("preStaffPhones", preStaffPhones);
			record.setAttribute("preStaffWorks", preStaffWorks);
			record.setAttribute("preStaffRaltive09", preStaffRaltive09);
			record.setAttribute("preStaffFamousPeople", preStaffFamousPeople);

			DSRequest req = new DSRequest();
			if (listGridRecord == null) {
				req.setAttribute("operationId", "addOrUpdate");
				dataSource.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "addOrUpdate");
				dataSource.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			}
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
