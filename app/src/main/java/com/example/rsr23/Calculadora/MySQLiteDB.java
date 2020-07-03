package com.example.rsr23.Calculadora;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MySQLiteDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Calculadora.db";
    private static final String TABLE_NAME="calculadora_history";
    private static final int VERSION_NUMBER= 1;

    private static final String ID ="_Id";
    private static final String RESULT = "Result";

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+RESULT+" VARCHAR(255) ); ";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String SELECT_ALL = "SELECT * FROM "+TABLE_NAME;
    private static final String DELETE_ALL = "DELETE FROM "+TABLE_NAME;
    private Context context;


    public MySQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }


    @Override public void onCreate(SQLiteDatabase db) {

        try{

            db.execSQL(CREATE_TABLE);

        }
        catch (Exception e){

            Toast.makeText(context, "Exception: "+e, Toast.LENGTH_LONG).show();
        }

    }


    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{

            db.execSQL(DROP_TABLE);
            onCreate(db);
        }
        catch (Exception e){

            Toast.makeText(context, "Exception: "+e, Toast.LENGTH_LONG).show();

        }

    }


    /* Insert Data */
    public void insertData(String Result){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESULT,Result);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }


    /* Read Data */
    public Cursor displayData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(SELECT_ALL,null);
    }


    /* Delete Data */
    public void deleteAll(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(DELETE_ALL);
    }
}
