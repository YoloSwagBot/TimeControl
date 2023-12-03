package com.appstr.timecontrol.domain.usecases.dialogs.cancelgame

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.TimeControl
import com.appstr.timecontrol.domain.usecases.SetNewGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class OnConfirmDialogCancelGameUseCase @Inject constructor() {
    operator fun invoke(
        dialogCancelGameTimeControl: TimeControl?,
        setNewGameUseCase: SetNewGameUseCase,
        _gameState: MutableStateFlow<GameState?>,
        _dialogCancelGameShowing: MutableStateFlow<TimeControl?>,
        _screenSetupTimeShowing: MutableStateFlow<Boolean>
    ){
//        dialogCancelGameTimeControl?.let { tc ->
//            setNewGameUseCase(tc, _gameState)
//        }
//
//        _dialogCancelGameShowing.update { null }
//        _screenSetupTimeShowing.update { false }
    }
}
