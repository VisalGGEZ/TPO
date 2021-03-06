package com.tpo_hr.tpohr.views.activities.main

import com.tpo_hr.tpohr.models.AccessTokenResponse

interface MainView {
    fun onGetTokenSuccess(tokenResponse: AccessTokenResponse?)
    fun onGetTokenFail()
    fun onRegisterFail()
    fun onRegisterSuccess()
}