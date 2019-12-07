package xyz.kishorenarang.zotes.ui.create

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_view_attachments.*
import kotlinx.android.synthetic.main.content_view_attachments.*
import xyz.kishorenarang.zotes.R
import xyz.kishorenarang.zotes.adapters.ImageGridAdapter

class ViewAttachmentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_attachments)
        setSupportActionBar(toolbar)
        val file: Uri = this.intent.getParcelableExtra("File")!!
        val sglm = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        imagesRecycleView.layoutManager = sglm

        val imageList = ArrayList<Uri>()
        imageList.add(file)

        val igka = ImageGridAdapter(this, imageList)
        imagesRecycleView.adapter = igka

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
