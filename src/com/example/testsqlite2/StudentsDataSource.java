package com.example.testsqlite2;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StudentsDataSource {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	// , MySQLiteHelper.COLUMN_NAME,
	// MySQLiteHelper.COLUMN_EMAIL

	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_STUDENT_ID };

	public StudentsDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * 
	 * @param student
	 * @return
	 */
	public Student createStudent(String student) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_STUDENT_ID, student);
		long insertId = database.insert(MySQLiteHelper.TABLE_STUDENTS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_STUDENTS,
				allColumns, MySQLiteHelper.COLUMN_ID + "=" + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Student newStudent = cursorToStudent(cursor);
		return newStudent;
	}

	public void deleteStudent(Student student) {
		long id = student.getId();
		System.out.println("Student deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_STUDENTS, MySQLiteHelper.COLUMN_ID
				+ "=" + id, null);
	}

	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_STUDENTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Student student = cursorToStudent(cursor);
			students.add(student);
			cursor.moveToNext();
		}
		cursor.close();
		return students;
	}

	private Student cursorToStudent(Cursor cursor) {
		Student student = new Student();
		/**
		 * 1. why it return null 47,... when it has a bug ???
		 */
		student.setId(cursor.getLong(0));
		student.setStudentid(cursor.getString(1));
		return student;
	}

}
