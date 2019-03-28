package com.tpo_hr.tpohr.utils

import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(private val token: String?) : Interceptor {

    companion object {
        private const val AUTH = "Authorization"
        private const val ACCEPT = "Accept"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        token?.let { builder.addHeader(AUTH, it) }
        builder.addHeader(ACCEPT, "application/json")
        return chain.proceed(builder.build())
    }

}