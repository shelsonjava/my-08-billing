package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.ArrayList;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyAddressPanel;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxRecord;
import com.info08.billing.callcenterbk.client.dialogs.org.DayOffsClientDS;
import com.info08.billing.callcenterbk.client.dialogs.org.MainOrgActClientDS;
import com.info08.billing.callcenterbk.client.dialogs.org.OrgDayOffsClientDS;
import com.info08.billing.callcenterbk.client.dialogs.org.OrgPartnetBanksClientDS;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
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
	private DynamicForm dynamicFormMainInfo;
	private DynamicForm dynamicFormMainInfo1;
	private DynamicForm dynamicFormMainInfo2;

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

	private TextAreaItem orgRemarkItem;
	private TextItem orgChiefItem;
	private TextItem orgContactPersonItem;
	private TextItem orgIdentCodeItem;
	private TextItem orgIdentCodeNewItem;

	private TextItem orgWorkHoursItem;
	private TextItem orgWebAddressItem;
	private TextItem orgSocialAddressItem;
	private TextItem orgMailItem;
	private TextItem orgIndItem;
	private TextItem orgStaffCountItem;
	private TextAreaItem orgInfoItem;
	private ComboBoxItem orgLegalStatusItem;

	private SelectItem superPriorityItem;
	private CheckboxItem importantRemark;
	private SelectItem orgStatusItem;
	private DataSource dataSource;

	private IButton saveItem;
	private int selectedTabIndex = 0;

	// private TreeGrid orgTreeGrid;
	private ListGridRecord listGridRecord;
	private ListGrid dayOffsGrid;
	private ListGrid orgDayOffsGrid;
	private ListGrid activityGrid;
	private ListGrid orgActivityGrid;
	private ListGrid bankOrgsGrid;
	private ListGrid orgPartBankOrgsGrid;
	private ToolStripButton copyAddress;

	private StaffEducationClientDS staffEducationClientDS;
	private ListGrid staffEducationGrid;

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

		dynamicFormMainInfo = new DynamicForm();
		dynamicFormMainInfo.setAutoFocus(true);
		dynamicFormMainInfo.setWidth100();
		dynamicFormMainInfo.setNumCols(2);
		dynamicFormMainInfo.setTitleWidth(150);
		dynamicFormMainInfo.setTitleOrientation(TitleOrientation.TOP);

		dynamicFormMainInfo1 = new DynamicForm();
		dynamicFormMainInfo1.setAutoFocus(false);
		dynamicFormMainInfo1.setWidth100();
		dynamicFormMainInfo1.setNumCols(2);
		dynamicFormMainInfo1.setTitleOrientation(TitleOrientation.TOP);

		dynamicFormMainInfo2 = new DynamicForm();
		dynamicFormMainInfo2.setAutoFocus(false);
		dynamicFormMainInfo2.setWidth100();
		dynamicFormMainInfo2.setNumCols(2);
		dynamicFormMainInfo2.setTitleOrientation(TitleOrientation.TOP);

		HLayout formsLayout = new HLayout();
		formsLayout.setWidth100();
		formsLayout.setHeight100();

		VLayout leftLayOut = new VLayout();
		leftLayOut.setWidth(650);
		leftLayOut.setHeight100();

		VLayout rigthLayOut = new VLayout();
		rigthLayOut.setHeight100();
		// rigthLayOut.addMember(dynamicFormMainInfo1);

		formsLayout.setMembers(leftLayOut, rigthLayOut);
		Tab tabMainInfo = new Tab(CallCenterBK.constants.maininfo());
		tabMainInfo.setPane(formsLayout);
		topTabSet.addTab(tabMainInfo);

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

		physicalAddress = new MyAddressPanel("PhysicalAddress",
				CallCenterBK.constants.orgAddressHeaderReal(), 614, 0);

		legalAddress = new MyAddressPanel("LegalAddress",
				CallCenterBK.constants.orgAddressHeaderLegal(), 614, 0);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth(1228);
		toolStrip.setPadding(5);

		copyAddress = new ToolStripButton(CallCenterBK.constants.sameAddress(),
				"copy.png");
		copyAddress.setLayoutAlign(Alignment.LEFT);
		copyAddress.setWidth(50);
		toolStrip.addButton(copyAddress);

		hLayoutForAddresses.setMembers(physicalAddress, legalAddress);

		// formsLayoutAddInfo.addMember(toolStrip);
		// formsLayoutAddInfo.addMember(hLayoutForAddresses);
		// formsLayoutAddInfo.addMember(dynamicFormMainInfo2);

		Tab tabAddInfo = new Tab(CallCenterBK.constants.addInfo());
		tabAddInfo.setPane(formsLayoutAddInfo);
		topTabSet.addTab(tabAddInfo);

		hLayout.addMember(topTabSet);

		ArrayList<MyComboBoxRecord> fieldRecords = new ArrayList<MyComboBoxRecord>();
		MyComboBoxRecord organization_name = new MyComboBoxRecord(
				"organization_name", CallCenterBK.constants.parrentOrgName(),
				true);
		MyComboBoxRecord remark = new MyComboBoxRecord("remark",
				CallCenterBK.constants.comment(), false);
		MyComboBoxRecord full_address_not_hidden = new MyComboBoxRecord(
				"full_address_not_hidden", CallCenterBK.constants.address(),
				true);

		fieldRecords.add(organization_name);
		fieldRecords.add(full_address_not_hidden);
		fieldRecords.add(remark);

		

		firstNameItem = new TextItem();
		firstNameItem.setName("first_name");
		firstNameItem.setWidth(250);
		firstNameItem.setTitle("სახელი");

		lastNameItem = new TextItem();
		lastNameItem.setName("last_name");
		lastNameItem.setWidth(250);
		lastNameItem.setTitle("გვარი");

		departmentItem = new ComboBoxItem();
		departmentItem.setTitle("განყოფილება");
		departmentItem.setName("department_id");
		departmentItem.setWidth(250);
		departmentItem.setFetchMissingValues(false);

		ClientUtils.fillCombo(departmentItem, "StaffDS", "getAllDepartments",
				"department_id", "department_name");

		genderItem = new SelectItem();
		genderItem.setTitle("სქესი");
		genderItem.setName("genderItem");
		genderItem.setWidth(250);
		genderItem.setFetchMissingValues(false);

		ClientUtils.fillDescriptionCombo(genderItem, 64000);

		nationalityItem = new SelectItem();
		nationalityItem.setTitle("ეროვნება");
		nationalityItem.setName("nationalityItem");
		nationalityItem.setWidth(250);
		nationalityItem.setFetchMissingValues(false);

		ClientUtils.fillDescriptionCombo(nationalityItem, 65000);

		familyStatusItem = new SelectItem();
		familyStatusItem.setTitle("ოჯახური მდგომარეობა");
		familyStatusItem.setName("familyStatusItem");
		familyStatusItem.setWidth(250);
		familyStatusItem.setFetchMissingValues(false);

		ClientUtils.fillDescriptionCombo(familyStatusItem, 66000);

		docNumberItem = new TextItem();
		docNumberItem.setTitle("პირადობა");
		docNumberItem.setWidth(250);
		docNumberItem.setName("doc_num");

		remarkItem = new TextAreaItem();
		remarkItem.setTitle("კომენტარი");
		remarkItem.setWidth(650);
		remarkItem.setColSpan(4);
		remarkItem.setName("remark");
		remarkItem.setHeight(70);

		dobItem = new DateItem();
		dobItem.setTitle("დაბადების თარიღი");
		dobItem.setWidth(250);
		dobItem.setName("dobItem");
		dobItem.setUseTextField(true);

		startDateItem = new DateItem();
		startDateItem.setTitle("დაწყების თარიღი");
		startDateItem.setWidth(250);
		startDateItem.setName("startDateItem");
		startDateItem.setUseTextField(true);

		staffEducationClientDS = StaffEducationClientDS.getInstance();

		staffEducationGrid = new ListGrid();
		staffEducationGrid.setWidth100();
		staffEducationGrid.setHeight(250);
		staffEducationGrid.setDataSource(staffEducationClientDS);
		staffEducationGrid.setCanReorderRecords(true);
		staffEducationGrid.setCanRemoveRecords(true);
		staffEducationGrid.setAutoFetchData(true);
		staffEducationGrid.setCanEdit(true);
		staffEducationGrid
				.setDuplicateDragMessage("ასეთი რაიონი უკვე არჩეულია !");

		
		
		
		
		
		
		
		leftLayOut.setMembers(dynamicFormMainInfo, staffEducationGrid); // TODO
		
		
		orgRemarkItem = new TextAreaItem();
		orgRemarkItem.setName("remark");
		orgRemarkItem.setWidth(650);
		orgRemarkItem.setTitle(CallCenterBK.constants.comment());
		orgRemarkItem.setHeight(142);

		orgChiefItem = new TextItem();
		orgChiefItem.setName("chief");
		orgChiefItem.setWidth(284);
		orgChiefItem.setTitle(CallCenterBK.constants.director());

		orgContactPersonItem = new TextItem();
		orgContactPersonItem.setName("contact_person");
		orgContactPersonItem.setWidth(284);
		orgContactPersonItem.setTitle(CallCenterBK.constants.contactPerson());

		orgIdentCodeItem = new TextItem();
		orgIdentCodeItem.setName("ident_code");
		orgIdentCodeItem.setWidth(284);
		orgIdentCodeItem.setTitle(CallCenterBK.constants.identCode());
		orgIdentCodeItem.setKeyPressFilter("[0-9]");

		orgIdentCodeNewItem = new TextItem();
		orgIdentCodeNewItem.setName("ident_code_new");
		orgIdentCodeNewItem.setWidth(284);
		orgIdentCodeNewItem.setTitle(CallCenterBK.constants.identCodeNew());
		orgIdentCodeNewItem.setKeyPressFilter("[0-9]");

		orgWorkHoursItem = new TextItem();
		orgWorkHoursItem.setName("work_hours");
		orgWorkHoursItem.setWidth(284);
		orgWorkHoursItem.setTitle(CallCenterBK.constants.workinghours());

		orgWebAddressItem = new TextItem();
		orgWebAddressItem.setName("web_address");
		orgWebAddressItem.setWidth(284);
		orgWebAddressItem.setTitle(CallCenterBK.constants.webaddress());

		orgSocialAddressItem = new TextItem();
		orgSocialAddressItem.setName("social_address");
		orgSocialAddressItem.setWidth(284);
		orgSocialAddressItem.setTitle(CallCenterBK.constants.socialAddress());

		orgMailItem = new TextItem();
		orgMailItem.setName("email_address");
		orgMailItem.setWidth(284);
		orgMailItem.setTitle(CallCenterBK.constants.eMail());

		orgIndItem = new TextItem();
		orgIndItem.setName("organization_index");
		orgIndItem.setWidth(614);
		orgIndItem.setTitle(CallCenterBK.constants.postIndex());
		orgIndItem.setKeyPressFilter("[0-9]");

		orgStaffCountItem = new TextItem();
		orgStaffCountItem.setName("staff_count");
		orgStaffCountItem.setWidth(284);
		orgStaffCountItem.setTitle(CallCenterBK.constants.workPersonCountity());
		orgStaffCountItem.setKeyPressFilter("[0-9]");

		orgInfoItem = new TextAreaItem();
		orgInfoItem.setName("additional_info");
		orgInfoItem.setWidth(568);
		orgInfoItem.setHeight(142);
		orgInfoItem.setColSpan(2);
		orgInfoItem.setTitle(CallCenterBK.constants.orgInfo());

		orgLegalStatusItem = new ComboBoxItem();
		orgLegalStatusItem.setTitle(CallCenterBK.constants.legalStatuse());
		orgLegalStatusItem.setName("legal_form_desc_id");
		orgLegalStatusItem.setWidth(284);
		ClientUtils
				.fillDescriptionCombo(orgLegalStatusItem, new Integer(51000));

		superPriorityItem = new SelectItem();
		superPriorityItem.setTitle(CallCenterBK.constants.extraPriority());
		superPriorityItem.setWidth(614);
		superPriorityItem.setName("super_priority");
		superPriorityItem.setDefaultToFirstOption(true);
		superPriorityItem.setValueMap(ClientMapUtil.getInstance()
				.getOrgNoteCrits());
		// boolean hasPermission = CommonSingleton.getInstance().hasPermission(
		// "105550");
		// superPriorityItem.setDisabled(!hasPermission);

		importantRemark = new CheckboxItem();
		importantRemark.setTitle(CallCenterBK.constants.noteCrit());
		importantRemark.setName("important_remark");
		importantRemark.setWidth(650);
		importantRemark.setHeight(23);

		orgStatusItem = new SelectItem();
		orgStatusItem.setTitle(CallCenterBK.constants.status());
		orgStatusItem.setName("status");
		orgStatusItem.setWidth(284);
		orgStatusItem.setValueMap(ClientMapUtil.getInstance().getOrgStatuses());
		orgStatusItem.setDefaultToFirstOption(true);

		// dynamicFormMainInfo.setFields(orgNameItem, orgNameEngItem,
		// importantRemark, orgRemarkItem);

		dynamicFormMainInfo.setFields(firstNameItem, lastNameItem,
				departmentItem, docNumberItem, genderItem, nationalityItem,
				familyStatusItem, dobItem, startDateItem, remarkItem); // TODO

		dynamicFormMainInfo1.setFields(orgChiefItem, orgContactPersonItem,
				orgIdentCodeItem, orgIdentCodeNewItem, orgWorkHoursItem,
				orgStaffCountItem, orgLegalStatusItem, orgStatusItem, dobItem,
				orgSocialAddressItem, orgWebAddressItem, orgMailItem,
				orgInfoItem);

		dynamicFormMainInfo2.setFields(orgIndItem, superPriorityItem);

		DataSource orgActDS = DataSource.get("OrgActDS");

		activityGrid = new ListGrid();
		activityGrid.setWidth(305);
		activityGrid.setHeight100();
		activityGrid.setDataSource(orgActDS);
		activityGrid.setShowFilterEditor(true);
		activityGrid.setFilterOnKeypress(true);
		activityGrid.setCanDragRecordsOut(true);
		activityGrid.setDragDataAction(DragDataAction.COPY);
		activityGrid.setAlternateRecordStyles(true);
		activityGrid.setAutoFetchData(true);
		activityGrid.setFetchOperation("searchAllBusinesActivitiesForCB");

		ListGridField activity = new ListGridField("activity_description",
				CallCenterBK.constants.activity());
		activity.setAlign(Alignment.LEFT);

		activityGrid.setFields(activity);

		MainOrgActClientDS mainOrgActClientDS = MainOrgActClientDS
				.getInstance();

		orgActivityGrid = new ListGrid();
		orgActivityGrid.setWidth(305);
		orgActivityGrid.setHeight100();
		orgActivityGrid.setDataSource(mainOrgActClientDS);
		orgActivityGrid.setCanAcceptDroppedRecords(true);
		orgActivityGrid.setCanRemoveRecords(true);
		orgActivityGrid.setAutoFetchData(true);
		orgActivityGrid.setPreventDuplicates(true);
		orgActivityGrid.setDuplicateDragMessage(CallCenterBK.constants
				.thisOrgActAlreadyChoosen());

		Img arrowImg = new Img("arrow_right.png", 32, 32);
		arrowImg.setLayoutAlign(Alignment.CENTER);
		arrowImg.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				orgActivityGrid.transferSelectedData(activityGrid);
			}
		});
		activityGrid
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						orgActivityGrid.transferSelectedData(activityGrid);
					}
				});

		HLayout gridsLayout = new HLayout();
		gridsLayout.setMargin(2);
		gridsLayout.setHeight100();

		HLayout hLayoutImg = new HLayout();
		hLayoutImg.setHeight100();
		hLayoutImg.setWidth(36);
		hLayoutImg.setAlign(Alignment.CENTER);
		hLayoutImg.addMember(arrowImg);

		gridsLayout.setMembers(activityGrid, hLayoutImg, orgActivityGrid);

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

		DayOffsClientDS dayOffsClientDS = DayOffsClientDS.getInstance();

		dayOffsGrid = new ListGrid();
		dayOffsGrid.setWidth(268);
		dayOffsGrid.setHeight100();
		dayOffsGrid.setCanDragRecordsOut(true);
		dayOffsGrid.setDragDataAction(DragDataAction.COPY);
		dayOffsGrid.setAlternateRecordStyles(true);
		dayOffsGrid.setDataSource(dayOffsClientDS);
		dayOffsGrid.setAutoFetchData(true);

		OrgDayOffsClientDS orgDayOffsClientDS = OrgDayOffsClientDS
				.getInstance();
		orgDayOffsGrid = new ListGrid();
		orgDayOffsGrid.setWidth(267);
		orgDayOffsGrid.setHeight100();
		orgDayOffsGrid.setDataSource(orgDayOffsClientDS);
		orgDayOffsGrid.setCanAcceptDroppedRecords(true);
		orgDayOffsGrid.setCanRemoveRecords(true);
		orgDayOffsGrid.setAutoFetchData(true);
		orgDayOffsGrid.setPreventDuplicates(true);
		orgDayOffsGrid.setDuplicateDragMessage("ასეთი უკვე არჩეულია !");

		Img arrowImg1 = new Img("arrow_right.png", 32, 32);
		arrowImg1.setLayoutAlign(Alignment.CENTER);
		arrowImg1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				orgDayOffsGrid.transferSelectedData(dayOffsGrid);
			}
		});
		dayOffsGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				orgDayOffsGrid.transferSelectedData(dayOffsGrid);
			}
		});

		HLayout gridsLayout1 = new HLayout(0);
		gridsLayout1.setMargin(2);

		gridsLayout1.setMembers(dayOffsGrid, arrowImg1, orgDayOffsGrid);
		// rigthLayOut.addMember(gridsLayout1);

		cancItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});
		saveItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// if (selectedTabIndex == 0) {
				// topTabSet.selectTab(1);
				// } else {
				save();
				// }
			}
		});

		DataSource orgDSForBanks = DataSource.get("OrgDS");
		bankOrgsGrid = new ListGrid();
		bankOrgsGrid.setWidth(612);
		bankOrgsGrid.setHeight100();
		bankOrgsGrid.setDataSource(orgActDS);
		bankOrgsGrid.setShowFilterEditor(true);
		bankOrgsGrid.setFilterOnKeypress(true);
		bankOrgsGrid.setAlternateRecordStyles(true);
		bankOrgsGrid.setAutoFetchData(true);
		bankOrgsGrid.setCanDragRecordsOut(true);
		bankOrgsGrid.setDragDataAction(DragDataAction.COPY);
		bankOrgsGrid.setDataSource(orgDSForBanks);
		bankOrgsGrid.setFetchOperation("searchPartnerBanks");
		bankOrgsGrid.setWrapCells(true);
		bankOrgsGrid.setFixedRecordHeights(false);
		bankOrgsGrid
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						orgPartBankOrgsGrid.transferSelectedData(bankOrgsGrid);
					}
				});

		ListGridField bank_organization_name = new ListGridField(
				"organization_name", CallCenterBK.constants.partnerBank());
		bank_organization_name.setAlign(Alignment.LEFT);

		ListGridField bank_full_address_not_hidden = new ListGridField(
				"full_address_not_hidden", CallCenterBK.constants.address());
		bank_full_address_not_hidden.setAlign(Alignment.LEFT);

		bankOrgsGrid.setFields(bank_organization_name,
				bank_full_address_not_hidden);

		OrgPartnetBanksClientDS orgPartnetBanksClientDS = OrgPartnetBanksClientDS
				.getInstance();
		orgPartBankOrgsGrid = new ListGrid();
		orgPartBankOrgsGrid.setWidth(610);
		orgPartBankOrgsGrid.setHeight100();
		orgPartBankOrgsGrid.setDataSource(orgActDS);
		orgPartBankOrgsGrid.setPreventDuplicates(true);
		orgPartBankOrgsGrid.setDuplicateDragMessage("ასეთი უკვე არჩეულია !");
		orgPartBankOrgsGrid.setCanAcceptDroppedRecords(true);
		orgPartBankOrgsGrid.setAlternateRecordStyles(true);
		orgPartBankOrgsGrid.setAutoFetchData(true);
		orgPartBankOrgsGrid.setDataSource(orgPartnetBanksClientDS);
		orgPartBankOrgsGrid.setWrapCells(true);
		orgPartBankOrgsGrid.setFixedRecordHeights(false);
		orgPartBankOrgsGrid.setCanRemoveRecords(true);

		HLayout hLayoutPartBanks = new HLayout();
		hLayoutPartBanks.setWidth100();
		hLayoutPartBanks.setMembersMargin(5);

		hLayoutPartBanks.setMembers(bankOrgsGrid, orgPartBankOrgsGrid);
		// formsLayoutAddInfo.addMember(hLayoutPartBanks);

		copyAddress.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// copyAddress();
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
					}
				}
			}, dsRequest);

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

			if (listGridRecord != null) {
				record.setAttribute("staff_id",
						listGridRecord.getAttributeAsInt("staff_id"));
			}
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
