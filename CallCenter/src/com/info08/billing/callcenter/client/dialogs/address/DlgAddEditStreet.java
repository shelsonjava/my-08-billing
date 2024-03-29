package com.info08.billing.callcenter.client.dialogs.address;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
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

public class DlgAddEditStreet extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private DynamicForm dynamicForm1;

	private ComboBoxItem cityItem;
	private TextItem streetNameGeoItem;
	private TextAreaItem streetLocationGeoItem;
	private TextAreaItem streetOldNameItem;

	private ListGrid cityRegionsGrid;
	private ListGrid streetCityRegionsGrid;
	private StreetCityRegsClientDS streetCityRegsClientDS;

	private ComboBoxItem streetLevelItem_1;
	private ComboBoxItem streetLevelItem_2;
	private ComboBoxItem streetLevelItem_3;
	private ComboBoxItem streetLevelItem_4;
	private ComboBoxItem streetLevelItem_5;
	// private ComboBoxItem streetLevelItem_6;
	// private ComboBoxItem streetLevelItem_7;
	// private ComboBoxItem streetLevelItem_8;
	// private ComboBoxItem streetLevelItem_9;
	// private ComboBoxItem streetLevelItem_10;

	private ComboBoxItem arrOfLevels[][] = new ComboBoxItem[5][2];

	private ComboBoxItem streetLevelTypeItem_1;
	private ComboBoxItem streetLevelTypeItem_2;
	private ComboBoxItem streetLevelTypeItem_3;
	private ComboBoxItem streetLevelTypeItem_4;
	private ComboBoxItem streetLevelTypeItem_5;
	// private ComboBoxItem streetLevelTypeItem_6;
	// private ComboBoxItem streetLevelTypeItem_7;
	// private ComboBoxItem streetLevelTypeItem_8;
	// private ComboBoxItem streetLevelTypeItem_9;
	// private ComboBoxItem streetLevelTypeItem_10;
	private CheckboxItem saveStreetHistOrNotItem;

	public DlgAddEditStreet(final ListGrid listGrid,
			final ListGridRecord pRecord) {

		try {

			setTitle(pRecord == null ? "ახალი ქუჩის დამატება"
					: "ქუჩის მოდიფიცირება");

			setHeight(630);
			setWidth(550);
			setShowMinimizeButton(false);
			setIsModal(true);
			setShowModalMask(true);
			setCanDrag(false);
			setCanDragReposition(false);
			setCanDragResize(false);
			setCanDragScroll(false);
			centerInPage();

			hLayout = new VLayout(5);
			hLayout.setWidth100();
			hLayout.setHeight100();
			hLayout.setPadding(10);

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(100);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			cityItem = new ComboBoxItem();
			cityItem.setTitle("ქალაქი");
			cityItem.setWidth("100%");
			cityItem.setName("city_name_geo");
			cityItem.setFetchMissingValues(true);
			cityItem.setFilterLocally(false);
			cityItem.setAddUnknownValues(false);

			cityItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = cityItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("city_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("city_id", nullO);
						}
					}
				}
			});

			streetNameGeoItem = new TextItem();
			streetNameGeoItem.setTitle("ძველი დასახ.");
			streetNameGeoItem.setWidth("100%");
			streetNameGeoItem.setName("street_name_geo");
			streetNameGeoItem.setCanEdit(false);

			streetLocationGeoItem = new TextAreaItem();
			streetLocationGeoItem.setTitle("ქუჩის აღწერა");
			streetLocationGeoItem.setWidth("100%");
			streetLocationGeoItem.setHeight(50);
			streetLocationGeoItem.setName("street_location_geo");

			streetOldNameItem = new TextAreaItem();
			streetOldNameItem.setTitle(CallCenter.constants.oldStreetName());
			streetOldNameItem.setWidth("100%");
			streetOldNameItem.setHeight(80);
			streetOldNameItem.setName("streetOldNameItem");
			streetOldNameItem.setCanEdit(false);

			saveStreetHistOrNotItem = new CheckboxItem();
			saveStreetHistOrNotItem
					.setTitle("შევინახოთ თუ არა ცვლილება ქუჩების ისტორიაში ?");
			saveStreetHistOrNotItem.setWidth("100%");
			saveStreetHistOrNotItem.setName("saveStreetHistOrNotItem");

			dynamicForm.setFields(cityItem, streetNameGeoItem,
					streetLocationGeoItem, streetOldNameItem,
					saveStreetHistOrNotItem);

			// regions ...
			DataSource cityRegionsDS = DataSource.get("CityRegionDS");
			cityRegionsGrid = new ListGrid();
			cityRegionsGrid.setWidth(240);
			cityRegionsGrid.setHeight(200);
			cityRegionsGrid.setDataSource(cityRegionsDS);
			cityRegionsGrid.setCanDragRecordsOut(true);
			cityRegionsGrid.setDragDataAction(DragDataAction.COPY);
			cityRegionsGrid.setAlternateRecordStyles(true);
			cityRegionsGrid.setAutoFetchData(false);
			cityRegionsGrid.setFetchOperation("fetchCityRegions");
			cityRegionsGrid.setCanDragSelectText(true);

			ListGridField city_region_name_geo = new ListGridField(
					"city_region_name_geo", "რეგიონების სია");
			cityRegionsGrid.setFields(city_region_name_geo);

			streetCityRegsClientDS = StreetCityRegsClientDS.getInstance();

			streetCityRegionsGrid = new ListGrid();
			streetCityRegionsGrid.setWidth(240);
			streetCityRegionsGrid.setHeight(200);
			streetCityRegionsGrid.setDataSource(streetCityRegsClientDS);
			streetCityRegionsGrid.setCanAcceptDroppedRecords(true);
			streetCityRegionsGrid.setCanRemoveRecords(true);
			streetCityRegionsGrid.setAutoFetchData(true);
			streetCityRegionsGrid.setPreventDuplicates(true);
			streetCityRegionsGrid
					.setDuplicateDragMessage("ასეთი რაიონი უკვე არჩეულია !");

			Img arrowImg = new Img("arrow_right.png", 32, 32);
			arrowImg.setLayoutAlign(Alignment.CENTER);
			arrowImg.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					streetCityRegionsGrid.transferSelectedData(cityRegionsGrid);
				}
			});
			cityRegionsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							streetCityRegionsGrid
									.transferSelectedData(cityRegionsGrid);
						}
					});

			cityItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					ListGridRecord listGridRecord = cityItem
							.getSelectedRecord();
					if (listGridRecord == null) {
						return;
					}
					Integer city_id = listGridRecord
							.getAttributeAsInt("city_id");
					if (city_id == null) {
						return;
					}
					fillCityRegionsCombo(city_id);
				}
			});

			HLayout gridsLayout = new HLayout(0);
			gridsLayout.setHeight(100);
			gridsLayout.setWidth100();

			HLayout hLayoutImg = new HLayout();
			hLayoutImg.setAlign(Alignment.CENTER);
			hLayoutImg.addMember(arrowImg);

			gridsLayout.setMembers(cityRegionsGrid, hLayoutImg,
					streetCityRegionsGrid);

			hLayout.addMember(gridsLayout);

			dynamicForm1 = new DynamicForm();
			dynamicForm1.setWidth100();
			dynamicForm1.setTitleWidth(200);
			dynamicForm1.setNumCols(4);
			hLayout.addMember(dynamicForm1);

			streetLevelItem_1 = new ComboBoxItem();
			streetLevelItem_1.setTitle("I დონე");
			streetLevelItem_1.setWidth(300);
			streetLevelItem_1.setName("descr_id_level_1");
			streetLevelItem_1.setFetchMissingValues(true);
			streetLevelItem_1.setFilterLocally(false);
			streetLevelItem_1.setAddUnknownValues(false);
			streetLevelItem_1.setPickListHeight(200);

			streetLevelItem_2 = new ComboBoxItem();
			streetLevelItem_2.setTitle("II დონე");
			streetLevelItem_2.setWidth(300);
			streetLevelItem_2.setName("descr_id_level_2");
			streetLevelItem_2.setFetchMissingValues(true);
			streetLevelItem_2.setFilterLocally(false);
			streetLevelItem_2.setAddUnknownValues(false);
			streetLevelItem_2.setPickListHeight(200);

			streetLevelItem_3 = new ComboBoxItem();
			streetLevelItem_3.setTitle("III დონე");
			streetLevelItem_3.setWidth(300);
			streetLevelItem_3.setName("descr_id_level_3");
			streetLevelItem_3.setFetchMissingValues(true);
			streetLevelItem_3.setFilterLocally(false);
			streetLevelItem_3.setAddUnknownValues(false);
			streetLevelItem_3.setPickListHeight(200);

			streetLevelItem_4 = new ComboBoxItem();
			streetLevelItem_4.setTitle("IV დონე");
			streetLevelItem_4.setWidth(300);
			streetLevelItem_4.setName("descr_id_level_4");
			streetLevelItem_4.setFetchMissingValues(true);
			streetLevelItem_4.setFilterLocally(false);
			streetLevelItem_4.setAddUnknownValues(false);
			streetLevelItem_4.setPickListHeight(200);

			streetLevelItem_5 = new ComboBoxItem();
			streetLevelItem_5.setTitle("V დონე");
			streetLevelItem_5.setWidth(300);
			streetLevelItem_5.setName("descr_id_level_5");
			streetLevelItem_5.setFetchMissingValues(true);
			streetLevelItem_5.setFilterLocally(false);
			streetLevelItem_5.setAddUnknownValues(false);
			streetLevelItem_5.setPickListHeight(200);

			streetLevelItem_1.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetLevelItem_1.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("street_descr_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_descr_id", nullO);
						}
					}
				}
			});

			streetLevelItem_2.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetLevelItem_2.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("street_descr_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_descr_id", nullO);
						}
					}
				}
			});

			streetLevelItem_3.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetLevelItem_3.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("street_descr_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_descr_id", nullO);
						}
					}
				}
			});

			streetLevelItem_4.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetLevelItem_4.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("street_descr_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_descr_id", nullO);
						}
					}
				}
			});

			streetLevelItem_5.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetLevelItem_5.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("street_descr_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_descr_id", nullO);
						}
					}
				}
			});

			// streetLevelItem_6 = new ComboBoxItem();
			// streetLevelItem_6.setTitle("VI დონე");
			// streetLevelItem_6.setWidth(300);
			// streetLevelItem_6.setName("descr_id_level_6");
			// streetLevelItem_6.setFetchMissingValues(true);
			// streetLevelItem_6.setFilterLocally(false);
			// streetLevelItem_6.setAddUnknownValues(false);
			// streetLevelItem_6.setPickListHeight(200);
			//
			// streetLevelItem_7 = new ComboBoxItem();
			// streetLevelItem_7.setTitle("VII დონე");
			// streetLevelItem_7.setWidth(300);
			// streetLevelItem_7.setName("descr_id_level_7");
			// streetLevelItem_7.setFetchMissingValues(true);
			// streetLevelItem_7.setFilterLocally(false);
			// streetLevelItem_7.setAddUnknownValues(false);
			// streetLevelItem_7.setPickListHeight(200);
			//
			// streetLevelItem_8 = new ComboBoxItem();
			// streetLevelItem_8.setTitle("VII დონე");
			// streetLevelItem_8.setWidth(300);
			// streetLevelItem_8.setName("descr_id_level_8");
			// streetLevelItem_8.setFetchMissingValues(true);
			// streetLevelItem_8.setFilterLocally(false);
			// streetLevelItem_8.setAddUnknownValues(false);
			// streetLevelItem_8.setPickListHeight(200);
			//
			// streetLevelItem_9 = new ComboBoxItem();
			// streetLevelItem_9.setTitle("IX დონე");
			// streetLevelItem_9.setWidth(300);
			// streetLevelItem_9.setName("descr_id_level_9");
			// streetLevelItem_9.setFetchMissingValues(true);
			// streetLevelItem_9.setFilterLocally(false);
			// streetLevelItem_9.setAddUnknownValues(false);
			// streetLevelItem_9.setPickListHeight(100);
			//
			// streetLevelItem_10 = new ComboBoxItem();
			// streetLevelItem_10.setTitle("X დონე");
			// streetLevelItem_10.setWidth(300);
			// streetLevelItem_10.setName("descr_id_level_10");
			// streetLevelItem_10.setFetchMissingValues(true);
			// streetLevelItem_10.setFilterLocally(false);
			// streetLevelItem_10.setAddUnknownValues(false);
			// streetLevelItem_10.setPickListHeight(100);

			streetLevelTypeItem_1 = new ComboBoxItem();
			streetLevelTypeItem_1.setTitle("ტიპი");
			streetLevelTypeItem_1.setWidth(100);
			streetLevelTypeItem_1.setName("descr_type_id_level_1");
			streetLevelTypeItem_1.setPickListHeight(200);
			streetLevelTypeItem_1.setFetchMissingValues(true);
			streetLevelTypeItem_1.setFilterLocally(false);
			streetLevelTypeItem_1.setAddUnknownValues(false);

			streetLevelTypeItem_2 = new ComboBoxItem();
			streetLevelTypeItem_2.setTitle("ტიპი");
			streetLevelTypeItem_2.setWidth(100);
			streetLevelTypeItem_2.setName("descr_type_id_level_2");
			streetLevelTypeItem_2.setPickListHeight(200);
			streetLevelTypeItem_2.setFetchMissingValues(true);
			streetLevelTypeItem_2.setFilterLocally(false);
			streetLevelTypeItem_2.setAddUnknownValues(false);

			streetLevelTypeItem_3 = new ComboBoxItem();
			streetLevelTypeItem_3.setTitle("ტიპი");
			streetLevelTypeItem_3.setWidth(100);
			streetLevelTypeItem_3.setName("descr_type_id_level_3");
			streetLevelTypeItem_3.setPickListHeight(200);
			streetLevelTypeItem_3.setFetchMissingValues(true);
			streetLevelTypeItem_3.setFilterLocally(false);
			streetLevelTypeItem_3.setAddUnknownValues(false);

			streetLevelTypeItem_4 = new ComboBoxItem();
			streetLevelTypeItem_4.setTitle("ტიპი");
			streetLevelTypeItem_4.setWidth(100);
			streetLevelTypeItem_4.setName("descr_type_id_level_4");
			streetLevelTypeItem_4.setPickListHeight(200);
			streetLevelTypeItem_4.setFetchMissingValues(true);
			streetLevelTypeItem_4.setFilterLocally(false);
			streetLevelTypeItem_4.setAddUnknownValues(false);

			streetLevelTypeItem_5 = new ComboBoxItem();
			streetLevelTypeItem_5.setTitle("ტიპი");
			streetLevelTypeItem_5.setWidth(100);
			streetLevelTypeItem_5.setName("descr_type_id_level_5");
			streetLevelTypeItem_5.setPickListHeight(200);
			streetLevelTypeItem_5.setFetchMissingValues(true);
			streetLevelTypeItem_5.setFilterLocally(false);
			streetLevelTypeItem_5.setAddUnknownValues(false);

			streetLevelTypeItem_1.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetLevelTypeItem_1
							.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("street_type_Id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_type_Id", nullO);
						}
					}
				}
			});

			streetLevelTypeItem_2.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetLevelTypeItem_2
							.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("street_type_Id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_type_Id", nullO);
						}
					}
				}
			});

			streetLevelTypeItem_3.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetLevelTypeItem_3
							.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("street_type_Id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_type_Id", nullO);
						}
					}
				}
			});

			streetLevelTypeItem_4.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetLevelTypeItem_4
							.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("street_type_Id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_type_Id", nullO);
						}
					}
				}
			});

			streetLevelTypeItem_5.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetLevelTypeItem_5
							.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("street_type_Id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_type_Id", nullO);
						}
					}
				}
			});

			// streetLevelTypeItem_6 = new ComboBoxItem();
			// streetLevelTypeItem_6.setTitle("ტიპი");
			// streetLevelTypeItem_6.setWidth(100);
			// streetLevelTypeItem_6.setName("descr_type_id_level_6");
			// streetLevelTypeItem_6.setPickListHeight(200);
			// streetLevelTypeItem_6.setFetchMissingValues(true);
			// streetLevelTypeItem_6.setFilterLocally(false);
			// streetLevelTypeItem_6.setAddUnknownValues(false);
			//
			// streetLevelTypeItem_7 = new ComboBoxItem();
			// streetLevelTypeItem_7.setTitle("ტიპი");
			// streetLevelTypeItem_7.setWidth(100);
			// streetLevelTypeItem_7.setName("descr_type_id_level_7");
			// streetLevelTypeItem_7.setPickListHeight(200);
			// streetLevelTypeItem_7.setFetchMissingValues(true);
			// streetLevelTypeItem_7.setFilterLocally(false);
			// streetLevelTypeItem_7.setAddUnknownValues(false);
			//
			// streetLevelTypeItem_8 = new ComboBoxItem();
			// streetLevelTypeItem_8.setTitle("ტიპი");
			// streetLevelTypeItem_8.setWidth(100);
			// streetLevelTypeItem_8.setName("descr_type_id_level_8");
			// streetLevelTypeItem_8.setPickListHeight(200);
			// streetLevelTypeItem_8.setFetchMissingValues(true);
			// streetLevelTypeItem_8.setFilterLocally(false);
			// streetLevelTypeItem_8.setAddUnknownValues(false);
			//
			// streetLevelTypeItem_9 = new ComboBoxItem();
			// streetLevelTypeItem_9.setTitle("ტიპი");
			// streetLevelTypeItem_9.setWidth(100);
			// streetLevelTypeItem_9.setName("descr_type_id_level_9");
			// streetLevelTypeItem_9.setPickListHeight(100);
			// streetLevelTypeItem_9.setFetchMissingValues(true);
			// streetLevelTypeItem_9.setFilterLocally(false);
			// streetLevelTypeItem_9.setAddUnknownValues(false);
			//
			// streetLevelTypeItem_10 = new ComboBoxItem();
			// streetLevelTypeItem_10.setTitle("ტიპი");
			// streetLevelTypeItem_10.setWidth(100);
			// streetLevelTypeItem_10.setName("descr_type_id_level_10");
			// streetLevelTypeItem_10.setPickListHeight(100);
			// streetLevelTypeItem_10.setFetchMissingValues(true);
			// streetLevelTypeItem_10.setFilterLocally(false);
			// streetLevelTypeItem_10.setAddUnknownValues(false);

			dynamicForm1.setFields(streetLevelItem_1, streetLevelTypeItem_1,
					streetLevelItem_2, streetLevelTypeItem_2,
					streetLevelItem_3, streetLevelTypeItem_3,
					streetLevelItem_4, streetLevelTypeItem_4,
					streetLevelItem_5, streetLevelTypeItem_5);

			// ,
			// streetLevelItem_6, streetLevelTypeItem_6,
			// streetLevelItem_7, streetLevelTypeItem_7,
			// streetLevelItem_8, streetLevelTypeItem_8,
			// streetLevelItem_9, streetLevelTypeItem_9,
			// streetLevelItem_10, streetLevelTypeItem_10

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle("შენახვა");
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle("დახურვა");
			cancItem.setWidth(100);

			hLayoutItem.setMembers(saveItem, cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});
			saveItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					save(pRecord, listGrid);
				}
			});

			addItem(hLayout);
			fillCombos(pRecord);

			arrOfLevels[0] = new ComboBoxItem[] { streetLevelItem_1,
					streetLevelTypeItem_1 };
			arrOfLevels[1] = new ComboBoxItem[] { streetLevelItem_2,
					streetLevelTypeItem_2 };
			arrOfLevels[2] = new ComboBoxItem[] { streetLevelItem_3,
					streetLevelTypeItem_3 };
			arrOfLevels[3] = new ComboBoxItem[] { streetLevelItem_4,
					streetLevelTypeItem_4 };
			arrOfLevels[4] = new ComboBoxItem[] { streetLevelItem_5,
					streetLevelTypeItem_5 };
			// arrOfLevels[5] = new ComboBoxItem[] { streetLevelItem_6,
			// streetLevelTypeItem_6 };
			// arrOfLevels[6] = new ComboBoxItem[] { streetLevelItem_7,
			// streetLevelTypeItem_7 };
			// arrOfLevels[7] = new ComboBoxItem[] { streetLevelItem_8,
			// streetLevelTypeItem_8 };
			// arrOfLevels[8] = new ComboBoxItem[] { streetLevelItem_9,
			// streetLevelTypeItem_9 };
			// arrOfLevels[9] = new ComboBoxItem[] { streetLevelItem_10,
			// streetLevelTypeItem_10 };

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillCombos(final ListGridRecord editRecord) {
		try {
			if (editRecord != null) {
				streetNameGeoItem.setValue(editRecord
						.getAttributeAsString("street_name_geo"));
				streetLocationGeoItem.setValue(editRecord
						.getAttributeAsString("street_location_geo"));
				streetOldNameItem.setValue(editRecord
						.getAttributeAsString("oldName"));

				DataSource streetsDS = DataSource.get("StreetsNewDS");

				Criteria criteria = new Criteria();
				criteria.setAttribute("street_id",
						editRecord.getAttributeAsString("street_id"));

				DSRequest dsRequest = new DSRequest();
				dsRequest.setOperationId("fetchStreetEnts");

				streetsDS.fetchData(criteria, new DSCallback() {
					@SuppressWarnings("unchecked")
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record record[] = response.getData();
						if (record != null && record.length > 0) {
							Map<String, String> mapRegions = record[0]
									.getAttributeAsMap("mapStreDistricts");
							if (mapRegions != null && !mapRegions.isEmpty()) {
								Set<String> keys = mapRegions.keySet();
								for (String city_region_id : keys) {
									String city_region_name_geo = mapRegions
											.get(city_region_id);
									ListGridRecord listGridRecord = new ListGridRecord();
									listGridRecord.setAttribute(
											"city_region_id", city_region_id);
									listGridRecord.setAttribute(
											"city_region_name_geo",
											city_region_name_geo);
									streetCityRegionsGrid
											.addData(listGridRecord);
								}
							}
						}
					}
				}, dsRequest);
			}

			DataSource citiesDS = DataSource.get("CityDS");
			cityItem.setOptionOperationId("searchCitiesFromDBForCombosAll");
			cityItem.setOptionDataSource(citiesDS);
			cityItem.setValueField("city_id");
			cityItem.setDisplayField("city_name_geo");

			Criteria criteria1 = new Criteria();
			cityItem.setOptionCriteria(criteria1);
			cityItem.setAutoFetchData(false);
			Integer pcity_id = Constants.defCityTbilisiId;
			if (editRecord != null) {
				Integer city_id = editRecord.getAttributeAsInt("city_id");
				if (city_id != null) {
					cityItem.setValue(city_id);
					pcity_id = city_id;
				} else {
					pcity_id = Constants.defCityTbilisiId;
					cityItem.setValue(pcity_id);
				}
			} else {
				pcity_id = Constants.defCityTbilisiId;
				cityItem.setValue(pcity_id);
			}
			fillCityRegionsCombo(pcity_id);

			DataSource descrDS = DataSource.get("StreetDescrDS");
			streetLevelItem_1.setOptionOperationId("fetchStreetDescrsForCB");
			streetLevelItem_1.setOptionDataSource(descrDS);
			streetLevelItem_1.setValueField("street_descr_id");
			streetLevelItem_1.setDisplayField("street_descr_name_geo");
			streetLevelItem_1.setOptionCriteria(criteria1);
			streetLevelItem_1.setAutoFetchData(false);

			streetLevelItem_2.setOptionOperationId("fetchStreetDescrsForCB");
			streetLevelItem_2.setOptionDataSource(descrDS);
			streetLevelItem_2.setValueField("street_descr_id");
			streetLevelItem_2.setDisplayField("street_descr_name_geo");
			streetLevelItem_2.setOptionCriteria(criteria1);
			streetLevelItem_2.setAutoFetchData(false);

			streetLevelItem_3.setOptionOperationId("fetchStreetDescrsForCB");
			streetLevelItem_3.setOptionDataSource(descrDS);
			streetLevelItem_3.setValueField("street_descr_id");
			streetLevelItem_3.setDisplayField("street_descr_name_geo");
			streetLevelItem_3.setOptionCriteria(criteria1);
			streetLevelItem_3.setAutoFetchData(false);

			streetLevelItem_4.setOptionOperationId("fetchStreetDescrsForCB");
			streetLevelItem_4.setOptionDataSource(descrDS);
			streetLevelItem_4.setValueField("street_descr_id");
			streetLevelItem_4.setDisplayField("street_descr_name_geo");
			streetLevelItem_4.setOptionCriteria(criteria1);
			streetLevelItem_4.setAutoFetchData(false);

			streetLevelItem_5.setOptionOperationId("fetchStreetDescrsForCB");
			streetLevelItem_5.setOptionDataSource(descrDS);
			streetLevelItem_5.setValueField("street_descr_id");
			streetLevelItem_5.setDisplayField("street_descr_name_geo");
			streetLevelItem_5.setOptionCriteria(criteria1);
			streetLevelItem_5.setAutoFetchData(false);

			// streetLevelItem_6.setOptionOperationId("fetchStreetDescrsForCB");
			// streetLevelItem_6.setOptionDataSource(descrDS);
			// streetLevelItem_6.setValueField("street_descr_id");
			// streetLevelItem_6.setDisplayField("street_descr_name_geo");
			// streetLevelItem_6.setOptionCriteria(criteria1);
			// streetLevelItem_6.setAutoFetchData(false);
			//
			// streetLevelItem_7.setOptionOperationId("fetchStreetDescrsForCB");
			// streetLevelItem_7.setOptionDataSource(descrDS);
			// streetLevelItem_7.setValueField("street_descr_id");
			// streetLevelItem_7.setDisplayField("street_descr_name_geo");
			// streetLevelItem_7.setOptionCriteria(criteria1);
			// streetLevelItem_7.setAutoFetchData(false);
			//
			// streetLevelItem_8.setOptionOperationId("fetchStreetDescrsForCB");
			// streetLevelItem_8.setOptionDataSource(descrDS);
			// streetLevelItem_8.setValueField("street_descr_id");
			// streetLevelItem_8.setDisplayField("street_descr_name_geo");
			// streetLevelItem_8.setOptionCriteria(criteria1);
			// streetLevelItem_8.setAutoFetchData(false);
			//
			// streetLevelItem_9.setOptionOperationId("fetchStreetDescrsForCB");
			// streetLevelItem_9.setOptionDataSource(descrDS);
			// streetLevelItem_9.setValueField("street_descr_id");
			// streetLevelItem_9.setDisplayField("street_descr_name_geo");
			// streetLevelItem_9.setOptionCriteria(criteria1);
			// streetLevelItem_9.setAutoFetchData(false);
			//
			// streetLevelItem_10.setOptionOperationId("fetchStreetDescrsForCB");
			// streetLevelItem_10.setOptionDataSource(descrDS);
			// streetLevelItem_10.setValueField("street_descr_id");
			// streetLevelItem_10.setDisplayField("street_descr_name_geo");
			// streetLevelItem_10.setOptionCriteria(criteria1);
			// streetLevelItem_10.setAutoFetchData(false);

			if (editRecord != null) {
				Integer descr1 = editRecord
						.getAttributeAsInt("descr_id_level_1");
				if (descr1 != null) {
					streetLevelItem_1.setValue(descr1);
				}
				Integer descr2 = editRecord
						.getAttributeAsInt("descr_id_level_2");
				if (descr2 != null) {
					streetLevelItem_2.setValue(descr2);
				}
				Integer descr3 = editRecord
						.getAttributeAsInt("descr_id_level_3");
				if (descr3 != null) {
					streetLevelItem_3.setValue(descr3);
				}
				Integer descr4 = editRecord
						.getAttributeAsInt("descr_id_level_4");
				if (descr4 != null) {
					streetLevelItem_4.setValue(descr4);
				}
				Integer descr5 = editRecord
						.getAttributeAsInt("descr_id_level_5");
				if (descr5 != null) {
					streetLevelItem_5.setValue(descr5);
				}
				// Integer descr6 = editRecord
				// .getAttributeAsInt("descr_id_level_6");
				// if (descr6 != null) {
				// streetLevelItem_6.setValue(descr6);
				// }
				// Integer descr7 = editRecord
				// .getAttributeAsInt("descr_id_level_7");
				// if (descr7 != null) {
				// streetLevelItem_7.setValue(descr7);
				// }
				// Integer descr8 = editRecord
				// .getAttributeAsInt("descr_id_level_8");
				// if (descr8 != null) {
				// streetLevelItem_8.setValue(descr8);
				// }
				// Integer descr9 = editRecord
				// .getAttributeAsInt("descr_id_level_9");
				// if (descr9 != null) {
				// streetLevelItem_9.setValue(descr9);
				// }
				// Integer descr10 = editRecord
				// .getAttributeAsInt("descr_id_level_10");
				// if (descr10 != null) {
				// streetLevelItem_10.setValue(descr10);
				// }
			}

			DataSource descrTypeDS = DataSource.get("StreetTypesDS");
			streetLevelTypeItem_1
					.setOptionOperationId("searchStrTypesFromDBForCB");
			streetLevelTypeItem_1.setOptionDataSource(descrTypeDS);
			streetLevelTypeItem_1.setValueField("street_type_Id");
			streetLevelTypeItem_1.setDisplayField("street_type_name_geo");
			streetLevelTypeItem_1.setOptionCriteria(criteria1);
			streetLevelTypeItem_1.setAutoFetchData(false);

			streetLevelTypeItem_2
					.setOptionOperationId("searchStrTypesFromDBForCB");
			streetLevelTypeItem_2.setOptionDataSource(descrTypeDS);
			streetLevelTypeItem_2.setValueField("street_type_Id");
			streetLevelTypeItem_2.setDisplayField("street_type_name_geo");
			streetLevelTypeItem_2.setOptionCriteria(criteria1);
			streetLevelTypeItem_2.setAutoFetchData(false);

			streetLevelTypeItem_3
					.setOptionOperationId("searchStrTypesFromDBForCB");
			streetLevelTypeItem_3.setOptionDataSource(descrTypeDS);
			streetLevelTypeItem_3.setValueField("street_type_Id");
			streetLevelTypeItem_3.setDisplayField("street_type_name_geo");
			streetLevelTypeItem_3.setOptionCriteria(criteria1);
			streetLevelTypeItem_3.setAutoFetchData(false);

			streetLevelTypeItem_4
					.setOptionOperationId("searchStrTypesFromDBForCB");
			streetLevelTypeItem_4.setOptionDataSource(descrTypeDS);
			streetLevelTypeItem_4.setValueField("street_type_Id");
			streetLevelTypeItem_4.setDisplayField("street_type_name_geo");
			streetLevelTypeItem_4.setOptionCriteria(criteria1);
			streetLevelTypeItem_4.setAutoFetchData(false);

			streetLevelTypeItem_5
					.setOptionOperationId("searchStrTypesFromDBForCB");
			streetLevelTypeItem_5.setOptionDataSource(descrTypeDS);
			streetLevelTypeItem_5.setValueField("street_type_Id");
			streetLevelTypeItem_5.setDisplayField("street_type_name_geo");
			streetLevelTypeItem_5.setOptionCriteria(criteria1);
			streetLevelTypeItem_5.setAutoFetchData(false);

			// streetLevelTypeItem_6
			// .setOptionOperationId("searchStrTypesFromDBForCB");
			// streetLevelTypeItem_6.setOptionDataSource(descrTypeDS);
			// streetLevelTypeItem_6.setValueField("street_type_Id");
			// streetLevelTypeItem_6.setDisplayField("street_type_name_geo");
			// streetLevelTypeItem_6.setOptionCriteria(criteria1);
			// streetLevelTypeItem_6.setAutoFetchData(false);
			//
			// streetLevelTypeItem_7
			// .setOptionOperationId("searchStrTypesFromDBForCB");
			// streetLevelTypeItem_7.setOptionDataSource(descrTypeDS);
			// streetLevelTypeItem_7.setValueField("street_type_Id");
			// streetLevelTypeItem_7.setDisplayField("street_type_name_geo");
			// streetLevelTypeItem_7.setOptionCriteria(criteria1);
			// streetLevelTypeItem_7.setAutoFetchData(false);
			//
			// streetLevelTypeItem_8
			// .setOptionOperationId("searchStrTypesFromDBForCB");
			// streetLevelTypeItem_8.setOptionDataSource(descrTypeDS);
			// streetLevelTypeItem_8.setValueField("street_type_Id");
			// streetLevelTypeItem_8.setDisplayField("street_type_name_geo");
			// streetLevelTypeItem_8.setOptionCriteria(criteria1);
			// streetLevelTypeItem_8.setAutoFetchData(false);
			//
			// streetLevelTypeItem_9
			// .setOptionOperationId("searchStrTypesFromDBForCB");
			// streetLevelTypeItem_9.setOptionDataSource(descrTypeDS);
			// streetLevelTypeItem_9.setValueField("street_type_Id");
			// streetLevelTypeItem_9.setDisplayField("street_type_name_geo");
			// streetLevelTypeItem_9.setOptionCriteria(criteria1);
			// streetLevelTypeItem_9.setAutoFetchData(false);
			//
			// streetLevelTypeItem_10
			// .setOptionOperationId("searchStrTypesFromDBForCB");
			// streetLevelTypeItem_10.setOptionDataSource(descrTypeDS);
			// streetLevelTypeItem_10.setValueField("street_type_Id");
			// streetLevelTypeItem_10.setDisplayField("street_type_name_geo");
			// streetLevelTypeItem_10.setOptionCriteria(criteria1);
			// streetLevelTypeItem_10.setAutoFetchData(false);

			DataSource descrsTypeDS = DataSource.get("StreetTypesDS");

			streetLevelTypeItem_1.setOptionOperationId("fetchStreetTypes");
			streetLevelTypeItem_1.setOptionDataSource(descrsTypeDS);
			streetLevelTypeItem_1.setValueField("street_type_Id");
			streetLevelTypeItem_1.setDisplayField("street_type_name_geo");

			streetLevelTypeItem_2.setOptionOperationId("fetchStreetTypes");
			streetLevelTypeItem_2.setOptionDataSource(descrsTypeDS);
			streetLevelTypeItem_2.setValueField("street_type_Id");
			streetLevelTypeItem_2.setDisplayField("street_type_name_geo");

			streetLevelTypeItem_3.setOptionOperationId("fetchStreetTypes");
			streetLevelTypeItem_3.setOptionDataSource(descrsTypeDS);
			streetLevelTypeItem_3.setValueField("street_type_Id");
			streetLevelTypeItem_3.setDisplayField("street_type_name_geo");

			streetLevelTypeItem_4.setOptionOperationId("fetchStreetTypes");
			streetLevelTypeItem_4.setOptionDataSource(descrsTypeDS);
			streetLevelTypeItem_4.setValueField("street_type_Id");
			streetLevelTypeItem_4.setDisplayField("street_type_name_geo");

			streetLevelTypeItem_5.setOptionOperationId("fetchStreetTypes");
			streetLevelTypeItem_5.setOptionDataSource(descrsTypeDS);
			streetLevelTypeItem_5.setValueField("street_type_Id");
			streetLevelTypeItem_5.setDisplayField("street_type_name_geo");

			// streetLevelTypeItem_6.setOptionOperationId("fetchStreetTypes");
			// streetLevelTypeItem_6.setOptionDataSource(descrsTypeDS);
			// streetLevelTypeItem_6.setValueField("street_type_Id");
			// streetLevelTypeItem_6.setDisplayField("street_type_name_geo");
			//
			// streetLevelTypeItem_7.setOptionOperationId("fetchStreetTypes");
			// streetLevelTypeItem_7.setOptionDataSource(descrsTypeDS);
			// streetLevelTypeItem_7.setValueField("street_type_Id");
			// streetLevelTypeItem_7.setDisplayField("street_type_name_geo");
			//
			// streetLevelTypeItem_8.setOptionOperationId("fetchStreetTypes");
			// streetLevelTypeItem_8.setOptionDataSource(descrsTypeDS);
			// streetLevelTypeItem_8.setValueField("street_type_Id");
			// streetLevelTypeItem_8.setDisplayField("street_type_name_geo");
			//
			// streetLevelTypeItem_9.setOptionOperationId("fetchStreetTypes");
			// streetLevelTypeItem_9.setOptionDataSource(descrsTypeDS);
			// streetLevelTypeItem_9.setValueField("street_type_Id");
			// streetLevelTypeItem_9.setDisplayField("street_type_name_geo");
			//
			// streetLevelTypeItem_10.setOptionOperationId("fetchStreetTypes");
			// streetLevelTypeItem_10.setOptionDataSource(descrsTypeDS);
			// streetLevelTypeItem_10.setValueField("street_type_Id");
			// streetLevelTypeItem_10.setDisplayField("street_type_name_geo");

			if (editRecord != null) {
				Integer levelType_1 = editRecord
						.getAttributeAsInt("descr_type_id_level_1");
				if (levelType_1 != null) {
					streetLevelTypeItem_1.setValue(levelType_1);
				}

				Integer levelType_2 = editRecord
						.getAttributeAsInt("descr_type_id_level_2");
				if (levelType_2 != null) {
					streetLevelTypeItem_2.setValue(levelType_2);
				}

				Integer levelType_3 = editRecord
						.getAttributeAsInt("descr_type_id_level_3");
				if (levelType_3 != null) {
					streetLevelTypeItem_3.setValue(levelType_3);
				}

				Integer levelType_4 = editRecord
						.getAttributeAsInt("descr_type_id_level_4");
				if (levelType_4 != null) {
					streetLevelTypeItem_4.setValue(levelType_4);
				}

				Integer levelType_5 = editRecord
						.getAttributeAsInt("descr_type_id_level_5");
				if (levelType_5 != null) {
					streetLevelTypeItem_5.setValue(levelType_5);
				}

				// Integer levelType_6 = editRecord
				// .getAttributeAsInt("descr_type_id_level_6");
				// if (levelType_6 != null) {
				// streetLevelTypeItem_6.setValue(levelType_6);
				// }
				//
				// Integer levelType_7 = editRecord
				// .getAttributeAsInt("descr_type_id_level_7");
				// if (levelType_7 != null) {
				// streetLevelTypeItem_7.setValue(levelType_7);
				// }
				//
				// Integer levelType_8 = editRecord
				// .getAttributeAsInt("descr_type_id_level_8");
				// if (levelType_8 != null) {
				// streetLevelTypeItem_8.setValue(levelType_8);
				// }
				//
				// Integer levelType_9 = editRecord
				// .getAttributeAsInt("descr_type_id_level_9");
				// if (levelType_9 != null) {
				// streetLevelTypeItem_9.setValue(levelType_9);
				// }
				//
				// Integer levelType_10 = editRecord
				// .getAttributeAsInt("descr_type_id_level_10");
				// if (levelType_10 != null) {
				// streetLevelTypeItem_10.setValue(levelType_10);
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillCityRegionsCombo(Integer city_id) {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("city_id", city_id);

			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("fetchCityRegions");
			cityRegionsGrid.fetchData(criteria, new DSCallback() {
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

	private void save(final ListGridRecord editRecord, final ListGrid listGrid) {
		try {
			ListGridRecord city_record = cityItem.getSelectedRecord();
			if (city_record == null
					|| city_record.getAttributeAsInt("city_id") == null) {
				SC.say("აირჩიეთ ქალაქი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			if (editRecord != null) {
				record.setAttribute("street_id",
						editRecord.getAttributeAsInt("street_id"));
			}
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("city_id",
					city_record.getAttributeAsInt("city_id"));
			record.setAttribute("deleted", 0);
			record.setAttribute("map_id", 0);
			record.setAttribute("visible_options", 0);
			record.setAttribute("record_type", 1);
			record.setAttribute("street_name_geo",
					streetNameGeoItem.getValueAsString());
			record.setAttribute("street_location_geo",
					streetLocationGeoItem.getValueAsString());

			boolean bSaveStreetHistOrNotItem = saveStreetHistOrNotItem
					.getValueAsBoolean();
			record.setAttribute("saveStreetHistOrNotItem",
					bSaveStreetHistOrNotItem);

			TreeMap<String, String> mapStreDistricts = new TreeMap<String, String>();
			ListGridRecord cityRegions[] = streetCityRegionsGrid.getRecords();
			if (cityRegions != null && cityRegions.length > 0) {
				for (ListGridRecord listGridRecord : cityRegions) {
					String city_region_id = listGridRecord
							.getAttributeAsString("city_region_id");
					String city_region_name_geo = listGridRecord
							.getAttributeAsString("city_region_name_geo");
					mapStreDistricts.put(city_region_id + "",
							city_region_name_geo);
				}
			}
			if (mapStreDistricts != null && !mapStreDistricts.isEmpty()) {
				record.setAttribute("mapStreDistricts", mapStreDistricts);
			}

			int index = 1;
			for (ComboBoxItem levels[] : arrOfLevels) {
				// combos
				ComboBoxItem level_descr = levels[0];
				ComboBoxItem level_type = levels[1];

				// selected records
				String level_sel_record = level_descr.getValueAsString();
				String level_type_sel_record = level_type.getValueAsString();

				// level identifiers
				Integer level_sel_rec_Id = null;
				Integer level__type_sel_rec_Id = null;

				if (level_sel_record != null) {
					level_sel_rec_Id = Integer.parseInt(level_sel_record);
				}
				if (level_type_sel_record != null) {
					level__type_sel_rec_Id = Integer
							.parseInt(level_type_sel_record);
				}

				// first level check
				if (index == 1
						&& (level_sel_rec_Id == null || level__type_sel_rec_Id == null)) {
					SC.say("გთხოვთ მიუთითოთ I დონე და მისი ტიპი აუცილებლად !");
					return;
				}

				// set fields to record - if not null
				if (level_sel_rec_Id != null && level__type_sel_rec_Id != null) {
					record.setAttribute("descr_id_level_" + index,
							level_sel_rec_Id);
					record.setAttribute("descr_type_id_level_" + index,
							level__type_sel_rec_Id);
				}
				index++;
			}
			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addStreetEnt");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateStreetEnt");
				listGrid.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			}
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
