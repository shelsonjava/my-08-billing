package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewWebSites;
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

public class TabFindWebSites extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private ComboBoxItem siteGroupItem;
	private TextItem webSiteItem;
	private TextItem siteDescrItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource mainDetailDS;

	public TabFindWebSites() {
		setTitle(CallCenter.constants.findSites());
		setCanClose(true);

		mainDetailDS = DataSource.get("MainDetailDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(true);
		searchForm.setWidth(750);
		searchForm.setNumCols(3);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(searchForm);

		siteGroupItem = new ComboBoxItem();
		siteGroupItem.setTitle(CallCenter.constants.group());
		siteGroupItem.setWidth(250);
		siteGroupItem.setName("main_detail_type_name_geo");
		siteGroupItem.setFetchMissingValues(true);
		siteGroupItem.setFilterLocally(false);
		siteGroupItem.setAddUnknownValues(false);
		siteGroupItem.setCompleteOnTab(true);

		DataSource mainDetTypeDS = DataSource.get("MainDetTypeDS");
		siteGroupItem.setOptionOperationId("searchMainDetailTypesFirWebSites");
		siteGroupItem.setOptionDataSource(mainDetTypeDS);
		siteGroupItem.setValueField("main_detail_type_id");
		siteGroupItem.setDisplayField("main_detail_type_name_geo");

		siteGroupItem.setOptionCriteria(new Criteria());
		siteGroupItem.setAutoFetchData(false);

		siteGroupItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = siteGroupItem.getOptionCriteria();
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

		webSiteItem = new TextItem();
		webSiteItem.setTitle(CallCenter.constants.webSite());
		webSiteItem.setName("webSiteItem");
		webSiteItem.setWidth(250);

		siteDescrItem = new TextItem();
		siteDescrItem.setTitle(CallCenter.constants.comment());
		siteDescrItem.setName("siteDescrItem");
		siteDescrItem.setWidth(250);

		searchForm.setFields(siteGroupItem, webSiteItem, siteDescrItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(750);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		findButton = new IButton();
		findButton.setTitle(CallCenter.constants.find());

		clearButton = new IButton();
		clearButton.setTitle(CallCenter.constants.clear());

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
		listGrid.setFetchOperation("searchGeoIndRegCountry");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setCanDragSelectText(true);

		ListGridField main_detail_type_name_geo = new ListGridField(
				"main_detail_type_name_geo", CallCenter.constants.group());
		main_detail_type_name_geo.setAlign(Alignment.LEFT);

		ListGridField main_detail_eng = new ListGridField("main_detail_eng",
				CallCenter.constants.webSite());
		main_detail_eng.setAlign(Alignment.LEFT);

		ListGridField main_detail_geo = new ListGridField("main_detail_geo",
				CallCenter.constants.information());
		main_detail_geo.setAlign(Alignment.LEFT);

		listGrid.setFields(main_detail_type_name_geo, main_detail_eng,
				main_detail_geo);

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
				siteGroupItem.clearValue();
				webSiteItem.clearValue();
				siteDescrItem.clearValue();
			}
		});

		webSiteItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		siteDescrItem.addKeyPressHandler(new KeyPressHandler() {
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
				DlgViewWebSites dlgViewGeoInd = new DlgViewWebSites(listGrid,
						mainDetailDS, listGrid.getSelectedRecord());
				dlgViewGeoInd.show();
			}
		});
		setPane(mainLayout);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);
			criteria.setAttribute("service_id", Constants.serviceWebSiteInfo);

			String main_detail_type_id = siteGroupItem.getValueAsString();
			if (main_detail_type_id != null
					&& !main_detail_type_id.trim().equals("")) {
				criteria.setAttribute("main_detail_type_id", new Integer(
						main_detail_type_id));
			}
			String main_detail_eng = webSiteItem.getValueAsString();
			if (main_detail_eng != null && !main_detail_eng.trim().equals("")) {
				criteria.setAttribute("main_detail_eng", main_detail_eng);
			}
			String main_detail_geo = siteDescrItem.getValueAsString();
			if (main_detail_geo != null && !main_detail_geo.trim().equals("")) {
				criteria.setAttribute("main_detail_geo", main_detail_geo);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchMainDetails");
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
