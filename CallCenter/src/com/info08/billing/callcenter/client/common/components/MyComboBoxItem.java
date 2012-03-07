package com.info08.billing.callcenter.client.common.components;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class MyComboBoxItem extends HLayout {

	final private HandlerManager handlerManager = new HandlerManager(this);

	private String myIdField;
	private String myDisplayField;
	private Integer myId;
	private String myValue;
	private DataSource myDataSource;
	private String myFieldTitle;
	private String myDataSourceOperation;
	private String myFieldName;
	private Integer myFieldTitleWidth;
	private Integer myFieldWidth;
	private Criteria myCriteria;
	private Integer myDlgHeight;
	private Integer myDlgWidth;
	private String myChooserTitle;

	private DynamicForm dynamicForm;

	private TextItem formItem;
	private IButton buttonItem;

	public MyComboBoxItem(String myFieldName, String myFieldTitle,
			Integer myFieldTitleWidth, Integer myFieldWidth) {

		this.myFieldName = myFieldName;
		this.myFieldTitleWidth = myFieldTitleWidth;
		this.myFieldTitle = myFieldTitle;
		this.myFieldWidth = myFieldWidth;

		setPadding(0);
		setMargin(0);
		setWidth100();

		dynamicForm = new DynamicForm();
		dynamicForm.setAutoFocus(true);
		dynamicForm.setWidth100();
		dynamicForm.setTitleWidth(myFieldTitleWidth);
		dynamicForm.setNumCols(2);

		addMember(dynamicForm);

		formItem = new TextItem();
		formItem.setWidth(myFieldWidth);
		formItem.setName("formItem" + myFieldName);
		formItem.setTitle(myFieldTitle);
		formItem.setCanEdit(false);

		dynamicForm.setFields(formItem);

		buttonItem = new IButton();
		buttonItem.setTitle(" ... ");
		buttonItem.setWidth(20);

		addMember(buttonItem);

		buttonItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showChooserDlg(event);
			}
		});
	}

	private void showChooserDlg(ClickEvent event) {
		try {
			DlgComboBoxItemChooser boxItemChooser = new DlgComboBoxItemChooser(
					this, myDataSource, myCriteria, myDataSourceOperation,
					myDisplayField, myFieldTitle, myDlgHeight, myDlgWidth,
					myIdField, myChooserTitle);
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

	public String getMyDisplayField() {
		return myDisplayField;
	}

	public void setMyDisplayField(String myDisplayField) {
		this.myDisplayField = myDisplayField;
	}

	public DataSource getMyDataSource() {
		return myDataSource;
	}

	public void setMyDataSource(DataSource myDataSource) {
		this.myDataSource = myDataSource;
	}

	public String getMyFieldTitle() {
		return myFieldTitle;
	}

	public void setMyFieldTitle(String myFieldTitle) {
		this.myFieldTitle = myFieldTitle;
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
		return formItem;
	}

	public void setFormItem(TextItem formItem) {
		this.formItem = formItem;
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

	public Integer getMyId() {
		return myId;
	}

	public void setMyId(Integer myId) {
		this.myId = myId;
	}

	public String getMyValue() {
		return myValue;
	}

	public void setMyValue(String myValue) {
		this.myValue = myValue;
		formItem.setValue(myValue);
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
}
