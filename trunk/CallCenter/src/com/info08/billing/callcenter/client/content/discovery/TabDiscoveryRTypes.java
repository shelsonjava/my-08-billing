package com.info08.billing.callcenter.client.content.discovery;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.dialogs.discovery.DlgAddEditDiscoveryRType;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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

public class TabDiscoveryRTypes extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem responseTypeItem;
	
	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton disableBtn;
	private ToolStripButton activateBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabDiscoveryRTypes() {
		try {
			setTitle(CallCenter.constants.menuDiscoveryStatuses());
			setCanClose(true);

			datasource = DataSource.get("DiscoveryRTypeDS");

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
			
			
			responseTypeItem = new TextItem();
			responseTypeItem.setTitle(CallCenter.constants.status());
			responseTypeItem.setName("response_type");
			responseTypeItem.setWidth(300);
			
			searchForm.setFields(responseTypeItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenter.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenter.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(780);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenter.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton(CallCenter.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			disableBtn = new ToolStripButton(CallCenter.constants.disable(),
					"deleteIcon.png");
			disableBtn.setLayoutAlign(Alignment.LEFT);
			disableBtn.setWidth(50);
			toolStrip.addButton(disableBtn);

			activateBtn = new ToolStripButton(CallCenter.constants.enable(),
					"restoreIcon.gif");
			activateBtn.setLayoutAlign(Alignment.LEFT);
			activateBtn.setWidth(50);
			toolStrip.addButton(activateBtn);

			toolStrip.addSeparator();

			listGrid = new ListGrid() {
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

			listGrid.setWidth(780);
			listGrid.setHeight(430);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchDiscoverRtypes");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("response_type").setTitle(CallCenter.constants.status());
			datasource.getField("rec_date").setTitle(CallCenter.constants.recDate());
			datasource.getField("rec_user").setTitle(CallCenter.constants.recUser());
			datasource.getField("upd_date").setTitle(CallCenter.constants.updDate());
			datasource.getField("upd_user").setTitle(CallCenter.constants.updUser());
			
			ListGridField response_type = new ListGridField("response_type",CallCenter.constants.status(), 190);
			ListGridField rec_date = new ListGridField("rec_date",CallCenter.constants.recDate(), 140);
			ListGridField rec_user = new ListGridField("rec_user",CallCenter.constants.recUser(), 130);
			ListGridField upd_date = new ListGridField("upd_date",CallCenter.constants.updDate(), 140);
			ListGridField upd_user = new ListGridField("upd_user",CallCenter.constants.updUser(), 140);
			
			response_type.setAlign(Alignment.LEFT);
			rec_date.setAlign(Alignment.CENTER);
			rec_user.setAlign(Alignment.CENTER);
			upd_date.setAlign(Alignment.CENTER);
			upd_user.setAlign(Alignment.CENTER);

			listGrid.setFields(response_type, rec_date, rec_user, upd_date,
					upd_user);

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
					responseTypeItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditDiscoveryRType dlgAddEditDiscoveryRType = new DlgAddEditDiscoveryRType(
							listGrid, null);
					dlgAddEditDiscoveryRType.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditDiscoveryRType dlgAddEditDiscoveryRType = new DlgAddEditDiscoveryRType(
							listGrid, listGridRecord);
					dlgAddEditDiscoveryRType.show();
				}
			});
			disableBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (!deleted.equals(0)) {
						SC.say(CallCenter.constants.recordAlrDisabled());
						return;
					}
					SC.ask(CallCenter.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(listGridRecord, 1);
									}
								}
							});
				}
			});
			activateBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (deleted.equals(0)) {
						SC.say(CallCenter.constants.recordAlrEnabled());
						return;
					}
					SC.ask(CallCenter.constants.askForEnable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(listGridRecord, 0);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(780);
			Tab tabDetViewer = new Tab(CallCenter.constants.view());
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasource);
			detailViewer.setWidth(750);
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
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditDiscoveryRType dlgAddEditDiscoveryRType = new DlgAddEditDiscoveryRType(
							listGrid, listGridRecord);
					dlgAddEditDiscoveryRType.show();
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
			Criteria criteria = new Criteria();
			
			String response_type = responseTypeItem.getValueAsString();
			if (response_type != null && !response_type.trim().equals("")) {
				criteria.setAttribute("response_type", response_type);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchDiscoverRtypes");
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

	private void changeStatus(ListGridRecord listGridRecord, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance().getSessionPerson().getUserName());
			record.setAttribute("deleted", deleted);
			record.setAttribute("response_type_id",listGridRecord.getAttributeAsInt("response_type_id"));
			
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateDiscoverRtypesStatus");
			listGrid.updateData(record, new DSCallback() {
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
