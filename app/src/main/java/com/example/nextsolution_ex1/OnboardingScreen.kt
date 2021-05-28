package com.example.nextsolution_ex1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_onboarding_screen.*

class OnboardingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_screen)
        var mHandler: Handler = Handler()
        mHandler.postDelayed(object : Runnable {
            override fun run() {
                val intent: Intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 4000)
    }
}