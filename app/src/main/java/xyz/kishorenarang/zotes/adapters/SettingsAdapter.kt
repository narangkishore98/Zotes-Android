package xyz.kishorenarang.zotes.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_settings_row_cell.view.*
import xyz.kishorenarang.zotes.R
import xyz.kishorenarang.zotes.customs.SettingItem


class SettingsAdapter(list:List<SettingItem>, context:Context) : RecyclerView.Adapter<SettingsAdapter.ItemHolder>()
{
    private val context: Context = context

    private val items:List<SettingItem> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsAdapter.ItemHolder {
         //To change body of created functions use File | Settings | File Templates.

        val row = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_row_cell,parent,  false)
        return ItemHolder(row,context)


    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: SettingsAdapter.ItemHolder, position: Int) {
        //To change body of created functions use File | Settings | File Templates.
        holder.item = items[position]
        holder.bindItem(items[position])

    }

    class ItemHolder(v: View, context: Context) : RecyclerView.ViewHolder(v), View.OnClickListener
    {
        override fun onClick(p0: View?) {

            Log.e("Item Clicked","Recycler View")
            //CategoryFragment.open(context)
        }

        var view:View = v
        var item:SettingItem? = null
        var context:Context = context

        init {
            view.setOnClickListener(this)

        }
        fun bindItem(item:SettingItem)
        {
            this.item = item
            view.settingsMenuItem.text = item.menuItem
        }
    }

}