package com.info08.billing.callcenterbk.client.dialogs.org;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyAddressPanel1;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItem;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxRecord;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
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

public class DlgAddEditOrgDepartments extends Window {

	// main layout
	private VLayout vLayout;

	private DynamicForm dynamicForm;
	private TextItem orgDepartmentNameItem;
	private MyComboBoxItem parrentOrgDepItem;

	private MyAddressPanel1 myAddressPanel1;

	private ListGridRecord listGridRecord;
	private ListGrid listGrid;
	private Integer organization_id;
	private DlgManageOrgDepartments dlgManageOrgDepartments;
	private ListGridRecord parrentListGridRecord;

	public DlgAddEditOrgDepartments(ListGridRecord parrentListGridRecord,
			Integer organization_id, ListGridRecord listGridRecord,
			ListGrid listGrid, DlgManageOrgDepartments dlgManageOrgDepartments) {
		try {
			this.parrentListGridRecord = parrentListGridRecord;
			this.listGrid = listGrid;
			this.listGridRecord = listGridRecord;
			this.organization_id = organization_id;
			this.dlgManageOrgDepartments = dlgManageOrgDepartments;
			setTitle(listGridRecord == null ? CallCenterBK.constants
					.addOrgDepartment() : CallCenterBK.constants
					.editOrgDepartment());

			setHeight(325);
			setWidth(1263);
			setShowMinimizeButton(false);
			setIsModal(true);
			setShowModalMask(true);
			setCanDrag(false);
			setCanDragReposition(false);
			setCanDragResize(false);
			setCanDragScroll(false);
			centerInPage();

			vLayout = new VLayout(5);
			vLayout.setWidth100();
			vLayout.setHeight100();
			vLayout.setPadding(10);

			parrentOrgDepItem = new MyComboBoxItem("parrent_org_dep_name",
					CallCenterBK.constants.parrentOrgDepName(), 0, 615);
			parrentOrgDepItem.setNameField("parrent_department_id");
			parrentOrgDepItem.setMyDlgHeight(400);
			parrentOrgDepItem.setMyDlgWidth(900);
			DataSource orgDepartmentDS = DataSource.get("OrgDepartmentDS");
			parrentOrgDepItem.setMyDataSource(orgDepartmentDS);
			parrentOrgDepItem
					.setMyDataSourceOperation("customOrgDepSearchByOrgId");
			parrentOrgDepItem.setMyIdField("org_department_id");
			Criteria myCriteria = new Criteria();
			myCriteria.setAttribute("organization_id", organization_id);
			parrentOrgDepItem.setMyCriteria(myCriteria);

			ArrayList<MyComboBoxRecord> fieldRecords = new ArrayList<MyComboBoxRecord>();
			MyComboBoxRecord department_original = new MyComboBoxRecord(
					"department_original", CallCenterBK.constants.department(),
					true);
			MyComboBoxRecord full_address_not_hidden = new MyComboBoxRecord(
					"full_address_not_hidden",
					CallCenterBK.constants.address(), false);

			fieldRecords.add(department_original);
			fieldRecords.add(full_address_not_hidden);

			parrentOrgDepItem.setMyFields(fieldRecords);
			parrentOrgDepItem.setMyChooserTitle(CallCenterBK.constants
					.organization());

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setNumCols(4);
			dynamicForm.setTitleOrientation(TitleOrientation.TOP);

			HLayout hLayout = new HLayout();
			hLayout.setWidth100();

			hLayout.setMembers(parrentOrgDepItem, dynamicForm);

			vLayout.addMember(hLayout);

			orgDepartmentNameItem = new TextItem();
			orgDepartmentNameItem.setTitle(CallCenterBK.constants.department());
			orgDepartmentNameItem.setWidth(615);
			orgDepartmentNameItem.setName("department_original");

			dynamicForm.setFields(orgDepartmentNameItem);

			myAddressPanel1 = new MyAddressPanel1(true,
					"organization_department_address",
					CallCenterBK.constants.address(), 615, 0);
			vLayout.addMember(myAddressPanel1);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.save());
			saveItem.setWidth(100);

			hLayoutItem.setMembers(saveItem, cancItem);
			vLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});
			saveItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					saveData();
				}
			});
			addItem(vLayout);
			fillData();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillData() {
		try {
			if (listGridRecord == null) {
				if (parrentListGridRecord != null) {
					parrentOrgDepItem.setDataValue(parrentListGridRecord
							.getAttributeAsInt("org_department_id"));
					myAddressPanel1.setValue(null);
				} else {
					myAddressPanel1.setValue(null);
				}
				return;
			}
			Map<?, ?> map = listGridRecord.toMap();
			parrentOrgDepItem.setDataValue(map);
			dynamicForm.setValues(map);

			Object physical_address_id = map.get("physical_address_id");
			myAddressPanel1.setValue(physical_address_id == null ? null
					: new Integer(physical_address_id.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void saveData() {
		try {
			Integer parrent_department_id = null;
			Object parrentRecord = parrentOrgDepItem.getCurrentValue();
			if (parrentRecord != null) {
				parrent_department_id = Integer.parseInt(parrentRecord
						.toString());
			}
			if (parrent_department_id != null && listGridRecord != null) {
				Integer org_department_id = listGridRecord
						.getAttributeAsInt("org_department_id");
				if (org_department_id != null
						&& org_department_id.equals(parrent_department_id)) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.parrentOrgDepIsSameAsChild());
					return;
				}
			}
			String department_original = orgDepartmentNameItem
					.getValueAsString();
			if (department_original == null
					|| department_original.trim().equals("")) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.plzEnterOrgDepName());
				return;
			}

			final Map<String, Object> fromMaps = new LinkedHashMap<String, Object>();
			boolean addressCheck = myAddressPanel1.isCheckedTurnOffItem();
			if (addressCheck) {
				Map<?, ?> physicalAddrValues = myAddressPanel1.getValues();

				Integer town_id = physicalAddrValues.get("town_id") == null ? null
						: new Integer(physicalAddrValues.get("town_id")
								.toString());
				if (town_id == null) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.plzChoosePhysicalAddrTown());
					myAddressPanel1.getDynamicForm().focusInItem(
							myAddressPanel1.getAddrTownItem());
					return;
				}
				Integer street_id = physicalAddrValues.get("streets_id") == null ? null
						: new Integer(physicalAddrValues.get("streets_id")
								.toString());
				if (street_id == null) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants
									.plzChoosePhysicalAddrStreet());
					myAddressPanel1.getDynamicForm().focusInItem(
							myAddressPanel1.getAddrStreetItem());
					return;
				}

				fromMaps.put("physicalAddrValues", physicalAddrValues);
			}

			fromMaps.put("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			fromMaps.put("parrent_department_id", parrent_department_id);
			fromMaps.put("organization_id", organization_id);
			fromMaps.put("department_original", department_original);

			if (listGridRecord != null) {
				fromMaps.put("org_department_id",
						listGridRecord.getAttributeAsInt("org_department_id"));
				fromMaps.put("legal_address_id",
						listGridRecord.getAttributeAsInt("legal_address_id"));
				fromMaps.put("physical_address_id",
						listGridRecord.getAttributeAsInt("physical_address_id"));
				fromMaps.put("inner_order",
						listGridRecord.getAttributeAsInt("inner_order"));
			}
			fromMaps.remove("_ref");
			fromMaps.remove("__ref");

			if (listGridRecord == null || parrent_department_id == null) {
				saveDataReal(fromMaps);
			} else {
				com.smartgwt.client.rpc.RPCManager.startQueue();
				DSRequest req = new DSRequest();
				req.setAttribute("operationId", "orgDepartCheckParrChild");
				Criteria criteria = new Criteria();
				criteria.setAttribute("curr_org_department_id",
						listGridRecord.getAttributeAsInt("org_department_id"));
				criteria.setAttribute("parr_org_department_id",
						parrent_department_id);
				criteria.setAttribute("cr_uId_" + HTMLPanel.createUniqueId(),
						HTMLPanel.createUniqueId());
				DataSource dataSource = DataSource.get("OrgDepartmentDS");
				dataSource.fetchData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						Record records[] = response.getData();
						if (records != null && records.length > 0) {
							Integer count = records[0]
									.getAttributeAsInt("recCount");
							if (count != null && count.intValue() > 0) {
								SC.say(CallCenterBK.constants.warning(),
										CallCenterBK.constants
												.parrentIsAlsoChild());
								return;
							}
						}
						saveDataReal(fromMaps);
					}
				}, req);
				com.smartgwt.client.rpc.RPCManager.sendQueue();
			}

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void saveDataReal(Map<String, Object> fromMaps) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record(fromMaps);
			DSRequest req = new DSRequest();

			if (listGridRecord == null) {
				req.setAttribute("operationId", "addOrganizationDepartment");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						dlgManageOrgDepartments.searchDepartment();
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateOrganizationDepartment");
				listGrid.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						dlgManageOrgDepartments.searchDepartment();
						destroy();
					}
				}, req);
			}

			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
