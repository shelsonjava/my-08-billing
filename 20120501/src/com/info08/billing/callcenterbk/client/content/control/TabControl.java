package com.info08.billing.callcenterbk.client.content.control;

import java.util.Date;
import java.util.LinkedHashMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyComboboxItemMultClass;
import com.info08.billing.callcenterbk.client.common.components.MyComboboxItemMultiple;
import com.info08.billing.callcenterbk.client.dialogs.control.DlgAddEditSessQuality;
import com.info08.billing.callcenterbk.client.dialogs.control.LogSMSDialog;
import com.info08.billing.callcenterbk.client.dialogs.control.NotesDialog;
import com.info08.billing.callcenterbk.client.dialogs.control.SessDiscHistDialog;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabControl extends Tab {

	private DynamicForm searchForm;
	private SelectItem callTypeItem;
	private ComboBoxItem operatorItem;
	private ComboBoxItem serviceItem;
	private SelectItem numberCondTypeItem;
	private SelectItem sessQualityTypeItem;
	private TextItem numberItem;
	private SelectItem chargedCallItem;
	private DateItem startDateItem;
	private DateItem endDateItem;
	private SpinnerItem numOfSecondsStart;
	private SpinnerItem numOfSecondsEnd;
	private ToolStripButton playButton;
	private ToolStripButton saveFileButton;
	private ToolStripButton remarksButton;
	private ToolStripButton opRemarksButton;
	private ToolStripButton qualityButton;
	private ToolStripButton surveyButton;
	private ToolStripButton smsButton;
	private ToolStripButton exportButton;
	private TimeItem startTimeItem;
	private TimeItem endTimeItem;
	private CheckboxItem checkboxItem;
	private SelectItem operatorItem1;
	private MyComboboxItemMultiple orgActivity;

	private DataSource sessionsDS;
	private DataSource sessionChargesDS;

	private IButton findButton;
	private IButton clearButton;

	private ListGrid sessionsGrid;
	private ListGrid sessionChargesGrid;
	private ListGrid sessionNavigGrid;

	public TabControl() {
		try {

			setTitle("კონტროლის განყოფილება");
			setCanClose(true);

			sessionsDS = DataSource.get("CallSessDS");
			sessionChargesDS = DataSource.get("LogSessChDS");

			VLayout vLayout = new VLayout(5);
			vLayout.setWidth100();
			vLayout.setHeight100();

			searchForm = new DynamicForm();
			searchForm.setPadding(10);
			searchForm.setAutoFocus(true);
			searchForm.setWidth(835);
			searchForm.setTitleWidth(170);
			searchForm.setNumCols(4);

			callTypeItem = new SelectItem();
			callTypeItem.setTitle("ზარის სახეობა");
			callTypeItem.setType("comboBox");
			callTypeItem.setName("callType");

			LinkedHashMap<String, String> mapCallTypes = new LinkedHashMap<String, String>();
			mapCallTypes.put("1", "შემომავალი");
			mapCallTypes.put("2", "გამავალი");
			callTypeItem.setValueMap(mapCallTypes);
			callTypeItem.setDefaultToFirstOption(true);
			callTypeItem.setWidth("100%");

			operatorItem = new ComboBoxItem();
			operatorItem.setTitle("User");
			operatorItem.setWidth("100%");
			operatorItem.setName("user_id");
			
			ClientUtils.fillCombo(operatorItem, "UsersDS",
					"searchUser11", "user_id", "fullPersonName");
			operatorItem.setTextMatchStyle(TextMatchStyle.SUBSTRING);
			
			serviceItem = new ComboBoxItem();
			serviceItem.setTitle("სერვისი");
			serviceItem.setType("comboBox");
			serviceItem.setWidth("100%");
			serviceItem.setName("service_price_id");

			numberItem = new TextItem();
			numberItem.setTitle("ნომერი");
			numberItem.setWidth("100%");
			numberItem.setName("phone");

			chargedCallItem = new SelectItem();
			chargedCallItem.setTitle("ზარების სახეობა");
			chargedCallItem.setType("comboBox");
			chargedCallItem.setName("chargedCall");

			LinkedHashMap<String, String> mapCallKinds = new LinkedHashMap<String, String>();
			mapCallKinds.put("1", "ყველა");
			mapCallKinds.put("2", "დარიცხული");
			mapCallKinds.put("3", "დაურიცხავი");
			chargedCallItem.setValueMap(mapCallKinds);
			chargedCallItem.setDefaultToFirstOption(true);
			chargedCallItem.setWidth("100%");

			startDateItem = new DateItem();
			startDateItem.setTitle("საწყისი თარიღი");
			startDateItem.setWidth(250);
			startDateItem.setValue(new Date());
			startDateItem.setName("startDate");
			startDateItem.setHint("აირჩიეთ");

			endDateItem = new DateItem();
			endDateItem.setTitle("დასრულების თარიღი");
			endDateItem.setWidth(200);
			endDateItem.setValue(new Date());
			endDateItem.setName("endDate");
			endDateItem.setHint("აირჩიეთ");

			numOfSecondsStart = new SpinnerItem();
			numOfSecondsStart.setTitle("წამების რაოდ. (მეტია)");
			numOfSecondsStart.setMin(0);
			numOfSecondsStart.setMax(10000);
			numOfSecondsStart.setStep(1);
			numOfSecondsStart.setWidth("100%");
			numOfSecondsStart.setName("numOfSecondsStart");

			numOfSecondsEnd = new SpinnerItem();
			numOfSecondsEnd.setTitle("წამების რაოდ. (ნაკლებია)");
			numOfSecondsEnd.setMin(0);
			numOfSecondsEnd.setMax(10000);
			numOfSecondsEnd.setStep(1);
			numOfSecondsEnd.setWidth("100%");
			numOfSecondsEnd.setName("numOfSecondsEnd");

			startTimeItem = new TimeItem("timeItem", "Time");
			startTimeItem.setTitle("საწყისი დრო");
			startTimeItem.setWidth("100%");
			startTimeItem.setName("startTime");
			startTimeItem.setHint("");
			startTimeItem.setValue("00:00:00");
			startTimeItem
					.setTimeFormatter(TimeDisplayFormat.TOPADDED24HOURTIME);
			startTimeItem.setUseMask(true);
			startTimeItem.setMask("00:00:00");

			endTimeItem = new TimeItem("timeItem", "Time");
			endTimeItem.setTitle("დასრულების დრო");
			endTimeItem.setWidth("100%");
			endTimeItem.setName("endTime");
			endTimeItem.setHint("");
			endTimeItem.setValue("23:59:59");
			endTimeItem.setTimeFormatter(TimeDisplayFormat.TOPADDED24HOURTIME);
			endTimeItem.setUseMask(true);
			endTimeItem.setMask("00:00:00");

			numberCondTypeItem = new SelectItem();
			numberCondTypeItem.setTitle("ნომრით ძებნის პირობა");
			numberCondTypeItem.setType("comboBox");
			numberCondTypeItem.setName("numberCondTypeItem");

			LinkedHashMap<String, String> numFindCondItems = new LinkedHashMap<String, String>();
			numFindCondItems.put("1", "საწყისით");
			numFindCondItems.put("2", "ნებისმიერი");

			numberCondTypeItem.setValueMap(numFindCondItems);
			numberCondTypeItem.setDefaultToFirstOption(true);
			numberCondTypeItem.setWidth("100%");

			sessQualityTypeItem = new SelectItem();
			sessQualityTypeItem.setTitle("ზარის ხარისხი");
			sessQualityTypeItem.setType("comboBox");
			sessQualityTypeItem.setName("sessQualityTypeItem");

			LinkedHashMap<String, String> sessQualItems = new LinkedHashMap<String, String>();
			sessQualItems.put("0", "მოუსმენელი");
			sessQualItems.put("2", "კარგი");
			sessQualItems.put("1", "ძალიან კარგი");
			sessQualItems.put("3", "ცუდი");
			sessQualItems.put("4", "ჩვეულებრივი");
			sessQualItems.put("5", "მენეჯერის დახმარება");
			sessQualItems.put("-1", "ძალიან ცუდი");
			sessQualItems.put("6", "არასრული");
			sessQualItems.put("100", "ყველა");

			checkboxItem = new CheckboxItem();
			checkboxItem.setTitle("მნიშვნელოვანი ზარი");
			checkboxItem.setName("checkboxItem");
			checkboxItem.setValue(false);

			sessQualityTypeItem.setValueMap(sessQualItems);
			sessQualityTypeItem.setDefaultToFirstOption(true);
			sessQualityTypeItem.setWidth("100%");

			operatorItem1 = new SelectItem();
			operatorItem1.setTitle(CallCenterBK.constants.operator());
			operatorItem1.setWidth("100%");
			operatorItem1.setName("operator_src");
			operatorItem1.setDefaultToFirstOption(true);
			ClientUtils.fillCombo(operatorItem1, "OperatorsDS",
					"searchOperators", "operator_src", "operator_src_descr");
			
			
			orgActivity = new MyComboboxItemMultiple();
			orgActivity.setTitle(CallCenterBK.constants.activity());
			orgActivity.setWidth("100%");
			MyComboboxItemMultClass params = new MyComboboxItemMultClass(
					"OrgActDS", "searchAllBusinesActivitiesForCB",
					"org_activity_id", new String[] { "activity_description" },
					new String[] { CallCenterBK.constants.activity() }, null,
					CallCenterBK.constants.chooseActivity(), 700, 400,
					CallCenterBK.constants.thisRecordAlreadyChoosen());
			orgActivity.setParams(params);

			searchForm.setFields(serviceItem, operatorItem, numberCondTypeItem,
					numberItem, startDateItem, endDateItem, startTimeItem,
					endTimeItem, numOfSecondsStart, numOfSecondsEnd,
					chargedCallItem, sessQualityTypeItem, checkboxItem,
					operatorItem1,orgActivity);

			vLayout.addMember(searchForm);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(835);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);
			buttonLayout.setStyleName("paddingRight");

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);

			vLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(825);
			toolStrip.setPadding(5);

			LinkedHashMap<String, String> palyerTypes = new LinkedHashMap<String, String>();
			palyerTypes.put("1", "Flash Player");

			playButton = new ToolStripButton("მოსმენა", "play.png");
			playButton.setLayoutAlign(Alignment.LEFT);
			playButton.setWidth(50);
			toolStrip.addButton(playButton);

			saveFileButton = new ToolStripButton("გადმოწერა", "download.png");
			saveFileButton.setLayoutAlign(Alignment.LEFT);
			saveFileButton.setWidth(50);
			toolStrip.addButton(saveFileButton);

			remarksButton = new ToolStripButton("შენიშვნა", "comment.png");
			remarksButton.setLayoutAlign(Alignment.LEFT);
			remarksButton.setWidth(50);
			toolStrip.addButton(remarksButton);

			opRemarksButton = new ToolStripButton("შენიშვნა", "person.png");
			opRemarksButton.setLayoutAlign(Alignment.LEFT);
			opRemarksButton.setWidth(50);
			toolStrip.addButton(opRemarksButton);

			qualityButton = new ToolStripButton("ხარისხი", "quality.png");
			qualityButton.setLayoutAlign(Alignment.LEFT);
			qualityButton.setWidth(50);
			toolStrip.addButton(qualityButton);

			surveyButton = new ToolStripButton("გარკვევები", "survey.png");
			surveyButton.setLayoutAlign(Alignment.LEFT);
			surveyButton.setWidth(50);
			toolStrip.addButton(surveyButton);

			smsButton = new ToolStripButton("SMS - ები", "sms.gif");
			smsButton.setLayoutAlign(Alignment.LEFT);
			smsButton.setWidth(50);
			toolStrip.addButton(smsButton);

			toolStrip.addSeparator();

			exportButton = new ToolStripButton("Excel - ში გადატანა",
					"excel.gif");
			exportButton.setLayoutAlign(Alignment.LEFT);
			exportButton.setWidth(50);
			toolStrip.addButton(exportButton);

			final boolean hasRemQualPerm = CommonSingleton.getInstance()
					.hasPermission("101000");
			final boolean hasExcellExpPerm = CommonSingleton.getInstance()
					.hasPermission("102000");

			vLayout.addMember(toolStrip);

			ListGridField phone = new ListGridField("call_phone", "ტელეფონი",
					80);
			ListGridField operator = new ListGridField("uname", "ოპ.", 40);
			ListGridField person_name = new ListGridField("full_user_name",
					"ოპერატორი");
			ListGridField date = new ListGridField("call_start_date",
					"თარიღი/დრო", 130);
			ListGridField duration = new ListGridField("call_duration",
					"ხანგრძლ.", 80);
			ListGridField chargeCount = new ListGridField("chargeCount",
					"რაოდ.", 50);
			ListGridField hangUp = new ListGridField("reject_type",
					"ვინ გათიშა", 90);
			ListGridField session_quality_desc = new ListGridField(
					"call_quality_desc", "ხარისხი");
			ListGridField operator_src = new ListGridField("operator_src",
					CallCenterBK.constants.operator(), 80);

			operator_src.setAlign(Alignment.CENTER);
			date.setAlign(Alignment.CENTER);
			phone.setAlign(Alignment.CENTER);
			duration.setAlign(Alignment.CENTER);
			chargeCount.setAlign(Alignment.CENTER);
			hangUp.setAlign(Alignment.CENTER);
			person_name.setAlign(Alignment.CENTER);

			sessionsGrid = new ListGrid();
			sessionsGrid.setWidth(825);
			sessionsGrid.setHeight(224);
			sessionsGrid.setAlternateRecordStyles(true);
			sessionsGrid.setDataSource(sessionsDS);
			sessionsGrid.setAutoFetchData(false);
			sessionsGrid.setShowFilterEditor(false);
			sessionsGrid.setCanEdit(false);
			sessionsGrid.setCanRemoveRecords(false);
			sessionsGrid.setFetchOperation("customSearch");
			sessionsGrid.setFields(operator_src, date, operator, person_name,
					phone, duration, chargeCount, hangUp, session_quality_desc);
			sessionsGrid.setCanSelectText(true);
			sessionsGrid.setCanDragSelectText(true);
			
			vLayout.addMember(sessionsGrid);

			HLayout hLayout = new HLayout(5);
			hLayout.setWidth(820);
			hLayout.setHeight(150);

			sessionChargesGrid = new ListGrid();
			sessionChargesGrid.setWidth(410);
			sessionChargesGrid.setHeight(200);
			sessionChargesGrid.setAlternateRecordStyles(true);
			sessionChargesGrid.setDataSource(sessionChargesDS);
			sessionChargesGrid.setAutoFetchData(false);
			sessionChargesGrid.setShowFilterEditor(false);
			sessionChargesGrid.setCanEdit(false);
			sessionChargesGrid.setCanRemoveRecords(false);
			sessionChargesGrid.setFetchOperation("custSessCharges");
			hLayout.addMember(sessionChargesGrid);

			sessionNavigGrid = new ListGrid();
			sessionNavigGrid.setWidth(410);
			sessionNavigGrid.setHeight(200);
			sessionNavigGrid.setAlternateRecordStyles(true);
			sessionNavigGrid.setDataSource(sessionChargesDS);
			sessionNavigGrid.setAutoFetchData(false);
			sessionNavigGrid.setShowFilterEditor(false);
			sessionNavigGrid.setCanEdit(false);
			sessionNavigGrid.setCanRemoveRecords(false);
			sessionNavigGrid.setFetchOperation("custSessNavig");
			hLayout.addMember(sessionNavigGrid);

			vLayout.addMember(hLayout);

			ListGridField sChFieldSrvName = new ListGridField(
					"service_description", "სერვისის დასახელება", 150);
			ListGridField sChFieldPrice = new ListGridField("aprice", "ხარჯი",
					40);
			ListGridField sChFieldOrgName = new ListGridField(
					"organization_name", "გაცემული ინფორმაცია", 210);

			sessionChargesGrid.setFields(sChFieldSrvName, sChFieldPrice,
					sChFieldOrgName);

			ListGridField sChFieldSrvName1 = new ListGridField(
					"service_description", "სერვისის დასახელება", 150);
			ListGridField sChFieldOrgName1 = new ListGridField(
					"organization_name", "გაცემული ინფორმაცია", 250);

			sessionNavigGrid.setFields(sChFieldSrvName1, sChFieldOrgName1);

			sessionsGrid
					.addSelectionChangedHandler(new SelectionChangedHandler() {
						public void onSelectionChanged(SelectionEvent event) {
							Record record = event.getRecord();
							Criteria criteria = new Criteria();
							criteria.setAttribute("sessionId",
									record.getAttribute("session_id"));
							sessionChargesGrid.invalidateCache();
							sessionChargesGrid.filterData(criteria);
							sessionNavigGrid.invalidateCache();
							sessionNavigGrid.filterData(criteria);
						}
					});

			findButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			KeyPressHandler kp = new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			};

			numberItem.addKeyPressHandler(kp);

			playButton
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
						@Override
						public void onClick(
								com.smartgwt.client.widgets.events.ClickEvent event) {
							try {

								ListGridRecord listGridRecord = sessionsGrid
										.getSelectedRecord();
								if (listGridRecord == null) {
									SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
									return;
								}
								Integer duration = listGridRecord
										.getAttributeAsInt("call_duration");
								if (duration == null) {
									SC.say("არასწორი ზარის ხანგრძლივობა");
									return;
								}
								String sessionId = listGridRecord
										.getAttributeAsString("session_id");
								Date date = listGridRecord
										.getAttributeAsDate("call_start_date");
								ClientUtils.getURL(sessionId, date);
							} catch (Exception e) {
								SC.say(e.toString());
							}
						}
					});

			saveFileButton
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
						@Override
						public void onClick(
								com.smartgwt.client.widgets.events.ClickEvent event) {
							try {
								ListGridRecord listGridRecord = sessionsGrid
										.getSelectedRecord();
								if (listGridRecord == null) {
									SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
									return;
								}
								String sessionId = listGridRecord
										.getAttributeAsString("session_id");
								Date date = listGridRecord
										.getAttributeAsDate("call_start_date");
								ClientUtils.downloadFile(sessionId, date);
							} catch (Exception e) {
								SC.say(e.toString());
							}
						}
					});

			remarksButton
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
						@Override
						public void onClick(
								com.smartgwt.client.widgets.events.ClickEvent event) {
							if (!hasRemQualPerm) {
								SC.say("თქვენ არ გაქვთ ამ სერვისით სარგებლობის უფლება !");
								return;
							}
							ListGridRecord listGridRecord = sessionsGrid
									.getSelectedRecord();
							if (listGridRecord == null) {
								SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
								return;
							}
							String sessionId = listGridRecord
									.getAttributeAsString("session_id");
							if (sessionId == null
									|| sessionId.trim().equals("")) {
								SC.say("არასწორის სესიის იდენტიფიკატორი : "
										+ sessionId);
								return;
							}
							showRemarksDialog(sessionId, null);
						}
					});
			opRemarksButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (!hasRemQualPerm) {
						SC.say("თქვენ არ გაქვთ ამ სერვისით სარგებლობის უფლება !");
						return;
					}
					String operId = operatorItem.getValueAsString();
					if (operId == null) {
						SC.say("აირჩიეთ ოპერატორი");
						return;
					}
					Integer iOperId = null;
					try {
						iOperId = new Integer(operId);
					} catch (NumberFormatException e) {
						SC.say("არასწორი ოპერატორი. აირჩიეთ ოპერატორი თავიდან");
						return;
					}
					showRemarksDialog(null, iOperId);
				}
			});
			smsButton
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
						@Override
						public void onClick(
								com.smartgwt.client.widgets.events.ClickEvent event) {

							ListGridRecord listGridRecord = sessionsGrid
									.getSelectedRecord();
							if (listGridRecord == null) {
								SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
								return;
							}
							String sessionId = listGridRecord
									.getAttributeAsString("session_id");
							LogSMSDialog addEditNoteDialog = new LogSMSDialog(
									sessionId);
							addEditNoteDialog.show();
						}
					});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					sessionsGrid.clearCriteria();
					searchForm.clearValues();
					startDateItem.setValue(new Date());
					endDateItem.setValue(new Date());
					startTimeItem.setValue("00:00:00");
					endTimeItem.setValue("23:59:59");
					chargedCallItem.setValue(1);
					sessQualityTypeItem.setValue(0);
					checkboxItem.setValue(false);

					Criteria criteria = sessionsGrid.getCriteria();
					if (criteria == null) {
						criteria = new Criteria();
					}
					Criteria formCriteria = searchForm.getValuesAsCriteria();
					criteria.addCriteria(formCriteria);
					orgActivity.clearValues();
				}
			});

			exportButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (!hasExcellExpPerm) {
						SC.say("თქვენ არ გაქვთ ამ სერვისით სარგებლობის უფლება !");
						return;
					}

					com.smartgwt.client.data.DSRequest dsRequestProperties = new com.smartgwt.client.data.DSRequest();
					dsRequestProperties.setExportAs((ExportFormat) EnumUtil
							.getEnum(ExportFormat.values(), "xls"));
					dsRequestProperties
							.setExportDisplay(ExportDisplay.DOWNLOAD);
					dsRequestProperties.setOperationId("customSearch");
					sessionsGrid.exportData(dsRequestProperties);
				}
			});

			surveyButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = sessionsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
						return;
					}
					String sessionId = listGridRecord
							.getAttributeAsString("session_id");
					SessDiscHistDialog addEditNoteDialog = new SessDiscHistDialog(
							sessionId);
					addEditNoteDialog.show();
				}
			});

			qualityButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (!hasRemQualPerm) {
						SC.say("თქვენ არ გაქვთ ამ სერვისით სარგებლობის უფლება !");
						return;
					}
					ListGridRecord listGridRecord = sessionsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
						return;
					}
					String sessionId = listGridRecord
							.getAttributeAsString("session_id");
					Integer quality = listGridRecord
							.getAttributeAsInt("call_quality");
					Integer call_session_id = listGridRecord
							.getAttributeAsInt("call_session_id");

					DlgAddEditSessQuality addEditSessQuality = new DlgAddEditSessQuality(
							sessionId, quality, call_session_id, sessionsDS);
					addEditSessQuality.show();
				}
			});
			fillCombos();
			setPane(vLayout);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void showRemarksDialog(String sessionId, Integer operatorId) {
		try {
			NotesDialog notesDialog = new NotesDialog(sessionId, operatorId);
			notesDialog.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void fillCombos() {
		try {
			DataSource services = CommonSingleton.getInstance().getServicesDS();
			if (services != null) {
				serviceItem.setOptionDataSource(services);
				serviceItem.setDisplayField("service_description");
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void search() {
		Criteria criteria = sessionsGrid.getCriteria();
		try {
			if (criteria == null) {
				criteria = new Criteria();
			}
			Criteria formCriteria = searchForm.getValuesAsCriteria();
			criteria.addCriteria(formCriteria);
			String operId = operatorItem.getValueAsString();
			if (operId != null && !operId.trim().equals("")) {
				criteria.setAttribute("user_id", new Integer(operId));
			}
			String serviceId = serviceItem.getValueAsString();
			if (serviceId != null && !serviceId.trim().equals("")) {
				criteria.setAttribute("serviceId", new Integer(serviceId));
			}
			Boolean isImportant = checkboxItem.getValueAsBoolean();
			if (isImportant != null && isImportant.booleanValue()) {
				criteria.setAttribute("isImportant", isImportant);
			}
			
			Integer operator_src = Integer.parseInt(operatorItem1.getValueAsString());
			criteria.setAttribute("operator_src", operator_src);
			
			MyComboboxItemMultClass orgActivities = orgActivity.getParamClass();
			
			Record records[] = orgActivities.getValueRecords();
			
			if(records!=null && records.length>0){
				String orgActivitiesCrit = "";
				int i = 0;
				for (Record record : records) {
					Integer org_activity_id = record
							.getAttributeAsInt("org_activity_id");
					if (i > 0) {
						orgActivitiesCrit += ",";
					}
					orgActivitiesCrit += org_activity_id;
					i++;
				}
				criteria.setAttribute("orgActivitiesCrit", orgActivitiesCrit);
			}

			sessionsGrid.invalidateCache();
			sessionsGrid.filterData(criteria);
			sessionChargesGrid.invalidateCache();
		} catch (Exception e) {
			e.printStackTrace();
			SC.warn(e.toString());
		}
	}
}
