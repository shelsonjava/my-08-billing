package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItem;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxRecord;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.client.utils.ISaveResult;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditOrgPriorities extends MyWindow {

	private VLayout hLayout;

	private DynamicForm dynamicForm;

	private TextItem descriptionItem;
	private TextAreaItem remarkItem;
	private SelectItem operatorItem;
	private MyComboBoxItem[] orgitems;

	private Record editRecord;
	private ListGrid listGrid;
	private ISaveResult saveResult;

	public DlgAddEditOrgPriorities(ListGrid listGrid, Record pRecord,
			ISaveResult saveResult) {
		super();
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			this.saveResult = saveResult;
			setTitle(CallCenterBK.constants.extraPriority());

			setHeight(350);
			setWidth(715);
			setShowMinimizeButton(false);
			setIsModal(true);
			setShowModalMask(true);
			// setCanDrag(false);
			// setCanDragReposition(false);
			setCanDragResize(true);

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

			descriptionItem = new TextItem();
			descriptionItem.setTitle(CallCenterBK.constants.description());
			descriptionItem.setWidth(600);
			descriptionItem.setName("description");
			descriptionItem.setRequired(true);

			remarkItem = new TextAreaItem();
			remarkItem.setTitle(CallCenterBK.constants.comment());
			remarkItem.setWidth(600);
			remarkItem.setHeight(50);
			remarkItem.setName("remark");
			remarkItem.setColSpan(4);
			remarkItem.setRequired(true);
			
			operatorItem = new SelectItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setWidth(600);
			operatorItem.setName("operator_src");
			operatorItem.setDefaultToFirstOption(true);
			ClientUtils.fillCombo(operatorItem, "OperatorsDS",
					"searchOperators", "operator_src", "operator_src_descr");
			
			dynamicForm.setFields(operatorItem, descriptionItem, remarkItem);

			ArrayList<MyComboBoxRecord> fieldRecords = new ArrayList<MyComboBoxRecord>();
			MyComboBoxRecord organization_name = new MyComboBoxRecord(
					"organization_name",
					CallCenterBK.constants.parrentOrgName(), true);
			MyComboBoxRecord remark = new MyComboBoxRecord("remark",
					CallCenterBK.constants.comment(), false);
			MyComboBoxRecord full_address_not_hidden = new MyComboBoxRecord(
					"full_address_not_hidden",
					CallCenterBK.constants.address(), true);

			fieldRecords.add(organization_name);
			fieldRecords.add(full_address_not_hidden);
			fieldRecords.add(remark);

			DataSource orgDS = DataSource.get("OrgDS");

			orgitems = new MyComboBoxItem[3];
			String org_ids = pRecord == null ? null : pRecord
					.getAttribute("org_ids");
			if (pRecord != null)
				dynamicForm.setValues(pRecord.toMap());
			String[] str = org_ids == null ? (new String[] { null, null, null })
					: org_ids.split(",");
			for (int i = 0; i < orgitems.length; i++) {
				String fieldName = "org_item_name" + i;
				Map<String, Object> mp = new TreeMap<String, Object>();
				String vl = str.length < i + 1 ? null : str[i];
				if (vl != null)
					mp.put("parrent_organization_id1", vl);
				MyComboBoxItem orgItem = new MyComboBoxItem(fieldName,
						CallCenterBK.constants.organization() + " " + (i + 1),
						0, 650);
				orgitems[i] = orgItem;
				orgItem.setNameField("parrent_organization_id1");
				orgItem.setMyDlgHeight(400);
				orgItem.setMyDlgWidth(900);

				orgItem.setMyDataSource(orgDS);
				orgItem.setMyDataSourceOperation("searchMainOrgsForCBDoubleLike");
				orgItem.setMyIdField("organization_id");
				orgItem.setMyFields(fieldRecords);
				orgItem.setMyChooserTitle(CallCenterBK.constants.organization());
				orgItem.setDataValue(mp);

				final int index = i;
				ClickHandler ch = new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						Object obj = event.getSource();
						if (obj == null)
							return;
						if (!obj.getClass().equals(IButton.class))
							return;
						IButton ib = (IButton) obj;
						String icon = ib.getIcon();
						int comp = 1 * (icon.contains("down") ? 1 : -1);
						Object curValue = orgitems[index].getCurrentValue();
						Object upValue = orgitems[index + comp]
								.getCurrentValue();
						Map<String, Object> mp = new TreeMap<String, Object>();
						if (upValue != null)
							mp.put("parrent_organization_id1", upValue);
						orgitems[index].setDataValue(mp);
						mp.clear();
						if (curValue != null)
							mp.put("parrent_organization_id1", curValue);
						orgitems[index + comp].setDataValue(mp);
					}
				};

				if (i != orgitems.length - 1) {
					IButton ib = new IButton("", ch);
					ib.setIcon("arrow_down.png");
					orgItem.addCanvas(ib);
					ib.setWidth(ib.getWidth() + 5);

				}
				if (i == orgitems.length - 1) {
					IButton ib = new IButton("", ch);
					ib.setIcon("arrow_up.png");
					orgItem.addCanvas(ib);

					ib.setWidth(ib.getWidth() + 5);
				}
//				if (i == 0 || i == orgitems.length - 1) {
//					VLayout canv = new VLayout();
//					orgItem.addCanvas(canv);
//				}

				hLayout.addMember(orgItem);
			}

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

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save() {
		Record newRecord = new Record(dynamicForm.getValues());
		String org_ids = "";
		for (int i = 0; i < orgitems.length; i++) {
			Object cv = orgitems[i].getCurrentValue();
			if (cv == null)
				continue;
			if (org_ids.length() > 0)
				org_ids += ",";
			org_ids += cv.toString();
		}
		newRecord.setAttribute("org_ids", org_ids);
		try {
			newRecord.setAttribute("changer_user", CommonSingleton
					.getInstance().getSessionPerson().getUser_name());
			
		} catch (CallCenterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DSCallback cb = new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				destroy();
				saveResult.saved(response.getData()[0],
						DlgAddEditOrgPriorities.class);
			}
		};
		if (editRecord == null)
			listGrid.addData(newRecord, cb);
		else
			listGrid.updateData(newRecord, cb);
	}

}
