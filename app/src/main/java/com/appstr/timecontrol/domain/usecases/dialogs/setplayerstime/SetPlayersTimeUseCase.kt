package com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.util.hour
import com.appstr.timecontrol.util.isValidHours
import com.appstr.timecontrol.util.isValidMinutes
import com.appstr.timecontrol.util.isValidSeconds
import com.appstr.timecontrol.util.minute
import com.appstr.timecontrol.util.second
import com.appstr.timecontrol.util.timeISValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SetPlayersTimeUseCase @Inject constructor() {
    operator fun invoke(
        player: Player,
        hours: String,
        minutes: String,
        seconds: String,
        _gameState: MutableStateFlow<GameState?>,
    ): Boolean {
        val isTimeValid = (!hours.isValidHours() || !minutes.isValidMinutes() || !seconds.isValidSeconds())
                || !timeISValid(hours, minutes, seconds)
        if (!isTimeValid) return false
        // check if time is valid, return false/true
        val hrs = hours.toIntOrNull() ?: 0
        val mins = minutes.toIntOrNull() ?: 0
        val secs = seconds.toIntOrNull() ?: 0
        _gameState.update { gs ->
            when (player){
                Player.ONE -> gs?.copy(
                    player1CurrentTime = (hrs * hour) + (mins * minute) + (secs * second)
                )
                Player.TWO -> gs?.copy(
                    player2CurrentTime = (hrs * hour) + (mins * minute) + (secs * second)
                )
            }
        }
        return true
    }
}
