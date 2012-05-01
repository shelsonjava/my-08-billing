package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.TreeSet;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditContractorPhones extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextAreaItem phonesItem;

	private ListGrid listGrid = null;

	public DlgAddEditContractorPhones(ListGrid listGrid) {
		try {
			this.listGrid = listGrid;
			setTitle(CallCenterBK.constants.addContractorPhones());

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
			phonesItem.setWidth(568);
			phonesItem.setHeight(305);

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
			phonesStr = phonesStr.trim();
			String phoneListArr[] = phonesStr.split(",");
			if (phoneListArr == null || phoneListArr.length <= 0) {
				SC.say(CallCenterBK.constants.plzEnterStartPhoneList());
				return;
			}
			TreeSet<String> phones = new TreeSet<String>();
			String error1 = CallCenterBK.constants.invalidPhone() + " : ";
			String error2 = CallCenterBK.constants.phoneAlreadyExists() + " : ";

			ListGridRecord oldRecords[] = listGrid.getRecords();

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
				if (oldRecords != null && oldRecords.length > 0) {
					for (ListGridRecord oldRecord : oldRecords) {
						String oldPhone = oldRecord
								.getAttributeAsString("phone");
						if (oldPhone == null) {
							continue;
						}
						if (oldPhone.trim().equals(phoneItem)) {
							SC.say((error2 + phoneItem));
							return;
						}
					}
				}
				phones.add(phoneItem);
			}

			for (String phone : phones) {
				ListGridRecord dateRec = new ListGridRecord();
				dateRec.setAttribute("phone", phone);
				listGrid.addData(dateRec);
			}
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
