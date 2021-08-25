package com.example.moviecataloguev2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.example.moviecataloguev2.R
import com.example.moviecataloguev2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        initUi()
    }

    private fun initUi() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuButton = menu?.findItem(R.id.menuSearch)
        menuButton?.setOnMenuItemClickListener {
            if (binding.bnNavigation.currentActiveItemPosition != 1) {
                binding.bnNavigation.setCurrentActiveItem(1)
                binding.btSearch.activate()
            }
            return@setOnMenuItemClickListener true
        }
        return true
    }
}