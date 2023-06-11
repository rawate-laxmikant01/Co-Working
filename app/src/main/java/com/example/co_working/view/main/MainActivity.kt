package com.example.co_working.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.view.marginTop
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.co_working.R
import com.example.co_working.databinding.ActivityMainBinding
import com.example.co_working.viewmodel.DashboardViewModel
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    @ExperimentalCoroutinesApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
        observeNavElements(binding, navHostFragment.navController)

    }

    private fun initViews(binding: ActivityMainBinding) {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return

        with(navHostFragment.navController) {
            appBarConfiguration = AppBarConfiguration(graph)
            setupActionBarWithNavController(this, appBarConfiguration)
        }

    }

    private fun observeNavElements(binding: ActivityMainBinding, navController: NavController) {

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.homeFragment -> {
                    supportActionBar!!.setDisplayShowTitleEnabled(false)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                R.id.loginFragment -> {
                    supportActionBar!!.setDisplayShowTitleEnabled(false)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)

                }
                R.id.createAccountFragment -> {
                    supportActionBar!!.setDisplayShowTitleEnabled(false)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                else -> {
                    supportActionBar!!.setDisplayShowTitleEnabled(true)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)

                }
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment.navController.navigateUp()
        return super.onSupportNavigateUp()
    }

    fun toggleCardState(view: View) {
        when (view.id) {
            R.id.book_work_card -> {
                book_work_card.setBackgroundColor(resources.getColor(R.color.primary_color) )
                meeting_room_card.setBackgroundColor(resources.getColor(R.color.inactive_color))
            }
            R.id.meeting_room_card -> {
                book_work_card.setBackgroundColor(resources.getColor(R.color.inactive_color))
                meeting_room_card.setBackgroundColor(resources.getColor(R.color.primary_color))

            }
        }
    }



}