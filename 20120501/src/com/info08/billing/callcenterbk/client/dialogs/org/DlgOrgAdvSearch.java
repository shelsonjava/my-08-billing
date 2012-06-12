package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.TabOrganization;
import com.info08.billing.callcenterbk.client.dialogs.address.StreetToTownDistrictsClientDS;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgOrgAdvSearch extends Window {

	private IButton clearButton;
	private IButton okButton;
	private IButton closeButton;

	private ListGrid activityGrid;
	private ListGrid critActivityGrid;

	private ListGrid townDistrictsGrid;
	private ListGrid critTownDistrictsGrid;

	private ListGrid bankOrgsGrid;
	private ListGrid critBankOrgsGrid;
	private TabOrganization tabOrganization;

	public DlgOrgAdvSearch(TabOrganization tabOrganization, Criteria criteria) {
		try {
			this.tabOrganization = tabOrganization;
			setTitle(CallCenterBK.constants.advParameters());
			setHeight(680);
			setWidth(1255);
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
			hLayout.setHeight(305);
			mainLayout.addMember(hLayout);

			DataSource orgActDS = DataSource.get("OrgActDS");
			activityGrid = new ListGrid();
			activityGrid.setWidth(305);
			activityGrid.setHeight100();
			activityGrid.setDataSource(orgActDS);
			activityGrid.setShowFilterEditor(true);
			activityGrid.setFilterOnKeypress(true);
			activityGrid.setCanDragRecordsOut(true);
			activityGrid.setDragDataAction(DragDataAction.COPY);
			activityGrid.setAlternateRecordStyles(true);
			activityGrid.setAutoFetchData(true);
			activityGrid.setFetchOperation("searchAllBusinesActivitiesForCB");

			ListGridField activity = new ListGridField("activity_description",
					CallCenterBK.constants.activity());
			activity.setAlign(Alignment.LEFT);

			activityGrid.setFields(activity);

			MainOrgActClientDS mainOrgActClientDS = MainOrgActClientDS
					.getInstance();

			critActivityGrid = new ListGrid();
			critActivityGrid.setWidth(305);
			critActivityGrid.setHeight100();
			critActivityGrid.setDataSource(mainOrgActClientDS);
			critActivityGrid.setCanAcceptDroppedRecords(true);
			critActivityGrid.setCanRemoveRecords(true);
			critActivityGrid.setAutoFetchData(true);
			critActivityGrid.setPreventDuplicates(true);
			critActivityGrid.setDuplicateDragMessage(CallCenterBK.constants
					.thisOrgActAlreadyChoosen());

			activityGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							critActivityGrid.transferSelectedData(activityGrid);
						}
					});

			HLayout gridsLayout = new HLayout();
			gridsLayout.setMargin(2);
			gridsLayout.setHeight100();

			gridsLayout.setMembers(activityGrid, critActivityGrid);
			hLayout.addMember(gridsLayout);

			DataSource cityRegionsDS = DataSource.get("TownDistrictDS");

			townDistrictsGrid = new ListGrid();
			townDistrictsGrid.setWidth(305);
			townDistrictsGrid.setHeight100();
			townDistrictsGrid.setCanDragRecordsOut(true);
			townDistrictsGrid.setDragDataAction(DragDataAction.COPY);
			townDistrictsGrid.setAlternateRecordStyles(true);
			townDistrictsGrid.setDataSource(cityRegionsDS);
			townDistrictsGrid.setAutoFetchData(true);
			townDistrictsGrid.setFetchOperation("fetchCityRegions");
			ListGridField town_district_name = new ListGridField(
					"town_district_name", "რეგიონების სია");
			townDistrictsGrid.setFields(town_district_name);

			StreetToTownDistrictsClientDS strToTownDistrClientDS = StreetToTownDistrictsClientDS
					.getInstance();
			critTownDistrictsGrid = new ListGrid();
			critTownDistrictsGrid.setWidth(304);
			critTownDistrictsGrid.setHeight100();
			critTownDistrictsGrid.setDataSource(strToTownDistrClientDS);
			critTownDistrictsGrid.setCanAcceptDroppedRecords(true);
			critTownDistrictsGrid.setCanRemoveRecords(true);
			critTownDistrictsGrid.setAutoFetchData(true);
			critTownDistrictsGrid.setPreventDuplicates(true);
			critTownDistrictsGrid
					.setDuplicateDragMessage("ასეთი რაიონი უკვე არჩეულია !");

			townDistrictsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							critTownDistrictsGrid
									.transferSelectedData(townDistrictsGrid);
						}
					});

			HLayout gridsLayout1 = new HLayout(0);
			gridsLayout1.setMargin(2);
			gridsLayout1.setHeight100();

			gridsLayout1.setMembers(townDistrictsGrid, critTownDistrictsGrid);
			hLayout.addMember(gridsLayout1);

			DataSource orgDSForBanks = DataSource.get("OrgDS");
			bankOrgsGrid = new ListGrid();
			bankOrgsGrid.setWidth(610);
			bankOrgsGrid.setHeight100();
			bankOrgsGrid.setDataSource(orgActDS);
			bankOrgsGrid.setShowFilterEditor(true);
			bankOrgsGrid.setFilterOnKeypress(true);
			bankOrgsGrid.setAlternateRecordStyles(true);
			bankOrgsGrid.setAutoFetchData(true);
			bankOrgsGrid.setCanDragRecordsOut(true);
			bankOrgsGrid.setDragDataAction(DragDataAction.COPY);
			bankOrgsGrid.setDataSource(orgDSForBanks);
			bankOrgsGrid.setFetchOperation("searchPartnerBanks");
			bankOrgsGrid.setWrapCells(true);
			bankOrgsGrid.setFixedRecordHeights(false);
			bankOrgsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							critBankOrgsGrid.transferSelectedData(bankOrgsGrid);
						}
					});

			ListGridField bank_organization_name = new ListGridField(
					"organization_name", CallCenterBK.constants.partnerBank());
			bank_organization_name.setAlign(Alignment.LEFT);

			ListGridField bank_full_address_not_hidden = new ListGridField(
					"full_address_not_hidden", CallCenterBK.constants.address());
			bank_full_address_not_hidden.setAlign(Alignment.LEFT);

			bankOrgsGrid.setFields(bank_organization_name,
					bank_full_address_not_hidden);

			OrgPartnetBanksClientDS orgPartnetBanksClientDS = OrgPartnetBanksClientDS
					.getInstance();
			critBankOrgsGrid = new ListGrid();
			critBankOrgsGrid.setWidth(610);
			critBankOrgsGrid.setHeight100();
			critBankOrgsGrid.setDataSource(orgActDS);
			critBankOrgsGrid.setPreventDuplicates(true);
			critBankOrgsGrid.setDuplicateDragMessage("ასეთი უკვე არჩეულია !");
			critBankOrgsGrid.setCanAcceptDroppedRecords(true);
			critBankOrgsGrid.setAlternateRecordStyles(true);
			critBankOrgsGrid.setAutoFetchData(true);
			critBankOrgsGrid.setDataSource(orgPartnetBanksClientDS);
			critBankOrgsGrid.setWrapCells(true);
			critBankOrgsGrid.setFixedRecordHeights(false);
			critBankOrgsGrid.setCanRemoveRecords(true);

			HLayout hLayoutPartBanks = new HLayout();
			hLayoutPartBanks.setMargin(2);
			hLayoutPartBanks.setWidth100();
			hLayoutPartBanks.setMembersMargin(13);
			hLayoutPartBanks.setHeight100();

			hLayoutPartBanks.setMembers(bankOrgsGrid, critBankOrgsGrid);
			mainLayout.addMember(hLayoutPartBanks);

			HLayout buttonsLayout = new HLayout();
			buttonsLayout.setMargin(4);
			buttonsLayout.setWidth100();
			buttonsLayout.setAlign(Alignment.RIGHT);
			buttonsLayout.setMembersMargin(5);
			mainLayout.addMember(buttonsLayout);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			okButton = new IButton();
			okButton.setTitle(CallCenterBK.constants.save());

			closeButton = new IButton();
			closeButton.setTitle(CallCenterBK.constants.close());

			buttonsLayout.setMembers(clearButton, okButton, closeButton);

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					critActivityGrid.setData(new ListGridRecord[] {});
					critTownDistrictsGrid.setData(new ListGridRecord[] {});
					critBankOrgsGrid.setData(new ListGridRecord[] {});
				}
			});
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					initAdvCriteria();
				}
			});
			addCloseClickHandler(new CloseClickHandler() {
				@Override
				public void onCloseClick(CloseClickEvent event) {
					initAdvCriteria();
				}
			});
			addItem(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void initAdvCriteria() {
		try {
			tabOrganization.setAdvCriteria(null);
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
