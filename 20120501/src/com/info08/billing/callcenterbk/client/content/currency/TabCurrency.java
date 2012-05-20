package com.info08.billing.callcenterbk.client.content.currency;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.currency.DlgAddEditRate;
import com.info08.billing.callcenterbk.client.dialogs.currency.DlgAddEditRateCurr;
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

public class TabCurrency extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem countryItem;
	private TextItem currNameGeoItem;
	private TextItem currAbbrItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton disableBtn;
	private ToolStripButton reteBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabCurrency() {
		try {
			setTitle(CallCenterBK.constants.manageCurrency());
			setCanClose(true);

			datasource = DataSource.get("CurrencyDS");

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
			countryItem.setTitle(CallCenterBK.constants.country());
			countryItem.setWidth(300);
			countryItem.setName("country_id");
			countryItem.setFetchMissingValues(true);
			countryItem.setFilterLocally(false);
			countryItem.setAddUnknownValues(false);

			DataSource countryDS = DataSource.get("CountryDS");
			countryItem.setOptionOperationId("searchAllCountriesForCombos");
			countryItem.setOptionDataSource(countryDS);
			countryItem.setValueField("country_id");
			countryItem.setDisplayField("country_name");

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
			currNameGeoItem.setTitle(CallCenterBK.constants.currencyName());
			currNameGeoItem.setName("name_descr");
			currNameGeoItem.setWidth(300);

			currAbbrItem = new TextItem();
			currAbbrItem.setTitle(CallCenterBK.constants.currencyAbbr());
			currAbbrItem.setName("code");
			currAbbrItem.setWidth(300);

			searchForm.setFields(countryItem, currNameGeoItem, currAbbrItem);

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
			toolStrip.setWidth(780);
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

			disableBtn = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			disableBtn.setLayoutAlign(Alignment.LEFT);
			disableBtn.setWidth(50);
			toolStrip.addButton(disableBtn);

			toolStrip.addSeparator();

			reteBtn = new ToolStripButton(CallCenterBK.constants.rates(),
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

					return super.getCellCSSText(record, rowNum, colNum);

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
			listGrid.setFetchOperation("searchAllCurrency");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("country_name").setTitle(
					CallCenterBK.constants.country());
			datasource.getField("name_descr").setTitle(
					CallCenterBK.constants.currencyName());
			datasource.getField("code").setTitle(
					CallCenterBK.constants.currencyAbbr());
			datasource.getField("sort_order").setTitle(
					CallCenterBK.constants.order());

			ListGridField country_name = new ListGridField(
					"country_name", CallCenterBK.constants.country(), 250);
			ListGridField name_descr = new ListGridField("name_descr",
					CallCenterBK.constants.currencyName(), 250);
			ListGridField code = new ListGridField("code",
					CallCenterBK.constants.currencyAbbr(), 150);
			ListGridField sort_order = new ListGridField("sort_order",
					CallCenterBK.constants.order(), 70);

			country_name.setAlign(Alignment.LEFT);
			name_descr.setAlign(Alignment.LEFT);
			code.setAlign(Alignment.CENTER);
			sort_order.setAlign(Alignment.CENTER);

			listGrid.setFields(country_name, name_descr, code, sort_order);

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
						SC.say(CallCenterBK.constants.pleaseSelrecord());
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
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}

					final Integer curr_id = listGridRecord
							.getAttributeAsInt("currency_id");
					SC.ask(CallCenterBK.constants.askForDisable(),
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

			reteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditRate dlgAddEditRate = new DlgAddEditRate(
							listGridRecord);
					dlgAddEditRate.show();
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(780);
			Tab tabDetViewer = new Tab(CallCenterBK.constants.view());
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
						SC.say(CallCenterBK.constants.pleaseSelrecord());
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
			String name_descr = currNameGeoItem.getValueAsString();
			if (name_descr != null && !name_descr.trim().equals("")) {
				criteria.setAttribute("name_descr", name_descr);
			}
			String code = currAbbrItem.getValueAsString();
			if (code != null && !code.trim().equals("")) {
				criteria.setAttribute("code", code);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllCurrency");
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
			final Record record = new Record();
			record.setAttribute("currency_id", curr_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeCurrency");
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
