package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.dialogs.address.DlgAddEditDistBetweenTowns;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.SharedUtils;
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

public class TabDistBetweenTowns extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem townStartItem;
	private ComboBoxItem townEndItem;
	private ComboBoxItem townDistanceTypeItem;

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

	public TabDistBetweenTowns() {
		try {
			setTitle("მანძილი ქალაქებს შორის");
			setCanClose(true);

			datasource = DataSource.get("DistBetweenTownsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(150);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			townStartItem = new ComboBoxItem();
			townStartItem.setTitle("საწყისი ქალაქი");
			townStartItem.setWidth(350);
			townStartItem.setName("town_id_start");
			townStartItem.setFetchMissingValues(true);
			townStartItem.setFilterLocally(false);
			townStartItem.setAddUnknownValues(false);

			DataSource townsDS = DataSource.get("TownsDS");
			townStartItem.setOptionOperationId("searchCitiesFromDBForCombos1"); // searchCitiesFromDBForCombosAll
			townStartItem.setOptionDataSource(townsDS);
			townStartItem.setValueField("town_id");
			townStartItem.setDisplayField("town_name");

			Criteria criteriaTown = new Criteria();
			townStartItem.setOptionCriteria(criteriaTown);
			townStartItem.setAutoFetchData(false);

			townStartItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = townStartItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("town_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("town_id", nullO);
						}
					}
				}
			});

			townEndItem = new ComboBoxItem();
			townEndItem.setTitle("საბოლოო ქალაქი");
			townEndItem.setWidth(350);
			townEndItem.setName("town_id_end");
			townEndItem.setFetchMissingValues(true);
			townEndItem.setFilterLocally(false);
			townEndItem.setAddUnknownValues(false);

			DataSource townsDS1 = DataSource.get("TownsDS");
			townEndItem.setOptionOperationId("searchCitiesFromDBForCombos1");
			townEndItem.setOptionDataSource(townsDS1);
			townEndItem.setValueField("town_id");
			townEndItem.setDisplayField("town_name");

			Criteria criteriaTown1 = new Criteria();
			townEndItem.setOptionCriteria(criteriaTown1);
			townEndItem.setAutoFetchData(false);

			townEndItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = townEndItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("town_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("town_id", nullO);
						}
					}
				}
			});

			townDistanceTypeItem = new ComboBoxItem();
			townDistanceTypeItem.setTitle("მანძილის ტიპი");
			townDistanceTypeItem.setName("town_id");
			townDistanceTypeItem.setWidth(350);
			townDistanceTypeItem.setValueMap(SharedUtils.getInstance()
					.getMapDistanceTypes());
			townDistanceTypeItem.setDefaultToFirstOption(true);

			searchForm.setFields(townStartItem, townEndItem,
					townDistanceTypeItem);

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
			toolStrip.setWidth(900);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton("დამატება", "person_add.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton("შეცვლა", "person_edit.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton("გაუქმება", "person_delete.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			toolStrip.addSeparator();

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

			listGrid.setWidth(900);
			listGrid.setHeight(500);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchFromDB");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("town_start").setTitle("საწყისი ქალაქი");
			datasource.getField("town_end").setTitle("საბოლოო ქალაქი");
			datasource.getField("town_distance_type_descr").setTitle(
					"მანძილის ტიპი");
			datasource.getField("dist_between_towns_remark").setTitle(
					"კომენტარი");
			datasource.getField("dist_between_towns_value").setTitle(
					"მანძილი (კმ.)");

			ListGridField town_start = new ListGridField("town_start",
					"საწყისი ქალაქი", 130); // 150
			ListGridField town_end = new ListGridField("town_end",
					"საბოლოო ქალაქი", 130); // 150
			ListGridField town_distance_type_descr = new ListGridField(
					"town_distance_type_descr", "მანძილის ტიპი", 100); // 150
			ListGridField dist_between_towns_value = new ListGridField(
					"dist_between_towns_value", "მანძილი (კმ.)", 100);
			ListGridField dist_between_towns_remark = new ListGridField(
					"dist_between_towns_remark", "კომენტარი", 300); // 150

			listGrid.setFields(town_start, town_end, town_distance_type_descr,
					dist_between_towns_value, dist_between_towns_remark);

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
					townEndItem.clearValue();
					townStartItem.clearValue();
					townDistanceTypeItem.setValue(Constants.defCityTbilisiId);
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditDistBetweenTowns dlgAddEditDistBetweenTowns = new DlgAddEditDistBetweenTowns(
							listGrid, null);
					dlgAddEditDistBetweenTowns.show();
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

					DlgAddEditDistBetweenTowns dlgAddEditDistBetweenTowns = new DlgAddEditDistBetweenTowns(
							listGrid, listGridRecord);
					dlgAddEditDistBetweenTowns.show();
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
					final Integer dist_between_towns_id = listGridRecord
							.getAttributeAsInt("dist_between_towns_id");
					if (dist_between_towns_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(dist_between_towns_id);
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

					DlgAddEditDistBetweenTowns dlgAddEditDistBetweenTowns = new DlgAddEditDistBetweenTowns(
							listGrid, listGridRecord);
					dlgAddEditDistBetweenTowns.show();
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
			ListGridRecord town_id_start = townStartItem
					.getSelectedRecord();
			ListGridRecord town_id_end = townEndItem.getSelectedRecord();
			Criteria criteria = new Criteria();
			if (town_id_start != null) {
				criteria.setAttribute("town_id_start",
						town_id_start.getAttributeAsString("town_id"));
			}
			if (town_id_end != null) {
				criteria.setAttribute("town_id_end",
						town_id_end.getAttributeAsString("town_id"));
			}
			if (townDistanceTypeItem.getValueAsString() != null) {
				criteria.setAttribute("town_distance_type", new Integer(
						townDistanceTypeItem.getValueAsString()));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchFromDB");
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

	private void delete(Integer dist_between_towns_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("dist_between_towns_id", dist_between_towns_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "deleteDistBetweenTowns");
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
