package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
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

public class DlgAddEditContractorPrice extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem priceCountStartItem;
	private TextItem priceCountEndItem;
	private TextItem priceItem;

	private ListGridRecord record = null;
	private ListGrid listGrid = null;

	public DlgAddEditContractorPrice(ListGrid listGrid, ListGridRecord record) {
		try {
			this.listGrid = listGrid;
			this.record = record;
			setTitle(record == null ? CallCenter.constants.addContractor()
					: CallCenter.constants.editContractor());

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
			dynamicForm.setTitleWidth(200);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			priceCountStartItem = new TextItem();
			priceCountStartItem.setTitle(CallCenter.constants.startCount());
			priceCountStartItem.setName("priceCountStartItem");
			priceCountStartItem.setWidth(200);
			priceCountStartItem.setKeyPressFilter("[0-9]");

			priceCountEndItem = new TextItem();
			priceCountEndItem.setTitle(CallCenter.constants.endCount());
			priceCountEndItem.setName("priceCountEndItem");
			priceCountEndItem.setWidth(200);
			priceCountEndItem.setKeyPressFilter("[0-9]");

			priceItem = new TextItem();
			priceItem.setTitle(CallCenter.constants.price());
			priceItem.setName("priceItem");
			priceItem.setWidth(200);
			priceItem.setKeyPressFilter("[0-9\\.]");

			dynamicForm.setFields(priceCountStartItem, priceCountEndItem,
					priceItem);

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
			if (record == null) {
				return;
			}
			priceCountStartItem.setValue(record
					.getAttributeAsInt("call_count_start"));
			priceCountEndItem.setValue(record
					.getAttributeAsInt("call_count_end"));
			priceItem.setValue(record.getAttributeAsString("price"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String priceCountStartStr = priceCountStartItem.getValueAsString();
			if (priceCountStartStr == null
					|| priceCountStartStr.trim().equals("")) {
				SC.say(CallCenter.constants.plzEnterStartPrice());
				return;
			}
			Integer startPriceCount = null;
			try {
				startPriceCount = Integer.parseInt(priceCountStartStr);
			} catch (Exception e) {
				SC.say(CallCenter.constants.invalidStartPrice());
				return;
			}

			if (startPriceCount.intValue() < 0) {
				SC.say(CallCenter.constants.invalidStartPrice());
				return;
			}

			String priceCountEndStr = priceCountEndItem.getValueAsString();
			if (priceCountEndStr == null || priceCountEndStr.trim().equals("")) {
				SC.say(CallCenter.constants.plzEnterEndPrice());
				return;
			}
			Integer endPriceCount = null;
			try {
				endPriceCount = Integer.parseInt(priceCountEndStr);
			} catch (Exception e) {
				SC.say(CallCenter.constants.invalidEndPrice());
				return;
			}
			if (endPriceCount.intValue() <= 0) {
				SC.say(CallCenter.constants.invalidEndPrice());
				return;
			}

			String priceStr = priceItem.getValueAsString();
			if (priceStr == null || priceStr.trim().equals("")) {
				SC.say(CallCenter.constants.plzEnterPrice());
				return;
			}
			try {
				Double.parseDouble(priceStr);
			} catch (Exception e) {
				SC.say(CallCenter.constants.invalidPrice());
				return;
			}
			if (startPriceCount.intValue() >= endPriceCount.intValue()) {
				SC.say(CallCenter.constants.endPriceMustBeMoreThenStartPrice());
				return;
			}

			if ((startPriceCount.intValue() + 99) >= endPriceCount.intValue()) {
				SC.say(CallCenter.constants.priceRangeMustBeMoreThen99());
				return;
			}

			RecordList recordList = listGrid.getDataAsRecordList();
			if (recordList != null && !recordList.isEmpty()) {
				int length = recordList.getLength();
				for (int i = 0; i < length; i++) {
					Record recordIt = recordList.get(i);
					Integer call_count_start = recordIt
							.getAttributeAsInt("call_count_start");
					Integer call_count_end = recordIt
							.getAttributeAsInt("call_count_end");
					if (startPriceCount.intValue() >= call_count_start
							.intValue()
							&& startPriceCount.intValue() <= call_count_end
									.intValue()) {
						SC.say(CallCenter.constants.invalidPriceRange());
						return;
					}
					if (endPriceCount.intValue() >= call_count_start.intValue()
							&& endPriceCount.intValue() <= call_count_end
									.intValue()) {
						SC.say(CallCenter.constants.invalidPriceRange());
						return;
					}
				}
			}

			ListGridRecord listGridRecord = new ListGridRecord();
			listGridRecord.setAttribute("call_count_start", startPriceCount);
			listGridRecord.setAttribute("call_count_end", endPriceCount);
			listGridRecord.setAttribute("price", priceStr);

			if (record == null) {
				listGrid.addData(listGridRecord);
			} else {
				listGridRecord.setAttribute("id", record.getAttribute("id"));
				listGrid.updateData(listGridRecord);
			}
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
