package com.info08.billing.callcenterbk.client.dialogs.correction;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.info08.billing.callcenterbk.client.content.TabAbonent;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditAbonent extends Window {

	private VLayout hLayout;
	private DynamicForm formPersInfo;
	private DynamicForm formAddress;
	private DynamicForm formAddressParams;
	private ComboBoxItem firstNameItem;
	private ComboBoxItem lastNameItem;
	private TextItem oldAddItem;
	private ComboBoxItem citiesItem;
	private ComboBoxItem streetItem;
	private ComboBoxItem regionItem;
	private ComboBoxItem adressOpCloseItem;
	private TextItem adressItem;
	private TextItem blockItem;
	private TextItem appartItem;
	private TextItem addrAddInfoItem;
	private TextAreaItem streetDescrItem;
	private ListGrid listGridPhones;
	private ListGrid abonentsGrid;

	public DlgAddEditAbonent(final ListGridRecord abonentRecord,
			DataSource subscriberDS, final TabAbonent tabAbonent,
			ListGrid abonentsGrid) {
		try {
			this.abonentsGrid = abonentsGrid;
			setWidth(760);
			setHeight(730);
			setTitle(abonentRecord == null ? "ახალი აბონენტის დამატება"
					: "აბონენტის მოდიფიცირება");
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

			formPersInfo = new DynamicForm();
			formPersInfo.setPadding(10);
			formPersInfo.setAutoFocus(false);
			formPersInfo.setWidth100();
			formPersInfo.setTitleWidth(80);
			formPersInfo.setNumCols(2);
			formPersInfo.setTitleOrientation(TitleOrientation.TOP);

			HeaderItem headerItemPersInfo = new HeaderItem();
			headerItemPersInfo.setValue("აბონენტის პირადი ინფორმაცია");
			headerItemPersInfo.setTextBoxStyle("headerStyle");

			firstNameItem = new ComboBoxItem();
			firstNameItem.setTitle("სახელი");
			firstNameItem.setName("name");
			firstNameItem.setWidth(270);
			firstNameItem.setFetchMissingValues(true);
			firstNameItem.setFilterLocally(false);
			firstNameItem.setAddUnknownValues(false);

			lastNameItem = new ComboBoxItem();
			lastNameItem.setTitle("გვარი");
			lastNameItem.setName("family_name");
			lastNameItem.setWidth(324);
			lastNameItem.setFetchMissingValues(true);
			lastNameItem.setFilterLocally(false);
			lastNameItem.setAddUnknownValues(false);

			formPersInfo.setFields(headerItemPersInfo, firstNameItem,
					lastNameItem);

			final AbPhonesClientDS abPhonesClientDS = AbPhonesClientDS
					.getInstance();

			listGridPhones = new ListGrid();
			listGridPhones.setHeight(150);
			listGridPhones.setWidth100();
			listGridPhones.setDataSource(abPhonesClientDS);
			listGridPhones.setDataPageSize(50);
			listGridPhones.setAutoFetchData(true);
			listGridPhones.setSelectionType(SelectionStyle.SINGLE);
			listGridPhones.setUseAllDataSourceFields(true);

			formAddress = new DynamicForm();
			formAddress.setPadding(10);
			formAddress.setAutoFocus(false);
			formAddress.setWidth100();
			formAddress.setTitleWidth(80);
			formAddress.setNumCols(3);
			formAddress.setTitleOrientation(TitleOrientation.TOP);
			formAddress.setCanDragResize(false);

			HeaderItem headerItemAddr = new HeaderItem();
			headerItemAddr.setValue("აბონენტის მისამართი");
			headerItemAddr.setTextBoxStyle("headerStyle");

			citiesItem = new ComboBoxItem();
			citiesItem.setTitle("ქალაქი");
			citiesItem.setName("town_id");
			citiesItem.setWidth(170);
			citiesItem.setFetchMissingValues(true);
			citiesItem.setFilterLocally(false);
			citiesItem.setAddUnknownValues(false);

			streetItem = new ComboBoxItem();
			streetItem.setTitle("ქუჩა");
			streetItem.setName("street_id");
			streetItem.setWidth(322);
			streetItem.setFetchMissingValues(true);
			streetItem.setFilterLocally(false);
			streetItem.setAddUnknownValues(false);

			regionItem = new ComboBoxItem();
			regionItem.setTitle("უბანი");
			regionItem.setName("town_district_id");
			regionItem.setWidth(217);
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

			streetDescrItem = new TextAreaItem();
			streetDescrItem.setTitle("ქუჩის აღწერა");
			streetDescrItem.setName("streetDescrItem");
			streetDescrItem.setWidth(709);
			streetDescrItem.setColSpan(3);
			streetDescrItem.setHeight(50);
			streetDescrItem.setCanEdit(false);

			formAddress.setFields(headerItemAddr, citiesItem, streetItem,
					regionItem, streetDescrItem);

			DynamicForm readOnlyForm = new DynamicForm();
			readOnlyForm.setPadding(10);
			readOnlyForm.setAutoFocus(false);
			readOnlyForm.setWidth100();
			readOnlyForm.setNumCols(6);
			readOnlyForm.setTitleOrientation(TitleOrientation.TOP);

			HeaderItem headerItemAddrParams = new HeaderItem();
			headerItemAddrParams.setValue("მისამართის პარამეტრები");
			headerItemAddrParams.setTextBoxStyle("headerStyle");

			oldAddItem = new TextItem();
			oldAddItem.setTitle("ძველი მისამართი");
			oldAddItem.setName("oldAddItem");
			oldAddItem.setWidth(280);
			oldAddItem.setCanEdit(false);

			boolean hasPermission = CommonSingleton.getInstance()
					.hasPermission("106500");

			if (hasPermission && abonentRecord != null) {
				TextItem updDate = new TextItem();
				updDate.setTitle("განახლების თარიღი");
				updDate.setName("updDate");
				updDate.setWidth(160);
				Date dUpdDate = abonentRecord.getAttributeAsDate("upd_date");
				if (dUpdDate != null) {
					String formatedDate = DateTimeFormat
							.getFormat("dd-MM-yyyy").format(dUpdDate);
					updDate.setValue(formatedDate);
					updDate.setCanEdit(false);
				}

				TextItem updUser = new TextItem();
				updUser.setTitle("ვინ განაახლა");
				updUser.setName("updUser");
				updUser.setWidth(269);
				updUser.setValue(abonentRecord.getAttributeAsString("upd_user"));
				updUser.setCanEdit(false);

				readOnlyForm.setFields(headerItemAddrParams, oldAddItem,
						updDate, updUser);

			} else {
				readOnlyForm.setFields(headerItemAddrParams, oldAddItem);
			}

			formAddressParams = new DynamicForm();
			formAddressParams.setPadding(10);
			formAddressParams.setAutoFocus(false);
			formAddressParams.setWidth100();
			formAddressParams.setNumCols(6);
			formAddressParams.setTitleOrientation(TitleOrientation.TOP);

			adressOpCloseItem = new ComboBoxItem();
			adressOpCloseItem.setValueMap(ClientMapUtil.getInstance()
					.getAddrMapOpClose());
			adressOpCloseItem.setDefaultToFirstOption(true);
			adressOpCloseItem.setTitle("ღია/დაფარული");
			adressOpCloseItem.setName("hidden_by_request");
			adressOpCloseItem.setWidth(170);
			adressOpCloseItem.setFetchMissingValues(false);

			adressItem = new TextItem();
			adressItem.setTitle("სახლი");
			adressItem.setName("anumber");
			adressItem.setWidth(110);

			blockItem = new TextItem();
			blockItem.setTitle("კორპუსი");
			blockItem.setName("block");
			blockItem.setWidth(80);

			appartItem = new TextItem();
			appartItem.setTitle("ბინა");
			appartItem.setName("appt");
			appartItem.setWidth(80);

			addrAddInfoItem = new TextItem();
			addrAddInfoItem.setTitle("დამატებითი ინფორმაცია");
			addrAddInfoItem.setName("descr");
			addrAddInfoItem.setWidth(269);

			formAddressParams.setFields(adressOpCloseItem, adressItem,
					blockItem, appartItem, addrAddInfoItem);

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

			HTMLFlow htmlFlow = new HTMLFlow();
			htmlFlow.setWidth100();
			htmlFlow.setHeight(10);
			String contents = "<hr style=\"border: 1px solid #9aaaba;\"></hr>";
			htmlFlow.setContents(contents);
			htmlFlow.setStyleName("paddingLeft12");

			HTMLFlow htmlFlow1 = new HTMLFlow();
			htmlFlow1.setHeight(10);
			htmlFlow1.setWidth100();
			htmlFlow1.setContents(contents);
			htmlFlow1.setStyleName("paddingLeft12");

			HTMLFlow htmlFlow2 = new HTMLFlow();
			htmlFlow2.setHeight(10);
			htmlFlow2.setWidth100();
			htmlFlow2.setContents(contents);
			htmlFlow2.setStyleName("paddingLeft12");

			VLayout tableLayout = new VLayout(5);
			tableLayout.setWidth100();
			tableLayout.setStyleName("paddingLeft12");

			HLayout buttons = new HLayout(5);

			IButton addPhone = new IButton("დამატება");
			addPhone.setIcon("new.png");

			addPhone.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditAbPhone addEditAbPhone = new DlgAddEditAbPhone(
							null, listGridPhones);
					addEditAbPhone.show();
				}
			});

			IButton editPhone = new IButton("შესწორება");
			editPhone.setIcon("edit.png");

			editPhone.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					ListGridRecord listGridRecord = listGridPhones
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("მონიშნეთ ჩანაწერი ცხრილში!");
						return;
					}
					DlgAddEditAbPhone addEditAbPhone = new DlgAddEditAbPhone(
							listGridRecord, listGridPhones);
					addEditAbPhone.show();
				}
			});

			IButton deletePhone = new IButton("წაშლა");
			deletePhone.setIcon("delete.png");

			deletePhone.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = listGridPhones
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("მონიშნეთ ჩანაწერი ცხრილში!");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის წაშლა ? ",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										abPhonesClientDS
												.removeData(listGridRecord);
									}
								}
							});
				}
			});

			buttons.setMembers(addPhone, editPhone, deletePhone);
			tableLayout.setMembers(listGridPhones, buttons);

			hLayout.setMembers(formPersInfo, htmlFlow, tableLayout, htmlFlow1,
					formAddress, htmlFlow2, readOnlyForm, formAddressParams,
					hLayoutItem);

			addItem(hLayout);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});

			citiesItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					String value = citiesItem.getValueAsString();
					if (value == null) {
						return;
					}
					Integer city_id = null;
					try {
						city_id = Integer.parseInt(value);
					} catch (NumberFormatException e) {
						return;
					}
					streetItem.clearValue();
					regionItem.clearValue();
					fillStreetsCombo(null, city_id);
					fillCityRegionCombo(null, city_id);
				}
			});
			streetItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					ListGridRecord record = streetItem.getSelectedRecord();
					if (record == null) {
						return;
					}
					String descr = record.getAttribute("street_location_geo");
					if (descr == null) {
						descr = "";
					}
					streetDescrItem.setValue(descr);
					Integer city_region_id = record
							.getAttributeAsInt("city_region_id");
					Integer cityId = record.getAttributeAsInt("city_id");
					if (city_region_id != null && cityId != null) {
						fillCityRegionCombo(null, cityId);
						regionItem.setValue(city_region_id);
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
				}
			});
			saveItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					saveAbonentsData(abonentRecord, tabAbonent);
				}
			});
			fillCombos(abonentRecord);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public void fillFields(ListGridRecord abonentRecord,
			Integer defCityTbilisiId) {
		try {
			if (abonentRecord == null) {
				citiesItem.setValue(defCityTbilisiId);
				return;
			}
			Map<?, ?> values=abonentRecord.toMap();
			formPersInfo.setValues(values);
			formAddress.setValues(values);
			formAddressParams.setValues(values);
//			Integer firstname_id = abonentRecord
//					.getAttributeAsInt("firstname_id");
//			if (firstname_id != null) {
//				firstNameItem.setValue(firstname_id);
//			}
//			Integer lastname_id = abonentRecord
//					.getAttributeAsInt("lastname_id");
//			if (lastname_id != null) {
//				lastNameItem.setValue(lastname_id);
//			}
//			Integer city_id = abonentRecord.getAttributeAsInt("city_id");
//			if (city_id != null) {
//				citiesItem.setValue(city_id);
//			} else {
//				citiesItem.setValue(defCityTbilisiId);
//			}
//			Integer street_id = abonentRecord.getAttributeAsInt("street_id");
//			if (street_id != null) {
//				streetItem.setValue(street_id);
//			}
//			Integer city_region_id = abonentRecord
//					.getAttributeAsInt("city_region_id");
//			if (city_region_id != null) {
//				regionItem.setValue(city_region_id);
//			}
//
//			String street_location_geo = abonentRecord
//					.getAttribute("street_location_geo");
//			if (street_location_geo != null) {
//				streetDescrItem.setValue(street_location_geo);
//			}
//			String address_suffix_geo = abonentRecord
//					.getAttribute("address_suffix_geo");
//			if (address_suffix_geo != null) {
//				oldAddItem.setValue(address_suffix_geo);
//			}
//			Integer abonent_hide = abonentRecord
//					.getAttributeAsInt("abonent_hide");
//			if (abonent_hide != null) {
//				adressOpCloseItem.setValue(abonent_hide);
//			}
//
//			String addr_number = abonentRecord.getAttribute("addr_number");
//			if (addr_number != null) {
//				adressItem.setValue(addr_number);
//			}
//			String addr_block = abonentRecord.getAttribute("addr_block");
//			if (addr_block != null) {
//				blockItem.setValue(addr_block);
//			}
//			String addr_appt = abonentRecord.getAttribute("addr_appt");
//			if (addr_appt != null) {
//				appartItem.setValue(addr_appt);
//			}
//			String addr_descr = abonentRecord.getAttribute("addr_descr");
//			if (addr_descr != null) {
//				addrAddInfoItem.setValue(addr_descr);
//			}
			Integer subscriber_id = abonentRecord.getAttributeAsInt("subscriber_id");
			if (subscriber_id != null) {
				DataSource phoneDataSource = DataSource.get("AbPhonesDS");
				Criteria criteria = new Criteria();
				criteria.setAttribute("subscriber_id", subscriber_id);

				DSRequest dsRequest = new DSRequest();
				dsRequest.setAttribute("operationId", "getPhonesByAbonentId");
				phoneDataSource.fetchData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record records[] = response.getData();
						if (records != null && records.length > 0) {
							for (Record record : records) {
								listGridPhones.addData(record);
							}
						}
					}
				}, dsRequest);
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	public void fillCombos(final ListGridRecord abonentRecord) {
		try {
			Integer defCityTbilisiId = 2208;
			fillFirstNamesCombo(abonentRecord);
			fillLastNames(abonentRecord);
			fillCitiesCombo(abonentRecord, defCityTbilisiId);
			fillStreetsCombo(abonentRecord, defCityTbilisiId);
			fillCityRegionCombo(abonentRecord, defCityTbilisiId);

			fillFields(abonentRecord, defCityTbilisiId);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillFirstNamesCombo(final ListGridRecord abonentRecord) {
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
			//
			// if (abonentRecord != null) {
			// Integer firstname_id = abonentRecord
			// .getAttributeAsInt("firstname_id");
			// if (firstname_id != null) {
			// firstNameItem.setValue(firstname_id);
			//
			// Criteria criteria = firstNameItem
			// .getOptionCriteria();
			// if (criteria != null) {
			// String oldAttr = criteria
			// .getAttribute("firstname_Id");
			// if (oldAttr != null) {
			// Object nullO = null;
			// criteria.setAttribute("firstname_Id", nullO);
			// }
			// }
			// }
			// }
			// }
			// }, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillLastNames(final ListGridRecord abonentRecord) {
		try {
			DataSource lastNamesDS = DataSource.get("LastNameDS");
			lastNameItem
					.setOptionOperationId("searchLastNamesFromDBCustomForCombos");
			lastNameItem.setOptionDataSource(lastNamesDS);
			lastNameItem.setValueField("lastname_Id");
			lastNameItem.setDisplayField("lastname");

			Criteria criteria = new Criteria();
			lastNameItem.setOptionCriteria(criteria);
			lastNameItem.setAutoFetchData(false);

			// DSRequest dsRequest = new DSRequest();
			// dsRequest.setOperationId("searchLastNamesFromDBCustomForCombos");
			//
			// lastNamesDS.fetchData(criteria, new DSCallback() {
			// @Override
			// public void execute(DSResponse response, Object rawData,
			// DSRequest request) {
			// if (abonentRecord != null) {
			// Integer lastname_id = abonentRecord
			// .getAttributeAsInt("lastname_id");
			// if (lastname_id != null) {
			// lastNameItem.setValue(lastname_id);
			// Criteria criteria = lastNameItem
			// .getOptionCriteria();
			// if (criteria != null) {
			// String oldAttr = criteria
			// .getAttribute("lastname_id");
			// if (oldAttr != null) {
			// Object nullO = null;
			// criteria.setAttribute("lastname_id", nullO);
			// }
			// }
			// }
			// }
			// }
			// }, dsRequest);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillCitiesCombo(final ListGridRecord abonentRecord,
			final Integer defCityTbilisiId) {
		try {
			DataSource citiesDS = DataSource.get("CityDS");
			citiesItem.setOptionOperationId("searchCitiesFromDBForCombos");
			citiesItem.setOptionDataSource(citiesDS);
			citiesItem.setValueField("city_id");
			citiesItem.setDisplayField("city_name_geo");

			Criteria criteria = new Criteria();
			// criteria.setAttribute("country_id", 194);
			citiesItem.setOptionCriteria(criteria);
			citiesItem.setAutoFetchData(false);

			// DSRequest dsRequest = new DSRequest();
			// dsRequest.setOperationId("searchCitiesFromDBForCombos");
			//
			// citiesDS.fetchData(criteria, new DSCallback() {
			// @Override
			// public void execute(DSResponse response, Object rawData,
			// DSRequest request) {
			// Integer city_id = defCityTbilisiId;
			// if (abonentRecord != null) {
			// city_id = abonentRecord.getAttributeAsInt("city_id");
			// }
			// citiesItem.setValue(city_id);
			// Criteria criteria = citiesItem.getOptionCriteria();
			// if (criteria != null) {
			// String oldAttr = criteria.getAttribute("city_id");
			// if (oldAttr != null) {
			// Object nullO = null;
			// criteria.setAttribute("city_id", nullO);
			// }
			// }
			// }
			// }, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillStreetsCombo(final ListGridRecord abonentRecord,
			Integer defCityTbilisiId) {
		try {
			Integer city_id = defCityTbilisiId;
			if (abonentRecord != null) {
				Integer fromEdit = abonentRecord.getAttributeAsInt("city_id");
				if (fromEdit != null) {
					city_id = fromEdit;
				}
			}

			DataSource streetsDS = DataSource.get("StreetsNewDS");
			streetItem.setOptionOperationId("searchStreetFromDBForCombos");
			streetItem.setOptionDataSource(streetsDS);
			streetItem.setValueField("street_id");
			streetItem.setDisplayField("street_name_geo");

			Criteria criteria = new Criteria();
			criteria.setAttribute("city_id", city_id);
			streetItem.setOptionCriteria(criteria);
			streetItem.setAutoFetchData(false);

			// DSRequest dsRequest = new DSRequest();
			// dsRequest.setOperationId("searchStreetFromDBForCombos");
			//
			// streetsDS.fetchData(criteria, new DSCallback() {
			// @Override
			// public void execute(DSResponse response, Object rawData,
			// DSRequest request) {
			// if (abonentRecord != null) {
			// Integer street_id = abonentRecord
			// .getAttributeAsInt("street_id");
			// if (street_id != null) {
			// streetItem.setValue(street_id);
			//
			// Criteria criteria = streetItem.getOptionCriteria();
			// if (criteria != null) {
			// String oldAttr = criteria
			// .getAttribute("street_id");
			// if (oldAttr != null) {
			// Object nullO = null;
			// criteria.setAttribute("street_id", nullO);
			// }
			// }
			// }
			// }
			// }
			// }, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillCityRegionCombo(final ListGridRecord abonentRecord,
			Integer defCityTbilisiId) {
		try {
			Integer city_id = defCityTbilisiId;
			if (abonentRecord != null) {
				Integer fromEdit = abonentRecord.getAttributeAsInt("city_id");
				if (fromEdit != null) {
					city_id = fromEdit;
				}
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

			// DSRequest dsRequest = new DSRequest();
			// dsRequest.setOperationId("searchCityRegsFromDBForCombos");
			//
			// streetsDS.fetchData(criteria, new DSCallback() {
			// @Override
			// public void execute(DSResponse response, Object rawData,
			// DSRequest request) {
			// if (abonentRecord != null) {
			// Integer city_region_id = abonentRecord
			// .getAttributeAsInt("city_region_id");
			// if (city_region_id != null) {
			// regionItem.setValue(city_region_id);
			//
			// Criteria criteria = regionItem.getOptionCriteria();
			// if (criteria != null) {
			// String oldAttr = criteria
			// .getAttribute("city_region_id");
			// if (oldAttr != null) {
			// Object nullO = null;
			// criteria.setAttribute("city_region_id",
			// nullO);
			// }
			// }
			// }
			// }
			// }
			// }, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void saveAbonentsData(ListGridRecord abonentRecord,
			final TabAbonent tabAbonent) {
		try {
			ListGridRecord nameRecord = firstNameItem.getSelectedRecord();
			if (nameRecord == null
					|| nameRecord.getAttributeAsInt("firstname_Id") == null) {
				SC.say("გთხოვთ აირჩიოთ სახელი !");
				return;
			}
			ListGridRecord lastNameRecord = lastNameItem.getSelectedRecord();
			if (lastNameRecord == null
					|| lastNameRecord.getAttributeAsInt("lastname_Id") == null) {
				SC.say("გთხოვთ აირჩიოთ გვარი !");
				return;
			}

			ListGridRecord cityRecord = citiesItem.getSelectedRecord();
			if (cityRecord == null
					|| cityRecord.getAttributeAsInt("city_id") == null) {
				SC.say("გთხოვთ აირჩიოთ ქალაქი !");
				return;
			}

			String streetRecord = streetItem.getValueAsString();
			if (streetRecord == null) {
				SC.say("გთხოვთ აირჩიოთ ქუჩა !");
				return;
			}

			String cityRegion = regionItem.getValueAsString();

			String addrStatType = adressOpCloseItem.getValueAsString();
			if (addrStatType == null) {
				SC.say("გთხოვთ აირჩიოთ მისამართი ღიაა თუ დაფარული !");
				return;
			}
			Integer iAddrStatType = null;
			try {
				iAddrStatType = Integer.parseInt(addrStatType);
			} catch (NumberFormatException e) {
				SC.say("გთხოვთ აირჩიოთ მისამართი ღიაა თუ დაფარული !");
				return;
			}
			String sAddress = adressItem.getValueAsString();

			String sBlock = blockItem.getValueAsString();

			String sAppart = appartItem.getValueAsString();

			String sAddrAddInfo = addrAddInfoItem.getValueAsString();

			ListGridRecord phoneRecors[] = listGridPhones.getRecords();
			if (phoneRecors == null || phoneRecors.length <= 0) {
				SC.say("გთხოვთ შეიყვანოთ მინიმუმ 1 ნომერი !");
				return;
			}

			Map items = new LinkedHashMap();
			for (ListGridRecord listGridRecord : phoneRecors) {
				Map mapPhoneProps = new LinkedHashMap();
				mapPhoneProps.put("deleted", 0);
				mapPhoneProps.put("is_hide",
						listGridRecord.getAttributeAsInt("is_hide"));
				mapPhoneProps.put("is_hide_descr",
						listGridRecord.getAttribute("is_hide_descr"));
				mapPhoneProps.put("is_parallel",
						listGridRecord.getAttributeAsInt("is_parallel"));
				mapPhoneProps.put("is_parallel_descr",
						listGridRecord.getAttribute("is_parallel_descr"));
				mapPhoneProps
						.put("phone", listGridRecord.getAttribute("phone"));
				mapPhoneProps.put("phone_state",
						listGridRecord.getAttribute("phone_state"));
				mapPhoneProps.put("phone_state_id",
						listGridRecord.getAttributeAsInt("phone_state_id"));
				mapPhoneProps.put("phone_status",
						listGridRecord.getAttribute("phone_status"));
				mapPhoneProps.put("phone_status_id",
						listGridRecord.getAttribute("phone_status_id"));
				mapPhoneProps.put("phone_type",
						listGridRecord.getAttribute("phone_type"));
				mapPhoneProps.put("phone_type_id",
						listGridRecord.getAttributeAsInt("phone_type_id"));
				items.put(listGridRecord.getAttribute("phone"), mapPhoneProps);
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("listPhones", items);
			record.setAttribute("firstname_id",
					nameRecord.getAttributeAsInt("firstname_Id"));
			record.setAttribute("lastname_id",
					lastNameRecord.getAttributeAsInt("lastname_Id"));
			record.setAttribute("city_id",
					cityRecord.getAttributeAsInt("city_id"));
			record.setAttribute("street_id", streetRecord);
			if (cityRegion != null) {
				record.setAttribute("city_region_id", new Integer(cityRegion));
			}

			record.setAttribute("address_hide", iAddrStatType);
			record.setAttribute("addr_number", sAddress);
			record.setAttribute("addr_block", sBlock);
			record.setAttribute("addr_appt", sAppart);
			record.setAttribute("addr_descr", sAddrAddInfo);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			// Edit
			if (abonentRecord != null) {
				record.setAttribute("abonent_id",
						abonentRecord.getAttributeAsInt("abonent_id"));
				record.setAttribute("organization_id",
						abonentRecord.getAttributeAsInt("organization_id"));
				record.setAttribute("address_id",
						abonentRecord.getAttributeAsInt("address_id"));
			}
			DSRequest req = new DSRequest();
			// add
			if (abonentRecord == null) {
				req.setAttribute("operationId", "addAbonent");
				abonentsGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			}
			// edit
			else {
				req.setAttribute("operationId", "updateAbonent");
				abonentsGrid.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			}
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
