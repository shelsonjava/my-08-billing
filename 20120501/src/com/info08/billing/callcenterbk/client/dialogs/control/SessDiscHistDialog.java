package com.info08.billing.callcenterbk.client.dialogs.control;

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
	private DataSource sessSurvHistDS = null;
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

		sessSurvHistDS = DataSource.get("SessSurvHistDS");

		sessSurvHistDS.getField("survey_id").setTitle("ID");
		sessSurvHistDS.getField("p_numb").setTitle("ნომერი");
		sessSurvHistDS.getField("survey_descript").setTitle("გარკვევის ტექსტი");
		sessSurvHistDS.getField("survey_creator").setTitle("ვინ დაარეგისტრირა");
		sessSurvHistDS.getField("loked_user").setTitle("ვინ უპასუხა");
		sessSurvHistDS.getField("survey_reply_type_name").setTitle("პასუხის სახეობა");
		sessSurvHistDS.getField("survery_responce_status").setTitle("შესრულების ტიპი");

		ListGridField p_numb = new ListGridField("p_numb", "ნომერი", 80);
		ListGridField survey_descript = new ListGridField("survey_descript",
				"გამოკვლევის ტექსტი", 250);
		ListGridField rec_user = new ListGridField("survey_creator",
				"ვინ დაარეგისტრირა", 150);
		ListGridField upd_user = new ListGridField("loked_user", "ვინ უპასუხა",
				150);
		ListGridField survey_reply_type_name = new ListGridField("survey_reply_type_name",
				"პასუხის სახეობა", 100);
		ListGridField survery_responce_status = new ListGridField("survery_responce_status",
				"შესრულების ტიპი", 100);

		hLayout = new VLayout(5);
		hLayout.setWidth100();
		hLayout.setHeight100();

		sessDiscHistGrid = new ListGrid();
		sessDiscHistGrid.setWidth100();
		sessDiscHistGrid.setHeight(250);
		sessDiscHistGrid.setAutoFetchData(false);
		sessDiscHistGrid.setAlternateRecordStyles(true);
		sessDiscHistGrid.setDataSource(sessSurvHistDS);
		sessDiscHistGrid.setShowFilterEditor(false);
		sessDiscHistGrid.setCanEdit(false);
		sessDiscHistGrid.setCanRemoveRecords(false);
		sessDiscHistGrid.setFetchOperation("sessDiscCustSearch");		

		sessDiscHistGrid.setFields(p_numb, survey_descript, rec_user, upd_user,
				survey_reply_type_name, survery_responce_status);

		Criteria criteria = new Criteria();
		criteria.addCriteria("sessionId", sessionId);
		sessDiscHistGrid.filterData(criteria);

		detailViewer = new DetailViewer();
		detailViewer.setDataSource(sessSurvHistDS);

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
