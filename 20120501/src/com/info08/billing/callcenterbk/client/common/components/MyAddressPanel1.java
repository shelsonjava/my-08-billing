package com.info08.billing.callcenterbk.client.common.components;

import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.client.utils.FormItemDescr;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class MyAddressPanel1 extends HLayout {

	protected Integer width;
	protected Integer height;

	private DynamicForm dynamicForm;

	private ComboBoxItem addrTownItem;
	private ComboBoxItem addrStreetItem;
	private ComboBoxItem addrRegionItem;
	private TextAreaItem addrStreetDescrItem;
	private TextAreaItem addrStreetIdxItem;
	private SelectItem adressOpCloseItem;
	private TextItem adressItem;
	private TextItem blockItem;
	private TextItem appartItem;
	private TextItem oldAddItem;
	protected String addressName;

	public MyAddressPanel1(String addressName, String title, Integer width,
			Integer height) {

		this.height = height;
		this.width = width;
		this.addressName = addressName;

		setWidth(width);
		setPadding(0);
		setMargin(0);

		dynamicForm = new DynamicForm();
		dynamicForm.setAutoFocus(true);
		dynamicForm.setWidth100();
		dynamicForm.setNumCols(4);
		dynamicForm.setTitleOrientation(TitleOrientation.TOP);

		addrTownItem = new ComboBoxItem();
		addrTownItem.setTitle(CallCenterBK.constants.town());
		addrTownItem.setName(addressName + "_town_id");
		addrTownItem.setWidth(200);
		ClientUtils.fillCombo(addrTownItem, "TownsDS",
				"searchCitiesFromDBForCombos", "town_id", "town_name");
		addrTownItem.setValue(Constants.defCityTbilisiId);

		addrStreetItem = new ComboBoxItem();
		addrStreetItem.setTitle(CallCenterBK.constants.street());
		addrStreetItem.setName(addressName + "_street_id");
		addrStreetItem.setWidth(414);

		Map<String, Integer> aditionalCriteria = new TreeMap<String, Integer>();
		aditionalCriteria.put("town_id", Constants.defCityTbilisiId);
		aditionalCriteria.put("need_indexes", 1);

		ClientUtils.fillCombo(addrStreetItem, "StreetsDS",
				"searchStreetFromDBForCombos", "street_id", "street_name",
				aditionalCriteria);

		addrRegionItem = new ComboBoxItem();
		addrRegionItem.setTitle(CallCenterBK.constants.district());
		addrRegionItem.setName(addressName + "_town_district_id");
		addrRegionItem.setWidth(200);

		Map<String, Integer> aditionalCriteria1 = new TreeMap<String, Integer>();
		aditionalCriteria1.put("town_id", Constants.defCityTbilisiId);

		ClientUtils.fillCombo(addrRegionItem, "TownDistrictDS",
				"searchCityRegsFromDBForCombos", "town_district_id",
				"town_district_name", aditionalCriteria1);

		addrStreetDescrItem = new TextAreaItem();
		addrStreetDescrItem.setTitle(CallCenterBK.constants.streetDescr());
		addrStreetDescrItem.setName(addressName + "_streetDescrItem");
		addrStreetDescrItem.setWidth(614);
		addrStreetDescrItem.setHeight(49);
		addrStreetDescrItem.setCanEdit(false);
		addrStreetDescrItem.setColSpan(2);

		addrStreetIdxItem = new TextAreaItem();
		addrStreetIdxItem.setTitle(CallCenterBK.constants.streetIdx());
		addrStreetIdxItem.setName(addressName + "_street_index");
		addrStreetIdxItem.setWidth(614);
		addrStreetIdxItem.setHeight(49);
		addrStreetIdxItem.setColSpan(2);
		addrStreetIdxItem.setCanEdit(false);

		adressOpCloseItem = new SelectItem();
		adressOpCloseItem.setValueMap(ClientMapUtil.getInstance()
				.getAddrMapOpClose());
		adressOpCloseItem.setDefaultToFirstOption(true);
		adressOpCloseItem.setTitle(CallCenterBK.constants.openClose());
		adressOpCloseItem.setName(addressName + "_legalAdressOpCloseItem");
		adressOpCloseItem.setWidth(414);
		adressOpCloseItem.setFetchMissingValues(false);

		adressItem = new TextItem();
		adressItem.setTitle(CallCenterBK.constants.home());
		adressItem.setName(addressName + "_legalAdressItem");
		adressItem.setWidth(414);

		blockItem = new TextItem();
		blockItem.setTitle(CallCenterBK.constants.block());
		blockItem.setName(addressName + "_legalBlockItem");
		blockItem.setWidth(200);

		appartItem = new TextItem();
		appartItem.setTitle(CallCenterBK.constants.appartment());
		appartItem.setName(addressName + "_appartItem");
		appartItem.setWidth(414);

		oldAddItem = new TextItem();
		oldAddItem.setTitle(CallCenterBK.constants.oldAddress());
		oldAddItem.setName(addressName + "_legalOldAddItem");
		oldAddItem.setWidth(200);
		oldAddItem.setCanEdit(false);

		HeaderItem legalAddHeaderItem = new HeaderItem();
		legalAddHeaderItem.setValue(title);
		legalAddHeaderItem.setTextBoxStyle("headerStyle");

		ClientUtils.makeDependancy(addrTownItem, "town_id", addrStreetItem,
				addrRegionItem);

		ClientUtils.makeDependancy(addrTownItem, true, new FormItemDescr(
				addrStreetDescrItem, "", "d"), new FormItemDescr(
				addrStreetIdxItem, "", "k"));
		ClientUtils.makeDependancy(addrStreetItem, true, new FormItemDescr(
				addrRegionItem, "street_id", "town_district_id"),
				new FormItemDescr(addrStreetDescrItem, "", "street_location"),
				new FormItemDescr(addrStreetIdxItem, "", "street_index"));

		dynamicForm.setFields(legalAddHeaderItem, addrTownItem, addrStreetItem,
				addrRegionItem, adressOpCloseItem, oldAddItem, adressItem,
				blockItem, appartItem, addrStreetDescrItem, addrStreetIdxItem);

		addMember(dynamicForm);
	}

	public Map<?, ?> getValues() {
		return dynamicForm.getValues();
	}

	public void setTownValue(Integer townValue) {
		if (townValue == null) {
			return;
		}
		addrTownItem.setValue(townValue);
		Criteria criteria = addrRegionItem.getOptionCriteria();
		if (criteria == null) {
			criteria = new Criteria();
		}
		criteria.setAttribute("town_id", townValue);
	}

	public void setStreetValue(Integer streetValue) {
		if (streetValue == null) {
			return;
		}
		addrStreetItem.setValue(streetValue);

		Criteria criteria = addrRegionItem.getOptionCriteria();
		if (criteria == null) {
			criteria = new Criteria();
		}
		criteria.setAttribute("street_id", streetValue);
		addrRegionItem.setOptionCriteria(criteria);
	}

	public void setStreetDistrictValue(Integer streetDistrictValue) {
		if (streetDistrictValue == null) {
			return;
		}
		addrRegionItem.setValue(streetDistrictValue);
	}

	public void setOpCloseValue(Integer opCloseValue) {
		if (opCloseValue == null) {
			return;
		}
		adressOpCloseItem.setValue(opCloseValue);
	}

	public void setOldAddressValue(String oldAddressValue) {
		if (oldAddressValue == null || oldAddressValue.trim().equals("")) {
			return;
		}
		oldAddItem.setValue(oldAddressValue);
	}

	public void setAdressValue(String adressValue) {
		if (adressValue == null || adressValue.trim().equals("")) {
			return;
		}
		adressItem.setValue(adressValue);
	}

	public void setBlockValue(String blockValue) {
		if (blockValue == null || blockValue.trim().equals("")) {
			return;
		}
		blockItem.setValue(blockValue);
	}

	public void setAppartValue(String appartValue) {
		if (appartValue == null || appartValue.trim().equals("")) {
			return;
		}
		appartItem.setValue(appartValue);
	}

	public void setStreetLocation(String streetLocation) {
		if (streetLocation == null || streetLocation.trim().equals("")) {
			return;
		}
		addrStreetDescrItem.setValue(streetLocation);
	}

	public void setStreetIndexes(String streetIndexes) {
		if (streetIndexes == null || streetIndexes.trim().equals("")) {
			return;
		}
		addrStreetIdxItem.setValue(streetIndexes);
	}

	public Integer getTownValue() {
		String townStr = addrTownItem.getValueAsString();
		return townStr == null ? null : new Integer(townStr);
	}

	public Integer getStreetValue() {
		String streetStr = addrStreetItem.getValueAsString();
		return streetStr == null ? null : new Integer(streetStr);
	}

	public Integer getStreetDistrictValue() {
		String distrStr = addrRegionItem.getValueAsString();
		return distrStr == null ? null : new Integer(distrStr);
	}

	public Integer getOpCloseValue() {
		String opCloseStr = adressOpCloseItem.getValueAsString();
		return opCloseStr == null ? null : new Integer(opCloseStr);
	}

	public String getOldAddressValue() {
		return oldAddItem.getValueAsString();
	}

	public String getAdressValue() {
		return adressItem.getValueAsString();
	}

	public String getBlockValue() {
		return blockItem.getValueAsString();
	}

	public String getAppartValue() {
		return appartItem.getValueAsString();
	}

	public String getStreetLocationValue() {
		return addrStreetDescrItem.getValueAsString();
	}

	public String getStreetIndexesValue() {
		return addrStreetIdxItem.getValueAsString();
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
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

	public TextAreaItem getAddrStreetDescrItem() {
		return addrStreetDescrItem;
	}

	public void setAddrStreetDescrItem(TextAreaItem addrStreetDescrItem) {
		this.addrStreetDescrItem = addrStreetDescrItem;
	}

	public TextAreaItem getAddrStreetIdxItem() {
		return addrStreetIdxItem;
	}

	public void setAddrStreetIdxItem(TextAreaItem addrStreetIdxItem) {
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

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

}
