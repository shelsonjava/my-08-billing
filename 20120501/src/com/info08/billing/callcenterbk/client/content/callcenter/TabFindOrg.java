package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewOrg;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
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

public class TabFindOrg extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem orgNameGeoItem;
	// private TextItem orgNameEngItem;
	private TextItem orgCommentItem;
	private TextItem orgDirectorItem;
	private TextItem orgLegalAddressItem;
	private TextItem orgIdentCodeItem;
	// private TextItem orgIdentCodeNewItem;
	private TextItem orgWebAddressItem;
	private TextItem orgEmailItem;
	private TextItem orgWorkingHoursItem;
	private TextItem orgDayOffsItem;
	private TextItem orgDepartmentItem;
	private DateItem orgFoundedStartItem;
	private DateItem orgFoundedEndItem;

	// address fields.
	private ComboBoxItem citiesItem;
	private TextItem streetItem;
	private ComboBoxItem regionItem;
	private TextItem adressItem;
	// private TextItem blockItem;
	// private TextItem appartItem;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabFindOrg() {
		try {
			setTitle(CallCenter.constants.findOrg());
			setCanClose(true);

			datasource = DataSource.get("OrgDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(980);
			searchForm.setNumCols(4);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			mainLayout.addMember(searchForm);

			orgNameGeoItem = new TextItem();
			orgNameGeoItem.setTitle(CallCenter.constants.orgName());
			orgNameGeoItem.setWidth(245);
			orgNameGeoItem.setName("org_name_geo");
			orgNameGeoItem.setValue("");

			orgCommentItem = new TextItem();
			orgCommentItem.setTitle(CallCenter.constants.comment());
			orgCommentItem.setWidth(245);
			orgCommentItem.setName("org_comment");

			orgDirectorItem = new TextItem();
			orgDirectorItem.setTitle(CallCenter.constants.director());
			orgDirectorItem.setWidth(245);
			orgDirectorItem.setName("org_director");

			orgLegalAddressItem = new TextItem();
			orgLegalAddressItem.setTitle(CallCenter.constants.legalAddress());
			orgLegalAddressItem.setWidth(245);
			orgLegalAddressItem.setName("org_legal_address");

			orgIdentCodeItem = new TextItem();
			orgIdentCodeItem.setTitle(CallCenter.constants.identCodeAndNew());
			orgIdentCodeItem.setWidth(245);
			orgIdentCodeItem.setName("org_ident_code");

			orgWebAddressItem = new TextItem();
			orgWebAddressItem.setTitle(CallCenter.constants.webaddress());
			orgWebAddressItem.setWidth(245);
			orgWebAddressItem.setName("org_web_address");

			orgEmailItem = new TextItem();
			orgEmailItem.setTitle(CallCenter.constants.eMail());
			orgEmailItem.setWidth(245);
			orgEmailItem.setName("org_email");

			orgWorkingHoursItem = new TextItem();
			orgWorkingHoursItem.setTitle(CallCenter.constants.workinghours());
			orgWorkingHoursItem.setWidth(245);
			orgWorkingHoursItem.setName("org_working_hours");

			orgDayOffsItem = new TextItem();
			orgDayOffsItem.setTitle(CallCenter.constants.dayOffs());
			orgDayOffsItem.setWidth(245);
			orgDayOffsItem.setName("org_day_offs");

			orgDepartmentItem = new TextItem();
			orgDepartmentItem.setTitle(CallCenter.constants.department());
			orgDepartmentItem.setWidth(245);
			orgDepartmentItem.setName("org_department");

			orgFoundedStartItem = new DateItem();
			orgFoundedStartItem.setTitle(CallCenter.constants
					.orgFoundDateStart());
			orgFoundedStartItem.setWidth(245);
			orgFoundedStartItem.setName("org_dound_date_start");
			orgFoundedStartItem.setHint(CallCenter.constants.choose());

			orgFoundedEndItem = new DateItem();
			orgFoundedEndItem.setTitle(CallCenter.constants.orgFoundDateEnd());
			orgFoundedEndItem.setWidth(245);
			orgFoundedEndItem.setName("org_dound_date_end");
			orgFoundedEndItem.setHint(CallCenter.constants.choose());

			streetItem = new TextItem();
			streetItem.setTitle(CallCenter.constants.street());
			streetItem.setName("street_name_geo");
			streetItem.setWidth(245);

			adressItem = new TextItem();
			adressItem.setTitle(CallCenter.constants.number());
			adressItem.setName("adressItem");
			adressItem.setWidth(245);

			citiesItem = new ComboBoxItem();
			citiesItem.setTitle(CallCenter.constants.city());
			citiesItem.setName("city_name_geo");
			citiesItem.setWidth(245);
			citiesItem.setFetchMissingValues(true);
			citiesItem.setFilterLocally(false);
			citiesItem.setAddUnknownValues(false);

			DataSource cityDS = DataSource.get("CityDS");
			citiesItem.setOptionOperationId("searchCitiesFromDBForCombosAll");
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
			regionItem.setTitle(CallCenter.constants.cityRegion());
			regionItem.setName("city_region_name_geo");
			regionItem.setWidth(245);
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
						String oldAttr = criteria
								.getAttribute("city_region_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("city_region_id", nullO);
						}
					}
				}
			});

			searchForm.setFields(orgNameGeoItem, orgCommentItem,
					orgDepartmentItem, orgDirectorItem, streetItem, adressItem,
					regionItem, citiesItem, orgWorkingHoursItem,
					orgDayOffsItem, orgIdentCodeItem, orgLegalAddressItem,
					orgWebAddressItem, orgEmailItem, orgFoundedStartItem,
					orgFoundedEndItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(980);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenter.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenter.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer statuse = countryRecord
							.getAttributeAsInt("statuse");
					Integer extra_priority = countryRecord
							.getAttributeAsInt("extra_priority");
					Integer note_crit = countryRecord
							.getAttributeAsInt("note_crit");

					if (extra_priority != null && extra_priority < 0) {
						return "color:red;";
					} else if (statuse != null && statuse.equals(2)) {
						if (note_crit != null && note_crit.intValue() == -1
								&& colNum == 4) {
							return "color:red;";
						} else {
							return "color:gray;";
						}
					} else if (statuse != null && statuse.equals(1)) {
						if (note_crit != null && note_crit.intValue() == -1
								&& colNum == 4) {
							return "color:red;";
						} else {
							return "color:blue;";
						}

					} else if (statuse != null && statuse.equals(3)) {
						if (note_crit != null && note_crit.intValue() == -1
								&& colNum == 4) {
							return "color:red;";
						} else {
							return "color:green;";
						}
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("customOrgSearchForCallCenterNew");
			listGrid.setCanSort(false);
			listGrid.setCanResizeFields(false);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setDataPageSize(75);
			listGrid.setCanDragSelectText(true);
			listGrid.setCanGroupBy(false);
			listGrid.setCanAutoFitFields(false);
			listGrid.setCanResizeFields(false);

			ListGridField tree_org_parrent = new ListGridField(
					"tree_org_parrent", " ", 20);
			tree_org_parrent.setAlign(Alignment.CENTER);
			tree_org_parrent.setType(ListGridFieldType.IMAGE);
			tree_org_parrent.setImageURLPrefix("orgtypes/");
			tree_org_parrent.setImageURLSuffix(".png");
			tree_org_parrent.setCanSort(false);
			tree_org_parrent.setCanFilter(false);

			ListGridField tree_org_child = new ListGridField("tree_org_child",
					" ", 20);
			tree_org_child.setAlign(Alignment.CENTER);
			tree_org_child.setType(ListGridFieldType.IMAGE);
			tree_org_child.setImageURLPrefix("orgtypes/");
			tree_org_child.setImageURLSuffix(".png");
			tree_org_child.setCanSort(false);
			tree_org_child.setCanFilter(false);

			ListGridField org_name = new ListGridField("org_name",
					CallCenter.constants.orgName(), 200);
			ListGridField real_address = new ListGridField("real_address",
					CallCenter.constants.realAddress(), 170);
			ListGridField note = new ListGridField("note",
					CallCenter.constants.comment());

			ListGridField director = new ListGridField("director",
					CallCenter.constants.director(), 120);
			ListGridField workinghours = new ListGridField("workinghours",
					CallCenter.constants.workinghours(), 120);
			ListGridField dayoffs = new ListGridField("dayoffs",
					CallCenter.constants.dayOffs(), 120);

			listGrid.setFields(tree_org_child, tree_org_parrent, org_name,
					real_address, note, director, workinghours, dayoffs);
			mainLayout.addMember(listGrid);

			Label commentLabel = new Label(
					CallCenter.constants.orgGridComment());
			commentLabel.setWidth100();
			commentLabel.setHeight(15);
			commentLabel.setStyleName("fontRed");
			mainLayout.addMember(commentLabel);

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					orgNameGeoItem.clearValue();
					orgCommentItem.clearValue();
					orgDepartmentItem.clearValue();
					orgDirectorItem.clearValue();
					streetItem.clearValue();
					adressItem.clearValue();
					regionItem.clearValue();
					citiesItem.clearValue();
					orgWorkingHoursItem.clearValue();
					orgDayOffsItem.clearValue();
					orgIdentCodeItem.clearValue();
					orgLegalAddressItem.clearValue();
					orgWebAddressItem.clearValue();
					orgEmailItem.clearValue();
					orgFoundedEndItem.clearValue();
					orgFoundedStartItem.clearValue();

					searchForm.focusInItem(orgNameGeoItem);

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
			orgNameGeoItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgCommentItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgDepartmentItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgDirectorItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			adressItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgIdentCodeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgLegalAddressItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgWebAddressItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgEmailItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgDayOffsItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgWorkingHoursItem.addKeyPressHandler(new KeyPressHandler() {
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
					DlgViewOrg dlgViewOrg = new DlgViewOrg(datasource, listGrid
							.getSelectedRecord());
					dlgViewOrg.show();

					String org_allert_by_buss_det = listGrid
							.getSelectedRecord().getAttributeAsString(
									"org_allert_by_buss_det");
					if (org_allert_by_buss_det != null
							&& !org_allert_by_buss_det.trim().equals("")) {
						SC.warn(org_allert_by_buss_det);
					}
				}
			});
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void fillCityRegionCombo(Integer defCityTbilisiId) {
		try {
			if (defCityTbilisiId == null) {
				defCityTbilisiId = Constants.defCityTbilisiId;
			}

			DataSource streetsDS = DataSource.get("CityRegionDS");
			regionItem.setOptionOperationId("searchCityRegsFromDBForCombos");
			regionItem.setOptionDataSource(streetsDS);
			regionItem.setValueField("city_region_id");
			regionItem.setDisplayField("city_region_name_geo");

			Criteria criteria = new Criterion();
			criteria.setAttribute("city_id", defCityTbilisiId);
			regionItem.setOptionCriteria(criteria);
			regionItem.setAutoFetchData(false);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("deleted", new Integer(0));
			String org_name = orgNameGeoItem.getValueAsString();
			if (org_name != null && !org_name.trim().equals("")) {
				String tmp = org_name.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("org_name" + i, item);
					i++;
				}
			}

			String note = orgCommentItem.getValueAsString();
			if (note != null && !note.trim().equals("")) {
				String tmp = note.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("note" + i, item);
					i++;
				}
			}
			String director = orgDirectorItem.getValueAsString();
			if (director != null && !director.trim().equals("")) {
				String tmp = director.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("director" + i, item);
					i++;
				}
			}
			String street = streetItem.getValueAsString();
			if (street != null && !street.trim().equalsIgnoreCase("")) {
				String tmp = street.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("real_address" + i, item);
					i++;
				}
			}
			String address_suffix_geo = adressItem.getValueAsString();
			if (address_suffix_geo != null
					&& !address_suffix_geo.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("address_suffix_geo", address_suffix_geo);
			}
			String city = citiesItem.getValueAsString();
			if (city != null && !city.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("city_id", new Integer(city));
			}
			String workinghours = orgWorkingHoursItem.getValueAsString();
			if (workinghours != null
					&& !workinghours.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("workinghours", workinghours);
			}
			String dayoffs = orgDayOffsItem.getValueAsString();
			if (dayoffs != null && !dayoffs.trim().equals("")) {
				String tmp = dayoffs.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("dayoffs" + i, item);
					i++;
				}
			}
			String ident_code = orgIdentCodeItem.getValueAsString();
			if (ident_code != null && !ident_code.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("ident_code", ident_code);
			}

			String legaladdress = orgLegalAddressItem.getValueAsString();
			if (legaladdress != null
					&& !legaladdress.trim().equalsIgnoreCase("")) {
				String tmp = legaladdress.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("legaladdress" + i, item);
					i++;
				}
			}

			String webaddress = orgWebAddressItem.getValueAsString();
			if (webaddress != null && !webaddress.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("webaddress", webaddress);
			}

			String mail = orgEmailItem.getValueAsString();
			if (mail != null && !mail.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("mail", mail);
			}

			String city_region_id = regionItem.getValueAsString();
			if (city_region_id != null && !city_region_id.trim().equals("")) {
				criteria.setAttribute("city_region_id", new Integer(
						city_region_id));
			}

			if ((org_name == null || org_name.trim().equals(""))
					&& (note == null || note.trim().equals(""))
					&& (director == null || director.trim().equals(""))
					&& (workinghours == null || workinghours.trim().equals(""))
					&& (dayoffs == null || dayoffs.trim().equals(""))
					&& (ident_code == null || ident_code.trim().equals(""))
					&& (legaladdress == null || legaladdress.trim().equals(""))
					&& (webaddress == null || webaddress.trim().equals(""))
					&& (mail == null || mail.trim().equals(""))
					&& (street == null || street.trim().equals(""))) {
				SC.say(CallCenter.constants.warning(),
						CallCenter.constants.findOrgEnterAnyParam());
				return;
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"customOrgSearchForCallCenterNew");
			listGrid.invalidateCache();
			listGrid.fetchData(criteria, new DSCallback() {
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
