package com.info08.billing.callcenterbk.client.dialogs.transport;

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

public class DlgAddEditTransportCompany extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem transportCompanyNameGeoItem;
	private ComboBoxItem transportTypeItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditTransportCompany(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "სატრანსპორტო კომპანიის დამატება"
				: "სატრანსპორტო კომპანიის მოდიფიცირება");

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

		transportCompanyNameGeoItem = new TextItem();
		transportCompanyNameGeoItem.setTitle("დასახელება(ქართ.)");
		transportCompanyNameGeoItem.setWidth(300);
		transportCompanyNameGeoItem.setName("transportCompanyNameGeoItem");

		transportTypeItem = new ComboBoxItem();
		transportTypeItem.setTitle("ტრანსპორტის ტიპი");
		transportTypeItem.setWidth(300);
		transportTypeItem.setName("transportTypeItem");
		transportTypeItem.setFetchMissingValues(true);
		transportTypeItem.setFilterLocally(false);
		transportTypeItem.setAddUnknownValues(false);

		DataSource firstNamesDS = DataSource.get("TranspTypeDS");
		transportTypeItem
				.setOptionOperationId("searchAllTransportTypesForCombos");
		transportTypeItem.setOptionDataSource(firstNamesDS);
		transportTypeItem.setValueField("transp_type_id");
		transportTypeItem.setDisplayField("name_descr");

		Criteria criteria = new Criteria();
		transportTypeItem.setOptionCriteria(criteria);
		transportTypeItem.setAutoFetchData(false);

		transportTypeItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = transportTypeItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("transp_type_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("transp_type_id", nullO);
					}
				}
			}
		});

		dynamicForm.setFields(transportCompanyNameGeoItem, transportTypeItem);

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
			transportCompanyNameGeoItem.setValue(editRecord.getAttribute("name_descr"));
			Integer transp_type_id = editRecord.getAttributeAsInt("transp_type_id");
			if (transp_type_id != null) {
				transportTypeItem.setValue(transp_type_id);
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String name_descr = transportCompanyNameGeoItem.getValueAsString();
			if (name_descr == null || name_descr.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ქართული დასახელება !");
				return;
			}
			String transp_type_id = transportTypeItem.getValueAsString();
			if (transp_type_id == null
					|| transp_type_id.trim().equalsIgnoreCase("")) {
				SC.say("აირჩიეთ ტრანსპორტის ტიპი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("name_descr", name_descr);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("transp_type_id", new Integer(transp_type_id));

			if (editRecord != null) {
				record.setAttribute("transp_comp_id",
						editRecord.getAttributeAsInt("transp_comp_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addTransportCompany");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateTransportCompany");
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
