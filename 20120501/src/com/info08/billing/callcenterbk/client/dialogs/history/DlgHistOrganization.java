package com.info08.billing.callcenterbk.client.dialogs.history;

import java.util.Date;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.GroupNode;
import com.smartgwt.client.widgets.grid.GroupTitleRenderer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgHistOrganization extends Window {

	private VLayout hLayout;
	private DynamicForm searchForm;

	// private DateItem histStartDateItem;
	// private DateItem histEndDateItem;

	private TextItem orgNameItem;
	private TextItem remarkItem;
	private TextItem departmentNameItem;
	private TextItem identCodeItem;

	private TextItem phoneItem;
	private TextItem streetItem;
	private TextItem districtItem;
	private TextItem townItem;

	private IButton findButton;
	private IButton clearButton;
	private IButton histOrgButton;

	private ListGrid histOrganizaionListGrid;
	private ListGrid partnerBankListGrid;
	private ListGrid activitiesListGrid;
	private ListGrid departmentListGrid;
	private ListGrid phonesListGrid;
	// private ListGrid histSubscriberPhoneListGrid;
	private DataSource OrgDS;
	private DataSource OrgDepartmentDS;
	private DataSource OrgDepPhoneDS;

	private TabSet topTabSet;
	private DetailViewer detailViewer;

	public DlgHistOrganization(final Record abonentRecord) {
		try {
			setWidth(1260);
			setHeight(730);
			setTitle("აბონენტის ინსტორია");
			setShowMinimizeButton(false);
			setIsModal(true);
			setShowModalMask(true);
			setCanDrag(false);
			setCanDragReposition(false);
			setCanDragResize(false);
			setCanDragScroll(false);
			centerInPage();

			boolean hasPermission = CommonSingleton.getInstance()
					.hasPermission("106500");

			hLayout = new VLayout(5);
			hLayout.setWidth100();
			hLayout.setHeight100();
			hLayout.setPadding(10);

			Date startDate = new Date();
			CalendarUtil.addMonthsToDate(startDate, -1);

			OrgDS = DataSource.get("OrgDS");
			OrgDepartmentDS = DataSource.get("OrgDepartmentDS");
			OrgDepPhoneDS = DataSource.get("OrgDepPhoneDS");

			orgNameItem = new TextItem();
			orgNameItem.setTitle(CallCenterBK.constants.orgName());
			orgNameItem.setName("orgNameItem");
			orgNameItem.setWidth(200);

			remarkItem = new TextItem();
			remarkItem.setTitle(CallCenterBK.constants.comment());
			remarkItem.setName("remarkItem");
			remarkItem.setWidth(200);

			departmentNameItem = new TextItem();
			departmentNameItem.setTitle(CallCenterBK.constants.department());
			departmentNameItem.setName("departmentNameItem");
			departmentNameItem.setWidth(200);

			identCodeItem = new TextItem();
			identCodeItem.setTitle(CallCenterBK.constants.identCodeAndNew());
			identCodeItem.setName("identCodeItem");
			identCodeItem.setWidth(200);

			townItem = new TextItem();
			townItem.setTitle(CallCenterBK.constants.town());
			townItem.setName("townItem");
			townItem.setWidth(200);

			streetItem = new TextItem();
			streetItem.setTitle(CallCenterBK.constants.street());
			streetItem.setName("streetItem");
			streetItem.setWidth(200);

			phoneItem = new TextItem();
			phoneItem.setTitle("ტელ.ნომერი");
			phoneItem.setName("phoneItem");
			phoneItem.setWidth(200);

			districtItem = new TextItem();
			districtItem.setTitle(CallCenterBK.constants.district());
			districtItem.setName("districtItem");
			districtItem.setWidth(200);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth100();
			searchForm.setNumCols(8);
			// searchForm.setColWidths("25%", "25%", "25%", "25%");
			searchForm.setPadding(5);
			searchForm.setTitleOrientation(TitleOrientation.LEFT);

			searchForm.setFields(orgNameItem, remarkItem, departmentNameItem,
					identCodeItem, streetItem, townItem, districtItem,
					phoneItem);

			searchForm.setBorder("1px solid #CCC");

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.LEFT);

			VLayout spacesLayout = new VLayout();
			spacesLayout.setWidth100();

			histOrgButton = new IButton();
			histOrgButton.setTitle("კონკრეტული ორგანიზაციის ისტორია");
			histOrgButton.setIcon("date.png");
			histOrgButton.setWidth(200);

			histOrgButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = null;
					listGridRecord = histOrganizaionListGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("მონიშნეთ ჩანაწერი ცხრილში");
						return;
					}
					searchBySubscriber(listGridRecord);
				}

			});

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			buttonLayout.setMembers(histOrgButton, spacesLayout, findButton,
					clearButton);

			histOrganizaionListGrid = new ListGrid() {
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					String rdeleted = record.getAttribute("rdeleted");
					if (rdeleted != null)
						if (rdeleted.trim().equals("true"))
							return "color:red;";
						else
							return super.getCellCSSText(record, rowNum, colNum);
					else
						return super.getCellCSSText(record, rowNum, colNum);
				}
			};
			histOrganizaionListGrid.setWidth100();
			histOrganizaionListGrid.setHeight(200);
			histOrganizaionListGrid.setAlternateRecordStyles(true);
			histOrganizaionListGrid.setDataSource(OrgDS);
			histOrganizaionListGrid.setAutoFetchData(false);
			histOrganizaionListGrid.setShowFilterEditor(false);
			histOrganizaionListGrid.setCanEdit(false);
			histOrganizaionListGrid.setCanRemoveRecords(false);
			histOrganizaionListGrid.setFetchOperation("histSearch");
			histOrganizaionListGrid.setCanSort(false);
			histOrganizaionListGrid.setCanResizeFields(false);
			histOrganizaionListGrid.setWrapCells(true);
			histOrganizaionListGrid.setFixedRecordHeights(false);
			histOrganizaionListGrid.setCanDragSelectText(true);

			histOrganizaionListGrid.setGroupStartOpen(GroupStartOpen.ALL);
			histOrganizaionListGrid.setGroupByField("organization_id");

			ListGridField organization_id = new ListGridField(
					"organization_id", "", 100);

			organization_id.setGroupTitleRenderer(new GroupTitleRenderer() {

				@Override
				public String getGroupTitle(Object groupValue,
						GroupNode groupNode, ListGridField field,
						String fieldName, ListGrid grid) {
					return "ორგანიზაცია";
				}
			});
			organization_id.setHidden(true);
			ListGridField l_original_org_name = new ListGridField(
					"original_org_name", "დასახელება", 400);
			l_original_org_name.setAlign(Alignment.LEFT);

			ListGridField l_full_address_not_hidden = new ListGridField(
					"full_address_not_hidden", "მისამართი");
			l_full_address_not_hidden.setAlign(Alignment.LEFT);

			ListGridField hist_user_on = new ListGridField("on_user", "დაამატა");
			hist_user_on.setWidth(50);

			ListGridField hist_user_off = new ListGridField("off_user",
					"შეცვალა");
			hist_user_off.setWidth(50);
			ListGridField hist_start = new ListGridField("hist_start_date",
					"-დან");
			hist_start.setWidth(120);

			ListGridField hist_end = new ListGridField("hist_end_date", "-მდე");
			hist_end.setWidth(120);

			ListGridField ph_deleted_org = new ListGridField("rdeleted",
					"წაშლილი");
			ph_deleted_org.setWidth(70);
			ph_deleted_org.setType(ListGridFieldType.BOOLEAN);

			if (hasPermission) {
				histOrganizaionListGrid.setFields(organization_id,
						l_original_org_name, l_full_address_not_hidden,
						hist_user_on, hist_start, hist_user_off, hist_end,
						ph_deleted_org);
			} else {
				histOrganizaionListGrid.setFields(organization_id,
						l_original_org_name, l_full_address_not_hidden,
						hist_start, hist_end, ph_deleted_org);
			}

			findButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					search();

				}
			});

			KeyPressHandler keyPressHandler = new KeyPressHandler() {

				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}

				}
			};

			orgNameItem.addKeyPressHandler(keyPressHandler);
			remarkItem.addKeyPressHandler(keyPressHandler);
			departmentNameItem.addKeyPressHandler(keyPressHandler);
			streetItem.addKeyPressHandler(keyPressHandler);
			phoneItem.addKeyPressHandler(keyPressHandler);
			districtItem.addKeyPressHandler(keyPressHandler);

			histOrganizaionListGrid.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (histOrganizaionListGrid.getSelectedRecord() != null) {
						searchBankPartners();
						searchActivities();
						searchDepartments();

						detailViewer.viewSelectedData(histOrganizaionListGrid);
					}
				}
			});

			partnerBankListGrid = new ListGrid() {
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					String rdeleted = record.getAttribute("rdeleted");
					if (rdeleted != null)
						if (rdeleted.trim().equals("true"))
							return "color:red;";
						else
							return super.getCellCSSText(record, rowNum, colNum);
					else
						return super.getCellCSSText(record, rowNum, colNum);
				}
			};
			partnerBankListGrid.setWidth100();
			partnerBankListGrid.setHeight("50%");
			partnerBankListGrid.setAlternateRecordStyles(true);
			partnerBankListGrid.setDataSource(OrgDS);
			partnerBankListGrid.setAutoFetchData(false);
			partnerBankListGrid.setShowFilterEditor(false);
			partnerBankListGrid.setCanEdit(false);
			partnerBankListGrid.setCanRemoveRecords(false);
			partnerBankListGrid.setFetchOperation("searchOrgPartnerBanksHist");
			partnerBankListGrid.setCanSort(false);
			partnerBankListGrid.setCanResizeFields(false);
			partnerBankListGrid.setWrapCells(true);
			partnerBankListGrid.setFixedRecordHeights(false);
			partnerBankListGrid.setCanDragSelectText(true);

			ListGridField partnior_bank_name = new ListGridField(
					"organization_name", "პარტნიორი ბანკი");
			partnior_bank_name.setAlign(Alignment.LEFT);

			ListGridField pb_hist_user_on = new ListGridField("on_user",
					"დაამატა");
			pb_hist_user_on.setWidth(50);

			ListGridField pb_hist_user_off = new ListGridField("off_user",
					"შეცვალა");
			pb_hist_user_off.setWidth(50);
			ListGridField pb_hist_start = new ListGridField("hist_start_date",
					"-დან");
			pb_hist_start.setWidth(120);

			ListGridField pb_hist_end = new ListGridField("hist_end_date",
					"-მდე");
			pb_hist_end.setWidth(120);

			ListGridField ph_deleted_par = new ListGridField("rdeleted",
					"წაშლილი");
			ph_deleted_par.setWidth(70);
			ph_deleted_par.setType(ListGridFieldType.BOOLEAN);

			if (hasPermission) {
				partnerBankListGrid.setFields(partnior_bank_name,
						pb_hist_user_on, pb_hist_start, pb_hist_user_off,
						pb_hist_end, ph_deleted_par);
			} else {
				partnerBankListGrid.setFields(partnior_bank_name,
						pb_hist_start, pb_hist_end, ph_deleted_par);
			}

			activitiesListGrid = new ListGrid() {
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					String rdeleted = record.getAttribute("rdeleted");
					if (rdeleted != null)
						if (rdeleted.trim().equals("true"))
							return "color:red;";
						else
							return super.getCellCSSText(record, rowNum, colNum);
					else
						return super.getCellCSSText(record, rowNum, colNum);
				}
			};
			activitiesListGrid.setWidth100();
			activitiesListGrid.setHeight("50%");
			activitiesListGrid.setAlternateRecordStyles(true);
			activitiesListGrid.setDataSource(OrgDS);
			activitiesListGrid.setAutoFetchData(false);
			activitiesListGrid.setShowFilterEditor(false);
			activitiesListGrid.setCanEdit(false);
			activitiesListGrid.setCanRemoveRecords(false);
			activitiesListGrid.setFetchOperation("searchOrgAcctivitiesHist");
			activitiesListGrid.setCanSort(false);
			activitiesListGrid.setCanResizeFields(false);
			activitiesListGrid.setWrapCells(true);
			activitiesListGrid.setFixedRecordHeights(false);
			activitiesListGrid.setCanDragSelectText(true);

			ListGridField org_acctivity_name = new ListGridField(
					"org_acctivity_name", "საქმიანობა");
			partnior_bank_name.setAlign(Alignment.LEFT);

			ListGridField acct_hist_user_on = new ListGridField("on_user",
					"დაამატა");
			acct_hist_user_on.setWidth(50);

			ListGridField acct_hist_user_off = new ListGridField("off_user",
					"შეცვალა");
			acct_hist_user_off.setWidth(50);
			ListGridField acct_hist_start = new ListGridField(
					"hist_start_date", "-დან");
			acct_hist_start.setWidth(120);

			ListGridField acct_hist_end = new ListGridField("hist_end_date",
					"-მდე");
			acct_hist_end.setWidth(120);

			ListGridField ph_deleted_acc = new ListGridField("rdeleted",
					"წაშლილი");
			ph_deleted_acc.setWidth(70);
			ph_deleted_acc.setType(ListGridFieldType.BOOLEAN);

			if (hasPermission) {
				activitiesListGrid.setFields(org_acctivity_name,
						acct_hist_user_on, acct_hist_start, acct_hist_user_off,
						acct_hist_end, ph_deleted_acc);
			} else {
				activitiesListGrid.setFields(org_acctivity_name,
						acct_hist_start, acct_hist_end, ph_deleted_acc);
			}

			OrgDS.getField("original_org_name").setTitle(
					CallCenterBK.constants.organization());
			OrgDS.getField("full_address_not_hidden").setTitle(
					CallCenterBK.constants.realAddress());
			OrgDS.getField("priority").setTitle("priority");
			// OrgDS.getField("dob_descr").setTitle("დაბადების თარიღი");
			// OrgDS.getField("department_name").setTitle("განყოფილება");
			// OrgDS.getField("remark").setTitle("კომენტარი");
			// OrgDS.getField("start_date_descr").setTitle("დაწყების თარიღი");
			// OrgDS.getField("gender_descr").setTitle("სქესი");

			detailViewer = new DetailViewer();
			detailViewer.setCanSelectText(true);
			detailViewer.setDataSource(OrgDS);
			DetailViewerField original_org_name = new DetailViewerField(
					"original_org_name", CallCenterBK.constants.organization());

			DetailViewerField full_address_not_hiddenDF = new DetailViewerField(
					"full_address_not_hidden",
					CallCenterBK.constants.realAddress());

			DetailViewerField priority = new DetailViewerField(
					"priority_descr", "გაწითლებული");

			DetailViewerField super_priority_descr = new DetailViewerField(
					"priority_descr", "პრიორიტეტი");

			DetailViewerField ident_code = new DetailViewerField("ident_code",
					"საიდენტიფიკაციო კოდი");

			DetailViewerField ident_code_new = new DetailViewerField(
					"ident_code_new", "საიდენტიფიკაციო კოდი (ახალი)");

			DetailViewerField chief = new DetailViewerField("chief",
					CallCenterBK.constants.director());

			DetailViewerField remark = new DetailViewerField("remark",
					CallCenterBK.constants.comment());

			DetailViewerField work_hours = new DetailViewerField("work_hours",
					CallCenterBK.constants.workinghours());

			DetailViewerField found_date = new DetailViewerField("found_date",
					CallCenterBK.constants.orgFoundDateStart());

			DetailViewerField email_address = new DetailViewerField(
					"email_address", CallCenterBK.constants.eMail());

			DetailViewerField additional_info = new DetailViewerField(
					"additional_info", "დამატებითი ინფორმაცია");

			DetailViewerField web_address = new DetailViewerField(
					"web_address", CallCenterBK.constants.webaddress());

			DetailViewerField contact_person = new DetailViewerField(
					"contact_person", "საკონტაქტო პირი");

			DetailViewerField day_offs_descr = new DetailViewerField(
					"day_offs_descr", CallCenterBK.constants.dayOffs());

			DetailViewerField staff_count = new DetailViewerField(
					"staff_count", "თანამშრომლების რაოდენობა");

			detailViewer.setFields(original_org_name,
					full_address_not_hiddenDF, priority, super_priority_descr,
					ident_code, ident_code_new, chief, remark, work_hours,
					found_date, email_address, additional_info, web_address,
					contact_person, day_offs_descr, staff_count);

			detailViewer.setWidth100();
			detailViewer.setHeight100();

			VLayout orgDetLayout = new VLayout();
			orgDetLayout.setWidth100();
			orgDetLayout.setHeight100();
			orgDetLayout.setMembers(partnerBankListGrid, activitiesListGrid);

			HLayout tabOrgHistLayout = new HLayout();
			tabOrgHistLayout.setWidth100();
			tabOrgHistLayout.setHeight100();

			HLayout leftLayout = new HLayout();
			leftLayout.setHeight100();
			leftLayout.setWidth100();

			// VLayout rigthLayout = new VLayout();
			// rigthLayout.setHeight100();

			leftLayout.setMembers(detailViewer, orgDetLayout);

			// rigthLayout.addMembers();

			tabOrgHistLayout.setMembers(leftLayout);// , rigthLayout);

			topTabSet = new TabSet();
			topTabSet.setTabBarPosition(Side.TOP);
			topTabSet.setWidth100();
			topTabSet.setHeight100();

			Tab tabMainInfo = new Tab("ორგანიზაციის ისტორია");
			tabMainInfo.setPane(tabOrgHistLayout);
			topTabSet.addTab(tabMainInfo);

			VLayout tabDepHistLayout = new VLayout();
			tabDepHistLayout.setWidth100();
			tabDepHistLayout.setHeight100();

			departmentListGrid = new ListGrid() {
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					String rdeleted = record.getAttribute("rdeleted");
					if (rdeleted != null)
						if (rdeleted.trim().equals("true"))
							return "color:red;";
						else
							return super.getCellCSSText(record, rowNum, colNum);
					else
						return super.getCellCSSText(record, rowNum, colNum);
				}
			};
			departmentListGrid.setWidth100();
			departmentListGrid.setHeight("50%");
			departmentListGrid.setAlternateRecordStyles(true);
			departmentListGrid.setDataSource(OrgDepartmentDS);
			departmentListGrid.setAutoFetchData(false);
			departmentListGrid.setShowFilterEditor(false);
			departmentListGrid.setCanEdit(false);
			departmentListGrid.setCanRemoveRecords(false);
			departmentListGrid.setFetchOperation("searchOrgDepartmentHist");
			departmentListGrid.setCanSort(false);
			departmentListGrid.setCanResizeFields(false);
			departmentListGrid.setWrapCells(true);
			departmentListGrid.setFixedRecordHeights(false);
			departmentListGrid.setCanDragSelectText(true);

			ListGridField department = new ListGridField("department",
					"დეპარტამენტი");
			partnior_bank_name.setAlign(Alignment.LEFT);

			ListGridField full_address_not_hidden = new ListGridField(
					"full_address_not_hidden", "მისამართი");
			partnior_bank_name.setAlign(Alignment.LEFT);

			ListGridField dep_hist_user_on = new ListGridField("on_user",
					"დაამატა");
			dep_hist_user_on.setWidth(50);

			ListGridField dep_hist_user_off = new ListGridField("off_user",
					"შეცვალა");
			dep_hist_user_off.setWidth(50);
			ListGridField dep_hist_start = new ListGridField("hist_start_date",
					"-დან");
			dep_hist_start.setWidth(120);

			ListGridField dep_hist_end = new ListGridField("hist_end_date",
					"-მდე");
			dep_hist_end.setWidth(120);

			ListGridField ph_deleted_d = new ListGridField("rdeleted",
					"წაშლილი");
			ph_deleted_d.setWidth(70);
			ph_deleted_d.setType(ListGridFieldType.BOOLEAN);

			if (hasPermission) {
				departmentListGrid.setFields(department,
						full_address_not_hidden, dep_hist_user_on,
						dep_hist_start, dep_hist_user_off, dep_hist_end,
						ph_deleted_d);
			} else {
				departmentListGrid.setFields(department,
						full_address_not_hidden, dep_hist_start, dep_hist_end,
						ph_deleted_d);
			}

			departmentListGrid.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (departmentListGrid.getSelectedRecord() != null) {
						searchDepPhones();
					}
				}
			});

			phonesListGrid = new ListGrid() {
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					String rdeleted = record.getAttribute("rdeleted");
					if (rdeleted != null)
						if (rdeleted.trim().equals("true"))
							return "color:red;";
						else
							return super.getCellCSSText(record, rowNum, colNum);
					else
						return super.getCellCSSText(record, rowNum, colNum);
				}
			};
			phonesListGrid.setWidth100();
			phonesListGrid.setHeight("50%");
			phonesListGrid.setAlternateRecordStyles(true);
			phonesListGrid.setDataSource(OrgDepPhoneDS);
			phonesListGrid.setAutoFetchData(false);
			phonesListGrid.setShowFilterEditor(false);
			phonesListGrid.setCanEdit(false);
			phonesListGrid.setCanRemoveRecords(false);
			phonesListGrid.setFetchOperation("searchOrgDepPhonesHist");
			phonesListGrid.setCanSort(false);
			phonesListGrid.setCanResizeFields(false);
			phonesListGrid.setWrapCells(true);
			phonesListGrid.setFixedRecordHeights(false);
			phonesListGrid.setCanDragSelectText(true);

			ListGridField phone = new ListGridField("phone",
					CallCenterBK.constants.phone(), 100);

			ListGridField phone_state_l = new ListGridField(
					"phone_state_descr", CallCenterBK.constants.phoneState(),
					100);
			phone_state_l.setAlign(Alignment.LEFT);

			ListGridField phone_contract_type_desr_l = new ListGridField(
					"phone_contract_type_descr",
					CallCenterBK.constants.phoneStatus(), 100);
			phone_contract_type_desr_l.setAlign(Alignment.CENTER);

			ListGridField phone_type_l = new ListGridField("phone_type_descr",
					"ტიპი", 100);

			ListGridField is_parallel_descr_l = new ListGridField(
					"is_parallel_descr", "პარალელური");
			ListGridField hidden_by_request_descr_l = new ListGridField(
					"hidden_by_request_descr", "ღია/დაფარული");

			ListGridField ph_hist_user_on_l = new ListGridField("on_user",
					"დაამატა");
			ph_hist_user_on_l.setWidth(80);
			ListGridField ph_hist_user_off_l = new ListGridField("off_user",
					"შეცვალა");
			ph_hist_user_off_l.setWidth(80);
			ListGridField ph_hist_start_l = new ListGridField(
					"hist_start_date", "-დან");
			ph_hist_start_l.setWidth(100);

			ListGridField ph_hist_end_l = new ListGridField("hist_end_date",
					"-მდე");
			ph_hist_end_l.setWidth(100);
			ListGridField ph_deleted_l = new ListGridField("rdeleted",
					"წაშლილი");
			ph_deleted_l.setWidth(70);
			ph_deleted_l.setType(ListGridFieldType.BOOLEAN);

			if (hasPermission) {
				phonesListGrid.setFields(phone, hidden_by_request_descr_l,
						phone_state_l, phone_contract_type_desr_l,
						phone_type_l, is_parallel_descr_l, ph_hist_user_on_l,
						ph_hist_start_l, ph_hist_user_off_l, ph_hist_end_l,
						ph_deleted_l);
			} else {
				phonesListGrid.setFields(phone, hidden_by_request_descr_l,
						phone_state_l, phone_contract_type_desr_l,
						phone_type_l, is_parallel_descr_l, ph_hist_start_l,
						ph_hist_end_l, ph_deleted_l);
			}

			tabDepHistLayout.setMembers(departmentListGrid, phonesListGrid);

			Tab tabDepInfo = new Tab("დეპარტამენტების ისტორია");
			tabDepInfo.setPane(tabDepHistLayout);
			topTabSet.addTab(tabDepInfo);

			hLayout.addMember(searchForm);
			hLayout.addMember(buttonLayout);

