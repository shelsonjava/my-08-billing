package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditStaffComputerSkills extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextAreaItem softwareItem;
	private TextAreaItem traningCourseItem;
	private TextAreaItem remarkItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditStaffComputerSkills(ListGrid listGrid,
			ListGridRecord pRecord) {
		super();
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "თანამშრომლის კომპიუტერული ცოდნის დამატება"
				: "თანამშრომლის კომპიუტერული ცოდნის მოდიფიცირება");

		setHeight(280);
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

		softwareItem = new TextAreaItem();
		softwareItem.setTitle("პროგრამა");
		softwareItem.setName("softwareItem");
		softwareItem.setWidth(250);
		softwareItem.setHeight(50);

		traningCourseItem = new TextAreaItem();
		traningCourseItem.setTitle("კურსის დასახელება");
		traningCourseItem.setName("traningCourseItem");
		traningCourseItem.setWidth(250);
		traningCourseItem.setHeight(50);

		remarkItem = new TextAreaItem();
		remarkItem.setTitle("დაწყება");
		remarkItem.setName("startYearItem");
		remarkItem.setWidth(250);
		remarkItem.setHeight(50);

		dynamicForm.setFields(softwareItem, traningCourseItem, remarkItem);

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
			String software = editRecord.getAttributeAsString("software");
			if (software != null) {
				softwareItem.setValue(software);
			}
			String training_course = editRecord
					.getAttributeAsString("training_course");
			if (training_course != null) {
				traningCourseItem.setValue(training_course);
			}
			String remark = editRecord.getAttributeAsString("remark");
			if (remark != null) {
				remarkItem.setValue(remark);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {

		Record record = new Record();
		boolean edit = false;
		if (edit = editRecord != null) {
			record.setAttribute("staff_comp_skill_id",
					editRecord.getAttribute("staff_comp_skill_id"));
		}

		String software = softwareItem.getValueAsString();
		if (software == null || software.equals("")) {
			SC.say("გთხოვთ შეიყვანოთ პროგრამა !");
			return;
		} else {
			record.setAttribute("software", softwareItem.getValueAsString());
		}

		String training_course = traningCourseItem.getValueAsString();
		if (training_course == null || training_course.equals("")) {
			SC.say("გთხოვთ შეიყვანოთ კურსის დასახელება !");
			return;
		} else {
			record.setAttribute("training_course",
					traningCourseItem.getValueAsString());
		}

		String remark = remarkItem.getValueAsString();
		if (remark == null || remark.equals("")) {
			SC.say("გთხოვთ შეიყვანოთ კომენტარი !");
			return;
		} else {
			record.setAttribute("remark", remarkItem.getValueAsString());
		}

		if (edit) {
			listGrid.updateData(record);
		} else {
			listGrid.addData(record);
		}

		destroy();
	}
}
