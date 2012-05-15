package com.info08.billing.callcenterbk.client.dialogs.address;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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

public class DlgAddEditGeoIndRegion extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private TextItem geoIndRegNameGeoItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditGeoIndRegion(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "ახალი რაიონის დამატება"
				: "რაიონის მოდიფიცირება");

		setHeight(110);
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

		geoIndRegNameGeoItem = new TextItem();
		geoIndRegNameGeoItem.setTitle("დასახელება (ქართულად)");
		geoIndRegNameGeoItem.setWidth(300);
		geoIndRegNameGeoItem.setName("region_name_geo");

		dynamicForm.setFields(geoIndRegNameGeoItem);

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
			geoIndRegNameGeoItem.setValue(editRecord
					.getAttribute("region_name_geo"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String region_name_geo = geoIndRegNameGeoItem
					.getValueAsString();
			if (region_name_geo == null
					|| region_name_geo.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("region_name_geo", region_name_geo);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("region_id",
						editRecord.getAttributeAsInt("region_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addGeoIndRegion");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateGeoIndRegion");
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
