package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.content.transport.TabTranspSchedule;
import com.info08.billing.callcenterbk.client.content.transport.TabTransportCityBusStreet;
import com.info08.billing.callcenterbk.client.content.transport.TabTransportCityPublTransport;
import com.info08.billing.callcenterbk.client.content.transport.TabTransportCompany;
import com.info08.billing.callcenterbk.client.content.transport.TabTranspStation;
import com.info08.billing.callcenterbk.client.content.transport.TabTransportType;
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

public class TransportStackSelection extends SectionStackSection {

	private Body body;
	public static final TreeNode[] menuData = new TreeNode[] {
			new MenuNode("100", "1", "ტრანსპორტის ტიპები", true,
					"configuration.png"),
			new MenuNode("101", "1", "სატრანსპ. კომპანიები", true,
					"city.png"),
			new MenuNode("102", "1", "სატრანსპ. სადგურები", true,
					"LetterSicon.png"),
			new MenuNode("103", "1", "ქალაქის ავტობუსი/მიკრო", true, "bus.gif"),
			new MenuNode("104", "1", "ავტ./მიკრო მარშუტები", true,
					"bus.gif"),
			new MenuNode("105", "1", "ტრანსპ. განრიგი(ავიაცია)", true,
					"aviation.gif"),
			new MenuNode("106", "1", "ტრანსპ. განრიგი(რკინ.)", true,
					"train.png"),
			new MenuNode("107", "1", "ტრანსპ. განრიგი(ავტ.)", true,
					"bus.gif") };

	private TreeGrid menuTreeGrid;

	public TransportStackSelection(Body body) {
		this.body = body;
		setTitle("ტრანსპორტის განყოფილება");
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
			boolean hasTranpTypePerm = CommonSingleton.getInstance()
					.hasPermission("201000");
			menuData[0].setAttribute("enabled", hasTranpTypePerm);

			boolean hasTranpCompPerm = CommonSingleton.getInstance()
					.hasPermission("202000");
			menuData[1].setAttribute("enabled", hasTranpCompPerm);

			boolean hasTranpStatPerm = CommonSingleton.getInstance()
					.hasPermission("203000");
			menuData[2].setAttribute("enabled", hasTranpStatPerm);

			boolean hasTranpBusPerm = CommonSingleton.getInstance()
					.hasPermission("204000");
			menuData[3].setAttribute("enabled", hasTranpBusPerm);

			boolean hasTranpBusRouteStrPerm = CommonSingleton.getInstance()
					.hasPermission("205000");
			menuData[4].setAttribute("enabled", hasTranpBusRouteStrPerm);

			boolean hasTranpGraphPerm = CommonSingleton.getInstance()
					.hasPermission("206000");
			menuData[5].setAttribute("enabled", hasTranpGraphPerm);
			menuData[6].setAttribute("enabled", hasTranpGraphPerm);
			menuData[7].setAttribute("enabled", hasTranpGraphPerm);

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
			TabTransportType tabTransportType = new TabTransportType();
			body.addTab(tabTransportType);
		} else if (menuId.equals("101")) {
			TabTransportCompany transportCompany = new TabTransportCompany();
			body.addTab(transportCompany);
		} else if (menuId.equals("102")) {
			TabTranspStation tabTransportPlaces = new TabTranspStation();
			body.addTab(tabTransportPlaces);
		} else if (menuId.equals("103")) {
			TabTransportCityPublTransport tabTransportCityBus = new TabTransportCityPublTransport();
			body.addTab(tabTransportCityBus);
		} else if (menuId.equals("104")) {
			TabTransportCityBusStreet tabTransportCityBusStreet = new TabTransportCityBusStreet();
			body.addTab(tabTransportCityBusStreet);
		} else if (menuId.equals("105")) {
			TabTranspSchedule transport = new TabTranspSchedule(1000005);
			body.addTab(transport);
		} else if (menuId.equals("106")) {
			TabTranspSchedule transport = new TabTranspSchedule(1000003);
			body.addTab(transport);
		} else if (menuId.equals("107")) {
			TabTranspSchedule transport = new TabTranspSchedule(1000004);
			body.addTab(transport);
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
