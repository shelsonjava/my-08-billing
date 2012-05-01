package com.info08.billing.callcenter.client.dialogs.transport;

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
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditBusRouteStreet extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem routeItem;
	private ComboBoxItem streetItem;
	private ComboBoxItem routeDirItem;
	private TextItem notesItem;
	private TextItem routeOrderItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditBusRouteStreet(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "მიკრო/ავტ. მარშუტის დამატება"
				: "მიკრო/ავტ. მარშუტის მოდიფიცირება");

		setHeight(210);
		setWidth(520);
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
		dynamicForm.setTitleWidth(200);
		dynamicForm.setNumCols(2);
		hLayout.addMember(dynamicForm);

		routeItem = new ComboBoxItem();
		routeItem.setTitle("მარშუტის ნომერი");
		routeItem.setWidth(300);
		routeItem.setName("route_id");
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
		routeDirItem
				.setValueMap(ClientMapUtil.getInstance().getRouteDirTypes());

		notesItem = new TextItem();
		notesItem.setTitle("კომენტარი");
		notesItem.setName("notes");
		notesItem.setWidth(300);

		routeOrderItem = new TextItem();
		routeOrderItem.setTitle("ნუმერაცია");
		routeOrderItem.setName("route_order");
		routeOrderItem.setWidth(300);

		dynamicForm.setFields(routeItem, streetItem, routeDirItem, notesItem,
				routeOrderItem);

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

			if (editRecord != null) {
				Integer route_id = editRecord.getAttributeAsInt("route_id");
				if (route_id != null) {
					routeItem.setValue(route_id);
				}
				Integer street_id = editRecord.getAttributeAsInt("street_id");
				if (route_id != null) {
					streetItem.setValue(street_id);
				}
				Integer route_dir = editRecord.getAttributeAsInt("route_dir");
				if (route_dir != null) {
					routeDirItem.setValue(route_dir);
				}
				String notes = editRecord.getAttributeAsString("notes");
				if (notes != null) {
					notesItem.setValue(notes);
				}
				Integer route_order = editRecord
						.getAttributeAsInt("route_order");
				if (route_order != null) {
					routeOrderItem.setValue(route_order);
				}
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String route_id = routeItem.getValueAsString();
			if (route_id == null || route_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ მარშუტი !");
				return;
			}
			String street_id = streetItem.getValueAsString();
			if (street_id == null || street_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ ქუჩა !");
				return;
			}
			String route_dir = routeDirItem.getValueAsString();
			if (route_dir == null || route_dir.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ მიმართულება !");
				return;
			}
			String route_order_str = routeOrderItem.getValueAsString();
			if (route_order_str == null
					|| route_order_str.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ნუმერაცია !");
				return;
			}
			Integer route_order = null;
			try {
				route_order = Integer.parseInt(route_order_str);
				if (route_order > 99) {
					SC.say("ნუმერაცია არის მაქსიმუმ 99 !");
					return;
				}
			} catch (NumberFormatException e) {
				SC.say("ნუმერაციის ველი უნდა იყოს ციფრი!");
				return;
			}

			String notes = notesItem.getValueAsString();

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("route_id", new Integer(route_id));
			record.setAttribute("street_id", new Integer(street_id));
			record.setAttribute("route_dir", new Integer(route_dir));
			record.setAttribute("route_order", route_order);
			record.setAttribute("notes", notes);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("route_street_id",
						editRecord.getAttributeAsInt("route_street_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addBusRouteStreet");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateBusRouteStreet");
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
