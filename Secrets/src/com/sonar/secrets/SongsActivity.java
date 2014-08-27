package com.sonar.secrets;

import java.io.File;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.sonar.secrets.java.R;

public class SongsActivity extends BaseActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		sDir=Messages.getString("SongsActivity.0"); //$NON-NLS-1$
		aDir=Messages.getString("SongsActivity.1"); //$NON-NLS-1$
		init();
		if (values == null) {
			values = fixDir();
		}
		setListView(values);

	}
	public String[] fixDir() {
		fixDirCommon();
		if(new File(path+Messages.getString("SongsActivity.2")).list()!=null){ //$NON-NLS-1$
			path+=Messages.getString("SongsActivity.3"); //$NON-NLS-1$
		}
		if(new File(path+Messages.getString("SongsActivity.4")).list()!=null){ //$NON-NLS-1$
			path+=Messages.getString("SongsActivity.5"); //$NON-NLS-1$
		}
		if(new File(path+Messages.getString("SongsActivity.6")).list()!=null){ //$NON-NLS-1$
			path+=Messages.getString("SongsActivity.7"); //$NON-NLS-1$
		}
		if(new File(path+Messages.getString("SongsActivity.8")).list()!=null){ //$NON-NLS-1$
			path+=Messages.getString("SongsActivity.9"); //$NON-NLS-1$
		}
		if(new File(path+Messages.getString("SongsActivity.10")).list()!=null){ //$NON-NLS-1$
			path+=Messages.getString("SongsActivity.11"); //$NON-NLS-1$
		}
		if(new File(path+Messages.getString("SongsActivity.12")).list()!=null){ //$NON-NLS-1$
			path+=Messages.getString("SongsActivity.13"); //$NON-NLS-1$
		}
		if(new File(path+Messages.getString("SongsActivity.14")).list()!=null){ //$NON-NLS-1$
			path+=Messages.getString("SongsActivity.15"); //$NON-NLS-1$
		}
		if(new File(path+Messages.getString("SongsActivity.16")).list()!=null){ //$NON-NLS-1$
			path+=Messages.getString("SongsActivity.17"); //$NON-NLS-1$
		}
		dir = new File(path);
		return dir.list();
	}



}
