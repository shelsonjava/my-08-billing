package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditRedOrg;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
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
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabOrgPriorityCompList extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem orgNameItem;

	private IButton sendButton;
	private IButton clearButton;

	private ListGrid listGrid;

	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	public TabOrgPriorityCompList() {
		try {

			setTitle(CallCenterBK.constants.red_orgs());
			setCanClose(true);

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			//
			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(600);
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			orgNameItem = new TextItem();
			orgNameItem.setTitle(CallCenterBK.constants.orgNameFull());
			orgNameItem.setWidth(250);
			orgNameItem.setName("orgNameItem");
			orgNameItem.setWidth("100%");

			searchForm.setFields(orgNameItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(600);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			sendButton = new IButton();
			sendButton.setTitle(CallCenterBK.constants.find());
			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());
			buttonLayout.setMembers(sendButton, clearButton);
			mainLayout.addMember(buttonLayout);

			sendButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchForm.clearValues();
				}
			});

			listGrid = new ListGrid() {

				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					Integer debt = record.getAttributeAsInt("debt");
					if (debt.equals(new Integer(1))) {
						return "color:red;";
					}
					return super.getCellCSSText(record, rowNum, colNum);
				};
			};

			listGrid.setWidth(1500);
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(DataSource.get("OrgPriorListDS"));
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("SearchOrgs");
			listGrid.setCanSort(true);
			listGrid.setCanResizeFields(false);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setDataPageSize(75);
			listGrid.setCanDragSelectText(true);
			listGrid.setShowRowNumbers(true);
			listGrid.setCanResizeFields(true);
			listGrid.setShowFilterEditor(true);
			listGrid.setFilterOnKeypress(true);
			listGrid.setShowGridSummary(true);
			listGrid.setShowGroupSummary(true);
			listGrid.setAutoFetchData(true); 
			listGrid.setShowAllRecords(true);  

			ListGridField organization_name = new ListGridField(
					"organization_name", CallCenterBK.constants.orgNameFull(),
					300);
			organization_name.setCanFilter(false);
			organization_name.setSummaryFunction(SummaryFunctionType.COUNT);
			
			
			ListGridField remark = new ListGridField("remark",
					CallCenterBK.constants.remark());
			remark.setCanFilter(true);

			ListGridField debt = new ListGridField("debt",
					CallCenterBK.constants.debt(), 50);
			debt.setType(ListGridFieldType.BOOLEAN);

			ListGridField debt_amount = new ListGridField("debt_amount",
					CallCenterBK.constants.debt_amount(), 60);
			debt_amount.setCanFilter(false);
			debt_amount.setSummaryFunction(SummaryFunctionType.SUM);

			ListGridField tariff = new ListGridField("tariff",
					CallCenterBK.constants.tariff(), 70);
			tariff.setCanFilter(false);
			tariff.setSummaryFunction(SummaryFunctionType.SUM);

			ListGridField debet = new ListGridField("debet",
					CallCenterBK.constants.debet_amount(), 70);
			debet.setCanFilter(false);
			debet.setSummaryFunction(SummaryFunctionType.SUM);

			ListGridField billing_date = new ListGridField("billing_date",
					CallCenterBK.constants.billing_date(), 100);
			billing_date.setCanFilter(false);

			ListGridField start_date = new ListGridField("start_date",
					CallCenterBK.constants.startDate(), 100);
			start_date.setCanFilter(false);

			ListGridField end_date = new ListGridField("end_date",
					CallCenterBK.constants.endDate(), 100);
			end_date.setCanFilter(false);

			ListGridField contact_phones = new ListGridField("contact_phones",
					CallCenterBK.constants.contactPhone(), 200);
			contact_phones.setCanFilter(true);

			ListGridField update_date = new ListGridField("update_date",
					CallCenterBK.constants.updDate(), 100);
			update_date.setCanFilter(false);

			ListGridField update_user = new ListGridField("update_user",
					CallCenterBK.constants.updUser(), 100);
			update_user.setCanFilter(false);

			listGrid.setFields(debt, organization_name, remark, tariff,
					debt_amount, debet, billing_date, start_date, end_date,
					update_date, contact_phones, update_user);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(1500);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenterBK.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton(CallCenterBK.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			mainLayout.addMember(listGrid);
			setPane(mainLayout);

			orgNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditRedOrg dlgAddEditContractor = new DlgAddEditRedOrg(
							listGrid, null);
					dlgAddEditContractor.show();
					// new DlgContractorPhones().show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditRedOrg dlgAddEditContractor = new DlgAddEditRedOrg(
							listGrid, listGridRecord);
					dlgAddEditContractor.show();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					SC.ask(CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										removeRecord(listGridRecord);
									}
								}
							});
				}
			});

			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditRedOrg dlgAddEditContractor = new DlgAddEditRedOrg(
							listGrid, listGridRecord);
					dlgAddEditContractor.show();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void removeRecord(ListGridRecord listGridRecord) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("id", listGridRecord.getAttributeAsInt("id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeContractor");
			listGrid.removeData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					listGrid.invalidateCache();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			String org_name = orgNameItem.getValueAsString();
			if (org_name != null && !org_name.trim().equals("")) {
				criteria.setAttribute("org_name", org_name.trim());
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("SearchOrgs");
			listGrid.invalidateCache();
			listGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						com.smartgwt.client.data.DSRequest request) {
				}
			}, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
