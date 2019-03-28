package com.tpo_hr.tpohr.views.activities.main

import android.annotation.SuppressLint
import android.content.Context
import com.tpo_hr.tpohr.models.AccessTokenRequest
import com.tpo_hr.tpohr.utils.RxObservable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MainPresenterImplement(
    private val context: Context,
    private val mainService: MainService,
    private val mainView: MainView
) : MainPresenter {
    @SuppressLint("CheckResult")
    override fun getAccessToken(
        grantType: String,
        clientId: Int,
        clientSecret: String,
        scope: String?
    ) {

        val accessTokenRequest =
            AccessTokenRequest(grantType = grantType, clientSecret = clientSecret, clientId = clientId, scope = scope)

        RxObservable.wrapAsync(mainService.getAccessToken(accessTokenRequest)).subscribe({
            if (it.code() == 200) {
                mainView.onGetTokenSuccess(it.body())
            } else {
                mainView.onGetTokenFail()
            }
        }, {
            mainView.onGetTokenFail()
        })

    }

    @SuppressLint("CheckResult")
    override fun registerCandidate(
        authorization: String,
        photo: File,
        name: String,
        sex: String,
        dob: String,
        age: String,
        education: String,
        thaiLevel: Int,
        phone1: String,
        phone2: String
    ) {

//        val registerRequest = RegisterRequest(name = name, sex = sex, dob = dob, age = age, education = education, thaiLevel = thaiLevel, phone1 = phone1, phone2 = phone2)

        val reqFile = RequestBody.create(MediaType.parse("image/*"), photo)
        val body = MultipartBody.Part.createFormData("photo", photo.name, reqFile)

        RxObservable.wrapAsync(
            mainService.registerCandidate(
                toRequestBody(name),
                toRequestBody(sex),
                toRequestBody(dob),
                toRequestBody(age),
                toRequestBody(education),
                toRequestBody(thaiLevel.toString()),
                toRequestBody(phone1),
                toRequestBody(phone2), body
            )
        )
            .subscribe({
                if (it.code() == 200) {
                    mainView.onRegisterSuccess()
                } else {
                    mainView.onRegisterFail()
                }
            }, {
                mainView.onRegisterFail()
            })
    }

    private fun toRequestBody(value: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    }
}