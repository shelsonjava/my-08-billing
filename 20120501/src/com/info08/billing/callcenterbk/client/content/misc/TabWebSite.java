package com.info08.billing.callcenterbk.client.content.misc;

import com.info08.billing.callcenterbk.client.dialogs.misc.DlgAddEditWebSite;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class TabWebSite extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem webSiteGroupsItem;
	private TextItem webSiteAddressItem;
	private TextItem webSiteRemarkItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource WebSitesDS;

	public TabWebSite() {
		try {
			setTitle("საიტების მართვა");
			setCanClose(true);

			WebSitesDS = DataSource.get("WebSitesDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(500);
			searchForm.setTitleWidth(200);
			searchForm.setNumCols(3);
			mainLayout.addMember(searchForm);

			Criteria criteria = new Criteria();

			webSiteGroupsItem = new ComboBoxItem();
			webSiteGroupsItem.setTitle("საიტების ჯგუფები");
			webSiteGroupsItem.setWidth(300);
			webSiteGroupsItem.setName("web_site_group_name");
			webSiteGroupsItem.setFetchMissingValues(true);
			webSiteGroupsItem.setFilterLocally(false);
			webSiteGroupsItem.setAddUnknownValues(false);

			DataSource WebSiteGroupsDS = DataSource.get("WebSiteGroupsDS");
			webSiteGroupsItem
					.setOptionOperationId("searchAllWebSiteGroupsForCB");
			webSiteGroupsItem.setOptionDataSource(WebSiteGroupsDS);
			webSiteGroupsItem.setValueField("web_site_group_id");
			webSiteGroupsItem.setDisplayField("web_site_group_name");

			webSiteGroupsItem.setOptionCriteria(criteria);
			webSiteGroupsItem.setAutoFetchData(false);

			webSiteGroupsItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = webSiteGroupsItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("web_site_group_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("web_site_group_id", nullO);
						}
					}
				}
			});

			webSiteAddressItem = new TextItem();
			webSiteAddressItem.setTitle("მისამართი");
			webSiteAddressItem.setName("address");
			webSiteAddressItem.setWidth(300);

			webSiteRemarkItem = new TextItem();
			webSiteRemarkItem.setTitle("კომენტარი");
			webSiteRemarkItem.setName("remark");
			webSiteRemarkItem.setWidth(300);

			searchForm.setFields(webSiteGroupsItem, webSiteAddressItem,
					webSiteRemarkItem);

			searchForm.focusInItem(webSiteAddressItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(500);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(930);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton("დამატება", "addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton("შეცვლა", "editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton("გაუქმება", "deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			toolStrip.addSeparator();

			listGrid = new ListGrid();

			listGrid.setWidth(930);
			listGrid.setHeight(380);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(WebSitesDS);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllWebSites");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			WebSitesDS.getField("address").setTitle("მისამართი");
			WebSitesDS.getField("web_site_group_id").setTitle(
					"საიტების ჯგუფის იდენტიფიკატორი");
			WebSitesDS.getField("web_site_group_name").setTitle(
					"საიტების ჯგუფი");
			WebSitesDS.getField("remark").setTitle("კომენტარი");

			ListGridField address = new ListGridField("address", "მისამართი",
					450);
			ListGridField web_site_group_name = new ListGridField(
					"web_site_group_name", "საიტების ჯგუფი", 180);
			ListGridField remark = new ListGridField("remark", "კომენტარი");

			listGrid.setFields(address, web_site_group_name, remark);

			mainLayout.addMember(listGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					webSiteGroupsItem.clearValue();
					webSiteAddressItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditWebSite dlgAddEditWebSite = new DlgAddEditWebSite(
							listGrid, null);
					dlgAddEditWebSite.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					DlgAddEditWebSite dlgAddEditWebSite = new DlgAddEditWebSite(
							listGrid, listGridRecord);
					dlgAddEditWebSite.show();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					final Integer web_site_id = listGridRecord
							.getAttributeAsInt("web_site_id");
					if (web_site_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(web_site_id);
									}
								}
							});
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(930);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(WebSitesDS);
			detailViewer.setWidth(910);
			tabDetViewer.setPane(detailViewer);

			listGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(listGrid);
				}
			});
			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					DlgAddEditWebSite dlgAddEditWebSite = new DlgAddEditWebSite(
							listGrid, listGridRecord);
					dlgAddEditWebSite.show();
				}
			});

			webSiteAddressItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void search() {
		try {
			String webSiteGroupId = webSiteGroupsItem.getValueAsString();
			String address = webSiteAddressItem.getValueAsString();
			String remark = webSiteRemarkItem.getValueAsString();

			Criteria criteria = new Criteria();
			if (webSiteGroupId != null) {
				criteria.setAttribute("web_site_group_id", new Integer(
						webSiteGroupId));
			}

			if (address != null && !address.trim().equals("")) {
				criteria.setAttribute("address", address);
			}

			if (remark != null && !remark.trim().equals("")) {
				criteria.setAttribute("remark", remark);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllWebSites");
			listGrid.invalidateCache();
			listGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void delete(Integer web_site_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("web_site_id", web_site_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeWebSites");
			listGrid.removeData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
