package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditContractor;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgBlackPhoneList;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgContractorPhones;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgGetContractorsBilling;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgUpdateContrCurrRangePrice;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabContractors extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem orgNameItem;
	private TextItem orgDepItem;
	private TextItem phoneItem;
	private SelectItem contractorType;
	private SelectItem limitItem;
	private SelectItem priceTypeItem;

	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ToolStripButton setRangePriceBtn;

	private ToolStripButton viewCallCntBtn;
	private ToolStripButton viewChargesSumBtn;
	private ToolStripButton blockPhoneListBtn;

	private ToolStripButton contractorsBillBtn;
	private ToolStripButton contractorsBillFullBtn;

	private ToolStripButton contractorsBillBtn1;
	private ToolStripButton contractorsBillFullBtn1;

	private ListGrid contractorsGrid;
	private DataSource contractorsDS;

	public TabContractors() {
		try {

			setTitle(CallCenterBK.constants.contractors());
			setCanClose(true);

			contractorsDS = DataSource.get("ContractorsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(830);
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(4);
			mainLayout.addMember(searchForm);

			orgNameItem = new TextItem();
			orgNameItem.setTitle(CallCenterBK.constants.orgNameFull());
			orgNameItem.setWidth(250);
			orgNameItem.setName("orgNameItem");

			orgDepItem = new TextItem();
			orgDepItem.setTitle(CallCenterBK.constants.department());
			orgDepItem.setWidth(250);
			orgDepItem.setName("orgDepItem");

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenterBK.constants.phone());
			phoneItem.setWidth(250);
			phoneItem.setName("phoneItem");

			contractorType = new SelectItem();
			contractorType.setTitle(CallCenterBK.constants.contractorType());
			contractorType.setWidth(250);
			contractorType.setName("contractorType");
			contractorType.setDefaultToFirstOption(true);
			contractorType.setValueMap(ClientMapUtil.getInstance()
					.getContractorTypes());

			limitItem = new SelectItem();
			limitItem.setTitle(CallCenterBK.constants.limit());
			limitItem.setWidth(250);
			limitItem.setName("limitItem");
			limitItem.setDefaultToFirstOption(true);
			limitItem.setValueMap(ClientMapUtil.getInstance().getLimitTypes());

			priceTypeItem = new SelectItem();
			priceTypeItem.setTitle(CallCenterBK.constants.priceType());
			priceTypeItem.setWidth(250);
			priceTypeItem.setName("priceTypeItem");
			priceTypeItem.setDefaultToFirstOption(true);
			priceTypeItem.setValueMap(ClientMapUtil.getInstance()
					.getContractorPriceTypes());

			searchForm.setFields(orgNameItem, contractorType, orgDepItem,
					limitItem, phoneItem, priceTypeItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(830);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenterBK.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton(CallCenterBK.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			setRangePriceBtn = new ToolStripButton(
					CallCenterBK.constants.setCurrentPrice(), "moneySmall.png");
			setRangePriceBtn.setLayoutAlign(Alignment.LEFT);
			setRangePriceBtn.setWidth(50);
			toolStrip.addButton(setRangePriceBtn);

			toolStrip.addSeparator();

			viewCallCntBtn = new ToolStripButton(
					CallCenterBK.constants.callsCount(), "stats.png");
			viewCallCntBtn.setLayoutAlign(Alignment.LEFT);
			viewCallCntBtn.setWidth(50);
			toolStrip.addButton(viewCallCntBtn);

			viewChargesSumBtn = new ToolStripButton(
					CallCenterBK.constants.charges(), "moneySmall.png");
			viewChargesSumBtn.setLayoutAlign(Alignment.LEFT);
			viewChargesSumBtn.setWidth(50);
			toolStrip.addButton(viewChargesSumBtn);

			blockPhoneListBtn = new ToolStripButton(
					CallCenterBK.constants.blockPhoneList(),
					"telephone_delete.png");
			blockPhoneListBtn.setLayoutAlign(Alignment.LEFT);
			blockPhoneListBtn.setWidth(50);
			toolStrip.addButton(blockPhoneListBtn);

			toolStrip.addSeparator();

			contractorsBillBtn = new ToolStripButton(
					CallCenterBK.constants.billing(), "billing.png");
			contractorsBillBtn.setLayoutAlign(Alignment.LEFT);
			contractorsBillBtn.setWidth(50);
			toolStrip.addButton(contractorsBillBtn);

			contractorsBillFullBtn = new ToolStripButton(
					CallCenterBK.constants.billingDetailed(), "billing.png");
			contractorsBillFullBtn.setLayoutAlign(Alignment.LEFT);
			contractorsBillFullBtn.setWidth(50);
			toolStrip.addButton(contractorsBillFullBtn);

			toolStrip.addSeparator();

			contractorsBillBtn1 = new ToolStripButton(
					CallCenterBK.constants.billing1(), "billing.png");
			contractorsBillBtn1.setLayoutAlign(Alignment.LEFT);
			contractorsBillBtn1.setWidth(50);
			toolStrip.addButton(contractorsBillBtn1);

			contractorsBillFullBtn1 = new ToolStripButton(
					CallCenterBK.constants.billingDetailed1(), "billing.png");
			contractorsBillFullBtn1.setLayoutAlign(Alignment.LEFT);
			contractorsBillFullBtn1.setWidth(50);
			toolStrip.addButton(contractorsBillFullBtn1);

			contractorsGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer deleted = countryRecord
							.getAttributeAsInt("deleted");
					if (deleted != null && !deleted.equals(0)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			contractorsGrid.setWidth100();
			contractorsGrid.setHeight100();
			contractorsGrid.setAlternateRecordStyles(true);
			contractorsGrid.setDataSource(contractorsDS);
			contractorsGrid.setAutoFetchData(false);
			contractorsGrid.setShowFilterEditor(false);
			contractorsGrid.setCanEdit(false);
			contractorsGrid.setCanRemoveRecords(false);
			contractorsGrid.setFetchOperation("searchAllContractors");
			contractorsGrid.setShowRowNumbers(true);
			contractorsGrid.setCanHover(true);
			contractorsGrid.setShowHover(true);
			contractorsGrid.setShowHoverComponents(true);
			contractorsGrid.setWrapCells(true);
			contractorsGrid.setFixedRecordHeights(false);
			contractorsGrid.setCanDragSelectText(true);

			ListGridField orgName = new ListGridField("orgName",
					CallCenterBK.constants.orgNameFull());
			ListGridField orgDepName = new ListGridField("orgDepName",
					CallCenterBK.constants.department(), 300);
			ListGridField start_date = new ListGridField("start_date",
					CallCenterBK.constants.startDate(), 120);
			ListGridField end_date = new ListGridField("end_date",
					CallCenterBK.constants.endDate(), 120);
			ListGridField price = new ListGridField("price",
					CallCenterBK.constants.price(), 70);
			ListGridField range_curr_price = new ListGridField(
					"range_curr_price",
					CallCenterBK.constants.currentPriceShort(), 70);
			ListGridField price_type_descr = new ListGridField(
					"price_type_descr", CallCenterBK.constants.priceType(), 70);
			ListGridField critical_number = new ListGridField(
					"critical_number", CallCenterBK.constants.limit(), 70);

			start_date.setAlign(Alignment.CENTER);
			end_date.setAlign(Alignment.CENTER);
			price_type_descr.setAlign(Alignment.CENTER);
			price.setAlign(Alignment.CENTER);
			range_curr_price.setAlign(Alignment.CENTER);
			critical_number.setAlign(Alignment.CENTER);

			contractorsGrid.setFields(orgName, orgDepName, price_type_descr,
					price, range_curr_price, critical_number, start_date,
					end_date);

			mainLayout.addMember(contractorsGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});

			orgNameItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			orgDepItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					orgNameItem.clearValue();
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
				/*	DlgAddEditContractor dlgAddEditContractor = new DlgAddEditContractor(
							contractorsGrid, null);
					dlgAddEditContractor.show();*/
					new DlgContractorPhones().show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = contractorsGrid
							.getSelectedRecord();

					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}

					DlgAddEditContractor dlgAddEditContractor = new DlgAddEditContractor(
							contractorsGrid, listGridRecord);
					dlgAddEditContractor.show();
				}
			});
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord listGridRecord = contractorsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (!deleted.equals(0)) {
						SC.say(CallCenterBK.constants.recordAlrDisabled());
						return;
					}
					SC.ask(CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										changeStatus(listGridRecord, 1);
									}
								}
							});
				}
			});
			contractorsGrid
					.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
						@Override
						public void onRecordDoubleClick(
								RecordDoubleClickEvent event) {
							ListGridRecord listGridRecord = contractorsGrid
									.getSelectedRecord();
							DlgAddEditContractor dlgAddEditContractor = new DlgAddEditContractor(
									contractorsGrid, listGridRecord);
							dlgAddEditContractor.show();
						}
					});

			viewCallCntBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = contractorsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer contract_id = listGridRecord
							.getAttributeAsInt("contract_id");
					showContractorCallCnt(contract_id);
				}

			});

			viewChargesSumBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = contractorsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer contract_id = listGridRecord
							.getAttributeAsInt("contract_id");
					showContractorCharges(contract_id);

				}
			});

			blockPhoneListBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = contractorsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					showBlockPhoneList(listGridRecord);
				}
			});

			setRangePriceBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = contractorsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					Integer price_type = listGridRecord
							.getAttributeAsInt("price_type");
					if (price_type == null || !price_type.equals(1)) {
						SC.say(CallCenterBK.constants.isNotAdvancePriceType());
						return;
					}
					updateCurrentRangePrice(listGridRecord);
				}
			});

			contractorsBillBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					getContractorsBilling(false);
				}
			});
			contractorsBillFullBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					getContractorsBilling(true);
				}
			});

			contractorsBillBtn1.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					getContractorsBilling1(false);
				}
			});
			contractorsBillFullBtn1.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					getContractorsBilling1(true);
				}
			});

			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void getContractorsBilling1(boolean full) {
		try {
			ListGridRecord listGridRecord = contractorsGrid.getSelectedRecord();
			if (listGridRecord == null) {
				SC.say(CallCenterBK.constants.pleaseSelrecord());
				return;
			}
			Integer contract_id = listGridRecord
					.getAttributeAsInt("contract_id");
			if (contract_id == null) {
				SC.say(CallCenterBK.constants.pleaseSelrecord());
				return;
			}

			Integer contractor_type = new Integer(
					contractorType.getValueAsString());

			DlgGetContractorsBilling dlgGetCOntractorsBilling = new DlgGetContractorsBilling(
					contract_id, full, contractor_type);
			dlgGetCOntractorsBilling.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void getContractorsBilling(boolean full) {
		try {
			Integer contractor_type = new Integer(
					contractorType.getValueAsString());

			DlgGetContractorsBilling dlgGetCOntractorsBilling = new DlgGetContractorsBilling(
					null, full, contractor_type);
			dlgGetCOntractorsBilling.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void updateCurrentRangePrice(ListGridRecord listGridRecord) {
		try {
			DlgUpdateContrCurrRangePrice blockPhoneList = new DlgUpdateContrCurrRangePrice(
					listGridRecord, contractorsGrid);
			blockPhoneList.show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void showBlockPhoneList(ListGridRecord listGridRecord) {
		try {
			DlgBlackPhoneList blockPhoneList = new DlgBlackPhoneList(
					listGridRecord);
			blockPhoneList.show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void showContractorCharges(Integer contract_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("contract_id", contract_id);

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "getContractorCharges");
			contractorsGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						Record record = records[0];
						Double contractor_charges = record
								.getAttributeAsDouble("contractor_charges");
						if (contractor_charges == null) {
							contractor_charges = 0D;
						}
						SC.say((CallCenterBK.constants.contractorCharges() + contractor_charges));
					}
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void showContractorCallCnt(Integer contract_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("contract_id", contract_id);

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "getContractorCallCnt");
			contractorsGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						Record record = records[0];
						Integer contractor_call_cnt = record
								.getAttributeAsInt("contractor_call_cnt");
						if (contractor_call_cnt == null) {
							contractor_call_cnt = 0;
						}
						SC.say((CallCenterBK.constants.contractorCallCnt() + contractor_call_cnt));
					}
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			String org_name = orgNameItem.getValueAsString();
			if (org_name != null && !org_name.trim().equals("")) {
				String tmp = org_name.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("org_name" + i, item);
					i++;
				}
			}
			String orgDepName = orgDepItem.getValueAsString();
			if (orgDepName != null && !orgDepName.trim().equals("")) {
				String tmp = orgDepName.trim();
				String arrStr[] = tmp.split(" ");
				int i = 1;
				for (String string : arrStr) {
					String item = string.trim();
					if (item.equals("")) {
						continue;
					}
					criteria.setAttribute("orgDepName" + i, item);
					i++;
				}
			}

			String is_budget_str = contractorType.getValueAsString();
			if (is_budget_str != null && !is_budget_str.trim().equals("")
					&& !is_budget_str.trim().equals("-1")) {
				criteria.setAttribute("is_budget", new Integer(is_budget_str));
			}
			String phone = phoneItem.getValueAsString();
			if (phone != null && !phone.trim().equals("")) {
				criteria.setAttribute("phone", phone);
			}

			String limitStr = limitItem.getValueAsString();
			if (limitStr != null && !limitStr.equals("")) {
				Integer limitType = new Integer(limitStr);
				if (limitType != -1) {
					criteria.setAttribute("limitType", limitType);
				}
			}

			Integer price_type = new Integer(priceTypeItem.getValueAsString());
			if (price_type != -1) {
				criteria.setAttribute("price_type", price_type);
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllContractors");
			contractorsGrid.invalidateCache();
			contractorsGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void changeStatus(ListGridRecord listGridRecord, Integer deleted) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("deleted", 1);
			record.setAttribute("contract_id",
					listGridRecord.getAttributeAsInt("contract_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeContractor");
			contractorsGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
