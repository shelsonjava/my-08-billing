package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditLandlineIndexes;
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

public class TabLandlineIndexes extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem landlineIndexItem;

	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addLandlineIndex;
	private ToolStripButton editLandlineIndex;
	private ToolStripButton deleteLandlineIndex;

	private ListGrid LandlineIndexesGrid;
	private DataSource LandlineIndexesDS;

	public TabLandlineIndexes() {
		try {

			setTitle(CallCenterBK.constants.fixedOpIndexes());
			setCanClose(true);

			LandlineIndexesDS = DataSource.get("LandlineIndexesDS");

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

			landlineIndexItem = new TextItem();
			landlineIndexItem.setTitle(CallCenterBK.constants.index());
			landlineIndexItem.setWidth(250);
			landlineIndexItem.setName("indexItem");

			searchForm.setFields(landlineIndexItem);

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

			addLandlineIndex = new ToolStripButton(
					CallCenterBK.constants.add(), "addIcon.png");
			addLandlineIndex.setLayoutAlign(Alignment.LEFT);
			addLandlineIndex.setWidth(50);
			toolStrip.addButton(addLandlineIndex);

			editLandlineIndex = new ToolStripButton(
					CallCenterBK.constants.modify(), "editIcon.png");
			editLandlineIndex.setLayoutAlign(Alignment.LEFT);
			editLandlineIndex.setWidth(50);
			toolStrip.addButton(editLandlineIndex);

			deleteLandlineIndex = new ToolStripButton(
					CallCenterBK.constants.disable(), "deleteIcon.png");
			deleteLandlineIndex.setLayoutAlign(Alignment.LEFT);
			deleteLandlineIndex.setWidth(50);
			toolStrip.addButton(deleteLandlineIndex);

			LandlineIndexesGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					return super.getCellCSSText(record, rowNum, colNum);
				};
			};

			LandlineIndexesGrid.setWidth(835);
			LandlineIndexesGrid.setHeight(400);
			LandlineIndexesGrid.setAlternateRecordStyles(true);
			LandlineIndexesGrid.setDataSource(LandlineIndexesDS);
			LandlineIndexesGrid.setAutoFetchData(false);
			LandlineIndexesGrid.setShowFilterEditor(false);
			LandlineIndexesGrid.setCanEdit(false);
			LandlineIndexesGrid.setCanRemoveRecords(false);
			LandlineIndexesGrid.setFetchOperation("searchLandlineIndexes");
			LandlineIndexesGrid.setShowRowNumbers(true);
			LandlineIndexesGrid.setCanHover(true);
			LandlineIndexesGrid.setShowHover(true);
			LandlineIndexesGrid.setShowHoverComponents(true);

			LandlineIndexesDS.getField("landline_index").setTitle(
					CallCenterBK.constants.index());

			ListGridField prefix = new ListGridField("landline_index",
					CallCenterBK.constants.index(), 307);

			LandlineIndexesGrid.setFields(prefix);

			mainLayout.addMember(LandlineIndexesGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					landlineIndexItem.clearValue();
				}
			});

			addLandlineIndex.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditLandlineIndexes dlgAddEditFixedOperPref = new DlgAddEditLandlineIndexes(
							LandlineIndexesGrid, null);
					dlgAddEditFixedOperPref.show();
				}
			});

			editLandlineIndex.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = LandlineIndexesGrid
							.getSelectedRecord();
					DlgAddEditLandlineIndexes dlgAddEditFixedOperPref = new DlgAddEditLandlineIndexes(
							LandlineIndexesGrid, listGridRecord);
					dlgAddEditFixedOperPref.show();
				}
			});
			deleteLandlineIndex.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = LandlineIndexesGrid
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
			detailViewer.setDataSource(LandlineIndexesDS);
			detailViewer.setWidth(800);
			tabDetViewer.setPane(detailViewer);

			LandlineIndexesGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(LandlineIndexesGrid);
				}
			});
			LandlineIndexesGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = LandlineIndexesGrid
									.getSelectedRecord();
							DlgAddEditLandlineIndexes dlgAddEditFixedOperPref = new DlgAddEditLandlineIndexes(
									LandlineIndexesGrid, listGridRecord);
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
			String landline_index = landlineIndexItem.getValueAsString();
			if (landline_index != null && !landline_index.trim().equals("")) {
				criteria.setAttribute("landline_index", landline_index);
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchLandlineIndexes");
			LandlineIndexesGrid.invalidateCache();
			LandlineIndexesGrid.filterData(criteria, new DSCallback() {
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
			record.setAttribute("landline_id",
					listGridRecord.getAttributeAsInt("landline_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "deleteLandlineIndexes");
			LandlineIndexesGrid.removeData(record, new DSCallback() {
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
