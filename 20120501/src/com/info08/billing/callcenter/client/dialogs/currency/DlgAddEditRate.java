package com.info08.billing.callcenter.client.dialogs.currency;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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
			setTitle(CallCenter.constants.rates());

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
			currNameGeoItem.setTitle(CallCenter.constants.currencyName());
			currNameGeoItem.setName("curr_name_geo");
			currNameGeoItem.setWidth(300);
			currNameGeoItem.setCanEdit(false);

			currAbbrItem = new TextItem();
			currAbbrItem.setTitle(CallCenter.constants.currencyAbbr());
			currAbbrItem.setName("curr_abbrev");
			currAbbrItem.setWidth(300);
			currAbbrItem.setCanEdit(false);

			rateCoeffItem = new FloatItem();
			rateCoeffItem.setTitle(CallCenter.constants.rateCoeff());
			rateCoeffItem.setName("rate_coeff");
			rateCoeffItem.setWidth(300);
			rateCoeffItem.setKeyPressFilter("[0-9]");
			
			rateItem = new FloatItem();
			rateItem.setTitle(CallCenter.constants.rate());
			rateItem.setName("rate");
			rateItem.setWidth(300);
			rateItem.setKeyPressFilter("[0-9\\.]");

			marketRateItem = new FloatItem();
			marketRateItem.setTitle(CallCenter.constants.marketRate());
			marketRateItem.setName("market_rate");
			marketRateItem.setWidth(300);
			marketRateItem.setKeyPressFilter("[0-9\\.]");

			salesMarketRateItem = new FloatItem();
			salesMarketRateItem
					.setTitle(CallCenter.constants.salesMarketRate());
			salesMarketRateItem.setName("sale_market_rate");
			salesMarketRateItem.setWidth(300);
			salesMarketRateItem.setKeyPressFilter("[0-9\\.]");

			dynamicForm.setFields(currNameGeoItem, currAbbrItem, rateCoeffItem,
					rateItem, marketRateItem, salesMarketRateItem);

			dynamicForm.focusInItem(rateCoeffItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenter.constants.save());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenter.constants.close());
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
					.getAttributeAsString("curr_name_geo"));
			currAbbrItem.setValue(editRecord
					.getAttributeAsString("curr_abbrev"));

			DataSource rateDS = DataSource.get("RateDS");
			Criteria criteria = new Criteria();
			Integer curr_id = editRecord.getAttributeAsInt("curr_id");
			if (curr_id != null) {
				criteria.setAttribute("curr_id", curr_id);
			}
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchAllRates");
			rateDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						Record record = records[records.length - 1];
						if (record != null) {
							String rate_coeff = record
									.getAttributeAsString("rate_coeff");
							if (rate_coeff != null
									&& !rate_coeff.trim().equalsIgnoreCase("")) {
								rateCoeffItem.setValue(rate_coeff);
							}
							String rate = record.getAttributeAsString("rate");
							if (rate != null
									&& !rate.trim().equalsIgnoreCase("")) {
								rateItem.setValue(rate);
							}
							String market_rate = record
									.getAttributeAsString("market_rate");
							if (market_rate != null
									&& !market_rate.trim().equalsIgnoreCase("")) {
								marketRateItem.setValue(market_rate);
							}
							String sale_market_rate = record
									.getAttributeAsString("sale_market_rate");
							if (sale_market_rate != null
									&& !sale_market_rate.trim()
											.equalsIgnoreCase("")) {
								salesMarketRateItem.setValue(sale_market_rate);
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
			String curr_name_geo = currNameGeoItem.getValueAsString();
			if (curr_name_geo == null || curr_name_geo.trim().equals("")) {
				SC.say(CallCenter.constants.enterCurrencyName());
				return;
			}
			String curr_abbrev = currAbbrItem.getValueAsString();
			if (curr_abbrev == null || curr_abbrev.trim().equals("")) {
				SC.say(CallCenter.constants.enterCurrencyAbbr());
				return;
			}
			String rate_coeff_str = rateCoeffItem.getValueAsString();
			if (rate_coeff_str == null || rate_coeff_str.trim().equals("")) {
				SC.say(CallCenter.constants.enterRateCoeff());
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
			Integer rate_coeff = null;
			try {
				rate_coeff = new Integer(rate_coeff_str);
			} catch (NumberFormatException e) {
				SC.say(CallCenter.constants.rateCoeffIsInteger());
				return;
			}
			String rate_str = rateItem.getValueAsString();
			try {
				new Float(rate_str);
			} catch (NumberFormatException e) {
				SC.say(CallCenter.constants.rateIsFloat());
				return;
			}
			String market_rate_str = marketRateItem.getValueAsString();
			try {
				new Float(market_rate_str);
			} catch (NumberFormatException e) {
				SC.say(CallCenter.constants.marketRateIsFloat());
				return;
			}
			String sale_market_rate_str = salesMarketRateItem
					.getValueAsString();
			try {
				new Float(sale_market_rate_str);
			} catch (NumberFormatException e) {
				SC.say(CallCenter.constants.saleMarketRateIsFloat());
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("curr_name_geo", curr_name_geo);
			record.setAttribute("curr_abbrev", curr_abbrev);
			record.setAttribute("rate_coeff", rate_coeff);
			record.setAttribute("market_rate", market_rate_str);
			record.setAttribute("sale_market_rate", sale_market_rate_str);
			record.setAttribute("rate", rate_str);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("curr_id",
					editRecord.getAttributeAsInt("curr_id"));

			DataSource rateDS = DataSource.get("RateDS");
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "updateRate");
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
