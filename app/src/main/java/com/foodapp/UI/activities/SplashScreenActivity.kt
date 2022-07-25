package com.foodapp.UI.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.AppClass
import com.foodapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val activity = this

        // Setting Up Ingredients in DB
        val app = (application as AppClass)
        app.repositoryFood.insertBasicProducts(app)

        object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {

                activity.startActivity(
                    Intent(
                        baseContext, HomeActivity::
                        class.java
                    )
                )
                activity.finish()
            }
        }.start()
    }
}