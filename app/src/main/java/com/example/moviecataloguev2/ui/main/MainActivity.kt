package com.example.moviecataloguev2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import com.example.moviecataloguev2.R
import com.example.moviecataloguev2.databinding.ActivityMainBinding
import com.example.moviecataloguev2.ui.main.favorite.FavoriteFragment
import com.example.moviecataloguev2.ui.main.movie.MovieFragment
import com.example.moviecataloguev2.ui.main.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnRecyclerViewScrolled {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        initUi()
    }

    private fun initUi() {
        changeFragment(MovieFragment())
        binding.bnNavigation.setNavigationChangeListener { _, position ->
            when (position) {
                0 -> changeFragment(MovieFragment())
                1 -> changeFragment(SearchFragment())
                2 -> changeFragment(FavoriteFragment())
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fcvFragment.id, fragment)
            disallowAddToBackStack()
            commit()
        }
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

    override fun show() {
        binding.cvNavigation.animate().translationY((binding.cvNavigation.height + binding.cvNavigation.marginBottom).toFloat())
            .setInterpolator(AccelerateInterpolator(2F)).start()
    }

    override fun hide() {
        binding.cvNavigation.animate().translationY(0F).setInterpolator(DecelerateInterpolator(2F)).start()
    }
}