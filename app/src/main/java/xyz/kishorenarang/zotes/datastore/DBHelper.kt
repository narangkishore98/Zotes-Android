package xyz.kishorenarang.zotes.datastore

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

import kotlin.collections.ArrayList


class ZoteDBHelper (context: Context,
                factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, Datastore.DATABASE_NAME,
        factory, Datastore.DATABASE_VERSION)
{

    val DATABASE_NAME = "ZOTES.db"
    val DATABASE_VERSION = 1
    val TABLE_NAME = "ZOTE"
    val ZOTE_COL_TITLE = "zote_title"
    val ZOTE_COL_CONTENT = "zote_content"
    val ZOTE_COL_ID = "zote_id"
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_ZOTE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( "+ZOTE_COL_ID+" INTEGER PRIMARY KEY "+ ZOTE_COL_TITLE+" VARCHAR(50) "+ZOTE_COL_CONTENT+" VARCHAR(2000))"
        db!!.execSQL(CREATE_ZOTE_TABLE)
    }


    fun addZote(zote:Zote)
    {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        //val contentValues = ContentValues()

        val values = ContentValues()
        values.put(Datastore.ZOTE_COL_TITLE,zote.title)
        values.put(Datastore.ZOTE_COL_CONTENT,zote.content)
        //values.put(Datastore.ZOTE_COL_DATETIME, zote.datetime)
        values.put(Datastore.ZOTE_COL_DATETIME, dateFormat.format(zote.datetime))
        values.put(Datastore.ZOTE_COL_LOCATION, zote.location)
        val db = this.writableDatabase
        db.insert(Datastore.TABLE_NAME,null, values)
        db.close()
    }

    fun getAllZotes() : ArrayList<Zote>
    {
        var list = ArrayList<Zote>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM "+Datastore.TABLE_NAME, null)
        cursor!!.moveToFirst()
        while(cursor!!.moveToNext())

        {
            val title = cursor.getString(cursor.getColumnIndex(Datastore.ZOTE_COL_TITLE))
            val content = cursor.getString(cursor.getColumnIndex(Datastore.ZOTE_COL_CONTENT))
            val datetime = cursor.getString(cursor.getColumnIndex(Datastore.ZOTE_COL_DATETIME))

            val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)
            val date = LocalDate.parse(datetime, formatter)

            val location = cursor.getString(cursor.getColumnIndex(Datastore.ZOTE_COL_LOCATION))

            list.add(Zote(title, content,  date,location ))



        }
        return list

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
class Datastore
{
    companion object{
        val DATABASE_NAME = "ZOTES.db"
        val DATABASE_VERSION = 1
        val TABLE_NAME = "ZOTE"
        val ZOTE_COL_TITLE = "zote_title"
        val ZOTE_COL_CONTENT = "zote_content"
        val ZOTE_COL_ID = "zote_id"
        val ZOTE_COL_DATETIME = "zote_datetime"
        val ZOTE_COL_LOCATION = "zote_location"
    }
}