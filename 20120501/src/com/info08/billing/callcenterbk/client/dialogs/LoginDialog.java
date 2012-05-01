package com.info08.billing.callcenterbk.client.dialogs;

import java.util.Date;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.info.TabInfoPortal;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.ui.layout.MainLayout;
import com.info08.billing.callcenterbk.client.ui.layout.West;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class LoginDialog extends Window {

	private HLayout hLayout;
	private Img img;
	private DynamicForm form;
	private TextItem userNameItem;
	private PasswordItem passwordItem;
	private ComboBoxItem languageItem;
	private ButtonItem buttonItem;
	private MainLayout mainLayout;

	public LoginDialog(MainLayout mainLayout) {
		this.mainLayout = mainLayout;
		setWidth(400);
		setHeight(170);
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

		form.setFields(userNameItem, passwordItem, languageItem, buttonItem);
		hLayout.setMembers(img, form);
		addItem(hLayout);
		setCookieValues();
	}

	private void login() {
		final String userName = userNameItem.getValueAsString();
		if (userName == null || userName.trim().equals("")) {
			SC.warn("შეიყვანეთ მომხმარებელი !!!");
			return;
		}
		final String password = passwordItem.getValueAsString();
		if (password == null || password.trim().equals("")) {
			SC.warn("შეიყვანეთ პაროლი !!! ");
			return;
		}
		try {
			buttonItem.setDisabled(true);
			CallCenterBK.commonService.login(userName, password, null,
					new AsyncCallback<ServerSession>() {
						@Override
						public void onSuccess(ServerSession serverSession) {
							try {
								CommonSingleton.getInstance().setServerSession(
										serverSession);
								CommonSingleton.getInstance().setSessionPerson(
										serverSession.getUser());
								West.openByLoggedUser();
								mainLayout.getCenterPanel().getBodyPanel();
								TabInfoPortal.draw();
								saveCookies();
								destroy();
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
		} catch (Exception e) {
			SC.say(e.toString());
		}
		buttonItem.setDisabled(false);
	}

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
		Cookies.setCookie("infoBill08PortUserName",
				userNameItem.getValueAsString(), now);
	}
}
