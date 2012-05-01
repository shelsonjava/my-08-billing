package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.info08.billing.callcenterbk.client.content.discovery.TabDiscovery;
import com.info08.billing.callcenterbk.client.content.discovery.TabDiscoveryHist;
import com.info08.billing.callcenterbk.client.content.discovery.TabDiscoveryRTypes;
import com.info08.billing.callcenterbk.client.content.discovery.TabDiscoveryTypes;
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

public class DiscoveryStackSelection extends SectionStackSection {

	private Body body;
	public static final TreeNode[] menuData = new TreeNode[] {
			new MenuNode("100", "1", CallCenter.constants.menuDiscoveryStatuses(), true, "discovery.png"),
			new MenuNode("101", "2", CallCenter.constants.menuDiscoveryTypes(), true, "discovery.png"),
			new MenuNode("102", "3", CallCenter.constants.menuDiscovery(), true, "discovery.png"),
			new MenuNode("103", "4", CallCenter.constants.menuDiscoveryHist(), true, "discovery.png")};

	private TreeGrid employeeTreeGrid;

	public DiscoveryStackSelection(Body body) {
		this.body = body;
		setTitle(CallCenter.constants.discoveryActions());
		setExpanded(false);
		setCanCollapse(true);

		Tree employeeTree = new Tree();
		employeeTree.setModelType(TreeModelType.PARENT);
		employeeTree.setRootValue(1);
		employeeTree.setNameProperty(CallCenter.constants.actionsList());
		employeeTree.setIdField("Id");
		employeeTree.setParentIdField("ReportsTo");
		employeeTree.setOpenProperty("isOpen");
		employeeTree.setData(menuData);

		TreeGridField formattedField = new TreeGridField(CallCenter.constants.actionsList());
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
	}
	
	
	public void setMenuPersmission() {
		try {
			boolean hasDiscStatPerm = CommonSingleton.getInstance().hasPermission(
					"401000");
			menuData[0].setAttribute("enabled", hasDiscStatPerm);
			
			boolean hasDiscTypesPerm = CommonSingleton.getInstance().hasPermission(
					"402000");
			menuData[1].setAttribute("enabled", hasDiscTypesPerm);
			
			boolean hasDiscrPerm = CommonSingleton.getInstance().hasPermission(
					"403000");
			menuData[2].setAttribute("enabled", hasDiscrPerm);
			
			boolean hasDiscHistPerm = CommonSingleton.getInstance().hasPermission(
					"403000");
			menuData[3].setAttribute("enabled", hasDiscHistPerm);

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
			TabDiscoveryRTypes tabDiscoveryRTypes = new TabDiscoveryRTypes();
			body.addTab(tabDiscoveryRTypes);
		} 
		else if (menuId.equals("101")) {
			TabDiscoveryTypes tabDiscoveryTypes = new TabDiscoveryTypes();
			body.addTab(tabDiscoveryTypes);
		}
		else if (menuId.equals("102")) {
			TabDiscovery tabDiscovery = new TabDiscovery();
			body.addTab(tabDiscovery);
		}
		else if (menuId.equals("103")) {
			TabDiscoveryHist tabDiscoveryHist = new TabDiscoveryHist();
			body.addTab(tabDiscoveryHist);
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
