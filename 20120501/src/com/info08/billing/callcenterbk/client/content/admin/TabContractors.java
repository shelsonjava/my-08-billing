package com.info08.billing.callcenterbk.client.content.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgAddEditContractor;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgBlackPhoneList;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgGetContractorsBilling;
import com.info08.billing.callcenterbk.client.dialogs.admin.DlgUpdateContrCurrRangePrice;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
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
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EnumUtil;
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
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

public class TabContractors extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;
	private TextItem orgNameItem;
	private TextItem phoneItem;
	private SelectItem contractorType;
	private SelectItem limitItem;
	private SelectItem priceTypeItem;
	private SelectItem operatorItem;

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

	private ToolStripButton contractorsBillFullBtn2;

	private ToolStripButton phonesExportBtn;

	private ListGrid contractorsGrid;
	private DataSource CorporateClientsDS;

	public TabContractors() {
		try {

			setTitle(CallCenterBK.constants.contractors());
			setCanClose(true);

			CorporateClientsDS = DataSource.get("CorporateClientsDS");

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

			operatorItem = new SelectItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setWidth(250);
			operatorItem.setName("operator_src");
			operatorItem.setDefaultToFirstOption(true);
			ClientUtils.fillCombo(operatorItem, "OperatorsDS",
					"searchOperators", "operator_src", "operator_src_descr");

			searchForm.setFields(orgNameItem, contractorType, limitItem,
					phoneItem, priceTypeItem, operatorItem);

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
			blockPhoneListBtn.setVisibility(Visibility.HIDDEN);
			toolStrip.addButton(blockPhoneListBtn);

			toolStrip.addSeparator();

			Menu billingMenu = new Menu();
			billingMenu.setShowShadow(false);
			billingMenu.setShadowOffset(50);

			MenuItem miContrBill = new MenuItem(
					CallCenterBK.constants.billing(), "billing.png");
			MenuItem miContrBillFull = new MenuItem(
					CallCenterBK.constants.billingDetailed(), "billing.png");
			MenuItem miContrBill1 = new MenuItem(
					CallCenterBK.constants.billing1(), "billing.png");
			MenuItem miContrBillFull1 = new MenuItem(
					CallCenterBK.constants.billingDetailed1(), "billing.png");
			MenuItem miContrBillFull2 = new MenuItem(
					CallCenterBK.constants.billingDetailed2(), "billing.png");
			MenuItem miContrBillFull3 = new MenuItem(
					CallCenterBK.constants.billingDetailed3(), "billing.png");

			billingMenu.setItems(miContrBill, miContrBillFull, miContrBill1,
					miContrBillFull1, miContrBillFull2, miContrBillFull3);

			ToolStripMenuButton menuButton = new ToolStripMenuButton(
					CallCenterBK.constants.billingtitle(), billingMenu);
			toolStrip.addMenuButton(menuButton);

			contractorsBillBtn = new ToolStripButton(
					CallCenterBK.constants.billing(), "billing.png");
			contractorsBillBtn.setLayoutAlign(Alignment.LEFT);
			contractorsBillBtn.setWidth(50);
			// toolStrip.addButton(contractorsBillBtn);

			contractorsBillFullBtn = new ToolStripButton(
					CallCenterBK.constants.billingDetailed(), "billing.png");
			contractorsBillFullBtn.setLayoutAlign(Alignment.LEFT);
			contractorsBillFullBtn.setWidth(50);
			// toolStrip.addButton(contractorsBillFullBtn);

			toolStrip.addSeparator();

			contractorsBillBtn1 = new ToolStripButton(
					CallCenterBK.constants.billing1(), "billing.png");
			contractorsBillBtn1.setLayoutAlign(Alignment.LEFT);
			contractorsBillBtn1.setWidth(50);
			// toolStrip.addButton(contractorsBillBtn1);

			contractorsBillFullBtn1 = new ToolStripButton(
					CallCenterBK.constants.billingDetailed1(), "billing.png");
			contractorsBillFullBtn1.setLayoutAlign(Alignment.LEFT);
			contractorsBillFullBtn1.setWidth(50);
			// toolStrip.addButton(contractorsBillFullBtn1);

			contractorsBillFullBtn2 = new ToolStripButton(
					CallCenterBK.constants.billingDetailed2(), "billing.png");
			contractorsBillFullBtn2.setLayoutAlign(Alignment.LEFT);
			contractorsBillFullBtn2.setWidth(50);
			// toolStrip.addButton(contractorsBillFullBtn2);

			phonesExportBtn = new ToolStripButton(
					CallCenterBK.constants.phones_export(), "excel.png");
			phonesExportBtn.setLayoutAlign(Alignment.LEFT);
			phonesExportBtn.setWidth(50);

			toolStrip.addSeparator();
			toolStrip.addButton(phonesExportBtn);

			contractorsGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					return super.getCellCSSText(record, rowNum, colNum);
				};
			};

			contractorsGrid.setWidth100();
			contractorsGrid.setHeight100();
			contractorsGrid.setAlternateRecordStyles(true);
			contractorsGrid.setDataSource(CorporateClientsDS);
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

			ListGridField orgName = new ListGridField("organization_name",
					CallCenterBK.constants.orgNameFull());
			ListGridField remark = new ListGridField("remark",
					CallCenterBK.constants.comment(), 300);
			ListGridField contract_start_date = new ListGridField(
					"contract_start_date", CallCenterBK.constants.startDate(),
					120);
			ListGridField contract_end_date = new ListGridField(
					"contract_end_date", CallCenterBK.constants.endDate(), 120);
			ListGridField call_price = new ListGridField("call_price",
					CallCenterBK.constants.price(), 70);
			ListGridField range_curr_price = new ListGridField(
					"range_curr_price",
					CallCenterBK.constants.currentPriceShort(), 70);
			ListGridField price_type_descr = new ListGridField(
					"price_type_descr", CallCenterBK.constants.priceType(), 70);
			ListGridField critical_number = new ListGridField(
					"critical_number", CallCenterBK.constants.limit(), 70);

			contract_start_date.setAlign(Alignment.CENTER);
			contract_end_date.setAlign(Alignment.CENTER);
			price_type_descr.setAlign(Alignment.CENTER);
			call_price.setAlign(Alignment.CENTER);
			range_curr_price.setAlign(Alignment.CENTER);
			critical_number.setAlign(Alignment.CENTER);

			contractorsGrid.setFields(orgName, remark, price_type_descr,
					call_price, range_curr_price, critical_number,
					contract_start_date, contract_end_date);

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

			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					orgNameItem.clearValue();
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditContractor dlgAddEditContractor = new DlgAddEditContractor(
							contractorsGrid, null);
					dlgAddEditContractor.show();
					// new DlgContractorPhones().show();
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
					SC.ask(CallCenterBK.constants.askForDisable(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										removeRecord(listGridRecord);
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
					Integer corporate_client_id = listGridRecord
							.getAttributeAsInt("corporate_client_id");
					showContractorCallCnt(corporate_client_id);
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
					Integer corporate_client_id = listGridRecord
							.getAttributeAsInt("corporate_client_id");
					showContractorCharges(corporate_client_id);

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
					getContractorsBilling(false, false, false);
				}
			});

			miContrBill
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						@Override
						public void onClick(MenuItemClickEvent event) {
							getContractorsBilling(false, false, false);
						}
					});

			contractorsBillFullBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					getContractorsBilling(true, false, false);
				}
			});

			miContrBillFull
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						@Override
						public void onClick(MenuItemClickEvent event) {
							getContractorsBilling(true, false, false);
						}
					});

			contractorsBillBtn1.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					getContractorsBilling1(false, false, false);
				}
			});

			miContrBill1
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						@Override
						public void onClick(MenuItemClickEvent event) {
							getContractorsBilling1(false, false, false);
						}
					});

			contractorsBillFullBtn1.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					getContractorsBilling1(true, false, false);
				}
			});
			miContrBillFull1
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						@Override
						public void onClick(MenuItemClickEvent event) {
							getContractorsBilling1(true, false, false);
						}
					});

			contractorsBillFullBtn2.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					getContractorsBilling1(true, true, false);
				}
			});
			miContrBillFull2
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						@Override
						public void onClick(MenuItemClickEvent event) {
							getContractorsBilling1(true, true, false);
						}
					});
			miContrBillFull3
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						@Override
						public void onClick(MenuItemClickEvent event) {
							getContractorsBilling1(true, true, true);
						}
					});

			phonesExportBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = contractorsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}

					Long corporate_client_id = listGridRecord
							.getAttributeAsLong("corporate_client_id");
					if (corporate_client_id == null) {
						SC.say(CallCenterBK.constants.invalidRecord());
						return;
					}

					DataSource dataSource = DataSource
							.get("CorpClientPhonesDS");

					DSRequest dsRequest = new DSRequest();
					dsRequest.setExportAs((ExportFormat) EnumUtil.getEnum(
							ExportFormat.values(), "xls"));
					dsRequest.setExportDisplay(ExportDisplay.DOWNLOAD);
					dsRequest.setOperationId("searchContractorPhones");
					dsRequest.setExportFields(new String[] { "phone_number" });

					Criteria criteria = new Criteria();
					criteria.setAttribute("corporate_client_id",
							corporate_client_id);

					dataSource.exportData(criteria, dsRequest,
							new DSCallback() {
								@Override
								public void execute(DSResponse response,
										Object rawData, DSRequest request) {

								}
							});

				}
			});

			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void getContractorsBilling1(boolean full, boolean byCnt,
			boolean byDays) {
		try {
			ListGridRecord listGridRecord = contractorsGrid.getSelectedRecord();
			if (listGridRecord == null) {
				SC.say(CallCenterBK.constants.pleaseSelrecord());
				return;
			}
			Integer corporate_client_id = listGridRecord
					.getAttributeAsInt("corporate_client_id");
			if (corporate_client_id == null) {
				SC.say(CallCenterBK.constants.pleaseSelrecord());
				return;
			}

			Integer contractor_type = new Integer(
					contractorType.getValueAsString());

			DlgGetContractorsBilling dlgGetCOntractorsBilling = new DlgGetContractorsBilling(
					corporate_client_id, full, byCnt, contractor_type, byDays);
			dlgGetCOntractorsBilling.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void getContractorsBilling(boolean full, boolean byCnt,
			boolean byDays) {
		try {
			Integer contractor_type = new Integer(
					contractorType.getValueAsString());

			DlgGetContractorsBilling dlgGetCOntractorsBilling = new DlgGetContractorsBilling(
					null, full, byCnt, contractor_type, byDays);
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

	private void showContractorCharges(Integer corporate_client_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("corporate_client_id", corporate_client_id);

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

	private void showContractorCallCnt(Integer corporate_client_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("corporate_client_id", corporate_client_id);

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
				criteria.setAttribute("org_name", org_name.trim());
			}
			String goverment_str = contractorType.getValueAsString();
			if (goverment_str != null && !goverment_str.trim().equals("")
					&& !goverment_str.trim().equals("-1")) {
				criteria.setAttribute("goverment", new Integer(goverment_str));
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
			Integer operator_src = Integer.parseInt(operatorItem
					.getValueAsString());
			criteria.setAttribute("operator_src", operator_src);

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

	private void removeRecord(ListGridRecord listGridRecord) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			record.setAttribute("corporate_client_id",
					listGridRecord.getAttributeAsInt("corporate_client_id"));

			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeContractor");
			contractorsGrid.removeData(record, new DSCallback() {
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
