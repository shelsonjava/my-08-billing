package com.info08.billing.callcenterbk.client.dialogs.transport;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditTranspItem extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem departTranspStatItem;
	private TimeItem departTimeItem;
	private TimeItem arrivalTimeItem;
	private SpinnerItem orderItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	DateTimeFormat dateFormatter = DateTimeFormat.getFormat("HH:mm");

	public DlgAddEditTranspItem(ListGrid listGrid, ListGridRecord pRecord,
			Integer transport_type_id) {
		super();
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "გაჩერების დამატება"
				: "გაჩერების მოდიფიცირება");

		setHeight(180);
		setWidth(400);
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
		dynamicForm.setTitleWidth(150);
		dynamicForm.setNumCols(2);
		hLayout.addMember(dynamicForm);

		departTranspStatItem = new ComboBoxItem();
		departTranspStatItem.setTitle("გასვლის პუნქტი");
		departTranspStatItem.setWidth(250);
		departTranspStatItem.setName("departTranspStatItem");
		departTranspStatItem.setFetchMissingValues(true);
		departTranspStatItem.setFilterLocally(false);
		departTranspStatItem.setAddUnknownValues(false);

		DataSource transpStatDS = DataSource.get("TranspStatDS");

		switch (transport_type_id) {
		case 1000005: // Aviation
			departTranspStatItem.setOptionOperationId("searchAllTransportPlacesForCBAv");
			break;
		case 1000003: // Railway
			departTranspStatItem.setOptionOperationId("searchAllTransportPlacesForCBRk");
			break;
		case 1000004: // Autobus
			departTranspStatItem.setOptionOperationId("searchAllTransportPlacesForCBAvt");
			break;
		case 1000002: // Autobus1
			departTranspStatItem.setOptionOperationId("searchAllTransportPlacesForCBAvt1");
			break;
		case 1000001: // Tax
			departTranspStatItem.setOptionOperationId("searchAllTransportPlacesForCBMarsh");
			break;
		default:
			departTranspStatItem.setOptionOperationId("searchAllTransportPlacesForCB");
			break;
		}

		departTranspStatItem.setOptionDataSource(transpStatDS);
		departTranspStatItem.setValueField("transp_stat_id");
		departTranspStatItem.setDisplayField("name_descr");

		departTranspStatItem.setOptionCriteria(new Criteria());
		departTranspStatItem.setAutoFetchData(false);

		departTranspStatItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = departTranspStatItem.getOptionCriteria();
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

		departTimeItem = new TimeItem("departTimeItem", "Time");
		departTimeItem.setTitle("გასვლის დრო");
		departTimeItem.setWidth(250);
		departTimeItem.setName("departTimeItem");
		departTimeItem.setHint("");
		departTimeItem.setMask("00:00");
		departTimeItem.setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);
		departTimeItem.setUseMask(true);

		arrivalTimeItem = new TimeItem("arrivalTimeItem", "Time");
		arrivalTimeItem.setTitle("ჩასვლის დრო");
		arrivalTimeItem.setWidth(250);
		arrivalTimeItem.setName("arrivalTimeItem");
		arrivalTimeItem.setHint("");
		arrivalTimeItem.setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);
		arrivalTimeItem.setUseMask(true);
		arrivalTimeItem.setMask("00:00");

		orderItem = new SpinnerItem();
		orderItem.setTitle("თანმიმდევრობა");
		orderItem.setWidth(250);
		orderItem.setName("orderItem");
		orderItem.setMin(0);
		orderItem.setMax(1000);

		dynamicForm.setFields(departTranspStatItem, departTimeItem, arrivalTimeItem,
				orderItem);

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

		addItem(hLayout);
		fillFields();
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			Integer transp_station_id = editRecord.getAttributeAsInt("transp_station_id");
			if (transp_station_id != null) {
				departTranspStatItem.setValue(transp_station_id);
			}
			Date departure_time = editRecord.getAttributeAsDate("departure_time");
			if (departure_time != null) {
				String departure_time_str = dateFormatter.format(departure_time);
				departTimeItem.setValue(departure_time_str);
			}

			Date arrival_time = editRecord.getAttributeAsDate("arrival_time");
			if (arrival_time != null) {
				String arrival_time_str = dateFormatter.format(arrival_time);
				arrivalTimeItem.setValue(arrival_time_str);
			}
			Integer item_order = editRecord.getAttributeAsInt("item_order");
			if (item_order != null) {
				orderItem.setValue(item_order);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String transp_station_id = departTranspStatItem.getValueAsString();
			if (transp_station_id == null) {
				SC.say("გთხოვთ მიუთითოთ გასვლის პუნქტი !");
				return;
			}
			String orderStr = orderItem.getValueAsString();
			if (orderStr == null || orderStr.trim().equals("")) {
				SC.say("გთხოვთ მიუთითოთ თანმიმდევრობა !");
				return;
			}
			Integer item_order = null;
			try {
				item_order = new Integer(orderStr);
			} catch (Exception e) {
				SC.say("თანმიმდევრობა ველი არის ციფრი !");
				return;
			}
			Object o_arrival_time = arrivalTimeItem.getValue();
			Object o_departure_time = departTimeItem.getValue();

			ListGridRecord listGridRecord = new ListGridRecord();
			listGridRecord.setAttribute("transp_station_id", new Integer(transp_station_id));
			listGridRecord.setAttribute("item_order",item_order);
			if (o_arrival_time != null) {
				Date arrival_time = (Date) o_arrival_time;
				listGridRecord.setAttribute("arrival_time", arrival_time);
			}
			if (o_departure_time != null) {
				Date departure_time = (Date) o_departure_time;
				listGridRecord.setAttribute("departure_time", departure_time);
			}

			String displayValue = departTranspStatItem.getDisplayValue();
			listGridRecord.setAttribute("depart_station", displayValue);

			if (editRecord == null) {
				listGrid.addData(listGridRecord);
			} else {
				listGridRecord.setAttribute("transp_item_id", editRecord.getAttributeAsInt("transp_item_id"));
				listGrid.updateData(listGridRecord);
			}
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
