package com.info08.billing.callcenterbk.client.content;

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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabStreetHist extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem streetOldNameDescrItem;
	private ComboBoxItem streetNameItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton deleteBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabStreetHist() {
		try {
			setTitle("ქუჩის ისტორია");
			setCanClose(true);

			datasource = DataSource.get("StreetOldNamesDS");

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

			streetOldNameDescrItem = new TextItem();
			streetOldNameDescrItem.setTitle("ქუჩის დასახელება(ისტორია)");
			streetOldNameDescrItem.setWidth(300);
			streetOldNameDescrItem.setName("street_old_name_geo");

			streetNameItem = new ComboBoxItem();
			streetNameItem.setTitle("ქუჩის დასახელება");
			streetNameItem.setWidth(300);
			streetNameItem.setName("street_name");
			streetNameItem.setFetchMissingValues(true);
			streetNameItem.setFilterLocally(false);
			streetNameItem.setAddUnknownValues(false);

			searchForm.setFields(streetOldNameDescrItem, streetNameItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(600);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			deleteBtn = new ToolStripButton("გაუქმება", "person_delete.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

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

			listGrid.setWidth(600);
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchFromDB");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("street_old_name_descr").setTitle(
					"ქუჩის დასახელება");
			ListGridField street_old_name_geo = new ListGridField(
					"street_old_name_descr", "დასახელება", 500);

			listGrid.setFields(street_old_name_geo);

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
					streetNameItem.clearValue();
					streetOldNameDescrItem.clearValue();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					final Integer street_old_id = listGridRecord
							.getAttributeAsInt("street_old_id");
					if (street_old_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(street_old_id, 1);
									}
								}
							});
				}
			});

			setPane(mainLayout);
			fillCombos();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	public void fillCombos() throws Exception {
		try {

			DataSource streetsDS = DataSource.get("StreetsNewDS");
			streetNameItem.setOptionOperationId("searchStreetFromDBForCombos");
			streetNameItem.setOptionDataSource(streetsDS);
			streetNameItem.setValueField("street_id");
			streetNameItem.setDisplayField("street_name");

			Criteria criteria = new Criteria();
			streetNameItem.setOptionCriteria(criteria);
			streetNameItem.setAutoFetchData(false);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String street_old_name_geo = streetOldNameDescrItem
					.getValueAsString();
			String street_id = streetNameItem.getValueAsString();
			Criteria criteria = new Criteria();
			criteria.setAttribute("street_old_name_descr", street_old_name_geo);
			criteria.setAttribute("street_id", street_id);

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchFromDB");
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

	private void changeStatus(Integer old_id, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("deleted", deleted);
			record.setAttribute("old_id", old_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updateStreetsOldEntStatus");
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
