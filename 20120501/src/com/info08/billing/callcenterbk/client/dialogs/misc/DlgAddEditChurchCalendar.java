package com.info08.billing.callcenterbk.client.dialogs.misc;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
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
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditChurchCalendar extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem calendarStateItem;
	private ComboBoxItem secCalendarTypeItem;
	private DateItem calendarDayItem;
	private TextAreaItem descriptionItem;
	
	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditChurchCalendar(ListGrid listGrid, ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenterBK.constants.addSecCalendar()
					: CallCenterBK.constants.editSecCalendar());

			setHeight(470);
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
			dynamicForm.setTitleWidth(200);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			calendarStateItem = new ComboBoxItem();
			calendarStateItem.setTitle(CallCenterBK.constants.type());
			calendarStateItem.setWidth(330);
			calendarStateItem.setName("calendar_state_id");
			calendarStateItem.setValueMap(ClientMapUtil.getInstance()
					.getCalendarStates());
			calendarStateItem.setAddUnknownValues(false);

			secCalendarTypeItem = new ComboBoxItem();
			secCalendarTypeItem.setTitle(CallCenterBK.constants.chruchCalEvent());
			secCalendarTypeItem.setWidth(330);
			secCalendarTypeItem.setName("event");
			secCalendarTypeItem.setFetchMissingValues(true);
			secCalendarTypeItem.setFilterLocally(false);
			secCalendarTypeItem.setAddUnknownValues(false);
			secCalendarTypeItem.setOptionOperationId("searchChurchCalForCB");
			DataSource datasource = DataSource.get("CalChurchDS");
			secCalendarTypeItem.setOptionDataSource(datasource);
			secCalendarTypeItem.setValueField("event_id");
			secCalendarTypeItem.setDisplayField("event");
			secCalendarTypeItem.setOptionCriteria(new Criteria());
			secCalendarTypeItem.setAutoFetchData(false);
			secCalendarTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = secCalendarTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("event_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("event_id", nullO);
						}
					}
				}
			});

			calendarDayItem = new DateItem();
			calendarDayItem.setTitle(CallCenterBK.constants.date());
			calendarDayItem.setWidth(330);
			calendarDayItem.setValue(new Date());
			calendarDayItem.setName("calendar_day");
			calendarDayItem.setHint(CallCenterBK.constants.choose());

			descriptionItem = new TextAreaItem();
			descriptionItem.setTitle(CallCenterBK.constants.description());
			descriptionItem.setName("calendar_description");
			descriptionItem.setWidth(330);
			descriptionItem.setHeight(300);
	
			dynamicForm.setFields(calendarStateItem, secCalendarTypeItem,calendarDayItem, descriptionItem);

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
			descriptionItem.setValue(editRecord
					.getAttributeAsString("calendar_description"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String calendar_state_id = calendarStateItem.getValueAsString();
			if (calendar_state_id == null
					|| calendar_state_id.trim().equals("")) {
				SC.say(CallCenterBK.constants.chooseType());
				return;
			}
			String calendar_event_id = secCalendarTypeItem.getValueAsString();
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
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("calendar_state_id", new Integer(calendar_state_id));
			record.setAttribute("calendar_event_id", new Integer(calendar_event_id));
			record.setAttribute("calendar_day", calendar_day);
			record.setAttribute("calendar_description", calendar_description);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("calendar_id",
						editRecord.getAttributeAsInt("calendar_id"));
			}

			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addChurchCalendar");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateChurchCalendar");
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
