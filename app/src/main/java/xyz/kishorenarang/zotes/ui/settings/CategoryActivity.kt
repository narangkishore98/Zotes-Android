package xyz.kishorenarang.zotes.ui.settings

import android.content.Context
import android.content.DialogInterface
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_category.*
import xyz.kishorenarang.zotes.R
import xyz.kishorenarang.zotes.adapters.CategoryAdapter
import xyz.kishorenarang.zotes.datastore.Category
import xyz.kishorenarang.zotes.datastore.CategoryDBHelper

/*

abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_bin)
    private val intrinsicWidth = deleteIcon!!.intrinsicWidth
    private val intrinsicHeight = deleteIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }


     override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        */
/**
         * To disable "swipe" for specific item return 0 here.
         * For example:
         * if (viewHolder?.itemViewType == YourAdapter.SOME_TYPE) return 0
         * if (viewHolder?.adapterPosition == 0) return 0
         *//*

        if (viewHolder?.adapterPosition == 10) return 0
        return super.getMovementFlags(recyclerView!!, viewHolder!!)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c!!, recyclerView!!, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        // Draw the red delete background
        background.color = backgroundColor
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        // Draw the delete icon
        deleteIcon!!.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon!!.draw(c!!)

        super.onChildDraw(c!!, recyclerView!!, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
*/

class CategoryActivity : AppCompatActivity() {

    var adapter:CategoryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(toolbar)
        setTitle("Categories")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CategoryDBHelper(this, null).categoriesAsStrings())
            adapter = CategoryAdapter(CategoryDBHelper(this, null).getCategories(), this)
        recyclerViewCategories.adapter = adapter

        recyclerViewCategories.layoutManager = LinearLayoutManager(this)

/*

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(...) {
                val adapter = recyclerViewCategories.adapter as CategoryAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerViewCategories)
*/


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.category_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menuAdd)
        {
            //Toast.makeText(this, "TEMP",Toast.LENGTH_LONG).show()
            val viewForDialogg = LayoutInflater.from(this).inflate(R.layout.category_dialog,null)
            val builder = AlertDialog.Builder(this)
            builder.setView(viewForDialogg)

            val editText = viewForDialogg.findViewById<EditText>(R.id.editTextForCategoryAddition)

            builder.setCancelable(false)
            builder
                .setCancelable(false)
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        // get user input and set it to result
                        // edit text
                        //result.setText(userInput.getText())
                        CategoryDBHelper(this, null).addCategory(Category(editText.text.toString()))
                        Toast.makeText(this, "Category Added",Toast.LENGTH_LONG).show()
                        adapter!!.categories = CategoryDBHelper(this,null).getCategories()
                        adapter!!.notifyDataSetChanged()


                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

            // create alert dialog
            // create alert dialog
            val alertDialog: AlertDialog = builder.create()

            // show it
            // show it
            alertDialog.show()
        }




        return super.onOptionsItemSelected(item)
    }



}
