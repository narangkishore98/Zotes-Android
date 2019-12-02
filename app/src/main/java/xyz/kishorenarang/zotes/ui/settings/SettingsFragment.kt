package xyz.kishorenarang.zotes.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notifications.*
import xyz.kishorenarang.zotes.R
import xyz.kishorenarang.zotes.adapters.SettingsAdapter
import xyz.kishorenarang.zotes.customs.Label
import xyz.kishorenarang.zotes.customs.SettingItem

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel


    private val list = listOf<SettingItem>(SettingItem("Categories"), SettingItem("About"))

    private  lateinit var adapter:SettingsAdapter
    private lateinit var  recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        //val textView: TextView = root.findViewById(R.id.text_notifications)
        settingsViewModel.text.observe(this, Observer {
            //textView.text = it
        })


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SettingsAdapter(list, context!!)
        Log.e("TEST IF NULL", adapter.toString())
        //recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerForSettings)
        if(adapter != null)
        {
            recyclerForSettings.adapter = adapter
            recyclerForSettings.layoutManager = LinearLayoutManager(context)
            Log.e("REC",recyclerForSettings.toString())
        }
    }
}