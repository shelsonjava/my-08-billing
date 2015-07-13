package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import com.google.gwt.i18n.client.NumberFormat;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgViewChargesByPhone extends MyWindow {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem nmItem;
	private ListGrid listGrid;

	public DlgViewChargesByPhone() {
		super();
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();

			setTitle(CallCenterBK.constants.viewNumberCharges());

			setHeight(700);
			setWidth(600);
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
			dynamicForm.setTitleWidth(150);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			nmItem = new TextItem();
			nmItem.setTitle(CallCenterBK.constants.phone());
			nmItem.setName("phoneItem");
			nmItem.setWidth(250);
			nmItem.setCanEdit(false);
			String call_phone = serverSession.getPhone();
			nmItem.setValue(call_phone);

			dynamicForm.setFields(nmItem);

			DataSource callSessDS = DataSource.get("CallSessExpDS1");

			listGrid = new ListGrid();
			listGrid.setWidth100();
			listGrid.setHeight100();
			Criteria criteria = new Criteria();
			criteria.setAttribute("phone", call_phone);
			listGrid.setCriteria(criteria);
			listGrid.setFetchOperation("selectChargesByPhoneInCurrMonth");
			listGrid.setDataSource(callSessDS);
			listGrid.setAutoFetchData(true);
			listGrid.setCanSort(false);
			listGrid.setCanResizeFields(false);
			listGrid.setShowAllRecords(true);
			listGrid.setGroupByField("service_description");
			listGrid.setGroupStartOpen(GroupStartOpen.ALL);
			listGrid.setShowGridSummary(true);
			listGrid.setShowGroupSummary(true);
			listGrid.setDataFetchMode(FetchMode.BASIC);
			hLayout.addMember(listGrid);

			ListGridField rec_date = new ListGridField("rec_date",
					CallCenterBK.constants.recDate(), 150);
			ListGridField aprice = new ListGridField("aprice",
					CallCenterBK.constants.price(), 70);
			aprice.setAlign(Alignment.CENTER);
			aprice.setShowGroupSummary(false);
			aprice.setShowGridSummary(false);

			ListGridField amount = new ListGridField("amount",
					CallCenterBK.constants.amount(), 100);
			amount.setSummaryFunction(SummaryFunctionType.SUM);
			amount.setCellFormatter(new CellFormatter() {
				public String format(Object value, ListGridRecord record,
						int rowNum, int colNum) {
					if (value == null)
						return null;
					try {
						NumberFormat nf = NumberFormat.getFormat("#,##0.00");
						return "GEL: "
								+ nf.format(((Number) value).doubleValue());
					} catch (Exception e) {
						return value.toString();
					}
				}
			});
			ListGridField cnt = new ListGridField("cnt",
					CallCenterBK.constants.cntShort(), 70);
			cnt.setAlign(Alignment.CENTER);

			ListGridField service_description = new ListGridField(
					"service_description", CallCenterBK.constants.service());
			rec_date.setAlign(Alignment.CENTER);
			amount.setAlign(Alignment.CENTER);
			service_description.setAlign(Alignment.CENTER);

			listGrid.setFields(rec_date, aprice, cnt, amount, service_description);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});
			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
