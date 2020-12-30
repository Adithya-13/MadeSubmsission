package com.extcode.project.madesubmission1.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.extcode.project.madesubmission1.R
import com.extcode.project.madesubmission1.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {

    private val splashScreenTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, splashScreenTime)
    }
}