package com.info08.billing.callcenterbk.client.ui.layout;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class Center extends HLayout {

	private West westPanel;
	private Body bodyPanel;
	private East eastPanel;

	// protected UserManagerServiceAsync userManagerService;
	// public Center(UserManagerServiceAsync userManagerService) {
	public Center() {
		try {
			setMembersMargin(5);
			// this.userManagerService = userManagerService;
			setWidth100();
			bodyPanel = new Body();
			westPanel = new West(bodyPanel);
			if (CommonSingleton.getInstance().isCallCenterOperator()) {
				eastPanel = new East(bodyPanel);
			}
			
			if (CommonSingleton.getInstance().isCallCenterOperator()) {
				setMembers(westPanel, bodyPanel, eastPanel);
			} else {
				setMembers(westPanel, bodyPanel, eastPanel);
			}

			addShowContextMenuHandler(new ShowContextMenuHandler() {
				@Override
				public void onShowContextMenu(ShowContextMenuEvent event) {
					event.cancel();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
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
