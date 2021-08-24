package com.example.moviecataloguev2.ui.splashscreen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviecataloguev2.databinding.ActivitySplashScreenBinding
import com.example.moviecataloguev2.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.util.*

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        Timer().schedule(object: TimerTask() {
            override fun run() {
                startActivity(intentFor<MainActivity>().clearTask().newTask())
            }
        }, 3000L)
    }
}