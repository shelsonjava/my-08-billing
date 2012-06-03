package com.info08.billing.callcenterbk.client.dialogs.org;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyAddressPanel;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItem;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxRecord;
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
import com.smartgwt.client.widgets.tree.TreeGrid;

public class DlgAddEditOrganization extends Window {

	// main layout
	private VLayout hLayout;

	// forms
	// org. main info
	private DynamicForm dynamicFormMainInfo;
	private DynamicForm dynamicFormMainInfo1;
	private DynamicForm dynamicFormMainInfo2;

	private MyComboBoxItem parrentOrgItem;

	// org. address
	private MyAddressPanel physicalAddress;
	private MyAddressPanel legalAddress;

	private TabSet topTabSet;

	// main info fields.
	private TextAreaItem orgNameItem;
	private TextAreaItem orgNameEngItem;
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
	private DateItem orgFoundedItem;
	private SelectItem superPriorityItem;
	private CheckboxItem importantRemark;
	private SelectItem orgStatusItem;

	private IButton saveItem;
	private int selectedTabIndex = 0;

	private TreeGrid orgTreeGrid;
	private ListGridRecord listGridRecord;
	private ListGrid dayOffsGrid;
	private ListGrid orgDayOffsGrid;
	private ListGrid activityGrid;
	private ListGrid orgActivityGrid;
	private ListGrid bankOrgsGrid;
	private ListGrid orgPartBankOrgsGrid;
	private ToolStripButton copyAddress;

