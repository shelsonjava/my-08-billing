package com.info08.billing.callcenterbk.client.dialogs.survey;

import java.util.Date;

import com.bramosystems.oss.player.core.client.AbstractMediaPlayer;
import com.bramosystems.oss.player.core.client.PlayerUtil;
import com.bramosystems.oss.player.core.client.Plugin;
import com.bramosystems.oss.player.core.client.PluginNotFoundException;
import com.bramosystems.oss.player.core.client.PluginVersionException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.survey.TabSurvey;
import com.info08.billing.callcenterbk.client.dialogs.control.NotesDialog;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
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

public class DlgSurveyManager extends Window {

	private VLayout hLayout;
	private ListGridRecord editRecord;

	private ToolStripButton saveRecordBtn;
	private ToolStripButton listenRecordBtn;
	private ToolStripButton chargeBtn;
	private ToolStripButton removeChargeBtn;
	private ToolStripButton smsBtn;
	private ToolStripButton commentBtn;
	private ToolStripButton resolveDiscBtn;

	public DlgSurveyManager(final TabSurvey tabSurvey,
			final DataSource dataSource, final ListGridRecord pRecord) {
		try {
			this.editRecord = pRecord;
			setTitle(CallCenterBK.constants.resolveSurvey());

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
					CallCenterBK.constants.saveRecord(), "save.png");
			saveRecordBtn.setLayoutAlign(Alignment.LEFT);
			saveRecordBtn.setWidth(50);
			toolStrip.addButton(saveRecordBtn);

			listenRecordBtn = new ToolStripButton(
					CallCenterBK.constants.listenRecord(), "play.png");
			listenRecordBtn.setLayoutAlign(Alignment.LEFT);
			listenRecordBtn.setWidth(50);
			toolStrip.addButton(listenRecordBtn);

			chargeBtn = new ToolStripButton(CallCenterBK.constants.charge(),
					"moneySmall.png");
			chargeBtn.setLayoutAlign(Alignment.LEFT);
			chargeBtn.setWidth(50);
			toolStrip.addButton(chargeBtn);

			removeChargeBtn = new ToolStripButton(
					CallCenterBK.constants.removeCharge(), "removeCharge.png");
			removeChargeBtn.setLayoutAlign(Alignment.LEFT);
			removeChargeBtn.setWidth(50);
			toolStrip.addButton(removeChargeBtn);

			smsBtn = new ToolStripButton(CallCenterBK.constants.sms(),
					"sms.png");
			smsBtn.setLayoutAlign(Alignment.LEFT);
			smsBtn.setWidth(50);
			toolStrip.addButton(smsBtn);

			commentBtn = new ToolStripButton(CallCenterBK.constants.comment(),
					"comment.png");
			commentBtn.setLayoutAlign(Alignment.LEFT);
			commentBtn.setWidth(50);
			toolStrip.addButton(commentBtn);

			resolveDiscBtn = new ToolStripButton(
					CallCenterBK.constants.resolve(), "yes.png");
			resolveDiscBtn.setLayoutAlign(Alignment.LEFT);
			resolveDiscBtn.setWidth(50);
			toolStrip.addButton(resolveDiscBtn);

			final DetailViewer detailViewer = new DetailViewer();
			detailViewer.setCanSelectText(true);
			detailViewer.setDataSource(dataSource);
			detailViewer.setWidth100();
			detailViewer.setHeight100();
			detailViewer.selectRecord(pRecord);

			DetailViewerField survey_kind_name = new DetailViewerField(
					"survey_kind_name", CallCenterBK.constants.type());
			DetailViewerField p_numb = new DetailViewerField("p_numb",
					CallCenterBK.constants.phone());
			DetailViewerField survey_phone = new DetailViewerField(
					"survey_phone", CallCenterBK.constants.contactPhone());
			DetailViewerField survey_person = new DetailViewerField(
					"survey_person", CallCenterBK.constants.contactPerson());
			DetailViewerField survey_descript = new DetailViewerField(
					"survey_descript", CallCenterBK.constants.message());
			DetailViewerField rec_user = new DetailViewerField(
					"survey_creator", CallCenterBK.constants.operator());
			DetailViewerField rec_date = new DetailViewerField(
					"survey_created", CallCenterBK.constants.time());
			DetailViewerField status_descr = new DetailViewerField(
					"status_descr", CallCenterBK.constants.status());

			rec_date.setDateFormatter(DateDisplayFormat.TOSERIALIZEABLEDATE);

			detailViewer.setFields(survey_kind_name, p_numb, survey_phone,
					survey_person, survey_descript, rec_user, rec_date,
					status_descr);

			ListGridRecord arr[] = new ListGridRecord[1];
			arr[0] = pRecord;
			detailViewer.setData(arr);

			hLayout.addMember(detailViewer);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenterBK.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(cancItem);

			hLayout.addMember(hLayoutItem);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					tabSurvey.search();
					destroy();
				}
			});
			addCloseClickHandler(new CloseClickHandler() {
				@Override
				public void onCloseClick(CloseClickEvent event) {
					tabSurvey.search();
					destroy();
				}
			});
			addItem(hLayout);

			listenRecordBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					try {
						String sessionId = editRecord
								.getAttributeAsString("session_call_id");
						Date date = editRecord.getAttributeAsDate("start_date");

						if (sessionId == null || sessionId.trim().equals("")) {
							SC.say(CallCenterBK.constants.warning(),
									CallCenterBK.constants.invalidSession());
							return;
						}
						if (date == null) {
							SC.say(CallCenterBK.constants.warning(),
									CallCenterBK.constants.invalidSessionDate());
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
					
					String phoneNumber = pRecord.getAttributeAsString("p_numb");
					
					DataSource dataSource = DataSource.get("SubscriberDS");
					Criteria criteria = new Criteria();
					criteria.setAttribute("phone_number", phoneNumber);
					DSRequest dsRequest = new DSRequest();
					dsRequest.setOperationId("countFreeOfCharge");
					dataSource.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							Record records[] = response.getData();
							boolean flag = false;
							if (records != null && records.length > 0) {
								Long cnt = new Long(records[0]
										.getAttributeAsString("tmp_count"));
								if (cnt.intValue() > 0) {
									flag = true;
								}
							}
							if (flag) {
								SC.say(CallCenterBK.constants
										.freeOfChargeMessage());
								return;
							} else {
								DlgAddCharge dlgAddCharge = new DlgAddCharge(
										editRecord
												.getAttributeAsString("p_numb"),
										editRecord
												.getAttributeAsString("rec_user"),
										editRecord
												.getAttributeAsDate("rec_date"));
								dlgAddCharge.show();
							}
						}
					}, dsRequest);
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
							.getAttributeAsString("session_call_id");
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
		DlgResolveSurvey dlgResolveSurvey = new DlgResolveSurvey(this,
				editRecord);
		dlgResolveSurvey.show();
	}

	private void showChargeDialog() {
		try {
			DlgRemoveCharge surveyChargesViewer = new DlgRemoveCharge(
					editRecord.getAttributeAsString("p_numb"),
					editRecord.getAttributeAsString("survey_creator"),
					editRecord.getAttributeAsDate("survey_created"));
			surveyChargesViewer.show();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void getURL(String sessionId, Date date) {
		try {
			CallCenterBK.commonService.findSessionMp3ById(sessionId, date,
					new AsyncCallback<String>() {

						@Override
						public void onSuccess(String result) {
							if (result == null || result.trim().equals("")) {
								SC.say(CallCenterBK.constants.sessionNotFound()
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
			AbstractMediaPlayer player = null;
			HLayout hLayout = new HLayout();
			hLayout.setWidth100();
			hLayout.setHeight100();
			player = PlayerUtil.getPlayer(Plugin.Auto, url, true);// new FlashMediaPlayer(url);

			hLayout.addMember(player);
			winModal.addItem(hLayout);
			winModal.show();
			// player.setVolume(1d);
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
