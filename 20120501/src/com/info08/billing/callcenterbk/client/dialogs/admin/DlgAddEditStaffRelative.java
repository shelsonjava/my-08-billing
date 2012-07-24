package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditStaffRelative extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem firstNameItem;
	private TextItem lastNameItem;
	private TextItem positionItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditStaffRelative(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "თანამშრომლის კავშირის  დამატება"
				: "თანამშრომლის კავშირის  მოდიფიცირება");

		setHeight(190);
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

		firstNameItem = new TextItem();
		firstNameItem.setTitle("სახელი");
		firstNameItem.setName("firstNameItem");
		firstNameItem.setWidth(250);

		lastNameItem = new TextItem();
		lastNameItem.setTitle("გვარი");
		lastNameItem.setName("lastNameItem");
		lastNameItem.setWidth(250);

		positionItem = new TextItem();
		positionItem.setTitle("პოზიცია");
		positionItem.setName("positionItem");
		positionItem.setWidth(250);

		dynamicForm.setFields(firstNameItem, lastNameItem, positionItem);

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

			String first_name = editRecord.getAttributeAsString("first_name");
			if (first_name != null) {
				firstNameItem.setValue(first_name);
			}

			String last_name = editRecord.getAttributeAsString("last_name");
			if (last_name != null) {
				lastNameItem.setValue(last_name);
			}

			String position = editRecord.getAttributeAsString("position");
			if (position != null) {
				positionItem.setValue(position);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {

		Record record = new Record();
		boolean edit = false;
		if (edit = editRecord != null) {
			record.setAttribute("staff_relative_id",
					editRecord.getAttribute("staff_relative_id"));
		}

		record.setAttribute("first_name", firstNameItem.getValueAsString());

		record.setAttribute("last_name", lastNameItem.getDisplayValue());

		record.setAttribute("position", positionItem.getValueAsString());

		if (edit) {
			listGrid.updateData(record);
		} else {
			listGrid.addData(record);
		}

		destroy();
	}
}
