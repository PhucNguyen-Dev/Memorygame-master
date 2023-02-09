package com.example.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String TAG = "";
    private static final String DATABASE_NAME = "";
    private static final int DATABASE_VERSION = 1;
    private static final String name = "";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        SQLiteDatabase db = getReadableDatabase();
    }

    //Lệnh truy vấn không trả về(Update, add)
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
        SQLiteDatabase db = getWritableDatabase();
    }

    //Lệnh truy vấn có trả về
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.i(TAG, "player name: ");
        String queryCreateTable = "CREATE TABLE " + name+ "  " +
                "id  PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR (255) NOT NULL, " +
                "price DECIMAL DEFAULT (0)" +
                ")";

        sqLiteDatabase.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP DATABASE_NAME " + name);

        onCreate(sqLiteDatabase);
    }
}
