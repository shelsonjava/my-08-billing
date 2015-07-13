package com.info08.billing.callcenterbk.client.dialogs.address;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditVillageIndexes extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	// form fields
	private ComboBoxItem districtIndexItem;
	private TextItem villageNameItem;
	private TextItem vilageIndexItem;
	private ComboBoxItem districtCenterItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditVillageIndexes(ListGrid listGrid, ListGridRecord pRecord) {
		super();
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

		districtIndexItem = new ComboBoxItem();
		districtIndexItem.setTitle("რაიონი");
		districtIndexItem.setWidth(350);
		districtIndexItem.setName("districtIndexItem");
		districtIndexItem.setFetchMissingValues(true);
		districtIndexItem.setFilterLocally(true);

		villageNameItem = new TextItem();
		villageNameItem.setTitle("დასახელება");
		villageNameItem.setWidth(350);
		villageNameItem.setName("villageNameItem");

		vilageIndexItem = new TextItem();
		vilageIndexItem.setTitle("ინდექსი");
		vilageIndexItem.setWidth(350);
		vilageIndexItem.setName("vilageIndexItem");

		districtCenterItem = new ComboBoxItem();
		districtCenterItem.setTitle("რაიონი");
		districtCenterItem.setWidth(350);
		districtCenterItem.setName("districtCenterItem");
		districtCenterItem.setValueMap(ClientMapUtil.getInstance()
				.getRaionCentTypes());
		districtCenterItem.setDefaultToFirstOption(true);

		dynamicForm.setFields(districtIndexItem, villageNameItem,
				vilageIndexItem, districtCenterItem);

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
			DataSource districtIndexesDS = DataSource.get("DistrictIndexesDS");
			districtIndexItem.setOptionOperationId("searchDistrictIndexes");
			districtIndexItem.setOptionDataSource(districtIndexesDS);
			districtIndexItem.setValueField("district_index_id");
			districtIndexItem.setDisplayField("district_index_name");
			Criteria optionCriteria = new Criteria();
			optionCriteria.setAttribute("DlgAddEditVillageIndexes",
					"DlgAddEditVillageIndexes_" + HTMLPanel.createUniqueId());
			optionCriteria.setAttribute(CommonSingleton.getInstance()
					.getUnixTimeStamp(), CommonSingleton.getInstance()
					.getUnixTimeStamp());
			districtIndexItem.setOptionCriteria(optionCriteria);
			districtIndexItem.setAutoFetchData(true);

			DSRequest requestProperties1 = new DSRequest();
			requestProperties1.setOperationId("searchDistrictIndexes");

			districtIndexItem.fetchData(new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					if (editRecord != null) {
						districtIndexItem.setValue(editRecord
								.getAttributeAsInt("district_index_id"));
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
			villageNameItem.setValue(editRecord
					.getAttributeAsString("village_index_name"));
			vilageIndexItem.setValue(editRecord
					.getAttributeAsString("village_index"));
			districtCenterItem.setValue(editRecord
					.getAttributeAsInt("district_center"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String district_index_id = districtIndexItem.getValueAsString();
			if (district_index_id == null
					|| district_index_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ რაიონი !");
				return;
			}
			String village_index_name = villageNameItem.getValueAsString();
			if (village_index_name == null
					|| village_index_name.trim().equals("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String village_index = vilageIndexItem.getValueAsString();
			if (village_index == null || village_index.trim().equals("")) {
				SC.say("შეიყვანეთ ინდექსი !");
				return;
			}
			String district_center = districtCenterItem.getValueAsString();
			if (district_center == null || district_center.trim().equals("")) {
				SC.say("შეიყვანეთ ტიპი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUserName = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();

			record.setAttribute("district_index_id", district_index_id);
			record.setAttribute("village_index_name", village_index_name);
			record.setAttribute("village_index", village_index);
			record.setAttribute("district_center", district_center);
			record.setAttribute("loggedUserName", loggedUserName);

			if (editRecord != null) {
				record.setAttribute("village_index_id",
						editRecord.getAttributeAsInt("village_index_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addVillageIndexes");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateVillageIndexes");
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
