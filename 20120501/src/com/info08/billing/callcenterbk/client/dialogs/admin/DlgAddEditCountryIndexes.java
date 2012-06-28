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

public class DlgAddEditCountryIndexes extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem countryIndexValueItem;
	private TextItem countryIndexRemarkGeoItem;
	private TextItem countryIndexRemarkEngItem;
	private ComboBoxItem countryItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;

	public DlgAddEditCountryIndexes(ListGrid listGrid, ListGridRecord pRecord,
			String country_id) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenterBK.constants
					.addMobOperatorPref() : CallCenterBK.constants
					.editMobOperatorPref());

			setHeight(190);
			setWidth(450);
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
			dynamicForm.setTitleWidth(200);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			countryIndexValueItem = new TextItem();
			countryIndexValueItem.setTitle(CallCenterBK.constants.index());
			countryIndexValueItem.setWidth(250);
			countryIndexValueItem.setName("countryIndexValueItem");

			countryIndexRemarkGeoItem = new TextItem();
			countryIndexRemarkGeoItem.setTitle(CallCenterBK.constants
					.indexRemarkGeo());
			countryIndexRemarkGeoItem.setWidth(250);
			countryIndexRemarkGeoItem.setName("countryIndexRemarkGeoItem");

			countryIndexRemarkEngItem = new TextItem();
			countryIndexRemarkEngItem.setTitle(CallCenterBK.constants
					.indexRemarkEng());
			countryIndexRemarkEngItem.setWidth(250);
			countryIndexRemarkEngItem.setName("countryIndexRemarkEngItem");

			countryItem = new ComboBoxItem();
			countryItem.setWidth(250);
			countryItem.setTitle("ქვეყანა");
			countryItem.setName("country_id");
			countryItem.setFetchMissingValues(true);
			countryItem.setFilterLocally(false);
			countryItem.setAddUnknownValues(false);

			countryItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = countryItem.getOptionCriteria();
					if (criteria != null) {
						String old_country_id = criteria
								.getAttribute("country_id");
						if (old_country_id != null) {
							Object nullO = null;
							criteria.setAttribute("country_id", nullO);
						}
					}
				}
			});

			dynamicForm.setFields(countryItem, countryIndexValueItem,
					countryIndexRemarkGeoItem, countryIndexRemarkEngItem);

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

			Integer edit_country_id = null;
			if (editRecord != null) {
				edit_country_id = editRecord.getAttributeAsInt("country_id");
			} else {
				if (country_id != null) {
					edit_country_id = new Integer(country_id);
				}
			}
			if (edit_country_id != null) {
				countryItem.setValue(edit_country_id);
			}

			fillCountryCombo();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillCountryCombo() {
		try {
			DataSource townsDS = DataSource.get("CountryDS");
			if (townsDS != null) {
				countryItem.setOptionOperationId("searchAllCountriesForCombos");
				countryItem.setOptionDataSource(townsDS);
				countryItem.setValueField("country_id");
				countryItem.setDisplayField("country_name");
				countryItem.setAutoFetchData(true);
				countryItem.fetchData(new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						if (editRecord != null) {
							Integer country_id = editRecord
									.getAttributeAsInt("country_id");
							if (country_id != null) {
								countryItem.setValue(country_id);

							}
						}
						countryItem.invalidateDisplayValueCache();
					}
				});
			}
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}
			countryItem.setValue(editRecord.getAttributeAsString("country_id"));
			countryIndexValueItem.setValue(editRecord
					.getAttributeAsString("country_index_value"));
			countryIndexRemarkGeoItem.setValue(editRecord
					.getAttributeAsString("country_index_remark_geo"));
			countryIndexRemarkEngItem.setValue(editRecord
					.getAttributeAsString("country_index_remark_eng"));
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String country_id = countryItem.getValueAsString();
			if (country_id == null || country_id.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterAmount());
				return;
			}

			String country_index_value = countryIndexValueItem
					.getValueAsString();
			if (country_index_value == null
					|| country_index_value.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterMobOperatorPrefix());
				return;
			}

			String country_index_remark_geo = countryIndexRemarkGeoItem
					.getValueAsString();
			if (country_index_remark_geo == null
					|| country_index_remark_geo.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterMobOperatorPrefix());
				return;
			}

			String country_index_remark_eng = countryIndexRemarkEngItem
					.getValueAsString();
			if (country_index_remark_eng == null
					|| country_index_remark_eng.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterMobOperatorPrefix());
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("country_id", country_id);
			record.setAttribute("country_index_value", country_index_value);
			record.setAttribute("country_index_remark_geo",
					country_index_remark_geo);
			record.setAttribute("country_index_remark_eng",
					country_index_remark_eng);

			if (editRecord != null) {
				record.setAttribute("country_index_id",
						editRecord.getAttributeAsInt("country_index_id"));
			}

			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addCountryIndexes");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateCountryIndexes");
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
