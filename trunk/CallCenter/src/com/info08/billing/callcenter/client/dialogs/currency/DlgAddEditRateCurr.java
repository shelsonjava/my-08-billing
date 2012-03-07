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

			setTitle(pRecord == null ? CallCenter.constants.addCurrencyTitle()
					: CallCenter.constants.modifyCurrencyTitle());

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
			countryItem.setTitle(CallCenter.constants.country());
			countryItem.setWidth(300);
			countryItem.setName("country_id");
			countryItem.setFetchMissingValues(true);
			countryItem.setFilterLocally(false);
			countryItem.setAddUnknownValues(false);

			DataSource countryDS = DataSource.get("CountryDS");
			countryItem.setOptionOperationId("searchAllCountriesForCombos");
			countryItem.setOptionDataSource(countryDS);
			countryItem.setValueField("country_id");
			countryItem.setDisplayField("country_name_geo");

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
			currNameGeoItem.setTitle(CallCenter.constants.currencyName());
			currNameGeoItem.setName("curr_name_geo");
			currNameGeoItem.setWidth(300);

			currAbbrItem = new TextItem();
			currAbbrItem.setTitle(CallCenter.constants.currencyAbbr());
			currAbbrItem.setName("curr_abbrev");
			currAbbrItem.setWidth(300);

			currOrderItem = new SpinnerItem();
			currOrderItem.setTitle(CallCenter.constants.order());
			currOrderItem.setMin(0);
			currOrderItem.setMax(10000);
			currOrderItem.setStep(1);
			currOrderItem.setWidth(300);
			currOrderItem.setName("curr_order");

			dynamicForm.setFields(countryItem, currNameGeoItem, currAbbrItem,
					currOrderItem);

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
			countryItem.setValue(editRecord.getAttributeAsString("country_id"));
			currNameGeoItem.setValue(editRecord.getAttributeAsString("curr_name_geo"));
			currAbbrItem.setValue(editRecord.getAttributeAsString("curr_abbrev"));
			currOrderItem.setValue(editRecord.getAttributeAsString("curr_order"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String country_id_str = countryItem.getValueAsString();
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
			String curr_order_str = currOrderItem.getValueAsString();
			Integer curr_order = null;
			if (curr_order_str != null) {
				try {
					curr_order = new Integer(curr_order_str);
				} catch (NumberFormatException e) {
					SC.say(CallCenter.constants.currOrderMustBeNum());
					return;
				}
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("loggedUserName", loggedUser);
			if (country_id_str != null && !country_id_str.trim().equals("")) {
				record.setAttribute("country_id", new Integer(country_id_str));	
			}
			record.setAttribute("curr_name_geo", curr_name_geo);
			record.setAttribute("curr_abbrev", curr_abbrev);
			record.setAttribute("curr_order", curr_order);
			record.setAttribute("deleted", 0);
			record.setAttribute("rec_user", loggedUser);

			if (editRecord != null) {
				record.setAttribute("curr_id",
						editRecord.getAttributeAsInt("curr_id"));
			}

			DSRequest req = new DSRequest();

			if (editRecord == null) {
				req.setAttribute("operationId", "addRateCurr");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateRateCurr");
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
