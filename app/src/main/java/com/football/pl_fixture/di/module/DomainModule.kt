package com.football.pl_fixture.di.module

import com.football.pl_fixture.data.MatchesRepoImpl.MatchesRepoImpl
import org.koin.dsl.module.module

val repoModule = module {
    single { MatchesRepoImpl(get() ,get()) }

}