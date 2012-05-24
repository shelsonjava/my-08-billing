package com.info08.billing.callcenterbk.client.dialogs.correction;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.info08.billing.callcenterbk.client.content.TabAbonent;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.client.utils.FormItemDescr;
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

	final static Integer defCityTbilisiId = 2208;

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
			ClientUtils.fillCombo(firstNameItem, "FirstNameDS",
					"searchFNamesFromDBCustomForCombos", "firstname_Id",
					"firstname");

			lastNameItem = new ComboBoxItem();
			lastNameItem.setTitle("გვარი");
			lastNameItem.setName("family_name");
			lastNameItem.setWidth(324);

			ClientUtils.fillCombo(lastNameItem, "LastNameDS",
					"searchLastNamesFromDBCustomForCombos", "lastname_Id",
					"lastname");


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
			ClientUtils.fillCombo(citiesItem, "TownsDS",
					"searchCitiesFromDBForCombos", "town_id", "town_name");

			streetItem = new ComboBoxItem();
			streetItem.setTitle("ქუჩა");
			streetItem.setName("street_id");
			streetItem.setWidth(322);

			Map<String, String> aditionalCriteria = new TreeMap<String, String>();
			
			aditionalCriteria.put("town_id", defCityTbilisiId.toString());

			ClientUtils.fillCombo(streetItem, "StreetsDS",
					"searchStreetFromDBForCombos", "street_id", "street_name",
					aditionalCriteria);


			regionItem = new ComboBoxItem();
			regionItem.setTitle("უბანი");
			regionItem.setName("town_district_id");
			regionItem.setWidth(217);

			ClientUtils.fillCombo(regionItem, "TownDistrictDS",
					"searchCityRegsFromDBForCombos", "town_district_id",
					"town_district_name", aditionalCriteria);


			streetDescrItem = new TextAreaItem();
			streetDescrItem.setTitle("ქუჩის აღწერა");
			streetDescrItem.setName("street_location");
			streetDescrItem.setWidth(709);
			streetDescrItem.setColSpan(3);
			streetDescrItem.setHeight(50);
			streetDescrItem.setCanEdit(false);

			ClientUtils.makeDependancy(citiesItem, "town_id", streetItem,
					regionItem);
			
			ClientUtils.makeDependancy(streetItem, true, new FormItemDescr(
					regionItem, "street_id", "town_district_id"),
					new FormItemDescr(streetDescrItem, "",
							"street_location"));

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
			Map<?, ?> values = abonentRecord.toMap();
			formPersInfo.setValues(values);
			formAddress.setValues(values);
			formAddressParams.setValues(values);
			
			Integer subscriber_id = abonentRecord
					.getAttributeAsInt("subscriber_id");
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

			

			fillFields(abonentRecord, defCityTbilisiId);
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
