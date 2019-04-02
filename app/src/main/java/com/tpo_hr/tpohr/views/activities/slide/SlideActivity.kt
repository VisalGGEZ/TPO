package com.tpo_hr.tpohr.views.activities.slide

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.TextView
import com.tpo_hr.tpohr.R
import com.tpo_hr.tpohr.views.activities.main.MainActivity
import com.tpo_hr.tpohr.views.activities.slide.fragments.SlideOneFragment
import com.tpo_hr.tpohr.views.activities.slide.fragments.SlideThreeFragment
import com.tpo_hr.tpohr.views.activities.slide.fragments.SlideTwoFragment
import me.relex.circleindicator.CircleIndicator
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class SlideActivity : AppCompatActivity() {

    private val listFragments = arrayListOf(SlideOneFragment(), SlideTwoFragment(), SlideThreeFragment())

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide)


        val viewPager = findViewById<ViewPager>(R.id.vpSlider)
        viewPager.adapter = SlideFragmentAdapter(supportFragmentManager, listFragments)

        val circleIndicator = findViewById<CircleIndicator>(R.id.indicator)
        circleIndicator.setViewPager(viewPager)

        findViewById<TextView>(R.id.tvSkip).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
