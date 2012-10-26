package com.info08.billing.callcenterbk.client.dialogs.address;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgHistStreets extends Window {

	private VLayout hLayout;
	private ListGrid histStreetsGrid;
	private DataSource streetsDS;

	public DlgHistStreets(ListGridRecord listGridRecord) {
		try {

			streetsDS = DataSource.get("StreetsDS");

			setWidth(1260);
			setHeight(600);
			setTitle("ქუჩების ისტორია");
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

			histStreetsGrid = new ListGrid() {
				@Override
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					String rdeleted = record.getAttribute("rdeleted");
					if (rdeleted != null)
						if (rdeleted.trim().equals("true"))
							return "color:red;";
						else
							return super.getCellCSSText(record, rowNum, colNum);
					else
						return super.getCellCSSText(record, rowNum, colNum);
				}
			};
			histStreetsGrid.setWidth100();
			histStreetsGrid.setHeight100();
			histStreetsGrid.setAlternateRecordStyles(true);
			histStreetsGrid.setDataSource(streetsDS);
			histStreetsGrid.setAutoFetchData(false);
			histStreetsGrid.setShowFilterEditor(false);
			histStreetsGrid.setCanEdit(false);
			histStreetsGrid.setCanRemoveRecords(false);
			histStreetsGrid.setFetchOperation("histSearch");
			histStreetsGrid.setCanSort(false);
			histStreetsGrid.setCanResizeFields(false);
			histStreetsGrid.setWrapCells(true);
			histStreetsGrid.setFixedRecordHeights(false);
			histStreetsGrid.setCanDragSelectText(true);

			ListGridField l_original_org_name = new ListGridField(
					"street_name", "დასახელება", 400);
			l_original_org_name.setAlign(Alignment.LEFT);

			ListGridField l_full_address_not_hidden = new ListGridField(
					"street_location", "კომენტარი");
			l_full_address_not_hidden.setAlign(Alignment.LEFT);

			ListGridField hist_user_on = new ListGridField("on_user", "დაამატა");
			hist_user_on.setWidth(50);

			ListGridField hist_user_off = new ListGridField("off_user",
					"შეცვალა");
			hist_user_off.setWidth(50);
			ListGridField hist_start = new ListGridField("hist_start_date",
					"-დან");
			hist_start.setWidth(120);

			ListGridField hist_end = new ListGridField("hist_end_date", "-მდე");
			hist_end.setWidth(120);

			ListGridField ph_deleted_org = new ListGridField("rdeleted",
					"წაშლილი");
			ph_deleted_org.setWidth(70);
			ph_deleted_org.setType(ListGridFieldType.BOOLEAN);

			histStreetsGrid.setFields(l_original_org_name,
					l_full_address_not_hidden, hist_user_on, hist_start,
					hist_user_off, hist_end, ph_deleted_org);

			hLayout.addMember(histStreetsGrid);
			addItem(hLayout);
			search(listGridRecord);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search(ListGridRecord listGridRecord) {
		try {
			Integer streets_id = listGridRecord.getAttributeAsInt("streets_id");
			Criteria criteria = new Criteria();
			criteria.setAttribute("streets_id", streets_id);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("histSearch");
			histStreetsGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
