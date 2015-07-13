package com.info08.billing.callcenterbk.client.dialogs.event;

import java.util.ArrayList;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItem;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxRecord;
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
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEventOwner extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem entTypeItem;
	private TextItem entPlaceGeo;
	private ComboBoxItem reservableItem;
	private MyComboBoxItem organizationItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEventOwner(ListGrid listGrid, ListGridRecord pRecord) {
		super();
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "აფიშა-რესურსის დამატება"
				: "აფიშა-რესურსის მოდიფიცირება");

		setHeight(202);
		setWidth(590);
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

		DataSource EventCategoryDS = DataSource.get("EventCategoryDS");
		entTypeItem.setOptionOperationId("searchAllEventCategoryForCB");
		entTypeItem.setOptionDataSource(EventCategoryDS);
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
		entPlaceGeo.setName("event_owner_name");
		entPlaceGeo.setWidth(400);

		reservableItem = new ComboBoxItem();
		reservableItem.setTitle("ჯავშანი");
		reservableItem.setWidth(400);
		reservableItem.setName("reservable");
		reservableItem.setAddUnknownValues(false);
		reservableItem.setDefaultToFirstOption(true);
		reservableItem.setValueMap(ClientMapUtil.getInstance()
				.getReservations());

		dynamicForm.setFields(entTypeItem, entPlaceGeo, reservableItem);

		organizationItem = new MyComboBoxItem("organization_name",
				CallCenterBK.constants.orgName(), 168, 543);
		organizationItem.setMyDlgHeight(400);
		organizationItem.setMyDlgWidth(600);
		DataSource orgDS = DataSource.get("OrgDS");
		organizationItem.setMyDataSource(orgDS);
		organizationItem
				.setMyDataSourceOperation("searchMainOrgsForCBDoubleLike");
		organizationItem.setMyIdField("organization_id");

		ArrayList<MyComboBoxRecord> fieldRecords = new ArrayList<MyComboBoxRecord>();
		MyComboBoxRecord organization_name = new MyComboBoxRecord(
				"organization_name", CallCenterBK.constants.parrentOrgName(),
				true);
		MyComboBoxRecord remark = new MyComboBoxRecord("remark",
				CallCenterBK.constants.comment(), false);
		MyComboBoxRecord full_address_not_hidden = new MyComboBoxRecord(
				"full_address_not_hidden", CallCenterBK.constants.address(),
				true);

		fieldRecords.add(organization_name);
		fieldRecords.add(full_address_not_hidden);
		fieldRecords.add(remark);

		organizationItem.setMyFields(fieldRecords);
		organizationItem.setMyChooserTitle(CallCenterBK.constants
				.organization());
		organizationItem.setNameField("organization_id");
		hLayout.addMember(organizationItem);

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
			Integer event_category_id = editRecord
					.getAttributeAsInt("event_category_id");
			if (event_category_id != null) {
				entTypeItem.setValue(event_category_id);
			}
			String event_owner_name = editRecord
					.getAttributeAsString("event_owner_name");
			if (event_owner_name != null) {
				entPlaceGeo.setValue(event_owner_name);
			}
			Integer reservable = editRecord.getAttributeAsInt("reservable");
			if (reservable != null) {
				reservableItem.setValue(reservable);
			}

			organizationItem.setDataValue(editRecord);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String event_category_id_str = entTypeItem.getValueAsString();
			if (event_category_id_str == null
					|| event_category_id_str.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ კატეგორია");
				return;
			}
			Integer event_category_id = new Integer(event_category_id_str);
			String event_owner_name = entPlaceGeo.getValueAsString();
			String reservable_str = reservableItem.getValueAsString();
			if (reservable_str == null || reservable_str.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ ჯავშანი");
				return;
			}
			Integer reservable = new Integer(reservable_str);
			Integer organization_id = null;
			Record organization_record = organizationItem.getSelectedRecord();
			if (organization_record != null) {
				organization_id = organization_record
						.getAttributeAsInt("organization_id");
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("event_category_id", event_category_id);
			record.setAttribute("event_owner_name", event_owner_name);
			record.setAttribute("reservable", reservable);
			record.setAttribute("organization_id", organization_id);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("event_owner_id",
						editRecord.getAttributeAsInt("event_owner_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addEventOwner");
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
