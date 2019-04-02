package com.tpo_hr.tpohr.views.activities.main

import com.tpo_hr.tpohr.utils.Authorization
import java.io.File

interface MainPresenter{

    fun getAccessToken(
        grantType: String,
        clientId:Int,
        clientSecret: String,
        scope: String? = null
    )

    fun registerCandidate(
        authorization: String,
        submission_date: String,
        photo: File,
        name: String,
        sex: String,
        dob: String,
        age: String,
        education: String,
        thaiLevel: String,
        phone1: String,
        phone2: String)

}