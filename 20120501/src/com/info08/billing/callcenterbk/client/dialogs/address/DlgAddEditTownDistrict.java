package com.info08.billing.callcenterbk.client.dialogs.address;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
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

public class DlgAddEditTownDistrict extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem townDistrictNameItem;
	private ComboBoxItem townsItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditTownDistrict(ListGrid listGrid, ListGridRecord pRecord) {
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

		townDistrictNameItem = new TextItem();
		townDistrictNameItem.setTitle("დასახელება");
		townDistrictNameItem.setWidth(300);
		townDistrictNameItem.setName("town_district_name");

		townsItem = new ComboBoxItem();
		townsItem.setTitle("ქალაქი");
		townsItem.setName("town_id");
		townsItem.setWidth(300);
		townsItem.setFetchMissingValues(false);

		dynamicForm.setFields(townDistrictNameItem, townsItem);

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
			DataSource townsDS = DataSource.get("TownsDS");
			if (townsDS != null) {
				townsItem.setOptionOperationId("searchFromDB");
				townsItem.setOptionDataSource(townsDS);
				townsItem.setValueField("town_id");
				townsItem.setDisplayField("town_name");
				townsItem.setAutoFetchData(true);
				townsItem.fetchData(new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						if (editRecord != null) {
							Integer town_id = editRecord
									.getAttributeAsInt("town_id");
							if (town_id != null) {
								townsItem.setValue(town_id);
							} else {
								townsItem.setValue(Constants.defCityTbilisiId);
							}
						} else {
							townsItem.setValue(Constants.defCityTbilisiId);
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
			townDistrictNameItem.setValue(editRecord
					.getAttribute("town_district_name"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String town_district_name_param = townDistrictNameItem
					.getValueAsString();
			if (town_district_name_param == null
					|| town_district_name_param.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			ListGridRecord town_record = townsItem.getSelectedRecord();
			if (town_record == null) {
				SC.say("გთხოვთ აირჩიოთ ქალაქი !");
				return;
			}
			Integer town_id = town_record.getAttributeAsInt("town_id");
			if (town_id == null) {
				SC.say("გთხოვთ აირჩიოთ ქალაქი - 1 !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("town_district_name", town_district_name_param);
			record.setAttribute("town_id", town_id);

			if (editRecord != null) {
				record.setAttribute("town_district_id",
						editRecord.getAttributeAsInt("town_district_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addTownDistrict");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateTownDistrict");
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
