package com.sonar.secrets;

import java.io.File;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.sonar.secrets.java.R;

public class FilesActivity extends BaseActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		sDir=Messages.getString("FilesActivity.0"); //$NON-NLS-1$
		aDir=Messages.getString("FilesActivity.1"); //$NON-NLS-1$
		init();
		if (values == null) {
			values = fixDir();
		}
		setListView(values);
	}

	public String[] fixDir() {
		fixDirCommon();
		dir = new File(path);
		return dir.list();
	}


}
