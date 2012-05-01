package com.info08.billing.callcenterbk.client.common.components;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.BrowserEvent;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgComboBoxItemChooser extends Window {

	private VLayout hLayout;

	private ListGrid listGrid;
	private MyComboBoxItem comboBoxItem;
	private String myIdField;
	private String myFieldName;

	public DlgComboBoxItemChooser(MyComboBoxItem comboBoxItem,
			DataSource dataSource, Criteria criteria, String operationId,
			String myFieldName, String myFieldTitle, Integer height,
			Integer width, String myIdField, String myChooserTitle) {
		this.myIdField = myIdField;
		this.myFieldName = myFieldName;
		this.comboBoxItem = comboBoxItem;
		setTitle(CallCenter.constants.shoose() + " : " + myChooserTitle + " !");

		setHeight(height);
		setWidth(width);
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

		listGrid = new ListGrid();
		listGrid.setAlternateRecordStyles(true);
		listGrid.setHeight100();
		listGrid.setWidth100();
		listGrid.setDataSource(dataSource);
		listGrid.setFetchOperation(operationId);
		listGrid.setAutoFetchData(true);
		if (criteria != null) {
			listGrid.setCriteria(criteria);
		}
		listGrid.setSelectionType(SelectionStyle.SINGLE);
		listGrid.setShowFilterEditor(true);
		listGrid.setFilterOnKeypress(true);

		ListGridField idField = new ListGridField(myIdField, "id");
		idField.setHidden(true);

		ListGridField valueField = new ListGridField(myFieldName, myFieldTitle);
		valueField.setAlign(Alignment.LEFT);

		listGrid.setFields(idField, valueField);

		hLayout.addMember(listGrid);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton saveItem = new IButton();
		saveItem.setTitle(CallCenter.constants.save());
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle(CallCenter.constants.close());
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
				save(event);
			}
		});

		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				save(event);
			}
		});

		addItem(hLayout);
	}

	@SuppressWarnings("rawtypes")
	public void save(BrowserEvent browserEvent) {
		try {
			ListGridRecord listGridRecord = listGrid.getSelectedRecord();
			if (listGridRecord == null) {
				SC.say(CallCenter.constants.pleaseSelrecord());
				return;
			}
			Integer myId = listGridRecord.getAttributeAsInt(myIdField);
			String myValue = listGridRecord.getAttributeAsString(myFieldName);
			comboBoxItem.setMyId(myId);
			comboBoxItem.setMyValue(myValue);

			comboBoxItem.getHandlerManagerMy().fireEvent(
					new MyComboBoxEvent(myId, myValue));
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
