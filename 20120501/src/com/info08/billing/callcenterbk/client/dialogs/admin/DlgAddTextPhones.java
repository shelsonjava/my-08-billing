package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.LinkedHashMap;
import java.util.TreeSet;

import com.info08.billing.callcenterbk.client.CallCenterBK;
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
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddTextPhones extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextAreaItem phonesItem;

	private DlgContractorPhones contractorPhones = null;
	private Integer organization_id;
	private DataSource ds;

	public DlgAddTextPhones(DlgContractorPhones contractorPhones,
			Integer organization_id) {
		try {
			this.contractorPhones = contractorPhones;
			this.organization_id = organization_id;
			this.ds = DataSource.get("CorporateClientsDS");
			setTitle(CallCenterBK.constants.phones());

			setHeight(400);
			setWidth(600);
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
			dynamicForm.setTitleWidth(0);
			dynamicForm.setNumCols(1);
			dynamicForm.setTitleOrientation(TitleOrientation.TOP);
			hLayout.addMember(dynamicForm);

			phonesItem = new TextAreaItem();
			phonesItem.setTitle(CallCenterBK.constants.phoneList());
			phonesItem.setName("phonesItem");
			phonesItem.setWidth(555);
			phonesItem.setHeight(300);

			dynamicForm.setFields(phonesItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
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
					save();
				}
			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String phonesStr = phonesItem.getValueAsString();
			if (phonesStr == null || phonesStr.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterStartPhoneList());
				return;
			}
			TreeSet<String> phones = new TreeSet<String>();

			phonesStr = phonesStr.trim();
			String phoneLineListArr[] = phonesStr.split("\n");
			for (int i = 0; i < phoneLineListArr.length; i++) {
				String str = phoneLineListArr[i].trim();
				String phoneListArr[] = str.split(",");
				if (phoneListArr == null || phoneListArr.length <= 0) {
					SC.say(CallCenterBK.constants.plzEnterStartPhoneList());
					return;
				}

				String error1 = CallCenterBK.constants.invalidPhone() + " : ";

				for (String phone : phoneListArr) {
					String phoneItem = phone.trim();
					if (phoneItem.length() < 7) {
						SC.say((error1 + phoneItem));
						return;
					}
					try {
						new Long(phoneItem);
					} catch (Exception e) {
						SC.say((error1 + phoneItem));
						return;
					}
					phones.add(phoneItem);
				}
			}

			checkNumbers(phones);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void checkNumbers(final TreeSet<String> phones) throws Exception {
		DSRequest req = new DSRequest();
		Criteria cr = new Criteria();

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (String phone : phones) {
			map.put(phone, phone);
		}
		cr.setAttribute("contractorAdvPhones", map);
		cr.setAttribute("organization_id", organization_id);

		req.setOperationId("checkPhones");
		ds.fetchData(cr, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				setNumbers(response.getData());
				destroy();

			}
		}, req);

	}

	private void setNumbers(Record[] data) {
		String unsetPhones = "";
		for (Record record : data) {
			String phone = record.getAttribute("phone");
			Long phone_number_id = record.getAttributeAsLong("phone_number_id");
			if (phone_number_id.longValue() != 1) {
				if (unsetPhones.length() != 0)
					unsetPhones += ",";
				unsetPhones += phone;
			} else
				contractorPhones.addPhone(phone);
		}
		if (unsetPhones.length() != 0) {
			SC.warn("შემდეგი ნომრები ამ ორგანიზაციას არ ეკუთვნოდა, და შესაბამისად არც დაემატა:"
					+ unsetPhones);
		}
	}
}
