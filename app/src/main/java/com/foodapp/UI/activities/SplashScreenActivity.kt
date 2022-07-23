package com.foodapp.UI.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.AppClass
import com.foodapp.R
import com.foodapp.repositories.FoodRepository

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val activity = this
        object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                FoodRepository().insertFoodData(
                    application,
                    (application as AppClass).builder.makeVegBurger()
                )
                FoodRepository().getFoodData(application)
                activity.startActivity(
                    Intent(
                        baseContext, ProductActivity::
                        class.java
                    )
                )
                activity.finish()
            }
        }.start()
    }
}