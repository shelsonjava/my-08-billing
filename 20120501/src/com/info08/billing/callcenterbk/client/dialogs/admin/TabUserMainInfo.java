package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FileItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabUserMainInfo extends Tab {

	private DynamicForm infoForm;
	private TextItem firstNameItem;
	private TextItem lastNameItem;
	private TextItem userNameItem;
	private TextItem identNoItem;
	private TextItem fathersNameItem;
	private DateItem birthdayItem;
	private ComboBoxItem genderItem;
	private ComboBoxItem familyStatusItem;
	private TextItem nationalityItem;
	private TextItem commentItem;
	private DataSource dataSource;

	public TabUserMainInfo() {
		setTitle("ძირითადი ინფორმაცია");
		setID("mainInfo");
		dataSource = DataSource.get("imageTestDS");

		VLayout mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setPadding(10);

		HLayout hLayout = new HLayout(2);
		hLayout.setWidth100();
		hLayout.setHeight(200);

		VLayout imageLayOut = new VLayout();
		imageLayOut.setWidth("40%");
		imageLayOut.setHeight(206);
		imageLayOut.setBorder("1px solid gray");

		mainLayout.addMember(hLayout);

		infoForm = new DynamicForm();
		infoForm.setAutoFocus(true);
		infoForm.setWidth("60%");
		infoForm.setTitleWidth(200);
		infoForm.setNumCols(2);
		hLayout.addMember(infoForm);
		hLayout.addMember(imageLayOut);

		firstNameItem = new TextItem();
		firstNameItem.setTitle("სახელი");
		firstNameItem.setWidth(250);
		firstNameItem.setName("firstname");

		lastNameItem = new TextItem();
		lastNameItem.setTitle("გვარი");
		lastNameItem.setWidth(250);
		lastNameItem.setName("lastname");

		userNameItem = new TextItem();
		userNameItem.setTitle("მომხმარებელი");
		userNameItem.setWidth(250);
		userNameItem.setName("username");

		identNoItem = new TextItem();
		identNoItem.setTitle("პირადი ნომერი");
		identNoItem.setWidth(250);
		identNoItem.setName("identNo");

		fathersNameItem = new TextItem();
		fathersNameItem.setTitle("მამის სახელი");
		fathersNameItem.setWidth(250);
		fathersNameItem.setName("fathersname");

		birthdayItem = new DateItem();
		birthdayItem.setUseTextField(true);
		birthdayItem.setTitle("დაბადების თარიღი");
		birthdayItem.setWidth(250);
		birthdayItem.setName("birthday");

		genderItem = new ComboBoxItem();
		genderItem.setValueMap(ClientMapUtil.getInstance().getMapGender());
		genderItem.setDefaultToFirstOption(true);
		genderItem.setTitle("სქესი");
		genderItem.setName("gender");
		genderItem.setWidth(250);

		familyStatusItem = new ComboBoxItem();
		familyStatusItem
				.setValueMap(ClientMapUtil.getInstance().getMapFamilyStatus());
		familyStatusItem.setDefaultToFirstOption(true);
		familyStatusItem.setTitle("ოჯახ. მდგომარეობა");
		familyStatusItem.setName("familystatus");
		familyStatusItem.setWidth(250);

		infoForm.setFields(firstNameItem, lastNameItem, userNameItem,
				identNoItem, fathersNameItem, birthdayItem, genderItem,
				familyStatusItem);
		// birthdayItem,

		final FormItemIcon icon = new FormItemIcon();
		// icon.setSrc("http://t0.gstatic.com/images?q=tbn:RCNiLrbQshAjzM:");
		icon.setWidth(85);
		icon.setHeight(113);

		final StaticTextItem severityLevel = new StaticTextItem();
		severityLevel.setName("severityLevel");
		severityLevel.setTitle("");
		severityLevel.setIcons(icon);
		severityLevel.setAlign(Alignment.CENTER);

		final DynamicForm imageForm = new DynamicForm();
		imageForm.setDataSource(dataSource);
		imageForm.setNumCols(2);
		imageForm.setTitleWidth(0);
		// imageForm.setBorder("1px solid gray");
		imageForm.setFields(severityLevel);
		imageForm.setAlign(Alignment.CENTER);

		imageLayOut.addMember(imageForm);

		final FileItem imageItem = new FileItem();
		imageItem.setTitle("აირჩიეთ ფაილი");
		imageItem.setName("imageTest");
		imageItem.setTitleOrientation(TitleOrientation.TOP);

		final HiddenItem hIdentNoItem = new HiddenItem();
		hIdentNoItem.setName("file_name");

		final DynamicForm imageForm1 = new DynamicForm();
		imageForm1.setDataSource(dataSource);
		imageForm1.setNumCols(2);
		// imageForm1.setBorder("1px solid red");
		imageForm1.setFields(hIdentNoItem, imageItem);
		imageLayOut.addMember(imageForm1);

		IButton uploadButton = new IButton();
		uploadButton.setTitle("ატვირთვა");
		uploadButton.setWidth(75);
		// uploadButton.setAlign(Alignment.RIGHT);

		HLayout upload = new HLayout();
		upload.setWidth100();
		// upload.setPadding(12);
		upload.setStyleName("paddingRight10");
		upload.setAlign(Alignment.RIGHT);
		upload.setMembers(uploadButton);
		imageLayOut.addMember(upload);

		uploadButton
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {

						final String identNo = identNoItem.getValueAsString();
						if (identNo == null) {
							SC.say("გთხოვთ შეიყვანოთ პირადი ნომერი !");
							return;
						}
						try {
							new Long(identNo);
						} catch (NumberFormatException e) {
							SC.say("პირადი ნომერი უნდა შედგებოდეს მხოლოდ ციფრებისაგან!");
							return;
						}
						if (identNo.length() != 11) {
							SC.say("პირადი ნომერი შედგება 11 ციფრისაგან!");
							return;
						}
						hIdentNoItem.setValue(identNo);

						imageForm1.saveData(new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								Record records[] = response.getData();
								if (records == null || records.length <= 0) {
									SC.say("შეცდომა სურათის ატვირთვისას!");
									return;
								}
								Record record = records[0];
								String ext = record.getAttribute("image_ext");
								String fullPath = Constants.hrImagesDirLink
										+ identNo + ext;
								// SC.say(fullPath);

								FormItemIcon icon = new FormItemIcon();
								icon.setSrc("");
								icon.setSrc(fullPath);
								icon.setWidth(85);
								icon.setHeight(113);
								severityLevel.clearValue();
								severityLevel.setIcons(icon);

								// Record records[] = response.getData();
								// if (records == null || records.length <= 0) {
								// return;
								// }
								// String arr[] = records[0].getAttributes();
								// String str = "";
								// for (String string : arr) {
								// str += " " + string;
								// }
								// SC.say(records[0].getAttribute("imageTest"));
								// DSRequest dsRequest = new DSRequest();
								// dsRequest.setAttribute("operationId",
								// "getImage");

								// listGrid.fetchData(new Criteria(), new
								// DSCallback() {
								// @Override
								// public void execute(DSResponse response,
								// Object rawData, DSRequest request) {
								//
								// Record records[] = response.getData();
								// if (records == null || records.length <= 0) {
								// return;
								// }
								// String arr[] = records[0].getAttributes();
								// String str = "";
								// for (String string : arr) {
								// str += " " + string;
								// }
								// SC.say(str);
								// }
								// });
							}
						});

					}
				});

		nationalityItem = new TextItem();
		nationalityItem.setTitle("ეროვნება");
		nationalityItem.setWidth(250);
		nationalityItem.setName("nationality");

		commentItem = new TextItem();
		commentItem.setTitle("შენიშვნა");
		commentItem.setWidth("100%");
		commentItem.setName("comment");

		DynamicForm commentForm = new DynamicForm();
		commentForm.setWidth100();
		commentForm.setNumCols(2);
		commentForm.setTitleWidth(131);

		commentForm.setFields(nationalityItem, commentItem);

		mainLayout.addMember(commentForm);

		setPane(mainLayout);
	}
}
