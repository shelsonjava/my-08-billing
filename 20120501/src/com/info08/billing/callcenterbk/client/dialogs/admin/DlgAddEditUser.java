package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class DlgAddEditUser extends MyWindow {

	private VLayout hLayout;

	public DlgAddEditUser(Object abonentRecord) {
		super();
		setTitle(abonentRecord == null ? "ახალი მომხმარებლის დამატება"
				: "მომხმარებლის მოდიფიცირება");
		setHeight(600);
		setWidth(700);
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		hLayout = new VLayout(5);
		hLayout.setWidth100();
		hLayout.setHeight100();
		hLayout.setPadding(10);

		final TabSet mainTabPanel = new TabSet();
		mainTabPanel.setTabBarPosition(Side.TOP);
		mainTabPanel.setWidth100();
		mainTabPanel.setHeight100();

		final TabUserMainInfo tabUserMainInfo = new TabUserMainInfo();
		mainTabPanel.addTab(tabUserMainInfo);

		Tab tTab2 = new Tab("დამატებითი ინფორმაცია");
		tTab2.setID("addInfo");
		mainTabPanel.addTab(tTab2);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton saveItem = new IButton();
		saveItem.setTitle("შენახვა");
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle("დახურვა");
		cancItem.setWidth(100);

		hLayoutItem.setMembers(saveItem, cancItem);

		hLayout.setMembers(mainTabPanel, hLayoutItem);

		addItem(hLayout);

		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				mainTabPanel.clear();
				mainTabPanel.destroy();
				destroy();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Escape")) {
					mainTabPanel.clear();
					mainTabPanel.destroy();
					destroy();
				}
			}
		});
	}
}
