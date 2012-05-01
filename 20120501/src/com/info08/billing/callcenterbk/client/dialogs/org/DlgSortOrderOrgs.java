package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
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
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgSortOrderOrgs extends Window {

	private VLayout hLayout;
	private ListGrid listGrid;

//	private ToolStripButton upBtn;
//	private ToolStripButton downBtn;
	private TabOrganization tabOrganization;

	public DlgSortOrderOrgs(TabOrganization tabOrganization, Record records[]) {
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

//			ToolStrip toolStrip = new ToolStrip();
//			toolStrip.setWidth100();
//			toolStrip.setPadding(5);
//			hLayout.addMember(toolStrip);
//
//			upBtn = new ToolStripButton(CallCenter.constants.sortUp(),
//					"sortUp.png");
//			upBtn.setLayoutAlign(Alignment.LEFT);
//			upBtn.setWidth(50);
//			toolStrip.addButton(upBtn);
//
//			downBtn = new ToolStripButton(CallCenter.constants.sortDown(),
//					"sortDown.png");
//			downBtn.setLayoutAlign(Alignment.LEFT);
//			downBtn.setWidth(50);
//			toolStrip.addButton(downBtn);

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer deleted = countryRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && !deleted.equals(0)) {
						return "color:red;";
					} else {
						Integer statuse = countryRecord
								.getAttributeAsInt("statuse");
						switch (statuse) {
						case 1: // not functions
							return "color:pink;";
						case 2: // disabled
							return "text-decoration:line-through;";
						default:
							return super.getCellCSSText(record, rowNum, colNum);
						}
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

			ListGridField nameField = new ListGridField("org_name",
					CallCenterBK.constants.orgName(), 400);

			ListGridField identcode = new ListGridField("identcode",
					CallCenterBK.constants.identCode(), 150);

			ListGridField director = new ListGridField("director",
					CallCenterBK.constants.director(), 150);

			ListGridField real_address = new ListGridField("real_address",
					CallCenterBK.constants.orgAddress(), 230);

			identcode.setAlign(Alignment.CENTER);
			director.setAlign(Alignment.CENTER);
			real_address.setAlign(Alignment.LEFT);

			listGrid.setFields(nameField, identcode, director, real_address);

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
				mainIdList[i++] = record.getAttributeAsInt("main_id");
			}
			DataSource dataSource = DataSource.get("OrgDS");
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("mainIdList", mainIdList);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateMainServiceOrders");
			dataSource.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					destroy();
					tabOrganization.search();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
