package com.example.a001264912.finalexam;

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
	private static final String DATABASE_NAME = "BankAccountManager";

	// Contacts table name
	private static final String TABLE_BANK_ACCOUNTS= "BankAccounts";

	// Contacts Table Columns names
	private static final String KEY_ID = "_id";
	private static final String KEY_OPERATION = "operation";
	private static final String KEY_AMOUNT = "amount";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ACCOUNTS_TABLE =
                "CREATE TABLE " + TABLE_BANK_ACCOUNTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_OPERATION + " TEXT,"
				+ KEY_AMOUNT + " REAL" + ")";
		db.execSQL(CREATE_ACCOUNTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK_ACCOUNTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new bankAccount
	void addAccount(BankAccount bankAccount) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
        values.put(KEY_OPERATION, bankAccount.operation);
		values.put(KEY_AMOUNT, bankAccount.amount);

		// Inserting Row
		db.insert(TABLE_BANK_ACCOUNTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	BankAccount getBankAccount(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_BANK_ACCOUNTS,
                new String[] {KEY_ID, KEY_OPERATION, KEY_AMOUNT},
                KEY_ID + "=?",
				new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null);

		if (cursor != null)
			cursor.moveToFirst();

		BankAccount bankAccount = new BankAccount(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
				Float.parseFloat(cursor.getString(2)));

		return bankAccount;
	}
	
	// Getting All Orders
	public List<BankAccount> getAllBankAccounts() {
		List<BankAccount> selectedAccounts = new ArrayList<>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_BANK_ACCOUNTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
				BankAccount bankAccount = new BankAccount(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1),
						Float.parseFloat(cursor.getString(2)));

                // Add order to list
                selectedAccounts.add(bankAccount);
            } while (cursor.moveToNext());
        }

        return selectedAccounts;
	}

	// Getting orders count
	public int getAccountsCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		String countQuery = "SELECT  * FROM " + TABLE_BANK_ACCOUNTS;
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		return cursor.getCount();
	}

}
