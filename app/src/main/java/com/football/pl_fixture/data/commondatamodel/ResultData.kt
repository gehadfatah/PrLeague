package com.football.pl_fixture.data.commondatamodel

sealed class ResultData<out T> {
    data class Success<out T>(val data: T? = null) : ResultData<T>()
    data class Loading(val loading: Boolean? = null) : ResultData<Nothing>()
    data class Failed(
        val status: String? = null,
        val message: String? = null,
        val title: String? = null
    ) : ResultData<Nothing>()

    data class Exception(val exception: kotlin.Exception? = null, val msg: String? = null,val desc:String?=null) :
        ResultData<Nothing>()
}
