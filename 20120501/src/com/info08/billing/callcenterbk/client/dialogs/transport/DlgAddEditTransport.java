package com.info08.billing.callcenterbk.client.dialogs.transport;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgAddEditTransport extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem transportTypeItem;
	private ComboBoxItem transportPlaceOutItem;
	private ComboBoxItem transportPlaceInItem;
	private ComboBoxItem transportPlaneItem;
	private ComboBoxItem transportCompanyItem;
	private TextAreaItem noteGeoItem;
	private TextAreaItem transportPriceItem;
	private TextItem tripCriteriaItem;
	private CheckboxItem noteCritItem;
	private TimeItem outTimeItem;
	private TimeItem inTimeItem;
	private ListGrid daysGrid;
	private ListGrid transpDaysGrid;
	private ListGrid transpDetailsGrid;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ToolStripButton restoreBtn;

	private Integer transport_type_id;

	public DlgAddEditTransport(ListGrid listGrid, ListGridRecord pRecord,
			final Integer transport_type_id) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;
		this.transport_type_id = transport_type_id;

		setTitle(pRecord == null ? "განრიგის(გრაფიკის) დამატება"
				: "განრიგის(გრაფიკის) მოდიფიცირება");

		setHeight(740);
		setWidth(800);
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		hLayout = new VLayout(5);
		hLayout.setWidth100();
		hLayout.setHeight100();
		hLayout.setPadding(10);

		dynamicForm = new DynamicForm();
		dynamicForm.setAutoFocus(true);
		dynamicForm.setWidth100();
		dynamicForm.setTitleWidth(250);
		dynamicForm.setNumCols(4);
		hLayout.addMember(dynamicForm);

		transportTypeItem = new TextItem();
		transportTypeItem.setTitle("ტრანსპორტის ტიპი");
		transportTypeItem.setWidth(250);
		transportTypeItem.setName("name_descr");
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
		transportPlaceOutItem.setWidth(250);
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
				Criteria criteria = transportPlaceOutItem.getOptionCriteria();
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
		transportPlaceInItem.setWidth(250);
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
				Criteria criteria = transportPlaceInItem.getOptionCriteria();
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
		transportPlaneItem.setTitle("სატრასპ. საშუალება");
		transportPlaneItem.setWidth(250);
		transportPlaneItem.setName("transport_plane_name_geo");
		transportPlaneItem.setFetchMissingValues(true);
		transportPlaneItem.setFilterLocally(false);
		transportPlaneItem.setAddUnknownValues(false);

		DataSource transpResDS = DataSource.get("TranspResDS");
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
		transportPlaneItem.setOptionDataSource(transpResDS);
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
		transportCompanyItem.setTitle("სატრასპ. კომპანია");
		transportCompanyItem.setWidth(250);
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
				Criteria criteria = transportCompanyItem.getOptionCriteria();
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

		noteGeoItem = new TextAreaItem();
		noteGeoItem.setTitle("შენიშვნა");
		noteGeoItem.setWidth(250);
		noteGeoItem.setName("note_geo");
		noteGeoItem.setHeight(70);

		transportPriceItem = new TextAreaItem();
		transportPriceItem.setTitle("ფასი");
		transportPriceItem.setWidth(250);
		transportPriceItem.setName("transport_price_geo");
		transportPriceItem.setHeight(70);

		tripCriteriaItem = new TextItem();
		tripCriteriaItem.setTitle("რეისი");
		tripCriteriaItem.setWidth(250);
		tripCriteriaItem.setName("trip_criteria");

		noteCritItem = new CheckboxItem();
		noteCritItem.setTitle("მნიშვნელოვანი");
		noteCritItem.setWidth(250);
		noteCritItem.setName("note_crit");

		outTimeItem = new TimeItem("timeItem", "Time");
		outTimeItem.setTitle("გასვლის დრო");
		outTimeItem.setWidth(250);
		outTimeItem.setName("out_time");
		outTimeItem.setHint("");
		outTimeItem.setMask("00:00");
		outTimeItem.setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);
		outTimeItem.setUseMask(true);

		inTimeItem = new TimeItem("timeItem", "Time");
		inTimeItem.setTitle("ჩასვლის დრო");
		inTimeItem.setWidth(250);
		inTimeItem.setName("in_time");
		inTimeItem.setHint("");
		inTimeItem.setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);
		inTimeItem.setUseMask(true);
		inTimeItem.setMask("00:00");

		StaticTextItem staticTextItem = new StaticTextItem();
		staticTextItem.setTitle("");
		staticTextItem.setWidth(250);
		staticTextItem.setName("space_item");

		dynamicForm.setFields(transportTypeItem, transportPlaneItem,
				transportCompanyItem, tripCriteriaItem, transportPlaceOutItem,
				outTimeItem, transportPlaceInItem, inTimeItem, staticTextItem,
				noteCritItem, transportPriceItem, noteGeoItem);

		WeekDaysClientDS weekDaysClientDS = WeekDaysClientDS.getInstance();

		daysGrid = new ListGrid();
		daysGrid.setWidth(360);
		daysGrid.setHeight(205);
		daysGrid.setCanDragRecordsOut(true);
		daysGrid.setDragDataAction(DragDataAction.COPY);
		daysGrid.setAlternateRecordStyles(true);
		daysGrid.setDataSource(weekDaysClientDS);
		daysGrid.setAutoFetchData(true);

		TranspWeekDaysClientDS transpWeekDaysClientDS = TranspWeekDaysClientDS
				.getInstance();
		transpDaysGrid = new ListGrid();
		transpDaysGrid.setWidth(360);
		transpDaysGrid.setHeight(205);
		transpDaysGrid.setDataSource(transpWeekDaysClientDS);
		transpDaysGrid.setCanAcceptDroppedRecords(true);
		transpDaysGrid.setCanRemoveRecords(true);
		transpDaysGrid.setAutoFetchData(true);
		transpDaysGrid.setPreventDuplicates(true);
		transpDaysGrid.setDuplicateDragMessage("ასეთი დღე უკვე არჩეულია !");

		Img arrowImg = new Img("arrow_right.png", 32, 32);
		arrowImg.setLayoutAlign(Alignment.CENTER);
		arrowImg.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				transpDaysGrid.transferSelectedData(daysGrid);
			}
		});
		daysGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				transpDaysGrid.transferSelectedData(daysGrid);
			}
		});

		HLayout gridsLayout = new HLayout(0);
		gridsLayout.setHeight(210);

		HLayout hLayoutImg = new HLayout();
		hLayoutImg.setAlign(Alignment.CENTER);
		hLayoutImg.addMember(arrowImg);

		gridsLayout.setMembers(daysGrid, hLayoutImg, transpDaysGrid);

		hLayout.addMember(gridsLayout);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		hLayout.addMember(toolStrip);

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

		TranspDetailsClientDS transpDetailsClientDS = TranspDetailsClientDS
				.getInstance();
		transpDetailsGrid = new ListGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord countryRecord = (ListGridRecord) record;
				if (countryRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				Integer deleted = countryRecord.getAttributeAsInt("deleted");
				if (deleted != null && !deleted.equals(0)) {
					return "color:red;";
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};
		};

		final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("HH:mm");

		transpDetailsGrid.setWidth100();
		transpDetailsGrid.setHeight(200);
		transpDetailsGrid.setDataSource(transpDetailsClientDS);
		transpDetailsGrid.setAutoFetchData(true);

		ListGridField transport_place_geo_out = new ListGridField(
				"transport_place_geo_out", "გავლის პუნქტი", 330);
		ListGridField out_time = new ListGridField("out_time", "გასვლის დრო",
				130);
		ListGridField in_time = new ListGridField("in_time", "ჩასვლის დრო", 130);
		ListGridField transport_detail_order = new ListGridField(
				"transport_detail_order", "თანმიმდევრობა", 130);

		out_time.setAlign(Alignment.CENTER);
		in_time.setAlign(Alignment.CENTER);
		transport_detail_order.setAlign(Alignment.CENTER);

		out_time.setCellFormatter(new CellFormatter() {
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

		transpDetailsGrid.setFields(transport_place_geo_out, out_time, in_time,
				transport_detail_order);

		transpDetailsGrid
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						ListGridRecord listGridRecord = transpDetailsGrid
								.getSelectedRecord();
						if (listGridRecord == null) {
							SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
							return;
						}

						String transport_type_id_str = transportTypeItem
								.getValueAsString();
						if (transport_type_id_str == null
								|| transport_type_id_str.trim().equals("")) {
							SC.say("გთხოვთ აირჩიოთ ტრანსპორტის ტიპი !");
							return;
						}
						Integer transport_type_id = null;
						try {
							transport_type_id = new Integer(
									transport_type_id_str);
						} catch (Exception e) {
							SC.say("გთხოვთ აირჩიოთ ტრანსპორტის ტიპი 1 !");
							return;
						}

						DlgAddEditTransportDetail dlgAddEditTransportDetail = new DlgAddEditTransportDetail(
								transpDetailsGrid, listGridRecord,
								transport_type_id);
						dlgAddEditTransportDetail.show();
					}
				});

		hLayout.addMember(transpDetailsGrid);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton saveItem = new IButton();
		saveItem.setTitle("შენახვა");
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle("დახურვა");
		cancItem.setWidth(100);

		hLayoutItem.setMembers(saveItem, cancItem);

		hLayout.addMember(hLayoutItem);

		cancItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});
		saveItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				save();
			}
		});

		addBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				DlgAddEditTransportDetail dlgAddEditTransportDetail = new DlgAddEditTransportDetail(
						transpDetailsGrid, null, transport_type_id);
				dlgAddEditTransportDetail.show();
			}
		});

		editBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord listGridRecord = transpDetailsGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}
				DlgAddEditTransportDetail dlgAddEditTransportDetail = new DlgAddEditTransportDetail(
						transpDetailsGrid, listGridRecord, transport_type_id);
				dlgAddEditTransportDetail.show();
			}
		});
		deleteBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final ListGridRecord listGridRecord = transpDetailsGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}
				Integer deleted = listGridRecord.getAttributeAsInt("deleted");
				if (!deleted.equals(0)) {
					SC.say("ჩანაწერი უკვე გაუქმებულია !");
					return;
				}
				SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value) {
									changeStatus(listGridRecord, 1);
								}
							}
						});
			}
		});
		restoreBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final ListGridRecord listGridRecord = transpDetailsGrid
						.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
					return;
				}
				Integer deleted = listGridRecord.getAttributeAsInt("deleted");
				if (deleted.equals(0)) {
					SC.say("ჩანაწერი უკვე აღდგენილია !");
					return;
				}
				SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის აღდგენა ?",
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value) {
									changeStatus(listGridRecord, 0);
								}
							}
						});
			}
		});

		addItem(hLayout);
		fillFields();
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}

			Integer transport_plane_id = editRecord
					.getAttributeAsInt("transport_plane_id");
			if (transport_plane_id != null) {
				transportPlaneItem.setValue(transport_plane_id);
			}

			Integer transport_company_id = editRecord
					.getAttributeAsInt("transport_company_id");
			if (transport_company_id != null) {
				transportCompanyItem.setValue(transport_company_id);
			}

			String trip_criteria = editRecord
					.getAttributeAsString("trip_criteria");
			if (trip_criteria != null) {
				tripCriteriaItem.setValue(trip_criteria);
			}

			Integer out_transport_place_id = editRecord
					.getAttributeAsInt("out_transport_place_id");
			if (out_transport_place_id != null) {
				transportPlaceOutItem.setValue(out_transport_place_id);
			}

			Integer in_transport_place_id = editRecord
					.getAttributeAsInt("in_transport_place_id");
			if (in_transport_place_id != null) {
				transportPlaceInItem.setValue(in_transport_place_id);
			}

			final DateTimeFormat dateFormatter = DateTimeFormat
					.getFormat("HH:mm");

			Date out_time = editRecord.getAttributeAsDate("out_time");
			if (out_time != null) {
				String out_time_str = dateFormatter.format(out_time);
				outTimeItem.setValue(out_time_str);
			}

			Date in_time = editRecord.getAttributeAsDate("in_time");
			if (in_time != null) {
				String in_time_str = dateFormatter.format(in_time);
				inTimeItem.setValue(in_time_str);
			}

			String transport_price_geo = editRecord
					.getAttributeAsString("transport_price_geo");
			if (transport_price_geo != null) {
				transportPriceItem.setValue(transport_price_geo);
			}

			String note_geo = editRecord.getAttributeAsString("note_geo");
			if (note_geo != null) {
				noteGeoItem.setValue(note_geo);
			}

			Integer note_crit = editRecord.getAttributeAsInt("note_crit");
			if (note_crit != null && note_crit.equals(-1)) {
				noteCritItem.setValue(true);
			}

			final Integer dayID = editRecord.getAttributeAsInt("days");
			if (dayID != null) {
				WeekDaysClientDS weekDaysClientDS = WeekDaysClientDS
						.getInstance();
				weekDaysClientDS.fetchData(new Criteria(), new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record records[] = response.getData();
						if (records != null && records.length > 0) {

							if (dayID == 0) {
								transpDaysGrid.addData(records[0]);
							} else {
								int dayArray[] = Constants.dayArray;
								for (int i = 1; i < dayArray.length; i++) {
									if ((dayArray[i] & dayID) == dayArray[i]) {
										transpDaysGrid.addData(records[i]);
									}
								}
							}
						}
					}
				});
			}

			DataSource transportDetailsDS = DataSource
					.get("TransportDetailsDS");
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchTransportDetails");
			Criteria criteria = new Criteria();
			criteria.setAttribute("transport_id",
					editRecord.getAttributeAsInt("transport_id"));
			transportDetailsDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						for (Record record : records) {
							transpDetailsGrid.addData(record);
						}
					}
				}
			}, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void changeStatus(ListGridRecord listGridRecord, Integer deleted) {
		try {
			listGridRecord.setAttribute("deleted", deleted);
			transpDetailsGrid.updateData(listGridRecord);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void save() {
		try {
			String out_transport_place_id_str = transportPlaceOutItem
					.getValueAsString();
			if (out_transport_place_id_str == null
					|| out_transport_place_id_str.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ გასვლის პუნქტი !");
				return;
			}
			Integer out_transport_place_id = null;
			try {
				out_transport_place_id = new Integer(out_transport_place_id_str);
			} catch (NumberFormatException e) {
				SC.say("გთხოვთ აირჩიოთ გასვლის პუნქტი ხელმეორედ!");
				return;
			}

			String in_transport_place_id_str = transportPlaceInItem
					.getValueAsString();
			if (in_transport_place_id_str == null
					|| in_transport_place_id_str.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ ჩასვლის პუნქტი !");
				return;
			}
			Integer in_transport_place_id = null;
			try {
				in_transport_place_id = new Integer(in_transport_place_id_str);
			} catch (NumberFormatException e) {
				SC.say("გთხოვთ აირჩიოთ გასვლის პუნქტი ხელმეორედ!");
				return;
			}

			Object out_time_obj = outTimeItem.getValue();
			if (out_time_obj == null) {
				SC.say("გთხოვთ მიუთითოთ გასვლის დრო !");
				return;
			}
			Date out_time = null;
			try {
				out_time = (Date) out_time_obj;
			} catch (Exception e) {
				SC.say("გასვლის დრო არასწორედაა მითითებული!");
				return;
			}

			Object in_time_obj = inTimeItem.getValue();
			if (transport_type_id != 1000004 && in_time_obj == null) {
				SC.say("გთხოვთ მიუთითოთ ჩასვლის დრო !");
				return;
			}
			Date in_time = null;
			if (in_time_obj != null) {
				try {
					in_time = (Date) in_time_obj;
				} catch (Exception e) {
					SC.say("ჩასვლის დრო არასწორედაა მითითებული!");
					return;
				}
			}
			String transport_price_geo = transportPriceItem.getValueAsString();
			String note_geo = noteGeoItem.getValueAsString();

			Integer deleted = null;
			if (editRecord == null) {
				deleted = 0;
			} else {
				deleted = editRecord.getAttributeAsInt("deleted");
			}

			String transport_company_id_str = transportCompanyItem
					.getValueAsString();
			if (transport_company_id_str == null
					|| transport_company_id_str.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ სატრანსპორტო კომპანია !");
				return;
			}
			Integer transport_company_id = null;
			try {
				transport_company_id = new Integer(transport_company_id_str);
			} catch (NumberFormatException e) {
				SC.say("გთხოვთ აირჩიოთ სატრანსპორტო კომპანია ხელმეორედ!");
				return;
			}

			String transport_plane_id_str = transportPlaneItem
					.getValueAsString();
			if (transport_plane_id_str == null
					|| transport_plane_id_str.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ სატრანსპორტო საშუალება !");
				return;
			}
			Integer transport_plane_id = null;
			try {
				transport_plane_id = new Integer(transport_plane_id_str);
			} catch (NumberFormatException e) {
				SC.say("გთხოვთ აირჩიოთ სატრანსპორტო საშუალება ხელმეორედ!");
				return;
			}
			String trip_criteria = tripCriteriaItem.getValueAsString();
			boolean note_crit = noteCritItem.getValueAsBoolean();

			ListGridRecord trasnport_days[] = transpDaysGrid.getRecords();
			if (trasnport_days == null || trasnport_days.length <= 0) {
				SC.say("გთხოვთ მიუთითოთ დღე აუცილებლად !");
				return;
			}

			// check all transport type : t.transport_type_id
			Integer out_tranp = transportPlaceOutItem.getSelectedRecord()
					.getAttributeAsInt("transp_type_id");
			Integer in_tranp = transportPlaceInItem.getSelectedRecord()
					.getAttributeAsInt("transp_type_id");
			Integer transp_pln = transportPlaneItem.getSelectedRecord()
					.getAttributeAsInt("transp_type_id");

			if (!transport_type_id.equals(out_tranp)
					|| !transport_type_id.equals(in_tranp)
					|| !transport_type_id.equals(transp_pln)) {
				SC.say("ტრანსპორტის ტიპი, გასვლის პუნქტი, ჩასვლის პუნქტი და სატრანსპორტო საშუალება უნდა იყოს ერთიდაიგივე სახეობის !");
				return;
			}

			Integer days = 0;
			boolean allWeekDaysIsSel = false;
			for (ListGridRecord listGridRecord : trasnport_days) {
				Integer day_id = listGridRecord.getAttributeAsInt("day_id");
				if (day_id.intValue() == 0) {
					allWeekDaysIsSel = true;
				}
				days += day_id;
			}
			if (allWeekDaysIsSel && trasnport_days.length > 1) {
				SC.say("თქვენ არჩეული გაქვთ კვირის \"ყველა\" დღე. გთხოვთ წაშალოთ სხვა დანარჩენი !");
				return;
			}
			if (days == 127) {
				SC.say("თქვენ არჩეული გაქვთ კვირის ყოველი დღე. გთხოვთ მიუთითოთ ვარიანტი : \"ყველა\" !");
				return;
			}

			Map records = new LinkedHashMap();
			RecordList trasnport_details = transpDetailsGrid
					.getDataAsRecordList();

			if (trasnport_details != null && trasnport_details.getLength() > 0) {
				int length = trasnport_details.getLength();
				for (int i = 0; i < length; i++) {
					Record tranpDetailRecord = trasnport_details.get(i);
					Map transportDetailsMap = new LinkedHashMap();

					Integer transport_detail_id = tranpDetailRecord
							.getAttributeAsInt("transport_detail_id");
					Integer transport_place_id = tranpDetailRecord
							.getAttributeAsInt("transport_place_id");
					Integer deleted_tr_det = tranpDetailRecord
							.getAttributeAsInt("deleted");
					Integer transport_detail_order = tranpDetailRecord
							.getAttributeAsInt("transport_detail_order");
					Date in_time_tr_det = tranpDetailRecord
							.getAttributeAsDate("in_time");
					Date out_time_tr_det = tranpDetailRecord
							.getAttributeAsDate("out_time");

					transportDetailsMap.put("transport_detail_id",
							transport_detail_id);
					transportDetailsMap.put("transport_place_id",
							transport_place_id);
					transportDetailsMap.put("deleted", deleted_tr_det);
					transportDetailsMap.put(
							"c_in_time",
							((in_time_tr_det == null) ? null : (in_time_tr_det
									.getTime() + "")));
					transportDetailsMap.put("c_out_time",
							((out_time_tr_det == null) ? null
									: (out_time_tr_det.getTime() + "")));
					transportDetailsMap.put("transport_detail_order",
							transport_detail_order);

					records.put(transport_detail_id.toString(),
							transportDetailsMap);
				}
			}
			
			//SC.say("MAP : "+records.toString());
			
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("rec_user", loggedUser);
			if (editRecord != null) {
				record.setAttribute("transport_id",
						editRecord.getAttributeAsInt("transport_id"));
			}
			record.setAttribute("transp_type_id", transport_type_id);
			record.setAttribute("out_transport_place_id",
					out_transport_place_id);
			record.setAttribute("in_transport_place_id", in_transport_place_id);
			record.setAttribute("out_time", out_time);
			record.setAttribute("in_time", in_time);
			record.setAttribute("note_geo", note_geo);
			record.setAttribute("deleted", deleted);
			record.setAttribute("transport_price_geo", transport_price_geo);
			record.setAttribute("transport_company_id", transport_company_id);
			record.setAttribute("transport_plane_id", transport_plane_id);
			record.setAttribute("trip_criteria", trip_criteria);
			if (note_crit) {
				record.setAttribute("note_crit", -1);
			} else {
				record.setAttribute("note_crit", 0);
			}
			record.setAttribute("days", days);
			record.setAttribute("listTranspDetails", records);
			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addTransport");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateTransport");
				listGrid.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			}
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
