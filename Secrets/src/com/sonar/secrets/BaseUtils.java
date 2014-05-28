package com.sonar.secrets;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.sonar.secrets.java.R;

public class BaseUtils extends Activity {

	public BaseUtils() {
		super();
	}

	protected void toast(String msg) {
		play(R.raw.ringerchanged);
		toast(msg, true);
	}

	protected void toast(String msg, boolean vibrate) {
		// textViewMsg.setText(msg);
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
		if (vibrate) {
			((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200);
		}
	}

	protected void play(int sound) {
		try {
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("BaseUtils.0"), false)) { //$NON-NLS-1$
				MediaPlayer.create(getBaseContext(), sound).start();
			}
		} catch (Exception e) {
			try {
				//((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200);
			} catch (Exception e1) {
			}
		}
	}

	protected void clearCache(final File path) {
		File f = new File(getExternalFilesDir(null) + Messages.getString("BaseUtils.1")); //$NON-NLS-1$
		rmdir(f);
		f = new File(getExternalFilesDir(null) + Messages.getString("BaseUtils.2")); //$NON-NLS-1$
		rmdir(f);
		f = new File(getExternalFilesDir(null) + Messages.getString("BaseUtils.3")); //$NON-NLS-1$
		rmdir(f);
		f = new File(getExternalFilesDir(null) + Messages.getString("BaseUtils.4")); //$NON-NLS-1$
		rmdir(f);
		f = new File(getExternalFilesDir(null) + Messages.getString("BaseUtils.5")); //$NON-NLS-1$
		rmdir(f);
		f = new File(getExternalFilesDir(null) + Messages.getString("BaseUtils.6")); //$NON-NLS-1$
		rmdir(f);
		f = new File(path + Messages.getString("BaseUtils.7")); //$NON-NLS-1$
		rmdir(f);
		f = new File(path + "/.." + Messages.getString("BaseUtils.7")); //$NON-NLS-1$
		rmdir(f);
		f = new File(getExternalFilesDir(null) + Messages.getString("BaseUtils.8")); //$NON-NLS-1$
		rmdir(f);
		scanMedia(path);
	}

	private void rmdir(File f) {
		File[] list = f.listFiles();
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					rmdir(list[i]);
				} else {
					list[i].delete();
				}
			}
			f.delete();
		}
		f.delete();
	}

	protected void scanMedia(final File path) {
		/*
		 * MediaScannerConnection.scanFile(this, new String[] { path.toString()
		 * }, null, new MediaScannerConnection.OnScanCompletedListener() {
		 * public void onScanCompleted(String path, Uri uri) {
		 * Log.i("ExternalStorage", "Scanned " + path + ":");
		 * Log.i("ExternalStorage", "-> uri=" + uri);
		 * 
		 * } });
		 */
		try {
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + path.toString())));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}