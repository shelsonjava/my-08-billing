package com.info08.billing.callcenter.client.dialogs.control;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class SessDiscHistDialog extends Window {

	private VLayout hLayout;
	private DataSource sessDiscHistDS = null;
	private ListGrid sessDiscHistGrid = null;
	private DetailViewer detailViewer;

	public SessDiscHistDialog(String sessionId) {

		setWidth(850);
		setHeight(450);
		setTitle("გარკვევები");
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		sessDiscHistDS = DataSource.get("SessDiscHistDS");

		sessDiscHistDS.getField("discover_id").setTitle("ID");
		sessDiscHistDS.getField("phone").setTitle("ნომერი");
		sessDiscHistDS.getField("discover_txt").setTitle("გარკვევის ტექსტი");
		sessDiscHistDS.getField("rec_user").setTitle("ვინ დაარეგისტრირა");
		sessDiscHistDS.getField("upd_user").setTitle("ვინ უპასუხა");
		sessDiscHistDS.getField("response_type").setTitle("პასუხის სახეობა");
		sessDiscHistDS.getField("execution_status").setTitle("შესრულების ტიპი");

		ListGridField phone = new ListGridField("phone", "ნომერი", 80);
		ListGridField discover_txt = new ListGridField("discover_txt",
				"გამოკვლევის ტექსტი", 250);
		ListGridField rec_user = new ListGridField("rec_user",
				"ვინ დაარეგისტრირა", 150);
		ListGridField upd_user = new ListGridField("upd_user", "ვინ უპასუხა",
				150);
		ListGridField response_type = new ListGridField("response_type",
				"პასუხის სახეობა", 100);
		ListGridField execution_status = new ListGridField("execution_status",
				"შესრულების ტიპი", 100);

		hLayout = new VLayout(5);
		hLayout.setWidth100();
		hLayout.setHeight100();

		sessDiscHistGrid = new ListGrid();
		sessDiscHistGrid.setWidth100();
		sessDiscHistGrid.setHeight(250);
		sessDiscHistGrid.setAutoFetchData(false);
		sessDiscHistGrid.setAlternateRecordStyles(true);
		sessDiscHistGrid.setDataSource(sessDiscHistDS);
		sessDiscHistGrid.setShowFilterEditor(false);
		sessDiscHistGrid.setCanEdit(false);
		sessDiscHistGrid.setCanRemoveRecords(false);
		sessDiscHistGrid.setFetchOperation("sessDiscCustSearch");		

		sessDiscHistGrid.setFields(phone, discover_txt, rec_user, upd_user,
				response_type, execution_status);

		Criteria criteria = new Criteria();
		criteria.addCriteria("sessionId", sessionId);
		sessDiscHistGrid.filterData(criteria);

		detailViewer = new DetailViewer();
		detailViewer.setDataSource(sessDiscHistDS);

		hLayout.setMembers(sessDiscHistGrid, detailViewer);

		sessDiscHistGrid.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				detailViewer.viewSelectedData(sessDiscHistGrid);
			}
		});
		addItem(hLayout);
	}
}
