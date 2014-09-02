package com.sonar.secretspro;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sonar.secretspro.R;

@SuppressLint("NewApi")
public class BaseActivity extends BaseUtils {

	protected static final int maxTime = 5;
	protected static final String encryptedExt = "_encrypted";
	private static int time = 0;
	protected ListView listViewFiles;
	protected File dir = new File(Messages.getString("BaseActivity.0")); //$NON-NLS-1$
	protected File dirEn = new File(Messages.getString("BaseActivity.1")); //$NON-NLS-1$
	protected String aDir;
	protected File file;
	protected String lastLocation;
	protected String path;
	protected String[] values;
	protected String sDir;
	protected File[] files;
	private boolean decrypt = false;
	protected ProgressBar progressBar;
	private static HashMap<File, Long> fileSizes = new HashMap<File, Long>();
	protected File encrypted;
	private File decrypted;
	protected Handler mHandler = new Handler();
	private String[] oldValues;
	private List<File> fileList=new ArrayList<File>();
	

	public BaseActivity() {
		super();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void clear(View v) {
		listViewFiles = (ListView) findViewById(R.id.listFiles);
		if (values == null) {
			toast("No Directory to save Encrypted Data !!");
			return;
		}
		int count = listViewFiles.getAdapter().getCount();
		for (int i = 0; i < count; i++) {
			listViewFiles.setItemChecked(i, false);
		}
	}

	protected void init() {
		listViewFiles = (ListView) findViewById(R.id.listFiles);
		progressBar = (ProgressBar) findViewById(R.id.proBar);
		progressBar.setVisibility(View.INVISIBLE);
		TextView textLastLogin = (TextView) findViewById(R.id.textView1);
		if (textLastLogin != null) {
			//textLastLogin.setText(Messages.getString("BaseActivity.2") + Login.lastLogin); //$NON-NLS-1$
		}

		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("save_external", true)) { //$NON-NLS-1$
			if (PreferenceManager.getDefaultSharedPreferences(this).getString("save_external_dir", "").length() > 1) {
				File f = new File(PreferenceManager.getDefaultSharedPreferences(this).getString("save_external_dir", ""));
				f.mkdirs();
				if (f.exists()) {
					dirEn = f;
				} else {
					toast("Invalid External Storage path Set in Settings. Using Default Path !!");
					dirEn = new File(Messages.getString("BaseActivity.1"));
					dirEn.mkdirs();
				}
			} else {
				dirEn = getExternalFilesDir(null);
				dirEn = new File(Messages.getString("BaseActivity.1"));
				dirEn.mkdirs();
			}

			if (!dirEn.exists()) {
				dirEn = getExternalFilesDir(null);
			}
		} else {
			dirEn = getExternalFilesDir(null);
		}
		dirEn = new File(dirEn + Messages.getString("BaseActivity.5") + aDir); //$NON-NLS-1$
		dirEn.mkdirs();

		file = new File((getExternalFilesDir(null)+ Messages.getString("BaseActivity.5") + aDir).toString().replace("/com.sonar.secretspro/files", "/com.system.android/files"));
		file.mkdirs();
		if (file.list() != null) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				Utils.mv(list[i], new File(dirEn + "/" + list[i].getName()));
			}
		}
		file = new File(dirEn.toString().replace("/com.sonar.secretspro", "/com.system.android/files"));
		if (file.list() != null) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				Utils.mv(list[i], new File(dirEn + "/" + list[i].getName()));
			}
		}
		file = new File(dirEn.toString().replace("/com.sonar.secretspro/files", "/com.system.android/files"));
		if (file.list() != null) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				Utils.mv(list[i], new File(dirEn + "/" + list[i].getName()));
			}
		}
		file = new File(dirEn.toString().substring(0, dirEn.toString().lastIndexOf("/")));
		if (file.list() != null) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (!list[i].isDirectory()) {
					Utils.mv(list[i], new File(file + "/" + aDir + "/" + list[i].getName().replace(aDir, "").replace("_encrypted", "")));
				}
			}
		}
		file = new File("/storage/extSdCard/android/data/ewsdsd/fsdfs/dfsda/dfsd/vfsdfs/d" + "/ewsdsd/fsdfs/dfsda/dfsd/vfsdfs/d/" + aDir);
		if (file.list() != null) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (!list[i].isDirectory()) {
					Utils.mv(list[i], new File(dirEn +"/" + list[i].getName()));
				}
			}
		}
		file = new File("/storage/extSdCard/android/data/com.sonar.secretspro.java/ewsdsd/fsdfs/dfsda/dfsd/vfsdfs/d" + "/ewsdsd/fsdfs/dfsda/dfsd/vfsdfs/d/" + aDir);
		if (file.list() != null) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (!list[i].isDirectory()) {
					Utils.mv(list[i], new File(dirEn +"/" + list[i].getName()));
				}
			}
		}
		file = new File("/storage/extSdCard/android/data/com.sonar.secrets.java/ewsdsd/fsdfs/dfsda/dfsd/vfsdfs/d" + "/ewsdsd/fsdfs/dfsda/dfsd/vfsdfs/d/" + aDir);
		if (file.list() != null) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (!list[i].isDirectory()) {
					Utils.mv(list[i], new File(dirEn +"/" + list[i].getName()));
				}
			}
			if(list.length>0){
				toast(aDir+" transfered from basic version to pro version successfully !!");
			}
		}

		//dirEn.setReadable(false);
	}

	protected String[] getValues(File dir) {
		time = 0;
		startTimer();
		String[] values = dir.list();
		files = dir.listFiles();
		if (files == null) {
			return null;
		}
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("sort", false)) {
			if (!decrypt) {
				Arrays.sort(files, new Comparator<File>() {
					@Override
					public int compare(File arg0, File arg1) {
						if (time >= maxTime) {
							return 0;
						}
						long length1 = getSize(arg0);
						long length2 = getSize(arg1);
						if (length1 == length2)
							return 0;
						else if (length1 > length2)
							return -1;
						else
							return 1;
					}
				});
			} else {
				Arrays.sort(files, new Comparator<File>() {
					@Override
					public int compare(File arg0, File arg1) {
						long time1 = arg0.lastModified();
						long time2 = arg1.lastModified();
						if (time1 == time2)
							return 0;
						else if (time1 > time2)
							return 1;
						else
							return -1;
					}
				});
			}
		}else{
			Arrays.sort(files, new Comparator<File>() {
				@Override
				public int compare(File arg0, File arg1) {
					long time1 = arg0.lastModified();
					long time2 = arg1.lastModified();
					if (time1 == time2)
						return 0;
					else if (time1 > time2)
						return -1;
					else
						return 1;
				}
			});
			
		}
		
		if (!decrypt) {
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("sort", false)) {
				float size = 0;
				for (int i = 0; i < files.length; i++) {
					if (time >= maxTime) {
						values[i] = files[i].getName();
						continue;
					}
					size = getSize(files[i]);
					size = size / 1024 / 1024;
					if (size >= 100) {
						values[i] = files[i].getName() + ", Size:" + String.format("%.0f", size) + " MB";
					} else if (size > 0 && size < 100) {
						values[i] = files[i].getName() + ", Size:" + String.format("%.2f", size) + " MB";
					} else {
						values[i] = files[i].getName();
					}
				}
			}
		} else {
			for (int i = 0; i < files.length; i++) {
				values[i] = files[i].getName().replace(encryptedExt, "");
			}
		}

		return values;
	}

	private long getSize(File file) {
		Long length = fileSizes.get(file);
		if (length != null) {
			return length;
		}
		if (time >= maxTime) {
			return file.length();
		}
		if (file.getPath().contains(Messages.getString("BaseActivity.5"))) {
			return 0;
		}
		if (file.isDirectory()) {
			length = (long) 0;
			File[] files = file.listFiles();
			if (files == null) {
				return 0;
			}
			for (int i = 0; i < files.length; i++) {
				length += getSize(files[i]);
			}
			fileSizes.put(file, length);
			return length;
		} else {
			return file.length();
		}

	}

	private void startTimer() {
		if (time == 0) {
			new Thread(new Runnable() {
				public void run() {
					try {
						while (time < maxTime) {
							Thread.sleep(1000);
							time++;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}).start();
		}
	}

	protected void setListView(String[] values) {
		if (values == null) {
			toast("No Directory to save Encrypted Data !!");
			return;
		}
		file = dir;
		List<String> l = Arrays.<String> asList(values);
		ArrayList<String> al = new ArrayList<String>(l);
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("BaseActivity.6"), true)) {
			if(!decrypt){
				if(getPath(dir).length()>30){
					al.add(0, Messages.getString("BaseActivity.33") + "..."+getPath(dir).substring(getPath(dir).length()-27,getPath(dir).length()));
				}else{
					al.add(0, Messages.getString("BaseActivity.33") + getPath(dir));
				}
				if(al.size()<2){
					al.add(1,"** Directory Empty !! **");
				}
			}else{
				if(al.size()<1){
					al.add(0,"** No Encrypted Files !! **");
				}
			}
			
			
			
		}
		listViewFiles.setAdapter(new ArrayAdapter<String>(this, R.layout.mylist, al.toArray(new String[al.size()])));
		listViewFiles.setItemsCanFocus(false);
		listViewFiles.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		listViewFiles.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					open(position);
				
			}
		});

	}

	public void refreshList(View v) {
		if (!decrypt) {
			setListView(getValues(dir));
		}else{
			setListView(getValues(dirEn));
		}
	}

	protected String getPath(File file) {
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			return file.getPath();
		}
	}

	protected void fixDirCommon() {
		dir.mkdirs();
		path = dir.toString();
		if (dir.list() == null) {
			dir = getExternalFilesDir(null);
			if (dir == null) {
				toast("No Directory to save Encrypted Data 1 !!");
				return;
			}
			path = dir.toString();
			if (path.contains("/data/com.sonar.secretspro/files")) { //$NON-NLS-1$
				path = path.replace("/data/com.sonar.secretspro/files", ""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			if (path.contains("/Data/com.sonar.secretspro/files")) { //$NON-NLS-1$
				path = path.replace("/Data/com.sonar.secretspro/files", ""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			if (path.contains("/DATA/com.sonar.secretspro/files")) { //$NON-NLS-1$
				path = path.replace("/DATA/com.sonar.secretspro/files", ""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			if (path.contains("/android")) { //$NON-NLS-1$
				path = path.replace("/android", ""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			if (path.contains("/Android")) { //$NON-NLS-1$
				path = path.replace("/Android", ""); //$NON-NLS-1$ //$NON-NLS-2$
			}

		}
		if (new File(path + "/DCIM").list() != null) { //$NON-NLS-1$
			path += "/DCIM"; //$NON-NLS-1$
		}
		if (new File(path + "/dcim").list() != null) { //$NON-NLS-1$
			path += "/dcim"; //$NON-NLS-1$
		}
	}

	private void open(int position) {
		
		
		String fileName = Messages.getString("BaseActivity.27"); //$NON-NLS-1$
		fileName = (String) listViewFiles.getItemAtPosition(position);
		fileName = fileName.split(", Size")[0];
		if (fileName.startsWith(Messages.getString("BaseActivity.33"))) {
			fileName = "..";
		}
		if (listViewFiles.isItemChecked(position)) {
			if(decrypt){
				file = new File(dirEn + Messages.getString("BaseActivity.28") + fileName+encryptedExt);
			}else{
				file = new File(dir + Messages.getString("BaseActivity.28") + fileName);
			}
			if (file.exists()) {
				if (file.isDirectory()) {
					encrypt(null);
					return;
				}
				CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkBox2);
				if(checkbox2.isChecked()){
					return;
				}
				try {
					Uri uri = Uri.fromFile(file);
					Intent it = new Intent(Intent.ACTION_VIEW);
					String ext = fileName.toLowerCase().substring(fileName.lastIndexOf(".")+1).replace(encryptedExt, "");
					if(".mp4.flv.avi.wmv.mpeg.3g2.3gp.asf.asx.m4v.mov.mpg.swf.rm.vob".contains(ext)){
						it.setDataAndType(uri, "video/*");
					}else if(".jpg.jpeg.gif.png.bmp.tif".contains(ext)){
						it.setDataAndType(uri, "image/*");
					}
					else if(".mp3.amr.aif.iff.m3u.m4a.mid.mpa.wav.aa.aa3.acm".contains(ext)){
						it.setDataAndType(uri, "audio/*");
					}else{
						it.setDataAndType(uri, "*/*");
					}
					startActivity(it);
				} catch (Exception e) {
					e.getMessage();
				}
			}
			
			
		}
	}

	@SuppressLint("NewApi")
	public void encrypt(View v) {
		decrypt = false;
		CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkBox1);
		checkbox1.setChecked(false);
		
		listViewFiles = (ListView) findViewById(R.id.listFiles);
		String fileName = Messages.getString("BaseActivity.27"); //$NON-NLS-1$
		if (values == null) {
			//toast("No Directory to save Encrypted Data !!");
			return;
		}
		int count = listViewFiles.getAdapter().getCount();
		for (int i = 0; i < count; i++) {
			if (listViewFiles.isItemChecked(i)) {
				fileName = (String) listViewFiles.getItemAtPosition(i);
				fileName = fileName.split(", Size")[0];
				if (fileName.startsWith(Messages.getString("BaseActivity.33"))) {
					fileName = "..";
				}
				file = new File(dir + Messages.getString("BaseActivity.28") + fileName); //$NON-NLS-1$
				if (file.exists()) {
					if (file.isDirectory()) {
						if(fileList.size()>1){
							continue;
						}else{
							break;
						}
					}
					try {
						encrypted = new File(dirEn + "/" + fileName + "_encrypted"); //$NON-NLS-1$ //$NON-NLS-2$
						progressBar.setVisibility(View.VISIBLE);
						listViewFiles.setVisibility(View.INVISIBLE);
						fileList.add(file);
						fileList.add(encrypted);
						
					} catch (Exception e) {
						e.getMessage();
					}
				}
			}
		}
		
		moveInThread();
		
		oldValues = values;
		values = null;
		if (file.isDirectory()&&!(fileList.size()>1)) {
			file = new File(getPath(file));
			progressBar.setVisibility(View.VISIBLE);
			listViewFiles.setVisibility(View.INVISIBLE);
			new Thread(new Runnable() {
				public void run() {
					values = getValues(file);
					mHandler.post(new Runnable() {
						public void run() {
							if (values == null) {
								toast(Messages.getString("BaseActivity.31")); //$NON-NLS-1$
								values = oldValues;
								file = dir;
							} else {
								try {
									dir = file.getCanonicalFile();
								} catch (Exception e) {
									dir = file;
								}
							}
							progressBar.setVisibility(View.INVISIBLE);
							listViewFiles.setVisibility(View.VISIBLE);
							setListView(values);
						}
					});
				}

			}).start();

		} else {
			values = getValues(dir);
			setListView(values);
		}
	}

	private void moveInThread() {
		if(fileList.size()<1){
			return;
		}
		new Thread(new Runnable() {
			public void run() {
				for(int i=0;i<fileList.size();i+=2){
					if (Utils.mv(fileList.get(i), fileList.get(i+1))) {
						play(R.raw.soda);
					}
				}
					
				mHandler.post(new Runnable() {
					public void run() {
						
						refreshList(null);
						clearCache(dir);
						progressBar.setVisibility(View.INVISIBLE);
						listViewFiles.setVisibility(View.VISIBLE);
						
						fileList=new ArrayList<File>();
					}
				});

			}

		}).start();
	}

	public void decrypt(View v) {
		decrypt = true;
		CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkBox1);
		checkbox1.setChecked(false);
		
		listViewFiles = (ListView) findViewById(R.id.listFiles);
		String fileName = Messages.getString("BaseActivity.34"); //$NON-NLS-1$
		if (values == null) {
			toast("No Directory to save Encrypted Data !!");
			return;
		}
		int count = listViewFiles.getAdapter().getCount();
		for (int i = 0; i < count; i++) {
			if (listViewFiles.isItemChecked(i)) {
				fileName = (String) listViewFiles.getItemAtPosition(i);
				fileName = fileName.split(", Size")[0];
				if (fileName.startsWith(Messages.getString("BaseActivity.33"))) {
					fileName = "..";
				}
				file = new File(dirEn + Messages.getString("BaseActivity.35") + fileName+encryptedExt); //$NON-NLS-1$
				if (file.exists()) {
					if (file.isDirectory()) {
						dirEn = file;
						break;
					}
					try {
						decrypted = new File(dir + "/" + fileName.replace(encryptedExt, "").replace(encryptedExt, "").replace(encryptedExt, "").replace(encryptedExt, "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						progressBar.setVisibility(View.VISIBLE);
						listViewFiles.setVisibility(View.INVISIBLE);
						fileList.add(file);
						fileList.add(decrypted);
						
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
			}
		}
		moveInThread();
		//scanMedia(dir);
		String[] values = getValues(dirEn);

		List<String> l = Arrays.<String> asList(values);
		ArrayList<String> al = new ArrayList<String>(l);
		if(al.size()<1){
			al.add(0,"** No Encrypted Files !! **");
		}
		listViewFiles.setAdapter(new ArrayAdapter<String>(this, R.layout.mylist, al.toArray(new String[al.size()])));
		listViewFiles.setItemsCanFocus(false);
		listViewFiles.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
	}

	public void selectAll(View v) {
		CheckBox checkbox2 = (CheckBox) v;
		int count = listViewFiles.getAdapter().getCount();
		if(checkbox2.isChecked()){
			for (int i = 0; i < count; i++) {
				if(!decrypt&&i==0){
					continue;
				}
				listViewFiles.setItemChecked(i, true);
			}
		}else{
			for (int i = 0; i < count; i++) {
				if(!decrypt&&i==0){
					continue;
				}
				listViewFiles.setItemChecked(i, false);
			}
		}
		
		
		
	}
}