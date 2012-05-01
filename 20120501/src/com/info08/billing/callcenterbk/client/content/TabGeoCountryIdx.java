package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.dialogs.address.DlgAddEditGeoIndCountry;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
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

public class TabGeoCountryIdx extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem geoIndRegionItem;
	private TextItem geoCountryGeoItem;
	private TextItem geoIndexItem;
	private ComboBoxItem isRaionCenterItem;

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

	public TabGeoCountryIdx() {
		try {
			setTitle("ინდექსების მართვა");
			setCanClose(true);

			datasource = DataSource.get("GeoIndCountryDS");

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

			geoIndRegionItem = new ComboBoxItem();
			geoIndRegionItem.setTitle("რაიონი");
			geoIndRegionItem.setWidth(350);
			geoIndRegionItem.setName("region_id");
			geoIndRegionItem.setFetchMissingValues(true);
			geoIndRegionItem.setFilterLocally(false);

			geoCountryGeoItem = new TextItem();
			geoCountryGeoItem.setTitle("დასახელება(ქართ.)");
			geoCountryGeoItem.setWidth(350);
			geoCountryGeoItem.setName("geo_country_geo");

			geoIndexItem = new TextItem();
			geoIndexItem.setTitle("ინდექსი");
			geoIndexItem.setWidth(350);
			geoIndexItem.setName("geo_index");

			isRaionCenterItem = new ComboBoxItem();
			isRaionCenterItem.setTitle("რაიონი");
			isRaionCenterItem.setWidth(350);
			isRaionCenterItem.setName("is_center");
			isRaionCenterItem.setValueMap(ClientMapUtil.getInstance().getRaionCentTypes());
			isRaionCenterItem.setDefaultToFirstOption(true);

			searchForm.setFields(geoIndRegionItem, geoCountryGeoItem,
					geoIndexItem, isRaionCenterItem);

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
			toolStrip.setWidth(880);
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

			listGrid.setWidth(880);
			listGrid.setHeight(300);
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

			datasource.getField("regionName").setTitle("რეგიონი");
			datasource.getField("geo_country_geo").setTitle("დასახელება (ქართ.)");
			datasource.getField("isCenterDescr").setTitle("ტიპი");
			datasource.getField("geo_index").setTitle("ინდექსი");
			datasource.getField("rec_date").setTitle("შექმინის თარიღი");
			datasource.getField("rec_user").setTitle("შემქმნელი");
			datasource.getField("upd_user").setTitle("ვინ განაახლა");

			ListGridField regionName = new ListGridField("regionName", "რეგიონი", 150);
			ListGridField geo_country_geo = new ListGridField("geo_country_geo", "დასახელება (ქართ.)", 150);
			ListGridField isCenterDescr = new ListGridField("isCenterDescr", "ტიპი", 100);
			ListGridField geo_index = new ListGridField("geo_index", "ინდექსი", 100);
			ListGridField rec_date = new ListGridField("rec_date","შექმინის თარიღი", 130);
			ListGridField rec_user = new ListGridField("rec_user", "შემქმნელი",100);
			ListGridField upd_user = new ListGridField("upd_user","ვინ განაახლა", 100);

			rec_date.setAlign(Alignment.CENTER);
			geo_index.setAlign(Alignment.CENTER);
			rec_user.setAlign(Alignment.CENTER);
			upd_user.setAlign(Alignment.CENTER);
			isCenterDescr.setAlign(Alignment.CENTER);

			listGrid.setFields(regionName, geo_country_geo,isCenterDescr,geo_index,
					rec_date, rec_user, upd_user);

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
					geoCountryGeoItem.clearValue();
					geoIndexItem.clearValue();
					isRaionCenterItem.clearValue();
					geoIndRegionItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditGeoIndCountry dlgAddEditGeoIndCountry = new DlgAddEditGeoIndCountry(
							listGrid, null);
					dlgAddEditGeoIndCountry.show();
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

					DlgAddEditGeoIndCountry dlgAddEditGeoIndCountry = new DlgAddEditGeoIndCountry(
							listGrid, listGridRecord);
					dlgAddEditGeoIndCountry.show();
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
					final Integer geo_country_id = listGridRecord
							.getAttributeAsInt("geo_country_id");
					if (geo_country_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(geo_country_id, 1);
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
					final Integer geo_country_id = listGridRecord
							.getAttributeAsInt("geo_country_id");
					if (geo_country_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის აღდგენა ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(geo_country_id, 0);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(880);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasource);
			detailViewer.setWidth(860);
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
					DlgAddEditGeoIndCountry dlgAddEditGeoIndCountry = new DlgAddEditGeoIndCountry(
							listGrid, listGridRecord);
					dlgAddEditGeoIndCountry.show();
				}
			});
			fillCombos();
			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void fillCombos() {
		try {
			DataSource geoIndRegDS = DataSource.get("GeoIndRegionDS");
			geoIndRegionItem.setOptionOperationId("searchGeoIndRegionFromDB");
			geoIndRegionItem.setOptionDataSource(geoIndRegDS);
			geoIndRegionItem.setValueField("region_id");
			geoIndRegionItem.setDisplayField("region_name_geo");
			geoIndRegionItem.setAutoFetchData(true);
			geoIndRegionItem.fetchData(new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String region_id = geoIndRegionItem.getValueAsString();
			String geo_country_geo = geoCountryGeoItem.getValueAsString();
			String geo_index = geoIndexItem.getValueAsString();
			String is_center = isRaionCenterItem.getValueAsString();
			Criteria criteria = new Criteria();
			criteria.setAttribute("region_id",region_id);
			criteria.setAttribute("geo_country_geo",geo_country_geo);
			criteria.setAttribute("geo_index",geo_index);
			criteria.setAttribute("is_center",is_center);

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchGeoIndRegionsFromDB");
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

	private void changeStatus(Integer geo_country_id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("geo_country_id", geo_country_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateGeoIndCountryStatus");
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
