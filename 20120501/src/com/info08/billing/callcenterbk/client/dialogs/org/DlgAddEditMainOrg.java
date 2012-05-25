package com.info08.billing.callcenterbk.client.dialogs.org;

import java.util.ArrayList;
import java.util.Map;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyAddressPanel;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItem;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxRecord;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tree.TreeGrid;

public class DlgAddEditMainOrg extends Window {

	// main layout
	private VLayout hLayout;

	// forms
	// org. main info
	private DynamicForm dynamicFormMainInfo;
	private DynamicForm dynamicFormMainInfo1;

	private MyComboBoxItem parrentOrgItem;

	// org. address
	private MyAddressPanel legalAddress;
	private MyAddressPanel physicalAddress;

	private TabSet topTabSet;

	// main info fields.
	private TextAreaItem orgNameItem;
	private TextAreaItem orgNameEngItem;
	private TextAreaItem orgNoteItem;
	private TextItem orgDirectorItem;
	private TextItem orgContactPersonItem;
	private TextItem orgIdentCodeItem;
	private TextItem orgNewIdentCodeItem;
	private TextItem orgWorkHoursItem;
	private TextItem orgWebAddressItem;
	private TextItem orgSocialAddressItem;
	private TextItem orgMailItem;
	private TextItem orgIndItem;
	private TextItem orgWorkPersQuantItem;
	private TextAreaItem orgInfoItem;
	private ComboBoxItem orgLegalStatusItem;
	private DateItem orgFoundedItem;
	private SelectItem extraPriorityItem;
	private CheckboxItem noteCritItem;
	private SelectItem orgStatusItem;

	// address fields.

	private TreeGrid orgTreeGrid;
	private ListGridRecord listGridRecord;

	private ListGrid dayOffsGrid;
	private ListGrid transpDayOffsGrid;

	private ListGrid activityGrid;
	private ListGrid orgActivityGrid;
	protected MainOrgActClientDS mainOrgActClientDS;

	private ListGrid bankOrgsGrid;
	private ListGrid orgPartBankOrgsGrid;

