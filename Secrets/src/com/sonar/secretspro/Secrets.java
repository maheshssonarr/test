package com.sonar.secretspro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sonar.secretspro.R;

public class Secrets extends BaseUtils {

	private EditText editText;
	byte[] key;
	private String message;
	private ListView listViewFiles;
	private ArrayList<String> filesList = new ArrayList<String>();
	private String fileName;
	private Button buttonQuit;
	private File file;
	private File dir;
	private Button buttonSave;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secrets);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			// getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		message = Login.message;
		dir = getDir("secrets", 0);
		TextView textLastLogin = (TextView) findViewById(R.id.textView1);
		textLastLogin.setText(Messages.getString("Secrets.1") + Login.lastLogin); //$NON-NLS-1$
		loadFilesList();
		saveSharedText();
		setListView();

	}

	private void saveSharedText() {
		fileName="sharedText";
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		InputStream inputStream;
		int i;
		try {
			inputStream = new FileInputStream(dir + Messages.getString("Secrets.10") + fileName); //$NON-NLS-1$
			i = inputStream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
		} catch (Exception e) {
			filesList.remove(fileName);
			saveFilesList();
		}
		String secrets;
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("Secrets.11"), false)) { //$NON-NLS-1$
			byte[] data1 = byteArrayOutputStream.toByteArray();
			String datastr = "";
			for (int j = 0; j < data1.length; j++) {
				datastr += data1[j] + ",";
			}
			secrets = datastr.substring(0, datastr.lastIndexOf(","));
			// secrets = new String(byteArrayOutputStream.toByteArray());
			Button buttonSave = (Button) findViewById(R.id.button_save);
			buttonSave.setVisibility(View.GONE);
		} else {
			key = Utils.getRawKey(message);
			secrets = Utils.decrypt(byteArrayOutputStream.toByteArray(), key);
		}
		if(secrets==null){
			secrets="";
		}
		String text = "";
		String sharedText = secrets;
		File file = new File(dir + "/sharedText1");
		try {
			BufferedReader f = new BufferedReader(new FileReader(file));
			sharedText+="\n";
			text = f.readLine();
			while (text != null) {
				sharedText += "\n"+text;
				text = f.readLine();
			}
			f.close();
		} catch (Exception e) {
		}
		file.delete();
		file = new File(dir + "/sharedText");
		if (!filesList.contains(file.getName())) {
			filesList.add(file.getName());
			saveFilesList();
		}
		try {
			FileOutputStream f = new FileOutputStream(file);
			key = Utils.getRawKey(message);
			text = sharedText;
			byte[] data = null;
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("save_encrypted", true)) {
				data = Utils.encrypt(text, key);
			} else {
				try {
					String[] data1 = text.split(",");
					byteArrayOutputStream = new ByteArrayOutputStream();
					int j;
					for (i = 0; i < data1.length; i++) {
						j = Integer.parseInt(data1[i]);
						byteArrayOutputStream.write(j);
					}
					data = byteArrayOutputStream.toByteArray();
				} catch (Exception e) {

				}
			}
			f.write(data);
			f.close();
			
		} catch (Exception e) {
			play(R.raw.lowpower);

		}
	}

	@SuppressLint("NewApi")
	protected void loadFilesList() {
		String line = Messages.getString("Secrets.2"); //$NON-NLS-1$
		try {
			BufferedReader f = new BufferedReader(new FileReader(getFilesDir() + Messages.getString("Secrets.3"))); //$NON-NLS-1$
			line = f.readLine();
			while (line != null) {
				filesList.add(line);
				line = f.readLine();
			}
			f.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		filesList.add(0, Messages.getString("Secrets.4")); //$NON-NLS-1$
	}

	@SuppressLint("NewApi")
	protected void saveFilesList() {
		try {
			BufferedWriter f = new BufferedWriter(new FileWriter(getFilesDir() + Messages.getString("Secrets.5"))); //$NON-NLS-1$
			for (int i = 1; i < filesList.size(); i++) {
				f.write(filesList.get(i) + '\n');
			}
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setListView() {
		listViewFiles = (ListView) findViewById(R.id.listViewFiles);
		listViewFiles.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filesList.toArray(new String[filesList.size()])));
		listViewFiles.setItemsCanFocus(false);
		listViewFiles.setClickable(false);
		listViewFiles.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
		listViewFiles.setVisibility(0);
		editText = (EditText) findViewById(R.id.textSecret);
		buttonQuit = (Button) findViewById(R.id.button_quit);
		if (filesList.size() > 1) {
			buttonQuit.setText(Messages.getString("Secrets.6")); //$NON-NLS-1$
		}
		buttonSave = (Button) findViewById(R.id.button_save);
		buttonSave.setVisibility(8);
		editText.setVisibility(8);

		listViewFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Object o = listViewFiles.getItemAtPosition(position);
				String fileName = (String) o;
				if (fileName.equals(Messages.getString("Secrets.7"))) { //$NON-NLS-1$
					getFileName();
				} else {
					showFile(fileName);
				}
				buttonQuit.setText(Messages.getString("Secrets.8")); //$NON-NLS-1$
				buttonSave.setText(Messages.getString("Secrets.9")); //$NON-NLS-1$
			}

		});
	}

	private void showFile(String fileName) {
		String secrets;
		editText = (EditText) findViewById(R.id.textSecret);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		InputStream inputStream;
		int i;
		try {
			inputStream = new FileInputStream(dir + Messages.getString("Secrets.10") + fileName); //$NON-NLS-1$
			i = inputStream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
		} catch (Exception e) {
			filesList.remove(fileName);
			saveFilesList();
			e.printStackTrace();
			return;
		}
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("Secrets.11"), false)) { //$NON-NLS-1$
			byte[] data1 = byteArrayOutputStream.toByteArray();
			String datastr = "";
			for (int j = 0; j < data1.length; j++) {
				datastr += data1[j] + ",";
			}
			secrets = datastr.substring(0, datastr.lastIndexOf(","));
			// secrets = new String(byteArrayOutputStream.toByteArray());
			Button buttonSave = (Button) findViewById(R.id.button_save);
			buttonSave.setVisibility(View.GONE);
		} else {
			key = Utils.getRawKey(message);
			secrets = Utils.decrypt(byteArrayOutputStream.toByteArray(), key);
		}
		listViewFiles.setVisibility(8);
		editText.setVisibility(0);
		editText.setText(secrets);
		this.fileName = fileName;
		buttonSave.setVisibility(0);

	}

	private void getFileName() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(Messages.getString("Secrets.12")); //$NON-NLS-1$
		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton(Messages.getString("Secrets.13"), new DialogInterface.OnClickListener() { //$NON-NLS-1$

					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						Editable value = input.getText();
						fileName = value.toString();
						listViewFiles.setVisibility(8);
						editText.setText(Messages.getString("Secrets.14")); //$NON-NLS-1$
						editText.setVisibility(0);
						buttonSave.setVisibility(0);
					}
				});

		alert.setNegativeButton(Messages.getString("Secrets.15"), new DialogInterface.OnClickListener() { //$NON-NLS-1$
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
		;

	}

	public void save(View view) {
		if (buttonSave.getText().equals(Messages.getString("Secrets.16"))) { //$NON-NLS-1$
			setListView();
			buttonSave.setText(Messages.getString("Secrets.17")); //$NON-NLS-1$
			buttonSave.setVisibility(8);

			return;
		}
		String text = Messages.getString("Secrets.18"); //$NON-NLS-1$
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("save_encrypted", true)) {
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("Secrets.19"), false)) { //$NON-NLS-1$
				Toast.makeText(getBaseContext(), Messages.getString("Secrets.20"), Toast.LENGTH_LONG).show(); //$NON-NLS-1$
				return;
			}
		}
		File file = new File(dir + Messages.getString("Secrets.21") + fileName); //$NON-NLS-1$
		if (!file.exists()) {
			filesList.add(fileName);
			saveFilesList();
			play(R.raw.login);
		}
		try {
			FileOutputStream f = new FileOutputStream(file);
			key = Utils.getRawKey(message);
			text = editText.getText().toString();
			byte[] data = null;
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("save_encrypted", true)) {
				data = Utils.encrypt(text, key);
			} else {
				try {
					String[] data1 = text.split(",");
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					int j;
					for (int i = 0; i < data1.length; i++) {
						j = Integer.parseInt(data1[i]);
						byteArrayOutputStream.write(j);
					}
					data = byteArrayOutputStream.toByteArray();
				} catch (Exception e) {

				}
				// data=text.getBytes("UTF8");

			}
			f.write(data);
			f.close();
			toast(Messages.getString("Secrets.22"), false); //$NON-NLS-1$
			play(R.raw.beepbeep);
		} catch (Exception e) {
			play(R.raw.lowpower);

		}
	}

	public void quit(View view) {
		if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("Secrets.23"), false)) { //$NON-NLS-1$
			play(R.raw.ussd);
		}
		if (buttonQuit.getText().equals(Messages.getString("Secrets.24"))) { //$NON-NLS-1$
			finish();
		} else if (buttonQuit.getText().equals(Messages.getString("Secrets.25"))) { //$NON-NLS-1$
			int count = listViewFiles.getAdapter().getCount();
			for (int i = 0; i < count; i++) {
				if (listViewFiles.isItemChecked(i)) {
					fileName = (String) listViewFiles.getItemAtPosition(i);
					if (fileName.equals(Messages.getString("Secrets.26"))) { //$NON-NLS-1$
						setListView();
						return;
					}
					file = new File(dir + Messages.getString("Secrets.27") + fileName); //$NON-NLS-1$
					if (file.exists()) {
						file.delete();
						toast(Messages.getString("Secrets.28") + fileName, false); //$NON-NLS-1$
						play(R.raw.lowpower);
						filesList.remove(fileName);
						saveFilesList();
						setListView();
						buttonQuit.setText(Messages.getString("Secrets.29")); //$NON-NLS-1$
						return;
					}
				}
			}
			listViewFiles.setOnItemClickListener(null);
			listViewFiles.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, filesList.toArray(new String[filesList.size()])));
			listViewFiles.setItemsCanFocus(false);
			listViewFiles.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
			toast(Messages.getString("Secrets.30")); //$NON-NLS-1$
			buttonSave.setVisibility(0);
			buttonSave.setText(Messages.getString("Secrets.31")); //$NON-NLS-1$
		} else if (buttonQuit.getText().equals(Messages.getString("Secrets.32"))) { //$NON-NLS-1$
			setListView();
		} else {
			buttonQuit.setText(Messages.getString("Secrets.33")); //$NON-NLS-1$
			setListView();
			editText.setVisibility(8);
			listViewFiles.setVisibility(0);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_secrets, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("Secrets.34"), false)) { //$NON-NLS-1$
			save(editText);
		}
		// play(R.raw.ussd);
		// finish();
	}

}
