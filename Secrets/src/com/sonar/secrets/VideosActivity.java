package com.sonar.secrets;

import java.io.File;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.sonar.secrets.java.R;

public class VideosActivity extends BaseActivity {

	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		sDir = Messages.getString("VideosActivity.0"); //$NON-NLS-1$
		aDir = Messages.getString("VideosActivity.1"); //$NON-NLS-1$
		init();
		
		if (values == null) {
			values = fixDir();
		}
		setListView(values);
	}

	public String[] fixDir() {
		fixDirCommon();
		if (new File(path + Messages.getString("VideosActivity.2")).list() != null) { //$NON-NLS-1$
			path += Messages.getString("VideosActivity.3"); //$NON-NLS-1$
		}
		if (new File(path + Messages.getString("VideosActivity.4")).list() != null) { //$NON-NLS-1$
			path += Messages.getString("VideosActivity.5"); //$NON-NLS-1$
		}
		if (new File(path + Messages.getString("VideosActivity.6")).list() != null) { //$NON-NLS-1$
			path += Messages.getString("VideosActivity.7"); //$NON-NLS-1$
		}
		if (new File(path + Messages.getString("VideosActivity.8")).list() != null) { //$NON-NLS-1$
			path += Messages.getString("VideosActivity.9"); //$NON-NLS-1$
		}
		if (new File(path + Messages.getString("VideosActivity.10")).list() != null) { //$NON-NLS-1$
			path += Messages.getString("VideosActivity.11"); //$NON-NLS-1$
		}
		if (new File(path + Messages.getString("VideosActivity.12")).list() != null) { //$NON-NLS-1$
			path += Messages.getString("VideosActivity.13"); //$NON-NLS-1$
		}
		if (new File(path + Messages.getString("VideosActivity.14")).list() != null) { //$NON-NLS-1$
			path += Messages.getString("VideosActivity.15"); //$NON-NLS-1$
		}
		if (new File(path + Messages.getString("VideosActivity.16")).list() != null) { //$NON-NLS-1$
			path += Messages.getString("VideosActivity.17"); //$NON-NLS-1$
		}
		dir = new File(path);
		return dir.list();
	}

}
