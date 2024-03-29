package xyz.kishorenarang.zotes.datastore

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract
import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
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
        val CREATE_ZOTE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( "+ZOTE_COL_ID+" INTEGER PRIMARY KEY, "+ ZOTE_COL_TITLE+" VARCHAR(50) ,"+ZOTE_COL_CONTENT+" VARCHAR(2000), "+Datastore.ZOTE_COL_LOCATION+" VARCHAR(200), "+Datastore.ZOTE_COL_DATETIME+" VARCHAR(50) )"
        //val dbb = this.writableDatabase
        Log.e("DB", db.toString())

        db!!.execSQL(CREATE_ZOTE_TABLE)
    }


    fun addZote(zote:Zote)
    {

        //val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss a")
        //val contentValues = ContentValues()
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss a", Locale.ENGLISH)

        val values = ContentValues()
        values.put(Datastore.ZOTE_COL_TITLE,zote.title)
        values.put(Datastore.ZOTE_COL_CONTENT,zote.content)
        //values.put(Datastore.ZOTE_COL_DATETIME, zote.datetime)
        values.put(Datastore.ZOTE_COL_DATETIME, zote.datetime.format(formatter))
        values.put(Datastore.ZOTE_COL_LOCATION, zote.location)
        val db = this.writableDatabase
        db.insert(Datastore.ZOTE_TABLE_NAME,null, values)
        db.close()
    }

    fun getAllZotes() : ArrayList<Zote>
    {
        var list = ArrayList<Zote>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM "+Datastore.ZOTE_TABLE_NAME, null)
        cursor.moveToFirst()
        while(cursor.moveToNext())

        {
            val title = cursor.getString(cursor.getColumnIndex(Datastore.ZOTE_COL_TITLE))
            val content = cursor.getString(cursor.getColumnIndex(Datastore.ZOTE_COL_CONTENT))
            val datetime = cursor.getString(cursor.getColumnIndex(Datastore.ZOTE_COL_DATETIME))

            val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss a", Locale.ENGLISH)
            val date = LocalDateTime.parse(datetime, formatter)

            val location = cursor.getString(cursor.getColumnIndex(Datastore.ZOTE_COL_LOCATION))

            val zote = Zote(title, content,  date,location )
            zote.id = cursor.getInt(cursor.getColumnIndex(Datastore.ZOTE_COL_ID))
            list.add(zote)



        }
        return list

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val CREATE_ZOTE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ( "+ZOTE_COL_ID+" INTEGER PRIMARY KEY, "+ ZOTE_COL_TITLE+" VARCHAR(50) ,"+ZOTE_COL_CONTENT+" VARCHAR(2000), "+Datastore.ZOTE_COL_LOCATION+" VARCHAR(200), "+Datastore.ZOTE_COL_DATETIME+" VARCHAR(50) )"
        //val dbb = this.writableDatabase
        Log.e("DB", db.toString())

        db!!.execSQL(CREATE_ZOTE_TABLE)
    }


}

//class for category addition.


class CategoryDBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, Datastore.DATABASE_NAME,
    factory, Datastore.DATABASE_VERSION)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_ZOTE_TABLE = "CREATE TABLE "+Datastore.CATEGORY_TABLE_NAME+" ( "+Datastore.CATEGORY_COL_ID+" INTEGER PRIMARY KEY, "+ Datastore.CATEGORY_COL_CATEGORY_NAME+" VARCHAR(50) )"
        //val dbb = this.writableDatabase
        Log.i("DB", db.toString())
        db!!.execSQL(CREATE_ZOTE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
    fun addCategory(category: Category)
    {
        val values  = ContentValues()
        values.put(Datastore.CATEGORY_COL_CATEGORY_NAME,category.categoryName)
        val db = this.writableDatabase
        db.insert(Datastore.CATEGORY_TABLE_NAME,null, values)
        db.close()

    }
    fun getCategories() : ArrayList<Category>
    {
        var list = ArrayList<Category>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM "+Datastore.CATEGORY_TABLE_NAME, null)
        cursor.moveToFirst()

        Log.i("CUR_I",cursor.toString())
        while(cursor.moveToNext())

        {

            val categoryName = cursor.getString(cursor.getColumnIndex(Datastore.CATEGORY_COL_CATEGORY_NAME))
            val id = cursor.getInt(cursor.getColumnIndex(Datastore.CATEGORY_COL_ID))
            val category = Category(categoryName)
            category.id = id
            list.add(category)


        }

        return list
    }

    fun categoriesAsStrings() : Array<String>
    {
        var array = Array<String>(getCategories().size, {i -> ""})

        for(i in 0..getCategories().size)
        {
            array[i] = getCategories().get(i).categoryName
        }
        return array
    }

}
class Datastore
{
    companion object{

        //for the zote table

        val DATABASE_NAME = "ZOTES.db"
        val DATABASE_VERSION = 3
        val ZOTE_TABLE_NAME = "ZOTE"
        val ZOTE_COL_TITLE = "zote_title"
        val ZOTE_COL_CONTENT = "zote_content"
        val ZOTE_COL_ID = "zote_id"
        val ZOTE_COL_DATETIME = "zote_datetime"
        val ZOTE_COL_LOCATION = "zote_location"

        //for the category table.

        val CATEGORY_TABLE_NAME = "CATEGORY"
        val CATEGORY_COL_CATEGORY_NAME = "category_name"
        val CATEGORY_COL_ID = "category_id"

        //for image table

        val IMAGE_TABLE_NAME = "IMAGES"
        val IMAGE_COL_IMAGE_NAME = "image_name"
        val IMAGE_COL_ID = "image_id"
        val IMAGE_COL_ZOTE = "image_of_zote"

    }
}


//image class



class ImageDBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, Datastore.DATABASE_NAME,
    factory, Datastore.DATABASE_VERSION)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_IMAGE_TABLE = "CREATE TABLE "+Datastore.IMAGE_TABLE_NAME+" ( "+Datastore.IMAGE_COL_ID+" INTEGER PRIMARY KEY, "+ Datastore.IMAGE_COL_IMAGE_NAME+" VARCHAR(255), "+Datastore.IMAGE_COL_ZOTE+" INTEGER , FOREIGN KEY("+Datastore.IMAGE_COL_ZOTE+" REFERENCES "+Datastore.ZOTE_TABLE_NAME+"("+Datastore.ZOTE_COL_ID+")) )"
        //val dbb = this.writableDatabase
        Log.i("DB", db.toString())
        db!!.execSQL(CREATE_IMAGE_TABLE)
    }

    fun addImage(image: Image)
    {
        val values  = ContentValues()
        values.put(Datastore.IMAGE_COL_IMAGE_NAME,image.imagePath)
        values.put(Datastore.IMAGE_COL_ZOTE, image.zote!!.id)
        val db = this.writableDatabase
        db.insert(Datastore.CATEGORY_TABLE_NAME,null, values)
        db.close()

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }

}