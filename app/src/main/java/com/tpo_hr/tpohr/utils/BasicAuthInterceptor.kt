package com.tpo_hr.tpohr.utils

import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(private val token: String?) : Interceptor {

    companion object {
        private const val ACCEPT = "Accept"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader(ACCEPT, "application/json")
        return chain.proceed(builder.build())
    }

}