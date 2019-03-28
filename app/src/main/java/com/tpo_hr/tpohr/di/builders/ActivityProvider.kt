package com.tpo_hr.tpohr.di.builders

import com.tpo_hr.tpohr.views.activities.main.MainActivity
import com.tpo_hr.tpohr.views.activities.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityProvider{

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainAct() : MainActivity


}