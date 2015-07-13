package com.info08.billing.callcenterbk.client.dialogs.correction;

import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class DlgOrgInfoViewByPhone extends MyWindow {

	public DlgOrgInfoViewByPhone(String phone) {
		super();
		setWidth(600);
		setHeight(700);
		setTitle("ორგანიზაციები ნომრის მიხედვით");
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

		DataSource dataSource = DataSource.get("OrgInfoByPhoneDS");
		dataSource.getField("org_name").setTitle("ორგანიზაციის დასახელება");
		dataSource.getField("note").setTitle("შენიშვნა");
		dataSource.getField("workinghours").setTitle("სამუშაო საათები");
		dataSource.getField("director").setTitle("დირექტორი");
		dataSource.getField("identcode").setTitle("საიდ. კოდი");
		dataSource.getField("founded").setTitle("დაარსდა");
		dataSource.getField("legaladdress").setTitle("მისამართი");
		dataSource.getField("mail").setTitle("ელ. ფოსტა");
		dataSource.getField("webaddress").setTitle("ვებ გვერდი");
		dataSource.getField("org_info").setTitle("ორგ. ინფორმაცია");
		dataSource.getField("contactperson").setTitle("საკონტაქტო პიროვნება");
		dataSource.getField("dayoffs").setTitle("დასვენების დღეები");
		dataSource.getField("legal_statuse").setTitle("სტატუსი");
		dataSource.getField("partnerbank").setTitle("პარტნიორი ბანკი");
		dataSource.getField("workpersoncountity").setTitle("თანამშრომლების რაოდენობა");
		dataSource.getField("upd_user").setTitle("ვინ განაახლა");
		dataSource.getField("upd_date").setTitle("როდის განახლდა");
		dataSource.getField("ind").setTitle("ინდექსი");		
		dataSource.getField("org_name_eng").setTitle("ორგ. დასახელება(ინგლისურად)");
		dataSource.getField("new_identcode").setTitle("ახალი კოდი");
		
		ListGridField org_name = new ListGridField("org_name",
				"ორგანიზაციის დასახელება", 250);
		ListGridField director = new ListGridField("director", "დირექტორი", 150);
		ListGridField identcode = new ListGridField("identcode", "საიდ. კოდი",
				120);

		final ListGrid orgGrid = new ListGrid();
		orgGrid.setWidth100();
		orgGrid.setHeight(100);
		orgGrid.setAlternateRecordStyles(true);
		orgGrid.setShowFilterEditor(false);
		orgGrid.setCanEdit(false);
		orgGrid.setCanRemoveRecords(false);		
		orgGrid.setShowRowNumbers(true);
		orgGrid.setCanHover(true);
		orgGrid.setShowHover(true);
		orgGrid.setShowHoverComponents(true);
		
		orgGrid.setAutoFetchData(true);
		Criteria criteria = new Criteria();
		criteria.setAttribute("phone", phone);
		orgGrid.setCriteria(criteria);
		orgGrid.setDataSource(dataSource);
		orgGrid.setFetchOperation("getOrgInfoByPhoneForAbonent");		
		orgGrid.setFields(org_name, director, identcode);

		final DetailViewer detailViewer = new DetailViewer();	
		detailViewer.setCanSelectText(true);
		detailViewer.setHeight(520);
		detailViewer.setWidth100();
		detailViewer.setDataSource(dataSource);

		orgGrid.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				detailViewer.viewSelectedData(orgGrid);
			}
		});

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);
		hLayoutItem.setMargin(10);

		IButton cancItem = new IButton();
		cancItem.setTitle("დახურვა");
		cancItem.setWidth(100);

		hLayoutItem.setMembers(cancItem);
		hLayout.setMembers(orgGrid, detailViewer, hLayoutItem);
		addItem(hLayout);

		cancItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});
	}
}
