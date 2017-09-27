package com.example.todolister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends Activity {

	MyDatabaseHelper helper = null;
	SQLiteDatabase db = null;
	StaticHelpers sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		MyDatabaseHelper helper = new MyDatabaseHelper(this);
		db = helper.getWritableDatabase();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getMenuInflater().inflate(R.menu.help_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.about) {
			new AlertDialog.Builder(this)
					.setTitle("About")
					.setMessage("ToDo Lister Version 1.0.\n\nMail us at :\n"

					+ "\t\tjeeva.nasa01@gmail.com")
					.setNeutralButton("Cool",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface d, int s) {

								}
							}).show();

		}

		return super.onOptionsItemSelected(item);
	}

}
