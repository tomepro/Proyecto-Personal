package com.example.login.db;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.login.db.FailedAttempt;

import java.util.ArrayList;

public class dbLogin extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "LoginAttempts.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_ATTEMPTS = "attempts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String DATABASE_CREATE = "create table " + TABLE_ATTEMPTS + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_USERNAME + " text not null, " +
            COLUMN_PASSWORD + " text not null, " +
            COLUMN_TIMESTAMP + " text not null);";

    public dbLogin(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTEMPTS);
        onCreate(db);
    }
    public ArrayList<FailedAttempt> obtenerIntentosFallidos() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<FailedAttempt> intentosFallidos = new ArrayList<>();

        String[] projection = {COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_TIMESTAMP};
        Cursor cursor = db.query(TABLE_ATTEMPTS, projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP));

            FailedAttempt intento = new FailedAttempt(username, password, timestamp);
            intentosFallidos.add(intento);
        }

        cursor.close();
        db.close();

        return intentosFallidos;
    }
}
