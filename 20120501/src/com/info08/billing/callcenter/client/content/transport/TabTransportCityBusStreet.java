package com.info08.billing.callcenter.client.content.transport;

import com.info08.billing.callcenter.client.dialogs.transport.DlgAddEditBusRouteStreet;
import com.info08.billing.callcenter.client.singletons.ClientMapUtil;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.shared.common.Constants;
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

public class TabTransportCityBusStreet extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem routeItem;
	private ComboBoxItem streetItem;
	private ComboBoxItem routeDirItem;
	private TextItem notesItem;

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

	public TabTransportCityBusStreet() {
		try {
			setTitle("მიკრო/ავტ. მარშუტების მართვა");
			setCanClose(true);

			datasource = DataSource.get("BusRouteStreetDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(923);
			searchForm.setTitleWidth(150);
			searchForm.setNumCols(4);
			mainLayout.addMember(searchForm);

			routeItem = new ComboBoxItem();
			routeItem.setTitle("მარშუტის ნომერი");
			routeItem.setWidth(300);
			routeItem.setName("route_descr");
			routeItem.setFetchMissingValues(true);
			routeItem.setFilterLocally(false);
			routeItem.setAddUnknownValues(false);

			routeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = routeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("route_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("route_id", nullO);
						}
					}
				}
			});

			streetItem = new ComboBoxItem();
			streetItem.setTitle("ქუჩა");
			streetItem.setName("street_name_geo");
			streetItem.setWidth(300);
			streetItem.setFetchMissingValues(true);
			streetItem.setFilterLocally(false);
			streetItem.setAddUnknownValues(false);

			streetItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("street_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_id", nullO);
						}
					}
				}
			});

			routeDirItem = new ComboBoxItem();
			routeDirItem.setTitle("მიმართულება");
			routeDirItem.setName("route_dir");
			routeDirItem.setWidth(300);
			routeDirItem.setValueMap(ClientMapUtil.getInstance()
					.getRouteDirTypes());

			notesItem = new TextItem();
			notesItem.setTitle("კომენტარი");
			notesItem.setName("notes");
			notesItem.setWidth(300);

			searchForm
					.setFields(routeItem, streetItem, routeDirItem, notesItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(910);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(910);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton("დამატება", "addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton("შეცვლა", "editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton("გაუქმება", "deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			restoreBtn = new ToolStripButton("აღდგენა", "restoreIcon.gif");
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

			listGrid.setWidth(910);
			listGrid.setHeight(330);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllBusRouteStreets");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("route_descr").setTitle("მარშუტის N");
			datasource.getField("street_name").setTitle("ქუჩა");
			datasource.getField("notes").setTitle("კომენტარი");
			datasource.getField("route_dir_descr").setTitle("მიმართულება");
			datasource.getField("route_order").setTitle("ნუმ.");
			datasource.getField("rec_date").setTitle("შექმინის თარიღი");
			datasource.getField("rec_user").setTitle("შემქმნელი");
			datasource.getField("upd_user").setTitle("ვინ განაახლა");

			ListGridField route_descr = new ListGridField("route_descr",
					"მარშუტის N", 100);
			ListGridField street_name = new ListGridField("street_name",
					"ქუჩა", 200);
			ListGridField notes = new ListGridField("notes", "კომენტარი", 150);
			ListGridField route_dir_descr = new ListGridField(
					"route_dir_descr", "მიმართ.", 50);
			ListGridField route_order = new ListGridField("route_order",
					"ნუმ.", 50);
			ListGridField rec_date = new ListGridField("rec_date",
					"შექმინის თარიღი", 130);
			ListGridField rec_user = new ListGridField("rec_user", "შემქმნელი",
					80);
			ListGridField upd_user = new ListGridField("upd_user",
					"ვინ განაახლა", 100);

			route_dir_descr.setAlign(Alignment.CENTER);
			route_order.setAlign(Alignment.CENTER);
			rec_date.setAlign(Alignment.CENTER);
			rec_user.setAlign(Alignment.CENTER);
			upd_user.setAlign(Alignment.CENTER);

			listGrid.setFields(route_descr, street_name, notes,
					route_dir_descr, route_order, rec_date, rec_user, upd_user);

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
					routeItem.clearValue();
					streetItem.clearValue();
					routeDirItem.clearValue();
					notesItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditBusRouteStreet dlgAddEditBusRouteStreet = new DlgAddEditBusRouteStreet(
							listGrid, null);
					dlgAddEditBusRouteStreet.show();
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
					DlgAddEditBusRouteStreet dlgAddEditBusRouteStreet = new DlgAddEditBusRouteStreet(
							listGrid, listGridRecord);
					dlgAddEditBusRouteStreet.show();
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
					final Integer route_street_id = listGridRecord
							.getAttributeAsInt("route_street_id");
					if (route_street_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(route_street_id, 1);
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
					final Integer route_street_id = listGridRecord
							.getAttributeAsInt("route_street_id");
					if (route_street_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის აღდგენა ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(route_street_id, 0);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(910);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasource);
			detailViewer.setWidth(890);
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
					DlgAddEditBusRouteStreet dlgAddEditBusRouteStreet = new DlgAddEditBusRouteStreet(
							listGrid, listGridRecord);
					dlgAddEditBusRouteStreet.show();
				}
			});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
			fillCombos();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void fillCombos() {
		try {
			DataSource busRouteDS = DataSource.get("BusRouteDS");
			routeItem.setOptionOperationId("searchAllBusRoutesForCombos");
			routeItem.setOptionDataSource(busRouteDS);
			routeItem.setValueField("route_id");
			routeItem.setDisplayField("route_nm");

			Criteria criteria = new Criteria();
			routeItem.setOptionCriteria(criteria);
			routeItem.setAutoFetchData(false);

			DataSource streetsDS = DataSource.get("StreetsNewDS");
			streetItem.setOptionOperationId("searchStreetFromDBForCombos");
			streetItem.setOptionDataSource(streetsDS);
			streetItem.setValueField("street_id");
			streetItem.setDisplayField("street_name_geo");

			criteria.setAttribute("city_id", Constants.defCityTbilisiId);
			streetItem.setOptionCriteria(criteria);
			streetItem.setAutoFetchData(false);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String route_id = routeItem.getValueAsString();
			String street_id = streetItem.getValueAsString();
			String route_dir = routeDirItem.getValueAsString();
			String notes = notesItem.getValueAsString();

			Criteria criteria = new Criteria();
			if (route_id != null) {
				criteria.setAttribute("route_id", new Integer(route_id));
			}
			if (street_id != null) {
				criteria.setAttribute("street_id", new Integer(street_id));
			}
			if (route_dir != null) {
				criteria.setAttribute("route_dir", new Integer(route_dir));
			}
			if (notes != null && !notes.trim().equals("")) {
				criteria.setAttribute("notes", notes);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllBusRouteStreets");
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

	private void changeStatus(Integer route_street_id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("route_street_id", route_street_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateBusRouteStreetStatus");
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
