package com.info08.billing.callcenter.client.dialogs.misc;

import java.util.Date;

import com.info08.billing.callcenter.client.CallCenter;
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
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditSecCalendar extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem calendarStateItem;
	private ComboBoxItem secCalendarTypeItem;
	private DateItem calendarDayItem;
	private TextItem sunRiseItem;
	private TextAreaItem descriptionItem;
	private TextAreaItem commentItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;
	private boolean isCopy;

	public DlgAddEditSecCalendar(ListGrid listGrid, ListGridRecord pRecord,
			boolean isCopy) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			this.isCopy = isCopy;
			setTitle(isCopy ? CallCenter.constants.copyCalendar()
					: (pRecord == null ? CallCenter.constants.addSecCalendar()
							: CallCenter.constants.editSecCalendar()));

			setHeight(490);
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

			calendarStateItem = new ComboBoxItem();
			calendarStateItem.setTitle(CallCenter.constants.type());
			calendarStateItem.setWidth(380);
			calendarStateItem.setName("calendar_state_id");
			calendarStateItem.setValueMap(ClientMapUtil.getInstance()
					.getCalendarStates());
			calendarStateItem.setAddUnknownValues(false);

			secCalendarTypeItem = new ComboBoxItem();
			secCalendarTypeItem.setTitle(CallCenter.constants.moonPhase());
			secCalendarTypeItem.setWidth(380);
			secCalendarTypeItem.setName("calendar_event_id");
			secCalendarTypeItem.setValueMap(ClientMapUtil.getInstance()
					.getSecCalendarTypes());
			secCalendarTypeItem.setAddUnknownValues(false);

			calendarDayItem = new DateItem();
			calendarDayItem.setTitle(CallCenter.constants.date());
			calendarDayItem.setWidth(380);
			calendarDayItem.setValue(new Date());
			calendarDayItem.setName("calendar_day");
			calendarDayItem.setHint(CallCenter.constants.choose());

			descriptionItem = new TextAreaItem();
			descriptionItem.setTitle(CallCenter.constants.description());
			descriptionItem.setName("calendar_description");
			descriptionItem.setWidth(380);
			descriptionItem.setHeight(50);

			commentItem = new TextAreaItem();
			commentItem.setTitle(CallCenter.constants.comment());
			commentItem.setName("calendar_comment");
			commentItem.setWidth(380);
			commentItem.setHeight(250);

			RegExpValidator regExpValidator = new RegExpValidator();
			regExpValidator
					.setExpression("^(([0-1][0-9])|([2][0-3])):([0-5][0-9])/(([0-1][0-9])|([2][0-3])):([0-5][0-9])$");

			sunRiseItem = new TextItem();
			sunRiseItem.setTitle(CallCenter.constants.sunRise());
			sunRiseItem.setName("sun_rise");
			sunRiseItem.setWidth(295);
			sunRiseItem.setValidators(regExpValidator);
			sunRiseItem.setHint("07:00/18:00");

			dynamicForm.setFields(calendarStateItem, secCalendarTypeItem,
					sunRiseItem, calendarDayItem, descriptionItem, commentItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenter.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenter.constants.close());
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

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			calendarStateItem.setValue(editRecord
					.getAttributeAsInt("calendar_state_id"));
			secCalendarTypeItem.setValue(editRecord
					.getAttributeAsString("calendar_event_id"));
			calendarDayItem.setValue(editRecord
					.getAttributeAsDate("calendar_day"));
			sunRiseItem.setValue(editRecord.getAttributeAsString("sun_rise"));
			descriptionItem.setValue(editRecord
					.getAttributeAsString("calendar_description"));
			commentItem.setValue(editRecord
					.getAttributeAsString("calendar_comment"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String calendar_state_id = calendarStateItem.getValueAsString();
			if (calendar_state_id == null
					|| calendar_state_id.trim().equals("")) {
				SC.say(CallCenter.constants.chooseType());
				return;
			}
			String calendar_event_id = secCalendarTypeItem.getValueAsString();
			if (calendar_event_id == null
					|| calendar_event_id.trim().equals("")) {
				SC.say(CallCenter.constants.chooseMoonPhase());
				return;
			}
			Date calendar_day = calendarDayItem.getValueAsDate();
			if (calendar_day == null) {
				SC.say(CallCenter.constants.enterRateCoeff());
				return;
			}
			String calendar_description = descriptionItem.getValueAsString();
			String calendar_comment = commentItem.getValueAsString();

			if (!sunRiseItem.validate()) {
				SC.say(CallCenter.constants.enterSunRise());
				return;
			}
			String sun_rise = sunRiseItem.getValueAsString();

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("calendar_state_id", new Integer(
					calendar_state_id));
			record.setAttribute("calendar_event_id", new Integer(
					calendar_event_id));
			record.setAttribute("calendar_day", calendar_day);
			record.setAttribute("calendar_description", calendar_description);
			record.setAttribute("calendar_comment", calendar_comment);
			record.setAttribute("sun_rise", sun_rise);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null && !isCopy) {
				record.setAttribute("calendar_id",
						editRecord.getAttributeAsInt("calendar_id"));
			}

			DSRequest req = new DSRequest();
			if (editRecord != null && !isCopy) {
				req.setAttribute("operationId", "updateSecularCalendar");
				listGrid.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "addSecularCalendar");
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
