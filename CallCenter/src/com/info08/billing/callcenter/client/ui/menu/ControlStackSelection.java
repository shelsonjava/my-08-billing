package com.info08.billing.callcenter.client.ui.menu;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.content.control.TabControl;
import com.info08.billing.callcenter.client.content.control.TabViewSMSLog;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.client.ui.layout.Body;
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

	public TreeNode[] employeeData = new TreeNode[2];
	private TreeGrid employeeTreeGrid;
	private Body body;
	private MenuTreeNode callViewnode;
	private MenuTreeNode viewSmsLogNode;

	public ControlStackSelection(Body body) {
		try {
			this.body = body;
			setTitle("კონტროლის განყოფილება");
			setExpanded(false);
			setCanCollapse(true);

			callViewnode = new MenuTreeNode("100", "1", "ზარების დათვალიერება",
					true, "call.gif", false);
			employeeData[0] = callViewnode;

			viewSmsLogNode = new MenuTreeNode("101", "1",
					CallCenter.constants.smsLog(), true, "sms.png", false);
			employeeData[1] = viewSmsLogNode;

			Tree employeeTree = new Tree();
			employeeTree.setModelType(TreeModelType.PARENT);
			employeeTree.setRootValue(1);
			employeeTree.setNameProperty("ქმედებების ჩამონათვალი");
			employeeTree.setIdField("Id");
			employeeTree.setParentIdField("ReportsTo");
			employeeTree.setOpenProperty("isOpen");
			employeeTree.setData(employeeData);

			TreeGridField formattedField = new TreeGridField(
					"ქმედებების ჩამონათვალი");
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
			boolean hasPermission = CommonSingleton.getInstance()
					.hasPermission("100000");
			callViewnode.setAttribute("enabled", hasPermission);

			boolean hasSmsLogPerm = CommonSingleton.getInstance()
					.hasPermission("100500");
			viewSmsLogNode.setAttribute("enabled", hasSmsLogPerm);

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
			TabViewSMSLog tabViewSMSLog = new TabViewSMSLog();
			body.addTab(tabViewSMSLog);
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
