package com.info08.billing.callcenterbk.client.ui.layout;

import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class MainLayout extends VLayout {

	private Header headerPanel;
	private Center centerPanel;
	private Bottom bottomPanel;
//	protected UserManagerServiceAsync userManagerService;
	//public MainLayout(UserManagerServiceAsync userManagerService) {
	public MainLayout() {
		setPadding(0);
		setMargin(0);
		setMembersMargin(5);
//		this.userManagerService = userManagerService;
		setHeight100();
		setWidth100();
		headerPanel = new Header();
		//centerPanel = new Center(userManagerService);
		centerPanel = new Center();
		bottomPanel = new Bottom();
		addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
			}
		});
		setMembers(headerPanel, centerPanel, bottomPanel);
	}

	public Header getHeaderPanel() {
		return headerPanel;
	}

	public Center getCenterPanel() {
		return centerPanel;
	}

	public Bottom getBottomPanel() {
		return bottomPanel;
	}
}
