package com.info08.billing.callcenter.client.ui.menu;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.content.currency.TabRateCurrency;
import com.info08.billing.callcenter.client.content.ent.TabEntPlace;
import com.info08.billing.callcenter.client.content.ent.TabEntPoster;
import com.info08.billing.callcenter.client.content.ent.TabEntType;
import com.info08.billing.callcenter.client.content.misc.TabCalendar;
import com.info08.billing.callcenter.client.content.misc.TabChurchCalendar;
import com.info08.billing.callcenter.client.content.misc.TabNonStandartGroup;
import com.info08.billing.callcenter.client.content.misc.TabNonStandartInfo;
import com.info08.billing.callcenter.client.content.misc.TabWebSite;
import com.info08.billing.callcenter.client.content.misc.TabWebSiteGroup;
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

public class MiscStackSelection extends SectionStackSection {

	private Body body;
	public static final TreeNode[] menuData = new TreeNode[] {
			new MenuNode("100", "1", CallCenter.constants.menuEntType(), true,"entertainment.png"),
			new MenuNode("101", "1", CallCenter.constants.menuEntPlace(), true,"entertainment.png"),
			new MenuNode("102", "1", CallCenter.constants.menuEntPoster(),true,"entertainment.png"),
			new MenuNode("103", "1", CallCenter.constants.menuCurrency(), true,"currency.png"),
			new MenuNode("104", "1", CallCenter.constants.menuCalendar(), true,"calendar.png"),
			new MenuNode("105", "1", CallCenter.constants.menuOrthCalendar(),true, "calendar.png"),
			new MenuNode("106", "1", CallCenter.constants.menuWebSiteGroup(),true, "web.png"),
			new MenuNode("107", "1", CallCenter.constants.menuWebSites(), true,"web.png"),
			new MenuNode("108", "1", CallCenter.constants.menuMiscCateg(),true, "palette.png"),
			new MenuNode("109", "1", CallCenter.constants.menuMisc(), true,"palette.png")
	};

	private TreeGrid menuTreeGrid;

	public MiscStackSelection(Body body) {
		this.body = body;
		setTitle(CallCenter.constants.miscActions());
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

		TreeGridField formattedField = new TreeGridField(
				CallCenter.constants.actionsList());
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
			boolean has100Perm = CommonSingleton.getInstance().hasPermission(
					"301000");
			menuData[0].setAttribute("enabled", has100Perm);

			boolean has101Perm = CommonSingleton.getInstance().hasPermission(
					"302000");
			menuData[1].setAttribute("enabled", has101Perm);

			boolean has102Perm = CommonSingleton.getInstance().hasPermission(
					"303000");
			menuData[2].setAttribute("enabled", has102Perm);

			boolean hasCurrPerm = CommonSingleton.getInstance().hasPermission(
					"304000");
			menuData[3].setAttribute("enabled", hasCurrPerm);

			boolean hasCalendar = CommonSingleton.getInstance().hasPermission(
					"305000");
			menuData[4].setAttribute("enabled", hasCalendar);
			menuData[5].setAttribute("enabled", hasCalendar);

			boolean hasWebSitePerm = CommonSingleton.getInstance()
					.hasPermission("306000");
			menuData[6].setAttribute("enabled", hasWebSitePerm);
			menuData[7].setAttribute("enabled", hasWebSitePerm);
			
			boolean hasMiscPerm = CommonSingleton.getInstance()
					.hasPermission("307000");
			menuData[8].setAttribute("enabled", hasMiscPerm);
			menuData[9].setAttribute("enabled", hasMiscPerm);

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
			TabEntType tabEntType = new TabEntType();
			body.addTab(tabEntType);
		} else if (menuId.equals("101")) {
			TabEntPlace tabEntPlace = new TabEntPlace();
			body.addTab(tabEntPlace);
		} else if (menuId.equals("102")) {
			TabEntPoster tabEntPoster = new TabEntPoster();
			body.addTab(tabEntPoster);
		} else if (menuId.equals("103")) {
			TabRateCurrency tabRateCurrency = new TabRateCurrency();
			body.addTab(tabRateCurrency);
		} else if (menuId.equals("104")) {
			TabCalendar tabCalendar = new TabCalendar();
			body.addTab(tabCalendar);
		} else if (menuId.equals("105")) {
			TabChurchCalendar tabChurchCalendar = new TabChurchCalendar();
			body.addTab(tabChurchCalendar);
		} else if (menuId.equals("106")) {
			TabWebSiteGroup tabWebSiteGroup = new TabWebSiteGroup();
			body.addTab(tabWebSiteGroup);
		} else if (menuId.equals("107")) {
			TabWebSite tabWebSite = new TabWebSite();
			body.addTab(tabWebSite);
		}else if (menuId.equals("108")) {
			TabNonStandartGroup tabNonStandartGroup = new TabNonStandartGroup();
			body.addTab(tabNonStandartGroup);
		}else if (menuId.equals("109")) {
			TabNonStandartInfo tabNonStandartInfo = new TabNonStandartInfo();
			body.addTab(tabNonStandartInfo);
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
