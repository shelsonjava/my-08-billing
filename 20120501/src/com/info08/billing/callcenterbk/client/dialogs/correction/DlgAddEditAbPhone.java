package com.info08.billing.callcenterbk.client.dialogs.correction;

import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditAbPhone extends Window {

	private VLayout hLayout;
	private DynamicForm formPhone;
	private TextItem phoneItem;
	private ComboBoxItem isHideItem;
	private ComboBoxItem isParallelItem;
	private ComboBoxItem phoneStatusItem;
	private ComboBoxItem phoneStateItem;
	private ComboBoxItem phoneTypeItem;
	private ListGrid listGridPhones;

	public DlgAddEditAbPhone(final ListGridRecord listGridRecord,
			ListGrid listGridPhones) {
		this.listGridPhones = listGridPhones;
		setWidth(750);
		setHeight(150);
		setTitle(listGridRecord == null ? "ახალი ნომრის დამატება"
				: "ნომრის შესწორება");
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

		formPhone = new DynamicForm();
		formPhone.setPadding(10);
		formPhone.setAutoFocus(true);
		formPhone.setWidth100();
		formPhone.setTitleWidth(80);
		formPhone.setNumCols(7);
		formPhone.setTitleOrientation(TitleOrientation.TOP);

		phoneItem = new TextItem();
		phoneItem.setTitle("ტელეფონი");
		phoneItem.setName("phone");
		phoneItem.setWidth(170);

		isHideItem = new ComboBoxItem();
		isHideItem.setValueMap(ClientMapUtil.getInstance().getMapOpClose());
		isHideItem.setDefaultToFirstOption(true);
		isHideItem.setTitle("ღია/დაფარული");
		isHideItem.setName("is_hide");
		isHideItem.setWidth(112);
		isHideItem.setFetchMissingValues(false);

		isParallelItem = new ComboBoxItem();
		isParallelItem.setValueMap(ClientMapUtil.getInstance().getMapParall());
		isParallelItem.setDefaultToFirstOption(true);
		isParallelItem.setTitle("პარალელური");
		isParallelItem.setName("is_parallel");
		isParallelItem.setWidth(120);
		isParallelItem.setFetchMissingValues(false);

		phoneStatusItem = new ComboBoxItem();
		phoneStatusItem.setValueMap(ClientMapUtil.getInstance().getMapStatuses());
		phoneStatusItem.setDefaultToFirstOption(true);
		phoneStatusItem.setTitle("სტატუსი");
		phoneStatusItem.setName("phone_status_id");
		phoneStatusItem.setWidth(100);
		phoneStatusItem.setFetchMissingValues(false);

		phoneStateItem = new ComboBoxItem();
		phoneStateItem.setValueMap(ClientMapUtil.getInstance().getMapStates());
		phoneStateItem.setDefaultToFirstOption(true);
		phoneStateItem.setTitle("მდგომარეობა");
		phoneStateItem.setName("phone_state_id");
		phoneStateItem.setWidth(100);
		phoneStateItem.setFetchMissingValues(false);

		phoneTypeItem = new ComboBoxItem();
		phoneTypeItem.setValueMap(ClientMapUtil.getInstance().getMapTypes());
		phoneTypeItem.setDefaultToFirstOption(true);
		phoneTypeItem.setTitle("ტიპი");
		phoneTypeItem.setName("phone_type_id");
		phoneTypeItem.setWidth(119);
		phoneTypeItem.setFetchMissingValues(false);

		if (listGridRecord != null) {
			phoneItem.setValue(listGridRecord.getAttribute("phone"));
			isHideItem.setValue(listGridRecord.getAttribute("is_hide"));
			isParallelItem.setValue(listGridRecord.getAttribute("is_parallel"));
			phoneStatusItem.setValue(listGridRecord
					.getAttribute("phone_status_id"));
			phoneStateItem.setValue(listGridRecord
					.getAttribute("phone_state_id"));
			phoneTypeItem
					.setValue(listGridRecord.getAttribute("phone_type_id"));
		}

		formPhone.setFields(phoneItem, isHideItem, isParallelItem,
				phoneStatusItem, phoneStateItem, phoneTypeItem);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);
		hLayoutItem.setMargin(10);

		IButton saveItem = new IButton();
		saveItem.setTitle("შენახვა");
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle("დახურვა");
		cancItem.setWidth(100);

		hLayoutItem.setMembers(saveItem, cancItem);
		hLayout.setMembers(formPhone, hLayoutItem);
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
				addRecord(listGridRecord);
			}
		});
	}

	private void addRecord(ListGridRecord listGridRecord) {
		try {
			String oPhone = phoneItem.getValueAsString();
			if (oPhone == null || oPhone.trim().equals("")) {
				SC.say("გთხოვთ შეიყვანოთ ტელეფონის ნომერი !");
				return;
			}
			Integer phone = null;
			try {
				phone = Integer.parseInt(oPhone);
			} catch (Exception e) {
				SC.say("არასწორი ტელეფონის ნომერი " + oPhone
						+ " !. ტელეფონის ნომერი შედგება მხოლოდ ციფრებისაგან. ");
				return;
			}
			if (oPhone.length() > 7) {
				SC.say("არასწორი ტელეფონის ნომერი " + oPhone
						+ " !. ტელეფონის ნომერი შედგება მხოლოდ 7 ციფრისაგან. ");
				return;
			}

			String opClose = isHideItem.getValueAsString();
			if (opClose == null) {
				SC.say("გთხოვთ აირჩიოთ ტელეფონის ნომერი ღიაა თუ დაფარული !");
				return;
			}
			Integer iOpClose = null;
			try {
				iOpClose = Integer.parseInt(opClose);
			} catch (NumberFormatException e) {
				SC.say("გთხოვთ აირჩიოთ ტელეფონის ნომერი ღიაა თუ დაფარული !");
				return;
			}
			String paralelUsual = isParallelItem.getValueAsString();
			if (paralelUsual == null) {
				SC.say("გთხოვთ აირჩიოთ ტელეფონის ნომერი პარალელურია თუ ჩვეულებრივი !");
				return;
			}
			Integer iParalelUsual = null;
			try {
				iParalelUsual = Integer.parseInt(paralelUsual);
			} catch (NumberFormatException e) {
				SC.say("გთხოვთ აირჩიოთ ტელეფონის ნომერი პარალელურია თუ ჩვეულებრივი !");
				return;
			}
			String phoneStatus = phoneStatusItem.getValueAsString();
			if (phoneStatus == null) {
				SC.say("გთხოვთ აირჩიოთ ტელეფონის სტატუსი !");
				return;
			}
			Integer iPhoneStatus = null;
			try {
				iPhoneStatus = Integer.parseInt(phoneStatus);
			} catch (NumberFormatException e) {
				SC.say("გთხოვთ აირჩიოთ ტელეფონის სტატუსი !");
				return;
			}

			String phoneState = phoneStateItem.getValueAsString();
			if (phoneState == null) {
				SC.say("გთხოვთ აირჩიოთ ტელეფონის მდგომარეობა !");
				return;
			}
			Integer iPhoneState = null;
			try {
				iPhoneState = Integer.parseInt(phoneState);
			} catch (NumberFormatException e) {
				SC.say("გთხოვთ აირჩიოთ ტელეფონის მდგომარეობა !");
				return;
			}

			String phoneType = phoneTypeItem.getValueAsString();
			if (phoneType == null) {
				SC.say("გთხოვთ აირჩიოთ ტელეფონის ტიპი !");
				return;
			}
			Integer iPhoneType = null;
			try {
				iPhoneType = Integer.parseInt(phoneType);
			} catch (NumberFormatException e) {
				SC.say("გთხოვთ აირჩიოთ ტელეფონის ტიპი !");
				return;
			}
			boolean isAdd = false;
			if (listGridRecord == null) {
				listGridRecord = new ListGridRecord();
				isAdd = true;
			}
			ListGridRecord records[] = listGridPhones.getRecords();
			if (records != null && records.length > 0) {
				for (ListGridRecord record : records) {
					if (listGridRecord.equals(record)) {
						continue;
					}
					String rPhone = record.getAttribute("phone");
					if (rPhone == null) {
						continue;
					}
					if (rPhone.equals(phone)) {
						SC.say("ასეთი ნომერი უკვე მინიჭებულია ამ აბონენტისათვის");
						return;
					}
				}
			}
			doSave(listGridRecord, phone, iOpClose, iParalelUsual,
					iPhoneStatus, iPhoneState, iPhoneType, isAdd);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void doSave(final ListGridRecord listGridRecord,
			final Integer phone, final Integer iOpClose,
			final Integer iParalelUsual, final Integer iPhoneStatus,
			final Integer iPhoneState, final Integer iPhoneType,
			final boolean isAdd1) {
		try {
			boolean check = true;
			if (!isAdd1) {
				String chPhone = listGridRecord.getAttributeAsString("phone");
				if (chPhone != null && !chPhone.trim().equalsIgnoreCase("")
						&& (phone + "").equals(chPhone)) {
					check = false;
				}
			}

			if (check) {
				DataSource dataSource = DataSource.get("AbPhonesDS");
				Criteria criteria = new Criteria();
				criteria.setAttribute("phone", phone + "");
				DSRequest req = new DSRequest();
				req.setAttribute("operationId", "getPhone");
				dataSource.fetchData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						try {

							Record records[] = response.getData();
							if (records != null && records.length > 0) {
								Record record = records[0];
								SC.say("ეს ნომერი უკვე მინიჭებულია აბონენტისათვის : "
										+ record.getAttribute("firstname"
												.toUpperCase())
										+ " "
										+ record.getAttribute("lastname"
												.toUpperCase())
										+ ", ნომერი - "
										+ record.getAttribute("phone"));
								return;
							} else {
								fillRecord(listGridRecord, phone, iOpClose,
										iParalelUsual, iPhoneStatus,
										iPhoneState, iPhoneType);
								if (isAdd1) {
									listGridPhones.addData(listGridRecord);
								} else {
									listGridPhones.updateData(listGridRecord);
								}
								destroy();
							}

						} catch (Exception e) {
							SC.say(e.toString());
						}
					}
				}, req);
			} else {
				fillRecord(listGridRecord, phone, iOpClose, iParalelUsual,
						iPhoneStatus, iPhoneState, iPhoneType);
				if (isAdd1) {
					listGridPhones.addData(listGridRecord);
				} else {
					listGridPhones.updateData(listGridRecord);
				}
				destroy();
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void fillRecord(ListGridRecord listGridRecord, Integer phone,
			Integer iOpClose, Integer iParalelUsual, Integer iPhoneStatus,
			Integer iPhoneState, Integer iPhoneType) throws CallCenterException {
		try {
			listGridRecord.setAttribute("phone", phone + "");
			listGridRecord.setAttribute("is_hide", iOpClose);
			listGridRecord.setAttribute("is_parallel", iParalelUsual);
			listGridRecord.setAttribute("phone_status_id", iPhoneStatus);
			listGridRecord.setAttribute("phone_state_id", iPhoneState);
			listGridRecord.setAttribute("phone_type_id", iPhoneType);
			listGridRecord.setAttribute("is_hide_descr", ClientMapUtil.getInstance()
					.getMapOpClose().get(iOpClose + ""));
			listGridRecord.setAttribute("is_parallel_descr", ClientMapUtil
					.getInstance().getMapParall().get(iParalelUsual + ""));
			listGridRecord.setAttribute("phone_status", ClientMapUtil.getInstance()
					.getMapStatuses().get(iPhoneStatus + ""));
			listGridRecord.setAttribute("phone_state", ClientMapUtil.getInstance()
					.getMapStates().get(iPhoneState + ""));
			listGridRecord.setAttribute("phone_type", ClientMapUtil.getInstance()
					.getMapTypes().get(iPhoneType + ""));
			listGridRecord.setAttribute("loggedUserName", CommonSingleton
					.getInstance().getSessionPerson() + "");
			listGridRecord.setAttribute("deleted", 0);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
