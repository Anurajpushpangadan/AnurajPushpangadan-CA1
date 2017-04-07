package com.anuraj_pushapngadan.taskapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.anuraj_pushapngadan.taskapp.models.Task;

import java.util.ArrayList;

import java.util.List;



public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "TasksDB.db";
    public static final String MYTASKS_TABLE_NAME = "mytasks";
    public static final String MYTASKS_COLUMN_TITLE = "title";
    public static final String MYTASKS_COLUMN_LOCATION = "location";
    public static final String MYTASKS_COLUMN_START_DATE = "start_date";
    public static final String MYTASKS_COLUMN_END_DATE = "end_date";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    // This method will create the table mytasks if it is not exist
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table mytasks " +
           "(id integer primary key AUTOINCREMENT NOT NULL, title text,location text,start_date text, end_date text)"
        );
    }


    //This method will call only in an upgrade of the software
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS mytasks");
        onCreate(db);
    }

    public boolean insertTask (String title, String location, String start_date, String end_date) {
        SQLiteDatabase db = this.getWritableDatabase();    //getting a writable instance because we are going to insert data to it
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("location", location);
        contentValues.put("start_date", start_date);
        contentValues.put("end_date", end_date);
        db.insert("mytasks", null, contentValues);
        return true;
    }

    //This method will return the list of the tasks
    public List<Task> getAllTasks() {

         List<Task> MyTasks    = new ArrayList<Task>();

        SQLiteDatabase db = this.getReadableDatabase();          //getting a readable instance because we are going to read the data
        Cursor res =  db.rawQuery( "select * from mytasks", null );
        res.moveToFirst();
        String Title="",Location="",StartDate="",EndDate="";
        while(res.isAfterLast() == false){
            Title = res.getString(res.getColumnIndex(MYTASKS_COLUMN_TITLE));
            Location = res.getString(res.getColumnIndex(MYTASKS_COLUMN_LOCATION));
            EndDate = res.getString(res.getColumnIndex(MYTASKS_COLUMN_START_DATE));
            StartDate = res.getString(res.getColumnIndex(MYTASKS_COLUMN_END_DATE));

            MyTasks.add(new Task(Title, Location, StartDate, EndDate));           //Adding a new task object to the MyTasks list
            res.moveToNext();
        }
        return MyTasks;             //returning the MyTasks list
    }

    //This method will delete all the tasks in the mytasks table
    public Integer deleteAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("mytasks",null,null);  // db.delete(String tableName, String whereClause, String[] whereArgs);
                                                // If whereClause is null, it will delete all rows.
    }

    //This method will return the total number of rows in the mytasks table.
    //Total number of rows is equal to the total number of tasks.
    public int getTotalNumberOfTasks(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MYTASKS_TABLE_NAME);
        return numRows;
    }

}
