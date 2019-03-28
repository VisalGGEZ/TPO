package com.tpo_hr.tpohr.views.activities.main

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule{

    @Provides
    fun provideMainView(mainActivity: MainActivity) : MainView{
       return mainActivity
    }

    @Provides
    fun provideMainService(retrofit: Retrofit) = retrofit.create(MainService::class.java)

    @Provides
    fun provideMainPresenter(context: Context, mainService: MainService, mainView: MainView) : MainPresenter{
        return MainPresenterImplement(context, mainService, mainView)
    }
}