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
package com.jorcus.myapplication;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jorcus.myapplication.data.InventoryContract.InventoryEntry;

import static android.content.ContentValues.TAG;

public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view,final Context context, Cursor cursor) {
        Button product_order = (Button)view.findViewById(R.id.product_order);
        product_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "kiang.ng@hotmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT,"Order");
                email.setType("message/rfc822");
                context.startActivity(Intent.createChooser(email, "Select a email client"));

            }
        });


        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView supplierTextView = (TextView) view.findViewById(R.id.supplier);
        TextView quantityTextView = (TextView) view.findViewById(R.id.set_inventory);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);

        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_NAME);
        int supplierColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_SUPPLIER);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_PRICE);


        final String inventoryName = cursor.getString(nameColumnIndex);
        String inventorySupplier = cursor.getString(supplierColumnIndex);
        final String inventoryQuantity = cursor.getString(quantityColumnIndex);
        String inventoryEachPrice = cursor.getString(priceColumnIndex);

        if (TextUtils.isEmpty(inventorySupplier)) {
            inventorySupplier = context.getString(R.string.unknown_supplier);
        }
        nameTextView.setText(inventoryName);
        supplierTextView.setText(inventorySupplier);
        quantityTextView.setText(inventoryQuantity);
        priceTextView.setText(inventoryEachPrice);


        int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
        int inventoryID = cursor.getInt(idColumnIndex);
        final Uri InventoryUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, inventoryID);


        new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "", null));
        final int quantity = Integer.parseInt(inventoryQuantity);
        Button product_add_btn = (Button) view.findViewById(R.id.product_sold);
        product_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, inventoryName + " quantity= " + quantity);
                ContentResolver resolver = view.getContext().getContentResolver();
                ContentValues values = new ContentValues();


                if (quantity > 0) {
                    int qq = quantity;
                    Log.d(TAG, "new quantity= " + qq);
                    values.put(InventoryEntry.COLUMN_INVENTORY_QUANTITY, --qq);
                    resolver.update(
                            InventoryUri,
                            values,
                            null,
                            null
                    );
                    context.getContentResolver().notifyChange(InventoryUri, null);
                } else {
                    Toast.makeText(context, "Item out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
