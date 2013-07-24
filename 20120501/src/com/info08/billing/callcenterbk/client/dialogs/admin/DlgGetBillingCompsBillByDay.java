package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
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
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgGetBillingCompsBillByDay extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private DateItem dateItem;
	private Integer billing_company_id;
	private SelectItem operatorItem;
	

	public DlgGetBillingCompsBillByDay(Integer billing_company_id) {
		try {
			this.billing_company_id = billing_company_id;
			setTitle(CallCenterBK.constants.telCombBillByDay());

			setHeight(130);
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

			dateItem = new DateItem();
			dateItem.setTitle(CallCenterBK.constants.chooseDate());
			dateItem.setName("dateItem");
			dateItem.setWidth(200);
			dateItem.setValue(new Date());
			
			operatorItem = new SelectItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setWidth(200);
			operatorItem.setName("operator_src");
			operatorItem.setDefaultToFirstOption(true);
			ClientUtils.fillCombo(operatorItem, "OperatorsDS",
					"searchOperators", "operator_src", "operator_src_descr");

			dynamicForm.setFields(operatorItem,dateItem);

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
			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			Date date_param = dateItem.getValueAsDate();
			if (date_param == null) {
				SC.say(CallCenterBK.constants.plzEnterDate());
				return;
			}

			final DataSource dataSource = DataSource.get("BillingCompsDS");
			DSRequest dsRequest = new DSRequest();
			dsRequest.setExportAs((ExportFormat) EnumUtil.getEnum(
					ExportFormat.values(), ExportFormat.CSV.toString()));
			dsRequest.setExportDisplay(ExportDisplay.DOWNLOAD);

			dsRequest.setOperationId("getBillingCompBillByDay");
			dsRequest.setExportFields(new String[] { "phonea", "phoneb",
					"charge_date1", "duration", "rate", "price" });
			dsRequest.setExportResults(true);

			DateTimeFormat dateFormatter = DateTimeFormat.getFormat("yyyyMMdd");
			dsRequest.setExportFilename(dateFormatter.format(date_param));

			Criteria criteria = new Criteria();
			criteria.setAttribute("billing_company_id", billing_company_id);
			criteria.setAttribute("date_param", date_param);
			criteria.setAttribute("no_email", 1);
			Integer operator_src = Integer.parseInt(operatorItem
					.getValueAsString());
			criteria.setAttribute("operator_src", operator_src);
			
			
			dataSource.exportData(criteria, dsRequest, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
