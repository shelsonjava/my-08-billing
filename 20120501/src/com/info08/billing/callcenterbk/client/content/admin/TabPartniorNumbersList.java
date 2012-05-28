package com.info08.billing.callcenterbk.client.content.admin;

import java.util.Map;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditBlackList;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class TabPartniorNumbersList extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	private SelectItem partniorPriority;
	private TextItem phoneItem;

	private IButton findButton;
	private IButton clearButton;

	private ListGrid importedListGrid;
	private DataSource partniorNumbersDS;

	private ListGridField subscriber_name_local;
	private ListGridField subscriber_address;
	private ListGridField organization_name;
	private ListGridField organization_address;

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
			searchForm.setNumCols(2);
			mainLayout.addMember(searchForm);

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setWidth(250);
			phoneItem.setName("phone");

			partniorPriority = new SelectItem();
			partniorPriority.setTitle(CallCenterBK.constants.priority());
			partniorPriority.setWidth(400);
			partniorPriority.setName("title_descr");

			ClientUtils.fillCombo(partniorPriority,
					"PartniorNumbersPriorityDS", "searchCitiesFromDBForCombos",
					"priority_id", "priority_name");
			partniorPriority.setValue(0);

			searchForm.setFields(partniorPriority, phoneItem);

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
			mainLayout.addMember(toolStrip);

			toolStrip.addSeparator();

			importedListGrid = new ListGrid();

			importedListGrid.setWidth100();
			importedListGrid.setHeight100();
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

			ListGridField phone = new ListGridField("phone_number",
					CallCenterBK.constants.phone(), 50);

			ListGridField subscriber_name = new ListGridField(
					"subscriber_name", CallCenterBK.constants.name(), 100);
			ListGridField address = new ListGridField("address",
					CallCenterBK.constants.address(), 250);

			subscriber_name_local = new ListGridField("subscriber_name_local",
					CallCenterBK.constants.abonent(), 100);

			subscriber_address = new ListGridField("subscriber_address",
					CallCenterBK.constants.abonentAddressShort(), 250);

			organization_name = new ListGridField("organization_name",
					CallCenterBK.constants.organization(), 250);

			organization_address = new ListGridField("organization_address",
					CallCenterBK.constants.orgAddressShort(), 250);

			importedListGrid.setFields(phone, subscriber_name,
					organization_name, subscriber_name_local, address,
					organization_address, subscriber_address);
			hideColumns(false, false);
			mainLayout.addMember(importedListGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			partniorPriority.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					Record record = partniorPriority.getSelectedRecord();
//					hideByRecord(record);

				}
			});

			importedListGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = importedListGrid
									.getSelectedRecord();
							new DlgAddEditBlackList(importedListGrid,
									listGridRecord);
						}
					});

			setPane(mainLayout);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	protected void hideColumns(Boolean need_subscriber,
			Boolean need_organisation) {
		if (need_subscriber) {
			importedListGrid.showField("subscriber_name_local");
			importedListGrid.showField("subscriber_address");
		} else {
			importedListGrid.hideField("subscriber_name_local");
			importedListGrid.hideField("subscriber_address");
		}
		if (need_organisation) {
			importedListGrid.showField("organization_name");
			importedListGrid.showField("organization_address");
		} else {
			importedListGrid.hideField("organization_name");
			importedListGrid.hideField("organization_address");
		}
		// subscriber_name_local.setHidden(!need_subscriber);
		// subscriber_address.setHidden(!need_subscriber);
		// organization_name.setHidden(!need_organisation);
		// organization_address.setHidden(!need_organisation);
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();

			final String phone = phoneItem.getValueAsString();
			Boolean searchByPhoneTmp = false;
			if (phone != null && !phone.equals("")) {
				criteria.setAttribute("phone", new Integer(phone));
				searchByPhoneTmp = true;
			}

			final Boolean searchByPhone = searchByPhoneTmp;
			String priority = partniorPriority.getValueAsString();
			if (priority != null && !priority.equals("")) {
				criteria.setAttribute("priority", new Integer(priority));
			}
			hideByRecord(partniorPriority.getSelectedRecord());
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "customSearch");
			importedListGrid.invalidateCache();
			importedListGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					if (searchByPhone) {
						Record[] records = response.getData();
						if (records == null || records.length == 0
								|| records.length > 1 || !searchByPhone) {
//							hideByRecord(partniorPriority.getSelectedRecord());
						} else if(searchByPhone){
							
						}
//							hideByRecord(records[0]);
					}
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	protected void hideByRecord(Record record) {
		Map<?, ?> map = record.toMap();
		System.out.println(map);
		Integer nNeed_subscriber = record.getAttributeAsInt("need_subscriber");
		Integer nNneed_organisation = record
				.getAttributeAsInt("need_organisation");

		nNeed_subscriber = nNeed_subscriber == null ? 0 : nNeed_subscriber;
		nNneed_organisation = nNneed_organisation == null ? 0
				: nNneed_organisation;
		hideColumns(nNeed_subscriber.equals(1), nNneed_organisation.equals(1));
	}
}
