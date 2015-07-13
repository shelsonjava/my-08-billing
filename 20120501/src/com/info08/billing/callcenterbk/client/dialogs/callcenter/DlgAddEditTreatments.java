package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.entity.callcenter.Treatments;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditTreatments extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem treatmentItem;
	private TextItem phoneNumberItem;
	private SelectItem genderItem;
	private SelectItem visibleItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	private Treatments treatmentCallCenter;

	public DlgAddEditTreatments(ListGrid listGrid, ListGridRecord pRecord) {
		this(listGrid, pRecord, null);
	}

	public DlgAddEditTreatments(ListGrid listGrid, ListGridRecord pRecord,
			Treatments treatment) {
		super();
		this.editRecord = pRecord;
		this.listGrid = listGrid;
		this.treatmentCallCenter = treatment;

		setTitle(pRecord == null ? "მიმართვის დამატება"
				: "მიმართვის მოდიფიცირება");

		setHeight(200);
		setWidth(390);
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
		dynamicForm.setTitleWidth(220);
		dynamicForm.setNumCols(1);
		hLayout.addMember(dynamicForm);

		phoneNumberItem = new TextItem();
		phoneNumberItem.setTitle("ნომერი");
		phoneNumberItem.setWidth(250);
		phoneNumberItem.setName("phoneNumberItem");

		treatmentItem = new TextItem();
		treatmentItem.setTitle("მიმართვა");
		treatmentItem.setWidth(250);
		treatmentItem.setName("treatmentItem");

		genderItem = new SelectItem();
		genderItem.setTitle("სქესი");
		genderItem.setWidth(250);
		genderItem.setName("genderItem");
		genderItem.setFetchMissingValues(true);
		genderItem.setFilterLocally(false);
		genderItem.setAddUnknownValues(false);

		genderItem.setValueMap(ClientMapUtil.getInstance().getGender());
		genderItem.setDefaultToFirstOption(true);

		visibleItem = new SelectItem();
		visibleItem.setTitle("მდგომარეობა");
		visibleItem.setWidth(250);
		visibleItem.setName("visibleItem");
		visibleItem.setFetchMissingValues(true);
		visibleItem.setFilterLocally(false);
		visibleItem.setAddUnknownValues(false);

		visibleItem.setValueMap(ClientMapUtil.getInstance().getVisible());
		visibleItem.setDefaultToFirstOption(true);

		if (treatmentCallCenter != null) {
			phoneNumberItem.setValue(treatmentCallCenter.getPhone_number());
			phoneNumberItem.setDisabled(true);
			treatmentItem.setValue(treatmentCallCenter.getTreatment());
			genderItem.setValue(treatmentCallCenter.getGender());
			visibleItem.setValue(treatmentCallCenter.getVisible());
			visibleItem.setDisabled(true);
		}

		dynamicForm.setFields(phoneNumberItem, treatmentItem, genderItem,
				visibleItem);

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
			phoneNumberItem.setValue(editRecord.getAttribute("phone_number"));
			treatmentItem.setValue(editRecord.getAttribute("treatment"));
			genderItem.setValue(editRecord.getAttribute("gender"));
			visibleItem.setValue(editRecord.getAttribute("visible"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String phone_number = phoneNumberItem.getValueAsString();
			if (phone_number == null
					|| phone_number.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ ნომერი !");
				return;
			}

			String treatment = treatmentItem.getValueAsString();
			if (treatment == null || treatment.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ მიმართვა !");
				return;
			}

			String gender = genderItem.getValueAsString();
			if (gender == null) {
				SC.say("შეიყვანეთ სქესი !");
				return;
			}

			String visible = visibleItem.getValueAsString();
			if (visible == null || visible.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ მდგომარეობა !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("phone_number", phone_number);
			record.setAttribute("treatment", treatment);
			record.setAttribute("gender", gender);
			record.setAttribute("visible", visible);

			if (editRecord != null) {
				record.setAttribute("treatment_id",
						editRecord.getAttributeAsInt("treatment_id"));
			}
			if (treatmentCallCenter != null
					&& treatmentCallCenter.getTreatment_id() != null) {
				record.setAttribute("treatment_id",
						treatmentCallCenter.getTreatment_id());
			}

			DSRequest req = new DSRequest();

			if (treatmentCallCenter != null
					&& treatmentCallCenter.getTreatment_id() == null
					&& editRecord == null) {
				DataSource dataSource = DataSource
						.getDataSource("TreatmentsDS");
				req.setAttribute("operationId", "addTreatment");
				dataSource.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else if (treatmentCallCenter != null
					&& treatmentCallCenter.getTreatment_id() != null
					&& editRecord == null) {
				DataSource dataSource = DataSource
						.getDataSource("TreatmentsDS");
				// record.setAttribute("treatment_id",
				// treatmentCallCenter.getTreatment_id());
				req.setAttribute("operationId", "updateTreatments");
				dataSource.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {

				if (editRecord == null) {
					req.setAttribute("operationId", "addTreatment");
					listGrid.addData(record, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							destroy();
						}
					}, req);
				} else {
					req.setAttribute("operationId", "updateTreatments");
					listGrid.updateData(record, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							destroy();
						}
					}, req);
				}
			}
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
