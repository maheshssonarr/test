package com.sonar.secrets;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore.MediaColumns;
import android.view.View;

import com.sonar.secrets.java.R;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class Share extends BaseActivity {

	private static final String HANDLE_MSG = Messages.getString("Share.0"); //$NON-NLS-1$

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		Intent intent = getIntent();
		@SuppressWarnings("unused")
		String action = intent.getAction();
		String type = intent.getType();

		if (type != null) {
			if (type.contains("image/")) { //$NON-NLS-1$
				aDir = "photos"; //$NON-NLS-1$
			} else if (type.contains("video/")) { //$NON-NLS-1$
				aDir = "videos"; //$NON-NLS-1$
			} else if (type.contains("mpeg") || type.contains("audio")) { //$NON-NLS-1$
				aDir = "songs"; //$NON-NLS-1$
			} else {
				aDir = "files"; //$NON-NLS-1$
			}

		}

		init();
		progressBar.setVisibility(View.VISIBLE);
		handle(intent);
		finish();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void handle(Intent intent) {
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("Share.8"), false)) { //$NON-NLS-1$
			file = getExternalFilesDir(null);
		} else {
			file = getDir(Messages.getString("Share.9"), 0); //$NON-NLS-1$
		}
		file = new File(file + Messages.getString("Share.10") + aDir); //$NON-NLS-1$
		file.mkdirs();
		if (file.list() != null) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				Utils.mv(list[i], dirEn);
			}
		}
		try {
			Uri fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
			ArrayList<Uri> fileUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
			String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
			if (sharedText != null) {
				try {
					FileWriter f = new FileWriter(getDir("secrets", 0) + "/sharedText1", true);
					f.write(sharedText + Messages.getString("Utils.7")); //$NON-NLS-1$
					f.close();
					play(R.raw.soda);
					toast("Added Shared text to Secrets !!", false);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//toast("Encrypting Selected Files in Background... Ignore thumbnails..", false);
			if (fileUris == null) {
				fileUris = new ArrayList<Uri>();
				fileUris.add(fileUri);
			}
			for (int i = 0; i < fileUris.size(); i++) {
				try {
					file = new File(getRealPathFromURI(fileUris.get(i)));
				} catch (Exception e) {
					file = new File(fileUris.get(i).toString().replace("file://", ""));
				}
				encrypted = new File(dirEn + Messages.getString("Share.13") + file.getName() + BaseActivity.encryptedExt); //$NON-NLS-1$ //$NON-NLS-2$
				progressBar.setVisibility(View.VISIBLE);
				new Thread(new Runnable() {
					public void run() {
						if (Utils.mv(file, encrypted)) {
							play(R.raw.soda);
						}
						mHandler.post(new Runnable() {
							public void run() {
								progressBar.setVisibility(View.INVISIBLE);
							}
						});

					}

				}).start();
			}
			clearCache(file.getParentFile());
		} catch (Error e) {
			toast(HANDLE_MSG);
		} catch (Exception e) {
			toast(HANDLE_MSG);
		}
	}

	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaColumns.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
