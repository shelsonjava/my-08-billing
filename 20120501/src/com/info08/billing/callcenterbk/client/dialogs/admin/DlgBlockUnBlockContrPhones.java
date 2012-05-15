package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgBlockUnBlockContrPhones extends Window {

	// search form
	private DynamicForm searchForm;

	private CheckboxItem phoneListTypeItem;
	private TextAreaItem phoneListItem;

	private VLayout hLayout;

	private ListGrid phonesGrid;
	private ListGridRecord listGridRecord;
	private boolean block = false;
	private ListGrid contractorsGrid;

	public DlgBlockUnBlockContrPhones(ListGrid contractorsGrid,
			ListGridRecord listGridRecord, boolean block) {
		try {
			this.listGridRecord = listGridRecord;
			this.block = block;
			this.contractorsGrid = contractorsGrid;
			setTitle(block ? CallCenterBK.constants.blockPhone()
					: CallCenterBK.constants.unBlockPhone());

			setHeight(700);
			setWidth(1000);
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

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(false);
			searchForm.setWidth100();
			searchForm.setNumCols(2);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			hLayout.addMember(searchForm);

			phoneListTypeItem = new CheckboxItem();
			phoneListTypeItem.setTitle(CallCenterBK.constants.onlyThisNumbers());
			phoneListTypeItem.setWidth("100%");
			phoneListTypeItem.setName("phoneListTypeItem");

			phoneListItem = new TextAreaItem();
			phoneListItem.setTitle(CallCenterBK.constants.phoneList());
			phoneListItem.setWidth("100%");
			phoneListItem.setHeight(100);
			phoneListItem.setName("phoneListItem");			

			searchForm.setFields(phoneListTypeItem, phoneListItem);

			DataSource dataSource = DataSource.get("OrgDS");

			phonesGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer deleted = countryRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && !deleted.equals(0)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			Criteria criteria = new Criteria();
			criteria.setAttribute("main_id",
					listGridRecord.getAttributeAsInt("main_id"));
			phonesGrid.setCriteria(criteria);
			phonesGrid.setWidth100();
			phonesGrid.setHeight100();
			phonesGrid.setAlternateRecordStyles(true);
			phonesGrid.setDataSource(dataSource);
			phonesGrid.setAutoFetchData(true);
			phonesGrid.setShowFilterEditor(false);
			phonesGrid.setCanEdit(false);
			phonesGrid.setCanRemoveRecords(false);
			phonesGrid.setFetchOperation("searchMainOrgPhonesByMainId");
			phonesGrid.setShowRowNumbers(true);
			phonesGrid.setCanHover(true);
			phonesGrid.setShowHover(true);
			phonesGrid.setShowHoverComponents(true);
			phonesGrid.setWrapCells(true);
			phonesGrid.setFixedRecordHeights(false);
			phonesGrid.setCanDragSelectText(true);

			ListGridField orgName = new ListGridField("org_name",
					CallCenterBK.constants.orgName());
			ListGridField orgDepName = new ListGridField("main_detail_geo",
					CallCenterBK.constants.department(), 300);
			ListGridField phone = new ListGridField("phone",
					CallCenterBK.constants.phone(), 80);

			orgName.setAlign(Alignment.LEFT);
			orgDepName.setAlign(Alignment.LEFT);
			phone.setAlign(Alignment.CENTER);

			phonesGrid.setFields(orgName, orgDepName, phone);

			hLayout.addMember(phonesGrid);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(saveItem, cancItem);

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
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("contract_id",
					listGridRecord.getAttributeAsInt("contract_id"));
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("main_id",
					listGridRecord.getAttributeAsInt("main_id"));
			if (block) {
				record.setAttribute("block_type", new Integer(1));
			} else {
				record.setAttribute("block_type", new Integer(0));
			}
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "blockUnBlockContractor");
			contractorsGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
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
