package com.football.pl_fixture.ui.fixture.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.football.pl_fixture.data.model.MatchesItem
import com.football.pl_fixture.domain.usecases.HomeUseCase
import com.football.pl_fixture.ui.base.BaseViewModel
import com.football.pl_fixture.ui.fixture.FixtureAdapter
import com.football.pl_fixture.ui.fixture.OnFavouriteClickListener
import com.football.pl_fixture.utils.Utils
import kotlinx.coroutines.launch
import java.util.*

class FixtureViewModel(private val homeUseCase: HomeUseCase) :
    BaseViewModel(), OnFavouriteClickListener {

    val adapter: FixtureAdapter = FixtureAdapter(this)
    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val errorMassage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { fetchMatches() }
    val isFavouriteChose: MutableLiveData<Boolean> = MutableLiveData()

    init {
        fetchMatches()
        //getMatchesWithDates()
    }


    fun fetchMatches() {
        viewModelScope.launch {

            try {
                val apiMatchesList = homeUseCase.getMatches()

                homeUseCase.insertMatch(*apiMatchesList.matches.toTypedArray())
                val dbMatchesList = homeUseCase.getAllMatchesDb()
                onFetchingListSuccess(dbMatchesList)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }


            loadingVisibility.value = (false)
        }

    }

    override fun onFavouriteClicked(matchesItem: MatchesItem) {

        matchesItem.isFavourite = matchesItem.isFavourite.not()
        viewModelScope.launch {
            homeUseCase.insertMatch(matchesItem)

        }
    }

    fun getFavourites() {
        viewModelScope.launch {
            adapter.addMatches(homeUseCase.getFavouriteMatches())
        }
    }

    fun getAllMatches() {
        viewModelScope.launch {

            adapter.addMatches(homeUseCase.getMatches().matches)
            loadingVisibility.value = false
        }
    }

    private fun onFetchingListSuccess(matches: List<MatchesItem>) {
        adapter.addMatches(matches)
    }

    fun getMatchesWithDates() {
        viewModelScope.launch {

            homeUseCase.getMatchList(Utils.getDates(Date(), true))?.matches?.toTypedArray()
                ?.let { homeUseCase.insertMatch(*it) }
            val dbMatchesList = homeUseCase.getAllMatchesDb()
            onFetchingListSuccess(dbMatchesList)
            loadingVisibility.value=false
        }
    }

}