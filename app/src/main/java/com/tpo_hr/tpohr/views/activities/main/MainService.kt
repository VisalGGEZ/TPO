package com.tpo_hr.tpohr.views.activities.main

import com.tpo_hr.tpohr.models.AccessTokenRequest
import com.tpo_hr.tpohr.models.AccessTokenResponse
import com.tpo_hr.tpohr.models.RegisterModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface MainService {

    @Multipart
    @POST("/public/api/candidate/register")
    fun registerCandidate(
        @Part("name") name: RequestBody,
        @Part("sex") sex: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("age") age: RequestBody,
        @Part("education") education: RequestBody,
        @Part("thai_level") thaiLevel: RequestBody,
        @Part("phone1") phone1: RequestBody,
        @Part("phone2") phone2: RequestBody,
        @Part photo: MultipartBody.Part
    ): Observable<Response<RegisterModel>>

    @POST("/public/oauth/token")
    fun getAccessToken(@Body accessTokenRequest: AccessTokenRequest): Observable<Response<AccessTokenResponse>>

}