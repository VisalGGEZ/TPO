package com.tpo_hr.tpohr.app

import android.app.Activity
import android.app.Application
import android.app.Service
import android.support.v4.app.Fragment
import com.tpo_hr.tpohr.R
import com.tpo_hr.tpohr.di.DaggerApplicationComponent
import com.tpo_hr.tpohr.utils.FontsOverride
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import android.os.StrictMode





class TPOApplication  : Application(), HasActivityInjector, HasServiceInjector, HasSupportFragmentInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        DaggerApplicationComponent.builder().application(this).build().inject(this)

        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("kantumruy_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )

    }

    override fun activityInjector() = activityInjector

    override fun serviceInjector() = serviceInjector

    override fun supportFragmentInjector() = fragmentInjector
}