	public DlgAddEditMainOrg(ListGridRecord listGridRecord, TreeGrid orgTreeGrid) {
		try {
			this.orgTreeGrid = orgTreeGrid;
			this.listGridRecord = listGridRecord;
			setTitle(CallCenterBK.constants.manageOrgs());

			setHeight(760);
			setWidth(1270);
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
			dynamicFormMainInfo.setNumCols(1);
			dynamicFormMainInfo.setTitleOrientation(TitleOrientation.TOP);

			dynamicFormMainInfo1 = new DynamicForm();
			dynamicFormMainInfo1.setAutoFocus(false);
			dynamicFormMainInfo1.setWidth100();
			dynamicFormMainInfo1.setNumCols(2);
			dynamicFormMainInfo1.setTitleOrientation(TitleOrientation.TOP);

			HLayout formsLayout = new HLayout();
			formsLayout.setWidth100();
			formsLayout.setHeight100();

			VLayout leftLayOut = new VLayout();
			leftLayOut.setWidth(650);
			leftLayOut.setHeight100();

			VLayout rigthLayOut = new VLayout();
			rigthLayOut.setHeight100();
			rigthLayOut.addMember(dynamicFormMainInfo1);

			formsLayout.setMembers(leftLayOut, rigthLayOut);
			Tab tabMainInfo = new Tab(CallCenterBK.constants.maininfo());
			tabMainInfo.setPane(formsLayout);
			topTabSet.addTab(tabMainInfo);

			VLayout formsLayoutAddInfo = new VLayout();
			formsLayoutAddInfo.setWidth100();
			formsLayoutAddInfo.setHeight100();
			formsLayoutAddInfo.setPadding(0);
			formsLayoutAddInfo.setMargin(0);

			HLayout hLayoutForAddresses = new HLayout();
			hLayoutForAddresses.setWidth100();
			hLayoutForAddresses.setPadding(0);
			hLayoutForAddresses.setMargin(0);

			legalAddress = new MyAddressPanel("LegalAddress",CallCenterBK.constants.orgAddressHeaderLegal(), 600, 400);
			physicalAddress = new MyAddressPanel("PhysicalAddress",CallCenterBK.constants.orgAddressHeaderReal(), 600, 400);
			
			hLayoutForAddresses.setMembers(physicalAddress, legalAddress);
			formsLayoutAddInfo.addMember(hLayoutForAddresses);

			Tab tabAddInfo = new Tab(CallCenterBK.constants.addInfo());
			tabAddInfo.setPane(formsLayoutAddInfo);
			topTabSet.addTab(tabAddInfo);

			hLayout.addMember(topTabSet);

			parrentOrgItem = new MyComboBoxItem("parrent_organization_name", CallCenterBK.constants.parrentOrgName(), 0, 650);
			parrentOrgItem.setMyDlgHeight(400);
			parrentOrgItem.setMyDlgWidth(900);
			DataSource orgDS = DataSource.get("OrgDS");
			parrentOrgItem.setMyDataSource(orgDS);
			parrentOrgItem.setMyDataSourceOperation("searchMainOrgsForCBDoubleLike");
			parrentOrgItem.setMyIdField("organization_id");

			ArrayList<MyComboBoxRecord> fieldRecords = new ArrayList<MyComboBoxRecord>();
			MyComboBoxRecord organization_name = new MyComboBoxRecord("organization_name",CallCenterBK.constants.parrentOrgName(), true);
			MyComboBoxRecord remark = new MyComboBoxRecord("remark",CallCenterBK.constants.comment(), false);
			MyComboBoxRecord full_address_not_hidden = new MyComboBoxRecord("full_address_not_hidden",CallCenterBK.constants.address(), true);

			fieldRecords.add(organization_name);
			fieldRecords.add(full_address_not_hidden);
			fieldRecords.add(remark);

			parrentOrgItem.setMyFields(fieldRecords);
			parrentOrgItem.setMyChooserTitle(CallCenterBK.constants.organization());

			leftLayOut.setMembers(parrentOrgItem, dynamicFormMainInfo);

			orgNameItem = new TextAreaItem();
			orgNameItem.setName("organization_name");
			orgNameItem.setWidth(650);
			orgNameItem.setTitle(CallCenterBK.constants.orgName());
			orgNameItem.setHeight(62);

			orgNameEngItem = new TextAreaItem();
			orgNameEngItem.setName("organization_name_eng");
			orgNameEngItem.setWidth(650);
			orgNameEngItem.setTitle(CallCenterBK.constants.orgNameEng());
			orgNameEngItem.setHeight(62);

			orgNoteItem = new TextAreaItem();
			orgNoteItem.setName("remark");
			orgNoteItem.setWidth(650);
			orgNoteItem.setTitle(CallCenterBK.constants.comment());
			orgNoteItem.setHeight(142);

			orgDirectorItem = new TextItem();
			orgDirectorItem.setName("chief");
			orgDirectorItem.setWidth(284);
			orgDirectorItem.setTitle(CallCenterBK.constants.director());

			orgContactPersonItem = new TextItem();
			orgContactPersonItem.setName("contact_person");
			orgContactPersonItem.setWidth(284);
			orgContactPersonItem.setTitle(CallCenterBK.constants
					.contactPerson());

			orgIdentCodeItem = new TextItem();
			orgIdentCodeItem.setName("ident_code");
			orgIdentCodeItem.setWidth(284);
			orgIdentCodeItem.setTitle(CallCenterBK.constants.identCode());

			orgNewIdentCodeItem = new TextItem();
			orgNewIdentCodeItem.setName("ident_code_new");
			orgNewIdentCodeItem.setWidth(284);
			orgNewIdentCodeItem.setTitle(CallCenterBK.constants.identCodeNew());

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
			orgSocialAddressItem.setTitle(CallCenterBK.constants
					.socialAddress());

			orgMailItem = new TextItem();
			orgMailItem.setName("email_address");
			orgMailItem.setWidth(284);
			orgMailItem.setTitle(CallCenterBK.constants.eMail());

			orgIndItem = new TextItem();
			orgIndItem.setName("organization_index");
			orgIndItem.setWidth(284);
			orgIndItem.setTitle(CallCenterBK.constants.postIndex());

			orgWorkPersQuantItem = new TextItem();
			orgWorkPersQuantItem.setName("staff_count");
			orgWorkPersQuantItem.setWidth(284);
			orgWorkPersQuantItem.setTitle(CallCenterBK.constants
					.workPersonCountity());

			orgInfoItem = new TextAreaItem();
			orgInfoItem.setName("additional_info");
			orgInfoItem.setWidth(568);
			orgInfoItem.setHeight(102);
			orgInfoItem.setColSpan(2);
			orgInfoItem.setTitle(CallCenterBK.constants.orgInfo());

			orgLegalStatusItem = new ComboBoxItem();
			orgLegalStatusItem.setTitle(CallCenterBK.constants.legalStatuse());
			orgLegalStatusItem.setName("legal_form_desc_id");
			orgLegalStatusItem.setWidth(284);
			orgLegalStatusItem.setFetchMissingValues(true);
			orgLegalStatusItem.setFilterLocally(false);
			orgLegalStatusItem.setAddUnknownValues(false);

			DataSource orgLegalStatusDS = DataSource.get("OrgLegalStatusDS");
			orgLegalStatusItem
					.setOptionOperationId("searchAllLegalStatusesForCB");
			orgLegalStatusItem.setOptionDataSource(orgLegalStatusDS);
			orgLegalStatusItem.setValueField("legal_statuse_id");
			orgLegalStatusItem.setDisplayField("legal_statuse");

			orgLegalStatusItem.setOptionCriteria(new Criteria());
			orgLegalStatusItem.setAutoFetchData(false);

			orgLegalStatusItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = orgLegalStatusItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("legal_statuse_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("legal_statuse_id", nullO);
						}
					}
				}
			});

			orgFoundedItem = new DateItem();
			orgFoundedItem.setTitle(CallCenterBK.constants.founded());
			orgFoundedItem.setWidth(284);
			orgFoundedItem.setName("found_date");
			orgFoundedItem.setUseTextField(true);

			extraPriorityItem = new SelectItem();
			extraPriorityItem.setTitle(CallCenterBK.constants.extraPriority());
			extraPriorityItem.setWidth(284);
			extraPriorityItem.setName("extraPriority");
			extraPriorityItem.setDefaultToFirstOption(true);
			extraPriorityItem.setValueMap(ClientMapUtil.getInstance()
					.getOrgNoteCrits());
			boolean hasPermission = CommonSingleton.getInstance()
					.hasPermission("105550");
			extraPriorityItem.setDisabled(!hasPermission);

			noteCritItem = new CheckboxItem();
			noteCritItem.setTitle(CallCenterBK.constants.noteCrit());
			noteCritItem.setName("note_crit");
			noteCritItem.setWidth(650);
			noteCritItem.setHeight(23);

			orgStatusItem = new SelectItem();
			orgStatusItem.setTitle(CallCenterBK.constants.status());
			orgStatusItem.setName("response_type");
			orgStatusItem.setWidth(284);
			orgStatusItem.setValueMap(ClientMapUtil.getInstance()
					.getOrgStatuses());
			orgStatusItem.setDefaultToFirstOption(true);

			dynamicFormMainInfo.setFields(orgNameItem, orgNameEngItem,
					noteCritItem, orgNoteItem);

			dynamicFormMainInfo1.setFields(orgDirectorItem,
					orgContactPersonItem, orgIdentCodeItem,
					orgNewIdentCodeItem, orgWorkHoursItem,
					orgWorkPersQuantItem, orgLegalStatusItem,
					extraPriorityItem, orgStatusItem, orgFoundedItem,
					orgIndItem, orgSocialAddressItem, orgWebAddressItem,
					orgMailItem, orgInfoItem);

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

			mainOrgActClientDS = MainOrgActClientDS.getInstance();

			orgActivityGrid = new ListGrid();
			orgActivityGrid.setWidth(305);
			orgActivityGrid.setHeight100();
			// orgActivityGrid.setDataSource(mainOrgActClientDS);
			orgActivityGrid.setCanAcceptDroppedRecords(true);
			orgActivityGrid.setCanRemoveRecords(true);
			// orgActivityGrid.setAutoFetchData(true);
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
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
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

			leftLayOut.addMember(gridsLayout);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
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
			transpDayOffsGrid = new ListGrid();
			transpDayOffsGrid.setWidth(267);
			transpDayOffsGrid.setHeight100();
			transpDayOffsGrid.setDataSource(orgDayOffsClientDS);
			transpDayOffsGrid.setCanAcceptDroppedRecords(true);
			transpDayOffsGrid.setCanRemoveRecords(true);
			transpDayOffsGrid.setAutoFetchData(true);
			transpDayOffsGrid.setPreventDuplicates(true);
			transpDayOffsGrid.setDuplicateDragMessage("ასეთი უკვე არჩეულია !");

			Img arrowImg1 = new Img("arrow_right.png", 32, 32);
			arrowImg1.setLayoutAlign(Alignment.CENTER);
			arrowImg1.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					transpDayOffsGrid.transferSelectedData(dayOffsGrid);
				}
			});
			dayOffsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							transpDayOffsGrid.transferSelectedData(dayOffsGrid);
						}
					});

			HLayout gridsLayout1 = new HLayout(0);
			gridsLayout1.setMargin(2);

			gridsLayout1.setMembers(dayOffsGrid, arrowImg1, transpDayOffsGrid);
			rigthLayOut.addMember(gridsLayout1);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});
			saveItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					save();
				}
			});

			DataSource orgDSForBanks = DataSource.get("OrgDS");
			bankOrgsGrid = new ListGrid();
			bankOrgsGrid.setWidth(600);
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
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							orgPartBankOrgsGrid
									.transferSelectedData(bankOrgsGrid);
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
			orgPartBankOrgsGrid.setWidth(600);
			orgPartBankOrgsGrid.setHeight100();
			orgPartBankOrgsGrid.setDataSource(orgActDS);
			orgPartBankOrgsGrid.setPreventDuplicates(true);
			orgPartBankOrgsGrid
					.setDuplicateDragMessage("ასეთი უკვე არჩეულია !");
			orgPartBankOrgsGrid.setCanAcceptDroppedRecords(true);
			orgPartBankOrgsGrid.setAlternateRecordStyles(true);
			orgPartBankOrgsGrid.setAutoFetchData(false);
			orgPartBankOrgsGrid.setDataSource(orgPartnetBanksClientDS);
			orgPartBankOrgsGrid.setFetchOperation("searchOrgPartnerBanks");
			orgPartBankOrgsGrid.setWrapCells(true);
			orgPartBankOrgsGrid.setFixedRecordHeights(false);
			orgPartBankOrgsGrid.setCanRemoveRecords(true);

			HLayout hLayoutPartBanks = new HLayout();
			hLayoutPartBanks.setWidth100();
			hLayoutPartBanks.setMembersMargin(5);

			hLayoutPartBanks.setMembers(bankOrgsGrid, orgPartBankOrgsGrid);
			formsLayoutAddInfo.addMember(hLayoutPartBanks);

			fillFields();
			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (listGridRecord != null) {
				Map<?,?> map = listGridRecord.toMap();
				parrentOrgItem.setSelectedRecord(listGridRecord);
				dynamicFormMainInfo.setValues(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			record.setAttribute("organization_id",
					listGridRecord.getAttributeAsInt("organization_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateMainOrgStatus");
			orgTreeGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					destroy();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
