package com.syyazilim.runout.database;

import com.syyazilim.runout.domain.User;
import com.syyyazilim.runout.constants.RundroidConstants;

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
    private static final String KEY_SEX = "sex";
    
    private static final String DATABASE_NAME = "runout";
    private static final String DATABASE_TABLE = "user";
    private static final int DATABASE_VERSION = RundroidConstants.Database.databaseVersionId.intValue();

    private static final String DATABASE_CREATE =
        "create table user (_id integer primary key autoincrement, "
        + "username text not null, name text not null, surname text not null," 
        + "weight text not null, height text not null,sex text not null);";
     
    
    		
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
            //db.execSQL("DROP TABLE IF EXISTS user");
           // onCreate(db);
          
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
    public long insertUser(String username, String name, String surname, String weight, String tall,String sex) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_SURNAME, surname);
        initialValues.put(KEY_WEIGHT, weight);
        initialValues.put(KEY_TALL, tall);
        initialValues.put(KEY_SEX,sex);
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
                		KEY_TALL,
                		KEY_SEX
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
    
 
    
    public User getLastUser() throws SQLException{
    	 String selectQuery = "SELECT * FROM user where _id = (select max(_id) from user);";
    	 Cursor cursor = db.rawQuery(selectQuery, null);
    	 User user = null;
    	 if(cursor.moveToFirst()){
    		user = this.buildUserFromCursor(cursor);
    	 }
    	 
    	 return user;
    }
    
    public User buildUserFromCursor(Cursor c){
    	User user = null;
    	if(c!=null){
    		user = new User();
    		user.setId(c.getLong(0));
    		user.setUsername(c.getString(1));
    		user.setName(c.getString(2));
    		user.setSurname(c.getString(3));
    		user.setTall(c.getString(4));
    		user.setWeight(c.getString(5));
    		user.setSex(c.getString(6));
    	}
    	return user;
    }

    //---updates a user---
    public boolean updateUser(long rowId, String username,String name, 
    String surname, String weight, String tall, String sex) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_USERNAME, username);
        args.put(KEY_NAME, name);
        args.put(KEY_SURNAME, surname);
        args.put(KEY_WEIGHT, weight);
        args.put(KEY_TALL, tall);
        args.put(KEY_SEX, sex);
        return db.update(DATABASE_TABLE, args, 
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
}