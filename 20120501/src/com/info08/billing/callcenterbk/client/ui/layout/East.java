package com.info08.billing.callcenterbk.client.ui.layout;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VLayout;

public class East extends VLayout {

	// private DynamicForm form;
	// private CometClient cometClient;
	// private TextAreaItem messageItem;
	private IButton logOutBtn;

	// private Body lBodyPanel;

	// private PersonelDTO personelDTO;
	// private UserManagerServiceAsync userManagerService;
	// private InfoBilling08 infoBilling08;
	// private ListGrid usersList;
	//
	// @SerialTypes({ ChatMessage.class, StatusUpdate.class })
	// public static abstract class ChatCometSerializer extends CometSerializer
	// {
	// }
	// public East(UserManagerServiceAsync userManagerService) {
	public East(final Body bodyPanel) {

		// this.userManagerService = userManagerService;
		setWidth(80);
		setHeight100();

		VLayout hLayout = new VLayout();
		hLayout.setHeight(40);
		hLayout.setWidth100();
		hLayout.setAlign(Alignment.RIGHT);
		hLayout.setDefaultLayoutAlign(Alignment.RIGHT);
		hLayout.setPadding(5);
		hLayout.setLayoutAlign(Alignment.RIGHT);
		hLayout.setMembersMargin(10);

		logOutBtn = new IButton();
		logOutBtn.setTitle("გამოსვლა");
		logOutBtn.setAlign(Alignment.RIGHT);
		logOutBtn.setLayoutAlign(Alignment.RIGHT);
		logOutBtn.setWidth(70);
		hLayout.addMember(logOutBtn);

		logOutBtn
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						try {
							logOut();
						} catch (Exception e) {
							SC.say(e.toString());
						}
					}
				});

		// usersList = new ListGrid();
		// usersList.setWidth100();
		// usersList.setHeight(350);
		// usersList.setShowAllRecords(true);
		// usersList.setCanGroupBy(false);
		// usersList.setCanSort(false);
		// usersList.setCanRemoveRecords(false);
		// usersList.setCanReorderFields(false);
		// usersList.setCanResizeFields(false);
		// usersList.setCanAutoFitFields(false);

		// ListGridField userStatusField = new ListGridField("userStatus",
		// "სტატუსი", 60);
		// userStatusField.setAlign(Alignment.CENTER);
		// userStatusField.setType(ListGridFieldType.IMAGE);

		// ListGridField userNameField = new ListGridField("userName",
		// "მომხმარებლის სახელი");
		// usersList.setFields(userStatusField, userNameField);
		// usersList.setFields(userNameField);
		// hLayout.addMember(usersList);

		// form = new DynamicForm();
		// form.setWidth100();
		// form.setHeight100();
		// form.setPadding(5);
		//
		// messageItem = new TextAreaItem();
		// messageItem.setShowTitle(false);
		// messageItem.setLength(5000);
		// messageItem.setColSpan(2);
		// messageItem.setWidth("*");
		// messageItem.setHeight("*");
		//
		// form.setFields(messageItem);

		setMembers(hLayout);
	}

	private void logOut() throws CallCenterException {
		SC.ask("გაფრთხილება", "დარწმუნებული ხართ რომ სისტემიდან გამოსვლა ?",
				new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							try {
								CallCenterBK.initUI();
							} catch (Exception e) {
								SC.say(e.toString());
							}
						}
					}
				});
	}

	// public void startMessagingService(InfoBilling08 infoBilling08,
	// PersonelDTO personelDTO) {
	// try {
	// this.personelDTO = personelDTO;
	// this.infoBilling08 = infoBilling08;
	// CometSerializer serializer = GWT.create(ChatCometSerializer.class);
	// cometClient = new CometClient(GWT.getModuleBaseURL() + "comet",
	// serializer, new CometListener() {
	//
	// @Override
	// public void onRefresh() {
	// output("refresh");
	// }
	//
	// @Override
	// public void onMessage(
	// List<? extends Serializable> messages) {
	// for (Serializable message : messages) {
	// if (message instanceof ChatMessage) {
	// ChatMessage chatMessage = (ChatMessage) message;
	// output(chatMessage.getUsername() + ": "
	// + chatMessage.getMessage());
	// } else if (message instanceof StatusUpdate) {
	// StatusUpdate statusUpdate = (StatusUpdate) message;
	// output(statusUpdate.getUsername() + ": "
	// + statusUpdate.getStatus());
	// } else {
	// output("unrecognised message " + message);
	// }
	// }
	// }
	//
	// @Override
	// public void onHeartbeat() {
	// retrieveAvailableUsers();
	// output("Heartbeat");
	// }
	//
	// @Override
	// public void onError(Throwable exception,
	// boolean connected) {
	// output("error . " + connected + ". " + exception);
	// }
	//
	// @Override
	// public void onDisconnected() {
	// output("disconnected");
	// }
	//
	// @Override
	// public void onConnected(int heartbeat) {
	// output("connected " + heartbeat);
	// retrieveAvailableUsers();
	// }
	// });
	// cometClient.start();
	// } catch (Exception e) {
	// SC.say(e.toString());
	// }
	// }

	// private void fillUsersGrid(ArrayList<String> users) {
	// try {
	// if (users != null && !users.isEmpty()) {
	// UserRecord arr[] = new UserRecord[users.size()];
	// int i = 0;
	// for (String userName : users) {
	// arr[i] = new UserRecord(userName);
	// i++;
	// }
	// usersList.setData(arr);
	// usersList.selectRecord(0);
	// }
	// } catch (Exception e) {
	// SC.say(e.toString());
	// }
	// }
	//
	// class UserRecord extends ListGridRecord {
	//
	// void ListGridRecord() {
	// }
	//
	// public UserRecord(String userName) {
	// setUserName(userName);
	// }
	//
	// public String getUserName() {
	// return getAttributeAsString("userName");
	// }
	//
	// public void setUserName(String userName) {
	// setAttribute("userName", userName);
	// }
	//
	// @Override
	// public String toString() {
	// return getUserName();
	// }
	// }

	// private void retrieveAvailableUsers() {
	// try {
	// userManagerService
	// .getAllAvailableUsers(new AsyncCallback<ArrayList<String>>() {
	// public void onSuccess(ArrayList<String> result) {
	// fillUsersGrid(result);
	// };
	//
	// @Override
	// public void onFailure(Throwable caught) {
	// SC.say(caught.toString());
	// }
	// });
	// } catch (Exception e) {
	// SC.say(e.toString());
	// }
	// }

	// private void logOut() {
	// userManagerService.logout(personelDTO.getUserName(),
	// new AsyncCallback<Void>() {
	// @Override
	// public void onSuccess(Void result) {
	// cometClient.stop();
	// LoginDialog loginDialog = new LoginDialog(
	// userManagerService, infoBilling08);
	// loginDialog.draw();
	// }
	//
	// @Override
	// public void onFailure(Throwable caught) {
	// output(caught.toString());
	// }
	// });
	// }
	//
	// private void output(String text) {
	// Object value = messageItem.getValue();
	// if (value == null) {
	// value = "";
	// }
	// String newValue = value.toString();
	// newValue += (text + "\n");
	// messageItem.setValue(newValue);
	// }
}
