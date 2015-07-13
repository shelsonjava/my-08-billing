package com.info08.billing.callcenterbk.client.dialogs;

import java.util.Date;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.content.info.TabInfoPortal;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.Users;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class LoginDialog extends MyWindow {

	private HLayout hLayout;
	private Img img;
	private DynamicForm form;
	private TextItem userNameItem;
	private PasswordItem passwordItem;
	private ComboBoxItem languageItem;
	private CheckboxItem sipLogindItem;
	private ButtonItem buttonItem;

	public LoginDialog() {
		super();
		setWidth(400);
		setHeight(190);
		setTitle("იდენტიფიკაცია");
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setShowCloseButton(false);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		hLayout = new HLayout();
		hLayout.setWidth100();
		hLayout.setHeight100();

		img = new Img("Login2.png", 128, 128);

		form = new DynamicForm();
		form.setHeight100();
		form.setWidth100();
		form.setPadding(10);
		form.setAutoFocus(true);

		userNameItem = new TextItem();
		userNameItem.setTitle("მომხმარებლის სახელი");

		passwordItem = new PasswordItem();
		passwordItem.setTitle("პაროლი");
		passwordItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					login();
				}
			}
		});

		languageItem = new ComboBoxItem();
		languageItem.setTitle("ენა");
		languageItem.setType("comboBox");
		languageItem.setValueMap("ქართული", "ინგლისური");
		languageItem.setDefaultToFirstOption(true);

		sipLogindItem = new CheckboxItem();
		sipLogindItem.setTitle("ოპერატორი");
		sipLogindItem.setDefaultValue(false);
		sipLogindItem.setValue(false);

		buttonItem = new ButtonItem();
		buttonItem.setTitle("შესვლა");
		buttonItem.setColSpan(2);
		buttonItem.setAlign(Alignment.RIGHT);

		buttonItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				login();
			}
		});

		form.setFields(userNameItem, passwordItem, languageItem, sipLogindItem, buttonItem);
		hLayout.setMembers(img, form);
		addItem(hLayout);
		setCookieValues();
	}

	@SuppressWarnings("rawtypes")
	private void login() {
		final String userName = userNameItem.getValueAsString();
		if (userName == null || userName.trim().equals("")) {
			SC.warn("შეიყვანეთ მომხმარებელი !!!");
			return;
		}
		final String user_password = passwordItem.getValueAsString();
		if (user_password == null || user_password.trim().equals("")) {
			SC.warn("შეიყვანეთ პაროლი !!! ");
			return;
		}
		final Boolean isOperUser = sipLogindItem.getValueAsBoolean();
		try {
			buttonItem.setDisabled(true);
			CallCenterBK.commonService.login(userName, user_password, null, isOperUser.booleanValue(), new AsyncCallback<ServerSession>() {
				@Override
				public void onSuccess(ServerSession serverSession) {
					try {
						Users user = serverSession.getUser();
						CommonSingleton.getInstance().setServerSession(serverSession);
						CommonSingleton.getInstance().setSessionPerson(user);
						String sipUserName = user.getSip_user_name();
						String sipPassword = user.getSip_password();
						if (isOperUser.booleanValue() && (sipUserName == null || sipUserName.trim().equals("") || sipPassword == null || sipPassword.trim().equals(""))) {
							buttonItem.setDisabled(false);
							SC.say("თქვენ ვერ შეხვალთ სისტემაში როგორც ოპერატორი!. მომხმარებელი არ განეკუთვნება ოპერატორთა ჯგუფს.");
							return;
						}

						if (isOperUser.booleanValue() && (sipUserName != null && !sipUserName.trim().equals("") && sipPassword != null && !sipPassword.trim().equals(""))) {

							int object = sipLogin("192.168.1.28", sipUserName, sipPassword, LoginDialog.this);

							if (object != 0) {
								buttonItem.setDisabled(false);
								SC.say("SIP მომხმარებლის რეგისტრაცია ვერ მოხერხდა. მიმართეთ IT დეპარტამენტს.");
								return;
							} else {
								CommonSingleton.getInstance().setCallCenterOperator(true);
								CallCenterBK.drawMainPanel();
								CallCenterBK.getMainLayout().getCenterPanel().getBodyPanel();
								CallCenterBK.getMainLayout().getCenterPanel().getWestPanel().openByLoggedUser();
								TabInfoPortal.draw();
								saveCookies();
								destroy();
							}
						} else if (!sipLogindItem.getValueAsBoolean()) {
							CallCenterBK.drawMainPanel();
							CallCenterBK.getMainLayout().getCenterPanel().getBodyPanel();
							CallCenterBK.getMainLayout().getCenterPanel().getWestPanel().openByLoggedUser();
							TabInfoPortal.draw();
							saveCookies();
							destroy();
						}
					} catch (Exception e) {
						SC.say(e.toString());
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					if (caught instanceof CallCenterException) {
						CallCenterException c = (CallCenterException) caught;
						SC.say("1a:" + c.getErrorMessage());
					} else {
						SC.say("1b:" + caught.toString());
					}
					buttonItem.setDisabled(false);
				}
			});
		} catch (Exception e) {
			SC.say("2:" + e.toString());
			buttonItem.setDisabled(false);
		}

	}

	public void myCallBack(String result) {
		SC.say("Result = " + result);
	}

	public static native int sipLogin(String serverHost, String sipUsername, String sipPassword, LoginDialog instance) /*-{
		return $wnd.sipRegister(serverHost, sipUsername, sipPassword);
		//$wnd.myCallBack = function(result1) {
		//	instance.@com.info08.billing.callcenterbk.client.dialogs.LoginDialog::myCallBack(Ljava/lang/String;)(result);
		//};
	}-*/;

	private void setCookieValues() {
		String objUserName = Cookies.getCookie("infoBill08PortUserName");
		if (objUserName != null) {
			userNameItem.setValue(objUserName);
			form.focusInItem(passwordItem);
		}
	}

	private void saveCookies() {
		String objUserName = userNameItem.getValueAsString();
		if (objUserName == null || objUserName.trim().equals("")) {
			return;
		}
		Date now = new Date();
		long nowLong = now.getTime();
		nowLong = nowLong + (1000 * 60 * 60 * 24 * 7);// seven days
		now.setTime(nowLong);
		Cookies.setCookie("infoBill08PortUserName", userNameItem.getValueAsString(), now);
	}
}
