package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.stat.TabBillCallsBySrvBK;
import com.info08.billing.callcenterbk.client.content.stat.TabStatByBillingComps;
import com.info08.billing.callcenterbk.client.content.stat.TabStatByOrg;
import com.info08.billing.callcenterbk.client.content.stat.TabStatFull;
import com.info08.billing.callcenterbk.client.content.stat.TabStatFullByMonth;
import com.info08.billing.callcenterbk.client.content.stat.TabStatOrgCorrect;
import com.info08.billing.callcenterbk.client.content.stat.TabStatOrgCorrectUser;
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

public class StatStackSelection extends SectionStackSection {

	private Body body;
	public static final TreeNode[] menuData = new TreeNode[] {
		new MenuNode("100", "1", CallCenterBK.constants.callsTotalBySrv(), true,"stats.png") ,
		new MenuNode("101", "1", CallCenterBK.constants.statisticFullDay(), true,"stats.png"),
		new MenuNode("102", "1", CallCenterBK.constants.statisticFullMonth(), true,"stats.png"),
		new MenuNode("103", "1", CallCenterBK.constants.statisticByBillingComp(), true,"stats.png"),
		new MenuNode("104", "1", CallCenterBK.constants.statisticOrgCorrect(), true,"stats.png"),		
		new MenuNode("105", "1", CallCenterBK.constants.statisticByOrgs(), true,"stats.png"),
		new MenuNode("106", "1", CallCenterBK.constants.statisticOrgCorrect()+"(1)", true,"stats.png"),
	};

	private TreeGrid menuTreeGrid;

	public StatStackSelection(Body body) {
		this.body = body;
		setTitle(CallCenterBK.constants.menuStat());
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
			boolean hasAdminPerm = CommonSingleton.getInstance().hasPermission("501000");
			menuData[0].setAttribute("enabled", hasAdminPerm);
			menuData[1].setAttribute("enabled", hasAdminPerm);
			menuData[2].setAttribute("enabled", hasAdminPerm);
			menuData[3].setAttribute("enabled", hasAdminPerm);
			menuData[4].setAttribute("enabled", hasAdminPerm);
			menuData[5].setAttribute("enabled", hasAdminPerm);
			menuData[6].setAttribute("enabled", hasAdminPerm);			
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
			TabBillCallsBySrvBK tabBillCallsBySrv = new TabBillCallsBySrvBK(body.getMainTabPanel());
			body.addTab(tabBillCallsBySrv);
		} else if (menuId.equals("101")) {
			TabStatFull tabStatFull = new TabStatFull(body.getMainTabPanel());
			body.addTab(tabStatFull);
		} else if (menuId.equals("102")) {
			TabStatFullByMonth tabStatFullByMonth = new TabStatFullByMonth(body.getMainTabPanel());
			body.addTab(tabStatFullByMonth);
		} else if (menuId.equals("103")) {
			TabStatByBillingComps tabStatByBillingComp = new TabStatByBillingComps(body.getMainTabPanel());
			body.addTab(tabStatByBillingComp);
		} else if (menuId.equals("104")) {
			TabStatOrgCorrect tabStatOrgCorrect = new TabStatOrgCorrect(body.getMainTabPanel());
			body.addTab(tabStatOrgCorrect);
		} else if (menuId.equals("105")) {
			TabStatByOrg tabStatByOrg = new TabStatByOrg(body.getMainTabPanel());
			body.addTab(tabStatByOrg);
		} else if (menuId.equals("106")) {
			TabStatOrgCorrectUser tabStatOrgCorrectUser = new TabStatOrgCorrectUser(body.getMainTabPanel());
			body.addTab(tabStatOrgCorrectUser);
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
