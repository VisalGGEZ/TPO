package com.tpo_hr.tpohr.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class RegisterRequest(
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("sex")
    @Expose
    var sex: String? = null,
    @SerializedName("dob")
    @Expose
    var dob: String? = null,
    @SerializedName("age")
    @Expose
    var age: String? = null,
    @SerializedName("education")
    @Expose
    var education: String? = null,
    @SerializedName("thai_level")
    @Expose
    var thaiLevel: Int? = null,
    @SerializedName("phone1")
    @Expose
    var phone1: String? = null,
    @SerializedName("phone2")
    @Expose
    var phone2: String? = null
)