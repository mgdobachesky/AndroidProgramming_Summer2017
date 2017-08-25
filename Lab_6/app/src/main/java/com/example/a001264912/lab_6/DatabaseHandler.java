package com.example.a001264912.lab_6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "candyOrdersManager";

	// Contacts table name
	private static final String TABLE_CANDY_ORDERS = "candyOrders";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_PRICE = "price";
	private static final String KEY_FIRST_NAME = "firstName";
	private static final String KEY_LAST_NAME = "lastName";
	private static final String KEY_CHOCOLATE_TYPE = "chocolateType";
	private static final String KEY_CHOCOLATE_QUANTITY = "chocolateQuantity";
	private static final String KEY_EXPEDITED_SHIPPING = "expeditedShipping";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ACCOUNTS_TABLE =
                "CREATE TABLE " + TABLE_CANDY_ORDERS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_PRICE + " REAL,"
				+ KEY_FIRST_NAME + " TEXT,"
				+ KEY_LAST_NAME + " TEXT,"
				+ KEY_CHOCOLATE_TYPE + " TEXT,"
				+ KEY_CHOCOLATE_QUANTITY + " INTEGER,"
				+ KEY_EXPEDITED_SHIPPING + " INTEGER" + ")";
		db.execSQL(CREATE_ACCOUNTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDY_ORDERS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new bankAccount
	void addOrder(Order candyOrder) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
        values.put(KEY_PRICE, candyOrder.getPrice());
		values.put(KEY_FIRST_NAME, candyOrder.getFirstName());
		values.put(KEY_LAST_NAME, candyOrder.getLastName());
        values.put(KEY_CHOCOLATE_TYPE, candyOrder.getChocolateType());
        values.put(KEY_CHOCOLATE_QUANTITY, candyOrder.getChocolateQuantity());
        if(candyOrder.isExpeditedShipping()) {
            values.put(KEY_EXPEDITED_SHIPPING, 1);
        } else if(!candyOrder.isExpeditedShipping()) {
            values.put(KEY_EXPEDITED_SHIPPING, 0);
        }

		// Inserting Row
		db.insert(TABLE_CANDY_ORDERS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Order getOrder(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CANDY_ORDERS,
                new String[] {KEY_ID, KEY_PRICE, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_CHOCOLATE_TYPE, KEY_CHOCOLATE_QUANTITY, KEY_EXPEDITED_SHIPPING},
                KEY_ID + "=?",
				new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null);

		if (cursor != null)
			cursor.moveToFirst();

		Order candyOrder = new Order(Integer.parseInt(cursor.getString(0)),
                Float.parseFloat(cursor.getString(1)),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                Integer.parseInt(cursor.getString(5)),
                (Integer.parseInt(cursor.getString(6)) == 1) ? true : false);

		return candyOrder;
	}

    // Getting single contact
    List<Order> getOrder(Float price) {
        List<Order> selectedOrders = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CANDY_ORDERS,
                new String[] {KEY_ID, KEY_PRICE, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_CHOCOLATE_TYPE, KEY_CHOCOLATE_QUANTITY, KEY_EXPEDITED_SHIPPING},
                KEY_PRICE + ">=?",
                new String[] {String.valueOf(price)},
                null,
                null,
                null,
                null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Order candyOrder = new Order(Integer.parseInt(cursor.getString(0)),
                        Float.parseFloat(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        Integer.parseInt(cursor.getString(5)),
                        (Integer.parseInt(cursor.getString(6)) == 1) ? true : false);

                // Add order to list
                selectedOrders.add(candyOrder);
            } while (cursor.moveToNext());
        }

        return selectedOrders;
    }
	
	// Getting All Orders
	public List<Order> getAllOrders() {
		List<Order> selectedOrders = new ArrayList<>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CANDY_ORDERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Order candyOrder = new Order(Integer.parseInt(cursor.getString(0)),
                        Float.parseFloat(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        Integer.parseInt(cursor.getString(5)),
                        (Integer.parseInt(cursor.getString(6)) == 1) ? true : false);

                // Add order to list
                selectedOrders.add(candyOrder);
            } while (cursor.moveToNext());
        }

        return selectedOrders;
	}

	// Updating single order
	public int updateOrder(Order candyOrder) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
        values.put(KEY_PRICE, candyOrder.getPrice());
        values.put(KEY_FIRST_NAME, candyOrder.getFirstName());
        values.put(KEY_LAST_NAME, candyOrder.getLastName());
        values.put(KEY_CHOCOLATE_TYPE, candyOrder.getChocolateType());
        values.put(KEY_CHOCOLATE_QUANTITY, candyOrder.getChocolateQuantity());
        if(candyOrder.isExpeditedShipping()) {
            values.put(KEY_EXPEDITED_SHIPPING, 1);
        } else if(!candyOrder.isExpeditedShipping()) {
            values.put(KEY_EXPEDITED_SHIPPING, 0);
        }

		// updating row
		return db.update(TABLE_CANDY_ORDERS,
                values,
                KEY_ID + " = ?",
				new String[] {String.valueOf(candyOrder.getId())});
	}

	// Deleting single order
	public void deleteOrder(Order candyOrder) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CANDY_ORDERS,
                KEY_ID + " = ?",
				new String[] {String.valueOf(candyOrder.getId())});
		db.close();
	}

	// Getting orders count
	public int getOrdersCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		String countQuery = "SELECT  * FROM " + TABLE_CANDY_ORDERS;
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		return cursor.getCount();
	}

}
