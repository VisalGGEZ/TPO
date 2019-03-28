package com.tpo_hr.tpohr.di

import com.tpo_hr.tpohr.app.TPOApplication
import com.tpo_hr.tpohr.di.builders.ActivityProvider
import com.tpo_hr.tpohr.di.builders.FragmentProvider
import com.tpo_hr.tpohr.di.builders.ServiceProvider
import com.tpo_hr.tpohr.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    OkHttpClientModule::class,
    RetrofitModule::class,
    AuthorizationModule::class,
    ActivityProvider::class,
    BAInterceptor::class,
    ServiceProvider::class,
    FragmentProvider::class])
@Singleton
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TPOApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: TPOApplication)

}