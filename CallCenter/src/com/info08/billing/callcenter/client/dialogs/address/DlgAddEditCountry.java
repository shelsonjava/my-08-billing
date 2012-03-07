package com.info08.billing.callcenter.client.dialogs.address;

import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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

public class DlgAddEditCountry extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private TextItem countryNameGeoItem;
	private TextItem countryNameEngItem;
	private TextItem countryCodeItem;
	private ComboBoxItem worldRegionItem;
	private ListGrid countryGrid;
	private ListGridRecord countryRecord;

	public DlgAddEditCountry(ListGrid countryGrid,
			ListGridRecord countryRecord, DataSource worldRegionDS) {
		this.countryGrid = countryGrid;
		this.countryRecord = countryRecord;
		setTitle(countryRecord == null ? "ახალი ქვეყნის დამატება"
				: "ქვეყნის მოდიფიცირება");
		setHeight(180);
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

		countryNameGeoItem = new TextItem();
		countryNameGeoItem.setTitle("დასახელება (ქართულად)");
		countryNameGeoItem.setWidth(300);
		countryNameGeoItem.setName("country_name_geo");

		countryNameEngItem = new TextItem();
		countryNameEngItem.setTitle("დასახელება (ინგლისურად)");
		countryNameEngItem.setWidth(300);
		countryNameEngItem.setName("country_name_eng");

		countryCodeItem = new TextItem();
		countryCodeItem.setTitle("ქვეყნის კოდი");
		countryCodeItem.setWidth(300);
		countryCodeItem.setName("country_code");

		worldRegionItem = new ComboBoxItem();
		worldRegionItem.setWidth(300);
		worldRegionItem.setTitle("რეგიონი");
		worldRegionItem.setOptionOperationId("worldRegionsSearch");
		worldRegionItem.setOptionDataSource(worldRegionDS);
		worldRegionItem.setValueField("world_region_id");
		worldRegionItem.setDisplayField("world_region_name_geo");
		worldRegionItem.setAutoFetchData(true);

		dynamicForm.setFields(countryNameGeoItem, countryNameEngItem,
				countryCodeItem, worldRegionItem);

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
			if (countryRecord == null) {
				return;
			}
			countryNameGeoItem.setValue(countryRecord
					.getAttribute("country_name_geo"));
			countryNameEngItem.setValue(countryRecord
					.getAttribute("country_name_eng"));
			countryCodeItem
					.setValue(countryRecord.getAttribute("country_code"));
			worldRegionItem.setValue(countryRecord
					.getAttribute("world_region_id"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void saveCountry() {
		try {
			String countryNameGeo = countryNameGeoItem.getValueAsString();
			if (countryNameGeo == null
					|| countryNameGeo.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String countryNameEng = countryNameEngItem.getValueAsString();
			if (countryNameEng == null
					|| countryNameEng.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ინგლისური დასახელება !");
				return;
			}
			String countryCode = countryCodeItem.getValueAsString();
			if (countryCode == null || countryCode.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქვეყნის კოდი !");
				return;
			}
			ListGridRecord world_region_record = worldRegionItem
					.getSelectedRecord();
			if (world_region_record == null
					|| world_region_record.getAttributeAsInt("world_region_id") == null) {
				SC.say("გთხოვთ აირჩიოთ რეგიონი !");
				return;
			}
			Integer world_region_id = world_region_record
					.getAttributeAsInt("world_region_id");

			if (countryNameGeo.length() > 155) {
				SC.say("ქართული დასახელება შედგება მაქსიმუმ 155 სიმბოლოსაგან !");
				return;
			}
			if (countryNameEng.length() > 155) {
				SC.say("ინგლისური დასახელება შედგება მაქსიმუმ 155 სიმბოლოსაგან !");
				return;
			}
			if (countryCode.length() > 90) {
				SC.say("ქვეყნის კოდი შედგება მაქსიმუმ 90 სიმბოლოსაგან !");
				return;
			}
			try {
				Integer.parseInt(countryCode);
			} catch (NumberFormatException e) {
				SC.say("ქვეყნის კოდის შედგება მხოლოდ ციფრებისაგან !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("country_name_geo", countryNameGeo);
			record.setAttribute("country_name_eng", countryNameEng);
			record.setAttribute("country_code", countryCode);
			record.setAttribute("world_region_id", world_region_id);

			if (countryRecord != null) {
				record.setAttribute("country_id",
						countryRecord.getAttributeAsInt("country_id"));
			}
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			if (countryRecord == null) {
				req.setAttribute("operationId", "countryAdd");
				countryGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "countryUpdate");
				countryGrid.updateData(record, new DSCallback() {
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
