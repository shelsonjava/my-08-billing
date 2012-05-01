package com.info08.billing.callcenterbk.client.dialogs.address;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.SharedUtils;
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
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditCityDistance extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem cityStartItem;
	private ComboBoxItem cityEndItem;
	private ComboBoxItem distanceTypeItem;
	private TextItem distanceItem;
	private TextAreaItem noteGeoItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditCityDistance(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "ახალი ქალაქის რაიონის დამატება"
				: "ქალაქის რაიონის მოდიფიცირება");

		setHeight(240);
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

		cityStartItem = new ComboBoxItem();
		cityStartItem.setTitle("საწყისი ქალაქი");
		cityStartItem.setWidth(300);
		cityStartItem.setName("city_id_start");
		cityStartItem.setFetchMissingValues(true);
		cityStartItem.setFilterLocally(false);
		cityStartItem.setAddUnknownValues(false);

		DataSource cityDS = DataSource.get("CityDS");
		cityStartItem.setOptionOperationId("searchCitiesFromDBForCombos1");
		cityStartItem.setOptionDataSource(cityDS);
		cityStartItem.setValueField("city_id");
		cityStartItem.setDisplayField("city_name_geo");

		Criteria criteriaCity = new Criteria();
		cityStartItem.setOptionCriteria(criteriaCity);
		cityStartItem.setAutoFetchData(false);

		cityStartItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = cityStartItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("city_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("city_id", nullO);
					}
				}
			}
		});
		
		cityEndItem = new ComboBoxItem();
		cityEndItem.setTitle("საბოლოო ქალაქი");
		cityEndItem.setWidth(300);
		cityEndItem.setName("city_id_end");
		cityEndItem.setFetchMissingValues(true);
		cityEndItem.setFilterLocally(false);
		cityEndItem.setAddUnknownValues(false);

		DataSource cityDS1 = DataSource.get("CityDS");
		cityEndItem.setOptionOperationId("searchCitiesFromDBForCombos1");
		cityEndItem.setOptionDataSource(cityDS1);
		cityEndItem.setValueField("city_id");
		cityEndItem.setDisplayField("city_name_geo");

		Criteria criteriaCity1 = new Criteria();
		cityEndItem.setOptionCriteria(criteriaCity1);
		cityEndItem.setAutoFetchData(false);

		cityEndItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = cityEndItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("city_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("city_id", nullO);
					}
				}
			}
		});

		distanceTypeItem = new ComboBoxItem();
		distanceTypeItem.setTitle("მანძილის ტიპი");
		distanceTypeItem.setName("city_id");
		distanceTypeItem.setWidth(300);
		distanceTypeItem.setValueMap(SharedUtils.getInstance()
				.getMapDistanceTypes());
		distanceTypeItem.setDefaultToFirstOption(true);

		distanceItem = new TextItem();
		distanceItem.setTitle("მანძილი (კმ.)");
		distanceItem.setWidth(300);
		distanceItem.setName("city_distance_geo");

		noteGeoItem = new TextAreaItem();
		noteGeoItem.setTitle("კომენტარი");
		noteGeoItem.setWidth(300);
		noteGeoItem.setHeight(50);
		noteGeoItem.setName("note_geo");

		dynamicForm.setFields(cityStartItem, cityEndItem, distanceTypeItem,
				distanceItem, noteGeoItem);

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
			cityStartItem.setValue(editRecord.getAttributeAsInt("city_id_start"));
			cityEndItem.setValue(editRecord.getAttributeAsInt("city_id_end"));
			distanceTypeItem.setValue(editRecord.getAttributeAsInt("city_distance_type"));
			distanceItem.setValue(editRecord.getAttributeAsString("city_distance_geo"));
			noteGeoItem.setValue(editRecord.getAttribute("note_geo"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String city_start_record = cityStartItem.getValueAsString();
			if (city_start_record == null) {
				SC.say("აირჩიეთ საწყისი ქალაქი !");
				return;
			}
			String city_end_record = cityEndItem.getValueAsString();
			if (city_end_record == null) {
				SC.say("აირჩიეთ საბოლოო ქალაქი !");
				return;
			}
			String dist_type_record = distanceTypeItem.getValueAsString();
			if (dist_type_record == null) {
				SC.say("აირჩიეთ მანძილის ტიპი !");
				return;
			}
			String city_distance_geo = distanceItem.getValueAsString();
			String note_geo = noteGeoItem.getValueAsString();

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("city_distance_geo", city_distance_geo);
			record.setAttribute("city_distance_type", new Integer(
					distanceTypeItem.getValueAsString()));
			record.setAttribute("city_id_end", city_end_record);
			record.setAttribute("city_id_start", city_start_record);
			record.setAttribute("deleted", 0);
			record.setAttribute("note_geo", note_geo);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("city_distance_id",
						editRecord.getAttributeAsInt("city_distance_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addCityDistance");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateCityDistance");
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
