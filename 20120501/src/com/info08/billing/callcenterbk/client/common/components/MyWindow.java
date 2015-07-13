package com.info08.billing.callcenterbk.client.common.components;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;

public class MyWindow extends Window {

	public MyWindow() {
		super();
		try {
			CommonSingleton.getInstance().addDialogInstance(this);
			addCloseClickHandler(new CloseClickHandler() {
				@Override
				public void onCloseClick(CloseClickEvent event) {
					try {
						removeInstance();
					} catch (Exception e) {
						e.printStackTrace();
						SC.say(e.toString());
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void removeInstance() {
		try {
			CommonSingleton.getInstance().removeDialogInstance(this);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	@Override
	public void destroy() {
		try {
			removeInstance();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
		super.destroy();
	}
}
