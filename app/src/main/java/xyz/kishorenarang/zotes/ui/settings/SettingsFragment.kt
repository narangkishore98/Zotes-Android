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
import kotlinx.android.synthetic.main.fragment_settings.*

import xyz.kishorenarang.zotes.R
import xyz.kishorenarang.zotes.adapters.SettingsAdapter
import xyz.kishorenarang.zotes.customs.SettingItem

class SettingsFragment : Fragment() {

    init {
        Log.e("ADAPTER","ERROR")
    }
    private lateinit var settingsViewModel: SettingsViewModel


    private val list = listOf<SettingItem>(SettingItem("Categories"), SettingItem("About"))

    private val settings = arrayOf("Categories","About")
    private lateinit var  recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //val settingsRecyclerView = view!!.findViewById<RecyclerView>(R.id.settingsRecyclerView)


        if(settingsList != null)
        {

            settingsList.adapter = SettingsAdapter(settings, context!!, fragmentManager!!)
            settingsList.layoutManager = LinearLayoutManager(activity)

        }

        //Log.e("OBJ_VAL",settingsList.toString())

        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        //val textView: TextView = root.findViewById(R.id.text_notifications)
        settingsViewModel.text.observe(this, Observer {
            //textView.text = it
        })


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        settingsList.adapter = SettingsAdapter(settings, context!!, fragmentManager!!)
        settingsList.layoutManager  = LinearLayoutManager(activity)
    }
}