package com.tpo_hr.tpohr.di.modules

import android.content.Context
import com.tpo_hr.tpohr.app.TPOApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule{

    @Provides
    fun provideApplicationContext(app: TPOApplication): Context = app.applicationContext

}