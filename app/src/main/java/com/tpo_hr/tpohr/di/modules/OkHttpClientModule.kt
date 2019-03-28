package com.tpo_hr.tpohr.di.modules

import com.tpo_hr.tpohr.utils.BasicAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class OkHttpClientModule {

    @Reusable
    @Provides
    @Named("ok-1")
    fun provideOkHttpClient1(
            basicAuthInterceptor: BasicAuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(1200, TimeUnit.SECONDS)
                .readTimeout(1200, TimeUnit.SECONDS)
                .addInterceptor(basicAuthInterceptor)
                .build()
    }

    @Reusable
    @Provides
    @Named("ok-2")
    fun provideOkHttpClient2(
            basicAuthInterceptor: BasicAuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(1200, TimeUnit.SECONDS)
                .readTimeout(1200, TimeUnit.SECONDS)
                .build()
    }


    @Reusable
    @Provides
    @Named("ok-search")
    fun provideOkHttpClientSearch(
            basicAuthInterceptor: BasicAuthInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .addInterceptor(basicAuthInterceptor)
                .build()
    }


}