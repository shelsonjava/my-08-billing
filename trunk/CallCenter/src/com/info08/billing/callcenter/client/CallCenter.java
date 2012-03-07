package com.info08.billing.callcenter.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.info08.billing.callcenter.client.content.info.TabInfoPortal;
import com.info08.billing.callcenter.client.dialogs.LoginDialog;
import com.info08.billing.callcenter.client.exception.CallCenterException;
import com.info08.billing.callcenter.client.service.CommonService;
import com.info08.billing.callcenter.client.service.CommonServiceAsync;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.client.ui.layout.MainLayout;
import com.info08.billing.callcenter.client.ui.layout.West;
import com.info08.billing.callcenter.shared.common.ServerSession;
import com.smartgwt.client.util.SC;

public class CallCenter implements EntryPoint {

	public static final CommonServiceAsync commonService = GWT
			.create(CommonService.class);
	private static MainLayout mainLayout;
	public static AppConstants constants = (AppConstants) GWT
			.create(AppConstants.class);
	private static boolean isOperator = false;
	private static String sessionId;

	public void onModuleLoad() {
		try {
			//KeyboardHandler
			//		.registerLanguage(
			//				1,
			//				"97:4304;98:4305;103:4306;100:4307;101:4308;118:4309;122:4310;84:4311;105:4312;107:4313;108:4314;109:4315;110:4316;111:4317;112:4318;74:4319;114:4320;115:4321;116:4322;117:4323;102:4324;113:4325;82:4326;121:4327;83:4328;67:4329;99:4330;90:4331;119:4332;87:4333;120:4334;106:4335;104:4336");
			//KeyboardHandler.setLanguage(0);
			// final String isDisc = com.google.gwt.user.client.Window.Location
			// .getParameter("isDisc");
			//
			// final String discoveryTxt =
			// com.google.gwt.user.client.Window.Location
			// .getParameter("discoveryTxt");
			// final String discTypeId =
			// com.google.gwt.user.client.Window.Location
			// .getParameter("discTypeId");
			//
			// if (isDisc != null && isDisc.trim().equals("yes")) {
			// commonService.login("noUserName", "noPassword", sessionId,
			// new AsyncCallback<ServerSession>() {
			// @Override
			// public void onSuccess(ServerSession serverSession) {
			// try {
			// CommonSingleton.getInstance()
			// .setSessionPerson(
			// serverSession.getUser());
			// CommonSingleton.getInstance()
			// .setServerSession(serverSession);
			// DlgSendDiscovery dlgSendDiscovery = new DlgSendDiscovery(
			// (discTypeId == null
			// || discTypeId
			// .equals("null") ? null
			// : Integer
			// .parseInt(discTypeId)),
			// (discoveryTxt == null
			// || discoveryTxt
			// .equals("null") ? null
			// : discoveryTxt));
			// dlgSendDiscovery.show();
			//
			// } catch (Exception e) {
			// SC.say(e.toString());
			// }
			// }
			//
			// @Override
			// public void onFailure(Throwable caught) {
			// SC.say(caught.toString());
			// }
			// });
			// return;
			// }

			sessionId = com.google.gwt.user.client.Window.Location
					.getParameter("sessionId");

			// SC.say("sessionId"+sessionId);
			if (sessionId != null && !sessionId.trim().equalsIgnoreCase("")) {
				isOperator = true;
			}
			// SC.showConsole();
			initUI();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}

	}

	public static void initUI() throws CallCenterException {
		if (mainLayout != null) {
			mainLayout.destroy();
		}

		if (!isOperator) {
			CommonSingleton.getInstance().reInitDS();
			mainLayout = new MainLayout();
			RootPanel.get().add(mainLayout);
			LoginDialog loginDialog = new LoginDialog(mainLayout);
			loginDialog.draw();
		} else {
			commonService.login("noUserName", "noPassword", sessionId,
					new AsyncCallback<ServerSession>() {
						@Override
						public void onSuccess(ServerSession serverSession) {
							try {
								CommonSingleton.getInstance().reInitDS();
								mainLayout = new MainLayout();
								RootPanel.get().add(mainLayout);
								CommonSingleton.getInstance().setSessionPerson(
										serverSession.getUser());
								CommonSingleton.getInstance().setServerSession(
										serverSession);
								West.openByLoggedUser();
								mainLayout.getCenterPanel().getBodyPanel();
								TabInfoPortal.draw();
								//KeyboardHandler.setLanguage(1);
							} catch (Exception e) {
								SC.say(e.toString());
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							SC.say(caught.toString());
						}
					});
		}
	}

	public MainLayout getMainLayout() {
		return mainLayout;
	}
}
