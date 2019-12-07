package xyz.kishorenarang.zotes

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import xyz.kishorenarang.zotes.datastore.CategoryDBHelper
import xyz.kishorenarang.zotes.datastore.ImageDBHelper
import xyz.kishorenarang.zotes.datastore.ZoteDBHelper

import xyz.kishorenarang.zotes.ui.create.ActionBottomDialogFragment


class MainActivity : AppCompatActivity() ,ActionBottomDialogFragment.ItemClickListener {
    override fun onItemClick(item: String?) {

    }





    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)

        ZoteDBHelper(this, null)
        CategoryDBHelper(this,null)
        ImageDBHelper(this,null)

        setContentView(R.layout.activity_main)
        val navView:BottomNavigationView = findViewById(R.id.nav_view)


        //ZoteDBHelper(this, null).addZote(Zote("TITLE","CONTENT", LocalDateTime.now(),"CANADA"))


//        CategoryDBHelper(this,null).addCategory(Category("Test"))

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_create, R.id.navigation_settings
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)




    }

    override fun onResume() {

        super.onResume()
        if (WHERE_TO_GO == 1)
        {

        }
    }
    companion object
    {
        var WHERE_TO_GO = 0
        fun goToHome()
        {

        }
    }
}
