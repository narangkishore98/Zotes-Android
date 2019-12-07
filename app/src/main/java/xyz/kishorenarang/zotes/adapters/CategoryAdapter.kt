package xyz.kishorenarang.zotes.adapters

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_settings.view.*
import xyz.kishorenarang.zotes.MainActivity
import xyz.kishorenarang.zotes.R
import xyz.kishorenarang.zotes.datastore.Category
import xyz.kishorenarang.zotes.ui.settings.CategoryActivity
import xyz.kishorenarang.zotes.ui.settings.SettingsFragment
import java.util.*
import kotlin.collections.ArrayList

class CategoryAdapter(var categories:ArrayList<Category>, val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
{
    class ViewHolder(view:View) : RecyclerView.ViewHolder(view)
    {
        val categoryTitle = view.findViewById<TextView>(R.id.settingsTitle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_settings,parent,false)
        view.setOnClickListener(View.OnClickListener {
            //  Toast.makeText(parent.context, "HELLO", Toast.LENGTH_LONG).show()
            if (view.settingsTitle.text.equals("Categories"))
            {
                //CategoryFragment.open(parent.context)

//                val fragment = CategoryFragment()
//                val settingsFragment = parent.context!! as MainActivity
//                val frtr  = settingsFragment


                //fragmentManager.beginTransaction().replace(R.id.frame_container, CategoryFragment()).addToBackStack(null).commit()


                Toast.makeText(context, "HEY",Toast.LENGTH_LONG).show()
                val intent = Intent(context, CategoryActivity::class.java)
                context.startActivity(intent)

            }
        })
        return CategoryAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size



    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.categoryTitle.text = categories[position].categoryName

    }
}