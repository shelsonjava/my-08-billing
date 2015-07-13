package com.info08.billing.callcenterbk.client.dialogs.address;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
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

public class DlgAddEditTown extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private TextItem cityNameGeoItem;
	private TextItem cityCodeItem;
	private TextItem cityNewCodeItem;
	private TextItem ofGmtItem;
	private TextItem ofGmtWinterItem;
	private ComboBoxItem countryItem;
	private ComboBoxItem townTypeItem;
	private ComboBoxItem isCapitalItem;
	private ListGridRecord lCityRecord;
	private ListGrid cityGrid;

	public DlgAddEditTown(ListGrid cityGrid, ListGridRecord cityRecord) {
		super();
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
		cityNameGeoItem.setTitle("დასახელება");
		cityNameGeoItem.setWidth(300);
		cityNameGeoItem.setName("town_name");

		cityCodeItem = new TextItem();
		cityCodeItem.setTitle("ქალაქის კოდი");
		cityCodeItem.setWidth(300);
		cityCodeItem.setName("town_code");

		cityNewCodeItem = new TextItem();
		cityNewCodeItem.setTitle("ქალაქის კოდი (ახალი)");
		cityNewCodeItem.setWidth(300);
		cityNewCodeItem.setName("town_new_code");

		ofGmtItem = new TextItem();
		ofGmtItem.setTitle("დრო");
		ofGmtItem.setWidth(300);
		ofGmtItem.setName("normal_gmt");

		ofGmtWinterItem = new TextItem();
		ofGmtWinterItem.setTitle("ზამთრის დრო");
		ofGmtWinterItem.setWidth(300);
		ofGmtWinterItem.setName("winter_gmt");

		countryItem = new ComboBoxItem();
		countryItem.setWidth(300);
		countryItem.setTitle("ქვეყანა");
		countryItem.setName("country_id");
		countryItem.setFetchMissingValues(true);
		countryItem.setFilterLocally(false);
		countryItem.setAddUnknownValues(false);

		townTypeItem = new ComboBoxItem();
		townTypeItem.setTitle("ქალაქის ტიპი");
		townTypeItem.setWidth(300);
		townTypeItem.setName("townTypeItem");
		townTypeItem.setFetchMissingValues(true);
		townTypeItem.setFilterLocally(false);
		townTypeItem.setAddUnknownValues(false);

		DataSource DescriptionsDS = DataSource.get("DescriptionsDS");
		townTypeItem.setOptionOperationId("searchDescriptionsOrderById");
		townTypeItem.setOptionDataSource(DescriptionsDS);
		townTypeItem.setValueField("description_id");
		townTypeItem.setDisplayField("description");

		Criteria criteria = new Criteria();
		criteria.setAttribute("description_type_id", "59000");
		townTypeItem.setOptionCriteria(criteria);
		townTypeItem.setAutoFetchData(false);

		townTypeItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = townTypeItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("town_type_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("town_type_id", nullO);
					}
				}
			}
		});

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

		isCapitalItem = new ComboBoxItem();
		isCapitalItem.setTitle("დედაქალაქი");
		isCapitalItem.setWidth(300);
		isCapitalItem.setName("capital_town");
		isCapitalItem.setValueMap(ClientMapUtil.getInstance().getIsCapital());

		dynamicForm.setFields(cityNameGeoItem, cityCodeItem, cityNewCodeItem,
				ofGmtItem, ofGmtWinterItem, countryItem, townTypeItem,
				isCapitalItem);

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
			countryItem.setDisplayField("country_name");
			Criteria criteria = new Criteria();
			countryItem.setOptionCriteria(criteria);
			countryItem.setAutoFetchData(false);
			if (lCityRecord != null) {
				countryItem.setValue(lCityRecord.getAttribute("country_id"));
			} else {
				countryItem.setValue(Constants.defCountryGeorgiaId);
			}

			if (lCityRecord == null) {
				isCapitalItem.setValue("0");
				return;
			}

			townTypeItem.setValue(lCityRecord.getAttribute("town_type_id"));
			cityNameGeoItem.setValue(lCityRecord.getAttribute("town_name"));
			cityCodeItem.setValue(lCityRecord.getAttribute("town_code"));
			cityNewCodeItem.setValue(lCityRecord.getAttribute("town_new_code"));
			isCapitalItem.setValue(lCityRecord.getAttribute("capital_town"));
			ofGmtItem.setValue(lCityRecord.getAttribute("normal_gmt"));
			ofGmtWinterItem.setValue(lCityRecord.getAttribute("winter_gmt"));

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

			String town_code = cityCodeItem.getValueAsString();
