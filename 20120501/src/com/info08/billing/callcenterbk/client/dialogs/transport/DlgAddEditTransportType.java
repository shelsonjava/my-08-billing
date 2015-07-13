package com.info08.billing.callcenterbk.client.dialogs.transport;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditTransportType extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private TextItem transportTypeNameGeoItem;
	private SelectItem interCityItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditTransportType(ListGrid listGrid, ListGridRecord pRecord) {
		super();
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
		transportTypeNameGeoItem.setName("name_descr");

		interCityItem = new SelectItem();
		interCityItem.setTitle("ტიპი");
		interCityItem.setWidth(300);
		interCityItem.setName("kind");
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
			transportTypeNameGeoItem.setValue(editRecord
					.getAttribute("name_descr"));
			interCityItem.setValue(editRecord.getAttribute("kind"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String name_descr = transportTypeNameGeoItem.getValueAsString();
			if (name_descr == null || name_descr.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String intercity = interCityItem.getValueAsString();
			if (intercity == null || intercity.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ ტიპი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("name_descr", name_descr);
			record.setAttribute("kind", new Integer(intercity));

			if (editRecord != null) {
				record.setAttribute("transp_type_id",
						editRecord.getAttributeAsInt("transp_type_id"));
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
