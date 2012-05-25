package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditCountryIndexes;
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

public class TabCountryIndexes extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem countryIndexValueItem;
	private TextItem countryIndexRemarkGeoItem;
	private TextItem countryIndexRemarkEngItem;
	private ComboBoxItem countryItem;

	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ListGrid countryIndexesGrid;
	private DataSource countryIndexesDS;

	public TabCountryIndexes() {
		try {

			setTitle(CallCenterBK.constants.mobOpIndexes());
			setCanClose(true);

			countryIndexesDS = DataSource.get("CountryIndexesDS");

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

			countryIndexValueItem = new TextItem();
			countryIndexValueItem.setTitle(CallCenterBK.constants.index());
			countryIndexValueItem.setWidth(250);
			countryIndexValueItem.setName("countryIndexValueItem");

			countryIndexRemarkGeoItem = new TextItem();
			countryIndexRemarkGeoItem.setTitle(CallCenterBK.constants
					.indexRemarkGeo());
			countryIndexRemarkGeoItem.setWidth(250);
			countryIndexRemarkGeoItem.setName("countryIndexRemarkGeoItem");

			countryIndexRemarkEngItem = new TextItem();
			countryIndexRemarkEngItem.setTitle(CallCenterBK.constants
					.indexRemarkEng());
			countryIndexRemarkEngItem.setWidth(250);
			countryIndexRemarkEngItem.setName("countryIndexRemarkEngItem");

			countryItem = new ComboBoxItem();
			countryItem.setWidth(250);
			countryItem.setTitle("ქვეყანა");
			countryItem.setName("country_id");
			countryItem.setFetchMissingValues(true);
			countryItem.setFilterLocally(false);
			countryItem.setAddUnknownValues(false);

			searchForm.setFields(countryItem, countryIndexRemarkGeoItem,
					countryIndexValueItem, countryIndexRemarkEngItem);

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

			countryIndexesGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					return super.getCellCSSText(record, rowNum, colNum);
				};
			};

			countryIndexesGrid.setWidth(835);
			countryIndexesGrid.setHeight(600);
			countryIndexesGrid.setAlternateRecordStyles(true);
			countryIndexesGrid.setDataSource(countryIndexesDS);
			countryIndexesGrid.setAutoFetchData(false);
			countryIndexesGrid.setShowFilterEditor(false);
			countryIndexesGrid.setCanEdit(false);
			countryIndexesGrid.setCanRemoveRecords(false);
			countryIndexesGrid.setFetchOperation("searchCountryIndexes");
			countryIndexesGrid.setShowRowNumbers(true);
			countryIndexesGrid.setCanHover(true);
			countryIndexesGrid.setShowHover(true);
			countryIndexesGrid.setShowHoverComponents(true);

			countryIndexesDS.getField("country_name").setTitle(
					CallCenterBK.constants.country());
			countryIndexesDS.getField("country_index_value").setTitle(
					CallCenterBK.constants.index());
			countryIndexesDS.getField("country_index_remark_geo").setTitle(
					CallCenterBK.constants.indexRemarkGeo());
			countryIndexesDS.getField("country_index_remark_eng").setTitle(
					CallCenterBK.constants.indexRemarkEng());

			ListGridField country_name = new ListGridField("country_name",
					CallCenterBK.constants.country(), 160);
			ListGridField country_index_value = new ListGridField(
					"country_index_value", CallCenterBK.constants.index(), 150);
			ListGridField country_index_remark_geo = new ListGridField(
					"country_index_remark_geo",
					CallCenterBK.constants.indexRemarkGeo(), 200);
			ListGridField country_index_remark_eng = new ListGridField(
					"country_index_remark_eng",
					CallCenterBK.constants.indexRemarkEng(), 200);

			countryIndexesGrid.setFields(country_name, country_index_value,
					country_index_remark_geo, country_index_remark_eng);

			mainLayout.addMember(countryIndexesGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					countryItem.clearValue();
					countryIndexValueItem.clearValue();
					countryIndexRemarkGeoItem.clearValue();
					countryIndexRemarkEngItem.clearValue();
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditCountryIndexes dlgAddEditCountryIndexes = new DlgAddEditCountryIndexes(
							countryIndexesGrid, null);
					dlgAddEditCountryIndexes.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = countryIndexesGrid
							.getSelectedRecord();
					DlgAddEditCountryIndexes dlgAddEditCountryIndexes = new DlgAddEditCountryIndexes(
							countryIndexesGrid, listGridRecord);
					dlgAddEditCountryIndexes.show();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = countryIndexesGrid
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

			countryIndexesGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = countryIndexesGrid
									.getSelectedRecord();
							DlgAddEditCountryIndexes dlgAddEditCountryIndexes = new DlgAddEditCountryIndexes(
									countryIndexesGrid, listGridRecord);
							dlgAddEditCountryIndexes.show();
						}
					});

			setPane(mainLayout);
			fillFields();

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			DataSource countryDS = DataSource.get("CountryDS");
			countryItem.setOptionOperationId("searchAllCountriesForCombos");
			countryItem.setOptionDataSource(countryDS);
			countryItem.setValueField("country_id");
			countryItem.setDisplayField("country_name");
			Criteria criteria = new Criteria();
			countryItem.setOptionCriteria(criteria);
			countryItem.setAutoFetchData(false);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			String country_id = countryItem.getValueAsString();
			if (country_id != null && !country_id.trim().equals("")) {
				criteria.setAttribute("country_id", country_id);
			}
			String country_index_value = countryIndexValueItem
					.getValueAsString();
			if (country_index_value != null
					&& !country_index_value.trim().equals("")) {
				criteria.setAttribute("country_index_value",
						country_index_value);
			}
			String country_index_remark_geo = countryIndexRemarkGeoItem
					.getValueAsString();
			if (country_index_remark_geo != null
					&& !country_index_remark_geo.trim().equals("")) {
				criteria.setAttribute("country_index_remark_geo",
						country_index_remark_geo);
			}
			String country_index_remark_eng = countryIndexRemarkEngItem
					.getValueAsString();
			if (country_index_remark_eng != null
					&& !country_index_remark_eng.trim().equals("")) {
				criteria.setAttribute("country_index_remark_eng",
						country_index_remark_eng);
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchCountryIndexes");
			countryIndexesGrid.invalidateCache();
			countryIndexesGrid.filterData(criteria, new DSCallback() {
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
			record.setAttribute("country_index_id",
					listGridRecord.getAttributeAsInt("country_index_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "deleteCountryIndexes");
			countryIndexesGrid.removeData(record, new DSCallback() {
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
