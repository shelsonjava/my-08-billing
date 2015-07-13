package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import java.util.LinkedHashMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
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
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddTreatments extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem treatmentItem;
	private RadioGroupItem genderItem;

	public DlgAddTreatments() {
		super();
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			final Treatments treatment = serverSession.getTreatment();

			setTitle(treatment == null ? CallCenterBK.constants
					.addAbonentName() : CallCenterBK.constants
					.editAbonentName());

			setHeight(160);
			setWidth(430);
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
			dynamicForm.setTitleWidth(150);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			treatmentItem = new TextItem();
			treatmentItem.setTitle(CallCenterBK.constants.abonentName());
			treatmentItem.setName("phoneItem");
			treatmentItem.setWidth(250);

			genderItem = new RadioGroupItem();
			genderItem.setTitle(CallCenterBK.constants.sex());
			LinkedHashMap<String, String> sexMap = new LinkedHashMap<String, String>();
			sexMap.put("1", CallCenterBK.constants.male());
			sexMap.put("0", CallCenterBK.constants.female());
			genderItem.setValueMap(sexMap);

			if (treatment != null) {
				treatmentItem.setValue(treatment.getTreatment());
				genderItem.setValue(treatment.getGender().toString());
			}

			dynamicForm.setFields(genderItem, treatmentItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
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
					save(treatment);
				}
			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save(Treatments treatmentObj) {
		try {

			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone_number = serverSession.getPhone();
			if (phone_number == null
					|| phone_number.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.phoneIsNotMobile());
				return;
			}
			String gender = genderItem.getValueAsString();
			if (gender == null || gender.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.plzSelectSex());
				return;
			}
			String treatment = treatmentItem.getValueAsString();
			if (treatment == null || treatment.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.plzEnterAbonentName());
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			if (treatmentObj != null) {
				record.setAttribute("treatment_id",
						treatmentObj.getTreatment_id());
			}
			record.setAttribute("phone_number", phone_number);
			record.setAttribute("gender", new Integer(gender));
			record.setAttribute("treatment", treatment);

			DSRequest req = new DSRequest();
			DataSource treatmentsDS = DataSource.get("TreatmentsDS");

			if (treatmentObj == null) {
				req.setAttribute("operationId", "addTreatments");
				treatmentsDS.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse responsRe, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateTreatments");
				treatmentsDS.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse responsRe, Object rawData,
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
