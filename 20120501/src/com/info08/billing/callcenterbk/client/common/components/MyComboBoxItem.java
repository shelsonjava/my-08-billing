package com.info08.billing.callcenterbk.client.common.components;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MyComboBoxItem extends HLayout {

	final private HandlerManager handlerManager = new HandlerManager(this);

	private Record selectedRecord;
	private String myIdField;
	private ArrayList<MyComboBoxRecord> myFields;
	private DataSource myDataSource;
	private String myDataSourceOperation;
	private String myFieldName;
	private Integer myFieldTitleWidth;
	private Integer myFieldWidth;
	private Criteria myCriteria;
	private Integer myDlgHeight;
	private Integer myDlgWidth;
	private String myChooserTitle;

	private String nameField;
	private Object currentValue;

	private DynamicForm dynamicForm;

	private TextItem displayFormItem;
	private IButton buttonItem;

	public MyComboBoxItem(String myFieldName, String myFieldTitle,
			Integer myFieldTitleWidth, Integer myFieldWidth) {

		this.myFieldName = myFieldName;
		this.myFieldTitleWidth = myFieldTitleWidth;
		this.myFieldWidth = myFieldWidth;

		setPadding(0);
		setMargin(0);
		setWidth100();

		dynamicForm = new DynamicForm();
		dynamicForm.setAutoFocus(false);
		dynamicForm.setWidth100();
		dynamicForm.setTitleWidth(myFieldTitleWidth);
		dynamicForm.setNumCols(2);
		dynamicForm.setTitleOrientation(TitleOrientation.TOP);
		addMember(dynamicForm);

		displayFormItem = new TextItem();
		displayFormItem.setWidth(myFieldWidth - 25);

		displayFormItem.setName("formItem" + myFieldName);
		displayFormItem.setTitle(myFieldTitle);
		displayFormItem.setCanEdit(false);

		dynamicForm.setFields(displayFormItem);

		buttonItem = new IButton();
		buttonItem.setTitle(" ... ");
		buttonItem.setWidth(20);
		buttonItem.setHeight(23);

		VLayout hLayout = new VLayout();
		hLayout.addMember(buttonItem);
		hLayout.setHeight(40);
		hLayout.setLayoutMargin(2);
		hLayout.setAlign(VerticalAlignment.BOTTOM);
		addMember(hLayout);

		buttonItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showChooserDlg(event);
			}
		});
	}

	public void setDataValue(Record record) {
		setDataValue(record.toMap());
	}

	public void setDataValue(Map<?, ?> map) {
		if (nameField == null)
			return;
		if (map == null)
			return;
		currentValue = map.get(nameField);
		refreshView();
	}

	private void refreshView() {
		if (myDataSource == null)
			return;
		if (currentValue == null) {
			displayFormItem.clearValue();
			return;
		}

		Criteria cr = new Criteria();
		cr.setAttribute(myIdField, currentValue);
		DSRequest req = new DSRequest();
		if (myDataSourceOperation != null) {
			req.setOperationId(myDataSourceOperation);
		}
		myDataSource.fetchData(cr, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				Record[] records = response.getData();
				if (records == null || records.length == 0)
					return;
				setSelectedRecord(records[0]);

			}
		}, req);

	}

	private void showChooserDlg(ClickEvent event) {
		try {
			DlgComboBoxItemChooser boxItemChooser = new DlgComboBoxItemChooser(
					this, myDataSource, myCriteria, myDataSourceOperation,
					myFields, myDlgHeight, myDlgWidth, myIdField,
					myChooserTitle);
			boxItemChooser.show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public void addDataChangedHandler(MyComboBoxItemDataChangedHandler handler) {
		handlerManager.addHandler(MyComboBoxEvent.getType(), handler);
	}

	public String getMyIdField() {
		return myIdField;
	}

	public void setMyIdField(String myIdField) {
		this.myIdField = myIdField;
	}

	public ArrayList<MyComboBoxRecord> getMyFields() {
		return myFields;
	}

	public void setMyFields(ArrayList<MyComboBoxRecord> myFields) {
		this.myFields = myFields;
	}

	public DataSource getMyDataSource() {
		return myDataSource;
	}

	public void setMyDataSource(DataSource myDataSource) {
		this.myDataSource = myDataSource;
	}

	public String getMyDataSourceOperation() {
		return myDataSourceOperation;
	}

	public void setMyDataSourceOperation(String myDataSourceOperation) {
		this.myDataSourceOperation = myDataSourceOperation;
	}

	public String getMyFieldName() {
		return myFieldName;
	}

	public void setMyFieldName(String myFieldName) {
		this.myFieldName = myFieldName;
	}

	public DynamicForm getDynamicForm() {
		return dynamicForm;
	}

	public void setDynamicForm(DynamicForm dynamicForm) {
		this.dynamicForm = dynamicForm;
	}

	public TextItem getFormItem() {
		return displayFormItem;
	}

	public void setFormItem(TextItem formItem) {
		this.displayFormItem = formItem;
	}

	public Integer getMyFieldTitleWidth() {
		return myFieldTitleWidth;
	}

	public void setMyFieldTitleWidth(Integer myFieldTitleWidth) {
		this.myFieldTitleWidth = myFieldTitleWidth;
	}

	public Integer getMyFieldWidth() {
		return myFieldWidth;
	}

	public void setMyFieldWidth(Integer myFieldWidth) {
		this.myFieldWidth = myFieldWidth;
	}

	public Criteria getMyCriteria() {
		return myCriteria;
	}

	public void setMyCriteria(Criteria myCriteria) {
		this.myCriteria = myCriteria;
	}

	public Integer getMyDlgHeight() {
		return myDlgHeight;
	}

	public void setMyDlgHeight(Integer myDlgHeight) {
		this.myDlgHeight = myDlgHeight;
	}

	public Integer getMyDlgWidth() {
		return myDlgWidth;
	}

	public void setMyDlgWidth(Integer myDlgWidth) {
		this.myDlgWidth = myDlgWidth;
	}

	public Record getSelectedRecord() {
		return selectedRecord;
	}

	public void setSelectedRecord(Record selectedRecord) {
		if (selectedRecord == null) {
			displayFormItem.clearValue();
			this.selectedRecord = selectedRecord;
			return;
		}
		String diplayValue = "";
		int index = 0;
		for (MyComboBoxRecord recordItem : myFields) {
			if (!recordItem.isDisplayField()) {
				continue;
			}
			String value = selectedRecord.getAttributeAsString(recordItem
					.getFieldName());
			diplayValue += (index == 0 ? value : " -- " + value);
			index++;
		}
		this.selectedRecord = selectedRecord;
		displayFormItem.setValue(diplayValue);
		currentValue = selectedRecord.getAttribute(myIdField);
	}

	public String getMyChooserTitle() {
		return myChooserTitle;
	}

	public void setMyChooserTitle(String myChooserTitle) {
		this.myChooserTitle = myChooserTitle;
	}

	public HandlerManager getHandlerManagerMy() {
		return handlerManager;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public Object getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Object currentValue) {
		this.currentValue = currentValue;
	}
}
