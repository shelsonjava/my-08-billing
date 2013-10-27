package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgGetContractorsBilling extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem ymItem;
	private CheckboxItem generateBillItem;
	private SelectItem operatorItem;
	private boolean full;
	private Integer corporate_client_id;
	private Integer is_budget;

	public DlgGetContractorsBilling(Integer corporate_client_id, boolean full,
			Integer is_budget) {
		try {
			this.full = full;
			this.corporate_client_id = corporate_client_id;
			this.is_budget = is_budget;
			setTitle(full ? CallCenterBK.constants.contractorsBillingFull()
					: CallCenterBK.constants.contractorsBilling());
			if (corporate_client_id == null) {
				setHeight(160);
			} else {
				setHeight(140);
			}

			setWidth(400);
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

			ymItem = new TextItem();
			ymItem.setTitle(CallCenterBK.constants.yearMonth());
			ymItem.setName("ymItem");
			ymItem.setWidth(200);

			generateBillItem = new CheckboxItem();
			generateBillItem.setTitle(CallCenterBK.constants
					.generateBillAgain());
			generateBillItem.setName("generateBillItem");
			generateBillItem.setWidth(200);

			if (corporate_client_id == null) {
				operatorItem = new SelectItem();
				operatorItem.setTitle(CallCenterBK.constants.operator());
				operatorItem.setWidth(200);
				operatorItem.setName("operator_src");
				operatorItem.setDefaultToFirstOption(true);
				ClientUtils
						.fillCombo(operatorItem, "OperatorsDS",
								"searchOperators", "operator_src",
								"operator_src_descr");

				dynamicForm.setFields(operatorItem, ymItem, generateBillItem);
			} else {
				dynamicForm.setFields(ymItem, generateBillItem);
			}

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenterBK.constants.download());
			saveItem.setIcon("excel.gif");
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

			ymItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						save();
					}
				}
			});
			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			String yearMonthStr = ymItem.getValueAsString();
			if (yearMonthStr == null || yearMonthStr.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzEnterYearMonth());
				return;
			}
			yearMonthStr = yearMonthStr.trim();

			if (yearMonthStr.length() < 4) {
				SC.say(CallCenterBK.constants.invalidYearMonth());
				return;
			}
			final Integer ym;
			try {
				ym = Integer.parseInt(yearMonthStr);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.invalidYearMonth());
				return;
			}

			final DataSource dataSource = DataSource.get("CorporateClientsDS");
			boolean genBill = generateBillItem.getValueAsBoolean();
			if (genBill) {
				Record record = new Record();
				record.setAttribute("ym", ym);
				record.setAttribute("corporate_client_id", 123123123);

				DSRequest dsRequest = new DSRequest();
				dsRequest.setOperationId("getContractorsBilling1");

				dataSource.addData(record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						showReport(dataSource, ym);
					}
				}, dsRequest);
			} else {
				showReport(dataSource, ym);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void showReport(DataSource dataSource, Integer ym) {
		try {
			DSRequest dsRequest = new DSRequest();
			dsRequest.setExportAs((ExportFormat) EnumUtil.getEnum(
					ExportFormat.values(), "xls"));
			dsRequest.setExportDisplay(ExportDisplay.DOWNLOAD);

			if (full) {
				dsRequest.setOperationId("getFullContrBillMain2");
				dsRequest.setExportFields(new String[] {
						"billing_company_name", "organization_name",
						"orgDepName", "service_name_geo", "phone",
						"charge_date", "call_price" });
			} else {
				dsRequest.setOperationId("getFullContrBillMain1");
				dsRequest.setExportFields(new String[] { "organization_name",
						"ident_code", "orgDepName", "call_count", "price",
						"amount" });
			}

			Criteria criteria = new Criteria();
			criteria.setAttribute("ym", ym);
			if (corporate_client_id != null) {
				criteria.setAttribute("corporate_client_id",
						corporate_client_id);
			}
			if (is_budget != null && !is_budget.equals(new Integer(-1))) {
				criteria.setAttribute("is_budget", is_budget);
				criteria.setAttribute("goverment", is_budget);
			}
			if (operatorItem != null && operatorItem.getValueAsString() != null
					&& !operatorItem.getValueAsString().trim().equals("")) {
				Integer operator_src = Integer.parseInt(operatorItem
						.getValueAsString());
				criteria.setAttribute("operator_src", operator_src);
			}

			dataSource.exportData(criteria, dsRequest, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {

				}
			});
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
