package com.sonar.numeroscope;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class BaseActivity extends Activity {

	public BaseActivity() {
		super();
	}

	protected void toast(String msg) {
		play(R.raw.ringerchanged);
		toast(msg, true);
	}

	protected void toast(String msg, boolean vibrate) {
		try {
			Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
			if (vibrate) {
				((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200);
			}
		} catch (Exception e) {}
	}

	protected void play(int sound) {
		try {
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("enable_sounds", true)) { //$NON-NLS-1$
				MediaPlayer.create(getBaseContext(), sound).start();
			}
		} catch (Exception e) {
			try {
				((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200);
			} catch (Exception e1) {
			}
		}
	}

}