package com.info08.billing.callcenter.client.dialogs.admin;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.singletons.ClientMapUtil;
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

public class DlgAddEditTelCompInd extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem startIndexItem;
	private TextItem endIndexItem;
	private SelectItem typeItem;

	private ListGrid listGrid = null;
	private ListGridRecord listGridRecord = null;

	public DlgAddEditTelCompInd(ListGrid listGrid, ListGridRecord listGridRecord) {
		try {
			this.listGrid = listGrid;
			this.listGridRecord = listGridRecord;
			setTitle(listGridRecord == null ? CallCenter.constants
					.addTelCompInd() : CallCenter.constants.editTelCompInd());

			setHeight(160);
			setWidth(400);
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
			dynamicForm.setTitleWidth(150);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			startIndexItem = new TextItem();
			startIndexItem.setTitle(CallCenter.constants.startIndex());
			startIndexItem.setName("startIndexItem");
			startIndexItem.setWidth(250);

			endIndexItem = new TextItem();
			endIndexItem.setTitle(CallCenter.constants.endIndex());
			endIndexItem.setName("endIndexItem");
			endIndexItem.setWidth(250);

			typeItem = new SelectItem();
			typeItem.setTitle(CallCenter.constants.type());
			typeItem.setName("typeItem");
			typeItem.setWidth(250);
			typeItem.setDefaultToFirstOption(true);
			typeItem.setValueMap(ClientMapUtil.getInstance()
					.getTelCompIndTypes());

			dynamicForm.setFields(startIndexItem, endIndexItem, typeItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenter.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenter.constants.close());
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
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (listGridRecord == null) {
				return;
			}
			startIndexItem.setValue(listGridRecord.getAttributeAsInt("st_ind"));
			endIndexItem.setValue(listGridRecord.getAttributeAsInt("end_ind"));
			typeItem.setValue(listGridRecord.getAttributeAsInt("cr"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String startIndexStr = startIndexItem.getValueAsString();
			if (startIndexStr == null || startIndexStr.trim().equals("")) {
				SC.say(CallCenter.constants.plzEnterStartIndex());
				return;
			}
			startIndexStr = startIndexStr.trim();

			String endIndexStr = endIndexItem.getValueAsString();
			if (endIndexStr == null || endIndexStr.trim().equals("")) {
				SC.say(CallCenter.constants.plzEnterEndIndex());
				return;
			}
			endIndexStr = endIndexStr.trim();

			Integer startIndex = null;
			try {
				startIndex = Integer.parseInt(startIndexStr);
			} catch (Exception e) {
				SC.say(CallCenter.constants.invalidStartIndex());
				return;
			}

			Integer endIndex = null;
			try {
				endIndex = Integer.parseInt(endIndexStr);
			} catch (Exception e) {
				SC.say(CallCenter.constants.invalidEndIndex());
				return;
			}

			if (startIndexStr.length() < 6) {
				SC.say(CallCenter.constants.invalidStartIndex());
				return;
			}
			if (endIndexStr.length() < 6) {
				SC.say(CallCenter.constants.invalidEndIndex());
				return;
			}

			Integer cr = Integer.parseInt(typeItem.getValueAsString());

			ListGridRecord oldRecords[] = listGrid.getRecords();
			if (oldRecords != null && oldRecords.length > 0) {
				for (int i = 0; i < oldRecords.length; i++) {
					ListGridRecord listGridRecord = oldRecords[i];
					Integer st_ind = listGridRecord.getAttributeAsInt("st_ind");
					Integer end_ind = listGridRecord
							.getAttributeAsInt("end_ind");
					if (startIndex.intValue() > st_ind && startIndex <= end_ind) {
						SC.say(CallCenter.constants.invalidStartIndex());
						return;
					}
					if (endIndex.intValue() > st_ind && endIndex <= end_ind) {
						SC.say(CallCenter.constants.invalidEndIndex());
						return;
					}
				}
			}

			ListGridRecord dateRec = new ListGridRecord();
			dateRec.setAttribute("st_ind", startIndex);
			dateRec.setAttribute("end_ind", endIndex);
			dateRec.setAttribute("cr", cr);
			if (listGridRecord != null) {
				dateRec.setAttribute("tel_comp_id",
						listGridRecord.getAttributeAsInt("tel_comp_id"));

			}
			dateRec.setAttribute("tel_comp_id", cr);
			listGrid.addData(dateRec);
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}