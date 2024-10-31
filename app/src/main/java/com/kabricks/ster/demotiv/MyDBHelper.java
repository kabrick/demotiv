package com.kabricks.ster.demotiv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kabricks.ster.demotiv.lists.DataModel;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "demotiv";

    //Table names
    private static final String PERSONAL_LIST_TABLE = "personal";

    //Common column names
    //PERSONAL_LIST column names
    public static final String PERSONAL_LIST_ID = "_id";
    public static final String PERSONAL_LIST_NAME = "name";
    public static final String PERSONAL_LIST_COMPLETE = "complete";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Auto-generated method stub
    }

    //create new tables for application
    void createInit(){
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + PERSONAL_LIST_TABLE + " (" +
                PERSONAL_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PERSONAL_LIST_NAME + " TEXT NOT NULL," + PERSONAL_LIST_COMPLETE + " INTEGER);";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(CREATE_TABLE);
    }

    //add item to personal list
    public String addPersonalListItem(String item){
        String result;
        try {
            ContentValues values = new ContentValues();
            values.put(PERSONAL_LIST_NAME, item);
            values.put(PERSONAL_LIST_COMPLETE, 0);

            SQLiteDatabase db = this.getWritableDatabase();

            db.insert(PERSONAL_LIST_TABLE, null, values);
            db.close();
            result = "Item added to Personal List";
        } catch (SQLException e) {
            result = "Error" + e;
        }

        return result;
    }

    //get number of entries in list
    public int getTotalPersonalList(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT COUNT(*) FROM " + PERSONAL_LIST_TABLE;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        cursor.close();
        return icount;
    }

    //get number of entries that are complete
    public int getTotalCompletePersonalList(){
        SQLiteDatabase db = getWritableDatabase();
        String count = "SELECT COUNT(*) FROM " + PERSONAL_LIST_TABLE + " WHERE " + PERSONAL_LIST_COMPLETE + "='" + 1 + "'";
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        cursor.close();
        return icount;
    }

    public String editPersonalListItem(String name, int id){
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        String result;

        try {
            contentValues.put(PERSONAL_LIST_NAME, name);
            db.update(PERSONAL_LIST_TABLE, contentValues, PERSONAL_LIST_ID + "='" + id + "'", null);
            db.close();
            result = "Item has been edited in Personal List";
        } catch (SQLException e) {
            result = "Error" + e;
        }
        return result;
    }

    public ArrayList<DataModel> getPersonalList(){

        String query = "SELECT * FROM " + PERSONAL_LIST_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<DataModel> dataModels = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                //obtain the id
                int id = cursor.getInt(0);

                //get string name
                String name = cursor.getString(1);

                //now if it is checked
                int checked = cursor.getInt(2);

                //convert to boolean, 0 = false and 1 = true
                boolean checked_final = checked != 0;

                dataModels.add(new DataModel(name, checked_final, id));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return dataModels;
    }

    public void updatePersonalListChecked(int id, int check){
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        contentValues.put(PERSONAL_LIST_COMPLETE, check);
        db.update(PERSONAL_LIST_TABLE, contentValues, PERSONAL_LIST_ID + "='" + id + "'", null);
    }

    public String getCurrentPersonalListName(int id){
        String name;
        String query = "SELECT * FROM " + PERSONAL_LIST_TABLE + " WHERE " + PERSONAL_LIST_ID + "='" + id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        name = cursor.getString(1);
        cursor.close();
        return name;
    }

    //delete selected entries
    public String deletePersonalListItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String report;
        try {
            db.delete(PERSONAL_LIST_TABLE, PERSONAL_LIST_ID + "='" + id + "'", null);
            report = "Item has been deleted";
        } catch (SQLException e) {
            report = "Error due to" + e;
        }

        db.close();

        return report;
    }

    //delete completed entries
    public String deletePersonalListComplete() {
        SQLiteDatabase db = getWritableDatabase();
        String report;
        try {
            db.delete(PERSONAL_LIST_TABLE, PERSONAL_LIST_COMPLETE + "='" + 1 + "'", null);
            report = "All complete items have been deleted";
        } catch (SQLException e) {
            report = "Error due to" + e;
        }

        db.close();

        return report;
    }

    //delete all entries
    public void deletePersonalList() {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM "+ PERSONAL_LIST_TABLE);

        db.close();
    }

}
