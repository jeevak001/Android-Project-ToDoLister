package com.example.todolister;

import java.util.Calendar;

import org.w3c.dom.ls.LSInput;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.webkit.WebView.FindListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StaticHelpers {

	public static Context context = null;
	public static ListView lv = null;
	public static Cursor cursor = null;

	public static String getDate() {

		int date, month, year;
		String combinedDate;
		Calendar c = Calendar.getInstance();

		date = c.get(Calendar.DATE);
		month = c.get((Calendar.MONTH)) + 1;
		year = c.get(Calendar.YEAR);
		combinedDate = String.valueOf(date) + "/" + String.valueOf(month) + "/"
				+ String.valueOf(year);

		return combinedDate;
	}

	public static String getPriorityCodeFromNumber(int i) {

		if (i == 1)
			return "V H";

		if (i == 2)
			return "H";

		if (i == 3)
			return "M";

		if (i == 4)
			return "L";

		if (i == 5)
			return "V L";

		return "M";

	}

	public static int getCodeForCompleted(boolean tmp) {

		if (tmp == true)
			return 1;
		else
			return 0;

	}

	public static boolean getBooleanForCompleted(int tmp) {

		if (tmp == 0)
			return false;
		else
			return true;

	}

	public static int getPositioninDatabase(String title, String description,
			Context c) {

		Log.e("inside Database Jeeva", "I came here");
		MyDatabaseHelper helper = new MyDatabaseHelper(c);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM TaskTable WHERE title = '"
				+ title + "' AND description = '" + description + "';", null);

		cursor.moveToFirst();

		int pos = cursor.getInt(cursor.getColumnIndex("_id"));

		return pos;
	}

	public static void toast(Context context, String tmp) {

		Toast t = Toast.makeText(context, tmp, Toast.LENGTH_SHORT);
		t.show();

	}

}
