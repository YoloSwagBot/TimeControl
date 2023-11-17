package com.appstr.timecontrol

import android.app.Application
import com.appstr.timecontrol.domain.repo.GameDatabase
import com.appstr.timecontrol.domain.repo.GameRepo
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TimeControlApp : Application() {

    // Database/Repo
    val gameDatabase: GameDatabase by lazy { GameDatabase.getDatabase(this) }
    val gameRepo: GameRepo by lazy { GameRepo(gameDatabase.gameDao()) }


}