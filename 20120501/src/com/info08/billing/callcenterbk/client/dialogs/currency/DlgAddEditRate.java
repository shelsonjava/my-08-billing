package com.info08.billing.callcenterbk.client.dialogs.currency;

import com.info08.billing.callcenterbk.client.CallCenterBK;
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
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditRate extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem currNameGeoItem;
	private TextItem currAbbrItem;
	private FloatItem rateCoeffItem;
	private FloatItem rateItem;
	private FloatItem marketRateItem;
	private FloatItem salesMarketRateItem;

	private ListGridRecord editRecord;

	public DlgAddEditRate(ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			setTitle(CallCenterBK.constants.rates());

			setHeight(235);
			setWidth(480);
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
			dynamicForm.setTitleWidth(320);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			currNameGeoItem = new TextItem();
			currNameGeoItem.setTitle(CallCenterBK.constants.currencyName());
			currNameGeoItem.setName("name_descr");
			currNameGeoItem.setWidth(300);
			currNameGeoItem.setCanEdit(false);

			currAbbrItem = new TextItem();
			currAbbrItem.setTitle(CallCenterBK.constants.currencyAbbr());
			currAbbrItem.setName("code");
			currAbbrItem.setWidth(300);
			currAbbrItem.setCanEdit(false);

			rateCoeffItem = new FloatItem();
			rateCoeffItem.setTitle(CallCenterBK.constants.rateCoeff());
			rateCoeffItem.setName("coefficient");
			rateCoeffItem.setWidth(300);
			rateCoeffItem.setKeyPressFilter("[0-9]");

			rateItem = new FloatItem();
			rateItem.setTitle(CallCenterBK.constants.rate());
			rateItem.setName("national_course");
			rateItem.setWidth(300);
			rateItem.setKeyPressFilter("[0-9\\.]");

			marketRateItem = new FloatItem();
			marketRateItem.setTitle(CallCenterBK.constants.marketRate());
			marketRateItem.setName("bank_buy_course");
			marketRateItem.setWidth(300);
			marketRateItem.setKeyPressFilter("[0-9\\.]");

			salesMarketRateItem = new FloatItem();
			salesMarketRateItem.setTitle(CallCenterBK.constants
					.salesMarketRate());
			salesMarketRateItem.setName("bank_sell_course");
			salesMarketRateItem.setWidth(300);
			salesMarketRateItem.setKeyPressFilter("[0-9\\.]");

			dynamicForm.setFields(currNameGeoItem, currAbbrItem, rateCoeffItem,
					rateItem, marketRateItem, salesMarketRateItem);

			dynamicForm.focusInItem(rateCoeffItem);

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
			currNameGeoItem.setValue(editRecord
					.getAttributeAsString("name_descr"));
			currAbbrItem.setValue(editRecord.getAttributeAsString("code"));

			DataSource rateDS = DataSource.get("CurrencyCourseDS");
			Criteria criteria = new Criteria();
			Integer currency_id = editRecord.getAttributeAsInt("currency_id");
			if (currency_id != null) {
				criteria.setAttribute("currency_id", currency_id);
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchAllCurrencyCourse");
			rateDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						Record record = records[records.length - 1];
						if (record != null) {
							String coefficient = record
									.getAttributeAsString("coefficient");
							if (coefficient != null
									&& !coefficient.trim().equalsIgnoreCase("")) {
								rateCoeffItem.setValue(coefficient);
							}
							String rate = record
									.getAttributeAsString("national_course");
							if (rate != null
									&& !rate.trim().equalsIgnoreCase("")) {
								rateItem.setValue(rate);
							}
							String bank_buy_course = record
									.getAttributeAsString("bank_buy_course");
							if (bank_buy_course != null
									&& !bank_buy_course.trim()
											.equalsIgnoreCase("")) {
								marketRateItem.setValue(bank_buy_course);
							}
							String bank_sell_course = record
									.getAttributeAsString("bank_sell_course");
							if (bank_sell_course != null
									&& !bank_sell_course.trim()
											.equalsIgnoreCase("")) {
								salesMarketRateItem.setValue(bank_sell_course);
							}
						}
					}
				}
			}, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String name_descr = currNameGeoItem.getValueAsString();
			if (name_descr == null || name_descr.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterCurrencyName());
				return;
			}
			String code = currAbbrItem.getValueAsString();
			if (code == null || code.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterCurrencyAbbr());
				return;
			}
			String coefficient_str = rateCoeffItem.getValueAsString();
			if (coefficient_str == null || coefficient_str.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterRateCoeff());
				return;
			}
			if (!rateCoeffItem.validate()) {
				return;
			}
			if (!rateItem.validate()) {
				return;
			}
			if (!marketRateItem.validate()) {
				return;
			}
			if (!salesMarketRateItem.validate()) {
				return;
			}
			Integer coefficient = null;
			try {
				coefficient = new Integer(coefficient_str);
			} catch (NumberFormatException e) {
				SC.say(CallCenterBK.constants.rateCoeffIsInteger());
				return;
			}
			String national_course_str = rateItem.getValueAsString();
			try {
				new Float(national_course_str);
			} catch (NumberFormatException e) {
				SC.say(CallCenterBK.constants.rateIsFloat());
				return;
			}
			String bank_buy_course_str = marketRateItem.getValueAsString();
			try {
				new Float(bank_buy_course_str);
			} catch (NumberFormatException e) {
				SC.say(CallCenterBK.constants.marketRateIsFloat());
				return;
			}
			String bank_sell_course_str = salesMarketRateItem
					.getValueAsString();
			try {
				new Float(bank_sell_course_str);
			} catch (NumberFormatException e) {
				SC.say(CallCenterBK.constants.saleMarketRateIsFloat());
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("name_descr", name_descr);
			record.setAttribute("code", code);
			record.setAttribute("coefficient", coefficient);
			record.setAttribute("bank_buy_course", bank_buy_course_str);
			record.setAttribute("bank_sell_course", bank_sell_course_str);
			record.setAttribute("national_course", national_course_str);
			record.setAttribute("currency_id",
					editRecord.getAttributeAsInt("currency_id"));

			DataSource rateDS = DataSource.get("CurrencyCourseDS");
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateCurrencyCourse");
			rateDS.updateData(record, new DSCallback() {
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
