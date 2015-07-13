package com.info08.billing.callcenterbk.client.dialogs.control;

import java.util.Date;

import com.bramosystems.oss.player.core.client.AbstractMediaPlayer;
import com.bramosystems.oss.player.core.client.PlayerUtil;
import com.bramosystems.oss.player.core.client.Plugin;
import com.bramosystems.oss.player.core.client.PluginNotFoundException;
import com.bramosystems.oss.player.core.client.PluginVersionException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.common.components.MyWindow;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class NotesDialog extends MyWindow {

	private VLayout hLayout;
	private DataSource operatorWarnsDS = null;
	private ListGrid notesGrid = null;
	private String mySessionId;
	private DetailViewer detailViewer;
	protected Integer myOperId;

	public NotesDialog(String sessionId, Integer operId) {
		super();
		mySessionId = sessionId;
		myOperId = operId;

		setWidth(1200);
		setHeight(700);
		setTitle(sessionId == null ? "ზარის შენიშვნები"
				: "შენიშვნები ოპერატორების მიხედვით");
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setCanDrag(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setCanDragScroll(false);
		centerInPage();

		operatorWarnsDS = DataSource.get("OperatorWarnsDS");

		operatorWarnsDS.getField("oper_warn_id").setTitle("ID");
		operatorWarnsDS.getField("call_session_id").setTitle("SID");
		operatorWarnsDS.getField("operator").setTitle("მიმღები");
		operatorWarnsDS.getField("warn_sender").setTitle("გამგზავნი");
		operatorWarnsDS.getField("phone_number").setTitle("ნომერი");
		operatorWarnsDS.getField("warning").setTitle("შენიშვნის დასახელება");
		operatorWarnsDS.getField("warn_send_date").setTitle("თარიღი");
		operatorWarnsDS.getField("hiddenDescr").setTitle("ხილვადობა");
		operatorWarnsDS.getField("importantDescr").setTitle("მნიშვნელოვანი");
		operatorWarnsDS.getField("update_date").setTitle(
				CallCenterBK.constants.updDate());
		operatorWarnsDS.getField("update_user").setTitle(
				CallCenterBK.constants.updUser());

		ListGridField receiver = new ListGridField("operator", "მიმღები", 200);
		ListGridField sender = new ListGridField("warn_sender", "გამგზავნი",
				200);
		ListGridField phone = new ListGridField("phone_number", "ნომერი", 80);
		ListGridField note = new ListGridField("warning",
				"შენიშვნის დასახელება");
		ListGridField recDate = new ListGridField("warn_send_date", "თარიღი",
				120);
		ListGridField visibility = new ListGridField("hiddenDescr",
				"ხილვადობა", 90);

		hLayout = new VLayout(5);
		hLayout.setWidth100();
		hLayout.setHeight100();

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(5);

		ToolStripButton newNote = null;
		if (sessionId != null) {
			newNote = new ToolStripButton("ახალი", "new_record.gif");
			newNote.setLayoutAlign(Alignment.LEFT);
			newNote.setWidth(50);
			toolStrip.addButton(newNote);
		}

		ToolStripButton editNote = new ToolStripButton("შესწორება",
				"edit_record.png");
		editNote.setLayoutAlign(Alignment.LEFT);
		editNote.setWidth(50);
		toolStrip.addButton(editNote);

		ToolStripButton deleteNote = new ToolStripButton("წაშლა",
				"delete_record.gif");
		deleteNote.setLayoutAlign(Alignment.LEFT);
		deleteNote.setWidth(50);
		toolStrip.addButton(deleteNote);

		toolStrip.addSeparator();

		ToolStripButton playButton = new ToolStripButton("მოსმენა", "play.png");
		playButton.setLayoutAlign(Alignment.LEFT);
		playButton.setWidth(50);
		toolStrip.addButton(playButton);

		notesGrid = new ListGrid();
		notesGrid.setWidth100();
		notesGrid.setHeight(250);
		notesGrid.setAutoFetchData(false);
		notesGrid.setAlternateRecordStyles(true);
		notesGrid.setDataSource(operatorWarnsDS);
		notesGrid.setShowFilterEditor(false);
		notesGrid.setCanEdit(false);
		notesGrid.setCanRemoveRecords(false);
		notesGrid.setCanDragSelectText(true);
		notesGrid.setFixedRecordHeights(false);
		notesGrid.setWrapCells(true);
		notesGrid.setFields(receiver, sender, phone, note, recDate, visibility);
		notesGrid.setFetchOperation("operatorWarnsSeach");
		Criteria criteria = new Criteria();
		if (sessionId != null) {
			criteria.addCriteria("call_session_id", sessionId);
		} else {
			criteria.addCriteria("user_id", operId);
		}
		notesGrid.filterData(criteria);

		detailViewer = new DetailViewer();
		detailViewer.setDataSource(operatorWarnsDS);

		hLayout.setMembers(toolStrip, notesGrid, detailViewer);

		notesGrid.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				detailViewer.viewSelectedData(notesGrid);
			}
		});

		if (newNote != null) {
			newNote.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					saveOrUpdateNote(false, notesGrid, operatorWarnsDS,
							mySessionId);
				}
			});
		}
		editNote.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveOrUpdateNote(true, notesGrid, operatorWarnsDS, mySessionId);
			}
		});
		deleteNote.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deleteNote();
			}
		});

		playButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = notesGrid.getSelectedRecord();
				if (record == null) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.pleaseSelrecord());
					return;
				}
				String call_session_id = record
						.getAttributeAsString("call_session_id");
				if (call_session_id == null
						|| call_session_id.trim().equals("")) {
					SC.say(CallCenterBK.constants.warning(),
							CallCenterBK.constants.invalidSession());
					return;
				}
				Date call_start_date = record
						.getAttributeAsDate("call_start_date");
				getURL(call_session_id, call_start_date);

			}
		});

		addItem(hLayout);
	}

	private void getURL(String sessionId, Date date) {
		try {
			CallCenterBK.commonService.findSessionMp3ById(sessionId, date,
					new AsyncCallback<String>() {

						@Override
						public void onSuccess(String result) {
							if (result == null || result.trim().equals("")) {
								SC.say("სესიის მისამართი ვერ მოიძებნა : "
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

	private void deleteNote() {
		try {
			final ListGridRecord listGridRecord = notesGrid.getSelectedRecord();
			if (listGridRecord == null) {
				SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
				return;
			}
			SC.ask("გაფრთხილება",
					"დარწმუნებული ხართ რომ გნებავთ ჩანაწერის წაშლა ?",
					new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if (value) {
								try {
									com.smartgwt.client.rpc.RPCManager
											.startQueue();
									Record record = new Record();
									record.setAttribute(
											"oper_warn_id",
											listGridRecord
													.getAttributeAsInt("oper_warn_id"));
									record.setAttribute("loggedUserName",
											CommonSingleton.getInstance()
													.getSessionPerson()
													.getUser_name());

									DSRequest req = new DSRequest();

									req.setAttribute("operationId",
											"deleteOperatorWarn");
									notesGrid.removeData(record,
											new DSCallback() {
												@Override
												public void execute(
														DSResponse response,
														Object rawData,
														DSRequest request) {
													detailViewer.clear();
												}
											}, req);
									com.smartgwt.client.rpc.RPCManager
											.sendQueue();
								} catch (Exception e) {
									e.printStackTrace();
									SC.say(e.toString());
								}
							}
						}
					});

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	private void saveOrUpdateNote(boolean isEdit, ListGrid notesGrid,
			DataSource notesDS, String sessionId) {
		try {
			Integer oper_warn_id = null;
			Integer hidden = null;
			Integer important = null;
			String warning = "";
			if (isEdit) {
				ListGridRecord listGridRecord = notesGrid.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
					return;
				}
				oper_warn_id = listGridRecord.getAttributeAsInt("oper_warn_id");
				hidden = listGridRecord.getAttributeAsInt("hidden");
				important = listGridRecord.getAttributeAsInt("important");
				warning = listGridRecord.getAttributeAsString("warning");
			}
			DlgAddEditSessNote addEditNoteDialog = new DlgAddEditSessNote(
					oper_warn_id, sessionId, hidden, important, warning,
					notesDS, notesGrid);
			addEditNoteDialog.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	public DataSource getNotesDS() {
		return operatorWarnsDS;
	}
}