//			if (town_code == null || town_code.trim().equalsIgnoreCase("")) {
//				SC.say("შეიყვანეთ ქალაქის კოდი !");
//				return;
//			}
			String town_code_new = cityNewCodeItem.getValueAsString();

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
//			if (town_code.length() > 180) {
//				SC.say("ქალაქის კოდი შედგება მაქსიმუმ 90 სიმბოლოსაგან !");
//				return;
//			}
//			if (town_code_new != null && town_code_new.length() > 180) {
//				SC.say("ქალაქის ახალი კოდი შედგება მაქსიმუმ 90 სიმბოლოსაგან !");
//				return;
//			}

			ListGridRecord city_type_record = townTypeItem.getSelectedRecord();
			if (city_type_record == null
					|| city_type_record.getAttributeAsInt("description_id") == null) {
				SC.say("გთხოვთ აირჩიოთ ქალაქის ტიპი !");
				return;
			}
			Integer town_type_id = city_type_record
					.getAttributeAsInt("description_id");

			String capital_town_record = isCapitalItem.getValueAsString();
			if (capital_town_record == null
					|| capital_town_record.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ ქალაქის სახეობა !");
				return;
			}
			Integer capital_town = Integer.parseInt(capital_town_record);

//			try {
//				Integer.parseInt(town_code);
//			} catch (NumberFormatException e) {
//				SC.say("ქალაქის კოდის შედგება მხოლოდ ციფრებისაგან !");
//				return;
//			}
//			if (town_code_new != null && !town_code_new.equals("")) {
//				try {
//					Integer.parseInt(town_code_new);
//				} catch (NumberFormatException e) {
//					SC.say("ქალაქის ახალი კოდის შედგება მხოლოდ ციფრებისაგან !");
//					return;
//				}
//			}
			String normal_gmt = ofGmtItem.getValueAsString();
			if (normal_gmt == null || normal_gmt.trim().equals("")) {
				SC.say("გთხოვთ შეიყვანოთ დრო !");
				return;
			}
			try {
				Integer.parseInt(normal_gmt);
			} catch (NumberFormatException e) {
				SC.say("დრო შედგება მხოლოდ ციფრებისაგან !");
				return;
			}
			String winter_gmt = ofGmtWinterItem.getValueAsString();
			if (winter_gmt == null || winter_gmt.trim().equals("")) {
				SC.say("გთხოვთ შეიყვანოთ ზამთრის დრო !");
				return;
			}
			try {
				Integer.parseInt(winter_gmt);
			} catch (NumberFormatException e) {
				SC.say("ზამთრის დრო შედგება მხოლოდ ციფრებისაგან !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("town_name", cityNameGeo);
			record.setAttribute("town_code", town_code);
			record.setAttribute("town_new_code", town_code_new);
			record.setAttribute("country_id", country_id);
			record.setAttribute("town_type_id", town_type_id);
			record.setAttribute("capital_town", capital_town);
			record.setAttribute("normal_gmt", normal_gmt);
			record.setAttribute("winter_gmt", winter_gmt);

			if (lCityRecord != null) {
				record.setAttribute("town_id",
						lCityRecord.getAttributeAsInt("town_id"));
			}
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			if (lCityRecord == null) {
				req.setAttribute("operationId", "townAdd");
				cityGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "townUpdate");
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
