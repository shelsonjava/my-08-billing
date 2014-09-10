package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.TabOrganization;
import com.info08.billing.callcenterbk.client.dialogs.correction.DlgAddEditSubscriber;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.ui.layout.Body;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.client.utils.ISaveResult;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabPartniorNumbersList extends Tab implements ISaveResult {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	private SelectItem partniorPriority;
	private TextItem phoneItem;

	private IButton findButton;
	private IButton clearButton;

	private ListGrid importedListGrid;
	private DataSource partniorNumbersDS;

	private ListGridField has_subscriber;

	private TabOrganization tabOrgs;

	private ListGridField has_organisation;
	private ListGridField org_department_count;

	private ListGrid lgPhones;

	private ToolStripButton addPersonBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deletePersonBtn;
	private ToolStripButton approveNmber;

	public TabPartniorNumbersList() {
		try {

			setTitle(CallCenterBK.constants.importedNumbers());
			setCanClose(true);

			partniorNumbersDS = DataSource.get("PartniorNumbersDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(830);
			searchForm.setTitleWidth(250);
			// searchForm.setNumCols(4);
			mainLayout.addMember(searchForm);

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setWidth(250);
			phoneItem.setName("phone");

			partniorPriority = new SelectItem();
			partniorPriority.setTitle(CallCenterBK.constants.priority());
			partniorPriority.setWidth(400);
			partniorPriority.setName("title_descr");
			// partniorPriority.setColSpan(2);

			ClientUtils.fillCombo(partniorPriority,
					"PartniorNumbersPriorityDS", "searchCitiesFromDBForCombos",
					"priority_id", "priority_name");
			partniorPriority.setValue(0);

			StaticTextItem itemCount = new StaticTextItem("itemCount",
					CallCenterBK.constants.count());
			itemCount.setValue(0);
			searchForm.setFields(partniorPriority, phoneItem, itemCount);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(830);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);

			toolStrip.addSeparator();

			importedListGrid = new ListGrid();

			importedListGrid.setWidth100();
			importedListGrid.setHeight("60%");
			importedListGrid.setAlternateRecordStyles(true);
			importedListGrid.setDataSource(partniorNumbersDS);
			importedListGrid.setAutoFetchData(false);
			importedListGrid.setShowFilterEditor(false);
			importedListGrid.setCanEdit(false);
			importedListGrid.setCanRemoveRecords(false);
			importedListGrid.setFetchOperation("customSearch");
			importedListGrid.setShowRowNumbers(true);
			importedListGrid.setCanHover(true);
			importedListGrid.setShowHover(true);
			importedListGrid.setShowHoverComponents(true);
			importedListGrid.setWrapCells(true);
			importedListGrid.setFixedRecordHeights(false);
			importedListGrid.setCanDragSelectText(true);
			importedListGrid.setShowFilterEditor(true);
			importedListGrid.setFilterOnKeypress(true);

			ListGridField phone = new ListGridField("phone_number", CallCenterBK.constants.phone(), 70);
			phone.setAlign(Alignment.CENTER);
			phone.setCanFilter(true);

			ListGridField subscriber_name = new ListGridField("subscriber_name", CallCenterBK.constants.name(), 350);
			subscriber_name.setCanFilter(true);

			ListGridField address = new ListGridField("address",CallCenterBK.constants.address(), 450);
			address.setCanFilter(true);

			has_subscriber = new ListGridField("has_subscriber",CallCenterBK.constants.abonent(), 50);
			has_subscriber.setCanFilter(false);			
			has_subscriber.setType(ListGridFieldType.BOOLEAN);
			

			has_organisation = new ListGridField("has_organisation",CallCenterBK.constants.organization(), 50);
			has_organisation.setType(ListGridFieldType.BOOLEAN);
			has_organisation.setCanFilter(false);

			org_department_count = new ListGridField("org_department_count", CallCenterBK.constants.count(), 50);
			org_department_count.setType(ListGridFieldType.INTEGER);
			org_department_count.setCanFilter(false);

			importedListGrid.setFields(phone, subscriber_name, address,
					has_subscriber, has_organisation, org_department_count);

			mainLayout.addMember(importedListGrid);

			lgPhones = new ListGrid();
			lgPhones.setWidth100();
			lgPhones.setHeight100();
			lgPhones.setAlternateRecordStyles(true);
			lgPhones.setDataSource(DataSource.get("PhoneViewDS"));
			lgPhones.setAutoFetchData(false);
			lgPhones.setShowFilterEditor(false);
			lgPhones.setCanEdit(false);
			lgPhones.setCanRemoveRecords(false);
			lgPhones.setFetchOperation("customSearch");
			lgPhones.setCanSort(false);
			// lgPhones.setCanResizeFields(false);
			lgPhones.setWrapCells(true);
			lgPhones.setFixedRecordHeights(false);
			lgPhones.setCanDragSelectText(true);
			lgPhones.setShowRowNumbers(true);

			ListGridField full_name = new ListGridField("full_name",
					CallCenterBK.constants.dasaxeleba(), 350);
			full_name.setAlign(Alignment.LEFT);

			ListGridField owner_type = new ListGridField("owner_type_descr",
					CallCenterBK.constants.type(), 70);
			owner_type.setAlign(Alignment.CENTER);

			ListGridField concat_address = new ListGridField("real_address",
					CallCenterBK.constants.street(), 450);
			address.setAlign(Alignment.LEFT);

			ListGridField done = new ListGridField("proceeded", "D", 50);
			done.setType(ListGridFieldType.BOOLEAN);

			lgPhones.setFields(owner_type, full_name, concat_address, done);

			ClickHandler ch = new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (addPersonBtn.equals(event.getSource()))
						manageSubscriber(null);
					else if (editBtn.equals(event.getSource()))
						editRecord();
					if (approveNmber.equals(event.getSource()))
						removeRecord();

				}
			};

			addPersonBtn = new ToolStripButton("აბონენტის  დამატება",
					"person_add.png");
			addPersonBtn.setLayoutAlign(Alignment.LEFT);
			addPersonBtn.setWidth(50);
			addPersonBtn.addClickHandler(ch);
			toolStrip.addButton(addPersonBtn);

			editBtn = new ToolStripButton("შეცვლა", "person_edit.png");
			editBtn.addClickHandler(ch);
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deletePersonBtn = new ToolStripButton("წაშლა", "person_delete.png");
			deletePersonBtn.addClickHandler(ch);
			deletePersonBtn.setLayoutAlign(Alignment.LEFT);
			deletePersonBtn.setWidth(50);
			toolStrip.addButton(deletePersonBtn);

			approveNmber = new ToolStripButton("დადასტურება",
					"[SKIN]/actions/approve.png");
			approveNmber.addClickHandler(ch);
			approveNmber.setLayoutAlign(Alignment.LEFT);
			approveNmber.setWidth(50);
			toolStrip.addButton(approveNmber);

			mainLayout.addMember(toolStrip);
			mainLayout.addMember(lgPhones);

			importedListGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent recordClickEvent) {
					searchPhones();
				}
			});
			lgPhones.addDoubleClickHandler(new DoubleClickHandler() {

				@Override
				public void onDoubleClick(DoubleClickEvent event) {
					editRecord();

				}
			});

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	protected void removeRecord() {
		Record rec = importedListGrid.getSelectedRecord();
		final Record newReq = new Record();
		newReq.setAttribute("phone_number", rec.getAttribute("phone_number"));

		try {
			newReq.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
		} catch (CallCenterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SC.ask("დარწმუნებული ხართ რომ გინდათ დაადასტუროთ ცვლილებები?",
				new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							DSRequest dsRequest = new DSRequest();
							dsRequest.setAttribute("operationId",
									"deletePartniorNumbers");
							importedListGrid.removeData(newReq,
									new DSCallback() {

										@Override
										public void execute(
												DSResponse response,
												Object rawData,
												DSRequest request) {

											searchPhones();

										}
									}, dsRequest);
						}

					}
				});
	}

	protected void searchPhones() {
		Record rec = importedListGrid.getSelectedRecord();
		Criteria criteria = new Criteria();
		criteria.setAttribute("phone", rec.getAttribute("phone_number"));
		criteria.setAttribute("reall_address", "1");
		criteria.setAttribute("subscriber_proceeded",
				rec.getAttribute("subscriber_proceeded"));
		criteria.setAttribute("organization_proceeded",
				rec.getAttribute("organization_proceeded"));
		lgPhones.fetchData(criteria);

	}

	protected void manageDepartment() {
		// TODO Auto-generated method stub

	}

	protected void manageSubscriber(Integer subscriber_id) {
		Record rec = importedListGrid.getSelectedRecord();
		String phone_number = rec.getAttribute("phone_number");

		boolean add = subscriber_id == null;
		if (add) {
			DlgAddEditSubscriber dlg = new DlgAddEditSubscriber(null, null,
					phone_number, this);
			dlg.setCanDrag(true);
			dlg.setCanDragReposition(true);
			return;
		}
		if (subscriber_id != null) {
			DataSource subscriberDS = DataSource.get("SubscriberDS");
			DSRequest req = new DSRequest();
			req.setOperationId("customSearch");
			Criteria cr = new Criteria();
			cr.setAttribute("subscriber_id", subscriber_id);
			subscriberDS.fetchData(cr, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					if (records != null && records.length == 1) {
						DlgAddEditSubscriber dlg = new DlgAddEditSubscriber(
								records[0], null, null,
								TabPartniorNumbersList.this);
						dlg.setCanDrag(true);
						dlg.setCanDragReposition(true);
						dlg.show();
					}
				}
			}, req);
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();

			final String phone = phoneItem.getValueAsString();
			if (phone != null && !phone.equals("")) {
				criteria.setAttribute("phone", new Integer(phone));
			}

			String priority = partniorPriority.getValueAsString();
			if (priority != null && !priority.equals("")) {
				criteria.setAttribute("priority", new Integer(priority));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "customSearch");
			importedListGrid.invalidateCache();
			importedListGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					if (response != null)
						searchForm.getField("itemCount").setValue(
								response.getTotalRows());
					else
						searchForm.getField("itemCount").setValue(-1);
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	@Override
	public void saved(Record record, Class<?> clazz) {
		Record rec = importedListGrid.getSelectedRecord();
		Record newReq = new Record();
		newReq.setAttribute("phone_number", rec.getAttribute("phone_number"));
		if (clazz.equals(DlgAddEditSubscriber.class)) {
			{
				newReq.setAttribute("subscriber_proceeded",
						record.getAttribute("subscriber_id"));
				try {
					newReq.setAttribute("loggedUserName", CommonSingleton
							.getInstance().getSessionPerson().getUser_name());
				} catch (CallCenterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(rec.toMap());
				DSRequest dsRequest = new DSRequest();
				dsRequest.setAttribute("operationId", "updatePartniorNumbers");
				importedListGrid.updateData(newReq, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						importedListGrid.selectRecord(response.getData()[0]);
						searchPhones();

					}
				}, dsRequest);
			}

		}
	}

	protected void editRecord() {
		Record rec = lgPhones.getSelectedRecord();
		String owner_type = rec.getAttribute("owner_type");
		String owner_id = rec.getAttribute("owner_id");

		if (owner_type.equals("0")) {
			manageSubscriber(owner_id == null ? null : new Integer(owner_id));
		} else {

			Record rec1 = importedListGrid.getSelectedRecord();
			String phone = rec1.getAttribute("phone_number");
			if (tabOrgs == null) {
				tabOrgs = new TabOrganization();
				Body.getInstance().addTab(tabOrgs);
			}

			try {
				tabOrgs.filterByDepartmentIdAndPhone(new Long(owner_id), phone);
			} catch (Exception e) {
				tabOrgs = new TabOrganization();
				Body.getInstance().addTab(tabOrgs);
				tabOrgs.filterByDepartmentIdAndPhone(new Long(owner_id), phone);
			}

			String tabId = tabOrgs.getID();
			Body.getInstance().activateTab(tabId);
		}
	}

}
