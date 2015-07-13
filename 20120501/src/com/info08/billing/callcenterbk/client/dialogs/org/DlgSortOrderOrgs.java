package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.content.TabOrganization;
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

public class DlgSortOrderOrgs extends MyWindow {

	private VLayout hLayout;
	private ListGrid listGrid;

	private TabOrganization tabOrganization;

	public DlgSortOrderOrgs(TabOrganization tabOrganization, Record records[]) {
		super();
		try {
			this.tabOrganization = tabOrganization;
			setTitle(CallCenterBK.constants.sortOrgs());

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
					Integer status = countryRecord.getAttributeAsInt("status");
					Integer super_priority = countryRecord
							.getAttributeAsInt("super_priority");
					Integer important = countryRecord
							.getAttributeAsInt("important");

					if (super_priority != null && super_priority < 0) {
						return "color:red;";
					} else if (status != null && status.equals(2)) {
						if (important != null && important.intValue() == -1
								&& colNum == 4) {
							return "color:red;";
						} else {
							return "color:gray;";
						}
					} else if (status != null && status.equals(1)) {
						if (important != null && important.intValue() == -1
								&& colNum == 4) {
							return "color:red;";
						} else {
							return "color:blue;";
						}

					} else if (status != null && status.equals(3)) {
						if (important != null && important.intValue() == -1
								&& colNum == 4) {
							return "color:red;";
						} else {
							return "color:green;";
						}
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
			listGrid.setData(records);

			ListGridField organization_name = new ListGridField(
					"organization_name", CallCenterBK.constants.orgName(), 400);
			ListGridField ident_code = new ListGridField("ident_code",
					CallCenterBK.constants.identCode(), 150);
			ListGridField chief = new ListGridField("chief",
					CallCenterBK.constants.director(), 150);
			ListGridField real_address = new ListGridField(
					"real_address_descr", CallCenterBK.constants.orgAddress(),
					230);

			ident_code.setAlign(Alignment.CENTER);
			chief.setAlign(Alignment.CENTER);
			real_address.setAlign(Alignment.LEFT);

			listGrid.setFields(organization_name, ident_code, chief,
					real_address);

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

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			Record records[] = listGrid.getRecords();

			Integer mainIdList[] = new Integer[records.length];
			int i = 0;
			for (Record record : records) {
				mainIdList[i++] = record.getAttributeAsInt("organization_id");
			}
			DataSource dataSource = DataSource.get("OrgDS");
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("mainIdList", mainIdList);
			record.setAttribute("organization_id", new Integer(-10000));
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateOrgSortOrder");
			dataSource.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					destroy();
					tabOrganization.search(false, false);
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
