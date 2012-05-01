package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import com.info08.billing.callcenterbk.client.CallCenter;
import com.info08.billing.callcenterbk.client.common.components.ChargePanel;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.data.Criteria;
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
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgSendDiscovery extends Window {

	private VLayout hLayout;
	private DynamicForm dynamicForm;

	private ComboBoxItem discoverTypeItem;
	private TextItem phoneItem;
	private TextItem contactPersonItem;
	private TextItem contactPhoneItem;
	private TextAreaItem messageItem;
	private ChargePanel chargePanel;
	private String sessionId;
	private String userName;

	public DlgSendDiscovery(final ChargePanel chargePanel,
			Integer discoveryTypeId, String discovery_txt) {
		this.chargePanel = chargePanel;
		try {
			setTitle(CallCenter.constants.sendDiscovery());

			setHeight(290);
			setWidth(430);
			setShowMinimizeButton(false);
			setIsModal(false);
			setShowModalMask(true);
			setCanDrag(false);
			setCanDragReposition(false);
			setCanDragResize(false);
			setCanDragScroll(false);
			setShowCloseButton(true);

			centerInPage();

			hLayout = new VLayout(5);
			hLayout.setWidth100();
			hLayout.setHeight100();
			hLayout.setPadding(10);

			dynamicForm = new DynamicForm();
			dynamicForm.setAutoFocus(true);
			dynamicForm.setWidth100();
			dynamicForm.setTitleWidth(100);
			dynamicForm.setNumCols(2);
			hLayout.addMember(dynamicForm);

			discoverTypeItem = new ComboBoxItem();
			discoverTypeItem.setTitle(CallCenter.constants.discoveryType());
			discoverTypeItem.setName("discover_type");
			discoverTypeItem.setWidth(300);
			discoverTypeItem.setFetchMissingValues(true);
			discoverTypeItem.setFilterLocally(false);
			discoverTypeItem.setAddUnknownValues(false);

			DataSource discoveryTypeDS = DataSource.get("DiscoveryTypeDS");
			discoverTypeItem.setOptionOperationId("searchDiscoverTypesForCB");
			discoverTypeItem.setOptionDataSource(discoveryTypeDS);
			discoverTypeItem.setValueField("discover_type_id");
			discoverTypeItem.setDisplayField("discover_type");
			discoverTypeItem.setOptionCriteria(new Criteria());
			discoverTypeItem.setAutoFetchData(false);

			discoverTypeItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					Criteria criteria = discoverTypeItem.getOptionCriteria();
					if (criteria != null) {
						String oldAttr = criteria
								.getAttribute("discover_type_id");
						if (oldAttr != null) {
							Object nullO = null;
							criteria.setAttribute("discover_type_id", nullO);
						}
					}
				}
			});

			if (discoveryTypeId != null) {
				discoverTypeItem.setValue(discoveryTypeId);
			}
			// else {
			// discoverTypeItem.setValue(Constants.defDiscTypeId);
			// }

			phoneItem = new TextItem();
			phoneItem.setTitle(CallCenter.constants.phone());
			phoneItem.setName("phoneItem");
			phoneItem.setWidth(300);
			phoneItem.setKeyPressFilter("[0-9]");

			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession != null && !serverSession.isWebSession()) {
				phoneItem.setValue(serverSession.getPhone());
				sessionId = serverSession.getSessionId();
				userName = serverSession.getUserName();
			}

			contactPersonItem = new TextItem();
			contactPersonItem.setTitle(CallCenter.constants.contactPerson());
			contactPersonItem.setName("contactPersonItem");
			contactPersonItem.setWidth(300);

			contactPhoneItem = new TextItem();
			contactPhoneItem.setTitle(CallCenter.constants.contactPhone());
			contactPhoneItem.setName("contactPhoneItem");
			contactPhoneItem.setWidth(300);
			contactPhoneItem.setKeyPressFilter("[0-9]");

			messageItem = new TextAreaItem();
			messageItem.setTitle(CallCenter.constants.message());
			messageItem.setName("messageItem");
			messageItem.setWidth(300);

			if (discovery_txt != null && !discovery_txt.trim().equals("")) {
				messageItem.setValue(discovery_txt);
			}

			dynamicForm.setFields(phoneItem, discoverTypeItem,
					contactPersonItem, contactPhoneItem, messageItem);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton saveItem = new IButton();
			saveItem.setTitle(CallCenter.constants.send());
			saveItem.setWidth(100);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenter.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(saveItem, cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					chargePanel.setDlgSendDiscovery(null);
					destroy();
				}
			});
			saveItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					save();
				}
			});

			addItem(hLayout);
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}

	}

	public DlgSendDiscovery(ChargePanel chargePanel) {
		this(chargePanel, null, null);
	}

	private void save() {
		try {
			if (sessionId == null || userName == null) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			if (sessionId.trim().equals("") || userName.trim().equals("")) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}

			String phone = phoneItem.getValueAsString();
			if (phone == null || phone.trim().equalsIgnoreCase("")) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			try {
				new Integer(phone);
			} catch (Exception e) {
				SC.say(CallCenter.constants.phoneMustBeNumeric());
				return;
			}

			String discover_txt = messageItem.getValueAsString();
			if (discover_txt == null) {
				discover_txt = "";
			}
			discover_txt = discover_txt.trim();
			String contact_phone = contactPhoneItem.getValueAsString();
			if (contact_phone == null) {
				contact_phone = "";
			}
			contact_phone = contact_phone.trim();

			String contact_person = contactPersonItem.getValueAsString();
			if (contact_person == null) {
				contact_person = "";
			}
			contact_person = contact_person.trim();

			String discover_type_id_str = discoverTypeItem.getValueAsString();
			if (discover_type_id_str == null
					|| discover_type_id_str.trim().equalsIgnoreCase("")) {
				SC.say(CallCenter.constants.chooseDiscoveryType());
				return;
			}
			Integer discover_type_id = null;
			try {
				discover_type_id = new Integer(discover_type_id_str);
			} catch (Exception e) {
				SC.say(CallCenter.constants.incorrDiscTypeSelected());
				return;
			}
			if (discover_txt.equalsIgnoreCase("")
					&& contact_phone.equalsIgnoreCase("")
					&& contact_person.equalsIgnoreCase("")) {
				SC.say(CallCenter.constants.enterSomeDiscoveryParam());
				return;
			}

			String discover_type = discoverTypeItem.getValueAsString();
			if (discover_type == null || discover_type.trim().equals("")) {
				SC.say(CallCenter.constants.enterType());
				return;
			}
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();

			record.setAttribute("phone", phone);
			record.setAttribute("call_id", sessionId);
			record.setAttribute("discover_txt", discover_txt);
			record.setAttribute("contact_phone", contact_phone);
			record.setAttribute("contact_person", contact_person);
			record.setAttribute("discover_type_id", discover_type_id);
			record.setAttribute("rec_user", userName);

			DSRequest req = new DSRequest();
			DataSource discoveryDS = DataSource.get("DiscoveryDS");
			req.setAttribute("operationId", "sendDiscovery");
			discoveryDS.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					chargePanel.setDlgSendDiscovery(null);
					destroy();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
