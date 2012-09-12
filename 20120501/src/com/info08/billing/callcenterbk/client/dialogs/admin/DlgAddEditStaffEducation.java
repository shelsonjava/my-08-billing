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
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditStaffEducation extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextAreaItem collegeNameItem;
	private TextAreaItem facultyNameItem;
	private SelectItem degreeSelectItem;
	private TextItem startYearItem;
	private TextItem endYearItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditStaffEducation(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "თანამშრომლის განათლების დამატება"
				: "თანამშრომლის განათლების მოდიფიცირება");

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

		degreeSelectItem = new SelectItem();
		degreeSelectItem.setTitle("წოდება");
		degreeSelectItem.setWidth(250);
		degreeSelectItem.setName("degreeSelectItem");
		degreeSelectItem.setFetchMissingValues(true);
		degreeSelectItem.setFilterLocally(false);
		degreeSelectItem.setAddUnknownValues(false);

		ClientUtils.fillDescriptionCombo(degreeSelectItem, 67000);

		collegeNameItem = new TextAreaItem();
		collegeNameItem.setTitle("დაწესებულების დასახელება");
		collegeNameItem.setName("collegeNameItem");
		collegeNameItem.setWidth(250);
		collegeNameItem.setHeight(50);

		facultyNameItem = new TextAreaItem();
		facultyNameItem.setTitle("ფაკულტეტი");
		facultyNameItem.setName("facultyNameItem");
		facultyNameItem.setWidth(250);
		facultyNameItem.setHeight(50);

		startYearItem = new TextItem();
		startYearItem.setTitle("დაწყება");
		startYearItem.setName("startYearItem");
		startYearItem.setWidth(250);
		startYearItem.setMask("0000");

		endYearItem = new TextItem();
		endYearItem.setTitle("დამთავრება");
		endYearItem.setName("endYearItem");
		endYearItem.setWidth(250);
		endYearItem.setMask("0000");

		dynamicForm.setFields(collegeNameItem, facultyNameItem,
				degreeSelectItem, startYearItem, endYearItem);

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
			String college_name = editRecord
					.getAttributeAsString("college_name");
			if (college_name != null) {
				collegeNameItem.setValue(college_name);
			}
			String faculty_name = editRecord
					.getAttributeAsString("faculty_name");
			if (faculty_name != null) {
				facultyNameItem.setValue(faculty_name);
			}
			String start_year = editRecord.getAttributeAsString("start_year");
			if (start_year != null) {
				startYearItem.setValue(start_year);
			}
			String end_year = editRecord.getAttributeAsString("end_year");
			if (end_year != null) {
				endYearItem.setValue(end_year);
			}

			Long degree_descr_id = new Long(
					editRecord.getAttributeAsString("degree_descr_id"));
			if (degree_descr_id != null) {
				degreeSelectItem.setValue(degree_descr_id);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {

		Record record = new Record();
		boolean edit = false;
		if (edit = editRecord != null) {
			record.setAttribute("staff_education_id",
					editRecord.getAttribute("staff_education_id"));
		}

		String college_name = collegeNameItem.getValueAsString();
		if (college_name == null || college_name.equals("")) {
			SC.say("გთხოვთ შეიყვანოთ დაწესებულების დასახელება !");
			return;
		} else {
			record.setAttribute("college_name",
					collegeNameItem.getValueAsString());
		}

		String faculty_name = facultyNameItem.getValueAsString();
		if (faculty_name == null || faculty_name.equals("")) {
			SC.say("გთხოვთ შეიყვანოთ ფაკულტეტი !");
			return;
		} else {
			record.setAttribute("faculty_name",
					facultyNameItem.getValueAsString());
		}

		record.setAttribute("degree_descr_id",
				degreeSelectItem.getValueAsString());
		record.setAttribute("degree_descr", degreeSelectItem.getDisplayValue());
		//int start_year = 0;
		if (startYearItem.getValueAsString() != null) {
			//start_year = new Long(startYearItem.getValueAsString()).intValue();
			record.setAttribute("start_year", startYearItem.getValueAsString());
		}

		//int end_year = 0;
		if (endYearItem.getValueAsString() != null) {
			//end_year = new Long(endYearItem.getValueAsString()).intValue();
			record.setAttribute("end_year", endYearItem.getValueAsString());
		}

//		GregorianCalendar gregorianCalendar = new GregorianCalendar();
//		gregorianCalendar.setTimeInMillis(System.currentTimeMillis());
//		int currentYear = gregorianCalendar.get(Calendar.YEAR);
//
//		if (start_year < 1950 || start_year > currentYear) {
//			SC.say("დაწყების წელი უნდა იყოს  ინტერვალში 1950-დან "
//					+ currentYear + "-მდე !");
//			return;
//		}
//
//		if (end_year < 1950 || end_year > currentYear) {
//			SC.say("დამთავრების წელი უნდა იყოს  ინტერვალში 1950-დან "
//					+ currentYear + "-მდე !");
//			return;
//		}
//
//		if (start_year >= end_year) {
//			SC.say("დაწყების და დამთავრების წლები შეყვანილია არასწორად !");
//			return;
//		}

		record.setAttribute("se_years", startYearItem.getValueAsString()
				+ " - " + endYearItem.getValueAsString());

		if (edit) {
			listGrid.updateData(record);
		} else {
			listGrid.addData(record);
		}

		destroy();
	}
}
