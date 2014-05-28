package com.sonar.secrets;

import java.io.File;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.sonar.secrets.java.R;

public class PhotosActivity extends BaseActivity {

	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		sDir="/lastPhotosDir"; 
		aDir=Messages.getString("PhotosActivity.1"); //$NON-NLS-1$
		init();
		if (values == null) {
			values = fixDir();
		}
		setListView(values);
	}

	public String[] fixDir() {
		fixDirCommon();
		if (new File(path + "/Camera").list() != null) { //$NON-NLS-1$
			path += "/Camera"; //$NON-NLS-1$
		}else
		if (new File(path + "/camera").list() != null) { //$NON-NLS-1$
			path += "/camera"; //$NON-NLS-1$
		}else
		if (new File(path + "/Pictures").list() != null) { //$NON-NLS-1$
			path += "/Pictures"; //$NON-NLS-1$
		}else
		if (new File(path + "/pictures").list() != null) { //$NON-NLS-1$
			path +="/pictures"; //$NON-NLS-1$
		}else
		if (new File(path + "/Photos").list() != null) { //$NON-NLS-1$
			path += "/Photos"; //$NON-NLS-1$
		}else
		if (new File(path + "/photos").list() != null) { //$NON-NLS-1$
			path += "/photos"; //$NON-NLS-1$
		}else
		if (new File(path + "/Pics").list() != null) { //$NON-NLS-1$
			path += "/Pics"; //$NON-NLS-1$
		}else
		if (new File(path + "/pics").list() != null) { //$NON-NLS-1$
			path += "/pics"; //$NON-NLS-1$
		}
		dir = new File(path);
		values = getValues(dir);
		return values;
	}


}
