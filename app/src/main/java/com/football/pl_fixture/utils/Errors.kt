package com.football.pl_fixture.utils


import com.google.gson.annotations.SerializedName
import android.os.Parcelable

data class Errors(
    @SerializedName("email")
    val email: List<String?>? = listOf(),
    @SerializedName("target")
    val target:String? ="",

    @SerializedName("phone")
    val phone: List<String?>? = listOf() ,
    @SerializedName("last_name")
    val last_name: List<String?>? = listOf(),
    @SerializedName("first_name")
    val first_name: List<String?>? = listOf(),
    @SerializedName("password")
    val password: List<String?>? = listOf(),
    @SerializedName("password_confirmation")
    val password_confirmation: List<String?>? = listOf()


)