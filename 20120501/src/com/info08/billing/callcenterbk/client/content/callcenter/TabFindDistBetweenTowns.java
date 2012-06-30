package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewDistBetweenTowns;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabFindDistBetweenTowns extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private ComboBoxItem townFromItem;
	private ComboBoxItem townToItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource distBetweenTowns;

	public TabFindDistBetweenTowns() {
		setTitle(CallCenterBK.constants.distBetweenCities());
		setCanClose(true);

		distBetweenTowns = DataSource.get("DistBetweenTownsDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(false);
		searchForm.setWidth(750);
		searchForm.setNumCols(3);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(searchForm);

		townFromItem = new ComboBoxItem();
		townFromItem.setTitle(CallCenterBK.constants.townFrom());
		townFromItem.setName("townFromItem");
		townFromItem.setWidth(375);
		townFromItem.setFetchMissingValues(true);
		townFromItem.setFilterLocally(false);
		townFromItem.setAddUnknownValues(false);
		townFromItem.setCompleteOnTab(true);

		ClientUtils.fillCombo(townFromItem, "TownsDS",
				"searchCitiesFromDBForCombos", "town_id", "town_name");

		townToItem = new ComboBoxItem();
		townToItem.setTitle(CallCenterBK.constants.townTo());
		townToItem.setName("townToItem");
		townToItem.setWidth(375);
		townToItem.setFetchMissingValues(true);
		townToItem.setFilterLocally(false);
		townToItem.setAddUnknownValues(false);
		townToItem.setCompleteOnTab(true);

		ClientUtils.fillCombo(townToItem, "TownsDS",
				"searchCitiesFromDBForCombos", "town_id", "town_name");

		searchForm.setFields(townFromItem, townToItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(750);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		findButton = new IButton();
		findButton.setTitle(CallCenterBK.constants.find());

		clearButton = new IButton();
		clearButton.setTitle(CallCenterBK.constants.clear());

		buttonLayout.setMembers(findButton, clearButton);
		mainLayout.addMember(buttonLayout);

		listGrid = new ListGrid();
		listGrid.setWidth(900);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(distBetweenTowns);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchFromDBForCC");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField town_start = new ListGridField("town_start",
				CallCenterBK.constants.townFrom(), 140);
		town_start.setAlign(Alignment.LEFT);
		town_start.setCanFilter(false);

		ListGridField town_end = new ListGridField("town_end",
				CallCenterBK.constants.townTo(), 140);
		town_end.setAlign(Alignment.LEFT);
		town_end.setCanFilter(false);

		ListGridField town_distance_type_descr = new ListGridField(
				"town_distance_type_descr", CallCenterBK.constants.type(), 140);
		town_distance_type_descr.setAlign(Alignment.LEFT);
		town_distance_type_descr.setCanFilter(false);

		ListGridField dist_between_towns_value = new ListGridField(
				"dist_between_towns_value", CallCenterBK.constants.distance(),
				180);
		dist_between_towns_value.setAlign(Alignment.LEFT);
		dist_between_towns_value.setCanFilter(false);

		ListGridField dist_between_towns_remark = new ListGridField(
				"dist_between_towns_remark", CallCenterBK.constants.comment());
		dist_between_towns_remark.setAlign(Alignment.LEFT);
		dist_between_towns_remark.setCanFilter(true);

		listGrid.setFields(town_start, town_end, town_distance_type_descr,
				dist_between_towns_value, dist_between_towns_remark);

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
				townFromItem.clearValue();
				townToItem.clearValue();
			}
		});

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				DlgViewDistBetweenTowns dlgViewTransport = new DlgViewDistBetweenTowns(
						listGrid, distBetweenTowns, listGrid
								.getSelectedRecord());
				dlgViewTransport.show();
			}
		});
		setPane(mainLayout);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();

			String town_id_start = townFromItem.getValueAsString();
			if (town_id_start != null && !town_id_start.trim().equals("")) {
				criteria.setAttribute("town_id_start", new Integer(
						town_id_start));
			}
			String town_id_end = townToItem.getValueAsString();
			if (town_id_end != null && !town_id_end.trim().equals("")) {
				criteria.setAttribute("town_id_end", new Integer(town_id_end));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchFromDBForCC");
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
