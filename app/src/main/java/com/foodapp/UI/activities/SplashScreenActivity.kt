package com.foodapp.UI.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R
import com.foodapp.builder.FoodBuilderImpl
import com.foodapp.repositories.FoodRepository
import com.foodapp.room.database.AppDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var repositoryFood: FoodRepository

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var builderImpl: FoodBuilderImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val activity = this

        repositoryFood.insertBasicProducts(db, repositoryFood, builderImpl)

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