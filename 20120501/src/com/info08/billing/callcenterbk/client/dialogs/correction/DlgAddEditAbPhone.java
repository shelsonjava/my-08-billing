package com.info08.billing.callcenterbk.client.dialogs.correction;

import java.util.Map;
import java.util.Set;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditAbPhone extends Window {

	private VLayout hLayout;
	private DynamicForm formPhone;
	private TextItem phoneItem;
	private SelectItem isHideItem;
	private SelectItem isParallelItem;
	private SelectItem phoneContractType;
	private SelectItem phoneStateItem;
	private SelectItem phoneTypeItem;
	private ListGrid listGridPhones;
	private Integer subscriber_id;

	public DlgAddEditAbPhone(final Record listGridRecord,
			ListGrid listGridPhones, Integer subscriber_id, String phone_number) {
		this.listGridPhones = listGridPhones;
		this.subscriber_id = subscriber_id;
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
		phoneItem.setName("phone");
		phoneItem.setTitle(CallCenterBK.constants.phone());
		phoneItem.setWidth(170);

		isHideItem = new SelectItem();
		isHideItem.setDefaultToFirstOption(true);
		isHideItem.setTitle(CallCenterBK.constants.openClose());
		isHideItem.setName("hidden_by_request");
		isHideItem.setWidth(112);
		isHideItem.setFetchMissingValues(false);
		ClientUtils.fillCombo(isHideItem, "ClosedOpenedDS",
				"searchClosedOpened", "id", "name");

		isParallelItem = new SelectItem();
		isParallelItem.setValueMap(ClientMapUtil.getInstance().getMapParall());
		isParallelItem.setDefaultToFirstOption(true);
		isParallelItem.setTitle(CallCenterBK.constants.paraller());
		isParallelItem.setName("is_parallel");
		isParallelItem.setWidth(120);
		isParallelItem.setFetchMissingValues(false);

		phoneContractType = new SelectItem();
		//ClientUtils.fillDescriptionCombo(phoneContractType,Constants.DT_PHONECONTRACTTYPES);
		phoneContractType.setValueMap(ClientMapUtil.getInstance().getMapStatuses());
		phoneContractType.setTitle(CallCenterBK.constants.status());
		phoneContractType.setName("phone_contract_type");
		phoneContractType.setWidth(100);
		phoneContractType.setDefaultToFirstOption(true);

		phoneStateItem = new SelectItem();
		//ClientUtils.fillDescriptionCombo(phoneStateItem,Constants.DT_PHONESTATES);
		phoneStateItem.setValueMap(ClientMapUtil.getInstance().getMapStates());
		phoneStateItem.setTitle(CallCenterBK.constants.state());
		phoneStateItem.setName("phone_state_id");
		phoneStateItem.setDefaultToFirstOption(true);

		phoneStateItem.setWidth(100);

		phoneTypeItem = new SelectItem();
		//ClientUtils.fillDescriptionCombo(phoneTypeItem, Constants.DT_PHONETYPES);
		phoneTypeItem.setValueMap(ClientMapUtil.getInstance().getMapTypes());
		phoneTypeItem.setTitle(CallCenterBK.constants.type());
		phoneTypeItem.setName("phone_type_id");
		phoneTypeItem.setWidth(119);
		phoneTypeItem.setDefaultToFirstOption(true);

		formPhone.setFields(phoneItem, isHideItem, isParallelItem,
				phoneContractType, phoneStateItem, phoneTypeItem);

		if (listGridRecord != null) {
			formPhone.setValues(listGridRecord.toMap());
		}

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
		if (phone_number != null)
			phoneItem.setValue(phone_number);
	}

	private void addRecord(Record listGridRecord) {
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
			/*if (oPhone.length() > 7) {
				SC.say("არასწორი ტელეფონის ნომერი " + oPhone
						+ " !. ტელეფონის ნომერი შედგება მხოლოდ 7 ციფრისაგან. ");
				return;
			}*/

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
			String phoneStatus = phoneContractType.getValueAsString();
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

	private void doSave(final Record listGridRecord, final Integer phone,
			final Integer iOpClose, final Integer iParalelUsual,
			final Integer iPhoneStatus, final Integer iPhoneState,
			final Integer iPhoneType, final boolean isAdd1) {
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
				if (subscriber_id != null)
					criteria.setAttribute("subscriber_id", subscriber_id);
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

	private void fillRecord(Record listGridRecord, Integer phone,
			Integer iOpClose, Integer iParalelUsual, Integer iPhoneStatus,
			Integer iPhoneState, Integer iPhoneType) throws CallCenterException {
		try {
			Map<?, ?> mp = formPhone.getValues();

			Set<?> keys = mp.keySet();
			for (Object key : keys) {
				String sKey = key.toString();
				listGridRecord.setAttribute(sKey, mp.get(key));
			}
			listGridRecord.setAttribute("phone", phone + "");
			listGridRecord.setAttribute(
					"hidden_by_request_descr",
					ClientMapUtil.getInstance().getMapOpClose()
							.get(iOpClose + ""));
			listGridRecord.setAttribute("is_parallel_descr", ClientMapUtil
					.getInstance().getMapParall().get(iParalelUsual + ""));
			listGridRecord.setAttribute("phone_contract_type_desr",
					phoneContractType.getDisplayValue());
			listGridRecord.setAttribute("phone_state",
					phoneStateItem.getDisplayValue());
			listGridRecord.setAttribute("phone_type",
					phoneTypeItem.getDisplayValue());
			listGridRecord.setAttribute("loggedUserName", CommonSingleton
					.getInstance().getSessionPerson() + "");
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
