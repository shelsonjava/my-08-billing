package com.info08.billing.callcenterbk.client.content.misc;

import com.info08.billing.callcenterbk.client.dialogs.misc.DlgAddEditNonStandartInfo;
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
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
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

public class TabNonStandartInfo extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem infoGroupNameItem;
	private TextItem infoNameItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource NonStandartInfosDS;

	public TabNonStandartInfo() {
		try {
			setTitle("არასტ. ინფორმაცია");
			setCanClose(true);

			NonStandartInfosDS = DataSource.get("NonStandartInfosDS");

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

			Criteria criteria = new Criteria();

			infoGroupNameItem = new ComboBoxItem();
			infoGroupNameItem.setTitle("ჯგუფი");
			infoGroupNameItem.setWidth(300);
			infoGroupNameItem.setName("info_group_name");
			infoGroupNameItem.setFetchMissingValues(true);
			infoGroupNameItem.setFilterLocally(false);
			infoGroupNameItem.setAddUnknownValues(false);

			DataSource NoneStandartInfoGroupsDS = DataSource
					.get("NoneStandartInfoGroupsDS");
			infoGroupNameItem
					.setOptionOperationId("searchAllNoneStandartInfoGroupsForCB");
			infoGroupNameItem.setOptionDataSource(NoneStandartInfoGroupsDS);
			infoGroupNameItem.setValueField("info_group_id");
			infoGroupNameItem.setDisplayField("info_group_name");

			infoGroupNameItem.setOptionCriteria(criteria);
			infoGroupNameItem.setAutoFetchData(false);

			infoGroupNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = infoGroupNameItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("info_group_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("info_group_id", nullO);
						}
					}
				}
			});

			infoNameItem = new TextItem();
			infoNameItem.setTitle("აღწერა");
			infoNameItem.setName("infoNameItem");
			infoNameItem.setWidth(300);

			searchForm.setFields(infoGroupNameItem, infoNameItem);

			searchForm.focusInItem(infoNameItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(930);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton("დამატება", "addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton("შეცვლა", "editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton("გაუქმება", "deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			toolStrip.addSeparator();

			listGrid = new ListGrid();

			listGrid.setWidth(930);
			listGrid.setHeight(380);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(NonStandartInfosDS);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllNoneStandartInfo");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			NonStandartInfosDS.getField("info_name").setTitle("აღწერა");
			NonStandartInfosDS.getField("info_group_id").setTitle(
					"ჯგუფის იდენტიფიკატორი");
			NonStandartInfosDS.getField("info_group_name").setTitle("ჯგუფი");

			ListGridField info_name = new ListGridField("info_name", "აღწერა");
			ListGridField info_group_name = new ListGridField(
					"info_group_name", "ჯგუფი", 200);

			listGrid.setFields(info_group_name, info_name);

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
					infoGroupNameItem.clearValue();
					infoNameItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditNonStandartInfo dlgAddEditNonStandartInfo = new DlgAddEditNonStandartInfo(
							listGrid, null);
					dlgAddEditNonStandartInfo.show();
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

					DlgAddEditNonStandartInfo dlgAddEditNonStandartInfo = new DlgAddEditNonStandartInfo(
							listGrid, listGridRecord);
					dlgAddEditNonStandartInfo.show();
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
					final Integer web_site_id = listGridRecord
							.getAttributeAsInt("info_id");
					if (web_site_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(web_site_id);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(930);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(NonStandartInfosDS);
			detailViewer.setWidth(910);
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
					DlgAddEditNonStandartInfo dlgAddEditNonStandartInfo = new DlgAddEditNonStandartInfo(
							listGrid, listGridRecord);
					dlgAddEditNonStandartInfo.show();
				}
			});

			infoNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void search() {
		try {
			String info_group_id = infoGroupNameItem.getValueAsString();
			String info_name = infoNameItem.getValueAsString();

			Criteria criteria = new Criteria();
			if (info_group_id != null) {
				criteria.setAttribute("info_group_id", new Integer(
						info_group_id));
			}

			if (info_name != null && !info_name.trim().equals("")) {
				criteria.setAttribute("info_name", info_name);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllNoneStandartInfo");
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

	private void delete(Integer info_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("info_id", info_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeNoneStandartInfo");
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
