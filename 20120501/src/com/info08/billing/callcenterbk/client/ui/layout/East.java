package com.info08.billing.callcenterbk.client.ui.layout;

import java.util.LinkedHashMap;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.info.TabInfoPortal;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.SipConfig;
import com.info08.billing.callcenterbk.shared.entity.Users;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public class East extends VLayout {

	private static Label timerLabel;
	private IButton logOutBtn;
	private IButton hangupBtn;
	private RadioGroupItem radioGroupItem;
	private LinkedHashMap<String, SipConfig> mapConfigs;

	public East(final Body bodyPanel) {
		try {
			mapConfigs = new LinkedHashMap<String, SipConfig>();
			setWidth(200);
			setHeight100();
			VLayout hLayout = new VLayout();
			hLayout.setHeight(40);
			hLayout.setWidth100();
			hLayout.setPadding(5);
			hLayout.setMembersMargin(10);

			LayoutSpacer spacer1 = new LayoutSpacer();
			spacer1.setHeight(2);
			hLayout.addMember(spacer1);

			timerLabel = new Label();
			timerLabel.setWidth100();
			timerLabel.setHeight(20);
			timerLabel.setContents("<html><body><p style=\"color:red;font-size: 14px;font-weight: bold;\">საუბრის დრო - 00:00 </p></body></html>");
			hLayout.addMember(timerLabel);

			logOutBtn = new IButton();
			logOutBtn.setTitle("პროგრამიდან გამოსვლა");
			logOutBtn.setAlign(Alignment.CENTER);
			logOutBtn.setLayoutAlign(Alignment.CENTER);
			logOutBtn.setWidth100();
			logOutBtn.setTitleStyle("fontRedAndBoldBtn");
			logOutBtn.setAlign(Alignment.LEFT);
			hLayout.addMember(logOutBtn);

			LayoutSpacer spacer3 = new LayoutSpacer();
			spacer3.setHeight(10);
			hLayout.addMember(spacer3);

			hangupBtn = new IButton();
			hangupBtn.setTitle("ზარის გათიშვა");
			hangupBtn.setAlign(Alignment.CENTER);
			hangupBtn.setLayoutAlign(Alignment.CENTER);
			hangupBtn.setWidth100();
			hangupBtn.setTitleStyle("fontRedAndBoldBtn");
			hangupBtn.setAlign(Alignment.LEFT);
			hLayout.addMember(hangupBtn);

			LayoutSpacer spacer4 = new LayoutSpacer();
			spacer4.setHeight(10);
			hLayout.addMember(spacer4);

			radioGroupItem = new RadioGroupItem();
			radioGroupItem.setTitle("");
			radioGroupItem.setTextBoxStyle("fontRedAndBoldBtn");
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put("0", "ჩართვა");
			valueMap.put("1", "ბლოკზე დადგომა");
			radioGroupItem.setValueMap(valueMap);

			radioGroupItem.addChangedHandler(new ChangedHandler() {
				@SuppressWarnings("rawtypes")
				@Override
				public void onChanged(ChangedEvent event) {
					int type = Integer.parseInt(radioGroupItem.getValueAsString());
					switch (type) {
					case 1:
						logOut();
						break;
					case 0:
						try {
							ServerSession serverSession = CommonSingleton.getInstance().getServerSession();
							Users user = serverSession.getUser();
							String sipUserName = user.getSip_user_name();
							String sipPassword = user.getSip_password();							
							sipLogin("192.168.1.28", sipUserName, sipPassword);
							break;
						} catch (Exception e) {
							e.printStackTrace();
							SC.say(e.toString());
						}
					default:
						break;
					}
				}
			});

			DynamicForm dynamicForm = new DynamicForm();
			dynamicForm.setWidth100();
			dynamicForm.setNumCols(1);
			dynamicForm.setItems(radioGroupItem);
			dynamicForm.setTitleOrientation(TitleOrientation.TOP);
			dynamicForm.setPrefix(" ");
			dynamicForm.setTitleSuffix(" ");
			dynamicForm.setTitleWidth(0);
			hLayout.addMember(dynamicForm);

			LayoutSpacer spacer2 = new LayoutSpacer();
			spacer2.setHeight(25);
			hLayout.addMember(spacer2);

			radioGroupItem.setValue("0");

			SipConfig sipConfigs[] = CommonSingleton.getInstance().getServerSession().getSipConfs();
			if (sipConfigs != null && sipConfigs.length > 0) {
				for (int i = 0; i < sipConfigs.length; i++) {
					SipConfig sipConfig = sipConfigs[i];
					if (!sipConfig.getConfig_id().equals(1L)) {
						continue;
					}
					final IButton btn = new IButton();
					btn.setTitle(sipConfig.getConfig_value());
					btn.setAlign(Alignment.CENTER);
					btn.setLayoutAlign(Alignment.CENTER);
					btn.setWidth100();
					btn.setAlign(Alignment.LEFT);
					hLayout.addMember(btn);

					mapConfigs.put(btn.getID(), sipConfig);

					btn.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							sipAction(btn);
						}
					});
				}
			}

			hangupBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					hangUp();
				}
			});

			logOutBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					logOut();
					Window.Location.reload();
				}
			});

			LayoutSpacer spacer = new LayoutSpacer();
			spacer.setHeight(9);
			setMembers(spacer, hLayout);
			prepareCallBack();
			hangUpCallBack();
			transferCallBack();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sipAction(IButton iButton) {
		try {
			String btnId = iButton.getID();
			SipConfig sipConfig = mapConfigs.get(btnId);
			String transferPhone = sipConfig.getParam_1();
			if (transferPhone == null || transferPhone.trim().equals("")) {
				SC.say(CallCenterBK.constants.invalid_transfer_phone());
				return;
			}
			makeTransfer(transferPhone);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public static native void makeTransfer(String transferPhone) /*-{
		$wnd.sipTransfer(transferPhone);
	}-*/;

	public static native void hangUp() /*-{
		$wnd.sipHangUp();
	}-*/;
	
	public static native void hold() /*-{
		$wnd.sipToggleHoldMy();
	}-*/;
	
	public static native void resume() /*-{
		$wnd.sipToggleResumeMy();
	}-*/;
	

	public static native void logOut() /*-{
		$wnd.sipUnRegister();
	}-*/;

	public static native void prepareCallBack() /*-{
		$wnd.sipIncommingCall = @com.info08.billing.callcenterbk.client.ui.layout.East::calling(Ljava/lang/String;);
	}-*/;
	
	public static native int sipLogin(String serverHost, String sipUsername, String sipPassword) /*-{
		return $wnd.sipRegister(serverHost, sipUsername, sipPassword);	
	}-*/;
	

	private static Timer timer;
	private static Long milis = 0L;

	private static void startTimer() {
		if (timer != null) {
			timer.cancel();
			milis = 0L;
		}
		timer = new Timer() {
			public void run() {
				milis++;
				redrawTime(milis);
			}
		};
		timer.scheduleRepeating(1000);
	}

	private static void stopTimer() {
		timer.cancel();
		milis = 0L;
	}

	private static void redrawTime(Long time) {
		long minutes = (long) time / 60;
		long seconds = time - (minutes * 60);
		String sMinutes = (minutes < 10) ? ("0" + minutes) : (minutes + "");
		String sSeconds = (seconds < 10) ? ("0" + seconds) : (seconds + "");
		timerLabel.setContents("<html><body><p style=\"color:red;font-size: 14px;font-weight: bold;\">საუბრის დრო - " + sMinutes + ":" + sSeconds + " </p></body></html>");
		timerLabel.redraw();
	}

	@SuppressWarnings("rawtypes")
	public static void calling(String phone) {
		try {
			startTimer();
			ServerSession serverSession = CommonSingleton.getInstance().getServerSession();
			CallCenterBK.commonService.getInfoByPhone(phone, serverSession, new AsyncCallback<ServerSession>() {
				@Override
				public void onSuccess(ServerSession result) {
					try {
						Users user = result.getUser();
						CommonSingleton.getInstance().setServerSession(result);
						CommonSingleton.getInstance().setSessionPerson(user);
						CommonSingleton.getInstance().closeAllOpenDialogs();
						Body.getInstance().reInitTabSet();
						TabInfoPortal.draw();
					} catch (Exception e) {
						SC.say("შეცდომა ზარის განხორციელების დროს მონაცემების წამოღებისას 1.");
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					SC.say("შეცდომა ზარის განხორციელების დროს მონაცემების წამოღებისას");
					hangUp();
				}
			});
		} catch (Exception e) {
			SC.say(e.toString());
			hangUp();
		}
	}

	public static native void hangUpCallBack() /*-{
		$wnd.mySipHangUpHandler = @com.info08.billing.callcenterbk.client.ui.layout.East::hangUp(Ljava/lang/String;Ljava/lang/String;);
	}-*/;

	@SuppressWarnings("rawtypes")
	public static void hangUp(String phone, String type) {
		try {
			Long callDuration = milis;
			if (type == null || type.trim().equals("")) {
				type = "0";
			}
			Long rejectType = Long.parseLong(type);
			Long switchOverType = 0L;
			ServerSession serverSession = CommonSingleton.getInstance().getServerSession();
			serverSession.setRejectType(rejectType);
			serverSession.setSipCallDuration(callDuration);
			serverSession.setSwitchOverType(switchOverType);
			stopTimer();
			CallCenterBK.commonService.endCallSession(phone, serverSession, new AsyncCallback<ServerSession>() {
				@Override
				public void onSuccess(ServerSession result) {

				}

				@Override
				public void onFailure(Throwable caught) {
					SC.say(caught.toString());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public static native void transferCallBack() /*-{
		$wnd.mySipTransferHandler = @com.info08.billing.callcenterbk.client.ui.layout.East::transfer(Ljava/lang/String;Ljava/lang/String;);
	}-*/;

	@SuppressWarnings("rawtypes")
	public static void transfer(String phone, String tranferPhone) {
		try {
			Long callDuration = milis;
			Long rejectType = 1L;
			Long switchOverType = 0L;
			if (tranferPhone == null || tranferPhone.trim().equals("")) {
				tranferPhone = "0";
			}
			switchOverType = Long.parseLong(tranferPhone);

			ServerSession serverSession = CommonSingleton.getInstance().getServerSession();
			serverSession.setRejectType(rejectType);
			serverSession.setSipCallDuration(callDuration);
			serverSession.setSwitchOverType(switchOverType);
			stopTimer();
			CallCenterBK.commonService.endCallSession(phone, serverSession, new AsyncCallback<ServerSession>() {
				@Override
				public void onSuccess(ServerSession result) {
				}

				@Override
				public void onFailure(Throwable caught) {
					SC.say(caught.toString());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
