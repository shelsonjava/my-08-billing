package com.info08.billing.callcenterbk.client.content.survey;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.survey.DlgSurveyManager;
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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
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

public class TabSurvey extends Tab {

	private DynamicForm searchForm;
	private SelectItem surveyKindItem;

	private VLayout mainLayout;

	private ToolStripButton resolveDiscBtn;
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	protected DataSource datasource;

	public TabSurvey() {
		try {
			setTitle(CallCenterBK.constants.menuSurvey());
			setCanClose(true);

			datasource = DataSource.get("SurveyDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(200);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			surveyKindItem = new SelectItem();
			surveyKindItem.setTitle(CallCenterBK.constants.surveyKind());
			surveyKindItem.setWidth(300);
			surveyKindItem.setName("surveyKindItem");

			DataSource surveyTypeDS = DataSource.get("SurveyKindDS");
			surveyKindItem.setOptionOperationId("searchSurveyKindsForCB");
			surveyKindItem.setOptionDataSource(surveyTypeDS);
			surveyKindItem.setValueField("survey_kind_id");
			surveyKindItem.setDisplayField("survey_kind_name");

			surveyKindItem.setOptionCriteria(new Criteria());
			surveyKindItem.setAutoFetchData(false);

			surveyKindItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = surveyKindItem.getOptionCriteria();
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

			searchForm.setFields(surveyKindItem);

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
			toolStrip.setWidth(1300);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			resolveDiscBtn = new ToolStripButton(
					CallCenterBK.constants.resolve(), "yes.png");
			resolveDiscBtn.setLayoutAlign(Alignment.LEFT);
			resolveDiscBtn.setWidth(50);
			toolStrip.addButton(resolveDiscBtn);

			toolStrip.addSeparator();

			listGrid = new ListGrid() {
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
			listGrid.setDataSource(datasource);
			listGrid.setFetchOperation("searchAllSurvey");
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);
			listGrid.setShowRowNumbers(true);

			ListGridField survey_kind_name = new ListGridField(
					"survey_kind_name", CallCenterBK.constants.type(), 100);
			ListGridField p_numb = new ListGridField("p_numb",
					CallCenterBK.constants.phone(), 80);
			ListGridField survey_phone = new ListGridField("survey_phone",
					CallCenterBK.constants.contactPhone(), 120);
			ListGridField survey_person = new ListGridField("survey_person",
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

			resolveDiscBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord gridRecord = listGrid.getSelectedRecord();
					if (gridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					lockDiscRecord(gridRecord);
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					surveyKindItem.clearValue();
				}
			});

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			setPane(mainLayout);
			// start();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void lockDiscRecord(final ListGridRecord listGridRecord) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("survey_id",
					listGridRecord.getAttributeAsInt("survey_id"));
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "takeSurvey");
			listGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					shorSurveyManagerDialog(listGridRecord);
				}
			}, req);

			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void shorSurveyManagerDialog(ListGridRecord listGridRecord) {
		DlgSurveyManager dlgResolveSurvey = new DlgSurveyManager(this,
				datasource, listGridRecord);
		dlgResolveSurvey.show();
	}

	public void search() {
		try {
			Criteria criteria = new Criteria();

			String survey_kind_id = surveyKindItem.getValueAsString();
			if (survey_kind_id != null && !survey_kind_id.trim().equals("")) {
				criteria.setAttribute("survey_kind_id", new Integer(
						survey_kind_id));
			}

			criteria.setAttribute("survery_responce_status", new Integer(0));
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllSurvey");
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
