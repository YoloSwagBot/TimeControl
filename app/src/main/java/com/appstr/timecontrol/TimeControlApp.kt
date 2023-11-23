package com.appstr.timecontrol

import android.app.Application
import com.appstr.timecontrol.domain.repo.GameStateDatabase
import com.appstr.timecontrol.domain.repo.GameStateRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class TimeControlApp : Application() {

    // Database/Repo
    @Inject lateinit var gameStateDatabase: GameStateDatabase
    @Inject lateinit var gameRepo: GameStateRepository


}