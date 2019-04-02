package com.tpo_hr.tpohr.utils

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import java.io.IOException

class Authorization(private val sharedPreferences: SharedPreferences) {

    private val WAS_USER_LOGIN = "WAS_USER_LOGIN"
    private val USRE_ID = "USER_ID"
    private val ACCESS_TOKEN = "ACCESS_TOKEN"
    private val EXPIRE_DATE = "EXPIRE_DATE"
    private val TOKEN_TYPE = "TOKEN_TYPE"
    private val SHOW_SLIDE = "SHOW_SLIDE"


    var isShowSlide: Boolean
        get() = sharedPreferences.getBoolean(SHOW_SLIDE, false)
        set(value) = sharedPreferences.edit().putBoolean(SHOW_SLIDE, value).apply()


    var wasLogin: Boolean
        get() = sharedPreferences.getBoolean(WAS_USER_LOGIN, false)
        set(value) = sharedPreferences.edit().putBoolean(WAS_USER_LOGIN, value).apply()

    var userID: String
        get() = sharedPreferences.getString(USRE_ID, "")
        set(value) = sharedPreferences.edit().putString(USRE_ID, value).apply()

    var accessToken: String
        get() = sharedPreferences.getString(ACCESS_TOKEN, "")
        set(value) = sharedPreferences.edit().putString(ACCESS_TOKEN, value).apply()

    var expireDate: String
        get() = sharedPreferences.getString(EXPIRE_DATE, "")
        set(value) = sharedPreferences.edit().putString(EXPIRE_DATE, value).apply()

    var tokenType: String
        get() = sharedPreferences.getString(TOKEN_TYPE, "")
        set(value) = sharedPreferences.edit().putString(TOKEN_TYPE, value).apply()

    var isSocialLogin: Boolean
        get() = sharedPreferences.getBoolean("isSocial", false)
        set(value) = sharedPreferences.edit().putBoolean("isSocial", value).apply()


}