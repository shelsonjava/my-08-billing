package com.info08.billing.callcenterbk.client.dialogs.transport;

import java.util.Map;

import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
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
	private SelectItem remarkTypeItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditBusRoute(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "ქალაქის მიკრო/ავტ. დამატება"
				: "ქალაქის მიკრო/ავტ. მოდიფიცირება");

		setHeight(220);
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
		routeNMItem.setName("dir_num");

		routeOldNMItem = new TextItem();
		routeOldNMItem.setTitle("მარშუტის ძველი ნომერი");
		routeOldNMItem.setWidth(300);
		routeOldNMItem.setName("dir_old_num");

		roundTypeItem = new ComboBoxItem();
		roundTypeItem.setTitle("წრიული/ჩვეულებრივი");
		roundTypeItem.setWidth(300);
		roundTypeItem.setName("cycled_id");
		roundTypeItem.setValueMap(ClientMapUtil.getInstance()
				.getTranspRoundType());

		transpTypeItem = new ComboBoxItem();
		transpTypeItem.setTitle("სახეობა");
		transpTypeItem.setWidth(300);
		transpTypeItem.setName("service_id");
		transpTypeItem.setValueMap(ClientMapUtil.getInstance()
				.getTranspTypeCustom());

		remarkTypeItem = new SelectItem();
		remarkTypeItem.setTitle("კომენტარი");
		remarkTypeItem.setWidth(300);
		remarkTypeItem.setName("remark_type");

		ClientUtils.fillDescriptionCombo(remarkTypeItem, 63000);
		remarkTypeItem.setValue(63100);

		dynamicForm.setFields(routeNMItem, routeOldNMItem, roundTypeItem,
				transpTypeItem, remarkTypeItem);

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

	@SuppressWarnings("rawtypes")
	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			Map mp = editRecord.toMap();
			dynamicForm.setValues(mp);
			// routeNMItem.setValue(editRecord.getAttribute("dir_num"));
			// routeOldNMItem.setValue(editRecord.getAttribute("dir_old_num"));
			// Integer cycled_id = editRecord.getAttributeAsInt("cycled_id");
			// if (cycled_id != null) {
			// roundTypeItem.setValue(cycled_id);
			// }
			// Integer service_id = editRecord.getAttributeAsInt("service_id");
			// if (service_id != null) {
			// transpTypeItem.setValue(service_id);
			// }
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String dir_num = routeNMItem.getValueAsString();
			if (dir_num == null || dir_num.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ მარშუტის ნომერი !");
				return;
			}
			String dir_old_num = routeOldNMItem.getValueAsString();

			String cycled_id = roundTypeItem.getValueAsString();
			if (cycled_id == null || cycled_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ წრიული/ჩვეულებრივი !");
				return;
			}
			String service_id = transpTypeItem.getValueAsString();
			if (service_id == null || service_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ სახეობა !");
				return;
			}

			String remark_type = remarkTypeItem.getValueAsString();
			if (remark_type == null || remark_type.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ კომენტარი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("dir_num", dir_num.trim());
			record.setAttribute("dir_old_num",
					dir_old_num != null ? dir_old_num.trim() : null);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("cycled_id", new Integer(cycled_id));
			record.setAttribute("service_id", new Integer(service_id));
			record.setAttribute("remark_type", new Integer(remark_type));

			if (editRecord != null) {
				record.setAttribute("pt_id",
						editRecord.getAttributeAsInt("pt_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addPublicTransportDirections");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId",
						"updatePublicTransportDirections");
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
