package com.info08.billing.callcenterbk.client.content;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.correction.DlgAddEditAbonent;
import com.info08.billing.callcenterbk.client.dialogs.correction.DlgOrgInfoViewByPhone;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.client.utils.FormItemDescr;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
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
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabSubscriber extends Tab {

	private VLayout mainLayout;
	private DataSource subscriberDS;

	private DynamicForm searchFormMain;
	private DynamicForm searchFormAddress;
	private DynamicForm searchFormPhone;

	private ComboBoxItem firstNameItem;
	private ComboBoxItem lastNameItem;
	private TextItem phoneItem;
	private ComboBoxItem citiesItem;
	private ComboBoxItem streetItem;
	private ComboBoxItem regionItem;
	private SelectItem phoneIsHideItem;
	private SelectItem isParallelItem;
	private SelectItem phoneContractType;
	private SelectItem phoneStateItem;
	private SelectItem phoneTypeItem;

	private SelectItem adressOpCloseItem;
	private TextItem adressItem;
	private TextItem blockItem;
	private TextItem appartItem;
	private TextAreaItem addrAddInfoItem;
	private IButton findButton;
	private IButton clearButton;

	private ToolStripButton addPersonBtn;
	private ToolStripButton editPersonBtn;
	private ToolStripButton deletePersonBtn;
	private ToolStripButton phoneOwnersBtn;
	private ToolStripButton exportButton;
	private ListGrid abonentsGrid;

	Map<String, Object> aditionalCriteria = new TreeMap<String, Object>();

	public TabSubscriber() {
		try {

			setTitle("აბონენტების მართვა");
			setCanClose(true);

			subscriberDS = DataSource.get("SubscriberDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			HLayout hl = new HLayout();
			hl.setWidth100();
			mainLayout.addMember(hl);

			searchFormMain = new DynamicForm();
			searchFormMain.setAutoFocus(false);
			searchFormMain.setHeight100();
			searchFormMain.setWidth(350);
			searchFormMain.setTitleWidth(115);
			searchFormMain.setNumCols(2);
			searchFormMain.setGroupTitle(CallCenterBK.constants.maininfo());
			searchFormMain.setIsGroup(true);
			searchFormMain.setPadding(5);
			hl.addMember(searchFormMain);

			firstNameItem = new ComboBoxItem();
			firstNameItem.setTitle(CallCenterBK.constants.name());
			firstNameItem.setName("name_id");
			firstNameItem.setWidth(200);
			ClientUtils.fillCombo(firstNameItem, "FirstNameDS",
					"searchFNamesFromDBCustomForCombos", "firstname_Id",
					"firstname");

			lastNameItem = new ComboBoxItem();
			lastNameItem.setTitle(CallCenterBK.constants.lastName());
			lastNameItem.setName("family_name_id");
			lastNameItem.setWidth(200);
			ClientUtils.fillCombo(lastNameItem, "LastNameDS",
					"searchLastNamesFromDBCustomForCombos", "lastname_Id",
					"lastname");

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setWidth(200);
			phoneItem.setName("phone");

			searchFormMain.setFields(firstNameItem, lastNameItem, phoneItem);

			searchFormAddress = new DynamicForm();
			searchFormAddress.setAutoFocus(false);
			searchFormAddress.setWidth100();
			searchFormAddress.setTitleWidth(115);
			searchFormAddress.setNumCols(8);
			searchFormAddress.setGroupTitle(CallCenterBK.constants
					.addressInfoTitle());
			searchFormAddress.setIsGroup(true);
			searchFormAddress.setPadding(5);
			searchFormAddress.setWrapItemTitles(false);
			mainLayout.addMember(searchFormAddress);

			citiesItem = new ComboBoxItem();
			citiesItem.setTitle(CallCenterBK.constants.town());
			citiesItem.setName("town_id");
			citiesItem.setWidth(200);
			ClientUtils.fillCombo(citiesItem, "TownsDS",
					"searchCitiesFromDBForCombos", "town_id", "town_name",
					aditionalCriteria);

			streetItem = new ComboBoxItem();
			streetItem.setTitle(CallCenterBK.constants.street());
			streetItem.setName("street_id");
			streetItem.setWidth(200);
			// streetItem.setColSpan(3);

			aditionalCriteria.put("town_id",
					Constants.defCityTbilisiId.toString());

			ClientUtils.fillCombo(streetItem, "StreetsDS",
					"searchStreetFromDBForCombos", "street_id", "street_name",
					aditionalCriteria);

			regionItem = new ComboBoxItem();
			regionItem.setTitle(CallCenterBK.constants.district());
			regionItem.setName("town_district_id");
			regionItem.setWidth(200);
			ClientUtils.fillCombo(regionItem, "TownDistrictDS",
					"searchCityRegsFromDBForCombos", "town_district_id",
					"town_district_name", aditionalCriteria);

			adressItem = new TextItem();
			adressItem.setTitle("ქუჩის დასახელება");

			adressItem.setName("anumber");
			adressItem.setWidth(200);

			blockItem = new TextItem();
			blockItem.setTitle(CallCenterBK.constants.block());
			blockItem.setName("block");
			blockItem.setWidth(200);

			appartItem = new TextItem();
			appartItem.setTitle(CallCenterBK.constants.appartment());
			appartItem.setName("appt");
			appartItem.setWidth(200);

			adressOpCloseItem = new SelectItem();
			adressOpCloseItem.setValueMap(ClientMapUtil.getInstance()
					.getAddrMapOpClose());
			adressOpCloseItem.setTitle(CallCenterBK.constants.openClose());
			adressOpCloseItem.setName("hidden_by_request");
			adressOpCloseItem.setWidth(200);

			addrAddInfoItem = new TextAreaItem();
			addrAddInfoItem.setTitle(CallCenterBK.constants.infoShort());
			addrAddInfoItem.setName("descr");
			addrAddInfoItem.setRowSpan(2);
			addrAddInfoItem.setHeight(50);
			addrAddInfoItem.setWidth(200);

			ClientUtils.makeDependancy(citiesItem, "town_id", streetItem,
					regionItem);

			ClientUtils.makeDependancy(streetItem, true, new FormItemDescr(
					regionItem, "street_id", "town_district_id"));
			searchFormAddress.setNumCols(6);
			searchFormAddress.setFields(citiesItem, adressItem,
					addrAddInfoItem, regionItem, blockItem, streetItem,
					appartItem, adressOpCloseItem);

			searchFormPhone = new DynamicForm();
			searchFormPhone.setAutoFocus(false);
			searchFormPhone.setWidth100();
			// searchFormPhone.setTitleWidth(200);
			searchFormPhone.setNumCols(4);
			// searchFormPhone.setTitleOrientation(TitleOrientation.TOP);
			searchFormPhone.setGroupTitle(CallCenterBK.constants.phoneShort());
			searchFormPhone.setIsGroup(true);
			searchFormPhone.setHeight100();
			searchFormPhone.setLayoutAlign(VerticalAlignment.BOTTOM);
			searchFormPhone.setPadding(5);
			// searchFormMain.setWidth(350);
			hl.addMember(searchFormPhone);
			hl.setMembersMargin(10);

			phoneIsHideItem = new SelectItem();
			phoneIsHideItem.setValueMap(ClientMapUtil.getInstance()
					.getMapOpClose());
			phoneIsHideItem.setDefaultToFirstOption(true);
			phoneIsHideItem.setTitle(CallCenterBK.constants.openClose());
			phoneIsHideItem.setName("ph_hidden_by_request");
			phoneIsHideItem.setWidth(200);

			isParallelItem = new SelectItem();

			isParallelItem.setValueMap(ClientMapUtil.getInstance()
					.getMapParall());
			isParallelItem.setTitle(CallCenterBK.constants.paraller());
			isParallelItem.setName("is_parallel");
			isParallelItem.setWidth(200);

			phoneContractType = new SelectItem();
			ClientUtils.fillDescriptionCombo(phoneContractType,
					Constants.DT_PHONECONTRACTTYPES);
			phoneContractType.setTitle(CallCenterBK.constants.status());
			phoneContractType.setName("phone_contract_type");
			phoneContractType.setWidth(200);

			phoneStateItem = new SelectItem();
			ClientUtils.fillDescriptionCombo(phoneStateItem,
					Constants.DT_PHONESTATES);
			phoneStateItem.setTitle(CallCenterBK.constants.state());
			phoneStateItem.setName("phone_state_id");
			phoneStateItem.setWidth(200);

			phoneTypeItem = new SelectItem();
			ClientUtils.fillDescriptionCombo(phoneTypeItem,
					Constants.DT_PHONETYPES);
			phoneTypeItem.setTitle(CallCenterBK.constants.type());
			phoneTypeItem.setName("phone_type_id");
			phoneTypeItem.setWidth(200);

			searchFormPhone.setFields(phoneIsHideItem, isParallelItem,
					phoneContractType, phoneStateItem, phoneTypeItem);

			clearItems();
			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(20);
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

			ListGridField firstName = new ListGridField("name", "სახელი", 70);
			ListGridField lastName = new ListGridField("family_name", "გვარი",
					100);
			ListGridField phone = new ListGridField("phones",
					CallCenterBK.constants.phones(), 70);

			ListGridField street = new ListGridField("street_name", "ქუჩა", 230);

			ListGridField addr_number = new ListGridField("anumber", "სახლი",
					50);
			ListGridField addr_block = new ListGridField("block", "კორპ.", 50);
			ListGridField addr_appt = new ListGridField("appt", "ბინა", 50);
			ListGridField addr_descr = new ListGridField("descr",
					"დამ. ინფორმაცია", 120);

			addr_number.setAlign(Alignment.CENTER);
			addr_block.setAlign(Alignment.CENTER);
			addr_appt.setAlign(Alignment.CENTER);

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
			abonentsGrid.setDataSource(subscriberDS);
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

			abonentsGrid.setFields(firstName, lastName, phone, street,
					addr_number, addr_block, addr_appt, addr_descr);

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

					clearItems();
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

					Criteria criteria = createCriteria();

					subscriberDS.exportData(criteria, dsRequestProperties,
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
								Integer city_region_id = record
										.getAttributeAsInt("city_region_id");
								if (city_region_id != null) {
									regionItem.setValue(city_region_id);
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

			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	protected void clearItems() {
		searchFormMain.clearValues();
		searchFormAddress.clearValues();
		searchFormPhone.clearValues();
		citiesItem.setValue(Constants.defCityTbilisiId);
		ClientUtils.setDefauldCriterias(streetItem, aditionalCriteria);
		ClientUtils.setDefauldCriterias(regionItem, aditionalCriteria);
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
				listGridRecord, subscriberDS, this, abonentsGrid);
		dlgAddEditAbonent.show();
	}

	private void changeAbonentStatus(ListGridRecord listGridRecord,
			Integer deleted) {
		try {
			if (listGridRecord == null) {
				return;
			}
			Integer subscriber_id = listGridRecord
					.getAttributeAsInt("subscriber_id");
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("subscriber_id", subscriber_id);
			record.setAttribute("loggedUserName", loggedUser);

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "removeSubscriber");
			abonentsGrid.removeData(record, new DSCallback() {
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

	private void filleMapFromForm(Map<String, Object> mp, DynamicForm dm) {
		Map<?, ?> vals = dm.getValues();
		Set<?> keys = vals.keySet();
		for (Object key : keys) {
			String sKey = key.toString();
			mp.put(sKey, vals.get(key));
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

		Criteria criteria = createCriteria();
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

	protected Criteria createCriteria() {
		Map<String, Object> mp = new TreeMap<String, Object>();
		filleMapFromForm(mp, searchFormMain);
		filleMapFromForm(mp, searchFormAddress);
		filleMapFromForm(mp, searchFormPhone);
		Criteria criteria = new Criteria();
		Set<?> keys = mp.keySet();
		for (Object key : keys) {
			String sKey = key.toString();
			criteria.setAttribute(sKey, mp.get(key));
		}
		return criteria;
	}
}