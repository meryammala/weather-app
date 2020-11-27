package fr.alamary.weatherapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import fr.alamary.weatherapp.R
import fr.alamary.weatherapp.globals.ConnectionInternetLiveData

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return
        initToolBar()
        setActionBar(host)


    }

    private fun setActionBar(host: NavHostFragment) {
        // Set up Action Bar
        navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBar(navController, appBarConfiguration)
    }

    private fun initToolBar() {
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    fun setActionBar(withBackButton : Boolean){

    }

    fun checkInternetConnectivity(){

    }
    fun showProgressBar(){

    }
    fun hideProgressBar(){

    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
//        // This allows NavigationUI to decide what label to show in the action bar
//        // By using appBarConfig, it will also determine whether to
//        // show the up arrow or drawer menu icon
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}