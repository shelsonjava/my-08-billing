package com.info08.billing.callcenterbk.client.common.components;

import java.util.ArrayList;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MyComboboxItemMultDlg extends MyWindow {

	private MyComboboxItemMultClass classItem;
	private ListGrid srcListGrid;
	private ListGrid destListGrid;

	public MyComboboxItemMultDlg(final MyComboboxItemMultiple myItem) {
		super();
		classItem = myItem.getParamClass();

		setWidth(classItem.getWindowWidth());
		setHeight(classItem.getWindowHeight());
		setTitle(classItem.getWindowTitle());
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		VLayout hLayout = new VLayout(5);
		hLayout.setWidth100();
		hLayout.setHeight100();

		DataSource datasource = DataSource.get(classItem.getDsName());
		srcListGrid = new ListGrid();
		srcListGrid.setWidth("50%");
		srcListGrid.setHeight100();
		srcListGrid.setDataSource(datasource);
		srcListGrid.setShowFilterEditor(true);
		srcListGrid.setFilterOnKeypress(true);
		srcListGrid.setCanDragRecordsOut(true);
		srcListGrid.setDragDataAction(DragDataAction.COPY);
		srcListGrid.setAlternateRecordStyles(true);
		srcListGrid.setAutoFetchData(true);
		srcListGrid.setFetchOperation(classItem.getDsOperationId());

		String columns[] = classItem.getDisplayFields();
		String columnTitles[] = classItem.getDisplayFieldTitles();

		DataSource dsClient = new DataSource();
		dsClient.setClientOnly(true);

		ArrayList<DataSourceField> dsFields = new ArrayList<DataSourceField>();

		DataSourceIntegerField idField = new DataSourceIntegerField(
				classItem.getIdField());
		idField.setHidden(true);
		idField.setPrimaryKey(true);
		idField.setRequired(true);
		dsFields.add(idField);

		int i = 0;
		ListGridField fields[] = new ListGridField[columns.length];
		for (String string : columns) {
			ListGridField listGridField = new ListGridField(string,
					columnTitles[i]);
			listGridField.setAlign(Alignment.LEFT);
			fields[i] = listGridField;
			DataSourceTextField field = new DataSourceTextField(string,
					columnTitles[i]);
			dsFields.add(field);
			i++;
		}
		dsClient.setFields(dsFields.toArray(new DataSourceField[] {}));
		srcListGrid.setFields(fields);

		destListGrid = new ListGrid();
		destListGrid.setWidth("50%");
		destListGrid.setHeight100();
		destListGrid.setDataSource(dsClient);
		destListGrid.setCanRemoveRecords(true);
		destListGrid.setAutoFetchData(true);
		destListGrid.setPreventDuplicates(true);
		destListGrid.setDuplicateDragMessage(classItem.getDublicateMessage());

		srcListGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				destListGrid.transferSelectedData(srcListGrid);
			}
		});
		destListGrid.setCanAcceptDroppedRecords(true);
		destListGrid.fetchData();

		HLayout layoutGrids = new HLayout(10);
		layoutGrids.setWidth100();
		layoutGrids.setHeight100();
		layoutGrids.setMembers(srcListGrid, destListGrid);

		HLayout buttonsLayout = new HLayout(5);
		buttonsLayout.setWidth100();
		buttonsLayout.setAlign(Alignment.RIGHT);
		buttonsLayout.setMargin(10);

		IButton saveItem = new IButton();
		saveItem.setTitle(CallCenterBK.constants.save());
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle(CallCenterBK.constants.close());
		cancItem.setWidth(100);

		buttonsLayout.setMembers(saveItem, cancItem);
		hLayout.setMembers(layoutGrids, buttonsLayout);

		cancItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});

		saveItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord valueRecords[] = destListGrid.getRecords();
				if (valueRecords == null) {
					destroy();
					return;
				}
				Record[] recs = new Record[valueRecords.length];
				for (int j = 0; j < recs.length; j++) {
					recs[j] = new Record(valueRecords[j].toMap());
				}
				classItem.setValueRecords(recs);
				myItem.setDisplayValue();
				destroy();
			}
		});

		if (classItem.getValueRecords() != null) {
			Record records[] = classItem.getValueRecords();
			for (Record record : records) {
				destListGrid.addData(record);
			}
			destListGrid.fetchData(new Criteria(), new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			});
		}

		addItem(hLayout);
	}
}
