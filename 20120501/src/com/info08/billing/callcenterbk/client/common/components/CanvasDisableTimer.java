package com.info08.billing.callcenterbk.client.common.components;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.widgets.Canvas;

public class CanvasDisableTimer {

	public static void addCanvasClickTimer(final Canvas canvas) {
		int delay = 3000;
		addCanvasClickTimer(canvas, delay);
	}

	public static void addCanvasClickTimer(final Canvas canvas, int delay) {
		canvas.setDisabled(true);
		new Timer() {
			public void run() {
				canvas.setDisabled(false);
				this.cancel();
			}
		}.schedule(delay);
	}
}
