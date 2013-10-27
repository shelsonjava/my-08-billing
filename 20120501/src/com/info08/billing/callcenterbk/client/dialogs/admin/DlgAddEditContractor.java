package com.info08.billing.callcenterbk.client.dialogs.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxEvent;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItem;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxItemDataChangedHandler;
import com.info08.billing.callcenterbk.client.common.components.MyComboBoxRecord;
import com.info08.billing.callcenterbk.client.singletons.ClientMapUtil;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgAddEditContractor extends Window {

	private VLayout hLayout;

	private MyComboBoxItem myComboBoxItemOrg;

	private DynamicForm dynamicForm;
	private DynamicForm dynamicForm1;
	private DynamicForm dynamicForm2;

	private DateItem startDateItem;
	private DateItem endDateItem;
	private CheckboxItem blockItem;
	private CheckboxItem smsWarnItem;
	private TextItem noteItem;
	private SelectItem contractorType;
	private SpinnerItem critNumberItem;
	private CheckboxItem priceTypeItem;
	private TextItem normalPriceItem;
	private CheckboxItem reCalcRancePriceItem;
	private TextItem currrentPriceItem;
	private SelectItem operatorItem;
	private TextItem mailServicePriceItem;

	private ListGridRecord editRecord;
	private ListGrid listGrid;
	private ListGrid listGridPrices;
	private ListGrid listGridPhones;

	private ToolStripButton addBtn;
	private ToolStripButton addBtnPhones;
	private ToolStripButton deleteBtn;
	private ToolStripButton deleteBtnPhones;

	private Integer organization_id;

	private IButton checkOrgCallsBtn;

	public DlgAddEditContractor(ListGrid listGrid, ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			this.listGrid = listGrid;
			setTitle(pRecord == null ? CallCenterBK.constants.addContractor()
					: CallCenterBK.constants.editContractor());

			setHeight(750);
			setWidth(800);
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

			String arrCapt[] = new String[2];
			arrCapt[0] = CallCenterBK.constants.orgNameFull();
			arrCapt[1] = CallCenterBK.constants.remark();
			myComboBoxItemOrg = new MyComboBoxItem("organization_name",
					CallCenterBK.constants.orgName(), 168, 769);

			myComboBoxItemOrg.setNameField("parrent_organization_id1");
			myComboBoxItemOrg.setMyDlgHeight(400);
			myComboBoxItemOrg.setMyDlgWidth(900);
			DataSource orgDS = DataSource.get("OrgDS");
			myComboBoxItemOrg.setMyDataSource(orgDS);
			myComboBoxItemOrg
					.setMyDataSourceOperation("searchMainOrgsForCBDoubleLike");
			myComboBoxItemOrg.setMyIdField("organization_id");
			myComboBoxItemOrg.setMyFields(fieldRecords);
			myComboBoxItemOrg.setMyChooserTitle(CallCenterBK.constants
					.organization());

			myComboBoxItemOrg.setMyDataSource(orgDS);
			myComboBoxItemOrg
					.setMyDataSourceOperation("searchMainOrgsForCBDoubleLike");
			myComboBoxItemOrg.setMyIdField("organization_id");
			// myComboBoxItemOrg.setMyDisplayField("organization_name","remark");
			myComboBoxItemOrg.setMyChooserTitle(CallCenterBK.constants
					.organization());
			myComboBoxItemOrg
					.addDataChangedHandler(new MyComboBoxItemDataChangedHandler() {
						@Override
						public void onDataChanged(MyComboBoxEvent event) {
							Integer tmp_organization_id = event
									.getListGridRecord().getAttributeAsInt(
											"organization_id");
							setUpPhones(tmp_organization_id);

						}
					});
			hLayout.addMember(myComboBoxItemOrg);

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(200);
			dynamicForm.setNumCols(4);
			hLayout.addMember(dynamicForm);

			startDateItem = new DateItem();
			startDateItem.setTitle(CallCenterBK.constants.startDate());
			startDateItem.setWidth(200);
			startDateItem.setName("startDateItem");
			startDateItem.setUseTextField(true);

			endDateItem = new DateItem();
			endDateItem.setTitle(CallCenterBK.constants.endDate());
			endDateItem.setWidth(200);
			endDateItem.setName("endDateItem");
			endDateItem.setUseTextField(true);

			blockItem = new CheckboxItem();
			blockItem.setTitle(CallCenterBK.constants.isBlockable());
			blockItem.setWidth(200);
			blockItem.setName("blockItem");

			smsWarnItem = new CheckboxItem();
			smsWarnItem.setTitle(CallCenterBK.constants.smsWarnable());
			smsWarnItem.setWidth(200);
			smsWarnItem.setName("smsWarnItem");

			noteItem = new TextItem();
			noteItem.setTitle(CallCenterBK.constants.comment());
			noteItem.setWidth(600);

			noteItem.setName("noteItem");
			noteItem.setColSpan(4);

			contractorType = new SelectItem();
			contractorType.setTitle(CallCenterBK.constants.contractorType());
			contractorType.setWidth(200);
			contractorType.setName("contractorType");
			contractorType.setValueMap(ClientMapUtil.getInstance()
					.getContractorTypes1());

			critNumberItem = new SpinnerItem();
			critNumberItem.setTitle(CallCenterBK.constants
					.contractorCritCallNumb());
			critNumberItem.setWidth(200);
			critNumberItem.setName("critNumberItem");
			critNumberItem.setValue(-999999999);

			priceTypeItem = new CheckboxItem();
			priceTypeItem.setTitle(CallCenterBK.constants.normalPrice());
			priceTypeItem.setWidth(200);
			priceTypeItem.setName("priceTypeItem");

			normalPriceItem = new TextItem();
			normalPriceItem.setTitle(CallCenterBK.constants.price());
			normalPriceItem.setWidth(200);
			normalPriceItem.setName("normalPriceItem");
			normalPriceItem.setKeyPressFilter("[0-9\\.]");

			mailServicePriceItem = new TextItem();
			mailServicePriceItem.setTitle(CallCenterBK.constants
					.mail_service_price());
			mailServicePriceItem.setWidth(200);
			mailServicePriceItem.setName("mailServicePriceItem");
			mailServicePriceItem.setKeyPressFilter("[0-9\\.]");

			operatorItem = new SelectItem();
			operatorItem.setTitle(CallCenterBK.constants.operator());
			operatorItem.setWidth(250);
			operatorItem.setName("operatorItem");
			operatorItem.setDefaultToFirstOption(true);
			ClientUtils.fillCombo(operatorItem, "OperatorsDS",
					"searchOperators", "operator_src", "operator_src_descr");

			dynamicForm.setFields(noteItem, contractorType, critNumberItem,
					startDateItem, endDateItem, blockItem, smsWarnItem,
					priceTypeItem, normalPriceItem, operatorItem,
					mailServicePriceItem);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			hLayout.addMember(toolStrip);

			addBtn = new ToolStripButton(CallCenterBK.constants.add(),
					"addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			deleteBtn = new ToolStripButton(CallCenterBK.constants.disable(),
					"deleteIcon.png");
			deleteBtn.setLayoutAlign(Alignment.LEFT);
			deleteBtn.setWidth(50);
			toolStrip.addButton(deleteBtn);

			addBtn.setDisabled(true);
			deleteBtn.setDisabled(true);

			dynamicForm2 = new DynamicForm();
			dynamicForm2.setAutoFocus(true);
			dynamicForm2.setWidth100();
			dynamicForm2.setTitleWidth(2);
			dynamicForm2.setNumCols(4);
			dynamicForm2.setTitleSuffix(" ");

			HLayout row = new HLayout();
			row.setWidth100();
			hLayout.addMember(row);

			reCalcRancePriceItem = new CheckboxItem();
			reCalcRancePriceItem.setTitle(CallCenterBK.constants
					.reCalcCopntrRangePrice());
			reCalcRancePriceItem.setWidth(200);
			reCalcRancePriceItem.setName("reCalcRancePriceItem");
			reCalcRancePriceItem.setValue(false);
			reCalcRancePriceItem.setTextBoxStyle("headerStyle12AndRed");

			currrentPriceItem = new TextItem();
			currrentPriceItem.setTitle(CallCenterBK.constants.currentPrice());
			currrentPriceItem.setWidth(200);
			currrentPriceItem.setName("currrentPriceItem");
			currrentPriceItem.setDisabled(true);
			currrentPriceItem.setValue("0");

			checkOrgCallsBtn = new IButton();
			checkOrgCallsBtn.setTitle(CallCenterBK.constants.callsCount());
			checkOrgCallsBtn.setWidth(150);

			row.addMembers(dynamicForm2, checkOrgCallsBtn);

			dynamicForm2.setFields(reCalcRancePriceItem, currrentPriceItem);

			ContractorPriceClientDS contractorPriceClientDS = ContractorPriceClientDS
					.getInstance();

			listGridPrices = new ListGrid();
			listGridPrices.setHeight(100);
			listGridPrices.setWidth100();
			listGridPrices.setDataSource(contractorPriceClientDS);
			listGridPrices.setDataPageSize(50);
			listGridPrices.setAutoFetchData(true);
			listGridPrices.setSelectionType(SelectionStyle.MULTIPLE);
			listGridPrices.setUseAllDataSourceFields(false);
			listGridPrices.setCanDragSelectText(true);

			ListGridField call_count_start = new ListGridField(
					"call_count_start", CallCenterBK.constants.startCount());
			ListGridField call_count_end = new ListGridField("call_count_end",
					CallCenterBK.constants.endCount());
			ListGridField price = new ListGridField("price",
					CallCenterBK.constants.price());
			call_count_start.setAlign(Alignment.CENTER);
			call_count_end.setAlign(Alignment.CENTER);
			price.setAlign(Alignment.CENTER);

			listGridPrices.setFields(call_count_start, call_count_end, price);

			hLayout.addMember(listGridPrices);

			dynamicForm1 = new DynamicForm();
			dynamicForm1.setAutoFocus(true);
			dynamicForm1.setWidth100();
			dynamicForm1.setTitleWidth(2);
			dynamicForm1.setNumCols(2);
			dynamicForm1.setTitleSuffix(" ");
			hLayout.addMember(dynamicForm1);

			ToolStrip toolStrip1 = new ToolStrip();
			toolStrip1.setWidth100();
			toolStrip1.setPadding(5);
			hLayout.addMember(toolStrip1);

			addBtnPhones = new ToolStripButton(CallCenterBK.constants.add(),
					"addIcon.png");
			addBtnPhones.setLayoutAlign(Alignment.LEFT);
			addBtnPhones.setWidth(50);
			toolStrip1.addButton(addBtnPhones);

			deleteBtnPhones = new ToolStripButton(
					CallCenterBK.constants.disable(), "deleteIcon.png");
			deleteBtnPhones.setLayoutAlign(Alignment.LEFT);
			deleteBtnPhones.setWidth(50);
			toolStrip1.addButton(deleteBtnPhones);

			addBtnPhones.setDisabled(true);
			deleteBtnPhones.setDisabled(true);

			ContractorPhoneClientDS contractorPhonesClientDS = ContractorPhoneClientDS
					.getInstance();

			listGridPhones = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					return super.getCellCSSText(record, rowNum, colNum);
				};
			};
			listGridPhones.setHeight100();
			listGridPhones.setWidth100();
			listGridPhones.setDataSource(contractorPhonesClientDS);
			listGridPhones.setDataPageSize(50);
			listGridPhones.setAutoFetchData(true);
			listGridPhones.setSelectionType(SelectionStyle.MULTIPLE);
			listGridPhones.setUseAllDataSourceFields(false);
			listGridPhones.setShowFilterEditor(true);
			listGridPhones.setFilterOnKeypress(true);
			listGridPhones.setCanDragSelectText(true);
			listGridPhones.setShowRowNumbers(true);

			ListGridField phone = new ListGridField("phone_number",
					CallCenterBK.constants.phone());
			phone.setAlign(Alignment.CENTER);
			listGridPhones.setFields(phone);
			hLayout.addMember(listGridPhones);

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

			priceTypeItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					Boolean value = (Boolean) event.getValue();
					drawByPriceType(value);
				}
			});

			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgAddEditContractorPrice dlgAddEditContractorPrice = new DlgAddEditContractorPrice(
							listGridPrices, null);
					dlgAddEditContractorPrice.show();
				}
			});

			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord record = listGridPrices
							.getSelectedRecord();
					if (record == null) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}

					SC.ask(CallCenterBK.constants.deleteConfirm(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										listGridPrices.removeData(record);
									}
								}
							});
				}
			});

			deleteBtnPhones.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final ListGridRecord records[] = listGridPhones
							.getSelectedRecords();
					if (records == null || records.length <= 0) {
						SC.say(CallCenterBK.constants.pleaseSelrecord());
						return;
					}
					SC.ask(CallCenterBK.constants.deleteConfirm(),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										for (ListGridRecord record : records) {
											listGridPhones.removeData(record);
										}
									}
								}
							});
				}
			});

			addBtnPhones.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					new DlgContractorPhones(organization_id, listGridPhones)
							.show();
				}
			});

			checkOrgCallsBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Object organization_id = myComboBoxItemOrg
							.getCurrentValue();

					if (organization_id == null) {
						SC.say(CallCenterBK.constants.noOrgOrDepSelected());
						return;
					}

					getCallCountByOrgOrDep(organization_id);
				}
			});

			addItem(hLayout);
			fillFields();

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	protected void setUpPhones(final Integer tmp_organization_id) {
		if (tmp_organization_id == null) {
			if (organization_id == null) {
				SC.warn("ორგანიზაცია აუცილებლად უნდა იყოს არჩეული!!!");
				disableEnablePhoneButtons(true);
				return;
			}
			disableEnablePhoneButtons(false);
			myComboBoxItemOrg.setDataValue(organization_id);
			return;
		}
		disableEnablePhoneButtons(false);
		if (listGridPhones.getRecordList().isEmpty()) {
			organization_id = tmp_organization_id;
			return;
		}

		listGridPhones.clearCriteria(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {

			}
		}, new DSRequest());
		if ((organization_id != null && !tmp_organization_id
				.equals(organization_id))) {
			SC.ask("შეიცვალა ორგანიზაცია, ხოლო ნომრების სია ცარიელი არაა, ამიტომ სია დაცარიელდება. განვაგრძოთ?",
					new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if (value.booleanValue()) {
								organization_id = tmp_organization_id;
								listGridPhones.selectAllRecords();
								listGridPhones.removeSelectedData();
							} else {
								myComboBoxItemOrg.setDataValue(organization_id);
							}
						}
					});
			return;
		}
		if (organization_id == null) {
			organization_id = tmp_organization_id;
		}
	}

	protected void getCallCountByOrgOrDep(Object organization_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Criteria record = new Criteria();
			record.setAttribute("organization_id", organization_id);
			DSRequest req = new DSRequest();
			DataSource dataSource = DataSource.get("CorporateClientsDS");

			req.setAttribute("operationId", "getCallsCountByMainAndYM");

			dataSource.fetchData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						Record record = records[0];
						String contractor_call_cnt = record
								.getAttribute("contractor_call_cnt");
						if (contractor_call_cnt == null) {
							contractor_call_cnt = "0";
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

	private void disableEnablePhoneButtons(Boolean value) {
		try {
			addBtnPhones.setDisabled(value);
			deleteBtnPhones.setDisabled(value);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void drawByPriceType(Boolean value) {
		try {
			if (value != null && value.booleanValue()) {
				normalPriceItem.setCanEdit(false);
				normalPriceItem.setDisabled(true);
				priceTypeItem.setTitle(CallCenterBK.constants.advancedPrice());
				priceTypeItem.redraw();
				addBtn.setDisabled(false);
				deleteBtn.setDisabled(false);
			} else {
				normalPriceItem.setCanEdit(true);
				normalPriceItem.setDisabled(false);
				priceTypeItem.setTitle(CallCenterBK.constants.normalPrice());
				priceTypeItem.redraw();
				addBtn.setDisabled(true);
				deleteBtn.setDisabled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void fillFields() {
		try {
			if (editRecord == null) {
				return;
			}

			DataSource dataSource = DataSource.get("ContractorsPricesDS");
			Criteria criteria = new Criteria();
			System.out.println(editRecord
					.getAttributeAsInt("corp_client_phone_id"));
			System.out.println(editRecord
					.getAttributeAsInt("corporate_client_id"));
			criteria.setAttribute("corp_client_phone_id",
					editRecord.getAttributeAsInt("corp_client_phone_id"));
			criteria.setAttribute("corporate_client_id",
					editRecord.getAttributeAsInt("corporate_client_id"));
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchContractorPrices");
			dataSource.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					if (records != null && records.length > 0) {
						for (Record record : records) {
							listGridPrices.addData(record);
						}
					}
				}
			}, dsRequest);

			DataSource CorpClientPhonesDS = DataSource
					.get("CorpClientPhonesDS");
			DSRequest dsRequest1 = new DSRequest();
			dsRequest1.setOperationId("searchContractorPhones");
			CorpClientPhonesDS.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record[] records = response.getData();
					if (records != null && records.length > 0) {
						for (Record record : records) {
							listGridPhones.addData(record);
						}
					}
				}
			}, dsRequest1);

			Integer organization_id = editRecord
					.getAttributeAsInt("organization_id");
			if (organization_id != null) {
				myComboBoxItemOrg.setDataValue(organization_id);
				this.organization_id = organization_id;
				disableEnablePhoneButtons(false);
			}
			String remark = editRecord.getAttributeAsString("remark");
			if (remark != null && !remark.trim().equals("")) {
				noteItem.setValue(remark);
			}
			Integer goverment = editRecord.getAttributeAsInt("goverment");
			if (goverment != null) {
				contractorType.setValue(goverment);
			}
			Integer max_call_count = editRecord
					.getAttributeAsInt("max_call_count");
			if (max_call_count != null) {
				critNumberItem.setValue(max_call_count);
			}
			Date contract_start_date = editRecord
					.getAttributeAsDate("contract_start_date");
			if (contract_start_date != null) {
				startDateItem.setValue(contract_start_date);
			}
			Date contract_end_date = editRecord
					.getAttributeAsDate("contract_end_date");
			if (contract_end_date != null) {
				endDateItem.setValue(contract_end_date);
			}
			Integer block = editRecord.getAttributeAsInt("block");
			if (block != null && block.equals(1)) {
				blockItem.setValue(true);
			}
			Integer sms_warning = editRecord.getAttributeAsInt("sms_warning");
			if (sms_warning != null && sms_warning.equals(1)) {
				smsWarnItem.setValue(true);
			}
			Integer price_type = editRecord.getAttributeAsInt("price_type");
			if (price_type != null && price_type.equals(1)) {
				priceTypeItem.setValue(true);
				drawByPriceType(true);
			}

			String call_price = editRecord.getAttributeAsString("call_price");
			if (call_price != null && !call_price.trim().equals("")) {
				normalPriceItem.setValue(call_price);
			}
			String mail_service_price = editRecord
					.getAttributeAsString("mail_service_price");
			if (mail_service_price != null
					&& !mail_service_price.trim().equals("")) {
				mailServicePriceItem.setValue(mail_service_price);
			}

			String range_curr_price = editRecord
					.getAttributeAsString("range_curr_price");
			if (range_curr_price != null && !range_curr_price.trim().equals("")) {
				currrentPriceItem.setValue(range_curr_price);
			}

			Integer operator_src = editRecord.getAttributeAsInt("operator_src");
			if (operator_src != null) {
				operatorItem.setValue(operator_src);
			}

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void save() {
		try {
			// Integer organization_id = myComboBoxItemOrg.getMyId();
			// if (organization_id == null || organization_id.intValue() <= 0) {
			// SC.say(CallCenterBK.constants.plzSelectOrg());
			// return;
			// }
			// Integer main_detail_id = myComboBoxItemOrgDetails.getMyId();
			// if (main_detail_id != null && main_detail_id.intValue() < 0) {
			// main_detail_id = 0;
			// }

			String remark = noteItem.getValueAsString();
			String is_budget_str = contractorType.getValueAsString();
			if (is_budget_str == null || is_budget_str.trim().equals("")) {
				SC.say(CallCenterBK.constants.plzSelectContractorType());
				return;
			}
			Integer goverment = null;
			try {
				goverment = Integer.parseInt(is_budget_str);
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.plzSelectContractorType());
				return;
			}

			String critNumberStr = critNumberItem.getValueAsString();
			Integer max_call_count = new Integer(Constants.criticalNumberIgnore);
			if (critNumberStr != null && !critNumberStr.trim().equals("")) {
				max_call_count = Integer.parseInt(critNumberStr);
			}
			if (max_call_count != Constants.criticalNumberIgnore
					&& max_call_count.intValue() < 0) {
				SC.say(CallCenterBK.constants.incorrectCriticalNumber());
				return;
			}

			Date contract_start_date = null;
			try {
				contract_start_date = startDateItem.getValueAsDate();
				if (contract_start_date == null) {
					SC.say(CallCenterBK.constants.plzSelectContrStartDate());
					return;
				}
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.plzSelectContrStartDate());
				return;
			}
			Date contract_end_date = null;
			try {
				contract_end_date = endDateItem.getValueAsDate();
				if (contract_end_date == null) {
					SC.say(CallCenterBK.constants.plzSelectContrEndDate());
					return;
				}
			} catch (Exception e) {
				SC.say(CallCenterBK.constants.plzSelectContrEndDate());
				return;
			}

			if (contract_start_date.after(contract_end_date)) {
				SC.say(CallCenterBK.constants.endDateMustBeAfterStartDate());
				return;
			}
			Date currDate = new Date();
			if (contract_start_date.getTime() > currDate.getTime()) {
				SC.say(CallCenterBK.constants.startDateMustNotBeAfterSysDate());
				return;
			}
			Integer block = new Integer(0);
			Boolean isBlock = blockItem.getValueAsBoolean();
			if (isBlock != null && isBlock.booleanValue()) {
				block = new Integer(1);
			}
			Integer sms_warning = new Integer(0);
			Boolean isSmsWarning = smsWarnItem.getValueAsBoolean();
			if (isSmsWarning != null && isSmsWarning.booleanValue()) {
				sms_warning = new Integer(1);
			}

			Integer price_type = new Integer(0);
			Boolean isPriceType = priceTypeItem.getValueAsBoolean();
			if (isPriceType != null && isPriceType.booleanValue()) {
				price_type = new Integer(1);
			}

			Integer operator_src = Integer.parseInt(operatorItem
					.getValueAsString());

			Boolean priceType = priceTypeItem.getValueAsBoolean();
			String normalPrice = normalPriceItem.getValueAsString();
			String mailSrvPrice = mailServicePriceItem.getValueAsString();

			if (mailSrvPrice != null && !mailSrvPrice.equals("")) {
				try {
					Float.parseFloat(mailSrvPrice);
					normalPrice = mailSrvPrice;
				} catch (Exception e) {
					SC.say(CallCenterBK.constants.invalid_mail_service_price());
					return;
				}
			}

			SortedMap<Integer, SortedMap<Integer, String>> sorted = new TreeMap<Integer, SortedMap<Integer, String>>();
			LinkedHashMap<String, LinkedHashMap<String, String>> sortedParam = new LinkedHashMap<String, LinkedHashMap<String, String>>();
			if (priceType == null || !priceType.booleanValue()) {
				String normalPriceStr = normalPriceItem.getValueAsString();
				if (normalPriceStr == null || normalPriceStr.trim().equals("")) {
					SC.say(CallCenterBK.constants.enterAmount());
					return;
				}

				try {
					Float.parseFloat(normalPriceStr);
					normalPrice = normalPriceStr;
				} catch (Exception e) {
					SC.say(CallCenterBK.constants.invalidAmount());
					return;
				}

			} else {
				RecordList recordList = listGridPrices.getDataAsRecordList();
				if (recordList == null || recordList.isEmpty()) {
					SC.say(CallCenterBK.constants.advPriceListIsEmpty());
					return;
				}
				int length = recordList.getLength();
				for (int i = 0; i < length; i++) {
					Record record = recordList.get(i);
					Integer call_count_start = record
							.getAttributeAsInt("call_count_start");
					Integer call_count_end = record
							.getAttributeAsInt("call_count_end");
					String price = record.getAttributeAsString("price");
					SortedMap<Integer, String> item = new TreeMap<Integer, String>();
					item.put(call_count_end, price);
					sorted.put(call_count_start, item);
				}

				if (!sorted.containsKey(new Integer(0))) {
					SC.say(CallCenterBK.constants.priceListMustContZero());
					return;
				}

				Set<Integer> keys = sorted.keySet();
				Integer pevStartCount = null;
				Integer pevEndCount = null;
				for (Integer currStartCount : keys) {
					SortedMap<Integer, String> value = sorted
							.get(currStartCount);
					Integer currEndCount = null;
					Set<Integer> vlSet = value.keySet();
					for (Integer integer : vlSet) {
						currEndCount = integer;
						break;
					}

					if (pevStartCount != null && pevEndCount != null) {
						if (currStartCount != (pevEndCount + 1)) {
							SC.say(CallCenterBK.constants.invalidPriceRange1());
							return;
						}
					}
					pevStartCount = currStartCount;
					pevEndCount = currEndCount;
				}
				Set<Integer> keys1 = sorted.keySet();
				for (Integer key1 : keys1) {
					SortedMap<Integer, String> value1 = sorted.get(key1);
					LinkedHashMap<String, String> itm = new LinkedHashMap<String, String>();
					Set<Integer> keys2 = value1.keySet();
					for (Integer key2 : keys2) {
						String value2 = value1.get(key2);
						itm.put(key2.toString(), value2);
						break;
					}
					sortedParam.put(key1.toString(), itm);
				}

			}

			LinkedHashMap<String, String> contractorAdvPhones = new LinkedHashMap<String, String>();

			RecordList recordList = listGridPhones.getDataAsRecordList();
			if ((recordList == null || recordList.isEmpty())) {
				SC.say(CallCenterBK.constants.phonesListIsEmpty());
				return;
			}
			int length = recordList.getLength();
			if (length > 0) {
				for (int i = 0; i < length; i++) {
					Record record = recordList.get(i);
					String phone = record.getAttributeAsString("phone_number");
					Integer deleted = record.getAttributeAsInt("deleted");
					if (deleted == null) {
						deleted = 0;
					}
					contractorAdvPhones.put(phone, deleted.toString());
				}
			}

			Integer pcorporate_client_id = null;

			Record record = new Record();
			if (editRecord != null) {
				pcorporate_client_id = editRecord
						.getAttributeAsInt("corporate_client_id");
				record.setAttribute("corporate_client_id", pcorporate_client_id);
			}
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUser_name();
			record.setAttribute("loggedUserName", loggedUser);
			record.setAttribute("rec_user", loggedUser);
			record.setAttribute("upd_user", loggedUser);
			record.setAttribute("organization_id", organization_id);

			record.setAttribute("remark", remark);
			record.setAttribute("goverment", goverment);
			record.setAttribute("contract_start_date", contract_start_date);
			record.setAttribute("contract_end_date", contract_end_date);
			record.setAttribute("block", block);
			record.setAttribute("sms_warning", sms_warning);
			record.setAttribute("price_type", price_type);
			record.setAttribute("call_price", normalPrice);
			record.setAttribute("mail_service_price", mailSrvPrice);
			record.setAttribute("contractorAdvPrices", sortedParam);
			record.setAttribute("contractorAdvPhones", contractorAdvPhones);
			record.setAttribute("max_call_count", max_call_count);
			record.setAttribute("operator_src", operator_src);

			record.setAttribute("range_curr_price",
					currrentPriceItem.getValueAsString());
			Boolean checkContractor = reCalcRancePriceItem.getValueAsBoolean();
			if (checkContractor != null && checkContractor.booleanValue()) {
				record.setAttribute("checkContractor", 1);
			} else {
				record.setAttribute("checkContractor", 0);
			}

			// if (contractorAdvPhones != null &&
			// !contractorAdvPhones.isEmpty()) {
			// checkContractPhones(record, contractorAdvPhones);
			// } else {
			saveContract(record);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	// private void checkContractPhones(final Record record,
	// LinkedHashMap<String, String> map) {
	// try {
	// DataSource ds = DataSource.get("CorporateClientsDS");
	// com.smartgwt.client.rpc.RPCManager.startQueue();
	// DSRequest req = new DSRequest();
	// Criteria cr = new Criteria();
	//
	// cr.setAttribute("contractorAdvPhones", map);
	// cr.setAttribute("organization_id", organization_id);
	//
	// req.setOperationId("checkPhones");
	// ds.fetchData(cr, new DSCallback() {
	//
	// @Override
	// public void execute(DSResponse response, Object rawData,
	// DSRequest request) {
	// saveContract(record);
	// }
	// }, req);
	//
	// com.smartgwt.client.rpc.RPCManager.sendQueue();
	// } catch (Exception e) {
	// e.printStackTrace();
	// SC.say(e.toString());
	// }
	// }

	private void saveContract(Record record) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			DSRequest req = new DSRequest();
			if (editRecord == null) {
				req.setAttribute("operationId", "addContractor");
				listGrid.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateContractor");
				listGrid.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			}

			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
