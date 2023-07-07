package com.football.pl_fixture.ui.fixture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.football.pl_fixture.data.model.MatchesItem
import com.football.pl_fixture.ui.base.BaseViewModel
import com.football.pl_fixture.utils.toHour

class MatchItemViewModel : BaseViewModel() {
    val matchHasStarted: MutableLiveData<Boolean> = MutableLiveData()
    private val _awayTeam = MutableLiveData<String>()
    val awayTeam: LiveData<String> get() = _awayTeam

    private val _awayScore = MutableLiveData<String>()
    val awayScore: LiveData<String> get() = _awayScore

    private val _homeTeam = MutableLiveData<String>()
    val homeTeam: LiveData<String> get() = _homeTeam
    private val _homeTeamImg = MutableLiveData<String>()
    val homeTeamImg: LiveData<String> get() = _homeTeamImg
    private val _wayTeamImg = MutableLiveData<String>()
    val wayTeamImg: LiveData<String> get() = _wayTeamImg
    private val _homeScore = MutableLiveData<String>()
    val homeScore: LiveData<String> get() = _homeScore

    private val _matchStatus = MutableLiveData<String>()
    val matchStatus: LiveData<String> get() = _matchStatus

    private val _matchTime = MutableLiveData<String>()
    val matchTime: LiveData<String> get() = _matchTime

    val isFavourite = MutableLiveData(false)

    fun bind(matchesItem: MatchesItem) {
        _awayTeam.value = matchesItem.awayteam.name
        _homeTeam.value = matchesItem.homeTeam.name
        _homeTeamImg.value = matchesItem.homeTeam.crest
        _wayTeamImg.value = matchesItem.awayteam.crest
        _matchStatus.value = matchesItem.status
        _awayScore.value = if (matchesItem.score?.fullTime?.awayTeam.toString().isNullOrEmpty())"-" else matchesItem.score?.fullTime?.awayTeam.toString()
        _homeScore.value = if (matchesItem.score?.fullTime?.homeTeam.toString().isNullOrEmpty()) "-" else matchesItem.score?.fullTime?.homeTeam.toString()
        isFavourite.value = matchesItem.isFavourite

        when (matchesItem.status) {
            "FINISHED" -> {
                matchHasStarted.value = true
            }
            else -> {
                matchHasStarted.value = false
                if (!matchesItem.date.isNullOrEmpty())
                _matchTime.value = toHour(matchesItem.date)
            }
        }
    }
}