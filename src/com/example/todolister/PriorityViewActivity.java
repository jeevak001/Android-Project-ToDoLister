package com.example.todolister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class PriorityViewActivity extends Activity {

	MyDatabaseHelper helper = null;
	SQLiteDatabase db = null;
	int RENAME_ITEM_REQUEST_CODEE = 6;
	Cursor c = null;
	StaticHelpers sh;
	String rTitle, rDesc;
	int rPriority;
	ListView listView = null;
	TasksCustomAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_priority_view);

		helper = new MyDatabaseHelper(this);
		db = helper.getReadableDatabase();
		c = getDetailsByPriority();

		adapter = new TasksCustomAdapter(this, c);
		listView = (ListView) findViewById(R.id.priorityActivityList);
		listView.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getMenuInflater().inflate(R.menu.priority_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.refreshh) {

			redraw();

			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	public Cursor getDetailsByPriority() {

		Cursor c;
		c = db.rawQuery(
				"SELECT * FROM TaskTable WHERE completed = 0 ORDER BY priority;",
				null);
		return c;
	}

	public void redraw() {

		helper = new MyDatabaseHelper(this);
		db = helper.getReadableDatabase();
		c = getDetailsByPriority();

		adapter = new TasksCustomAdapter(this, c);
		listView = (ListView) findViewById(R.id.priorityActivityList);
		listView.setAdapter(adapter);
	}

	public class TasksCustomAdapter extends CursorAdapter {

		MyDatabaseHelper helper = null;
		SQLiteDatabase db = null;

		StaticHelpers sh;
		public String date = "", title = "", description = "";
		public int priority = 1;
		public Cursor cursor;
		TextView dateT = null;
		Context mcontext = null;
		TextView titleT = null;
		int pos;
		int pos1;
		TextView priorityT = null;
		TextView descriptionT = null;
		CheckBox completedC = null;
		String vh = "#f44336", h = "#ff9800", m = "#fbc02d", l = "#8bc34a",
				vl = "#388e3c";

		public TasksCustomAdapter(Context context, Cursor c) {

			super(context, c);
			mcontext = context;
			cursor = c;
			this.notifyDataSetChanged();

		}

		@Override
		public void bindView(final View view, final Context context,
				final Cursor c) {

			final int pos1 = c.getPosition();
			dateT = (TextView) view.findViewById(R.id.date);
			dateT.setText(c.getString(c.getColumnIndex("date")));
			titleT = (TextView) view.findViewById(R.id.title);
			titleT.setText(c.getString(c.getColumnIndex("title")));
			priorityT = (TextView) view.findViewById(R.id.priority);
			priorityT.setText((sh.getPriorityCodeFromNumber(Integer.parseInt(c
					.getString(c.getColumnIndex("priority"))))));
			descriptionT = (TextView) view.findViewById(R.id.description);
			descriptionT.setText(c.getString(c.getColumnIndex("description")));
			completedC = (CheckBox) view.findViewById(R.id.completed);
			completedC.setChecked(sh.getBooleanForCompleted(c.getInt(c
					.getColumnIndex("completed"))));

			if ((Integer.parseInt(c.getString(c.getColumnIndex("priority")))) == 1) {
				priorityT.setTextColor(Color.parseColor(vh));
				titleT.setBackgroundColor(Color.parseColor(vh));
			}
			if ((Integer.parseInt(c.getString(c.getColumnIndex("priority")))) == 2) {
				priorityT.setTextColor(Color.parseColor(h));
				titleT.setBackgroundColor(Color.parseColor(h));
			}
			if ((Integer.parseInt(c.getString(c.getColumnIndex("priority")))) == 3) {
				priorityT.setTextColor(Color.parseColor(m));
				titleT.setBackgroundColor(Color.parseColor(m));
			}
			if ((Integer.parseInt(c.getString(c.getColumnIndex("priority")))) == 4) {
				priorityT.setTextColor(Color.parseColor(l));
				titleT.setBackgroundColor(Color.parseColor(l));
			}
			if ((Integer.parseInt(c.getString(c.getColumnIndex("priority")))) == 5) {
				priorityT.setTextColor(Color.parseColor(vl));
				titleT.setBackgroundColor(Color.parseColor(vl));
			}

			helper = new MyDatabaseHelper(context);
			db = helper.getWritableDatabase();
			completedC
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {

							c.moveToPosition(pos1);

							Cursor d = db.rawQuery(
									"SELECT _id FROM TaskTable WHERE title = '"
											+ c.getString(
													c.getColumnIndex("title"))
													.trim()
											+ "' AND description = '"
											+ c.getString(
													c.getColumnIndex("description"))
													.trim() + "';", null);
							d.moveToFirst();
							final int pos = d.getInt(d.getColumnIndex("_id"));

							if (isChecked == true) {

								new AlertDialog.Builder(context)
										.setTitle("Finished Really")
										.setMessage(
												"Confirm to complete the task !")
										.setPositiveButton(
												"Yes",
												new DialogInterface.OnClickListener() {

													public void onClick(
															DialogInterface d,
															int s) {
														sh.toast(context,
																"Task updated as completed ! \n \t\t\tRefresh to update");
														ContentValues cv = new ContentValues();
														cv.put("completed", 1);
														db.update("Tasktable",
																cv, "_id ="
																		+ pos,
																null);
														view.setVisibility(View.GONE);
														redraw();

													}
												})
										.setOnKeyListener(new OnKeyListener() {
											@Override
											public boolean onKey(
													DialogInterface dialog,
													int keyCode, KeyEvent event) {
												// TODO Auto-generated method
												// stub
												if (keyCode == KeyEvent.KEYCODE_BACK) {

													dialog.dismiss();
													redraw();
													return true;
												}
												return false;
											}
										}).show();

							}
							if (isChecked == false) {

								new AlertDialog.Builder(context)
										.setTitle("Finished Really")
										.setMessage("Really add it up again !")

										.setNeutralButton(
												"Yes",
												new DialogInterface.OnClickListener() {

													public void onClick(
															DialogInterface d,
															int s) {
														sh.toast(context,
																"Task added up again to List !\n \t\t\tRefresh to update");
														ContentValues cv = new ContentValues();
														cv.put("completed", 0);
														db.update("Tasktable",
																cv, "_id ="
																		+ pos,
																null);
														view.setVisibility(View.GONE);
														redraw();

													}
												})

										.setOnKeyListener(new OnKeyListener() {
											@Override
											public boolean onKey(
													DialogInterface dialog,
													int keyCode, KeyEvent event) {
												// TODO Auto-generated method
												// stub
												if (keyCode == KeyEvent.KEYCODE_BACK) {

													dialog.dismiss();
													redraw();
													return true;
												}
												return false;
											}
										}).show();
							}

						}
					});

			final ViewGroup vg = (ViewGroup) view.findViewById(R.id.mainLayout);

			vg.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View arg0) {

					TextView odescription = (TextView) vg
							.findViewById(R.id.description);
					CheckBox c = (CheckBox) vg.findViewById(R.id.completed);

					odescription
							.setBackgroundResource(R.drawable.description_white);
					c.setBackgroundResource(R.drawable.checkbox_white);
					return false;

				}
			});

		}

		@Override
		public View newView(Context context, Cursor c, ViewGroup parent) {

			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View view = inflater.inflate(R.layout.main_list, parent, false);

			return view;

		}

	}

}
