package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgUpdateContrCurrRangePrice extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem rangeCurrPriceItem;
	private ListGridRecord listGridRecord;
	private ListGrid listGrid;

	public DlgUpdateContrCurrRangePrice(ListGridRecord listGridRecord,
			ListGrid listGrid) {
		try {
			this.listGridRecord = listGridRecord;
			this.listGrid = listGrid;
			setTitle(CallCenterBK.constants.setCurrentPrice());

			setHeight(110);
			setWidth(350);
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

			String range_curr_price = listGridRecord
					.getAttributeAsString("range_curr_price");

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setNumCols(2);
			dynamicForm.setTitleWidth(120);
			hLayout.addMember(dynamicForm);

			rangeCurrPriceItem = new TextItem();
			rangeCurrPriceItem.setTitle(CallCenterBK.constants
					.setCurrentPrice());
			rangeCurrPriceItem.setWidth("100%");
			rangeCurrPriceItem.setName("rangeCurrPriceItem");
			rangeCurrPriceItem.setKeyPressFilter("[0-9\\.]");
			rangeCurrPriceItem.setValue(range_curr_price);

			dynamicForm.setFields(rangeCurrPriceItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.save());
			saveItem.setWidth(100);

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
			String range_curr_price = rangeCurrPriceItem.getValueAsString();
			if (range_curr_price == null || range_curr_price.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterAmount());
				return;
			}
			try {
				Float.parseFloat(range_curr_price);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.invalidAmount());
				return;
			}
			Record record = new Record();
			record.setAttribute("corporate_client_id",
					listGridRecord.getAttributeAsInt("corporate_client_id"));
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("range_curr_price", range_curr_price);
			com.smartgwt.client.rpc.RPCManager.startQueue();
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateContractorRangePrice");
			listGrid.updateData(record, new DSCallback() {
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
