package com.syyazilim.runout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapterForUser 
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_TALL = "height";
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "runout";
    private static final String DATABASE_TABLE = "user";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table user (_id integer primary key autoincrement, "
        + "username text not null, name text not null, surname text not null," 
        + "weight text not null, height text not null);";
        
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapterForUser(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS user");
            onCreate(db);
        }
    }    
    
    //---opens the database---
    public DBAdapterForUser open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a user into the database---
    public long insertUser(String username, String name, String surname, String weight, String tall) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_SURNAME, surname);
        initialValues.put(KEY_WEIGHT, weight);
        initialValues.put(KEY_TALL, tall);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular user---
    public boolean deleteUser(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + 
        		"=" + rowId, null) > 0;
    }

    //---retrieves a particular user---
    public Cursor getUser(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_USERNAME, 
                		KEY_NAME,
                		KEY_SURNAME,
                		KEY_WEIGHT,
                		KEY_TALL
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    public Cursor getLastUser() throws SQLException{
    	 String selectQuery = "SELECT * FROM user WHERE _id = (SELECT MAX(_id) FROM user);";
    	 Cursor cursor = db.rawQuery(selectQuery, null);
    	 return cursor;
    }

    //---updates a user---
    public boolean updateUser(long rowId, String username,String name, 
    String surname, String weight, String tall) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_USERNAME, username);
        args.put(KEY_NAME, name);
        args.put(KEY_SURNAME, surname);
        args.put(KEY_WEIGHT, weight);
        args.put(KEY_TALL, tall);
        return db.update(DATABASE_TABLE, args, 
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
}