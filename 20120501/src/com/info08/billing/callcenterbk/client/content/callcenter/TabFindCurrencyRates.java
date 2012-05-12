package com.info08.billing.callcenterbk.client.content.callcenter;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewCurrencyRate;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class TabFindCurrencyRates extends Tab {

	private DynamicForm searchForm;

	private TextItem amountItem;

	private IButton calculateButton;
	private IButton clearButton;

	private ListGrid listGridFrom;
	private ListGrid listGridTo;

	// main content layout
	private VLayout mainLayout;

	private DataSource CurrencyDS;

	public TabFindCurrencyRates() {
		setTitle(CallCenterBK.constants.valute());
		setCanClose(true);

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setMargin(5);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(true);
		searchForm.setWidth(500);
		searchForm.setNumCols(1);
		searchForm.setTitleOrientation(TitleOrientation.TOP);

		amountItem = new TextItem();
		amountItem.setTitle(CallCenterBK.constants.amount());
		amountItem.setName("amountItem");
		amountItem.setWidth(500);

		searchForm.setFields(amountItem);
		mainLayout.addMember(searchForm);

		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setWidth(500);
		buttonLayout.setHeight(30);
		buttonLayout.setAlign(Alignment.RIGHT);

		clearButton = new IButton();
		clearButton.setTitle(CallCenterBK.constants.clear());

		calculateButton = new IButton();
		calculateButton.setTitle(CallCenterBK.constants.calculate());

		buttonLayout.setMembers(calculateButton, clearButton);
		mainLayout.addMember(buttonLayout);

		HLayout gridsLayout = new HLayout(20);
		gridsLayout.setWidth100();
		gridsLayout.setHeight100();

		CurrencyDS = DataSource.get("CurrencyDS");

		SectionStack sectionStack1 = new SectionStack();
		sectionStack1.setWidth(500);
		sectionStack1.setHeight100();

		SectionStackSection section1 = new SectionStackSection(
				CallCenterBK.constants.chooseCurrFrom());
		section1.setCanCollapse(false);
		section1.setExpanded(true);

		SectionStack sectionStack2 = new SectionStack();
		sectionStack2.setWidth(500);
		sectionStack2.setHeight100();

		SectionStackSection section2 = new SectionStackSection(
				CallCenterBK.constants.chooseCurrTo());
		section2.setCanCollapse(false);
		section2.setExpanded(true);

		listGridFrom = new ListGrid();
		listGridFrom.setWidth100();
		listGridFrom.setHeight100();
		listGridFrom.setAlternateRecordStyles(true);
		listGridFrom.setDataSource(CurrencyDS);
		listGridFrom.setAutoFetchData(true);
		listGridFrom.setShowFilterEditor(false);
		listGridFrom.setCanEdit(false);
		listGridFrom.setCanRemoveRecords(false);
		listGridFrom.setFetchOperation("searchAllCurrencyForCallCenter");
		listGridFrom.setCanHover(true);
		listGridFrom.setShowHover(true);
		listGridFrom.setShowHoverComponents(true);
		listGridFrom.setShowFilterEditor(true);
		listGridFrom.setFilterOnKeypress(true);
		listGridFrom.setWrapCells(true);
		listGridFrom.setFixedRecordHeights(false);

		ListGridField flag1 = new ListGridField("code", " ", 30);
		flag1.setAlign(Alignment.CENTER);
		flag1.setType(ListGridFieldType.IMAGE);
		flag1.setImageURLPrefix("flags/");
		flag1.setImageURLSuffix(".png");
		flag1.setCanSort(false);
		flag1.setCanFilter(false);

		ListGridField country_name_geo = new ListGridField("country_name_geo",
				CallCenterBK.constants.country(), 200);
		country_name_geo.setAlign(Alignment.LEFT);

		ListGridField name_descr = new ListGridField("name_descr",
				CallCenterBK.constants.currencyName());
		name_descr.setAlign(Alignment.LEFT);

		ListGridField code = new ListGridField("code",
				CallCenterBK.constants.currencyAbbrShort(), 50);
		code.setAlign(Alignment.LEFT);

		listGridFrom.setFields(flag1, country_name_geo, name_descr, code);
		section1.setItems(listGridFrom);
		sectionStack1.setSections(section1);

		listGridTo = new ListGrid();
		listGridTo.setWidth(500);
		listGridTo.setHeight100();
		listGridTo.setAlternateRecordStyles(true);
		listGridTo.setDataSource(CurrencyDS);
		listGridTo.setAutoFetchData(true);
		listGridTo.setShowFilterEditor(false);
		listGridTo.setCanEdit(false);
		listGridTo.setCanRemoveRecords(false);
		listGridTo.setFetchOperation("searchAllCurrencyForCallCenter");
		listGridTo.setCanHover(true);
		listGridTo.setShowHover(true);
		listGridTo.setShowHoverComponents(true);
		listGridTo.setShowFilterEditor(true);
		listGridTo.setFilterOnKeypress(true);
		listGridTo.setWrapCells(true);
		listGridTo.setFixedRecordHeights(false);

		ListGridField flag = new ListGridField("code", " ", 30);
		flag.setAlign(Alignment.CENTER);
		flag.setType(ListGridFieldType.IMAGE);
		flag.setImageURLPrefix("flags/");
		flag.setImageURLSuffix(".png");
		flag.setCanSort(false);
		flag.setCanFilter(false);

		ListGridField country_name_geo1 = new ListGridField("country_name_geo",
				CallCenterBK.constants.country(), 200);
		country_name_geo1.setAlign(Alignment.LEFT);

		ListGridField name_descr1 = new ListGridField("name_descr",
				CallCenterBK.constants.currencyName());
		name_descr1.setAlign(Alignment.LEFT);

		ListGridField code1 = new ListGridField("code",
				CallCenterBK.constants.currencyAbbrShort(), 50);
		code1.setAlign(Alignment.LEFT);

		listGridTo
				.setFields(flag, country_name_geo1, name_descr1, code1);

		section2.setItems(listGridTo);
		sectionStack2.setSections(section2);

		gridsLayout.setMembers(sectionStack1, sectionStack2);

		mainLayout.addMember(gridsLayout);
		setPane(mainLayout);

		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				amountItem.clearValue();
			}
		});

		calculateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				calculateAmount();
			}
		});

		amountItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					calculateAmount();
				}
			}
		});
	}

	private void calculateAmount() {
		try {
			String amount_str = amountItem.getValueAsString();
			double amount = 0.0;
			if (amount_str != null && !amount_str.trim().equals("")) {
				try {
					double tmp = Double.parseDouble(amount_str.trim());
					amount = tmp;
				} catch (Exception e) {
					SC.say(CallCenterBK.constants.invalidAmount());
				}
			}

			final double pAmount = amount;

			final ListGridRecord listGridRecordFrom = listGridFrom
					.getSelectedRecord();
			if (listGridRecordFrom == null) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.chooseCurrFrom());
				return;
			}

			final ListGridRecord listGridRecordTo = listGridTo
					.getSelectedRecord();
			if (listGridRecordTo == null) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.chooseCurrTo());
				return;
			}

			Integer form_curr_id = listGridRecordFrom
					.getAttributeAsInt("currency_id");
			Integer to_curr_id = listGridRecordTo.getAttributeAsInt("currency_id");

			if (form_curr_id.equals(to_curr_id)) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.fromAndToCurrIsSame());
				return;
			}

			DataSource dataSourceRate = DataSource.get("CurrencyCourseDS");

			Criteria criteria1 = new Criteria();
			criteria1.setAttribute("currency_id", form_curr_id);
			criteria1.setAttribute("currency_id1", to_curr_id);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchAllCurrencyCourseForCallCenter");
			dataSourceRate.fetchData(criteria1, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records == null || records.length != 2) {
						SC.say(CallCenterBK.constants.ratesNotFound());
						return;
					}

					DlgViewCurrencyRate dlgViewCurrencyRate = new DlgViewCurrencyRate(
							CurrencyDS, listGridRecordFrom, listGridRecordTo,
							pAmount, records);
					dlgViewCurrencyRate.show();
				}
			}, dsRequest);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
