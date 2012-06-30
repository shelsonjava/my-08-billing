package com.info08.billing.callcenterbk.client.dialogs.misc;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditWebSite extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem webSiteGroupsItem;
	private TextItem addressItem;
	private TextItem remarkItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditWebSite(ListGrid listGrid, ListGridRecord pRecord) {
		this.editRecord = pRecord;
		this.listGrid = listGrid;

		setTitle(pRecord == null ? "საიტის დამატება" : "საიტის მოდიფიცირება");

		setHeight(160);
		setWidth(590);
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

		dynamicForm = new DynamicForm();
		dynamicForm.setAutoFocus(true);
		dynamicForm.setWidth100();
		dynamicForm.setTitleWidth(180);
		dynamicForm.setNumCols(2);
		hLayout.addMember(dynamicForm);

		webSiteGroupsItem = new ComboBoxItem();
		webSiteGroupsItem.setTitle("საიტის ჯგუფი");
		webSiteGroupsItem.setWidth(400);
		webSiteGroupsItem.setName("web_site_group_id");
		webSiteGroupsItem.setFetchMissingValues(true);
		webSiteGroupsItem.setFilterLocally(false);
		webSiteGroupsItem.setAddUnknownValues(false);

		DataSource WebSiteGroupsDS = DataSource.get("WebSiteGroupsDS");
		webSiteGroupsItem.setOptionOperationId("searchAllWebSiteGroupsForCB");
		webSiteGroupsItem.setOptionDataSource(WebSiteGroupsDS);
		webSiteGroupsItem.setValueField("web_site_group_id");
		webSiteGroupsItem.setDisplayField("web_site_group_name");

		Criteria criteria = new Criteria();

		webSiteGroupsItem.setOptionCriteria(criteria);
		webSiteGroupsItem.setAutoFetchData(false);

		webSiteGroupsItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				Criteria criteria = webSiteGroupsItem.getOptionCriteria();
				if (criteria != null) {
					String oldAttr = criteria.getAttribute("web_site_group_id");
					if (oldAttr != null) {
						Object nullO = null;
						criteria.setAttribute("web_site_group_id", nullO);
					}
				}
			}
		});

		addressItem = new TextItem();
		addressItem.setTitle("მისამართი");
		addressItem.setName("address");
		addressItem.setWidth(400);

		remarkItem = new TextItem();
		remarkItem.setTitle("კომენტარი");
		remarkItem.setName("remark");
		remarkItem.setWidth(400);

		dynamicForm.setFields(webSiteGroupsItem, addressItem, remarkItem);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton saveItem = new IButton();
		saveItem.setTitle("შენახვა");
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle("დახურვა");
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
		fillFields();
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}

			Integer web_site_group_id = editRecord
					.getAttributeAsInt("web_site_group_id");
			if (web_site_group_id != null) {
				webSiteGroupsItem.setValue(web_site_group_id);
			}

			String address = editRecord.getAttributeAsString("address");
			if (address != null) {
				addressItem.setValue(address);
			}

			String remark = editRecord.getAttributeAsString("remark");
			if (remark != null) {
				remarkItem.setValue(remark);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String web_site_group = webSiteGroupsItem.getValueAsString();
			if (web_site_group == null || web_site_group.trim().equals("")) {
				SC.say("გთხოვთ აირჩიოთ კატეგორია");
				return;
			}
			Integer web_site_group_id = new Integer(web_site_group);
			String address = addressItem.getValueAsString();
			String remark = remarkItem.getValueAsString();

			if (address == null || address.trim().equals("")) {
				SC.say("გთხოვთ შეიყვანოთ მიოსამართი");
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("web_site_group_id", web_site_group_id);
			record.setAttribute("address", address);
			record.setAttribute("remark", remark);

			if (editRecord != null) {
				record.setAttribute("web_site_id",
						editRecord.getAttributeAsInt("web_site_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addWebSites");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateWebSites");
				listGrid.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			}
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
