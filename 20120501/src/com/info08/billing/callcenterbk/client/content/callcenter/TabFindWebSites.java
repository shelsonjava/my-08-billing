package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewWebSites;
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
	private ComboBoxItem webSiteGroupItem;
	private TextItem addressItem;
	private TextItem remarkItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource WebSitesDS;

	public TabFindWebSites() {
		setTitle(CallCenterBK.constants.findSites());
		setCanClose(true);

		WebSitesDS = DataSource.get("WebSitesDS");

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

		webSiteGroupItem = new ComboBoxItem();
		webSiteGroupItem.setTitle(CallCenterBK.constants.group());
		webSiteGroupItem.setWidth(250);
		webSiteGroupItem.setName("web_site_group");
		webSiteGroupItem.setFetchMissingValues(true);
		webSiteGroupItem.setFilterLocally(false);
		webSiteGroupItem.setAddUnknownValues(false);
		webSiteGroupItem.setCompleteOnTab(true);

		DataSource WebSiteGroupsDS = DataSource.get("WebSiteGroupsDS");
		webSiteGroupItem.setOptionOperationId("searchAllWebSiteGroupsForCB");
		webSiteGroupItem.setOptionDataSource(WebSiteGroupsDS);
		webSiteGroupItem.setValueField("web_site_group_id");
		webSiteGroupItem.setDisplayField("web_site_group_name");

		webSiteGroupItem.setOptionCriteria(new Criteria());
		webSiteGroupItem.setAutoFetchData(false);

		webSiteGroupItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = webSiteGroupItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("web_site_group_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("web_site_group_id", nullO);
					}
				}
			}
		});

		addressItem = new TextItem();
		addressItem.setTitle(CallCenterBK.constants.webSite());
		addressItem.setName("addressItem");
		addressItem.setWidth(250);

		remarkItem = new TextItem();
		remarkItem.setTitle(CallCenterBK.constants.comment());
		remarkItem.setName("remarkItem");
		remarkItem.setWidth(250);

		searchForm.setFields(webSiteGroupItem, addressItem, remarkItem);

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
		listGrid.setWidth(750);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(WebSitesDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchAllWebSites");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setCanDragSelectText(true);

		ListGridField web_site_group_name = new ListGridField(
				"web_site_group_name", CallCenterBK.constants.group());
		web_site_group_name.setAlign(Alignment.LEFT);

		ListGridField address = new ListGridField("address",
				CallCenterBK.constants.webSite());
		address.setAlign(Alignment.LEFT);

		ListGridField remark = new ListGridField("remark",
				CallCenterBK.constants.information());
		remark.setAlign(Alignment.LEFT);

		listGrid.setFields(web_site_group_name, address, remark);

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
				webSiteGroupItem.clearValue();
				addressItem.clearValue();
				remarkItem.clearValue();
			}
		});

		addressItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		remarkItem.addKeyPressHandler(new KeyPressHandler() {
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
						WebSitesDS, listGrid.getSelectedRecord());
				dlgViewGeoInd.show();
			}
		});
		setPane(mainLayout);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();

			String web_site_group_id = webSiteGroupItem.getValueAsString();
			if (web_site_group_id != null
					&& !web_site_group_id.trim().equals("")) {
				criteria.setAttribute("web_site_group_id", new Integer(
						web_site_group_id));
			}

			String address = addressItem.getValueAsString();
			if (address != null && !address.trim().equals("")) {
				criteria.setAttribute("address", address);
			}

			String remark = remarkItem.getValueAsString();
			if (remark != null && !remark.trim().equals("")) {
				criteria.setAttribute("remark", remark);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllWebSites");
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