	public DlgAddEditOrganization(ListGridRecord listGridRecord,
			TreeGrid orgTreeGrid) {
		try {
			this.orgTreeGrid = orgTreeGrid;
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
			dynamicFormMainInfo.setNumCols(1);
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
			formsLayoutAddInfo.setMembersMargin(10);

			HLayout hLayoutForAddresses = new HLayout();
			hLayoutForAddresses.setWidth100();
			hLayoutForAddresses.setPadding(0);
			hLayoutForAddresses.setMargin(0);

			physicalAddress = new MyAddressPanel("PhysicalAddress",
					CallCenterBK.constants.orgAddressHeaderReal(), 614, 0);
			physicalAddress.setMyDataSource(DataSource.get("AddressDS"));
			physicalAddress.setMyDataSourceOperation("addressSearch");
			physicalAddress.setMyIdField("addr_id");

			legalAddress = new MyAddressPanel("LegalAddress",
					CallCenterBK.constants.orgAddressHeaderLegal(), 614, 0);
			legalAddress.setMyDataSource(DataSource.get("AddressDS"));
			legalAddress.setMyDataSourceOperation("addressSearch");
			legalAddress.setMyIdField("addr_id");

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(1228);
			toolStrip.setPadding(5);

			copyAddress = new ToolStripButton(
					CallCenterBK.constants.sameAddress(), "copy.png");
			copyAddress.setLayoutAlign(Alignment.LEFT);
			copyAddress.setWidth(50);
			toolStrip.addButton(copyAddress);

			hLayoutForAddresses.setMembers(physicalAddress, legalAddress);

			formsLayoutAddInfo.addMember(toolStrip);
			formsLayoutAddInfo.addMember(hLayoutForAddresses);
			formsLayoutAddInfo.addMember(dynamicFormMainInfo2);

			Tab tabAddInfo = new Tab(CallCenterBK.constants.addInfo());
			tabAddInfo.setPane(formsLayoutAddInfo);
			topTabSet.addTab(tabAddInfo);

			hLayout.addMember(topTabSet);

			parrentOrgItem = new MyComboBoxItem("parrent_organization_name",
					CallCenterBK.constants.parrentOrgName(), 0, 650);
			parrentOrgItem.setNameField("parrent_organization_id1");
			parrentOrgItem.setMyDlgHeight(400);
			parrentOrgItem.setMyDlgWidth(900);
			DataSource orgDS = DataSource.get("OrgDS");
			parrentOrgItem.setMyDataSource(orgDS);
			parrentOrgItem
					.setMyDataSourceOperation("searchMainOrgsForCBDoubleLike");
			parrentOrgItem.setMyIdField("organization_id");

			ArrayList<MyComboBoxRecord> fieldRecords = new ArrayList<MyComboBoxRecord>();
			MyComboBoxRecord organization_name = new MyComboBoxRecord(
					"organization_name",
					CallCenterBK.constants.parrentOrgName(), true);
			MyComboBoxRecord remark = new MyComboBoxRecord("remark",
					CallCenterBK.constants.comment(), false);
			MyComboBoxRecord full_address_not_hidden = new MyComboBoxRecord(
					"full_address_not_hidden",
					CallCenterBK.constants.address(), true);

			fieldRecords.add(organization_name);
			fieldRecords.add(full_address_not_hidden);
			fieldRecords.add(remark);

			parrentOrgItem.setMyFields(fieldRecords);
			parrentOrgItem.setMyChooserTitle(CallCenterBK.constants
					.organization());

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
			orgContactPersonItem.setTitle(CallCenterBK.constants
					.contactPerson());

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
			orgSocialAddressItem.setTitle(CallCenterBK.constants
					.socialAddress());

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
			orgStaffCountItem.setTitle(CallCenterBK.constants
					.workPersonCountity());
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
			ClientUtils.fillDescriptionCombo(orgLegalStatusItem, new Integer(
					51000));

			orgFoundedItem = new DateItem();
			orgFoundedItem.setTitle(CallCenterBK.constants.founded());
			orgFoundedItem.setWidth(284);
			orgFoundedItem.setName("found_date");
			orgFoundedItem.setUseTextField(true);

			superPriorityItem = new SelectItem();
			superPriorityItem.setTitle(CallCenterBK.constants.extraPriority());
			superPriorityItem.setWidth(614);
			superPriorityItem.setName("super_priority");
			superPriorityItem.setDefaultToFirstOption(true);
			superPriorityItem.setValueMap(ClientMapUtil.getInstance()
					.getOrgNoteCrits());
			boolean hasPermission = CommonSingleton.getInstance()
					.hasPermission("105550");
			superPriorityItem.setDisabled(!hasPermission);

			importantRemark = new CheckboxItem();
			importantRemark.setTitle(CallCenterBK.constants.noteCrit());
			importantRemark.setName("important_remark");
			importantRemark.setWidth(650);
			importantRemark.setHeight(23);
			importantRemark.setValue(false);

			orgStatusItem = new SelectItem();
			orgStatusItem.setTitle(CallCenterBK.constants.status());
			orgStatusItem.setName("status");
			orgStatusItem.setWidth(284);
			orgStatusItem.setValueMap(ClientMapUtil.getInstance()
					.getOrgStatuses());
			orgStatusItem.setDefaultToFirstOption(true);

			dynamicFormMainInfo.setFields(orgNameItem, orgNameEngItem,
					importantRemark, orgRemarkItem);

			dynamicFormMainInfo1.setFields(orgChiefItem, orgContactPersonItem,
					orgIdentCodeItem, orgIdentCodeNewItem, orgWorkHoursItem,
					orgStaffCountItem, orgLegalStatusItem, orgStatusItem,
					orgFoundedItem, orgSocialAddressItem, orgWebAddressItem,
					orgMailItem, orgInfoItem);

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
			dayOffsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							orgDayOffsGrid.transferSelectedData(dayOffsGrid);
						}
					});

			HLayout gridsLayout1 = new HLayout(0);
			gridsLayout1.setMargin(2);

			gridsLayout1.setMembers(dayOffsGrid, arrowImg1, orgDayOffsGrid);
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
					if (selectedTabIndex == 0) {
						topTabSet.selectTab(1);
					} else {
						save();
					}
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
			orgPartBankOrgsGrid.setWidth(610);
			orgPartBankOrgsGrid.setHeight100();
			orgPartBankOrgsGrid.setDataSource(orgActDS);
			orgPartBankOrgsGrid.setPreventDuplicates(true);
			orgPartBankOrgsGrid
					.setDuplicateDragMessage("ასეთი უკვე არჩეულია !");
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
			formsLayoutAddInfo.addMember(hLayoutPartBanks);

			copyAddress.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					copyAddress();
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
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (listGridRecord != null) {
				Map<?, ?> map = listGridRecord.toMap();
				Integer important_remark = (Integer) map.get("important_remark");
				map.remove("important_remark");
				parrentOrgItem.setDataValue(map);
				dynamicFormMainInfo.setValues(map);
				dynamicFormMainInfo1.setValues(map);
				dynamicFormMainInfo2.setValues(map);

				if (important_remark != null && important_remark.equals(new Integer(-1))) {
					importantRemark.setValue(true);
				}

				DataSource dataSource = DataSource.get("OrgActDS");
				Criteria criteria = new Criteria();
				criteria.setAttribute("organization_id",
						listGridRecord.getAttributeAsInt("organization_id"));
				DSRequest dsRequest = new DSRequest();
				dsRequest.setOperationId("searchOrgBusinessActivities");
				dataSource.fetchData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record records[] = response.getData();
						if (records != null && records.length > 0) {
							for (Record record : records) {
								orgActivityGrid.addData(record);
							}
						}
					}
				}, dsRequest);

				final Integer dayID = listGridRecord
						.getAttributeAsInt("day_offs");
				if (dayID != null) {
					DayOffsClientDS weekDaysClientDS = DayOffsClientDS
							.getInstance();
					weekDaysClientDS.fetchData(new Criteria(),
							new DSCallback() {
								@Override
								public void execute(DSResponse response,
										Object rawData, DSRequest request) {
									Record records[] = response.getData();
									if (records != null && records.length > 0) {

										if (dayID == 0) {
											orgDayOffsGrid.addData(records[0]);
										} else {
											int dayArray[] = Constants.dayArray;
											for (int i = 1; i < dayArray.length; i++) {
												if ((dayArray[i] & dayID) == dayArray[i]) {
													orgDayOffsGrid
															.addData(records[i]);
												}
											}
										}
									}
								}
							});
				}

				physicalAddress.setValue(listGridRecord
						.getAttributeAsInt("physical_address_id"));
				legalAddress.setValue(listGridRecord
						.getAttributeAsInt("legal_address_id"));

				DataSource orgDS = DataSource.get("OrgDS");
				Criteria criteria1 = new Criteria();
				criteria1.setAttribute("organization_id",
						listGridRecord.getAttributeAsInt("organization_id"));
				DSRequest dsRequest1 = new DSRequest();
				dsRequest1.setOperationId("searchOrgPartnerBanks");
				orgDS.fetchData(criteria1, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record records[] = response.getData();
						if (records != null && records.length > 0) {
							for (Record record : records) {
								orgPartBankOrgsGrid.addData(record);
							}
						}
					}
				}, dsRequest1);

			}

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void copyAddress() {
		try {
			legalAddress.setValues(physicalAddress.getValues());
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			Integer parrent_organization_id = null;
			Record parrentRecord = parrentOrgItem.getSelectedRecord();
			if (parrentRecord != null) {
				parrent_organization_id = parrentRecord
						.getAttributeAsInt("organization_id");
			}
			if (orgNameItem.getValueAsString() == null
					|| orgNameItem.getValueAsString().trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzEnterOrgName());
				topTabSet.selectTab(0);
				dynamicFormMainInfo.focusInItem(orgNameItem);
				return;
			}
			if (orgRemarkItem.getValueAsString() == null
					|| orgRemarkItem.getValueAsString().trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzEnterOrgRemark());
				topTabSet.selectTab(0);
				dynamicFormMainInfo.focusInItem(orgRemarkItem);
				return;
			}
			if (orgChiefItem.getValueAsString() == null
					|| orgChiefItem.getValueAsString().trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzEnterOrgChief());
				topTabSet.selectTab(0);
				dynamicFormMainInfo1.focusInItem(orgChiefItem);
				return;
			}

			String identCode = orgIdentCodeItem.getValueAsString();
			String identCodeNew = orgIdentCodeNewItem.getValueAsString();
			if ((identCode == null && identCodeNew == null)
					|| (identCode != null && identCode.trim().equals("")
							&& identCodeNew != null && identCodeNew.trim()
							.equals(""))) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzEnterOrgIdentCode());
				topTabSet.selectTab(0);
				dynamicFormMainInfo1.focusInItem(orgIdentCodeItem);
				return;
			}

			if (identCode != null && identCode.trim().length() != 9
					&& identCode.trim().length() != 11) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.orgIdentCodeIsInvalid());
				topTabSet.selectTab(0);
				dynamicFormMainInfo1.focusInItem(orgIdentCodeItem);
				return;
			}

			if (identCodeNew != null && identCodeNew.trim().length() != 9
					&& identCodeNew.trim().length() != 11) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.orgIdentCodeIsInvalid());
				topTabSet.selectTab(0);
				dynamicFormMainInfo1.focusInItem(orgIdentCodeNewItem);
				return;
			}

			if (orgWorkHoursItem.getValueAsString() == null
					|| orgWorkHoursItem.getValueAsString().trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzEnterOrgWorkHours());
				topTabSet.selectTab(0);
				dynamicFormMainInfo1.focusInItem(orgWorkHoursItem);
				return;
			}

			ListGridRecord orgActivityList[] = orgActivityGrid.getRecords();
			if (orgActivityList == null || orgActivityList.length <= 0) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzChooseOneOrgActivity());
				return;
			}
			ListGridRecord orgDayOffsList[] = orgDayOffsGrid.getRecords();
			if (orgDayOffsList == null || orgDayOffsList.length <= 0) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzChooseOneOrgDayOff());
				return;
			}

			String orgLegalForm = orgLegalStatusItem.getValueAsString();
			if (orgLegalForm == null || orgLegalForm.trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzChooseOrgLegalForm());
				topTabSet.selectTab(0);
				dynamicFormMainInfo1.focusInItem(orgLegalStatusItem);
				return;
			}

			Map<?, ?> physicalAddrValues = physicalAddress.getValues();
			Map<?, ?> legalAddrValues = physicalAddress.getValues();

			Integer town_id = physicalAddrValues.get("town_id") == null ? null
					: new Integer(physicalAddrValues.get("town_id").toString());
			if (town_id == null) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzChoosePhysicalAddrTown());
				topTabSet.selectTab(1);
				physicalAddress.getDynamicForm().focusInItem(
						physicalAddress.getAddrTownItem());
				return;
			}
			Integer street_id = physicalAddrValues.get("street_id") == null ? null
					: new Integer(physicalAddrValues.get("street_id")
							.toString());
			if (street_id == null) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzChoosePhysicalAddrStreet());
				topTabSet.selectTab(1);
				physicalAddress.getDynamicForm().focusInItem(
						physicalAddress.getAddrStreetItem());
				return;
			}

			Integer legal_town_id = legalAddrValues.get("town_id") == null ? null
					: new Integer(legalAddrValues.get("town_id").toString());
			if (legal_town_id == null) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzChooseLegalAddrTown());
				topTabSet.selectTab(1);
				legalAddress.getDynamicForm().focusInItem(
						legalAddress.getAddrTownItem());
				return;
			}
			Integer legal_street_id = legalAddrValues.get("street_id") == null ? null
					: new Integer(legalAddrValues.get("street_id").toString());
			if (legal_street_id == null) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzChooseLegalAddrStreet());
				topTabSet.selectTab(1);
				legalAddress.getDynamicForm().focusInItem(
						legalAddress.getAddrStreetItem());
				return;
			}
			if (orgIndItem.getValueAsString() == null
					|| orgIndItem.getValueAsString().trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzEnterOrgIndex());
				topTabSet.selectTab(1);
				dynamicFormMainInfo2.focusInItem(orgIndItem);
				return;
			}

			Integer days = 0;
			boolean allDayOffsIsSel = false;
			for (ListGridRecord listGridRecord : orgDayOffsList) {
				Integer day_id = listGridRecord.getAttributeAsInt("day_id");
				if (day_id.intValue() == 0) {
					allDayOffsIsSel = true;
				}
				days += day_id;
			}
			if (allDayOffsIsSel && orgDayOffsList.length > 1) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzChooseValidOrgDayOffs());
				topTabSet.selectTab(0);
				return;
			}
			if (days == 127) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzChooseValidOrgDayOffs1());
				topTabSet.selectTab(0);
				return;
			}
			LinkedHashMap<String, String> orgActivities = new LinkedHashMap<String, String>();
			for (ListGridRecord orgActivity : orgActivityList) {
				String orgActStr = orgActivity.getAttributeAsInt(
						"org_activity_id").toString();
				orgActivities.put("org_activity_id_" + orgActStr, orgActStr);
			}
			boolean bImportant_remark = importantRemark.getValueAsBoolean();
			Integer important_remark1 = null;
			if (bImportant_remark) {
				important_remark1 = -1;
			} else {
				important_remark1 = 0;
			}
			Map<String, Object> fromMaps = new LinkedHashMap<String, Object>();
			fromMaps.put("priority", new Integer(0));
			fromMaps.put("orgActivities", orgActivities);
			fromMaps.put("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			fromMaps.put("day_offs", days);
			fromMaps.put("parrent_organization_id", parrent_organization_id);
			if (listGridRecord != null) {
				fromMaps.put("organization_id",
						listGridRecord.getAttributeAsInt("organization_id"));
				fromMaps.put("legal_address_id",
						listGridRecord.getAttributeAsInt("legal_address_id"));
				fromMaps.put("physical_address_id",
						listGridRecord.getAttributeAsInt("physical_address_id"));
			}
			fromMaps.put("physicalAddrValues", physicalAddrValues);
			fromMaps.put("legalAddrValues", legalAddrValues);

			ListGridRecord partBankRecordList[] = orgPartBankOrgsGrid
					.getRecords();
			if (partBankRecordList != null && partBankRecordList.length > 0) {
				LinkedHashMap<String, String> orgPartnerBanks = new LinkedHashMap<String, String>();
				for (ListGridRecord rec : partBankRecordList) {
					String orgId = rec.getAttributeAsInt("organization_id")
							.toString();
					orgPartnerBanks.put("organization_id_" + orgId, orgId);
				}
				fromMaps.put("orgPartnerBanks", orgPartnerBanks);
			}
			ClientUtils.fillMapFromForm(fromMaps, dynamicFormMainInfo,
					dynamicFormMainInfo1, dynamicFormMainInfo2);
			fromMaps.put("important_remark", important_remark1);

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record(fromMaps);
			DSRequest req = new DSRequest();

			if (listGridRecord == null) {
				req.setAttribute("operationId", "addOrganization");
				orgTreeGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateOrganization");
				orgTreeGrid.updateData(record, new DSCallback() {
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
