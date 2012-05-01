package com.info08.billing.callcenterbk.client.content.control;

import java.util.Date;
import java.util.LinkedHashMap;

import com.bramosystems.oss.player.core.client.LoadException;
import com.bramosystems.oss.player.core.client.PluginNotFoundException;
import com.bramosystems.oss.player.core.client.PluginVersionException;
import com.bramosystems.oss.player.core.client.ui.FlashMediaPlayer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenterbk.client.CallCenter;
import com.info08.billing.callcenterbk.client.dialogs.control.DlgAddEditSessQuality;
import com.info08.billing.callcenterbk.client.dialogs.control.LogSMSDialog;
import com.info08.billing.callcenterbk.client.dialogs.control.NotesDialog;
import com.info08.billing.callcenterbk.client.dialogs.control.SessDiscHistDialog;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.LinkItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
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
	private ToolStripButton discoveryButton;
	private ToolStripButton smsButton;
	private ToolStripButton exportButton;
	private TimeItem startTimeItem;
	private TimeItem endTimeItem;
	private DataSource sessionsDS;
	private DataSource sessionChargesDS;

	private IButton findButton;
	private IButton clearButton;

	public TabControl() {
		try {

			setTitle("კონტროლის განყოფილება");
			setCanClose(true);

			sessionsDS = DataSource.get("LogSessDS");
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
			operatorItem.setTitle("ოპერატორი");
			operatorItem.setType("comboBox");
			operatorItem.setWidth("100%");
			operatorItem.setName("personelId");

			serviceItem = new ComboBoxItem();
			serviceItem.setTitle("სერვისი");
			serviceItem.setType("comboBox");
			serviceItem.setWidth("100%");
			serviceItem.setName("serviceId");

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
			sessQualItems.put("100", "ყველა");

			sessQualityTypeItem.setValueMap(sessQualItems);
			sessQualityTypeItem.setDefaultToFirstOption(true);
			sessQualityTypeItem.setWidth("100%");

			searchForm.setFields(serviceItem, operatorItem, numberCondTypeItem,
					numberItem, startDateItem, endDateItem, startTimeItem,
					endTimeItem, numOfSecondsStart, numOfSecondsEnd,
					chargedCallItem, sessQualityTypeItem);

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

			discoveryButton = new ToolStripButton("გარკვევები", "discovery.png");
			discoveryButton.setLayoutAlign(Alignment.LEFT);
			discoveryButton.setWidth(50);
			toolStrip.addButton(discoveryButton);

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

			ListGridField phone = new ListGridField("phone", "ტელეფონი", 80);
			ListGridField operator = new ListGridField("user_name", "ოპ.", 40);
			ListGridField person_name = new ListGridField("person_name",
					"ოპერატორი", 230);
			ListGridField date = new ListGridField("start_date", "თარიღი/დრო",
					130);
			ListGridField duration = new ListGridField("duration", "ხანგრძლ.",
					80);
			ListGridField chargeCount = new ListGridField("chargeCount",
					"რაოდ.", 50);
			ListGridField hangUp = new ListGridField("hangUp", "ვინ გათიშა", 90);
			ListGridField session_quality_desc = new ListGridField(
					"session_quality_desc", "ხარისხი");

			date.setAlign(Alignment.CENTER);
			phone.setAlign(Alignment.CENTER);
			duration.setAlign(Alignment.CENTER);
			chargeCount.setAlign(Alignment.CENTER);
			hangUp.setAlign(Alignment.CENTER);
			person_name.setAlign(Alignment.CENTER);

			final ListGrid sessionsGrid = new ListGrid();
			sessionsGrid.setWidth(825);
			sessionsGrid.setHeight(224);
			sessionsGrid.setAlternateRecordStyles(true);
			sessionsGrid.setDataSource(sessionsDS);
			sessionsGrid.setAutoFetchData(false);
			sessionsGrid.setShowFilterEditor(false);
			sessionsGrid.setCanEdit(false);
			sessionsGrid.setCanRemoveRecords(false);
			sessionsGrid.setFetchOperation("customSearch");
			sessionsGrid.setFields(date, operator, person_name, phone,
					duration, chargeCount, hangUp, session_quality_desc);

			vLayout.addMember(sessionsGrid);

			HLayout hLayout = new HLayout(5);
			hLayout.setWidth(820);
			hLayout.setHeight(150);

			final ListGrid sessionChargesGrid = new ListGrid();
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

			final ListGrid sessionNavigGrid = new ListGrid();
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
					"service_name_geo", "სერვისის დასახელება", 150);
			ListGridField sChFieldPrice = new ListGridField("price", "ხარჯი",
					40);
			ListGridField sChFieldOrgName = new ListGridField("org_name",
					"ორგანიზაციის დასახელება", 210);

			sessionChargesGrid.setFields(sChFieldSrvName, sChFieldPrice,
					sChFieldOrgName);

			ListGridField sChFieldSrvName1 = new ListGridField(
					"service_name_geo", "სერვისის დასახელება", 150);
			ListGridField sChFieldOrgName1 = new ListGridField("org_name",
					"ორგანიზაციის დასახელება", 250);

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
					Criteria criteria = sessionsGrid.getCriteria();
					if (criteria == null) {
						criteria = new Criteria();
					}
					Criteria formCriteria = searchForm.getValuesAsCriteria();
					criteria.addCriteria(formCriteria);
					String operId = operatorItem.getValueAsString();
					if (operId != null && !operId.trim().equals("")) {
						criteria.setAttribute("personelId", new Integer(operId));
					}
					String serviceId = serviceItem.getValueAsString();
					if (serviceId != null && !serviceId.trim().equals("")) {
						criteria.setAttribute("serviceId", new Integer(
								serviceId));
					}
					sessionsGrid.invalidateCache();
					sessionsGrid.filterData(criteria);
					sessionChargesGrid.invalidateCache();
				}
			});

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
										.getAttributeAsInt("duration");
								if (duration == null) {
									SC.say("არასწორი ზარის ხანგრძლივობა");
									return;
								}
								// if (duration <= 0) {
								// SC.say("მოსმენა შეუძლებელია. ზარის ხანგრძლივობა არის : "
								// + duration);
								// return;
								// }
								String sessionId = listGridRecord
										.getAttributeAsString("session_id");
								Date date = listGridRecord
										.getAttributeAsDate("start_date");
								getURL(sessionId, date);
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
										.getAttributeAsDate("start_date");
								downloadFile(sessionId, date);
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

					Criteria criteria = sessionsGrid.getCriteria();
					if (criteria == null) {
						criteria = new Criteria();
					}
					Criteria formCriteria = searchForm.getValuesAsCriteria();
					criteria.addCriteria(formCriteria);
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

			discoveryButton.addClickHandler(new ClickHandler() {
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
							.getAttributeAsInt("session_quality");

					DlgAddEditSessQuality addEditSessQuality = new DlgAddEditSessQuality(
							sessionId, quality, sessionsDS);
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

	private void downloadFile(String sessionId, Date date) {
		try {
			CallCenter.commonService.findSessionMp3ById(sessionId, date,
					new AsyncCallback<String>() {

						@Override
						public void onSuccess(String result) {
							if (result == null || result.trim().equals("")) {
								SC.say("სესიის მისამართი ვერ მოიძებნა : "
										+ result);
								return;
							}
							getDownloadFile(result);
						}

						@Override
						public void onFailure(Throwable caught) {
							SC.say(caught.toString());
						}
					});
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void getURL(String sessionId, Date date) {
		try {
			CallCenter.commonService.findSessionMp3ById(sessionId, date,
					new AsyncCallback<String>() {

						@Override
						public void onSuccess(String result) {
							if (result == null || result.trim().equals("")) {
								SC.say("სესიის მისამართი ვერ მოიძებნა : "
										+ result);
								return;
							}
							playSessionRecord(result);
						}

						@Override
						public void onFailure(Throwable caught) {
							SC.say(caught.toString());
						}
					});
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void getDownloadFile(String url) {
		try {
			Window window = new Window();
			window.setWidth(350);
			window.setHeight(70);
			window.setTitle("ფაილის გადმოწერა");
			window.setIsModal(true);
			window.setShowCloseButton(true);
			window.setCanDrag(false);
			window.setCanDragReposition(false);
			window.setCanDragResize(false);
			window.setCanDragScroll(false);
			window.centerInPage();

			HLayout hLayout = new HLayout();
			hLayout.setWidth100();
			hLayout.setHeight100();
			hLayout.setPadding(10);

			DynamicForm dynamicForm = new DynamicForm();
			dynamicForm.setWidth100();
			dynamicForm.setHeight100();
			dynamicForm.setTitleWidth(150);

			LinkItem linkItem = new LinkItem();
			linkItem.setTitle("ფაილის მისამართი");
			linkItem.setLinkTitle("გადმოწერეთ ფაილი");
			linkItem.setValue(url);

			dynamicForm.setFields(linkItem);

			hLayout.addMember(dynamicForm);

			window.addItem(hLayout);
			window.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void playSessionRecord(String url) {
		try {
			final Window winModal = new Window();
			winModal.setWidth(500);
			winModal.setHeight(80);
			winModal.setTitle("ჩანაწერის მოსმენა");
			winModal.setShowMinimizeButton(false);
			winModal.setIsModal(true);
			winModal.setShowModalMask(true);
			winModal.centerInPage();
			winModal.addCloseClickHandler(new CloseClickHandler() {
				@Override
				public void onCloseClick(CloseClickEvent event) {
					winModal.destroy();
				}
			});
			FlashMediaPlayer player = null;
			HLayout hLayout = new HLayout();
			hLayout.setWidth100();
			hLayout.setHeight100();
			player = new FlashMediaPlayer(url);

			hLayout.addMember(player);
			winModal.addItem(hLayout);
			winModal.show();
			// player.setVolume(1d);
		} catch (LoadException e) {
			SC.say(e.getMessage());
			return;
		} catch (PluginVersionException e) {
			SC.say(e.getMessage());
			return;
		} catch (PluginNotFoundException e) {
			SC.say(e.getMessage());
			return;
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void fillCombos() {
		try {
			DataSource persons = CommonSingleton.getInstance().getPersonsDS();
			DataSource services = CommonSingleton.getInstance().getServicesDS();
			if (persons != null) {
				operatorItem.setOptionOperationId("customPersSearch");
				operatorItem.setOptionDataSource(persons);
				operatorItem.setValueField("personelId");
				operatorItem.setDisplayField("fullPersonName");
			}
			if (services != null) {
				serviceItem.setOptionDataSource(services);
				serviceItem.setDisplayField("serviceNameGeo");
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
