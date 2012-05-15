package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditUserNew;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class TabAdmin extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem firstNameItem;
	private TextItem lastNameItem;
	private TextItem userNameItem;
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addPersonBtn;
	private ToolStripButton editPersonBtn;
	private ToolStripButton deletePersonBtn;
	private ToolStripButton exportButton;
	private ListGrid usersGrid;
	private DataSource usersDS;

	public TabAdmin() {
		try {

			setTitle("სისტემის მომხმარებლების მართვა");
			setCanClose(true);

			usersDS = DataSource.get("UsersDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			//
			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(835);
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(4);
			mainLayout.addMember(searchForm);

			firstNameItem = new TextItem();
			firstNameItem.setTitle("სახელი");
			firstNameItem.setWidth(250);
			firstNameItem.setName("user_firstname");

			lastNameItem = new TextItem();
			lastNameItem.setTitle("გვარი");
			lastNameItem.setWidth(250);
			lastNameItem.setName("user_lastname");

			userNameItem = new TextItem();
			userNameItem.setTitle("მომხმარებელი");
			userNameItem.setWidth(250);
			userNameItem.setName("userName");

			searchForm.setFields(firstNameItem, lastNameItem, userNameItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(835);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(835);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addPersonBtn = new ToolStripButton("დამატება", "person_add.png");
			addPersonBtn.setLayoutAlign(Alignment.LEFT);
			addPersonBtn.setWidth(50);
			toolStrip.addButton(addPersonBtn);

			editPersonBtn = new ToolStripButton("შეცვლა", "person_edit.png");
			editPersonBtn.setLayoutAlign(Alignment.LEFT);
			editPersonBtn.setWidth(50);
			toolStrip.addButton(editPersonBtn);

			deletePersonBtn = new ToolStripButton("გაუქმება",
					"person_delete.png");
			deletePersonBtn.setLayoutAlign(Alignment.LEFT);
			deletePersonBtn.setWidth(50);
			toolStrip.addButton(deletePersonBtn);

			// toolStrip.addSeparator();

			exportButton = new ToolStripButton("Excel - ში გადატანა",
					"excel.gif");
			exportButton.setLayoutAlign(Alignment.LEFT);
			exportButton.setWidth(50);
			// toolStrip.addButton(exportButton);

			usersGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}

					return super.getCellCSSText(record, rowNum, colNum);

				};
			};

			usersGrid.setWidth(835);
			usersGrid.setHeight(280);
			usersGrid.setAlternateRecordStyles(true);
			usersGrid.setDataSource(usersDS);
			usersGrid.setAutoFetchData(false);
			usersGrid.setShowFilterEditor(false);
			usersGrid.setCanEdit(false);
			usersGrid.setCanRemoveRecords(false);
			usersGrid.setFetchOperation("searchUser");
			usersGrid.setShowRowNumbers(true);
			usersGrid.setCanHover(true);
			usersGrid.setShowHover(true);
			usersGrid.setShowHoverComponents(true);

			usersDS.getField("user_firstname").setTitle("სახელი");
			usersDS.getField("user_lastname").setTitle("გვარი");
			usersDS.getField("user_name").setTitle("მომხმარებელი");
			usersDS.getField("user_password").setTitle("პაროლი");
			usersDS.getField("department_name").setTitle("განყოფილება");

			ListGridField user_firstname = new ListGridField("user_firstname",
					"სახელი", 150);
			ListGridField user_lastname = new ListGridField("user_lastname",
					"გვარი", 150);
			ListGridField user_name = new ListGridField("user_name",
					"მომხმარებელი", 150);
			ListGridField user_password = new ListGridField("user_password",
					"პაროლი", 70);
			ListGridField department_name = new ListGridField(
					"department_name", "განყოფილება", 150);

			usersGrid.setFields(user_firstname, user_lastname, department_name,
					user_name, user_password);

			mainLayout.addMember(usersGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Criteria criteria = usersGrid.getCriteria();
					if (criteria == null) {
						criteria = new Criteria();
					}
					Criteria formCriteria = searchForm.getValuesAsCriteria();
					criteria.addCriteria(formCriteria);

					DSRequest dsRequest = new DSRequest();
					dsRequest
							.setAttribute("operationId", "searchAllUserFromDB");
					usersGrid.invalidateCache();
					usersGrid.filterData(criteria, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
						}
					}, dsRequest);
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchForm.clearValues();
					DSRequest dsRequest = new DSRequest();
					dsRequest
							.setAttribute("operationId", "searchAllUserFromDB");
					usersGrid.clearCriteria(new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
						}
					}, dsRequest);
				}
			});

			addPersonBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditUserNew addEditUserNew = new DlgAddEditUserNew(
							usersDS, null);
					addEditUserNew.show();
				}
			});

			final Long loggedPersonnelId = CommonSingleton.getInstance()
					.getSessionPerson().getUser_id();

			editPersonBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = usersGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					Integer user_id = listGridRecord.getAttributeAsInt("user_id");
					if (!loggedPersonnelId.equals(new Long(user_id))
							&& user_id.intValue() == 215) {
						SC.say("მოდიფიკაცია შეუძლებელია. ეს სისტემური მომხმარებელია !");
						return;
					}

					DlgAddEditUserNew addEditUserNew = new DlgAddEditUserNew(
							usersDS, listGridRecord);
					addEditUserNew.show();
				}
			});
			deletePersonBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = usersGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					final Integer user_id = listGridRecord
							.getAttributeAsInt("user_id");
					if (user_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}
					if (!loggedPersonnelId.equals(new Long(user_id))
							&& user_id.intValue() == 215) {
						SC.say("მოდიფიკაცია შეუძლებელია. ეს სისტემური მომხმარებელია !");
						return;
					}
					SC.ask("დარწმუნებული ხართ რომ გნებავთ მომხმარებლის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										deleteUser(user_id);
									}
								}
							});
				}
			});

			exportButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					com.smartgwt.client.data.DSRequest dsRequestProperties = new com.smartgwt.client.data.DSRequest();
					dsRequestProperties.setExportAs((ExportFormat) EnumUtil
							.getEnum(ExportFormat.values(), "xls"));
					dsRequestProperties
							.setExportDisplay(ExportDisplay.DOWNLOAD);
					dsRequestProperties.setOperationId("searchAllUser");
					usersGrid.exportData(dsRequestProperties);
				}
			});
			TabSet tabSet = new TabSet();
			tabSet.setWidth(835);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(usersDS);
			detailViewer.setWidth(800);
			tabDetViewer.setPane(detailViewer);

			usersGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(usersGrid);
				}
			});
			usersGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = usersGrid
									.getSelectedRecord();
							if (listGridRecord == null) {
								SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
								return;
							}
							Integer user_id = listGridRecord
									.getAttributeAsInt("user_id");
							if (!loggedPersonnelId.equals(new Long(user_id))
									&& user_id.intValue() == 215) {
								SC.say("მოდიფიკაცია შეუძლებელია. ეს სისტემური მომხმარებელია !");
								return;
							}

							DlgAddEditUserNew addEditUserNew = new DlgAddEditUserNew(
									usersDS, listGridRecord);
							addEditUserNew.show();
						}
					});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void deleteUser(Integer user_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("user_id", user_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("aaaaaa", "bbbbbbbbbbbbbbbb");

			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "deleteUser");

			usersGrid.removeData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
