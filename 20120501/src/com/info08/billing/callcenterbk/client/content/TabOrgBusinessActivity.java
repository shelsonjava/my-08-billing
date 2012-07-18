package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.org.DlgAddEditOrgActivity;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
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

public class TabOrgBusinessActivity extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem activityDescriptionItem;
	private TextItem remarkItem;
	private SelectItem isBankActivityItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabOrgBusinessActivity() {
		try {
			setTitle(CallCenterBK.constants.orgActivities());
			setCanClose(true);

			datasource = DataSource.get("OrgActDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(200);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			activityDescriptionItem = new TextItem();
			activityDescriptionItem.setTitle(CallCenterBK.constants
					.orgActivity());
			activityDescriptionItem.setWidth(300);
			activityDescriptionItem.setName("activityDescriptionItem");

			remarkItem = new TextItem();
			remarkItem.setTitle(CallCenterBK.constants.remark());
			remarkItem.setWidth(300);
			remarkItem.setName("remarkItem");

			isBankActivityItem = new SelectItem();
			isBankActivityItem.setName("isBankActivityItem");
			isBankActivityItem.setWidth(300);
			isBankActivityItem.setTitle(CallCenterBK.constants.bankActivity());
			isBankActivityItem.setValueMap(ClientMapUtil.getInstance()
					.getAllYesAndNo());
			isBankActivityItem.setDefaultToFirstOption(true);

			searchForm.setFields(activityDescriptionItem, remarkItem,
					isBankActivityItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(730);
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

			toolStrip.addSeparator();

			listGrid = new ListGrid();

			listGrid.setWidth(730);
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllBusinesActivities");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);
			listGrid.setShowFilterEditor(true);
			listGrid.setFilterOnKeypress(true);

			datasource.getField("activity_description").setTitle(
					CallCenterBK.constants.orgActivity());
			datasource.getField("remark").setTitle(
					CallCenterBK.constants.comment());

			ListGridField activity_description = new ListGridField(
					"activity_description",
					CallCenterBK.constants.orgActivity(), 300);
			activity_description.setCanFilter(true);

			ListGridField is_bank_activity_descr = new ListGridField(
					"is_bank_activity_descr",
					CallCenterBK.constants.bankActivity(), 150);
			is_bank_activity_descr.setCanFilter(false);

			ListGridField remark = new ListGridField("remark",
					CallCenterBK.constants.comment());
			remark.setCanFilter(false);

			listGrid.setFields(activity_description, is_bank_activity_descr,
					remark);

			mainLayout.addMember(listGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					activityDescriptionItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditOrgActivity dlgAddEditOrgActivity = new DlgAddEditOrgActivity(
							listGrid, null);
					dlgAddEditOrgActivity.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					DlgAddEditOrgActivity dlgAddEditOrgActivity = new DlgAddEditOrgActivity(
							listGrid, listGridRecord);
					dlgAddEditOrgActivity.show();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					final Integer org_activity_id = listGridRecord
							.getAttributeAsInt("org_activity_id");
					if (org_activity_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										remove(org_activity_id);
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
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					DlgAddEditOrgActivity dlgAddEditOrgActivity = new DlgAddEditOrgActivity(
							listGrid, listGridRecord);
					dlgAddEditOrgActivity.show();
				}
			});

			activityDescriptionItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			remarkItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void search() {
		try {
			String activity_description = activityDescriptionItem
					.getValueAsString();
			String remark = remarkItem.getValueAsString();
			Integer is_bank_activity = new Integer(
					isBankActivityItem.getValueAsString());

			Criteria criteria = new Criteria();
			criteria.setAttribute("activity_description_param",
					activity_description);
			criteria.setAttribute("remark", remark);
			if (!is_bank_activity.equals(new Integer(-1))) {
				criteria.setAttribute("is_bank_activity", is_bank_activity);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllBusinesActivities");
			listGrid.invalidateCache();
			listGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void remove(Integer org_activity_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("org_activity_id", org_activity_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeOrgActivity");
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
