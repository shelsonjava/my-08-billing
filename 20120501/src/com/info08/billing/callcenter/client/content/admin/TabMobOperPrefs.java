package com.info08.billing.callcenter.client.content.admin;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.dialogs.admin.DlgAddEditMobOperPref;
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

public class TabMobOperPrefs extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem operatorItem;
	private TextItem indexItem;

	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addMobOperPrefBtn;
	private ToolStripButton editMobOperPrefBtn;
	private ToolStripButton deleteMobOpepPrefBtn;
	private ToolStripButton restoreMobOperPrefBtn;
	private ListGrid mobOperPrefsGrid;
	private DataSource mobOperPrefDS;

	public TabMobOperPrefs() {
		try {

			setTitle(CallCenter.constants.mobOpIndexes());
			setCanClose(true);

			mobOperPrefDS = DataSource.get("MobOpPrefDS");

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

			operatorItem = new TextItem();
			operatorItem.setTitle(CallCenter.constants.mobOperator());
			operatorItem.setWidth(250);
			operatorItem.setName("operatorItem");

			indexItem = new TextItem();
			indexItem.setTitle(CallCenter.constants.mobOperatorIndex());
			indexItem.setWidth(250);
			indexItem.setName("indexItem");

			searchForm.setFields(operatorItem, indexItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(835);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenter.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenter.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(835);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addMobOperPrefBtn = new ToolStripButton(CallCenter.constants.add(),
					"addIcon.png");
			addMobOperPrefBtn.setLayoutAlign(Alignment.LEFT);
			addMobOperPrefBtn.setWidth(50);
			toolStrip.addButton(addMobOperPrefBtn);

			editMobOperPrefBtn = new ToolStripButton(
					CallCenter.constants.modify(), "editIcon.png");
			editMobOperPrefBtn.setLayoutAlign(Alignment.LEFT);
			editMobOperPrefBtn.setWidth(50);
			toolStrip.addButton(editMobOperPrefBtn);

			deleteMobOpepPrefBtn = new ToolStripButton(
					CallCenter.constants.disable(), "deleteIcon.png");
			deleteMobOpepPrefBtn.setLayoutAlign(Alignment.LEFT);
			deleteMobOpepPrefBtn.setWidth(50);
			toolStrip.addButton(deleteMobOpepPrefBtn);

			restoreMobOperPrefBtn = new ToolStripButton(
					CallCenter.constants.enable(), "restoreIcon.gif");
			restoreMobOperPrefBtn.setLayoutAlign(Alignment.LEFT);
			restoreMobOperPrefBtn.setWidth(50);
			toolStrip.addButton(restoreMobOperPrefBtn);

			mobOperPrefsGrid = new ListGrid() {
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

			mobOperPrefsGrid.setWidth(835);
			mobOperPrefsGrid.setHeight(400);
			mobOperPrefsGrid.setAlternateRecordStyles(true);
			mobOperPrefsGrid.setDataSource(mobOperPrefDS);
			mobOperPrefsGrid.setAutoFetchData(false);
			mobOperPrefsGrid.setShowFilterEditor(false);
			mobOperPrefsGrid.setCanEdit(false);
			mobOperPrefsGrid.setCanRemoveRecords(false);
			mobOperPrefsGrid.setFetchOperation("searchMobOperPrefs");
			mobOperPrefsGrid.setShowRowNumbers(true);
			mobOperPrefsGrid.setCanHover(true);
			mobOperPrefsGrid.setShowHover(true);
			mobOperPrefsGrid.setShowHoverComponents(true);

			mobOperPrefDS.getField("oper").setTitle(
					CallCenter.constants.mobOperator());
			mobOperPrefDS.getField("prefix").setTitle(
					CallCenter.constants.mobOperatorIndex());
			mobOperPrefDS.getField("rec_date").setTitle(
					CallCenter.constants.recDate());
			mobOperPrefDS.getField("rec_user").setTitle(
					CallCenter.constants.recUser());
			mobOperPrefDS.getField("upd_date").setTitle(
					CallCenter.constants.updDate());
			mobOperPrefDS.getField("upd_user").setTitle(
					CallCenter.constants.updUser());

			ListGridField oper = new ListGridField("oper",
					CallCenter.constants.mobOperator(), 157);
			ListGridField prefix = new ListGridField("prefix",
					CallCenter.constants.mobOperatorIndex(), 150);
			ListGridField rec_date = new ListGridField("rec_date",
					CallCenter.constants.recDate(), 130);
			ListGridField rec_user = new ListGridField("rec_user",
					CallCenter.constants.recUser(), 110);
			ListGridField upd_date = new ListGridField("upd_date",
					CallCenter.constants.updDate(), 130);
			ListGridField upd_user = new ListGridField("upd_user",
					CallCenter.constants.updUser(), 110);

			rec_user.setAlign(Alignment.CENTER);
			rec_date.setAlign(Alignment.CENTER);
			upd_date.setAlign(Alignment.CENTER);
			upd_user.setAlign(Alignment.CENTER);

			mobOperPrefsGrid.setFields(oper, prefix, rec_user, rec_date,
					upd_user, upd_date);

			mainLayout.addMember(mobOperPrefsGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					operatorItem.clearValue();
					indexItem.clearValue();
				}
			});

			addMobOperPrefBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditMobOperPref dlgAddEditMobOperPref = new DlgAddEditMobOperPref(
							mobOperPrefsGrid, null);
					dlgAddEditMobOperPref.show();
				}
			});

			editMobOperPrefBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = mobOperPrefsGrid
							.getSelectedRecord();
					DlgAddEditMobOperPref dlgAddEditMobOperPref = new DlgAddEditMobOperPref(
							mobOperPrefsGrid, listGridRecord);
					dlgAddEditMobOperPref.show();
				}
			});
			deleteMobOpepPrefBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = mobOperPrefsGrid
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
			restoreMobOperPrefBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = mobOperPrefsGrid
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
			tabSet.setWidth(835);
			Tab tabDetViewer = new Tab(CallCenter.constants.view());
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(mobOperPrefDS);
			detailViewer.setWidth(800);
			tabDetViewer.setPane(detailViewer);

			mobOperPrefsGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(mobOperPrefsGrid);
				}
			});
			mobOperPrefsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = mobOperPrefsGrid
									.getSelectedRecord();
							DlgAddEditMobOperPref dlgAddEditMobOperPref = new DlgAddEditMobOperPref(
									mobOperPrefsGrid, listGridRecord);
							dlgAddEditMobOperPref.show();
						}
					});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			String oper = operatorItem.getValueAsString();
			if (oper != null && !oper.trim().equals("")) {
				criteria.setAttribute("oper", oper);
			}
			String prefix = indexItem.getValueAsString();
			if (prefix != null && !prefix.trim().equals("")) {
				criteria.setAttribute("prefix", prefix);
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchMobOperPrefs");
			mobOperPrefsGrid.invalidateCache();
			mobOperPrefsGrid.filterData(criteria, new DSCallback() {
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
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			record.setAttribute("deleted", deleted);
			record.setAttribute("id", listGridRecord.getAttributeAsInt("id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateMobileOperatorPrefixStatus");
			mobOperPrefsGrid.updateData(record, new DSCallback() {
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
