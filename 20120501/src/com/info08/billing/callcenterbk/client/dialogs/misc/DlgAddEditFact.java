package com.info08.billing.callcenterbk.client.dialogs.misc;

import java.util.Date;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.CallCenterBK;
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
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditFact extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private SelectItem factStatusItem;
	private SelectItem factDescriptorItem;
	private SelectItem factTypeItem;
	private DateItem calendarDayItem;
	private TextItem sunRiseItem;
	private TextAreaItem descriptionItem;
	private TextAreaItem commentItem;
	private CheckboxItem priorityItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;
	private boolean isCopy;

	public DlgAddEditFact(ListGrid listGrid, ListGridRecord pRecord,
			boolean isCopy) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			this.isCopy = isCopy;
			setTitle(isCopy ? CallCenterBK.constants.copyCalendar()
					: (pRecord == null ? CallCenterBK.constants.addFact()
							: CallCenterBK.constants.editFact()));

			setHeight(510);
			setWidth(510);
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
			dynamicForm.setTitleWidth(100);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			DataSource factStatusDS = DataSource.get("FactStatusDS");
			factStatusItem = new SelectItem();
			factStatusItem.setTitle(CallCenterBK.constants.factStatus());
			factStatusItem.setWidth(380);
			factStatusItem.setName("fact_status_id");
			factStatusItem.setOptionDataSource(factStatusDS);
			factStatusItem.setValueField("fact_status_id");
			factStatusItem.setDisplayField("fact_status_name");
			factStatusItem.setAddUnknownValues(false);

			DataSource factsDescriptorDS = DataSource.get("FactsDescriptorDS");
			factDescriptorItem = new SelectItem();
			factDescriptorItem
					.setTitle(CallCenterBK.constants.factDescriptor());
			factDescriptorItem.setWidth(380);
			factDescriptorItem.setName("facts_descriptor_id");
			factDescriptorItem.setOptionDataSource(factsDescriptorDS);
			factDescriptorItem.setValueField("facts_descriptor_id");
			factDescriptorItem.setDisplayField("facts_descriptor_name");
			factDescriptorItem.setAddUnknownValues(false);

			factDescriptorItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					descriptorChanged(event.getValue());
				}
			});

			DataSource factTypeDS = DataSource.get("FactTypeDS");
			factTypeItem = new SelectItem();
			factTypeItem.setTitle(CallCenterBK.constants.factType());
			factTypeItem.setWidth(380);
			factTypeItem.setName("fact_type_id");
			factTypeItem.setOptionDataSource(factTypeDS);
			factTypeItem.setValueField("fact_type_id");
			factTypeItem.setDisplayField("fact_type_name");
			factTypeItem.setAddUnknownValues(false);
			descriptorChanged(100001);

			calendarDayItem = new DateItem();
			calendarDayItem.setTitle(CallCenterBK.constants.date());
			calendarDayItem.setWidth(380);
			calendarDayItem.setValue(new Date());
			calendarDayItem.setName("fact_date");
			calendarDayItem.setHint(CallCenterBK.constants.choose());

			descriptionItem = new TextAreaItem();
			descriptionItem.setTitle(CallCenterBK.constants.description());
			descriptionItem.setName("remark");
			descriptionItem.setWidth(380);
			descriptionItem.setHeight(50);

			commentItem = new TextAreaItem();
			commentItem.setTitle(CallCenterBK.constants.comment());
			commentItem.setName("additional_comment");
			commentItem.setWidth(380);
			commentItem.setHeight(200);

			RegExpValidator regExpValidator = new RegExpValidator();
			regExpValidator
					.setExpression("^(([0-1][0-9])|([2][0-3])):([0-5][0-9])/(([0-1][0-9])|([2][0-3])):([0-5][0-9])$");

			sunRiseItem = new TextItem();
			sunRiseItem.setTitle(CallCenterBK.constants.sunRise());
			sunRiseItem.setName("sunup");
			sunRiseItem.setWidth(295);
			sunRiseItem.setValidators(regExpValidator);
			sunRiseItem.setHint("07:00/18:00");

			priorityItem = new CheckboxItem();
			priorityItem.setTitle(CallCenterBK.constants.priority());
			priorityItem.setWidth(300);
			priorityItem.setName("priority");
			priorityItem.setValue(false);

			dynamicForm.setFields(factStatusItem, factDescriptorItem,
					factTypeItem, sunRiseItem, calendarDayItem,
					descriptionItem, commentItem, priorityItem);

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
					save();
				}
			});

			addItem(hLayout);
			fillFields();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void descriptorChanged(Object value) {
		factTypeItem.setValue((Object) null);
		Criteria cr = new Criteria();
		cr.setAttribute("PKuni_", HTMLPanel.createUniqueId());
		cr.setAttribute("facts_descriptor_id", value);
		factTypeItem.setOptionCriteria(cr);

	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			factStatusItem.setValue(editRecord
					.getAttributeAsInt("fact_status_id"));
			Object value = editRecord.getAttributeAsInt("facts_descriptor_id");
			descriptorChanged(value);
			factDescriptorItem.setValue(value);
			factTypeItem.setValue(editRecord
					.getAttributeAsString("fact_type_id"));
			calendarDayItem
					.setValue(editRecord.getAttributeAsDate("fact_date"));
			sunRiseItem.setValue(editRecord.getAttributeAsString("sunup"));
			descriptionItem.setValue(editRecord.getAttributeAsString("remark"));
			commentItem.setValue(editRecord
					.getAttributeAsString("additional_comment"));
			priorityItem.setValue(editRecord.getAttributeAsString("priority").equals("1")?true:false);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String calendar_state_id = factStatusItem.getValueAsString();
			if (calendar_state_id == null
					|| calendar_state_id.trim().equals("")) {
				SC.say(CallCenterBK.constants.chooseType());
				return;
			}
			String calendar_event_id = factTypeItem.getValueAsString();
			if (calendar_event_id == null
					|| calendar_event_id.trim().equals("")) {
				SC.say(CallCenterBK.constants.chooseMoonPhase());
				return;
			}
			Date calendar_day = calendarDayItem.getValueAsDate();
			if (calendar_day == null) {
				SC.say(CallCenterBK.constants.enterRateCoeff());
				return;
			}
			String calendar_description = descriptionItem.getValueAsString();
			String calendar_comment = commentItem.getValueAsString();

			if (!sunRiseItem.validate()) {
				SC.say(CallCenterBK.constants.enterSunRise());
				return;
			}
			String sun_rise = sunRiseItem.getValueAsString();
			String priority = priorityItem.getValue().toString();
			String priorityString = "0";
			if (priority == "true") {
				priorityString = "1";
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("fact_status_id",
					new Integer(calendar_state_id));
			record.setAttribute("fact_type_id", new Integer(calendar_event_id));
			record.setAttribute("fact_date", calendar_day);
			record.setAttribute("remark", calendar_description);
			record.setAttribute("additional_comment", calendar_comment);
			record.setAttribute("sunup", sun_rise);
			record.setAttribute("priority", priorityString);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null && !isCopy) {
				record.setAttribute("fact_id",
						editRecord.getAttributeAsInt("fact_id"));
			}

			DSRequest req = new DSRequest();
			if (editRecord != null && !isCopy) {
				req.setAttribute("operationId", "updateFact");
				listGrid.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "addFact");
				listGrid.addData(record, new DSCallback() {
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
