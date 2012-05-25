package com.info08.billing.callcenterbk.client.common.components;

import com.google.gwt.event.shared.GwtEvent;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MyComboBoxEvent extends GwtEvent<MyComboBoxItemDataChangedHandler> {

	private ListGridRecord listGridRecord;

	public MyComboBoxEvent(ListGridRecord listGridRecord) {
		super();
		this.listGridRecord = listGridRecord;
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

	public ListGridRecord getListGridRecord() {
		return listGridRecord;
	}

	public void setListGridRecord(ListGridRecord listGridRecord) {
		this.listGridRecord = listGridRecord;
	}

	public static Type<MyComboBoxItemDataChangedHandler> getType() {
		return TYPE;
	}
}
