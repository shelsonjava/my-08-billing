package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.Date;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.control.NotesDialog;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgHistOrgPriority extends Window {

	private VLayout hLayout;
	private DynamicForm searchForm;

	private DateItem histDate;
	private TextItem organizationNameItem;

	private ButtonItem findButton;

	private ListGrid histGroupListGrid;
	private ListGrid histSessionsListGrid;
	private DataSource orgPrioritiesStatisticsDS;

	public DlgHistOrgPriority() {
		try {
			setWidth(750);
			setHeight(660);
			setTitle("გაწითლებული ორგანიზაციების ზარები");
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

			Date startDate = new Date();
			CalendarUtil.addMonthsToDate(startDate, -1);

			orgPrioritiesStatisticsDS = DataSource
					.get("OrgPrioritiesStatisticsDS");

			histDate = new DateItem();
			histDate.setTitle(CallCenterBK.constants.date());
			histDate.setName("histDate");
			histDate.setWidth(300);

			organizationNameItem = new TextItem();
			organizationNameItem.setTitle(CallCenterBK.constants.orgNameFull());
			organizationNameItem.setName("organizationNameItem");
			organizationNameItem.setWidth(300);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth100();
			searchForm.setNumCols(3);
			searchForm.setPadding(5);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			findButton = new ButtonItem();
			findButton.setTitle(CallCenterBK.constants.find());

			findButton.setStartRow(false);
			findButton.setEndRow(false);
			searchForm.setFields(histDate, organizationNameItem, findButton);

			searchForm.setBorder("1px solid #CCC");

			histGroupListGrid = new ListGrid();
			histGroupListGrid.setWidth100();
			histGroupListGrid.setHeight(320);
			histGroupListGrid.setAlternateRecordStyles(true);
			histGroupListGrid.setDataSource(orgPrioritiesStatisticsDS);
			histGroupListGrid.setAutoFetchData(false);
			histGroupListGrid.setShowFilterEditor(false);
			histGroupListGrid.setCanEdit(false);
			histGroupListGrid.setCanRemoveRecords(false);
			histGroupListGrid.setFetchOperation("getStatisticsGroupNew");
			histGroupListGrid.setCanSort(false);
			histGroupListGrid.setCanResizeFields(false);
			histGroupListGrid.setWrapCells(true);
			histGroupListGrid.setFixedRecordHeights(false);
			histGroupListGrid.setCanDragSelectText(true);
			histGroupListGrid.setShowRowNumbers(true);

			ListGridField real_name = new ListGridField("real_name",
					CallCenterBK.constants.organization());
			real_name.setAlign(Alignment.LEFT);

			ListGridField cnt = new ListGridField("cnt",
					CallCenterBK.constants.count(), 50);
			cnt.setAlign(Alignment.LEFT);

			histGroupListGrid.setFields(real_name, cnt);

			histSessionsListGrid = new ListGrid();
			histSessionsListGrid.setWidth100();
			histSessionsListGrid.setHeight(180);
			histSessionsListGrid.setAlternateRecordStyles(true);
			histSessionsListGrid.setDataSource(orgPrioritiesStatisticsDS);
			histSessionsListGrid.setAutoFetchData(false);
			histSessionsListGrid.setShowFilterEditor(false);
			histSessionsListGrid.setCanEdit(false);
			histSessionsListGrid.setCanRemoveRecords(false);
			histSessionsListGrid.setFetchOperation("getStatisticsNew");
			histSessionsListGrid.setCanSort(false);
			histSessionsListGrid.setCanResizeFields(false);
			histSessionsListGrid.setWrapCells(true);
			histSessionsListGrid.setFixedRecordHeights(false);
			histSessionsListGrid.setCanDragSelectText(true);
			histSessionsListGrid.setShowRowNumbers(true);

			ListGridField phones = new ListGridField("call_phone",
					CallCenterBK.constants.phone(), 100);
			phones.setAlign(Alignment.LEFT);

			ListGridField ph_hist_user_on = new ListGridField("uname",
					CallCenterBK.constants.username());
			ph_hist_user_on.setWidth(80);

			ListGridField ph_hist_start = new ListGridField("call_start_date",
					"-დან");
			ph_hist_start.setWidth(100);

			ListGridField ph_hist_end = new ListGridField("call_end_date",
					"-მდე");
			ph_hist_end.setWidth(100);

			histSessionsListGrid.setFields(phones, ph_hist_user_on,
					ph_hist_start, ph_hist_end);

			hLayout.addMember(searchForm);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);

			ToolStripButton saveFileButton = new ToolStripButton("გადმოწერა",
					"download.png");
			saveFileButton.setLayoutAlign(Alignment.LEFT);
			saveFileButton.setWidth(50);
			toolStrip.addButton(saveFileButton);

			saveFileButton
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
						@Override
						public void onClick(
								com.smartgwt.client.widgets.events.ClickEvent event) {
							try {
								ListGridRecord listGridRecord = histSessionsListGrid
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

			ToolStripButton playButton = new ToolStripButton("მოსმენა",
					"play.png");
			playButton.setLayoutAlign(Alignment.LEFT);
			playButton.setWidth(50);
			toolStrip.addButton(playButton);

			playButton
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
						@Override
						public void onClick(
								com.smartgwt.client.widgets.events.ClickEvent event) {
							try {

								ListGridRecord listGridRecord = histSessionsListGrid
										.getSelectedRecord();
								if (listGridRecord == null) {
									SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
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

			ToolStripButton remarksButton = new ToolStripButton("შენიშვნა",
					"comment.png");
			remarksButton.setLayoutAlign(Alignment.LEFT);
			remarksButton.setWidth(50);
			toolStrip.addButton(remarksButton);

			ToolStripButton reportButton = new ToolStripButton("რეპორტი",
					"excel.gif");
			reportButton.setLayoutAlign(Alignment.LEFT);
			reportButton.setWidth(50);
			toolStrip.addButton(reportButton);

			reportButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					searchSessions(true);

				}
			});

			remarksButton
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
						@Override
						public void onClick(
								com.smartgwt.client.widgets.events.ClickEvent event) {

							ListGridRecord listGridRecord = histSessionsListGrid
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

			hLayout.addMember(histGroupListGrid);
			hLayout.addMember(toolStrip);
			hLayout.addMember(histSessionsListGrid);

			findButton
					.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
						@Override
						public void onClick(
								com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
							search();

						}
					});

			organizationNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			histGroupListGrid.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					searchSessions(false);
				}
			});

			addItem(hLayout);
			addVisibilityChangedHandler(new VisibilityChangedHandler() {

				@Override
				public void onVisibilityChanged(VisibilityChangedEvent event) {
					if (!event.getIsVisible())
						destroy();

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {

			Criteria criteria = new Criteria();

			criteria.setAttribute("time", histDate.getValueAsDate());
			criteria.setAttribute("organization_name",
					organizationNameItem.getValueAsString());
			Date vHistDate = histDate.getValueAsDate();
			criteria.setAttribute("vHistDate", vHistDate);
			
			searchByCriteria(criteria);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	protected void searchByCriteria(Criteria criteria) {
		DSRequest dsRequest = new DSRequest();
		dsRequest.setAttribute("operationId", "getStatisticsGroupNew");
		histGroupListGrid.filterData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				// histGroupListGrid.getGroupTree().openAll();
				if (response.getData() != null && response.getData().length > 0) {
					histGroupListGrid.selectRecord(0);
					searchSessions(false);
				}
			}
		}, dsRequest);
	}

	private void searchSessions(boolean export) {
		try {
			ListGridRecord gridRecord = histGroupListGrid.getSelectedRecord();
			Criteria criteria = new Criteria();
			if (gridRecord != null) {
				criteria.setAttribute("real_org_id",new Integer(gridRecord.getAttribute("real_org_id")));
				Date vHistDate = histDate.getValueAsDate();
				criteria.setAttribute("vHistDate", vHistDate);
			} else {
				return;
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "getStatisticsNew");
			if (export) {
				dsRequest.setExportAs(ExportFormat.XLS);

				dsRequest.setExportFields(new String[] { "call_phone",
						"call_start_date", "call_end_date", "uname" });
				dsRequest.setExportHeader(gridRecord.getAttribute("real_name")
						+ "                    სულ:"
						+ gridRecord.getAttribute("cnt"));
				// dsRequest.setExportFooter();
				histSessionsListGrid.getDataSource().exportData(criteria,
						dsRequest, new DSCallback() {

							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								// TODO Auto-generated method stub

							}
						});
			} else
				histSessionsListGrid.filterData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {

					}
				}, dsRequest);
		} catch (Exception e) {
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

}
