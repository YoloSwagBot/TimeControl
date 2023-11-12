package com.appstr.timecontrol

import android.app.Application
import com.appstr.timecontrol.domain.data.GameDatabase
import com.appstr.timecontrol.domain.data.repo.GameRepo

class TimeControlApp : Application() {

    // Database/Repo
    val gameDatabase: GameDatabase by lazy { GameDatabase.getDatabase(this) }
    val gameRepo: GameRepo by lazy { GameRepo(gameDatabase.gameDao()) }


}