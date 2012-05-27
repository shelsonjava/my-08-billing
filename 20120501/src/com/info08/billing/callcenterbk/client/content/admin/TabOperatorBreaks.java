package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditOperatorBreak;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabOperatorBreaks extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem firstNameItem;
	private TextItem lastNameItem;
	private TextItem userNameItem;
	private DateItem operatorBreakDateItem;
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addOperatorBreakBtn;
	private ToolStripButton editOperatorBreakBtn;
	private ToolStripButton deleteOperatorBreakBtn;
	private ToolStripButton exportOperatorBreakButton;
	private ListGrid operatorBreaksGrid;
	private DataSource dataSource;

	public TabOperatorBreaks() {
		try {

			setTitle("შესფვენების განრიგი");
			setCanClose(true);

			dataSource = DataSource.get("OperatorBreaksDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			//
			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(835);
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(4);
			mainLayout.addMember(searchForm);

			firstNameItem = new TextItem();
			firstNameItem.setTitle("სახელი");
			firstNameItem.setWidth(250);
			firstNameItem.setName("user_firstname");

			lastNameItem = new TextItem();
			lastNameItem.setTitle("გვარი");
			lastNameItem.setWidth(250);
			lastNameItem.setName("user_lastname");

			userNameItem = new TextItem();
			userNameItem.setTitle("მომხმარებელი");
			userNameItem.setWidth(250);
			userNameItem.setName("user_name");

			operatorBreakDateItem = new DateItem();
			operatorBreakDateItem.setTitle("შესვენების დღე");
			operatorBreakDateItem.setWidth(250);
			// operatorBreakDateItem.setValue(new Date());
			operatorBreakDateItem.setName("break_date");
			operatorBreakDateItem.setUseTextField(true);

			searchForm.setFields(firstNameItem, lastNameItem, userNameItem,
					operatorBreakDateItem);

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

			addOperatorBreakBtn = new ToolStripButton("დამატება",
					"person_add.png");
			addOperatorBreakBtn.setLayoutAlign(Alignment.LEFT);
			addOperatorBreakBtn.setWidth(50);
			toolStrip.addButton(addOperatorBreakBtn);

			editOperatorBreakBtn = new ToolStripButton("შეცვლა",
					"person_edit.png");
			editOperatorBreakBtn.setLayoutAlign(Alignment.LEFT);
			editOperatorBreakBtn.setWidth(50);
			toolStrip.addButton(editOperatorBreakBtn);

			deleteOperatorBreakBtn = new ToolStripButton("გაუქმება",
					"person_delete.png");
			deleteOperatorBreakBtn.setLayoutAlign(Alignment.LEFT);
			deleteOperatorBreakBtn.setWidth(50);
			toolStrip.addButton(deleteOperatorBreakBtn);

			// toolStrip.addSeparator();

			exportOperatorBreakButton = new ToolStripButton(
					"Excel - ში გადატანა", "excel.gif");
			exportOperatorBreakButton.setLayoutAlign(Alignment.LEFT);
			exportOperatorBreakButton.setWidth(50);
			// toolStrip.addButton(exportButton);

			operatorBreaksGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}

					return super.getCellCSSText(record, rowNum, colNum);

				};
			};

			operatorBreaksGrid.setWidth(835);
			operatorBreaksGrid.setHeight(500);
			operatorBreaksGrid.setAlternateRecordStyles(true);
			operatorBreaksGrid.setDataSource(dataSource);
			operatorBreaksGrid.setAutoFetchData(false);
			operatorBreaksGrid.setShowFilterEditor(false);
			operatorBreaksGrid.setCanEdit(false);
			operatorBreaksGrid.setCanRemoveRecords(false);
			operatorBreaksGrid.setFetchOperation("getOperatorBreaksAll");
			operatorBreaksGrid.setShowRowNumbers(true);
			operatorBreaksGrid.setCanHover(true);
			operatorBreaksGrid.setShowHover(true);
			operatorBreaksGrid.setShowHoverComponents(true);

			dataSource.getField("user_firstname").setTitle("სახელი");
			dataSource.getField("user_lastname").setTitle("გვარი");
			dataSource.getField("user_name").setTitle("მომხმარებელი");
			dataSource.getField("break_date_text").setTitle("შესვენების დღე");
			dataSource.getField("break_time").setTitle("შესვენების დრო");

			ListGridField user_firstname = new ListGridField("user_firstname",
					"სახელი", 100);
			ListGridField user_lastname = new ListGridField("user_lastname",
					"გვარი", 100);
			ListGridField user_name = new ListGridField("user_name",
					"მომხმარებელი", 150);
			ListGridField break_date = new ListGridField("break_date_text",
					"შესვენების დღე", 150);
			break_date.setAlign(Alignment.CENTER);
			ListGridField break_time = new ListGridField("break_time",
					"შესვენების დრო", 100);
			break_time.setAlign(Alignment.CENTER);

			operatorBreaksGrid.setFields(user_name, user_firstname,
					user_lastname, break_date, break_time);

			mainLayout.addMember(operatorBreaksGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Criteria criteria = operatorBreaksGrid.getCriteria();
					if (criteria == null) {
						criteria = new Criteria();
					}
					Criteria formCriteria = searchForm.getValuesAsCriteria();
					criteria.addCriteria(formCriteria);

					DSRequest dsRequest = new DSRequest();
					dsRequest.setAttribute("operationId",
							"getOperatorBreaksAll");
					operatorBreaksGrid.invalidateCache();
					operatorBreaksGrid.filterData(criteria, new DSCallback() {
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
					dsRequest.setAttribute("operationId",
							"getOperatorBreaksAll");
					operatorBreaksGrid.clearCriteria(new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
						}
					}, dsRequest);
				}
			});

			addOperatorBreakBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditOperatorBreak dlgAddEditOperatorBreak = new DlgAddEditOperatorBreak(
							operatorBreaksGrid, null);
					dlgAddEditOperatorBreak.show();
				}
			});

			editOperatorBreakBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = operatorBreaksGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					DlgAddEditOperatorBreak dlgAddEditOperatorBreak = new DlgAddEditOperatorBreak(
							operatorBreaksGrid, listGridRecord);
					dlgAddEditOperatorBreak.show();
				}
			});

			operatorBreaksGrid.addDoubleClickHandler(new DoubleClickHandler() {

				@Override
				public void onDoubleClick(DoubleClickEvent event) {
					ListGridRecord listGridRecord = operatorBreaksGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					DlgAddEditOperatorBreak dlgAddEditOperatorBreak = new DlgAddEditOperatorBreak(
							operatorBreaksGrid, listGridRecord);
					dlgAddEditOperatorBreak.show();

				}
			});

			deleteOperatorBreakBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = operatorBreaksGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					final Integer operator_break_id = listGridRecord
							.getAttributeAsInt("operator_break_id");
					if (operator_break_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										deleteOperatorBreak(operator_break_id);
									}
								}
							});
				}
			});

			exportOperatorBreakButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					com.smartgwt.client.data.DSRequest dsRequestProperties = new com.smartgwt.client.data.DSRequest();
					dsRequestProperties.setExportAs((ExportFormat) EnumUtil
							.getEnum(ExportFormat.values(), "xls"));
					dsRequestProperties
							.setExportDisplay(ExportDisplay.DOWNLOAD);
					dsRequestProperties.setOperationId("searchAllUser");
					operatorBreaksGrid.exportData(dsRequestProperties);
				}
			});

			setPane(mainLayout);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void deleteOperatorBreak(Integer operator_break_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("operator_break_id", operator_break_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "deleteOperatorBreak");

			operatorBreaksGrid.removeData(record, new DSCallback() {
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
