package com.info08.billing.callcenterbk.client.content.misc;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.misc.DlgAddEditWebSite;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class TabWebSite extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem mainDetTypeItem;
	private TextItem mainDetailGeoItem;
	private TextItem mainDetailEngItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton disableBtn;
	private ToolStripButton activateBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabWebSite() {
		try {
			setTitle(CallCenterBK.constants.menuWebSites());
			setCanClose(true);

			datasource = DataSource.get("MainDetailDS");

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
			
			mainDetTypeItem = new ComboBoxItem();
			mainDetTypeItem.setTitle(CallCenterBK.constants.group());
			mainDetTypeItem.setWidth(300);
			mainDetTypeItem.setName("main_detail_type_name_geo");
			mainDetTypeItem.setFetchMissingValues(true);
			mainDetTypeItem.setFilterLocally(false);
			mainDetTypeItem.setAddUnknownValues(false);

			DataSource mainDetTypeDS = DataSource.get("MainDetTypeDS");
			mainDetTypeItem.setOptionOperationId("searchMainDetailTypesFirWebSites");
			mainDetTypeItem.setOptionDataSource(mainDetTypeDS);
			mainDetTypeItem.setValueField("main_detail_type_id");
			mainDetTypeItem.setDisplayField("main_detail_type_name_geo");

			mainDetTypeItem.setOptionCriteria(new Criteria());
			mainDetTypeItem.setAutoFetchData(false);

			mainDetTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = mainDetTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("main_detail_type_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("main_detail_type_id", nullO);
						}
					}
				}
			});
			
			mainDetailGeoItem = new TextItem();
			mainDetailGeoItem.setTitle(CallCenterBK.constants.description());
			mainDetailGeoItem.setName("main_detail_geo");
			mainDetailGeoItem.setWidth(300);
			
			mainDetailEngItem = new TextItem();
			mainDetailEngItem.setTitle(CallCenterBK.constants.webSite());
			mainDetailEngItem.setName("main_detail_eng");
			mainDetailEngItem.setWidth(300);

			searchForm.setFields(mainDetTypeItem, mainDetailGeoItem,mainDetailEngItem);

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
			toolStrip.setWidth(780);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenterBK.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton(CallCenterBK.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			disableBtn = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			disableBtn.setLayoutAlign(Alignment.LEFT);
			disableBtn.setWidth(50);
			toolStrip.addButton(disableBtn);

			activateBtn = new ToolStripButton(CallCenterBK.constants.enable(),
					"restoreIcon.gif");
			activateBtn.setLayoutAlign(Alignment.LEFT);
			activateBtn.setWidth(50);
			toolStrip.addButton(activateBtn);

			toolStrip.addSeparator();

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer deleted = countryRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && !deleted.equals(0)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			listGrid.setWidth(780);
			listGrid.setHeight(320);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchMainDetails");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("main_detail_type_name_geo").setTitle(CallCenterBK.constants.group());
			datasource.getField("main_detail_geo").setTitle(CallCenterBK.constants.description());
			datasource.getField("main_detail_eng").setTitle(CallCenterBK.constants.webSite());
			datasource.getField("rec_date").setTitle(CallCenterBK.constants.recDate());
			datasource.getField("rec_user").setTitle(CallCenterBK.constants.recUser());
			datasource.getField("upd_date").setTitle(CallCenterBK.constants.updDate());
			datasource.getField("upd_user").setTitle(CallCenterBK.constants.updUser());
			datasource.getField("main_detail_note_geo").setHidden(true);
			datasource.getField("main_detail_note_eng").setHidden(true);

			ListGridField main_detail_type_name_geo = new ListGridField("main_detail_type_name_geo",CallCenterBK.constants.group(), 180);
			ListGridField main_detail_geo = new ListGridField("main_detail_geo",CallCenterBK.constants.description(), 300);
			ListGridField main_detail_eng = new ListGridField("main_detail_eng",CallCenterBK.constants.webSite(), 250);

			main_detail_type_name_geo.setAlign(Alignment.LEFT);
			main_detail_geo.setAlign(Alignment.LEFT);

			listGrid.setFields(main_detail_type_name_geo, main_detail_geo, main_detail_eng);

			mainLayout.addMember(listGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					mainDetTypeItem.clearValue();
					mainDetailGeoItem.clearValue();
					mainDetailEngItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditWebSite dlgEditDlgAddEditWebSite = new DlgAddEditWebSite(
							listGrid, null);
					dlgEditDlgAddEditWebSite.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditWebSite dlgEditDlgAddEditWebSite = new DlgAddEditWebSite(
							listGrid, listGridRecord);
					dlgEditDlgAddEditWebSite.show();
				}
			});
			disableBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (!deleted.equals(0)) {
						SC.say(CallCenterBK.constants.recordAlrDisabled());
						return;
					}
					SC.ask(CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(listGridRecord, 1);
									}
								}
							});
				}
			});
			activateBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (deleted.equals(0)) {
						SC.say(CallCenterBK.constants.recordAlrEnabled());
						return;
					}
					SC.ask(CallCenterBK.constants.askForEnable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(listGridRecord, 0);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(780);
			Tab tabDetViewer = new Tab(CallCenterBK.constants.view());
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasource);
			detailViewer.setWidth(750);
			tabDetViewer.setPane(detailViewer);

			listGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(listGrid);
				}
			});

			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					DlgAddEditWebSite dlgEditDlgAddEditWebSite = new DlgAddEditWebSite(
							listGrid, listGridRecord);
					dlgEditDlgAddEditWebSite.show();
				}
			});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("service_id", 63);
			String main_detail_type_id = mainDetTypeItem.getValueAsString();
			if (main_detail_type_id != null && !main_detail_type_id.trim().equals("")) {
				criteria.setAttribute("main_detail_type_id", new Integer(main_detail_type_id));
			}
			String main_detail_geo = mainDetailGeoItem.getValueAsString();
			if (main_detail_geo != null && !main_detail_geo.trim().equals("")) {
				criteria.setAttribute("main_detail_geo", main_detail_geo);
			}
			String main_detail_eng = mainDetailEngItem.getValueAsString();
			if (main_detail_eng != null && !main_detail_eng.trim().equals("")) {
				criteria.setAttribute("main_detail_eng", main_detail_eng);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchMainDetails");
			listGrid.invalidateCache();
			listGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void changeStatus(ListGridRecord listGridRecord, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance().getSessionPerson().getUser_name());
			record.setAttribute("deleted", deleted);
			record.setAttribute("service_id", 63);
			record.setAttribute("rec_user", CommonSingleton.getInstance().getSessionPerson().getUser_name());
			record.setAttribute("main_detail_type_id",listGridRecord.getAttributeAsInt("main_detail_type_id"));
			record.setAttribute("main_detail_geo", listGridRecord.getAttributeAsString("main_detail_geo"));
			record.setAttribute("main_detail_eng", listGridRecord.getAttributeAsString("main_detail_eng"));
			record.setAttribute("fields_order", 0);
			record.setAttribute("main_detail_master_id", 0);
			record.setAttribute("main_id", -100);
			record.setAttribute("old_id", 0);
			record.setAttribute("main_detail_id",listGridRecord.getAttributeAsInt("main_detail_id"));
			record.setAttribute("main_id",listGridRecord.getAttributeAsInt("main_id"));
			
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateMainDetailStatus");
			listGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
