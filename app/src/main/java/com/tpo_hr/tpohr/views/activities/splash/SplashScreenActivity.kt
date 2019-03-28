package com.tpo_hr.tpohr.views.activities.splash

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tpo_hr.tpohr.R
import com.tpo_hr.tpohr.views.activities.slide.SlideActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class SplashScreenActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, SlideActivity::class.java))
            finish()
        }, 2500)
    }
}
