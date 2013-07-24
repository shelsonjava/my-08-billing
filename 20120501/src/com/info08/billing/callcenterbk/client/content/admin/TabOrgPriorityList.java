package com.info08.billing.callcenterbk.client.content.admin;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditOrgPriorities;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgHistOrgPriority;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ISaveResult;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabOrgPriorityList extends Tab implements ISaveResult {

	private VLayout mainLayout;

	private ListGrid lgOrgPriorityType;
	private ListGrid lgOrgPriorities;

	private ToolStripButton addPriorityBtn;
	private ToolStripButton editPriorityBtn;
	private ToolStripButton deletePriorityBtn;
	private ToolStripButton histPriorityBtn;

	public TabOrgPriorityList() {
		try {

			setTitle(CallCenterBK.constants.extraPriority());
			setCanClose(true);

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);

			toolStrip.addSeparator();
			mainLayout.addMember(toolStrip);
			lgOrgPriorityType = new ListGrid();

			lgOrgPriorityType.setWidth100();
			lgOrgPriorityType.setHeight("60%");
			lgOrgPriorityType.setAlternateRecordStyles(true);
			lgOrgPriorityType
					.setDataSource(DataSource.get("OrgPriorityTypeDS"));
			lgOrgPriorityType.setAutoFetchData(true);
			lgOrgPriorityType.setShowFilterEditor(false);
			lgOrgPriorityType.setCanEdit(false);
			lgOrgPriorityType.setCanRemoveRecords(false);
			lgOrgPriorityType.setShowRowNumbers(true);
			lgOrgPriorityType.setCanHover(true);
			lgOrgPriorityType.setShowHover(true);
			lgOrgPriorityType.setShowHoverComponents(true);
			lgOrgPriorityType.setWrapCells(true);
			lgOrgPriorityType.setFixedRecordHeights(false);
			lgOrgPriorityType.setCanDragSelectText(true);
			lgOrgPriorityType.setShowAllRecords(true);

			lgOrgPriorityType.setShowFilterEditor(true);
			lgOrgPriorityType.setFilterOnKeypress(true);

			ListGridField description = new ListGridField("description",
					CallCenterBK.constants.description(), 350);
			description.setAlign(Alignment.CENTER);
			description.setCanFilter(true);

			ListGridField operator_src = new ListGridField("operator_src",
					CallCenterBK.constants.operator(), 80);
			operator_src.setAlign(Alignment.CENTER);
			operator_src.setCanFilter(true);

			ListGridField remark = new ListGridField("remark",
					CallCenterBK.constants.comment());
			remark.setCanFilter(false);
			description.setAlign(Alignment.LEFT);

			ListGridField changer_user = new ListGridField("changer_user",
					CallCenterBK.constants.username(), 90);
			changer_user.setCanFilter(false);
			lgOrgPriorityType.setFields(operator_src, description, remark,
					changer_user);

			mainLayout.addMember(lgOrgPriorityType);

			lgOrgPriorities = new ListGrid();
			lgOrgPriorities.setWidth100();
			lgOrgPriorities.setHeight100();
			lgOrgPriorities.setAlternateRecordStyles(true);
			lgOrgPriorities.setDataSource(DataSource.get("OrgPrioritiesDS"));
			lgOrgPriorities.setAutoFetchData(false);
			lgOrgPriorities.setShowFilterEditor(false);
			lgOrgPriorities.setCanEdit(false);
			lgOrgPriorities.setCanRemoveRecords(false);
			lgOrgPriorities.setCanSort(false);
			lgOrgPriorities.setWrapCells(true);
			lgOrgPriorities.setFixedRecordHeights(false);
			lgOrgPriorities.setCanDragSelectText(true);
			lgOrgPriorities.setShowRowNumbers(true);

			ListGridField org_name = new ListGridField("org_name",
					CallCenterBK.constants.organization());
			org_name.setAlign(Alignment.LEFT);

			lgOrgPriorities.setFields(org_name);

			ClickHandler ch = new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (addPriorityBtn.equals(event.getSource()))
						addRecord();
					else if (editPriorityBtn.equals(event.getSource()))
						editRecord();
					if (deletePriorityBtn.equals(event.getSource()))
						removeRecord();
					if (histPriorityBtn.equals(event.getSource()))
						new DlgHistOrgPriority().show();

				}
			};

			addPriorityBtn = new ToolStripButton("დამატება",
					"[SKIN]/actions/add.png");
			addPriorityBtn.setLayoutAlign(Alignment.LEFT);
			addPriorityBtn.setWidth(50);
			addPriorityBtn.addClickHandler(ch);
			toolStrip.addButton(addPriorityBtn);

			editPriorityBtn = new ToolStripButton("შეცვლა",
					"[SKIN]/actions/edit.png");
			editPriorityBtn.addClickHandler(ch);
			editPriorityBtn.setLayoutAlign(Alignment.LEFT);
			editPriorityBtn.setWidth(50);
			toolStrip.addButton(editPriorityBtn);

			deletePriorityBtn = new ToolStripButton("წაშლა",
					"[SKIN]/actions/remove.png");
			deletePriorityBtn.addClickHandler(ch);
			deletePriorityBtn.setLayoutAlign(Alignment.LEFT);
			deletePriorityBtn.setWidth(50);
			toolStrip.addButton(deletePriorityBtn);

			histPriorityBtn = new ToolStripButton(
					CallCenterBK.constants.history(), "moneySmall.png");
			histPriorityBtn.addClickHandler(ch);
			histPriorityBtn.setLayoutAlign(Alignment.LEFT);
			histPriorityBtn.setWidth(50);
			toolStrip.addButton(histPriorityBtn);

			mainLayout.addMember(lgOrgPriorities);

			lgOrgPriorityType.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent recordClickEvent) {
					fetchPriorities();
				}
			});
			lgOrgPriorityType.addDoubleClickHandler(new DoubleClickHandler() {

				@Override
				public void onDoubleClick(DoubleClickEvent event) {
					editRecord();

				}
			});
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	protected void addRecord() {
		new DlgAddEditOrgPriorities(lgOrgPriorityType, null, this).show();

	}

	protected void editRecord() {
		Record record = lgOrgPriorityType.getSelectedRecord();
		if (record == null)
			return;
		new DlgAddEditOrgPriorities(lgOrgPriorityType, record, this).show();

	}

	protected void fetchPriorities() {
		Record record = lgOrgPriorityType.getSelectedRecord();
		String org_priority_type_id = "-100000";
		if (record != null)
			org_priority_type_id = record.getAttribute("org_priority_type_id")
					.toString();
		Integer iOrg_priority_type_id = null;
		try {
			iOrg_priority_type_id = Integer.parseInt(org_priority_type_id);
		} catch (Exception e) {
			iOrg_priority_type_id = -100000;
		}
		Criteria cr = new Criteria();
		cr.setAttribute("org_priority_type_id", iOrg_priority_type_id);

		cr.setAttribute("_UUUUUUUIDUUU", HTMLPanel.createUniqueId());
		lgOrgPriorities.filterData(cr);
	}

	protected void removeRecord() {
		Record rec = lgOrgPriorityType.getSelectedRecord();
		if (rec == null)
			return;
		final Record newReq = new Record();
		newReq.setAttribute("org_priority_type_id",
				rec.getAttribute("org_priority_type_id"));

		try {
			newReq.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
		} catch (CallCenterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SC.ask("დარწმუნებული ხართ რომ გინდათ წავშალოთ ჩანაწერი?",
				new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							lgOrgPriorityType.removeData(newReq,
									new DSCallback() {

										@Override
										public void execute(
												DSResponse response,
												Object rawData,
												DSRequest request) {
											fetchPriorities();

										}
									});
						}

					}
				});
	}

	@Override
	public void saved(Record record, Class<?> clazz) {
		// final Integer org_priority_type_id = record
		// .getAttributeAsInt("org_priority_type_id");
		// record =
		// lgOrgPriorityType.getRecordList().find("org_priority_type_id",
		// org_priority_type_id);
		// if (record != null) {
		// lgOrgPriorityType.selectRecord(record);
		// }
		lgOrgPriorityType.deselectAllRecords();
		fetchPriorities();
		// lgOrgPriorityType.fetchData(new Criteria("1", "1"), new DSCallback()
		// {
		//
		// @Override
		// public void execute(DSResponse response, Object rawData,
		// DSRequest request) {
		// Record record = lgOrgPriorityType.getRecordList().find(
		// "org_priority_type_id", org_priority_type_id);
		// if (record != null) {
		// lgOrgPriorityType.selectRecord(record);
		// }
		// fetchPriorities();
		// }
		// });

	}

}