//			if (abonentRecord != null) {
//				searchBySubscriber(abonentRecord);
//			}
			hLayout.addMember(histOrganizaionListGrid);
			hLayout.addMember(topTabSet);
			addItem(hLayout);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String orgName = orgNameItem.getValueAsString();
			String remark = remarkItem.getValueAsString();
			String departmentName = departmentNameItem.getValueAsString();
			String ident_code = identCodeItem.getValueAsString();

			String street = streetItem.getValueAsString();
			String town_name = townItem.getValueAsString();
			String town_district = districtItem.getValueAsString();
			String phone_number = phoneItem.getValueAsString();

			Criteria criteria = new Criteria();

			if ((orgName == null || orgName.equals(""))
					&& (remark == null || remark.equals(""))
					&& (departmentName == null || departmentName.equals(""))
					&& (ident_code == null || ident_code.equals(""))
					&& (phone_number == null || phone_number.equals(""))) {
				SC.say("გთხოვთ შეიყვანოთ აუცილებელი მნიშვნელობები!");
				return;

			}

			if (orgName != null && !orgName.equals("")) {
				criteria.setAttribute("original_org_name", orgName);
			}

			if (remark != null && !remark.equals("")) {
				criteria.setAttribute("remark", remark);
			}

			if (departmentName != null && !departmentName.equals("")) {
				criteria.setAttribute("departmentName", departmentName);
			}

			if (ident_code != null && !ident_code.equals("")) {
				criteria.setAttribute("ident_code", ident_code);
			}

			if (street != null && !street.equals("")) {
				criteria.setAttribute("concat_address", street);
			}

			if (town_name != null && !town_name.equals("")) {
				criteria.setAttribute("town_name", town_name);
			}

			if (town_district != null && !town_district.equals("")) {
				criteria.setAttribute("town_district", town_district);
			}

			if (phone_number != null && !phone_number.equals("")) {
				criteria.setAttribute("phone_number", phone_number);
			}

			searchByCriteria(criteria);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	protected void searchByCriteria(Criteria criteria) {
		DSRequest dsRequest = new DSRequest();
		dsRequest.setAttribute("operationId", "histSearch");
		// histSubscriberListGrid.invalidateCache();
		histOrganizaionListGrid.filterData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				histOrganizaionListGrid.getGroupTree().openAll();
				if (response.getData() != null && response.getData().length > 0) {
					histOrganizaionListGrid.selectRecord(1);
					// searchPhones();
				}
			}
		}, dsRequest);
	}

	private void searchBankPartners() {
		try {
			ListGridRecord gridRecord = histOrganizaionListGrid
					.getSelectedRecord();
			String organization_id = "";
			if (gridRecord != null) {
				organization_id = gridRecord.getAttribute("organization_id");
			}
			Criteria criteria = new Criteria();
			if (!organization_id.equals("")) {
				criteria.setAttribute("organization_id", new Long(
						organization_id));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchOrgPartnerBanksHist");
			partnerBankListGrid.invalidateCache();
			partnerBankListGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					// partnerBankListGrid.getGroupTree().openAll();
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void searchActivities() {
		try {
			ListGridRecord gridRecord = histOrganizaionListGrid
					.getSelectedRecord();
			String organization_id = "";
			if (gridRecord != null) {
				organization_id = gridRecord.getAttribute("organization_id");
			}
			Criteria criteria = new Criteria();
			if (!organization_id.equals("")) {
				criteria.setAttribute("organization_id", new Long(
						organization_id));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchOrgAcctivitiesHist");
			activitiesListGrid.invalidateCache();
			activitiesListGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					// partnerBankListGrid.getGroupTree().openAll();
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void searchDepartments() {
		try {

			ListGridRecord gridRecord = histOrganizaionListGrid
					.getSelectedRecord();
			String organization_id = "";
			if (gridRecord != null) {
				organization_id = gridRecord.getAttribute("organization_id");
			}
			Criteria criteria = new Criteria();
			if (!organization_id.equals("")) {
				criteria.setAttribute("organization_id", new Long(
						organization_id));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchOrgDepartmentHist");
			departmentListGrid.invalidateCache();
			departmentListGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					searchDepPhones();
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void searchDepPhones() {
		try {
			ListGridRecord gridRecord = departmentListGrid.getSelectedRecord();
			String org_department_id = "";
			if (gridRecord != null) {
				org_department_id = gridRecord
						.getAttribute("org_department_id");
			}
			Criteria criteria = new Criteria();
			if (!org_department_id.equals("")) {
				criteria.setAttribute("org_department_id", new Long(
						org_department_id));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchOrgDepPhonesHist");
			phonesListGrid.invalidateCache();
			phonesListGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					// partnerBankListGrid.getGroupTree().openAll();
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	protected void searchBySubscriber(Record listGridRecord) {
		Integer subscriber_id = listGridRecord
				.getAttributeAsInt("subscriber_id");
		Criteria criteria = new Criteria();
		criteria.setAttribute("subscriber_id", subscriber_id);
		searchByCriteria(criteria);

	}
}
