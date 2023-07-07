package com.football.pl_fixture.domain

import com.football.pl_fixture.data.model.MatchesItem
import com.football.pl_fixture.data.services.response.FixtureResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

interface MatchesRepo {
    suspend fun getMatchList(from: String, to: String): Response<FixtureResponse>
    suspend fun getMatchListo(): FixtureResponse
    suspend fun getFavouriteMatches(): List<MatchesItem>
    suspend fun insertMatch( vararg matchesItem: MatchesItem)
    suspend fun getAllMatchesDb(): List<MatchesItem>
}