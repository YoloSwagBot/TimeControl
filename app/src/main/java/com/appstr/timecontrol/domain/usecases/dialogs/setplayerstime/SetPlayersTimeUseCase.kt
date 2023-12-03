package com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime

import android.util.Log
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import com.appstr.timecontrol.util.hour
import com.appstr.timecontrol.util.isValidHours
import com.appstr.timecontrol.util.isValidMinutes
import com.appstr.timecontrol.util.isValidSeconds
import com.appstr.timecontrol.util.minute
import com.appstr.timecontrol.util.second
import javax.inject.Inject

class SetPlayersTimeUseCase @Inject constructor() {
    operator fun invoke(
        player: Player,
        hours: String,
        minutes: String,
        seconds: String,
        gameVM: GameViewModel
    ): Boolean {
        Log.d("Carson", "SetPlayersTimeUseCase ---- hours: ${hours} - minutes: ${minutes} - seconds: ${seconds}")
        val isTimeValid = (hours.isValidHours() && minutes.isValidMinutes() && seconds.isValidSeconds())
        Log.d("Carson", "SetPlayersTimeUseCase ---- isTimeValid0: ${isTimeValid}")
        if (!isTimeValid) return false
        // check if time is valid, return false/true
        val hrs = hours.toIntOrNull() ?: 0
        val mins = minutes.toIntOrNull() ?: 0
        val secs = seconds.toIntOrNull() ?: 0

        val newTime = (hrs * hour) + (mins * minute) + (secs * second)

        val currentGameState = gameVM.gState
        Log.d("Carson", "SetPlayersTimeUseCase --PRE-- original_GameState: ${currentGameState.player1CurrentTime}")
        Log.d("Carson", "SetPlayersTimeUseCase --PRE-- original_GameState: ${currentGameState.player2CurrentTime}")
        val newGameState = currentGameState.copy()
        Log.d("Carson", "SetPlayersTimeUseCase --PRE-- p1_Time: ${newGameState.player1CurrentTime}")
        Log.d("Carson", "SetPlayersTimeUseCase --PRE-- p2_Time: ${newGameState.player2CurrentTime}")
        when (player){
            Player.ONE -> {
                newGameState.player1CurrentTime = (hrs * hour) + (mins * minute) + (secs * second)
            }
            Player.TWO -> {
                newGameState.player2CurrentTime = (hrs * hour) + (mins * minute) + (secs * second)
            }
        }
        Log.d("Carson", "SetPlayersTimeUseCase --POST-- p1_Time: ${newGameState.player1CurrentTime}")
        Log.d("Carson", "SetPlayersTimeUseCase --POST-- p2_Time: ${newGameState.player2CurrentTime}")
        gameVM.gState = newGameState
        return true
    }
}
