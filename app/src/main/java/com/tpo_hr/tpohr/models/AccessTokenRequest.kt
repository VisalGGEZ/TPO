package com.tpo_hr.tpohr.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class AccessTokenRequest(
    @SerializedName("grant_type")
    @Expose
    var grantType: String? = null,
    @SerializedName("client_id")
    @Expose
    var clientId: Int? = null,
    @SerializedName("client_secret")
    @Expose
    var clientSecret: String? = null,
    @SerializedName("scope")
    @Expose
    var scope: String? = null
)