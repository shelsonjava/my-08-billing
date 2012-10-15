package com.info08.billing.callcenterbk.client.content.transport;

import com.info08.billing.callcenterbk.client.dialogs.transport.DlgAddEditBusRouteStreet;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
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
	private TextItem remarksItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	// private ToolStripButton restoreBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabTransportCityBusStreet() {
		try {
			setTitle("მიკრო/ავტ. მარშუტების მართვა");
			setCanClose(true);

			datasource = DataSource.get("PubTranspDirStreetDS");

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
			routeItem.setName("descr");
			routeItem.setFetchMissingValues(true);
			routeItem.setFilterLocally(false);
			routeItem.setAddUnknownValues(false);

			routeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = routeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("pt_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("pt_id", nullO);
						}
					}
				}
			});

			streetItem = new ComboBoxItem();
			streetItem.setTitle("ქუჩა");
			streetItem.setName("street_name");
			streetItem.setWidth(300);
			ClientUtils.fillCombo(streetItem, "StreetsDS",
					"searchStreetFromDBForCombos", "streets_id", "street_name");

			routeDirItem = new ComboBoxItem();
			routeDirItem.setTitle("მიმართულება");
			routeDirItem.setName("dir");
			routeDirItem.setWidth(300);
			routeDirItem.setValueMap(ClientMapUtil.getInstance()
					.getRouteDirTypes());

			remarksItem = new TextItem();
			remarksItem.setTitle("კომენტარი");
			remarksItem.setName("remarks");
			remarksItem.setWidth(300);

			searchForm.setFields(routeItem, streetItem, routeDirItem,
					remarksItem);

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

			// restoreBtn = new ToolStripButton("აღდგენა", "restoreIcon.gif");
			// restoreBtn.setLayoutAlign(Alignment.LEFT);
			// restoreBtn.setWidth(50);
			// toolStrip.addButton(restoreBtn);

			toolStrip.addSeparator();

			listGrid = new ListGrid();

			listGrid.setWidth(910);
			listGrid.setHeight(330);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllPublicTransportDirectionstreets");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("descr").setTitle("მარშუტის N");
			datasource.getField("street_name").setTitle("ქუჩა");
			datasource.getField("remarks").setTitle("კომენტარი");
			datasource.getField("dir_descr").setTitle("მიმართულება");
			datasource.getField("dir_order").setTitle("ნუმ.");

			ListGridField descr = new ListGridField("descr", "მარშუტის N", 100);
			ListGridField street_name = new ListGridField("street_name",
					"ქუჩა", 200);
			ListGridField remarks = new ListGridField("remarks", "კომენტარი",
					150);
			ListGridField dir_descr = new ListGridField("dir_descr", "მიმართ.",
					50);
			ListGridField dir_order = new ListGridField("dir_order", "ნუმ.", 50);

			dir_descr.setAlign(Alignment.CENTER);
			dir_order.setAlign(Alignment.CENTER);

			listGrid.setFields(descr, street_name, remarks, dir_descr,
					dir_order);

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
					remarksItem.clearValue();
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
					final Integer pts_id = listGridRecord
							.getAttributeAsInt("pts_id");
					if (pts_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(pts_id);
									}
								}
							});
				}
			});
			// restoreBtn.addClickHandler(new ClickHandler() {
			// @Override
			// public void onClick(ClickEvent event) {
			// ListGridRecord listGridRecord = listGrid
			// .getSelectedRecord();
			// if (listGridRecord == null) {
			// SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
			// return;
			// }
			// Integer deleted = listGridRecord
			// .getAttributeAsInt("deleted");
			// if (deleted.equals(0)) {
			// SC.say("ჩანაწერი უკვე აღდგენილია !");
			// return;
			// }
			// .getAttributeAsInt("route_street_id");
			// if (route_street_id == null) {
			// SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
			// return;
			// }
			//
			// SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის აღდგენა ?",
			// new BooleanCallback() {
			// @Override
			// public void execute(Boolean value) {
			// if (value) {
			// changeStatus(route_street_id, 0);
			// }
			// }
			// });
			// }
			// });

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
			DataSource PubTranspDirDS = DataSource.get("PubTranspDirDS");
			routeItem
					.setOptionOperationId("searchAllPublicTransportDirectionsForCombos");
			routeItem.setOptionDataSource(PubTranspDirDS);
			routeItem.setValueField("pt_id");
			routeItem.setDisplayField("dir_num");

			Criteria criteria = new Criteria();
			routeItem.setOptionCriteria(criteria);
			routeItem.setAutoFetchData(false);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String id = routeItem.getValueAsString();
			String streets_id = streetItem.getValueAsString();
			String route_dir = routeDirItem.getValueAsString();
			String remarks = remarksItem.getValueAsString();

			Criteria criteria = new Criteria();
			if (id != null) {
				criteria.setAttribute("pt_id", new Integer(id));
			}
			if (streets_id != null) {
				criteria.setAttribute("street_id", new Integer(streets_id));
			}
			if (route_dir != null) {
				criteria.setAttribute("dir", new Integer(route_dir));
			}
			if (remarks != null && !remarks.trim().equals("")) {
				criteria.setAttribute("remarks", remarks);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"searchAllPublicTransportDirectionstreets");
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

	private void delete(Integer pts_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("pts_id", pts_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId",
					"updatePublicTransportDirectionsStreetStatus");
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
