package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
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

public class DlgSortOrderOrgDeps extends Window {

	private VLayout hLayout;
	private ListGrid listGrid;

	private DlgManageOrgDepartments dlgManageOrgDepartments;
	private Integer organization_id;
	private Integer org_department_id;

	public DlgSortOrderOrgDeps(Integer organization_id,
			Integer org_department_id,
			DlgManageOrgDepartments dlgManageOrgDepartments) {
		try {
			this.dlgManageOrgDepartments = dlgManageOrgDepartments;
			this.organization_id = organization_id;
			this.org_department_id = org_department_id;
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
					Integer isBold = countryRecord.getAttributeAsInt("isBold");
					if (isBold != null && isBold.equals(1)) {
						return "font-weight: bold;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				}
			};
			listGrid.setWidth100();
			listGrid.setHeight(625);
			listGrid.setCanReorderRecords(true);
			listGrid.setCanAcceptDroppedRecords(true);
			listGrid.setAutoSaveEdits(false);
			listGrid.setCanSelectAll(false);

			ListGridField organization_name = new ListGridField("department",
					CallCenterBK.constants.department());
			ListGridField real_address = new ListGridField(
					"real_address_descr", CallCenterBK.constants.orgAddress(),
					230);

			real_address.setAlign(Alignment.LEFT);

			listGrid.setFields(organization_name, real_address);

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
			DataSource dataSource = DataSource.get("OrgDepartmentDS");
			Criteria criteria = new Criteria();
			criteria.setAttribute("organization_id", organization_id);
			if (org_department_id != null) {
				criteria.setAttribute("parrent_department_id",
						org_department_id);
			} else {
				criteria.setAttribute("parrent_department_id", new Integer(
						-100000));
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("customOrgDepSearchByOrgId");

			dataSource.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records == null || records.length <= 0) {
						return;
					}
					listGrid.setData(records);
				}
			}, dsRequest);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			Record records[] = listGrid.getRecords();

			Integer orgDepIdList[] = new Integer[records.length];
			int i = 0;
			for (Record record : records) {
				orgDepIdList[i++] = record
						.getAttributeAsInt("org_department_id");
			}
			DataSource dataSource = DataSource.get("OrgDepartmentDS");
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("orgDepIdList", orgDepIdList);
			record.setAttribute("org_department_id", -10000);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateOrgDepSortOrder");
			dataSource.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					destroy();
					dlgManageOrgDepartments.searchDepartment();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
