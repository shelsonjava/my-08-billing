package com.info08.billing.callcenter.client.dialogs.address;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenter.client.singletons.ClientMapUtil;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditGeoIndCountry extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	// form fields
	private ComboBoxItem geoIndRegionItem;
	private TextItem geoCountryGeoItem;
	private TextItem geoIndexItem;
	private ComboBoxItem isRaionCenterItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditGeoIndCountry(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "ახალი ინდექსის დამატება"
				: "ინდექსის მოდიფიცირება");

		setHeight(200);
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

		geoIndRegionItem = new ComboBoxItem();
		geoIndRegionItem.setTitle("რაიონი");
		geoIndRegionItem.setWidth(350);
		geoIndRegionItem.setName("region_id");
		geoIndRegionItem.setFetchMissingValues(true);
		geoIndRegionItem.setFilterLocally(true);

		geoCountryGeoItem = new TextItem();
		geoCountryGeoItem.setTitle("დასახელება(ქართ.)");
		geoCountryGeoItem.setWidth(350);
		geoCountryGeoItem.setName("geo_country_geo");

		geoIndexItem = new TextItem();
		geoIndexItem.setTitle("ინდექსი");
		geoIndexItem.setWidth(350);
		geoIndexItem.setName("geo_index");

		isRaionCenterItem = new ComboBoxItem();
		isRaionCenterItem.setTitle("რაიონი");
		isRaionCenterItem.setWidth(350);
		isRaionCenterItem.setName("is_center");
		isRaionCenterItem.setValueMap(ClientMapUtil.getInstance()
				.getRaionCentTypes());
		isRaionCenterItem.setDefaultToFirstOption(true);

		dynamicForm.setFields(geoIndRegionItem, geoCountryGeoItem,
				geoIndexItem, isRaionCenterItem);

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
		fillCombos();
		addItem(hLayout);
		fillFields();
	}

	private void fillCombos() {
		try {
			DataSource geoIndRegDS = DataSource.get("GeoIndRegionDS");
			geoIndRegionItem.setOptionOperationId("searchGeoIndRegionFromDB");
			geoIndRegionItem.setOptionDataSource(geoIndRegDS);
			geoIndRegionItem.setValueField("region_id");
			geoIndRegionItem.setDisplayField("region_name_geo");
			Criteria optionCriteria = new Criteria();
			optionCriteria.setAttribute("DlgAddEditGeoIndCountry",
					"DlgAddEditGeoIndCountry_" + HTMLPanel.createUniqueId());
			optionCriteria.setAttribute(CommonSingleton.getInstance()
					.getUnixTimeStamp(), CommonSingleton.getInstance()
					.getUnixTimeStamp());
			geoIndRegionItem.setOptionCriteria(optionCriteria);
			geoIndRegionItem.setAutoFetchData(true);

			DSRequest requestProperties1 = new DSRequest();
			requestProperties1.setOperationId("searchGeoIndRegionFromDB");

			geoIndRegionItem.fetchData(new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					if (editRecord != null) {
						geoIndRegionItem.setValue(editRecord
								.getAttributeAsInt("region_id"));
					}
				}
			}, requestProperties1);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			geoCountryGeoItem.setValue(editRecord
					.getAttributeAsString("geo_country_geo"));
			geoIndexItem.setValue(editRecord.getAttributeAsString("geo_index"));
			isRaionCenterItem.setValue(editRecord
					.getAttributeAsInt("is_center"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String region_id = geoIndRegionItem.getValueAsString();
			if (region_id == null || region_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ რაიონი !");
				return;
			}
			String geo_country_geo = geoCountryGeoItem.getValueAsString();
			if (geo_country_geo == null || geo_country_geo.trim().equals("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String geo_index = geoIndexItem.getValueAsString();
			if (geo_index == null || geo_index.trim().equals("")) {
				SC.say("შეიყვანეთ ინდექსი !");
				return;
			}
			String is_center = isRaionCenterItem.getValueAsString();
			if (is_center == null || is_center.trim().equals("")) {
				SC.say("შეიყვანეთ ტიპი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("region_id", region_id);
			record.setAttribute("geo_country_geo", geo_country_geo);
			record.setAttribute("geo_index", geo_index);
			record.setAttribute("is_center", is_center);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("geo_country_id",
						editRecord.getAttributeAsInt("geo_country_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addGeoIndCountry");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateGeoIndCountry");
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
