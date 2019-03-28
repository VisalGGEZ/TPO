package com.tpo_hr.tpohr.di.modules

import com.tpo_hr.tpohr.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class RetrofitModule {

    @Provides
    @Named("BASE_URL")
    fun provideBaseURL() = BuildConfig.BASE_URL

    @Reusable
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Reusable
    @Provides
    fun provideRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }


    @Reusable
    @Provides
    fun provideRetrofit(@Named("ok-1") client: OkHttpClient, converterFactory: GsonConverterFactory, adapterFactory: RxJava2CallAdapterFactory, @Named("BASE_URL") baseURL: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .build()
    }

    @Reusable
    @Provides
    @Named("UserDetail")
    fun provideRetrofitUserDetail(@Named("ok-2") client: OkHttpClient, converterFactory: GsonConverterFactory, adapterFactory: RxJava2CallAdapterFactory, @Named("BASE_URL") baseURL: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .build()
    }


    @Reusable
    @Provides
    @Named("retrofit-search")
    fun provideRetrofitForSearch(
            @Named("ok-search") client: OkHttpClient,
            converterFactory: GsonConverterFactory,
            adapterFactory: RxJava2CallAdapterFactory,
            @Named("BASE_URL") baseURL: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .build()
    }


}