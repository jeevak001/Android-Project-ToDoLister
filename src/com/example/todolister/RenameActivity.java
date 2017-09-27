package com.example.todolister;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RenameActivity extends Activity implements OnItemSelectedListener,
		OnClickListener {
	Spinner s = null;
	StaticHelpers sh;
	EditText titleEditText = null;
	EditText descriptionEditText = null;
	CheckBox completedC = null;
	MyDatabaseHelper helper = null;
	SQLiteDatabase db = null;
	String[] projection = { "date", "title", "description", "priority" };

	String date = "", title = "", description = "";
	int priority = 1;
	int completed = 0;
	int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rename_item);

		s = (Spinner) findViewById(R.id.priority_dropdown);
		s.setOnItemSelectedListener(this);
		MyPriorityAdapter p = new MyPriorityAdapter();

		s.setAdapter(p);
		titleEditText = (EditText) findViewById(R.id.titleEditText);
		descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
		helper = new MyDatabaseHelper(this);
		db = helper.getWritableDatabase();

		Intent i = getIntent();
		title = i.getStringExtra("title");
		description = i.getStringExtra("description");
		priority = Integer.parseInt(i.getStringExtra("priority"));
		id = Integer.parseInt(i.getStringExtra("id"));
		titleEditText.setText(title);
		descriptionEditText.setText(description);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}

	public void goHomeMethod(View v) {

		finish();

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int pos, long id) {
		if (pos == 0) {
			priority = 1;
			Toast.makeText(this, "Priority set to Very High",
					Toast.LENGTH_SHORT).show();
		}
		if (pos == 1) {
			priority = 2;
			Toast.makeText(this, "Priority set to High", Toast.LENGTH_SHORT)
					.show();
		}
		if (pos == 2) {
			priority = 3;
			Toast.makeText(this, "Priority set to Medium", Toast.LENGTH_SHORT)
					.show();
		}
		if (pos == 3) {
			priority = 4;
			Toast.makeText(this, "Priority set to Low", Toast.LENGTH_SHORT)
					.show();
		}
		if (pos == 4) {
			priority = 5;
			Toast.makeText(this, "Priority set to Very Low", Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	public void renameMethod(View v) {

		db.delete("TaskTable", "_id = " + id + ";", null);
		ContentValues cv = new ContentValues();

		cv.put("date", sh.getDate());
		cv.put("title", titleEditText.getText().toString());
		cv.put("description", descriptionEditText.getText().toString());
		cv.put("priority", priority);
		cv.put("completed", completed);

		db.insert("TaskTable", null, cv);
		Toast.makeText(this, "Task Edited and Updated", Toast.LENGTH_SHORT).show();

		finish();

	}

}
