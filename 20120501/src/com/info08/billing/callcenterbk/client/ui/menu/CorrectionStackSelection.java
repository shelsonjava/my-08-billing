package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.TabAbonentLastNames;
import com.info08.billing.callcenterbk.client.content.TabAbonentNames;
import com.info08.billing.callcenterbk.client.content.TabOrgBusinessActivity;
import com.info08.billing.callcenterbk.client.content.TabOrganization;
import com.info08.billing.callcenterbk.client.content.TabSubscriber;
import com.info08.billing.callcenterbk.client.content.TabTreatments;
import com.info08.billing.callcenterbk.client.content.TabVirtualCharge;
import com.info08.billing.callcenterbk.client.content.admin.TabPartniorNumbersList;
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

public class CorrectionStackSelection extends SectionStackSection {

	private Body body;
	public static final TreeNode[] menuData = new TreeNode[] {
			new MenuNode("100", "1", "ორგ. საქმიანობები", true,
					"organization.gif"),
			new MenuNode("101", "2", "ორგანიზაციები", true, "organization.gif"),
			new MenuNode("102", "3", "აბონენტები", true, "person.png"),
			new MenuNode("103", "4", "სახელები", true, "person.png"),
			new MenuNode("104", "5", "გვარები", true, "person.png"),
			new MenuNode("105", "6", CallCenterBK.constants.virtualCharge(),
					true, "moneySmall.png"),
			new MenuNode("106", "1", CallCenterBK.constants.importedNumbers(),
					true, "import.png"),
			new MenuNode("107", "1", "მიმართვები", true, "person.png") };

	private TreeGrid menuTreeGrid;

	public CorrectionStackSelection(Body body) {
		this.body = body;
		setTitle("კორექციის განყოფილება");
		setExpanded(false);
		setCanCollapse(true);

		Tree menuTree = new Tree();
		menuTree.setModelType(TreeModelType.PARENT);
		menuTree.setRootValue(1);
		menuTree.setNameProperty("ქმედებების ჩამონათვალი");
		menuTree.setIdField("Id");
		menuTree.setParentIdField("ReportsTo");
		menuTree.setOpenProperty("isOpen");
		menuTree.setData(menuData);

		TreeGridField formattedField = new TreeGridField(
				"ქმედებების ჩამონათვალი");
		formattedField.setWidth("100%");
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
			boolean hasOrgBusActPerm = CommonSingleton.getInstance()
					.hasPermission("103500");
			boolean hasAbonentPerm = CommonSingleton.getInstance()
					.hasPermission("105000");
			boolean hasAbonentNameSurPerm = CommonSingleton.getInstance()
					.hasPermission("104000");
			boolean hasOrgManPerm = CommonSingleton.getInstance()
					.hasPermission("105500");
			boolean hasVirtChargePerm = CommonSingleton.getInstance()
					.hasPermission("106550");

			menuData[0].setAttribute("enabled", hasOrgBusActPerm);
			menuData[1].setAttribute("enabled", hasOrgManPerm);
			menuData[2].setAttribute("enabled", hasAbonentPerm);
			menuData[3].setAttribute("enabled", hasAbonentNameSurPerm);
			menuData[4].setAttribute("enabled", hasAbonentNameSurPerm);
			menuData[5].setAttribute("enabled", hasVirtChargePerm);
			menuData[6].setAttribute("enabled", hasOrgManPerm);
			menuData[7].setAttribute("enabled", hasOrgManPerm);

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
			TabOrgBusinessActivity tabOrgBusinessActivity = new TabOrgBusinessActivity();
			body.addTab(tabOrgBusinessActivity);
		} else if (menuId.equals("101")) {
			TabOrganization controlTabPane = new TabOrganization();
			body.addTab(controlTabPane);
		} else if (menuId.equals("102")) {
			TabSubscriber tabAbonent = new TabSubscriber();
			body.addTab(tabAbonent);
		} else if (menuId.equals("103")) {
			TabAbonentNames tabAbonentName = new TabAbonentNames();
			body.addTab(tabAbonentName);
		} else if (menuId.equals("104")) {
			TabAbonentLastNames tabAbonentLastNames = new TabAbonentLastNames();
			body.addTab(tabAbonentLastNames);
		} else if (menuId.equals("105")) {
			TabVirtualCharge tabVirtualCharge = new TabVirtualCharge();
			body.addTab(tabVirtualCharge);
		} else if (menuId.equals("106")) {
			body.addTab(new TabPartniorNumbersList());
		} else if (menuId.equals("107")) {
			body.addTab(new TabTreatments());
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
