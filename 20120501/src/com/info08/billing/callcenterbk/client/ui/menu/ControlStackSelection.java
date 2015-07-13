package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.control.TabControl;
import com.info08.billing.callcenterbk.client.content.control.TabControlRemark;
import com.info08.billing.callcenterbk.client.content.control.TabViewSentSMSLog;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.ui.layout.Body;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ControlStackSelection extends SectionStackSection {

	public TreeNode[] employeeData = new TreeNode[3];
	private TreeGrid employeeTreeGrid;
	private Body body;

	public ControlStackSelection(Body body) {
		try {
			this.body = body;
			setTitle("კონტროლი");
			setExpanded(false);
			setCanCollapse(true);

			employeeData[0] = new MenuTreeNode("100", "1", "ზარების დათვ.",true, "call.gif", false);
			employeeData[1] = new MenuTreeNode("101", "1",CallCenterBK.constants.smsLog(), true, "sms.png", false);
			employeeData[2] = new MenuTreeNode("102", "1",CallCenterBK.constants.remarks(), true, "alert.png", false);

			Tree employeeTree = new Tree();
			employeeTree.setModelType(TreeModelType.PARENT);
			employeeTree.setRootValue(1);
			employeeTree.setNameProperty("ქმედებები");
			employeeTree.setIdField("Id");
			employeeTree.setParentIdField("ReportsTo");
			employeeTree.setOpenProperty("isOpen");
			employeeTree.setData(employeeData);

			TreeGridField formattedField = new TreeGridField(
					"ქმედებები");
			formattedField.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					return record.getAttributeAsString("Name");
				}
			});

			employeeTreeGrid = new TreeGrid();

			employeeTreeGrid.setWidth100();
			employeeTreeGrid.setHeight100();
			employeeTreeGrid.setCanReorderRecords(false);
			employeeTreeGrid.setCanAcceptDroppedRecords(false);
			employeeTreeGrid.setShowOpenIcons(false);
			employeeTreeGrid.setDropIconSuffix("into");
			employeeTreeGrid.setClosedIconSuffix("");
			employeeTreeGrid.setData(employeeTree);
			employeeTreeGrid.setFields(formattedField);
			employeeTreeGrid.setCanAutoFitFields(false);
			employeeTreeGrid.setCanSort(false);
			employeeTreeGrid.setCanAcceptDrop(false);
			employeeTreeGrid.setCanAcceptDroppedRecords(false);
			employeeTreeGrid.setCanDrag(false);
			employeeTreeGrid.setCanDrop(false);
			employeeTreeGrid.setCanSelectAll(false);
			employeeTreeGrid.setCanEdit(false);

			employeeTreeGrid.addDoubleClickHandler(new DoubleClickHandler() {
				@Override
				public void onDoubleClick(DoubleClickEvent event) {
					openTab(employeeTreeGrid.getSelectedRecord()
							.getAttributeAsString("Id"));
				}
			});

			addItem(employeeTreeGrid);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public void setMenuPersmission() {
		try {
			boolean hasPermission = CommonSingleton.getInstance().hasPermission("100000");
			boolean hasSmsLogPerm = CommonSingleton.getInstance().hasPermission("100500");
			boolean hasRemarksPerm = CommonSingleton.getInstance().hasPermission("102500");
			employeeData[0].setAttribute("enabled", hasPermission);
			employeeData[1].setAttribute("enabled", hasSmsLogPerm);
			employeeData[2].setAttribute("enabled", hasRemarksPerm);
			
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void openTab(String menuId) {
		if (menuId == null || menuId.trim().equals("")) {
			return;
		}
		if (menuId.equals("100")) {
			TabControl controlTabPane = new TabControl();
			body.addTab(controlTabPane);
		} else if (menuId.equals("101")) {
			TabViewSentSMSLog tabViewSMSLog = new TabViewSentSMSLog();
			body.addTab(tabViewSMSLog);
		} else if (menuId.equals("102")) {
			TabControlRemark tabControlRemark = new TabControlRemark();
			body.addTab(tabControlRemark);
		}		
	}

	public static class MenuTreeNode extends TreeNode {
		public MenuTreeNode(String id, String reportsTo, String name,
				boolean isOpen, String icon, boolean enabled) {
			setAttribute("Id", id);
			setAttribute("ReportsTo", reportsTo);
			setAttribute("Name", name);
			setAttribute("isOpen", isOpen);
			setAttribute("enabled", enabled);
			setIcon(icon);
		}
	}
}
