package com.info08.billing.callcenter.client.ui.layout;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.layout.VLayout;

public class Header extends VLayout {
	//
	// private IButton logOutBtn;
	// private IButton showConsole;

	public Header() {
		setWidth100();
		setHeight(5);
		// setStyleName("headerClass");
		setAlign(VerticalAlignment.BOTTOM);

		VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight(5);
		// vLayout.setMargin(10);

		// logOutBtn = new IButton();
		// logOutBtn.setTitle("გამოსვლა");
		// logOutBtn.setAlign(Alignment.RIGHT);
		// logOutBtn.setLayoutAlign(Alignment.RIGHT);
		// logOutBtn.setWidth(90);
		// logOutBtn.setIcon("logout.gif");
		//
		// showConsole = new IButton();
		// showConsole.setTitle("Show Console");
		// showConsole.setAlign(Alignment.RIGHT);
		// showConsole.setLayoutAlign(Alignment.RIGHT);
		// showConsole.setWidth(90);
		//
		// vLayout.setMembers(logOutBtn);
		// showConsole
		// logOutBtn
		// .addClickHandler(new
		// com.smartgwt.client.widgets.events.ClickHandler() {
		// @Override
		// public void onClick(
		// com.smartgwt.client.widgets.events.ClickEvent event) {
		// try {
		// logOut();
		// } catch (Exception e) {
		// SC.say(e.toString());
		// }
		// }
		// });
		// showConsole.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// SC.showConsole();
		// }
		// });
		// addShowContextMenuHandler(new ShowContextMenuHandler() {
		// @Override
		// public void onShowContextMenu(ShowContextMenuEvent event) {
		// event.cancel();
		// }
		// });
		addMember(vLayout);
	}

	// private void logOut() throws CallCenterException {
	// SC.ask("გაფრთხილება", "დარწმუნებული ხართ რომ სისტემიდან გამოსვლა ?",
	// new BooleanCallback() {
	// @Override
	// public void execute(Boolean value) {
	// if (value) {
	// try {
	// CallCenter.initUI();
	// } catch (Exception e) {
	// SC.say(e.toString());
	// }
	// }
	// }
	// });
	// }
}
