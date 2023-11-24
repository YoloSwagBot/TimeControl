package com.appstr.timecontrol.domain.usecases.dialogs.cancelgame

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.TimeControl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ShowDialogCancelGameUseCase @Inject constructor() {
    operator fun invoke(
        timeControl: TimeControl,
        _gameState: MutableStateFlow<GameState?>,
        _dialogCancelGameShowing: MutableStateFlow<TimeControl?>
    ){
        _gameState.update { it?.copy(isPaused = true) }

        _dialogCancelGameShowing.update { timeControl }
    }
}
