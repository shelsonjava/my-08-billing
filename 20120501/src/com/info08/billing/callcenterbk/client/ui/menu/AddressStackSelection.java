package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.content.TabTowns;
import com.info08.billing.callcenterbk.client.content.TabDistBetweenTowns;
import com.info08.billing.callcenterbk.client.content.TabTownDistrict;
import com.info08.billing.callcenterbk.client.content.TabCountry;
import com.info08.billing.callcenterbk.client.content.TabVillageIndexes;
import com.info08.billing.callcenterbk.client.content.TabDistrictIndexes;
import com.info08.billing.callcenterbk.client.content.TabStreetIndex;
import com.info08.billing.callcenterbk.client.content.TabStreet;
import com.info08.billing.callcenterbk.client.content.TabStreetNames;
import com.info08.billing.callcenterbk.client.content.TabStreetOldNames;
import com.info08.billing.callcenterbk.client.content.TabStreetKinds;
import com.info08.billing.callcenterbk.client.content.admin.TabCountryIndexes;
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

public class AddressStackSelection extends SectionStackSection {

	private Body body;
	public static final TreeNode[] menuData = new TreeNode[] {
			new MenuNode("100", "1", "ქვეყნები", true, "country.png"),
			new MenuNode("101", "1", "ქალაქები", true, "city.png"),
			new MenuNode("102", "1", "ქალაქის რეგიონები", true, "city.png"),
			new MenuNode("103", "1", "მანძილი ქალაქებს შორის", true, "measure_distance.gif"),
			new MenuNode("104", "1", "ქუჩების მართვა", true, "street.png"),
			new MenuNode("105", "1", "ქუჩების ტიპების მართვა", true, "street.png"),
			new MenuNode("106", "1", "ქუჩების დასახელებები", true, "street.png"),
			new MenuNode("107", "1", "ქუჩების ისტორია", true, "street.png"),
			new MenuNode("108", "1", "ინდექსები - რაიონები", true, "index.jpg"),
			new MenuNode("109", "1", "ინდექსები - სოფლები", true, "index.jpg"),
			new MenuNode("110", "1", "ინდექსები - ქუჩები", true, "index.jpg"),
			new MenuNode("111", "1", "ინდექსები - ქვეყნები", true, "index.jpg") };

	private TreeGrid menuTreeGrid;

	public AddressStackSelection(Body body) {
		this.body = body;
		setTitle("მისამართების განყოფილება");
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
			boolean hasPerm = CommonSingleton.getInstance().hasPermission(
					"109000");
			menuData[0].setAttribute("enabled", hasPerm);

			boolean hasCityPerm = CommonSingleton.getInstance().hasPermission(
					"110000");
			menuData[1].setAttribute("enabled", hasCityPerm);

			boolean hasCityRegPerm = CommonSingleton.getInstance()
					.hasPermission("115000");
			menuData[2].setAttribute("enabled", hasCityRegPerm);

			boolean hasCityDistPerm = CommonSingleton.getInstance()
					.hasPermission("115500");
			menuData[3].setAttribute("enabled", hasCityDistPerm);

			boolean hasStreetPerm = CommonSingleton.getInstance()
					.hasPermission("120000");
			menuData[4].setAttribute("enabled", hasStreetPerm);

			boolean hasStreetTypePerm = CommonSingleton.getInstance()
					.hasPermission("130000");
			menuData[5].setAttribute("enabled", hasStreetTypePerm);

			boolean hasStreetNamesPerm = CommonSingleton.getInstance()
					.hasPermission("140000");
			menuData[6].setAttribute("enabled", hasStreetNamesPerm);

			boolean hasStreetHistPerm = CommonSingleton.getInstance()
					.hasPermission("150000");
			menuData[7].setAttribute("enabled", hasStreetHistPerm);

			boolean hasGeoRegIdxPerm = CommonSingleton.getInstance()
					.hasPermission("160000");
			menuData[8].setAttribute("enabled", hasGeoRegIdxPerm);

			boolean hasGeoCouIdxPerm = CommonSingleton.getInstance()
					.hasPermission("170000");
			menuData[9].setAttribute("enabled", hasGeoCouIdxPerm);

			boolean hasGeoStrIdxPerm = CommonSingleton.getInstance()
					.hasPermission("180000");
			menuData[10].setAttribute("enabled", hasGeoStrIdxPerm);

			boolean hasCountryIndexes = CommonSingleton.getInstance()
					.hasPermission("110000");
			menuData[11].setAttribute("enabled", hasCountryIndexes);

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
			TabCountry tabCountry = new TabCountry();
			body.addTab(tabCountry);
		} else if (menuId.equals("101")) {
			TabTowns tabCity = new TabTowns();
			body.addTab(tabCity);
		} else if (menuId.equals("102")) {
			TabTownDistrict tabCityRegions = new TabTownDistrict();
			body.addTab(tabCityRegions);
		} else if (menuId.equals("103")) {
			TabDistBetweenTowns tabDistBetweenTowns = new TabDistBetweenTowns();
			body.addTab(tabDistBetweenTowns);
		} else if (menuId.equals("104")) {
			TabStreet tabStreet = new TabStreet();
			body.addTab(tabStreet);
		} else if (menuId.equals("105")) {
			TabStreetKinds tabStreetTypes = new TabStreetKinds();
			body.addTab(tabStreetTypes);
		} else if (menuId.equals("106")) {
			TabStreetNames tabStreetDescr = new TabStreetNames();
			body.addTab(tabStreetDescr);
		} else if (menuId.equals("107")) {
			TabStreetOldNames tabStreetHist = new TabStreetOldNames();
			body.addTab(tabStreetHist);
		} else if (menuId.equals("108")) {
			TabDistrictIndexes tabGeoRegIdx = new TabDistrictIndexes();
			body.addTab(tabGeoRegIdx);
		} else if (menuId.equals("109")) {
			TabVillageIndexes tabGeoCountryIdx = new TabVillageIndexes();
			body.addTab(tabGeoCountryIdx);
		} else if (menuId.equals("110")) {
			TabStreetIndex tabGeoStreetIdx = new TabStreetIndex();
			body.addTab(tabGeoStreetIdx);
		} else if (menuId.equals("111")) {
			TabCountryIndexes tabGeoStreetIdx = new TabCountryIndexes();
			body.addTab(tabGeoStreetIdx);
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
