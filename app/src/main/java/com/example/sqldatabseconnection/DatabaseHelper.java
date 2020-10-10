package com.example.sqldatabseconnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // database name and table name
    private static final String DATABASE_NAME = "trial_database";
    private static final String TABLE_NAME = "Details";
    private static final String TABLE_NAME1 = "List";

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table creation

        String createTable = "create table " + TABLE_NAME +
                "(NAME TEXT PRIMARY KEY , EMAIL TEXT);" ;

        db.execSQL(createTable);

        String createTable1 = "create table " + TABLE_NAME1 +
                "(SR_NO INTEGER PRIMARY KEY , LIST_ITEM TEXT );" ;


        db.execSQL(createTable1);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // drop older table if exists

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        onCreate(db);
    }

    public  boolean addText(String name_1 , String email_1) {

        //get writeable database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //create contetnt values
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name_1);
        contentValues.put("EMAIL",email_1);

        //Add Values
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();

        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean addList(Integer no , String list){

        //get writeable database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //create contetnt values
        ContentValues contentValues = new ContentValues();
        contentValues.put("SR_NO",no);
        contentValues.put("LIST_ITEM",list);


        long input = sqLiteDatabase.insert(TABLE_NAME1,null,contentValues);
        sqLiteDatabase.close();


        if(input == -1)
            return false;
        else
            return true;

    }

    public boolean deleteRow(long l) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String where = "SR_NO" + "=" + l;
        return sqLiteDatabase.delete(TABLE_NAME1, where, null) != 0;
    }

    public int maxSR() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return (int) DatabaseUtils.longForQuery(sqLiteDatabase, "SELECT MAX(SR_NO) FROM " + TABLE_NAME1, null);
    }

    //retriving data

    public Cursor alldata() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " +TABLE_NAME+ " WHERE EMAIL ='amrutakoshe'" , null);
        return cursor;
    }


}
