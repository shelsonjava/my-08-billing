package com.info08.billing.callcenterbk.client.dialogs.ent;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.info08.billing.callcenterbk.client.CallCenter;
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

public class DlgAddEditEntPoster extends Window {

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

	private Integer ent_place_id;
	private Integer ent_type_id;

	public DlgAddEditEntPoster(ListGrid listGrid, ListGridRecord pRecord,
			Integer ent_place_id, Integer ent_type_id) {
		try {

			this.editRecord = pRecord;
			this.listGrid = listGrid;
			this.ent_place_id = ent_place_id;
			this.ent_type_id = ent_type_id;

			setTitle(pRecord == null ? CallCenter.constants.addEntPoster()
					: CallCenter.constants.modifyEntPoster());

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
			entTypeItem.setTitle(CallCenter.constants.entType());
			entTypeItem.setWidth(250);
			entTypeItem.setName("ent_type_geo");
			entTypeItem.setFetchMissingValues(true);
			entTypeItem.setFilterLocally(false);
			entTypeItem.setAddUnknownValues(false);

			DataSource entTypeDS = DataSource.get("EntTypeDS");
			entTypeItem.setOptionOperationId("searchAllEntTypesForCB");
			entTypeItem.setOptionDataSource(entTypeDS);
			entTypeItem.setValueField("ent_type_id");
			entTypeItem.setDisplayField("ent_type_geo");

			entTypeItem.setOptionCriteria(new Criteria());
			entTypeItem.setAutoFetchData(false);

			entTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = entTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("ent_type_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("ent_type_id", nullO);
						}
					}
				}
			});
			entTypeItem.setValue(this.ent_type_id);

			entPlacesItem = new ComboBoxItem();
			entPlacesItem.setTitle(CallCenter.constants.entPlace());
			entPlacesItem.setWidth(250);
			entPlacesItem.setName("ent_place_id");
			entPlacesItem.setFetchMissingValues(true);
			entPlacesItem.setFilterLocally(false);
			entPlacesItem.setAddUnknownValues(false);

			DataSource entPlaceDS = DataSource.get("EntPlaceDS");
			entPlacesItem.setOptionOperationId("searchAllEntPlacesForCB");
			entPlacesItem.setOptionDataSource(entPlaceDS);
			entPlacesItem.setValueField("ent_place_id");
			entPlacesItem.setDisplayField("ent_place_geo");

			entPlacesItem.setOptionCriteria(new Criteria());
			entPlacesItem.setAutoFetchData(false);

			entPlacesItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = entPlacesItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("ent_place_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("ent_place_id", nullO);
						}
					}
				}
			});
			entPlacesItem.setValue(this.ent_place_id);

			entPosterGeoItem = new TextAreaItem();
			entPosterGeoItem.setTitle(CallCenter.constants.entPosterName());
			entPosterGeoItem.setName("ent_poster_geo");
			entPosterGeoItem.setWidth(250);
			entPosterGeoItem.setHeight(100);

			entCommentGeoItem = new TextAreaItem();
			entCommentGeoItem.setTitle(CallCenter.constants.comment());
			entCommentGeoItem.setName("comment_geo");
			entCommentGeoItem.setWidth(250);
			entCommentGeoItem.setHeight(100);

			posterDateItem = new DateItem();
			posterDateItem.setTitle(CallCenter.constants.date());
			posterDateItem.setWidth("100%");
			posterDateItem.setValue(new Date());
			posterDateItem.setName("poster_date");
			posterDateItem.setHint(CallCenter.constants.choose());

			postTimeItem = new TimeItem("poster_time", "Time");
			postTimeItem.setTitle(CallCenter.constants.time());
			postTimeItem.setWidth(250);
			postTimeItem.setName("poster_time");
			postTimeItem.setHint("");
			postTimeItem.setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);
			postTimeItem.setUseMask(true);
			postTimeItem.setMask("00:00");

			entPriceGeoItem = new TextAreaItem();
			entPriceGeoItem.setTitle(CallCenter.constants.price());
			entPriceGeoItem.setName("poster_price_geo");
			entPriceGeoItem.setWidth(250);
			entPriceGeoItem.setHeight(100);

			entSMSCommentItem = new TextAreaItem();
			entSMSCommentItem.setTitle(CallCenter.constants.smsComment());
			entSMSCommentItem.setName("sms_comment");
			entSMSCommentItem.setWidth(250);
			entSMSCommentItem.setHeight(100);

			dtCritItem = new CheckboxItem();
			dtCritItem.setTitle(CallCenter.constants.timeCrit());
			dtCritItem.setName("dt_crit");
			dtCritItem.setWidth(250);

			dtViewCritItem = new CheckboxItem();
			dtViewCritItem.setTitle(CallCenter.constants.dtViewCrit());
			dtViewCritItem.setName("dtViewCrit");
			dtViewCritItem.setWidth(250);

			dynamicForm.setFields(entTypeItem, entPlacesItem, entPosterGeoItem,
					entCommentGeoItem, entPriceGeoItem, entSMSCommentItem,
					posterDateItem, postTimeItem, dtCritItem, dtViewCritItem);

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
			entPosterGeoItem.setValue(editRecord
					.getAttributeAsString("ent_poster_geo"));
			entCommentGeoItem.setValue(editRecord
					.getAttributeAsString("comment_geo"));
			entPriceGeoItem.setValue(editRecord
					.getAttributeAsString("poster_price_geo"));
			entSMSCommentItem.setValue(editRecord
					.getAttributeAsString("sms_comment"));
			Date posterDate = editRecord.getAttributeAsDate("poster_date");
			if (posterDate != null) {
				posterDateItem.setValue(posterDate);
			}
			String posterTime = editRecord.getAttributeAsString("poster_time");
			if (posterTime != null) {
				postTimeItem.setValue(posterTime);
			}
			Integer dt_crit = editRecord.getAttributeAsInt("dt_crit");
			if (dt_crit != null && dt_crit.intValue() == -1) {
				dtCritItem.setValue(true);
			}
			Integer dt_view_crit = editRecord.getAttributeAsInt("dt_view_crit");
			if (dt_view_crit != null && dt_view_crit.intValue() == -1) {
				dtViewCritItem.setValue(true);
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String ent_type_id_str = entTypeItem.getValueAsString();
			if (ent_type_id_str == null || ent_type_id_str.trim().equals("")) {
				SC.say(CallCenter.constants.chooseEntType());
				return;
			}
			String ent_place_id_str = entPlacesItem.getValueAsString();
			if (ent_place_id_str == null || ent_place_id_str.trim().equals("")) {
				SC.say(CallCenter.constants.chooseEntPlace());
				return;
			}
			String ent_poster_geo = entPosterGeoItem.getValueAsString();
			if (ent_poster_geo == null || ent_poster_geo.trim().equals("")) {
				SC.say(CallCenter.constants.enterEntPosterGeo());
				return;
			}
			String comment_geo = entCommentGeoItem.getValueAsString();
			String poster_price_geo = entPriceGeoItem.getValueAsString();
			String sms_comment = entSMSCommentItem.getValueAsString();
			Date poster_date = posterDateItem.getValueAsDate();
			Date time = (Date) postTimeItem.getValue();
			String str_time = "";
			if (time != null) {
				final DateTimeFormat dateFormatter = DateTimeFormat
						.getFormat("HH:mm");
				str_time = dateFormatter.format(time);
			}

			boolean dt_crit_bool = dtCritItem.getValueAsBoolean();
			boolean dt_view_crit_bool = dtViewCritItem.getValueAsBoolean();

			Integer dt_crit = dt_crit_bool ? -1 : 0;
			Integer dt_view_crit = dt_view_crit_bool ? -1 : 0;

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("ent_type_id", new Integer(ent_type_id_str));
			record.setAttribute("ent_place_id", new Integer(ent_place_id_str));
			record.setAttribute("ent_poster_geo", ent_poster_geo);
			record.setAttribute("comment_geo", comment_geo);
			record.setAttribute("poster_price_geo", poster_price_geo);
			record.setAttribute("sms_comment", sms_comment);
			record.setAttribute("poster_date", poster_date);
			record.setAttribute("poster_time", str_time == null ? null
					: str_time.toString());
			record.setAttribute("dt_crit", dt_crit);
			record.setAttribute("dt_view_crit", dt_view_crit);

			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("ent_poster_id",
						editRecord.getAttributeAsInt("ent_poster_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null
					|| editRecord.getAttributeAsInt("ent_poster_id") == null) {
				req.setAttribute("operationId", "addEntPoster");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateEntPoster");
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
