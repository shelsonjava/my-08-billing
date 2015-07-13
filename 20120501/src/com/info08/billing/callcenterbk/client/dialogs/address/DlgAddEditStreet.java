package com.info08.billing.callcenterbk.client.dialogs.address;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.shared.common.CommonFunctions;
import com.info08.billing.callcenterbk.shared.common.Constants;
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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditStreet extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private DynamicForm dynamicFormForHide;
	private DynamicForm dynamicForm1;

	private ComboBoxItem townItem;
	private TextItem streetNameItem;
	private TextAreaItem streetLocationItem;
	private TextAreaItem streetOldNameItem;

	private ListGrid townDistrictsGrid;
	private ListGrid streetToTownDistrictsGrid;
	private StreetToTownDistrictsClientDS streetToTownDistrictsClientDS;

	private ComboBoxItem level_I;
	private ComboBoxItem level_II;
	private ComboBoxItem level_III;
	private ComboBoxItem level_IV;
	private ComboBoxItem level_V;
	private ComboBoxItem arrOfLevels[][] = new ComboBoxItem[5][2];

	private ComboBoxItem typeLevel_I;
	private ComboBoxItem typeLevel_II;
	private ComboBoxItem typeLevel_III;
	private ComboBoxItem typeLevel_IV;
	private ComboBoxItem typeLevel_V;
	private CheckboxItem saveStreetHistOrNotItem;
	private CheckboxItem hideForCorrectionItem;
	private CheckboxItem hideForCallCenterItem;

	public DlgAddEditStreet(final ListGrid listGrid,
			final ListGridRecord pRecord) {
		super();
		try {

			setTitle(pRecord == null ? "ახალი ქუჩის დამატება"
					: "ქუჩის მოდიფიცირება");

			setHeight(690);
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

			HLayout layout1 = new HLayout();

			HLayout layout2 = new HLayout();

			dynamicFormForHide = new DynamicForm();
			dynamicFormForHide.setWidth(300);
			dynamicFormForHide.setTitleWidth(100);
			dynamicFormForHide.setNumCols(4);

			townItem = new ComboBoxItem();
			townItem.setTitle("ქალაქი");
			townItem.setWidth("100%");
			townItem.setName("town_name");
			ClientUtils.fillCombo(townItem, "TownsDS",
					"searchCitiesFromDBForCombosAll", "town_id", "town_name");

			streetNameItem = new TextItem();
			streetNameItem.setTitle("ძველი დასახ.");
			streetNameItem.setWidth("100%");
			streetNameItem.setName("street_name");
			streetNameItem.setCanEdit(false);

			streetLocationItem = new TextAreaItem();
			streetLocationItem.setTitle("ქუჩის აღწერა");
			streetLocationItem.setWidth("100%");
			streetLocationItem.setHeight(50);
			streetLocationItem.setName("street_location");

			streetOldNameItem = new TextAreaItem();
			streetOldNameItem.setTitle(CallCenterBK.constants.oldStreetName());
			streetOldNameItem.setWidth("100%");
			streetOldNameItem.setHeight(80);
			streetOldNameItem.setName("streetOldNameItem");
			streetOldNameItem.setCanEdit(false);

			saveStreetHistOrNotItem = new CheckboxItem();
			saveStreetHistOrNotItem
					.setTitle("შევინახოთ თუ არა ცვლილება ქუჩების ისტორიაში ?");
			saveStreetHistOrNotItem.setWidth("100%");
			saveStreetHistOrNotItem.setName("saveStreetHistOrNotItem");

			SpacerItem item = new SpacerItem();
			item.setHeight(10);

			hideForCorrectionItem = new CheckboxItem();
			hideForCorrectionItem.setTitle("დამალულია კორექციისთვის");
			hideForCorrectionItem.setName("hideForCorrectionItem");

			hideForCallCenterItem = new CheckboxItem();
			hideForCallCenterItem.setTitle("დამალულია ქოლცენტრისთვის");
			hideForCallCenterItem.setName("hideForCallCenterItem");

			dynamicForm.setFields(townItem, streetNameItem, streetLocationItem,
					streetOldNameItem, saveStreetHistOrNotItem, item);

			dynamicFormForHide.setFields(hideForCorrectionItem,
					hideForCallCenterItem);

			layout2.setWidth(92);
			layout1.addMember(layout2);
			layout1.addMember(dynamicFormForHide);

			VLayout border = new VLayout();
			border.setHeight(1);
			border.setBorder("1px #CCC solid");

			hLayout.addMember(border);

			hLayout.addMember(layout1);

			DataSource cityRegionsDS = DataSource.get("TownDistrictDS");
			townDistrictsGrid = new ListGrid();
			townDistrictsGrid.setWidth(240);
			townDistrictsGrid.setHeight(200);
			townDistrictsGrid.setDataSource(cityRegionsDS);
			townDistrictsGrid.setCanDragRecordsOut(true);
			townDistrictsGrid.setDragDataAction(DragDataAction.COPY);
			townDistrictsGrid.setAlternateRecordStyles(true);
			townDistrictsGrid.setAutoFetchData(false);
			townDistrictsGrid.setFetchOperation("fetchCityRegions");
			townDistrictsGrid.setCanDragSelectText(true);

			ListGridField town_district_name = new ListGridField(
					"town_district_name", "რეგიონების სია");
			townDistrictsGrid.setFields(town_district_name);

			streetToTownDistrictsClientDS = StreetToTownDistrictsClientDS.getInstance();

			streetToTownDistrictsGrid = new ListGrid();
			streetToTownDistrictsGrid.setWidth(240);
			streetToTownDistrictsGrid.setHeight(200);
			streetToTownDistrictsGrid.setDataSource(streetToTownDistrictsClientDS);
			streetToTownDistrictsGrid.setCanAcceptDroppedRecords(true);
			streetToTownDistrictsGrid.setCanRemoveRecords(true);
			streetToTownDistrictsGrid.setAutoFetchData(true);
			streetToTownDistrictsGrid.setPreventDuplicates(true);
			streetToTownDistrictsGrid.setDuplicateDragMessage("ასეთი რაიონი უკვე არჩეულია !");

			Img arrowImg = new Img("arrow_right.png", 32, 32);
			arrowImg.setLayoutAlign(Alignment.CENTER);
			arrowImg.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					streetToTownDistrictsGrid.transferSelectedData(townDistrictsGrid);
				}
			});
			townDistrictsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							streetToTownDistrictsGrid
									.transferSelectedData(townDistrictsGrid);
						}
					});

			townItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					ListGridRecord listGridRecord = townItem
							.getSelectedRecord();
					if (listGridRecord == null) {
						return;
					}
					Integer town_id = listGridRecord
							.getAttributeAsInt("town_id");
					if (town_id == null) {
						return;
					}
					fillCityRegionsCombo(town_id);
				}
			});

			HLayout gridsLayout = new HLayout(0);
			gridsLayout.setHeight(100);
			gridsLayout.setWidth100();

			HLayout hLayoutImg = new HLayout();
			hLayoutImg.setAlign(Alignment.CENTER);
			hLayoutImg.addMember(arrowImg);

			gridsLayout.setMembers(townDistrictsGrid, hLayoutImg,
					streetToTownDistrictsGrid);

			hLayout.addMember(gridsLayout);

			dynamicForm1 = new DynamicForm();
			dynamicForm1.setWidth100();
			dynamicForm1.setTitleWidth(200);
			dynamicForm1.setNumCols(4);
			hLayout.addMember(dynamicForm1);

			level_I = new ComboBoxItem();
			level_I.setName("level_I");
			level_I.setTitle("I დონე");
			level_I.setWidth(300);
			ClientUtils.fillCombo(level_I, "StreetNamesDS",
					"fetchStreetNamesForCB", "street_name_id",
					"street_name_descr");

			level_II = new ComboBoxItem();
			level_II.setName("level_II");
			level_II.setTitle("II დონე");
			level_II.setWidth(300);
			ClientUtils.fillCombo(level_II, "StreetNamesDS",
					"fetchStreetNamesForCB", "street_name_id",
					"street_name_descr");

			level_III = new ComboBoxItem();
			level_III.setName("level_III");
			level_III.setTitle("III დონე");
			level_III.setWidth(300);
			ClientUtils.fillCombo(level_III, "StreetNamesDS",
					"fetchStreetNamesForCB", "street_name_id",
					"street_name_descr");

			level_IV = new ComboBoxItem();
			level_IV.setName("level_IV");
			level_IV.setTitle("IV დონე");
			level_IV.setWidth(300);
			ClientUtils.fillCombo(level_IV, "StreetNamesDS",
					"fetchStreetNamesForCB", "street_name_id",
					"street_name_descr");

			level_V = new ComboBoxItem();
			level_V.setName("level_V");
			level_V.setTitle("V დონე");
			level_V.setWidth(300);
			ClientUtils.fillCombo(level_V, "StreetNamesDS",
					"fetchStreetNamesForCB", "street_name_id",
					"street_name_descr");

			typeLevel_I = new ComboBoxItem();
			typeLevel_I.setTitle("ტიპი");
			typeLevel_I.setWidth(100);
			typeLevel_I.setName("type_level_I");
			ClientUtils.fillCombo(typeLevel_I, "StreetKindDS",
					"searchStrKindsFromDBForCB", "street_kind_id",
					"street_kind_name");

			typeLevel_II = new ComboBoxItem();
			typeLevel_II.setTitle("ტიპი");
			typeLevel_II.setWidth(100);
			typeLevel_II.setName("type_level_II");
			ClientUtils.fillCombo(typeLevel_II, "StreetKindDS",
					"searchStrKindsFromDBForCB", "street_kind_id",
					"street_kind_name");

			typeLevel_III = new ComboBoxItem();
			typeLevel_III.setTitle("ტიპი");
			typeLevel_III.setWidth(100);
			typeLevel_III.setName("type_level_III");
			ClientUtils.fillCombo(typeLevel_III, "StreetKindDS",
					"searchStrKindsFromDBForCB", "street_kind_id",
					"street_kind_name");

			typeLevel_IV = new ComboBoxItem();
			typeLevel_IV.setTitle("ტიპი");
			typeLevel_IV.setWidth(100);
			typeLevel_IV.setName("type_level_IV");
			ClientUtils.fillCombo(typeLevel_IV, "StreetKindDS",
					"searchStrKindsFromDBForCB", "street_kind_id",
					"street_kind_name");

			typeLevel_V = new ComboBoxItem();
			typeLevel_V.setTitle("ტიპი");
			typeLevel_V.setWidth(100);
			typeLevel_V.setName("type_level_V");
			ClientUtils.fillCombo(typeLevel_V, "StreetKindDS",
					"searchStrKindsFromDBForCB", "street_kind_id",
					"street_kind_name");

			dynamicForm1.setFields(level_I, typeLevel_I, level_II,
					typeLevel_II, level_III, typeLevel_III, level_IV,
					typeLevel_IV, level_V, typeLevel_V);

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

			arrOfLevels[0] = new ComboBoxItem[] { level_I, typeLevel_I };
			arrOfLevels[1] = new ComboBoxItem[] { level_II, typeLevel_II };
			arrOfLevels[2] = new ComboBoxItem[] { level_III, typeLevel_III };
			arrOfLevels[3] = new ComboBoxItem[] { level_IV, typeLevel_IV };
			arrOfLevels[4] = new ComboBoxItem[] { level_V, typeLevel_V };

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillCombos(final ListGridRecord editRecord) {
		try {
			if (editRecord != null) {
				streetNameItem.setValue(editRecord
						.getAttributeAsString("street_name"));
				streetLocationItem.setValue(editRecord
						.getAttributeAsString("street_location"));
				streetOldNameItem.setValue(editRecord
						.getAttributeAsString("street_old_name_descr"));

				if (editRecord.getAttributeAsString("hide_for_call_center")
						.equals("1")) {
					// TODO
					hideForCallCenterItem.setValue(true);
				} else {
					hideForCallCenterItem.setValue(false);
				}

				if (editRecord.getAttributeAsString("hide_for_correction")
						.equals("1")) {
					// TODO
					hideForCorrectionItem.setValue(true);
				} else {
					hideForCorrectionItem.setValue(false);
				}

				DataSource streetsDS = DataSource.get("StreetsDS");

				Criteria criteria = new Criteria();
				criteria.setAttribute("streets_id",editRecord.getAttributeAsString("streets_id"));

				DSRequest dsRequest = new DSRequest();
				dsRequest.setOperationId("fetchStreetEnts");

				streetsDS.fetchData(criteria, new DSCallback() {
					@SuppressWarnings("unchecked")
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record record[] = response.getData();
						if (record != null && record.length > 0) {
							Map<String, String> mapRegions = record[0].getAttributeAsMap("mapStreDistricts");
							if (mapRegions != null && !mapRegions.isEmpty()) {
								Set<String> keys = mapRegions.keySet();
								for (String town_district_id : keys) {
									String town_district_name = mapRegions.get(town_district_id);
									ListGridRecord listGridRecord = new ListGridRecord();
									listGridRecord.setAttribute("town_district_id", town_district_id);
									listGridRecord.setAttribute("town_district_name", town_district_name);
									streetToTownDistrictsGrid.addData(listGridRecord);
								}
							}
						}
					}
				}, dsRequest);
			}
			Integer ptown_id = Constants.defCityTbilisiId;
			if (editRecord != null) {
				Integer town_id = editRecord.getAttributeAsInt("town_id");
				if (town_id != null) {
					townItem.setValue(town_id);
					ptown_id = town_id;
				} else {
					ptown_id = Constants.defCityTbilisiId;
					townItem.setValue(ptown_id);
				}
			} else {
				ptown_id = Constants.defCityTbilisiId;
				townItem.setValue(ptown_id);
			}
			fillCityRegionsCombo(ptown_id);

			if (editRecord != null) {
				Integer descr1 = editRecord.getAttributeAsInt("level_I");
				if (descr1 != null) {
					level_I.setValue(descr1);
				}
				Integer descr2 = editRecord.getAttributeAsInt("level_II");
				if (descr2 != null) {
					level_II.setValue(descr2);
				}
				Integer descr3 = editRecord.getAttributeAsInt("level_III");
				if (descr3 != null) {
					level_III.setValue(descr3);
				}
				Integer descr4 = editRecord.getAttributeAsInt("level_IV");
				if (descr4 != null) {
					level_IV.setValue(descr4);
				}
				Integer descr5 = editRecord.getAttributeAsInt("level_V");
				if (descr5 != null) {
					level_V.setValue(descr5);
				}
			}
			if (editRecord != null) {
				Integer levelType_1 = editRecord
						.getAttributeAsInt("type_level_I");
				if (levelType_1 != null) {
					typeLevel_I.setValue(levelType_1);
				}

				Integer levelType_2 = editRecord
						.getAttributeAsInt("type_level_II");
				if (levelType_2 != null) {
					typeLevel_II.setValue(levelType_2);
				}

				Integer levelType_3 = editRecord
						.getAttributeAsInt("type_level_III");
				if (levelType_3 != null) {
					typeLevel_III.setValue(levelType_3);
				}

				Integer levelType_4 = editRecord
						.getAttributeAsInt("type_level_IV");
				if (levelType_4 != null) {
					typeLevel_IV.setValue(levelType_4);
				}

				Integer levelType_5 = editRecord
						.getAttributeAsInt("type_level_V");
				if (levelType_5 != null) {
					typeLevel_V.setValue(levelType_5);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillCityRegionsCombo(Integer town_id) {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("town_id", town_id);

			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("fetchCityRegions");
			townDistrictsGrid.fetchData(criteria, new DSCallback() {
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
			ListGridRecord city_record = townItem.getSelectedRecord();
			if (city_record == null
					|| city_record.getAttributeAsInt("town_id") == null) {
				SC.say("აირჩიეთ ქალაქი !");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			if (editRecord != null) {
				record.setAttribute("streets_id", editRecord.getAttributeAsInt("streets_id"));
			}
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("town_id", city_record.getAttributeAsInt("town_id"));
			record.setAttribute("is_visible", 0);
			record.setAttribute("rec_kind", 1);
			record.setAttribute("street_name", streetNameItem.getValueAsString());
			record.setAttribute("street_location", streetLocationItem.getValueAsString());

			boolean bSaveStreetHistOrNotItem = saveStreetHistOrNotItem.getValueAsBoolean();
			boolean bhideForCallCenterItem = hideForCallCenterItem.getValueAsBoolean();
			boolean bhideForCorrectionItem = hideForCorrectionItem.getValueAsBoolean();
			
			record.setAttribute("saveStreetHistOrNotItem",bSaveStreetHistOrNotItem);
			record.setAttribute("hide_for_call_center", bhideForCallCenterItem == true ? "1" : "0");
			record.setAttribute("hide_for_correction", bhideForCorrectionItem == true ? "1" : "0");

			TreeMap<String, String> mapStreDistricts = new TreeMap<String, String>();
			ListGridRecord cityRegions[] = streetToTownDistrictsGrid
					.getRecords();
			if (cityRegions != null && cityRegions.length > 0) {
				for (ListGridRecord listGridRecord : cityRegions) {
					String town_district_id = listGridRecord
							.getAttributeAsString("town_district_id");
					String town_district_name = listGridRecord
							.getAttributeAsString("town_district_name");
					mapStreDistricts.put(town_district_id + "",
							town_district_name);
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
				Integer level_type_sel_rec_Id = null;

				if (level_sel_record != null) {
					level_sel_rec_Id = Integer.parseInt(level_sel_record);
				}
				if (level_type_sel_record != null) {
					level_type_sel_rec_Id = Integer
							.parseInt(level_type_sel_record);
				}

				// first level check
				if (index == 1
						&& (level_sel_rec_Id == null || level_type_sel_rec_Id == null)) {
					SC.say("გთხოვთ მიუთითოთ I დონე და მისი ტიპი აუცილებლად !");
					return;
				}

				// set fields to record - if not null
				if (level_sel_rec_Id != null && level_type_sel_rec_Id != null) {
					record.setAttribute(
							"level_" + CommonFunctions.getRomanNumber(index),
							level_sel_rec_Id);
					record.setAttribute(
							"type_level_"
									+ CommonFunctions.getRomanNumber(index),
							level_type_sel_rec_Id);
				}
				index++;
			}
			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addStreet");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateStreet");
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
