package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgAddEditTreatments;
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
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabTreatments extends Tab {

	private VLayout mainLayout;
	private DataSource TreatmentsDS;
	private DynamicForm searchForm;

	private TextItem treatmentItem;
	private TextItem phoneNumberItem;
	private SelectItem genderItem;
	private SelectItem visibleItem;

	private IButton findButton;
	private IButton clearButton;

	private ToolStripButton addFirstNameBtn;
	private ToolStripButton editFirstiNameBtn;
	private ToolStripButton deleteFirstNameBtn;
	private ToolStripButton exportButton;

	private ListGrid treatmentsGrid;

	public TabTreatments() {
		try {
			setTitle("აბონენტების მიმართვის მართვა");
			setCanClose(true);

			TreatmentsDS = DataSource.get("TreatmentsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();

			searchForm = new DynamicForm();
			searchForm.setPadding(10);
			searchForm.setAutoFocus(true);
			searchForm.setWidth(540);
			searchForm.setTitleWidth(150);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			phoneNumberItem = new TextItem();
			phoneNumberItem.setTitle("ნომერი");
			phoneNumberItem.setWidth(250);
			phoneNumberItem.setName("phone_number");

			treatmentItem = new TextItem();
			treatmentItem.setTitle("მიმართვა");
			treatmentItem.setWidth(250);
			treatmentItem.setName("treatment");

			genderItem = new SelectItem();
			genderItem.setTitle("სქესი");
			genderItem.setWidth(250);
			genderItem.setName("gender");
			genderItem.setFetchMissingValues(true);
			genderItem.setFilterLocally(false);
			genderItem.setAddUnknownValues(false);

			ClientUtils.fillDescriptionCombo(genderItem, 74000);

			visibleItem = new SelectItem();
			visibleItem.setTitle("მდგომარეობა");
			visibleItem.setWidth(250);
			visibleItem.setName("visible");
			visibleItem.setFetchMissingValues(true);
			visibleItem.setFilterLocally(false);
			visibleItem.setAddUnknownValues(false);

			ClientUtils.fillDescriptionCombo(visibleItem, 75000);

			searchForm.setFields(phoneNumberItem, treatmentItem, genderItem,
					visibleItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(540);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);
			buttonLayout.setStyleName("paddingRight");
			mainLayout.addMember(buttonLayout);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(530);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addFirstNameBtn = new ToolStripButton("დამატება", "new.png");
			addFirstNameBtn.setLayoutAlign(Alignment.LEFT);
			addFirstNameBtn.setWidth(50);
			toolStrip.addButton(addFirstNameBtn);

			editFirstiNameBtn = new ToolStripButton("შეცვლა", "edit.png");
			editFirstiNameBtn.setLayoutAlign(Alignment.LEFT);
			editFirstiNameBtn.setWidth(50);
			toolStrip.addButton(editFirstiNameBtn);

			deleteFirstNameBtn = new ToolStripButton("გაუქმება", "delete.png");
			deleteFirstNameBtn.setLayoutAlign(Alignment.LEFT);
			deleteFirstNameBtn.setWidth(50);
			toolStrip.addButton(deleteFirstNameBtn);

			toolStrip.addSeparator();

			exportButton = new ToolStripButton("Excel - ში გადატანა",
					"excel.gif");
			exportButton.setLayoutAlign(Alignment.LEFT);
			exportButton.setWidth(50);
			toolStrip.addButton(exportButton);

			treatmentsGrid = new ListGrid();
			treatmentsGrid.setWidth(530);
			treatmentsGrid.setHeight100();
			treatmentsGrid.setAlternateRecordStyles(true);
			treatmentsGrid.setDataSource(TreatmentsDS);
			treatmentsGrid.setAutoFetchData(false);
			treatmentsGrid.setShowFilterEditor(false);
			treatmentsGrid.setCanEdit(false);
			treatmentsGrid.setCanRemoveRecords(false);
			treatmentsGrid.setFetchOperation("searchFromDB");

			ListGridField phone_number = new ListGridField("phone_number",
					"ნომერი");
			ListGridField treatment = new ListGridField("treatment", "მიმართვა");
			ListGridField gender_descr = new ListGridField("gender_descr",
					"სქესი");
			ListGridField visible_descr = new ListGridField("visible_descr",
					"მდგომარეობა");

			treatmentsGrid.setFields(phone_number, treatment, gender_descr,
					visible_descr);

			mainLayout.addMember(treatmentsGrid);

			findButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchForm.clearValues();
					treatmentsGrid.clearCriteria();
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

			treatmentItem.addKeyPressHandler(keyPressHandler);
			phoneNumberItem.addKeyPressHandler(keyPressHandler);
			genderItem.addKeyPressHandler(keyPressHandler);
			visibleItem.addKeyPressHandler(keyPressHandler);

			addFirstNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditTreatments dlgAddEditTreatments = new DlgAddEditTreatments(
							treatmentsGrid, null);
					dlgAddEditTreatments.show();
				}
			});
			editFirstiNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = treatmentsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
						return;
					}
					Integer treatment_id = listGridRecord
							.getAttributeAsInt("treatment_id");
					if (treatment_id == null) {
						SC.say("არასწორი ჩანაწერი");
						return;
					}

					DlgAddEditTreatments dlgAddEditTreatments = new DlgAddEditTreatments(
							treatmentsGrid, listGridRecord);
					dlgAddEditTreatments.show();
				}
			});

			treatmentsGrid.addDoubleClickHandler(new DoubleClickHandler() {

				@Override
				public void onDoubleClick(DoubleClickEvent event) {

					ListGridRecord listGridRecord = treatmentsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
						return;
					}
					Integer treatment_id = listGridRecord
							.getAttributeAsInt("treatment_id");
					if (treatment_id == null) {
						SC.say("არასწორი ჩანაწერი");
						return;
					}

					DlgAddEditTreatments dlgAddEditTreatments = new DlgAddEditTreatments(
							treatmentsGrid, listGridRecord);
					dlgAddEditTreatments.show();
				}
			});

			deleteFirstNameBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = treatmentsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					final Integer treatment_id = listGridRecord
							.getAttributeAsInt("treatment_id");
					if (treatment_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(treatment_id);
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
					dsRequestProperties.setOperationId("searchFromDB");
					treatmentsGrid.exportData(dsRequestProperties);
				}
			});
			setPane(mainLayout);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search() {
		Criteria criteria = treatmentsGrid.getCriteria();
		if (criteria == null) {
			criteria = new Criteria();
		}
		Criteria formCriteria = searchForm.getValuesAsCriteria();
		criteria.addCriteria(formCriteria);

		treatmentsGrid.invalidateCache();
		DSRequest dsRequest = new DSRequest();
		dsRequest.setOperationId("searchFromDB");
		treatmentsGrid.filterData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {

			}
		}, dsRequest);
	}

	private void delete(Integer treatment_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("treatment_id", treatment_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeTreatments");
			treatmentsGrid.removeData(record, new DSCallback() {
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
