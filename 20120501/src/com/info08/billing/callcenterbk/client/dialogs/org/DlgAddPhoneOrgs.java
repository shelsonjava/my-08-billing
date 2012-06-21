package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddPhoneOrgs extends Window {

	// main layout
	private VLayout hLayout;
	private ListGrid listGrid;

	public DlgAddPhoneOrgs(final String phone,
			final DlgAddEditOrgDepPhone dlgAddEditOrgDepPhone, Record records[]) {
		try {
			setTitle(CallCenterBK.constants.phoneOrgs());
			setHeight(500);
			setWidth(953);
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

			listGrid = new ListGrid() {

				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord gridRecord = (ListGridRecord) record;
					if (gridRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer owner_type = gridRecord
							.getAttributeAsInt("owner_type");
					if (owner_type == null || owner_type.equals(0)) {
						return super.getCellCSSText(record, rowNum, colNum);
					}

					Integer contact_phones = gridRecord
							.getAttributeAsInt("for_contact");

					Integer status = gridRecord.getAttributeAsInt("status");
					Integer super_priority = gridRecord
							.getAttributeAsInt("super_priority");
					Integer important_remark = gridRecord
							.getAttributeAsInt("important_remark");
					String columnName = listGrid.getFieldName(colNum);

					boolean isphoneColumns = ClientUtils.containsOneOfString(
							columnName, "phone_shown",
							"phone_contract_type_desc");
					if (super_priority != null && super_priority < 0
							&& (!isphoneColumns)) {
						return "color:red;";
					} else if (status != null && status.equals(2)
							&& (!isphoneColumns)) {
						if (important_remark != null
								&& important_remark.intValue() == -1
								&& (!isphoneColumns)) {
							return "color:red;";
						} else {
							return "color:gray;";
						}
					} else if (status != null && status.equals(1)
							&& (!isphoneColumns)) {
						if (important_remark != null
								&& important_remark.intValue() == -1
								&& (!isphoneColumns)) {
							return "color:red;";
						} else {
							return "color:blue;";
						}

					} else if (status != null && status.equals(3)
							&& (!isphoneColumns)) {
						if (important_remark != null
								&& important_remark.intValue() == -1
								&& (!isphoneColumns)) {
							return "color:red;";
						} else {
							return "color:green;";
						}
					} else if (contact_phones == null
							|| contact_phones.equals(1) && (isphoneColumns)) {
						return "color: red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};

			};
			listGrid.setAlternateRecordStyles(true);
			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setCriteria(new Criteria());
			listGrid.setCanSort(false);
			listGrid.setCanResizeFields(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setFilterOnKeypress(false);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanSelectText(true);
			listGrid.setCanDragSelectText(true);

			ListGridField full_name = new ListGridField("full_name",
					CallCenterBK.constants.dasaxeleba());

			ListGridField full_name_reverse = new ListGridField(
					"full_name_reverse", CallCenterBK.constants.department(),
					400);

			ListGridField concat_address = new ListGridField("concat_address",
					CallCenterBK.constants.address());

			listGrid.setFields(full_name, full_name_reverse, concat_address);
			hLayout.addMember(listGrid);
			for (Record record : records) {
				listGrid.addData(record);
			}

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);
			hLayoutItem.setPadding(2);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.agree());
			saveItem.setWidth(100);

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
					dlgAddEditOrgDepPhone.checkedAndSave(phone);
				}
			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
