package com.info08.billing.callcenterbk.client.content.transport;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.info08.billing.callcenterbk.client.dialogs.transport.DlgAddEditTranspSchedule;
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

public class TabTranspSchedule extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem transpTypeItem;
	private ComboBoxItem depTranspStationItem;
	private ComboBoxItem arrTranspStationItem;
	private ComboBoxItem transpResourceItem;
	private ComboBoxItem transpCompanyItem;
	private TextItem remarkItem;
	private TextItem transpModelDescrItem;

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

	private Integer transp_type_id;

	public TabTranspSchedule(final Integer transp_type_id) {
		try {
			this.transp_type_id = transp_type_id;
			setCanClose(true);
			datasource = DataSource.get("TranspScheduleDS");
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

			transpTypeItem = new TextItem();
			transpTypeItem.setTitle("ტრანსპორტის ტიპი");
			transpTypeItem.setWidth(300);
			transpTypeItem.setName("name_descr");
			transpTypeItem.setCanEdit(false);
			switch (transp_type_id) {
			case 1000005:
				setTitle("განრიგის(ავიაცია) მართვა");
				transpTypeItem.setValue("ავიაცია");
				break;
			case 1000003:
				setTitle("განრიგის(რკინიგზა) მართვა");
				transpTypeItem.setValue("რკინიგზა");
				break;
			case 1000004:
				setTitle("განრიგის(ავტობუსი) მართვა");
				transpTypeItem.setValue("ავტობუსი");
				break;
			default:
				break;
			}

			Criteria criteria = new Criteria();

			depTranspStationItem = new ComboBoxItem();
			depTranspStationItem.setTitle("გასვლის პუნქტი");
			depTranspStationItem.setWidth(300);
			depTranspStationItem.setName("depTranspStationItem");
			depTranspStationItem.setFetchMissingValues(true);
			depTranspStationItem.setFilterLocally(false);
			depTranspStationItem.setAddUnknownValues(false);

			DataSource transpStatDS = DataSource.get("TranspStatDS");

			switch (transp_type_id) {
			case 1000005:
				depTranspStationItem
						.setOptionOperationId("searchAllTransportPlacesForCBAv");
				break;
			case 1000003:
				depTranspStationItem
						.setOptionOperationId("searchAllTransportPlacesForCBRk");
				break;
			case 1000004:
				depTranspStationItem
						.setOptionOperationId("searchAllTransportPlacesForCBAvt");
				break;
			default:
				break;
			}

			depTranspStationItem.setOptionDataSource(transpStatDS);
			depTranspStationItem.setValueField("transp_stat_id");
			depTranspStationItem.setDisplayField("name_descr");

			depTranspStationItem.setOptionCriteria(criteria);
			depTranspStationItem.setAutoFetchData(false);

			depTranspStationItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = depTranspStationItem
							.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("transp_stat_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("transp_stat_id", nullO);
						}
					}
				}
			});

			arrTranspStationItem = new ComboBoxItem();
			arrTranspStationItem.setTitle("ჩასვლის პუნქტი");
			arrTranspStationItem.setWidth(300);
			arrTranspStationItem.setName("arrTranspStationItem");
			arrTranspStationItem.setFetchMissingValues(true);
			arrTranspStationItem.setFilterLocally(false);
			arrTranspStationItem.setAddUnknownValues(false);

			DataSource TranspStatDS1 = DataSource.get("TranspStatDS");

			switch (transp_type_id) {
			case 1000005:
				arrTranspStationItem
						.setOptionOperationId("searchAllTransportPlacesForCBAv");
				break;
			case 1000003:
				arrTranspStationItem
						.setOptionOperationId("searchAllTransportPlacesForCBRk");
				break;
			case 1000004:
				arrTranspStationItem
						.setOptionOperationId("searchAllTransportPlacesForCBAvt");
				break;
			default:
				break;
			}

			arrTranspStationItem.setOptionDataSource(TranspStatDS1);
			arrTranspStationItem.setValueField("transp_stat_id");
			arrTranspStationItem.setDisplayField("name_descr");

			arrTranspStationItem.setOptionCriteria(criteria);
			arrTranspStationItem.setAutoFetchData(false);

			arrTranspStationItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = arrTranspStationItem
							.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("transp_stat_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("transp_stat_id", nullO);
						}
					}
				}
			});

			transpResourceItem = new ComboBoxItem();
			transpResourceItem.setTitle("სატრანსპორტო საშუალება");
			transpResourceItem.setWidth(300);
			transpResourceItem.setName("transpResourceItem");
			transpResourceItem.setFetchMissingValues(true);
			transpResourceItem.setFilterLocally(false);
			transpResourceItem.setAddUnknownValues(false);

			DataSource transpResDS = DataSource.get("TranspResDS");
			switch (transp_type_id) {
			case 1000005:
				transpResourceItem
						.setOptionOperationId("searchAllTranspPlanesForCombosAv");
				break;
			case 1000003:
				transpResourceItem
						.setOptionOperationId("searchAllTranspPlanesForCombosRail");
				break;
			case 1000004:
				transpResourceItem
						.setOptionOperationId("searchAllTranspPlanesForCombosAvt");
				break;
			default:
				break;
			}

			transpResourceItem.setOptionDataSource(transpResDS);
			transpResourceItem.setValueField("transp_res_id");
			transpResourceItem.setDisplayField("name_descr");

			transpResourceItem.setOptionCriteria(criteria);
			transpResourceItem.setAutoFetchData(false);

			transpResourceItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = transpResourceItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("transp_res_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("transp_res_id", nullO);
						}
					}
				}
			});

			transpCompanyItem = new ComboBoxItem();
			transpCompanyItem.setTitle("სატრანსპორტო კომპანია");
			transpCompanyItem.setWidth(300);
			transpCompanyItem.setName("transpCompanyItem");
			transpCompanyItem.setFetchMissingValues(true);
			transpCompanyItem.setFilterLocally(false);
			transpCompanyItem.setAddUnknownValues(false);

			DataSource transpCompDS = DataSource.get("TranspCompDS");
			switch (transp_type_id) {
			case 1000005:
				transpCompanyItem
						.setOptionOperationId("searchAllTransportCompsForCBAv");
				break;
			case 1000003:
				transpCompanyItem
						.setOptionOperationId("searchAllTransportCompsForCBRail");
				break;
			case 1000004:
				transpCompanyItem
						.setOptionOperationId("searchAllTransportCompsForCBAvt");
				break;
			default:
				break;
			}

			transpCompanyItem.setOptionDataSource(transpCompDS);
			transpCompanyItem.setValueField("transp_comp_id");
			transpCompanyItem.setDisplayField("name_descr");

			transpCompanyItem.setOptionCriteria(criteria);
			transpCompanyItem.setAutoFetchData(false);

			transpCompanyItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = transpCompanyItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("transp_comp_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("transp_comp_id", nullO);
						}
					}
				}
			});

			remarkItem = new TextItem();
			remarkItem.setTitle("შენიშვნა");
			remarkItem.setWidth(300);
			remarkItem.setName("remarkItem");

			transpModelDescrItem = new TextItem();
			transpModelDescrItem.setTitle("რეისი");
			transpModelDescrItem.setWidth(300);
			transpModelDescrItem.setName("transpModelDescrItem");

			searchForm
					.setFields(transpTypeItem, remarkItem,
							depTranspStationItem, arrTranspStationItem,
							transpResourceItem, transpCompanyItem,
							transpModelDescrItem);

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
			toolStrip.setWidth(1140);
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

			listGrid = new ListGrid();

			listGrid.setWidth(1140);
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

			datasource.getField("transport_type").setTitle("ტრანსპორტის ტიპი");
			datasource.getField("depart_station").setTitle("გასვლის პუნქტი");
			datasource.getField("arrival_station").setTitle("ჩასვლის პუნქტი");
			datasource.getField("price_descr").setTitle("ფასი");
			datasource.getField("transp_company").setTitle(
					"სატრანსპორტო კომპანია");
			datasource.getField("transp_resource").setTitle(
					"სატრანსპორტო საშუალება");
			datasource.getField("days_descr").setTitle("კვირის დღეები");
			datasource.getField("transp_model_descr").setTitle("რეისი");
			datasource.getField("remark").setTitle("კომენტარი");

			ListGridField transport_type = new ListGridField("transport_type",
					"ტრანსპ. ტიპი", 150);
			ListGridField depart_station = new ListGridField("depart_station",
					"გასვლის პუნქტი", 200);
			ListGridField arrival_station = new ListGridField(
					"arrival_station", "ჩასვლის პუნქტი", 200);
			ListGridField transp_company = new ListGridField("transp_company",
					"სატრანსპ. კომპანია", 200);
			ListGridField transp_resource = new ListGridField(
					"transp_resource", "ტრანსპორტი", 120);
			ListGridField transp_model_descr = new ListGridField(
					"transp_model_descr", "რეისი", 70);
			ListGridField formated_depart_time = new ListGridField(
					"formated_depart_time", "გასვ. დრო", 70);
			ListGridField formated_arrival_time = new ListGridField(
					"formated_arrival_time", "ჩასვ. დრო", 70);

			formated_depart_time.setCellFormatter(new CellFormatter() {
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
			formated_arrival_time.setCellFormatter(new CellFormatter() {
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

			listGrid.setFields(transport_type, depart_station, arrival_station,
					transp_company, transp_resource, transp_model_descr,
					formated_depart_time, formated_arrival_time);

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
					remarkItem.clearValue();
					depTranspStationItem.clearValue();
					arrTranspStationItem.clearValue();
					transpResourceItem.clearValue();
					transpCompanyItem.clearValue();
					transpModelDescrItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditTranspSchedule dlgAddEditTransport = new DlgAddEditTranspSchedule(
							listGrid, null, transp_type_id);
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
					DlgAddEditTranspSchedule dlgAddEditTransport = new DlgAddEditTranspSchedule(
							listGrid, listGridRecord, transp_type_id);
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
					final Integer transp_schedule_id = listGridRecord
							.getAttributeAsInt("transp_schedule_id");
					if (transp_schedule_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										remove(transp_schedule_id);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(1140);
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
					DlgAddEditTranspSchedule dlgAddEditTransport = new DlgAddEditTranspSchedule(
							listGrid, listGridRecord, transp_type_id);
					dlgAddEditTransport.show();
				}
			});

			transpTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			remarkItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			transpModelDescrItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
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
			String depart_transp_stat_id = depTranspStationItem
					.getValueAsString();
			String arrival_transp_stat_id = arrTranspStationItem
					.getValueAsString();
			String transp_comp_id = transpCompanyItem.getValueAsString();
			String remark = remarkItem.getValueAsString();
			String transp_model_descr = transpModelDescrItem.getValueAsString();
			String transp_res_id = transpResourceItem.getValueAsString();

			Criteria criteria = new Criteria();
			if (transp_type_id != null) {
				criteria.setAttribute("transp_type_id", new Integer(
						transp_type_id));
			}
			if (depart_transp_stat_id != null) {
				criteria.setAttribute("depart_transp_stat_id", new Integer(
						depart_transp_stat_id));
			}
			if (arrival_transp_stat_id != null) {
				criteria.setAttribute("arrival_transp_stat_id", new Integer(
						arrival_transp_stat_id));
			}
			if (transp_comp_id != null) {
				criteria.setAttribute("transp_comp_id", new Integer(
						transp_comp_id));
			}
			if (transp_res_id != null) {
				criteria.setAttribute("transp_res_id", new Integer(
						transp_res_id));
			}

			if (remark != null && !remark.trim().equals("")) {
				criteria.setAttribute("remark", remark);
			}
			if (transp_model_descr != null
					&& !transp_model_descr.trim().equals("")) {
				criteria.setAttribute("transp_model_descr", transp_model_descr);
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

	private void remove(Integer transp_schedule_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("transp_schedule_id", transp_schedule_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeTranspSchedule");
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
