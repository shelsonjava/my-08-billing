package com.info08.billing.callcenterbk.client.dialogs.admin;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FileItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;

public class DlgStaffUploadPicture extends Window {

	private VLayout hLayout;
	int photoId;

	private TileGrid tileGrid;
	private CanvasItem photoItem;
	private FileItem fileItem;

	private DynamicForm photoUploadForm;

	public DlgStaffUploadPicture(int photoId) {
		this.photoId = photoId;
		setTitle("სურათის ატვირთვა");

		setHeight(380);
		setWidth(340);
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

		photoUploadForm = new DynamicForm();
		photoUploadForm.setAutoFocus(true);
		photoUploadForm.setWidth100();
		photoUploadForm.setNumCols(1);
		photoUploadForm.setTitleWidth(150);
		photoUploadForm.setTitleOrientation(TitleOrientation.TOP);
		photoUploadForm.setLayoutAlign(Alignment.CENTER);
		photoUploadForm.setAlign(Alignment.CENTER);

		photoItem = new CanvasItem();
		photoItem.setTitle("");
		photoItem.setWidth(120);
		photoItem.setName("photo");
		photoItem.setHeight(70);
		photoItem.setRowSpan(5);

		tileGrid = new TileGrid();
		tileGrid.setHeight(180);
		tileGrid.setWidth(144);

		fileItem = new FileItem();
		fileItem.setName("fileItem");

		photoItem.setCanvas(tileGrid);

		ButtonItem saveButtonItem = new ButtonItem();
		saveButtonItem.setName("saveButtonItem");
		saveButtonItem.setTitle("ატვირთვა");
		saveButtonItem.setWidth(150);

		photoUploadForm.setFields(photoItem, fileItem, saveButtonItem);

		HLayout formsLayout = new HLayout();
		formsLayout.setWidth100();
		formsLayout.setHeight100();

		formsLayout.setMembers(photoUploadForm);

		HLayout hLayoutItem = new HLayout(5);
		hLayoutItem.setWidth100();
		hLayoutItem.setAlign(Alignment.RIGHT);

		IButton saveItem = new IButton();
		saveItem.setTitle(CallCenterBK.constants.save());
		saveItem.setWidth(100);

		IButton cancItem = new IButton();
		cancItem.setTitle(CallCenterBK.constants.close());
		cancItem.setWidth(100);

		hLayoutItem.setMembers(saveItem, cancItem);

		hLayout.setMembers(formsLayout, hLayoutItem);

		fillFields();
		addItem(hLayout);
	}

	private void fillFields() {
	}

}
