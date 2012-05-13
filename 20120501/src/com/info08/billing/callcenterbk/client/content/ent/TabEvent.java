package com.info08.billing.callcenterbk.client.content.ent;

import java.util.Date;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.info08.billing.callcenterbk.client.dialogs.ent.DlgAddEditEvent;
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
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class TabEvent extends Tab {

	private DynamicForm searchForm;
	private VLayout mainLayout;

	// form fields
	private ComboBoxItem entTypeItem;
	private ComboBoxItem entPlacesItem;
	private TextItem entPosterGeoItem;
	private TextItem commentGeoItem;
	private TextItem posterPriceGeoItem;
	private DateItem posterDateStartItem;
	private DateItem posterDateEndItem;
	// private ComboBoxItem deletedItem;

	// actions
	private IButton findButton;
	private IButton clearButton;
	private ToolStripButton addBtn;
	private ToolStripButton editBtn;
	private ToolStripButton disableBtn;
	private ToolStripButton copyBtn;

	// ListGrid
	private ListGrid listGrid;

	// DataSource
	private DataSource datasource;

	public TabEvent() {
		try {
			setTitle("აფიშა-ღონისძიებების მართვა");
			setCanClose(true);

			datasource = DataSource.get("EventDS");

			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);

			searchForm = new DynamicForm();
			searchForm.setAutoFocus(true);
			searchForm.setWidth(930);
			searchForm.setTitleWidth(300);
			searchForm.setNumCols(4);
			mainLayout.addMember(searchForm);

			entTypeItem = new ComboBoxItem();
			entTypeItem.setTitle("აფიშა-კატეგორია");
			entTypeItem.setWidth(300);
			entTypeItem.setName("event_category_name");
			entTypeItem.setFetchMissingValues(true);
			entTypeItem.setFilterLocally(false);
			entTypeItem.setAddUnknownValues(false);

			DataSource EventCategoryDS = DataSource.get("EventCategoryDS");
			entTypeItem.setOptionOperationId("searchAllEventCategoryForCB");
			entTypeItem.setOptionDataSource(EventCategoryDS);
			entTypeItem.setValueField("event_category_id");
			entTypeItem.setDisplayField("event_category_name");

			entTypeItem.setOptionCriteria(new Criteria());
			entTypeItem.setAutoFetchData(false);

			entTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = entTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("event_category_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("event_category_id", nullO);
						}
					}
				}
			});

			entTypeItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					String event_category_str = entTypeItem.getValueAsString();

					if (event_category_str != null
							&& !event_category_str.trim().equals("")) {
						try {
							Integer event_category_id = Integer
									.parseInt(event_category_str);

							Criteria criteria = entPlacesItem
									.getOptionCriteria();
							if (criteria != null) {
								criteria.setAttribute("event_category_id",
										event_category_id);
								entPlacesItem.setOptionCriteria(criteria);
							}

						} catch (Exception e) {
							return;
						}
					}
				}
			});

			entPlacesItem = new ComboBoxItem();
			entPlacesItem.setTitle("აფიშა-რესურსი");
			entPlacesItem.setWidth(300);
			entPlacesItem.setName("event_owner_name");
			entPlacesItem.setFetchMissingValues(true);
			entPlacesItem.setFilterLocally(false);
			entPlacesItem.setAddUnknownValues(false);

			DataSource EventOwnerDS = DataSource.get("EventOwnerDS");
			entPlacesItem.setOptionOperationId("searchAllEventOwnerForCB");
			entPlacesItem.setOptionDataSource(EventOwnerDS);
			entPlacesItem.setValueField("event_owner_id");
			entPlacesItem.setDisplayField("event_owner_name");

			entPlacesItem.setOptionCriteria(new Criteria());
			entPlacesItem.setAutoFetchData(false);

			entPlacesItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = entPlacesItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("event_owner_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("event_owner_id", nullO);
						}
					}
				}
			});

			entPosterGeoItem = new TextItem();
			entPosterGeoItem.setTitle("ღონისძიების დასახელება");
			entPosterGeoItem.setName("event_name");
			entPosterGeoItem.setWidth(300);

			commentGeoItem = new TextItem();
			commentGeoItem.setTitle("კომენტარი");
			commentGeoItem.setName("remark");
			commentGeoItem.setWidth(300);

			posterPriceGeoItem = new TextItem();
			posterPriceGeoItem.setTitle("ფასი");
			posterPriceGeoItem.setName("event_price");
			posterPriceGeoItem.setWidth(300);

			Date startDate = new Date();
			CalendarUtil.addMonthsToDate(startDate, -192);

			posterDateStartItem = new DateItem();
			posterDateStartItem.setTitle("საწყისი თარიღი");
			posterDateStartItem.setWidth(300);
			posterDateStartItem.setValue(startDate);
			posterDateStartItem.setName("poster_date_start");
			posterDateStartItem.setHint("აირჩიეთ");

			posterDateEndItem = new DateItem();
			posterDateEndItem.setTitle("საბოლოო თარიღი");
			posterDateEndItem.setWidth(300);
			posterDateEndItem.setValue(new Date());
			posterDateEndItem.setName("poster_date_end");
			posterDateEndItem.setHint("აირჩიეთ");

			// deletedItem = new ComboBoxItem();
			// deletedItem.setTitle("სტატუსი");
			// deletedItem.setWidth(300);
			// deletedItem.setName("deleted_transport");
			// deletedItem.setValueMap(ClientMapUtil.getInstance().getStatuses());

			searchForm.setFields(entTypeItem, entPlacesItem, entPosterGeoItem,
					commentGeoItem, posterPriceGeoItem, // deletedItem,
					posterDateStartItem, posterDateEndItem);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setWidth(930);
			buttonLayout.setHeight(30);
			buttonLayout.setAlign(Alignment.RIGHT);

			clearButton = new IButton();
			clearButton.setTitle("გასუფთავება");

			findButton = new IButton();
			findButton.setTitle("ძებნა");

			buttonLayout.setMembers(findButton, clearButton);
			mainLayout.addMember(buttonLayout);

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth(930);
			toolStrip.setPadding(5);
			mainLayout.addMember(toolStrip);

			addBtn = new ToolStripButton("დამატება", "addIcon.png");
			addBtn.setLayoutAlign(Alignment.LEFT);
			addBtn.setWidth(50);
			toolStrip.addButton(addBtn);

			editBtn = new ToolStripButton("შეცვლა", "editIcon.png");
			editBtn.setLayoutAlign(Alignment.LEFT);
			editBtn.setWidth(50);
			toolStrip.addButton(editBtn);

			disableBtn = new ToolStripButton("გაუქმება", "deleteIcon.png");
			disableBtn.setLayoutAlign(Alignment.LEFT);
			disableBtn.setWidth(50);
			toolStrip.addButton(disableBtn);

			toolStrip.addSeparator();

			copyBtn = new ToolStripButton("კოპირება", "copy.png");
			copyBtn.setLayoutAlign(Alignment.LEFT);
			copyBtn.setWidth(50);
			toolStrip.addButton(copyBtn);

			listGrid = new ListGrid() {
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

			listGrid.setWidth(930);
			listGrid.setHeight(260);
			listGrid.setAlternateRecordStyles(true);
			listGrid.setDataSource(datasource);
			listGrid.setAutoFetchData(false);
			listGrid.setShowFilterEditor(false);
			listGrid.setCanEdit(false);
			listGrid.setCanRemoveRecords(false);
			listGrid.setFetchOperation("searchAllEventCategory");
			listGrid.setShowRowNumbers(true);
			listGrid.setCanHover(true);
			listGrid.setShowHover(true);
			listGrid.setShowHoverComponents(true);

			datasource.getField("event_owner_name").setTitle("რესურსი");
			datasource.getField("event_name").setTitle("ღონისძიება");
			datasource.getField("remark").setTitle("კომენტარი");
			datasource.getField("event_price").setTitle("ფასი");
			datasource.getField("event_date").setTitle("თარიღი");
			datasource.getField("event_time").setTitle("დრო");
			datasource.getField("sms_remark").setTitle("SMS კომენტარი");

			ListGridField event_owner_name = new ListGridField(
					"event_owner_name", "რესურსი", 330);
			ListGridField event_name = new ListGridField("event_name",
					"ღონისძიება", 300);
			ListGridField remark = new ListGridField("remark", "კომენტარი", 250);

			listGrid.setFields(event_owner_name, event_name, remark);

			mainLayout.addMember(listGrid);
			findButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
			clearButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					entPlacesItem.clearValue();
					entPosterGeoItem.clearValue();
					commentGeoItem.clearValue();
					posterPriceGeoItem.clearValue();
					entTypeItem.clearValue();
					// deletedItem.clearValue();
				}
			});
			addBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord selEntPlaceRecord = entPlacesItem
							.getSelectedRecord();
					if (selEntPlaceRecord == null) {
						SC.say("გთხოვთ აირჩიოთ აფიშა-რესურსი !");
						return;
					}
					Integer event_owner_id = selEntPlaceRecord
							.getAttributeAsInt("event_owner_id");
					Integer event_category_id = selEntPlaceRecord
							.getAttributeAsInt("event_category_id");

					DlgAddEditEvent dlgAddEditEvent = new DlgAddEditEvent(
							listGrid, null, event_owner_id, event_category_id);
					dlgAddEditEvent.show();
				}
			});

			editBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}
					Integer event_owner_id = listGridRecord
							.getAttributeAsInt("event_owner_id");
					Integer event_category_id = listGridRecord
							.getAttributeAsInt("event_category_id");
					DlgAddEditEvent dlgAddEditEvent = new DlgAddEditEvent(
							listGrid, listGridRecord, event_owner_id,
							event_category_id);
					dlgAddEditEvent.show();
				}
			});
			disableBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					final Integer event_id = listGridRecord
							.getAttributeAsInt("event_id");
					if (event_id == null) {
						SC.say("არასწორი ჩანაწერი, გთხოვთ გააკეთოთ ძებნა ხელმეორედ !");
						return;
					}

					SC.ask("დარწმუნებული ხართ რომ გნებავთ ჩანაწერის გაუქმება ?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										delete(event_id);
									}
								}
							});
				}
			});

			copyBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					Integer event_owner_id = listGridRecord
							.getAttributeAsInt("event_owner_id");
					Integer event_category_id = listGridRecord
							.getAttributeAsInt("event_category_id");
					Object nullObj = null;
					listGridRecord.setAttribute("event_id", nullObj);

					DlgAddEditEvent dlgAddEditEvent = new DlgAddEditEvent(
							listGrid, listGridRecord, event_owner_id,
							event_category_id);
					dlgAddEditEvent.show();
				}
			});

			TabSet tabSet = new TabSet();
			tabSet.setWidth(930);
			Tab tabDetViewer = new Tab("დათვალიერება");
			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setDataSource(datasource);
			detailViewer.setWidth(900);
			tabDetViewer.setPane(detailViewer);

			listGrid.addRecordClickHandler(new RecordClickHandler() {
				public void onRecordClick(RecordClickEvent event) {
					detailViewer.viewSelectedData(listGrid);
				}
			});

			listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
				@Override
				public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში !");
						return;
					}

					Integer event_owner_id = listGridRecord
							.getAttributeAsInt("event_owner_id");
					Integer event_category_id = listGridRecord
							.getAttributeAsInt("event_category_id");

					DlgAddEditEvent dlgAddEditEvent = new DlgAddEditEvent(
							listGrid, listGridRecord, event_owner_id,
							event_category_id);
					dlgAddEditEvent.show();
				}
			});

			tabSet.setTabs(tabDetViewer);
			mainLayout.addMember(tabSet);
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	private void search() {
		try {
			String event_owner_id = entPlacesItem.getValueAsString();
			Criteria criteria = new Criteria();
			if (event_owner_id != null && !event_owner_id.trim().equals("")) {
				criteria.setAttribute("event_owner_id", new Integer(
						event_owner_id));
			}
			String event_name = entPosterGeoItem.getValueAsString();
			if (event_name != null && !event_name.trim().equals("")) {
				criteria.setAttribute("event_name", event_name);
			}
			String remark = commentGeoItem.getValueAsString();
			if (remark != null && !remark.trim().equals("")) {
				criteria.setAttribute("remark", remark);
			}
			String event_price = posterPriceGeoItem.getValueAsString();
			if (event_price != null && !event_price.trim().equals("")) {
				criteria.setAttribute("event_price", event_price);
			}
			Date poster_date_start = posterDateStartItem.getValueAsDate();
			if (poster_date_start != null) {
				criteria.setAttribute("poster_date_start", poster_date_start);
			}
			Date poster_date_end = posterDateEndItem.getValueAsDate();
			if (poster_date_end != null) {
				criteria.setAttribute("poster_date_end", poster_date_end);
			}
			String event_category_id = entTypeItem.getValueAsString();
			if (event_category_id != null) {
				criteria.setAttribute("event_category_id", new Integer(
						event_category_id));
			}

			DSRequest dsRequest = new DSRequest();
			dsRequest.setAttribute("operationId", "searchAllEvent");
			listGrid.invalidateCache();
			listGrid.filterData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, dsRequest);
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void delete(Integer ent_poster_id) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("event_id", ent_poster_id);
			record.setAttribute("loggedUserName", CommonSingleton.getInstance()
					.getSessionPerson().getUserName());
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "removeEvent");
			listGrid.removeData(record, new DSCallback() {
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

	// private void changeStatus(Integer ent_poster_id, Integer deleted) {
	// try {
	// com.smartgwt.client.rpc.RPCManager.startQueue();
	// Record record = new Record();
	//
	// record.setAttribute("event_id", ent_poster_id);
	// record.setAttribute("loggedUserName", CommonSingleton.getInstance()
	// .getSessionPerson().getUserName());
	// DSRequest req = new DSRequest();
	//
	// req.setAttribute("operationId", "updateEntPosterStatus");
	// listGrid.updateData(record, new DSCallback() {
	// @Override
	// public void execute(DSResponse response, Object rawData,
	// DSRequest request) {
	// }
	// }, req);
	// com.smartgwt.client.rpc.RPCManager.sendQueue();
	// } catch (Exception e) {
	// SC.say(e.toString());
	// }
	// }
}
