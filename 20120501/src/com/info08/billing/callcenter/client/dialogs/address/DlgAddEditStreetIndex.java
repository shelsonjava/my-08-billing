package com.info08.billing.callcenter.client.dialogs.address;

import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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

public class DlgAddEditStreetIndex extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	// form fields
	private ComboBoxItem streetsItem;
	private TextItem streetCommentItem;
	private TextItem streetIndexItem;
	
	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditStreetIndex(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "ქუჩის ინდექსის დამატება"
				: "ქუჩის ინდექსის მოდიფიცირება");

		setHeight(160);
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

		streetsItem = new ComboBoxItem();
		streetsItem.setTitle("ქუჩა");
		streetsItem.setWidth(350);
		streetsItem.setName("street_name_geo");
		streetsItem.setFetchMissingValues(true);
		streetsItem.setFilterLocally(false);
		streetsItem.setAddUnknownValues(false);

		DataSource streetsNewDS = DataSource.get("StreetsNewDS");
		streetsItem.setOptionOperationId("searchStreetFromDBForCombosNoDistrTbil");
		streetsItem.setOptionDataSource(streetsNewDS);
		streetsItem.setValueField("street_id");
		streetsItem.setDisplayField("street_name_geo");

		streetsItem.setOptionCriteria(new Criteria());
		streetsItem.setAutoFetchData(false);

		streetsItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = streetsItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("street_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("street_id", nullO);
					}
				}
			}
		});

		streetCommentItem = new TextItem();
		streetCommentItem.setTitle("კომენტარი");
		streetCommentItem.setWidth(350);
		streetCommentItem.setName("street_comment");

		streetIndexItem = new TextItem();
		streetIndexItem.setTitle("ინდექსი");
		streetIndexItem.setWidth(350);
		streetIndexItem.setName("street_index");

		dynamicForm.setFields(streetsItem, streetCommentItem, streetIndexItem);

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
			streetCommentItem.setValue(editRecord.getAttributeAsString("street_comment"));
			streetIndexItem.setValue(editRecord.getAttributeAsString("street_index"));
			streetsItem.setValue(editRecord.getAttributeAsInt("street_id"));
			
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String street_id = streetsItem.getValueAsString();
			if (street_id == null || street_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ ქუჩა !");
				return;
			}
			String street_comment = streetCommentItem.getValueAsString();
			if (street_comment == null || street_comment.trim().equals("")) {
				SC.say("შეიყვანეთ კომენტარი !");
				return;
			}
			String street_index = streetIndexItem.getValueAsString();
			if (street_index == null || street_index.trim().equals("")) {
				SC.say("შეიყვანეთ ინდექსი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("street_id", street_id);
			record.setAttribute("street_comment", street_comment);
			record.setAttribute("street_index",street_index);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("street_index_id",
						editRecord.getAttributeAsInt("street_index_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addStreetIndex");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateStreetIndex");
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
