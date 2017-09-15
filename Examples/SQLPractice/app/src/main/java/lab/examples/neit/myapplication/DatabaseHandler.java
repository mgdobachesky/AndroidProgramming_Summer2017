package lab.examples.neit.myapplication;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "bankManager";

	// Contacts table name
	private static final String TABLE_CONTACTS = "accounts";

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
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_TYPE + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addContact(BankAccount bankAccount) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, bankAccount.getName()); // Contact Name
		values.put(KEY_TYPE, bankAccount.getType()); // Contact Phone

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single account
	BankAccount getAccount(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_NAME, KEY_TYPE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		BankAccount bankAccount = new BankAccount(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return bankAccount;
	}
	
	// Getting All Accounts
	public List<BankAccount> getAllAccounts() {
		List<BankAccount> accountList = new ArrayList<BankAccount>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				BankAccount bankAccount = new BankAccount();
				bankAccount.setID(Integer.parseInt(cursor.getString(0)));
				bankAccount.setName(cursor.getString(1));
		        bankAccount.setType(cursor.getString(2));
				// Adding contact to list
				accountList.add(bankAccount);
			} while (cursor.moveToNext());
		}

		// return account list
		return accountList;
	}

	// Updating single account
	public int updateAccount(BankAccount bankAccount) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, bankAccount.getName());
		values.put(KEY_TYPE, bankAccount.getType());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(bankAccount.getID()) });
	}

	// Deleting single account
	public void deleteAccount(BankAccount bankAccount) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(bankAccount.getID()) });
		db.close();
	}


	// Getting accounts Count
	public int getAccountsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

    // Getting All Accounts By Name
    public List<BankAccount> getBankAccountByName(String name) {
        List<BankAccount> accountList = new ArrayList<BankAccount>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE " + KEY_NAME + " = " + "'" + name + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setID(Integer.parseInt(cursor.getString(0)));
                bankAccount.setName(cursor.getString(1));
                bankAccount.setType(cursor.getString(2));
                // Adding contact to list
                accountList.add(bankAccount);
            } while (cursor.moveToNext());
        }

        // return account list
        return accountList;
    }

}
