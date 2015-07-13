package com.info08.billing.callcenterbk.client.dialogs.control;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class LogSMSDialog extends MyWindow {

	private VLayout hLayout;
	private DataSource smsDS = null;
	private ListGrid notesGrid = null;
	private DetailViewer detailViewer;

	public LogSMSDialog(String session_call_id) {
		super();
		setWidth(630);
		setHeight(400);
		setTitle("SMS");
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		smsDS = DataSource.get("SentSMSHist");

		smsDS.getField("reciever_number").setTitle("ნომერი");
		smsDS.getField("message_context").setTitle("SMS - ის ტექსტი");

		ListGridField reciever_number = new ListGridField("reciever_number", "ნომერი", 80);
		ListGridField sms = new ListGridField("message_context", "SMS - ის ტექსტი");

		hLayout = new VLayout(5);
		hLayout.setWidth100();
		hLayout.setHeight100();

		notesGrid = new ListGrid();
		notesGrid.setWidth100();
		notesGrid.setHeight(250);
		notesGrid.setAutoFetchData(false);
		notesGrid.setAlternateRecordStyles(true);
		notesGrid.setDataSource(smsDS);
		notesGrid.setShowFilterEditor(false);
		notesGrid.setCanEdit(false);
		notesGrid.setCanRemoveRecords(false);
		notesGrid.setFetchOperation("findSMSLog");
		notesGrid.setWrapCells(true);
		notesGrid.setCanSelectText(true);
		notesGrid.setFixedRecordHeights(false);

		notesGrid.setFields(reciever_number, sms);

		Criteria criteria = new Criteria();
		criteria.addCriteria("session_call_id", session_call_id);
		notesGrid.filterData(criteria);

		detailViewer = new DetailViewer();
		detailViewer.setDataSource(smsDS);

		hLayout.setMembers(notesGrid, detailViewer);

		notesGrid.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				detailViewer.viewSelectedData(notesGrid);
			}
		});
		addItem(hLayout);
	}
}
