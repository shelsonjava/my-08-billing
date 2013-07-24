package com.info08.billing.callcenterbk.client.content.callcenter;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewOrg;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.utils.ClientUtils;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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

public class TabFindOrg extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private TextItem orgNameGeoItem;
	// private TextItem orgNameEngItem;
	private TextItem orgCommentItem;
	private TextItem orgDirectorItem;
	private TextItem orgLegalAddressItem;
	private TextItem orgIdentCodeItem;
	// private TextItem orgIdentCodeNewItem;
	private TextItem orgWebAddressItem;
	private TextItem orgEmailItem;
	private TextItem orgWorkingHoursItem;
	private TextItem orgDayOffsItem;
	private TextItem orgDepartmentItem;
	private DateItem orgFoundedStartItem;
	private DateItem orgFoundedEndItem;

	// address fields.
	private ComboBoxItem townItem;
	private TextItem realAddressDescrItem;
	private ComboBoxItem regionItem;
	private TextItem phoneNumberItem;
	// private TextItem blockItem;
	// private TextItem appartItem;

	// actions
	private IButton findButton;
	private IButton clearButton;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabFindOrg() {
		try {
			setTitle(CallCenterBK.constants.findOrg());
			setCanClose(true);

			datasource = DataSource.get("OrgDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(980);
			searchForm.setNumCols(4);
			searchForm.setTitleOrientation(TitleOrientation.TOP);
			mainLayout.addMember(searchForm);

			orgNameGeoItem = new TextItem();
			orgNameGeoItem.setTitle(CallCenterBK.constants.orgName());
			orgNameGeoItem.setWidth(245);
			orgNameGeoItem.setName("org_name_geo");
			orgNameGeoItem.setValue("");

			orgCommentItem = new TextItem();
			orgCommentItem.setTitle(CallCenterBK.constants.comment());
			orgCommentItem.setWidth(245);
			orgCommentItem.setName("org_comment");

			orgDirectorItem = new TextItem();
			orgDirectorItem.setTitle(CallCenterBK.constants.director());
			orgDirectorItem.setWidth(245);
			orgDirectorItem.setName("org_director");

			orgLegalAddressItem = new TextItem();
			orgLegalAddressItem.setTitle(CallCenterBK.constants.legalAddress());
			orgLegalAddressItem.setWidth(245);
			orgLegalAddressItem.setName("org_legal_address");

			orgIdentCodeItem = new TextItem();
			orgIdentCodeItem.setTitle(CallCenterBK.constants.identCodeAndNew());
			orgIdentCodeItem.setWidth(245);
			orgIdentCodeItem.setName("org_ident_code");

			orgWebAddressItem = new TextItem();
			orgWebAddressItem.setTitle(CallCenterBK.constants.webaddress());
			orgWebAddressItem.setWidth(245);
			orgWebAddressItem.setName("org_web_address");

			orgEmailItem = new TextItem();
			orgEmailItem.setTitle(CallCenterBK.constants.eMail());
			orgEmailItem.setWidth(245);
			orgEmailItem.setName("org_email");

			orgWorkingHoursItem = new TextItem();
			orgWorkingHoursItem.setTitle(CallCenterBK.constants.workinghours());
			orgWorkingHoursItem.setWidth(245);
			orgWorkingHoursItem.setName("org_working_hours");

			orgDayOffsItem = new TextItem();
			orgDayOffsItem.setTitle(CallCenterBK.constants.dayOffs());
			orgDayOffsItem.setWidth(245);
			orgDayOffsItem.setName("org_day_offs");

			orgDepartmentItem = new TextItem();
			orgDepartmentItem.setTitle(CallCenterBK.constants.department());
			orgDepartmentItem.setWidth(245);
			orgDepartmentItem.setName("org_department");

			orgFoundedStartItem = new DateItem();
			orgFoundedStartItem.setTitle(CallCenterBK.constants
					.orgFoundDateStart());
			orgFoundedStartItem.setWidth(245);
			orgFoundedStartItem.setName("org_dound_date_start");
			orgFoundedStartItem.setUseTextField(true);

			orgFoundedEndItem = new DateItem();
			orgFoundedEndItem
					.setTitle(CallCenterBK.constants.orgFoundDateEnd());
			orgFoundedEndItem.setWidth(245);
			orgFoundedEndItem.setName("org_dound_date_end");
			orgFoundedEndItem.setUseTextField(true);

			realAddressDescrItem = new TextItem();
			realAddressDescrItem.setTitle(CallCenterBK.constants.realAddress());
			realAddressDescrItem.setName("street_name");
			realAddressDescrItem.setWidth(245);

			phoneNumberItem = new TextItem();
			phoneNumberItem.setTitle(CallCenterBK.constants.phoneNumber());
			phoneNumberItem.setName("phoneNumberItem");
			phoneNumberItem.setWidth(245);

			townItem = new ComboBoxItem();
			townItem.setTitle(CallCenterBK.constants.town());
			townItem.setName("town_id");
			townItem.setWidth(245);
			ClientUtils.fillCombo(townItem, "TownsDS",
					"searchCitiesFromDBForCombos", "town_id", "town_name");
			townItem.setValue(Constants.defCityTbilisiId);

			regionItem = new ComboBoxItem();
			regionItem.setTitle(CallCenterBK.constants.cityRegion());
			regionItem.setName("town_district_id");
			regionItem.setWidth(245);

			Map<String, Integer> aditionalCriteria = new TreeMap<String, Integer>();
			aditionalCriteria.put("town_id", Constants.defCityTbilisiId);
			aditionalCriteria.put("need_indexes", 1);

			ClientUtils.fillCombo(regionItem, "TownDistrictDS",
					"searchCityRegsFromDBForCombos", "town_district_id",
					"town_district_name", aditionalCriteria);

			searchForm.setFields(orgNameGeoItem, orgCommentItem,
					orgDepartmentItem, orgDirectorItem, realAddressDescrItem,
					phoneNumberItem, regionItem, townItem, orgWorkingHoursItem,
					orgDayOffsItem, orgIdentCodeItem, orgLegalAddressItem,
					orgWebAddressItem, orgEmailItem, orgFoundedStartItem,
					orgFoundedEndItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(980);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle(CallCenterBK.constants.clear());

			findButton = new IButton();
			findButton.setTitle(CallCenterBK.constants.find());

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();

			boolean tmp_is_09_call = false;
			if (!serverSession.isWebSession()) {
				tmp_is_09_call = serverSession.getOperatorSrc().toString()
						.equals(Constants.OPERATOR_11809);
			}
			final boolean is_09_call = tmp_is_09_call;

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {
					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer statuse = countryRecord.getAttributeAsInt("status");
					Integer extra_priority = countryRecord
							.getAttributeAsInt("super_priority");
					Integer note_crit = countryRecord
							.getAttributeAsInt("priority");
					Integer flow_priority = countryRecord
							.getAttributeAsInt("flow_priority");
					Integer flow_priority_09 = countryRecord
							.getAttributeAsInt("flow_priority_09");

					if (!is_09_call && flow_priority != null
							&& flow_priority.intValue() < 0) {
						return "color:red;";
					} else if (is_09_call && flow_priority_09 != null
							&& flow_priority_09.intValue() < 0) {
						return "color:red;";
					}

					else if (extra_priority != null && extra_priority < 0) {
						return "color:red;";
					} else if (statuse != null && statuse.equals(2)) {
						if (note_crit != null && note_crit.intValue() == -1
								&& colNum == 4) {
							return "color:red;";
						} else {
							return "color:gray;";
						}
					} else if (statuse != null && statuse.equals(1)) {
						if (note_crit != null && note_crit.intValue() == -1
								&& colNum == 4) {
							return "color:red;";
						} else {
							return "color:blue;";
						}

					} else if (statuse != null && statuse.equals(3)) {
						if (note_crit != null && note_crit.intValue() == -1
								&& colNum == 4) {
							return "color:red;";
						} else {
							return "color:green;";
						}
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				};
			};

			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("customOrgSearchForCallCenterNew");
			listGrid.setCanSort(false);
			listGrid.setCanResizeFields(false);
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setDataPageSize(75);
			listGrid.setCanDragSelectText(true);
			listGrid.setCanGroupBy(false);
			listGrid.setCanAutoFitFields(false);
			listGrid.setCanResizeFields(false);

			ListGridField tree_org_parrent = new ListGridField(
					"tree_org_parrent", " ", 20);
			tree_org_parrent.setAlign(Alignment.CENTER);
			tree_org_parrent.setType(ListGridFieldType.IMAGE);
			tree_org_parrent.setImageURLPrefix("orgtypes/");
			tree_org_parrent.setImageURLSuffix(".png");
			tree_org_parrent.setCanSort(false);
			tree_org_parrent.setCanFilter(false);

			ListGridField tree_org_child = new ListGridField("tree_org_child",
					" ", 20);
			tree_org_child.setAlign(Alignment.CENTER);
			tree_org_child.setType(ListGridFieldType.IMAGE);
			tree_org_child.setImageURLPrefix("orgtypes/");
			tree_org_child.setImageURLSuffix(".png");
			tree_org_child.setCanSort(false);
			tree_org_child.setCanFilter(false);

			ListGridField org_name = new ListGridField("organization_name",
					CallCenterBK.constants.orgName(), 200);
			ListGridField real_address = new ListGridField(
					"call_center_address",
					CallCenterBK.constants.realAddress(), 170);
			ListGridField note = new ListGridField("remark",
					CallCenterBK.constants.comment());

			ListGridField director = new ListGridField("chief",
					CallCenterBK.constants.director(), 120);
			ListGridField workinghours = new ListGridField("work_hours",
					CallCenterBK.constants.workinghours(), 120);
			ListGridField dayoffs = new ListGridField("day_offs_descr",
					CallCenterBK.constants.dayOffs(), 120);

			listGrid.setFields(tree_org_child, tree_org_parrent, org_name,
					real_address, note, director, workinghours, dayoffs);
			mainLayout.addMember(listGrid);

			Label commentLabel = new Label(
					CallCenterBK.constants.orgGridComment());
			commentLabel.setWidth100();
			commentLabel.setHeight(15);
			commentLabel.setStyleName("fontRed");
			mainLayout.addMember(commentLabel);

			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					orgNameGeoItem.clearValue();
					orgCommentItem.clearValue();
					orgDepartmentItem.clearValue();
					orgDirectorItem.clearValue();
					realAddressDescrItem.clearValue();
					phoneNumberItem.clearValue();
					regionItem.clearValue();
					townItem.clearValue();
					orgWorkingHoursItem.clearValue();
					orgDayOffsItem.clearValue();
					orgIdentCodeItem.clearValue();
					orgLegalAddressItem.clearValue();
					orgWebAddressItem.clearValue();
					orgEmailItem.clearValue();
					orgFoundedEndItem.clearValue();
					orgFoundedStartItem.clearValue();

					searchForm.focusInItem(orgNameGeoItem);

				}
			});

			townItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					String value = townItem.getValueAsString();
					if (value == null) {
						return;
					}
					regionItem.clearValue();
					fillCityRegionCombo(new Integer(value));
				}
			});
			orgNameGeoItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgCommentItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgDepartmentItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgDirectorItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			phoneNumberItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgIdentCodeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgLegalAddressItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgWebAddressItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgEmailItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgDayOffsItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			orgWorkingHoursItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});
			realAddressDescrItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals("Enter")) {
						search();
					}
				}
			});

			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					DlgViewOrg dlgViewOrg = new DlgViewOrg(datasource, listGrid
							.getSelectedRecord());
					dlgViewOrg.show();

					String org_allert_by_buss_det = listGrid
							.getSelectedRecord().getAttributeAsString(
									"org_allert_by_buss_det");
					if (org_allert_by_buss_det != null
							&& !org_allert_by_buss_det.trim().equals("")) {
						SC.warn(org_allert_by_buss_det);
					}
				}
			});
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void fillCityRegionCombo(Integer defCityTbilisiId) {
		try {
			if (defCityTbilisiId == null) {
				defCityTbilisiId = Constants.defCityTbilisiId;
			}

			DataSource streetsDS = DataSource.get("TownDistrictDS");
			regionItem.setOptionOperationId("searchCityRegsFromDBForCombos");
			regionItem.setOptionDataSource(streetsDS);
			regionItem.setValueField("town_district_id");
			regionItem.setDisplayField("town_district_name");

			Criteria criteria = new Criterion();
			criteria.setAttribute("town_id", defCityTbilisiId);
			regionItem.setOptionCriteria(criteria);
			regionItem.setAutoFetchData(false);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void search() {
		try {
			Criteria criteria = new Criteria();
			criteria.setAttribute("operator_src", Constants.OPERATOR_11808);
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession != null && !serverSession.isWebSession()
					&& serverSession.getOperatorSrc() != null) {
				criteria.setAttribute("operator_src",
						serverSession.getOperatorSrc());
			}
			criteria.setAttribute("isCallCenter", "1");

			String org_name = orgNameGeoItem.getValueAsString();
			if (org_name != null && !org_name.trim().equals("")) {
				criteria.setAttribute("organization_name", org_name);
			}

			String remark = orgCommentItem.getValueAsString();
			if (remark != null && !remark.trim().equals("")) {
				criteria.setAttribute("remark", remark);
			}
			String chief = orgDirectorItem.getValueAsString();
			if (chief != null && !chief.trim().equals("")) {
				criteria.setAttribute("chief", chief);
			}

			String real_address_descr = realAddressDescrItem.getValueAsString();
			if (real_address_descr != null
					&& !real_address_descr.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("real_address_descr", real_address_descr);
			}

			String phoneNumber = phoneNumberItem.getValueAsString();
			if (phoneNumber != null && !phoneNumber.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("phone", phoneNumber);
			}

			String town_id = townItem.getValueAsString();
			if (town_id != null && !town_id.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("town_id", new Integer(town_id));
			}

			String work_hours = orgWorkingHoursItem.getValueAsString();
			if (work_hours != null && !work_hours.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("work_hours", work_hours);
			}

			String day_offs = orgDayOffsItem.getValueAsString();
			if (day_offs != null && !day_offs.trim().equals("")) {
				criteria.setAttribute("day_offs", day_offs);
			}

			String ident_code = orgIdentCodeItem.getValueAsString();
			if (ident_code != null && !ident_code.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("ident_code", ident_code);
			}

			String legal_real_address_descr = orgLegalAddressItem
					.getValueAsString();
			if (legal_real_address_descr != null
					&& !legal_real_address_descr.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("legal_real_address_descr",
						legal_real_address_descr);
			}

			String web_address = orgWebAddressItem.getValueAsString();
			if (web_address != null && !web_address.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("web_address", web_address);
			}

			String email_address = orgEmailItem.getValueAsString();
			if (email_address != null
					&& !email_address.trim().equalsIgnoreCase("")) {
				criteria.setAttribute("email_address", email_address);
			}

			String town_district_id = regionItem.getValueAsString();
			if (town_district_id != null && !town_district_id.trim().equals("")) {
				criteria.setAttribute("town_district_id", town_district_id);
			}

			Date org_found_date_start = orgFoundedStartItem.getValueAsDate();
			if (org_found_date_start != null) {
				criteria.setAttribute("org_found_date_start",
						org_found_date_start);
			}

			Date org_found_date_end = orgFoundedEndItem.getValueAsDate();
			if (org_found_date_end != null) {
				criteria.setAttribute("org_found_date_end", org_found_date_end);
			}

			if ((org_name == null || org_name.trim().equals(""))
					&& (phoneNumber == null || phoneNumber.trim().equals(""))
					&& (remark == null || remark.trim().equals(""))
					&& (chief == null || chief.trim().equals(""))
					&& (work_hours == null || work_hours.trim().equals(""))
					&& (ident_code == null || ident_code.trim().equals(""))
					&& (legal_real_address_descr == null || legal_real_address_descr
							.trim().equals(""))
					&& (web_address == null || web_address.trim().equals(""))
					&& (email_address == null || email_address.trim()
							.equals(""))
					&& (real_address_descr == null || real_address_descr.trim()
							.equals(""))) {
				SC.say(CallCenterBK.constants.warning(),
						CallCenterBK.constants.findOrgEnterAnyParam());

				return;
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId",
					"customOrgSearchForCallCenterNew");
			listGrid.invalidateCache();
			listGrid.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
