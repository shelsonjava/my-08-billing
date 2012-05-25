package com.info08.billing.callcenterbk.client.common.components;

import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.client.utils.FormItemDescr;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class MyAddressPanel extends HLayout {

	protected Integer width;
	protected Integer height;

	private DynamicForm dynamicForm;

	private ComboBoxItem addrTownItem;
	private ComboBoxItem addrStreetItem;
	private ComboBoxItem addrRegionItem;
	private TextAreaItem addrStreetDescrItem;
	private TextItem addrStreetIdxItem;
	private SelectItem adressOpCloseItem;
	private TextItem adressItem;
	private TextItem blockItem;
	private TextItem appartItem;
	private TextItem oldAddItem;
	protected String addressName;

	public MyAddressPanel(String addressName, String title, Integer width, Integer height) {

		this.height = height;
		this.width = width;
		this.addressName = addressName;

		//setHeight(height);
		setWidth(width);
		setPadding(0);
		setMargin(0);
		
		dynamicForm = new DynamicForm();
		dynamicForm.setAutoFocus(true);
		dynamicForm.setWidth100();
		dynamicForm.setNumCols(2);
		dynamicForm.setTitleOrientation(TitleOrientation.TOP);
		
		addrTownItem = new ComboBoxItem();
		addrTownItem.setTitle(CallCenterBK.constants.city());
		addrTownItem.setName("town_id");
		addrTownItem.setWidth(200);
		ClientUtils.fillCombo(addrTownItem, "TownsDS",
				"searchCitiesFromDBForCombos", "town_id", "town_name");

		addrStreetItem = new ComboBoxItem();
		addrStreetItem.setTitle(CallCenterBK.constants.street());
		addrStreetItem.setName("street_id");
		addrStreetItem.setWidth(400);

		Map<String, Integer> aditionalCriteria = new TreeMap<String, Integer>();
		aditionalCriteria.put("town_id", Constants.defCityTbilisiId);
		aditionalCriteria.put("need_indexes", 1);

		ClientUtils.fillCombo(addrStreetItem, "StreetsDS",
				"searchStreetFromDBForCombos", "street_id", "street_name",
				aditionalCriteria);

		addrRegionItem = new ComboBoxItem();
		addrRegionItem.setTitle(CallCenterBK.constants.district());
		addrRegionItem.setName("town_district_id");
		addrRegionItem.setWidth(200);

		ClientUtils.fillCombo(addrRegionItem, "TownDistrictDS",
				"searchCityRegsFromDBForCombos", "town_district_id",
				"town_district_name", aditionalCriteria);

		addrStreetDescrItem = new TextAreaItem();
		addrStreetDescrItem.setTitle(CallCenterBK.constants.streetDescr());
		addrStreetDescrItem.setName("streetDescrItem");
		addrStreetDescrItem.setWidth(600);
		addrStreetDescrItem.setHeight(49);
		addrStreetDescrItem.setCanEdit(false);
		addrStreetDescrItem.setColSpan(2);

		addrStreetIdxItem = new TextItem();
		addrStreetIdxItem.setTitle(CallCenterBK.constants.streetIdx());
		addrStreetIdxItem.setName("street_index");
		addrStreetIdxItem.setWidth(600);
		addrStreetIdxItem.setColSpan(2);
		addrStreetIdxItem.setCanEdit(false);

		adressOpCloseItem = new SelectItem();
		adressOpCloseItem.setValueMap(ClientMapUtil.getInstance()
				.getAddrMapOpClose());
		adressOpCloseItem.setDefaultToFirstOption(true);
		adressOpCloseItem.setTitle(CallCenterBK.constants.openClose());
		adressOpCloseItem.setName("legalAdressOpCloseItem");
		adressOpCloseItem.setWidth(400);
		adressOpCloseItem.setFetchMissingValues(false);

		adressItem = new TextItem();
		adressItem.setTitle(CallCenterBK.constants.home());
		adressItem.setName("legalAdressItem");
		adressItem.setWidth(400);

		blockItem = new TextItem();
		blockItem.setTitle(CallCenterBK.constants.block());
		blockItem.setName("legalBlockItem");
		blockItem.setWidth(200);

		appartItem = new TextItem();
		appartItem.setTitle(CallCenterBK.constants.appartment());
		appartItem.setName("appartItem");
		appartItem.setWidth(400);

		oldAddItem = new TextItem();
		oldAddItem.setTitle(CallCenterBK.constants.oldAddress());
		oldAddItem.setName("legalOldAddItem");
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

	public void setValues() {

	}
}
