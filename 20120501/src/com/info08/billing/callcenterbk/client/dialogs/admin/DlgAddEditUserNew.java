package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.LinkedHashMap;
import java.util.Map;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditUserNew extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private TextItem firstNameItem;
	private TextItem lastNameItem;
	private TextItem userNameItem;
	private TextItem passwordItem;
	private ComboBoxItem deputyItem;
	private ListGrid permissionsGrid;
	private ListGrid userPermissionsGrid;
	private DataSource permisssionsDS;
	private UserPermissinsClientDS userPermissinsClientDS;
	private DataSource userDS;
	private ListGridRecord userRecord;

	public DlgAddEditUserNew(DataSource userDS, ListGridRecord userRecord) {
		this.userDS = userDS;
		this.userRecord = userRecord;
		setTitle(userRecord == null ? "ახალი მომხმარებლის დამატება"
				: "მომხმარებლის მოდიფიცირება");
		setHeight(560);
		setWidth(700);
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

		dynamicForm = new DynamicForm();
		dynamicForm.setAutoFocus(true);
		dynamicForm.setWidth100();
		dynamicForm.setTitleWidth(200);
		dynamicForm.setNumCols(4);
		hLayout.addMember(dynamicForm);

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
		userNameItem.setName("userName");

		passwordItem = new TextItem();
		passwordItem.setTitle("პაროლი");
		passwordItem.setWidth(250);
		passwordItem.setName("user_password");

		deputyItem = new ComboBoxItem();
		deputyItem.setWidth(250);
		deputyItem.setTitle("განყოფილება");
		DataSource DepartmentDS = DataSource.get("DepartmentDS");
		deputyItem.setOptionOperationId("searchDepartments");
		deputyItem.setOptionDataSource(DepartmentDS);
		deputyItem.setValueField("department_id");
		deputyItem.setDisplayField("department_name");
		deputyItem.setAutoFetchData(true);

		dynamicForm.setFields(firstNameItem, lastNameItem, userNameItem,
				passwordItem, deputyItem);

		permisssionsDS = DataSource.get("PermisssionsDS");

		permisssionsDS.getField("permission_name").setTitle("უფლებების ჩამონათვალი");
		permissionsGrid = new ListGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord countryRecord = (ListGridRecord) record;
				if (countryRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				Integer is_permission_group = countryRecord
						.getAttributeAsInt("is_permission_group");
				if (is_permission_group != null && is_permission_group.equals(1)) {
					return "font-weight:bold;";
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};
		};
		permissionsGrid.setWidth(300);
		permissionsGrid.setHeight(400);
		permissionsGrid.setDataSource(permisssionsDS);
		permissionsGrid.setCanDragRecordsOut(true);
		permissionsGrid.setDragDataAction(DragDataAction.COPY);
		permissionsGrid.setAlternateRecordStyles(true);
		permissionsGrid.setAutoFetchData(true);
		permissionsGrid.setUseAllDataSourceFields(true);
		permissionsGrid.setFetchOperation("searchUser");

		userPermissinsClientDS = UserPermissinsClientDS.getInstance();

		userPermissionsGrid = new ListGrid() {
			protected String getCellCSSText(ListGridRecord record, int rowNum,
					int colNum) {
				ListGridRecord countryRecord = (ListGridRecord) record;
				if (countryRecord == null) {
					return super.getCellCSSText(record, rowNum, colNum);
				}
				Integer is_permission_group = countryRecord
						.getAttributeAsInt("is_permission_group");
				if (is_permission_group != null && is_permission_group.equals(1)) {
					return "font-weight:bold;";
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};
		};
		userPermissionsGrid.setWidth(300);
		userPermissionsGrid.setHeight(400);
		userPermissionsGrid.setDataSource(userPermissinsClientDS);
		userPermissionsGrid.setCanAcceptDroppedRecords(true);
		userPermissionsGrid.setCanRemoveRecords(true);
		userPermissionsGrid.setAutoFetchData(true);
		userPermissionsGrid.setPreventDuplicates(true);
		userPermissionsGrid
				.setDuplicateDragMessage("ასეთი უფლება უკვე არჩეულია !");

		Img arrowImg = new Img("arrow_right.png", 32, 32);
		arrowImg.setLayoutAlign(Alignment.CENTER);
		arrowImg.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				userPermissionsGrid.transferSelectedData(permissionsGrid);
			}
		});
		permissionsGrid
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						userPermissionsGrid
								.transferSelectedData(permissionsGrid);
					}
				});

		HLayout gridsLayout = new HLayout(0);
		gridsLayout.setHeight(400);

		HLayout hLayoutImg = new HLayout();
		hLayoutImg.setAlign(Alignment.CENTER);
		hLayoutImg.addMember(arrowImg);

		gridsLayout
				.setMembers(permissionsGrid, hLayoutImg, userPermissionsGrid);

		hLayout.addMember(gridsLayout);

		HLayout buttonsLayout = new HLayout(5);
		buttonsLayout.setWidth100();
		buttonsLayout.setAlign(Alignment.RIGHT);

		IButton saveItem = new IButton();
		saveItem.setTitle("შენახვა");
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle("დახურვა");
		cancItem.setWidth(100);

		buttonsLayout.setMembers(saveItem, cancItem);

		hLayout.addMember(buttonsLayout);

		addItem(hLayout);

		cancItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});

		saveItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveUser();
			}
		});

		fillFields();
	}

	private void fillFields() {
		try {
			if (userRecord == null) {
				return;
			}
			firstNameItem.setValue(userRecord.getAttribute("user_firstname"));
			lastNameItem.setValue(userRecord.getAttribute("user_lastname"));
			userNameItem.setValue(userRecord.getAttribute("user_name"));
			passwordItem.setValue(userRecord.getAttribute("user_password"));
			deputyItem.setValue(userRecord.getAttribute("department_id"));

			DataSource userPermsDS = DataSource.get("PermisssionsDS");

			Criteria criteria = new Criteria();
			criteria.setAttribute("user_id",
					userRecord.getAttributeAsInt("user_id"));

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "userPermissionSearch");

			userPermsDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records == null || records.length <= 0) {
						return;
					}
					for (Record record : records) {
						userPermissionsGrid.addData(record);
					}
				}
			}, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void saveUser() {
		try {
			String firstName = firstNameItem.getValueAsString();
			if (firstName == null || firstName.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ სახელი !");
				return;
			}
			String lastName = lastNameItem.getValueAsString();
			if (lastName == null || lastName.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ გვარი !");
				return;
			}
			String userName = userNameItem.getValueAsString();
			if (userName == null || userName.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ მომხმარებელი !");
				return;
			}
			String user_password = passwordItem.getValueAsString();
			if (user_password == null
					|| user_password.trim().equalsIgnoreCase("")) {
				SC.say("შეიყვანეთ პაროლი !");
				return;
			}
			ListGridRecord depRecord = deputyItem.getSelectedRecord();
			if (depRecord == null
					|| depRecord.getAttributeAsInt("department_id") == null) {
				SC.say("გთხოვთ აირჩიოთ განყოფილება !");
				return;
			}
			Integer department_id = depRecord
					.getAttributeAsInt("department_id");

			if (firstName.length() > 100) {
				SC.say("სახელი შედგება მაქსიმუმ 100 სიმბოლოსაგან !");
				return;
			}
			if (lastName.length() > 150) {
				SC.say("გვარი შედგება მაქსიმუმ 150 სიმბოლოსაგან !");
				return;
			}
			if (userName.length() > 30) {
				SC.say("მომხმარებლის სახელი შედგება მაქსიმუმ 30 სიმბოლოსაგან !");
				return;
			}
			if (user_password.length() > 6) {
				SC.say("პაროლი შედგება მაქსიმუმ 6 სიმბოლოსაგან !");
				return;
			}

			Map<String, String> userPerms = new LinkedHashMap<String, String>();
			ListGridRecord phoneRecors[] = userPermissionsGrid.getRecords();
			if (phoneRecors != null && phoneRecors.length > 0) {
				for (ListGridRecord listGridRecord : phoneRecors) {
					Integer permission_id = listGridRecord
							.getAttributeAsInt("permission_id");
					userPerms.put(permission_id + "", "1");
				}
			}
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("userPerms", userPerms);
			record.setAttribute("user_firstname", firstName);
			record.setAttribute("user_lastname", lastName);
			record.setAttribute("user_name", userName);
			record.setAttribute("user_password", user_password);
			record.setAttribute("department_id", department_id);

			if (userRecord != null) {
				record.setAttribute("user_id",
						userRecord.getAttributeAsInt("user_id"));
			}

			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();
			if (userRecord == null) {
				req.setAttribute("operationId", "addUser");
				userDS.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateUser");
				userDS.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			}
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
