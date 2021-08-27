package com.example.moviecataloguev2.ui.splashscreen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.moviecataloguev2.databinding.ActivitySplashScreenBinding
import com.example.moviecataloguev2.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.util.*

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel: SplashScreenViewModel by viewModels()

    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        snackbar = Snackbar.make(binding.root, "Checking data..", Snackbar.LENGTH_INDEFINITE)

        initObserver()

        snackbar.show()
        viewModel.cekGenre()
    }

    private fun initObserver() {
        viewModel.status.observe(this, {
            if (it) {
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        startActivity(intentFor<MainActivity>().clearTask().newTask())
                    }
                }, 1500L)
            } else {
                snackbar.setText("Downloading data..")
                viewModel.loadGenre("en-US")
            }
        })
        viewModel.loading.observe(this, {
            if (!it) startActivity(intentFor<MainActivity>().clearTask().newTask())
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        snackbar.dismiss()
    }
}