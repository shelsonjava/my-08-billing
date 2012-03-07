package com.info08.billing.callcenter.client.dialogs.org;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.singletons.ClientMapUtil;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
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
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;

public class DlgAddEditMainOrg extends Window {

	// main layout
	private VLayout hLayout;

	// forms
	// org. main info
	private DynamicForm dynamicFormMainInfo;
	private DynamicForm dynamicFormMainInfo1;

	// org. address
	// private DynamicForm dynFormOrgAddress;

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
	private TextItem orgMailItem;
	private TextItem orgDayOffsItem;
	private TextItem orgIndItem;
	private TextItem orgWorkPersQuantItem;
	private TextAreaItem orgInfoItem;
	private TextItem orgLegalAddressItem;
	private ComboBoxItem orgLegalStatusItem;
	private ComboBoxItem orgPartnerBankItem;
	private DateItem orgFoundedItem;
	private SelectItem extraPriorityItem;
	private CheckboxItem noteCritItem;
	private SelectItem orgStatusItem;

	// address fields.
	private ComboBoxItem citiesItem;
	private ComboBoxItem streetItem;
	private ComboBoxItem regionItem;
	private TextAreaItem streetDescrItem;
	private TextItem streetIdxItem;
	private TextItem oldAddItem;
	private ComboBoxItem adressOpCloseItem;
	private TextItem adressItem;
	private TextItem blockItem;
	private TextItem appartItem;

	private TreeGrid orgTreeGrid;
	private ListGridRecord listGridRecord;

	private ListGrid activityGrid;
	private ListGrid orgActivityGrid;
	private MainOrgActClientDS mainOrgActClientDS;
	protected boolean isAddNewOrg;

