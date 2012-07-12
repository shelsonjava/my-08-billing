package com.info08.billing.callcenterbk.client.dialogs.org;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyAddressPanel2;
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
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class DlgAddEditOrganization extends Window {

	// main layout
	private VLayout hLayout;

	// forms
	// org. main info
	private DynamicForm dynamicFormMainInfo;
	private DynamicForm dynamicFormMainInfo1;

	private MyComboBoxItem parrentOrgItem;

	// org. address
	private MyAddressPanel2 physicalAddress;
	private MyAddressPanel2 legalAddress;

	private TabSet topTabSet;

	// main info fields.
	private TextAreaItem orgNameItem;
	private TextItem orgNameEngItem;
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
	private TextItem orgInfoItem;
	private ComboBoxItem orgLegalStatusItem;
	private DateItem orgFoundedItem;
	private SelectItem superPriorityItem;
	private CheckboxItem importantRemark;
	private SelectItem orgStatusItem;

	private IButton saveItem;
	private IButton copyItem;
	// private int selectedTabIndex = 0;

	private ListGrid organizationsGrid;
	private Record listGridRecord;
	private ListGrid orgDayOffsGrid;
	private ListGrid orgActivityGrid;
	private ListGrid orgPartBankOrgsGrid;
	private ListGridRecord parrentRecord;

	public DlgAddEditOrganization(ListGridRecord parrentRecord,
			Record listGridRecord, ListGrid organizationsGrid) {
		try {
			this.organizationsGrid = organizationsGrid;
			this.listGridRecord = listGridRecord;
			this.parrentRecord = parrentRecord;
			setTitle(CallCenterBK.constants.manageOrgs());

			setHeight(760);
			setWidth(1232);
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

			int minus = 15;

			VLayout leftLayOut = new VLayout();
			leftLayOut.setWidth(610 - minus);
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

			// Tab tabDepartment = new Tab(CallCenterBK.constants.department());
			// tabDepartment.setPane(formsLayoutAddInfo);
			// topTabSet.addTab(tabDepartment);

			hLayout.addMember(topTabSet);

			parrentOrgItem = new MyComboBoxItem("parrent_organization_name",
					CallCenterBK.constants.parrentOrgName(), 0, 610 - minus);
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
			orgNameItem.setName("original_org_name");
			orgNameItem.setWidth(610 - minus);
			orgNameItem.setTitle(CallCenterBK.constants.orgName());
			orgNameItem.setHeight(62);

			orgNameEngItem = new TextItem();
			orgNameEngItem.setName("organization_name_eng");
			orgNameEngItem.setWidth(610 - minus);
			orgNameEngItem.setTitle(CallCenterBK.constants.orgNameEng());

			orgRemarkItem = new TextAreaItem();
			orgRemarkItem.setName("remark");
			orgRemarkItem.setWidth(610 - minus);
			orgRemarkItem.setTitle(CallCenterBK.constants.comment());
			orgRemarkItem.setHeight(102);
			orgRemarkItem.setTextBoxStyle("input14Px");

			orgInfoItem = new TextItem();
			orgInfoItem.setName("additional_info");
			orgInfoItem.setWidth(591);
			orgInfoItem.setColSpan(2);
			orgInfoItem.setTitle(CallCenterBK.constants.orgInfo());

			orgChiefItem = new TextItem();
			orgChiefItem.setName("chief");
			orgChiefItem.setWidth(284);
			orgChiefItem.setTitle(CallCenterBK.constants.director());

			orgContactPersonItem = new TextItem();
			orgContactPersonItem.setName("contact_person");
			orgContactPersonItem.setWidth(307);
			orgContactPersonItem.setTitle(CallCenterBK.constants
					.contactPerson());

			orgIdentCodeItem = new TextItem();
			orgIdentCodeItem.setName("ident_code");
			orgIdentCodeItem.setWidth(284);
			orgIdentCodeItem.setTitle(CallCenterBK.constants.identCode());
			orgIdentCodeItem.setMask("###########");

			orgIdentCodeNewItem = new TextItem();
			orgIdentCodeNewItem.setName("ident_code_new");
			orgIdentCodeNewItem.setWidth(307);
			orgIdentCodeNewItem.setTitle(CallCenterBK.constants.identCodeNew());
			orgIdentCodeNewItem.setMask("###########");

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
			orgSocialAddressItem.setWidth(307);
			orgSocialAddressItem.setTitle(CallCenterBK.constants
					.socialAddress());

			orgMailItem = new TextItem();
			orgMailItem.setName("email_address");
			orgMailItem.setWidth(307);
			orgMailItem.setTitle(CallCenterBK.constants.eMail());

			orgIndItem = new TextItem();
			orgIndItem.setName("organization_index");
			orgIndItem.setWidth(284);
			orgIndItem.setTitle(CallCenterBK.constants.postIndex());
			orgIndItem.setMask("####");

			orgStaffCountItem = new TextItem();
			orgStaffCountItem.setName("staff_count");
			orgStaffCountItem.setWidth(307);
			orgStaffCountItem.setTitle(CallCenterBK.constants
					.workPersonCountity());
			orgStaffCountItem.setKeyPressFilter("[0-9]");

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
			orgFoundedItem
					.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
			orgFoundedItem.setUseMask(true);

			superPriorityItem = new SelectItem();
			superPriorityItem.setTitle(CallCenterBK.constants.extraPriority());
			superPriorityItem.setWidth(307);
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
			importantRemark.setWidth(610 - minus);
			importantRemark.setHeight(23);
			importantRemark.setValue(false);

			orgStatusItem = new SelectItem();
			orgStatusItem.setTitle(CallCenterBK.constants.status());
			orgStatusItem.setName("status");
			orgStatusItem.setWidth(307);
			orgStatusItem.setValueMap(ClientMapUtil.getInstance()
					.getOrgStatuses());
			orgStatusItem.setDefaultToFirstOption(true);

			dynamicFormMainInfo.setFields(orgNameItem, orgNameEngItem,
					importantRemark, orgRemarkItem);

			dynamicFormMainInfo1.setFields(orgChiefItem, orgContactPersonItem,
					orgIdentCodeItem, orgIdentCodeNewItem, orgWorkHoursItem,
					orgStaffCountItem, orgLegalStatusItem, orgStatusItem,
					orgFoundedItem, orgSocialAddressItem, orgWebAddressItem,
					orgMailItem, orgIndItem, superPriorityItem, orgInfoItem);

			DynamicForm dForm1 = new DynamicForm();
			dForm1.setAutoFocus(false);
			dForm1.setWidth100();
			dForm1.setNumCols(3);
			dForm1.setTitleWidth(100);

			final ComboBoxItem orgActsItem = new ComboBoxItem();
			orgActsItem.setTitle(CallCenterBK.constants.activity());
			orgActsItem.setName("org_activity_id");
			orgActsItem.setWidth(220 - (minus / 2));
			orgActsItem.setTitleAlign(Alignment.LEFT);
			ClientUtils.fillCombo(orgActsItem, "OrgActDS",
					"searchAllBusinesActivitiesForCB", "org_activity_id",
					"activity_description");

			PickerIcon addOrgActivity = new PickerIcon(new PickerIcon.Picker(
					"addIcon.png"), new FormItemClickHandler() {
				public void onFormItemClick(FormItemIconClickEvent event) {
					ListGridRecord listGridRecord = orgActsItem
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Record oldRecord = orgActivityGrid
							.getRecordList()
							.find("org_activity_id",
									listGridRecord
											.getAttributeAsInt("org_activity_id"));
					if (oldRecord != null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants
										.thisOrgActAlreadyChoosen());
						return;
					}
					orgActivityGrid.addData(listGridRecord);
				}
			});
			orgActsItem.setIcons(addOrgActivity);

			dForm1.setFields(orgActsItem);

			MainOrgActClientDS mainOrgActClientDS = MainOrgActClientDS
					.getInstance();

			orgActivityGrid = new ListGrid();
			orgActivityGrid.setWidth(300 - (minus / 2));
			orgActivityGrid.setHeight(130);
			orgActivityGrid.setDataSource(mainOrgActClientDS);
			orgActivityGrid.setCanAcceptDroppedRecords(true);
			orgActivityGrid.setCanRemoveRecords(true);
			orgActivityGrid.setAutoFetchData(true);
			orgActivityGrid.setPreventDuplicates(true);
			orgActivityGrid.setDuplicateDragMessage(CallCenterBK.constants
					.thisOrgActAlreadyChoosen());

			VLayout gridsLayout = new VLayout();
			gridsLayout.setMargin(2);
			gridsLayout.setHeight100();

			gridsLayout.setMembers(dForm1, orgActivityGrid);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.LEFT);

			copyItem = new IButton();
			copyItem.setTitle(CallCenterBK.constants.sameAddress());
			copyItem.setWidth(350);
			copyItem.setIcon("copy.png");

			saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			LayoutSpacer spacer = new LayoutSpacer();
			spacer.setWidth100();
			hLayoutItem.setMembers(copyItem, spacer, saveItem, cancItem);

			hLayout.addMember(hLayoutItem);

			DynamicForm dForm2 = new DynamicForm();
			dForm2.setAutoFocus(false);
			dForm2.setWidth100();
			dForm2.setNumCols(2);
			dForm2.setTitleWidth(200);

			final SelectItem weekDaysItem = new SelectItem();
			weekDaysItem.setTitle(CallCenterBK.constants.weekDay());
			weekDaysItem.setName("day_id");
			weekDaysItem.setWidth(220 - (minus / 2));
			weekDaysItem.setTitleAlign(Alignment.LEFT);
			weekDaysItem.setValueMap(ClientMapUtil.getInstance().getDayOffs());
			weekDaysItem.setDefaultToFirstOption(true);
			PickerIcon addWeekDay = new PickerIcon(new PickerIcon.Picker(
					"addIcon.png"), new FormItemClickHandler() {
				public void onFormItemClick(FormItemIconClickEvent event) {
					String idValue = weekDaysItem.getValueAsString();
					String displayValue = weekDaysItem.getDisplayValue();

					Record oldRecord = orgDayOffsGrid.getRecordList().find(
							"day_id", new Integer(idValue));
					if (oldRecord != null) {
						SC.say(CallCenterBK.constants.warning(),
								"ასეთი უკვე არჩეულია !");
						return;
					}
					ListGridRecord listGridRecord = new ListGridRecord();
					listGridRecord.setAttribute("day_id", idValue);
					listGridRecord.setAttribute("day_name", displayValue);
					orgDayOffsGrid.addData(listGridRecord);
				}
			});
			weekDaysItem.setIcons(addWeekDay);

			dForm2.setFields(weekDaysItem);

			OrgDayOffsClientDS orgDayOffsClientDS = OrgDayOffsClientDS
					.getInstance();
			orgDayOffsGrid = new ListGrid();
			orgDayOffsGrid.setWidth(300 - (minus / 2));
			orgDayOffsGrid.setHeight(130);
			orgDayOffsGrid.setDataSource(orgDayOffsClientDS);
			orgDayOffsGrid.setCanAcceptDroppedRecords(true);
			orgDayOffsGrid.setCanRemoveRecords(true);
			orgDayOffsGrid.setAutoFetchData(true);
			orgDayOffsGrid.setPreventDuplicates(true);
			orgDayOffsGrid.setDuplicateDragMessage("ასეთი უკვე არჩეულია !");

			VLayout gridsLayout1 = new VLayout(0);
			gridsLayout1.setMargin(2);

			gridsLayout1.setMembers(dForm2, orgDayOffsGrid);

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

			DynamicForm dForm3 = new DynamicForm();
			dForm3.setAutoFocus(false);
			dForm3.setWidth100();
			dForm3.setNumCols(2);
			dForm3.setTitleWidth(174);

			final ComboBoxItem partnerBankItem = new ComboBoxItem();
			partnerBankItem.setTitle(CallCenterBK.constants.partnerBank());
			partnerBankItem.setName("organization_id_111");
			partnerBankItem.setWidth(419);
			partnerBankItem.setTitleAlign(Alignment.LEFT);
			ClientUtils.fillCombo(partnerBankItem, "OrgDS",
					"searchPartnerBanks", "organization_id",
					"organization_name");
			PickerIcon addPartnerOrgBank = new PickerIcon(
					new PickerIcon.Picker("addIcon.png"),
					new FormItemClickHandler() {
						public void onFormItemClick(FormItemIconClickEvent event) {
							ListGridRecord listGridRecord = partnerBankItem
									.getSelectedRecord();
							if (listGridRecord == null) {
								SC.say(CallCenterBK.constants.warning(),
										CallCenterBK.constants
												.pleaseSelrecord());
								return;
							}
							Record oldRecord = orgPartBankOrgsGrid
									.getRecordList()
									.find("organization_id",
											listGridRecord
													.getAttributeAsInt("organization_id"));
							if (oldRecord != null) {
								SC.say(CallCenterBK.constants.warning(),
										"ასეთი უკვე არჩეულია !");
								return;
							}
							orgPartBankOrgsGrid.addData(listGridRecord);
						}
					});
			partnerBankItem.setIcons(addPartnerOrgBank);

			dForm3.setFields(partnerBankItem);

			OrgPartnetBanksClientDS orgPartnetBanksClientDS = OrgPartnetBanksClientDS
					.getInstance();
			orgPartBankOrgsGrid = new ListGrid();
			orgPartBankOrgsGrid.setWidth(591);
			orgPartBankOrgsGrid.setHeight(130);
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

			VLayout hLayoutPartBanks = new VLayout();
			hLayoutPartBanks.setWidth(591);
			hLayoutPartBanks.setMembersMargin(2);
			hLayoutPartBanks.setHeight(130);

			hLayoutPartBanks.setMembers(dForm3, orgPartBankOrgsGrid);

			HLayout vLayoutGrids = new HLayout();
			vLayoutGrids.setMembers(gridsLayout, gridsLayout1);

			leftLayOut.addMember(vLayoutGrids);

			// Address
			HLayout hLayoutGrids = new HLayout();
			hLayoutGrids.setHeight100();

			physicalAddress = new MyAddressPanel2(false, "PhysicalAddress1",
					CallCenterBK.constants.orgAddressHeaderReal(), 550, 0);
			hLayoutGrids.addMember(physicalAddress);
			leftLayOut.addMember(hLayoutGrids);
			//

			rigthLayOut.addMember(hLayoutPartBanks);

			legalAddress = new MyAddressPanel2(true, "LegalAddress",
					CallCenterBK.constants.orgAddressHeaderLegal(), 550, 0);
			rigthLayOut.addMember(legalAddress);

			copyItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					copyAddress();
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
				Integer important_remark = (Integer) map
						.get("important_remark");
				map.remove("important_remark");
				parrentOrgItem.setDataValue(map);
				dynamicFormMainInfo.setValues(map);
				dynamicFormMainInfo1.setValues(map);

				if (important_remark != null
						&& important_remark.equals(new Integer(-1))) {
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

			} else if (parrentRecord != null) {
				parrentOrgItem.setDataValue(parrentRecord
						.getAttributeAsInt("organization_id"));
				physicalAddress.setValue(null);
				legalAddress.setValue(null);
			} else {
				physicalAddress.setValue(null);
				legalAddress.setValue(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void copyAddress() {
		try {
			legalAddress.clearValues();
			Map<?, ?> valuesCopy = physicalAddress.getValues();
			String oldAddress = legalAddress.getOldAddItem().getValueAsString();
			legalAddress.setValues(valuesCopy);
			legalAddress.getOldAddItem().setValue(oldAddress);
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

			if (identCode != null && !identCode.trim().equals("")) {
				try {
					Long.parseLong(identCode);
				} catch (NumberFormatException e) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.orgIdentCodeIsInvalid());
					topTabSet.selectTab(0);
					dynamicFormMainInfo1.focusInItem(orgIdentCodeItem);
					return;
				}
			}

			if (identCodeNew != null && !identCodeNew.trim().equals("")) {
				try {
					Long.parseLong(identCodeNew);
				} catch (NumberFormatException e) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.orgIdentCodeIsInvalid());
					topTabSet.selectTab(0);
					dynamicFormMainInfo1.focusInItem(orgIdentCodeNewItem);
					return;
				}
			}

			String organization_index = orgIndItem.getValueAsString();
			if (organization_index == null
					|| organization_index.trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.orgIndIsInvalid());
				topTabSet.selectTab(0);
				dynamicFormMainInfo1.focusInItem(orgIndItem);
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
			Map<?, ?> legalAddrValues = legalAddress.getValues();

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

			if (legalAddress.isCheckedTurnOffItem()) {
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
						: new Integer(legalAddrValues.get("street_id")
								.toString());
				if (legal_street_id == null) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.plzChooseLegalAddrStreet());
					topTabSet.selectTab(1);
					legalAddress.getDynamicForm().focusInItem(
							legalAddress.getAddrStreetItem());
					return;
				}
			}

			Integer days = 0;
			boolean allDayOffsIsSel = false;

			for (ListGridRecord listGridRecord : orgDayOffsList) {
				Integer day_id = Integer.parseInt(listGridRecord
						.getAttributeAsString("day_id"));
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
			final Map<String, Object> fromMaps = new LinkedHashMap<String, Object>();
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
					dynamicFormMainInfo1);
			fromMaps.put("important_remark", important_remark1);
			fromMaps.put("organization_name", orgNameItem.getValueAsString());

			if (listGridRecord == null || parrent_organization_id == null) {
				saveData(fromMaps);
			} else {
				com.smartgwt.client.rpc.RPCManager.startQueue();
				DSRequest req = new DSRequest();
				req.setAttribute("operationId", "orgCheckParrChild");
				Criteria criteria = new Criteria();
				criteria.setAttribute("curr_organization_id",
						listGridRecord.getAttributeAsInt("organization_id"));
				criteria.setAttribute("parr_organization_id",
						parrent_organization_id);
				criteria.setAttribute("cr_uId_" + HTMLPanel.createUniqueId(),
						HTMLPanel.createUniqueId());
				DataSource dataSource = DataSource.get("OrgDS");
				dataSource.fetchData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record records[] = response.getData();
						if (records != null && records.length > 0) {
							Integer count = records[0]
									.getAttributeAsInt("recCount");
							if (count != null && count.intValue() > 0) {
								SC.say(CallCenterBK.constants.warning(),
										CallCenterBK.constants
												.parrentIsAlsoChild());
								return;
							}
						}
						saveData(fromMaps);
					}
				}, req);
				com.smartgwt.client.rpc.RPCManager.sendQueue();
			}

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void saveData(Map<String, Object> fromMaps) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record(fromMaps);
			DSRequest req = new DSRequest();

			if (listGridRecord == null) {
				req.setAttribute("operationId", "addOrganization");
				organizationsGrid.addData(record, new DSCallback() {
					@Override
					public void execute(final DSResponse response,
							Object rawData, DSRequest request) {
						destroy();
						SC.ask(CallCenterBK.constants.information(),
								CallCenterBK.constants
										.doYouWantOrgDepManagement(),
								new BooleanCallback() {
									@Override
									public void execute(Boolean value) {
										if (value) {
											try {
												Record record = response
														.getData()[0];
												DlgManageOrgDepartments addEditOrgDepartments = new DlgManageOrgDepartments(
														record,
														organizationsGrid);
												addEditOrgDepartments.show();
											} catch (Exception e) {
												e.printStackTrace();
												SC.say(e.toString());
											}
										}
									}
								});
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateOrganization");
				organizationsGrid.updateData(record, new DSCallback() {
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
