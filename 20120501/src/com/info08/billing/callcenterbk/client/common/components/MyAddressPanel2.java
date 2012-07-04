package com.info08.billing.callcenterbk.client.common.components;

import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.client.utils.FormItemDescr;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class MyAddressPanel2 extends HLayout {

	protected Integer width;
	protected Integer height;

	private DynamicForm dynamicForm;

	private ComboBoxItem addrTownItem;
	private ComboBoxItem addrStreetItem;
	private ComboBoxItem addrRegionItem;
	private TextItem addrStreetIdxItem;
	private SelectItem adressOpCloseItem;
	private TextItem adressItem;
	private TextItem blockItem;
	private TextItem appartItem;
	private TextItem oldAddItem;
	private TextItem addrDescrItem;
	private CheckboxItem turnOffItem;
	protected String addressName;

	private String myIdField = "addr_id";
	private DataSource myDataSource = DataSource.get("AddressDS");
	private String myDataSourceOperation = "addressSearch";
	private HeaderItem addHeaderItem;
	private String title;
	private FormItem header;

	public MyAddressPanel2(Boolean needTurnOffCheck, String addressName,
			String title, Integer width, Integer height) {

		this.height = height;
		this.width = width;
		this.addressName = addressName;
		this.title = title;

		setWidth(width);
		setPadding(0);
		setMargin(0);

		dynamicForm = new DynamicForm();
		dynamicForm.setAutoFocus(false);
		dynamicForm.setWidth100();
		dynamicForm.setNumCols(4);
		dynamicForm.setTitleOrientation(TitleOrientation.TOP);

		addrTownItem = new ComboBoxItem();
		addrTownItem.setTitle(CallCenterBK.constants.town());
		addrTownItem.setName("town_id");
		addrTownItem.setWidth(160);
		ClientUtils.fillCombo(addrTownItem, "TownsDS",
				"searchCitiesFromDBForCombos", "town_id", "town_name");
		addrTownItem.setValue(Constants.defCityTbilisiId);

		addrStreetItem = new ComboBoxItem();
		addrStreetItem.setTitle(CallCenterBK.constants.street());
		addrStreetItem.setName("street_id");
		addrStreetItem.setWidth(270);
		addrStreetItem.setColSpan(2);

		Map<String, Integer> aditionalCriteria = new TreeMap<String, Integer>();
		aditionalCriteria.put("town_id", Constants.defCityTbilisiId);
		aditionalCriteria.put("need_indexes", 1);

		ClientUtils.fillCombo(addrStreetItem, "StreetsDS",
				"searchStreetFromDBForCombos", "street_id", "street_name",
				aditionalCriteria);

		addrRegionItem = new ComboBoxItem();
		addrRegionItem.setTitle(CallCenterBK.constants.district());
		addrRegionItem.setName("town_district_id");
		addrRegionItem.setWidth(160);

		Map<String, Integer> aditionalCriteria1 = new TreeMap<String, Integer>();
		aditionalCriteria1.put("town_id", Constants.defCityTbilisiId);

		ClientUtils.fillCombo(addrRegionItem, "TownDistrictDS",
				"searchCityRegsFromDBForCombos", "town_district_id",
				"town_district_name", aditionalCriteria1);

		addrStreetIdxItem = new TextItem();
		addrStreetIdxItem.setTitle(CallCenterBK.constants.streetIdx());
		addrStreetIdxItem.setName("street_index_text");
		addrStreetIdxItem.setWidth(270);
		addrStreetIdxItem.setColSpan(2);
		addrStreetIdxItem.setCanEdit(true);

		adressOpCloseItem = new SelectItem();
		adressOpCloseItem.setValueMap(ClientMapUtil.getInstance()
				.getAddrMapOpClose());
		adressOpCloseItem.setDefaultToFirstOption(true);
		adressOpCloseItem.setTitle(CallCenterBK.constants.openClose());
		adressOpCloseItem.setName("hidden_by_request");
		adressOpCloseItem.setWidth(160);
		adressOpCloseItem.setFetchMissingValues(false);

		adressItem = new TextItem();
		adressItem.setTitle(CallCenterBK.constants.home());
		adressItem.setName("anumber");
		adressItem.setWidth(160);

		blockItem = new TextItem();
		blockItem.setTitle(CallCenterBK.constants.block());
		blockItem.setName("block");
		blockItem.setWidth(160);

		appartItem = new TextItem();
		appartItem.setTitle(CallCenterBK.constants.appartment());
		appartItem.setName("appt");
		appartItem.setWidth(50);

		oldAddItem = new TextItem();
		oldAddItem.setTitle(CallCenterBK.constants.oldAddress());
		oldAddItem.setName("full_address");
		oldAddItem.setWidth(160);
		oldAddItem.setCanEdit(false);

		addrDescrItem = new TextItem();
		addrDescrItem.setTitle(CallCenterBK.constants.addInfo());
		addrDescrItem.setName("descr");
		addrDescrItem.setWidth(220);

		if (needTurnOffCheck == null) {
			addHeaderItem = new HeaderItem();
			addHeaderItem.setValue(title);
			addHeaderItem.setTextBoxStyle("headerStyle");
			header = addHeaderItem;
		} else {
			turnOffItem = new CheckboxItem();
			turnOffItem.setTitle(title);
			turnOffItem.setName("turnOffItem");
			turnOffItem.setWidth("100%");
			turnOffItem.setColSpan(4);
			turnOffItem.setDisabled(!needTurnOffCheck);
			turnOffItem.setValue(!needTurnOffCheck);
			header = turnOffItem;
			turnOffItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					setCleared();
				}
			});
		}

		ClientUtils.makeDependancy(addrTownItem, "town_id", addrStreetItem,
				addrRegionItem);

		ClientUtils.makeDependancy(addrTownItem, true, new FormItemDescr(
				addrStreetIdxItem, "", "k"));
		ClientUtils.makeDependancy(addrStreetItem, true, new FormItemDescr(
				addrRegionItem, "street_id", "town_district_id"),
				new FormItemDescr(addrStreetIdxItem, "", "street_index"));

		dynamicForm.setFields(header, addrTownItem, addrRegionItem,
				addrStreetItem, adressOpCloseItem, oldAddItem,
				addrStreetIdxItem, adressItem, blockItem, appartItem,
				addrDescrItem);

		addMember(dynamicForm);
	}

	public void setValue(Integer addressa_id_value) {
		dynamicForm.clearValues();
		if (addressa_id_value == null) {
			if (turnOffItem != null) {
				turnOffItem.setValue(turnOffItem.isDisabled());
				setCleared();
			}
			if (turnOffItem == null) {
				header.setValue(title);
			}
			return;
		}

		if (turnOffItem != null) {
			turnOffItem.setValue(true);
		}

		Criteria cr = new Criteria();
		cr.setAttribute(myIdField, addressa_id_value);
		DSRequest req = new DSRequest();
		if (myDataSourceOperation != null) {
			req.setOperationId(myDataSourceOperation);
		}
		myDataSource.fetchData(cr, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				Record[] records = response.getData();
				if (records == null || records.length == 0) {
					dynamicForm.clearValues();
					if (turnOffItem != null) {
						turnOffItem.setValue(false);
						setCleared();
					}
					return;
				}
				Record record = records[0];

				Criteria cr = addrStreetItem.getOptionCriteria();
				if (cr == null) {
					cr = new Criteria();
				}
				cr.setAttribute("town_id", record.getAttributeAsInt("town_id"));
				addrStreetItem.setOptionCriteria(cr);

				Criteria cr1 = addrRegionItem.getOptionCriteria();
				if (cr1 == null) {
					cr1 = new Criteria();
				}
				cr1.setAttribute("street_id",
						record.getAttributeAsInt("street_id"));
				addrRegionItem.setOptionCriteria(cr1);

				dynamicForm.setValues(record.toMap());
				header.setTitle(title);
				if (turnOffItem != null) {
					turnOffItem.setValue(true);
				}

			}
		}, req);

	}

	public void setValues(Map<?, ?> values) {
		boolean checkBoxValue = (turnOffItem == null) ? false : turnOffItem
				.getValueAsBoolean();
		dynamicForm.setValues(values);
		header.setTitle(title);
		if (turnOffItem != null) {
			turnOffItem.setValue(checkBoxValue);
		}
	}

	public Map<?, ?> getValues() {
		if (turnOffItem != null && !turnOffItem.getValueAsBoolean()) {
			return null;
		}
		return dynamicForm.getValues();
	}

	public DynamicForm getDynamicForm() {
		return dynamicForm;
	}

	public void setDynamicForm(DynamicForm dynamicForm) {
		this.dynamicForm = dynamicForm;
	}

	public ComboBoxItem getAddrTownItem() {
		return addrTownItem;
	}

	public void setAddrTownItem(ComboBoxItem addrTownItem) {
		this.addrTownItem = addrTownItem;
	}

	public ComboBoxItem getAddrStreetItem() {
		return addrStreetItem;
	}

	public void setAddrStreetItem(ComboBoxItem addrStreetItem) {
		this.addrStreetItem = addrStreetItem;
	}

	public ComboBoxItem getAddrRegionItem() {
		return addrRegionItem;
	}

	public void setAddrRegionItem(ComboBoxItem addrRegionItem) {
		this.addrRegionItem = addrRegionItem;
	}

	public TextItem getAddrStreetIdxItem() {
		return addrStreetIdxItem;
	}

	public void setAddrStreetIdxItem(TextItem addrStreetIdxItem) {
		this.addrStreetIdxItem = addrStreetIdxItem;
	}

	public SelectItem getAdressOpCloseItem() {
		return adressOpCloseItem;
	}

	public void setAdressOpCloseItem(SelectItem adressOpCloseItem) {
		this.adressOpCloseItem = adressOpCloseItem;
	}

	public TextItem getAdressItem() {
		return adressItem;
	}

	public void setAdressItem(TextItem adressItem) {
		this.adressItem = adressItem;
	}

	public TextItem getBlockItem() {
		return blockItem;
	}

	public void setBlockItem(TextItem blockItem) {
		this.blockItem = blockItem;
	}

	public TextItem getAppartItem() {
		return appartItem;
	}

	public void setAppartItem(TextItem appartItem) {
		this.appartItem = appartItem;
	}

	public TextItem getOldAddItem() {
		return oldAddItem;
	}

	public void setOldAddItem(TextItem oldAddItem) {
		this.oldAddItem = oldAddItem;
	}

	private void setCleared() {
		if (turnOffItem.isDisabled()) {
			addrTownItem.setValue(Constants.defCityTbilisiId);
			return;
		}
		boolean disabled = !turnOffItem.getValueAsBoolean().booleanValue();
		FormItem[] fields = dynamicForm.getFields();
		for (FormItem formItem : fields) {
			if (!formItem.equals(turnOffItem)) {
				formItem.setDisabled(disabled);
			}
		}
		addrTownItem.setValue(Constants.defCityTbilisiId);
		if (disabled) {
			dynamicForm.clearValues();
		}
	}

	public CheckboxItem getTurnOffItem() {
		return turnOffItem;
	}

	public boolean isCheckedTurnOffItem() {
		if (turnOffItem == null || turnOffItem.isDisabled()) {
			return true;
		}
		return turnOffItem.getValueAsBoolean().booleanValue();
	}
}
