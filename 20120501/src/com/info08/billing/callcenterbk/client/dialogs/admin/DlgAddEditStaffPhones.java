package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
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

public class DlgAddEditStaffPhones extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private SelectItem staffPhoneTypeItem;
	private TextItem phoneItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditStaffPhones(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "თანამშრომლის ტელეფონის ნომერის დამატება"
				: "თანამშრომლის ტელეფონის ნომერის მოდიფიცირება");

		setHeight(130);
		setWidth(450);
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
		dynamicForm.setTitleWidth(180);
		dynamicForm.setNumCols(2);
		hLayout.addMember(dynamicForm);

		staffPhoneTypeItem = new SelectItem();
		staffPhoneTypeItem.setTitle("ტელეფონის ტიპი");
		staffPhoneTypeItem.setWidth(250);
		staffPhoneTypeItem.setName("staffPhoneTypeItem");
		staffPhoneTypeItem.setFetchMissingValues(true);
		staffPhoneTypeItem.setFilterLocally(false);
		staffPhoneTypeItem.setAddUnknownValues(false);

		ClientUtils.fillDescriptionCombo(staffPhoneTypeItem, 71000);

		phoneItem = new TextItem();
		phoneItem.setTitle("ნომერი");
		phoneItem.setName("phoneItem");
		phoneItem.setWidth(250);

		dynamicForm.setFields(staffPhoneTypeItem, phoneItem);

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
				save();
			}
		});

		addItem(hLayout);
		fillFields();
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}

			Long staff_phone_type_id = new Long(
					editRecord.getAttributeAsString("staff_phone_type_id"));
			if (staff_phone_type_id != null) {
				staffPhoneTypeItem.setValue(staff_phone_type_id);
			}

			String staff_phone = editRecord.getAttributeAsString("staff_phone");
			if (staff_phone != null) {
				phoneItem.setValue(staff_phone);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {

		Record record = new Record();
		boolean edit = false;
		if (edit = editRecord != null) {
			record.setAttribute("staff_phone_id",
					editRecord.getAttribute("staff_phone_id"));
		}

		record.setAttribute("staff_phone_type_id",
				staffPhoneTypeItem.getValueAsString());

		record.setAttribute("staff_phone_type",
				staffPhoneTypeItem.getDisplayValue());

		String staff_phone = phoneItem.getValueAsString();
		if (staff_phone == null || staff_phone.equals("")) {
			SC.say("გთხოვთ შეიყვანოთ ნომერი !");
			return;
		} else {
			record.setAttribute("staff_phone", phoneItem.getValueAsString());
		}

		if (edit) {
			listGrid.updateData(record);
		} else {
			listGrid.addData(record);
		}

		destroy();
	}
}
