package com.football.pl_fixture.utils

import com.football.pl_fixture.data.commondatamodel.ResultData

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


fun <T> Response<T>.parseErrorResponseServer(): Errors {
    val gson = Gson()
    val type = object : TypeToken<Errors>() {}.type
    return gson.fromJson(errorBody()?.source()?.buffer?.snapshot()?.utf8()?.replace("\n", ""), type)
}

fun Exception.translateToError(): ResultData.Exception {
    when (this) {
        is HttpException -> {
            val str = this.response()?.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                ?.replace("\n", "")


            return ResultData.Exception(
                this,
                "${
                    this.response()?.errorBody()?.source()?.buffer?.snapshot()?.utf8()
                        ?.replace("\n", "")
                }"
           )
        }

        is IOException -> {
            return ResultData.Exception(
                this,
                "Couldn't reach server, Check your internet connection"
            )
        }

        else -> {
            return ResultData.Exception(
                this,
                "${this.message}"
            )
        }
    }
}
