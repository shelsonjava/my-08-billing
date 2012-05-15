package com.info08.billing.callcenterbk.client.dialogs.event;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditEvent extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem entTypeItem;
	private ComboBoxItem entPlacesItem;
	private TextAreaItem entPosterGeoItem;
	private TextAreaItem entCommentGeoItem;
	private DateItem posterDateItem;
	private TimeItem postTimeItem;
	private TextAreaItem entPriceGeoItem;
	private TextAreaItem entSMSCommentItem;
	private CheckboxItem dtCritItem;
	private CheckboxItem dtViewCritItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	private Integer event_owner_id;
	private Integer event_category_id;

	public DlgAddEditEvent(ListGrid listGrid, ListGridRecord pRecord,
			Integer event_owner_id, Integer event_category_id) {
		try {

			this.editRecord = pRecord;
			this.listGrid = listGrid;
			this.event_owner_id = event_owner_id;
			this.event_category_id = event_category_id;

			setTitle(pRecord == null ? CallCenterBK.constants.addEntPoster()
					: CallCenterBK.constants.modifyEntPoster());

			setHeight(360);
			setWidth(800);
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
			dynamicForm.setTitleWidth(320);
			dynamicForm.setNumCols(4);
			hLayout.addMember(dynamicForm);

			entTypeItem = new ComboBoxItem();
			entTypeItem.setTitle(CallCenterBK.constants.entType());
			entTypeItem.setWidth(250);
			entTypeItem.setName("event_category_name");
			entTypeItem.setFetchMissingValues(true);
			entTypeItem.setFilterLocally(false);
			entTypeItem.setAddUnknownValues(false);

			DataSource EventCategoryDS = DataSource.get("EventCategoryDS");
			entTypeItem.setOptionOperationId("searchAllEventCategoryForCB");
			entTypeItem.setOptionDataSource(EventCategoryDS);
			entTypeItem.setValueField("event_category_id");
			entTypeItem.setDisplayField("event_category_name");

			entTypeItem.setOptionCriteria(new Criteria());
			entTypeItem.setAutoFetchData(false);

			entTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = entTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("event_category_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("event_category_id", nullO);
						}
					}
				}
			});
			entTypeItem.setValue(this.event_category_id);

			entPlacesItem = new ComboBoxItem();
			entPlacesItem.setTitle(CallCenterBK.constants.entPlace());
			entPlacesItem.setWidth(250);
			entPlacesItem.setName("event_owner_id");
			entPlacesItem.setFetchMissingValues(true);
			entPlacesItem.setFilterLocally(false);
			entPlacesItem.setAddUnknownValues(false);

			DataSource EventOwnerDS = DataSource.get("EventOwnerDS");
			entPlacesItem.setOptionOperationId("searchAllEventOwnerForCB");
			entPlacesItem.setOptionDataSource(EventOwnerDS);
			entPlacesItem.setValueField("event_owner_id");
			entPlacesItem.setDisplayField("event_owner_name");

			entPlacesItem.setOptionCriteria(new Criteria());
			entPlacesItem.setAutoFetchData(false);

			entPlacesItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = entPlacesItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("event_owner_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("event_owner_id", nullO);
						}
					}
				}
			});
			entPlacesItem.setValue(this.event_owner_id);

			entPosterGeoItem = new TextAreaItem();
			entPosterGeoItem.setTitle(CallCenterBK.constants.entPosterName());
			entPosterGeoItem.setName("event_name");
			entPosterGeoItem.setWidth(250);
			entPosterGeoItem.setHeight(100);

			entCommentGeoItem = new TextAreaItem();
			entCommentGeoItem.setTitle(CallCenterBK.constants.comment());
			entCommentGeoItem.setName("remark");
			entCommentGeoItem.setWidth(250);
			entCommentGeoItem.setHeight(100);

			posterDateItem = new DateItem();
			posterDateItem.setTitle(CallCenterBK.constants.date());
			posterDateItem.setWidth("100%");
			posterDateItem.setValue(new Date());
			posterDateItem.setName("event_date");
			posterDateItem.setHint(CallCenterBK.constants.choose());

			postTimeItem = new TimeItem("event_time", "Time");
			postTimeItem.setTitle(CallCenterBK.constants.time());
			postTimeItem.setWidth(250);
			postTimeItem.setName("event_time");
			postTimeItem.setHint("");
			postTimeItem.setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);
			postTimeItem.setUseMask(true);
			postTimeItem.setMask("00:00");

			entPriceGeoItem = new TextAreaItem();
			entPriceGeoItem.setTitle(CallCenterBK.constants.price());
			entPriceGeoItem.setName("event_price");
			entPriceGeoItem.setWidth(250);
			entPriceGeoItem.setHeight(100);

			entSMSCommentItem = new TextAreaItem();
			entSMSCommentItem.setTitle(CallCenterBK.constants.smsComment());
			entSMSCommentItem.setName("sms_remark");
			entSMSCommentItem.setWidth(250);
			entSMSCommentItem.setHeight(100);

			dtCritItem = new CheckboxItem();
			dtCritItem.setTitle(CallCenterBK.constants.timeCrit());
			dtCritItem.setName("date_criteria");
			dtCritItem.setWidth(250);

			dtViewCritItem = new CheckboxItem();
			dtViewCritItem.setTitle(CallCenterBK.constants.dtViewCrit());
			dtViewCritItem.setName("date_visibility");
			dtViewCritItem.setWidth(250);

			dynamicForm.setFields(entTypeItem, entPlacesItem, entPosterGeoItem,
					entCommentGeoItem, entPriceGeoItem, entSMSCommentItem,
					posterDateItem, postTimeItem, dtCritItem, dtViewCritItem);

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
			entPosterGeoItem.setValue(editRecord
					.getAttributeAsString("event_name"));
			entCommentGeoItem.setValue(editRecord
					.getAttributeAsString("remark"));
			entPriceGeoItem.setValue(editRecord
					.getAttributeAsString("event_price"));
			entSMSCommentItem.setValue(editRecord
					.getAttributeAsString("sms_remark"));
			Date posterDate = editRecord.getAttributeAsDate("event_date");
			if (posterDate != null) {
				posterDateItem.setValue(posterDate);
			}
			String posterTime = editRecord.getAttributeAsString("event_time");
			if (posterTime != null) {
				postTimeItem.setValue(posterTime);
			}
			Integer date_criteria = editRecord.getAttributeAsInt("date_criteria");
			if (date_criteria != null && date_criteria.intValue() == -1) {
				dtCritItem.setValue(true);
			}
			Integer date_visibility = editRecord.getAttributeAsInt("date_visibility");
			if (date_visibility != null && date_visibility.intValue() == -1) {
				dtViewCritItem.setValue(true);
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String event_category_id_str = entTypeItem.getValueAsString();
			if (event_category_id_str == null
					|| event_category_id_str.trim().equals("")) {
				SC.say(CallCenterBK.constants.chooseEntType());
				return;
			}
			String event_owner_id_str = entPlacesItem.getValueAsString();
			if (event_owner_id_str == null
					|| event_owner_id_str.trim().equals("")) {
				SC.say(CallCenterBK.constants.chooseEntPlace());
				return;
			}
			String event_name = entPosterGeoItem.getValueAsString();
			if (event_name == null || event_name.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterEntPosterGeo());
				return;
			}
			String remark = entCommentGeoItem.getValueAsString();
			String event_price = entPriceGeoItem.getValueAsString();
			String sms_remark = entSMSCommentItem.getValueAsString();
			Date event_date = posterDateItem.getValueAsDate();
			Date time = (Date) postTimeItem.getValue();
			String str_time = "";
			if (time != null) {
				final DateTimeFormat dateFormatter = DateTimeFormat
						.getFormat("HH:mm");
				str_time = dateFormatter.format(time);
			}

			boolean date_criteria_bool = dtCritItem.getValueAsBoolean();
			boolean date_visibility_bool = dtViewCritItem.getValueAsBoolean();

			Integer date_criteria = date_criteria_bool ? -1 : 0;
			Integer date_visibility = date_visibility_bool ? -1 : 0;

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("event_category_id", new Integer(
					event_category_id_str));
			record.setAttribute("event_owner_id", new Integer(
					event_owner_id_str));
			record.setAttribute("event_name", event_name);
			record.setAttribute("remark", remark);
			record.setAttribute("event_price", event_price);
			record.setAttribute("sms_remark", sms_remark);
			record.setAttribute("event_date", event_date);
			record.setAttribute("event_time", str_time == null ? null
					: str_time.toString());
			record.setAttribute("date_criteria", date_criteria);
			record.setAttribute("date_visibility", date_visibility);

			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("event_id",
						editRecord.getAttributeAsInt("event_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null
					|| editRecord.getAttributeAsInt("event_id") == null) {
				req.setAttribute("operationId", "addEvent");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateEvent");
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
