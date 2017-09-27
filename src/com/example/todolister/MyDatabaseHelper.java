package com.example.todolister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "TaskDatabase.db";
	public static final int DATABASE_VERSION = 3;

	public MyDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE TaskTable ( _id INTEGER PRIMARY KEY , date TEXT,title TEXT,description TEXT,priority INTEGER ,completed INTEGER);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS TaskTable;");
		onCreate(db);
	}

}
