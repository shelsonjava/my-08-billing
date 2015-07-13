package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgSortOrderOrgDepPhones extends MyWindow {

	private VLayout hLayout;
	private ListGrid listGrid;

	private ListGridRecord phoneData[];
	private ListGrid orgDepPhonesListGrid;

	public DlgSortOrderOrgDepPhones(ListGrid orgDepPhonesListGrid,
			ListGridRecord phoneData[]) {
		super();
		try {
			this.orgDepPhonesListGrid = orgDepPhonesListGrid;
			this.phoneData = phoneData;
			setTitle(CallCenterBK.constants.sortPhones());

			setHeight(710);
			setWidth(1000);
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
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer for_contact = countryRecord
							.getAttributeAsInt("for_contact");
					if (for_contact != null && for_contact.equals(1)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};
			listGrid.setWidth100();
			listGrid.setHeight(625);
			listGrid.setCanReorderRecords(true);
			listGrid.setCanAcceptDroppedRecords(true);
			listGrid.setAutoSaveEdits(false);
			listGrid.setCanSelectAll(false);

			ListGridField phone = new ListGridField("phone",
					CallCenterBK.constants.phone());
			phone.setCanFilter(true);

			ListGridField phone_state_descr = new ListGridField(
					"phone_state_descr", CallCenterBK.constants.phoneState(),
					150);
			phone_state_descr.setAlign(Alignment.CENTER);
			phone_state_descr.setCanFilter(false);

			ListGridField hidden_by_request_descr = new ListGridField(
					"hidden_by_request_descr",
					CallCenterBK.constants.openClose(), 150);
			hidden_by_request_descr.setAlign(Alignment.CENTER);
			hidden_by_request_descr.setCanFilter(false);

			ListGridField phone_contract_type_descr = new ListGridField(
					"phone_contract_type_descr",
					CallCenterBK.constants.phoneStatus(), 150);
			phone_contract_type_descr.setAlign(Alignment.CENTER);
			phone_contract_type_descr.setCanFilter(false);

			ListGridField for_contact_descr = new ListGridField(
					"for_contact_descr", CallCenterBK.constants.contactPhone(),
					150);
			for_contact_descr.setAlign(Alignment.CENTER);
			for_contact_descr.setCanFilter(false);

			ListGridField phone_type_descr = new ListGridField(
					"phone_type_descr", CallCenterBK.constants.type(), 150);
			phone_type_descr.setAlign(Alignment.CENTER);
			phone_type_descr.setCanFilter(false);

			ListGridField is_parallel_descr = new ListGridField(
					"is_parallel_descr", CallCenterBK.constants.paraller(), 150);
			is_parallel_descr.setAlign(Alignment.CENTER);
			is_parallel_descr.setCanFilter(false);

			listGrid.setFields(phone, phone_state_descr,
					hidden_by_request_descr, phone_contract_type_descr,
					for_contact_descr, phone_type_descr, is_parallel_descr);

			hLayout.addMember(listGrid);

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
			filldata();
			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void filldata() {
		try {
			for (ListGridRecord item : phoneData) {
				listGrid.addData(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			Record records[] = listGrid.getRecords();

			Integer orgDepPhoneIdList[] = new Integer[records.length];
			int i = 0;
			for (Record record : records) {
				orgDepPhoneIdList[i++] = record
						.getAttributeAsInt("org_dep_to_ph_id");
			}
			DataSource dataSource = DataSource.get("OrgDepPhoneDS");
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("orgDepPhoneIdList", orgDepPhoneIdList);
			record.setAttribute("org_dep_to_ph_id", -10000);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateOrgDepPhoneSortOrder");
			dataSource.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					destroy();
					orgDepPhonesListGrid.invalidateCache();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
