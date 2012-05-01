package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
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

public class DlgBlockPhoneList extends Window {

	private VLayout hLayout;

	public DlgBlockPhoneList(ListGridRecord listGridRecord) {
		try {

			setTitle(CallCenterBK.constants.blockPhoneList());

			setHeight(700);
			setWidth(300);
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

			Integer main_detail_id = listGridRecord
					.getAttributeAsInt("main_detail_id");
			Integer main_id = listGridRecord.getAttributeAsInt("main_id");
			Integer contract_id = listGridRecord
					.getAttributeAsInt("contract_id");
			Integer phone_list_type = listGridRecord
					.getAttributeAsInt("phone_list_type");

			String fetchMethod = "";
			Criteria criteria = new Criteria();
			criteria.setAttribute("main_detail_id", main_detail_id);
			criteria.setAttribute("main_id", main_id);
			criteria.setAttribute("contract_id", contract_id);

			if (main_detail_id != null && main_detail_id.longValue() > 0) {
				switch (phone_list_type.intValue()) {
				case 1: // 1 - All Phones From Organization's Department Can
						// Call, Ignore Blocking
					fetchMethod = "selectByDepAll";
					criteria.setAttribute("main_id", -111111111);
					break;
				case 2: // 2 - Only Listed Phones Can Call From
						// Organization's
						// Department, Block Other
					fetchMethod = "selectByDepExceptList";
					break;
				case 3: // 3 - All Phones From Organization's Department Can
						// Call Except List, Block This List
					fetchMethod = "selectByDepOnlyList";
					break;
				default:
					break;
				}
			} else {
				switch (phone_list_type.intValue()) {
				case 1: // 1 - All Phones From Organization Can Call, Ignore
						// Blocking
					fetchMethod = "selectByOrganizationAll";
					criteria.setAttribute("main_id", -111111111);
					break;
				case 2: // 2 - Only Listed Phones Can Call From
						// Organization,
						// Block Other
					fetchMethod = "selectByOrganizationExceptList";
					break;
				case 3: // 3 - All Phones From Organization Can Call Except
						// List, Block This List
					fetchMethod = "selectByOrganizationOnlyList";
					break;
				default:
					break;
				}
			}

			ListGrid phoneList = new ListGrid();
			phoneList.setWidth100();
			phoneList.setHeight100();
			phoneList.setAlternateRecordStyles(true);
			phoneList.setShowFilterEditor(true);
			phoneList.setFilterOnKeypress(true);
			phoneList.setCanEdit(false);
			phoneList.setCanRemoveRecords(false);
			phoneList.setShowRowNumbers(true);
			phoneList.setCanHover(true);
			phoneList.setShowHover(true);
			phoneList.setShowHoverComponents(true);
			phoneList.setWrapCells(true);
			phoneList.setFixedRecordHeights(false);
			phoneList.setCanDragSelectText(true);

			DataSource contractorsDS = DataSource.get("ContractorsDS");
			phoneList.setDataSource(contractorsDS);
			phoneList.setCriteria(criteria);
			phoneList.setAutoFetchData(true);
			phoneList.setFetchOperation(fetchMethod);

			ListGridField phone = new ListGridField("phone",
					CallCenterBK.constants.phone());
			phone.setAlign(Alignment.CENTER);

			phoneList.setFields(phone);

			hLayout.addMember(phoneList);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
