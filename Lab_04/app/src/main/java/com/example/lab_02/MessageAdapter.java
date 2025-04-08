package com.example.lab_02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MessageAdapter {
    static DataBaseHelper dbHelper;
    private Context context;
    public MessageAdapter(Context _context) {
        dbHelper = new DataBaseHelper(_context);
    context = _context;
    }
    public long insertMessage(Message message) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.NAME, message.getName());
        contentValues.put(DataBaseHelper.NUMBER, message.getNumber());
        return db.insert(DataBaseHelper.MY_TABLE_NAME, null, contentValues);
    }
    public Message findMessage(String mobileNumber) {
        Message msg = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] args = {mobileNumber};
        Cursor cursor = db.query(DataBaseHelper.MY_TABLE_NAME, null, "number = ?", args, null, null, null);
        if (cursor != null) {
            cursor.moveToNext();
            msg = new Message(cursor.getString(0), cursor.getString(1));
        } else {}
        return msg;
    }
    static class DataBaseHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "user_msg.db";
        private static final String MY_TABLE_NAME = "MESSAGES";

        private static final String NUMBER = "NUMBER";
        private static final String NAME = "NAME";

        public DataBaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + MY_TABLE_NAME + " (" + NUMBER + " TEXT PRIMARY KEY, " + NAME + " VARCHAR(255));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    }
}
