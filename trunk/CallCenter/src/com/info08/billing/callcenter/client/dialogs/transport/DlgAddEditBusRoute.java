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
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditBusRoute extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem routeNMItem;
	private TextItem routeOldNMItem;
	private ComboBoxItem roundTypeItem;
	private ComboBoxItem transpTypeItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditBusRoute(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "ქალაქის მიკრო/ავტ. დამატება"
				: "ქალაქის მიკრო/ავტ. მოდიფიცირება");

		setHeight(190);
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

		routeNMItem = new TextItem();
		routeNMItem.setTitle("მარშუტის ნომერი");
		routeNMItem.setWidth(300);
		routeNMItem.setName("route_nm");

		routeOldNMItem = new TextItem();
		routeOldNMItem.setTitle("მარშუტის ძველი ნომერი");
		routeOldNMItem.setWidth(300);
		routeOldNMItem.setName("route_old_nm");

		roundTypeItem = new ComboBoxItem();
		roundTypeItem.setTitle("წრიული/ჩვეულებრივი");
		roundTypeItem.setWidth(300);
		roundTypeItem.setName("round_id");
		roundTypeItem.setValueMap(ClientMapUtil.getInstance()
				.getTranspRoundType());

		transpTypeItem = new ComboBoxItem();
		transpTypeItem.setTitle("სახეობა");
		transpTypeItem.setWidth(300);
		transpTypeItem.setName("service_id");
		transpTypeItem.setValueMap(ClientMapUtil.getInstance()
				.getTranspTypeCustom());

		dynamicForm.setFields(routeNMItem, routeOldNMItem, roundTypeItem,
				transpTypeItem);

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
			routeNMItem.setValue(editRecord.getAttribute("route_nm"));
			routeOldNMItem.setValue(editRecord.getAttribute("route_old_nm"));
			Integer round_id = editRecord.getAttributeAsInt("round_id");
			if (round_id != null) {
				roundTypeItem.setValue(round_id);
			}
			Integer service_id = editRecord.getAttributeAsInt("service_id");
			if (service_id != null) {
				transpTypeItem.setValue(service_id);
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String route_nm = routeNMItem.getValueAsString();
			if (route_nm == null || route_nm.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ მარშუტის ნომერი !");
				return;
			}
			String route_old_nm = routeOldNMItem.getValueAsString();

			String round_id = roundTypeItem.getValueAsString();
			if (round_id == null || round_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ წრიული/ჩვეულებრივი !");
				return;
			}
			String service_id = transpTypeItem.getValueAsString();
			if (service_id == null || service_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ სახეობა !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("route_nm", route_nm);
			record.setAttribute("route_old_nm", route_old_nm);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("round_id", new Integer(round_id));
			record.setAttribute("service_id", new Integer(service_id));

			if (editRecord != null) {
				record.setAttribute("route_id",
						editRecord.getAttributeAsInt("route_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addBusRoute");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateBusRoute");
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
