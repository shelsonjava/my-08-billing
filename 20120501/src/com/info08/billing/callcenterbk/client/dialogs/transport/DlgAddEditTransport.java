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

	private TextItem transpTypeItem;
	private ComboBoxItem depTranspStationItem;
	private ComboBoxItem arrivalTranspStationItem;
	private ComboBoxItem transpResourceItem;
	private ComboBoxItem transpCompItem;
	private TextAreaItem remarkItem;
	private TextAreaItem transpPriceDescrItem;
	private TextItem transpModelDescrItem;
	private CheckboxItem importantItem;
	private TimeItem departTimeItem;
	private TimeItem arrivalTimeItem;
	private ListGrid daysGrid;
	private ListGrid transpDaysGrid;
	private ListGrid transpItemsGrid;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	private Integer transp_type_id;

	public DlgAddEditTransport(ListGrid listGrid, ListGridRecord pRecord,
			final Integer transp_type_id) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			this.transp_type_id = transp_type_id;

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

			transpTypeItem = new TextItem();
			transpTypeItem.setTitle("ტრანსპორტის ტიპი");
			transpTypeItem.setWidth(250);
			transpTypeItem.setName("transpTypeItem");
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
			depTranspStationItem.setWidth(250);
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

			arrivalTranspStationItem = new ComboBoxItem();
			arrivalTranspStationItem.setTitle("ჩასვლის პუნქტი");
			arrivalTranspStationItem.setWidth(250);
			arrivalTranspStationItem.setName("arrivalTranspStationItem");
			arrivalTranspStationItem.setFetchMissingValues(true);
			arrivalTranspStationItem.setFilterLocally(false);
			arrivalTranspStationItem.setAddUnknownValues(false);

			DataSource transpStatDS1 = DataSource.get("TranspStatDS");
			switch (transp_type_id) {
			case 1000005:
				arrivalTranspStationItem
						.setOptionOperationId("searchAllTransportPlacesForCBAv");
				break;
			case 1000003:
				arrivalTranspStationItem
						.setOptionOperationId("searchAllTransportPlacesForCBRk");
				break;
			case 1000004:
				arrivalTranspStationItem
						.setOptionOperationId("searchAllTransportPlacesForCBAvt");
				break;
			default:
				break;
			}
			arrivalTranspStationItem.setOptionDataSource(transpStatDS1);
			arrivalTranspStationItem.setValueField("transp_stat_id");
			arrivalTranspStationItem.setDisplayField("name_descr");

			arrivalTranspStationItem.setOptionCriteria(criteria);
			arrivalTranspStationItem.setAutoFetchData(false);

			arrivalTranspStationItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = arrivalTranspStationItem
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
			transpResourceItem.setTitle("სატრასპ. საშუალება");
			transpResourceItem.setWidth(250);
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

			transpCompItem = new ComboBoxItem();
			transpCompItem.setTitle("სატრასპ. კომპანია");
			transpCompItem.setWidth(250);
			transpCompItem.setName("transpCompItem");
			transpCompItem.setFetchMissingValues(true);
			transpCompItem.setFilterLocally(false);
			transpCompItem.setAddUnknownValues(false);

			DataSource transpCompDS = DataSource.get("TranspCompDS");
			switch (transp_type_id) {
			case 1000005:
				transpCompItem
						.setOptionOperationId("searchAllTransportCompsForCBAv");
				break;
			case 1000003:
				transpCompItem
						.setOptionOperationId("searchAllTransportCompsForCBRail");
				break;
			case 1000004:
				transpCompItem
						.setOptionOperationId("searchAllTransportCompsForCBAvt");
				break;
			default:
				break;
			}
			transpCompItem.setOptionDataSource(transpCompDS);
			transpCompItem.setValueField("transp_comp_id");
			transpCompItem.setDisplayField("name_descr");

			transpCompItem.setOptionCriteria(criteria);
			transpCompItem.setAutoFetchData(false);

			transpCompItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = transpCompItem.getOptionCriteria();
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

			remarkItem = new TextAreaItem();
			remarkItem.setTitle("შენიშვნა");
			remarkItem.setWidth(250);
			remarkItem.setName("remarkItem");
			remarkItem.setHeight(70);

			transpPriceDescrItem = new TextAreaItem();
			transpPriceDescrItem.setTitle("ფასი");
			transpPriceDescrItem.setWidth(250);
			transpPriceDescrItem.setName("transpPriceDescrItem");
			transpPriceDescrItem.setHeight(70);

			transpModelDescrItem = new TextItem();
			transpModelDescrItem.setTitle("რეისი");
			transpModelDescrItem.setWidth(250);
			transpModelDescrItem.setName("transpModelDescrItem");

			importantItem = new CheckboxItem();
			importantItem.setTitle("მნიშვნელოვანი");
			importantItem.setWidth(250);
			importantItem.setName("importantItem");

			departTimeItem = new TimeItem("departTimeItem", "Time");
			departTimeItem.setTitle("გასვლის დრო");
			departTimeItem.setWidth(250);
			departTimeItem.setName("departTimeItem");
			departTimeItem.setHint("");
			departTimeItem.setMask("00:00");
			departTimeItem
					.setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);
			departTimeItem.setUseMask(true);

			arrivalTimeItem = new TimeItem("arrivalTimeItem", "Time");
			arrivalTimeItem.setTitle("ჩასვლის დრო");
			arrivalTimeItem.setWidth(250);
			arrivalTimeItem.setName("arrivalTimeItem");
			arrivalTimeItem.setHint("");
			arrivalTimeItem
					.setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);
			arrivalTimeItem.setUseMask(true);
			arrivalTimeItem.setMask("00:00");

			StaticTextItem staticTextItem = new StaticTextItem();
			staticTextItem.setTitle("");
			staticTextItem.setWidth(250);
			staticTextItem.setName("staticTextItem");

			dynamicForm.setFields(transpTypeItem, transpResourceItem,
					transpCompItem, transpModelDescrItem, depTranspStationItem,
					departTimeItem, arrivalTranspStationItem, arrivalTimeItem,
					staticTextItem, importantItem, transpPriceDescrItem,
					remarkItem);

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

			TranspDetailsClientDS transpDetailsClientDS = TranspDetailsClientDS
					.getInstance();
			transpItemsGrid = new ListGrid();

			final DateTimeFormat dateFormatter = DateTimeFormat
					.getFormat("HH:mm");

			transpItemsGrid.setWidth100();
			transpItemsGrid.setHeight(200);
			transpItemsGrid.setDataSource(transpDetailsClientDS);
			transpItemsGrid.setAutoFetchData(true);

			ListGridField transport_place_geo_out = new ListGridField(
					"transport_place_geo_out", "გავლის პუნქტი", 330);
			ListGridField out_time = new ListGridField("out_time",
					"გასვლის დრო", 130);
			ListGridField in_time = new ListGridField("in_time", "ჩასვლის დრო",
					130);
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

			transpItemsGrid.setFields(transport_place_geo_out, out_time,
					in_time, transport_detail_order);

			transpItemsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = transpItemsGrid
									.getSelectedRecord();
							if (listGridRecord == null) {
								SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
								return;
							}

							String transp_type_id_str = transpTypeItem
									.getValueAsString();
							if (transp_type_id_str == null
									|| transp_type_id_str.trim().equals("")) {
								SC.say("გთხოვთ აირჩიოთ ტრანსპორტის ტიპი !");
								return;
							}
							Integer transp_type_id = null;
							try {
								transp_type_id = new Integer(transp_type_id_str);
							} catch (Exception e) {
								SC.say("გთხოვთ აირჩიოთ ტრანსპორტის ტიპი 1 !");
								return;
							}

							DlgAddEditTransportDetail dlgAddEditTransportDetail = new DlgAddEditTransportDetail(
									transpItemsGrid, listGridRecord,
									transp_type_id);
							dlgAddEditTransportDetail.show();
						}
					});

			hLayout.addMember(transpItemsGrid);

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
							transpItemsGrid, null, transp_type_id);
					dlgAddEditTransportDetail.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = transpItemsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					DlgAddEditTransportDetail dlgAddEditTransportDetail = new DlgAddEditTransportDetail(
							transpItemsGrid, listGridRecord, transp_type_id);
					dlgAddEditTransportDetail.show();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = transpItemsGrid
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
					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										remove(listGridRecord);
									}
								}
							});
				}
			});
			addItem(hLayout);
			fillFields();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}

			Integer transp_res_id = editRecord.getAttributeAsInt("transp_res_id");
			if (transp_res_id != null) {
				transpResourceItem.setValue(transp_res_id);
			}

			Integer transp_comp_id = editRecord.getAttributeAsInt("transp_comp_id");
			if (transp_comp_id != null) {
				transpCompItem.setValue(transp_comp_id);
			}

			String transp_model_descr = editRecord.getAttributeAsString("transp_model_descr");
			if (transp_model_descr != null) {
				transpModelDescrItem.setValue(transp_model_descr);
			}

			Integer depart_transp_stat_id = editRecord.getAttributeAsInt("depart_transp_stat_id");
			if (depart_transp_stat_id != null) {
				depTranspStationItem.setValue(depart_transp_stat_id);
			}

			Integer arrival_transp_stat_id = editRecord.getAttributeAsInt("arrival_transp_stat_id");
			if (arrival_transp_stat_id != null) {
				arrivalTranspStationItem.setValue(arrival_transp_stat_id);
			}

			final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("HH:mm");

			Date depart_time = editRecord.getAttributeAsDate("depart_time");
			if (depart_time != null) {
				String depart_time_str = dateFormatter.format(depart_time);
				departTimeItem.setValue(depart_time_str);
			}

			Date arrival_time = editRecord.getAttributeAsDate("arrival_time");
			if (arrival_time != null) {
				String arrival_time_str = dateFormatter.format(arrival_time);
				arrivalTimeItem.setValue(arrival_time_str);
			}

			String price_descr = editRecord.getAttributeAsString("price_descr");
			if (price_descr != null) {
				transpPriceDescrItem.setValue(price_descr);
			}

			String remark = editRecord.getAttributeAsString("remark");
			if (remark != null) {
				remarkItem.setValue(remark);
			}

			Integer important = editRecord.getAttributeAsInt("important");
			if (important != null && important.equals(-1)) {
				importantItem.setValue(true);
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

			DataSource transportDetailsDS = DataSource.get("TranspItemsDS");
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchTransportDetails");
			Criteria criteria = new Criteria();
			criteria.setAttribute("transp_schedule_id",editRecord.getAttributeAsInt("transp_schedule_id"));
			transportDetailsDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						for (Record record : records) {
							transpItemsGrid.addData(record);
						}
					}
				}
			}, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void remove(ListGridRecord listGridRecord) {
		try {
			transpItemsGrid.removeData(listGridRecord);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void save() {
		try {
			String out_transport_place_id_str = depTranspStationItem
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

			String in_transport_place_id_str = arrivalTranspStationItem
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

			Object out_time_obj = departTimeItem.getValue();
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

			Object in_time_obj = arrivalTimeItem.getValue();
			if (transp_type_id != 1000004 && in_time_obj == null) {
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
			String transport_price_geo = transpPriceDescrItem
					.getValueAsString();
			String note_geo = remarkItem.getValueAsString();

			Integer deleted = null;
			if (editRecord == null) {
				deleted = 0;
			} else {
				deleted = editRecord.getAttributeAsInt("deleted");
			}

			String transport_company_id_str = transpCompItem.getValueAsString();
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

			String transport_plane_id_str = transpResourceItem
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
			String trip_criteria = transpModelDescrItem.getValueAsString();
			boolean note_crit = importantItem.getValueAsBoolean();

			ListGridRecord trasnport_days[] = transpDaysGrid.getRecords();
			if (trasnport_days == null || trasnport_days.length <= 0) {
				SC.say("გთხოვთ მიუთითოთ დღე აუცილებლად !");
				return;
			}

			// check all transport type : t.transport_type_id
			Integer out_tranp = depTranspStationItem.getSelectedRecord()
					.getAttributeAsInt("transp_type_id");
			Integer in_tranp = arrivalTranspStationItem.getSelectedRecord()
					.getAttributeAsInt("transp_type_id");
			Integer transp_pln = transpResourceItem.getSelectedRecord()
					.getAttributeAsInt("transp_type_id");

			if (!transp_type_id.equals(out_tranp)
					|| !transp_type_id.equals(in_tranp)
					|| !transp_type_id.equals(transp_pln)) {
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
			RecordList trasnport_details = transpItemsGrid
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

			// SC.say("MAP : "+records.toString());

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
			record.setAttribute("transp_type_id", transp_type_id);
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
