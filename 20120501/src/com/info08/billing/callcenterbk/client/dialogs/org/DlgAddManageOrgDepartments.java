package com.info08.billing.callcenterbk.client.dialogs.org;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class DlgAddManageOrgDepartments extends Window {

	// main layout
	private VLayout hLayout;

	private DynamicForm searchForm;
	private TextItem orgDepNameItem;
	private TextItem orgDepAddrItem;

	private ListGrid orgDepTreeGrid;
	private DataSource orgDepDataSource;

	protected ListGridRecord listGridRecord;
	protected TreeGrid orgTreeGrid;

	private ToolStripButton sortBtn;
	private ToolStripButton addNewOrgBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	public DlgAddManageOrgDepartments(ListGridRecord listGridRecord,
			TreeGrid orgTreeGrid) {
		try {
			this.orgTreeGrid = orgTreeGrid;
			this.listGridRecord = listGridRecord;
			setTitle(CallCenterBK.constants.manageOrgDepartments());

			setHeight(760);
			setWidth(1272);
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

			VLayout content = new VLayout();
			content.setHeight100();
			content.setWidth100();
			content.setMembersMargin(10);
			hLayout.addMember(content);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth100();
			searchForm.setNumCols(4);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			content.addMember(searchForm);

			orgDepNameItem = new TextItem();
			orgDepNameItem.setTitle(CallCenterBK.constants.department());
			orgDepNameItem.setWidth(620);
			orgDepNameItem.setName("department");

			orgDepAddrItem = new TextItem();
			orgDepAddrItem.setTitle(CallCenterBK.constants.address());
			orgDepAddrItem.setWidth(620);
			orgDepAddrItem.setName("real_address_descr");

			searchForm.setFields(orgDepNameItem, orgDepAddrItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth100();
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			IButton clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			IButton findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			content.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			content.addMember(toolStrip);

			addNewOrgBtn = new ToolStripButton(
					CallCenterBK.constants.addNewOrgDepartment(), "addIcon.png");
			addNewOrgBtn.setLayoutAlign(Alignment.LEFT);
			addNewOrgBtn.setWidth(50);
			toolStrip.addButton(addNewOrgBtn);

			editBtn = new ToolStripButton(CallCenterBK.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			toolStrip.addSeparator();

			sortBtn = new ToolStripButton(CallCenterBK.constants.sort(),
					"sort.png");
			sortBtn.setLayoutAlign(Alignment.LEFT);
			sortBtn.setWidth(50);
			toolStrip.addButton(sortBtn);

			orgDepDataSource = DataSource.get("OrgDepartmentDS");
			Criteria criteria = new Criteria();
			criteria.setAttribute("p_organization_id",
					listGridRecord.getAttributeAsInt("organization_id"));

			orgDepTreeGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer isBold = countryRecord.getAttributeAsInt("isBold");
					if (isBold != null && isBold.equals(1)) {
						return "font-weight: bold;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};
			orgDepTreeGrid.setLeft(50);
			orgDepTreeGrid.setTop(50);
			orgDepTreeGrid.setWidth100();
			orgDepTreeGrid.setHeight100();
			orgDepTreeGrid.setFetchOperation("orgDepartSearch");
			orgDepTreeGrid.setCriteria(criteria);
			orgDepTreeGrid.setDataSource(orgDepDataSource);
			orgDepTreeGrid.setAutoFetchData(true);
			orgDepTreeGrid.setAutoSaveEdits(false);
			orgDepTreeGrid.setCanSelectAll(false);
			orgDepTreeGrid.setWrapCells(true);
			orgDepTreeGrid.setFixedRecordHeights(false);
			orgDepTreeGrid.setCanSort(false);

			content.addMember(orgDepTreeGrid);
			ListGridField tree_org_parrent = new ListGridField("tree_parrent",
					" ", 30);
			tree_org_parrent.setAlign(Alignment.CENTER);
			tree_org_parrent.setType(ListGridFieldType.IMAGE);
			tree_org_parrent.setImageURLPrefix("orgtypes/");
			tree_org_parrent.setImageURLSuffix(".png");
			tree_org_parrent.setCanSort(false);
			tree_org_parrent.setCanFilter(false);

			ListGridField tree_org_child = new ListGridField("tree_child", " ",
					30);
			tree_org_child.setAlign(Alignment.CENTER);
			tree_org_child.setType(ListGridFieldType.IMAGE);
			tree_org_child.setImageURLPrefix("orgtypes/");
			tree_org_child.setImageURLSuffix(".png");
			tree_org_child.setCanSort(false);
			tree_org_child.setCanFilter(false);

			TreeGridField organization_name = new TreeGridField("department",
					CallCenterBK.constants.department());
			organization_name.setTreeField(true);

			ListGridField real_address = new ListGridField(
					"full_address_not_hidden",
					CallCenterBK.constants.realAddress(), 400);
			orgDepTreeGrid.setFields(organization_name, real_address,
					tree_org_parrent, tree_org_child);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.addMember(cancItem);

			hLayout.addMember(hLayoutItem);

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					orgDepNameItem.clearValue();
					orgDepAddrItem.clearValue();
				}
			});
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchDepartment();
				}
			});

			orgDepNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						searchDepartment();
					}
				}
			});
			orgDepAddrItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						searchDepartment();
					}
				}
			});

			fillFields();
			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void searchDepartment() {
		try {
			Integer p_organization_id = listGridRecord
					.getAttributeAsInt("organization_id");
			String departament = orgDepNameItem.getValueAsString();
			String real_address_descr = orgDepAddrItem.getValueAsString();

			Criteria criteria = new Criteria();
			criteria.setAttribute("p_organization_id", p_organization_id);

			if (departament != null && !departament.trim().equals("")) {
				String tmp = departament.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("department" + i, item);
					i++;
				}
			}
			criteria.setAttribute("real_address_descr", real_address_descr);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "orgDepartSearch");
			orgDepTreeGrid.invalidateCache();
			orgDepTreeGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (listGridRecord != null) {
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	protected void save() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
