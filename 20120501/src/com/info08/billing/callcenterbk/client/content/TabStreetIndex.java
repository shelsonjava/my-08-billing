package com.info08.billing.callcenterbk.client.content;

import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.dialogs.address.DlgAddEditStreetIndex;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.shared.common.Constants;
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
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabStreetIndex extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem townsItem;
	private ComboBoxItem streetsItem;
	private TextItem streetIndexRemarkItem;
	private TextItem streetIndexValueItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabStreetIndex() {
		try {
			setTitle("ქუჩის ინდექსების მართვა");
			setCanClose(true);

			datasource = DataSource.get("StreetIndexDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(150);
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			townsItem = new ComboBoxItem();
			townsItem.setTitle("ქალაქი");
			townsItem.setName("town_id");
			townsItem.setWidth(350);
			ClientUtils.fillCombo(townsItem, "TownsDS",
					"searchCitiesFromDBForCombos", "town_id", "town_name");
			townsItem.setValue(Constants.defCityTbilisiId);

			streetsItem = new ComboBoxItem();
			streetsItem.setTitle("ქუჩა");
			streetsItem.setWidth(350);
			streetsItem.setName("street_id");
			streetsItem.setFetchMissingValues(true);
			streetsItem.setFilterLocally(false);
			streetsItem.setAddUnknownValues(false);

			Map<String, Object> aditionalCriteria = new TreeMap<String, Object>();

			aditionalCriteria.put("town_id", Constants.defCityTbilisiId);

			ClientUtils.fillCombo(streetsItem, "StreetsDS",
					"searchStreetFromDBForCombos", "street_id", "street_name",
					aditionalCriteria);

			streetIndexRemarkItem = new TextItem();
			streetIndexRemarkItem.setTitle("კომენტარი");
			streetIndexRemarkItem.setWidth(350);
			streetIndexRemarkItem.setName("street_index_remark");

			streetIndexValueItem = new TextItem();
			streetIndexValueItem.setTitle("ინდექსი");
			streetIndexValueItem.setWidth(350);
			streetIndexValueItem.setName("street_index_value");

			searchForm.setFields(townsItem, streetsItem, streetIndexRemarkItem,
					streetIndexValueItem);
			ClientUtils.makeDependancy(townsItem, "town_id", streetsItem);

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
			toolStrip.setWidth(880);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton("დამატება", "person_add.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton("შეცვლა", "person_edit.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

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
					return super.getCellCSSText(record, rowNum, colNum);
				};
			};

			listGrid.setWidth(880);
			listGrid.setHeight(300);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchStreetIndexesFromDB");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("street_name").setTitle("ქუჩა");
			datasource.getField("street_index_remark").setTitle("კომენტარი");
			datasource.getField("street_index_value").setTitle("ინდექსი");

			ListGridField streetName = new ListGridField("street_name", "ქუჩა",
					250);
			ListGridField street_index_remark = new ListGridField(
					"street_index_remark", "კომენტარი", 100);
			ListGridField street_index_value = new ListGridField(
					"street_index_value", "ინდექსი", 100);

			street_index_value.setAlign(Alignment.CENTER);

			listGrid.setFields(streetName, street_index_remark,
					street_index_value);

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
					streetIndexRemarkItem.clearValue();
					streetIndexValueItem.clearValue();
					streetsItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					new DlgAddEditStreetIndex(listGrid, null).show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					new DlgAddEditStreetIndex(listGrid, listGridRecord).show();
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
					final Integer street_index_id = listGridRecord
							.getAttributeAsInt("street_index_id");
					if (street_index_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(street_index_id);
									}
								}
							});
				}
			});

			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					DlgAddEditStreetIndex dlgAddEditStreetIndex = new DlgAddEditStreetIndex(
							listGrid, listGridRecord);
					dlgAddEditStreetIndex.show();
				}
			});

			setPane(mainLayout);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void search() {
		try {
			String street_id = streetsItem.getValueAsString();
			String street_index_remark = streetIndexRemarkItem
					.getValueAsString();
			String street_index_value = streetIndexValueItem.getValueAsString();

			Criteria criteria = new Criteria();
			criteria.setAttribute("street_id", street_id);
			criteria.setAttribute("street_index_remark", street_index_remark);
			criteria.setAttribute("street_index_value", street_index_value);
			criteria.setAttribute("town_id", townsItem.getValueAsString());

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchStreetIndexesFromDB");
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

	private void delete(Integer street_index_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("street_index_id", street_index_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "deleteStreetIndex");
			listGrid.removeData(record, new DSCallback() {
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
