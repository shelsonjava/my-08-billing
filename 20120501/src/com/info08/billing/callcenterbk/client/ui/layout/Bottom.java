package com.info08.billing.callcenterbk.client.ui.layout;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class Bottom extends HLayout {

	public Bottom() {
		setWidth100();
		setHeight(20);
		Label about = new Label("Information Center 08. Copyright 2010 - 2011. ");
		about.setWidth100();
		about.setHeight100();
		about.setAlign(Alignment.RIGHT);
		about.setStyleName("abouttext");
		setMembers(about);
		addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
			}
		});
	}
}
