package com.info08.billing.callcenterbk.client.content;

import com.info08.billing.callcenterbk.client.dialogs.correction.DlgAddEditAbonent;
import com.info08.billing.callcenterbk.client.dialogs.correction.DlgOrgInfoViewByPhone;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FocusEvent;
import com.smartgwt.client.widgets.form.fields.events.FocusHandler;
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
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabAbonent extends Tab {

	private VLayout mainLayout;
	private DataSource abonentsDS;
	private DynamicForm searchForm;
	private ComboBoxItem firstNameItem;
	private ComboBoxItem lastNameItem;
	private TextItem phoneItem;
	private ComboBoxItem citiesItem;
	private ComboBoxItem streetItem;
	private ComboBoxItem regionItem;
	private ComboBoxItem isHideItem;
	private ComboBoxItem isParallelItem;
	private ComboBoxItem phoneStatusItem;
	private ComboBoxItem phoneStateItem;
	private ComboBoxItem phoneTypeItem;
	private TextItem oldAddItem;
	private ComboBoxItem adressOpCloseItem;
	private TextItem adressItem;
	private TextItem blockItem;
	private TextItem appartItem;
	private TextItem addrAddInfoItem;
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addPersonBtn;
	private ToolStripButton editPersonBtn;
	private ToolStripButton deletePersonBtn;
	private ToolStripButton restorePersonBtn;
	private ToolStripButton phoneOwnersBtn;
	private ToolStripButton exportButton;
	private ListGrid abonentsGrid;

	public TabAbonent() {
		try {
			setTitle("აბონენტების მართვა");
			setCanClose(true);

			abonentsDS = DataSource.get("AbonentsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(false);
			searchForm.setWidth100();
			searchForm.setTitleWidth(200);
			searchForm.setNumCols(10);
			searchForm.setDataSource(abonentsDS);
			mainLayout.addMember(searchForm);

			firstNameItem = new ComboBoxItem();
			firstNameItem.setTitle("სახელი");
			firstNameItem.setName("firstname_id");
			firstNameItem.setWidth(150);
			firstNameItem.setFetchMissingValues(true);
			firstNameItem.setFilterLocally(false);
			firstNameItem.setAddUnknownValues(false);

			lastNameItem = new ComboBoxItem();
			lastNameItem.setTitle("გვარი");
			lastNameItem.setName("lastname_id");
			lastNameItem.setWidth(150);
			lastNameItem.setFetchMissingValues(true);
			lastNameItem.setFilterLocally(false);
			lastNameItem.setAddUnknownValues(false);

			citiesItem = new ComboBoxItem();
			citiesItem.setTitle("ქალ.");
			citiesItem.setName("town_id");
			citiesItem.setWidth(150);
			citiesItem.setFetchMissingValues(true);
			citiesItem.setFilterLocally(false);
			citiesItem.setAddUnknownValues(false);

			streetItem = new ComboBoxItem();
			streetItem.setTitle("ქუჩა");
			streetItem.setName("street_id");
			streetItem.setWidth(150);
			streetItem.setFetchMissingValues(true);
			streetItem.setFilterLocally(false);
			streetItem.setAddUnknownValues(false);

			regionItem = new ComboBoxItem();
			regionItem.setTitle("უბანი");
			regionItem.setName("town_district_id");
			regionItem.setWidth(150);
			regionItem.setFetchMissingValues(true);
			regionItem.setFilterLocally(false);
			regionItem.setAddUnknownValues(false);

			firstNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = firstNameItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("firstname_Id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("firstname_Id", nullO);
						}
					}
				}
			});
			lastNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = lastNameItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("lastname_Id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("lastname_Id", nullO);
						}
					}
				}
			});
			citiesItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = citiesItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("town_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("town_id", nullO);
						}
					}
				}
			});
			streetItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = streetItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("street_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("street_id", nullO);
						}
					}
				}
			});
			regionItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = regionItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("town_district_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("town_district_id", nullO);
						}
					}
				}
			});

			phoneItem = new TextItem();
			phoneItem.setTitle("ნომერი");
			phoneItem.setWidth(150);
			phoneItem.setName("phone");

			isHideItem = new ComboBoxItem();
			isHideItem.setValueMap(ClientMapUtil.getInstance().getMapOpClose());
			isHideItem.setDefaultToFirstOption(true);
			isHideItem.setTitle("ღია/დაფ.");
			isHideItem.setName("is_hide");
			isHideItem.setWidth(150);

			isParallelItem = new ComboBoxItem();
			isParallelItem.setValueMap(ClientMapUtil.getInstance()
					.getMapParall());
			isParallelItem.setTitle("პარ.");
			isParallelItem.setName("is_parallel");
			isParallelItem.setWidth(150);

			phoneStatusItem = new ComboBoxItem();
			phoneStatusItem.setValueMap(ClientMapUtil.getInstance()
					.getMapStatuses());
			phoneStatusItem.setTitle("სტატუსი");
			phoneStatusItem.setName("phone_status_id");
			phoneStatusItem.setWidth(150);

			phoneStateItem = new ComboBoxItem();
			phoneStateItem.setValueMap(ClientMapUtil.getInstance()
					.getMapStates());
			phoneStateItem.setTitle("მდგ.");
			phoneStateItem.setName("phone_state_id");
			phoneStateItem.setWidth(150);

			phoneTypeItem = new ComboBoxItem();
			phoneTypeItem
					.setValueMap(ClientMapUtil.getInstance().getMapTypes());
			phoneTypeItem.setTitle("ტიპი");
			phoneTypeItem.setName("phone_type_id");
			phoneTypeItem.setWidth(150);

			oldAddItem = new TextItem();
			oldAddItem.setTitle("ძვ. მისამართი");
			oldAddItem.setName("oldAddItem");
			oldAddItem.setWidth(150);

			adressOpCloseItem = new ComboBoxItem();
			adressOpCloseItem.setValueMap(ClientMapUtil.getInstance()
					.getAddrMapOpClose());
			adressOpCloseItem.setTitle("ღია");
			adressOpCloseItem.setName("is_hide");
			adressOpCloseItem.setWidth(150);

			adressItem = new TextItem();
			adressItem.setTitle("სახლი");
			adressItem.setName("addr_number");
			adressItem.setWidth(150);

			blockItem = new TextItem();
			blockItem.setTitle("კორპ.");
			blockItem.setName("addr_block");
			blockItem.setWidth(150);

			appartItem = new TextItem();
			appartItem.setTitle("ბინა");
			appartItem.setName("addr_appt");
			appartItem.setWidth(150);

			addrAddInfoItem = new TextItem();
			addrAddInfoItem.setTitle("ინფ.");
			addrAddInfoItem.setName("addr_descr");
			addrAddInfoItem.setWidth(150);

			searchForm.setFields(firstNameItem, lastNameItem, phoneItem,
					citiesItem, regionItem, streetItem, adressItem, blockItem,
					appartItem, addrAddInfoItem, isParallelItem,
					phoneStatusItem, phoneStateItem, phoneTypeItem,
					adressOpCloseItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);
			mainLayout.addMember(buttonLayout);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addPersonBtn = new ToolStripButton("დამატება", "person_add.png");
			addPersonBtn.setLayoutAlign(Alignment.LEFT);
			addPersonBtn.setWidth(50);
			toolStrip.addButton(addPersonBtn);

			editPersonBtn = new ToolStripButton("შეცვლა", "person_edit.png");
			editPersonBtn.setLayoutAlign(Alignment.LEFT);
			editPersonBtn.setWidth(50);
			toolStrip.addButton(editPersonBtn);

			deletePersonBtn = new ToolStripButton("წაშლა", "person_delete.png");
			deletePersonBtn.setLayoutAlign(Alignment.LEFT);
			deletePersonBtn.setWidth(50);
			toolStrip.addButton(deletePersonBtn);

			restorePersonBtn = new ToolStripButton("აღდგენა", "person_add.png");
			restorePersonBtn.setLayoutAlign(Alignment.LEFT);
			restorePersonBtn.setWidth(50);
			toolStrip.addButton(restorePersonBtn);

			phoneOwnersBtn = new ToolStripButton("ტელ. მესაკუთრეები",
					"phone.png");
			phoneOwnersBtn.setLayoutAlign(Alignment.LEFT);
			phoneOwnersBtn.setWidth(50);
			toolStrip.addButton(phoneOwnersBtn);

			toolStrip.addSeparator();

			exportButton = new ToolStripButton("Excel - ში გადატანა",
					"excel.gif");
			exportButton.setLayoutAlign(Alignment.LEFT);
			exportButton.setWidth(50);
			toolStrip.addButton(exportButton);

			ListGridField firstName = new ListGridField("firstname", "სახელი",
					70);
			ListGridField lastName = new ListGridField("lastname", "გვარი", 100);
			ListGridField phone = new ListGridField("phone", "ნომერი", 70);
			ListGridField phone_state = new ListGridField("phone_state",
					"სტატუსი", 60);
			// ListGridField city = new ListGridField("city", "ქალაქი", 70);
			ListGridField street = new ListGridField("street", "ქუჩა", 230);

			ListGridField addr_number = new ListGridField("addr_number",
					"სახლი", 50);
			ListGridField addr_block = new ListGridField("addr_block", "კორპ.",
					50);
			ListGridField addr_appt = new ListGridField("addr_appt", "ბინა", 50);
			ListGridField addr_descr = new ListGridField("addr_descr",
					"დამ. ინფორმაცია", 120);

			addr_number.setAlign(Alignment.CENTER);
			addr_block.setAlign(Alignment.CENTER);
			addr_appt.setAlign(Alignment.CENTER);

			ListGridField upd_date = new ListGridField("upd_date",
					"განახ. თარიღი", 140);
			upd_date.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

			abonentsGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					Integer deleted = countryRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && !deleted.equals(0)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};
			abonentsGrid.setWidth100();
			abonentsGrid.setHeight100();
			abonentsGrid.setAlternateRecordStyles(true);
			abonentsGrid.setDataSource(abonentsDS);
			abonentsGrid.setAutoFetchData(false);
			abonentsGrid.setShowFilterEditor(false);
			abonentsGrid.setCanEdit(false);
			abonentsGrid.setCanRemoveRecords(false);
			abonentsGrid.setFetchOperation("customSearch");
			abonentsGrid.setShowRowNumbers(true);
			abonentsGrid.setCanHover(true);
			abonentsGrid.setShowHover(true);
			abonentsGrid.setShowHoverComponents(true);
			abonentsGrid.setFixedRecordHeights(false);
			abonentsGrid.setWrapCells(true);
			abonentsGrid.setCanSelectText(true);

			abonentsGrid.setFields(firstName, lastName, phone, phone_state,
					street, addr_number, addr_block, addr_appt, addr_descr,
					upd_date);

			mainLayout.addMember(abonentsGrid);

			findButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					search();
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

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchForm.clearValues();
					fillCityCombo();
				}
			});

			final boolean hasExcelExpPerm = CommonSingleton.getInstance()
					.hasPermission("106000");

			exportButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (!hasExcelExpPerm) {
						SC.say("თქვენ არ გაქვთ ამ სერვისით სარგებლობის უფლება !");
						return;
					}
					com.smartgwt.client.data.DSRequest dsRequestProperties = new com.smartgwt.client.data.DSRequest();

					dsRequestProperties.setExportAs((ExportFormat) EnumUtil
							.getEnum(ExportFormat.values(), "xls"));
					dsRequestProperties
							.setExportDisplay(ExportDisplay.DOWNLOAD);
					dsRequestProperties.setOperationId("customSearch");

					String selFirstName = firstNameItem.getValueAsString();
					String selLastName = lastNameItem.getValueAsString();
					String phone = phoneItem.getValueAsString();
					String street = streetItem.getValueAsString();

					if (selFirstName == null && selLastName == null
							&& phone == null && street == null) {
						SC.say("მიუთითეთ შემდეგი პარამეტრებიდან ერთ-ერთი : სახელი, გვარი, ნომერი, ქუჩა");
						return;
					}

					Criteria criteria = new Criteria();

					String town_id = citiesItem.getValueAsString();
					String region = regionItem.getValueAsString();
					String isHide = isHideItem.getValueAsString();
					String isParallel = isParallelItem.getValueAsString();
					String phoneStatus = phoneStatusItem.getValueAsString();
					String phoneState = phoneStateItem.getValueAsString();
					String phoneType = phoneTypeItem.getValueAsString();
					String adress = adressItem.getValueAsString();
					String block = blockItem.getValueAsString();
					String appart = appartItem.getValueAsString();
					String addrAddInfo = addrAddInfoItem.getValueAsString();

					criteria.setAttribute("firstname_id", selFirstName);
					criteria.setAttribute("lastname_id", selLastName);
					criteria.setAttribute("phone", phone);
					criteria.setAttribute("town_id", town_id);
					criteria.setAttribute("town_district_id", region);
					criteria.setAttribute("street_id", street);
					criteria.setAttribute("addr_number", adress);
					criteria.setAttribute("addr_block", block);
					criteria.setAttribute("addr_appt", appart);
					criteria.setAttribute("addr_descr", addrAddInfo);
					criteria.setAttribute("is_parallel", isParallel);
					criteria.setAttribute("phone_status_id", phoneStatus);
					criteria.setAttribute("phone_state_id", phoneState);
					criteria.setAttribute("phone_type_id", phoneType);
					criteria.setAttribute("is_hide", isHide);

					abonentsDS.exportData(criteria, dsRequestProperties,
							new DSCallback() {
								@Override
								public void execute(DSResponse response,
										Object rawData, DSRequest request) {
								}
							});
				}
			});

			addPersonBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					addEditAbonent(false);
				}
			});

			editPersonBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					addEditAbonent(true);
				}
			});
			deletePersonBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = abonentsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("მონიშნეთ ჩანაწერი ცხრილში");
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && deleted.equals(1)) {
						SC.say("აბონენტი უკვე გაუქმებულია !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ აბონენტის წაშლა ? ",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeAbonentStatus(listGridRecord, 1);
									}
								}
							});
				}
			});

			restorePersonBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = abonentsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("მონიშნეთ ჩანაწერი ცხრილში");
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && deleted.equals(0)) {
						SC.say("აბონენტი უკვე აქტიურია !");
						return;
					}
					SC.ask("დარწმუნებული ხართ რომ გნებავთ აბონენტის აღდგენა ? ",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeAbonentStatus(listGridRecord, 0);
									}
								}
							});
				}
			});

			phoneOwnersBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = abonentsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("მონიშნეთ ჩანაწერი ცხრილში");
						return;
					}
					String phone = listGridRecord.getAttributeAsString("phone");
					if (phone == null || phone.trim().equalsIgnoreCase("")) {
						SC.say("არასწორი ტელეფონის ნომერი !");
						return;
					}

					DlgOrgInfoViewByPhone dlgOrgInfoViewByPhone = new DlgOrgInfoViewByPhone(
							phone);
					dlgOrgInfoViewByPhone.show();
				}
			});

			abonentsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord record = abonentsGrid
									.getSelectedRecord();
							if (record == null) {
								return;
							}
							int fieldNum = event.getFieldNum();
							switch (fieldNum) {
							case 1: // FirstName
								Integer firstname_id = record
										.getAttributeAsInt("firstname_id");
								if (firstname_id != null) {
									firstNameItem.setValue(firstname_id);
								}
								break;
							case 2: // LastName
								Integer lastname_id = record
										.getAttributeAsInt("lastname_id");
								if (lastname_id != null) {
									lastNameItem.setValue(lastname_id);
								}
								break;
							case 3: // Phone
								String phone = record
										.getAttributeAsString("phone");
								phoneItem.setValue(phone);
								break;
							case 5: // Street
								Integer street_id = record
										.getAttributeAsInt("street_id");
								if (street_id != null) {
									streetItem.setValue(street_id);
								}
								Integer town_district_id = record
										.getAttributeAsInt("town_district_id");
								if (town_district_id != null) {
									regionItem.setValue(town_district_id);
								}
								break;
							case 6: // address number
								String addr_number = record
										.getAttributeAsString("addr_number");
								adressItem.setValue(addr_number);
								break;
							case 7: // block
								String addr_block = record
										.getAttributeAsString("addr_block");
								blockItem.setValue(addr_block);
								break;
							case 8: // appt
								String addr_appt = record
										.getAttributeAsString("addr_appt");
								appartItem.setValue(addr_appt);
								break;
							case 9: // add. Info.
								String addr_descr = record
										.getAttributeAsString("addr_descr");
								addrAddInfoItem.setValue(addr_descr);
								break;
							default:
								break;
							}
						}
					});

			fillCombos();

			citiesItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					String value = citiesItem.getValueAsString();
					if (value == null) {
						return;
					}
					Integer town_id = null;
					try {
						town_id = Integer.parseInt(value);
					} catch (NumberFormatException e) {
						return;
					}
					fillStreetsCombo(town_id);
					fillCityRegionCombo(town_id);
				}
			});
			streetItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					ListGridRecord record = streetItem.getSelectedRecord();
					if (record == null) {
						return;
					}
					Integer town_district_id = record
							.getAttributeAsInt("town_district_id");
					Integer cityId = record.getAttributeAsInt("town_id");
					if (town_district_id != null && cityId != null) {
						fillCityRegionCombo(cityId);
						regionItem.setValue(town_district_id);
						Criteria criteria = regionItem.getOptionCriteria();
						if (criteria != null) {
							String oldAttr = criteria
									.getAttribute("town_district_id");
							if (oldAttr != null) {
								Object nullO = null;
								criteria.setAttribute("town_district_id", nullO);
							}
						}
					}
				}
			});
			regionItem.addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					String value = citiesItem.getValueAsString();
					if (value == null) {
						return;
					}
					Integer town_id = null;
					try {
						town_id = Integer.parseInt(value);
					} catch (NumberFormatException e) {
						return;
					}
					fillCityRegionCombo(town_id);
				}
			});
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillCombos() {
		try {
			// datasource
			DataSource firstNamesDS = DataSource.get("FirstNameDS");

			// fetch name
			firstNameItem
					.setOptionOperationId("searchFNamesFromDBCustomForCombos");
			firstNameItem.setOptionDataSource(firstNamesDS);
			firstNameItem.setValueField("firstname_Id");
			firstNameItem.setDisplayField("firstname");

			Criteria criteria = new Criteria();
			firstNameItem.setOptionCriteria(criteria);
			firstNameItem.setAutoFetchData(false);

			// DSRequest dsRequest = new DSRequest();
			// dsRequest.setOperationId("searchFNamesFromDBCustomForCombos");
			// firstNamesDS.fetchData(criteria, new DSCallback() {
			// @Override
			// public void execute(DSResponse response, Object rawData,
			// DSRequest request) {
			// }
			// }, dsRequest);

			DataSource lastNamesDS = DataSource.get("LastNameDS");
			lastNameItem
					.setOptionOperationId("searchLastNamesFromDBCustomForCombos");
			lastNameItem.setOptionDataSource(lastNamesDS);
			lastNameItem.setValueField("lastname_Id");
			lastNameItem.setDisplayField("lastname");

			criteria = new Criteria();
			lastNameItem.setOptionCriteria(criteria);
			lastNameItem.setAutoFetchData(false);

			// dsRequest = new DSRequest();
			// dsRequest.setOperationId("searchLastNamesFromDBCustomForCombos");
			//
			// lastNamesDS.fetchData(criteria, new DSCallback() {
			// @Override
			// public void execute(DSResponse response, Object rawData,
			// DSRequest request) {
			// }
			// }, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
		fillCityCombo();
	}

	private void addEditAbonent(boolean isEdit) {
		ListGridRecord listGridRecord = null;
		if (isEdit) {
			listGridRecord = abonentsGrid.getSelectedRecord();
		}
		if (isEdit && listGridRecord == null) {
			SC.say("მონიშნეთ ჩანაწერი ცხრილში");
			return;
		}
		DlgAddEditAbonent dlgAddEditAbonent = new DlgAddEditAbonent(
				listGridRecord, abonentsDS, this, abonentsGrid);
		dlgAddEditAbonent.show();
	}

	private void fillCityCombo() {
		try {
			final Integer defCityTbilisiId = Constants.defCityTbilisiId;
			DataSource townsDS = DataSource.get("TownsDS");
			citiesItem.setOptionOperationId("searchCitiesFromDBForCombos");
			citiesItem.setOptionDataSource(townsDS);
			citiesItem.setValueField("town_id");
			citiesItem.setDisplayField("town_name");

			Criteria criteria = new Criteria();
			criteria.setAttribute("country_id", 194);
			citiesItem.setOptionCriteria(criteria);
			citiesItem.setAutoFetchData(false);

			citiesItem.setValue(defCityTbilisiId);

			// DSRequest dsRequest = new DSRequest();
			// dsRequest.setOperationId("searchCitiesFromDBForCombos");
			//
			// citiesDS.fetchData(criteria, new DSCallback() {
			// @Override
			// public void execute(DSResponse response, Object rawData,
			// DSRequest request) {
			// Integer town_id = defCityTbilisiId;
			// citiesItem.setValue(town_id);
			// Criteria criteria = citiesItem.getOptionCriteria();
			// if (criteria != null) {
			// String oldAttr = criteria.getAttribute("town_id");
			// if (oldAttr != null) {
			// Object nullO = null;
			// criteria.setAttribute("town_id", nullO);
			// }
			// }
			// }
			// }, dsRequest);
			fillStreetsCombo(defCityTbilisiId);
			fillCityRegionCombo(defCityTbilisiId);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void fillStreetsCombo(Integer town_id) {
		try {
			DataSource streetsDS = DataSource.get("StreetsNewDS");
			streetItem.setOptionOperationId("searchStreetFromDBForCombos");
			streetItem.setOptionDataSource(streetsDS);
			streetItem.setValueField("street_id");
			streetItem.setDisplayField("street_name");

			Criteria criteria = new Criteria();
			criteria.setAttribute("town_id", town_id);
			streetItem.setOptionCriteria(criteria);
			streetItem.setAutoFetchData(false);

			// DSRequest dsRequest = new DSRequest();
			// dsRequest.setOperationId("searchStreetFromDBForCombos");
			//
			// streetsDS.fetchData(criteria, new DSCallback() {
			// @Override
			// public void execute(DSResponse response, Object rawData,
			// DSRequest request) {
			// }
			// }, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void fillCityRegionCombo(Integer town_id) {
		try {
			DataSource streetsDS = DataSource.get("CityRegionDS");
			regionItem.setOptionOperationId("searchCityRegsFromDBForCombos");
			regionItem.setOptionDataSource(streetsDS);
			regionItem.setValueField("town_district_id");
			regionItem.setDisplayField("town_district_name");

			Criteria criteria = new Criterion();
			criteria.setAttribute("town_id", town_id);
			regionItem.setOptionCriteria(criteria);
			regionItem.setAutoFetchData(false);

			// DSRequest dsRequest = new DSRequest();
			// dsRequest.setOperationId("searchCityRegsFromDBForCombos");
			//
			// streetsDS.fetchData(criteria, new DSCallback() {
			// @Override
			// public void execute(DSResponse response, Object rawData,
			// DSRequest request) {
			// }
			// }, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void changeAbonentStatus(ListGridRecord listGridRecord,
			Integer deleted) {
		try {
			if (listGridRecord == null) {
				return;
			}
			Integer abonent_id = listGridRecord.getAttributeAsInt("abonent_id");
			Integer address_id = listGridRecord.getAttributeAsInt("address_id");
			Integer organization_id = listGridRecord.getAttributeAsInt("organization_id");
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("abonent_id", abonent_id);
			record.setAttribute("address_id", address_id);
			record.setAttribute("organization_id", organization_id);
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("deleted", deleted);

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateAbonentStatus");
			abonentsGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					// search();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	public void search() {

		String selFirstName = firstNameItem.getValueAsString();
		String selLastName = lastNameItem.getValueAsString();
		String phone = phoneItem.getValueAsString();
		String street = streetItem.getValueAsString();

		if (selFirstName == null && selLastName == null && phone == null
				&& street == null) {
			SC.say("მიუთითეთ შემდეგი პარამეტრებიდან ერთ-ერთი : სახელი, გვარი, ნომერი, ქუჩა");
			return;
		}
		String city = citiesItem.getValueAsString();
		String region = regionItem.getValueAsString();
		String isHide = isHideItem.getValueAsString();
		String isParallel = isParallelItem.getValueAsString();
		String phoneStatus = phoneStatusItem.getValueAsString();
		String phoneState = phoneStateItem.getValueAsString();
		String phoneType = phoneTypeItem.getValueAsString();
		String adress = adressItem.getValueAsString();
		String block = blockItem.getValueAsString();
		String appart = appartItem.getValueAsString();
		String addrAddInfo = addrAddInfoItem.getValueAsString();

		Criteria criteria = new Criteria();
		criteria.setAttribute("firstname_id", selFirstName);
		criteria.setAttribute("lastname_id", selLastName);
		criteria.setAttribute("phone", phone);
		criteria.setAttribute("town_id", city);
		criteria.setAttribute("town_district_id", region);
		criteria.setAttribute("street_id", street);
		criteria.setAttribute("addr_number", adress);
		criteria.setAttribute("addr_block", block);
		criteria.setAttribute("addr_appt", appart);
		criteria.setAttribute("addr_descr", addrAddInfo);
		criteria.setAttribute("is_parallel", isParallel);
		criteria.setAttribute("phone_status_id", phoneStatus);
		criteria.setAttribute("phone_state_id", phoneState);
		criteria.setAttribute("phone_type_id", phoneType);
		criteria.setAttribute("is_hide", isHide);

		abonentsGrid.invalidateCache();
		DSRequest dsRequest = new DSRequest();
		dsRequest.setOperationId("customSearch");
		abonentsGrid.filterData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
			}
		}, dsRequest);
		// abonentsGrid.filterData(criteria);
	}
}
