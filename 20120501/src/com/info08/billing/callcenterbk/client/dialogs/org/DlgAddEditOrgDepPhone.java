package com.info08.billing.callcenterbk.client.dialogs.org;

import java.util.Map;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditOrgDepPhone extends Window {

	// main layout
	private VLayout hLayout;

	private DynamicForm dynamicForm;
	private TextItem phoneItem;
	private SelectItem phoneStateItem;
	private SelectItem hidByReqItem;
	private SelectItem contrTypeItem;
	private SelectItem isContactItem;
	private SelectItem phoneTypeItem;
	private SelectItem isParallelItem;

	private ListGridRecord listGridRecord;
	private ListGrid phoneListGrid;
	private boolean saveOnClientSide;
	private Record departRecord;

	public DlgAddEditOrgDepPhone(boolean saveOnClientSide,
			ListGridRecord listGridRecord, ListGrid phoneListGrid,
			Record departRecord) {
		try {
			this.listGridRecord = listGridRecord;
			this.phoneListGrid = phoneListGrid;
			this.saveOnClientSide = saveOnClientSide;
			this.departRecord = departRecord;
			setTitle(listGridRecord == null ? CallCenterBK.constants
					.addOrgDepartmentPhone() : CallCenterBK.constants
					.editOrgDepartmentPhone());

			setHeight(120);
			setWidth(953);
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
			dynamicForm.setNumCols(7);
			dynamicForm.setTitleOrientation(TitleOrientation.TOP);
			hLayout.addMember(dynamicForm);

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setWidth(200);
			phoneItem.setName("phone");

			phoneStateItem = new SelectItem();
			phoneStateItem.setTitle(CallCenterBK.constants.phoneState());
			phoneStateItem.setWidth(120);
			phoneStateItem.setName("phone_state_id");
			ClientUtils.fillDescriptionCombo(phoneStateItem, 52000);

			hidByReqItem = new SelectItem();
			hidByReqItem.setTitle(CallCenterBK.constants.openClose());
			hidByReqItem.setWidth(120);
			hidByReqItem.setName("hidden_by_request");
			hidByReqItem.setValueMap(ClientMapUtil.getInstance()
					.getMapOpClose());
			hidByReqItem.setDefaultToFirstOption(true);

			contrTypeItem = new SelectItem();
			contrTypeItem.setTitle(CallCenterBK.constants.phoneStatus());
			contrTypeItem.setWidth(120);
			contrTypeItem.setName("phone_contract_type");
			ClientUtils.fillDescriptionCombo(contrTypeItem, 53000);

			isContactItem = new SelectItem();
			isContactItem.setTitle(CallCenterBK.constants.contactPhone());
			isContactItem.setWidth(120);
			isContactItem.setName("for_contact");
			isContactItem
					.setValueMap(ClientMapUtil.getInstance().getYesAndNo());
			isContactItem.setDefaultToFirstOption(true);

			phoneTypeItem = new SelectItem();
			phoneTypeItem.setTitle(CallCenterBK.constants.type());
			phoneTypeItem.setWidth(120);
			phoneTypeItem.setName("phone_type_id");
			ClientUtils.fillDescriptionCombo(phoneTypeItem, 54000);

			isParallelItem = new SelectItem();
			isParallelItem.setTitle(CallCenterBK.constants.paraller());
			isParallelItem.setWidth(120);
			isParallelItem.setName("is_parallel");
			isParallelItem.setValueMap(ClientMapUtil.getInstance()
					.getMapParall());
			isParallelItem.setDefaultToFirstOption(true);

			if (listGridRecord == null) {
				phoneStateItem.setValue(new Integer(52100));
				contrTypeItem.setValue(new Integer(53102));
				phoneTypeItem.setValue(new Integer(54100));
			}

			dynamicForm
					.setFields(phoneItem, phoneStateItem, hidByReqItem,
							contrTypeItem, isContactItem, phoneTypeItem,
							isParallelItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);
			hLayoutItem.setPadding(2);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.save());
			saveItem.setWidth(100);

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
					saveData();
				}
			});

			phoneItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						saveData();
					}
				}
			});

			addItem(hLayout);
			fillData();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillData() {
		try {
			if (listGridRecord == null) {
				return;
			}
			phoneItem.setValue(listGridRecord.getAttributeAsString("phone"));
			phoneStateItem.setValue(listGridRecord
					.getAttributeAsInt("phone_state_id"));
			hidByReqItem.setValue(listGridRecord
					.getAttributeAsInt("hidden_by_request"));
			contrTypeItem.setValue(listGridRecord
					.getAttributeAsInt("phone_contract_type"));
			isContactItem.setValue(listGridRecord
					.getAttributeAsInt("for_contact"));
			phoneTypeItem.setValue(listGridRecord
					.getAttributeAsInt("phone_type_id"));
			isParallelItem.setValue(listGridRecord
					.getAttributeAsInt("is_parallel"));
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void saveData() {
		try {
			String phone = phoneItem.getValueAsString();
			String unique_id = (listGridRecord == null) ? null : listGridRecord
					.getAttribute("org_dep_to_ph_id");
			if (phone == null || phone.trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.invalidPhone());
				return;
			}
			try {
				Integer.parseInt(phone);
			} catch (NumberFormatException e) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.invalidPhone());
				return;
			}
			ListGridRecord phones[] = phoneListGrid.getRecords();
			if (phones != null && phones.length > 0) {
				for (ListGridRecord listGridRecordItem : phones) {
					String phoneItem = listGridRecordItem
							.getAttributeAsString("phone");
					if (phoneItem == null || phoneItem.trim().equals("")) {
						continue;
					}
					String cur_id = listGridRecordItem
							.getAttribute("org_dep_to_ph_id");

					if (phoneItem.equals(phone) && unique_id == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.phoneAlreadyExists());
						return;
					}
					if (phoneItem.equals(phone) && unique_id != null
							&& !unique_id.equals(cur_id)) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.phoneAlreadyExists());
						return;
					}
				}
			}
			boolean isNew = false;
			if (isNew = (listGridRecord == null)) {
				listGridRecord = new ListGridRecord();
			}
			listGridRecord.setAttribute("hidden_by_request",
					Integer.parseInt(hidByReqItem.getValueAsString()));
			listGridRecord.setAttribute("phone_contract_type",
					Integer.parseInt(contrTypeItem.getValueAsString()));
			listGridRecord.setAttribute("for_contact",
					Integer.parseInt(isContactItem.getValueAsString()));
			listGridRecord.setAttribute("phone_state_id",
					Integer.parseInt(phoneStateItem.getValueAsString()));
			listGridRecord.setAttribute("phone_type_id",
					Integer.parseInt(phoneTypeItem.getValueAsString()));
			listGridRecord.setAttribute("is_parallel",
					Integer.parseInt(isParallelItem.getValueAsString()));
			listGridRecord.setAttribute("phone", phone);
			listGridRecord.setAttribute("hidden_by_request_descr",
					hidByReqItem.getDisplayValue());
			listGridRecord.setAttribute("phone_contract_type_descr",
					contrTypeItem.getDisplayValue());
			listGridRecord.setAttribute("for_contact_descr",
					isContactItem.getDisplayValue());
			listGridRecord.setAttribute("phone_state_descr",
					phoneStateItem.getDisplayValue());
			listGridRecord.setAttribute("phone_type_descr",
					phoneTypeItem.getDisplayValue());
			listGridRecord.setAttribute("is_parallel_descr",
					isParallelItem.getDisplayValue());
			if (saveOnClientSide) {
				if (isNew) {
					listGridRecord.setAttribute("org_dep_to_ph_id",
							(HTMLPanel.createUniqueId()));
					phoneListGrid.addData(listGridRecord);
				} else {
					phoneListGrid.updateData(listGridRecord);
				}
			} else {
				com.smartgwt.client.rpc.RPCManager.startQueue();
				Map<?, ?> map = listGridRecord.toMap();
				map.remove("__ref");
				map.remove("_ref");
				Record record = new Record();
				record.setAttribute("loggedUserName", CommonSingleton
						.getInstance().getSessionPerson().getUser_name());
				Integer phone_order = record.getAttributeAsInt("phone_order");
				if (phone_order == null) {
					phone_order = new Integer(0);
				}
				record.setAttribute("phone_order", phone_order);
				record.setAttribute("org_department_id",
						departRecord.getAttributeAsInt("org_department_id"));
				DSRequest req = new DSRequest();
				if (listGridRecord == null) {
					req.setAttribute("operationId", "addOrgDepPhone");
					phoneListGrid.addData(record, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							destroy();
						}
					}, req);
				} else {
					req.setAttribute("operationId", "updateOrgDepPhone");
					phoneListGrid.updateData(record, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							destroy();
						}
					}, req);
				}

				com.smartgwt.client.rpc.RPCManager.sendQueue();
			}
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
