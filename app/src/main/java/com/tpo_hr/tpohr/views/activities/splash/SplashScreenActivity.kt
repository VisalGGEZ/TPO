package com.tpo_hr.tpohr.views.activities.splash

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tpo_hr.tpohr.R
import com.tpo_hr.tpohr.utils.Authorization
import com.tpo_hr.tpohr.views.activities.main.MainActivity
import com.tpo_hr.tpohr.views.activities.slide.SlideActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash_screen.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var authorization: Authorization

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            if(authorization.isShowSlide) {
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                startActivity(Intent(this, SlideActivity::class.java))
            }
            finish()
        }, 2500)

        tvSplashEnglish.typeface = Typeface.createFromAsset(assets, "Arial_Bold.ttf");
        tvSplashKhmer.typeface = Typeface.createFromAsset(assets, "Kantumruy_Bold.ttf");

    }
}
