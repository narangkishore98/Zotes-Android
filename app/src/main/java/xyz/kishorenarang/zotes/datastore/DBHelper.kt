package xyz.kishorenarang.zotes.datastore

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASE_NAME = ""
val DATABASE_VERSION = ""
val TABLE_NAME = ""
val

class DBHelper (context: Context,
                factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_ZOTE_TABLE = "CREATE TABLE "+TABLE_NAME+" "
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}