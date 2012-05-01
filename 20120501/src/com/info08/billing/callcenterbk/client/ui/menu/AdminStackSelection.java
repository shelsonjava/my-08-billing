package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.info08.billing.callcenterbk.client.content.admin.TabAdmin;
import com.info08.billing.callcenterbk.client.content.admin.TabBlockList;
import com.info08.billing.callcenterbk.client.content.admin.TabContractors;
import com.info08.billing.callcenterbk.client.content.admin.TabFixedOperPrefs;
import com.info08.billing.callcenterbk.client.content.admin.TabMobOperPrefs;
import com.info08.billing.callcenterbk.client.content.admin.TabTelComps;
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

public class AdminStackSelection extends SectionStackSection {

	private Body body;
	public static final TreeNode[] menuData = new TreeNode[] { 
			new MenuNode("100", "1", CallCenter.constants.users(), true, "person.png"),
			new MenuNode("101", "1", CallCenter.constants.mobOpIndexes(), true, "mobile.png"),
			new MenuNode("102", "1", CallCenter.constants.fixedOpIndexes(), true, "phone.png"),
			new MenuNode("103", "1", CallCenter.constants.contractors(), true, "contracts.png"),
			new MenuNode("104", "1", CallCenter.constants.blockPhone(), true, "telephone_delete.png"),
			new MenuNode("105", "1", CallCenter.constants.telComps(), true, "phone.png"),
	};

	private TreeGrid menuTreeGrid;

	public AdminStackSelection(Body body) {
		this.body = body;
		setTitle(CallCenter.constants.menuAdmin());
		setExpanded(false);
		setCanCollapse(true);

		Tree menuTree = new Tree();
		menuTree.setModelType(TreeModelType.PARENT);
		menuTree.setRootValue(1);
		menuTree.setNameProperty(CallCenter.constants.actionsList());
		menuTree.setIdField("Id");
		menuTree.setParentIdField("ReportsTo");
		menuTree.setOpenProperty("isOpen");
		menuTree.setData(menuData);

		TreeGridField formattedField = new TreeGridField(CallCenter.constants.actionsList());
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
			boolean hasAdminPerm = CommonSingleton.getInstance().hasPermission(
					"107000");
			menuData[0].setAttribute("enabled", hasAdminPerm);
			menuData[1].setAttribute("enabled", hasAdminPerm);
			menuData[2].setAttribute("enabled", hasAdminPerm);
			menuData[3].setAttribute("enabled", hasAdminPerm);
			menuData[4].setAttribute("enabled", hasAdminPerm);
			menuData[5].setAttribute("enabled", hasAdminPerm);
			
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
			TabAdmin tabAdmin = new TabAdmin();
			body.addTab(tabAdmin);
		} else if (menuId.equals("101")) {
			TabMobOperPrefs tabMobOperPrefs = new TabMobOperPrefs();
			body.addTab(tabMobOperPrefs);
		} else if (menuId.equals("102")) {
			TabFixedOperPrefs tabFixedOperPrefs = new TabFixedOperPrefs();
			body.addTab(tabFixedOperPrefs);
		} else if (menuId.equals("103")) {
			TabContractors tabContractors = new TabContractors();
			body.addTab(tabContractors);
		} else if (menuId.equals("104")) {
			TabBlockList tabBlockList = new TabBlockList();
			body.addTab(tabBlockList);
		} else if (menuId.equals("105")) {
			TabTelComps tabTelComps = new TabTelComps();
			body.addTab(tabTelComps);
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
