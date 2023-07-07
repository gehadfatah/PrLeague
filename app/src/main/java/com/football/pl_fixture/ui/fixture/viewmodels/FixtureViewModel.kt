package com.football.pl_fixture.ui.fixture.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.football.pl_fixture.data.model.MatchesItem
import com.football.pl_fixture.domain.usecases.HomeUseCase
import com.football.pl_fixture.ui.base.BaseViewModel
import com.football.pl_fixture.ui.fixture.FixtureAdapter
import com.football.pl_fixture.ui.fixture.OnFavouriteClickListener
import kotlinx.coroutines.launch

class FixtureViewModel(private val homeUseCase: HomeUseCase) :
    BaseViewModel(), OnFavouriteClickListener {

    val adapter: FixtureAdapter = FixtureAdapter(this)
    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val errorMassage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { fetchMatches() }
    val isFavouriteChose: MutableLiveData<Boolean> = MutableLiveData()

    init {
        fetchMatches()
    }


    fun fetchMatches() {
        viewModelScope.launch {
            val dbMatchesList = homeUseCase.getAllMatchesDb()

            if (dbMatchesList.isEmpty()) {
                val apiMatchesList = homeUseCase.getMatches()
                homeUseCase.insertMatch(*apiMatchesList.matches.toTypedArray())
                onFetchingListSuccess(apiMatchesList.matches)
                loadingVisibility.value=false
            } else {

            }

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

        }
    }

    private fun onFetchingListSuccess(matches: List<MatchesItem>) {
        adapter.addMatches(matches)
    }


}