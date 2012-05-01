package com.info08.billing.callcenter.client.dialogs.transport;

import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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

public class DlgAddEditTransportPlace extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem transportPlaceGeoItem;
	private ComboBoxItem transportTypeItem;
	private ComboBoxItem cityItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditTransportPlace(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "სატრანსპორტო სადგურის დამატება"
				: "სატრანსპორტო სადგურის მოდიფიცირება");

		setHeight(160);
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

		transportPlaceGeoItem = new TextItem();
		transportPlaceGeoItem.setTitle("დასახელება(ქართ.)");
		transportPlaceGeoItem.setWidth(300);
		transportPlaceGeoItem.setName("transport_place_geo");

		transportTypeItem = new ComboBoxItem();
		transportTypeItem.setTitle("ტრანსპორტის ტიპი");
		transportTypeItem.setWidth(300);
		transportTypeItem.setName("transport_type_name_geo");
		transportTypeItem.setFetchMissingValues(true);
		transportTypeItem.setFilterLocally(false);
		transportTypeItem.setAddUnknownValues(false);

		DataSource firstNamesDS = DataSource.get("TranspTypeDS");
		transportTypeItem
				.setOptionOperationId("searchAllTransportTypesForCombos");
		transportTypeItem.setOptionDataSource(firstNamesDS);
		transportTypeItem.setValueField("transport_type_id");
		transportTypeItem.setDisplayField("transport_type_name_geo");

		Criteria criteria = new Criteria();
		transportTypeItem.setOptionCriteria(criteria);
		transportTypeItem.setAutoFetchData(false);

		transportTypeItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = transportTypeItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("transport_type_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("transport_type_id", nullO);
					}
				}
			}
		});

		cityItem = new ComboBoxItem();
		cityItem.setTitle("ქალაქი");
		cityItem.setWidth(300);
		cityItem.setName("city_name_geo");
		cityItem.setFetchMissingValues(true);
		cityItem.setFilterLocally(false);
		cityItem.setAddUnknownValues(false);

		DataSource cityDS = DataSource.get("CityDS");
		cityItem.setOptionOperationId("searchCitiesFromDBForCombosAll");
		cityItem.setOptionDataSource(cityDS);
		cityItem.setValueField("city_id");
		cityItem.setDisplayField("city_name_geo");

		Criteria criteriaCity = new Criteria();
		cityItem.setOptionCriteria(criteriaCity);
		cityItem.setAutoFetchData(false);

		cityItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = cityItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("city_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("city_id", nullO);
					}
				}
			}
		});

		dynamicForm.setFields(transportPlaceGeoItem, transportTypeItem,
				cityItem);

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
			transportPlaceGeoItem.setValue(editRecord
					.getAttribute("transport_place_geo"));
			Integer transport_type_id = editRecord
					.getAttributeAsInt("transport_type_id");
			if (transport_type_id != null) {
				transportTypeItem.setValue(transport_type_id);
			}
			Integer city_id = editRecord.getAttributeAsInt("city_id");
			if (transport_type_id != null) {
				cityItem.setValue(city_id);
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String transport_place_geo = transportPlaceGeoItem
					.getValueAsString();
			if (transport_place_geo == null
					|| transport_place_geo.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String transport_type_id = transportTypeItem.getValueAsString();
			if (transport_type_id == null
					|| transport_type_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ ტრანსპორტის ტიპი !");
				return;
			}
			String city_id = cityItem.getValueAsString();
			if (city_id == null || city_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ ქალაქი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("transport_place_geo", transport_place_geo);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("transport_type_id", new Integer(
					transport_type_id));
			record.setAttribute("city_id", new Integer(city_id));

			if (editRecord != null) {
				record.setAttribute("transport_place_id",
						editRecord.getAttributeAsInt("transport_place_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addTransportPlace");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateTransportPlace");
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
