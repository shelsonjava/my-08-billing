package com.info08.billing.callcenterbk.client.dialogs.ent;

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

public class DlgAddEditEndPlace extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem entTypeItem;
	private TextItem entPlaceGeo;
	private ComboBoxItem reservationItem;
	private ComboBoxItem mainOrgsItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditEndPlace(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "აფიშა-რესურსის დამატება"
				: "აფიშა-რესურსის მოდიფიცირება");

		setHeight(180);
		setWidth(580);
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
		dynamicForm.setTitleWidth(180);
		dynamicForm.setNumCols(2);
		hLayout.addMember(dynamicForm);

		entTypeItem = new ComboBoxItem();
		entTypeItem.setTitle("აფიშა-კატეგორია");
		entTypeItem.setWidth(400);
		entTypeItem.setName("event_category_id");
		entTypeItem.setFetchMissingValues(true);
		entTypeItem.setFilterLocally(false);
		entTypeItem.setAddUnknownValues(false);

		DataSource entTypeDS = DataSource.get("EntTypeDS");
		entTypeItem.setOptionOperationId("searchAllEventCategoryForCB");
		entTypeItem.setOptionDataSource(entTypeDS);
		entTypeItem.setValueField("event_category_id");
		entTypeItem.setDisplayField("event_category_name");

		Criteria criteria = new Criteria();

		entTypeItem.setOptionCriteria(criteria);
		entTypeItem.setAutoFetchData(false);

		entTypeItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = entTypeItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("event_category_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("event_category_id", nullO);
					}
				}
			}
		});

		entPlaceGeo = new TextItem();
		entPlaceGeo.setTitle("რესურსის დასახელება");
		entPlaceGeo.setName("ent_place_geo");
		entPlaceGeo.setWidth(400);

		reservationItem = new ComboBoxItem();
		reservationItem.setTitle("ჯავშანი");
		reservationItem.setWidth(400);
		reservationItem.setName("reservation");
		reservationItem.setAddUnknownValues(false);
		reservationItem.setDefaultToFirstOption(true);
		reservationItem.setValueMap(ClientMapUtil.getInstance()
				.getReservations());

		mainOrgsItem = new ComboBoxItem();
		mainOrgsItem.setTitle("ორგანიზაცია");
		mainOrgsItem.setWidth(400);
		mainOrgsItem.setName("event_category_name");
		mainOrgsItem.setFetchMissingValues(true);
		mainOrgsItem.setFilterLocally(false);
		mainOrgsItem.setAddUnknownValues(false);

		DataSource entPlaceDS = DataSource.get("EntPlaceDS");
		mainOrgsItem.setOptionOperationId("searchMainOrgsForCB");
		mainOrgsItem.setOptionDataSource(entPlaceDS);
		mainOrgsItem.setValueField("main_id");
		mainOrgsItem.setDisplayField("org_name");

		mainOrgsItem.setOptionCriteria(new Criteria());
		mainOrgsItem.setAutoFetchData(false);

		mainOrgsItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = mainOrgsItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("main_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("main_id", nullO);
					}
				}
			}
		});

		dynamicForm.setFields(entTypeItem, entPlaceGeo, reservationItem,
				mainOrgsItem);

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
			Integer event_category_id = editRecord.getAttributeAsInt("event_category_id");
			if (event_category_id != null) {
				entTypeItem.setValue(event_category_id);
			}
			String ent_place_geo = editRecord
					.getAttributeAsString("ent_place_geo");
			if (ent_place_geo != null) {
				entPlaceGeo.setValue(ent_place_geo);
			}
			Integer reservation = editRecord.getAttributeAsInt("reservation");
			if (reservation != null) {
				reservationItem.setValue(reservation);
			}
			Integer main_id = editRecord.getAttributeAsInt("main_id");
			if (main_id != null) {
				mainOrgsItem.setValue(main_id);
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String event_category_id_str = entTypeItem.getValueAsString();
			if (event_category_id_str == null || event_category_id_str.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ კატეგორია");
				return;
			}
			Integer event_category_id = new Integer(event_category_id_str);
			String ent_place_geo = entPlaceGeo.getValueAsString();
			String reservation_str = reservationItem.getValueAsString();
			if (reservation_str == null || reservation_str.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ ჯავშანი");
				return;
			}
			Integer reservation = new Integer(reservation_str);
			Integer main_id = null;
			String main_id_str = mainOrgsItem.getValueAsString();
			if (main_id_str != null && !main_id_str.trim().equals("")) {
				main_id = new Integer(main_id_str);
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("event_category_id", event_category_id);
			record.setAttribute("ent_place_geo", ent_place_geo);
			record.setAttribute("reservation", reservation);
			record.setAttribute("main_id", main_id);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("event_owner_id",
						editRecord.getAttributeAsInt("event_owner_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addEntPlace");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateEventOwner");
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
