package com.example.a001264912.weeksix_inclass;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;


public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "accountsManager";

	// Contacts table name
	private static final String TABLE_ACCOUNTS = "accounts";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_TYPE = "type";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_TYPE + " TEXT" + ")";
		db.execSQL(CREATE_ACCOUNTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);

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
		values.put(KEY_NAME, bankAccount.getName()); // BankAccount Name
		values.put(KEY_TYPE, bankAccount.getType()); // BankAccount Type

		// Inserting Row
		db.insert(TABLE_ACCOUNTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	BankAccount getAccount(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] { KEY_ID,
				KEY_NAME, KEY_TYPE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		BankAccount bankAccount = new BankAccount(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return bankAccount
		return bankAccount;
	}

    // Getting single contact
    BankAccount getAccount(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_TYPE }, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        BankAccount bankAccount = new BankAccount(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return bankAccount
        return bankAccount;
    }
	
	// Getting All Accounts
	public List<BankAccount> getAllAccounts() {
		List<BankAccount> bankAccountList = new ArrayList<BankAccount>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				BankAccount bankAccount = new BankAccount();
				bankAccount.setID(Integer.parseInt(cursor.getString(0)));
				bankAccount.setName(cursor.getString(1));
				bankAccount.setType(cursor.getString(2));
				// Adding bankAccount to list
				bankAccountList.add(bankAccount);
			} while (cursor.moveToNext());
		}

		// return contact list
		return bankAccountList;
	}

	// Updating single bankAccount
	public int updateAccount(BankAccount bankAccount) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, bankAccount.getName());
		values.put(KEY_TYPE, bankAccount.getType());

		// updating row
		return db.update(TABLE_ACCOUNTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(bankAccount.getID()) });
	}

	// Deleting single bankAccount
	public void deleteAccount(BankAccount bankAccount) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ACCOUNTS, KEY_ID + " = ?",
				new String[] { String.valueOf(bankAccount.getID()) });
		db.close();
	}


	// Getting contacts Count
	public int getAccountsCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		String countQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		return cursor.getCount();
	}

}
