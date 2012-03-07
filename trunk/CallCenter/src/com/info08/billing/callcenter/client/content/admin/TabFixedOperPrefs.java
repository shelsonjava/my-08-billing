package com.info08.billing.callcenter.client.content.admin;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.dialogs.admin.DlgAddEditFixedOperPref;
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

public class TabFixedOperPrefs extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem indexItem;

	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addFixedOperPrefBtn;
	private ToolStripButton editFixedOperPrefBtn;
	private ToolStripButton deleteFixedOperPrefBtn;
	private ToolStripButton restoreFixedOperPrefBtn;
	private ListGrid fixedOperPrefsGrid;
	private DataSource fixedOperPrefDS;

	public TabFixedOperPrefs() {
		try {

			setTitle(CallCenter.constants.fixedOpIndexes());
			setCanClose(true);

			fixedOperPrefDS = DataSource.get("FixedOpPrefDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			//
			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(835);
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			indexItem = new TextItem();
			indexItem.setTitle(CallCenter.constants.index());
			indexItem.setWidth(250);
			indexItem.setName("indexItem");

			searchForm.setFields(indexItem);

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

			addFixedOperPrefBtn = new ToolStripButton(
					CallCenter.constants.add(), "addIcon.png");
			addFixedOperPrefBtn.setLayoutAlign(Alignment.LEFT);
			addFixedOperPrefBtn.setWidth(50);
			toolStrip.addButton(addFixedOperPrefBtn);

			editFixedOperPrefBtn = new ToolStripButton(
					CallCenter.constants.modify(), "editIcon.png");
			editFixedOperPrefBtn.setLayoutAlign(Alignment.LEFT);
			editFixedOperPrefBtn.setWidth(50);
			toolStrip.addButton(editFixedOperPrefBtn);

			deleteFixedOperPrefBtn = new ToolStripButton(
					CallCenter.constants.disable(), "deleteIcon.png");
			deleteFixedOperPrefBtn.setLayoutAlign(Alignment.LEFT);
			deleteFixedOperPrefBtn.setWidth(50);
			toolStrip.addButton(deleteFixedOperPrefBtn);

			restoreFixedOperPrefBtn = new ToolStripButton(
					CallCenter.constants.enable(), "restoreIcon.gif");
			restoreFixedOperPrefBtn.setLayoutAlign(Alignment.LEFT);
			restoreFixedOperPrefBtn.setWidth(50);
			toolStrip.addButton(restoreFixedOperPrefBtn);

			fixedOperPrefsGrid = new ListGrid() {
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

			fixedOperPrefsGrid.setWidth(835);
			fixedOperPrefsGrid.setHeight(400);
			fixedOperPrefsGrid.setAlternateRecordStyles(true);
			fixedOperPrefsGrid.setDataSource(fixedOperPrefDS);
			fixedOperPrefsGrid.setAutoFetchData(false);
			fixedOperPrefsGrid.setShowFilterEditor(false);
			fixedOperPrefsGrid.setCanEdit(false);
			fixedOperPrefsGrid.setCanRemoveRecords(false);
			fixedOperPrefsGrid.setFetchOperation("searchFixedOperatorPrefixes");
			fixedOperPrefsGrid.setShowRowNumbers(true);
			fixedOperPrefsGrid.setCanHover(true);
			fixedOperPrefsGrid.setShowHover(true);
			fixedOperPrefsGrid.setShowHoverComponents(true);

			fixedOperPrefDS.getField("prefix").setTitle(
					CallCenter.constants.index());
			fixedOperPrefDS.getField("rec_date").setTitle(
					CallCenter.constants.recDate());
			fixedOperPrefDS.getField("rec_user").setTitle(
					CallCenter.constants.recUser());
			fixedOperPrefDS.getField("upd_date").setTitle(
					CallCenter.constants.updDate());
			fixedOperPrefDS.getField("upd_user").setTitle(
					CallCenter.constants.updUser());

			ListGridField prefix = new ListGridField("prefix",
					CallCenter.constants.index(), 307);
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

			fixedOperPrefsGrid.setFields(prefix, rec_user, rec_date, upd_user,
					upd_date);

			mainLayout.addMember(fixedOperPrefsGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					indexItem.clearValue();
				}
			});

			addFixedOperPrefBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditFixedOperPref dlgAddEditFixedOperPref = new DlgAddEditFixedOperPref(
							fixedOperPrefsGrid, null);
					dlgAddEditFixedOperPref.show();
				}
			});

			editFixedOperPrefBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = fixedOperPrefsGrid
							.getSelectedRecord();
					DlgAddEditFixedOperPref dlgAddEditFixedOperPref = new DlgAddEditFixedOperPref(
							fixedOperPrefsGrid, listGridRecord);
					dlgAddEditFixedOperPref.show();
				}
			});
			deleteFixedOperPrefBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = fixedOperPrefsGrid
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
			restoreFixedOperPrefBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = fixedOperPrefsGrid
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
			detailViewer.setDataSource(fixedOperPrefDS);
			detailViewer.setWidth(800);
			tabDetViewer.setPane(detailViewer);

			fixedOperPrefsGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(fixedOperPrefsGrid);
				}
			});
			fixedOperPrefsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = fixedOperPrefsGrid
									.getSelectedRecord();
							DlgAddEditFixedOperPref dlgAddEditFixedOperPref = new DlgAddEditFixedOperPref(
									fixedOperPrefsGrid, listGridRecord);
							dlgAddEditFixedOperPref.show();
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
			String prefix = indexItem.getValueAsString();
			if (prefix != null && !prefix.trim().equals("")) {
				criteria.setAttribute("prefix", prefix);
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest
					.setAttribute("operationId", "searchFixedOperatorPrefixes");
			fixedOperPrefsGrid.invalidateCache();
			fixedOperPrefsGrid.filterData(criteria, new DSCallback() {
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

			req.setAttribute("operationId", "updateFixedOperatorPrefixStatus");
			fixedOperPrefsGrid.updateData(record, new DSCallback() {
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
