package com.info08.billing.callcenter.client.dialogs.transport;

import com.info08.billing.callcenter.client.singletons.ClientMapUtil;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditTransportType extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private TextItem transportTypeNameGeoItem;
	private SelectItem interCityItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditTransportType(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "ტრანსპორტის ტიპის დამატება"
				: "ტრანსპორტის ტიპის მოდიფიცირება");

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

		transportTypeNameGeoItem = new TextItem();
		transportTypeNameGeoItem.setTitle("დასახელება (ქართულად)");
		transportTypeNameGeoItem.setWidth(300);
		transportTypeNameGeoItem.setName("transport_type_name_geo");

		interCityItem = new SelectItem();
		interCityItem.setTitle("ტიპი");
		interCityItem.setWidth(300);
		interCityItem.setName("intercity");
		interCityItem.setValueMap(ClientMapUtil.getInstance()
				.getTranspTypeInt());

		dynamicForm.setFields(transportTypeNameGeoItem, interCityItem);

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
			transportTypeNameGeoItem.setValue(editRecord.getAttribute("transport_type_name_geo"));
			interCityItem.setValue(editRecord.getAttribute("intercity"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String transport_type_name_geo = transportTypeNameGeoItem.getValueAsString();
			if (transport_type_name_geo == null || transport_type_name_geo.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String intercity = interCityItem.getValueAsString();
			if (intercity==null ||intercity.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ ტიპი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("transport_type_name_geo", transport_type_name_geo);
			record.setAttribute("deleted", 0);			
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("intercity", new Integer(intercity));

			if (editRecord != null) {
				record.setAttribute("transport_type_id",
						editRecord.getAttributeAsInt("transport_type_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addTransportType");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateTransportType");
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
