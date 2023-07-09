package com.football.pl_fixture.data.services

import com.football.pl_fixture.BuildConfig
import com.football.pl_fixture.data.services.response.FixtureResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FixtureAPI {
    @GET("v2/competitions/2021/matches")
    suspend fun getMatches(@Header("X-Auth-Token") apiKey :String = BuildConfig.API_KEY) : FixtureResponse
    //this api to get match from date to date with images
    @GET("v4/matches")
    suspend fun getMatchList(
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String
    ): Response<FixtureResponse>
}