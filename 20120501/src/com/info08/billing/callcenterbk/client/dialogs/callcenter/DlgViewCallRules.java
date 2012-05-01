package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgViewCallRules extends Window {

	private VLayout mainLayout;

	public DlgViewCallRules() {
		setTitle(CallCenter.constants.info());

		setHeight(160);
		setWidth(500);
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		mainLayout = new VLayout(5);
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.setPadding(10);
		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setAutoFocus(false);
		dynamicForm.setWidth100();
		dynamicForm.setNumCols(2);
		dynamicForm.setTitleOrientation(TitleOrientation.TOP);
		mainLayout.addMember(dynamicForm);

		TextAreaItem infoItem = new TextAreaItem();
		infoItem.setTitle(CallCenter.constants.callInfo());
		infoItem.setWidth("100%");
		infoItem.setHeight(65);
		infoItem.setName("infoItem");
		infoItem.setColSpan(3);
		infoItem.setCanEdit(false);
		infoItem.setTextBoxStyle("fontRedAndBoldNoBorder1");
		infoItem.setCanFocus(false);
		infoItem.setValue(CallCenter.constants.codesTextConstant());
		dynamicForm.setFields(infoItem);

		addItem(mainLayout);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton cancItem = new IButton();
		cancItem.setTitle(CallCenter.constants.close());
		cancItem.setWidth(100);

		hLayoutItem.setMembers(cancItem);

		mainLayout.addMember(hLayoutItem);

		cancItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});
	}
}
