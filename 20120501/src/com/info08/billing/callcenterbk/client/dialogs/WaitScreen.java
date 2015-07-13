package com.info08.billing.callcenterbk.client.dialogs;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class WaitScreen extends MyWindow {

	public WaitScreen() {
		super();
		setWidth(300);
		setHeight(70);
		setIsModal(true);
		setShowModalMask(true);
		setShowCloseButton(false);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		setShowMinimizeButton(false);
		setTitle("შეტყობინება");
		// setPadding(5);
		// setShowHeader(false);
		// setShowTitle(false);
		centerInPage();

		HLayout vLayout = new HLayout(10);
		vLayout.setWidth100();
		vLayout.setHeight100();
		vLayout.setPadding(5);
		// vLayout.setBorder("1px solid red");

		Label label = new Label(
				"მიმდინარეობს მონაცემების ჩათვირთვა, გთხოვთ დაიცადოთ !");
		// label.setBorder("1px solid blue");
		label.setWidth100();

		Img img = new Img("ajax-loader.gif", 32, 32);
		// img.setBorder("1px solid green");

		vLayout.setMembers(img, label);

		addItem(vLayout);
	}
}
