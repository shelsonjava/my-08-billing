package com.info08.billing.callcenterbk.client.content.survey;

import java.util.Date;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.control.LogSMSDialog;
import com.info08.billing.callcenterbk.client.dialogs.survey.DlgChangeResolveStatus;
import com.info08.billing.callcenterbk.client.dialogs.survey.DlgDiscoveryManagerHist;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabDiscoveryHist extends Tab {

	private DynamicForm searchForm;
	private SelectItem discoveryTypeItem;
	private DateItem discoveryDateItem;
	private TextItem nmItem;
	private TextItem contactNmItem;
	private ComboBoxItem operatorItem;
	private ComboBoxItem updateUserItem;
	private SelectItem discResponseType;

	private VLayout mainLayout;

	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	protected DataSource datasource;

	public TabDiscoveryHist() {
		try {
			setTitle(CallCenterBK.constants.menuDiscoveryHist());
			setCanClose(true);

			datasource = DataSource.get("DiscoveryDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(200);
			searchForm.setNumCols(2);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			mainLayout.addMember(searchForm);

			discoveryTypeItem = new SelectItem();
			discoveryTypeItem.setTitle(CallCenterBK.constants.discoveryType());
			discoveryTypeItem.setWidth(250);
			discoveryTypeItem.setName("discoveryTypeItem");

			DataSource discoveryTypeDS = DataSource.get("DiscoveryTypeDS");
			discoveryTypeItem.setOptionOperationId("searchDiscoverTypesForCB");
			discoveryTypeItem.setOptionDataSource(discoveryTypeDS);
			discoveryTypeItem.setValueField("survey_kind_id");
			discoveryTypeItem.setDisplayField("survey_kind_name");

			discoveryTypeItem.setOptionCriteria(new Criteria());
			discoveryTypeItem.setAutoFetchData(false);

			discoveryTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = discoveryTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("survey_kind_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("survey_kind_id", nullO);
						}
					}
				}
			});

			discoveryDateItem = new DateItem();
			discoveryDateItem.setUseTextField(true);
			discoveryDateItem.setTitle(CallCenterBK.constants.date());
			discoveryDateItem.setName("discoveryDateItem");
			discoveryDateItem.setWidth(250);
			discoveryDateItem.setValue(new Date());

			nmItem = new TextItem();
			nmItem.setTitle(CallCenterBK.constants.phone());
			nmItem.setName("nmItem");
			nmItem.setWidth(250);

			contactNmItem = new TextItem();
			contactNmItem.setTitle(CallCenterBK.constants.contactPhone());
			contactNmItem.setName("contactNmItem");
			contactNmItem.setWidth(250);

			operatorItem = new ComboBoxItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setType("comboBox");
			operatorItem.setWidth(250);
			operatorItem.setName("operatorItem");
			DataSource persons = CommonSingleton.getInstance().getPersonsDS();
			if (persons != null) {
				operatorItem.setOptionOperationId("customPersSearch");
				operatorItem.setOptionDataSource(persons);
				operatorItem.setValueField("personelId");
				operatorItem.setDisplayField("fullPersonName");
			}

			updateUserItem = new ComboBoxItem();
			updateUserItem.setTitle(CallCenterBK.constants.resolve());
			updateUserItem.setType("comboBox");
			updateUserItem.setWidth(250);
			updateUserItem.setName("updateUserItem");
			DataSource persons1 = CommonSingleton.getInstance().getPersonsDS();
			if (persons1 != null) {
				updateUserItem.setOptionOperationId("customPersSearch");
				updateUserItem.setOptionDataSource(persons1);
				updateUserItem.setValueField("personelId");
				updateUserItem.setDisplayField("fullPersonName");
			}

			discResponseType = new SelectItem();
			discResponseType.setTitle(CallCenterBK.constants.status());
			discResponseType.setName("discResponseType");
			discResponseType.setWidth(250);

			DataSource discoveryRTypeDS = DataSource.get("DiscoveryRTypeDS");
			discResponseType.setOptionOperationId("searchDiscoverRtypesForCB");
			discResponseType.setOptionDataSource(discoveryRTypeDS);
			discResponseType.setValueField("survey_reply_type_id");
			discResponseType.setDisplayField("survey_reply_type_name");
			discResponseType.setAutoFetchData(true);

			searchForm.setFields(nmItem, contactNmItem, discoveryTypeItem,
					discoveryDateItem, operatorItem, updateUserItem,
					discResponseType);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			ToolStripButton smsBtn = new ToolStripButton(
					CallCenterBK.constants.sms(), "save.png");
			smsBtn.setLayoutAlign(Alignment.LEFT);
			smsBtn.setWidth(50);
			toolStrip.addButton(smsBtn);

			ToolStripButton changeStatus = new ToolStripButton(
					CallCenterBK.constants.changeStatus(), "yes.png");
			changeStatus.setLayoutAlign(Alignment.LEFT);
			changeStatus.setWidth(50);
			toolStrip.addButton(changeStatus);

			toolStrip.addSeparator();
			toolStrip.addSpacer(100);

			ToolStripButton viewBtn = new ToolStripButton(
					CallCenterBK.constants.view(), "yes.png");
			viewBtn.setLayoutAlign(Alignment.LEFT);
			viewBtn.setWidth(50);
			toolStrip.addButton(viewBtn);

			listGrid = new ListGrid();

			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setDataSource(datasource);
			listGrid.setFetchOperation("searchAllDiscoveryHist");
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);
			listGrid.setShowRowNumbers(true);

			ListGridField survey_kind_name = new ListGridField("survey_kind_name",
					CallCenterBK.constants.type(), 100);
			ListGridField p_numb = new ListGridField("p_numb",
					CallCenterBK.constants.phone(), 80);
			ListGridField survey_phone = new ListGridField("survey_phone",
					CallCenterBK.constants.contactPhone(), 120);
			ListGridField contact_person = new ListGridField("contact_person",
					CallCenterBK.constants.contactPerson(), 150);
			ListGridField survey_descript = new ListGridField("survey_descript",
					CallCenterBK.constants.message());
			ListGridField survey_reply_type_name = new ListGridField("survey_reply_type_name",
					CallCenterBK.constants.status(), 150);
			ListGridField rec_user = new ListGridField("rec_user",
					CallCenterBK.constants.shortOp(), 50);
			ListGridField rec_date = new ListGridField("rec_date",
					CallCenterBK.constants.time(), 100);
			// rec_date.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
			ListGridField upd_user = new ListGridField("upd_user",
					CallCenterBK.constants.updUser(), 100);

			survey_kind_name.setAlign(Alignment.LEFT);
			p_numb.setAlign(Alignment.LEFT);
			survey_phone.setAlign(Alignment.LEFT);
			contact_person.setAlign(Alignment.LEFT);
			rec_user.setAlign(Alignment.CENTER);
			rec_date.setAlign(Alignment.CENTER);

			listGrid.setFields(survey_kind_name, survey_reply_type_name, p_numb,
					survey_phone, contact_person, survey_descript, rec_user,
					rec_date, upd_user);

			mainLayout.addMember(listGrid);

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					discoveryTypeItem.clearValue();
					discoveryDateItem.clearValue();
					nmItem.clearValue();
					contactNmItem.clearValue();
					operatorItem.clearValue();
					updateUserItem.clearValue();
				}
			});

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			nmItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			contactNmItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			smsBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.warning(),
								CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					String sessionId = listGridRecord
							.getAttributeAsString("session_call_id");
					LogSMSDialog addEditNoteDialog = new LogSMSDialog(sessionId);
					addEditNoteDialog.show();
				}
			});

			changeStatus.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					try {
						changeStatus();
					} catch (Exception e) {
						e.printStackTrace();
						SC.say(e.toString());
					}
				}
			});

			viewBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					ListGridRecord gridRecord = listGrid.getSelectedRecord();
					if (gridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					DlgDiscoveryManagerHist dlgResolveDiscovery = new DlgDiscoveryManagerHist(
							datasource, gridRecord);
					dlgResolveDiscovery.show();
				}
			});

			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void changeStatus() {
		try {
			String userName = CommonSingleton.getInstance().getSessionPerson()
					.getUserName();
			ListGridRecord listGridRecord = listGrid.getSelectedRecord();
			if (listGridRecord == null) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.pleaseSelrecord());
				return;
			}
			String upd_user = listGridRecord.getAttributeAsString("upd_user");
			if (!userName.trim().equals(
					((upd_user == null) ? "NO_USSERRRRRRRRRRRRRR" : upd_user
							.trim()))) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.discNotAuthUser());
				return;
			}
			// listGrid.setCriteria(null);
			DlgChangeResolveStatus changeResolveStatus = new DlgChangeResolveStatus(
					listGrid, listGridRecord);
			changeResolveStatus.show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();

			String survey_kind_id = discoveryTypeItem.getValueAsString();
			if (survey_kind_id != null && !survey_kind_id.trim().equals("")) {
				criteria.setAttribute("survey_kind_id", new Integer(
						survey_kind_id));
			}
			String survey_reply_type_id = discResponseType.getValueAsString();
			if (survey_reply_type_id != null && !survey_reply_type_id.trim().equals("")) {
				criteria.setAttribute("survey_reply_type_id", new Integer(
						survey_reply_type_id));
			}

			criteria.setAttribute("survery_responce_status", new Integer(1));

			try {
				Date rec_date = discoveryDateItem.getValueAsDate();
				if (rec_date != null) {
					criteria.setAttribute("rec_date", rec_date);
				}
			} catch (Exception e) {
			}

			String p_numb = nmItem.getValueAsString();
			if (p_numb != null && !p_numb.trim().equals("")) {
				criteria.setAttribute("p_numb", p_numb);
			}

			String survey_phone = contactNmItem.getValueAsString();
			if (survey_phone != null && !survey_phone.trim().equals("")) {
				criteria.setAttribute("survey_phone", survey_phone);
			}

			String personnel_id = operatorItem.getValueAsString();
			if (personnel_id != null && !personnel_id.trim().equals("")) {
				criteria.setAttribute("personnel_id", new Integer(personnel_id));
			}

			String personnel_id1 = updateUserItem.getValueAsString();
			if (personnel_id1 != null && !personnel_id1.trim().equals("")) {
				criteria.setAttribute("personnel_id1", new Integer(
						personnel_id1));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllDiscoveryHist");
			listGrid.invalidateCache();
			listGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
