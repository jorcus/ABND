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
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kiang.pushuptracker.data.PushUpContract.PushUpEntry;
import com.example.kiang.pushuptracker.data.PushUpDbHelper;

import static com.example.kiang.pushuptracker.data.PushUpContract.PushUpEntry.TABLE_NAME;

public class CatalogActivity extends AppCompatActivity {
    private PushUpDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new PushUpDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }


    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                PushUpEntry._ID,
                PushUpEntry.COLUMN_PUSHUP_DATE,
                PushUpEntry.COLUMN_PUSHUP_TIME,
                PushUpEntry.COLUMN_PUSHUP_NAME,
                PushUpEntry.COLUMN_PUSHUP_GENDER,
                PushUpEntry.COLUMN_PUSHUP_QUANTITY};

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            displayView.setText("The push up table contains " + cursor.getCount() + " records.\n\n");
            displayView.append(PushUpEntry._ID + " - " +
                    PushUpEntry.COLUMN_PUSHUP_DATE + " - " +
                    PushUpEntry.COLUMN_PUSHUP_TIME + " - " +
                    PushUpEntry.COLUMN_PUSHUP_NAME + " - " +
                    PushUpEntry.COLUMN_PUSHUP_GENDER + " - " +
                    PushUpEntry.COLUMN_PUSHUP_QUANTITY + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PushUpEntry._ID);
            int dateColumnIndex = cursor.getColumnIndex(PushUpEntry.COLUMN_PUSHUP_DATE);
            int timeColumnIndex = cursor.getColumnIndex(PushUpEntry.COLUMN_PUSHUP_TIME);
            int nameColumnIndex = cursor.getColumnIndex(PushUpEntry.COLUMN_PUSHUP_NAME);
            int genderColumnIndex = cursor.getColumnIndex(PushUpEntry.COLUMN_PUSHUP_GENDER);
            int quantityColumnIndex = cursor.getColumnIndex(PushUpEntry.COLUMN_PUSHUP_QUANTITY);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                String currentTime = cursor.getString(timeColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentDate + " - " +
                        currentTime + " - " +
                        currentName + " - " +
                        currentGender + " - " +
                        currentQuantity));
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertPushUp() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PushUpEntry.COLUMN_PUSHUP_DATE, "21 Dec 2016");
        values.put(PushUpEntry.COLUMN_PUSHUP_TIME, "11:10AM");
        values.put(PushUpEntry.COLUMN_PUSHUP_NAME, "Jorcus");
        values.put(PushUpEntry.COLUMN_PUSHUP_GENDER, PushUpEntry.GENDER_MALE);
        values.put(PushUpEntry.COLUMN_PUSHUP_QUANTITY, 20);


        long newRowId = db.insert(TABLE_NAME, null, values);
    }

    private  void deletePushUp(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_NAME, null, null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertPushUp();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:
                deletePushUp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
