package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewInCityTransport;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabFindInCityTransport extends Tab {

	// search form
	private DynamicForm searchForm;

	// fields
	private SelectItem transportTypeItem;
	private TextItem routeNumItem;
	private TextItem streetFromItem;
	private TextItem streetToItem;
	private TextItem remarkItem;

	// main content layout
	private VLayout mainLayout;

	// actions
	private IButton findBtn;
	private IButton clearBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource pubTranspDirDS;

	public TabFindInCityTransport() {
		setTitle(CallCenterBK.constants.findInCityTransport());
		setCanClose(true);

		pubTranspDirDS = DataSource.get("PubTranspDirDS");

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(false);
		searchForm.setWidth(500);
		searchForm.setNumCols(3);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(searchForm);

		transportTypeItem = new SelectItem();
		transportTypeItem.setTitle(CallCenterBK.constants.transportType());
		transportTypeItem.setWidth(250);
		transportTypeItem.setName("name_descr");
		transportTypeItem.setValueMap(ClientMapUtil.getInstance()
				.getTranspTypeCustom());
		transportTypeItem.setDefaultToFirstOption(true);

		routeNumItem = new TextItem();
		routeNumItem.setTitle(CallCenterBK.constants.routeNumber());
		routeNumItem.setWidth(250);
		routeNumItem.setName("routeNumItem");

		streetFromItem = new TextItem();
		streetFromItem.setTitle(CallCenterBK.constants.streetStart());
		streetFromItem.setName("streetFromItem");
		streetFromItem.setWidth(250);

		streetToItem = new TextItem();
		streetToItem.setTitle(CallCenterBK.constants.streetEnd());
		streetToItem.setName("streetToItem");
		streetToItem.setWidth(250);

		remarkItem = new TextItem();
		remarkItem.setTitle(CallCenterBK.constants.remark());
		remarkItem.setName("remarkItem");
		remarkItem.setWidth(250);

		searchForm.setFields(transportTypeItem, routeNumItem, remarkItem,
				streetFromItem, streetToItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(500);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		findBtn = new IButton();
		findBtn.setTitle(CallCenterBK.constants.find());

		clearBtn = new IButton();
		clearBtn.setTitle(CallCenterBK.constants.clear());

		buttonLayout.setMembers(findBtn, clearBtn);
		mainLayout.addMember(buttonLayout);

		listGrid = new ListGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				if (colNum == 2) {
					return "color:red;";
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};
		};

		listGrid.setWidth(950);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(pubTranspDirDS);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("searchAllPublicTransportDirectionsForCallCenter");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setCanDragSelectText(true);
		listGrid.setShowRowNumbers(true);

		ListGridField dir_num = new ListGridField("dir_num",
				CallCenterBK.constants.routeNumber(), 100);

		ListGridField service_descr = new ListGridField("service_descr",
				CallCenterBK.constants.transportType(), 250);

		ListGridField start_place = new ListGridField("start_place",
				CallCenterBK.constants.stationFrom(), 280);

		ListGridField end_place = new ListGridField("end_place",
				CallCenterBK.constants.stationTo());

		ListGridField cycle_descr = new ListGridField("cycle_descr",
				CallCenterBK.constants.type(), 100);

		listGrid.setFields(service_descr, dir_num, start_place, end_place,
				cycle_descr);

		mainLayout.addMember(listGrid);

		findBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search();
			}
		});
		clearBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				transportTypeItem.clearValue();
				routeNumItem.clearValue();
				streetFromItem.clearValue();
				streetToItem.clearValue();
			}
		});

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				new DlgViewInCityTransport(pubTranspDirDS, listGrid
						.getSelectedRecord(), fromStreetId, toStreetId).show();
			}
		});
		routeNumItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});

		streetFromItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		streetToItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});

		setPane(mainLayout);
	}

	private String fromStreetId = null;
	private String toStreetId = null;

	private void search() {
		try {
			fromStreetId = null;
			toStreetId = null;
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", 0);

			String transport_type_id = transportTypeItem.getValueAsString();
			if (transport_type_id != null
					&& !transport_type_id.trim().equals("")) {
				criteria.setAttribute("transp_type_id", transport_type_id);
			}
			String transportno = routeNumItem.getValueAsString();
			if (transportno != null && !transportno.trim().equals("")) {
				criteria.setAttribute("transportno", transportno.trim());
			}

			criteria.setAttribute("remark", remarkItem.getValueAsString());

			String start_place_id = streetFromItem.getValueAsString();
			if (start_place_id != null && !start_place_id.trim().equals("")) {
				fromStreetId = start_place_id;
				criteria.setAttribute("start_place_id", start_place_id);
			}
			String end_place_id = streetToItem.getValueAsString();
			if (end_place_id != null && !end_place_id.trim().equals("")) {
				toStreetId = end_place_id;
				criteria.setAttribute(fromStreetId == null ? "start_place_id"
						: "end_place_id", end_place_id);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"searchAllPublicTransportDirectionsForCallCenter");
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
