package com.football.pl_fixture.app

import android.app.Application
import com.football.pl_fixture.di.module.*
import org.koin.android.ext.android.startKoin

class FixtureApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(homeUseCase,databaseModule,
            repoModule, networkModule, fixtureViewModel))
    }
}