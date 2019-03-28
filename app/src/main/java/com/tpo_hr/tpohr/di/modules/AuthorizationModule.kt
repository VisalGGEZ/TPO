package com.tpo_hr.tpohr.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.tpo_hr.tpohr.R
import com.tpo_hr.tpohr.utils.Authorization
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AuthorizationModule {

    @Provides
    @Named("Authorization")
    fun provideSharePreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    @Provides
    fun provideAuthorization(@Named("Authorization") sharedPreferences: SharedPreferences): Authorization {
        return Authorization(sharedPreferences)
    }

}