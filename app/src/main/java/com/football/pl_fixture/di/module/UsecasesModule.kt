package com.football.pl_fixture.di.module

import com.football.pl_fixture.domain.usecases.HomeUseCase
import org.koin.dsl.module.module

val homeUseCase = module {
    single { HomeUseCase(get() )}

}