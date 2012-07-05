package com.info08.billing.callcenterbk.client.common.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class ImageViewerItem extends CanvasItem {

	public TileGrid tileGrid;

	public ImageViewerItem() {
		super();
		setProperties();
	}

	public ImageViewerItem(JavaScriptObject jsObj) {
		super(jsObj);
		setProperties();
	}

	public ImageViewerItem(String name) {
		super(name);
		setProperties();
	}

	public ImageViewerItem(String name, String title) {
		super(name, title);
		setProperties();
	}

	private void setProperties() {
		this.setWidth(140);
		this.setHeight(70);
		this.setRowSpan(5);

		tileGrid = new TileGrid();
		tileGrid.setWidth(150);
		tileGrid.setHeight(180);
		tileGrid.setTileHeight(158);
		tileGrid.setTileWidth(127);
		tileGrid.setTileMargin(0);
		tileGrid.setTileValueAlign("center");
		tileGrid.setDataSource(DataSource.get("ImageDataDS"));
		tileGrid.setAutoFetchData(false);

		DetailViewerField pictureField = new DetailViewerField("image_data");
		pictureField.setImageHeight(190);
		pictureField.setImageWidth(130);

		tileGrid.setFields(pictureField);

		setCanvas(tileGrid);
		fetchImage(null);
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		try {
			fetchImage(new Integer(value.toString()));
		} catch (Exception e) {
			fetchImage(null);
		}
	}

	private void fetchImage(Integer tbl_image_id) {
		if (tbl_image_id == null)
			tbl_image_id = -100000;
		Criteria criteria = new Criteria();
		criteria.setAttribute("tbl_image_id", tbl_image_id);
		tileGrid.invalidateCache();
		tileGrid.fetchData(criteria);
	}

	public void setChoosable() {

		
		tileGrid.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				Integer tmp = null;
				try {
					tmp = new Integer(getValue().toString());
				} catch (Exception e) {
					// TODO: handle exception
				}
				final Integer tbl_image_id = tmp;
				DlgStaffUploadPicture dlgStaffUploadPicture = new DlgStaffUploadPicture(
						tbl_image_id, ImageViewerItem.this);
				dlgStaffUploadPicture.show();
			}
		});
	}

}
