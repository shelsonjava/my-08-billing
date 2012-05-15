package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditGSMIndexes;
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

public class TabGSMIndexes extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem gsmCompanyItem;
	private TextItem gsmIndexItem;

	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addGSMIndexesBtn;
	private ToolStripButton editGSMIndexesBtn;
	private ToolStripButton deleteGSMIndexesBtn;
	private ListGrid gsmIndexesGrid;
	private DataSource gsmIndexesDS;

	public TabGSMIndexes() {
		try {

			setTitle(CallCenterBK.constants.mobOpIndexes());
			setCanClose(true);

			gsmIndexesDS = DataSource.get("GSMIndexesDS");

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

			gsmCompanyItem = new TextItem();
			gsmCompanyItem.setTitle(CallCenterBK.constants.mobOperator());
			gsmCompanyItem.setWidth(250);
			gsmCompanyItem.setName("gsmCompanyItem");

			gsmIndexItem = new TextItem();
			gsmIndexItem.setTitle(CallCenterBK.constants.mobOperatorIndex());
			gsmIndexItem.setWidth(250);
			gsmIndexItem.setName("gsmIndexItem");

			searchForm.setFields(gsmCompanyItem, gsmIndexItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(835);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(835);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addGSMIndexesBtn = new ToolStripButton(
					CallCenterBK.constants.add(), "addIcon.png");
			addGSMIndexesBtn.setLayoutAlign(Alignment.LEFT);
			addGSMIndexesBtn.setWidth(50);
			toolStrip.addButton(addGSMIndexesBtn);

			editGSMIndexesBtn = new ToolStripButton(
					CallCenterBK.constants.modify(), "editIcon.png");
			editGSMIndexesBtn.setLayoutAlign(Alignment.LEFT);
			editGSMIndexesBtn.setWidth(50);
			toolStrip.addButton(editGSMIndexesBtn);

			deleteGSMIndexesBtn = new ToolStripButton(
					CallCenterBK.constants.disable(), "deleteIcon.png");
			deleteGSMIndexesBtn.setLayoutAlign(Alignment.LEFT);
			deleteGSMIndexesBtn.setWidth(50);
			toolStrip.addButton(deleteGSMIndexesBtn);

			gsmIndexesGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					return super.getCellCSSText(record, rowNum, colNum);
				};
			};

			gsmIndexesGrid.setWidth(835);
			gsmIndexesGrid.setHeight(400);
			gsmIndexesGrid.setAlternateRecordStyles(true);
			gsmIndexesGrid.setDataSource(mobOperPrefDS);
			gsmIndexesGrid.setAutoFetchData(false);
			gsmIndexesGrid.setShowFilterEditor(false);
			gsmIndexesGrid.setCanEdit(false);
			gsmIndexesGrid.setCanRemoveRecords(false);
			gsmIndexesGrid.setFetchOperation("searchMobOperPrefs");
			gsmIndexesGrid.setShowRowNumbers(true);
			gsmIndexesGrid.setCanHover(true);
			gsmIndexesGrid.setShowHover(true);
			gsmIndexesGrid.setShowHoverComponents(true);

			mobOperPrefDS.getField("gsm_company").setTitle(
					CallCenterBK.constants.mobOperator());
			mobOperPrefDS.getField("gsm_index").setTitle(
					CallCenterBK.constants.mobOperatorIndex());

			ListGridField gsm_company = new ListGridField("gsm_company",
					CallCenterBK.constants.mobOperator(), 157);
			ListGridField prefix = new ListGridField("gsm_index",
					CallCenterBK.constants.mobOperatorIndex(), 150);

			mobOperPrefsGrid.setFields(gsm_company, prefix);

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
					gsmCompanyItem.clearValue();
					gsmIndexItem.clearValue();
				}
			});

			addMobOperPrefBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditGSMIndexes dlgAddEditMobOperPref = new DlgAddEditGSMIndexes(
							mobOperPrefsGrid, null);
					dlgAddEditMobOperPref.show();
				}
			});

			editMobOperPrefBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = mobOperPrefsGrid
							.getSelectedRecord();
					DlgAddEditGSMIndexes dlgAddEditMobOperPref = new DlgAddEditGSMIndexes(
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
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}

					SC.ask(CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(listGridRecord);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(835);
			Tab tabDetViewer = new Tab(CallCenterBK.constants.view());
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
							DlgAddEditGSMIndexes dlgAddEditMobOperPref = new DlgAddEditGSMIndexes(
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
			String gsm_company = gsmCompanyItem.getValueAsString();
			if (gsm_company != null && !gsm_company.trim().equals("")) {
				criteria.setAttribute("gsm_company", gsm_company);
			}
			String gsm_index = gsmIndexItem.getValueAsString();
			if (gsm_index != null && !gsm_index.trim().equals("")) {
				criteria.setAttribute("gsm_index", gsm_index);
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchGSMIndexes");
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

	private void delete(ListGridRecord listGridRecord) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("gsm_index_id",
					listGridRecord.getAttributeAsInt("gsm_index_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "deleteGSMIndexes");
			mobOperPrefsGrid.removeData(record, new DSCallback() {
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
