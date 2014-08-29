package com.sonar.secretspro;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sonar.secretspro.R;

@SuppressLint("NewApi")
public class Login extends Activity {
	public static String message = Messages.getString("Login.0"); //$NON-NLS-1$
	public static String lastLogin = Messages.getString("Login.1"); //$NON-NLS-1$

	private static long lockTimeout = 1;
	private EditText editText;
	private String secret = null;
	private String mSecret = null;
	private String mSecret1 = null;
	private Button buttonLogin;
	private String confirmCode = null;
	private byte[] key;
	private int attempts = 0;
	private final int noOfAttempts = 5;
	private long lastAttempt;

	private TextView textViewMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		play(R.raw.newmail);
		editText = (EditText) findViewById(R.id.code);
		buttonLogin = (Button) findViewById(R.id.buttonLogin);
		textViewMsg = (TextView) findViewById(R.id.textViewMsg);

		editText.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
					login(editText);
				}
				return false;
			}

		});

		try {
			openFileInput("key"); 
		} catch (Exception e) {
			buttonLogin.setText("Register"); 
			textViewMsg.setText("Register New One Time Secret Code !! \nDo not forget this code. It would be used to Login every time.."); 
			toast("Welcome to Secrets !!", false); 
			editText.setHint(""); 
		}
		
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("master", false)) {
			try {
				openFileInput("mkey"); //$NON-NLS-1$
			} catch (Exception e) {
				buttonLogin.setText("Enter"); //$NON-NLS-1$
				textViewMsg.setText("Enter Master Secret Code !!"); //$NON-NLS-1$
				toast("Welcome to Secrets !!", false); //$NON-NLS-1$
				editText.setHint(""); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		play(R.raw.newmail);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:
			play(R.raw.ussd);
			finish();
			return true;
		case R.id.action_help:
			help();
			return true;
		case R.id.action_about:
			help(editText);
			return true;
		case R.id.action_exit:
			play(R.raw.ussd);
			finish();
			return true;
		case R.id.action_menu_settings:
			settings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void settings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void login(View view) {
		buttonLogin = (Button) findViewById(R.id.buttonLogin);
		editText = (EditText) findViewById(R.id.code);
		textViewMsg = (TextView) findViewById(R.id.textViewMsg);
		message = editText.getText().toString();
		if (message.length() < 6) {
			toast(Messages.getString("Login.7")); //$NON-NLS-1$
			// textViewMsg.setText("Enter 6 or more digits !!");
			return;
		}

		try {
			InputStream inputStream = openFileInput(Messages.getString("Login.8")); //$NON-NLS-1$
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			int i;
			i = inputStream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
			key = Utils.getRawKey(Messages.getString("Login.9")); //$NON-NLS-1$
			secret = Utils.decrypt(byteArrayOutputStream.toByteArray(), key);
			attempts = Integer.parseInt(secret);
			attempts++;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			FileOutputStream f = openFileOutput(Messages.getString("Login.10"), MODE_PRIVATE); //$NON-NLS-1$
			key = Utils.getRawKey(Messages.getString("Login.11")); //$NON-NLS-1$
			byte[] encryptedData = Utils.encrypt(Integer.toString(attempts), key);
			f.write(encryptedData);
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (attempts > noOfAttempts) {
			try {
				InputStream inputStream = openFileInput(Messages.getString("Login.12")); //$NON-NLS-1$
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				int i;
				i = inputStream.read();
				while (i != -1) {
					byteArrayOutputStream.write(i);
					i = inputStream.read();
				}
				inputStream.close();
				key = Utils.getRawKey(Messages.getString("Login.13")); //$NON-NLS-1$
				secret = Utils.decrypt(byteArrayOutputStream.toByteArray(), key);
				lastAttempt = Long.parseLong(secret);
				long timePassed = (System.currentTimeMillis() - lastAttempt) / 1000 / 60;
				if (timePassed < lockTimeout) {
					toast(Messages.getString("Login.14") + lockTimeout + Messages.getString("Login.15"), false); //$NON-NLS-1$ //$NON-NLS-2$
					textViewMsg.setText(Messages.getString("Login.16") + lockTimeout + Messages.getString("Login.17")); //$NON-NLS-1$ //$NON-NLS-2$
					play(R.raw.lowpower);
					try {
						FileOutputStream f = openFileOutput(Messages.getString("Login.18"), MODE_PRIVATE); //$NON-NLS-1$
						key = Utils.getRawKey(Messages.getString("Login.19")); //$NON-NLS-1$
						byte[] encryptedData = Utils.encrypt(Long.toString(System.currentTimeMillis()), key);
						f.write(encryptedData);
						f.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					lockTimeout*=2;
					editText.setText(Messages.getString("Login.20")); //$NON-NLS-1$
					help();
					return;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("master", false)) {
			try {
				InputStream inputStream = openFileInput("mkey"); //$NON-NLS-1$
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				int i;
				try {
					i = inputStream.read();
					while (i != -1) {
						byteArrayOutputStream.write(i);
						i = inputStream.read();
					}
					inputStream.close();
				} catch (Exception e) {
				}
				key = Utils.getRawKey(message);
				mSecret = Utils.decrypt(byteArrayOutputStream.toByteArray(), key);
				if (mSecret != null) {
					mSecret1 = mSecret;
				}
			} catch (FileNotFoundException e) {
				if (confirmCode == null) {
					confirmCode = message;
					// toast("Confirm Master Secret Code !!", false);
					editText.setHint(Messages.getString("Login.22")); //$NON-NLS-1$
					textViewMsg.setText("Confirm Master Secret Code !!"); //$NON-NLS-1$
					buttonLogin.setText(Messages.getString("Login.24")); //$NON-NLS-1$
					editText.setText(Messages.getString("Login.25")); //$NON-NLS-1$
					return;
				} else if (!message.equals(confirmCode)) {
					confirmCode = null;
					toast("Confirm Master Code Mismatch !!"); //$NON-NLS-1$
					editText.setHint(Messages.getString("Login.27")); //$NON-NLS-1$
					textViewMsg.setText("Enter Master Secret Code !!"); //$NON-NLS-1$
					buttonLogin.setText("Register"); //$NON-NLS-1$
					editText.setText(Messages.getString("Login.30")); //$NON-NLS-1$
					return;
				} else {
					try {
						FileOutputStream f = openFileOutput("mkey", MODE_PRIVATE); //$NON-NLS-1$
						key = Utils.getRawKey(message);
						byte[] encryptedData = Utils.encrypt(message, key);
						f.write(encryptedData);
						f.close();
						toast("Enter Master Secret Code !!", false); //$NON-NLS-1$
						confirmCode = null;
						mSecret1 = message;
						editText.setHint(""); //$NON-NLS-1$
						textViewMsg.setText("Enter Secret Code !!"); //$NON-NLS-1$
						buttonLogin.setText("Enter"); //$NON-NLS-1$
						editText.setText(""); //$NON-NLS-1$
						return;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} catch (Exception e) {

			}
		}

		try {
			InputStream inputStream = openFileInput(Messages.getString("Login.37")); //$NON-NLS-1$
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			int i;
			try {
				i = inputStream.read();
				while (i != -1) {
					byteArrayOutputStream.write(i);
					i = inputStream.read();
				}
				inputStream.close();
			} catch (Exception e) {
			}
			key = Utils.getRawKey(message);
			secret = Utils.decrypt(byteArrayOutputStream.toByteArray(), key);
			try {
				if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("master", false)&&mSecret1!=null&&secret!=null) {
					FileOutputStream f = openFileOutput("mbkey", MODE_PRIVATE); //$NON-NLS-1$
					key = Utils.getRawKey(mSecret1);
					byte[] encryptedData = Utils.encrypt(message, key);
					f.write(encryptedData);
					f.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("master", false)) {
				if (mSecret != null) {
					toast("Welcome Master !! Set Secret Code.."); //$NON-NLS-1$
					textViewMsg.setText(Messages.getString("Login.39")); //$NON-NLS-1$
					editText.setHint(Messages.getString("Login.40")); //$NON-NLS-1$
					editText.setText(Messages.getString("Login.41")); //$NON-NLS-1$
					buttonLogin.setText("Register"); //$NON-NLS-1$
					return;
				}
				if (mSecret1 == null) {
					// toast("Enter Master Code..");
					textViewMsg.setText("Enter Master Code First !!"); //$NON-NLS-1$
					editText.setHint(Messages.getString("Login.44")); //$NON-NLS-1$
					editText.setText(Messages.getString("Login.45")); //$NON-NLS-1$
					buttonLogin.setText("Register"); //$NON-NLS-1$
					return;
				}
			}

			if (confirmCode == null) {
				confirmCode = message;
				// toast("Confirm Secret Code !!",false);
				editText.setHint(Messages.getString("Login.47")); //$NON-NLS-1$
				textViewMsg.setText(Messages.getString("Login.48")); //$NON-NLS-1$
				buttonLogin.setText(Messages.getString("Login.49")); //$NON-NLS-1$
				editText.setText(Messages.getString("Login.50")); //$NON-NLS-1$
				return;
			} else if (!message.equals(confirmCode)) {
				confirmCode = null;
				toast(Messages.getString("Login.51")); //$NON-NLS-1$
				textViewMsg.setText("Register New One Time Secret Code !! \nDo not forget this code. It would be used to Login every time.."); 
				editText.setHint(Messages.getString("Login.53")); //$NON-NLS-1$
				buttonLogin.setText("Register"); //$NON-NLS-1$
				editText.setText(Messages.getString("Login.55")); //$NON-NLS-1$
				return;
			} else {
				toast(Messages.getString("Login.56"), false); //$NON-NLS-1$
				editText.setHint(Messages.getString("Login.57")); //$NON-NLS-1$
				textViewMsg.setText(Messages.getString("Login.58")); //$NON-NLS-1$
				buttonLogin.setText(Messages.getString("Login.59")); //$NON-NLS-1$
				editText.setText(Messages.getString("Login.60")); //$NON-NLS-1$
			}
			secret = message;

			try {
				FileOutputStream f = openFileOutput(Messages.getString("Login.61"), MODE_PRIVATE); //$NON-NLS-1$
				key = Utils.getRawKey(message);
				byte[] encryptedData = Utils.encrypt(message, key);
				f.write(encryptedData);
				f.close();

				if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("master", false)) {
					f = openFileOutput("mbkey", MODE_PRIVATE); //$NON-NLS-1$
					key = Utils.getRawKey(mSecret1);
					encryptedData = Utils.encrypt(message, key);
					f.write(encryptedData);
					f.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!message.equals(secret)) {
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("master", false)) {
				try {
					InputStream inputStream = openFileInput("mbkey"); //$NON-NLS-1$
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					int i;
					try {
						i = inputStream.read();
						while (i != -1) {
							byteArrayOutputStream.write(i);
							i = inputStream.read();
						}
						inputStream.close();
					} catch (IOException e) {
					}
					key = Utils.getRawKey(message);
					secret = Utils.decrypt(byteArrayOutputStream.toByteArray(), key);
					if (secret != null) {
						message = secret;
						toast("Welcome Master !! Your Secret Code is :" + secret, false); //$NON-NLS-1$
						play(R.raw.smsreceived);
					}
				} catch (Exception e) {
				}
			}

			if (!message.equals(secret)) {
				if (attempts < (noOfAttempts - 1)) {
					toast(Messages.getString("Login.65") + (noOfAttempts - attempts) + Messages.getString("Login.66")); //$NON-NLS-1$ //$NON-NLS-2$
				} else if (attempts == (noOfAttempts - 1)) {
					textViewMsg.setText(Messages.getString("Login.67")); //$NON-NLS-1$
					play(R.raw.smsreceived6);
				} else {
					toast(Messages.getString("Login.68") + lockTimeout + Messages.getString("Login.69"), false); //$NON-NLS-1$ //$NON-NLS-2$
					textViewMsg.setText(Messages.getString("Login.70") + lockTimeout + Messages.getString("Login.71")); //$NON-NLS-1$ //$NON-NLS-2$
					play(R.raw.lock);
					try {
						FileOutputStream f = openFileOutput(Messages.getString("Login.72"), MODE_PRIVATE); //$NON-NLS-1$
						key = Utils.getRawKey(Messages.getString("Login.73")); //$NON-NLS-1$
						byte[] encryptedData = Utils.encrypt(Long.toString(System.currentTimeMillis()), key);
						f.write(encryptedData);
						f.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				editText.setText(Messages.getString("Login.74")); //$NON-NLS-1$
				return;
			}
		}

		try {
			FileOutputStream f = openFileOutput(Messages.getString("Login.75"), MODE_PRIVATE); //$NON-NLS-1$
			key = Utils.getRawKey(Messages.getString("Login.76")); //$NON-NLS-1$
			byte[] encryptedData = Utils.encrypt(Integer.toString(0), key);
			f.write(encryptedData);
			f.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			InputStream inputStream = openFileInput(Messages.getString("Login.77")); //$NON-NLS-1$
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			int i;
			i = inputStream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
			key = Utils.getRawKey(Messages.getString("Login.78")); //$NON-NLS-1$
			secret = Utils.decrypt(byteArrayOutputStream.toByteArray(), key);
			lastLogin = android.text.format.DateFormat.format(Messages.getString("Login.79"), new java.util.Date(Long.parseLong(secret))).toString(); //$NON-NLS-1$
		} catch (Exception e) {
			lastLogin = android.text.format.DateFormat.format(Messages.getString("Login.80"), new java.util.Date()).toString(); //$NON-NLS-1$
			e.printStackTrace();
		}

		try {
			FileOutputStream f = openFileOutput(Messages.getString("Login.81"), MODE_PRIVATE); //$NON-NLS-1$
			key = Utils.getRawKey(Messages.getString("Login.82")); //$NON-NLS-1$
			byte[] encryptedData = Utils.encrypt(Long.toString(System.currentTimeMillis()), key);
			f.write(encryptedData);
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mSecret == null) {
			play(R.raw.login);
		}
		editText.setText(Messages.getString("Login.83")); //$NON-NLS-1$
		Intent intent = new Intent(this, TabsActivity.class);
		// Intent intent = new Intent(this, Secrets.class);
		finish();
		startActivity(intent);
	}

	@Override
	public void onPause() {
		super.onPause();
		// play(R.raw.ussd);
		// finish();
	}

	private void play(int sound) {
		try {
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Messages.getString("Login.84"), false)) { //$NON-NLS-1$
				MediaPlayer.create(getBaseContext(), sound).start();
			}
		} catch (Exception e) {
			try {
				//((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200);
			} catch (Exception e1) {
			}
		}
	}

	private void toast(String msg) {
		play(R.raw.ringerchanged);
		toast(msg, true);
	}

	private void toast(String msg, boolean vibrate) {
		// textViewMsg.setText(msg);
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
		if (vibrate) {
			((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200);
		}
	}

	public void cancel(View view) {
		if (editText == null || editText.getText().length() < 1) {
			play(R.raw.ussd);
			finish();
		} else {
			editText.setText(Messages.getString("Login.85")); //$NON-NLS-1$
		}
	}

	public void help(View view) {
		help();
	}

	public void help() {
		Intent intent = new Intent(this, Help.class);
		// Intent intent = new Intent(this, TabsActivity.class);
		startActivity(intent);
	}

}
