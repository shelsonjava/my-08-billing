package com.info08.billing.callcenter.client.dialogs.admin;

import com.info08.billing.callcenter.client.CallCenter;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgAddEditTelComp extends Window {

	private VLayout hLayout;

	private TextItem telCompNameItem;
	private DynamicForm dynamicForm;

	private ListGridRecord editRecord;
	private ListGrid listGridIndexes;

	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	public DlgAddEditTelComp(ListGrid listGrid, ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			setTitle(pRecord == null ? CallCenter.constants.addTelComp()
					: CallCenter.constants.editTelComp());

			setHeight(600);
			setWidth(800);
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

			telCompNameItem = new TextItem();
			telCompNameItem.setTitle(CallCenter.constants.companyNameFull());
			telCompNameItem.setWidth("100%");
			telCompNameItem.setName("telCompNameItem");

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(250);
			dynamicForm.setNumCols(2);

			dynamicForm.setFields(telCompNameItem);

			hLayout.addMember(dynamicForm);

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(200);
			dynamicForm.setNumCols(4);
			hLayout.addMember(dynamicForm);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			hLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenter.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton(CallCenter.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton(CallCenter.constants.disable(),
					"deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			TelCompIndClientDS telCompIndClientDS = TelCompIndClientDS
					.getInstance();

			listGridIndexes = new ListGrid();
			listGridIndexes.setHeight100();
			listGridIndexes.setWidth100();
			listGridIndexes.setDataSource(telCompIndClientDS);
			listGridIndexes.setDataPageSize(50);
			listGridIndexes.setAutoFetchData(true);
			listGridIndexes.setSelectionType(SelectionStyle.MULTIPLE);
			listGridIndexes.setUseAllDataSourceFields(false);
			listGridIndexes.setShowFilterEditor(true);
			listGridIndexes.setFilterOnKeypress(true);
			listGridIndexes.setCanDragSelectText(true);

			ListGridField st_ind = new ListGridField("st_ind",
					CallCenter.constants.startIndex());
			st_ind.setAlign(Alignment.CENTER);

			ListGridField end_ind = new ListGridField("end_ind",
					CallCenter.constants.endIndex());
			end_ind.setAlign(Alignment.CENTER);

			ListGridField cr_descr = new ListGridField("cr_descr",
					CallCenter.constants.type());
			cr_descr.setAlign(Alignment.CENTER);

			listGridIndexes.setFields(st_ind, end_ind, cr_descr);

			hLayout.addMember(listGridIndexes);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenter.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenter.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(saveItem, cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});
			saveItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					save();
				}
			});

			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord records[] = listGridIndexes
							.getSelectedRecords();
					if (records == null || records.length <= 0) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					SC.ask(CallCenter.constants.deleteConfirm(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										for (ListGridRecord record : records) {
											listGridIndexes.removeData(record);
										}
									}
								}
							});
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditTelCompInd dlgAddEditTelCompInd = new DlgAddEditTelCompInd(
							listGridIndexes, null);
					dlgAddEditTelCompInd.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGridIndexes
							.getSelectedRecord();

					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}

					DlgAddEditTelCompInd dlgAddEditTelCompInd = new DlgAddEditTelCompInd(
							listGridIndexes, listGridRecord);
					dlgAddEditTelCompInd.show();
				}
			});

			listGridIndexes
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = listGridIndexes
									.getSelectedRecord();

							DlgAddEditTelCompInd dlgAddEditTelCompInd = new DlgAddEditTelCompInd(
									listGridIndexes, listGridRecord);
							dlgAddEditTelCompInd.show();
						}
					});

			addItem(hLayout);
			fillFields();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			telCompNameItem.setValue(editRecord
					.getAttributeAsString("tel_comp_name_geo"));

			DataSource telCompIndDS = DataSource.get("TelCompIndDS");
			Criteria criteria = new Criteria();
			criteria.setAttribute("tel_comp_id",
					editRecord.getAttributeAsInt("tel_comp_id"));
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchAllTelCompInds");
			telCompIndDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					if (records != null && records.length > 0) {
						for (Record record : records) {
							listGridIndexes.addData(record);
						}
					}
				}
			}, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
