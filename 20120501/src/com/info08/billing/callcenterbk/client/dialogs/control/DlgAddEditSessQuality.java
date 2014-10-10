package com.info08.billing.callcenterbk.client.dialogs.control;

import java.util.LinkedHashMap;

import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditSessQuality extends Window {

	private VLayout hLayout;
	private SelectItem qualityItem;
	private DynamicForm form;
	
	public DlgAddEditSessQuality(final String sessionId, Integer quality, final Integer call_session_id,
			final DataSource sessionDS) {

		try {
			setWidth(330);
			setHeight(128);
			setTitle("ზარის ხარისხის მართვა");
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

			form = new DynamicForm();
			form.setPadding(10);
			form.setAutoFocus(true);
			form.setWidth100();
			form.setHeight(40);
			form.setTitleWidth(150);
			form.setNumCols(2);
			form.setDataSource(sessionDS);

			qualityItem = new SelectItem();
			qualityItem.setTitle("ზარის ხარისხიანობა");
			qualityItem.setType("comboBox");
			qualityItem.setName("session_quality");

			LinkedHashMap<String, String> mapSessQualities = new LinkedHashMap<String, String>();
			mapSessQualities.put("4", "ჩვეულებრივი");
			mapSessQualities.put("2", "კარგი");
			mapSessQualities.put("1", "ძალიან კარგი");
			mapSessQualities.put("3", "ცუდი");
			mapSessQualities.put("5", "მენეჯერის დახმარება");
			mapSessQualities.put("-1", "ძალიან ცუდი");
			mapSessQualities.put("6", "არასრული");

			qualityItem.setValueMap(mapSessQualities);
			qualityItem.setDefaultToFirstOption(true);
			if (quality != 0) {
				qualityItem.setValue(quality);
			}

			HiddenItem loggedUserName = new HiddenItem();
			loggedUserName.setValue(CommonSingleton.getInstance()
					.getSessionPerson().getUser_name());
			loggedUserName.setName("loggedUserName");
			loggedUserName.setVisible(false);

			HiddenItem sessionIdItem = new HiddenItem();
			sessionIdItem.setValue(sessionId);
			sessionIdItem.setName("sessionId");
			sessionIdItem.setVisible(false);

			form.setFields(qualityItem, loggedUserName, sessionIdItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);
			hLayoutItem.setMargin(12);

			IButton saveItem = new IButton();
			saveItem.setTitle("შენახვა");
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle("დახურვა");
			cancItem.setWidth(100);

			hLayoutItem.setMembers(saveItem, cancItem);
			hLayout.setMembers(form, hLayoutItem);
			addItem(hLayout);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					destroy();
				}
			});

			saveItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					try {
						com.smartgwt.client.rpc.RPCManager.startQueue();
						Record record = new Record();
						record.setAttribute("call_quality", new Integer(qualityItem.getValueAsString()));
						record.setAttribute("sessionId", sessionId);
						record.setAttribute("call_session_id", call_session_id);

						DSRequest req = new DSRequest();
						req.setAttribute("operationId", "update");
						sessionDS.updateData(record, new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								destroy();
							}
						}, req);
						com.smartgwt.client.rpc.RPCManager.sendQueue();
					} catch (Exception e) {
						SC.say(e.toString());
					}
				}
			});
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
