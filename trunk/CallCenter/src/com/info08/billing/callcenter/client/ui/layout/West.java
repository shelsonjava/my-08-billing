package com.info08.billing.callcenter.client.ui.layout;

import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.client.ui.menu.AddressStackSelection;
import com.info08.billing.callcenter.client.ui.menu.AdminStackSelection;
import com.info08.billing.callcenter.client.ui.menu.CallCenterStackSelection;
import com.info08.billing.callcenter.client.ui.menu.ControlStackSelection;
import com.info08.billing.callcenter.client.ui.menu.CorrectionStackSelection;
import com.info08.billing.callcenter.client.ui.menu.DiscoveryStackSelection;
import com.info08.billing.callcenter.client.ui.menu.MiscStackSelection;
import com.info08.billing.callcenter.client.ui.menu.StatStackSelection;
import com.info08.billing.callcenter.client.ui.menu.TransportStackSelection;
import com.info08.billing.callcenter.shared.common.ServerSession;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.VLayout;

public class West extends VLayout {

	private static SectionStack menuStack;
	protected static Body body;
	private static CorrectionStackSelection correction;
	private static ControlStackSelection control;
	private static DiscoveryStackSelection discovery;
	private static AddressStackSelection address;
	private static TransportStackSelection transport;
	private static MiscStackSelection various;
	private static AdminStackSelection admin;
	private static CallCenterStackSelection callCenter;
	private static StatStackSelection statistics;

	public West(Body body) {
		West.body = body;
		setWidth(210);
		setHeight100();

		menuStack = new SectionStack();
		menuStack.setVisibilityMode(VisibilityMode.MUTEX);
		menuStack.setWidth100();
		menuStack.setHeight100();

		callCenter = new CallCenterStackSelection(body);
		menuStack.addSection(callCenter);

		addMember(menuStack);
		addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
			}
		});
	}

	public static void openByLoggedUser() {
		try {
			Long personelTypeId = CommonSingleton.getInstance()
					.getSessionPerson().getPersonelTypeId();
			if (personelTypeId == null) {
				return;
			}
			// Not Call Center User
			if (!personelTypeId.equals(9L)) {
				correction = new CorrectionStackSelection(body);
				menuStack.addSection(correction);

				control = new ControlStackSelection(body);
				menuStack.addSection(control);

				discovery = new DiscoveryStackSelection(body);
				menuStack.addSection(discovery);

				address = new AddressStackSelection(body);
				menuStack.addSection(address);

				transport = new TransportStackSelection(body);
				menuStack.addSection(transport);

				various = new MiscStackSelection(body);
				menuStack.addSection(various);

				admin = new AdminStackSelection(body);
				menuStack.addSection(admin);
				
				statistics = new StatStackSelection(body);
				menuStack.addSection(statistics);
				
			}
			switch (personelTypeId.intValue()) {
			case 2: // Admin
				control.setExpanded(true);
				break;
			case 4: // Correction
				correction.setExpanded(true);
				break;
			case 6: // Discovery
				discovery.setExpanded(true);
				break;
			case 7: // IT
				control.setExpanded(true);
				break;
			case 8: // Control
				control.setExpanded(true);
				break;
			case 9: // Operator
				callCenter.setExpanded(true);
				break;
			case 10: // Accounting
				break;
			case 11: // Address
				address.setExpanded(true);
				break;
			case 12: // Transport
				transport.setExpanded(true);
				break;
			case 13: // Various
				various.setExpanded(true);
				break;
			default: // Other
				control.setExpanded(true);
				break;
			}
			// Not Call Center User
			if (!personelTypeId.equals(9L)) {
				control.setMenuPersmission();
				correction.setMenuPersmission();
				admin.setMenuPersmission();
				address.setMenuPersmission();
				transport.setMenuPersmission();
				various.setMenuPersmission();
				discovery.setMenuPersmission();
				statistics.setMenuPersmission();
			}
			callCenter.setMenuPersmission();
			if (personelTypeId.equals(9L)) {

				ServerSession serverSession = CommonSingleton.getInstance()
						.getServerSession();
				if (serverSession == null || !serverSession.isWebSession()) {

					// application loaded
					
					DataSource dataSource = DataSource.get("LockDS");
					
					com.smartgwt.client.rpc.RPCManager.startQueue();
					Record record = new Record();
					String sid = serverSession.getSessionId();
					record.setAttribute("session_my_id", sid);
					DSRequest dsRequest = new DSRequest();
					dsRequest.setAttribute("operationId", "updateLockStatus");
					dataSource.updateData(record, new DSCallback() {
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
						}
					}, dsRequest);
					com.smartgwt.client.rpc.RPCManager.sendQueue();
					
					
					String specTextMessage = serverSession
							.getSpecialAlertMessage();
					if (specTextMessage != null
							&& !specTextMessage.trim().equals("")) {
						SC.warn(specTextMessage.trim());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
