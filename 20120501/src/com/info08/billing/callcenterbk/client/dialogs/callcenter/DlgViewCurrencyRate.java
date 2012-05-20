package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import com.google.gwt.i18n.client.NumberFormat;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.CanvasDisableTimer;
import com.info08.billing.callcenterbk.client.common.components.ChargePanel;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgViewCurrencyRate extends Window {

	private DynamicForm searchForm;
	private TextItem amountItem;
	private TextItem unitCalcAmmNatItem;
	private TextAreaItem unitCalcAmmBankItem;
	private TextItem calcAmountNatItem;
	private TextAreaItem calcAmountBankItem;

	private VLayout mainLayout;

	protected ListGridRecord recordFrom;
	protected ListGridRecord recordTo;
	private ChargePanel chargePanel;
	private boolean smsSend = false;

	private ToolStripButton sendSMS;
	private ToolStripButton sendSMS1;

	private ToolStripButton sendSMS2;
	private ToolStripButton sendSMS3;

	protected double amount;
	protected Record rates[];

	public DlgViewCurrencyRate(DataSource dataSource,
			ListGridRecord recordFrom, ListGridRecord recordTo, double amount,
			Record[] rates) {

		this.recordFrom = recordFrom;
		this.recordTo = recordTo;
		this.rates = rates;
		this.amount = amount;

		setTitle(CallCenterBK.constants.valute());

		setHeight(730);
		setWidth(950);
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setPadding(10);

		chargePanel = new ChargePanel(950, true, true,
				Constants.serviceCurrencyInfo, null);
		mainLayout.addMember(chargePanel);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);
		mainLayout.addMember(toolStrip);

		sendSMS = new ToolStripButton(CallCenterBK.constants.smsCurrencyRate(),
				"sms.png");
		sendSMS.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS);

		sendSMS1 = new ToolStripButton(
				CallCenterBK.constants.smsCurrencyAmountRate(), "sms.png");
		sendSMS1.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS1);
		toolStrip.addFill();

		sendSMS2 = new ToolStripButton(
				CallCenterBK.constants.smsCurrencyRateBank(), "sms.png");
		sendSMS2.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS2);

		sendSMS3 = new ToolStripButton(
				CallCenterBK.constants.smsCurrencyAmountRateBank(), "sms.png");
		sendSMS3.setLayoutAlign(Alignment.LEFT);
		toolStrip.addButton(sendSMS3);

		// Label labelSMSWarning = new
		// Label(CallCenter.constants.currSMSWarning());
		// labelSMSWarning.setWidth100();
		// labelSMSWarning.setStyleName("fontRed");
		// labelSMSWarning.setHeight(20);
		// toolStrip.addMember(labelSMSWarning);

		Label label1 = new Label(CallCenterBK.constants.currFrom());
		label1.setWidth100();
		label1.setStyleName("fontRed");
		label1.setHeight(20);
		mainLayout.addMember(label1);

		final DetailViewer detailViewer = new DetailViewer();
		detailViewer.setCanSelectText(true);
		detailViewer.setDataSource(dataSource);
		detailViewer.setWidth100();

		DetailViewerField country_name = new DetailViewerField(
				"country_name", CallCenterBK.constants.country());

		DetailViewerField name_descr = new DetailViewerField("name_descr",
				CallCenterBK.constants.currencyName());

		DetailViewerField code = new DetailViewerField("code",
				CallCenterBK.constants.currencyAbbrShort());

		detailViewer.selectRecord(recordFrom);
		ListGridRecord arr[] = new ListGridRecord[1];
		arr[0] = recordFrom;
		detailViewer.setData(arr);

		detailViewer.setFields(country_name, name_descr, code);

		mainLayout.addMember(detailViewer);

		Label label2 = new Label(CallCenterBK.constants.currTo());
		label2.setWidth100();
		label2.setStyleName("fontRed");
		label2.setHeight(20);
		mainLayout.addMember(label2);

		final DetailViewer detailViewer1 = new DetailViewer();
		detailViewer1.setCanSelectText(true);
		detailViewer1.setDataSource(dataSource);
		detailViewer1.setWidth100();

		DetailViewerField country_name1 = new DetailViewerField(
				"country_name", CallCenterBK.constants.country());

		DetailViewerField name_descr1 = new DetailViewerField("name_descr",
				CallCenterBK.constants.currencyName());

		DetailViewerField code1 = new DetailViewerField("code",
				CallCenterBK.constants.currencyAbbrShort());

		detailViewer1.selectRecord(recordTo);
		ListGridRecord arr1[] = new ListGridRecord[1];
		arr1[0] = recordTo;
		detailViewer1.setData(arr1);

		detailViewer1.setFields(country_name1, name_descr1, code1);

		mainLayout.addMember(detailViewer1);

		IButton iButtonCalc = new IButton();
		iButtonCalc.setTitle(CallCenterBK.constants.calculate());
		mainLayout.addMember(iButtonCalc);

		searchForm = new DynamicForm();
		searchForm.setAutoFocus(false);
		searchForm.setWidth100();
		searchForm.setNumCols(2);
		searchForm.setTitleWidth(200);
		searchForm.setBorder("1px solid #a7abb4;");
		searchForm.setPadding(10);

		amountItem = new TextItem();
		amountItem.setTitle(CallCenterBK.constants.amount());
		amountItem.setName("amountItem");
		amountItem.setWidth("100%");
		amountItem.setTextBoxStyle("fontRedAndBoldNoBorder");
		amountItem.setCanFocus(false);

		unitCalcAmmNatItem = new TextItem();
		unitCalcAmmNatItem.setTitle(CallCenterBK.constants.unitCalcAmmNat());
		unitCalcAmmNatItem.setName("unitCalcAmmNatItem");
		unitCalcAmmNatItem.setWidth("100%");
		unitCalcAmmNatItem.setCanEdit(false);
		unitCalcAmmNatItem.setCanFocus(false);

		calcAmountNatItem = new TextItem();
		calcAmountNatItem.setTitle(CallCenterBK.constants.calcAmountNat());
		calcAmountNatItem.setName("calcAmountNatItem");
		calcAmountNatItem.setWidth("100%");
		calcAmountNatItem.setCanEdit(false);
		calcAmountNatItem.setCanFocus(false);

		unitCalcAmmBankItem = new TextAreaItem();
		unitCalcAmmBankItem.setTitle(CallCenterBK.constants.unitCalcAmmBank());
		unitCalcAmmBankItem.setName("unitCalcAmmBankItem");
		unitCalcAmmBankItem.setWidth("100%");
		unitCalcAmmBankItem.setCanEdit(false);
		unitCalcAmmBankItem.setTextBoxStyle("fontRedAndBoldNoBorder");
		unitCalcAmmBankItem.setCanFocus(false);
		unitCalcAmmBankItem.setHeight(50);

		calcAmountBankItem = new TextAreaItem();
		calcAmountBankItem.setTitle(CallCenterBK.constants.calcAmountBank());
		calcAmountBankItem.setName("calcAmountBankItem");
		calcAmountBankItem.setWidth("100%");
		calcAmountBankItem.setCanEdit(false);
		calcAmountBankItem.setTextBoxStyle("fontRedAndBoldNoBorder");
		calcAmountBankItem.setCanFocus(false);
		calcAmountBankItem.setHeight(50);

		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setWidth("100%");

		searchForm.setFields(amountItem, spacerItem, unitCalcAmmNatItem,
				unitCalcAmmBankItem, spacerItem, calcAmountNatItem,
				calcAmountBankItem);
		mainLayout.addMember(searchForm);

		amountItem.setValue(amount);

		fillCalculatedAmount();

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton cancItem = new IButton();
		cancItem.setTitle(CallCenterBK.constants.close());
		cancItem.setWidth(100);

		hLayoutItem.setMembers(cancItem);

		mainLayout.addMember(hLayoutItem);

		cancItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroyDlg();
			}
		});
		addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				destroyDlg();
			}
		});

		sendSMS.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendCurrencySMS();
			}
		});

		sendSMS1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendCurrencyAmountSMS();
			}
		});

		sendSMS2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendCurrencySMSBank();
			}
		});

		sendSMS3.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendCurrencyAmountSMSBank();
			}
		});

		iButtonCalc.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				reCalc();
			}
		});

		amountItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					reCalc();
				}
			}
		});

		addItem(mainLayout);
	}

	private void reCalc() {
		try {
			String entText = amountItem.getValueAsString();
			if (entText == null || entText.trim().equals("")) {
				SC.say(CallCenterBK.constants.enterAmount());
				return;
			}
			Double tmpAmount = null;
			try {
				tmpAmount = Double.parseDouble(entText);
			} catch (NumberFormatException e) {
				SC.say(CallCenterBK.constants.invalidAmount());
				return;
			}
			amount = tmpAmount.doubleValue();
			fillCalculatedAmount();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private String strSMS1 = "";
	private String strSMS2 = "";
	private String strSMS3 = "";
	private String strSMS4 = "";

	private void fillCalculatedAmount() {
		try {

			int unitAmount = 1;
			String unitRateNatStr = "-";
			String unitRateBankMStr = "-";
			String unitRateBankMSStr = "-";
			String amountRateNatStr = "-";
			String amountRateBankMStr = "-";
			String amountRateBankMSStr = "-";

			// find rates
			Record rateFromRecord = null;
			Record rateToRecord = null;

			// currencies
			Integer curr_id_from = recordFrom.getAttributeAsInt("currency_id");
			String curr_name_from = recordFrom
					.getAttributeAsString("name_descr");
			String curr_abbrev_from = recordFrom
					.getAttributeAsString("code");

			Integer curr_id_to = recordTo.getAttributeAsInt("currency_id");
			String curr_name_to = recordTo.getAttributeAsString("name_descr");
			String curr_abbrev_to = recordTo
					.getAttributeAsString("code");

			// find
			for (Record recItem : rates) {
				Integer curr_id_it = recItem.getAttributeAsInt("currency_id");
				if (curr_id_from.equals(curr_id_it)) {
					rateFromRecord = recItem;
				} else if (curr_id_to.equals(curr_id_it)) {
					rateToRecord = recItem;
				}
			}
			NumberFormat nf = NumberFormat.getFormat("################0.0000");
			if (rateFromRecord == null || rateToRecord == null) {
				unitRateNatStr = "-";
				unitRateBankMStr = "-";
				unitRateBankMSStr = "-";
				amountRateNatStr = "-";
				amountRateBankMStr = "-";
				amountRateBankMSStr = "-";
			} else {

				Double coefficient_from = rateFromRecord
						.getAttributeAsDouble("coefficient");
				Double coefficient_to = rateToRecord
						.getAttributeAsDouble("coefficient");
				if (coefficient_from == null) {
					coefficient_from = 1.0;
				}
				if (coefficient_to == null) {
					coefficient_to = 1.0;
				}

				// Calculate National Rate - START

				Double nationalCourseFrom = rateFromRecord.getAttributeAsDouble("national_course");
				Double nationalCourseTo = rateToRecord.getAttributeAsDouble("national_course");
				if (nationalCourseFrom == null || nationalCourseTo == null) {
					unitRateNatStr = "-";
					amountRateNatStr = "-";
				} else if ((nationalCourseFrom.doubleValue() < 0.0001 && nationalCourseFrom
						.doubleValue() > -0.0001)
						|| (nationalCourseTo.doubleValue() < 0.0001 && nationalCourseTo
								.doubleValue() > -0.0001)) {
					unitRateNatStr = "-";
					amountRateNatStr = "-";
				} else {

					double resultUnitCalcAmNat = nationalCourseFrom.doubleValue()
							* coefficient_to / nationalCourseTo.doubleValue()
							/ coefficient_from;
					unitRateNatStr = nf.format(((Number) resultUnitCalcAmNat)
							.doubleValue());
					if (amount > 0.0) {
						double resultAmmUnitCallAmNat = resultUnitCalcAmNat
								* amount;
						amountRateNatStr = nf
								.format(((Number) resultAmmUnitCallAmNat)
										.doubleValue());
					} else {
						amountRateNatStr = "-";
					}
				}

				// Calculate National Rate - END

				// Bank Market Rate Calculation For Unit - START
				Double dUnitMarketRateFrom = rateFromRecord
						.getAttributeAsDouble("bank_buy_course");
				Double dUnitMarketRateTo = rateToRecord
						.getAttributeAsDouble("bank_buy_course");
				if (dUnitMarketRateFrom == null || dUnitMarketRateTo == null) {
					unitRateBankMStr = "-";
				} else if ((dUnitMarketRateFrom.doubleValue() < 0.0001 && dUnitMarketRateFrom
						.doubleValue() > -0.0001)
						|| (dUnitMarketRateTo.doubleValue() < 0.0001 && dUnitMarketRateTo
								.doubleValue() > -0.0001)) {
					unitRateBankMStr = "-";
				} else {
					double resultUnitMarkRate = dUnitMarketRateFrom
							.doubleValue()
							* coefficient_to
							/ dUnitMarketRateTo.doubleValue() / coefficient_from;

					unitRateBankMStr = nf.format(((Number) resultUnitMarkRate)
							.doubleValue());

					if (amount > 0.0) {
						double resultAmountMarkRate = resultUnitMarkRate
								* amount;
						amountRateBankMStr = nf
								.format(((Number) resultAmountMarkRate)
										.doubleValue());
					} else {
						amountRateBankMStr = "-";
					}

				}

				// Bank Market Rate Calculation For Unit - END

				// Bank Sales Market Rate Calculation For Unit - START
				Double dUnitSalesMarketRateFrom = rateFromRecord
						.getAttributeAsDouble("bank_sell_course");
				Double dUnitSalesMarketRateTo = rateToRecord
						.getAttributeAsDouble("bank_sell_course");
				if (dUnitSalesMarketRateFrom == null
						|| dUnitSalesMarketRateTo == null) {
					unitRateBankMSStr = "-";
				} else if ((dUnitSalesMarketRateFrom.doubleValue() < 0.0001 && dUnitSalesMarketRateFrom
						.doubleValue() > -0.0001)
						|| (dUnitSalesMarketRateTo.doubleValue() < 0.0001 && dUnitSalesMarketRateTo
								.doubleValue() > -0.0001)) {
					unitRateBankMSStr = "-";
				} else {
					double resultUnitSalesMarkRate = dUnitSalesMarketRateFrom
							.doubleValue()
							* coefficient_to
							/ dUnitSalesMarketRateTo.doubleValue()
							/ coefficient_from;

					unitRateBankMSStr = nf
							.format(((Number) resultUnitSalesMarkRate)
									.doubleValue());

					if (amount > 0.0) {
						double resultSalesAmountMarkRate = resultUnitSalesMarkRate
								* amount;
						amountRateBankMSStr = nf
								.format(((Number) resultSalesAmountMarkRate)
										.doubleValue());
					} else {
						amountRateBankMSStr = "-";
					}

				}

				// Bank Sales Market Rate Calculation For Unit - END

			}
			String unitCalcAmmNatStr = unitAmount + " " + curr_name_from
					+ " = " + unitRateNatStr + " " + curr_name_to;
			unitCalcAmmNatItem.setValue(unitCalcAmmNatStr);
			strSMS1 = unitAmount + " " + curr_abbrev_from + " = "
					+ unitRateNatStr + " " + curr_abbrev_to;

			String amountCalcAmmNatStr = (amount > 0 ? (nf
					.format(((Number) amount).doubleValue()) + "") : "-")
					+ " "
					+ curr_name_from
					+ " = "
					+ amountRateNatStr
					+ " "
					+ curr_name_to;
			calcAmountNatItem.setValue(amountCalcAmmNatStr);

			strSMS2 = (amount > 0 ? (nf.format(((Number) amount).doubleValue()) + "")
					: "-")
					+ " "
					+ curr_abbrev_from
					+ " = "
					+ amountRateNatStr
					+ " "
					+ curr_abbrev_to;

			String unitRateStr = CallCenterBK.constants.marketRate() + " : "
					+ unitAmount + " " + curr_name_from + " = "
					+ unitRateBankMStr + " " + curr_name_to + "  /  "
					+ CallCenterBK.constants.salesMarketRate() + " : "
					+ unitAmount + " " + curr_name_from + " = "
					+ unitRateBankMSStr + " " + curr_name_to;

			unitCalcAmmBankItem.setValue(unitRateStr);

			strSMS3 = "sheskidva: " + unitAmount + " " + curr_abbrev_from
					+ " = " + unitRateBankMStr + " " + curr_abbrev_to + "  /  "
					+ "gakidva: " + unitAmount + " " + curr_abbrev_from + " = "
					+ unitRateBankMSStr + " " + curr_abbrev_to;

			String amountRateStr = CallCenterBK.constants.marketRate()
					+ " : "
					+ (amount > 0 ? (nf.format(((Number) amount).doubleValue()) + "")
							: "-")
					+ " "
					+ curr_name_from
					+ " = "
					+ amountRateBankMStr
					+ " "
					+ curr_name_to
					+ "  /  "
					+ CallCenterBK.constants.salesMarketRate()
					+ " : "
					+ (amount > 0 ? (nf.format(((Number) amount).doubleValue()) + "")
							: "-") + " " + curr_name_from + " = "
					+ amountRateBankMSStr + " " + curr_name_to;

			calcAmountBankItem.setValue(amountRateStr);

			strSMS4 = "sheskidva: "
					+ (amount > 0 ? (nf.format(((Number) amount).doubleValue()) + "")
							: "-")
					+ " "
					+ curr_abbrev_from
					+ " = "
					+ amountRateBankMStr
					+ " "
					+ curr_abbrev_to
					+ "  /  "
					+ "gakidva: "
					+ (amount > 0 ? (nf.format(((Number) amount).doubleValue()) + "")
							: "-") + " " + curr_abbrev_from + " = "
					+ amountRateBankMSStr + " " + curr_abbrev_to;

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void destroyDlg() {
		try {
			final DlgViewCurrencyRate dlgViewIndex = this;
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				destroy();
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				destroy();
				return;
			}
			boolean isMobile = serverSession.isPhoneIsMobile();
			if (isMobile) {
				if (!smsSend) {
					SC.ask(CallCenterBK.constants.warning(),
							CallCenterBK.constants.smsDidNotSent(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										dlgViewIndex.destroy();
									}
								}
							});
				} else {
					destroy();
				}
			} else {
				int chCount = chargePanel.getChrgCounter();
				if (chCount <= 0) {
					SC.ask(CallCenterBK.constants.warning(),
							CallCenterBK.constants.chargeDidNotMade(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										dlgViewIndex.destroy();
									}
								}
							});
				} else {
					destroy();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sendCurrencySMS() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.phoneIsNotMobile());
				return;
			}
			CanvasDisableTimer.addCanvasClickTimer(sendSMS);

			String txt = strSMS1;

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id",
					Constants.serviceCurrencyInfo);
			recordParam
					.setAttribute("session_id", serverSession.getSessionId());
			recordParam.setAttribute("sms_text", txt);
			recordParam.setAttribute("phone", phone);
			recordParam.setAttribute("rec_user", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "LogSMS");

			logSessChDS.addData(recordParam, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					smsSend = true;
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sendCurrencyAmountSMS() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.phoneIsNotMobile());
				return;
			}
			CanvasDisableTimer.addCanvasClickTimer(sendSMS1);

			String txt = strSMS2;

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id",
					Constants.serviceCurrencyInfo);
			recordParam
					.setAttribute("session_id", serverSession.getSessionId());
			recordParam.setAttribute("sms_text", txt);
			recordParam.setAttribute("phone", phone);
			recordParam.setAttribute("rec_user", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "LogSMS");
			logSessChDS.addData(recordParam, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					smsSend = true;
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sendCurrencySMSBank() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.phoneIsNotMobile());
				return;
			}
			CanvasDisableTimer.addCanvasClickTimer(sendSMS2);

			String txt = strSMS3;

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id",
					Constants.serviceCurrencyInfo);
			recordParam
					.setAttribute("session_id", serverSession.getSessionId());
			recordParam.setAttribute("sms_text", txt);
			recordParam.setAttribute("phone", phone);
			recordParam.setAttribute("rec_user", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "LogSMS");

			logSessChDS.addData(recordParam, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					smsSend = true;
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void sendCurrencyAmountSMSBank() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenterBK.constants.phoneIsNotMobile());
				return;
			}
			CanvasDisableTimer.addCanvasClickTimer(sendSMS3);

			String txt = strSMS4;

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record recordParam = new Record();

			recordParam.setAttribute("service_id",
					Constants.serviceCurrencyInfo);
			recordParam
					.setAttribute("session_id", serverSession.getSessionId());
			recordParam.setAttribute("sms_text", txt);
			recordParam.setAttribute("phone", phone);
			recordParam.setAttribute("rec_user", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());

			DSRequest req = new DSRequest();
			DataSource logSessChDS = DataSource.get("LogSessChDS");
			req.setAttribute("operationId", "LogSMS");
			logSessChDS.addData(recordParam, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					smsSend = true;
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

}
