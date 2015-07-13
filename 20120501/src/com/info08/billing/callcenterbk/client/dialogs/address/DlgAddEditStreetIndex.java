package com.info08.billing.callcenterbk.client.dialogs.address;

import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
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

public class DlgAddEditStreetIndex extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	// form fields
	private ComboBoxItem townsItem;
	private ComboBoxItem streetsItem;
	private TextItem streetIndexRemarkItem;
	private TextItem streetIndexValueItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditStreetIndex(ListGrid listGrid, ListGridRecord pRecord) {
		super();
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "ქუჩის ინდექსის დამატება"
				: "ქუჩის ინდექსის მოდიფიცირება");

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

		townsItem = new ComboBoxItem();
		townsItem.setTitle("ქალაქი");
		townsItem.setName("town_id");
		townsItem.setWidth(350);
		ClientUtils.fillCombo(townsItem, "TownsDS",
				"searchCitiesFromDBForCombos", "town_id", "town_name");
		townsItem.setValue(Constants.defCityTbilisiId);

		streetsItem = new ComboBoxItem();
		streetsItem.setTitle("ქუჩა");
		streetsItem.setWidth(350);
		streetsItem.setName("streets_id");
		streetsItem.setFetchMissingValues(true);
		streetsItem.setFilterLocally(false);
		streetsItem.setAddUnknownValues(false);

		Map<String, Object> aditionalCriteria = new TreeMap<String, Object>();

		aditionalCriteria.put("town_id", Constants.defCityTbilisiId);

		ClientUtils.fillCombo(streetsItem, "StreetsDS",
				"searchStreetFromDBForCombos", "streets_id", "street_name",
				aditionalCriteria);

		streetIndexRemarkItem = new TextItem();
		streetIndexRemarkItem.setTitle("კომენტარი");
		streetIndexRemarkItem.setWidth(350);
		streetIndexRemarkItem.setName("street_index_remark");

		streetIndexValueItem = new TextItem();
		streetIndexValueItem.setTitle("ინდექსი");
		streetIndexValueItem.setWidth(350);
		streetIndexValueItem.setName("street_index_value");

		dynamicForm.setFields(townsItem, streetsItem, streetIndexRemarkItem,
				streetIndexValueItem);

		ClientUtils.makeDependancy(townsItem, "town_id", streetsItem);
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
			dynamicForm.setValues(editRecord.toMap());
			Criteria cr = streetsItem.getOptionCriteria();
			if (cr == null)
				cr = new Criteria();
			cr.setAttribute("town_id", editRecord.getAttribute("town_id"));
			streetsItem.setOptionCriteria(cr);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String streets_id = streetsItem.getValueAsString();
			if (streets_id == null || streets_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ ქუჩა !");
				return;
			}
			String street_index_remark = streetIndexRemarkItem
					.getValueAsString();
			if (street_index_remark == null
					|| street_index_remark.trim().equals("")) {
				SC.say("შეიყვანეთ კომენტარი !");
				return;
			}
			String street_index_value = streetIndexValueItem.getValueAsString();
			if (street_index_value == null
					|| street_index_value.trim().equals("")) {
				SC.say("შეიყვანეთ ინდექსი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("streets_id", streets_id);
			record.setAttribute("street_index_remark", street_index_remark);
			record.setAttribute("street_index_value", street_index_value);

			if (editRecord != null) {
				record.setAttribute("street_index_id",
						editRecord.getAttributeAsInt("street_index_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addStreetIndex");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateStreetIndex");
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
