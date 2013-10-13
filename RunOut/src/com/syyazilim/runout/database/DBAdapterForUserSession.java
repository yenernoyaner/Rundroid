package com.syyazilim.runout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapterForUserSession {
	public static final String KEY_ROWID = "_id";
    public static final String KEY_CALORIES = "calories";
    public static final String KEY_SPEED = "speed";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_TEMPO = "tempo";
    public static final String KEY_TIME = "time";
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "runout";
    private static final String DATABASE_TABLE = "usersession";
    private static final int DATABASE_VERSION = 1;
    
    private static final String DATABASE_CREATE =
            "create table user_session (_id integer primary key autoincrement, "
            + "calories text , speed text , distance text ," 
            + "tempo text , time text);";
 
    
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapterForUserSession(Context ctx) 
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
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }    

    
    public DBAdapterForUserSession open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    public long insertUserSession(String calories, String speed, String distance, String tempo, String time) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CALORIES, calories);
        initialValues.put(KEY_SPEED, speed);
        initialValues.put(KEY_DISTANCE, distance);
        initialValues.put(KEY_TEMPO, tempo);
        initialValues.put(KEY_TIME, time);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    
    public Cursor getUserSession(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_CALORIES, 
                		KEY_SPEED,
                		KEY_DISTANCE,
                		KEY_TEMPO,
                		KEY_TIME
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

}
