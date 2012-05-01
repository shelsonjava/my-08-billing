package com.info08.billing.callcenterbk.client.dialogs.address;

import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
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

public class DlgAddEditCity extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private TextItem cityNameGeoItem;
	private TextItem cityNameEngItem;
	private TextItem cityCodeItem;
	private TextItem cityNewCodeItem;
	private TextItem ofGmtItem;
	private TextItem ofGmtWinterItem;
	private ComboBoxItem countryItem;
	private ComboBoxItem cityTypeItem;
	private ComboBoxItem isCapitalItem;
	private ListGridRecord lCityRecord;
	private ListGrid cityGrid;

	public DlgAddEditCity(ListGrid cityGrid, ListGridRecord cityRecord) {
		this.lCityRecord = cityRecord;
		this.cityGrid = cityGrid;

		setTitle(cityRecord == null ? "ახალი ქალაქის დამატება"
				: "ქალაქის მოდიფიცირება");

		setHeight(310);
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

		cityNameGeoItem = new TextItem();
		cityNameGeoItem.setTitle("დასახელება (ქართულად)");
		cityNameGeoItem.setWidth(300);
		cityNameGeoItem.setName("city_name_geo");

		cityNameEngItem = new TextItem();
		cityNameEngItem.setTitle("დასახელება (ინგლისურად)");
		cityNameEngItem.setWidth(300);
		cityNameEngItem.setName("city_name_eng");

		cityCodeItem = new TextItem();
		cityCodeItem.setTitle("ქალაქის კოდი");
		cityCodeItem.setWidth(300);
		cityCodeItem.setName("city_code");

		cityNewCodeItem = new TextItem();
		cityNewCodeItem.setTitle("ქალაქის კოდი (ახალი)");
		cityNewCodeItem.setWidth(300);
		cityNewCodeItem.setName("city_new_code");

		ofGmtItem = new TextItem();
		ofGmtItem.setTitle("დრო");
		ofGmtItem.setWidth(300);
		ofGmtItem.setName("of_gmt");

		ofGmtWinterItem = new TextItem();
		ofGmtWinterItem.setTitle("ზამთრის დრო");
		ofGmtWinterItem.setWidth(300);
		ofGmtWinterItem.setName("of_gmt_winter");

		countryItem = new ComboBoxItem();
		countryItem.setWidth(300);
		countryItem.setTitle("ქვეყანა");
		countryItem.setName("country_id");
		countryItem.setFetchMissingValues(true);
		countryItem.setFilterLocally(false);
		countryItem.setAddUnknownValues(false);

		cityTypeItem = new ComboBoxItem();
		cityTypeItem.setTitle("ქალაქის ტიპი");
		cityTypeItem.setWidth(300);
		cityTypeItem.setName("city_type_id");
		cityTypeItem.setFetchMissingValues(true);
		cityTypeItem.setFilterLocally(false);
		cityTypeItem.setAddUnknownValues(false);
		
		countryItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = countryItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("country_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("country_id", nullO);
					}
				}
			}
		});
		
		cityTypeItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = cityTypeItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("city_type_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("city_type_id", nullO);
					}
				}
			}
		});

		isCapitalItem = new ComboBoxItem();
		isCapitalItem.setTitle("დედაქალაქი");
		isCapitalItem.setWidth(300);
		isCapitalItem.setName("is_capital");
		isCapitalItem.setValueMap(ClientMapUtil.getInstance().getIsCapital());

		dynamicForm.setFields(cityNameGeoItem, cityNameEngItem, cityCodeItem,
				cityNewCodeItem, ofGmtItem, ofGmtWinterItem, countryItem,
				cityTypeItem, isCapitalItem);

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
				saveCountry();
			}
		});

		addItem(hLayout);
		fillFields();
	}

	private void fillFields() {
		try {
			DataSource countryDS = DataSource.get("CountryDS");
			countryItem.setOptionOperationId("searchAllCountriesForCombos");
			countryItem.setOptionDataSource(countryDS);
			countryItem.setValueField("country_id");
			countryItem.setDisplayField("country_name_geo");
			Criteria criteria = new Criteria();
			countryItem.setOptionCriteria(criteria);
			countryItem.setAutoFetchData(false);
			if (lCityRecord != null) {
				countryItem.setValue(lCityRecord
						.getAttribute("country_id"));
			} else {
				countryItem.setValue(194);
			}
			
			DataSource cityTypeDS = DataSource.get("CityTypeDS");
			cityTypeItem.setOptionOperationId("searchAllCityTypesForCombos");
			cityTypeItem.setOptionDataSource(cityTypeDS);
			cityTypeItem.setValueField("city_type_id");
			cityTypeItem.setDisplayField("city_type_geo");
			cityTypeItem.setOptionCriteria(criteria);
			cityTypeItem.setAutoFetchData(false);
			if (lCityRecord != null) {
				cityTypeItem.setValue(lCityRecord.getAttribute("city_type_id"));
			}else{
				cityTypeItem.setValue("1");
			}
			if (lCityRecord == null) {
				isCapitalItem.setValue("0");
				return;
			}
			cityNameGeoItem.setValue(lCityRecord.getAttribute("city_name_geo"));
			cityNameEngItem.setValue(lCityRecord.getAttribute("city_name_eng"));
			cityCodeItem.setValue(lCityRecord.getAttribute("city_code"));
			cityNewCodeItem.setValue(lCityRecord.getAttribute("city_new_code"));
			isCapitalItem.setValue(lCityRecord.getAttribute("is_capital"));
			ofGmtItem.setValue(lCityRecord.getAttribute("of_gmt"));
			ofGmtWinterItem.setValue(lCityRecord.getAttribute("of_gmt_winter"));

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void saveCountry() {
		try {
			String cityNameGeo = cityNameGeoItem.getValueAsString();
			if (cityNameGeo == null || cityNameGeo.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String cityNameEng = cityNameEngItem.getValueAsString();
			if (cityNameEng == null || cityNameEng.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ინგლისური დასახელება !");
				return;
			}
			String cityCode = cityCodeItem.getValueAsString();
			if (cityCode == null || cityCode.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქალაქის კოდი !");
				return;
			}
			String cityNewCode = cityNewCodeItem.getValueAsString();

			ListGridRecord country_record = countryItem.getSelectedRecord();
			if (country_record == null
					|| country_record.getAttributeAsInt("country_id") == null) {
				SC.say("გთხოვთ აირჩიოთ ქვეყანა !");
				return;
			}
			Integer country_id = country_record.getAttributeAsInt("country_id");

			if (cityNameGeo.length() > 1005) {
				SC.say("ქართული დასახელება შედგება მაქსიმუმ 155 სიმბოლოსაგან !");
				return;
			}
			if (cityNameEng.length() > 1005) {
				SC.say("ინგლისური დასახელება შედგება მაქსიმუმ 155 სიმბოლოსაგან !");
				return;
			}
			if (cityCode.length() > 180) {
				SC.say("ქალაქის კოდი შედგება მაქსიმუმ 90 სიმბოლოსაგან !");
				return;
			}
			if (cityNewCode!=null && cityNewCode.length() > 180) {
				SC.say("ქალაქის ახალი კოდი შედგება მაქსიმუმ 90 სიმბოლოსაგან !");
				return;
			}

			ListGridRecord city_type_record = cityTypeItem.getSelectedRecord();
			if (city_type_record == null
					|| city_type_record.getAttributeAsInt("city_type_id") == null) {
				SC.say("გთხოვთ აირჩიოთ ქალაქის ტიპი !");
				return;
			}
			Integer city_type_id = city_type_record
					.getAttributeAsInt("city_type_id");

			String is_capital_record = isCapitalItem.getValueAsString();
			if (is_capital_record == null
					|| is_capital_record.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ ქალაქის სახეობა !");
				return;
			}
			Integer is_capital = Integer.parseInt(is_capital_record);

			try {
				Integer.parseInt(cityCode);
			} catch (NumberFormatException e) {
				SC.say("ქალაქის კოდის შედგება მხოლოდ ციფრებისაგან !");
				return;
			}
			if (cityNewCode!=null && !cityNewCode.equals("")) {
				try {
					Integer.parseInt(cityNewCode);
				} catch (NumberFormatException e) {
					SC.say("ქალაქის ახალი კოდის შედგება მხოლოდ ციფრებისაგან !");
					return;
				}
			}
			String of_gmt = ofGmtItem.getValueAsString();
			if (of_gmt == null || of_gmt.trim().equals("")) {
				SC.say("გთხოვთ შეიყვანოთ დრო !");
				return;
			}
			try {
				Integer.parseInt(of_gmt);
			} catch (NumberFormatException e) {
				SC.say("დრო შედგება მხოლოდ ციფრებისაგან !");
				return;
			}
			String of_gmt_winter = ofGmtWinterItem.getValueAsString();
			if (of_gmt_winter == null || of_gmt_winter.trim().equals("")) {
				SC.say("გთხოვთ შეიყვანოთ ზამთრის დრო !");
				return;
			}
			try {
				Integer.parseInt(of_gmt_winter);
			} catch (NumberFormatException e) {
				SC.say("ზამთრის დრო შედგება მხოლოდ ციფრებისაგან !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("city_name_geo", cityNameGeo);
			record.setAttribute("city_name_eng", cityNameEng);
			record.setAttribute("city_code", cityCode);
			record.setAttribute("city_new_code", cityNewCode);
			record.setAttribute("country_id", country_id);
			record.setAttribute("city_type_id", city_type_id);
			record.setAttribute("is_capital", is_capital);
			record.setAttribute("of_gmt", of_gmt);
			record.setAttribute("of_gmt_winter", of_gmt_winter);

			if (lCityRecord != null) {
				record.setAttribute("city_id",
						lCityRecord.getAttributeAsInt("city_id"));
			}
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			if (lCityRecord == null) {
				req.setAttribute("operationId", "cityAdd");
				cityGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "cityUpdate");
				cityGrid.updateData(record, new DSCallback() {
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
