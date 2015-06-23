package com.futureinst.utils;

import android.app.Activity;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ToastUtils {
	public static void showToast(Activity activity, String message, int time,Style style) {
		Crouton crouton = Crouton.makeText(activity, message, style);
		Configuration configuration = new Configuration.Builder().setDuration(
				time).build();
		crouton.setConfiguration(configuration).show();
	}
}
