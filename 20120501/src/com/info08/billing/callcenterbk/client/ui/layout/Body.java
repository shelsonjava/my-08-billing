package com.info08.billing.callcenterbk.client.ui.layout;

import com.info08.billing.callcenterbk.client.content.info.TabInfoPortal;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class Body extends VLayout {

	private TabSet mainTabPanel;
	private TabInfoPortal tabInfoPortal;

	Menu contextMenu;

	private static Body instance = null;

	public static Body getInstance() {
		return instance;
	}

	public Body() {
		instance = this;
		contextMenu = new Menu();
		contextMenu.setWidth(150);

		MenuItem closeTab = new MenuItem("ფანჯრის დახურვა");
		closeTab.setIcon("icon_close.gif");
		closeTab.setIconHeight(16);
		closeTab.setIconWidth(16);

		MenuItem closeAllTab = new MenuItem("ყველა ფანჯრის დახურვა");
		closeAllTab.setIcon("icon_closeall.gif");
		closeAllTab.setIconHeight(16);
		closeAllTab.setIconWidth(16);

		contextMenu.setItems(closeTab, closeAllTab);
		setHeight100();
		setWidth100();

		mainTabPanel = new TabSet();
		mainTabPanel.setTabBarPosition(Side.TOP);
		mainTabPanel.setWidth100();
		mainTabPanel.setHeight100();

		tabInfoPortal = new TabInfoPortal();
		tabInfoPortal.setID("InfoPortalTabId");
		mainTabPanel.addTab(tabInfoPortal);

		closeTab.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Tab tab = mainTabPanel.getSelectedTab();
				mainTabPanel.removeTab(tab);
			}
		});
		closeAllTab.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Tab tabs[] = mainTabPanel.getTabs();
				if (tabs != null && tabs.length > 0) {
					for (Tab tab : tabs) {
						if (!tab.getID().equals("InfoPortalTabId")) {
							mainTabPanel.removeTab(tab);
						}
					}
				}
			}
		});
		addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
			}
		});
		setMembers(mainTabPanel);
	}

	public void addTab(Tab tab) {
		tab.setContextMenu(contextMenu);
		mainTabPanel.addTab(tab);
		mainTabPanel.selectTab(tab.getID());
	}

	public boolean hasTab(String tabId) {
		return mainTabPanel.getTab(tabId) != null;
	}

	public void activateTab(String tabId) {
		mainTabPanel.selectTab(tabId);
	}

	public Tab getTab(String tabId) {
		return mainTabPanel.getTab(tabId);
	}

	public void closeAllTab() {
		mainTabPanel.clear();
	}

	public void reInitTabSet() {
		Tab tabs[] = mainTabPanel.getTabs();
		if (tabs != null && tabs.length > 0) {
			for (Tab tab : tabs) {
				if (tab.getCanClose()) {
					mainTabPanel.removeTab(tab);
				}
			}
		}
	}

	public TabInfoPortal getTabInfoPortal() {
		return tabInfoPortal;
	}

	public TabSet getMainTabPanel() {
		return mainTabPanel;
	}
}