	public DlgAddEditMainOrg(boolean isAddNewOrg,
			ListGridRecord listGridRecord, TreeGrid orgTreeGrid) {
		try {
			this.isAddNewOrg = isAddNewOrg;
			this.orgTreeGrid = orgTreeGrid;
			this.listGridRecord = listGridRecord;
			setTitle(CallCenter.constants.manageOrgs());

			setHeight(750);
			setWidth(1250);
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

			VLayout rightLayOut = new VLayout();
			rightLayOut.setWidth(650);
			rightLayOut.setHeight100();
			rightLayOut.addMember(dynamicFormMainInfo);

			formsLayout.setMembers(rightLayOut, dynamicFormMainInfo1);

			hLayout.addMember(formsLayout);

			HeaderItem headerItemOrgInfo = new HeaderItem();
			String campTex = "";
			if (isAddNewOrg) {
				if (listGridRecord == null) {
					campTex = CallCenter.constants.addNewOrg1();
				} else {
					campTex = CallCenter.constants.addChildOrg1();
				}
			} else {
				campTex = CallCenter.constants.editOrg();
			}
			headerItemOrgInfo.setValue(campTex);
			headerItemOrgInfo.setTextBoxStyle("headerStyle12AndRed");

			HeaderItem headerItemOrgInfo1 = new HeaderItem();
			headerItemOrgInfo1.setValue(CallCenter.constants.orgCommonInfo());
			headerItemOrgInfo1.setTextBoxStyle("headerStyle");

			orgNameItem = new TextAreaItem();
			orgNameItem.setName("org_name");
			orgNameItem.setWidth(650);
			orgNameItem.setTitle(CallCenter.constants.orgName());
			orgNameItem.setHeight(62);

			orgNameEngItem = new TextAreaItem();
			orgNameEngItem.setName("org_name_eng");
			orgNameEngItem.setWidth(650);
			orgNameEngItem.setTitle(CallCenter.constants.orgNameEng());
			orgNameEngItem.setHeight(62);

			orgNoteItem = new TextAreaItem();
			orgNoteItem.setName("comment");
			orgNoteItem.setWidth(650);
			orgNoteItem.setTitle(CallCenter.constants.comment());
			orgNoteItem.setHeight(126);

			orgDirectorItem = new TextItem();
			orgDirectorItem.setName("director");
			orgDirectorItem.setWidth(284);
			orgDirectorItem.setTitle(CallCenter.constants.director());

			orgContactPersonItem = new TextItem();
			orgContactPersonItem.setName("contact_person");
			orgContactPersonItem.setWidth(284);
			orgContactPersonItem.setTitle(CallCenter.constants.contactPerson());

			orgIdentCodeItem = new TextItem();
			orgIdentCodeItem.setName("ident_code");
			orgIdentCodeItem.setWidth(284);
			orgIdentCodeItem.setTitle(CallCenter.constants.identCode());

			orgNewIdentCodeItem = new TextItem();
			orgNewIdentCodeItem.setName("new_ident_code");
			orgNewIdentCodeItem.setWidth(284);
			orgNewIdentCodeItem.setTitle(CallCenter.constants.identCodeNew());

			orgWorkHoursItem = new TextItem();
			orgWorkHoursItem.setName("working_hours");
			orgWorkHoursItem.setWidth(284);
			orgWorkHoursItem.setTitle(CallCenter.constants.workinghours());

			orgWebAddressItem = new TextItem();
			orgWebAddressItem.setName("orgWebAddressItem");
			orgWebAddressItem.setWidth(284);
			orgWebAddressItem.setTitle(CallCenter.constants.webaddress());

			orgMailItem = new TextItem();
			orgMailItem.setName("orgMailItem");
			orgMailItem.setWidth(284);
			orgMailItem.setTitle(CallCenter.constants.eMail());

			orgDayOffsItem = new TextItem();
			orgDayOffsItem.setName("day_offs");
			orgDayOffsItem.setWidth(284);
			orgDayOffsItem.setTitle(CallCenter.constants.dayOffs());

			orgIndItem = new TextItem();
			orgIndItem.setName("post_ind");
			orgIndItem.setWidth(284);
			orgIndItem.setTitle(CallCenter.constants.postIndex());

			orgWorkPersQuantItem = new TextItem();
			orgWorkPersQuantItem.setName("work_pers_quant");
			orgWorkPersQuantItem.setWidth(284);
			orgWorkPersQuantItem.setTitle(CallCenter.constants
					.workPersonCountity());

			orgInfoItem = new TextAreaItem();
			orgInfoItem.setName("org_info");
			orgInfoItem.setWidth(650);
			orgInfoItem.setHeight(62);
			orgInfoItem.setTitle(CallCenter.constants.orgInfo());

			orgLegalAddressItem = new TextItem();
			orgLegalAddressItem.setName("org_legal_address");
			orgLegalAddressItem.setWidth(284);
			orgLegalAddressItem.setTitle(CallCenter.constants.legalAddress());

			orgLegalStatusItem = new ComboBoxItem();
			orgLegalStatusItem.setTitle(CallCenter.constants.legalStatuse());
			orgLegalStatusItem.setName("org_legal_status");
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

			orgPartnerBankItem = new ComboBoxItem();
			orgPartnerBankItem.setTitle(CallCenter.constants.orgPartnerBank());
			orgPartnerBankItem.setName("org_partner_bank");
			orgPartnerBankItem.setWidth(284);
			orgPartnerBankItem.setFetchMissingValues(true);
			orgPartnerBankItem.setFilterLocally(false);
			orgPartnerBankItem.setAddUnknownValues(false);

			DataSource orgDS = DataSource.get("OrgDS");
			orgPartnerBankItem.setOptionOperationId("searchCustomMainOrgBanks");
			orgPartnerBankItem.setOptionDataSource(orgDS);
			orgPartnerBankItem.setValueField("main_id");
			orgPartnerBankItem.setDisplayField("org_name");

			orgPartnerBankItem.setOptionCriteria(new Criteria());
			orgPartnerBankItem.setAutoFetchData(false);

			orgPartnerBankItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = orgPartnerBankItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("main_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("main_id", nullO);
						}
					}
				}
			});

			orgFoundedItem = new DateItem();
			orgFoundedItem.setTitle(CallCenter.constants.founded());
			orgFoundedItem.setWidth(284);
			orgFoundedItem.setName("founded");
			orgFoundedItem.setUseTextField(true);

			extraPriorityItem = new SelectItem();
			extraPriorityItem.setTitle(CallCenter.constants.extraPriority());
			extraPriorityItem.setWidth(284);
			extraPriorityItem.setName("extraPriority");
			extraPriorityItem.setDefaultToFirstOption(true);
			extraPriorityItem.setValueMap(ClientMapUtil.getInstance()
					.getOrgNoteCrits());
			boolean hasPermission = CommonSingleton.getInstance()
					.hasPermission("105550");
			extraPriorityItem.setDisabled(!hasPermission);

			noteCritItem = new CheckboxItem();
			noteCritItem.setTitle(CallCenter.constants.noteCrit());
			noteCritItem.setWidth(650);
			noteCritItem.setHeight(24);
			noteCritItem.setName("note_crit");

			HeaderItem headerItemAddr1 = new HeaderItem();
			headerItemAddr1.setValue(CallCenter.constants
					.orgAddressHeaderReal());
			headerItemAddr1.setTextBoxStyle("headerStyle");

			citiesItem = new ComboBoxItem();
			citiesItem.setTitle(CallCenter.constants.city());
			citiesItem.setName("city_name_geo");
			citiesItem.setWidth(284);
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
			citiesItem.setValue(Constants.defCityTbilisiId);

			streetItem = new ComboBoxItem();
			streetItem.setTitle(CallCenter.constants.street());
			streetItem.setName("street_name_geo");
			streetItem.setWidth(284);
			streetItem.setFetchMissingValues(true);
			streetItem.setFilterLocally(false);
			streetItem.setAddUnknownValues(false);

			DataSource streetsNewDS = DataSource.get("StreetsNewDS");
			streetItem.setOptionOperationId("searchStreetFromDBForCombos");
			streetItem.setOptionDataSource(streetsNewDS);
			streetItem.setValueField("street_id");
			streetItem.setDisplayField("street_name_geo");
			streetItem.setOptionCriteria(new Criteria());
			streetItem.setAutoFetchData(false);

			streetItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("street_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_id", nullO);
						}
					}
				}
			});

			regionItem = new ComboBoxItem();
			regionItem.setTitle(CallCenter.constants.cityRegion());
			regionItem.setName("city_region_name_geo");
			regionItem.setWidth(284);
			regionItem.setFetchMissingValues(true);
			regionItem.setFilterLocally(false);
			regionItem.setAddUnknownValues(false);

			DataSource streetsDS = DataSource.get("CityRegionDS");
			regionItem.setOptionOperationId("searchCityRegsFromDBForCombos");
			regionItem.setOptionDataSource(streetsDS);
			regionItem.setValueField("city_region_id");
			regionItem.setDisplayField("city_region_name_geo");

			Criteria criteria = new Criterion();
			criteria.setAttribute("city_id", Constants.defCityTbilisiId);
			regionItem.setOptionCriteria(criteria);
			regionItem.setAutoFetchData(false);

			regionItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = regionItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("city_region_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("city_region_id", nullO);
						}
					}
				}
			});

			streetDescrItem = new TextAreaItem();
			streetDescrItem.setTitle(CallCenter.constants.streetDescr());
			streetDescrItem.setName("streetDescrItem");
			streetDescrItem.setWidth(569);
			streetDescrItem.setColSpan(2);
			streetDescrItem.setHeight(49);
			streetDescrItem.setCanEdit(false);

			streetIdxItem = new TextItem();
			streetIdxItem.setTitle(CallCenter.constants.streetIdx());
			streetIdxItem.setName("street_index");
			streetIdxItem.setWidth(569);
			// streetIdxItem.setHeight(50);
			streetIdxItem.setCanEdit(false);
			streetIdxItem.setColSpan(2);

			oldAddItem = new TextItem();
			oldAddItem.setTitle(CallCenter.constants.oldAddress());
			oldAddItem.setName("oldAddItem");
			oldAddItem.setWidth(284);
			oldAddItem.setCanEdit(false);

			adressOpCloseItem = new ComboBoxItem();
			adressOpCloseItem.setValueMap(ClientMapUtil.getInstance()
					.getAddrMapOpClose());
			adressOpCloseItem.setDefaultToFirstOption(true);
			adressOpCloseItem.setTitle(CallCenter.constants.openClose());
			adressOpCloseItem.setName("adressOpCloseItem");
			adressOpCloseItem.setWidth(284);
			adressOpCloseItem.setFetchMissingValues(false);

			adressItem = new TextItem();
			adressItem.setTitle(CallCenter.constants.home());
			adressItem.setName("adressItem");
			adressItem.setWidth(284);

			blockItem = new TextItem();
			blockItem.setTitle(CallCenter.constants.block());
			blockItem.setName("blockItem");
			blockItem.setWidth(284);

			appartItem = new TextItem();
			appartItem.setTitle(CallCenter.constants.appartment());
			appartItem.setName("appartItem");
			appartItem.setWidth(284);

			orgStatusItem = new SelectItem();
			orgStatusItem.setTitle(CallCenter.constants.status());
			orgStatusItem.setName("response_type");
			orgStatusItem.setWidth(284);
			orgStatusItem.setValueMap(ClientMapUtil.getInstance()
					.getOrgStatuses());
			orgStatusItem.setDefaultToFirstOption(true);

			SpacerItem spacerItem = new SpacerItem();
			spacerItem.setHeight(37);

			dynamicFormMainInfo.setFields(headerItemOrgInfo, orgNameItem,
					orgNameEngItem, noteCritItem, orgNoteItem, orgInfoItem);

			dynamicFormMainInfo1.setFields(headerItemOrgInfo1, orgDirectorItem,
					orgContactPersonItem, orgIdentCodeItem,
					orgNewIdentCodeItem, orgWorkHoursItem, orgDayOffsItem,
					orgLegalAddressItem, orgWorkPersQuantItem,
					orgLegalStatusItem, orgPartnerBankItem, extraPriorityItem,
					orgStatusItem, orgFoundedItem, orgIndItem, spacerItem,
					headerItemAddr1, citiesItem, regionItem, streetItem,
					adressOpCloseItem, adressItem, blockItem, appartItem,
					oldAddItem, orgWebAddressItem, orgMailItem,
					streetDescrItem, streetIdxItem);

			DataSource businessDetDS = DataSource.get("BusinessDetDS");

			activityGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer deleted = countryRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && !deleted.equals(0)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};
			activityGrid.setWidth(300);
			activityGrid.setHeight100();
			activityGrid.setDataSource(businessDetDS);
			activityGrid.setShowFilterEditor(true);
			activityGrid.setFilterOnKeypress(true);
			activityGrid.setCanDragRecordsOut(true);
			activityGrid.setDragDataAction(DragDataAction.COPY);
			activityGrid.setAlternateRecordStyles(true);
			activityGrid.setAutoFetchData(true);
			activityGrid.setFetchOperation("searchAllBusinessDetailsForCB");

			ListGridField activity = new ListGridField(
					"business_detail_name_geo", CallCenter.constants.activity());
			activity.setAlign(Alignment.LEFT);

			activityGrid.setFields(activity);

			mainOrgActClientDS = MainOrgActClientDS.getInstance();

			orgActivityGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer deleted = countryRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && !deleted.equals(0)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};
			orgActivityGrid.setWidth(300);
			orgActivityGrid.setHeight100();
			orgActivityGrid.setDataSource(mainOrgActClientDS);
			orgActivityGrid.setCanAcceptDroppedRecords(true);
			orgActivityGrid.setCanRemoveRecords(true);
			orgActivityGrid.setAutoFetchData(true);
			orgActivityGrid.setPreventDuplicates(true);
			orgActivityGrid.setDuplicateDragMessage(CallCenter.constants
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
			hLayoutImg.setWidth(46);
			hLayoutImg.setAlign(Alignment.CENTER);
			hLayoutImg.addMember(arrowImg);

			gridsLayout.setMembers(activityGrid, hLayoutImg, orgActivityGrid);

			rightLayOut.addMember(gridsLayout);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenter.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenter.constants.close());
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
					save();
				}
			});

			citiesItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					String value = citiesItem.getValueAsString();
					if (value == null) {
						return;
					}
					Integer city_id = null;
					try {
						city_id = Integer.parseInt(value);
					} catch (NumberFormatException e) {
						return;
					}
					streetItem.clearValue();
					regionItem.clearValue();
					streetDescrItem.setValue("");
					streetIdxItem.setValue("");
					fillStreetsCombo(null, city_id);
					fillCityRegionCombo(null, city_id);
				}
			});
			streetItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					ListGridRecord record = streetItem.getSelectedRecord();
					if (record == null) {
						return;
					}
					String descr = record.getAttribute("street_location_geo");
					if (descr == null) {
						descr = "";
					}
					streetDescrItem.setValue(descr);
					Integer city_region_id = record
							.getAttributeAsInt("city_region_id");
					Integer cityId = record.getAttributeAsInt("city_id");
					if (city_region_id != null && cityId != null) {
						fillCityRegionCombo(null, cityId);
						regionItem.setValue(city_region_id);
						Criteria criteria = regionItem.getOptionCriteria();
						if (criteria != null) {
							String oldAttr = criteria
									.getAttribute("city_region_id");
							if (oldAttr != null) {
								Object nullO = null;
								criteria.setAttribute("city_region_id", nullO);
							}
						}
					}

					DataSource dataSource = DataSource.get("StreetIndexDS");
					Criteria criteria = new Criteria();
					criteria.setAttribute("street_id",
							record.getAttributeAsInt("street_id"));
					DSRequest requestProperties = new DSRequest();
					requestProperties.setOperationId("searchStrIdxDescrs");
					dataSource.fetchData(criteria, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							Record records[] = response.getData();
							if (records != null && records.length > 0) {
								String str = "";
								for (Record recordItem : records) {
									String item = recordItem
											.getAttribute("idx_descr");
									if (item != null && !item.trim().equals("")) {
										str += (item + " ; ");
									}
								}
								streetIdxItem.setValue(str);
							}
						}
					}, requestProperties);
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
			if (!isAddNewOrg && listGridRecord != null) {
				orgNameItem.setValue(listGridRecord
						.getAttributeAsString("org_name"));
				orgNameEngItem.setValue(listGridRecord
						.getAttributeAsString("org_name_eng"));
				orgNoteItem.setValue(listGridRecord
						.getAttributeAsString("note"));
				orgInfoItem.setValue(listGridRecord
						.getAttributeAsString("org_info"));
				orgDirectorItem.setValue(listGridRecord
						.getAttributeAsString("director"));
				orgContactPersonItem.setValue(listGridRecord
						.getAttributeAsString("contactperson"));
				orgIdentCodeItem.setValue(listGridRecord
						.getAttributeAsString("identcode"));
				orgNewIdentCodeItem.setValue(listGridRecord
						.getAttributeAsString("new_identcode"));
				orgWorkHoursItem.setValue(listGridRecord
						.getAttributeAsString("workinghours"));
				orgDayOffsItem.setValue(listGridRecord
						.getAttributeAsString("dayoffs"));
				orgLegalAddressItem.setValue(listGridRecord
						.getAttributeAsString("legaladdress"));
				orgWorkPersQuantItem.setValue(listGridRecord
						.getAttributeAsString("workpersoncountity"));
				orgLegalStatusItem.setValue(listGridRecord
						.getAttributeAsInt("legal_statuse_id"));
				orgPartnerBankItem.setValue(listGridRecord
						.getAttributeAsInt("p_bank_main_id"));
				extraPriorityItem.setValue(listGridRecord
						.getAttributeAsInt("extra_priority"));
				orgStatusItem.setValue(listGridRecord
						.getAttributeAsInt("statuse"));
				orgFoundedItem.setValue(listGridRecord
						.getAttributeAsString("founded"));
				orgIndItem.setValue(listGridRecord.getAttributeAsString("ind"));
				citiesItem
						.setValue(listGridRecord.getAttributeAsInt("city_id"));
				regionItem.setValue(listGridRecord
						.getAttributeAsInt("city_region_id"));
				streetItem.setValue(listGridRecord
						.getAttributeAsInt("street_id"));
				blockItem.setValue(listGridRecord
						.getAttributeAsString("addr_block"));
				appartItem.setValue(listGridRecord
						.getAttributeAsString("addr_appt"));
				adressItem.setValue(listGridRecord
						.getAttributeAsString("addr_number"));
				oldAddItem.setValue(listGridRecord
						.getAttributeAsString("address_suffix_geo"));
				orgWebAddressItem.setValue(listGridRecord
						.getAttributeAsString("webaddress"));
				orgMailItem.setValue(listGridRecord
						.getAttributeAsString("mail"));
				streetDescrItem.setValue(listGridRecord
						.getAttributeAsString("street_location_geo"));
				streetIdxItem.setValue(listGridRecord
						.getAttributeAsString("street_indexes"));
				adressOpCloseItem.setValue(listGridRecord
						.getAttributeAsInt("address_hide"));

				DataSource orgDS = DataSource.get("OrgDS");

				Criteria criteria = new Criteria();
				criteria.setAttribute("main_id",
						listGridRecord.getAttributeAsInt("main_id"));

				DSRequest dsRequest = new DSRequest();
				dsRequest.setAttribute("operationId",
						"searchBusinessDetailsByMainId");

				orgDS.fetchData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record records[] = response.getData();
						if (records == null || records.length <= 0) {
							return;
						}
						for (Record record : records) {
							orgActivityGrid.addData(record);
						}
					}
				}, dsRequest);

			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillStreetsCombo(final ListGridRecord abonentRecord,
			Integer defCityTbilisiId) {
		try {
			Integer city_id = defCityTbilisiId;
			if (abonentRecord != null) {
				Integer fromEdit = abonentRecord.getAttributeAsInt("city_id");
				if (fromEdit != null) {
					city_id = fromEdit;
				}
			}

			DataSource streetsDS = DataSource.get("StreetsNewDS");
			streetItem.setOptionOperationId("searchStreetFromDBForCombos");
			streetItem.setOptionDataSource(streetsDS);
			streetItem.setValueField("street_id");
			streetItem.setDisplayField("street_name_geo");

			Criteria criteria = new Criteria();
			criteria.setAttribute("city_id", city_id);
			streetItem.setOptionCriteria(criteria);
			streetItem.setAutoFetchData(false);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillCityRegionCombo(final ListGridRecord abonentRecord,
			Integer defCityTbilisiId) {
		try {
			Integer city_id = defCityTbilisiId;
			if (abonentRecord != null) {
				Integer fromEdit = abonentRecord.getAttributeAsInt("city_id");
				if (fromEdit != null) {
					city_id = fromEdit;
				}
			}

			DataSource streetsDS = DataSource.get("CityRegionDS");
			regionItem.setOptionOperationId("searchCityRegsFromDBForCombos");
			regionItem.setOptionDataSource(streetsDS);
			regionItem.setValueField("city_region_id");
			regionItem.setDisplayField("city_region_name_geo");

			Criteria criteria = new Criterion();
			criteria.setAttribute("city_id", city_id);
			regionItem.setOptionCriteria(criteria);
			regionItem.setAutoFetchData(false);

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
					.getSessionPerson().getUserName());

			record.setAttribute("main_id",
					listGridRecord.getAttributeAsInt("main_id"));

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
