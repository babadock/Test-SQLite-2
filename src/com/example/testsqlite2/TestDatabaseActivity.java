package com.example.testsqlite2;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class TestDatabaseActivity extends ListActivity {
	private StudentsDataSource datasource;

	private EditText editName;
	private EditText editStudentId;
	private EditText editEmail;

	private Student student;
	private String name;
	private String student_id;
	private String email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		editName = (EditText) findViewById(R.id.student_name);
		editStudentId = (EditText) findViewById(R.id.student_id);
		editEmail = (EditText) findViewById(R.id.student_email);

		datasource = new StudentsDataSource(this);
		datasource.open();

		// use the SimpleCursorAdapter to show the
		// elements in a ListView
		List<Student> values = datasource.getAllStudents();
		ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Student> adapter = (ArrayAdapter<Student>) getListAdapter();
		Student student = null;
		switch (view.getId()) {
		case R.id.insert:
			/*
			 * String[] Students = new String[] { "51104279", "51104204",
			 * "51104240" }; int nextInt = new Random().nextInt(3);
			 */
			// save the new Student to the database
			name = editName.getText().toString();
			student_id = editStudentId.getText().toString();
			email = editEmail.getText().toString();
			student = datasource.createStudent(name, student_id, email);
			adapter.add(student);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				student = (Student) getListAdapter().getItem(0);
				datasource.deleteStudent(student);
				adapter.remove(student);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		datasource.close();
		super.onPause();
	}
}
