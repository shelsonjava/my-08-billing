package com.info08.billing.callcenterbk.client.dialogs.transport;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
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

public class DlgAddEditTransportDetail extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem transportPlaceOutItem;
	private TimeItem outTimeItem;
	private TimeItem inTimeItem;
	private SpinnerItem orderItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	DateTimeFormat dateFormatter = DateTimeFormat.getFormat("HH:mm");

	public DlgAddEditTransportDetail(ListGrid listGrid, ListGridRecord pRecord,
			Integer transport_type_id) {
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

		transportPlaceOutItem = new ComboBoxItem();
		transportPlaceOutItem.setTitle("გასვლის პუნქტი");
		transportPlaceOutItem.setWidth(250);
		transportPlaceOutItem.setName("transport_place_geo_out");
		transportPlaceOutItem.setFetchMissingValues(true);
		transportPlaceOutItem.setFilterLocally(false);
		transportPlaceOutItem.setAddUnknownValues(false);

		DataSource transpPlaceDS = DataSource.get("TranspPlaceDS");

		switch (transport_type_id) {
		case 1000005: // Aviation
			transportPlaceOutItem
					.setOptionOperationId("searchAllTransportPlacesForCBAv");
			break;
		case 1000003: // Railway
			transportPlaceOutItem
					.setOptionOperationId("searchAllTransportPlacesForCBRk");
			break;
		case 1000004: // Autobus
			transportPlaceOutItem
					.setOptionOperationId("searchAllTransportPlacesForCBAvt");
			break;
		case 1000002: // Autobus1
			transportPlaceOutItem
					.setOptionOperationId("searchAllTransportPlacesForCBAvt1");
			break;
		case 1000001: // Tax
			transportPlaceOutItem
					.setOptionOperationId("searchAllTransportPlacesForCBMarsh");
			break;
		default:
			transportPlaceOutItem
					.setOptionOperationId("searchAllTransportPlacesForCB");
			break;
		}

		transportPlaceOutItem.setOptionDataSource(transpPlaceDS);
		transportPlaceOutItem.setValueField("transport_place_id");
		transportPlaceOutItem.setDisplayField("transport_place_geo_descr");

		transportPlaceOutItem.setOptionCriteria(new Criteria());
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

		orderItem = new SpinnerItem();
		orderItem.setTitle("თანმიმდევრობა");
		orderItem.setWidth(250);
		orderItem.setName("transport_detail_order");
		orderItem.setMin(0);
		orderItem.setMax(1000);

		dynamicForm.setFields(transportPlaceOutItem, outTimeItem, inTimeItem,
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
			Integer transport_place_id = editRecord
					.getAttributeAsInt("transport_place_id");
			if (transport_place_id != null) {
				transportPlaceOutItem.setValue(transport_place_id);
			}
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
			Integer transport_detail_order = editRecord
					.getAttributeAsInt("transport_detail_order");
			if (transport_detail_order != null) {
				orderItem.setValue(transport_detail_order);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String transport_place_id = transportPlaceOutItem
					.getValueAsString();
			if (transport_place_id == null) {
				SC.say("გთხოვთ მიუთითოთ გასვლის პუნქტი !");
				return;
			}
			String orderStr = orderItem.getValueAsString();
			if (orderStr == null || orderStr.trim().equals("")) {
				SC.say("გთხოვთ მიუთითოთ თანმიმდევრობა !");
				return;
			}
			Integer transport_detail_order = null;
			try {
				transport_detail_order = new Integer(orderStr);
			} catch (Exception e) {
				SC.say("თანმიმდევრობა ველი არის ციფრი !");
				return;
			}
			Object oin_time = inTimeItem.getValue();
			Object oout_time = outTimeItem.getValue();

			ListGridRecord listGridRecord = new ListGridRecord();
			listGridRecord.setAttribute("transport_place_id", new Integer(
					transport_place_id));
			listGridRecord.setAttribute("transport_detail_order",
					transport_detail_order);
			if (oin_time != null) {
				Date in_time = (Date) oin_time;
				listGridRecord.setAttribute("in_time", in_time);
			}
			if (oout_time != null) {
				Date out_time = (Date) oout_time;
				listGridRecord.setAttribute("out_time", out_time);
			}

			String displayValue = transportPlaceOutItem.getDisplayValue();
			listGridRecord
					.setAttribute("transport_place_geo_out", displayValue);

			if (editRecord == null) {
				listGridRecord.setAttribute("deleted", 0);
				listGrid.addData(listGridRecord);
			} else {
				listGridRecord.setAttribute("transport_detail_id",
						editRecord.getAttributeAsInt("transport_detail_id"));
				listGridRecord.setAttribute("deleted",
						editRecord.getAttributeAsInt("deleted"));
				listGrid.updateData(listGridRecord);
			}
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
