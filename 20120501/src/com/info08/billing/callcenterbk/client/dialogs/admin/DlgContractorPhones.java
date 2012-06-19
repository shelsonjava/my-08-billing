package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.ArrayList;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgContractorPhones extends Window {

	private IButton okButton;
	private IButton closeButton;

	private ListGrid orgPhonesGrid;
	private ListGrid contractPhonesGrid;

	private String findPhone = null;
	private int lastIndex = 0;

	public DlgContractorPhones() {
		try {
			setTitle(CallCenterBK.constants.advParameters());
			setHeight(680);
			setWidth(800);
			setShowMinimizeButton(false);
			setIsModal(true);
			setShowModalMask(true);
			setCanDrag(false);
			setCanDragReposition(false);
			setCanDragResize(false);
			setCanDragScroll(false);
			centerInPage();

			VLayout mainLayout = new VLayout(5);
			mainLayout.setMargin(2);
			mainLayout.setWidth100();
			mainLayout.setHeight100();

			HLayout hLayout = new HLayout();
			hLayout.setMembersMargin(10);
			hLayout.setWidth100();
			hLayout.setHeight100();
			mainLayout.addMember(hLayout);

			orgPhonesGrid = new ListGrid() {
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					if (record == null)
						return super.getCellCSSText(record, rowNum, colNum);
					String field = orgPhonesGrid.getField(colNum).getName();
					if ("name".equals(field)) {
						String tp = record.getAttribute("pr");
						if ("1".equals(tp)) {
							return "font-weight:bold;";
						} else if ("2".equals(tp)) {
							return "color:blue;";
						} else if ("3".equals(tp)) {
							String cp_id = record.getAttribute("cp_id");
							if (cp_id != null)
								return "color:red;";
						}
					}
					return super.getCellCSSText(record, rowNum, colNum);
				}
			};

			DataSource dsSource = new DataSource();
			DataSourceField dsName = new DataSourceField("name", FieldType.TEXT);
			DataSourceField dsId = new DataSourceField("rid", FieldType.TEXT);
			dsId.setPrimaryKey(true);
			dsSource.setFields(dsName, dsId);
			dsSource.setClientOnly(true);
			orgPhonesGrid.setDataSource(dsSource);

			orgPhonesGrid.setWidth("83%");
			orgPhonesGrid.setHeight100();
			orgPhonesGrid.setAlternateRecordStyles(true);
			orgPhonesGrid.setAutoFetchData(false);
			orgPhonesGrid.setShowFilterEditor(true);
			orgPhonesGrid.setFilterOnKeypress(true);

			ListGridField name = new ListGridField("name",
					CallCenterBK.constants.name());
			name.setAlign(Alignment.LEFT);

			ListGridField add = new ListGridField("add", "", 20);
			add.setAlign(Alignment.LEFT);
			add.setType(ListGridFieldType.ICON);
			add.setCellIcon("restore.png");
			add.setCanEdit(false);
			add.setCanFilter(true);
			add.setFilterEditorType(new SpacerItem());
			add.setCanGroupBy(false);
			add.setCanSort(false);

			orgPhonesGrid.addCellClickHandler(new CellClickHandler() {

				@Override
				public void onCellClick(CellClickEvent event) {
					String field_name = orgPhonesGrid.getField(
							event.getColNum()).getName();
					if (field_name.equals("add"))
						addPhones(event.getRecord());
				}
			});

			orgPhonesGrid.setFields(name, add);

			contractPhonesGrid = new ListGrid();
			contractPhonesGrid.setWidth("17%");
			contractPhonesGrid.setHeight100();
			contractPhonesGrid.setPreventDuplicates(true);
			contractPhonesGrid.setDuplicateDragMessage(CallCenterBK.constants
					.thisOrgActAlreadyChoosen());

			ListGridField phone = new ListGridField("phone",
					CallCenterBK.constants.phone());
			phone.setAlign(Alignment.LEFT);

			ListGridField remove = new ListGridField("remove", "", 20);
			remove.setAlign(Alignment.LEFT);
			remove.setType(ListGridFieldType.ICON);
			remove.setCellIcon("[SKINIMG]/actions/remove.png");
			remove.setCanEdit(false);
			remove.setCanFilter(true);
			remove.setFilterEditorType(new SpacerItem());
			remove.setCanGroupBy(false);
			remove.setCanSort(false);

			DataSource dsDest = new DataSource();
			DataSourceField dsPhone = new DataSourceField("phone",
					FieldType.TEXT);
			dsPhone.setPrimaryKey(true);
			dsDest.setFields(dsPhone);
			dsDest.setClientOnly(true);

			contractPhonesGrid.setDataSource(dsDest);
			contractPhonesGrid.setFields(phone, remove);
			contractPhonesGrid.setShowFilterEditor(true);
			contractPhonesGrid.setFilterOnKeypress(true);
			contractPhonesGrid.addCellClickHandler(new CellClickHandler() {

				@Override
				public void onCellClick(CellClickEvent event) {
					String field_name = contractPhonesGrid.getField(
							event.getColNum()).getName();
					if (field_name.equals("remove")) {
						removePhone(event.getRecord().getAttribute("phone"));
						orgPhonesGrid.redraw();
					}
				}
			});
			DataSource ds = DataSource.get("ContractorsPhonesDS");
			Criteria cr = new Criteria();
			DSRequest req = new DSRequest();
			req.setOperationId("organisationPhones");
			cr.setAttribute("organization_id", 80353);
			cr.setAttribute("contract_id", 5);
			ds.fetchData(cr, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					if (response.getData() != null) {
						DataSource ds = orgPhonesGrid.getDataSource();
						for (Record record : response.getData()) {
							ds.addData(record);
						}
					}
					orgPhonesGrid.fetchData();
				}
			}, req);

			contractPhonesGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							findNext(event.getRecord());

						}
					});
			HLayout gridsLayout = new HLayout();
			gridsLayout.setMargin(2);
			gridsLayout.setHeight100();
			gridsLayout.setWidth100();

			gridsLayout.setMembers(orgPhonesGrid, contractPhonesGrid);
			hLayout.addMember(gridsLayout);

			HLayout buttonsLayout = new HLayout();
			buttonsLayout.setMargin(4);
			buttonsLayout.setWidth100();
			buttonsLayout.setAlign(Alignment.RIGHT);
			buttonsLayout.setMembersMargin(5);
			buttonsLayout.setHeight(50);
			mainLayout.addMember(buttonsLayout);

			okButton = new IButton();
			okButton.setTitle(CallCenterBK.constants.save());

			closeButton = new IButton();
			closeButton.setTitle(CallCenterBK.constants.close());

			buttonsLayout.setMembers(okButton, closeButton);

			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});
			addCloseClickHandler(new CloseClickHandler() {
				@Override
				public void onCloseClick(CloseClickEvent event) {
				}
			});
			addItem(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	protected void findNext(Record record) {
		if (record == null)
			return;
		String phone = record.getAttribute("phone");
		if (phone == null)
			return;
		if (!phone.equals(findPhone))
			lastIndex = -1;
		else
			lastIndex++;
		findPhone = phone;
		lastIndex = orgPhonesGrid.getRecordList().findNextIndex(lastIndex,
				"phone", phone, orgPhonesGrid.getRecordList().getLength());
		orgPhonesGrid.deselectAllRecords();
		orgPhonesGrid.selectRecord(lastIndex);
		Integer[] rowVisible = orgPhonesGrid.getVisibleRows();
		if (!(rowVisible[0] + 3 < lastIndex && rowVisible[1] - 5 > lastIndex))
			orgPhonesGrid.scrollToRow(lastIndex - 3);
	}

	private void findAllPhonesByID(String id, ArrayList<String> phones) {
		Record record = orgPhonesGrid.getRecordList().find("id", id);
		if (record == null)
			return;
		String tp = record.getAttribute("pr");
		if (tp == null)
			return;
		if ("3".equals(tp)) {
			String phone = record.getAttribute("phone");
			if (phone != null)
				phones.add(phone);
			return;
		}
		int len = orgPhonesGrid.getRecordList().getLength();
		int index = orgPhonesGrid.getRecordList().indexOf(record);
		for (int i = index + 1; i < len; i++) {
			record = orgPhonesGrid.getRecordList().get(i);
			if (record == null)
				continue;
			String pid = record.getAttribute("pid");
			if (pid == null)
				continue;
			if (pid.equals(id)) {
				String nid = record.getAttribute("id");
				if (nid == null)
					continue;
				findAllPhonesByID(nid, phones);
			}
		}

	}

	private ArrayList<String> getAllPhones() {
		ArrayList<String> phones = new ArrayList<String>();
		Record[] records = orgPhonesGrid.getRecords();
		for (Record record : records) {
			String phone = record.getAttribute("phone");
			if (phone == null)
				continue;
			phones.add(phone);
		}
		return phones;
	}

	private ArrayList<String> getSelectedPhones() {
		ArrayList<String> phones = new ArrayList<String>();
		Record[] records = orgPhonesGrid.getRecords();
		for (Record record : records) {
			String phone = record.getAttribute("phone");
			if (phone == null)
				continue;
			String tp = record.getAttribute("pr");
			if (tp == null)
				continue;
			if ("3".equals(tp)) {
				phones.add(phone);
			}
		}
		return phones;
	}

	protected void addPhones(ListGridRecord record) {
		if (record == null)
			return;

		String tp = record.getAttribute("pr");
		if (tp == null)
			return;
		if ("3".equals(tp)) {
			String phone = record.getAttribute("phone");
			if (phone != null)
				addPhone(phone, true);
			orgPhonesGrid.redraw();
			return;
		}
		String id = record.getAttribute("rid");
		if (id == null)
			return;
		DataSource ds = DataSource.get("ContractorsPhonesDS");
		Criteria cr = new Criteria();
		DSRequest req = new DSRequest();
		req.setOperationId("searchPhones");
		if ("1".equals(tp)) {
			cr.setAttribute("organization_id", id);
		}
		if ("2".equals(tp)) {
			cr.setAttribute("org_department_id", id);
		}
		ds.fetchData(cr, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				if (response.getData() != null) {
					Record records[] = response.getData();
					for (Record record2 : records) {
						String phone = record2.getAttribute("phone");
						if (phone != null)
							addPhone(phone, true);
					}
					orgPhonesGrid.redraw();

				}

			}
		}, req);

	}

	private void addPhone(String phone, boolean setSource) {
		Record record = contractPhonesGrid.getRecordList().find("phone", phone);
		if (record == null) {
			record = new Record();
			record.setAttribute("phone", phone);
			contractPhonesGrid.getDataSource().addData(record);
			contractPhonesGrid.fetchData();
		}
		if (setSource) {
			Record[] records = orgPhonesGrid.getRecordList().findAll("phone",
					phone);
			if (records != null) {
				for (Record record2 : records) {
					record2.setAttribute("cp_id", "1");
				}

			}
		}
	}

	private void removePhone(String phone) {

		Record record = contractPhonesGrid.getRecordList().find("phone", phone);
		if (record != null) {
			contractPhonesGrid.getDataSource().removeData(record);

		}
		Record[] records = orgPhonesGrid.getRecordList()
				.findAll("phone", phone);
		if (records != null) {
			for (Record record2 : records) {
				record2.setAttribute("cp_id", (String) null);
			}

		}
	}
}
