package com.football.pl_fixture.data.MatchesRepoImpl

import com.football.pl_fixture.data.commondatamodel.ResultData
import com.football.pl_fixture.data.locale.room.dao.MatchesDao
import com.football.pl_fixture.data.model.MatchesItem
import com.football.pl_fixture.data.services.FixtureAPI
import com.football.pl_fixture.data.services.response.FixtureResponse
import com.football.pl_fixture.domain.MatchesRepo
import com.football.pl_fixture.utils.translateToError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


class MatchesRepoImpl(
    private val service: FixtureAPI, private val matchesDao: MatchesDao,
    private val context: CoroutineContext = Dispatchers.IO
) :
    MatchesRepo {

    override suspend fun getMatchList(from: String, to: String): FixtureResponse? =
        withContext(context) {

            val response = service.getMatchList(from, to)
            if (response.isSuccessful) {
                response.body()
            } else FixtureResponse(errors = " ${response.code()} ${response.message()}")

        }

    override suspend fun getMatchListo() = withContext(context) {
        service.getMatches() ?: FixtureResponse()
    }

    override suspend fun getMatches(): ResultData<FixtureResponse> = withContext(context) {


        try {
            val response = service.getMatches()
            if (response.errors == null) {
                val result = response
                result.let {

                    ResultData.Success(response)
                }
            } else {
                ResultData.Failed(
                    title = "API Error",
                    message = " Reason - ${response.errors}"
                )
            }
        } catch (ex: Exception) {
            ex.translateToError()
        }
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