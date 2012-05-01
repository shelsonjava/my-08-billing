package com.info08.billing.callcenterbk.client.dialogs.misc;

import com.info08.billing.callcenterbk.client.CallCenterBK;
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

	private ComboBoxItem mainDetTypeItem;
	private TextItem mainDetailGeoItem;
	private TextItem mainDetailEngItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditWebSite(ListGrid listGrid, ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenterBK.constants.addSiteTitle()
					: CallCenterBK.constants.editSiteTitle());

			setHeight(160);
			setWidth(430);
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
			dynamicForm.setTitleWidth(100);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			mainDetTypeItem = new ComboBoxItem();
			mainDetTypeItem.setTitle(CallCenterBK.constants.group());
			mainDetTypeItem.setWidth(300);
			mainDetTypeItem.setName("main_detail_type_name_geo");
			mainDetTypeItem.setFetchMissingValues(true);
			mainDetTypeItem.setFilterLocally(false);
			mainDetTypeItem.setAddUnknownValues(false);

			DataSource mainDetTypeDS = DataSource.get("MainDetTypeDS");
			mainDetTypeItem
					.setOptionOperationId("searchMainDetailTypesFirWebSites");
			mainDetTypeItem.setOptionDataSource(mainDetTypeDS);
			mainDetTypeItem.setValueField("main_detail_type_id");
			mainDetTypeItem.setDisplayField("main_detail_type_name_geo");

			mainDetTypeItem.setOptionCriteria(new Criteria());
			mainDetTypeItem.setAutoFetchData(false);

			mainDetTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = mainDetTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("main_detail_type_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("main_detail_type_id", nullO);
						}
					}
				}
			});

			mainDetailGeoItem = new TextItem();
			mainDetailGeoItem.setTitle(CallCenterBK.constants.description());
			mainDetailGeoItem.setName("main_detail_geo");
			mainDetailGeoItem.setWidth(300);

			mainDetailEngItem = new TextItem();
			mainDetailEngItem.setTitle(CallCenterBK.constants.webSite());
			mainDetailEngItem.setName("main_detail_eng");
			mainDetailEngItem.setWidth(300);

			dynamicForm.setFields(mainDetTypeItem, mainDetailGeoItem,
					mainDetailEngItem);

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
			fillFields();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			mainDetTypeItem.setValue(editRecord.getAttributeAsInt("main_detail_type_id"));
			mainDetailGeoItem.setValue(editRecord.getAttributeAsString("main_detail_geo"));
			mainDetailEngItem.setValue(editRecord.getAttributeAsString("main_detail_eng"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String main_detail_type_id_str = mainDetTypeItem.getValueAsString();
			if (main_detail_type_id_str == null || main_detail_type_id_str.trim().equals("")) {
				SC.say(CallCenterBK.constants.chooseSiteGroup());
				return;
			}
			String main_detail_geo = mainDetailGeoItem.getValueAsString();
			if (main_detail_geo == null || main_detail_geo.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterDescription());
				return;
			}
			String main_detail_eng = mainDetailEngItem.getValueAsString();
			if (main_detail_eng == null || main_detail_eng.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterWebSite());
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("deleted", 0);
			record.setAttribute("service_id", 63);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("main_detail_type_id",new Integer(main_detail_type_id_str));
			record.setAttribute("main_detail_geo", main_detail_geo);
			record.setAttribute("main_detail_eng", main_detail_eng);
			record.setAttribute("fields_order", 0);
			record.setAttribute("main_detail_master_id", 0);
			record.setAttribute("main_id", -100);
			record.setAttribute("old_id", 0);

			if (editRecord != null) {
				record.setAttribute("main_detail_id",editRecord.getAttributeAsInt("main_detail_id"));
				record.setAttribute("main_id",editRecord.getAttributeAsInt("main_id"));
			}

			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addMainDetail");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateMainDetail");
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
