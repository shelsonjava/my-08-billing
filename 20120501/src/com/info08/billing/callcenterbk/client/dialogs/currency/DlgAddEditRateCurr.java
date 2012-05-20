package com.info08.billing.callcenterbk.client.dialogs.currency;

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
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditRateCurr extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem countryItem;
	private TextItem currNameGeoItem;
	private TextItem currAbbrItem;
	private SpinnerItem currOrderItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditRateCurr(ListGrid listGrid, ListGridRecord pRecord) {
		try {

			this.editRecord = pRecord;
			this.listGrid = listGrid;

			setTitle(pRecord == null ? CallCenterBK.constants
					.addCurrencyTitle() : CallCenterBK.constants
					.modifyCurrencyTitle());

			setHeight(180);
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

			countryItem = new ComboBoxItem();
			countryItem.setTitle(CallCenterBK.constants.country());
			countryItem.setWidth(300);
			countryItem.setName("country_id");
			countryItem.setFetchMissingValues(true);
			countryItem.setFilterLocally(false);
			countryItem.setAddUnknownValues(false);

			DataSource countryDS = DataSource.get("CountryDS");
			countryItem.setOptionOperationId("searchAllCountriesForCombos");
			countryItem.setOptionDataSource(countryDS);
			countryItem.setValueField("country_id");
			countryItem.setDisplayField("country_name");

			countryItem.setOptionCriteria(new Criteria());
			countryItem.setAutoFetchData(false);

			countryItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = countryItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria.getAttribute("country_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("country_id", nullO);
						}
					}
				}
			});

			currNameGeoItem = new TextItem();
			currNameGeoItem.setTitle(CallCenterBK.constants.currencyName());
			currNameGeoItem.setName("name_descr");
			currNameGeoItem.setWidth(300);

			currAbbrItem = new TextItem();
			currAbbrItem.setTitle(CallCenterBK.constants.currencyAbbr());
			currAbbrItem.setName("code");
			currAbbrItem.setWidth(300);

			currOrderItem = new SpinnerItem();
			currOrderItem.setTitle(CallCenterBK.constants.order());
			currOrderItem.setMin(0);
			currOrderItem.setMax(10000);
			currOrderItem.setStep(1);
			currOrderItem.setWidth(300);
			currOrderItem.setName("sort_order");

			dynamicForm.setFields(countryItem, currNameGeoItem, currAbbrItem,
					currOrderItem);

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
			countryItem.setValue(editRecord.getAttributeAsString("country_id"));
			currNameGeoItem.setValue(editRecord
					.getAttributeAsString("name_descr"));
			currAbbrItem.setValue(editRecord.getAttributeAsString("code"));
			currOrderItem.setValue(editRecord
					.getAttributeAsString("sort_order"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String country_id_str = countryItem.getValueAsString();
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
			String sort_order_str = currOrderItem.getValueAsString();
			Integer sort_order = null;
			if (sort_order_str != null) {
				try {
					sort_order = new Integer(sort_order_str);
				} catch (NumberFormatException e) {
					SC.say(CallCenterBK.constants.currOrderMustBeNum());
					return;
				}
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			if (country_id_str != null && !country_id_str.trim().equals("")) {
				record.setAttribute("country_id", new Integer(country_id_str));
			}
			record.setAttribute("name_descr", name_descr);
			record.setAttribute("code", code);
			record.setAttribute("sort_order", sort_order);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("currency_id",
						editRecord.getAttributeAsInt("currency_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addCurrency");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateCurrency");
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
