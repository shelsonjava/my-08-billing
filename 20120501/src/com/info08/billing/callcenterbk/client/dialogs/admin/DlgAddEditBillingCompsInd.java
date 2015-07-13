package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
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

public class DlgAddEditBillingCompsInd extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem startIndexItem;
	private TextItem endIndexItem;
	private SelectItem typeItem;
	private SelectItem typeItem1;

	private ListGrid listGrid = null;
	private ListGridRecord listGridRecord = null;

	public DlgAddEditBillingCompsInd(ListGrid listGrid,
			ListGridRecord listGridRecord) {
		super();
		try {
			this.listGrid = listGrid;
			this.listGridRecord = listGridRecord;
			setTitle(listGridRecord == null ? CallCenterBK.constants
					.addBillingCompInd() : CallCenterBK.constants
					.editBillingCompInd());

			setHeight(180);
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
			startIndexItem.setTitle(CallCenterBK.constants.startIndex());
			startIndexItem.setName("startIndexItem");
			startIndexItem.setWidth(250);

			endIndexItem = new TextItem();
			endIndexItem.setTitle(CallCenterBK.constants.endIndex());
			endIndexItem.setName("endIndexItem");
			endIndexItem.setWidth(250);

			typeItem = new SelectItem();
			typeItem.setTitle(CallCenterBK.constants.type());
			typeItem.setName("typeItem");
			typeItem.setWidth(250);
			typeItem.setDefaultToFirstOption(true);
			typeItem.setValueMap(ClientMapUtil.getInstance()
					.getBillingCompIndTypes());

			typeItem1 = new SelectItem();
			typeItem1.setTitle(CallCenterBK.constants.type());
			typeItem1.setName("typeItem1");
			typeItem1.setWidth(250);
			typeItem1.setDefaultToFirstOption(true);
			typeItem1.setValueMap(ClientMapUtil.getInstance()
					.getBillingCompIndTypes1());

			dynamicForm.setFields(startIndexItem, endIndexItem, typeItem,
					typeItem1);

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
			startIndexItem.setValue(listGridRecord
					.getAttributeAsInt("bill_index_start"));
			endIndexItem.setValue(listGridRecord
					.getAttributeAsInt("bill_index_end"));
			typeItem.setValue(listGridRecord
					.getAttributeAsInt("applied_wholly"));
			typeItem1.setValue(listGridRecord.getAttributeAsInt("calcul_type"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String startIndexStr = startIndexItem.getValueAsString();
			if (startIndexStr == null || startIndexStr.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterStartIndex());
				return;
			}
			startIndexStr = startIndexStr.trim();

			String endIndexStr = endIndexItem.getValueAsString();
			if (endIndexStr == null || endIndexStr.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterEndIndex());
				return;
			}
			endIndexStr = endIndexStr.trim();

			Integer startIndex = null;
			try {
				startIndex = Integer.parseInt(startIndexStr);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.invalidStartIndex());
				return;
			}

			Integer endIndex = null;
			try {
				endIndex = Integer.parseInt(endIndexStr);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.invalidEndIndex());
				return;
			}

			if (startIndexStr.length() < 6) {
				SC.say(CallCenterBK.constants.invalidStartIndex());
				return;
			}
			if (endIndexStr.length() < 6) {
				SC.say(CallCenterBK.constants.invalidEndIndex());
				return;
			}
			if (startIndex.intValue() >= endIndex.intValue()) {
				SC.say(CallCenterBK.constants.invalidEndIndex());
				return;
			}

			Integer cr = Integer.parseInt(typeItem.getValueAsString());
			Integer count_type = Integer.parseInt(typeItem1.getValueAsString());

			ListGridRecord oldRecords[] = listGrid.getRecords();
			if (oldRecords != null && oldRecords.length > 0) {
				for (int i = 0; i < oldRecords.length; i++) {
					ListGridRecord item = oldRecords[i];
					if (item.equals(listGridRecord)) {
						continue;
					}
					Integer ind_id = item.getAttributeAsInt("bill_index_id");
					if (ind_id != null && listGridRecord != null) {
						Integer ind_id1 = listGridRecord
								.getAttributeAsInt("bill_index_id");
						if (ind_id1 != null
								&& ind_id1.intValue() == ind_id.intValue()) {
							continue;
						}
					}

					Integer st_ind = item.getAttributeAsInt("bill_index_start");
					Integer end_ind = item.getAttributeAsInt("bill_index_end");
					if (startIndex.intValue() >= st_ind
							&& startIndex <= end_ind) {
						SC.say(CallCenterBK.constants.invalidStartIndex());
						return;
					}
					if (endIndex.intValue() >= st_ind && endIndex <= end_ind) {
						SC.say(CallCenterBK.constants.invalidEndIndex());
						return;
					}
				}
			}

			ListGridRecord dateRec = null;
			boolean isUpdate = false;
			if (listGridRecord == null) {
				dateRec = new ListGridRecord();
			} else {
				isUpdate = true;
				dateRec = listGridRecord;
			}
			dateRec.setAttribute("bill_index_start", startIndex);
			dateRec.setAttribute("bill_index_end", endIndex);
			dateRec.setAttribute("applied_wholly", cr);
			dateRec.setAttribute("calcul_type", count_type);
			dateRec.setAttribute("calcul_type_descr",
					typeItem1.getDisplayValue());

			dateRec.setAttribute("applied_wholly_descr",
					typeItem.getDisplayValue());
			if (listGridRecord != null) {
				dateRec.setAttribute("billing_company_id",
						listGridRecord.getAttributeAsInt("billing_company_id"));
				dateRec.setAttribute("bill_index_id",
						listGridRecord.getAttributeAsInt("bill_index_id"));
			}
			dateRec.setAttribute("billing_company_id", cr);
			if (isUpdate) {
				listGrid.updateData(dateRec);
			} else {
				listGrid.addData(dateRec);
			}

			destroy();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
