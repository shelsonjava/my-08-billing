package com.info08.billing.callcenterbk.client.common.components;

import java.util.Map;
import java.util.TreeMap;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FileItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DlgStaffUploadPicture extends Window {

	private VLayout hLayout;
	Integer photoId;

	// private TileGrid tileGrid;
	private ImageViewerItem photoItem;
	private FileItem fileItem;

	private DynamicForm photoUploadForm;
	private ImageViewerItem parentItem;

	public DlgStaffUploadPicture(Integer photoId, ImageViewerItem parentItem) {
		this.photoId = photoId;
		this.parentItem = parentItem;
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

		photoItem = new ImageViewerItem("photo", "");
		// photoItem.setTitle("");
		// photoItem.setWidth(140);
		// photoItem.setName("photo");
		// photoItem.setHeight(70);
		// photoItem.setRowSpan(5);
		//
		// tileGrid = new TileGrid();
		// tileGrid.setWidth(150);
		// tileGrid.setHeight(180);
		//
		//
		// tileGrid.setTileHeight(158);
		// tileGrid.setTileWidth(127);
		// tileGrid.setTileMargin(0);
		// tileGrid.setTileValueAlign("center");
		// // tileGrid.setTileValueStyle(tileValueStyle)
		// // tileGrid.setShowAllRecords(false);
		// // tileGrid.setShowAllRecords(true);
		// tileGrid.setDataSource(DataSource.get("ImageDataDS"));
		// tileGrid.setAutoFetchData(true);
		// // tileGrid.setAnimateTileChange(true);
		// // tileGrid.setAlternateRecordStyles(true);

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
		photoUploadForm.setDataSource(DataSource.get("ImageDataDS"));

		// DetailViewerField pictureField = new DetailViewerField("image_data");
		// pictureField.setImageHeight(190);
		// pictureField.setImageWidth(130);
		// tileGrid.setFields(pictureField);

		fileItem = new FileItem("image_data", "სურათის არჩევა");

		// photoItem.setCanvas(tileGrid);

		ButtonItem saveButtonItem = new ButtonItem();
		saveButtonItem.setName("saveButtonItem");
		saveButtonItem.setTitle("ატვირთვა");
		saveButtonItem.setWidth(150);

		saveButtonItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				photoUploadForm.saveData(new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						setResult(response.getData() != null
								&& response.getData().length >= 1 ? response
								.getData()[0].getAttributeAsInt("tbl_image_id")
								: null);
					}
				});
			}
		});

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
		addItem(hLayout);

		photoUploadForm.editNewRecord();
		if (parentItem != null) {
			parentItem.tileGrid.invalidateCache();
		}
		setResult(photoId);
		cancItem.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {
				if (DlgStaffUploadPicture.this.parentItem != null) {
					DlgStaffUploadPicture.this.parentItem.tileGrid.invalidateCache();
				}
				destroy();

			}
		});
		
		addVisibilityChangedHandler(new VisibilityChangedHandler() {
			
			@Override
			public void onVisibilityChanged(VisibilityChangedEvent event) {
				if(!event.getIsVisible())
				{
					if (DlgStaffUploadPicture.this.parentItem != null) {
						DlgStaffUploadPicture.this.parentItem.tileGrid.invalidateCache();
					}
				}
				
			}
		});
		saveItem.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {
				choosePicture();

			}
		});
	}

	protected void choosePicture() {
		if (parentItem == null) {
			SC.say("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!!");
			return;
		}
		try {
			Object obj = photoItem.getValue();
			obj = new Integer(obj.toString());
			parentItem.setValue(obj);
			destroy();
		} catch (Exception e) {
			SC.say("გთხოვთ აირჩიოთ ფოტო!!!!");
			return;
		}

	}

	private void setResult(Object image_id) {
		photoItem.setValue(image_id);
		if (parentItem != null) {
			parentItem.tileGrid.invalidateCache();
		}
	}

}
