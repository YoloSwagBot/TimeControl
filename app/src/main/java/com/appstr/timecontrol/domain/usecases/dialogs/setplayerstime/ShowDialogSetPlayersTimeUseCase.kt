package com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ShowDialogSetPlayersTimeUseCase @Inject constructor() {
    operator fun invoke(
        player: Player,
        _gameState: MutableStateFlow<GameState?>,
        _dialogSetPlayersTimeShowing: MutableStateFlow<Player?>
    ){
        _gameState.update { it?.copy(isPaused = true) }
        _dialogSetPlayersTimeShowing.update { player }
    }
}
