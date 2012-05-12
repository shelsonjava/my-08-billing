package com.info08.billing.callcenterbk.client.content.transport;

import com.info08.billing.callcenterbk.client.dialogs.transport.DlgAddEditBusRoute;
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

public class TabTransportCityPublTransport extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem dirNMItem;
	private TextItem dirOldNUMItem;
	private ComboBoxItem cycleTypeItem;
	private ComboBoxItem transpTypeItem;

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

	public TabTransportCityPublTransport() {
		try {
			setTitle("ქალაქის მიკრო/ავტ. მართვა");
			setCanClose(true);

			datasource = DataSource.get("PubTranspDirDS");

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

			dirNMItem = new TextItem();
			dirNMItem.setTitle("მარშუტის ნომერი");
			dirNMItem.setWidth(350);
			dirNMItem.setName("dir_num");

			dirOldNUMItem = new TextItem();
			dirOldNUMItem.setTitle("მარშუტის ძველი ნომერი");
			dirOldNUMItem.setWidth(350);
			dirOldNUMItem.setName("dir_old_num");

			cycleTypeItem = new ComboBoxItem();
			cycleTypeItem.setTitle("წრიული/ჩვეულებრივი");
			cycleTypeItem.setWidth(350);
			cycleTypeItem.setName("cycled_id");
			cycleTypeItem.setValueMap(ClientMapUtil.getInstance()
					.getTranspRoundType());

			transpTypeItem = new ComboBoxItem();
			transpTypeItem.setTitle("სახეობა");
			transpTypeItem.setWidth(350);
			transpTypeItem.setName("service_id");
			transpTypeItem.setValueMap(ClientMapUtil.getInstance()
					.getTranspTypeCustom());

			searchForm.setFields(dirNMItem, dirOldNUMItem, cycleTypeItem,
					transpTypeItem);

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
			listGrid.setFetchOperation("searchAllPublicTransportDirections");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("dir_num").setTitle("მარშუტის ნომერი");
			datasource.getField("dir_old_num")
					.setTitle("მარშუტის ძველი ნომერი");
			datasource.getField("cycle_descr").setTitle("წრიული/ჩვეულებრივი");
			datasource.getField("service_descr").setTitle("სახეობა");

			ListGridField dir_num = new ListGridField("dir_num", "მარშ. N", 80);
			ListGridField dir_old_num = new ListGridField("dir_old_num",
					"ძვ.მარშ. N", 80);
			ListGridField cycle_descr = new ListGridField("cycle_descr",
					"წრიული/ჩვ.", 120);
			ListGridField service_descr = new ListGridField("service_descr",
					"სახეობა", 150);

			cycle_descr.setAlign(Alignment.CENTER);

			listGrid.setFields(dir_num, dir_old_num, cycle_descr, service_descr);

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
					dirNMItem.clearValue();
					dirOldNUMItem.clearValue();
					cycleTypeItem.clearValue();
					transpTypeItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditBusRoute dlgAddEditBusRoute = new DlgAddEditBusRoute(
							listGrid, null);
					dlgAddEditBusRoute.show();
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
					DlgAddEditBusRoute dlgAddEditBusRoute = new DlgAddEditBusRoute(
							listGrid, listGridRecord);
					dlgAddEditBusRoute.show();
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
					// Integer deleted = listGridRecord
					// .getAttributeAsInt("deleted");
					// if (!deleted.equals(0)) {
					// SC.say("ჩანაწერი უკვე გაუქმებულია !");
					// return;
					// }
					final Integer id = listGridRecord
							.getAttributeAsInt("pt_id");
					if (id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(id, 1);
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
			// final Integer id = listGridRecord
			// .getAttributeAsInt("pt_id");
			// if (id == null) {
			// SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
			// return;
			// }
			//
			// SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის აღდგენა ?",
			// new BooleanCallback() {
			// @Override
			// public void execute(Boolean value) {
			// if (value) {
			// changeStatus(id, 0);
			// }
			// }
			// });
			// }
			// });

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
					DlgAddEditBusRoute dlgAddEditBusRoute = new DlgAddEditBusRoute(
							listGrid, listGridRecord);
					dlgAddEditBusRoute.show();
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
			String dir_num = dirNMItem.getValueAsString();
			String dir_old_num = dirOldNUMItem.getValueAsString();
			String cycled_id = cycleTypeItem.getValueAsString();
			String service_id = transpTypeItem.getValueAsString();

			Criteria criteria = new Criteria();
			criteria.setAttribute("dir_num", dir_num);
			criteria.setAttribute("dir_old_num", dir_old_num);
			if (cycled_id != null) {
				criteria.setAttribute("cycled_id", new Integer(cycled_id));
			}
			if (service_id != null) {
				criteria.setAttribute("service_id", new Integer(service_id));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"searchAllPublicTransportDirections");
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

	private void changeStatus(Integer id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("pt_id", id);
			String loggedUserName = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUserName);
			DSRequest req = new DSRequest();

			req.setAttribute("operationId",
					"updatePublicTransportDirectionsStatus");
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
