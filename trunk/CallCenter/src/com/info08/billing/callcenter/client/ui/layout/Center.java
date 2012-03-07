package com.info08.billing.callcenter.client.ui.layout;

import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class Center extends HLayout {

	private West westPanel;
	private Body bodyPanel;
	private East eastPanel;
//	protected UserManagerServiceAsync userManagerService;
//public Center(UserManagerServiceAsync userManagerService) {
	public Center() {
		setMembersMargin(5);
		//this.userManagerService = userManagerService;
		setWidth100();
		bodyPanel = new Body();
		westPanel = new West(bodyPanel);		
//		eastPanel = new East(bodyPanel);
		setMembers(westPanel, bodyPanel, eastPanel);
		addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
			}
		});
	}

	public West getWestPanel() {
		return westPanel;
	}

	public Body getBodyPanel() {
		return bodyPanel;
	}

	public East getEastPanel() {
		return eastPanel;
	}
}
