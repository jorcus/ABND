/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.kiang.pushuptracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kiang.pushuptracker.data.PushUpContract.PushUpEntry;
import com.example.kiang.pushuptracker.data.PushUpDbHelper;


public class EditorActivity extends AppCompatActivity {

    private EditText mDateEditText;
    private EditText mTimeEditText;
    private EditText mNameEditText;
    private Spinner mGenderSpinner;
    private EditText mQuantityEditText;
    private int mGender = PushUpEntry.GENDER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mDateEditText = (EditText) findViewById(R.id.edit_pushup_date);
        mTimeEditText = (EditText) findViewById(R.id.edit_pushup_time);
        mNameEditText = (EditText) findViewById(R.id.edit_pushup_name);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        mQuantityEditText = (EditText) findViewById(R.id.edit_pushup_quantity);


        setupSpinner();
    }


    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PushUpEntry.GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PushUpEntry.GENDER_FEMALE;
                    } else {
                        mGender = PushUpEntry.GENDER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = PushUpEntry.GENDER_UNKNOWN;
            }
        });
    }

    private void insertPushUp() {
        String dateString = mDateEditText.getText().toString().trim();
        String timeString = mTimeEditText.getText().toString().trim();
        String nameString = mNameEditText.getText().toString().trim();
        String qualityString = mQuantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt(qualityString);

        PushUpDbHelper mDbHelper = new PushUpDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PushUpEntry.COLUMN_PUSHUP_DATE, dateString);
        values.put(PushUpEntry.COLUMN_PUSHUP_TIME, timeString);
        values.put(PushUpEntry.COLUMN_PUSHUP_NAME, nameString);
        values.put(PushUpEntry.COLUMN_PUSHUP_GENDER, mGender);
        values.put(PushUpEntry.COLUMN_PUSHUP_QUANTITY, quantity);

        long newRowId = db.insert(PushUpEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving push up record", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Push Up record saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertPushUp();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}