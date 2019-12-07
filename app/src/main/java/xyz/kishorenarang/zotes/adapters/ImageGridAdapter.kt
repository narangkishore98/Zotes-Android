package xyz.kishorenarang.zotes.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.image_cell.view.*
import xyz.kishorenarang.zotes.R


class ImageGridAdapter(private val c: Context, private val images: ArrayList<Uri>) :
    RecyclerView.Adapter<ImageGridAdapter.ColorViewHolder>() {


    override fun getItemCount(): Int {
        Log.e("Count ->",images.size.toString())
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(LayoutInflater.from(c).inflate(R.layout.image_cell, parent, false))
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val path = images[position]
        Log.e("path - >",path.toString())
        //val uri = Uri.parse(path)
        //val file = File(path)

       // Picasso.get()
         //   .load(path)
           // .resize(250, 250)
            //.centerCrop()
            //.into(holder.iv)

        //Picasso.get().load(path)
          //  .placeholder(R.drawable.ic_create_zote_black)
           // .fit()
            //.centerCrop()
            //.into(holder.iv)
        //Picasso.get().load(File(path.toString())).config(Bitmap.Config.RGB_565).fit().centerCrop().into(holder.iv)
        //val bitmap = BitmapFactory.decodeFile(path.toString())
        //val ims: InputStream = getContentResolver().openInputStream(path)
        //holder.iv.setImageBitmap(BitmapFactory.decodeFile(path.toString()))
        holder.iv.setImageResource(R.drawable.capturedd)
        holder.iv.setOnClickListener {
            //handle click event on image
        }
    }

    class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv = view.iv as ImageView
    }
}