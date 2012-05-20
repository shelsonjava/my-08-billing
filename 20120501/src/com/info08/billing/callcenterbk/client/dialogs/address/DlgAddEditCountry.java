package com.info08.billing.callcenterbk.client.dialogs.address;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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
	private TextItem countryNameItem;
	private TextItem phoneCodeItem;
	private ComboBoxItem continentItem;
	private ListGrid countryGrid;
	private ListGridRecord countryRecord;

	public DlgAddEditCountry(ListGrid countryGrid,
			ListGridRecord countryRecord, DataSource continentDS) {
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

		countryNameItem = new TextItem();
		countryNameItem.setTitle("დასახელება");
		countryNameItem.setWidth(300);
		countryNameItem.setName("country_name");

		phoneCodeItem = new TextItem();
		phoneCodeItem.setTitle("ქვეყნის კოდი");
		phoneCodeItem.setWidth(300);
		phoneCodeItem.setName("phone_code");

		continentItem = new ComboBoxItem();
		continentItem.setWidth(300);
		continentItem.setTitle("კონტინენტი");
		continentItem.setOptionOperationId("continentSearch");
		continentItem.setOptionDataSource(continentDS);
		continentItem.setValueField("id");
		continentItem.setDisplayField("name_descr");
		continentItem.setAutoFetchData(true);

		dynamicForm.setFields(countryNameItem, phoneCodeItem, continentItem);

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
			countryNameItem
					.setValue(countryRecord.getAttribute("country_name"));
			phoneCodeItem.setValue(countryRecord.getAttribute("phone_code"));
			continentItem.setValue(countryRecord.getAttribute("continent_id"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void saveCountry() {
		try {
			String countryNameGeo = countryNameItem.getValueAsString();
			if (countryNameGeo == null
					|| countryNameGeo.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}

			String phone_code = phoneCodeItem.getValueAsString();
			if (phone_code == null || phone_code.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქვეყნის კოდი !");
				return;
			}
			ListGridRecord continent_record = continentItem.getSelectedRecord();
			if (continent_record == null
					|| continent_record.getAttributeAsInt("id") == null) {
				SC.say("გთხოვთ აირჩიოთ კონტინენტი !");
				return;
			}
			Integer continent_id = continent_record.getAttributeAsInt("id");

			if (countryNameGeo.length() > 155) {
				SC.say("ქართული დასახელება შედგება მაქსიმუმ 155 სიმბოლოსაგან !");
				return;
			}
			
			if (phone_code.length() > 90) {
				SC.say("ქვეყნის კოდი შედგება მაქსიმუმ 90 სიმბოლოსაგან !");
				return;
			}
			try {
				Integer.parseInt(phone_code);
			} catch (NumberFormatException e) {
				SC.say("ქვეყნის კოდის შედგება მხოლოდ ციფრებისაგან !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("country_name", countryNameGeo);
			record.setAttribute("phone_code", phone_code);
			record.setAttribute("continent_id", continent_id);

			if (countryRecord != null) {
				record.setAttribute("country_id",
						countryRecord.getAttributeAsInt("country_id"));
			}
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
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
