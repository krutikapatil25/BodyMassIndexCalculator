package com.future.bodymassindexproject;

/**
 * Created by admin on 07-07-2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


/**
 * Created by admin on 27-06-2018.
 */

public class MyDbHandler extends SQLiteOpenHelper {

    SQLiteDatabase db;
    Context context;

    MyDbHandler(Context context) {
        super(context, "bmidb", null, 1);
        this.context = context;
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table bmidata(name text, sage text,weight text,bmi text)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void addData(String name, String sage,String weight,String bmi) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("sage",sage);
        cv.put("weight",weight);
        cv.put("bmi",bmi);
        long rid = db.insert("bmidata", null, cv);
        if (rid < 0)
            Toast.makeText(context, "Issue", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Data Saved successfully", Toast.LENGTH_SHORT).show();
    }

    public String viewStudent()
    {
        StringBuffer data= new StringBuffer();
        Cursor cursor = db.query("bmidata", null, null, null, null, null, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0)
        {
            do {
                data.append("Name : " + cursor.getString(0) + "\n" + "Age : " + cursor.getString(1) + "\n"
                        + "Weight : " + cursor.getString(2) + "\n" + "BMI:" + cursor.getString(3) + "\n");

            }while(cursor.moveToNext());
        }
        return data.toString();
    }

}
