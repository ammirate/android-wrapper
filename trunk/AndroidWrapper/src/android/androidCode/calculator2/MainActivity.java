package android.androidCode.calculator2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	public String eingabe = "";
	int counter_operationen = 0;
	int operator = 0;
	int zahl_end = 0;
	String ergebnis;
	boolean gleich_pressed = false;
	String[][] zahlen = new String[25][3];
	char[][] operatoren = new char[20][2];
	int level = 0;
	int x;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void ChangeTextView(String add) // Aktualisiert das TextView
	{
		TextView text = (TextView) findViewById(R.id.eingabefeld);

		if (text.getText() == "") {
			eingabe = add;
		} else {
			eingabe = eingabe + add;
		}
		text.setText(eingabe);
	}

	public void setZahl(String zahl, String trig) {
		if (zahlen[operator][0] == null) {
			zahlen[operator][0] = zahl;
			zahlen[operator][1] = String.valueOf(level);
		} else {
			zahlen[operator][0] = zahlen[operator][0] + "" + zahl;
		}
		zahlen[operator][1] = String.valueOf(level);
		zahlen[operator][2] = trig;
	}

	public void setOperator(char operatorZeichen) {
		if (operatorZeichen != 's' && operatorZeichen != 'c') {
			operatoren[operator][0] = operatorZeichen;
		} else if (operatorZeichen == 's') {
			operatoren[operator - 1][1] = 's';
		} else if (operatorZeichen == 'c') {
			operatoren[operator - 1][1] = 'c';
		}
	}

	public void cleanCalc() {
		TextView text = (TextView) findViewById(R.id.eingabefeld);
		if (gleich_pressed == false) {
			for (int ii = 0; ii < 2; ii++) {
				for (int i = 0; i < 20; i++) {
					zahlen[i][ii] = null;
				}
			}
		} else {
			for (int ii = 0; ii < 2; ii++) {
				for (int i = 0; i < 20; i++) {
					zahlen[i][1] = null;
				}
			}
			zahlen[0][0] = ergebnis;
			zahlen[0][1] = String.valueOf(0);
			eingabe = ergebnis;

			operator = 0;
			level = 0;
			counter_operationen = 0;
			zahl_end = 0;

			text.setText(ergebnis);
		}
	}

	public void countOperationen() {
		TextView text = (TextView) findViewById(R.id.eingabefeld);

		for (int i = 0; i < text.length(); i++) {
			if (String.valueOf(text.getText()).charAt(i) == '(') {
				counter_operationen++;
			}
		}
	}

	public void ClickButton_gleich(View view) {
		TextView text = (TextView) findViewById(R.id.eingabefeld);
		countOperationen();
		if (String.valueOf(text.getText()) != "") {

			for (int i = 0; zahl_end != 1; i++) {
				zahl_end = 0;
				if (operatoren[i + 1][0] == '*' || operatoren[i + 1][0] == '/') {
					if (zahlen[i + 1][1] == zahlen[i + 2][1]
							&& zahlen[i + 2][1] != zahlen[i + 3][1]) {
						i++;
					}
				}
				if (zahlen[i][1] == zahlen[i + 1][1]) {
					zahlen = this.Calculate(zahlen, i, 1);

					for (int y = i + 1; zahlen[y - 1][0] != null; y++) {
						zahlen[y][0] = zahlen[y + 1][0];
						zahlen[y][1] = zahlen[y + 1][1];
						zahlen[y + 1][0] = null;
						zahlen[y + 1][1] = null;
						operatoren[y - 1] = operatoren[y];
					}
				}
				for (int y = 0; y < zahlen.length; y++) {
					if (zahlen[y][0] != null) {
						zahl_end++;
					}
				}
				if (zahlen[i + 1][0] == null && zahl_end > 1
						|| zahlen[i + 1][0] == null && zahlen[i + 2][0] == null && zahl_end > 1) {
					i = -1;
				}
			}
			gleich_pressed = true;
			text.setText(getErgebnis(zahlen));
			ergebnis = getErgebnis(zahlen);
			cleanCalc();
		}
	}

	public String getErgebnis(String[][] zahlen) {
		String ergebnis = "Uuuups";
		for (int i = 0; i < zahlen.length; i++) {
			if (zahlen[i][0] != null) {
				ergebnis = zahlen[i][0];
			}
		}
		return ergebnis;
	}

	public String[][] Calculate(String[][] zahlen, int i, int ii) {
		if (zahlen[i][2] != "sinus" && zahlen[i][2] != "cosinus") {
			if (operatoren[i][0] == '+') {
				zahlen[i][0] = String.valueOf(Double.parseDouble(zahlen[i][0])
						+ Double.parseDouble(zahlen[i + ii][0]));
			} else if (operatoren[i][0] == '-') {
				zahlen[i][0] = String.valueOf(Double.parseDouble(zahlen[i][0])
						- Double.parseDouble(zahlen[i + ii][0]));
			} else if (operatoren[i][0] == '*') {
				zahlen[i][0] = String.valueOf(Double.parseDouble(zahlen[i][0])
						* Double.parseDouble(zahlen[i + ii][0]));
			} else if (operatoren[i][0] == '/') {
				zahlen[i][0] = String.valueOf(Double.parseDouble(zahlen[i][0])
						/ Double.parseDouble(zahlen[i + ii][0]));
			}
		} else if (zahlen[i][2] == "sinus") {
			zahlen[i][0] = String.valueOf(Math.sin(Double
					.parseDouble(zahlen[i][0])));
			zahlen[i][2] = null;
			Calculate(zahlen, i, ii);
		} else if (zahlen[i][2] == "cosinus") {
			zahlen[i][0] = String.valueOf(Math.cos((Double
					.parseDouble(zahlen[i][0])/180)*Math.PI));
			zahlen[i][2] = null;
			Calculate(zahlen, i, ii);
		}
		zahlen[i + ii][0] = null;
		zahlen[i + ii][1] = null;

		if (zahlen[i][1] != null) {
			if (Integer.parseInt(zahlen[i][1]) > 0) {
				zahlen[i][1] = String
						.valueOf(Integer.parseInt(zahlen[i][1]) - 1);
			}
		}
		return zahlen;
	}

	public void ClickButton_clean(View view) {
		TextView text = (TextView) findViewById(R.id.eingabefeld);
		text.setText("");
		gleich_pressed = false;
		cleanCalc();
	}

	public void ClickButton_back(View view) {
		TextView text = (TextView) findViewById(R.id.eingabefeld);
		if (String.valueOf(text.getText()) != null) {

			if (eingabe.charAt(eingabe.length() - 1) == '(') {
				level--;
			} else if (eingabe.charAt(eingabe.length() - 1) == ')') {
				level++;
			} else if (eingabe.charAt(eingabe.length() - 1) == '+'
					|| eingabe.charAt(eingabe.length() - 1) == '-'
					|| eingabe.charAt(eingabe.length() - 1) == '*'
					|| eingabe.charAt(eingabe.length() - 1) == '/') {
				for (int i = 0; i < operatoren.length; i++) {
					if (operatoren[i + 1][0] != '+'
							|| operatoren[i + 1][0] != '-'
							|| operatoren[i + 1][0] != '*'
							|| operatoren[i + 1][0] != '/') {
						operator--;
					}
				}
			} else {
				zahlen[operator][0] = String.valueOf(zahlen[operator][0]
						.substring(0, zahlen[operator][0].length() - 1));
			}
			eingabe = String
					.valueOf(eingabe.substring(0, eingabe.length() - 1));
			text.setText(eingabe);
		}
	}

	public void ClickButton_sinus(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl("", "sinus");
		level++;
	}
	

	public void ClickButton_cosinus(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl("", "cosinus");
		level++;	}

	public void ClickButton_mal(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setOperator(buttonText.charAt(0));
		operator++;
	}

	public void ClickButton_0(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_1(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_2(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_3(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_4(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_5(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_6(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_7(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_8(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_9(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_punkt(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(buttonText, null);
	}

	public void ClickButton_minus(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setOperator(buttonText.charAt(0));
		operator++;
	}

	public void ClickButton_geteilt(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setOperator(buttonText.charAt(0));
		operator++;
	}

	public void ClickButton_plus(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setOperator(buttonText.charAt(0));
		operator++;
	}

	public void ClickButton_klammerauf(View view) {
		level = level + 1;
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		if (operatoren[operator][0] != '+' || operatoren[operator][0] != '-'
				|| operatoren[operator][0] != '*'
				|| operatoren[operator][0] != '/') {
			operatoren[operator][0] = '*';
		}
		ChangeTextView(buttonText);
	}

	public void ClickButton_klammerzu(View view) {
		level = level - 1;
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
	}

	public void ClickButton_pi(View view) {
		Button b = (Button) view;
		String buttonText = b.getText().toString();
		ChangeTextView(buttonText);
		setZahl(String.valueOf(3.14159265359), null);
	}

}
