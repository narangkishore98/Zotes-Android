package xyz.kishorenarang.zotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import xyz.kishorenarang.zotes.R
import xyz.kishorenarang.zotes.datastore.Zote
import java.time.format.DateTimeFormatter


class ZoteAdapter (var zotes:ArrayList<Zote>, val context: Context) : RecyclerView.Adapter<ZoteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {

        var title = view.findViewById<TextView>(R.id.zoteTitle)
        var dateandlocation = view.findViewById<TextView>(R.id.zoteLocationandtime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       //To change body of created functions use File | Settings | File Templates.
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_zote,parent,false)
        return ZoteAdapter.ViewHolder(view)
    }

    override fun getItemCount() = zotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val formatDateTime: String = zotes.get(position).datetime.format(formatter)
        holder.dateandlocation.text = zotes.get(position).location + " "+ formatDateTime
        holder.title.text = zotes.get(position).title

    }
}