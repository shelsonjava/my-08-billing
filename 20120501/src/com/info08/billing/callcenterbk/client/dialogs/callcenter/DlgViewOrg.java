package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.CanvasDisableTimer;
import com.info08.billing.callcenterbk.client.common.components.ChargePanel;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
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
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgViewOrg extends Window {

	private VLayout hLayout;

	protected Record record;
	// protected ListGrid listGrid;
	protected DataSource dataSource;
	protected DataSource depsDataSource;
	private ListGrid depsTreeGrid;
	private ListGrid phonesGrid;
	private DynamicForm searchDepsForm;
	private TextItem depNameGeoItem;
	private TextItem depPhonesItem;
	private TextItem depAddressItem;
	private CheckboxItem depContPhonesItem;
	private TextAreaItem orgNoteOnDep;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton sendAddInfoSMS;
	private ToolStripButton sendStreetInfoSMS;
	private IButton sendDepInfoSMS;
	private ChargePanel chargePanel;
	// private boolean smsSend = false;

	private DynamicForm mainInfoForm;
	private TextItem orgNameInfo;
	private TextItem orgAddressInfo;
	private ListGrid listGridOrgTreeChilds;
	private ListGrid listGridOrgTreeParrent;

	public DlgViewOrg(final DataSource dataSource, Record pRecord) {
		this(dataSource, pRecord, false);
	}

	public DlgViewOrg(final DataSource dataSource, final Record pRecord,
			boolean viewMainInfoFirst) {

		try {

			this.record = pRecord;
			this.dataSource = dataSource;

			setTitle(CallCenterBK.constants.organization());

			setHeight(700);
			setWidth(1100);
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

			chargePanel = new ChargePanel(1100, true, true,
					Constants.serviceOrganization,
					pRecord.getAttributeAsInt("organization_id"),
					pRecord.getAttributeAsString("organization_name"));
			hLayout.addMember(chargePanel);

			mainInfoForm = new DynamicForm();
			mainInfoForm.setAutoFocus(false);
			mainInfoForm.setWidth100();
			mainInfoForm.setNumCols(2);
			mainInfoForm.setTitleWidth(200);
			hLayout.addMember(mainInfoForm);

			orgNameInfo = new TextItem();
			orgNameInfo.setWidth("100%");
			orgNameInfo.setName("orgNameInfo");
			orgNameInfo.setCanEdit(false);
			orgNameInfo.setTitle(CallCenterBK.constants.orgName());
			orgNameInfo.setTextBoxStyle("BoldNoBorder");
			orgNameInfo.setValue(pRecord
					.getAttributeAsString("organization_name"));
			orgNameInfo.setCanFocus(false);

			orgAddressInfo = new TextItem();
			orgAddressInfo.setWidth("100%");
			orgAddressInfo.setName("orgAddressInfo");
			orgAddressInfo.setCanEdit(false);
			orgAddressInfo.setTitle(CallCenterBK.constants.realAddress());
			orgAddressInfo.setTextBoxStyle("BoldNoBorder");
			orgAddressInfo.setCanFocus(false);

			final Integer address_hide = pRecord
					.getAttributeAsInt("address_hide");

			if (address_hide != null && address_hide.equals(1)) {
				orgAddressInfo.setValue(CallCenterBK.constants.addressHide());
			} else {
				String full_address_not_hidden = pRecord
						.getAttributeAsString("full_address_not_hidden");
				orgAddressInfo.setValue(full_address_not_hidden);
			}
			mainInfoForm.setFields(orgNameInfo, orgAddressInfo);

			TabSet tabSet = new TabSet();
			tabSet.setWidth100();
			tabSet.setHeight100();

			Tab tabDetViewer = new Tab(CallCenterBK.constants.maininfo());

			VLayout addInfoLayout = new VLayout(5);
			addInfoLayout.setWidth100();
			addInfoLayout.setHeight100();

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			addInfoLayout.addMember(toolStrip);

			sendAddInfoSMS = new ToolStripButton(
					CallCenterBK.constants.orgSMSAddInfo(), "sms.png");
			sendAddInfoSMS.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(sendAddInfoSMS);

			sendStreetInfoSMS = new ToolStripButton(
					CallCenterBK.constants.orgSMSStreetInfo1(),
					"information.png");
			sendStreetInfoSMS.setLayoutAlign(Alignment.LEFT);
			toolStrip.addButton(sendStreetInfoSMS);

			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setCanSelectText(true);
			detailViewer.setDataSource(dataSource);
			detailViewer.setWidth100();
			detailViewer.setHeight100();
			detailViewer.selectRecord(pRecord);
			Record arr[] = new Record[1];
			arr[0] = pRecord;
			detailViewer.setData(arr);

			DetailViewerField org_name = new DetailViewerField(
					"organization_name", CallCenterBK.constants.orgName());
			DetailViewerField org_name_eng = new DetailViewerField(
					"organization_name_eng",
					CallCenterBK.constants.orgNameEng());
			DetailViewerField note = new DetailViewerField("remark",
					CallCenterBK.constants.comment());
			DetailViewerField director = new DetailViewerField("chief",
					CallCenterBK.constants.director());
			DetailViewerField founded = new DetailViewerField("found_date",
					CallCenterBK.constants.founded());
			founded.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

			DetailViewerField identcode = new DetailViewerField("ident_code",
					CallCenterBK.constants.identCode());
			DetailViewerField new_identcode = new DetailViewerField(
					"ident_code_new", CallCenterBK.constants.identCodeNew());
			DetailViewerField legaladdress = new DetailViewerField(
					"call_center_legal_address",
					CallCenterBK.constants.legalAddress());
			DetailViewerField workinghours = new DetailViewerField(
					"work_hours", CallCenterBK.constants.workinghours());
			DetailViewerField dayoffs = new DetailViewerField("day_offs_descr",
					CallCenterBK.constants.dayOffs());
			DetailViewerField org_info = new DetailViewerField(
					"additional_info", CallCenterBK.constants.orgInfo());
			DetailViewerField contactperson = new DetailViewerField(
					"contact_person", CallCenterBK.constants.contactPerson());
			DetailViewerField workpersoncountity = new DetailViewerField(
					"staff_count", CallCenterBK.constants.workPersonCountity());
			DetailViewerField ind = new DetailViewerField("organization_index",
					CallCenterBK.constants.index());
			DetailViewerField webaddress = new DetailViewerField("web_address",
					CallCenterBK.constants.webaddress());
			DetailViewerField mail = new DetailViewerField("email_address",
					CallCenterBK.constants.eMail());
			DetailViewerField legal_statuse = new DetailViewerField(
					"legal_form_desc", CallCenterBK.constants.legalStatuse());
			DetailViewerField partnerbank = new DetailViewerField(
					"concat_org_partner_banks",
					CallCenterBK.constants.partnerBank());
			DetailViewerField social_address = new DetailViewerField(
					"social_address", CallCenterBK.constants.socialAddress());

			DetailViewerField full_address = new DetailViewerField(
					"call_center_address",
					CallCenterBK.constants.fullOrgAddress());

			Integer important_remark = pRecord
					.getAttributeAsInt("important_remark");
			if (important_remark != null && important_remark.intValue() == -1) {
				note.setCellStyle("fontRedWithBorder");
			}

			detailViewer.setFields(org_name, org_name_eng, full_address, note,
					workinghours, dayoffs, director, contactperson,
					legal_statuse, org_info, identcode, new_identcode,
					webaddress, mail, social_address, ind, legaladdress,
					founded, partnerbank, workpersoncountity);

			addInfoLayout.addMember(detailViewer);
			tabDetViewer.setPane(addInfoLayout);

			Tab tabDeps = new Tab(CallCenterBK.constants.departments());

			VLayout depsContent = new VLayout(10);
			depsContent.setWidth100();
			depsContent.setHeight100();
			tabDeps.setPane(depsContent);

			searchDepsForm = new DynamicForm();
			searchDepsForm.setAutoFocus(true);
			searchDepsForm.setWidth100();
			searchDepsForm.setNumCols(8);

			depNameGeoItem = new TextItem();
			depNameGeoItem.setTitle(CallCenterBK.constants.orgName());
			depNameGeoItem.setWidth(300);
			depNameGeoItem.setName("dep_name_geo");

			depPhonesItem = new TextItem();
			depPhonesItem.setTitle(CallCenterBK.constants.phones());
			depPhonesItem.setWidth(100);
			depPhonesItem.setName("depPhonesItem");

			depAddressItem = new TextItem();
			depAddressItem.setTitle(CallCenterBK.constants.address());
			depAddressItem.setWidth(150);
			depAddressItem.setName("depAddressItem");

			depContPhonesItem = new CheckboxItem();
			depContPhonesItem.setTitle(CallCenterBK.constants.contPhoneShort());
			depContPhonesItem.setWidth(50);
			depContPhonesItem.setName("depContPhonesItem");

			searchDepsForm.setFields(depNameGeoItem, depAddressItem,
					depPhonesItem, depContPhonesItem);

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			HLayout formLayOut = new HLayout(5);
			formLayOut.setWidth100();
			formLayOut.setMembers(searchDepsForm, findButton, clearButton);
			depsContent.addMember(formLayOut);

			depsDataSource = DataSource.get("OrgDepartmentDS");
			depsTreeGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer parrent_department_id = countryRecord
							.getAttributeAsInt("parrent_department_id");
					if (parrent_department_id == null
							|| parrent_department_id.intValue() < 0) {
						return "font-weight: bold;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};
			depsTreeGrid.setWidth100();
			depsTreeGrid.setHeight100();
			Criteria criteria = new Criteria();
			criteria.setAttribute("organization_id",
					pRecord.getAttributeAsInt("organization_id"));
			depsTreeGrid.setCriteria(criteria);
			depsTreeGrid.setFetchOperation("orgDepartSearch");
			depsTreeGrid.setDataSource(depsDataSource);
			depsTreeGrid.setAutoFetchData(true);
			depsTreeGrid.setCanSort(false);
			depsTreeGrid.setCanResizeFields(false);
			depsTreeGrid.setCanDragSelectText(true);
			depsTreeGrid.setWrapCells(true);
			depsTreeGrid.setFixedRecordHeights(false);
			depsTreeGrid.setEmptyMessage(CallCenterBK.constants
					.depsNotFoundMessage());
			depsTreeGrid.setEmptyMessageStyle("redStyle");

			TreeGridField main_detail_geo = new TreeGridField("department",
					CallCenterBK.constants.department());

			TreeGridField main_detail_note_geo = new TreeGridField(
					"real_address_descr", CallCenterBK.constants.address(), 300);

			// TreeGridField p_phones = new TreeGridField("p_phones",
			// CallCenter.constants.phones(), 100);
			// p_phones.setCanFilter(true);

			// TreeGridField p_cont_phones = new TreeGridField("p_cont_phones",
			// CallCenter.constants.contPhoneShort(), 40);
			// p_cont_phones.setCanFilter(true);

			depsTreeGrid.setFields(main_detail_geo, main_detail_note_geo);
			depsContent.addMember(depsTreeGrid);

			sendDepInfoSMS = new IButton(CallCenterBK.constants.orgSMSDepInfo());
			sendDepInfoSMS.setWidth(200);
			sendDepInfoSMS.setIcon("sms.png");

			DynamicForm dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(false);
			dynamicForm.setWidth100();
			dynamicForm.setHeight100();
			dynamicForm.setTitleOrientation(TitleOrientation.TOP);

			orgNoteOnDep = new TextAreaItem();
			orgNoteOnDep.setWidth(645);
			orgNoteOnDep.setHeight(87);
			orgNoteOnDep.setName("orgNoteOnDep");
			orgNoteOnDep.setTitle(CallCenterBK.constants.comment());
			orgNoteOnDep.setCanEdit(false);
			orgNoteOnDep.setCanFocus(false);
			if (important_remark != null && important_remark.intValue() == -1) {
				orgNoteOnDep.setTextBoxStyle("fontRedAndBoldNoBorder");
			}
			orgNoteOnDep.setValue(pRecord.getAttributeAsString("remark"));

			dynamicForm.setFields(orgNoteOnDep);

			phonesGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer contact_phones = countryRecord
							.getAttributeAsInt("for_contact");
					if (contact_phones != null && contact_phones.equals(1)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			DataSource phoneDS = DataSource.get("OrgDepPhoneDS");

			phonesGrid.setAlternateRecordStyles(true);
			phonesGrid.setWidth(400);
			phonesGrid.setHeight(130);
			phonesGrid.setDataSource(phoneDS);
			phonesGrid.setAutoFetchData(false);
			phonesGrid.setCanEdit(false);
			phonesGrid.setCanRemoveRecords(false);
			phonesGrid.setFetchOperation("searchOrgDepPhones");
			phonesGrid.setCriteria(new Criteria());
			phonesGrid.setCanSort(false);
			phonesGrid.setCanResizeFields(false);
			phonesGrid.setShowFilterEditor(true);
			phonesGrid.setFilterOnKeypress(true);
			phonesGrid.setWrapCells(true);
			phonesGrid.setFixedRecordHeights(false);
			phonesGrid.setCanSelectText(true);
			phonesGrid.setCanDragSelectText(true);

			ListGridField dep_phone = new ListGridField("hid_phone",
					CallCenterBK.constants.phone());
			dep_phone.setCanFilter(true);

			ListGridField phone_state = new ListGridField("phone_state_descr",
					CallCenterBK.constants.phoneState(), 100);
			phone_state.setCanFilter(false);

			phonesGrid.setFields(dep_phone, phone_state);

			HLayout depDalyOut = new HLayout(10);
			depDalyOut.setWidth100();
			depDalyOut.setHeight(100);

			VLayout layout = new VLayout(5);
			layout.setWidth100();
			layout.setHeight100();
			layout.setMembers(sendDepInfoSMS, dynamicForm);

			depDalyOut.setMembers(phonesGrid, layout);

			depsContent.addMember(depDalyOut);

			Tab tabOrgTree = new Tab(CallCenterBK.constants.mainIerarch());

			VLayout orgTreeLayOut = new VLayout(10);
			orgTreeLayOut.setWidth100();
			orgTreeLayOut.setHeight100();

			DataSource dataSourceTree = DataSource.get("OrgDS");

			listGridOrgTreeParrent = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer statuse = countryRecord.getAttributeAsInt("status");
					Integer extra_priority = countryRecord
							.getAttributeAsInt("super_priority");
					if (extra_priority != null && extra_priority.equals(-1)) {
						return "color:red;";
					} else if (statuse != null && statuse.equals(2)) {
						return "color:gray;";
					} else if (statuse != null && statuse.equals(1)) {
						return "color:blue;";
					} else if (statuse != null && statuse.equals(3)) {
						return "color:green;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			Integer parrent_organization_id = record
					.getAttributeAsInt("parrent_organization_id");
			if (parrent_organization_id == null
					|| parrent_organization_id.equals(0)) {
				// parrent_organization_id = record
				// .getAttributeAsInt("parrent_organization_id");
				parrent_organization_id = -1;
			}

			Criteria criteriaInner1 = new Criteria();
			criteriaInner1.setAttribute("organization_id",
					parrent_organization_id);
			listGridOrgTreeParrent.setCriteria(criteriaInner1);
			listGridOrgTreeParrent.setWidth100();
			listGridOrgTreeParrent.setHeight(70);
			listGridOrgTreeParrent.setAlternateRecordStyles(true);
			listGridOrgTreeParrent.setDataSource(dataSourceTree);
			listGridOrgTreeParrent.setAutoFetchData(true);
			listGridOrgTreeParrent.setCanEdit(false);
			listGridOrgTreeParrent.setCanRemoveRecords(false);
			listGridOrgTreeParrent
					.setFetchOperation("customOrgSearchForCallCenterNew");
			listGridOrgTreeParrent.setCanSort(false);
			listGridOrgTreeParrent.setCanResizeFields(false);
			listGridOrgTreeParrent.setWrapCells(true);
			listGridOrgTreeParrent.setFixedRecordHeights(false);
			listGridOrgTreeParrent.setCanDragSelectText(true);
			listGridOrgTreeParrent.setEmptyMessage(CallCenterBK.constants
					.orgParrentsNotFound());

			ListGridField org_nameTree1 = new ListGridField(
					"organization_name", CallCenterBK.constants.orgName());

			ListGridField real_addressTree1 = new ListGridField(
					"call_center_address",
					CallCenterBK.constants.realAddress(), 400);

			listGridOrgTreeParrent.setFields(org_nameTree1, real_addressTree1);
			orgTreeLayOut.addMember(listGridOrgTreeParrent);

			listGridOrgTreeChilds = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer statuse = countryRecord.getAttributeAsInt("status");
					Integer extra_priority = countryRecord
							.getAttributeAsInt("extra_priority");
					if (extra_priority != null && extra_priority.equals(-1)) {
						return "color:red;";
					} else if (statuse != null && statuse.equals(2)) {
						return "color:gray;";
					} else if (statuse != null && statuse.equals(1)) {
						return "color:blue;";
					} else if (statuse != null && statuse.equals(3)) {
						return "color:green;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			Integer organization_id = record
					.getAttributeAsInt("organization_id");
			if (organization_id == null || organization_id.equals(0)) {
				// organization_id =
				// record.getAttributeAsInt("organization_id");
				organization_id = -1;
			}

			Criteria criteriaInner = new Criteria();
			criteriaInner.setAttribute("parrent_organization_id",
					organization_id);
			listGridOrgTreeChilds.setCriteria(criteriaInner);
			listGridOrgTreeChilds.setWidth100();
			listGridOrgTreeChilds.setHeight100();
			listGridOrgTreeChilds.setAlternateRecordStyles(true);
			listGridOrgTreeChilds.setDataSource(dataSourceTree);
			listGridOrgTreeChilds.setAutoFetchData(true);
			listGridOrgTreeChilds.setCanEdit(false);
			listGridOrgTreeChilds.setCanRemoveRecords(false);
			listGridOrgTreeChilds
					.setFetchOperation("customOrgSearchForCallCenterNew");
			listGridOrgTreeChilds.setCanSort(false);
			listGridOrgTreeChilds.setCanResizeFields(false);
			listGridOrgTreeChilds.setWrapCells(true);
			listGridOrgTreeChilds.setFixedRecordHeights(false);
			listGridOrgTreeChilds.setShowFilterEditor(true);
			listGridOrgTreeChilds.setFilterOnKeypress(true);
			listGridOrgTreeChilds.setCanDragSelectText(true);
			listGridOrgTreeChilds.setEmptyMessage(CallCenterBK.constants
					.orgChildsNotFound());

			ListGridField org_nameTree = new ListGridField("organization_name",
					CallCenterBK.constants.orgName());
			org_nameTree.setCanFilter(true);

			ListGridField real_addressTree = new ListGridField(
					"call_center_address",
					CallCenterBK.constants.realAddress(), 400);
			real_addressTree.setCanFilter(true);

			listGridOrgTreeChilds.setFields(org_nameTree, real_addressTree);
			orgTreeLayOut.addMember(listGridOrgTreeChilds);

			tabOrgTree.setPane(orgTreeLayOut);

			tabSet.setTabs(tabDeps, tabDetViewer, tabOrgTree);
			if (viewMainInfoFirst) {
				tabSet.selectTab(tabDetViewer);
			} else {
				tabSet.selectTab(tabDeps);
			}

			hLayout.addMember(tabSet);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroyDlg();
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					depNameGeoItem.clearValue();
					depAddressItem.clearValue();
					depPhonesItem.clearValue();
					depContPhonesItem.clearValue();
				}
			});
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchDeps();
				}
			});
			depNameGeoItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						searchDeps();
					}
				}
			});

			depAddressItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						searchDeps();
					}
				}
			});
			depPhonesItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						searchDeps();
					}
				}
			});

			depsTreeGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = depsTreeGrid
									.getSelectedRecord();
							depNameGeoItem.setValue(listGridRecord
									.getAttributeAsString("main_detail_geo_orig"));
							depNameGeoItem.focusInItem();
						}
					});
			depsTreeGrid
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
							phonesGrid.invalidateCache();
							phonesGrid.filterData(criteria, new DSCallback() {
								@Override
								public void execute(DSResponse response,
										Object rawData, DSRequest request) {
								}
							}, dsRequest);
						}
					});

			sendDepInfoSMS.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					sendDepartInfoSMS();
				}
			});

			sendAddInfoSMS.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					sendAddInfoSMS();
				}
			});

			addCloseClickHandler(new CloseClickHandler() {
				@Override
				public void onCloseClick(CloseClickEvent event) {
					destroyDlg();
				}
			});

			addItem(hLayout);
			logMainHit();
			sendStreetInfoSMS.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					if (address_hide != null && address_hide.equals(1)) {
						SC.say(CallCenterBK.constants.addressHide());
						return;
					}

					DlgViewStreetInfo dlgViewStreetInfo = new DlgViewStreetInfo(
							record, dataSource, chargePanel);
					dlgViewStreetInfo.show();
				}
			});

			// refreshBtn.addClickHandler(new ClickHandler() {
			// @Override
			// public void onClick(ClickEvent event) {
			// searchOrgTree();
			// }
			// });

			listGridOrgTreeChilds
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = listGridOrgTreeChilds
									.getSelectedRecord();
							if (listGridRecord == null) {
								return;
							}
							Integer organization_id = listGridRecord
									.getAttributeAsInt("organization_id");
							if (organization_id == null) {
								return;
							}
							Integer cOrganization_id = pRecord
									.getAttributeAsInt("organization_id");
							if (cOrganization_id == null) {
								return;
							}
							if (cOrganization_id.equals(organization_id)) {
								SC.say(CallCenterBK.constants.warning(),
										CallCenterBK.constants
												.orgAlreadyOpened());
								return;
							}

							DataSource dataSource = DataSource.get("OrgDS");
							DlgViewOrg dlgViewOrg = new DlgViewOrg(dataSource,
									listGridRecord);
							dlgViewOrg.show();
						}
					});

			listGridOrgTreeParrent
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = listGridOrgTreeParrent
									.getSelectedRecord();
							if (listGridRecord == null) {
								return;
							}
							Integer organization_id = listGridRecord
									.getAttributeAsInt("organization_id");
							if (organization_id == null) {
								return;
							}
							Integer cOrganization_id = pRecord
									.getAttributeAsInt("organization_id");
							if (cOrganization_id == null) {
								return;
							}
							if (cOrganization_id.equals(organization_id)) {
								SC.say(CallCenterBK.constants.warning(),
										CallCenterBK.constants
												.orgAlreadyOpened());
								return;
							}

							DataSource dataSource = DataSource.get("OrgDS");
							DlgViewOrg dlgViewOrg = new DlgViewOrg(dataSource,
									listGridRecord);
							dlgViewOrg.show();
						}
					});

			// phonesGrid.addRecordDoubleClickHandler(new
			// RecordDoubleClickHandler()
			// {
			// @Override
			// public void onRecordDoubleClick(RecordDoubleClickEvent event) {
			// ListGridRecord listGridRecord = phonesGrid.getSelectedRecord();
			// if (listGridRecord == null) {
			// return;
			// }
			// String dep_phone =
			// listGridRecord.getAttributeAsString("dep_phone");
			// if (dep_phone == null || dep_phone.trim().equals("")) {
			// return;
			// }
			// String txt = CallCenter.constants.phone() + " : " + dep_phone;
			// SC.say(CallCenter.constants.info(), txt);
			// }
			// });
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void destroyDlg() {
		try {
			destroy();
			// final DlgViewOrg dlgViewOrg = this;
			// ServerSession serverSession = CommonSingleton.getInstance()
			// .getServerSession();
			// if (serverSession == null || serverSession.isWebSession()) {
			// destroy();
			// return;
			// }
			// String phone = serverSession.getPhone();
			// if (phone == null || phone.trim().equalsIgnoreCase("")) {
			// destroy();
			// return;
			// }
			// boolean isMobile = serverSession.isPhoneIsMobile();
			// if (isMobile) {
			// if (!smsSend) {
			// SC.ask(CallCenter.constants.warning(),
			// CallCenter.constants.smsDidNotSent(),
			// new BooleanCallback() {
			// @Override
			// public void execute(Boolean value) {
			// if (value) {
			// dlgViewOrg.destroy();
			// }
			// }
			// });
			// } else {
			// destroy();
			// }
			// } else {
			// int chCount = chargePanel.getChrgCounter();
			// if (chCount <= 0) {
			// SC.ask(CallCenter.constants.warning(),
			// CallCenter.constants.chargeDidNotMade(),
			// new BooleanCallback() {
			// @Override
			// public void execute(Boolean value) {
			// if (value) {
			// dlgViewOrg.destroy();
			// }
			// }
			// });
			// } else {
			// destroy();
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void logMainHit() {
		try {
			boolean flag = true;
			if (flag) {
				// TODO LOOOOOOOOOOOOOOOGGGGGGGGGGG
				return;
			}
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				return;
			}
			Integer ym = serverSession.getYearMonth();

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id",
					Constants.serviceOrganization);
			recordParam.setAttribute("ym", ym);
			recordParam
					.setAttribute("session_id", serverSession.getSessionId());
			recordParam.setAttribute("organization_id",
					record.getAttributeAsInt("organization_id"));

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "LogMainHits");
			logSessChDS.addData(recordParam, new DSCallback() {
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

	private void sendAddInfoSMS() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.phoneIsNotMobile());
				return;
			}
			CanvasDisableTimer.addCanvasClickTimer(sendAddInfoSMS);
			StringBuilder sms_text = new StringBuilder();

			String org_name = record.getAttributeAsString("organization_name");
			sms_text.append(org_name).append(". ");

			String director = record.getAttributeAsString("chief");
			if (director != null && !director.trim().equalsIgnoreCase("")) {
				sms_text.append("director: ").append(director).append("; ");
			}

			String workinghours = record.getAttributeAsString("work_hours");
			if (workinghours != null
					&& !workinghours.trim().equalsIgnoreCase("")) {
				sms_text.append("saatebi: ").append(workinghours).append(";");
			}
			String dayoffs = record.getAttributeAsString("day_offs_descr");
			if (dayoffs != null && !dayoffs.trim().equalsIgnoreCase("")) {
				sms_text.append("dasveneba: ").append(dayoffs).append(";");
			}
			String mail = record.getAttributeAsString("email_address");
			if (mail != null && !mail.trim().equalsIgnoreCase("")) {
				sms_text.append("mail: ");
				sms_text.append(mail).append("; ");
			}
			String webaddress = record.getAttributeAsString("web_address");

			if (webaddress != null && !webaddress.trim().equals("")) {
				sms_text.append("web: ").append(webaddress).append("; ");
			}

			String new_identcode = record
					.getAttributeAsString("ident_code_new");
			if (new_identcode == null || new_identcode.trim().equals("")) {
				new_identcode = record.getAttributeAsString("ident_code");
			}
			if (new_identcode != null && !new_identcode.trim().equals("")) {
				sms_text.append("s/k: ").append(new_identcode);
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id",
					Constants.serviceOrganization);
			recordParam
					.setAttribute("session_id", serverSession.getSessionId());
			recordParam.setAttribute("sms_text", sms_text.toString());
			recordParam.setAttribute("phone", phone);
			recordParam.setAttribute("rec_user", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "LogSMS");
			logSessChDS.addData(recordParam, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					// smsSend = true;
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sendDepartInfoSMS() {
		try {
			ListGridRecord listGridRecord = depsTreeGrid.getSelectedRecord();
			if (listGridRecord == null) {
				SC.say(CallCenterBK.constants.plzSelectDepart());
				return;
			}

			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.phoneIsNotMobile());
				return;
			}
			CanvasDisableTimer.addCanvasClickTimer(sendDepInfoSMS);
			StringBuilder sms_text = new StringBuilder();
			String legal_statuse = record
					.getAttributeAsString("legal_form_desc");
			if (legal_statuse != null
					&& !legal_statuse.trim().equalsIgnoreCase("")) {
				sms_text.append(legal_statuse).append(" ");
			}
			String org_name = record.getAttributeAsString("organization_name");
			sms_text.append(org_name).append("; ");

			String mainDetail = listGridRecord
					.getAttributeAsString("department");

			if (mainDetail != null && !mainDetail.trim().equalsIgnoreCase("")) {
				if (mainDetail.equals(CallCenterBK.constants.common())) {
					sms_text.append(CallCenterBK.constants.phoneShort());
				} else {
					sms_text.append(mainDetail).append(" ");
				}
			}
			Integer town_id = record.getAttributeAsInt("town_id");
			RecordList recordList = phonesGrid.getDataAsRecordList();
			int length = recordList.getLength();
			if (length > 0) {
				StringBuilder phones = new StringBuilder("");
				for (int i = 0; i < length; i++) {
					Record record = recordList.get(i);
					String dep_phone = record.getAttributeAsString("hid_phone");
					if (dep_phone == null
							|| dep_phone.trim().equalsIgnoreCase("")) {
						continue;
					}
					if (dep_phone.equals(CallCenterBK.constants.moxs())
							|| dep_phone.equals(CallCenterBK.constants.daf())) {
						continue;
					}
					if (dep_phone.startsWith("2")
							&& town_id.equals(Constants.defCityTbilisiId)) {
						dep_phone = "032" + dep_phone;
					} else if (dep_phone.startsWith("79")) {
						dep_phone = "0" + dep_phone;
					}
					phones.append(dep_phone).append(";").append(" ");
				}
				if (phones != null && phones.toString().length() > 2) {
					sms_text.append(phones.toString());
				}
			}

			String real_address = record
					.getAttributeAsString("call_center_address");

			if (real_address != null
					&& !real_address.trim().equalsIgnoreCase("")) {
				sms_text.append("mis:").append(real_address).append("; ");
			}

			String mail = record.getAttributeAsString("email_address");
			if (mail != null && !mail.trim().equalsIgnoreCase("")) {
				sms_text.append("mail: ");
				sms_text.append(mail).append("; ");
			}

			String webaddress = record.getAttributeAsString("web_address");

			if (webaddress != null && !webaddress.trim().equals("")) {
				sms_text.append("web: ");
				sms_text.append(webaddress).append("; ");
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("service_id", Constants.serviceOrganization);
			record.setAttribute("session_id", serverSession.getSessionId());
			record.setAttribute("sms_text", sms_text.toString());
			record.setAttribute("phone", phone);
			record.setAttribute("organization_id",
					this.record.getAttributeAsInt("organization_id"));
			record.setAttribute("rec_user", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "LogSMS");
			logSessChDS.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					// smsSend = true;
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void searchDeps() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("organization_id",
					record.getAttributeAsInt("organization_id"));
			String depName = depNameGeoItem.getValueAsString();
			if (depName != null && !depName.trim().equalsIgnoreCase("")) {

				String tmp = depName.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					criteria.setAttribute("department" + i, item);
					i++;
				}

			} else {
				criteria.setAttribute("department", "");
			}

			String main_detail_note_geo = depAddressItem.getValueAsString();
			if (main_detail_note_geo != null
					&& !main_detail_note_geo.trim().equals("")) {
				criteria.setAttribute("main_detail_note_geo",
						main_detail_note_geo);
			}

			String phone = depPhonesItem.getValueAsString();
			if (phone != null && !phone.trim().equals("")) {
				criteria.setAttribute("phone", phone);
			}

			Boolean for_contact = depContPhonesItem.getValueAsBoolean();
			if (for_contact != null && for_contact) {
				criteria.setAttribute("for_contact", for_contact == true ? 1
						: 0);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "orgDepartSearch");
			depsTreeGrid.invalidateCache();
			depsTreeGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	public ChargePanel getChargePanel() {
		return chargePanel;
	}
}
