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
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgAddEditSessNote extends Window {

	private VLayout hLayout;
	private TextAreaItem noteItem;
	private SelectItem visOptItem;
	private SelectItem particItem;
	private DynamicForm form;

	public DlgAddEditSessNote(final Integer noteId, final String sessionId,
			Integer pVisibOption, Integer pParticular, String note,
			DataSource notesDS, final ListGrid listGrid) {
		try {

			setWidth(500);
			setHeight(260);
			setTitle(noteId == null ? "ახალი შენიშვნა" : "შენიშვნის შესწორება");
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
			form.setHeight(150);
			form.setTitleWidth(80);
			form.setNumCols(2);
			form.setDataSource(notesDS);

			visOptItem = new SelectItem();
			visOptItem.setTitle("ხილვადობა");
			visOptItem.setType("comboBox");
			visOptItem.setName("visibilityInt");

			LinkedHashMap<String, String> mapCallTypes = new LinkedHashMap<String, String>();
			mapCallTypes.put("0", "ღია");
			mapCallTypes.put("1", "დაფარული");
			visOptItem.setValueMap(mapCallTypes);
			visOptItem.setDefaultToFirstOption(true);
			visOptItem.setValue(pVisibOption);

			particItem = new SelectItem();
			particItem.setTitle("მნიშვნელოვანი");
			particItem.setType("comboBox");
			particItem.setName("particularInt");

			LinkedHashMap<String, String> mapParticTypes = new LinkedHashMap<String, String>();
			mapParticTypes.put("0", "ჩვეულებრივი");
			mapParticTypes.put("1", "მნიშვნელოვანი");
			particItem.setValueMap(mapParticTypes);
			particItem.setDefaultToFirstOption(true);
			particItem.setValue(pParticular);

			noteItem = new TextAreaItem();
			noteItem.setTitle("შენიშვნა");
			noteItem.setWidth(373);
			noteItem.setValue(note);
			noteItem.setName("note");

			HiddenItem textItem = new HiddenItem();
			textItem.setValue(CommonSingleton.getInstance().getSessionPerson()
					.getUser_name());
			textItem.setName("loggedUserName");
			textItem.setVisible(false);

			HiddenItem textItemSID = new HiddenItem();
			textItemSID.setValue(sessionId);
			textItemSID.setName("sessionId");
			textItemSID.setVisible(false);

			HiddenItem notIdItem = null;
			if (noteId != null) {
				notIdItem = new HiddenItem();
				notIdItem.setValue(noteId);
				notIdItem.setName("note_id");
				notIdItem.setVisible(false);
			}
			if (noteId == null) {
				form.setFields(visOptItem, particItem, noteItem, textItem,
						textItemSID);
			} else {
				form.setFields(visOptItem, particItem, noteItem, textItem,
						textItemSID, notIdItem);
			}

			HLayout hLayoutItem = new HLayout(5);
			hLayoutItem.setWidth100();
			hLayoutItem.setAlign(Alignment.RIGHT);
			hLayoutItem.setMargin(10);

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
						String note = noteItem.getValueAsString();
						if (note == null || note.trim().equals("")) {
							SC.say("ცარიელი კომენტარის შენახვა შეუძლებელია!");
							return;
						}
						com.smartgwt.client.rpc.RPCManager.startQueue();
						Record record = new Record();
						record.setAttribute("note_id", noteId);
						record.setAttribute("sessionId", sessionId);
						record.setAttribute("visibilityInt", new Integer(
								visOptItem.getValueAsString()));
						record.setAttribute("note", noteItem.getValueAsString());
						record.setAttribute("particularInt", new Integer(
								particItem.getValueAsString()));
						record.setAttribute("loggedUserName", CommonSingleton
								.getInstance().getSessionPerson().getUser_name());

						DSRequest req = new DSRequest();
						if (noteId == null) {
							req.setAttribute("operationId", "add");
							listGrid.addData(record, new DSCallback() {
								@Override
								public void execute(DSResponse response,
										Object rawData, DSRequest request) {
									destroy();
								}
							}, req);
						} else {
							req.setAttribute("operationId", "update");
							listGrid.updateData(record, new DSCallback() {
								@Override
								public void execute(DSResponse response,
										Object rawData, DSRequest request) {
									destroy();
								}
							}, req);
						}

						com.smartgwt.client.rpc.RPCManager.sendQueue();
						// form.saveData(new DSCallback() {
						// @Override
						// public void execute(DSResponse response,
						// Object rawData, DSRequest request) {
						// destroy();
						// }
						// });
					} catch (Exception e) {
						e.printStackTrace();
						SC.say(e.toString());
					}
				}
			});

		} catch (Exception e) {
			SC.say(e.toString());
		}
	}
}
