package com.info08.billing.callcenterbk.client.common.components;

import com.google.gwt.event.shared.GwtEvent;

public class MyComboBoxEvent extends GwtEvent<MyComboBoxItemDataChangedHandler> {

	private Integer selectedId;
	private String selectedValue;

	public MyComboBoxEvent(Integer selectedId, String selectedValue) {
		super();
		this.selectedId = selectedId;
		this.selectedValue = selectedValue;
	}

	public static final Type<MyComboBoxItemDataChangedHandler> TYPE = new Type<MyComboBoxItemDataChangedHandler>();

	@Override
	public Type<MyComboBoxItemDataChangedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MyComboBoxItemDataChangedHandler handler) {
		handler.onDataChanged(this);
	}

	public Integer getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(Integer selectedId) {
		this.selectedId = selectedId;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public static Type<MyComboBoxItemDataChangedHandler> getType() {
		return TYPE;
	}
}
