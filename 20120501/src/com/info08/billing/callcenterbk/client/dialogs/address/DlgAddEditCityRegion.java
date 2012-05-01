package com.info08.billing.callcenterbk.client.dialogs.address;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditCityRegion extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem cityRegionNameGeoItem;
	private TextItem cityRegionNameEngItem;
	private ComboBoxItem citiesItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditCityRegion(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "ახალი ქალაქის რაიონის დამატება"
				: "ქალაქის რაიონის მოდიფიცირება");

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

		cityRegionNameGeoItem = new TextItem();
		cityRegionNameGeoItem.setTitle("დასახელება (ქართულად)");
		cityRegionNameGeoItem.setWidth(300);
		cityRegionNameGeoItem.setName("city_region_name_geo");

		cityRegionNameEngItem = new TextItem();
		cityRegionNameEngItem.setTitle("დასახელება (ინგლისურად)");
		cityRegionNameEngItem.setWidth(300);
		cityRegionNameEngItem.setName("city_region_name_eng");

		citiesItem = new ComboBoxItem();
		citiesItem.setTitle("ქალაქი");
		citiesItem.setName("city_id");
		citiesItem.setWidth(300);
		citiesItem.setFetchMissingValues(false);

		dynamicForm.setFields(cityRegionNameGeoItem, cityRegionNameEngItem,
				citiesItem);

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
		fillCityCombo();
		fillFields();
	}

	private void fillCityCombo() {
		try {
			DataSource citiesDS = DataSource.get("CityDS");
			if (citiesDS != null) {
				citiesItem.setOptionOperationId("citiesSearchCached");
				citiesItem.setOptionDataSource(citiesDS);
				citiesItem.setValueField("city_id");
				citiesItem.setDisplayField("city_name_geo");
				Criteria criteria = new Criteria();
				criteria.setAttribute(
						"filterCityName_11_"
								+ CommonSingleton.getInstance()
										.getUnixTimeStamp(),
						"city_111_"
								+ CommonSingleton.getInstance()
										.getUnixTimeStamp() + "_"
								+ HTMLPanel.createUniqueId());
				citiesItem.setOptionCriteria(criteria);
				citiesItem.setAutoFetchData(true);
				citiesItem.fetchData(new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						if (editRecord != null) {
							Integer city_id = editRecord
									.getAttributeAsInt("city_id");
							if (city_id != null) {
								citiesItem.setValue(city_id);
							} else {
								citiesItem.setValue(Constants.defCityTbilisiId);
							}
						} else {
							citiesItem.setValue(Constants.defCityTbilisiId);
						}
					}
				});
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			cityRegionNameGeoItem.setValue(editRecord
					.getAttribute("city_region_name_geo"));
			cityRegionNameEngItem.setValue(editRecord
					.getAttribute("city_region_name_eng"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String city_region_name_geo = cityRegionNameGeoItem
					.getValueAsString();
			if (city_region_name_geo == null
					|| city_region_name_geo.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String city_region_name_eng = cityRegionNameEngItem
					.getValueAsString();
			if (city_region_name_geo.length() > 155) {
				SC.say("ქართული დასახელება შედგება მაქსიმუმ 155 სიმბოლოსაგან !");
				return;
			}
			if (city_region_name_eng != null
					&& city_region_name_eng.length() > 155) {
				SC.say("ინგლისური დასახელება შედგება მაქსიმუმ 155 სიმბოლოსაგან !");
				return;
			}
			ListGridRecord city_record = citiesItem.getSelectedRecord();
			if (city_record == null) {
				SC.say("გთხოვთ აირჩიოთ ქალაქი !");
				return;
			}
			Integer city_id = city_record.getAttributeAsInt("city_id");
			if (city_id == null) {
				SC.say("გთხოვთ აირჩიოთ ქალაქი - 1 !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("city_region_name_geo", city_region_name_geo);
			record.setAttribute("city_region_name_eng", city_region_name_eng);
			record.setAttribute("deleted", 0);
			record.setAttribute("city_id", city_id);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("city_region_id",
						editRecord.getAttributeAsInt("city_region_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addCityRegion");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateCityRegion");
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
