package com.info08.billing.callcenterbk.client.dialogs.transport;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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

public class DlgAddEditTranspStation extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem transpStatNameDescrItem;
	private ComboBoxItem transpTypeItem;
	private ComboBoxItem cityItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditTranspStation(ListGrid listGrid, ListGridRecord pRecord) {
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

		transpStatNameDescrItem = new TextItem();
		transpStatNameDescrItem.setTitle("დასახელება(ქართ.)");
		transpStatNameDescrItem.setWidth(300);
		transpStatNameDescrItem.setName("transpStatNameDescrItem");

		transpTypeItem = new ComboBoxItem();
		transpTypeItem.setTitle("ტრანსპორტის ტიპი");
		transpTypeItem.setWidth(300);
		transpTypeItem.setName("name_descr");
		transpTypeItem.setFetchMissingValues(true);
		transpTypeItem.setFilterLocally(false);
		transpTypeItem.setAddUnknownValues(false);

		DataSource firstNamesDS = DataSource.get("TranspTypeDS");
		transpTypeItem.setOptionOperationId("searchAllTransportTypesForCombos");
		transpTypeItem.setOptionDataSource(firstNamesDS);
		transpTypeItem.setValueField("transp_type_id");
		transpTypeItem.setDisplayField("name_descr");

		Criteria criteria = new Criteria();
		transpTypeItem.setOptionCriteria(criteria);
		transpTypeItem.setAutoFetchData(false);

		transpTypeItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = transpTypeItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("transp_type_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("transp_type_id", nullO);
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

		dynamicForm.setFields(transpStatNameDescrItem, transpTypeItem,
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
			transpStatNameDescrItem.setValue(editRecord.getAttribute("name_descr"));
			Integer transport_type_id = editRecord.getAttributeAsInt("transp_type_id");
			if (transport_type_id != null) {
				transpTypeItem.setValue(transport_type_id);
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
			String name_descr = transpStatNameDescrItem.getValueAsString();
			if (name_descr == null || name_descr.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String transp_type_id = transpTypeItem.getValueAsString();
			if (transp_type_id == null || transp_type_id.trim().equalsIgnoreCase("")) {
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
			record.setAttribute("name_descr", name_descr);			
			record.setAttribute("transp_type_id", new Integer(transp_type_id));
			record.setAttribute("city_id", new Integer(city_id));

			if (editRecord != null) {
				record.setAttribute("transp_stat_id",editRecord.getAttributeAsInt("transp_stat_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addTransportStation");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateTransportStation");
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
