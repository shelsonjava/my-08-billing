package com.info08.billing.callcenter.client.dialogs.callcenter;

import java.util.LinkedHashMap;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.shared.common.ServerSession;
import com.info08.billing.callcenter.shared.entity.callcenter.MyMobbase;
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
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddMyMobBase extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private TextItem nmItem;
	private RadioGroupItem sexItem;

	public DlgAddMyMobBase() {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			final MyMobbase myMobbase = serverSession.getMyMobbase();

			setTitle(myMobbase == null ? CallCenter.constants.addAbonentName()
					: CallCenter.constants.editAbonentName());

			setHeight(160);
			setWidth(430);
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

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(150);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			nmItem = new TextItem();
			nmItem.setTitle(CallCenter.constants.abonentName());
			nmItem.setName("phoneItem");
			nmItem.setWidth(250);

			sexItem = new RadioGroupItem();
			sexItem.setTitle(CallCenter.constants.sex());
			LinkedHashMap<String, String> sexMap = new LinkedHashMap<String, String>();
			sexMap.put("1", CallCenter.constants.male());
			sexMap.put("0", CallCenter.constants.female());
			sexItem.setValueMap(sexMap);

			if (myMobbase != null) {
				nmItem.setValue(myMobbase.getNm());
				sexItem.setValue(myMobbase.getSex().toString());
			}

			dynamicForm.setFields(sexItem, nmItem);

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
					save(myMobbase);
				}
			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void save(MyMobbase myMobbase) {
		try {
			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			String phone = serverSession.getPhone();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			if (!serverSession.isPhoneIsMobile()) {
				SC.say(CallCenter.constants.phoneIsNotMobile());
				return;
			}
			String sex = sexItem.getValueAsString();
			if (sex == null || sex.trim().equalsIgnoreCase("")) {
				SC.say(CallCenter.constants.plzSelectSex());
				return;
			}
			String nm = nmItem.getValueAsString();
			if (nm == null || nm.trim().equalsIgnoreCase("")) {
				SC.say(CallCenter.constants.plzEnterAbonentName());
				return;
			}

			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			if (myMobbase != null) {
				record.setAttribute("id", myMobbase.getId());
			}
			record.setAttribute("phone", phone);
			record.setAttribute("sex", new Integer(sex));
			record.setAttribute("nm", nm);
			record.setAttribute("rec_user", serverSession.getUserName());
			record.setAttribute("upd_user", serverSession.getUserName());

			DSRequest req = new DSRequest();
			DataSource myMobBaseDS = DataSource.get("MyMobBaseDS");

			if (myMobbase == null) {
				req.setAttribute("operationId", "addMyMobBaseRecord");
				myMobBaseDS.addData(record, new DSCallback() {
					@Override
					public void execute(DSResponse responsRe, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			} else {
				req.setAttribute("operationId", "updateMyMobBaseRecord");
				myMobBaseDS.updateData(record, new DSCallback() {
					@Override
					public void execute(DSResponse responsRe, Object rawData,
							DSRequest request) {
						destroy();
					}
				}, req);
			}

			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
