package com.info08.billing.callcenter.client.content.info;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.common.components.ChargePanel;
import com.info08.billing.callcenter.client.dialogs.callcenter.DlgViewOpRemarks;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.shared.common.ServerSession;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TabInfoPortal extends Tab {

	private static VLayout mainLayout;

	public TabInfoPortal() {
		try {
			setTitle("საინფორმაციო პორტალი");
			setCanClose(false);
			mainLayout = new VLayout(5);
			mainLayout.setWidth100();
			mainLayout.setHeight100();
			mainLayout.setMargin(5);
			setPane(mainLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.getMessage().toString());
		}
	}

	public static void draw() {
		try {
			ChargePanel chargePanel = new ChargePanel(800, false, true, -1, -1);
			mainLayout.addMember(chargePanel);

			DynamicForm searchFormComm = new DynamicForm();
			searchFormComm = new DynamicForm();
			searchFormComm.setAutoFocus(false);
			searchFormComm.setWidth(770);
			searchFormComm.setNumCols(2);
			searchFormComm.setTitleOrientation(TitleOrientation.TOP);
			mainLayout.addMember(searchFormComm);

			SpacerItem spacerItem1 = new SpacerItem();
			spacerItem1.setWidth(770);
			spacerItem1.setName("spacerItem1");
			spacerItem1.setColSpan(2);
			spacerItem1.setHeight(5);

			final TextAreaItem commentItem = new TextAreaItem();
			commentItem.setTitle(CallCenter.constants.mainNews());
			commentItem.setCanFocus(false);
			commentItem.setName("commentItem");
			commentItem.setWidth(770);
			commentItem.setCanEdit(false);
			commentItem.setTextBoxStyle("fontRedAndBoldNoBorder");
			commentItem.setHeight(50);

			searchFormComm.setFields(spacerItem1, commentItem);

			DataSource dataSource = DataSource.get("MainNewsDS");
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("searchMainWarningNews");
			dataSource.fetchData(new Criteria(), new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						String mn = records[0].getAttributeAsString("mn");
						if (mn != null && !mn.trim().equals("")) {
							commentItem.setValue(mn);
						}
					}
				}
			}, dsRequest);

			final ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();

			if (serverSession != null && !serverSession.isWebSession()) {

				final Long persNotesCount = serverSession
						.getUnreadPersNotesCount();

				ToolStrip toolStrip = new ToolStrip();
				toolStrip.setWidth(770);
				toolStrip.setPadding(5);

				ToolStripButton remarks = null;
				if (persNotesCount == null || persNotesCount.longValue() <= 0) {
					remarks = new ToolStripButton(
							CallCenter.constants.remarks(), "information.png");
				} else {
					remarks = new ToolStripButton(
							CallCenter.constants.remarks(), "remarks.png");
				}
				final ToolStripButton remarksBtn = remarks;
				remarksBtn.setLayoutAlign(Alignment.LEFT);
				remarksBtn.setWidth(50);
				toolStrip.addButton(remarksBtn);

				mainLayout.addMember(toolStrip);

				remarks.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						try {
							Long myPersNotesCount = serverSession
									.getUnreadPersNotesCount();
							if (myPersNotesCount == null
									|| myPersNotesCount.longValue() <= 0) {
								SC.say(CallCenter.constants.persNotesIsEmpty());
								return;
							} else {
								DlgViewOpRemarks dlgViewOpRemarks = new DlgViewOpRemarks(
										remarksBtn);
								dlgViewOpRemarks.show();
							}
						} catch (Exception e) {
							e.printStackTrace();
							SC.say(e.toString());
						}
					}
				});
			}

			String callCenterReqMsg = serverSession.getCallCenterReqMsg();
			if (callCenterReqMsg != null && !callCenterReqMsg.trim().equals("")) {
				SC.warn(callCenterReqMsg);
			}

			HLayout hLayout = new HLayout();
			hLayout.setHeight(400);
			hLayout.setWidth100();
			hLayout.setStyleName("headerClass");
			mainLayout.addMember(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
