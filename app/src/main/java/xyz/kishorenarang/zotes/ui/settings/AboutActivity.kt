package xyz.kishorenarang.zotes.ui.settings

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import xyz.kishorenarang.zotes.R

import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)
        setTitle("Categories")


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
