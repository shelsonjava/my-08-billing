package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewNonStandInfo;
import com.info08.billing.callcenterbk.shared.common.Constants;
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
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabFindNonStandartInfo extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private ComboBoxItem infoGroupItem;
	private TextItem infoDescrItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource mainDetailDS;

	public TabFindNonStandartInfo() {

		setTitle(CallCenterBK.constants.wiki());
		setCanClose(true);

		mainDetailDS = DataSource.get("MainDetailDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(true);
		searchForm.setWidth(500);
		searchForm.setNumCols(3);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(searchForm);

		infoGroupItem = new ComboBoxItem();
		infoGroupItem.setTitle(CallCenterBK.constants.group());
		infoGroupItem.setWidth(250);
		infoGroupItem.setName("main_detail_type_name_geo");
		infoGroupItem.setFetchMissingValues(true);
		infoGroupItem.setFilterLocally(false);
		infoGroupItem.setAddUnknownValues(false);
		infoGroupItem.setCompleteOnTab(true);

		DataSource mainDetTypeDS = DataSource.get("MainDetTypeDS");
		infoGroupItem.setOptionOperationId("searchMainDetailTypesForNonStInfo");
		infoGroupItem.setOptionDataSource(mainDetTypeDS);
		infoGroupItem.setValueField("main_detail_type_id");
		infoGroupItem.setDisplayField("main_detail_type_name_geo");

		infoGroupItem.setOptionCriteria(new Criteria());
		infoGroupItem.setAutoFetchData(false);

		infoGroupItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = infoGroupItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria
							.getAttribute("main_detail_type_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("main_detail_type_id", nullO);
					}
				}
			}
		});

		infoDescrItem = new TextItem();
		infoDescrItem.setTitle(CallCenterBK.constants.comment());
		infoDescrItem.setName("infoDescrItem");
		infoDescrItem.setWidth(250);

		searchForm.setFields(infoGroupItem, infoDescrItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(500);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		findButton = new IButton();
		findButton.setTitle(CallCenterBK.constants.find());

		clearButton = new IButton();
		clearButton.setTitle(CallCenterBK.constants.clear());

		buttonLayout.setMembers(findButton, clearButton);
		mainLayout.addMember(buttonLayout);

		listGrid = new ListGrid();
		listGrid.setWidth(750);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(mainDetailDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchMainDetailsSplitedBySpace");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField main_detail_type_name_geo = new ListGridField(
				"main_detail_type_name_geo", CallCenterBK.constants.category(),
				150);
		main_detail_type_name_geo.setAlign(Alignment.LEFT);

		ListGridField main_detail_geo = new ListGridField("main_detail_geo",
				CallCenterBK.constants.information());
		main_detail_geo.setAlign(Alignment.LEFT);

		listGrid.setFields(main_detail_type_name_geo, main_detail_geo);

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
				infoGroupItem.clearValue();
				infoDescrItem.clearValue();
			}
		});

		infoDescrItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				DlgViewNonStandInfo dlgViewNonStandInfo = new DlgViewNonStandInfo(
						listGrid, mainDetailDS, listGrid.getSelectedRecord());
				dlgViewNonStandInfo.show();
			}
		});
		setPane(mainLayout);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);
			criteria.setAttribute("service_id",
					Constants.serviceNonStandartInfo);

			String main_detail_type_id = infoGroupItem.getValueAsString();
			if (main_detail_type_id != null
					&& !main_detail_type_id.trim().equals("")) {
				criteria.setAttribute("main_detail_type_id", new Integer(
						main_detail_type_id));
			}
			String main_detail_geo = infoDescrItem.getValueAsString();
			if (main_detail_geo != null && !main_detail_geo.trim().equals("")) {
				String tmp = main_detail_geo.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("main_detail_geo" + i, item);
					i++;
				}
				
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchMainDetailsSplitedBySpace");
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
