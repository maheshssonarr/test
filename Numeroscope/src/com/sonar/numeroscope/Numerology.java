package com.sonar.numeroscope;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint({ "NewApi", "DefaultLocale" })
public class Numerology extends BaseActivity {

	private EditText editText;
	private TextView textViewMsg;
	private TextView textViewMsg1;
	private TextView textViewMsg2;
	private int sum = 0;
	private String text = "";
	private TextView textViewMsgR;
	private TextView textViewMsg1R;
	private TextView textViewMsg2R;
	public static int psychic;
	public static boolean isNumber;
	public static int destiny;
	private boolean show = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_numerology);
		play(R.raw.newmail);
		editText = (EditText) findViewById(R.id.editText1);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
			editText.setTextColor(Color.WHITE);
		}

		/*
		 * editText.setOnEditorActionListener(new OnEditorActionListener() {
		 * public boolean onEditorAction(TextView v, int actionId, KeyEvent
		 * event) { check(editText); if (isNumber) { if (text.length() != 8) {
		 * return false; } } predictions(editText); return false; } });
		 */
		editText.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				check(editText);
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
					if (isNumber) {
						if (!isDate(text) && text.length() > 5) {
							if (show) {
								toast("Birth Date of 8 digits in mmddyyyy format !!");
								show = false;
							}
							
						}
						if(!isDate(text)){
							return true;
						}
					} 
					predictions(editText);
					return true;
				}
				return false;
			}

			
		});

	}
	private boolean isDate(String text) {
		if (text.length() == 8) {
			if (Integer.parseInt(text.substring(0, 2)) < 32 && Integer.parseInt(text.substring(2, 4)) < 13 && Integer.parseInt(text.substring(4)) < 3000 && Integer.parseInt(text.substring(4)) > 1799) {
				return true;
			}
		}
		return false;
	}
	
	public void predictions(View view) {
		check(view);
		play(R.raw.login);
		Intent intent = new Intent(this, Predictions.class);
		startActivity(intent);
	}

	public void check(View view) {
		editText = (EditText) findViewById(R.id.editText1);
		textViewMsgR = (TextView) findViewById(R.id.textViewNumberR);
		textViewMsg1R = (TextView) findViewById(R.id.textViewNumber1R);
		textViewMsg2R = (TextView) findViewById(R.id.textViewNumber2R);
		textViewMsgR.setText("0");
		textViewMsg1R.setText("0");
		textViewMsg2R.setText("0");

		textViewMsg = (TextView) findViewById(R.id.textViewNumber);
		textViewMsg1 = (TextView) findViewById(R.id.textViewNumber1);
		textViewMsg2 = (TextView) findViewById(R.id.textViewNumber2);
		textViewMsg.setText("0");
		textViewMsg1.setText("0");
		textViewMsg2.setText("0");

		text = editText.getText().toString().trim().toLowerCase();
		// editText.setText(text);
		isNumber = isNumber(text);
		if (isNumber && text.length() > 1) {
			psychic = getSum(text.substring(0, 2));
		}

		calcR(text.toCharArray(), 1);
		if (sum > 9) {
			calcR(Integer.toString(sum).toCharArray(), 2);
		}
		if (sum > 9) {
			calcR(Integer.toString(sum).toCharArray(), 3);
		}

		calc(text.toCharArray(), 1);
		if (sum > 9) {
			calc(Integer.toString(sum).toCharArray(), 2);
		}
		if (sum > 9) {
			calc(Integer.toString(sum).toCharArray(), 3);
		}

	}

	private int getSum(String str) {
		do {
			sum = calc(str.toCharArray(), 0);
			str = Integer.toString(sum);
		} while (sum > 9);

		return sum;
	}

	private boolean isNumber(String text) {
		char[] arr = text.trim().toCharArray();
		for (int i = 0; i < arr.length; i++) {
			try {
				Integer.parseInt("" + arr[i]);
			} catch (Exception e) {
				return false;
			}

		}
		return true;
	}

	private int calcR(char[] text, int view) {
		sum = 0;
		for (int i = 0; i < text.length; i++) {
			switch (text[i]) {
			case '0':
				sum += 0;
				break;
			case '1':
			case 'a':
			case 'j':
			case 's':
				sum += 1;
				break;
			case '2':
			case 'b':
			case 'k':
			case 't':
				sum += 2;
				break;
			case '3':
			case 'c':
			case 'l':
			case 'u':
				sum += 3;
				break;
			case '4':
			case 'd':
			case 'm':
			case 'v':
				sum += 4;
				break;
			case '5':
			case 'e':
			case 'n':
			case 'w':
				sum += 5;
				break;
			case '6':
			case 'f':
			case 'o':
			case 'x':
				sum += 6;
				break;
			case '7':
			case 'g':
			case 'p':
			case 'y':
				sum += 7;
				break;
			case '8':
			case 'h':
			case 'q':
			case 'z':
				sum += 8;
				break;
			case '9':
			case 'i':
			case 'r':
				sum += 9;
				break;
			}
		}

		switch (view) {
		case 1:
			textViewMsgR.setText(Integer.toString(sum));
			break;
		case 2:
			textViewMsg1R.setText(Integer.toString(sum));
			break;
		case 3:
			textViewMsg2R.setText(Integer.toString(sum));
		}
		return sum;
	}

	private int calc(char[] text, int view) {
		sum = 0;
		for (int i = 0; i < text.length; i++) {
			switch (text[i]) {
			case '0':
				sum += 0;
				break;
			case '1':
			case 'a':
			case 'i':
			case 'j':
			case 'q':
			case 'y':
				sum += 1;
				break;
			case '2':
			case 'b':
			case 'k':
			case 'r':
				sum += 2;
				break;
			case '3':
			case 'c':
			case 'g':
			case 'l':
			case 's':
				sum += 3;
				break;
			case '4':
			case 'd':
			case 'm':
			case 't':
				sum += 4;
				break;
			case '5':
			case 'e':
			case 'h':
			case 'n':
			case 'x':
				sum += 5;
				break;
			case '6':
			case 'u':
			case 'v':
			case 'w':
				sum += 6;
				break;
			case '7':
			case 'o':
			case 'z':
				sum += 7;
				break;
			case '8':
			case 'f':
			case 'p':
				sum += 8;
				break;
			case '9':
				sum += 9;
				break;
			}
		}

		switch (view) {
		case 1:
			textViewMsg.setText(Integer.toString(sum));
			break;
		case 2:
			textViewMsg1.setText(Integer.toString(sum));
			break;
		case 3:
			textViewMsg2.setText(Integer.toString(sum));
		}
		destiny = sum;
		return sum;
	}

}
