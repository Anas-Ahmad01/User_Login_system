package com.example.login_page;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";

    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DOB = "dob";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE =
                "CREATE TABLE " + TABLE_USERS + "("
                        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + KEY_EMAIL + " TEXT,"
                        + KEY_USERNAME + " TEXT,"
                        + KEY_PASSWORD + " TEXT,"
                        + KEY_DOB + " TEXT"
                        + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public long addUser(String email, String username, String password, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_DOB, dob);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
    }

    public Cursor getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_USERS,
                null,
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
    }

    public int updateUser(int id, String email, String username, String password, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_DOB, dob);
        int rows = db.update(
                TABLE_USERS,
                values,
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}
        );
        db.close();
        return rows;
    }

    public boolean delete_User(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(
                TABLE_USERS,
                KEY_USERNAME + "=?",
                new String[]{username}
        );
        db.close();
        return rows > 0;
    }


    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{KEY_ID},
                KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{username, password},
                null,
                null,
                null
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }


    public boolean checkUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{KEY_ID},
                KEY_EMAIL + "=?",
                new String[]{email},
                null,
                null,
                null
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean updatePasswordByEmail(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, newPassword);

        int rows = db.update(
                TABLE_USERS,
                values,
                KEY_EMAIL + "=?",
                new String[]{email}
        );
        db.close();
        return rows > 0;
    }



}
