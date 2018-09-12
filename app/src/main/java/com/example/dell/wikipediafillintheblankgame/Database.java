/**
 * The purpose of this class is to create the table in memory which is going to add all the score scored by user.
 * This class is used to fetch the data from database and compare the sore with higgest score.
 */
package com.example.dell.wikipediafillintheblankgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table SaveData (score Int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {
    }
}
