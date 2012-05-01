package com.info08.billing.callcenterbk.client.dialogs.callcenter;

import java.util.Date;

import com.bramosystems.oss.player.core.client.LoadException;
import com.bramosystems.oss.player.core.client.PluginNotFoundException;
import com.bramosystems.oss.player.core.client.PluginVersionException;
import com.bramosystems.oss.player.core.client.ui.FlashMediaPlayer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.info08.billing.callcenterbk.client.CallCenter;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DlgViewOpRemarks extends Window {

	private VLayout hLayout;
	private ListGrid listGrid;
	private DataSource logPersNotesDS = DataSource.get("LogPersNotesDS");
	private ToolStripButton remarksBtn;

	public DlgViewOpRemarks(ToolStripButton remarksBtn) {
		try {
			this.remarksBtn = remarksBtn;
			setTitle(CallCenter.constants.remarks());

			setHeight(400);
			setWidth(1000);
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

			ToolStrip toolStrip = new ToolStrip();
			toolStrip.setWidth100();
			toolStrip.setPadding(5);
			hLayout.addMember(toolStrip);

			ToolStripButton listenRecord = new ToolStripButton(
					CallCenter.constants.listenRecord(), "play.png");

			listenRecord.setLayoutAlign(Alignment.LEFT);
			listenRecord.setWidth(50);
			toolStrip.addButton(listenRecord);

			ToolStripButton remarks = new ToolStripButton(
					CallCenter.constants.agree(), "yes.png");

			remarks.setLayoutAlign(Alignment.LEFT);
			remarks.setWidth(50);
			toolStrip.addButton(remarks);

			listGrid = new ListGrid() {
				protected String getCellCSSText(ListGridRecord record,
						int rowNum, int colNum) {

					ListGridRecord countryRecord = (ListGridRecord) record;
					if (countryRecord == null) {
						return super.getCellCSSText(record, rowNum, colNum);
					}
					Integer particular = countryRecord
							.getAttributeAsInt("particularInt");
					if (particular != null && particular.equals(1)) {
						return "color:red;";
					} else {
						return super.getCellCSSText(record, rowNum, colNum);
					}
				}
			};

			listGrid.setWidth100();
			listGrid.setHeight100();
			listGrid.setWrapCells(true);
			listGrid.setFixedRecordHeights(false);
			listGrid.setCanDragSelectText(true);
			listGrid.setDataSource(logPersNotesDS);
			listGrid.setFetchOperation("notesCustSearchByOpCustom");
			String user_name = CommonSingleton.getInstance().getSessionPerson()
					.getUserName();
			Criteria criteria = new Criteria();
			criteria.setAttribute("user_name", user_name);
			criteria.setAttribute("received", 0);
			listGrid.setCriteria(criteria);
			listGrid.setAutoFetchData(true);

			ListGridField note = new ListGridField("note",
					CallCenter.constants.remark());
			note.setAlign(Alignment.LEFT);

			ListGridField sender = new ListGridField("sender",
					CallCenter.constants.sender(), 150);
			sender.setAlign(Alignment.LEFT);
			ListGridField receiver = new ListGridField("receiver",
					CallCenter.constants.receiver(), 150);
			receiver.setAlign(Alignment.LEFT);

			ListGridField rec_date = new ListGridField("rec_date",
					CallCenter.constants.sendDate(), 130);
			rec_date.setAlign(Alignment.CENTER);
			rec_date.setDateFormatter(DateDisplayFormat.TOUSSHORTDATETIME);

			ListGridField phone = new ListGridField("phone",
					CallCenter.constants.phone(), 80);
			phone.setAlign(Alignment.LEFT);

			listGrid.setFields(sender, rec_date, receiver, phone, note);

			hLayout.addMember(listGrid);

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);

			IButton cancItem = new IButton();
			cancItem.setTitle(CallCenter.constants.close());
			cancItem.setWidth(100);

			hLayoutItem.setMembers(cancItem);

			hLayout.addMember(hLayoutItem);

			addItem(hLayout);

			cancItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					closeDlg();
				}
			});

			remarks.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ListGridRecord listGridRecord = listGrid
							.getSelectedRecord();
					if (listGridRecord == null) {
						SC.say(CallCenter.constants.warning(),
								CallCenter.constants.pleaseSelrecord());
						return;
					}
					updatePersNote(listGridRecord);
				}
			});

			listenRecord.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					ListGridRecord editRecord = listGrid.getSelectedRecord();
					if (editRecord == null) {
						SC.say(CallCenter.constants.warning(),
								CallCenter.constants.pleaseSelrecord());
						return;
					}

					String sessionId = editRecord
							.getAttributeAsString("sessionId");
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
				}
			});

			addCloseClickHandler(new CloseClickHandler() {
				@Override
				public void onCloseClick(CloseClickEvent event) {
					closeDlg();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void closeDlg() {
		try {
			RecordList recordList = listGrid.getDataAsRecordList();
			int length = recordList.getLength();
			if (length <= 0) {
				ServerSession serverSession = CommonSingleton.getInstance()
						.getServerSession();
				serverSession.setUnreadPersNotesCount(0L);
				CommonSingleton.getInstance().setServerSession(serverSession);
				remarksBtn.setIcon("information.png");
			}
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void updatePersNote(ListGridRecord listGridRecord) {
		try {
			com.smartgwt.client.rpc.RPCManager.startQueue();
			Record record = new Record();
			record.setAttribute("note_id",
					listGridRecord.getAttributeAsInt("note_id"));
			String loggedUser = CommonSingleton.getInstance()
					.getSessionPerson().getUserName();
			record.setAttribute("upd_user", loggedUser);
			DSRequest req = new DSRequest();

			req.setAttribute("operationId", "updatePersNote");
			listGrid.updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
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
