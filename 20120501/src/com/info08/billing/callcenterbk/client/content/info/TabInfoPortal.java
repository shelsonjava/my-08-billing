package com.info08.billing.callcenterbk.client.content.info;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.ChargePanel;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewOpRemarks;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.common.ServerSessionSurvItem;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabInfoPortal extends Tab {

	private static VLayout mainLayout;

	public TabInfoPortal() {
		try {
			setTitle("საინფორმაციო პორტალი");
			setCanClose(false);
			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	public static void draw() {
		try {
			ChargePanel chargePanel = new ChargePanel(800, false, true, -1, -1);
			mainLayout.addMember(chargePanel);

			DynamicForm searchFormComm = new DynamicForm();
			searchFormComm = new DynamicForm();
			searchFormComm.setAutoFocus(false);
			searchFormComm.setWidth(770);
			searchFormComm.setNumCols(2);
			searchFormComm.setTitleOrientation(TitleOrientation.TOP);
			mainLayout.addMember(searchFormComm);

			SpacerItem spacerItem1 = new SpacerItem();
			spacerItem1.setWidth(770);
			spacerItem1.setName("spacerItem1");
			spacerItem1.setColSpan(2);
			spacerItem1.setHeight(5);

			final TextAreaItem commentItem = new TextAreaItem();
			commentItem.setTitle(CallCenterBK.constants.mainNews());
			commentItem.setCanFocus(false);
			commentItem.setName("commentItem");
			commentItem.setWidth(770);
			commentItem.setCanEdit(false);
			commentItem.setTextBoxStyle("fontRedAndBoldNoBorder");
			commentItem.setHeight(50);

			searchFormComm.setFields(spacerItem1, commentItem);

			DataSource dataSource = DataSource.get("CallCenterNewsDS");
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchCallCenterWarnings");
			dataSource.fetchData(new Criteria(), new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						String call_center_news_text = records[0]
								.getAttributeAsString("call_center_news_text");
						if (call_center_news_text != null
								&& !call_center_news_text.trim().equals("")) {
							commentItem.setValue(call_center_news_text);
						}
					}
				}
			}, dsRequest);

			final ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();

			if (serverSession != null && !serverSession.isWebSession()) {

				final Long persNotesCount = serverSession
						.getUnreadPersNotesCount();

				ToolStrip toolStrip = new ToolStrip();
				toolStrip.setWidth(770);
				toolStrip.setPadding(5);

				ToolStripButton remarks = null;
				if (persNotesCount == null || persNotesCount.longValue() <= 0) {
					remarks = new ToolStripButton(
							CallCenterBK.constants.remarks(), "information.png");
				} else {
					remarks = new ToolStripButton(
							CallCenterBK.constants.remarks(), "remarks.png");
				}
				final ToolStripButton remarksBtn = remarks;
				remarksBtn.setLayoutAlign(Alignment.LEFT);
				remarksBtn.setWidth(50);
				toolStrip.addButton(remarksBtn);

				mainLayout.addMember(toolStrip);

				remarks.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						try {
							Long myPersNotesCount = serverSession
									.getUnreadPersNotesCount();
							if (myPersNotesCount == null
									|| myPersNotesCount.longValue() <= 0) {
								SC.say(CallCenterBK.constants
										.persNotesIsEmpty());
								return;
							} else {
								DlgViewOpRemarks dlgViewOpRemarks = new DlgViewOpRemarks(
										remarksBtn);
								dlgViewOpRemarks.show();
							}
						} catch (Exception e) {
							e.printStackTrace();
							SC.say(e.toString());
						}
					}
				});

				final ListGrid listGrid = new ListGrid() {
					protected String getCellCSSText(ListGridRecord record,
							int rowNum, int colNum) {
						ListGridRecord countryRecord = (ListGridRecord) record;
						if (countryRecord == null) {
							return super.getCellCSSText(record, rowNum, colNum);
						}
						Integer bblocked = countryRecord
								.getAttributeAsInt("bblocked");
						if (bblocked != null && bblocked.equals(1)) {
							return "color:red;";
						} else {
							return super.getCellCSSText(record, rowNum, colNum);
						}
					};
				};

				listGrid.setWidth(1300);
				listGrid.setHeight100();
				listGrid.setDataSource(SurveyClientDS.getInstance());
				listGrid.setWrapCells(true);
				listGrid.setFixedRecordHeights(false);
				listGrid.setCanDragSelectText(true);
				listGrid.setShowRowNumbers(true);
				listGrid.setAutoFetchData(true);

				ListGridField survey_kind_name = new ListGridField(
						"survey_kind_name", CallCenterBK.constants.type(), 100);
				ListGridField p_numb = new ListGridField("p_numb",
						CallCenterBK.constants.phone(), 80);
				ListGridField survey_phone = new ListGridField("survey_phone",
						CallCenterBK.constants.contactPhone(), 120);
				ListGridField survey_person = new ListGridField(
						"survey_person",
						CallCenterBK.constants.contactPerson(), 150);
				ListGridField survey_descript = new ListGridField(
						"survey_descript", CallCenterBK.constants.message());
				ListGridField rec_user = new ListGridField("survey_creator",
						CallCenterBK.constants.shortOp(), 50);
				ListGridField rec_date = new ListGridField("survey_created",
						CallCenterBK.constants.time(), 100);
				ListGridField upd_user = new ListGridField("loked_user",
						CallCenterBK.constants.updUser(), 100);
				ListGridField operator_src = new ListGridField("operator_src",
						CallCenterBK.constants.operator(), 80);

				survey_kind_name.setAlign(Alignment.LEFT);
				p_numb.setAlign(Alignment.LEFT);
				survey_phone.setAlign(Alignment.LEFT);
				survey_person.setAlign(Alignment.LEFT);
				rec_user.setAlign(Alignment.CENTER);
				rec_date.setAlign(Alignment.CENTER);
				operator_src.setAlign(Alignment.CENTER);

				listGrid.setFields(operator_src, survey_kind_name, p_numb,
						survey_phone, survey_person, survey_descript, rec_user,
						rec_date, upd_user);

				mainLayout.addMember(listGrid);

				ServerSessionSurvItem surveyData[] = serverSession
						.getSurveryList();
				if (surveyData != null && surveyData.length > 0) {
					for (ServerSessionSurvItem record : surveyData) {
						ListGridRecord gridRecord = new ListGridRecord();
						gridRecord.setAttribute("bblocked", record.getBblocked());
						gridRecord.setAttribute("loked_user", record.getLoked_user());
						gridRecord.setAttribute("operator_src", record.getOperator_src());
						gridRecord.setAttribute("p_numb", record.getP_numb());
						gridRecord.setAttribute("personnel_id", record.getPersonnel_id());
						gridRecord.setAttribute("session_call_id", record.getSession_call_id());
						gridRecord.setAttribute("survery_responce_status", record.getSurvery_responce_status());
						gridRecord.setAttribute("survey_creator", record.getSurvey_creator());
						gridRecord.setAttribute("survey_descript", record.getSurvey_descript());
						gridRecord.setAttribute("survey_done", record.getSurvey_done());
						gridRecord.setAttribute("survey_kind_id", record.getSurvey_kind_id());
						gridRecord.setAttribute("survey_kind_name", record.getSurvey_kind_name());
						gridRecord.setAttribute("survey_person", record.getSurvey_person());
						gridRecord.setAttribute("survey_phone", record.getSurvey_phone());
						gridRecord.setAttribute("survey_reply_type_id", record.getSurvey_reply_type_id());
						gridRecord.setAttribute("survey_reply_type_name", record.getSurvey_reply_type_name());
						gridRecord.setAttribute("start_date", record.getStart_date());						
						gridRecord.setAttribute("survey_created", record.getSurvey_created());
						gridRecord.setAttribute("survey_id", record.getSurvey_id());
						listGrid.addData(gridRecord, new DSCallback() {							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record dat[] = response.getData();
								if(dat!=null && dat.length>0){
									listGrid.selectRecord(dat[0]);	
								}
							}
						});
					}								
				}
			}

			if (serverSession.getCallType() == Constants.callTypeNoncharge) {
				SC.say(serverSession.getNon_charge_remark(),
						new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								String callCenterReqMsg = serverSession
										.getCallCenterReqMsg();
								if (callCenterReqMsg != null
										&& !callCenterReqMsg.trim().equals("")) {
									SC.warn(callCenterReqMsg);
								}
							}
						});
			} else {
				String callCenterReqMsg = serverSession.getCallCenterReqMsg();
				if (callCenterReqMsg != null
						&& !callCenterReqMsg.trim().equals("")) {
					SC.warn(callCenterReqMsg);
				}
			}
			String freeOfCharge = serverSession.getFreeOfChargeText();
			if (freeOfCharge != null && !freeOfCharge.trim().equals("")) {
				SC.say(freeOfCharge);
			}

			boolean isBirthdayOrg = serverSession.isBirthdayOrg();
			if (isBirthdayOrg) {
				SC.say(CallCenterBK.constants.birthdayAlert());
			}

//			HLayout hLayout = new HLayout();
//			hLayout.setHeight(400);
//			hLayout.setWidth100();
//			hLayout.setStyleName("headerClass");
//			mainLayout.addMember(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
