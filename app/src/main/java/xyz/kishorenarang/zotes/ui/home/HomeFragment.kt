package xyz.kishorenarang.zotes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import xyz.kishorenarang.zotes.R
import xyz.kishorenarang.zotes.adapters.ZoteAdapter
import xyz.kishorenarang.zotes.datastore.ZoteDBHelper

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        //homeViewModel.text.observe(this, Observer {
        //    textView.text = it
        //})
        recyclerViewHome.adapter = ZoteAdapter(ZoteDBHelper(context!!, null).getAllZotes(),context!!)
        recyclerViewHome.layoutManager = LinearLayoutManager(context!!)

        return root

    }
}