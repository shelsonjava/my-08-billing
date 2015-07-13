package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditStaffFamousPeople extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem firstNameItem;
	private TextItem lastNameItem;
	private SelectItem relationTypeItem;
	private SelectItem sphereOfActivityItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditStaffFamousPeople(ListGrid listGrid, ListGridRecord pRecord) {
		super();
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "თანამშრომლის ცნობილი პიროვნების დამატება"
				: "თანამშრომლის ცნობილი პიროვნების მოდიფიცირება");

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

		relationTypeItem = new SelectItem();
		relationTypeItem.setTitle("კავშირი");
		relationTypeItem.setWidth(250);
		relationTypeItem.setName("relationTypeItem");
		relationTypeItem.setFetchMissingValues(true);
		relationTypeItem.setFilterLocally(false);
		relationTypeItem.setAddUnknownValues(false);

		ClientUtils.fillDescriptionCombo(relationTypeItem, 72000);

		sphereOfActivityItem = new SelectItem();
		sphereOfActivityItem.setTitle("მოღვაწეობის სფერო");
		sphereOfActivityItem.setWidth(250);
		sphereOfActivityItem.setName("sphereOfActivityItem");
		sphereOfActivityItem.setFetchMissingValues(true);
		sphereOfActivityItem.setFilterLocally(false);
		sphereOfActivityItem.setAddUnknownValues(false);

		ClientUtils.fillDescriptionCombo(sphereOfActivityItem, 73000);

		dynamicForm.setFields(firstNameItem, lastNameItem, relationTypeItem,
				sphereOfActivityItem);

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

			Long relation_type_id = new Long(
					editRecord.getAttributeAsString("relation_type_id"));
			if (relation_type_id != null) {
				relationTypeItem.setValue(relation_type_id);
			}

			Long sphere_of_activity_id = new Long(
					editRecord.getAttributeAsString("sphere_of_activity_id"));
			if (sphere_of_activity_id != null) {
				sphereOfActivityItem.setValue(sphere_of_activity_id);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {

		Record record = new Record();
		boolean edit = false;
		if (edit = editRecord != null) {
			record.setAttribute("staff_famous_people_id",
					editRecord.getAttribute("staff_famous_people_id"));
		}

		String first_name = firstNameItem.getValueAsString();
		if (first_name != null && !first_name.equals("")) {
			record.setAttribute("first_name", firstNameItem.getValueAsString());
		}

		String last_name = lastNameItem.getValueAsString();
		if (last_name != null && !last_name.equals("")) {
			record.setAttribute("last_name", lastNameItem.getValueAsString());
		}

		record.setAttribute("relation_type_id",
				relationTypeItem.getValueAsString());

		record.setAttribute("relation_type_id_descr",
				relationTypeItem.getDisplayValue());

		record.setAttribute("sphere_of_activity_id",
				sphereOfActivityItem.getValueAsString());

		record.setAttribute("sphere_of_activity_id_descr",
				sphereOfActivityItem.getDisplayValue());

		if (edit) {
			listGrid.updateData(record);
		} else {
			listGrid.addData(record);
		}

		destroy();
	}
}
