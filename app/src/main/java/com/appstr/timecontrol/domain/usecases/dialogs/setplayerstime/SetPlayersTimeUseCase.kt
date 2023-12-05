package com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime

import android.util.Log
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.util.hour
import com.appstr.timecontrol.util.isValidHours
import com.appstr.timecontrol.util.isValidMinutes
import com.appstr.timecontrol.util.isValidSeconds
import com.appstr.timecontrol.util.minute
import com.appstr.timecontrol.util.second
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SetPlayersTimeUseCase @Inject constructor(
    val _gameState: MutableStateFlow<GameState>
) {
    operator fun invoke(
        player: Player,
        hours: String,
        minutes: String,
        seconds: String,
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

        _gameState.update { game ->
            when (player){
                Player.ONE -> {
                    game.copy(
                        player1CurrentTime = newTime
                    )
                }
                Player.TWO -> {
                    game.copy(
                        player2CurrentTime = newTime
                    )
                }
            }
        }

//        Log.d("Carson", "SetPlayersTimeUseCase --POST-- p1_Time: ${newGameState.player1CurrentTime}")
//        Log.d("Carson", "SetPlayersTimeUseCase --POST-- p2_Time: ${newGameState.player2CurrentTime}")
//        gameVM.gState.value = newGameState
        return true
    }
}
