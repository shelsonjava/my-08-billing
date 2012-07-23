package com.info08.billing.callcenterbk.client.content;

import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditStreetOldNames;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
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

public class TabStreetOldNames extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem streetOldNameDescrItem;
	private ComboBoxItem addrTownItem;
	private ComboBoxItem addrStreetItem;

	// private ComboBoxItem streetNameItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton manageBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabStreetOldNames() {
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

			addrTownItem = new ComboBoxItem();
			addrTownItem.setTitle(CallCenterBK.constants.town());
			addrTownItem.setName("town_id");
			addrTownItem.setWidth(300);
			ClientUtils.fillCombo(addrTownItem, "TownsDS",
					"searchCitiesFromDBForCombos", "town_id", "town_name");

			addrStreetItem = new ComboBoxItem();
			addrStreetItem.setTitle(CallCenterBK.constants.street());
			addrStreetItem.setName("streets_id");
			addrStreetItem.setWidth(300);

			Map<String, Integer> aditionalCriteria = new TreeMap<String, Integer>();
			aditionalCriteria.put("town_id", Constants.defCityTbilisiId);
			aditionalCriteria.put("need_indexes", 1);

			ClientUtils.fillCombo(addrStreetItem, "StreetsDS",
					"searchStreetFromDBForCombos", "streets_id", "street_name",
					aditionalCriteria);

			ClientUtils.makeDependancy(addrTownItem, "town_id", addrStreetItem);

			searchForm.setFields(streetOldNameDescrItem, addrTownItem,
					addrStreetItem);

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

			manageBtn = new ToolStripButton("მართვა", "manage.png");
			manageBtn.setLayoutAlign(Alignment.LEFT);
			manageBtn.setWidth(50);
			toolStrip.addButton(manageBtn);

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

			listGrid.addDoubleClickHandler(new DoubleClickHandler() {
				@Override
				public void onDoubleClick(DoubleClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					DlgAddEditStreetOldNames dlgAddEditStreetOldNames = new DlgAddEditStreetOldNames(
							TabStreetOldNames.this, listGrid, listGridRecord);
					dlgAddEditStreetOldNames.show();

				}
			});

			datasource.getField("street_old_name_descr").setTitle(
					"ქუჩის დასახელება");
			ListGridField street_old_name_geo = new ListGridField(
					"street_old_name_descr", "დასახელება", 500);

			listGrid.setFields(street_old_name_geo);

			mainLayout.addMember(listGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search(false);
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					addrStreetItem.clearValue();
					streetOldNameDescrItem.clearValue();
				}
			});
			manageBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					DlgAddEditStreetOldNames dlgAddEditStreetOldNames = new DlgAddEditStreetOldNames(
							TabStreetOldNames.this, listGrid, listGridRecord);
					dlgAddEditStreetOldNames.show();
				}
			});
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	public void search(boolean refresh) {
		try {
			String street_old_name_geo = streetOldNameDescrItem
					.getValueAsString();
			String street_id = addrStreetItem.getValueAsString();
			String town_id = addrTownItem.getValueAsString();
			Criteria criteria = new Criteria();
			criteria.setAttribute("street_old_name_descr", street_old_name_geo);
			criteria.setAttribute("street_id", street_id);
			criteria.setAttribute("town_id", town_id);

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

}
