package com.latifah.authdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.latifah.authdemo.databinding.ActivityMainBinding
//1. GET SIGNING REPORT
//2. GET SHA1 KEY FROM REPORT
//3. GO TO FIREBASE CONSOLE TO COMPLETE SETUP
//4. ADD JSON FILE TO APP
//4. ADD DEPENDENCIES AND SYNC
//5. AFTER GRADLE SYNC ENABLE EMAIL AND PASSWORD SIGNUP FROM FIREBASE CONSOLE
//6. CREATE A NEW FRAGMENT FOR REGISTER AND PROFILE FRAGMENT
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}