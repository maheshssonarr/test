package com.sonar.secrets;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.sonar.secrets.java.R;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class TabsActivity extends TabActivity {

	public static String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_layout);
		// Show the Up button in the action bar.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		TabHost tabHost = getTabHost();

		// Tab for Secrets
		TabSpec secretspec = tabHost.newTabSpec(Messages.getString("TabsActivity.0")); //$NON-NLS-1$
		secretspec.setIndicator(Messages.getString("TabsActivity.1"), getResources().getDrawable(R.drawable.ic_tab_text)); //$NON-NLS-1$
		Intent secretsIntent = new Intent(this, Secrets.class);
		secretspec.setContent(secretsIntent);
		tabHost.addTab(secretspec); // Adding secrets tab

		// Adding all TabSpec to TabHost
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("TabsActivity.2"), true)) { //$NON-NLS-1$
			// Tab for Photos
			TabSpec photospec = tabHost.newTabSpec(Messages.getString("TabsActivity.3")); //$NON-NLS-1$
			// setting Title and Icon for the Tab
			photospec.setIndicator(Messages.getString("TabsActivity.4"), getResources().getDrawable(R.drawable.ic_tab_images)); //$NON-NLS-1$
			Intent photosIntent = new Intent(this, PhotosActivity.class);
			photospec.setContent(photosIntent);
			tabHost.addTab(photospec); // Adding photos tab
		}

		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("TabsActivity.5"), true)) { //$NON-NLS-1$
			// Tab for Songs
			TabSpec songspec = tabHost.newTabSpec(Messages.getString("TabsActivity.6")); //$NON-NLS-1$
			songspec.setIndicator(Messages.getString("TabsActivity.7"), getResources().getDrawable(R.drawable.ic_tab_songs)); //$NON-NLS-1$
			Intent songsIntent = new Intent(this, SongsActivity.class);
			songspec.setContent(songsIntent);
			tabHost.addTab(songspec); // Adding songs tab
		}

		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("TabsActivity.8"), true)) { //$NON-NLS-1$
			// Tab for Videos
			TabSpec videospec = tabHost.newTabSpec(Messages.getString("TabsActivity.9")); //$NON-NLS-1$
			videospec.setIndicator(Messages.getString("TabsActivity.10"), getResources().getDrawable(R.drawable.ic_tab_video)); //$NON-NLS-1$
			Intent videosIntent = new Intent(this, VideosActivity.class);
			videospec.setContent(videosIntent);
			tabHost.addTab(videospec); // Adding videos tab
		}

		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("TabsActivity.11"), true)) { //$NON-NLS-1$
			// Tab for Files
			TabSpec filespec = tabHost.newTabSpec(Messages.getString("TabsActivity.12")); //$NON-NLS-1$
			// setting Title and Icon for the Tab
			filespec.setIndicator(Messages.getString("TabsActivity.13"), getResources().getDrawable(R.drawable.ic_tab_files)); //$NON-NLS-1$
			Intent filesIntent = new Intent(this, FilesActivity.class);
			filespec.setContent(filesIntent);
			tabHost.addTab(filespec); // Adding Files tab
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		//finish();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tab_layout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		//case android.R.id.:
			
		}
		return super.onOptionsItemSelected(item);
	}

}
