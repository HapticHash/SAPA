package com.codebrain.harshit.sapa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Harshit on 1/22/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SAPRSA";

    private static final String table_name_global = "Global";

    private static final String variablename = "variable";
    private static final String variablevalue = "variablevalue";

    private SQLiteDatabase db, db1;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String Create_table = "CREATE TABLE " + table_name_global + "("
                + variablename + " TEXT," + variablevalue + " VARCHAR2)";
        //globaladdData("flag","0");
        sqLiteDatabase.execSQL(Create_table);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void globaladdData(String variable_name,String value) {
        SQLiteDatabase db1 = this.getReadableDatabase();
        String query = "SELECT "+variablename+" FROM "+table_name_global+" WHERE "+variablename+" = '"+variable_name+"'";
        Cursor cursor = db1.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        if(count==0)
        {
            ContentValues values = new ContentValues();
            values.put(variablename, variable_name);
            values.put(variablevalue, value);
            db1.insert(table_name_global, null, values);
        }
        else {
        }
        db1.close();
    }

    public String globalgetvalue(String variable_name){
        String value = "";
        String query = "SELECT "+variablevalue+" FROM "+ table_name_global + " WHERE " +variablename+" = '"+variable_name+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                value = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return value;
    }

    public void globalsetvalue(String variable_name,String variable_value){

        String query = "UPDATE "+table_name_global+" SET "+ variablevalue +"=" +variable_value+ " WHERE " +variablename+" = '"+variable_name+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);

    }
}
