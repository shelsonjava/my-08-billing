package com.info08.billing.callcenter.client.content.admin;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.dialogs.admin.DlgAddEditContractor;
import com.info08.billing.callcenter.client.dialogs.admin.DlgBlockPhoneList;
import com.info08.billing.callcenter.client.dialogs.admin.DlgUpdateContrCurrRangePrice;
import com.info08.billing.callcenter.client.singletons.ClientMapUtil;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
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
	private TextItem phoneItem;
	private SelectItem contractorType;
	private SelectItem deletedItem;

	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton deleteBtn;
	private ToolStripButton setRangePriceBtn;

	// private ToolStripButton restoreBtn;
	private ToolStripButton viewCallCntBtn;
	private ToolStripButton viewChargesSumBtn;
	private ToolStripButton blockPhoneListBtn;

	private ListGrid contractorsGrid;
	private DataSource contractorsDS;

	public TabContractors() {
		try {

			setTitle(CallCenter.constants.contractors());
			setCanClose(true);

			contractorsDS = DataSource.get("ContractorsDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			//
			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(830);
			searchForm.setTitleWidth(250);
			searchForm.setNumCols(4);
			mainLayout.addMember(searchForm);

			orgNameItem = new TextItem();
			orgNameItem.setTitle(CallCenter.constants.orgNameFull());
			orgNameItem.setWidth(250);
			orgNameItem.setName("orgNameItem");

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenter.constants.phone());
			phoneItem.setWidth(250);
			phoneItem.setName("phoneItem");

			contractorType = new SelectItem();
			contractorType.setTitle(CallCenter.constants.contractorType());
			contractorType.setWidth(250);
			contractorType.setName("contractorType");
			contractorType.setDefaultToFirstOption(true);
			contractorType.setValueMap(ClientMapUtil.getInstance()
					.getContractorTypes());

			deletedItem = new SelectItem();
			deletedItem.setTitle(CallCenter.constants.status());
			deletedItem.setWidth(250);
			deletedItem.setName("deletedItem");
			deletedItem.setDefaultToFirstOption(true);
			deletedItem.setValueMap(ClientMapUtil.getInstance().getStatuses());

			searchForm.setFields(orgNameItem, contractorType, phoneItem,
					deletedItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(830);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenter.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenter.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenter.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton(CallCenter.constants.modify(),
					"editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			deleteBtn = new ToolStripButton(CallCenter.constants.disable(),
					"deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			setRangePriceBtn = new ToolStripButton(
					CallCenter.constants.setCurrentPrice(), "moneySmall.png");
			setRangePriceBtn.setLayoutAlign(Alignment.LEFT);
			setRangePriceBtn.setWidth(50);
			toolStrip.addButton(setRangePriceBtn);

			// restoreBtn = new ToolStripButton(CallCenter.constants.enable(),
			// "restoreIcon.gif");
			// restoreBtn.setLayoutAlign(Alignment.LEFT);
			// restoreBtn.setWidth(50);
			// toolStrip.addButton(restoreBtn);

			toolStrip.addSeparator();

			viewCallCntBtn = new ToolStripButton(
					CallCenter.constants.callsCount(), "stats.png");
			viewCallCntBtn.setLayoutAlign(Alignment.LEFT);
			viewCallCntBtn.setWidth(50);
			toolStrip.addButton(viewCallCntBtn);

			viewChargesSumBtn = new ToolStripButton(
					CallCenter.constants.charges(), "moneySmall.png");
			viewChargesSumBtn.setLayoutAlign(Alignment.LEFT);
			viewChargesSumBtn.setWidth(50);
			toolStrip.addButton(viewChargesSumBtn);

			blockPhoneListBtn = new ToolStripButton(
					CallCenter.constants.blockPhoneList(),
					"telephone_delete.png");
			blockPhoneListBtn.setLayoutAlign(Alignment.LEFT);
			blockPhoneListBtn.setWidth(50);
			toolStrip.addButton(blockPhoneListBtn);

			// toolStrip.addSeparator();
			//
			// blockPhonesBtn = new ToolStripButton(
			// CallCenter.constants.blockPhone(), "telephone_delete.png");
			// blockPhonesBtn.setLayoutAlign(Alignment.LEFT);
			// blockPhonesBtn.setWidth(50);
			// toolStrip.addButton(blockPhonesBtn);
			//
			// unBlockPhonesBtn = new ToolStripButton(
			// CallCenter.constants.unBlockPhone(), "telephone_add.png");
			// unBlockPhonesBtn.setLayoutAlign(Alignment.LEFT);
			// unBlockPhonesBtn.setWidth(50);
			// toolStrip.addButton(unBlockPhonesBtn);

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
					CallCenter.constants.orgNameFull());
			ListGridField orgDepName = new ListGridField("orgDepName",
					CallCenter.constants.department(), 300);
			ListGridField start_date = new ListGridField("start_date",
					CallCenter.constants.startDate(), 120);
			ListGridField end_date = new ListGridField("end_date",
					CallCenter.constants.endDate(), 120);
			ListGridField note = new ListGridField("note",
					CallCenter.constants.comment(), 200);
			ListGridField price = new ListGridField("price",
					CallCenter.constants.price(), 70);
			ListGridField range_curr_price = new ListGridField(
					"range_curr_price",
					CallCenter.constants.currentPriceShort(), 70);
			ListGridField price_type_descr = new ListGridField(
					"price_type_descr", CallCenter.constants.priceType(), 70);

			start_date.setAlign(Alignment.CENTER);
			end_date.setAlign(Alignment.CENTER);
			price_type_descr.setAlign(Alignment.CENTER);
			price.setAlign(Alignment.CENTER);
			range_curr_price.setAlign(Alignment.CENTER);

			contractorsGrid.setFields(orgName, orgDepName, note,
					price_type_descr, price, range_curr_price, start_date,
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
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = contractorsGrid
							.getSelectedRecord();

					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
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
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					Integer deleted = listGridRecord
							.getAttributeAsInt("deleted");
					if (!deleted.equals(0)) {
						SC.say(CallCenter.constants.recordAlrDisabled());
						return;
					}
					SC.ask(CallCenter.constants.askForDisable(),
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
			// restoreBtn.addClickHandler(new ClickHandler() {
			// @Override
			// public void onClick(ClickEvent event) {
			// final ListGridRecord listGridRecord = contractorsGrid
			// .getSelectedRecord();
			// if (listGridRecord == null) {
			// SC.say(CallCenter.constants.pleaseSelrecord());
			// return;
			// }
			// Integer deleted = listGridRecord
			// .getAttributeAsInt("deleted");
			// if (deleted.equals(0)) {
			// SC.say(CallCenter.constants.recordAlrEnabled());
			// return;
			// }
			// SC.ask(CallCenter.constants.askForEnable(),
			// new BooleanCallback() {
			// @Override
			// public void execute(Boolean value) {
			// if (value) {
			// changeStatus(listGridRecord, 0);
			// }
			// }
			// });
			// }
			// });

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

			// blockPhonesBtn.addClickHandler(new ClickHandler() {
			// @Override
			// public void onClick(ClickEvent event) {
			// final ListGridRecord listGridRecord = contractorsGrid
			// .getSelectedRecord();
			// if (listGridRecord == null) {
			// SC.say(CallCenter.constants.pleaseSelrecord());
			// return;
			// }
			// DlgBlockUnBlockContrPhones blockContrPhones = new
			// DlgBlockUnBlockContrPhones(
			// contractorsGrid, listGridRecord, true);
			// blockContrPhones.show();
			// }
			// });
			// unBlockPhonesBtn.addClickHandler(new ClickHandler() {
			// @Override
			// public void onClick(ClickEvent event) {
			// final ListGridRecord listGridRecord = contractorsGrid
			// .getSelectedRecord();
			// if (listGridRecord == null) {
			// SC.say(CallCenter.constants.pleaseSelrecord());
			// return;
			// }
			// DlgBlockUnBlockContrPhones blockContrPhones = new
			// DlgBlockUnBlockContrPhones(
			// contractorsGrid, listGridRecord, false);
			// blockContrPhones.show();
			// }
			// });

			viewCallCntBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = contractorsGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.pleaseSelrecord());
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
						SC.say(CallCenter.constants.pleaseSelrecord());
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
						SC.say(CallCenter.constants.pleaseSelrecord());
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
						SC.say(CallCenter.constants.pleaseSelrecord());
						return;
					}
					Integer price_type = listGridRecord
							.getAttributeAsInt("price_type");
					if (price_type == null || !price_type.equals(1)) {
						SC.say(CallCenter.constants.isNotAdvancePriceType());
						return;
					}
					updateCurrentRangePrice(listGridRecord);
				}
			});

			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
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
			DlgBlockPhoneList blockPhoneList = new DlgBlockPhoneList(
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
					.getSessionPerson().getUserName());
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
						SC.say((CallCenter.constants.contractorCharges() + contractor_charges));
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
					.getSessionPerson().getUserName());
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
						SC.say((CallCenter.constants.contractorCallCnt() + contractor_call_cnt));
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
			String is_budget_str = contractorType.getValueAsString();
			if (is_budget_str != null && !is_budget_str.trim().equals("")
					&& !is_budget_str.trim().equals("-1")) {
				criteria.setAttribute("is_budget", new Integer(is_budget_str));
			}
			String phone = phoneItem.getValueAsString();
			if (phone != null && !phone.trim().equals("")) {
				criteria.setAttribute("phone", phone);
			}

			String deleted = deletedItem.getValueAsString();
			if (deleted != null && !deleted.equals("")) {
				criteria.setAttribute("deleted", new Integer(deleted));
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
					.getSessionPerson().getUserName());
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
