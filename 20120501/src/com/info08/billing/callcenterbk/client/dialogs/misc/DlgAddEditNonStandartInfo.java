package com.info08.billing.callcenterbk.client.dialogs.misc;

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

public class DlgAddEditNonStandartInfo extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem infoGroupNameItem;
	private TextItem infoNameItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditNonStandartInfo(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "არასტანდარტული ინფორმაციის დამატება"
				: "არასტანდარტული ინფორმაციის მოდიფიცირება");

		setHeight(140);
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

		infoGroupNameItem = new ComboBoxItem();
		infoGroupNameItem.setTitle("ჯგუფი");
		infoGroupNameItem.setWidth(400);
		infoGroupNameItem.setName("info_group_id");
		infoGroupNameItem.setFetchMissingValues(true);
		infoGroupNameItem.setFilterLocally(false);
		infoGroupNameItem.setAddUnknownValues(false);

		DataSource NoneStandartInfoGroupsDS = DataSource
				.get("NoneStandartInfoGroupsDS");
		infoGroupNameItem
				.setOptionOperationId("searchAllNoneStandartInfoGroupsForCB");
		infoGroupNameItem.setOptionDataSource(NoneStandartInfoGroupsDS);
		infoGroupNameItem.setValueField("info_group_id");
		infoGroupNameItem.setDisplayField("info_group_name");

		Criteria criteria = new Criteria();

		infoGroupNameItem.setOptionCriteria(criteria);
		infoGroupNameItem.setAutoFetchData(false);

		infoGroupNameItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = infoGroupNameItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("info_group_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("info_group_id", nullO);
					}
				}
			}
		});

		infoNameItem = new TextItem();
		infoNameItem.setTitle("აღწერა");
		infoNameItem.setName("infoNameItem");
		infoNameItem.setWidth(400);

		dynamicForm.setFields(infoGroupNameItem, infoNameItem);

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

			Integer info_group_id = editRecord
					.getAttributeAsInt("info_group_id");
			if (info_group_id != null) {
				infoGroupNameItem.setValue(info_group_id);
			}

			String info_name = editRecord.getAttributeAsString("info_name");
			if (info_name != null) {
				infoNameItem.setValue(info_name);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String info_group = infoGroupNameItem.getValueAsString();
			if (info_group == null || info_group.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ კატეგორია");
				return;
			}
			Integer info_group_id = new Integer(info_group);

			String info_name = infoNameItem.getValueAsString();

			if (info_name == null || info_name.trim().equals("")) {
				SC.say("გთხოვთ შეიყვანოთ მიოსამართი");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("info_group_id", info_group_id);
			record.setAttribute("info_name", info_name);

			if (editRecord != null) {
				record.setAttribute("info_id",
						editRecord.getAttributeAsInt("info_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addNoneStandartInfo");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateNoneStandartInfo");
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
