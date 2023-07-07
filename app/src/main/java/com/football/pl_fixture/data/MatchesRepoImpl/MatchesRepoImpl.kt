package com.football.pl_fixture.data.MatchesRepoImpl

import com.football.pl_fixture.data.locale.room.dao.MatchesDao
import com.football.pl_fixture.data.model.MatchesItem
import com.football.pl_fixture.data.services.FixtureAPI
import com.football.pl_fixture.data.services.response.FixtureResponse
import com.football.pl_fixture.domain.MatchesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


class MatchesRepoImpl(
    private val service: FixtureAPI, private val matchesDao: MatchesDao,
    private val context: CoroutineContext = Dispatchers.IO
) :
    MatchesRepo {

    override suspend fun getMatchList(from: String, to: String): Response<FixtureResponse> {
        return service.getMatchList(from, to)
    }

    override suspend fun getMatchListo(): FixtureResponse = withContext(context) {
        service.getMatches().blockingGet() ?: FixtureResponse()
    }

    override suspend fun getFavouriteMatches(): List<MatchesItem> = withContext(context) {
        matchesDao.getFavouriteMatches
    }

    override suspend fun insertMatch(vararg matchesItem: MatchesItem) {
        withContext(context) {
            matchesDao.insert(*matchesItem)
        }
    }

    override suspend fun getAllMatchesDb(): List<MatchesItem> = withContext(context) {
        matchesDao.getAllMatches
    }


}