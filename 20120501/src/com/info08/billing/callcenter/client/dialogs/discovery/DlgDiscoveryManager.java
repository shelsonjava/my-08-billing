package com.info08.billing.callcenter.client.dialogs.discovery;

import java.util.Date;

import com.bramosystems.oss.player.core.client.LoadException;
import com.bramosystems.oss.player.core.client.PluginNotFoundException;
import com.bramosystems.oss.player.core.client.PluginVersionException;
import com.bramosystems.oss.player.core.client.ui.FlashMediaPlayer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.content.discovery.TabDiscovery;
import com.info08.billing.callcenter.client.dialogs.control.NotesDialog;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.LinkItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class DlgDiscoveryManager extends Window {

	private VLayout hLayout;
	private ListGridRecord editRecord;

	private ToolStripButton saveRecordBtn;
	private ToolStripButton listenRecordBtn;
	private ToolStripButton chargeBtn;
	private ToolStripButton removeChargeBtn;
	private ToolStripButton smsBtn;
	private ToolStripButton commentBtn;
	private ToolStripButton resolveDiscBtn;

	public DlgDiscoveryManager(final TabDiscovery tabDiscovery,
			final DataSource dataSource, final ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			setTitle(CallCenter.constants.resolveDiscovery());

			setHeight(330);
			setWidth(850);
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

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			hLayout.addMember(toolStrip);

			saveRecordBtn = new ToolStripButton(
					CallCenter.constants.saveRecord(), "save.png");
			saveRecordBtn.setLayoutAlign(Alignment.LEFT);
			saveRecordBtn.setWidth(50);
			toolStrip.addButton(saveRecordBtn);

			listenRecordBtn = new ToolStripButton(
					CallCenter.constants.listenRecord(), "play.png");
			listenRecordBtn.setLayoutAlign(Alignment.LEFT);
			listenRecordBtn.setWidth(50);
			toolStrip.addButton(listenRecordBtn);

			chargeBtn = new ToolStripButton(CallCenter.constants.charge(),
					"moneySmall.png");
			chargeBtn.setLayoutAlign(Alignment.LEFT);
			chargeBtn.setWidth(50);
			toolStrip.addButton(chargeBtn);

			removeChargeBtn = new ToolStripButton(
					CallCenter.constants.removeCharge(), "removeCharge.png");
			removeChargeBtn.setLayoutAlign(Alignment.LEFT);
			removeChargeBtn.setWidth(50);
			toolStrip.addButton(removeChargeBtn);

			smsBtn = new ToolStripButton(CallCenter.constants.sms(), "sms.png");
			smsBtn.setLayoutAlign(Alignment.LEFT);
			smsBtn.setWidth(50);
			toolStrip.addButton(smsBtn);

			commentBtn = new ToolStripButton(CallCenter.constants.comment(),
					"comment.png");
			commentBtn.setLayoutAlign(Alignment.LEFT);
			commentBtn.setWidth(50);
			toolStrip.addButton(commentBtn);

			resolveDiscBtn = new ToolStripButton(
					CallCenter.constants.resolve(), "yes.png");
			resolveDiscBtn.setLayoutAlign(Alignment.LEFT);
			resolveDiscBtn.setWidth(50);
			toolStrip.addButton(resolveDiscBtn);

			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setCanSelectText(true);
			detailViewer.setDataSource(dataSource);
			detailViewer.setWidth100();
			detailViewer.setHeight100();
			detailViewer.selectRecord(pRecord);
			DetailViewerField discover_type = new DetailViewerField(
					"discover_type", CallCenter.constants.type());
			DetailViewerField phone = new DetailViewerField("phone",
					CallCenter.constants.phone());
			DetailViewerField contact_phone = new DetailViewerField(
					"contact_phone", CallCenter.constants.contactPhone());
			DetailViewerField contact_person = new DetailViewerField(
					"contact_person", CallCenter.constants.contactPerson());
			DetailViewerField discover_txt = new DetailViewerField(
					"discover_txt", CallCenter.constants.message());
			DetailViewerField rec_user = new DetailViewerField("rec_user",
					CallCenter.constants.operator());
			DetailViewerField rec_date = new DetailViewerField("rec_date",
					CallCenter.constants.time());
			DetailViewerField status_descr = new DetailViewerField(
					"status_descr", CallCenter.constants.status());

			rec_date.setDateFormatter(DateDisplayFormat.TOSERIALIZEABLEDATE);

			detailViewer.setFields(discover_type, phone, contact_phone,
					contact_person, discover_txt, rec_user, rec_date,
					status_descr);

			ListGridRecord arr[] = new ListGridRecord[1];
			arr[0] = pRecord;
			detailViewer.setData(arr);

			hLayout.addMember(detailViewer);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenter.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					tabDiscovery.search();
					destroy();
				}
			});
			addCloseClickHandler(new CloseClickHandler() {
				@Override
				public void onCloseClick(CloseClickEvent event) {
					tabDiscovery.search();
					destroy();
				}
			});
			addItem(hLayout);

			listenRecordBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					try {
						String sessionId = editRecord
								.getAttributeAsString("call_id");
						Date date = editRecord.getAttributeAsDate("start_date");

						if (sessionId == null || sessionId.trim().equals("")) {
							SC.say(CallCenter.constants.warning(),
									CallCenter.constants.invalidSession());
							return;
						}
						if (date == null) {
							SC.say(CallCenter.constants.warning(),
									CallCenter.constants.invalidSessionDate());
							return;
						}
						getURL(sessionId, date);
					} catch (Exception e) {
						SC.say(e.toString());
					}
				}
			});

			removeChargeBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					showChargeDialog();
				}
			});

			chargeBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					DlgAddCharge dlgAddCharge = new DlgAddCharge(editRecord
							.getAttributeAsString("phone"), editRecord
							.getAttributeAsString("rec_user"), editRecord
							.getAttributeAsDate("rec_date"));
					dlgAddCharge.show();
				}
			});

			smsBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgSendDiscSMS dlgSendDiscSMS = new DlgSendDiscSMS(
							editRecord);
					dlgSendDiscSMS.show();
				}
			});

			resolveDiscBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					showResolveDialog();
				}
			});

			commentBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					String sessionId = editRecord
							.getAttributeAsString("call_id");
					Integer personnel_id = editRecord
							.getAttributeAsInt("personnel_id");
					NotesDialog notesDialog = new NotesDialog(sessionId,
							personnel_id);
					notesDialog.show();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void showResolveDialog() {
		DlgResolveDiscovery dlgResolveDiscovery = new DlgResolveDiscovery(this,
				editRecord);
		dlgResolveDiscovery.show();
	}

	private void showChargeDialog() {
		try {
			DlgRemoveCharge discoveryChargesViewer = new DlgRemoveCharge(
					editRecord.getAttributeAsString("phone"),
					editRecord.getAttributeAsString("rec_user"),
					editRecord.getAttributeAsDate("rec_date"));
			discoveryChargesViewer.show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void getURL(String sessionId, Date date) {
		try {
			CallCenter.commonService.findSessionMp3ById(sessionId, date,
					new AsyncCallback<String>() {

						@Override
						public void onSuccess(String result) {
							if (result == null || result.trim().equals("")) {
								SC.say(CallCenter.constants.sessionNotFound()
										+ result);
								return;
							}
							playSessionRecord(result);
						}

						@Override
						public void onFailure(Throwable caught) {
							SC.say(caught.toString());
						}
					});
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	protected void getDownloadFile(String url) {
		try {
			Window window = new Window();
			window.setWidth(350);
			window.setHeight(70);
			window.setTitle("ფაილის გადმოწერა");
			window.setIsModal(true);
			window.setShowCloseButton(true);
			window.setCanDrag(false);
			window.setCanDragReposition(false);
			window.setCanDragResize(false);
			window.setCanDragScroll(false);
			window.centerInPage();

			HLayout hLayout = new HLayout();
			hLayout.setWidth100();
			hLayout.setHeight100();
			hLayout.setPadding(10);

			DynamicForm dynamicForm = new DynamicForm();
			dynamicForm.setWidth100();
			dynamicForm.setHeight100();
			dynamicForm.setTitleWidth(150);

			LinkItem linkItem = new LinkItem();
			linkItem.setTitle("ფაილის მისამართი");
			linkItem.setLinkTitle("გადმოწერეთ ფაილი");
			linkItem.setValue(url);

			dynamicForm.setFields(linkItem);

			hLayout.addMember(dynamicForm);

			window.addItem(hLayout);
			window.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void playSessionRecord(String url) {
		try {
			final Window winModal = new Window();
			winModal.setWidth(500);
			winModal.setHeight(80);
			winModal.setTitle("ჩანაწერის მოსმენა");
			winModal.setShowMinimizeButton(false);
			winModal.setIsModal(true);
			winModal.setShowModalMask(true);
			winModal.centerInPage();
			winModal.addCloseClickHandler(new CloseClickHandler() {
				@Override
				public void onCloseClick(CloseClickEvent event) {
					winModal.destroy();
				}
			});
			FlashMediaPlayer player = null;
			HLayout hLayout = new HLayout();
			hLayout.setWidth100();
			hLayout.setHeight100();
			player = new FlashMediaPlayer(url);

			hLayout.addMember(player);
			winModal.addItem(hLayout);
			winModal.show();
			// player.setVolume(1d);
		} catch (LoadException e) {
			SC.say(e.getMessage());
			return;
		} catch (PluginVersionException e) {
			SC.say(e.getMessage());
			return;
		} catch (PluginNotFoundException e) {
			SC.say(e.getMessage());
			return;
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

}
