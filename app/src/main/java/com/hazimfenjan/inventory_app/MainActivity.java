package com.hazimfenjan.inventory_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;
import com.hazimfenjan.inventory_app.data.InventoryContract.InventoryEntry;

import com.hazimfenjan.inventory_app.data.InventoryDbHelper;

public class MainActivity extends AppCompatActivity {

    // helper to access db
    private InventoryDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, editorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new InventoryDbHelper(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PRODUCT_PRICE,
                InventoryEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                InventoryEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER
        };
        Cursor cursor = db.query(
                InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_product);

        try {
            displayView.setText("Inventory contains : " + cursor.getCount() + " products.\n\n");

            displayView.append(
                    InventoryEntry._ID + " - " +
                            InventoryEntry.COLUMN_PRODUCT_NAME + " - " +
                            InventoryEntry.COLUMN_PRODUCT_PRICE + " - " +
                            InventoryEntry.COLUMN_PRODUCT_QUANTITY + " - " +
                            InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " - " +
                            InventoryEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER + "\n");
            int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER);
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName =cursor.getString(nameColumnIndex);
                int currentPrice =cursor.getInt(priceColumnIndex);
                int currentQuantity =cursor.getInt(quantityColumnIndex);
                int currentSupplierName =cursor.getInt(supplierNameColumnIndex);
                int currentSupplierPhone =cursor.getInt(supplierPhoneColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhone ));
            }

        } finally {
            cursor.close();
        }
    }
}