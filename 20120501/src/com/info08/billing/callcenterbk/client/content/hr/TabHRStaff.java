package com.info08.billing.callcenterbk.client.content.hr;

import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditStaff;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
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
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class TabHRStaff extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem firstNameItem;
	private TextItem lastNameItem;
	private TextItem docNumberItem;
	private TextItem remarkItem;
	private ComboBoxItem departmentItem;
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addPersonBtn;
	private ToolStripButton editPersonBtn;
	private ToolStripButton deletePersonBtn;
	private ToolStripButton exportButton;
	private ListGrid listGrid;
	private DataSource dataSource;

	public TabHRStaff() {
		try {

			setTitle("თანამშრომლების მართვა");
			setCanClose(true);

			dataSource = DataSource.get("StaffDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			//
			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(835);
			// searchForm.setTitleWidth(150);
			searchForm.setNumCols(6);
			mainLayout.addMember(searchForm);

			firstNameItem = new TextItem();
			firstNameItem.setTitle("სახელი");
			firstNameItem.setWidth(150);
			firstNameItem.setName("first_name");

			lastNameItem = new TextItem();
			lastNameItem.setTitle("გვარი");
			lastNameItem.setWidth(150);
			lastNameItem.setName("last_name");

			docNumberItem = new TextItem();
			docNumberItem.setTitle("პირადობა");
			docNumberItem.setWidth(150);
			docNumberItem.setName("doc_num");

			remarkItem = new TextItem();
			remarkItem.setTitle("კომენტარი");
			remarkItem.setWidth(150);
			remarkItem.setName("remark");

			departmentItem = new ComboBoxItem();
			departmentItem.setTitle("განყოფილება");
			departmentItem.setName("department_id");
			departmentItem.setWidth(150);
			departmentItem.setFetchMissingValues(false);

			ClientUtils.fillCombo(departmentItem, "StaffDS",
					"getAllDepartments", "department_id", "department_name");

			searchForm.setFields(firstNameItem, lastNameItem, departmentItem,
					docNumberItem, remarkItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(835);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(835);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addPersonBtn = new ToolStripButton("დამატება", "person_add.png");
			addPersonBtn.setLayoutAlign(Alignment.LEFT);
			addPersonBtn.setWidth(50);
			toolStrip.addButton(addPersonBtn);

			editPersonBtn = new ToolStripButton("შეცვლა", "person_edit.png");
			editPersonBtn.setLayoutAlign(Alignment.LEFT);
			editPersonBtn.setWidth(50);
			toolStrip.addButton(editPersonBtn);

			deletePersonBtn = new ToolStripButton("გაუქმება",
					"person_delete.png");
			deletePersonBtn.setLayoutAlign(Alignment.LEFT);
			deletePersonBtn.setWidth(50);
			toolStrip.addButton(deletePersonBtn);

			// toolStrip.addSeparator();

			exportButton = new ToolStripButton("Excel - ში გადატანა",
					"excel.gif");
			exportButton.setLayoutAlign(Alignment.LEFT);
			exportButton.setWidth(50);
			// toolStrip.addButton(exportButton);

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}

					return super.getCellCSSText(record, rowNum, colNum);

				};
			};

			listGrid.setWidth(835);
			// listGrid.setHeight(280);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(dataSource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("getAllStaff");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			dataSource.getField("first_name").setTitle("სახელი");
			dataSource.getField("last_name").setTitle("გვარი");
			dataSource.getField("doc_num").setTitle("პირადობის ნომერი");
			dataSource.getField("dob_descr").setTitle("დაბადების თარიღი");
			dataSource.getField("department_name").setTitle("განყოფილება");
			dataSource.getField("remark").setTitle("კომენტარი");
			dataSource.getField("start_date_descr").setTitle("დაწყების თარიღი");
			dataSource.getField("gender_descr").setTitle("სქესი");
			dataSource.getField("nationality_descr").setTitle("ეროვნება");
			dataSource.getField("family_status_descr").setTitle(
					"ოჯახური მდგომარეობა");

			ListGridField first_name = new ListGridField("first_name",
					"სახელი", 110);
			ListGridField last_name = new ListGridField("last_name", "გვარი",
					110);
			ListGridField doc_num = new ListGridField("doc_num",
					"პირადობის ნომერი", 110);
			ListGridField dob_descr = new ListGridField("dob_descr",
					"დაბადების თარიღი", 150);
			ListGridField department_name = new ListGridField(
					"department_name", "განყოფილება", 100);
			ListGridField remark = new ListGridField("remark", "კომენტარი");

			listGrid.setFields(first_name, last_name, department_name, doc_num,
					dob_descr, remark);

			mainLayout.addMember(listGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Criteria criteria = listGrid.getCriteria();
					if (criteria == null) {
						criteria = new Criteria();
					}
					Criteria formCriteria = searchForm.getValuesAsCriteria();
					criteria.addCriteria(formCriteria);

					DSRequest dsRequest = new DSRequest();
					dsRequest.setAttribute("operationId", "getAllStaff");
					listGrid.invalidateCache();
					listGrid.filterData(criteria, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
						}
					}, dsRequest);
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchForm.clearValues();
					DSRequest dsRequest = new DSRequest();
					dsRequest.setAttribute("operationId", "getAllStaff");
					listGrid.clearCriteria(new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
						}
					}, dsRequest);
				}
			});

			addPersonBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditStaff dlgAddEditStaff = new DlgAddEditStaff(
							dataSource, null);
					dlgAddEditStaff.show();
				}
			});

			editPersonBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					DlgAddEditStaff dlgAddEditStaff = new DlgAddEditStaff(
							dataSource, listGridRecord);
					dlgAddEditStaff.show();
				}
			});
			deletePersonBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					final Integer staff_id = listGridRecord
							.getAttributeAsInt("staff_id");
					if (staff_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ თანამშრომლის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										deleteStaff(staff_id);
									}
								}
							});
				}
			});

			exportButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					com.smartgwt.client.data.DSRequest dsRequestProperties = new com.smartgwt.client.data.DSRequest();
					dsRequestProperties.setExportAs((ExportFormat) EnumUtil
							.getEnum(ExportFormat.values(), "xls"));
					dsRequestProperties
							.setExportDisplay(ExportDisplay.DOWNLOAD);
					dsRequestProperties.setOperationId("searchAllUser");
					listGrid.exportData(dsRequestProperties);
				}
			});
			TabSet tabSet = new TabSet();
			tabSet.setWidth(835);
			tabSet.setHeight(267);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();

			DetailViewerField dvffirst_name = new DetailViewerField(
					"first_name", "სახელი");
			DetailViewerField dvflast_name = new DetailViewerField("last_name",
					"გვარი");
			DetailViewerField dvfdoc_num = new DetailViewerField("doc_num",
					"პირადობის ნომერი");
			DetailViewerField dvfdob_descr = new DetailViewerField("dob_descr",
					"დაბადების თარიღი");
			DetailViewerField dvfdepartment_name = new DetailViewerField(
					"department_name", "განყოფილება");
			DetailViewerField dvfstart_date_descr = new DetailViewerField(
					"start_date_descr", "დაწყების თარიღი");
			DetailViewerField dvfgender_descr = new DetailViewerField(
					"gender_descr", "სქესი");
			DetailViewerField dvfnationality_descr = new DetailViewerField(
					"nationality_descr", "ეროვნება");
			DetailViewerField dvffamily_status_descr = new DetailViewerField(
					"family_status_descr", "ოჯახური მდგომარეობა");
			DetailViewerField dvfremark = new DetailViewerField("remark",
					"კომენტარი");

			detailViewer.setFields(dvfdoc_num, dvffirst_name, dvflast_name,
					dvfdepartment_name, dvfdob_descr, dvfgender_descr,
					dvfnationality_descr, dvffamily_status_descr,
					dvfstart_date_descr, dvfremark);
			detailViewer.setDataSource(dataSource);
			detailViewer.setWidth(800);
			tabDetViewer.setPane(detailViewer);

			listGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(listGrid);
				}
			});
			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();

					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					DlgAddEditStaff dlgAddEditStaff = new DlgAddEditStaff(
							dataSource, listGridRecord);
					dlgAddEditStaff.show();

				}
			});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void deleteStaff(Integer staff_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("staff_id", staff_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "removeStaff");

			listGrid.removeData(record, new DSCallback() {
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
}
