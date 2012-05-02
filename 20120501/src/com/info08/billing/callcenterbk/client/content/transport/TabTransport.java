package com.info08.billing.callcenterbk.client.content.transport;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.info08.billing.callcenterbk.client.dialogs.transport.DlgAddEditTransport;
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
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
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

public class TabTransport extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem transportTypeItem;
	private ComboBoxItem transportPlaceOutItem;
	private ComboBoxItem transportPlaceInItem;
	private ComboBoxItem transportPlaneItem;
	private ComboBoxItem transportCompanyItem;
	private TextItem noteGeoItem;
	private TextItem tripCriteriaItem;
	private ComboBoxItem deletedItem;

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
	
	private Integer transport_type_id;

	public TabTransport(final Integer transport_type_id) {
		try {
			this.transport_type_id = transport_type_id;
			setCanClose(true);
			datasource = DataSource.get("TransportDS");
			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(1000);
			searchForm.setTitleWidth(200);
			searchForm.setNumCols(4);
			mainLayout.addMember(searchForm);

			transportTypeItem = new TextItem();
			transportTypeItem.setTitle("ტრანსპორტის ტიპი");
			transportTypeItem.setWidth(300);
			transportTypeItem.setName("transport_type_name_geo");
			transportTypeItem.setCanEdit(false);
			switch (transport_type_id) {
			case 1000005:
				setTitle("განრიგის(ავიაცია) მართვა");
				transportTypeItem.setValue("ავიაცია");
				break;
			case 1000003:
				setTitle("განრიგის(რკინიგზა) მართვა");
				transportTypeItem.setValue("რკინიგზა");
				break;
			case 1000004:
				setTitle("განრიგის(ავტობუსი) მართვა");
				transportTypeItem.setValue("ავტობუსი");
				break;
			default:
				break;
			}

			Criteria criteria = new Criteria();

			transportPlaceOutItem = new ComboBoxItem();
			transportPlaceOutItem.setTitle("გასვლის პუნქტი");
			transportPlaceOutItem.setWidth(300);
			transportPlaceOutItem.setName("transport_place_geo_out");
			transportPlaceOutItem.setFetchMissingValues(true);
			transportPlaceOutItem.setFilterLocally(false);
			transportPlaceOutItem.setAddUnknownValues(false);

			DataSource transpPlaceDS = DataSource.get("TranspPlaceDS");

			switch (transport_type_id) {
			case 1000005:
				transportPlaceOutItem
						.setOptionOperationId("searchAllTransportPlacesForCBAv");
				break;
			case 1000003:
				transportPlaceOutItem
						.setOptionOperationId("searchAllTransportPlacesForCBRk");
				break;
			case 1000004:
				transportPlaceOutItem
						.setOptionOperationId("searchAllTransportPlacesForCBAvt");
				break;
			default:
				break;
			}

			transportPlaceOutItem.setOptionDataSource(transpPlaceDS);
			transportPlaceOutItem.setValueField("transport_place_id");
			transportPlaceOutItem.setDisplayField("transport_place_geo_descr");

			transportPlaceOutItem.setOptionCriteria(criteria);
			transportPlaceOutItem.setAutoFetchData(false);

			transportPlaceOutItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = transportPlaceOutItem
							.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("transport_place_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("transport_place_id", nullO);
						}
					}
				}
			});

			transportPlaceInItem = new ComboBoxItem();
			transportPlaceInItem.setTitle("ჩასვლის პუნქტი");
			transportPlaceInItem.setWidth(300);
			transportPlaceInItem.setName("transport_place_geo_in");
			transportPlaceInItem.setFetchMissingValues(true);
			transportPlaceInItem.setFilterLocally(false);
			transportPlaceInItem.setAddUnknownValues(false);

			DataSource transpPlaceDS1 = DataSource.get("TranspPlaceDS");

			switch (transport_type_id) {
			case 1000005:
				transportPlaceInItem
						.setOptionOperationId("searchAllTransportPlacesForCBAv");
				break;
			case 1000003:
				transportPlaceInItem
						.setOptionOperationId("searchAllTransportPlacesForCBRk");
				break;
			case 1000004:
				transportPlaceInItem
						.setOptionOperationId("searchAllTransportPlacesForCBAvt");
				break;
			default:
				break;
			}

			transportPlaceInItem.setOptionDataSource(transpPlaceDS1);
			transportPlaceInItem.setValueField("transport_place_id");
			transportPlaceInItem.setDisplayField("transport_place_geo_descr");

			transportPlaceInItem.setOptionCriteria(criteria);
			transportPlaceInItem.setAutoFetchData(false);

			transportPlaceInItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = transportPlaceInItem
							.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("transport_place_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("transport_place_id", nullO);
						}
					}
				}
			});

			transportPlaneItem = new ComboBoxItem();
			transportPlaneItem.setTitle("სატრანსპორტო საშუალება");
			transportPlaneItem.setWidth(300);
			transportPlaneItem.setName("transport_plane_name_geo");
			transportPlaneItem.setFetchMissingValues(true);
			transportPlaneItem.setFilterLocally(false);
			transportPlaneItem.setAddUnknownValues(false);

			DataSource transpPlaneDS = DataSource.get("TranspPlaneDS");
			switch (transport_type_id) {
			case 1000005:
				transportPlaneItem
						.setOptionOperationId("searchAllTranspPlanesForCombosAv");
				break;
			case 1000003:
				transportPlaneItem
						.setOptionOperationId("searchAllTranspPlanesForCombosRail");
				break;
			case 1000004:
				transportPlaneItem
						.setOptionOperationId("searchAllTranspPlanesForCombosAvt");
				break;
			default:
				break;
			}

			transportPlaneItem.setOptionDataSource(transpPlaneDS);
			transportPlaneItem.setValueField("transport_plane_id");
			transportPlaneItem.setDisplayField("transport_plane_geo_descr");

			transportPlaneItem.setOptionCriteria(criteria);
			transportPlaneItem.setAutoFetchData(false);

			transportPlaneItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = transportPlaneItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("transport_plane_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("transport_plane_id", nullO);
						}
					}
				}
			});

			transportCompanyItem = new ComboBoxItem();
			transportCompanyItem.setTitle("სატრანსპორტო კომპანია");
			transportCompanyItem.setWidth(300);
			transportCompanyItem.setName("transport_company_geo");
			transportCompanyItem.setFetchMissingValues(true);
			transportCompanyItem.setFilterLocally(false);
			transportCompanyItem.setAddUnknownValues(false);

			DataSource transpCompDS = DataSource.get("TranspCompDS");
			switch (transport_type_id) {
			case 1000005:
				transportCompanyItem
						.setOptionOperationId("searchAllTransportCompsForCBAv");
				break;
			case 1000003:
				transportCompanyItem
						.setOptionOperationId("searchAllTransportCompsForCBRail");
				break;
			case 1000004:
				transportCompanyItem
						.setOptionOperationId("searchAllTransportCompsForCBAvt");
				break;
			default:
				break;
			}

			transportCompanyItem.setOptionDataSource(transpCompDS);
			transportCompanyItem.setValueField("transport_company_id");
			transportCompanyItem.setDisplayField("transport_company_geo");

			transportCompanyItem.setOptionCriteria(criteria);
			transportCompanyItem.setAutoFetchData(false);

			transportCompanyItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = transportCompanyItem
							.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("transport_company_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("transport_company_id", nullO);
						}
					}
				}
			});

			noteGeoItem = new TextItem();
			noteGeoItem.setTitle("შენიშვნა");
			noteGeoItem.setWidth(300);
			noteGeoItem.setName("note_geo");

			tripCriteriaItem = new TextItem();
			tripCriteriaItem.setTitle("რეისი");
			tripCriteriaItem.setWidth(300);
			tripCriteriaItem.setName("trip_criteria");

			deletedItem = new ComboBoxItem();
			deletedItem.setTitle("სტატუსი");
			deletedItem.setWidth(300);
			deletedItem.setName("deleted_transport");
			deletedItem.setValueMap(ClientMapUtil.getInstance().getStatuses());

			searchForm.setFields(transportTypeItem, noteGeoItem,
					transportPlaceOutItem, transportPlaceInItem,
					transportPlaneItem, transportCompanyItem, tripCriteriaItem,
					deletedItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(998);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(1000);
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

			listGrid.setWidth(1000);
			listGrid.setHeight(300);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllTransports");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			final DateTimeFormat dateFormatter = DateTimeFormat
					.getFormat("HH:mm");

			datasource.getField("transport_type_name_geo").setTitle(
					"ტრანსპორტის ტიპი");
			datasource.getField("transport_place_geo_out").setTitle(
					"გასვლის პუნქტი");
			datasource.getField("transport_place_geo_in").setTitle(
					"ჩასვლის პუნქტი");
			datasource.getField("transport_price_geo").setTitle("ფასი");
			datasource.getField("transport_company_geo").setTitle(
					"სატრანსპორტო კომპანია");
			datasource.getField("transport_plane_geo").setTitle(
					"სატრანსპორტო საშუალება");
			datasource.getField("days_descr").setTitle("კვირის დღეები");
			datasource.getField("trip_criteria").setTitle("რეისი");
			datasource.getField("note_geo").setTitle("კომენტარი");
			datasource.getField("rec_date").setTitle("შექმინის თარიღი");
			datasource.getField("rec_user").setTitle("შემქმნელი");
			datasource.getField("upd_date").setTitle("განახლების თარიღი");
			datasource.getField("upd_user").setTitle("ვინ განაახლა");

			ListGridField transport_type_name_geo = new ListGridField(
					"transport_type_name_geo", "ტრანსპ. ტიპი", 80);
			ListGridField transport_place_geo_out = new ListGridField(
					"transport_place_geo_out", "გასვლის პუნქტი", 180);
			ListGridField transport_place_geo_in = new ListGridField(
					"transport_place_geo_in", "ჩასვლის პუნქტი", 180);
			ListGridField transport_company_geo = new ListGridField(
					"transport_company_geo", "სატრანსპ. კომპანია", 170);
			ListGridField transport_plane_geo = new ListGridField(
					"transport_plane_geo", "ტრანსპორტი", 120);
			ListGridField trip_criteria = new ListGridField("trip_criteria",
					"რეისი", 70);
			ListGridField outTime = new ListGridField("out_time", "გასვ. დრო",
					70);
			ListGridField in_time = new ListGridField("in_time", "ჩასვ. დრო",
					70);

			outTime.setCellFormatter(new CellFormatter() {
				@Override
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value != null) {
						try {
							Date dateValue = (Date) value;
							return dateFormatter.format(dateValue);
						} catch (Exception e) {
							return value.toString();
						}
					} else {
						return "";
					}
				}
			});
			in_time.setCellFormatter(new CellFormatter() {
				@Override
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value != null) {
						try {
							Date dateValue = (Date) value;
							return dateFormatter.format(dateValue);
						} catch (Exception e) {
							return value.toString();
						}
					} else {
						return "";
					}
				}
			});

			listGrid.setFields(transport_type_name_geo,
					transport_place_geo_out, transport_place_geo_in,
					transport_company_geo, transport_plane_geo, trip_criteria,
					outTime, in_time);

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
					transportTypeItem.clearValue();
					noteGeoItem.clearValue();
					transportPlaceOutItem.clearValue();
					transportPlaceInItem.clearValue();
					transportPlaneItem.clearValue();
					transportCompanyItem.clearValue();
					tripCriteriaItem.clearValue();
					deletedItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditTransport dlgAddEditTransport = new DlgAddEditTransport(
							listGrid, null, transport_type_id);
					dlgAddEditTransport.show();
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
					DlgAddEditTransport dlgAddEditTransport = new DlgAddEditTransport(
							listGrid, listGridRecord, transport_type_id);
					dlgAddEditTransport.show();
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
					final Integer transport_id = listGridRecord
							.getAttributeAsInt("transport_id");
					if (transport_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(transport_id, 1);
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
					final Integer transport_id = listGridRecord
							.getAttributeAsInt("transport_id");
					if (transport_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის აღდგენა ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(transport_id, 0);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(1000);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasource);
			detailViewer.setWidth(970);
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
					DlgAddEditTransport dlgAddEditTransport = new DlgAddEditTransport(
							listGrid, listGridRecord, transport_type_id);
					dlgAddEditTransport.show();
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

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String out_transport_place_id = transportPlaceOutItem
					.getValueAsString();
			String in_transport_place_id = transportPlaceInItem
					.getValueAsString();
			String transport_company_id = transportCompanyItem
					.getValueAsString();
			String note_geo = noteGeoItem.getValueAsString();
			String trip_criteria = tripCriteriaItem.getValueAsString();
			String deleted = deletedItem.getValueAsString();

			Criteria criteria = new Criteria();
			if (transport_type_id != null) {
				criteria.setAttribute("transport_type_id", new Integer(
						transport_type_id));
			}
			if (out_transport_place_id != null) {
				criteria.setAttribute("out_transport_place_id", new Integer(
						out_transport_place_id));
			}
			if (in_transport_place_id != null) {
				criteria.setAttribute("in_transport_place_id", new Integer(
						in_transport_place_id));
			}
			if (transport_company_id != null) {
				criteria.setAttribute("transport_company_id", new Integer(
						transport_company_id));
			}
			if (deleted != null) {
				criteria.setAttribute("deleted", new Integer(deleted));
			}

			if (note_geo != null && !note_geo.trim().equals("")) {
				criteria.setAttribute("note_geo", note_geo);
			}
			if (trip_criteria != null && !trip_criteria.trim().equals("")) {
				criteria.setAttribute("trip_criteria", trip_criteria);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllTransports");
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

	private void changeStatus(Integer transport_id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("transport_id", transport_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateTransportStatus");
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
