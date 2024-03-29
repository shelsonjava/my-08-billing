package com.info08.billing.callcenter.client.content.currency;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.dialogs.currency.DlgAddEditRate;
import com.info08.billing.callcenter.client.dialogs.currency.DlgAddEditRateCurr;
import com.info08.billing.callcenter.client.singletons.ClientMapUtil;
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

public class TabRateCurrency extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem countryItem;
	private TextItem currNameGeoItem;
	private TextItem currAbbrItem;
	private ComboBoxItem deletedItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton disableBtn;
	private ToolStripButton activateBtn;
	private ToolStripButton reteBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabRateCurrency() {
		try {
			setTitle(CallCenter.constants.manageCurrency());
			setCanClose(true);

			datasource = DataSource.get("RateCurrDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(300);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			countryItem = new ComboBoxItem();
			countryItem.setTitle(CallCenter.constants.country());
			countryItem.setWidth(300);
			countryItem.setName("country_id");
			countryItem.setFetchMissingValues(true);
			countryItem.setFilterLocally(false);
			countryItem.setAddUnknownValues(false);

			DataSource countryDS = DataSource.get("CountryDS");
			countryItem.setOptionOperationId("searchAllCountriesForCombos");
			countryItem.setOptionDataSource(countryDS);
			countryItem.setValueField("country_id");
			countryItem.setDisplayField("country_name_geo");

			countryItem.setOptionCriteria(new Criteria());
			countryItem.setAutoFetchData(false);

			countryItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = countryItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("country_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("country_id", nullO);
						}
					}
				}
			});

			currNameGeoItem = new TextItem();
			currNameGeoItem.setTitle(CallCenter.constants.currencyName());
			currNameGeoItem.setName("curr_name_geo");
			currNameGeoItem.setWidth(300);

			currAbbrItem = new TextItem();
			currAbbrItem.setTitle(CallCenter.constants.currencyAbbr());
			currAbbrItem.setName("curr_abbrev");
			currAbbrItem.setWidth(300);

			deletedItem = new ComboBoxItem();
			deletedItem.setTitle(CallCenter.constants.status());
			deletedItem.setWidth(300);
			deletedItem.setName("deleted_curr_rates");
			deletedItem.setValueMap(ClientMapUtil.getInstance().getStatuses());
			deletedItem.setDefaultToFirstOption(true);

			searchForm.setFields(countryItem, currNameGeoItem, currAbbrItem,
					deletedItem);

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

			reteBtn = new ToolStripButton(CallCenter.constants.rates(),
					"exchange.png");
			reteBtn.setLayoutAlign(Alignment.LEFT);
			reteBtn.setWidth(50);
			toolStrip.addButton(reteBtn);

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
			listGrid.setHeight(260);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllRateCurrs");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("country_name_geo").setTitle(
					CallCenter.constants.country());
			datasource.getField("curr_name_geo").setTitle(
					CallCenter.constants.currencyName());
			datasource.getField("curr_abbrev").setTitle(
					CallCenter.constants.currencyAbbr());
			datasource.getField("curr_order").setTitle(
					CallCenter.constants.order());
			datasource.getField("rec_date").setTitle(
					CallCenter.constants.recDate());
			datasource.getField("rec_user").setTitle(
					CallCenter.constants.recUser());
			datasource.getField("upd_date").setTitle(
					CallCenter.constants.updDate());
			datasource.getField("upd_user").setTitle(
					CallCenter.constants.updUser());

			ListGridField country_name_geo = new ListGridField(
					"country_name_geo", CallCenter.constants.country(), 250);
			ListGridField curr_name_geo = new ListGridField("curr_name_geo",
					CallCenter.constants.currencyName(), 250);
			ListGridField curr_abbrev = new ListGridField("curr_abbrev",
					CallCenter.constants.currencyAbbr(), 150);
			ListGridField curr_order = new ListGridField("curr_order",
					CallCenter.constants.order(), 70);

			country_name_geo.setAlign(Alignment.LEFT);
			curr_name_geo.setAlign(Alignment.LEFT);
			curr_abbrev.setAlign(Alignment.CENTER);
			curr_order.setAlign(Alignment.CENTER);

			listGrid.setFields(country_name_geo, curr_name_geo, curr_abbrev,
					curr_order);

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
					countryItem.clearValue();
					currNameGeoItem.clearValue();
					currAbbrItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditRateCurr dlgAddEditEntCurrency = new DlgAddEditRateCurr(
							listGrid, null);
					dlgAddEditEntCurrency.show();
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
					DlgAddEditRateCurr dlgAddEditEntCurrency = new DlgAddEditRateCurr(
							listGrid, listGridRecord);
					dlgAddEditEntCurrency.show();
				}
			});
			disableBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
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
					final Integer curr_id = listGridRecord
							.getAttributeAsInt("curr_id");
					SC.ask(CallCenter.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(curr_id, 1);
									}
								}
							});
				}
			});
			activateBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
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
					final Integer curr_id = listGridRecord
							.getAttributeAsInt("curr_id");
					SC.ask(CallCenter.constants.askForEnable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(curr_id, 0);
									}
								}
							});
				}
			});

			reteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditRate dlgAddEditRate = new DlgAddEditRate(
							listGridRecord);
					dlgAddEditRate.show();
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
					DlgAddEditRate dlgAddEditRate = new DlgAddEditRate(
							listGridRecord);
					dlgAddEditRate.show();
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
			String country_id = countryItem.getValueAsString();
			Criteria criteria = new Criteria();
			if (country_id != null && !country_id.trim().equals("")) {
				criteria.setAttribute("country_id", new Integer(country_id));
			}
			String curr_name_geo = currNameGeoItem.getValueAsString();
			if (curr_name_geo != null && !curr_name_geo.trim().equals("")) {
				criteria.setAttribute("curr_name_geo", curr_name_geo);
			}
			String curr_abbrev = currAbbrItem.getValueAsString();
			if (curr_abbrev != null && !curr_abbrev.trim().equals("")) {
				criteria.setAttribute("curr_abbrev", curr_abbrev);
			}
			String deleted_str = deletedItem.getValueAsString();
			if (deleted_str != null && !deleted_str.trim().equals("")) {
				criteria.setAttribute("deleted", new Integer(deleted_str));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllRateCurrs");
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

	private void changeStatus(Integer curr_id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("curr_id", curr_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateRateCurrStatus");
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
