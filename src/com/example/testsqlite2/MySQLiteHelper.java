package com.example.testsqlite2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_STUDENTS = "students";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_STUDENT_ID = "student_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_EMAIL = "email";

	private static final String DATABASE_NAME = "students.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	// + COLUMN_NAME + " text not null "
	// + COLUMN_EMAIL + " text not null)"
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_STUDENTS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_STUDENT_ID
			+ " text not null)";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		/**
		 * how to drop table with COLUMN_COMMENT ???
		 */
		db.execSQL("DROP TABLE IF EXISTS" + COLUMN_STUDENT_ID);
		onCreate(db);
	}
}
