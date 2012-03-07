package com.info08.billing.callcenter.client.dialogs.control;

import java.util.Date;

import com.bramosystems.oss.player.core.client.LoadException;
import com.bramosystems.oss.player.core.client.PluginNotFoundException;
import com.bramosystems.oss.player.core.client.PluginVersionException;
import com.bramosystems.oss.player.core.client.ui.FlashMediaPlayer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenter.client.CallCenter;
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

public class NotesDialog extends Window {

	private VLayout hLayout;
	private DataSource notesDS = null;
	private ListGrid notesGrid = null;
	private String mySessionId;
	private DetailViewer detailViewer;
	protected Integer myOperId;

	public NotesDialog(String sessionId, Integer operId) {

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

		notesDS = DataSource.get("LogPersNotesDS");

		notesDS.getField("note_id").setTitle("ID");
		notesDS.getField("sessionId").setTitle("SID");
		notesDS.getField("receiver").setTitle("მიმღები");
		notesDS.getField("sender").setTitle("გამგზავნი");
		notesDS.getField("phone").setTitle("ნომერი");
		notesDS.getField("note").setTitle("შენიშვნის დასახელება");
		notesDS.getField("rec_date").setTitle("თარიღი");
		notesDS.getField("visibility").setTitle("ხილვადობა");
		notesDS.getField("particular").setTitle("მნიშვნელოვანი");
		notesDS.getField("visibilityInt").setTitle("VINT");
		notesDS.getField("particularInt").setTitle("PINT");

		ListGridField receiver = new ListGridField("receiver", "მიმღები", 200);
		ListGridField sender = new ListGridField("sender", "გამგზავნი", 200);
		ListGridField phone = new ListGridField("phone", "ნომერი", 80);
		ListGridField note = new ListGridField("note", "შენიშვნის დასახელება");
		ListGridField recDate = new ListGridField("rec_date", "თარიღი", 120);
		ListGridField visibility = new ListGridField("visibility", "ხილვადობა",
				90);

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
		notesGrid.setDataSource(notesDS);
		notesGrid.setShowFilterEditor(false);
		notesGrid.setCanEdit(false);
		notesGrid.setCanRemoveRecords(false);
		notesGrid.setCanDragSelectText(true);
		notesGrid.setFixedRecordHeights(false);
		notesGrid.setWrapCells(true);
		notesGrid.setFields(receiver, sender, phone, note, recDate, visibility);

		Criteria criteria = new Criteria();
		if (sessionId != null) {
			notesGrid.setFetchOperation("notesCustSearch");
			criteria.addCriteria("sessionId", sessionId);
		} else {
			notesGrid.setFetchOperation("notesCustSearchByOp");
			criteria.addCriteria("operId", operId);
		}
		notesGrid.filterData(criteria);

		detailViewer = new DetailViewer();
		detailViewer.setDataSource(notesDS);

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
					saveOrUpdateNote(false, notesGrid, notesDS, mySessionId);
				}
			});
		}
		editNote.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveOrUpdateNote(true, notesGrid, notesDS, mySessionId);
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
					SC.say(CallCenter.constants.warning(),
							CallCenter.constants.pleaseSelrecord());
					return;
				}
				String sessionId = record.getAttributeAsString("sessionId");
				if (sessionId == null || sessionId.trim().equals("")) {
					SC.say(CallCenter.constants.warning(),
							CallCenter.constants.invalidSession());
					return;
				}
				Date date = record.getAttributeAsDate("start_date");
				getURL(sessionId, date);

			}
		});

		addItem(hLayout);
	}

	private void getURL(String sessionId, Date date) {
		try {
			CallCenter.commonService.findSessionMp3ById(sessionId, date,
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

								com.smartgwt.client.rpc.RPCManager.startQueue();
								Record record = new Record();
								record.setAttribute("note_id", listGridRecord
										.getAttributeAsInt("note_id"));

								DSRequest req = new DSRequest();

								req.setAttribute("operationId",
										"deleteLogPersNoteItem");
								notesGrid.removeData(record, new DSCallback() {
									@Override
									public void execute(DSResponse response,
											Object rawData, DSRequest request) {
									}
								}, req);
								com.smartgwt.client.rpc.RPCManager.sendQueue();
								detailViewer.clear();
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
			Integer noteId = null;
			Integer pVisibOption = null;
			Integer pParticular = null;
			String note = "";
			if (isEdit) {
				ListGridRecord listGridRecord = notesGrid.getSelectedRecord();
				if (listGridRecord == null) {
					SC.say("გთხოვთ მონიშნოთ ჩანაწერი ცხრილში");
					return;
				}
				noteId = listGridRecord.getAttributeAsInt("note_id");
				pVisibOption = listGridRecord
						.getAttributeAsInt("visibilityInt");
				pParticular = listGridRecord.getAttributeAsInt("particularInt");
				note = listGridRecord.getAttributeAsString("note");
			}
			DlgAddEditSessNote addEditNoteDialog = new DlgAddEditSessNote(
					noteId, sessionId, pVisibOption, pParticular, note,
					notesDS, notesGrid);
			addEditNoteDialog.show();
		} catch (Exception e) {
			SC.say(e.toString());
		}
	}

	public DataSource getNotesDS() {
		return notesDS;
	}
}
