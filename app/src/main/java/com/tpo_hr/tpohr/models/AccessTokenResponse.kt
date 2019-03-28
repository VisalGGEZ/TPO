package com.tpo_hr.tpohr.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class AccessTokenResponse(
    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null,
    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int? = null,
    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null
)