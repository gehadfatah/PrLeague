package com.football.pl_fixture.domain.usecases

import com.football.pl_fixture.data.MatchesRepoImpl.MatchesRepoImpl
import com.football.pl_fixture.data.model.MatchesItem

class HomeUseCase(
    private val repository: MatchesRepoImpl
) {
    suspend fun getMatchList(dates: Pair<String, String>) =
        repository.getMatchList(dates.first, dates.second)
    suspend fun getMatches() = repository.getMatchListo()
    suspend fun getMatche() = repository.getMatches()
    suspend fun getFavouriteMatches() = repository.getFavouriteMatches()
    suspend fun insertMatch(vararg matchesItem: MatchesItem) {
        return repository.insertMatch(*matchesItem)

    }

    suspend fun getAllMatchesDb():  List<MatchesItem> {
     return  repository.getAllMatchesDb()
    }
}