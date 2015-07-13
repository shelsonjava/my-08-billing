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
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditStaffLanguages extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private SelectItem languageDescrItem;
	private SelectItem languageLevelDescrItem;
	private TextAreaItem certificateItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditStaffLanguages(ListGrid listGrid, ListGridRecord pRecord) {
		super();
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "თანამშრომლის ენების ცოდნის დამატება"
				: "თანამშრომლის ენების ცოდნის მოდიფიცირება");

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

		languageDescrItem = new SelectItem();
		languageDescrItem.setTitle("ენა");
		languageDescrItem.setWidth(250);
		languageDescrItem.setName("languageDescrItem");
		languageDescrItem.setFetchMissingValues(true);
		languageDescrItem.setFilterLocally(false);
		languageDescrItem.setAddUnknownValues(false);

		ClientUtils.fillDescriptionCombo(languageDescrItem, 69000);

		languageLevelDescrItem = new SelectItem();
		languageLevelDescrItem.setTitle("ცოდნის დონე");
		languageLevelDescrItem.setWidth(250);
		languageLevelDescrItem.setName("languageLevelDescrItem");
		languageLevelDescrItem.setFetchMissingValues(true);
		languageLevelDescrItem.setFilterLocally(false);
		languageLevelDescrItem.setAddUnknownValues(false);

		ClientUtils.fillDescriptionCombo(languageLevelDescrItem, 70000);

		certificateItem = new TextAreaItem();
		certificateItem.setTitle("სერტიფიკატი");
		certificateItem.setName("certificateItem");
		certificateItem.setWidth(250);
		certificateItem.setHeight(50);

		dynamicForm.setFields(languageDescrItem, languageLevelDescrItem,
				certificateItem);

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

			Long language_descr_id = new Long(
					editRecord.getAttributeAsString("language_descr_id"));
			if (language_descr_id != null) {
				languageDescrItem.setValue(language_descr_id);
			}

			Long language_level_descr_id = new Long(
					editRecord.getAttributeAsString("language_level_descr_id"));
			if (language_level_descr_id != null) {
				languageLevelDescrItem.setValue(language_level_descr_id);
			}

			String certificate_descr = editRecord
					.getAttributeAsString("certificate_descr");
			if (certificate_descr != null) {
				certificateItem.setValue(certificate_descr);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {

		Record record = new Record();
		boolean edit = false;
		if (edit = editRecord != null) {
			record.setAttribute("staff_language_id",
					editRecord.getAttribute("staff_language_id"));
		}

		record.setAttribute("language_descr_id",
				languageDescrItem.getValueAsString());

		record.setAttribute("language_descr",
				languageDescrItem.getDisplayValue());

		record.setAttribute("language_level_descr_id",
				languageLevelDescrItem.getValueAsString());

		record.setAttribute("language_level_descr",
				languageLevelDescrItem.getDisplayValue());

		String certificate_descr = certificateItem.getValueAsString();
		if (certificate_descr != null && !certificate_descr.equals("")) {
			record.setAttribute("certificate_descr",
					certificateItem.getValueAsString());
		}

		if (edit) {
			listGrid.updateData(record);
		} else {
			listGrid.addData(record);
		}

		destroy();
	}
}
