package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.LinkedHashMap;
import java.util.Map;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.TabStreetOldNames;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgAddEditStreetOldNames extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;
	private DynamicForm addDynamicForm;

	private TextItem StreetNameItem;
	private TextItem townNameItem;
	private TextItem addStreetNameItem;
	private ListGrid oldStreetNamesGrid;

	private ToolStripButton addBtn;

	private OldStreetNamesClientDS oldStreetNamesClientDS;

	private ListGridRecord editRecord;
	private ListGrid listGrid;
	private TabStreetOldNames  hstStreets;

	public DlgAddEditStreetOldNames(TabStreetOldNames  hstStreets,ListGrid listGrid, ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			this.hstStreets = hstStreets;
			setTitle(pRecord == null ? CallCenterBK.constants
					.addMobOperatorPref() : CallCenterBK.constants
					.editMobOperatorPref());

			setHeight(445);
			setWidth(350);
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
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			addDynamicForm = new DynamicForm();
			addDynamicForm.setAutoFocus(true);
			addDynamicForm.setWidth100();
			addDynamicForm.setTitleWidth(0);
			addDynamicForm.setNumCols(2);
			addDynamicForm.setTitleSuffix("");

			StreetNameItem = new TextItem();
			StreetNameItem.setTitle(CallCenterBK.constants.street());
			StreetNameItem.setWidth(250);
			StreetNameItem.setName("countryIndexValueItem");
			StreetNameItem.setCanEdit(false);

			townNameItem = new TextItem();
			townNameItem.setTitle(CallCenterBK.constants.town());
			townNameItem.setWidth(250);
			townNameItem.setName("townItem");
			townNameItem.setCanEdit(false);

			addStreetNameItem = new TextItem();
			addStreetNameItem.setWidth(190);
			addStreetNameItem.setTitle("");

			addStreetNameItem.setName("addStreetNameItem");

			HLayout hManuLayoutItem = new HLayout(0);
			hManuLayoutItem.setWidth100();
			hManuLayoutItem.setAlign(Alignment.CENTER);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			hManuLayoutItem.addMember(toolStrip);

			addBtn = new ToolStripButton("დამატება", "addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			addDynamicForm.setFields(addStreetNameItem);
			toolStrip.addMember(addDynamicForm);
			toolStrip.addButton(addBtn);

			addBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					addClientData();
				}
			});

			addStreetNameItem
					.addKeyPressHandler(new com.smartgwt.client.widgets.form.fields.events.KeyPressHandler() {

						@Override
						public void onKeyPress(
								com.smartgwt.client.widgets.form.fields.events.KeyPressEvent event) {
							if (event.getKeyName().equals("Enter")) {
								addClientData();
							}

						}
					});

			oldStreetNamesClientDS = OldStreetNamesClientDS.getInstance();

			oldStreetNamesGrid = new ListGrid();
			oldStreetNamesGrid.setWidth100();
			oldStreetNamesGrid.setHeight(250);
			oldStreetNamesGrid.setDataSource(oldStreetNamesClientDS);
			oldStreetNamesGrid.setCanReorderRecords(true);
			oldStreetNamesGrid.setCanRemoveRecords(true);
			oldStreetNamesGrid.setAutoFetchData(true);
			oldStreetNamesGrid.setCanEdit(true);
			oldStreetNamesGrid
					.setDuplicateDragMessage("ასეთი რაიონი უკვე არჩეულია !");

			dynamicForm.setFields(townNameItem, StreetNameItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			HLayout hGridLayoutItem = new HLayout(0);
			hGridLayoutItem.setWidth100();
			hGridLayoutItem.setAlign(Alignment.CENTER);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(saveItem, cancItem);
			hGridLayoutItem.setMembers(oldStreetNamesGrid);
			hLayout.addMember(hManuLayoutItem);
			hLayout.addMember(hGridLayoutItem);
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

			addItem(hLayout);

			fillFields();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private boolean checkForStretOldNamesDublicates(String oldStreetName) {
		ListGridRecord[] gridRecords = oldStreetNamesGrid.getRecords();
		for (ListGridRecord listGridRecord : gridRecords) {
			if (listGridRecord != null
					&& listGridRecord.getAttributeAsString(
							"street_old_name_descr").equals(oldStreetName)) {
				return true;
			}
		}
		return false;
	}

	private void addClientData() {
		String addOldStreetName = addStreetNameItem.getValueAsString();
		if (addOldStreetName != null && !addOldStreetName.equals("")) {
			if (checkForStretOldNamesDublicates(addOldStreetName)) {
				SC.say("გაფრთხილება", addOldStreetName + " უკვე შეყვანილია!");
				return;
			}
			Record record = new Record();
			record.setAttribute("street_old_name_descr", addOldStreetName);
			oldStreetNamesGrid.addData(record);

			addStreetNameItem.setValue("");
			addStreetNameItem.focusInItem();
		}
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			// setStreetId(editRecord.getAttributeAsString("street_id"));
			// setTownId(editRecord.getAttributeAsString("town_id"));
			StreetNameItem.setValue(editRecord
					.getAttributeAsString("street_name"));
			townNameItem.setValue(editRecord.getAttributeAsString("town_name"));

			DataSource streetOldNamesDS = DataSource.get("StreetOldNamesDS");

			Criteria criteria = new Criteria();
			criteria.setAttribute("street_id",
					editRecord.getAttributeAsInt("street_id"));

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchFromDB");

			streetOldNamesDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records == null || records.length <= 0) {
						return;
					}
					for (Record record : records) {
						oldStreetNamesGrid.addData(record);
					}
				}
			}, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("street_id",
					editRecord.getAttributeAsInt("street_id"));
			record.setAttribute("town_id",
					editRecord.getAttributeAsInt("town_id"));

			Map<String, String> streetOldNamesMap = new LinkedHashMap<String, String>();
			int i = 1;
			ListGridRecord streetOldNamesRecord[] = oldStreetNamesGrid
					.getRecords();
			if (streetOldNamesRecord != null && streetOldNamesRecord.length > 0) {
				for (ListGridRecord listGridRecord : streetOldNamesRecord) {
					streetOldNamesMap.put("" + (i++), listGridRecord
							.getAttributeAsString("street_old_name_descr"));
				}
			}
			record.setAttribute("streetOldNamesMap", streetOldNamesMap);
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateStreetOldNames");
			
			
			
			listGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					
					hstStreets.search(true);
					destroy();
				}
			}, req);

			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
