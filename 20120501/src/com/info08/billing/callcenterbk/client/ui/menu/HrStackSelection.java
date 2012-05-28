package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.hr.TabHRStaff;
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

public class HrStackSelection extends SectionStackSection {

	private Body body;
	public static final TreeNode[] menuData = new TreeNode[] { new MenuNode(
			"100", "1", "თანამშრომლების მართვა", true, "users.png") };

	private TreeGrid menuTreeGrid;

	public HrStackSelection(Body body) {
		this.body = body;
		setTitle("HR");
		setExpanded(false);
		setCanCollapse(true);

		Tree menuTree = new Tree();
		menuTree.setModelType(TreeModelType.PARENT);
		menuTree.setRootValue(1);
		menuTree.setNameProperty(CallCenterBK.constants.actionsList());
		menuTree.setIdField("Id");
		menuTree.setParentIdField("ReportsTo");
		menuTree.setOpenProperty("isOpen");
		menuTree.setData(menuData);

		TreeGridField formattedField = new TreeGridField(
				CallCenterBK.constants.actionsList());
		formattedField.setCellFormatter(new CellFormatter() {
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				return record.getAttributeAsString("Name");
			}
		});

		menuTreeGrid = new TreeGrid();

		menuTreeGrid.setWidth100();
		menuTreeGrid.setHeight100();
		menuTreeGrid.setCanReorderRecords(false);
		menuTreeGrid.setCanAcceptDroppedRecords(false);
		menuTreeGrid.setShowOpenIcons(false);
		menuTreeGrid.setDropIconSuffix("into");
		menuTreeGrid.setClosedIconSuffix("");
		menuTreeGrid.setData(menuTree);
		menuTreeGrid.setFields(formattedField);
		menuTreeGrid.setCanAutoFitFields(false);
		menuTreeGrid.setCanSort(false);
		menuTreeGrid.setCanAcceptDrop(false);
		menuTreeGrid.setCanAcceptDroppedRecords(false);
		menuTreeGrid.setCanDrag(false);
		menuTreeGrid.setCanDrop(false);
		menuTreeGrid.setCanSelectAll(false);
		menuTreeGrid.setCanEdit(false);

		menuTreeGrid.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				openTab(menuTreeGrid.getSelectedRecord().getAttributeAsString(
						"Id"));
			}
		});

		addItem(menuTreeGrid);
	}

	public void setMenuPersmission() {
		try {
			boolean hasHRPerm = CommonSingleton.getInstance().hasPermission(
					"600000");
			menuData[0].setAttribute("enabled", hasHRPerm);

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
			TabHRStaff tabHRStaff = new TabHRStaff();
			body.addTab(tabHRStaff);
		}
	}

	public static class MenuNode extends TreeNode {
		public MenuNode(String id, String reportsTo, String name,
				boolean isOpen, String icon) {
			setAttribute("Id", id);
			setAttribute("ReportsTo", reportsTo);
			setAttribute("Name", name);
			setAttribute("isOpen", isOpen);
			setIcon(icon);
		}
	}
}
