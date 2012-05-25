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

public class DlgAddEditDistBetweenTowns extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem townStartItem;
	private ComboBoxItem townEndItem;
	private ComboBoxItem distanceTypeItem;
	private TextItem distanceItem;
	private TextAreaItem distBetweenTownsRemarkItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditDistBetweenTowns(ListGrid listGrid, ListGridRecord pRecord) {
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

		townStartItem = new ComboBoxItem();
		townStartItem.setTitle("საწყისი ქალაქი");
		townStartItem.setWidth(300);
		townStartItem.setName("town_id_start");
		townStartItem.setFetchMissingValues(true);
		townStartItem.setFilterLocally(false);
		townStartItem.setAddUnknownValues(false);

		DataSource townsDS = DataSource.get("TownsDS");
		townStartItem.setOptionOperationId("searchCitiesFromDBForCombos1");
		townStartItem.setOptionDataSource(townsDS);
		townStartItem.setValueField("town_id");
		townStartItem.setDisplayField("town_name");

		Criteria criteriaCity = new Criteria();
		townStartItem.setOptionCriteria(criteriaCity);
		townStartItem.setAutoFetchData(false);

		townStartItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = townStartItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("town_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("town_id", nullO);
					}
				}
			}
		});

		townEndItem = new ComboBoxItem();
		townEndItem.setTitle("საბოლოო ქალაქი");
		townEndItem.setWidth(300);
		townEndItem.setName("town_id_end");
		townEndItem.setFetchMissingValues(true);
		townEndItem.setFilterLocally(false);
		townEndItem.setAddUnknownValues(false);

		DataSource townsDS1 = DataSource.get("TownsDS");
		townEndItem.setOptionOperationId("searchCitiesFromDBForCombos1");
		townEndItem.setOptionDataSource(townsDS1);
		townEndItem.setValueField("town_id");
		townEndItem.setDisplayField("town_name");

		Criteria criteriaCity1 = new Criteria();
		townEndItem.setOptionCriteria(criteriaCity1);
		townEndItem.setAutoFetchData(false);

		townEndItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = townEndItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("town_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("town_id", nullO);
					}
				}
			}
		});

		distanceTypeItem = new ComboBoxItem();
		distanceTypeItem.setTitle("მანძილის ტიპი");
		distanceTypeItem.setName("town_id");
		distanceTypeItem.setWidth(300);
		distanceTypeItem.setValueMap(SharedUtils.getInstance()
				.getMapDistanceTypes());
		distanceTypeItem.setDefaultToFirstOption(true);

		distanceItem = new TextItem();
		distanceItem.setTitle("მანძილი (კმ.)");
		distanceItem.setWidth(300);
		distanceItem.setName("dist_between_towns_value");

		distBetweenTownsRemarkItem = new TextAreaItem();
		distBetweenTownsRemarkItem.setTitle("კომენტარი");
		distBetweenTownsRemarkItem.setWidth(300);
		distBetweenTownsRemarkItem.setHeight(50);
		distBetweenTownsRemarkItem.setName("dist_between_towns_remark");

		dynamicForm.setFields(townStartItem, townEndItem, distanceTypeItem,
				distanceItem, distBetweenTownsRemarkItem);

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
			townStartItem.setValue(editRecord
					.getAttributeAsInt("town_id_start"));
			townEndItem.setValue(editRecord.getAttributeAsInt("town_id_end"));
			distanceTypeItem.setValue(editRecord
					.getAttributeAsInt("town_distance_type"));
			distanceItem.setValue(editRecord
					.getAttributeAsString("dist_between_towns_value"));
			distBetweenTownsRemarkItem.setValue(editRecord
					.getAttribute("dist_between_towns_remark"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String town_id_start = townStartItem.getValueAsString();
			if (town_id_start == null) {
				SC.say("აირჩიეთ საწყისი ქალაქი !");
				return;
			}
			String town_id_end = townEndItem.getValueAsString();
			if (town_id_end == null) {
				SC.say("აირჩიეთ საბოლოო ქალაქი !");
				return;
			}
			String dist_type_record = distanceTypeItem.getValueAsString();
			if (dist_type_record == null) {
				SC.say("აირჩიეთ მანძილის ტიპი !");
				return;
			}
			String dist_between_towns_value = distanceItem.getValueAsString();
			String dist_between_towns_remark = distBetweenTownsRemarkItem
					.getValueAsString();

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("dist_between_towns_value",
					dist_between_towns_value);
			record.setAttribute("town_distance_type", new Integer(
					distanceTypeItem.getValueAsString()));
			record.setAttribute("town_id_end", town_id_end);
			record.setAttribute("town_id_start", town_id_start);
			record.setAttribute("dist_between_towns_remark",
					dist_between_towns_remark);

			if (editRecord != null) {
				record.setAttribute("dist_between_towns_id",
						editRecord.getAttributeAsInt("dist_between_towns_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addDistBetweenTowns");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateDistBetweenTowns");
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
