package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewNonStandInfo;
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
	private DataSource NonStandartInfosDS;

	public TabFindNonStandartInfo() {

		setTitle(CallCenterBK.constants.wiki());
		setCanClose(true);

		NonStandartInfosDS = DataSource.get("NonStandartInfosDS");

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
		infoGroupItem.setName("infoGroupItem");
		infoGroupItem.setFetchMissingValues(true);
		infoGroupItem.setFilterLocally(false);
		infoGroupItem.setAddUnknownValues(false);
		infoGroupItem.setCompleteOnTab(true);

		DataSource NoneStandartInfoGroupsDS = DataSource
				.get("NoneStandartInfoGroupsDS");
		infoGroupItem
				.setOptionOperationId("searchAllNoneStandartInfoGroupsForCB");
		infoGroupItem.setOptionDataSource(NoneStandartInfoGroupsDS);
		infoGroupItem.setValueField("info_group_id");
		infoGroupItem.setDisplayField("info_group_name");

		infoGroupItem.setOptionCriteria(new Criteria());
		infoGroupItem.setAutoFetchData(false);

		infoGroupItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = infoGroupItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("info_group_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("info_group_id", nullO);
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
		listGrid.setDataSource(NonStandartInfosDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchAllNoneStandartInfo");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setWrapCells(true);
		listGrid.setFixedRecordHeights(false);
		listGrid.setCanDragSelectText(true);

		ListGridField info_group_name = new ListGridField("info_group_name",
				CallCenterBK.constants.category(), 150);
		info_group_name.setAlign(Alignment.LEFT);

		ListGridField info_name = new ListGridField("info_name",
				CallCenterBK.constants.information());
		info_name.setAlign(Alignment.LEFT);

		listGrid.setFields(info_group_name, info_name);

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
						listGrid, NonStandartInfosDS, listGrid
								.getSelectedRecord());
				dlgViewNonStandInfo.show();
			}
		});
		setPane(mainLayout);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();

			String info_group_id = infoGroupItem.getValueAsString();
			if (info_group_id != null && !info_group_id.trim().equals("")) {
				criteria.setAttribute("info_group_id", new Integer(
						info_group_id));
			}
			String info_name = infoDescrItem.getValueAsString();
			if (info_name != null && !info_name.trim().equals("")) {
				String tmp = info_name.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("info_name" + i, item);
					i++;
				}

			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllNoneStandartInfo");
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
