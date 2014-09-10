
package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.admin.TabAdmin;
import com.info08.billing.callcenterbk.client.content.admin.TabBillingComps;
import com.info08.billing.callcenterbk.client.content.admin.TabBlackList;
import com.info08.billing.callcenterbk.client.content.admin.TabContractors;
import com.info08.billing.callcenterbk.client.content.admin.TabGSMIndexes;
import com.info08.billing.callcenterbk.client.content.admin.TabLandlineIndexes;
import com.info08.billing.callcenterbk.client.content.admin.TabOrgPriorityCompList;
import com.info08.billing.callcenterbk.client.content.admin.TabOrgPriorityList;
import com.info08.billing.callcenterbk.client.content.admin.TabSendSMS;
import com.info08.billing.callcenterbk.client.content.admin.TabStatByOrgAct;
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
			new MenuNode("100", "1", CallCenterBK.constants.users(), true, "person.png"),
			new MenuNode("101", "1", CallCenterBK.constants.mobOpIndexes(), true, "mobile.png"),
			new MenuNode("102", "1", CallCenterBK.constants.fixedOpIndexes(), true, "phone.png"),
			new MenuNode("103", "1", CallCenterBK.constants.contractors(), true, "contracts.png"),
			new MenuNode("104", "1", CallCenterBK.constants.blockPhone(), true, "telephone_delete.png"),
			new MenuNode("105", "1", CallCenterBK.constants.billingComps(), true, "phone.png"),
			new MenuNode("106", "1", CallCenterBK.constants.extraPriority(), true, "sort.png"),
			new MenuNode("107", "1", CallCenterBK.constants.sendSMS(), true, "sms.png"),			
			new MenuNode("108", "1", "Test Panel",true, "sms.png"),
			new MenuNode("109", "1", CallCenterBK.constants.charges_by_org_act(), true, "stats.png"),
			new MenuNode("110", "1", CallCenterBK.constants.red_orgs(), true, "contracts.png"),
	};

	private TreeGrid menuTreeGrid;

	public AdminStackSelection(Body body) {
		this.body = body;
		setTitle(CallCenterBK.constants.menuAdmin());
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
			menuData[0].setAttribute("enabled", CommonSingleton.getInstance().hasPermission("107100"));
			menuData[1].setAttribute("enabled", CommonSingleton.getInstance().hasPermission("107200"));
			menuData[2].setAttribute("enabled", CommonSingleton.getInstance().hasPermission("107300"));
			menuData[3].setAttribute("enabled", CommonSingleton.getInstance().hasPermission("107400"));
			menuData[4].setAttribute("enabled", CommonSingleton.getInstance().hasPermission("107500"));
			menuData[5].setAttribute("enabled", CommonSingleton.getInstance().hasPermission("107600"));
			menuData[6].setAttribute("enabled", CommonSingleton.getInstance().hasPermission("107700"));
			menuData[7].setAttribute("enabled", CommonSingleton.getInstance().hasPermission("107800"));
			menuData[8].setAttribute("enabled", CommonSingleton.getInstance().hasPermission("107000"));
			menuData[9].setAttribute("enabled", CommonSingleton.getInstance().hasPermission("107700"));
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
			TabGSMIndexes tabMobOperPrefs = new TabGSMIndexes();
			body.addTab(tabMobOperPrefs);
		} else if (menuId.equals("102")) {
			TabLandlineIndexes tabFixedOperPrefs = new TabLandlineIndexes();
			body.addTab(tabFixedOperPrefs);
		} else if (menuId.equals("103")) {
			TabContractors tabContractors = new TabContractors();
			body.addTab(tabContractors);
		} else if (menuId.equals("104")) {
			TabBlackList tabBlockList = new TabBlackList();
			body.addTab(tabBlockList);
		} else if (menuId.equals("105")) {
			TabBillingComps tabBillingComps = new TabBillingComps();
			body.addTab(tabBillingComps);
		} else if (menuId.equals("106")) {
			body.addTab(new TabOrgPriorityList());
		} else if (menuId.equals("107")) {
			body.addTab(new TabSendSMS());
		} else if (menuId.equals("109")) {
			body.addTab(new TabStatByOrgAct());
		}else if (menuId.equals("110")) {
			body.addTab(new TabOrgPriorityCompList());
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
