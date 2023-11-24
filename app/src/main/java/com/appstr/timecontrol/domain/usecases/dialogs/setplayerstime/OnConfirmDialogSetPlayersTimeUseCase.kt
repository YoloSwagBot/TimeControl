package com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.util.hour
import com.appstr.timecontrol.util.minute
import com.appstr.timecontrol.util.second
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class OnConfirmDialogSetPlayersTimeUseCase @Inject constructor() {
    operator fun invoke(
        player: Player,
        hours: Int,
        minutes: Int,
        seconds: Int,
        _gameState: MutableStateFlow<GameState?>,
        _dialogSetPlayersTimeShowing: MutableStateFlow<Player?>
    ){
        _gameState.update { gs ->
            when (player){
                Player.ONE -> gs?.copy(
                    player1CurrentTime = (hours * hour) + (minutes * minute) + (seconds * second)
                )
                Player.TWO -> gs?.copy(
                    player2CurrentTime = (hours * hour) + (minutes * minute) + (seconds * second)
                )
            }
        }
        _dialogSetPlayersTimeShowing.update { null }
    }
}
