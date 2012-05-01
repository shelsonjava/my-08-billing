package com.info08.billing.callcenter.client.dialogs.address;

import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditStreetDescr extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private TextItem streetDescrNameGeoItem;
	private TextItem streetDescrNameEngItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditStreetDescr(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "ახალი ქუჩის დასახელების დამატება"
				: "ქუჩის დასახელების მოდიფიცირება");

		setHeight(140);
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

		streetDescrNameGeoItem = new TextItem();
		streetDescrNameGeoItem.setTitle("დასახელება (ქართულად)");
		streetDescrNameGeoItem.setWidth(300);
		streetDescrNameGeoItem.setName("street_descr_name_geo");

		streetDescrNameEngItem = new TextItem();
		streetDescrNameEngItem.setTitle("დასახელება (ინგლისურად)");
		streetDescrNameEngItem.setWidth(300);
		streetDescrNameEngItem.setName("street_descr_name_eng");

		dynamicForm.setFields(streetDescrNameGeoItem, streetDescrNameEngItem);

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
			streetDescrNameGeoItem.setValue(editRecord
					.getAttribute("street_descr_name_geo"));
			streetDescrNameEngItem.setValue(editRecord
					.getAttribute("street_descr_name_eng"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String street_descr_name_geo = streetDescrNameGeoItem
					.getValueAsString();
			if (street_descr_name_geo == null
					|| street_descr_name_geo.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String street_descr_name_eng = streetDescrNameEngItem
					.getValueAsString();
			if (street_descr_name_geo.length() > 155) {
				SC.say("ქართული დასახელება შედგება მაქსიმუმ 155 სიმბოლოსაგან !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("street_descr_name_geo", street_descr_name_geo);
			record.setAttribute("street_descr_name_eng", street_descr_name_eng);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("street_descr_id",
						editRecord.getAttributeAsInt("street_descr_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addStreetDescr");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateStreetDescr");
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
