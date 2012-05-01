package com.info08.billing.callcenter.client.content;

import com.info08.billing.callcenter.client.dialogs.address.DlgAddEditCityDistance;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.shared.common.Constants;
import com.info08.billing.callcenter.shared.common.SharedUtils;
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

public class TabCityDistance extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem cityStartItem;
	private ComboBoxItem cityEndItem;
	private ComboBoxItem distanceTypeItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ToolStripButton restoreBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabCityDistance() {
		try {
			setTitle("მანძილი ქალაქებს შორის");
			setCanClose(true);

			datasource = DataSource.get("CitiesDistDS");

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

			cityStartItem = new ComboBoxItem();
			cityStartItem.setTitle("საწყისი ქალაქი");
			cityStartItem.setWidth(350);
			cityStartItem.setName("city_id_start");
			cityStartItem.setFetchMissingValues(true);
			cityStartItem.setFilterLocally(false);
			cityStartItem.setAddUnknownValues(false);

			DataSource cityDS = DataSource.get("CityDS");
			cityStartItem.setOptionOperationId("searchCitiesFromDBForCombos1");
			cityStartItem.setOptionDataSource(cityDS);
			cityStartItem.setValueField("city_id");
			cityStartItem.setDisplayField("city_name_geo");

			Criteria criteriaCity = new Criteria();
			cityStartItem.setOptionCriteria(criteriaCity);
			cityStartItem.setAutoFetchData(false);

			cityStartItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = cityStartItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("city_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("city_id", nullO);
						}
					}
				}
			});

			cityEndItem = new ComboBoxItem();
			cityEndItem.setTitle("საბოლოო ქალაქი");
			cityEndItem.setWidth(350);
			cityEndItem.setName("city_id_end");
			cityEndItem.setFetchMissingValues(true);
			cityEndItem.setFilterLocally(false);
			cityEndItem.setAddUnknownValues(false);

			DataSource cityDS1 = DataSource.get("CityDS");
			cityEndItem.setOptionOperationId("searchCitiesFromDBForCombos1");
			cityEndItem.setOptionDataSource(cityDS1);
			cityEndItem.setValueField("city_id");
			cityEndItem.setDisplayField("city_name_geo");

			Criteria criteriaCity1 = new Criteria();
			cityEndItem.setOptionCriteria(criteriaCity1);
			cityEndItem.setAutoFetchData(false);

			cityEndItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = cityEndItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("city_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("city_id", nullO);
						}
					}
				}
			});
			

			distanceTypeItem = new ComboBoxItem();
			distanceTypeItem.setTitle("მანძილის ტიპი");
			distanceTypeItem.setName("city_id");
			distanceTypeItem.setWidth(350);
			distanceTypeItem.setValueMap(SharedUtils.getInstance()
					.getMapDistanceTypes());
			distanceTypeItem.setDefaultToFirstOption(true);

			searchForm.setFields(cityStartItem, cityEndItem, distanceTypeItem);

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

			restoreBtn = new ToolStripButton("აღდგენა", "person_add.png");
			restoreBtn.setLayoutAlign(Alignment.LEFT);
			restoreBtn.setWidth(50);
			toolStrip.addButton(restoreBtn);

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

			listGrid.setWidth(900);
			listGrid.setHeight(290);
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

			datasource.getField("cityStart").setTitle("საწყისი ქალაქი");
			datasource.getField("cityEnd").setTitle("საბოლოო ქალაქი");
			datasource.getField("cityDistTypeDesc").setTitle("მანძილის ტიპი");
			datasource.getField("note_geo").setTitle("კომენტარი");
			datasource.getField("rec_date").setTitle("შექმინის თარიღი");
			datasource.getField("rec_user").setTitle("შემქმნელი");
			datasource.getField("upd_user").setTitle("ვინ განაახლა");
			datasource.getField("upd_date").setTitle("როდის განაახლა");
			datasource.getField("city_distance_geo").setTitle("მანძილი (კმ.)");

			ListGridField cityStart = new ListGridField("cityStart",
					"საწყისი ქალაქი", 130); // 150
			ListGridField cityEnd = new ListGridField("cityEnd",
					"საბოლოო ქალაქი", 130); // 150
			ListGridField cityDistTypeDesc = new ListGridField(
					"cityDistTypeDesc", "მანძილის ტიპი", 100); // 150
			ListGridField city_distance_geo = new ListGridField(
					"city_distance_geo", "მანძილი (კმ.)", 100);
			ListGridField note_geo = new ListGridField("note_geo", "კომენტარი",
					150); // 150
			ListGridField rec_date = new ListGridField("rec_date",
					"შექმინის თარიღი", 130);
			ListGridField upd_user = new ListGridField("upd_user",
					"ვინ განაახლა", 100);

			rec_date.setAlign(Alignment.CENTER);
			upd_user.setAlign(Alignment.CENTER);

			listGrid.setFields(cityStart, cityEnd, cityDistTypeDesc,
					city_distance_geo, note_geo, rec_date, upd_user);

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
					cityEndItem.clearValue();
					cityStartItem.clearValue();
					distanceTypeItem.setValue(Constants.defCityTbilisiId);
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditCityDistance dlgAddEditCityDistance = new DlgAddEditCityDistance(
							listGrid, null);
					dlgAddEditCityDistance.show();
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

					DlgAddEditCityDistance dlgAddEditCityDistance = new DlgAddEditCityDistance(
							listGrid, listGridRecord);
					dlgAddEditCityDistance.show();
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
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (!deleted.equals(0)) {
						SC.say("ჩანაწერი უკვე გაუქმებულია !");
						return;
					}
					final Integer city_distance_id = listGridRecord
							.getAttributeAsInt("city_distance_id");
					if (city_distance_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(city_distance_id, 1);
									}
								}
							});
				}
			});
			restoreBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (deleted.equals(0)) {
						SC.say("ჩანაწერი უკვე აღდგენილია !");
						return;
					}
					final Integer city_distance_id = listGridRecord
							.getAttributeAsInt("city_distance_id");
					if (city_distance_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის აღდგენა ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(city_distance_id, 0);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(900);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasource);
			detailViewer.setWidth(870);
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

					DlgAddEditCityDistance dlgAddEditCityDistance = new DlgAddEditCityDistance(
							listGrid, listGridRecord);
					dlgAddEditCityDistance.show();
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
			ListGridRecord city_start_record = cityStartItem
					.getSelectedRecord();
			ListGridRecord city_end_record = cityEndItem.getSelectedRecord();
			Criteria criteria = new Criteria();
			if (city_start_record != null) {
				criteria.setAttribute("city_id_start",
						city_start_record.getAttributeAsString("city_id"));
			}
			if (city_end_record != null) {
				criteria.setAttribute("city_id_end",
						city_end_record.getAttributeAsString("city_id"));
			}
			if (distanceTypeItem.getValueAsString() != null) {
				criteria.setAttribute("city_distance_type", new Integer(
						distanceTypeItem.getValueAsString()));
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

	private void changeStatus(Integer city_distance_id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("city_distance_id", city_distance_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateCityDistanceStatus");
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
