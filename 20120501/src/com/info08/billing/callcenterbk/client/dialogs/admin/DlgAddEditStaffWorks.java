package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.Date;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditStaffWorks extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextAreaItem companyItem;
	private TextAreaItem positionItem;
	private DateItem workStartDateItem;
	private DateItem workEndDateItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditStaffWorks(ListGrid listGrid, ListGridRecord pRecord) {
		super();
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "თანამშრომლის სამსახურის  დამატება"
				: "თანამშრომლის სამსახურის მოდიფიცირება");

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

		companyItem = new TextAreaItem();
		companyItem.setTitle("კომპანია");
		companyItem.setName("companyItem");
		companyItem.setWidth(250);
		companyItem.setHeight(50);

		positionItem = new TextAreaItem();
		positionItem.setTitle("პოზიცია");
		positionItem.setName("positionItem");
		positionItem.setWidth(250);
		positionItem.setHeight(50);

		workStartDateItem = new DateItem();
		workStartDateItem.setTitle("დაწყება");
		workStartDateItem.setWidth(250);
		workStartDateItem.setName("workStartDateItem");
		workStartDateItem.setUseTextField(true);

		workEndDateItem = new DateItem();
		workEndDateItem.setTitle("დამთავრება");
		workEndDateItem.setWidth(250);
		workEndDateItem.setName("workEndDateItem");
		workEndDateItem.setUseTextField(true);

		dynamicForm.setFields(companyItem, positionItem, workStartDateItem,
				workEndDateItem);

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
			String work_place = editRecord.getAttributeAsString("work_place");
			if (work_place != null) {
				companyItem.setValue(work_place);
			}
			String work_position = editRecord
					.getAttributeAsString("work_position");
			if (work_position != null) {
				positionItem.setValue(work_position);
			}

			Date work_start_date = editRecord
					.getAttributeAsDate("work_start_date");
			if (work_start_date != null) {
				workStartDateItem.setValue(work_start_date);
			}

			Date work_end_date = editRecord.getAttributeAsDate("work_end_date");
			if (work_end_date != null) {
				workEndDateItem.setValue(work_end_date);
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {

		Record record = new Record();
		boolean edit = false;
		if (edit = editRecord != null) {
			record.setAttribute("staff_work_id",
					editRecord.getAttribute("staff_work_id"));
		}

		String work_place = companyItem.getValueAsString();
		if (work_place == null || work_place.equals("")) {
			SC.say("გთხოვთ შეიყვანოთ კომპანია !");
			return;
		} else {
			record.setAttribute("work_place", companyItem.getValueAsString());
		}

		String work_position = companyItem.getValueAsString();
		if (work_position == null || work_position.equals("")) {
			SC.say("გთხოვთ შეიყვანოთ კომპანია !");
			return;
		} else {
			record.setAttribute("work_position",
					positionItem.getValueAsString());
		}

		record.setAttribute("work_start_date",
				workStartDateItem.getValueAsDate());
		record.setAttribute("work_end_date", workEndDateItem.getValueAsDate());

		record.setAttribute("work_start_date_descr",
				workStartDateItem.getValueAsDate());
		record.setAttribute("work_end_date_descr",
				workEndDateItem.getValueAsDate());

		if (edit) {
			listGrid.updateData(record);
		} else {
			listGrid.addData(record);
		}

		destroy();
	}
}
