package com.estebanposada.breakingbadtestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.estebanposada.breakingbadtestapp.R
import com.estebanposada.breakingbadtestapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBar: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBar = AppBarConfiguration(navController.graph)
//        NavigationUI.setupActionBarWithNavController(this, navController, appBar)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController, appBar) || super.onSupportNavigateUp()
//    }
}