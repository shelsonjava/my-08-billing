package com.info08.billing.callcenterbk.client.dialogs.history;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgHistSubscriber extends Window {

	private VLayout hLayout;

	public DlgHistSubscriber(final Record abonentRecord) {
		try {
			setWidth(760);
			setHeight(730);
			setTitle(abonentRecord == null ? "ახალი აბონენტის დამატება"
					: "აბონენტის მოდიფიცირება");
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

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
