package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewAbonent;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
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
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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

public class TabFindAbonent extends Tab {

	// search form
	private DynamicForm searchForm;

	// main content layout
	private VLayout mainLayout;

	private TextItem firstNameItem;
	private TextItem lastNameItem;
	private TextItem phoneItem;
	private ComboBoxItem citiesItem;
	private TextItem streetItem;
	private ComboBoxItem regionItem;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabFindAbonent() {
		setTitle(CallCenterBK.constants.findAbonent());
		setCanClose(true);

		datasource = DataSource.get("AbonentsDS");

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

		firstNameItem = new TextItem();
		firstNameItem.setTitle(CallCenterBK.constants.name());
		firstNameItem.setWidth(250);
		firstNameItem.setName("firstNameItem");

		lastNameItem = new TextItem();
		lastNameItem.setTitle(CallCenterBK.constants.lastName());
		lastNameItem.setWidth(250);
		lastNameItem.setName("lastNameItem");

		phoneItem = new TextItem();
		phoneItem.setTitle(CallCenterBK.constants.phone());
		phoneItem.setWidth(250);
		phoneItem.setName("phone");

		streetItem = new TextItem();
		streetItem.setTitle(CallCenterBK.constants.street());
		streetItem.setName("street_name_geo");
		streetItem.setWidth(250);

		citiesItem = new ComboBoxItem();
		citiesItem.setTitle(CallCenterBK.constants.city());
		citiesItem.setName("city_name_geo");
		citiesItem.setWidth(250);
		citiesItem.setFetchMissingValues(true);
		citiesItem.setFilterLocally(false);
		citiesItem.setAddUnknownValues(false);

		DataSource cityDS = DataSource.get("CityDS");
		citiesItem.setOptionOperationId("searchCitiesFromDBForCombos");
		citiesItem.setOptionDataSource(cityDS);
		citiesItem.setValueField("city_id");
		citiesItem.setDisplayField("city_name_geo");

		citiesItem.setOptionCriteria(new Criteria());
		citiesItem.setAutoFetchData(false);

		citiesItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = citiesItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("city_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("city_id", nullO);
					}
				}
			}
		});

		regionItem = new ComboBoxItem();
		regionItem.setTitle(CallCenterBK.constants.cityRegion());
		regionItem.setName("city_region_name_geo");
		regionItem.setWidth(250);
		regionItem.setFetchMissingValues(true);
		regionItem.setFilterLocally(false);
		regionItem.setAddUnknownValues(false);

		DataSource streetsDS = DataSource.get("CityRegionDS");
		regionItem.setOptionOperationId("searchCityRegsFromDBForCombos");
		regionItem.setOptionDataSource(streetsDS);
		regionItem.setValueField("city_region_id");
		regionItem.setDisplayField("city_region_name_geo");

		Criteria criteria = new Criterion();
		regionItem.setOptionCriteria(criteria);
		regionItem.setAutoFetchData(false);

		regionItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = regionItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("city_region_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("city_region_id", nullO);
					}
				}
			}
		});

		searchForm.setFields(lastNameItem, firstNameItem, phoneItem,
				streetItem, citiesItem, regionItem);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(750);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		clearButton = new IButton();
		clearButton.setTitle(CallCenterBK.constants.clear());

		findButton = new IButton();
		findButton.setTitle(CallCenterBK.constants.find());

		buttonLayout.setMembers(findButton, clearButton);
		mainLayout.addMember(buttonLayout);

		listGrid = new ListGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord countryRecord = (ListGridRecord) record;
				if (countryRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				Integer deleted = countryRecord.getAttributeAsInt("deleted");
				if (deleted != null && !deleted.equals(0)) {
					return "color:red;";
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};
		};

		listGrid.setWidth(1000);
		listGrid.setHeight100();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setDataSource(datasource);
		listGrid.setAutoFetchData(false);
		listGrid.setShowFilterEditor(false);
		listGrid.setCanEdit(false);
		listGrid.setCanRemoveRecords(false);
		listGrid.setFetchOperation("customSearchForCC");
		listGrid.setCanSort(false);
		listGrid.setCanResizeFields(false);
		listGrid.setCanSelectText(true);
		listGrid.setCanDragSelectText(true);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);

		ListGridField firstname = new ListGridField("firstname",
				CallCenterBK.constants.name(), 120);
		firstname.setCanFilter(true);
		
		ListGridField lastname = new ListGridField("lastname",
				CallCenterBK.constants.lastName(), 150);
		lastname.setCanFilter(true);
		
		ListGridField city = new ListGridField("city",
				CallCenterBK.constants.city(), 140);
		city.setCanFilter(false);
		
		ListGridField address = new ListGridField("address",
				CallCenterBK.constants.address());
		address.setCanFilter(true);
		
		ListGridField phone = new ListGridField("phone",
				CallCenterBK.constants.phone(), 100);
		phone.setCanFilter(true);

		firstname.setAlign(Alignment.LEFT);
		lastname.setAlign(Alignment.LEFT);
		city.setAlign(Alignment.LEFT);
		address.setAlign(Alignment.LEFT);
		phone.setAlign(Alignment.CENTER);

		listGrid.setFields(lastname, firstname, city, address, phone);
		mainLayout.addMember(listGrid);

		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				firstNameItem.clearValue();
				lastNameItem.clearValue();
				phoneItem.clearValue();
				streetItem.clearValue();
				citiesItem.clearValue();
				regionItem.clearValue();
			}
		});

		findButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search();
			}
		});

		citiesItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String value = citiesItem.getValueAsString();
				if (value == null) {
					return;
				}
				regionItem.clearValue();
				fillCityRegionCombo(new Integer(value));
			}
		});

		firstNameItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		lastNameItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		phoneItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					search();
				}
			}
		});
		streetItem.addKeyPressHandler(new KeyPressHandler() {
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
				DlgViewAbonent dlgViewAbonent = new DlgViewAbonent(listGrid,
						datasource, listGrid.getSelectedRecord());
				dlgViewAbonent.show();
			}
		});

		setPane(mainLayout);
	}

	private void fillCityRegionCombo(Integer city_id) {
		try {
			if (city_id == null) {
				city_id = Constants.defCityTbilisiId;
			}

			DataSource streetsDS = DataSource.get("CityRegionDS");
			regionItem.setOptionOperationId("searchCityRegsFromDBForCombos");
			regionItem.setOptionDataSource(streetsDS);
			regionItem.setValueField("city_region_id");
			regionItem.setDisplayField("city_region_name_geo");

			Criteria criteria = new Criterion();
			criteria.setAttribute("city_id", city_id);
			regionItem.setOptionCriteria(criteria);
			regionItem.setAutoFetchData(false);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			String firstname = firstNameItem.getValueAsString();
			String lastname = lastNameItem.getValueAsString();
			String phone = phoneItem.getValueAsString();
			String street_id_str = streetItem.getValueAsString();
			String city_id_str = citiesItem.getValueAsString();
			String city_region_id_str = regionItem.getValueAsString();

			if ((firstname == null || firstname.trim().equals(""))
					&& (lastname == null || lastname.trim().equals(""))
					&& (phone == null || phone.trim().equals(""))
					&& (street_id_str == null || street_id_str.trim()
							.equals(""))) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.enterAbonentSearchParam());
				return;
			}
			Criteria criteria = new Criteria();
			if (firstname != null && !firstname.trim().equals("")) {
				criteria.setAttribute("firstname_param", firstname);
			}
			if (lastname != null && !lastname.trim().equals("")) {
				criteria.setAttribute("lastname_param", lastname);
			}
			if (phone != null && !phone.trim().equals("")) {
				criteria.setAttribute("phone_param", phone);
			}
			if (street_id_str != null && !street_id_str.trim().equals("")) {
				String tmp = street_id_str.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					criteria.setAttribute("street_name_geo_param" + i, item);
					i++;
				}
			}
			if (city_id_str != null && !city_id_str.trim().equals("")) {
				criteria.setAttribute("city_id", new Integer(city_id_str));
			}
			if (city_region_id_str != null
					&& !city_region_id_str.trim().equals("")) {
				criteria.setAttribute("city_region_id", new Integer(
						city_region_id_str));
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "customSearchForCC");
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
